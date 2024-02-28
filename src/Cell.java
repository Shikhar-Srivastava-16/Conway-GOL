import java.util.ArrayList;

public class Cell {

    // attributes
    /**
     * for mode = true, cell is live
     * for mode = false, cell is dead
     */
    private boolean live;

    public boolean isLive() {
        return live;
    }

    public Cell(boolean mode) {
        this.live = mode;
    }

    /**
     * 
     * @param listAdjCells ArrayList of all adjacent cells
     * @param x            A live cell with fewer than x live neighbours dies
     * @param y            A live cell with more than y live neighbours dies
     * @param z            A dead cell with exactly z live neighbours becomes live
     */
    public void changeCell(ArrayList<Cell> listAdjCells, int x, int y, int z) {

        int liveCells = 8;
        for (Cell newCell : listAdjCells) {
            if (!newCell.isLive()) {
                liveCells++;
            }
        }

        if (liveCells < x || liveCells == z || liveCells > y) {
            live = false;
        }
    }
}