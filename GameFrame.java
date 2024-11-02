import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    private final GameArea gameArea;
    private final JLabel scoreLabel;
    private final JLabel timeLabel;
    private final JPanel nextBlockPanel1; 
    private final JPanel nextBlockPanel2; 
    private final int dalaytime , NumberOfLevel; 
    private ImageIcon rawBackgroundImage;

    public GameFrame(int dalaytime ,int NumberOfLevel) {
        this.dalaytime = dalaytime;
        this.NumberOfLevel = NumberOfLevel;
        switch(this.NumberOfLevel){
            case 0 : rawBackgroundImage = new ImageIcon(getClass().getResource("BG2.jpg")); break;
            case 1 : rawBackgroundImage = new ImageIcon(getClass().getResource("BG3.jpg")); break;
            case 2 : rawBackgroundImage = new ImageIcon(getClass().getResource("BG4.jpg")); break;
            case 3 : rawBackgroundImage = new ImageIcon(getClass().getResource("BG5.jpg")); break;
            case 4 : rawBackgroundImage = new ImageIcon(getClass().getResource("BG6.jpg")); break;
            case 5 : rawBackgroundImage = new ImageIcon(getClass().getResource("BG7.jpg")); break;
        }
        Image scaledBackgroundImage = rawBackgroundImage.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
        ImageIcon backgroundImage = new ImageIcon(scaledBackgroundImage);
        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0, 0, 1280, 720);

        gameArea = new GameArea(NumberOfLevel); 
        gameArea.setBorder(BorderFactory.createLineBorder(Color.black,3)); 
        gameArea.setBounds(435, 60, 400, 580);

        JLabel nextBlockLabel1 = new JLabel("NEXT");
        nextBlockLabel1.setBounds(295, 50, 100, 50);
        nextBlockLabel1.setForeground(Color.WHITE);
        nextBlockLabel1.setFont(new Font("Tahoma", Font.BOLD, 35));

        nextBlockPanel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawNextBlock1(g);
            }
        };
        nextBlockPanel1.setBounds(265, 100, 150, 200);
        nextBlockPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        nextBlockPanel1.setBackground(Color.WHITE);
        //====================================================================================================
        JLabel nextBlockLabel2 = new JLabel("NEXT");
        nextBlockLabel2.setBounds(890, 50, 100, 50);
        nextBlockLabel2.setForeground(Color.WHITE);
        nextBlockLabel2.setFont(new Font("Tahoma", Font.BOLD, 35));

        nextBlockPanel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawNextBlock2(g);
            }
        };
        nextBlockPanel2.setBounds(860, 100, 150, 200);
        nextBlockPanel2.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        nextBlockPanel2.setBackground(Color.WHITE);

        
        scoreLabel = new JLabel("Score: ");
        scoreLabel.setBounds(860, 550, 200, 50);
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 30));

        timeLabel = new JLabel("Time: ");
        timeLabel.setBounds(860, 500, 200, 50);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        
        Image scaledwasdImage = new ImageIcon(getClass().getResource("wasd.png")).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel wasdControls = new JLabel(new ImageIcon(scaledwasdImage));
        wasdControls.setBounds(50, 300, 200, 200);

        Image scaledarrowsImage = new ImageIcon(getClass().getResource("arrows.png")).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        JLabel arrowControls = new JLabel(new ImageIcon(scaledarrowsImage)); 
        arrowControls.setBounds(1000, 280, 200, 200); 

        
        setLayout(null);
        add(gameArea);
        add(nextBlockLabel1);
        add(nextBlockPanel1);

        add(nextBlockLabel2);
        add(nextBlockPanel2);
        
        add(scoreLabel);
        add(timeLabel);
        add(wasdControls);
        add(arrowControls);
        add(background);

        initControls();
        startGame();
    }
    public void startGame(){
        GameThread gameThread = new GameThread(gameArea, this , dalaytime);
        gameThread.start();    
        gameThread.startTimer();  
    }
    public void updateScore(int score){scoreLabel.setText("Score : " + score);}
    public void updateTime(int time){timeLabel.setText("Time : " + time);}
    public void repaintNextBlockPanel() {
        nextBlockPanel1.repaint();
        nextBlockPanel2.repaint();
    }

    private void drawNextBlock1(Graphics g) {
        TetrisBlock nextBlock = gameArea.getNextBlock1();
        if (nextBlock != null) {
            int[][] shape = nextBlock.getShape();
            int blockSize = 35; 
            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    if (shape[row][col] == 1) {
                        g.setColor(Color.BLUE);
                        g.fillRect(col * blockSize + 25, row * blockSize + 25, blockSize, blockSize);
                        g.setColor(Color.BLACK);
                        g.drawRect(col * blockSize + 25, row * blockSize + 25, blockSize, blockSize);
                    }
                }
            }
        }
    }
    private void drawNextBlock2(Graphics g) {
        TetrisBlock nextBlock = gameArea.getNextBlock2();
        if (nextBlock != null) {
            int[][] shape = nextBlock.getShape();
            int blockSize = 35; 
            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    if (shape[row][col] == 1) {
                        g.setColor(Color.ORANGE);
                        g.fillRect(col * blockSize + 25, row * blockSize + 25, blockSize, blockSize);
                        g.setColor(Color.BLACK);
                        g.drawRect(col * blockSize + 25, row * blockSize + 25, blockSize, blockSize);
                    }
                }
            }
        }
    }
    private void initControls() {
        InputMap im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke('d'), "right2");
        im.put(KeyStroke.getKeyStroke('a'), "left2");
        im.put(KeyStroke.getKeyStroke('w'), "rotate2");
        im.put(KeyStroke.getKeyStroke('s'), "drop2");

        am.put("right2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveBlockRight1(); 
            }
        });
        am.put("left2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveBlockLeft1();
            }
        });
        am.put("rotate2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.rotateBlock1(); 
            }
        });
        am.put("drop2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.dropBlock1(); 
                SoundEffect drop = new SoundEffect("/item/drop.wav");
                drop.setVolume(0.7f);
                drop.play();
            }
        });
        
        im.put(KeyStroke.getKeyStroke("RIGHT"), "right1");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left1");
        im.put(KeyStroke.getKeyStroke("UP"), "rotate1");
        im.put(KeyStroke.getKeyStroke("DOWN"), "drop1");

        am.put("right1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveBlockRight2();
            }
        });
        am.put("left1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveBlockLeft2(); 
            }
        });
        am.put("rotate1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.rotateBlock2(); 
            }
        });
        am.put("drop1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.dropBlock2();
                SoundEffect drop = new SoundEffect("/item/drop.wav");
                drop.setVolume(0.7f);
                drop.play();
            }
        });
    }
    public void showGameOverPopup(int finalScore, int time) {
        SoundEffect clickSound = new SoundEffect("/item/Gameover.wav");
        clickSound.play(); 

        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 20)); 
        panel.setBackground(new Color(50, 50, 50)); 
    
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER); 
        gameOverLabel.setFont(new Font("Tahoma", Font.BOLD, 60)); 
        gameOverLabel.setForeground(Color.WHITE);
        panel.add(gameOverLabel);
    
        JLabel scoreLabel = new JLabel("Score: " + finalScore, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 40)); 
        scoreLabel.setForeground(Color.YELLOW); 
        panel.add(scoreLabel);

        int seconds = time; 
        int minutes = seconds / 60;  
        int remainingSeconds = seconds % 60;

        JLabel timeLabel = new JLabel("Time: " + minutes + " minutes " + remainingSeconds + " seconds", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 40)); 
        timeLabel.setForeground(Color.YELLOW);
        panel.add(timeLabel);
    
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Tahoma", Font.PLAIN, 30)); 
        playAgainButton.setBackground(Color.BLACK); 
        playAgainButton.setForeground(Color.WHITE); 
        playAgainButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2)); 
        panel.add(playAgainButton); 

        JButton Mainmanu = new JButton("Main manu");
        Mainmanu.setFont(new Font("Tahoma", Font.PLAIN, 30)); 
        Mainmanu.setBackground(Color.BLACK); 
        Mainmanu.setForeground(Color.WHITE); 
        Mainmanu.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2)); 
        panel.add(Mainmanu);

        Mainmanu.addActionListener(e -> {
            SoundEffect buttonClickSound = new SoundEffect("/item/Click.wav");
            buttonClickSound.play();
            this.dispose(); 
            MyFrame MainFrame = new MyFrame();
            MainFrame.setTitle("Tetris 2.0");
            MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            MainFrame.setResizable(false);
            MainFrame.setSize(1280, 720);   
            MainFrame.setLocationRelativeTo(null);  
            MainFrame.setLayout(null);
            MainFrame.setVisible(true);
        });

        playAgainButton.addActionListener(e -> {
            SoundEffect buttonClickSound = new SoundEffect("/item/Click.wav");
            buttonClickSound.play();
            this.restartGame(); 
        });
        
        JOptionPane.showMessageDialog(this, panel, "Game Over", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void restartGame() {
        this.dispose(); 

        GameFrame newGameFrame = new GameFrame(dalaytime,NumberOfLevel);
        newGameFrame.setTitle("Tetris 2.0 - Game");
        newGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGameFrame.setResizable(false);
        newGameFrame.setSize(1280, 720);
        newGameFrame.setLocationRelativeTo(null);  
        newGameFrame.setLayout(null);
        newGameFrame.setVisible(true);
    }
}
