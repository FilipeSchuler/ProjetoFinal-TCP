/*
    Módulo contendo a função main (principal do programa)

*/

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class Main {
    public static void main(String[] args) throws InvalidMidiDataException{

        MusicGenerator.musicGenerator();
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