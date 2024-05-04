/*
    Módulo que de fato cria a música.
    Recebe os parâmetro devidamente tratados e modificados de acordo
    com o desejado e adiciona na track

    Entendendo JavaSound:
    Sequencer = responsável por controlar a reprodução -> "maestro"
    Track = eventos musicais organizados em uma linha do tempo -> "instrumento"
    -'uma Sequence é composta por várias tracks'
    Sequence = contém todos eventos musicais (notas, instrumentos, bpm, etc) -> "partitura"

*/

import javax.sound.midi.*;

public class MusicGenerator {

    private static Sequencer sequencer;
    private static Sequence sequence;
    private static Track track;
    private int tick = 0;

    public static int[] decodedMusic = null;

    public static void musicGenerator() throws InvalidMidiDataException{
        sequence = new Sequence(Sequence.PPQ, Constant.NUM_PULSES_PER_QUARTER); //Sequence(tipo de tempo, qntidade)
        track = sequence.createTrack();

        generateMusic();
    }

    //Tratamento dos TICKS
    private void startTick(){
        setTick(Constant.INITIAL_TICK);
    }
    public void setTick(int tick){
        this.tick = tick;
    }
    public int getTick(){
        return this.tick;
    }
    private void updateTick(){
        setTick(getTick() + Constant.NUM_PULSES_PER_QUARTER);
    }


    public static void generateMusic() {
        /*  Para gerar a música é necessário
            1- descodificar o texto de entrada - OK
            2- efetuar tratamentos para cada item do vetor descodificado
                2.1- Sempre atualizar o tick a cada tratamento
                -alterar volume se necessário
                -Trocar instrumentos e adicionar na track
                -alterar oitava se necessário
                -Adicionar nota na track
            3- os parametros para tocar a música devem estar corretos

        */

        decodedMusic = TextMapping.handleInputText();
    }


    private void addNoteOnTrack(int noteMidiValue) throws InvalidMidiDataException {
        track.add(newEventForNote(Constant.COMMAND_NOTEON, noteMidiValue, getTick()));
        track.add(newEventForNote(Constant.COMMAND_NOTEOFF, noteMidiValue, getTick()+Constant.NUM_PULSES_PER_QUARTER));
    }

    private MidiEvent newEventForNote(int command, int noteMidiValue, int tick) throws InvalidMidiDataException {
        MidiEvent event;
        ShortMessage message = new ShortMessage();
        message.setMessage(command, 0, noteMidiValue, tick);
        event = new MidiEvent(message,tick);

        return event;
    }

}
