import javax.swing.*;
import java.awt.*;

public class Item {
    private int x, y; 
    private String type;
    private Image image;

    public Item(int x, int y, String type) {
        if (x >= 0 && x < GameArea.BOARD_WIDTH) { this.x = x;} 
        else { this.x = 0; }

        if (y >= 0 && y < GameArea.BOARD_HEIGHT) { this.y = y;
        } else { this.y = 0;}

        this.type = type;
        loadImage(); 
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void moveDown() {this.y++;}
    
    public String getType() {return type;}
    public Image getImage() {return image;}

    private void loadImage() {
        if (type.equals("bombBlock")) {
            image = new ImageIcon(getClass().getResource("/item/atomic-bomb.png")).getImage();
        } else if (type.equals("bonus")) {
            image = new ImageIcon(getClass().getResource("/item/bonus.png")).getImage();
        } else if (type.equals("scoreD")) {
            image = new ImageIcon(getClass().getResource("/item/ScoreD.png")).getImage();
        } else if (type.equals("speedUp")) {
            image = new ImageIcon(getClass().getResource("/item/speedup.png")).getImage();
        } else if (type.equals("whiteblock")) {
            image = new ImageIcon(getClass().getResource("/item/white.png")).getImage();
        } else if (type.equals("color")) {
            image = new ImageIcon(getClass().getResource("/item/Color.png")).getImage();
        }
    }
    public void activateEffect(GameThread gameThread) {
        switch (type) {
            case "bombBlock":
                gameThread.BombLine(); 
                break;
            case "bonus":
                gameThread.BonusScore(); 
                break;
            case "scoreD":
                gameThread.Scoredecrease(); 
                break;
            case "speedUp":
                gameThread.SpeedUp(); 
                break;
            case "whiteblock":
                gameThread.WhiteBlock();
                break;
            case "color":
                gameThread.Color(); 
                break;
        }
    }
}
