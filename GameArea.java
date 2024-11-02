import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class GameArea extends JPanel { 
    public static final int BOARD_WIDTH = 11;
    public static final int BOARD_HEIGHT = 16;
    private static final int CELL_SIZE = 36;
    private final Color[][] background;
    private final Color[] RColor = {Color.BLACK,Color.GRAY,Color.GREEN,Color.MAGENTA,Color.RED,Color.CYAN,Color.PINK,Color.YELLOW};
    private final Object[][] grid;

    private TetrisBlock player1Block;
    private TetrisBlock player2Block;
    private TetrisBlock nextPlayer1Block;
    private TetrisBlock nextPlayer2Block;

    private final TetrisBlock[] blocks;
    private final int NumberOfLevel;

    GameArea(int NumberOfLevel) {
        this.NumberOfLevel = NumberOfLevel;
        background = new Color[BOARD_HEIGHT][BOARD_WIDTH];
        grid = new Object[BOARD_HEIGHT][BOARD_WIDTH];
        blocks = new TetrisBlock[]{new ShapeI(),
                                   new ShapeJ(),
                                   new ShapeL(),
                                   new ShapeO(),
                                   new ShapeS(),
                                   new ShapeZ(),
                                   new ShapeT()};
        StarterBlock(NumberOfLevel);
    }
    private void StarterBlock(int NumberOfLevel) {
        Random R = new Random();
        for(int i = 0 ; i < NumberOfLevel ; i++){
            int Rc = R.nextInt(BOARD_WIDTH);
            int Rr = (BOARD_HEIGHT / 2) + R.nextInt((BOARD_HEIGHT/2));
            background[Rr][Rc] = RColor[0];
        }
    }

    public void spawnBlocks() {
        Random r1 = new Random();
        int R1 = r1.nextInt(blocks.length);
        int R1n = r1.nextInt(blocks.length);
        Random r2 = new Random();
        int R2 = r2.nextInt(blocks.length);
        int R2n = r2.nextInt(blocks.length);

        if (nextPlayer1Block == null) {
            player1Block = blocks[R1];
            player1Block.setColor(Color.blue);
            player1Block.spawn1(BOARD_WIDTH);
            nextPlayer1Block = blocks[R1n];
        } else {
            player1Block = nextPlayer1Block;
            player1Block.setColor(Color.blue);
            player1Block.spawn1(BOARD_WIDTH);
            nextPlayer1Block = blocks[R1n];
        }
        while(R2 == R1 || R2n == R1n){
            R2 = r2.nextInt(blocks.length);
            R2n = r2.nextInt(blocks.length);
        }
        if (nextPlayer2Block == null) {
            player2Block = blocks[R2];
            player2Block.setColor(Color.ORANGE);
            player2Block.spawn2(BOARD_WIDTH);
            nextPlayer2Block = blocks[R2n];
        } else {
            player2Block = nextPlayer2Block;
            player2Block.setColor(Color.ORANGE);
            player2Block.spawn2(BOARD_WIDTH);
            nextPlayer2Block = blocks[R2n];
        }
    }
    
    public TetrisBlock getNextBlock1() {return nextPlayer1Block;}
    public TetrisBlock getNextBlock2() {return nextPlayer2Block;}

    public int[][] getCurrentBlockPosition1() {return player1Block.getBlockPositions(); }
    public int[][] getCurrentBlockPosition2() {return player2Block.getBlockPositions(); }
    
    //Item==============================================================
    public void addItemToGrid(Item item) {
        int x = item.getX();
        int y = item.getY();
        
        if (x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT) {
            grid[y][x] = item;  
            repaint();
        }
    }
    public void moveItemsDown() {
        for (int y = BOARD_HEIGHT - 1; y >= 0; y--) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (grid[y][x] instanceof Item) {
                    Item item = (Item) grid[y][x];
                    if (canMoveItemDown(item)) {
                        grid[item.getY()][item.getX()] = null;  
                        item.moveDown(); 
                        grid[item.getY()][item.getX()] = item;
                    }else removeItemFromGrid(item);
                }
            }
        }
        repaint();
    }
    private boolean canMoveItemDown(Item item) {
        int newY = item.getY() + 1;
        return !(newY >= BOARD_HEIGHT || grid[newY][item.getX()] != null);
    }
    public void removeItemFromGrid(Item item) {
        grid[item.getY()][item.getX()] = null; 
        repaint(); 
    }
    public void BombLine() {
        int lastLineIndex = BOARD_HEIGHT - 1;
        clearLine(lastLineIndex); 
        for (int r = lastLineIndex; r > 0; r--) {
            shiftDown(r);
        }
        clearLine(0); 
        repaint();
    }
    public void randomColor(){
        Random r1 = new Random();
        int R1 = r1.nextInt(RColor.length);
        int R2 = r1.nextInt(RColor.length);
        player1Block.setColor(RColor[R1]);
        player2Block.setColor(RColor[R2]);
    }
    public void WhiteBlock(){
        player1Block.setColor(Color.WHITE);
        player2Block.setColor(Color.WHITE);
    }

    //==================================================================
    public boolean isBlockOutOfBounds1(){
        return player1Block != null && player1Block.getY() < 0;
    }
    public boolean isBlockOutOfBounds2(){
        return player2Block != null && player2Block.getY() < 0;
    }
    public boolean moveBlockDown1(){
        if(checkBottom1() == false){return false;} 

        player1Block.moveDown();
        repaint();
        return true;
    }
    public boolean moveBlockDown2(){
        if(checkBottom2() == false){return false;} 
        
        player2Block.moveDown();
        repaint();
        return true;
    }
    public void moveBlockRight1(){
        if(player1Block == null) return;
        if(!checkRight1()) return;
        if(!checkBottom1()) return;
        player1Block.moveRight();
        if(checkBlockCollision(player1Block, player2Block)) {
            player1Block.moveLeft();
            return;
        }
        repaint();
    }
    public void moveBlockRight2(){
        if(player2Block == null) return;
        if(!checkRight2()) return;
        if(!checkBottom2()) return;
        player2Block.moveRight();
        if(checkBlockCollision(player1Block, player2Block)) {
            player2Block.moveLeft();
            return;
        }
        repaint();
    }
    public void moveBlockLeft1(){
        if(player1Block == null) return;
        if(!checkLeft1()) return;
        if(!checkBottom1()) return;
        player1Block.moveLeft();
        if(checkBlockCollision(player1Block, player2Block)) {
            player1Block.moveRight();
            return;
        }
        repaint();
    }
    public void moveBlockLeft2(){
        if(player2Block == null) return;
        if(!checkLeft2()) return;
        if(!checkBottom2()) return;
        player2Block.moveLeft();
        if(checkBlockCollision(player1Block, player2Block)) {
            player2Block.moveRight();
            return;
        }
        repaint();
    }
    public void dropBlock1(){
        if(player1Block == null) return;
        while (checkBottom1()) { 
            player1Block.moveDown();
        }
        moveBlockToBackground1();
        repaint();
    }
    public void dropBlock2(){
        if(player2Block == null) return;
        while (checkBottom2()) { 
            player2Block.moveDown();
        }
        moveBlockToBackground2();
        repaint();
    }
    public void rotateBlock1(){
        if(player1Block == null) return;
        if(player1Block.getLeftEdge() < 0) player1Block.setX(0);
        if(player1Block.getRightEdge() >= BOARD_WIDTH) player1Block.setX(BOARD_WIDTH - player1Block.getWidth());
        if(player1Block.getBottomEdge() >= BOARD_HEIGHT) player1Block.setY(BOARD_HEIGHT - player1Block.getHeight());
        if(!checkLeft1()) return;
        if(!checkRight1()) return;
        if(!checkBottom1()) return;
        player1Block.rotate();
        repaint();
    }
    public void rotateBlock2(){
        if(player2Block == null) return;
        if(player2Block.getLeftEdge() < 0) player2Block.setX(0);
        if(player2Block.getRightEdge() >= BOARD_WIDTH) player2Block.setX(BOARD_WIDTH - player2Block.getWidth());
        if(player2Block.getBottomEdge() >= BOARD_HEIGHT) player2Block.setY(BOARD_HEIGHT - player2Block.getHeight());
        if(!checkLeft2()) return;
        if(!checkRight2()) return;
        if(!checkBottom2()) return;
        player2Block.rotate();
        repaint();
    }
    private boolean checkBottom1(){
        if(player1Block.getBottomEdge() == BOARD_HEIGHT){
            return false;
        }
        int[][]shape = player1Block.getShape();
        int w = player1Block.getWidth();
        int h = player1Block.getHeight();

        for(int col = 0 ; col < w ; col++){
            for(int row = h -1 ; row >= 0; row--){
                if(shape[row][col] != 0){
                    int x = col + player1Block.getX();
                    int y = row + player1Block.getY() + 1;
                    if(y >= BOARD_HEIGHT || x < 0 || x >= BOARD_WIDTH) return false;
                    if(y < 0) break;
                    if(background[y][x] != null)return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkBottom2(){
        if(player2Block.getBottomEdge() == BOARD_HEIGHT){
            return false;
        }
        int[][]shape = player2Block.getShape();
        int w = player2Block.getWidth();
        int h = player2Block.getHeight();

        for(int col = 0 ; col < w ; col++){
            for(int row = h -1 ; row >= 0; row--){
                if(shape[row][col] != 0){
                    int x = col + player2Block.getX();
                    int y = row + player2Block.getY() + 1;
                    if(y >= BOARD_HEIGHT || x < 0 || x >= BOARD_WIDTH) return false;
                    if(y < 0) break;
                    if(background[y][x] != null)return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkLeft1(){
        if(player1Block.getLeftEdge() == 0){
            return false;
        }

        int[][]shape = player1Block.getShape();
        int w = player1Block.getWidth();
        int h = player1Block.getHeight();

        for(int row = 0 ; row < h ; row++){
            for(int col = 0 ; col < w; col++){
                if(shape[row][col] != 0){
                    int x = col + player1Block.getX() - 1;
                    int y = row + player1Block.getY();
                    if(y < 0) break;
                    if(background[y][x] != null)return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkLeft2(){
        if(player2Block.getLeftEdge() == 0){
            return false;
        }

        int[][]shape = player2Block.getShape();
        int w = player2Block.getWidth();
        int h = player2Block.getHeight();

        for(int row = 0 ; row < h ; row++){
            for(int col = 0 ; col < w; col++){
                if(shape[row][col] != 0){
                    int x = col + player2Block.getX() - 1;
                    int y = row + player2Block.getY();
                    if(y < 0) break;
                    if(background[y][x] != null)return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkRight1(){
        if(player1Block.getRightEdge() == BOARD_WIDTH){
            return false;
        }
        int[][]shape = player1Block.getShape();
        int w = player1Block.getWidth();
        int h = player1Block.getHeight();

        for(int row = 0 ; row < h ; row++){
            for(int col = w-1 ; col >= 0; col--){
                if(shape[row][col] != 0){
                    int x = col + player1Block.getX() + 1;
                    int y = row + player1Block.getY();
                    if(y < 0) break;
                    if(background[y][x] != null)return false;
                    break;
                }
            }
        }
        return true;
    }
    private boolean checkRight2(){
        if(player2Block.getRightEdge() == BOARD_WIDTH){
            return false;
        }
        int[][]shape = player2Block.getShape();
        int w = player2Block.getWidth();
        int h = player2Block.getHeight();

        for(int row = 0 ; row < h ; row++){
            for(int col = w-1 ; col >= 0; col--){
                if(shape[row][col] != 0){
                    int x = col + player2Block.getX() + 1;
                    int y = row + player2Block.getY();
                    if(y < 0) break;
                    if(background[y][x] != null)return false;
                    break;
                }
            }
        }
        return true;
    }
    public boolean checkBlockCollision(TetrisBlock block1, TetrisBlock block2) {
        int[][] shape1 = block1.getShape();
        int[][] shape2 = block2.getShape();
        
        for (int row1 = 0; row1 < shape1.length; row1++) {
            for (int col1 = 0; col1 < shape1[row1].length; col1++) {
                if (shape1[row1][col1] != 0) {
                    int x1 = block1.getX() + col1;
                    int y1 = block1.getY() + row1;
                    
                    for (int row2 = 0; row2 < shape2.length; row2++) {
                        for (int col2 = 0; col2 < shape2[row2].length; col2++) {
                            if (shape2[row2][col2] != 0) {
                                int x2 = block2.getX() + col2;
                                int y2 = block2.getY() + row2;
                                
                                if (x1 == x2 && y1 == y2) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public int clearLines(){
        boolean lineFilled;
        int LinesCleard = 0;
        for(int r = BOARD_HEIGHT - 1 ; r>=0 ;r--){
            lineFilled = true;
            for(int c = 0 ; c < BOARD_WIDTH;c++){
                if(background[r][c] == null){
                    lineFilled = false;
                    break;
                }
            }
            if(lineFilled){
                LinesCleard++;
                clearLine(r);
                shiftDown(r);
                clearLine(0);
                r++;
            }
        }
        repaint();
        return LinesCleard;
    }
    private void clearLine(int r){
        for(int i = 0 ; i < BOARD_WIDTH ; i++){background[r][i] = null;}
    }
    private void shiftDown(int r){
        SoundEffect clear = new SoundEffect("/item/clear.wav");
        clear.play();
        for(int row = r ; row > 0 ; row--){
            for(int col = 0 ; col < BOARD_WIDTH;col++){
                background[row][col] = background[row-1][col];
            }
        }
    }
    public void moveBlockToBackground1() {
        int[][] shape = player1Block.getShape();
        int h = player1Block.getHeight();
        int w = player1Block.getWidth();

        int xPos = player1Block.getX();
        int yPos = player1Block.getY();

        Color color = player1Block.getColor();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if(shape[row][col] == 1) {
                    int x = col + xPos;
                    int y = row + yPos;
                    
                    if (x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT) {
                        background[y][x] = color; 
                    }
                }
            }
        }
    }
    public void moveBlockToBackground2() {
        int[][] shape = player2Block.getShape();
        int h = player2Block.getHeight();
        int w = player2Block.getWidth();

        int xPos = player2Block.getX();
        int yPos = player2Block.getY();

        Color color = player2Block.getColor();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if(shape[row][col] == 1) {
                    int x = col + xPos;
                    int y = row + yPos;
                    
                    if (x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT) {
                        background[y][x] = color; 
                    }
                }
            }
        }
    }
    private void drawBlock1(Graphics g){
        int h = player1Block.getHeight();
        int w = player1Block.getWidth();
        Color c = player1Block.getColor();
        int[][] shape = player1Block.getShape();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if(shape[row][col] == 1){
                    int x = (player1Block.getX() + col) * CELL_SIZE;
                    int y = (player1Block.getY() + row) * CELL_SIZE;

                    drawGridSquare(g,c,x,y);
                }
            }
        }
    }
    private void drawBlock2(Graphics g){
        int h = player2Block.getHeight();
        int w = player2Block.getWidth();
        Color c = player2Block.getColor();
        int[][] shape = player2Block.getShape();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if(shape[row][col] == 1){
                    int x = (player2Block.getX() + col) * CELL_SIZE;
                    int y = (player2Block.getY() + row) * CELL_SIZE;

                    drawGridSquare(g,c,x,y);
                }
            }
        }
    }
    private void drawBackground(Graphics g){
        Color color;
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                color = background[row][col];
                if(color != null){
                    int x = col * CELL_SIZE;
                    int y = row * CELL_SIZE;

                    drawGridSquare(g,color,x,y);
                }
            }
        }
    }
    private void drawGridSquare(Graphics g , Color color , int x , int y){
        g.setColor(color);
        g.fillRect(x , y  , CELL_SIZE , CELL_SIZE);
        g.setColor(Color.black);
        g.drawRect(x  , y  , CELL_SIZE , CELL_SIZE);
    }
    private void drawItems(Graphics g) {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (grid[y][x] instanceof Item) {
                    Item item = (Item) grid[y][x];
                    g.drawImage(item.getImage(), x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                }
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawBlock1(g);
        drawBlock2(g);
        drawItems(g);
    }
}