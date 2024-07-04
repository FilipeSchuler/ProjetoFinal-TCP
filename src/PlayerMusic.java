
import javax.sound.midi.*;
import javax.swing.*;

public class PlayerMusic {
    private static boolean isPlaying = false;

    private static Sequencer sequencer;

    private static long currentTick = 0;

    public static void PlayMusic() throws InvalidMidiDataException, MidiUnavailableException {
        AddVolumeListener();
        if (!isPlaying) {
            sequencer.setTickPosition(currentTick);
            sequencer.start();
            isPlaying = true;



            AddEndListener();
        }
    }

    public static void PauseMusic() throws MidiUnavailableException {
        if (isPlaying) {
            sequencer.stop();
            currentTick = sequencer.getTickPosition();
            isPlaying = false;
        }
    }

    public static void StopMusic() throws MidiUnavailableException {
        sequencer.close();
        currentTick = 0;
        isPlaying = false;
    }

    public static int GetBpmFromText(JTextField bpmText) {
        int bpm = 0;
        try {
            bpm = Integer.parseInt(bpmText.getText());
        } catch (NumberFormatException ex) {
            Interface.ShowBpmWarning();
        }
        return bpm;
    }

    public static void HandleMusicParameters(String music, int bpm) throws InvalidMidiDataException, MidiUnavailableException {
        sequencer = MidiSystem.getSequencer();

        if (sequencer.getSequence() == null){
            MusicGenerator musicGenerator = new MusicGenerator();
            sequencer.open();
            Sequence sequence = musicGenerator.GenerateMusic(music, bpm);
            sequencer.setSequence(sequence);
            sequencer.setTempoInBPM(musicGenerator.GetBPM());
        }
    }

    public static void AddEndListener() {
        sequencer.addMetaEventListener(meta -> {
            if (meta.getType() == Constant.END_OF_SEQUENCE) { // Verifica se a mensagem meta é o fim da sequência
                sequencer.close();
                isPlaying = false;
                currentTick = 0;
                Interface.UpdateVolumeBar(Constant.INITIAL_VOLUME);
            }
        });
    }

    public static void AddVolumeListener() throws MidiUnavailableException {
        sequencer.getTransmitter().setReceiver(new Receiver() {
            public void send(MidiMessage message, long timeStamp) {
                if (message instanceof ShortMessage sm) {
                    if(sm.getCommand() == Constant.NOTE_ON)
                        Interface.UpdateVolumeBar(sm.getData2());
                }
            }

            @Override
            public void close() {}
        });
    }
}
