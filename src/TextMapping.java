/*
    Aplica o mapeamento predefinido para converter o texto em informações musicais.
    Trata de decodificar o texto e armazenar as informações músicais em um vetor.
    Obs: o vetor decodificado deve conter SOMENTE os valores MIDI's, além de comandos de
    mudar volume e/ou oitavas

*/

public class TextMapping {

    public static int[] HandleInputText(String inputText){
        char[] musicToDecode = inputText.toCharArray();
        int[] decodedMusic = new int[musicToDecode.length];
        char charToDecode;

        for(int i = 0; i < musicToDecode.length; i++){
            charToDecode = musicToDecode[i];

            if(MusicalNote.isNote(charToDecode)){
                decodedMusic[i] = ConvertCharIntoNote(charToDecode);
            }
            else if (Instruments.isInstrument(charToDecode)){
                Instruments.currentInstrument = ConvertCharIntoInstrument(charToDecode);
                decodedMusic[i] = Instruments.currentInstrument;
            }
            else{
                decodedMusic[i] = ConvertCharIntoInstruction(charToDecode);
            }
        }

        return decodedMusic;
    }

    public static int ConvertCharIntoInstruction(char character){
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

    public static int ConvertCharIntoInstrument(char character){
        int instrumentMidiValue;

        switch (Character.toUpperCase(character)) {
            case '!' -> instrumentMidiValue = Instruments.AGOGO.GetMidiValue();
            case 'I', 'O', 'U' -> instrumentMidiValue = Instruments.HARPSICHORD.GetMidiValue();
            case '\n' -> instrumentMidiValue = Instruments.TUBULAR_BELLS.GetMidiValue();
            case ';' -> instrumentMidiValue = Instruments.PAN_FLUTE.GetMidiValue();
            case ',' -> instrumentMidiValue = Instruments.CHURCH_ORGAN.GetMidiValue();
            case '0','1','2','3','4',
                 '5','6','7','8','9' -> instrumentMidiValue = Instruments.GetMidiValueWithOffset(character);
            default -> instrumentMidiValue = Instruments.INVALID_INSTRUMENT.GetMidiValue();
        }
        return instrumentMidiValue;
    }

    public static int ConvertCharIntoNote(char character){
        int noteMidiValue;

        switch (character) {
            case 'A' -> noteMidiValue = MusicalNote.LA.GetMidiValue();
            case 'B' -> noteMidiValue = MusicalNote.SI.GetMidiValue();
            case 'C' -> noteMidiValue = MusicalNote.DO.GetMidiValue();
            case 'D' -> noteMidiValue = MusicalNote.RE.GetMidiValue();
            case 'E' -> noteMidiValue = MusicalNote.MI.GetMidiValue();
            case 'F' -> noteMidiValue = MusicalNote.FA.GetMidiValue();
            case 'G' -> noteMidiValue = MusicalNote.SOL.GetMidiValue();
            default -> noteMidiValue = MusicalNote.INVALID_NOTE.GetMidiValue();
        }
        return noteMidiValue;
    }
}
