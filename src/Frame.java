import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.*;

public class Frame extends JFrame {
    public JPanel mainGrid = new JPanel();
    public JButton saveGol = new JButton("Save .gol");
    public JButton saveJSON = new JButton("Save JSON");
    public JButton load = new JButton("Load");
    public JButton stepButton = new JButton("New Generation");
    public JButton runButton = new JButton("Run");
    public JButton clearButton = new JButton("Clear");
    public JButton exitButton = new JButton("Regenerate Grid");
    public JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 100, 1500, 800);
    public JPanel fieldPanel = new JPanel(new GridLayout(3, 2));
    public JTextField xField = new JTextField("2");
    public JTextField yField = new JTextField("3");
    public JTextField zField = new JTextField("3");
    public JButton starter = new JButton("Start Game");
    public JTextField gridField = new JTextField("50");

    public Frame() {
        setResizable(false);
        setLayout(null);
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1170, 860);
    }

    /**
     * 
     * @param gridSize is used to initialize grid
     *                 puts all elements required for the game on screen.
     */
    public void makeGameReady(int gridSize) {

        fieldPanel.add(new JLabel("Death by Solitude:"));
        fieldPanel.add(xField);
        fieldPanel.add(new JLabel("Death by Overcrowding:"));
        fieldPanel.add(yField);
        fieldPanel.add(new JLabel("Rebirth:"));
        fieldPanel.add(zField);
        fieldPanel.setBounds(830, 430, 300, 150);
        add(fieldPanel);

        mainGrid.setLayout(new GridLayout(gridSize, gridSize));
        mainGrid.setBounds(10, 10, 800, 800);
        this.add(mainGrid);

        runButton.setBounds(830, 50, 300, 70);
        add(runButton);
        clearButton.setBounds(830, 126, 300, 70);
        add(clearButton);
        stepButton.setBounds(830, 202, 300, 70);
        add(stepButton);
        saveGol.setBounds(830, 278, 100, 70);
        add(saveGol);
        load.setBounds(930, 278, 100, 70);
        add(load);
        saveJSON.setBounds(1030, 278, 100, 70);
        add(saveJSON);

        exitButton.setBounds(830, 354, 300, 70);
        add(exitButton);

        framesPerSecond.setInverted(true);
        Hashtable<Integer, JLabel> sliderLabels = new Hashtable<>();
        sliderLabels.put(1500, new JLabel("Slow"));
        sliderLabels.put(100, new JLabel("Fast"));
        framesPerSecond.setLabelTable(sliderLabels);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setBounds(830, 580, 300, 50);
        add(framesPerSecond);

        setVisible(true);
    }

    /**
     * Puts all elements needed for the menu on screen
     */
    public void makeMenuReady() {

        JLabel background = new JLabel(new ImageIcon("../assets/others/GOL-opening.png"));
        background.setBounds(0, 0, 1170, 1100);
        add(background);

        starter.setBounds(100, 555, 200, 70);
        add(starter);

        JLabel gridLabel = new JLabel("Grid Size:");
        gridLabel.setBounds(100, 500, 100, 50);
        add(gridLabel);

        gridField.setBounds(200, 500, 100, 50);
        add(gridField);

        setVisible(true);
    }
}