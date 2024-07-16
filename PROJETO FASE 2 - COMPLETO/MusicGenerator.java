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
    private int instrument = Instruments.DEFAULT_INSTRUMENT.GetMidiValue();
    public int bpm;
    private int octave = Constant.INITIAL_OCTAVE;
    private int volume = Constant.INITIAL_VOLUME;

    public MusicGenerator() throws InvalidMidiDataException{
        sequence = new Sequence(Sequence.PPQ, Constant.NUM_PULSES_PER_QUARTER); //Sequence(tipo de tempo, qntidade)
        track = sequence.createTrack();
    }

    public Sequence GenerateMusic(String musicText, int bpm) throws InvalidMidiDataException {

        int[] decodedMusic = DecodeMusicText(musicText);

        SetBpm(bpm);
        CreateMusicTrack(decodedMusic);

        return this.sequence;
    }

    private int[] DecodeMusicText(String musicToDecode){
        return TextMapping.HandleInputText(musicToDecode);
    }

    private void CreateMusicTrack(int[] music) throws InvalidMidiDataException {
        StartTick();
        for (int musicalEvent : music) {

            if (MusicalNote.isNote(musicalEvent))
                HandleNoteCase(musicalEvent);

            else if (musicalEvent == Constant.CODE_TO_CHANGE_OCTAVE)
                SetOctave();

            else if (musicalEvent == Constant.CODE_TO_CHANGE_VOLUME)
                SetVolume();

            else if (musicalEvent == Constant.CODE_INCREASED_DURATION)
                IncreasedDurationOfNote();

            else if (Instruments.isInstrument(musicalEvent))
                HandleInstrumentCase(musicalEvent);

            else
                HandleLastNoteCase(musicalEvent);
        }
    }

    private void IncreasedDurationOfNote(){
        UpdateTick();
    }

    private void HandleNoteCase(int musicalEvent) throws InvalidMidiDataException {
        SetNote(musicalEvent);
        AddNoteOnTrack();
        UpdateTick();
    }

    private void HandleInstrumentCase(int musicalEvent) throws InvalidMidiDataException {
        SetInstrument(musicalEvent);
        AddInstrumentOnTrack();
    }

    private void HandleLastNoteCase(int musicalEvent) throws InvalidMidiDataException {
        UpdateLastNote();
        SetNote(musicalEvent);
        if(GetLastNote() != Constant.CODE_DO_NOTHING)
        {
            AddLastNoteOnTrack();
        }
        UpdateTick();
    }

    private void AddNoteOnTrack() throws InvalidMidiDataException {
        MidiEvent noteOn = EventForNote(Constant.NOTE_ON, GetNote()+ GetOctave(), GetTick());
        MidiEvent noteOff = EventForNote(Constant.NOTE_OFF, GetNote()+ GetOctave(),
                    GetTick()+Constant.FADE);

        this.track.add(noteOn);
        this.track.add(noteOff);
    }

    private void AddLastNoteOnTrack() throws InvalidMidiDataException {
        MidiEvent noteOn = EventForNote(Constant.NOTE_ON, GetLastNote()+ GetOctave(), GetTick());
        MidiEvent noteOff = EventForNote(Constant.NOTE_OFF, GetLastNote()+ GetOctave(),
                        GetTick()+Constant.NUM_PULSES_PER_QUARTER);

        this.track.add(noteOn);
        this.track.add(noteOff);
    }

    private MidiEvent EventForNote(int command, int noteMidiValue, int tick) throws InvalidMidiDataException {
        MidiEvent event;
        ShortMessage message = new ShortMessage();

        message.setMessage(command, Constant.DEFAULT_CHANNEL, noteMidiValue, GetVolume());
        event = new MidiEvent(message,tick);

        return event;
    }

    private void AddInstrumentOnTrack() throws InvalidMidiDataException {
        MidiEvent changeInstrument = EventForInstrument(GetInstrument(), GetTick());

        this.track.add(changeInstrument);
    }

    private MidiEvent EventForInstrument(int instrumentMidiValue, int tick) throws InvalidMidiDataException {
        MidiEvent event;
        ShortMessage message = new ShortMessage();

        message.setMessage(Constant.CHANGE_INSTRUMENT, Constant.DEFAULT_CHANNEL, instrumentMidiValue,0);
        event = new MidiEvent(message,tick);

        return event;
    }

    private void StartTick(){
        SetTick(Constant.INITIAL_TICK);
    }
    public void SetTick(int tick){
        this.tick = tick;
    }
    public int GetTick(){
        return this.tick;
    }
    private void UpdateTick(){
        SetTick(GetTick() + Constant.NUM_PULSES_PER_QUARTER);
    }

    private int GetInstrument(){
        return this.instrument;
    }
    private void SetInstrument(int midiValue){
        this.instrument = midiValue;
    }

    private int GetNote(){
        return this.note;
    }
    private void SetNote(int midiValue){
        this.note = midiValue;
    }
    private int GetLastNote(){
        return this.lastNote;
    }
    private void UpdateLastNote(){
        this.lastNote = GetNote();
    }

    private int GetOctave(){
        return this.octave;
    }
    private void SetOctave(){
        this.octave = NoteUtils.ChangeOctave(GetOctave());
    }

    private int GetVolume(){
        return this.volume;
    }
    private void SetVolume(){
        this.volume = NoteUtils.ChangeVolume(GetVolume());
    }

    public int GetBPM(){
        return this.bpm;
    }
    public void SetBpm(int bpm){
        this.bpm = bpm;
    }
}
