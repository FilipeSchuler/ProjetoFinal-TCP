
import javax.sound.midi.*;
import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerMusic {
    private static boolean IsPlaying = false;

    private static Sequencer sequencer;
    private static MusicGenerator musicGenerator;
    private static Sequence sequence;

    private static long currentTick = 0;

    public static void PlayMusic() throws InvalidMidiDataException, MidiUnavailableException {
        if (!IsPlaying) {
            sequencer.start();
            IsPlaying = true;
            VolumeListener();

            AddEndListener();
        }
    }

    public static void PauseMusic() throws MidiUnavailableException {
        if (IsPlaying) {
            sequencer.stop();
            currentTick = sequencer.getTickPosition();
            IsPlaying = false;
        }
    }

    public static void StopMusic() throws MidiUnavailableException {
        sequencer.close();
        currentTick = 0;
        IsPlaying = false;
    }

    public static int GetBpmFromText(JTextField bpmText) {
        int bpm = 0;
        try {
            bpm = Integer.parseInt(bpmText.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Digite um valor inteiro válido de BPM!");
        }
        return bpm;
    }

    public static void GenerateSequence(String musica, int bpm) throws InvalidMidiDataException, MidiUnavailableException {

        musicGenerator = new MusicGenerator();
        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequence = musicGenerator.generateMusic(musica, bpm);
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(musicGenerator.getBPM());
        sequencer.setTickPosition(currentTick);

    }

    public static void AddEndListener() {
        sequencer.addMetaEventListener(meta -> {
            if (meta.getType() == Constant.END_OF_SEQUENCE) { // Verifica se a mensagem meta é o fim da sequência
                sequencer.close();
                currentTick = 0;
                IsPlaying = false;
            }
        });
    }


    public static void VolumeListener() throws MidiUnavailableException {
        sequencer.getTransmitter().setReceiver(new Receiver() {
            public void send(MidiMessage message, long timeStamp) {
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.println("Dado2 = " + sm.getData2());
                    if(sm.getData2() != 0)
                        Interface.UpdateVolumeBar(sm.getData2());
                }
            }

            @Override
            public void close() {}
        });




      /*  Track[] tracks = sequence.getTracks();
        Track currentTrack = tracks[0];
        for (int j = 0; j < currentTrack.size(); j++) {
            MidiEvent event = currentTrack.get(j);
            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                Interface.UpdateVolumeBar(sm.getData2());
            }
        }
*/
    }
}
