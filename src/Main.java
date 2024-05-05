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




/*  COMO FUNCIONA A API JAVASOUND +/-

    import javax.sound.midi.*;
    public void tocar(){
        System.out.print("Tocando musica...");
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            ShortMessage message = new ShortMessage();
            message.setMessage(192, 0, 15, 0);

            MidiEvent event = new MidiEvent(message, 32);
            track.add(event);

            track.add(event);


            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, DO, 100), 0));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, DO, 100), 16));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, RE, 100), 16));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, RE, 100), 32));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, MI, 100), 32));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, MI, 100), 48));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, FA, 100), 48));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, FA, 100), 64));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, SOL, 100), 64));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, SOL, 100), 80));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, LA, 100), 80));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, LA, 100), 96));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, SI, 100), 96));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, SI, 100), 112));

            sequencer.setSequence(sequence);
            sequencer.setTempoInBPM(600);

            sequencer.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/