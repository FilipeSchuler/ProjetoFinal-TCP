/*
    Módulo contendo a função main (principal do programa)
*/

import javax.sound.midi.*;

public class Main {
    private static Sequencer sequencer;


    public static void main(String[] args) throws InvalidMidiDataException, MidiUnavailableException {
        //Rodar a interface
        MusicGenerator musicGenerator = new MusicGenerator();

        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.setSequence(musicGenerator.generateMusic());

        sequencer.setTempoInBPM(musicGenerator.getBPM());
        sequencer.start();

        sequencer.addMetaEventListener(meta -> {
            if (meta.getType() == Constant.END_OF_SEQUENCE) { // Verifica se a mensagem meta é o fim da sequência
                sequencer.close();
            }
        });
    }
}

