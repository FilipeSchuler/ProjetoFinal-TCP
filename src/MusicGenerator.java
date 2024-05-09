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

    public Sequence sequence;
    public Track track;
    private int tick = 0;

    private int note;
    private int lastNote;
    private int instrument = Instruments.DEFAULT_INSTRUMENT.getMidiValue();
    public int bpm = Interface.bpm;
    private int octave = Constant.INITIAL_OCTAVE;
    private int volume = Constant.INITIAL_VOLUME;

    public MusicGenerator() throws InvalidMidiDataException{
        sequence = new Sequence(Sequence.PPQ, Constant.NUM_PULSES_PER_QUARTER); //Sequence(tipo de tempo, qntidade)
        track = sequence.createTrack();
    }

    public Sequence generateMusic() throws InvalidMidiDataException {
        int[] decodedMusic;

        decodedMusic = TextMapping.handleInputText();

        return musicTrack(decodedMusic);
    }

    private Sequence musicTrack(int[] music) throws InvalidMidiDataException {
        startTick();
        for (int musicalEvent : music) {

            if (MusicalNote.isNote(musicalEvent))
                handleNoteCase(musicalEvent);

            else if (musicalEvent == Constant.CODE_TO_CHANGE_OCTAVE)
                setOctave();

            else if (musicalEvent == Constant.CODE_TO_CHANGE_VOLUME)
                setVolume();

            else if (musicalEvent == Constant.CODE_INCREASED_DURATION)
                increasedDurationOfNote();

            else if (Instruments.isInstrument(musicalEvent))
                handleInstrumentCase(musicalEvent);

            else
                handleLastNoteCase(musicalEvent);
        }
        return this.sequence;
    }

    private void increasedDurationOfNote(){
        updateTick();
    }

    private void handleNoteCase(int musicalEvent) throws InvalidMidiDataException {
        setNote(musicalEvent);
        addNoteOnTrack();
        updateTick();
    }

    private void handleInstrumentCase(int musicalEvent) throws InvalidMidiDataException {
        setInstrument(musicalEvent);
        addInstrumentOnTrack();
    }

    private void handleLastNoteCase(int musicalEvent) throws InvalidMidiDataException {
        updateLastNote();
        setNote(musicalEvent);
        if(getLastNote() != Constant.CODE_DO_NOTHING)
        {
            addLastNoteOnTrack();
        }
        updateTick();
    }

    private void addNoteOnTrack() throws InvalidMidiDataException {
        MidiEvent noteOn = eventForNote(Constant.NOTE_ON, getNote()+getOctave(), getTick());
        MidiEvent noteOff = eventForNote(Constant.NOTE_OFF, getNote()+getOctave(),
                    getTick()+Constant.FADE);

        this.track.add(noteOn);
        this.track.add(noteOff);
    }

    private void addLastNoteOnTrack() throws InvalidMidiDataException {
        MidiEvent noteOn = eventForNote(Constant.NOTE_ON, getLastNote()+getOctave(), getTick());
        MidiEvent noteOff = eventForNote(Constant.NOTE_OFF, getLastNote()+getOctave(),
                        getTick()+Constant.NUM_PULSES_PER_QUARTER);

        this.track.add(noteOn);
        this.track.add(noteOff);
    }

    private MidiEvent eventForNote(int command, int noteMidiValue, int tick) throws InvalidMidiDataException {
        MidiEvent event;
        ShortMessage message = new ShortMessage();

        message.setMessage(command, Constant.DEFAULT_CHANNEL, noteMidiValue, getVolume());
        event = new MidiEvent(message,tick);

        return event;
    }

    private void addInstrumentOnTrack() throws InvalidMidiDataException {
        MidiEvent changeInstrument = eventForInstrument(getInstrument(), getTick());

        this.track.add(changeInstrument);
    }

    private MidiEvent eventForInstrument(int instrumentMidiValue, int tick) throws InvalidMidiDataException {
        MidiEvent event;
        ShortMessage message = new ShortMessage();

        message.setMessage(Constant.CHANGE_INSTRUMENT, Constant.DEFAULT_CHANNEL, instrumentMidiValue,0);
        event = new MidiEvent(message,tick);

        return event;
    }

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

    private int getInstrument(){
        return this.instrument;
    }
    private void setInstrument(int midiValue){
        this.instrument = midiValue;
    }

    private int getNote(){
        return this.note;
    }
    private void setNote(int midiValue){
        this.note = midiValue;
    }
    private int getLastNote(){
        return this.lastNote;
    }
    private void updateLastNote(){
        this.lastNote = getNote();
    }

    private int getOctave(){
        return this.octave;
    }
    private void setOctave(){
        this.octave = NoteUtils.changeOctave(getOctave());
    }

    private int getVolume(){
        return this.volume;
    }
    private void setVolume(){
        this.volume = NoteUtils.changeVolume(getVolume());
    }

    public int getBPM(){
        return this.bpm;
    }
}
