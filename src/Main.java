import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;

public class Main {

    private static Cell[][] arrCells = new Cell[50][50];

    public static void main(String[] args) {
        Frame gameFrame = new Frame();

        for (Cell[] row : arrCells) {
            for (Cell cell : row) {
                cell = new Cell();
                gameFrame.mainGrid.add(cell);
            }
        }

        gameFrame.mainGrid.setPreferredSize(new Dimension(1000, 1000));
        gameFrame.mainGrid.setVisible(true);
        gameFrame.setVisible(true);
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
