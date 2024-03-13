import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GOL {

    public boolean runningState = false;
    public int gridSize, x, y, z;

    public Cell[][] arrCells;
    public Frame gameFrame;
    public int frameRate = 750;

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public GOL(int x, int y, int z) {

        this.gridSize = 50;
        this.x = x;
        this.y = y;
        this.z = z;

        gameFrame = new Frame();
        // gameFrame.makeGameReady(gridSize);
        gameFrame.makeMenuReady();
        gameFrame.starter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setup();
            }

        });

    }

    public void addButtonActions() {
        gameFrame.stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step(x, y, z);
            }
        });

        gameFrame.save.addActionListener(new ActionListener() {

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

        gameFrame.runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (runningState == false) {
                    runningState = true;
                    gameFrame.runButton.setText("Stop");
                } else {
                    runningState = false;
                    gameFrame.runButton.setText("Run");
                }

                Thread task = new Thread(new ReasourceIntensiveTask());
                task.start();

            }
        });

        gameFrame.load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    loadSave();
                } catch (Exception a) {
                    System.out.println(a.getMessage());
                }

            }

        });

        gameFrame.clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < arrCells.length; i++) {
                    for (int j = 0; j < arrCells[i].length; j++) {
                        arrCells[i][j].setLive(false);
                    }
                }
            }

        });

        gameFrame.xField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setX(Integer.parseInt(gameFrame.xField.getText()));
                } catch (IllegalArgumentException a) {
                    System.out.println("x should be an integer");
                }

            }

        });

        gameFrame.yField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setY(Integer.parseInt(gameFrame.yField.getText()));
                } catch (IllegalArgumentException a) {
                    System.out.println("y should be an integer");
                }

            }

        });

        gameFrame.zField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setZ(Integer.parseInt(gameFrame.zField.getText()));
                } catch (IllegalArgumentException a) {
                    System.out.println("z should be an integer");
                }

            }

        });

        gameFrame.exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (runningState) {
                    runningState = false;
                    gameFrame.runButton.setText("Run");
                }

                gameFrame.getContentPane().removeAll();
                gameFrame.repaint();
                gameFrame.mainGrid.removeAll();
                gameFrame.fieldPanel.removeAll();
                arrCells = null;
                gameFrame.makeMenuReady();
            }

        });
    }

    public void setup() {
        gameFrame.getContentPane().removeAll();
        gameFrame.repaint();

        try {
            int size = Integer.parseInt(gameFrame.gridField.getText());
            setGridSize(size);
            arrCells = new Cell[gridSize][gridSize];
            gameFrame.makeGameReady(size);

            initializeEmptyGrid();

            gameFrame.mainGrid.setVisible(true);
            gameFrame.setVisible(true);

            gameFrame.framesPerSecond.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    frameRate = gameFrame.framesPerSecond.getValue();
                }

            });
        } catch (NumberFormatException e) {
            System.out.println("not a number");
            gameFrame.getContentPane().removeAll();
            gameFrame.repaint();
            gameFrame.mainGrid.removeAll();
            gameFrame.fieldPanel.removeAll();
            arrCells = null;
            gameFrame.makeMenuReady();

        } catch (IllegalArgumentException e) {
            System.out.println("0 not a valid grid size");
            gameFrame.getContentPane().removeAll();
            gameFrame.repaint();
            gameFrame.mainGrid.removeAll();
            gameFrame.fieldPanel.removeAll();
            arrCells = null;
            gameFrame.makeMenuReady();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initializeEmptyGrid() {
        for (int i = 0; i < arrCells.length; i++) {
            for (int j = 0; j < arrCells[i].length; j++) {
                arrCells[i][j] = new Cell();
                gameFrame.mainGrid.add(arrCells[i][j]);
            }
        }
    }

    public ArrayList<Cell> getAdjacentCells(int x, int y) {
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

    public void step(int x, int y, int z) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++)
                arrCells[i][j].changeCell(getAdjacentCells(i, j), x, y, z);
        }
        Cell.changeEndTurn(arrCells);
    }

    /**
     * @throws IOException
     * 
     */
    public void saveGame() throws IOException {
        // File saveFile = null;

        // JFileChooser fileChooser = new JFileChooser();
        // int option = fileChooser.showSaveDialog(null);
        // if (option == JFileChooser.APPROVE_OPTION) {
        // saveFile = changeExtension(fileChooser.getSelectedFile(), ".gol");
        // System.out.println("File Saved as: " + saveFile.getName());
        // } else {
        // System.out.println("Save command canceled");
        // }

        FileWriter writerObj = new FileWriter(createSaveFile());
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

    public File createSaveFile() {
        File saveFile = null;

        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            saveFile = changeExtension(fileChooser.getSelectedFile(), ".gol");
            System.out.println("File Saved as: " + saveFile.getName());
        } else {
            System.out.println("Save command canceled");
        }
        return saveFile;
    }

    public File openSaveFile() throws FileSystemException {
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
        return loadFile;
    }

    /**
     * 
     */
    public void loadSave() throws FileSystemException {

        // File loadFile = null;
        // JFileChooser fileChooser = new JFileChooser();
        // int option = fileChooser.showOpenDialog(null);
        // if (option == JFileChooser.APPROVE_OPTION) {
        // if (fileChooser.getSelectedFile().getName().contains(".gol")) {
        // loadFile = fileChooser.getSelectedFile();
        // System.out.println("File opened: " + loadFile.getName());
        // } else {
        // String fileName = fileChooser.getSelectedFile().getName();
        // throw new FileSystemException(fileName + " is not a .gol file");
        // }
        // } else {
        // System.out.println("load command canceled");
        // }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(openSaveFile()));
            String line;
            for (Cell[] row : arrCells) {
                line = reader.readLine();
                for (int i = 0; i < gridSize; i++) {
                    if (line.charAt(i) == 'o') {
                        row[i].setLive(true);
                    } else if (line.charAt(i) == '.') {
                        row[i].setLive(false);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("test");
            System.out.println(e.getMessage());
        }
    }

    public File changeExtension(File f, String newExtension) {
        if (f.getAbsolutePath().contains(".")) {
            int i = f.getAbsolutePath().lastIndexOf('.');
            String name = f.getAbsolutePath().substring(0, i);
            return new File(name + newExtension);
        } else {
            return new File(f.getAbsolutePath() + ".gol");
        }
    }

    public class ReasourceIntensiveTask implements Runnable {

        @Override
        public void run() {
            while (runningState) {
                step(x, y, z);
                try {
                    Thread.sleep(frameRate);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted!");
                }
            }
        }
    }
}
