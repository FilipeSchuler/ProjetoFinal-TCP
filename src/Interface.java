import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class Interface {

    public static final int WIDTH_WINDOW = 800;
    public static final int HEIGHT_WINDOW = 600;
    public static final int WIDTH_BUTTON = 70;
    public static final int HEIGHT_BUTTON = 70;
    public static final int POSITION_X_BUTTON = 680;
    public static final int POSITION_Y_BUTTON = 130;
    public static final int OFFSET_BETWEEN_BUTTONS = 100;
    public static final int TEXT_MUSIC_SPACE_WIDTH = 600;
    public static final int TEXT_MUSIC_SPACE_HEIGHT = 400;
    public static final int POSITION_X_HEADTEXT = 5;
    public static final int POSITION_Y_HEADTEXT = 0;
    public static final int HEIGHT_HEADTEXT = 20;
    public static final int WIDTH_HEADTEXT = 160;
    public static final int FONT_SIZE = 20;
    public static final int POSITION_X_TEXTAREA = 5;
    public static final int POSITION_Y_TEXTAREA = 20;
    public static final int POSITION_X_BPM_LABEL = 650;
    public static final int POSITION_Y_BPM_LABEL = 350;
    public static final int HEIGHT_BPM_LABEL = 20;
    public static final int WIDTH_BPM_LABEL = 30;
    public static final int POSITION_X_BPM_TEXT = 690;
    public static final int POSITION_Y_BPM_TEXT = 350;
    public static final int HEIGHT_BPM_TEXT = 20;
    public static final int WIDTH_BPM_TEXT = 50;
    public static final int POSITION_X_SCROLLPANE = 5;
    public static final int POSITION_Y_SCROLLPANE = 20;
    public static final int HEIGHT_SCROLLPANE = 400;
    public static final int WIDTH_SCROLLPANE = 600;
    public static final int POSITION_X_VOLUMEBAR_LABEL = 5;
    public static final int POSITION_Y_VOLUMEBAR_LABEL = 450;
    public static final int VOLUMEBAR_LABEL_WIDTH = 160;
    public static final int VOLUMEBAR_LABEL_HEIGHT = 20;
    public static final int VOLUMEBAR_SLIDER_MIN = 0;
    public static final int VOLUMEBAR_SLIDER_MAX = 100;
    public static final int VOLUMEBAR_SLIDER_INITIAL_VALUE = 5;
    public static final int VOLUMEBAR_SLIDER_MAJOR_SPACING = 10;
    public static final int VOLUMEBAR_SLIDER_MINOR_SPACING = 5;
    public static final int POSITION_X_VOLUMEBAR = 5;
    public static final int POSITION_Y_VOLUMEBAR = 480;
    public static final int VOLUMEBAR_WIDTH = 600;
    public static final int VOLUMEBAR_HEIGHT = 50;

    public static boolean IsPlaying = false;
//    private JSlider volumeBar;
    public static String musica;
    public static int bpm;
    public JFrame Player;



    public Interface() {

        Player = new JFrame("Tocador_de_Musica");

        //BOTOES PLAY, PAUSE, STOP

        JButton playButton = new JButton("Play");
        Player.add(playButton);
        playButton.setBounds(POSITION_X_BUTTON, POSITION_Y_BUTTON - OFFSET_BETWEEN_BUTTONS, WIDTH_BUTTON, HEIGHT_BUTTON);

        JButton pauseButton = new JButton("Pause");
        Player.add(pauseButton);
        pauseButton.setBounds(POSITION_X_BUTTON, POSITION_Y_BUTTON, WIDTH_BUTTON, HEIGHT_BUTTON);

        JButton stopButton = new JButton("Stop");
        Player.add(stopButton);
        stopButton.setBounds(POSITION_X_BUTTON, POSITION_Y_BUTTON + OFFSET_BETWEEN_BUTTONS, WIDTH_BUTTON, HEIGHT_BUTTON);


//AREA DO TEXTO

        JLabel label = new JLabel("Escreva a seguir seu texto:");
        Player.add(label);
        label.setBounds(POSITION_X_HEADTEXT, POSITION_Y_HEADTEXT, WIDTH_HEADTEXT, HEIGHT_HEADTEXT);

        JTextArea musicatexto = new JTextArea();
        musicatexto.setLineWrap(true);
        musicatexto.setWrapStyleWord(true);
        musicatexto.setFont(new Font("arial", Font.PLAIN, FONT_SIZE));
        Player.add(musicatexto);
        musicatexto.setBounds(POSITION_X_TEXTAREA, POSITION_Y_TEXTAREA, TEXT_MUSIC_SPACE_WIDTH, TEXT_MUSIC_SPACE_HEIGHT);

//AREA BPM
        JTextField bpmText = new JTextField();
        bpmText.setBounds(POSITION_X_BPM_TEXT, POSITION_Y_BPM_TEXT, WIDTH_BPM_TEXT, HEIGHT_BPM_TEXT);
        Player.add(bpmText);

        JLabel bpmLabel = new JLabel("BPM:");
        bpmLabel.setBounds(POSITION_X_BPM_LABEL, POSITION_Y_BPM_LABEL, WIDTH_BPM_LABEL, HEIGHT_BPM_LABEL);
        Player.add(bpmLabel);



//scrollbar
        JScrollPane scrollPane = new JScrollPane(musicatexto);
        scrollPane.setBounds(POSITION_X_SCROLLPANE, POSITION_Y_SCROLLPANE, WIDTH_SCROLLPANE, HEIGHT_SCROLLPANE);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Player.add(scrollPane);

//BARRA VOLUME

        JLabel textoVolume = new JLabel("Volume:");
        Player.add(textoVolume);
        textoVolume.setBounds(POSITION_X_VOLUMEBAR_LABEL, POSITION_Y_VOLUMEBAR_LABEL, VOLUMEBAR_LABEL_WIDTH, VOLUMEBAR_LABEL_HEIGHT);
        // Adiciona a barra de volume
        JSlider VolumeBar = new JSlider(JSlider.HORIZONTAL, VOLUMEBAR_SLIDER_MIN, VOLUMEBAR_SLIDER_MAX, VOLUMEBAR_SLIDER_INITIAL_VALUE);
        VolumeBar.setMajorTickSpacing(VOLUMEBAR_SLIDER_MAJOR_SPACING);
        VolumeBar.setMinorTickSpacing(VOLUMEBAR_SLIDER_MINOR_SPACING);
        VolumeBar.setPaintTicks(true);
        VolumeBar.setPaintLabels(true);
        Player.add(VolumeBar);
        VolumeBar.setBounds(POSITION_X_VOLUMEBAR, POSITION_Y_VOLUMEBAR, VOLUMEBAR_WIDTH, VOLUMEBAR_HEIGHT);

//JANELA DA INTERFACE PRINCIPAL
        Player.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Player.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        Player.setLayout(null);
        Player.setVisible(true);
        Player.setLocationRelativeTo(null);
        Player.setResizable(false);

//AÇOES DOS BOTOES
        playButton.addActionListener(e -> {
            musica = musicatexto.getText();
            bpm = PlayerMusic.GetBpmFromText(bpmText);
            if (musica.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite uma musica!");
            }else if (!IsPlaying) {
                try {
                    PlayerMusic.PlayMusic();
                } catch (InvalidMidiDataException | MidiUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        pauseButton.addActionListener(e -> {
            try {
                PlayerMusic.PauseMusic();
            } catch (MidiUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });

        stopButton.addActionListener(e -> {
            try{
                PlayerMusic.StopMusic();
            }catch (MidiUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
//AÇÃO DA BARRA DE VOLUME




}
