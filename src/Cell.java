import java.util.ArrayList;

import javax.swing.JButton;

public class Cell extends JButton {

    // attributes
    /**
     * for live = true, cell is live
     * for live = false, cell is dead
     * 
     * at the end of the turn, if shouldLive = false, set live to false and set
     * shouldLive to true
     */
    private boolean live;
    private boolean shouldLive;

    // setters
    public void setLive(boolean live) {
        this.live = live;
    }

    public void setShouldLive(boolean shouldLive) {
        this.shouldLive = shouldLive;
    }

    // getters
    public boolean isShouldLive() {
        return shouldLive;
    }

    public boolean isLive() {
        return live;
    }

    public Cell() {
        this.live = false;
        this.setSize(1, 1);
    }

    // contructor
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
            shouldLive = false;
        }
    }

    public static void changeEndTurn(Cell[][] map) {
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (!cell.isShouldLive()) {
                    cell.setLive(false);
                    cell.setShouldLive(true);
                } else {
                    cell.setLive(true);
                }
            }
        }
    }
}