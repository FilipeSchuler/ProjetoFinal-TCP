/*
    Módulo contendo todas operações relacionadas às notas musicais.

*/

public class NoteUtils {

    public static int ChangeVolume(int volume) {
        int newVolume = volume * Constant.TWO;

        if (isVolumeValid(newVolume))
            return newVolume;
        else
            return Constant.INITIAL_VOLUME;
    }

    private static boolean isVolumeValid(int volume){
        return volume >= Constant.MIN_VOLUME && volume <= Constant.MAX_VOLUME;
    }

    public static int ChangeOctave(int octave){
        int newOctave = octave + Constant.OFFSET_OCTAVE;

        if (isOctaveValid(newOctave))
            return newOctave;
        else
            return Constant.INITIAL_OCTAVE;
    }

    private static boolean isOctaveValid(int octave){
        return octave >= Constant.MIN_OCTAVE && octave <= Constant.MAX_OCTAVE;
    }
}