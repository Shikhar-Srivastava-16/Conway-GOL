import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.*;

public class Frame extends JFrame {
    JPanel mainGrid = new JPanel();
    JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 3, 3));
    final JLabel saveLabel = new JLabel();
    JButton save = new JButton("Save");
    JButton load = new JButton("Load");
    JButton stepButton = new JButton("New Generation");
    JButton runButton = new JButton("Run");
    JButton clearButton = new JButton("Clear");
    JButton exitButton = new JButton("Exit");
    JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 250, 1500, 750);
    JPanel fieldPanel = new JPanel(new GridLayout(3, 2));
    int delay;
    JTextField xField = new JTextField("2");
    JTextField yField = new JTextField("3");
    JTextField zField = new JTextField("3");
    JButton starter = new JButton("Start Game");
    JTextField gridField = new JTextField("50");

    public Frame() {
        setResizable(false);
        setLayout(null);
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 1000);
    }

    public void makeGameReady(int gridSize) {

        fieldPanel.add(new JLabel("value of x"));
        fieldPanel.add(xField);
        fieldPanel.add(new JLabel("value of y"));
        fieldPanel.add(yField);
        fieldPanel.add(new JLabel("value of z"));
        fieldPanel.add(zField);
        fieldPanel.setBounds(800, 300, 200, 150);
        add(fieldPanel);

        mainGrid.setLayout(new GridLayout(gridSize, gridSize));
        mainGrid.setBounds(30, 30, 700, 700);
        this.add(mainGrid);

        buttonPanel.setBounds(0, 760, 800, 100);
        buttonPanel.add(clearButton);
        buttonPanel.add(runButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(save);
        buttonPanel.add(load);

        framesPerSecond.setInverted(true);
        Hashtable<Integer, JLabel> sliderLabels = new Hashtable<>();
        sliderLabels.put(1500, new JLabel("Slow"));
        sliderLabels.put(250, new JLabel("Fast"));
        framesPerSecond.setLabelTable(sliderLabels);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setBounds(0, 860, 760, 50);
        add(framesPerSecond);

        buttonPanel.add(exitButton);
        this.add(buttonPanel);
        buttonPanel.setVisible(true);

        setVisible(true);
    }

    public void makeMenuReady() {

        JLabel background = new JLabel(new ImageIcon("./assets/others/GOL-opening.png"));
        background.setBounds(0, 30, 1400, 1000);
        add(background);

        starter.setBounds(800, 800, 200, 100);
        add(starter);

        JLabel gridLabel = new JLabel("Size of Grid to start with");
        gridLabel.setBounds(500, 800, 150, 50);
        add(gridLabel);

        gridField.setBounds(650, 800, 100, 50);
        add(gridField);

        setVisible(true);
    }
}
