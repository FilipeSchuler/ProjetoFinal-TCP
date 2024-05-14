/*
    Módulo contendo a função main (principal do programa)
*/

import javax.sound.midi.*;

public class Main {
    private static Sequencer sequencer;


    public static void main(String[] args) {

        Interface PlayerGUI = new Interface();
        PlayerGUI.Player.setVisible(true);

    }
}

