import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

public class GOL {

    public boolean runningState = false;
    public int gridSize, x, y, z;
    public Cell[][] arrCells;
    public Frame gameFrame;
    public int frameRate = 750;

    // setters
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

    /**
     * 
     * @param x
     * @param y
     * @param z x, y, z used for defualt rules in any instance of the game
     *          each game starts on the menu
     *          menu button has actionlistener added
     */
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

    /**
     * Add all actionlisteners to fields and buttons
     */
    public void addButtonActions() {

        gameFrame.saveJSON.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAsJson();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });

        gameFrame.stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step(x, y, z);
            }
        });

        gameFrame.saveGol.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAsGol();
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
                    System.out.println("IllegalArg: x should be an integer");
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

    /**
     * removes menu elements and repaints
     * adds all game elements by running functions to initialize empty grid of
     * correct size, change frame to game mode.
     */
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

    /**
     * Initializes cells at each index of arrCells
     * Goes through all cells, adds them to the screen
     */
    public void initializeEmptyGrid() {
        for (int i = 0; i < arrCells.length; i++) {
            for (int j = 0; j < arrCells[i].length; j++) {
                arrCells[i][j] = new Cell();
                gameFrame.mainGrid.add(arrCells[i][j]);
            }
        }
    }

    /**
     * 
     * @param x x-axis index of cell
     * @param y y-axis index of cell
     * @return list of all cells adjacent to cell at given index
     */
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

    /**
     * changes all cells using the changeCell() method
     * runs Cell.changeEndTurn() to change all cell states
     * 
     * @param x,y,z for rules
     */
    public void step(int x, int y, int z) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++)
                arrCells[i][j].changeCell(getAdjacentCells(i, j), x, y, z);
        }
        Cell.changeEndTurn(arrCells);
    }

    // -------------------------------!!!-----------------------------------
    /**
     * @throws IOException
     * 
     */
    public void saveAsGol() throws IOException {
        File golSaveFile = createSaveFile(".gol");

        if (golSaveFile != null){
            FileWriter writerObj = new FileWriter(golSaveFile);
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
    }

    /**
     * @param newExtension
     * @return
     */
    public File createSaveFile(String newExtension) {
        File saveFile = null;
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            saveFile = fileChooser.getSelectedFile();
            if (!saveFile.getAbsolutePath().endsWith(newExtension)) {
                saveFile = new File(saveFile.getAbsolutePath() + newExtension);
            }
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
            loadFile = fileChooser.getSelectedFile();
            System.out.println("File opened: " + loadFile.getName());
        } else {
            System.out.println("load command canceled");
        }
        return loadFile;
    }

    /**
     * 
     */
    public void loadSave() throws FileSystemException {

        try {
            File savedFile = openSaveFile();
            if (savedFile != null){
                if (savedFile.getName().endsWith(".gol")) {
                    BufferedReader reader = new BufferedReader(new FileReader(savedFile));
                    String line;
                    for (Cell[] row : arrCells) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        for (int i = 0; i < gridSize; i++) {
                            try {
                                if (line.charAt(i) == 'o') {
                                    row[i].setLive(true);
                                } else if (line.charAt(i) == '.') {
                                    row[i].setLive(false);
                                }
                            } catch (StringIndexOutOfBoundsException e) {
                                break;
                            }

                        }
                    }
                    reader.close();
                } else if (savedFile.getName().endsWith(".json")) {
                    loadFromJson(savedFile);
                } else {
                    System.out.println("Invalid File for loading save");
                }
            }
        }catch (IOException e) {
            System.out.println("test");
            System.out.println(e.getMessage());
        }
        
    
    }

    public void saveAsJson() throws IOException {
        File saveFile = createSaveFile(".json");
        if (saveFile != null) {
            FileWriter fileWriter = new FileWriter(saveFile);
            JSONObject jsonObject = new JSONObject();

            int n = arrCells.length;

            jsonObject.put("minRows", n);

            JSONObject rows = new JSONObject();

            for (int rowIndex = 0; rowIndex < arrCells.length; rowIndex++) {

                ArrayList<Integer> thisRow = new ArrayList<>();
                for (int i = 0; i < arrCells[rowIndex].length; i++) {
                    if (arrCells[rowIndex][i].isLive()) {
                        thisRow.add(i);
                    }
                }

                int[] arrayForRow = thisRow.stream().mapToInt(i -> i).toArray();

                if (arrayForRow.length != 0) {
                    rows.put(String.valueOf(rowIndex), arrayForRow);
                }
            }

            jsonObject.put("rows", rows);
            try {
                fileWriter.write(jsonObject.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("JSON file created: " + jsonObject);
        }
    }

    // https://crunchify.com/how-to-read-json-object-from-file-in-java/
    public void loadFromJson(File jsonSave) throws FileNotFoundException {
        System.out.println("loading from JSON");

        JsonObject myObj = Json.createReader(new FileReader(jsonSave)).readObject();
        JsonObject rowObject = myObj.getJsonObject("rows");

        for (int i = 0; i < arrCells.length; i++) {
            try {
                JsonArray arrThisRow = rowObject.getJsonArray(String.valueOf(i));
                if (arrThisRow != null) {
                    for (JsonValue jsonValue : arrThisRow) {
                        arrCells[i][Integer.valueOf(jsonValue.toString())].setLive(true);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            }
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