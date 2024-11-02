import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyFrame extends JFrame {
    private JButton startButton, howToPlayButton, itemsGuideButton, exitButton;
    private JComboBox<String> difficultyComboBox, levelComboBox;
    private int dalaytime; 
    private int NumberOfLevel;

    MyFrame() {
        startButton = createTextButton("START", 0, 250, 400, 100);
        startButton.setFont(new Font("Tahoma", Font.BOLD, 100));
        howToPlayButton = createTextButton("How to Play", 0, 350, 450, 80); 
        itemsGuideButton = createTextButton("Items Guide", 0, 425, 450, 80); 
        exitButton = createTextButton("Exit Game", 0, 550, 300, 80);
        exitButton.setFont(new Font("Tahoma", Font.PLAIN, 40));
        exitButton.setForeground(new Color(192, 192, 192));

        String[] difficultyLevels = { "Easy", "Normal", "Hard" };
        difficultyComboBox = new JComboBox<>(difficultyLevels);
        difficultyComboBox.setBounds(1050, 50, 150, 50);
        difficultyComboBox.setFont(new Font("Tahoma", Font.BOLD, 30));
        difficultyComboBox.setOpaque(true);
        difficultyComboBox.setFocusable(false);
        difficultyComboBox.setBorder(null);

        String[] gameLevels = { "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6" };
        levelComboBox = new JComboBox<>(gameLevels);
        levelComboBox.setBounds(1050, 120, 150, 50);
        levelComboBox.setFont(new Font("Tahoma", Font.BOLD, 30));
        levelComboBox.setOpaque(true);
        levelComboBox.setFocusable(false);
        levelComboBox.setBorder(null);

        Listener listener = new Listener(this);
        startButton.addActionListener(listener);
        howToPlayButton.addActionListener(listener);
        itemsGuideButton.addActionListener(listener);
        exitButton.addActionListener(listener);
        difficultyComboBox.addActionListener(listener); 
        levelComboBox.addActionListener(listener);

        ImageIcon rawLogoImage = new ImageIcon(getClass().getResource("logoW.png"));  
        Image scaledLogoImage = rawLogoImage.getImage().getScaledInstance(600, 200, Image.SCALE_SMOOTH);  
        ImageIcon logoImage = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(logoImage);
        logoLabel.setBounds(20, -20, 600, 200); 

        ImageIcon rawBackgroundImage = new ImageIcon(getClass().getResource("BG.jpg")); 
        Image scaledBackgroundImage = rawBackgroundImage.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
        ImageIcon backgroundImage = new ImageIcon(scaledBackgroundImage);
        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0, 0, 1280, 720);

        add(logoLabel);
        add(startButton);
        add(howToPlayButton);
        add(itemsGuideButton);
        add(exitButton);
        add(difficultyComboBox);
        add(levelComboBox);
        add(background);
    }

    private JButton createTextButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Tahoma", Font.BOLD, 65));
        button.setForeground(Color.WHITE); 
        button.setBackground(new Color(0, 0, 0, 0)); 
        button.setBorder(null);
        button.setFocusable(false); 
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        return button;
    }

    private static class Listener implements ActionListener {
        private final MyFrame frame;
        Listener(MyFrame frame) { this.frame = frame; }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == frame.startButton) {
                String selectedDifficulty = (String) frame.difficultyComboBox.getSelectedItem();
                switch (selectedDifficulty) {
                    case "Easy": frame.dalaytime = 1000;  break;
                    case "Normal": frame.dalaytime = 500; break;
                    case "Hard":frame.dalaytime = 250;    break;
                }

                String selectedLevel = (String) frame.levelComboBox.getSelectedItem();
                switch (selectedLevel) {
                    case "Level 1": frame.NumberOfLevel = 0; break;
                    case "Level 2": frame.NumberOfLevel = 1; break;
                    case "Level 3": frame.NumberOfLevel = 2; break;
                    case "Level 4": frame.NumberOfLevel = 3; break;
                    case "Level 5": frame.NumberOfLevel = 4; break;
                    case "Level 6": frame.NumberOfLevel = 5; break;
                }

                frame.dispose(); 
                SoundEffect buttonClickSound = new SoundEffect("/item/Click.wav");
                buttonClickSound.play();

                GameFrame gameFrame = new GameFrame(frame.dalaytime,frame.NumberOfLevel);
                gameFrame.setTitle("Tetris 2.0 - Game");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setResizable(false);
                gameFrame.setSize(1280, 720);
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setLayout(null);
                gameFrame.setVisible(true);
            } else if (e.getSource() == frame.howToPlayButton) {
                SoundEffect buttonClickSound = new SoundEffect("/item/Click.wav");
                buttonClickSound.play();
                new HowToPlayDialog(frame);
            } else if (e.getSource() == frame.itemsGuideButton) {
                SoundEffect buttonClickSound = new SoundEffect("/item/Click.wav");
                buttonClickSound.play();
                new ItemsGuideDialog(frame);
            } else if (e.getSource() == frame.exitButton) {
                SoundEffect buttonClickSound = new SoundEffect("/item/Click.wav");
                buttonClickSound.play();
                System.exit(0); 
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new MyFrame();
        frame.setTitle("Tetris 2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1280, 720);   
        frame.setLocationRelativeTo(null);  
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
