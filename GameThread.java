import java.util.*;

public class GameThread extends Thread {
    private final GameArea gameArea;
    private final GameFrame gf;
    private final List<Item> items = new ArrayList<>();
    private final Random random = new Random();
    private final SoundEffect music = new SoundEffect("/item/music.wav");
    private int score;
    private int time;
    private int dalaytime;
    private boolean gameRunning = true;
    private boolean timerStarted = false;


    GameThread(GameArea gameArea, GameFrame gf ,int dalaytime) {
        this.dalaytime = dalaytime;
        this.gameArea = gameArea;
        this.gf = gf;
        music.setVolume(0.77f);
        music.loop();
    }

    @Override
    public void run() {
        gf.updateScore(score); 
        startTimer();
        

        int blockCount = 0;
        while (gameRunning) {
            gameArea.spawnBlocks();
            gf.repaintNextBlockPanel();
            
            boolean canMove1 = true;
            boolean canMove2 = true;

            blockCount++;
            if (blockCount >= 3) {
                spawnItem();
                blockCount = 0;
            }

            while (canMove1 || canMove2) {
                gameArea.moveItemsDown();
                checkItemCollision(gameArea.getCurrentBlockPosition1());
                checkItemCollision(gameArea.getCurrentBlockPosition2());

                if (canMove1) { canMove1 = gameArea.moveBlockDown1();}
                if (canMove2) { canMove2 = gameArea.moveBlockDown2();}
                
                sleep(dalaytime);
                
                if (!canMove1) {gameArea.moveBlockToBackground1();}
                if (!canMove2) {gameArea.moveBlockToBackground2();}
            }
            
            score += gameArea.clearLines() * 100;
            gf.updateScore(score);
            
            if (gameArea.isBlockOutOfBounds1() || gameArea.isBlockOutOfBounds2()) {
                gameRunning = false;
                music.stop();
                gf.showGameOverPopup(score, time);
                break;
            }
        }
    }

    private void sleep(int milliseconds) {
        try {Thread.sleep(milliseconds);} 
        catch (InterruptedException e) {}
    }
    
    private void spawnItem() {
        int x = random.nextInt(GameArea.BOARD_HEIGHT);
        int y = random.nextInt(GameArea.BOARD_WIDTH);
        String[] itemTypes = {"bombBlock","bonus","scoreD","speedUp","whiteblock","color"};
        String randomType = itemTypes[random.nextInt(itemTypes.length)];

        Item newItem = new Item(x, y, randomType);
        items.add(newItem);
        gameArea.addItemToGrid(newItem);
    }

    private void checkItemCollision(int[][] blockPositions) {
        for (int[] position : blockPositions) {
            int blockX = position[0];
            int blockY = position[1];
            for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
                Item item = iterator.next();
                if (blockX == item.getX() && blockY == item.getY()) {
                    item.activateEffect(this); 
                    iterator.remove(); 
                    gameArea.removeItemFromGrid(item);
                }
            }
        }
    }
    public void startTimer() {
        if (!timerStarted) { 
            timerStarted = true; 
            new Thread(() -> {
                while (gameRunning) {
                    sleep(1000);
                    time++;
                    gf.updateTime(time);
                }
            }).start();
        }
    }
    //Item Function  =============================
    public void BonusScore() {score += 10;} 
    public void Color() {gameArea.randomColor();}
    public void BombLine() {
        gameArea.BombLine();
        score += 100;
    }
    public void Scoredecrease() {
        int S = random.nextInt(25);
        score -= S;
    }
    public void SpeedUp() {
        int S = random.nextInt(50) + 50;
        dalaytime-=S;
    }
    public void WhiteBlock() {
        gameArea.WhiteBlock();
    }
    //============================================
}
