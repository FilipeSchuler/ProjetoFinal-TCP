/*
    Módulo usado para representar instrumentos por meio de um enum.
    Cada constante enum possui um valor associado ao valor MIDI do instrumento.

*/

public enum Instruments {
    AGOGO(114),
    HARPSICHORD(7),
    TUBULAR_BELLS(15),
    PAN_FLUTE(76),
    CHURCH_ORGAN(20),
    INVALID_INSTRUMENT(0);

    private final int midiValue;

    public static int currentInstrument;

    Instruments(int midiValue) {
        this.midiValue = midiValue;
    }

    public int getMidiValue() {
        return midiValue;
    }

    public static int getMidiValueWithOffset(char character) {
        int midiValue;
        int digitValue = Character.getNumericValue(character);

        midiValue = digitValue + currentInstrument;

        return midiValue;
    }


}


