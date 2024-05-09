import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.swing.*;

public class PlayerMusic {
    public static boolean IsPaused = false;
    public static boolean IsPlaying = false;
    public static int bpm;

    private static Sequencer sequencer;
    private static MusicGenerator musicGenerator;

    private static long meutick = 0;

    public static void PlayMusic() throws InvalidMidiDataException, MidiUnavailableException {
        if (!IsPlaying) {
            musicGenerator = new MusicGenerator();
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(musicGenerator.generateMusic());
            sequencer.setTempoInBPM(musicGenerator.getBPM());
            sequencer.setTickPosition(meutick);
            sequencer.start();
            IsPlaying = true;

            sequencer.addMetaEventListener(meta -> {
                if (meta.getType() == Constant.END_OF_SEQUENCE) { // Verifica se a mensagem meta é o fim da sequência
                    sequencer.close();
                    meutick = 0;
                    IsPlaying = false;
                }
            });
        }
    }
    public static void PauseMusic() throws MidiUnavailableException {
        if (IsPlaying) {
            sequencer.stop();
            meutick = sequencer.getTickPosition();
            IsPlaying = false;
        }
    }

    public static void StopMusic() throws MidiUnavailableException {
        if (IsPlaying) {
            sequencer.close();
            IsPlaying = false;
        }
    }

    public static int GetBpmFromText(JTextField bpmText){
        try {
            bpm = Integer.parseInt(bpmText.getText());
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Digite um valor inteiro válido de BPM!");
        }
        return bpm;
    }

    public static void VolumeCheck(){



    }
}
