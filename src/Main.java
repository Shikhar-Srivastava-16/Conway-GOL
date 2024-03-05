import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private static Cell[][] arrCells = new Cell[50][50];
    public static void main(String[] args) {

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
