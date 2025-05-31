package life;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JComponent;

public class LifeWidget extends JComponent {

    private static final Color BG = new Color(60, 60, 60);
    private static final Color GRID = Color.BLACK;
    private static final Color CELL = Color.WHITE;

    private int s;
    private int xOffset;

    private volatile boolean[][] board;
    private long cycleTime;
    private Thread thread;

    public LifeWidget() {
        this.board = new boolean[50][50];
        this.cycleTime = Long.MAX_VALUE;
        gameCycle();
    }

    public boolean[][] getBoard() {
        return board;
    }

    public void setBoard(boolean[][] board) {
        this.board = board;
        this.repaint();
    }

    public void setBoard(int width, int height) {
        this.board = new boolean[width][height];
        this.repaint();
    }

    public void changeCell(int x, int y) {
        x = (x - xOffset) / s;
        y /= s;
        if (!outOfBounds(x, y)) {
            board[x][y] = !board[x][y];
            this.repaint();
        }
    }

    public void generateRandomCells(int percentage) {
        int newCellCount = board.length * board[0].length * percentage / 100;
        Random r = new Random();
        for (int i = 0; i < newCellCount; i++) {
            int x = r.nextInt(board.length);
            int y = r.nextInt(board[0].length);
            board[x][y] = true;
        }
    }

    public void clearLife() {
        board = new boolean[board.length][board[0].length];
        this.repaint();
    }

    public void setCycleTime(long cycleTime) {
        this.cycleTime = cycleTime;
        thread.interrupt();
    }

    private void gameCycle() {
        thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(cycleTime);
                    } catch (InterruptedException ex) {
                        continue;
                    }
                    gameOfLife();
                    LifeWidget.this.repaint();
                }

            }
        };
        thread.start();
    }

    public void gameOfLife() {
        int[][] neighborCount = new int[board.length][board[0].length];
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                neighborCount[x][y] = getNeighbors(x, y);
            }
        }
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (neighborCount[x][y] < 2 || neighborCount[x][y] > 3) {
                    board[x][y] = false;
                    continue;
                }
                if (neighborCount[x][y] == 3) {
                    board[x][y] = true;
                }
            }
        }
    }

    private int getNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (!outOfBounds(x + i, y + j) && board[x + i][y + j] == true) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean outOfBounds(int x, int y) {
        return x < 0 || x >= board.length || y < 0 || y >= board[0].length;
    }

    private void getScaling() {
        int width = this.getWidth() / board.length;
        int height = this.getHeight() / board[0].length;
        s = Math.min(width, height);
        xOffset = height < width ? (this.getWidth() - s * board.length) / 2 : 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(BG);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        getScaling();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                g.setColor(GRID);
                g.drawRect(x * s + xOffset, y * s, s, s);
                if (board[x][y]) {
                    g.setColor(CELL);
                    g.fillRect(x * s + xOffset, y * s, s, s);
                }
            }
        }
    }
}
