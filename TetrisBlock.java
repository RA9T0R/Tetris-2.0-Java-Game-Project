import java.awt.*;
import java.util.Random;

public class TetrisBlock{
    private int[][] shape;
    private Color color;
    private int x , y;
    private int[][][] shapes;
    private int currentRotation;

    TetrisBlock(int [][] shape ){
        this.shape = shape;
        initShapes();
    }
    private void initShapes(){
        shapes = new int[4][][];
        for(int i = 0 ; i < 4 ; i++){
            int r = shape[0].length;
            int c = shape.length;
            shapes[i] = new int[r][c];

            for(int y = 0 ; y < r ; y++){
                for(int x = 0 ; x < c ; x++){
                    shapes[i][y][x] = shape[c-x-1][y];
                }
            }
            shape = shapes[i];
        }
    }
    public void spawn1(int gridWidth){
        Random r = new Random();
        currentRotation = r.nextInt(shapes.length);
        shape = shapes[currentRotation];

        y = -getHeight();
        x = r.nextInt((gridWidth / 2) - getWidth()) + 1;
    }
    public void spawn2(int gridWidth){
        Random r = new Random();
        currentRotation = r.nextInt(shapes.length);
        shape = shapes[currentRotation];

        y = -getHeight();
        x = (gridWidth / 2) + r.nextInt((gridWidth / 2) - getWidth()) + 1;
    }
    public int[][] getShape(){return shape;}
    public Color getColor(){return color;}
    public int getHeight(){return shape.length;}
    public int getWidth(){return shape[0].length;}
    public int getX(){return x;}
    public int getY(){return y;}
    public int getBottomEdge(){return y + getHeight();}
    public int getRightEdge(){return x + getWidth();}
    public int getLeftEdge(){return x;}

    public void moveUp(){y--;}
    public void moveDown(){y++;}
    public void moveLeft(){x--;}
    public void moveRight(){x++;}
    public void rotate(){
        currentRotation++;
        if(currentRotation > 3) currentRotation = 0;
        shape = shapes[currentRotation];
    }

    public void setColor(Color color){this.color = color;}
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}

    public int[][] getBlockPositions() {
        int cellCount = 0;
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    cellCount++;
                }
            }
        }
    
        int[][] positions = new int[cellCount][2];
        int index = 0;
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    positions[index][0] = x + col; 
                    positions[index][1] = y + row; 
                    index++;
                }
            }
        }
    
        return positions; 
    }
    
}