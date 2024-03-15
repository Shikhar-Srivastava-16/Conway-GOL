import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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
            // live cells are black
            this.setBackground(Color.BLACK);
        else
            // dead cells are white
            this.setBackground(Color.WHITE);

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

    // contructor
    /**
     * initially, shouldLive is false and cell is dead.
     * Each cell has actionlistener
     */
    public Cell() {
        shouldLive = false;
        setLive(false);
        setBorder(BorderFactory.createEtchedBorder());
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
     *                     Method will checks how many neigbouring cells are alive
     *                     and will set the state according to the rules
     */
    public void changeCell(ArrayList<Cell> listAdjCells, int x, int y, int z) {

        int livingNeighbourCells = 0;
        for (Cell newCell : listAdjCells) {
            if (newCell.isLive()) {
                livingNeighbourCells++;
            }
        }

        if (this.isLive()) {
            if (!(livingNeighbourCells < x || livingNeighbourCells > y)) {
                this.shouldLive = true;
            }
        } else {
            if (livingNeighbourCells == z) {
                this.shouldLive = true;
            }
        }
    }

    /**
     *
     * @param map A 2D cell array which represents the grid/map for the game
     *            method itterates through all the cells and checks its should live
     *            status
     *            if it is false it will set the cells live status to false and its
     *            should live to true and vice verca
     */
    public static void changeEndTurn(Cell[][] map) {
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (!cell.isShouldLive()) {
                    cell.setLive(false);
                } else {
                    cell.setLive(true);
                    cell.setShouldLive(false);
                }
            }
        }
    }
}