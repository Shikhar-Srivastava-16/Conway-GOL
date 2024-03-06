// https://stackoverflow.com/questions/12209801/how-to-change-file-extension-at-runtime-in-java

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main {
    private static boolean runningState = false;
    private static Cell[][] arrCells = new Cell[50][50];
    private static int gridSize;
    private static Frame gameFrame;
    private static int frameRate = 750;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        gridSize = 50;
        gameFrame = new Frame(gridSize);

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

        addActions(gameFrame.stepButton, gameFrame.runButton, gameFrame.save, gameFrame.reload, gameFrame.clearButton);

        gameFrame.framesPerSecond.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                frameRate = gameFrame.framesPerSecond.getValue();
            }
            
        });

    }

    public static void initializeEmptyGrid(Frame gameFrame){
        for (int i = 0; i < arrCells.length; i++) {
            for (int j = 0; j < arrCells[i].length; j++) {
                arrCells[i][j] = new Cell();
                gameFrame.mainGrid.add(arrCells[i][j]);
            }
        }
    }

    public static void addActions(JButton stepButton, JButton runButton, JButton saveButton, JButton loadButton, JButton clearButton) {
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
                    saveGame();
                } catch (Exception a) {
                    System.out.println(a.getMessage());
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

                Thread task = new Thread(new ReasourceIntensiveTask());
                task.start();

            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    loadSave();
                } catch (Exception a) {
                    System.out.println(a.getMessage());
                }

            }

        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < arrCells.length; i++) {
                    for (int j = 0; j < arrCells[i].length; j++) {
                        arrCells[i][j].setLive(false);
                    }
                }
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
                    Thread.sleep(frameRate);
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
    public static void saveGame() throws IOException {
        File saveFile = null;

        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            saveFile = changeExtension(fileChooser.getSelectedFile(), ".gol");
            System.out.println("File Saved as: " + saveFile.getName());
        } else {
            System.out.println("Save command canceled");
        }

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
            }
            writerObj.write(rowString + "\n");
        }
        writerObj.close();
    }

    /**
     * 
     */
    public static void loadSave() throws FileSystemException {

        File loadFile = null;

        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            if (fileChooser.getSelectedFile().getName().contains(".gol")) {
                loadFile = fileChooser.getSelectedFile();
                System.out.println("File opened: " + loadFile.getName());
            } else {
                String fileName = fileChooser.getSelectedFile().getName();
                throw new FileSystemException(fileName + " is not a .gol file");
            }
        } else {
            System.out.println("load command canceled");
        }

        String line = "";
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(loadFile))) {
            while ((line = reader.readLine()) != null) {
                lineCount++;
                for (int i = 0; i < line.length(); i++) {
                    String c = String.valueOf(line.charAt(i));
                    if (c.equals("o")) {
                        arrCells[lineCount][i].setLive(true);
                    } else {
                        arrCells[lineCount][i].setLive(false);
                    }
                }
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static File changeExtension(File f, String newExtension) {
        if (f.getAbsolutePath().contains(".")) {
            int i = f.getAbsolutePath().lastIndexOf('.');
            String name = f.getAbsolutePath().substring(0, i);
            return new File(name + newExtension);
        } else {
            return new File(f.getAbsolutePath() + ".gol");
        }
    }

}
