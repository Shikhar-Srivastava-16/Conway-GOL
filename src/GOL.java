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
import javax.naming.directory.InvalidAttributeValueException;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

public class GOL {
    // fields
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
    // constructor for GOL class which initialises the grid size and x, y and z
    // values. It also creates a new gameframe which will display the main menu
    public GOL(int x, int y, int z) {

        this.gridSize = 50;
        this.x = x;
        this.y = y;
        this.z = z;

        gameFrame = new Frame();
        // gameFrame.makeGameReady(gridSize);
        gameFrame.makeMenuReady();
        // adds an actionlistener to the start game button which will call the setup()
        // method when clicked
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
        // calls the saveAsJson() method when the saveJSON button is clicked
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
        // calls the step method when the stepButton/new generation button is clicked
        gameFrame.stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step(x, y, z);
            }
        });
        // calls the saveAsGol() method when the saveGol button is clicked
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
        // when the run button is clicked the method will check if the running state
        // boolean is true or false
        // if true it will set running state to false and set the button text to run
        // if false it will set running state to true and the button text to stop as
        // well as create a new thread with a new loopedStepTask object as an
        // argument and will then run the task
        gameFrame.runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (runningState == false) {
                    runningState = true;
                    gameFrame.runButton.setText("Stop");
                    Thread task = new Thread(new loopedStepTask());
                    task.start();
                } else {
                    runningState = false;
                    gameFrame.runButton.setText("Run");
                }

            }
        });
        // calls the loadSave() method when the load button is clicked
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
        // iterates through the cell array and sets all the cells to dead when the clear
        // button is clicked
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
        // will detect when a new integer is input into the x field and will set the x
        // attribute accordingly provided it's valid
        gameFrame.xField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (checkIfValidXYZ(Integer.parseInt(gameFrame.xField.getText()))) {
                        setX(Integer.parseInt(gameFrame.xField.getText()));
                    } else {
                        System.out.println("Enter an integer between 0 and 8");
                        gameFrame.xField.setText(Integer.toString(x));
                    }
                } catch (IllegalArgumentException a) {
                    System.out.println("IllegalArg: x should be an integer");
                    gameFrame.xField.setText(Integer.toString(x));
                }
            }
        });
        // will detect when a new integer is input into the y field and will set the y
        // attribute accordingly provided it's valid
        gameFrame.yField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (checkIfValidXYZ(Integer.parseInt(gameFrame.yField.getText()))) {
                        setY(Integer.parseInt(gameFrame.yField.getText()));
                    } else {
                        System.out.println("Enter an integer between 0 and 8");
                        gameFrame.yField.setText(Integer.toString(y));
                    }
                } catch (IllegalArgumentException a) {
                    System.out.println("y should be an integer");
                    gameFrame.yField.setText(Integer.toString(y));
                }
            }
        });
        // will detect when a new integer is input into the z field and will set the z
        // attribute accordingly provided it's valid
        gameFrame.zField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (checkIfValidXYZ(Integer.parseInt(gameFrame.zField.getText()))) {
                        setZ(Integer.parseInt(gameFrame.zField.getText()));
                    } else {
                        System.out.println("Enter an integer between 0 and 8");
                        gameFrame.zField.setText(Integer.toString(z));
                    }
                } catch (IllegalArgumentException a) {
                    System.out.println("z should be an integer");
                    gameFrame.zField.setText(Integer.toString(z));
                }
            }
        });
        // when the exit button is pressed the running state if not already is set to
        // false and the run button text is set to run. Then everything is removed from
        // the frame and the cell array is set to null and then the makeMenuReady()
        // method is called
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

    // checks if the inputed x, y or z values are between 0 to 8 and will return a
    // boolean
    public boolean checkIfValidXYZ(int inputedValue) {
        int[] validValues = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
        for (int validValue : validValues) {
            if (inputedValue == validValue) {
                return true;
            }
        }
        return false;
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
            if (size > 100 || size < 3) {
                throw new InvalidAttributeValueException();
            }
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
            resetMenu();
        } catch (IllegalArgumentException e) {
            System.out.println("0 not a valid grid size");
            resetMenu();
        } catch (NegativeArraySizeException e) {
            System.out.println("cannot have a negative grid size");
            resetMenu();
        } catch (InvalidAttributeValueException e) {
            System.out.println("please enter a grid size between 3 and 100");
            resetMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method that will remove everything from the frame and recreate the menu with
    // grid size set to 50
    public void resetMenu() {
        gameFrame.getContentPane().removeAll();
        gameFrame.repaint();
        gameFrame.mainGrid.removeAll();
        gameFrame.fieldPanel.removeAll();
        arrCells = null;
        gameFrame.makeMenuReady();
        gameFrame.gridField.setText(50 + "");
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

    /**
     * @throws IOException
     *                     method will create a new .gol file and will write the
     *                     current state of the grid to it
     */
    public void saveAsGol() throws IOException {
        File golSaveFile = createSaveFile(".gol");

        if (golSaveFile != null) {
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
     *         creates a pop up window asking the user where they would like to
     *         create the save file and will return said file
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

    /**
     * @throws FileSystemException
     *                             creates a pop up window asking the user which
     *                             file the would like to load and
     *                             will return the file
     */
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
     * @throws FileSystemException
     *                             if a load file is selected this method will read
     *                             the file and set the state of all cells
     *                             accordingly
     */
    public void loadSave() throws FileSystemException {
        try {
            File savedFile = openSaveFile();
            if (savedFile != null) {
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
        } catch (IOException e) {
            System.out.println("test");
            System.out.println(e.getMessage());
        }
    }

    /**
     * @throws IOException
     *                     method will create a new .json file and will write the
     *                     current state of the grid to it
     */
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

    // the code below references code from a blog posted on
    // https://crunchify.com/how-to-read-json-object-from-file-in-java/
    // (last accessed 15/03/2024)
    // BEGIN referenced code
    /**
     * 
     * @throws FileNotFoundException
     *                               method reads the json file selected by the user
     *                               and set the grid accordingly
     */
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
    // END referenced code

    // class that implements runnable with an overriden run method which will run
    // the step() method followed by delay depending on the framerate in an infinite
    // loop while the runningState boolean is true
    public class loopedStepTask implements Runnable {
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