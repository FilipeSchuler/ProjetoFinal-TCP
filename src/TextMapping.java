/*
    Aplica o mapeamento predefinido para converter o texto em informações musicais.
    Trata de decodificar o texto e armazenar as informações músicais em um vetor.
    Obs: o vetor decodificado deve conter SOMENTE os valores MIDI's, além de comandos de
    mudar volume e/ou oitavas

*/

public class TextMapping {

    public static String inputText = Interface.musica;

    public static int[] handleInputText(){
        char[] musicToDecode = inputText.toCharArray();
        int[] decodedMusic = new int[musicToDecode.length];
        char charToDecode;

        //musica codificada =    [A, ,M,e,n,s,..., !          /* os dois vetores devem ter
        //musica descodificada = [69(La), , , , , ..., 114  */ o mesmo tamanho

        for(int i = 0; i < musicToDecode.length; i++){
            charToDecode = musicToDecode[i];
            if(MusicalNote.isNote(charToDecode)){
                decodedMusic[i] = convertCharIntoNote(charToDecode);
            }
            else if (Instruments.isInstrument(charToDecode)){
                Instruments.currentInstrument = convertCharIntoInstrument(charToDecode);
                decodedMusic[i] = Instruments.currentInstrument;
            }
            else{
                decodedMusic[i] = convertCharIntoInstruction(charToDecode);
            }
        }

        return decodedMusic;
    }

    public static int convertCharIntoInstruction(char character){
        int instructionCode;

        switch(character){
            case ' ' -> instructionCode = Constant.CODE_TO_CHANGE_VOLUME;
            case '?', '.' -> instructionCode = Constant.CODE_TO_CHANGE_OCTAVE;

            //NÃO SÃO ESPECIFICAÇÃO DO TRABALHO
            case '>' -> instructionCode = Constant.CODE_INCREASED_DURATION;

            default -> instructionCode = Constant.CODE_DO_NOTHING; //SILENCIO OU PAUSA!!
        }

        return instructionCode;
    }

    public static int convertCharIntoInstrument(char character){
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


    public static int convertCharIntoNote(char character){
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
}
