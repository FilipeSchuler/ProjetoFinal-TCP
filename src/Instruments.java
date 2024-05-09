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
    DEFAULT_INSTRUMENT(0),
    INVALID_INSTRUMENT(-10);

    private final int midiValue;

    public static int currentInstrument = DEFAULT_INSTRUMENT.getMidiValue();

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

        if (isValidInstrument(midiValue))
            return midiValue;
        else
            return DEFAULT_INSTRUMENT.getMidiValue();
    }

    private static boolean isValidInstrument(int midiValue) {
        return midiValue <= Constant.MAX_MIDI_INSTRUMENT;
    }

    public static boolean isInstrument(int instrument){

        return instrument > DEFAULT_INSTRUMENT.getMidiValue();
    }

    public static boolean isInstrument(char noteChar){
        boolean isInstrument;

        switch (Character.toUpperCase(noteChar)){
            case '!', 'I', 'O', 'U', '\n', ';', ',',
                 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> isInstrument = true;

            default -> isInstrument = false;
        }
        return isInstrument;
    }
}


