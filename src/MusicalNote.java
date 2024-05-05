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

    public static MusicalNote getNoteFromMidiValue(int midiValue) {
        for (MusicalNote note : MusicalNote.values()) {
            if (note.getMidiValue() == midiValue) {
                return note;
            }
        }
        return MusicalNote.INVALID_NOTE;
    }

    public static boolean isNote(int command){
        MusicalNote note = getNoteFromMidiValue(command);

        return note != MusicalNote.INVALID_NOTE;
    }

    public static boolean isNote(char noteChar){
        boolean isNote;

        switch (noteChar){
            case 'A', 'B', 'C', 'D', 'E', 'F', 'G' -> isNote = true;
            default -> isNote = false;
        }
        return isNote;
    }

}
