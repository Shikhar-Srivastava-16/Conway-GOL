import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        if (this.live)
            this.setBackground(Color.yellow);
        else
            this.setBackground(Color.black);

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
        setLive(false);
        // this.setBackground(Color.black);
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setLive(!isLive());
            }

        });
    }

    // contructor
    public Cell(boolean mode) {
        this.setLive(mode);
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setLive(!isLive());
            }

        });
    }

    /**
     *
     * @param listAdjCells ArrayList of all adjacent cells
     * @param x            A live cell with fewer than x live neighbours dies
     * @param y            A live cell with more than y live neighbours dies
     * @param z            A dead cell with exactly z live neighbours becomes live
     */
    public void changeCell(ArrayList<Cell> listAdjCells, int x, int y, int z) {

        int livingNeighbourCells = 0;
        for (Cell newCell : listAdjCells) {
            if (newCell.isLive()) {
                livingNeighbourCells++;
            }
        }

        if (this.isLive()) {
            if (livingNeighbourCells < x || livingNeighbourCells > y) {
                this.shouldLive = false;
            } else {
                this.shouldLive = true;
            }
        } else {
            if (livingNeighbourCells == z) {
                this.shouldLive = true;
            } else {
                this.shouldLive = false;
            }

        }
    }

    public static void changeEndTurn(Cell[][] map) {
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (!cell.isShouldLive()) {
                    cell.setLive(false);
                    cell.setBackground(Color.black);
                    cell.setShouldLive(true);
                } else {
                    cell.setLive(true);
                    cell.setBackground(Color.yellow);
                }
            }
        }
    }
}