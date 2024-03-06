import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class Main {
    private static boolean runningState = false;
    private static Cell[][] arrCells = new Cell[50][50];

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Frame gameFrame = new Frame();

        for (int i = 0; i < arrCells.length; i++) {
            for (int j = 0; j < arrCells[i].length; j++) {
                arrCells[i][j] = new Cell();
                gameFrame.mainGrid.add(arrCells[i][j]);
            }
        }

        gameFrame.mainGrid.setVisible(true);
        gameFrame.setVisible(true);

        gameFrame.stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step(2, 3, 3);
            }
        });

        gameFrame.runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (runningState == false) {
                    runningState = true;
                } else {
                    runningState = false;
                }

                // ExecutorService executorService = Executors.newFixedThreadPool(5);

                // executorService.execute(new Runnable() {
                // public void run() {
                // while (runningState) {
                // step(2, 3, 3);
                // }
                // }
                // });

                // executorService.shutdown();

                Thread task = new Thread(new ReasourceIntensiveTask());
                task.start();

            }
        });

        gameFrame.mainGrid.setVisible(true);
        gameFrame.setVisible(true);

    }

    public static ArrayList<Cell> getAdjacentCells(int x, int y) {
        ArrayList<Cell> adjacentCells = new ArrayList<>();

        int right = x + 1;
        int left = x - 1;
        int up = y - 1;
        int down = y + 1;

        if (right >= arrCells.length) {
            right = right - arrCells.length;
        }
        if (down >= arrCells.length) {
            down = down - arrCells.length;
        }
        if (left < 0) {
            left = left + arrCells.length;
        }
        if (up < 0) {
            up = up + arrCells.length;
        }
        adjacentCells.add(arrCells[left][up]);
        adjacentCells.add(arrCells[x][up]);
        adjacentCells.add(arrCells[right][up]);
        adjacentCells.add(arrCells[left][y]);
        adjacentCells.add(arrCells[right][y]);
        adjacentCells.add(arrCells[left][down]);
        adjacentCells.add(arrCells[x][down]);
        adjacentCells.add(arrCells[right][down]);

        return adjacentCells;
    }

    public static void step(int x, int y, int z) {
        for (int i = 0; i < arrCells.length; i++) {
            for (int j = 0; j < arrCells[i].length; j++) {
                int livingNeighbours = 0;
                ArrayList<Cell> neighbourCells = getAdjacentCells(i, j);
                for (Cell neighbour : neighbourCells) {
                    if (neighbour.isLive()) {
                        livingNeighbours++;
                    }
                }

                if (arrCells[i][j].isLive()) {
                    if (livingNeighbours < x || livingNeighbours > y) {
                        arrCells[i][j].setShouldLive(false);
                    } else {
                        arrCells[i][j].setShouldLive(true);
                    }
                } else {
                    if (livingNeighbours == z) {
                        arrCells[i][j].setShouldLive(true);
                    } else {
                        arrCells[i][j].setShouldLive(false);
                    }

                }
            }
        }
        Cell.changeEndTurn(arrCells);
    }

    private static class ReasourceIntensiveTask implements Runnable {

        @Override
        public void run() {
            while (runningState) {
                step(2, 3, 3);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted!");
                }
            }
        }

    }

    /**
     * @throws IOException
     * 
     * 
     */
    public static void saveGame(File saveFile) throws IOException {
        FileWriter writerObj = new FileWriter(saveFile);
        for (Cell[] row : arrCells) {
            for (Cell cell : row) {
                // for live cell
                if (cell.isLive())
                    writerObj.write("o");
                else
                    writerObj.write(".");
                // System.out.println(); for logs
            }
        }
    }

    /**
     * 
     */
    public static void loadSave() {
        /*
         * FileWriter writerObj = new FileWriter(saveFile);
         * for (Cell[] row : arrCells) {
         * for (Cell cell : row) {
         * // for live cell
         * if (cell.isLive())
         * writerObj.write("o");
         * else
         * writerObj.write(".");
         * // System.out.println(); for logs
         * }
         * }
         */
    }
}
