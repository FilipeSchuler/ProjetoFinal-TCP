/*
    Módulo usado para representar notas musicais por meio de um enum.
    Cada constante enum possui um valor associado ao valor MIDI da nota.
    "Mapeamento de notas e seus devidos MIDI's"
*/

public enum MusicalNote {
    DO(60),
    RE(62),
    MI(64),
    FA(65),
    SOL(67),
    LA(69),
    SI(71),
    INVALID_NOTE(0);

    private final int midiValue;

    MusicalNote(int midiValue) {
        this.midiValue = midiValue;
    }

    public int getMidiValue() {
        return this.midiValue;
    }

}
