import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.UIManager;

public class Main {
    private static boolean runningState = false;
    private static Cell[][] arrCells = new Cell[50][50];
    private static File saveFile = new File("save.gol");
    private static int gridSize;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        gridSize = 50;
        Frame gameFrame = new Frame(gridSize);

        for (int i = 0; i < arrCells.length; i++) {
            for (int j = 0; j < arrCells[i].length; j++) {
                arrCells[i][j] = new Cell();
                gameFrame.mainGrid.add(arrCells[i][j]);
            }
        }

        gameFrame.mainGrid.setVisible(true);
        gameFrame.setVisible(true);

        gameFrame.mainGrid.setVisible(true);
        gameFrame.setVisible(true);

        addActions(gameFrame.stepButton, gameFrame.runButton, gameFrame.save);
    }

    public static void addActions(JButton stepButton, JButton runButton, JButton saveButton) {
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step(2, 3, 3);
            }
        });

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveGame(saveFile);
                } catch (Exception a) {
                    System.out.println("Main.addActions(...).new ActionListener() {...}.actionPerformed()");
                }
            }

        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (runningState == false) {
                    runningState = true;
                    runButton.setText("Stop");
                } else {
                    runningState = false;
                    runButton.setText("Run");
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
            for (int j = 0; j < arrCells[i].length; j++)
                arrCells[i][j].changeCell(getAdjacentCells(i, j), x, y, z);
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
     */
    public static void saveGame(File saveFile) throws IOException {
        FileWriter writerObj = new FileWriter(saveFile);
        for (Cell[] row : arrCells) {
            String rowString = "";
            for (Cell cell : row) {
                // for live cell
                if (cell.isLive()) {
                    rowString = rowString + "o";
                } else {
                    rowString = rowString + ".";
                }
                // System.out.println(); for logs
            }
            // System.out.printf("", rowString);
            writerObj.write(rowString + "\n");
        }
        writerObj.close();
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
