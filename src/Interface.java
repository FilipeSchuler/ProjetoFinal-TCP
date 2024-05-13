import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class Interface {

    private static final int WIDTH_WINDOW = 800;
    private static final int HEIGHT_WINDOW = 600;
    private static final int WIDTH_BUTTON = 70;
    private static final int HEIGHT_BUTTON = 70;
    private static final int POSITION_X_BUTTON = 680;
    private static final int POSITION_Y_BUTTON = 130;
    private static final int OFFSET_BETWEEN_BUTTONS = 100;
    private static final int TEXT_MUSIC_SPACE_WIDTH = 600;
    private static final int TEXT_MUSIC_SPACE_HEIGHT = 400;
    private static final int POSITION_X_HEADTEXT = 5;
    private static final int POSITION_Y_HEADTEXT = 0;
    private static final int HEIGHT_HEADTEXT = 20;
    private static final int WIDTH_HEADTEXT = 160;
    private static final int FONT_SIZE = 20;
    private static final int POSITION_X_TEXTAREA = 5;
    private static final int POSITION_Y_TEXTAREA = 20;
    private static final int POSITION_X_BPM_LABEL = 650;
    private static final int POSITION_Y_BPM_LABEL = 350;
    private static final int HEIGHT_BPM_LABEL = 20;
    private static final int WIDTH_BPM_LABEL = 30;
    private static final int POSITION_X_BPM_TEXT = 690;
    private static final int POSITION_Y_BPM_TEXT = 350;
    private static final int HEIGHT_BPM_TEXT = 20;
    private static final int WIDTH_BPM_TEXT = 50;
    private static final int POSITION_X_SCROLLPANE = 5;
    private static final int POSITION_Y_SCROLLPANE = 20;
    private static final int HEIGHT_SCROLLPANE = 400;
    private static final int WIDTH_SCROLLPANE = 600;
    private static final int POSITION_X_VOLUMEBAR_LABEL = 5;
    private static final int POSITION_Y_VOLUMEBAR_LABEL = 450;
    private static final int VOLUMEBAR_LABEL_WIDTH = 160;
    private static final int VOLUMEBAR_LABEL_HEIGHT = 20;
    private static final int VOLUMEBAR_SLIDER_MIN = 0;
    private static final int VOLUMEBAR_SLIDER_MAX = 100;
    private static final int VOLUMEBAR_SLIDER_MAJOR_SPACING = 10;
    private static final int VOLUMEBAR_SLIDER_MINOR_SPACING = 5;
    private static final int POSITION_X_VOLUMEBAR = 5;
    private static final int POSITION_Y_VOLUMEBAR = 480;
    private static final int VOLUMEBAR_WIDTH = 600;
    private static final int VOLUMEBAR_HEIGHT = 50;

    private static String musica;
    public JFrame Player;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JTextArea musicatexto;
    private JTextField bpmText;
    private static JSlider VolumeBar;


    public Interface() {

        Player = new JFrame("Tocador_de_Musica");

        CreateButtons();

        CreateTextArea();

        CreateBpmArea();

        CreateScrollBar();

        CreateVolumeArea();

        CreateMainInterface();

        CreateButtonsAction();

    }


    private void CreateButtons() {
        playButton = new JButton("Play");
        Player.add(playButton);
        playButton.setBounds(POSITION_X_BUTTON, POSITION_Y_BUTTON - OFFSET_BETWEEN_BUTTONS, WIDTH_BUTTON, HEIGHT_BUTTON);

        pauseButton = new JButton("Pause");
        Player.add(pauseButton);
        pauseButton.setBounds(POSITION_X_BUTTON, POSITION_Y_BUTTON, WIDTH_BUTTON, HEIGHT_BUTTON);

        stopButton = new JButton("Stop");
        Player.add(stopButton);
        stopButton.setBounds(POSITION_X_BUTTON, POSITION_Y_BUTTON + OFFSET_BETWEEN_BUTTONS, WIDTH_BUTTON, HEIGHT_BUTTON);
    }

    private void CreateTextArea() {
        JLabel label = new JLabel("Escreva a seguir seu texto:");
        Player.add(label);
        label.setBounds(POSITION_X_HEADTEXT, POSITION_Y_HEADTEXT, WIDTH_HEADTEXT, HEIGHT_HEADTEXT);

        musicatexto = new JTextArea();
        musicatexto.setLineWrap(true);
        musicatexto.setWrapStyleWord(true);
        musicatexto.setFont(new Font("arial", Font.PLAIN, FONT_SIZE));
        Player.add(musicatexto);
        musicatexto.setBounds(POSITION_X_TEXTAREA, POSITION_Y_TEXTAREA, TEXT_MUSIC_SPACE_WIDTH, TEXT_MUSIC_SPACE_HEIGHT);
    }
    private void CreateBpmArea(){
        bpmText = new JTextField();
        bpmText.setBounds(POSITION_X_BPM_TEXT, POSITION_Y_BPM_TEXT, WIDTH_BPM_TEXT, HEIGHT_BPM_TEXT);
        bpmText.setText("100");
        Player.add(bpmText);

        JLabel bpmLabel = new JLabel("BPM:");
        bpmLabel.setBounds(POSITION_X_BPM_LABEL, POSITION_Y_BPM_LABEL, WIDTH_BPM_LABEL, HEIGHT_BPM_LABEL);
        Player.add(bpmLabel);
    }
    private void CreateScrollBar(){
        JScrollPane scrollPane = new JScrollPane(musicatexto);
        scrollPane.setBounds(POSITION_X_SCROLLPANE, POSITION_Y_SCROLLPANE, WIDTH_SCROLLPANE, HEIGHT_SCROLLPANE);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Player.add(scrollPane);
    }
    private void CreateVolumeArea(){
        JLabel textoVolume = new JLabel("Volume inicial:");
        Player.add(textoVolume);
        textoVolume.setBounds(POSITION_X_VOLUMEBAR_LABEL, POSITION_Y_VOLUMEBAR_LABEL, VOLUMEBAR_LABEL_WIDTH, VOLUMEBAR_LABEL_HEIGHT);
        // Adiciona a barra de volume
        VolumeBar = new JSlider(JSlider.HORIZONTAL, VOLUMEBAR_SLIDER_MIN, VOLUMEBAR_SLIDER_MAX, Constant.INITIAL_VOLUME);
        VolumeBar.setMajorTickSpacing(VOLUMEBAR_SLIDER_MAJOR_SPACING);
        VolumeBar.setMinorTickSpacing(VOLUMEBAR_SLIDER_MINOR_SPACING);
        VolumeBar.setPaintTicks(true);
        VolumeBar.setPaintLabels(true);
        Player.add(VolumeBar);
        VolumeBar.setEnabled(false);
        VolumeBar.setBounds(POSITION_X_VOLUMEBAR, POSITION_Y_VOLUMEBAR, VOLUMEBAR_WIDTH, VOLUMEBAR_HEIGHT);
    }
    private void CreateMainInterface(){
        Player.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Player.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        Player.setLayout(null);
        Player.setVisible(true);
        Player.setLocationRelativeTo(null);
        Player.setResizable(false);
    }

    private void CreateButtonsAction(){
        playButton.addActionListener(e -> {
            musica = musicatexto.getText();
            int bpm = PlayerMusic.GetBpmFromText(bpmText);
            if (musica.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite uma musica!");
            }else {
                try {
                    PlayerMusic.GenerateSequence(musica, bpm);
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
    public static void UpdateVolumeBar(int volume){
        VolumeBar.setValue(volume);
    }

}
