/*
    CLASSE CRIADA PARA AGRUPAR CONSTANTES QUE VÃO SER USADAS NO DECORRER DO PROJETO
*/

public class Constant {

    //SIMULAÇÕES DE ENTRADAS DE TEXTO
    public static final String MSG1 = "A.E>.A.E>.B.E>.B.E>CE>CE>DE>DE.BA.E.AA.E.AB.E.BB.E.B.CECCECDEDDE.BA.E.AA.E." +
                                        "AB.E.BB.E.B.CECCECDEDDE.B.EEEEEE";

    public static final String MSG2 = ".FAFE>>>FAED>>>DFDC>DAGDAGFDE>>FGFE>>>EGEC>CEC";
    public static final String MSG3 = ";.C!FBGA.";

    //VALORES INICIAIS
    public static final int INITIAL_TICK = 1;
    public static final int INITIAL_VOLUME = 20;
    public static final int INITIAL_OCTAVE = 0;
    public static final int INITIAL_BPM = 90;

    //ALGUNS PARAMETROS PARA O JAVA SOUND
    public static final int NUM_PULSES_PER_QUARTER = 3;
    public static final int FADE = 20;
    public static final int NOTE_ON = 144;
    public static final int NOTE_OFF = 128;
    public static final int DEFAULT_CHANNEL = 0;
    public static final int CHANGE_INSTRUMENT = 192;
    public static final int END_OF_SEQUENCE = 47;

    //CÓDIGOS (FLAGS) PARA ALGUMAS INSTRUÇÕES
    public static final int CODE_TO_CHANGE_VOLUME = -1;
    public static final int CODE_TO_CHANGE_OCTAVE = -2;
    public static final int CODE_INCREASED_DURATION = -3;
    public static final int CODE_DO_NOTHING = 0;

    //CONSTANTES NUMÉRICAS
    public static final int TWO = 2;

    //VOLUME
    public static final int MAX_VOLUME = 100;
    public static final int MIN_VOLUME = 0;

    //OITAVA
    public static final int MAX_OCTAVE = 12;
    public static final int MIN_OCTAVE = -12;
    public static final int OFFSET_OCTAVE = 12;

    //INSTRUMENT
    public static final int MAX_MIDI_INSTRUMENT = 126;

}
