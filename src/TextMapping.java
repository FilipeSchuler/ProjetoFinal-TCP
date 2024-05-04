/*
    Aplica o mapeamento predefinido para converter o texto em informações musicais.
    Trata de decodificar o texto e armazenar as informações músicais em um vetor.
    Obs: o vetor decodificado deve conter SOMENTE os valores MIDI's, além de comandos de
    mudar volume e/ou oitavas

*/


public class TextMapping {
    public static String inputText = Constant.MSG2;
        //
    public static int[] handleInputText(){
        char[] musicToDecode = inputText.toCharArray();
        int[] decodedMusic = new int[musicToDecode.length];

        //Sequence sequence = new Sequence(Sequence.PPQ, Constant.NUM_PULSES_PER_QUARTER);
        //musica codificada =    [A,M,e,s,a,..., !          /* os dois vetores devem ter
        //musica descodificada = [69(La), , , , , ..., 114  */ o mesmo tamanho
        char charTodecod;

        for(int i = 0; i < musicToDecode.length; i++){
            charTodecod = musicToDecode[i];
            if(isNote(charTodecod)){
                decodedMusic[i] = handleNotes(charTodecod);
            }
            else if (isInstrument(charTodecod)){
                Instruments.currentInstrument = handleInstruments(charTodecod);
                decodedMusic[i] = Instruments.currentInstrument;
            }
            else{
                decodedMusic[i] = handleInstructions(charTodecod);
            }
        }

        return decodedMusic;
    }

    public static int handleInstructions(char character){
        int instructionMidiValue;

        switch(character){
            case ' ' -> instructionMidiValue = Constant.COMMAND_DOUBLE_VOLUME;
            case '?', '.' -> instructionMidiValue = Constant.COMMAND_CHANGE_OCTAVE;
            default -> instructionMidiValue = 0; //SILENCIO OU PAUSA!!
        }

        return instructionMidiValue;
    }

    public static int handleInstruments(char character){
        int instrumentMidiValue;

        switch (Character.toUpperCase(character)) {
            case '!' -> instrumentMidiValue = Instruments.AGOGO.getMidiValue();
            case 'I', 'O', 'U' -> instrumentMidiValue = Instruments.HARPSICHORD.getMidiValue();
            case '\n' -> instrumentMidiValue = Instruments.TUBULAR_BELLS.getMidiValue();
            case ';' -> instrumentMidiValue = Instruments.PAN_FLUTE.getMidiValue();
            case ',' -> instrumentMidiValue = Instruments.CHURCH_ORGAN.getMidiValue();
            case '0','1','2','3','4',
                 '5','6','7','8','9' -> instrumentMidiValue = Instruments.getMidiValueWithOffset(character);
            default -> instrumentMidiValue = Instruments.INVALID_INSTRUMENT.getMidiValue();
        }
        return instrumentMidiValue;
    }


    public static int handleNotes(char character){
        int noteMidiValue;

        switch (character) {
            case 'A' -> noteMidiValue = MusicalNote.LA.getMidiValue();
            case 'B' -> noteMidiValue = MusicalNote.SI.getMidiValue();
            case 'C' -> noteMidiValue = MusicalNote.DO.getMidiValue();
            case 'D' -> noteMidiValue = MusicalNote.RE.getMidiValue();
            case 'E' -> noteMidiValue = MusicalNote.MI.getMidiValue();
            case 'F' -> noteMidiValue = MusicalNote.FA.getMidiValue();
            case 'G' -> noteMidiValue = MusicalNote.SOL.getMidiValue();
            default -> noteMidiValue = MusicalNote.INVALID_NOTE.getMidiValue();
        }
        return noteMidiValue;
    }

    public static boolean isNote(char noteChar){
        boolean isNote;

        switch (noteChar){
            case 'A', 'B', 'C', 'D', 'E', 'F', 'G' -> isNote = true;
            default -> isNote = false;
        }
        return isNote;
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
