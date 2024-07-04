import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class Interface {
    //WINDOW
    private static final int WIDTH_WINDOW = 800, HEIGHT_WINDOW = 600;
    //BUTTON
    private static final int WIDTH_BUTTON = 70, HEIGHT_BUTTON = 70;
    private static final int POSITION_X_BUTTON = 680, POSITION_Y_BUTTON = 130;
    private static final int OFFSET_BETWEEN_BUTTONS = 100;
    //TEXT AREA
    private static final int WIDTH_TEXT_AREA = 600, HEIGHT_TEXT_AREA = 400;
    private static final int POSITION_X_TEXT_AREA = 5, POSITION_Y_TEXT_AREA = 20;
    private static final int WIDTH_TEXT_AREA_LABEL = 160, HEIGHT_TEXT_AREA_LABEL = 20;
    private static final int POSITION_X_TEXT_AREA_LABEL = 5, POSITION_Y_TEXT_AREA_LABEL = 0;
    private static final int FONT_SIZE = 20;
    //BPM
    private static final int POSITION_X_BPM_LABEL = 650, POSITION_Y_BPM_LABEL = 350;
    private static final int WIDTH_BPM_LABEL = 30, HEIGHT_BPM_LABEL = 20;
    private static final int POSITION_X_BPM_TEXT = 690, POSITION_Y_BPM_TEXT = 350;
    private static final int WIDTH_BPM_TEXT = 50, HEIGHT_BPM_TEXT = 20;
    //SCROLL
    private static final int POSITION_X_SCROLLPANE = 5, POSITION_Y_SCROLLPANE = 20;
    private static final int WIDTH_SCROLLPANE = 600, HEIGHT_SCROLLPANE = 400;
    //VOLUME
    private static final int POSITION_X_VOLUMEBAR_LABEL = 5, POSITION_Y_VOLUMEBAR_LABEL = 450;
    private static final int VOLUMEBAR_LABEL_WIDTH = 160, VOLUMEBAR_LABEL_HEIGHT = 20;
    private static final int VOLUMEBAR_SLIDER_MIN = 0, VOLUMEBAR_SLIDER_MAX = 100;
    private static final int VOLUMEBAR_SLIDER_MAJOR_SPACING = 10, VOLUMEBAR_SLIDER_MINOR_SPACING = 5;
    private static final int POSITION_X_VOLUMEBAR = 5, POSITION_Y_VOLUMEBAR = 480;
    private static final int VOLUMEBAR_WIDTH = 600, VOLUMEBAR_HEIGHT = 50;
    //MENSAGEM
    private static final String EMPTY_MUSIC_WARNING = "Digite uma música!";
    private static final String INVALID_BPM_WARNING = "Digite um valor inteiro válido de BPM!";

    private static JFrame playerFrame;

    private static JButton playButton;
    private static JButton pauseButton;
    private static JButton stopButton;
    private static JTextArea inputTextArea;
    private static JTextField bpmText;
    private static JSlider volumeBar;

    public Interface() {

        playerFrame = new JFrame("Tocador_de_Musica");

        CreateButtons();
        CreateTextArea();
        CreateBpmArea();
        CreateScrollBar();
        CreateVolumeArea();
        CreateMainInterface();

        playerFrame.setVisible(true);

        CreateButtonsAction();
    }

    private void CreateButtons() {
        playButton = new JButton("Play");
        playerFrame.add(playButton);
        playButton.setBounds(POSITION_X_BUTTON, POSITION_Y_BUTTON - OFFSET_BETWEEN_BUTTONS, WIDTH_BUTTON, HEIGHT_BUTTON);

        pauseButton = new JButton("Pause");
        playerFrame.add(pauseButton);
        pauseButton.setBounds(POSITION_X_BUTTON, POSITION_Y_BUTTON, WIDTH_BUTTON, HEIGHT_BUTTON);

        stopButton = new JButton("Stop");
        playerFrame.add(stopButton);
        stopButton.setBounds(POSITION_X_BUTTON, POSITION_Y_BUTTON + OFFSET_BETWEEN_BUTTONS, WIDTH_BUTTON, HEIGHT_BUTTON);
    }

    private void CreateTextArea() {
        JLabel textAreaLabel = new JLabel("Escreva a seguir seu texto:");
        textAreaLabel.setBounds(POSITION_X_TEXT_AREA_LABEL, POSITION_Y_TEXT_AREA_LABEL, WIDTH_TEXT_AREA_LABEL, HEIGHT_TEXT_AREA_LABEL);
        playerFrame.add(textAreaLabel);

        inputTextArea = new JTextArea();
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        inputTextArea.setFont(new Font("arial", Font.PLAIN, FONT_SIZE));
        inputTextArea.setBounds(POSITION_X_TEXT_AREA, POSITION_Y_TEXT_AREA, WIDTH_TEXT_AREA, HEIGHT_TEXT_AREA);
        playerFrame.add(inputTextArea);
    }

    private void CreateBpmArea(){
        JLabel bpmLabel = new JLabel("BPM:");
        bpmLabel.setBounds(POSITION_X_BPM_LABEL, POSITION_Y_BPM_LABEL, WIDTH_BPM_LABEL, HEIGHT_BPM_LABEL);
        playerFrame.add(bpmLabel);

        bpmText = new JTextField();
        bpmText.setBounds(POSITION_X_BPM_TEXT, POSITION_Y_BPM_TEXT, WIDTH_BPM_TEXT, HEIGHT_BPM_TEXT);
        bpmText.setText("100");
        playerFrame.add(bpmText);
    }

    private void CreateScrollBar(){
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        scrollPane.setBounds(POSITION_X_SCROLLPANE, POSITION_Y_SCROLLPANE, WIDTH_SCROLLPANE, HEIGHT_SCROLLPANE);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        playerFrame.add(scrollPane);
    }

    private void CreateVolumeArea(){
        JLabel volumeText = new JLabel("Indicador de Volume:");
        volumeText.setBounds(POSITION_X_VOLUMEBAR_LABEL, POSITION_Y_VOLUMEBAR_LABEL, VOLUMEBAR_LABEL_WIDTH, VOLUMEBAR_LABEL_HEIGHT);
        playerFrame.add(volumeText);
        // Adiciona a barra de volume
        volumeBar = new JSlider(JSlider.HORIZONTAL, VOLUMEBAR_SLIDER_MIN, VOLUMEBAR_SLIDER_MAX, Constant.INITIAL_VOLUME);
        volumeBar.setMajorTickSpacing(VOLUMEBAR_SLIDER_MAJOR_SPACING);
        volumeBar.setMinorTickSpacing(VOLUMEBAR_SLIDER_MINOR_SPACING);
        volumeBar.setBounds(POSITION_X_VOLUMEBAR, POSITION_Y_VOLUMEBAR, VOLUMEBAR_WIDTH, VOLUMEBAR_HEIGHT);
        volumeBar.setPaintTicks(true);
        volumeBar.setPaintLabels(true);
        volumeBar.setEnabled(false);
        playerFrame.add(volumeBar);
    }

    private void CreateMainInterface(){
        playerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerFrame.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        playerFrame.setLayout(null);
        playerFrame.setVisible(true);
        playerFrame.setLocationRelativeTo(null);
        playerFrame.setResizable(false);
    }

    private void CreateButtonsAction(){
        playButton.addActionListener(_ -> PlayAction());

        pauseButton.addActionListener(_ -> PauseAction());

        stopButton.addActionListener(_ -> StopAction());
    }

    private void PlayAction(){
        String inputText = inputTextArea.getText();
        int inputBpm = PlayerMusic.GetBpmFromText(bpmText);

        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(playerFrame, EMPTY_MUSIC_WARNING);
        }else {
            try {
                PlayerMusic.HandleMusicParameters(inputText, inputBpm);
                PlayerMusic.PlayMusic();
            } catch (InvalidMidiDataException | MidiUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void PauseAction(){
        try {
            PlayerMusic.PauseMusic();
        } catch (MidiUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void StopAction(){
        try{
            PlayerMusic.StopMusic();
        }catch (MidiUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void UpdateVolumeBar(int volume){
        volumeBar.setValue(volume);
    }

    public static void ShowBpmWarning(){
        JOptionPane.showMessageDialog(playerFrame, INVALID_BPM_WARNING);
    }
}
