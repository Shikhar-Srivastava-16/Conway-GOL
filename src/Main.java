import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.UIManager;

public class Main {

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
