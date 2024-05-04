/*
    Módulo contendo todas operações relacionadas às notas musicais.


*/

public class NoteUtils {

    MusicalNote note;
    private static int volume = Constant.INITIAL_VOLUME;

    public static void changeVolume(){
        volume = 2*volume;
    }
}