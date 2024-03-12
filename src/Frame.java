import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;

public class Frame extends JFrame {
    JPanel mainGrid = new JPanel();
    JPanel buttonPanel = new JPanel();
    final JLabel saveLabel = new JLabel();
    JButton save = new JButton("Save");
    JButton load = new JButton("Reload");
    JButton stepButton = new JButton("Step");
    JButton runButton = new JButton("Run");
    JButton clearButton = new JButton("Clear");
    JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 250, 1000, 750);
    JPanel fieldPanel = new JPanel(new GridLayout(6, 1));
    int delay;
    JTextField xField = new JTextField("2");
    JTextField yField = new JTextField("3");
    JTextField zField = new JTextField("3");
    JButton starter = new JButton("Start Game");
    JTextField gridField = new JTextField();

    public Frame() {
        setResizable(false);
        setLayout(null);
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);

    }

    public void makeGameReady(int gridSize) {

        fieldPanel.add(new JLabel("value of x"));
        fieldPanel.add(xField);
        fieldPanel.add(new JLabel("value of y"));
        fieldPanel.add(yField);
        fieldPanel.add(new JLabel("value of z"));
        fieldPanel.add(zField);
        fieldPanel.setBounds(800, 200, 100, 600);
        add(fieldPanel);

        mainGrid.setLayout(new GridLayout(gridSize, gridSize));
        mainGrid.setBounds(30, 30, 700, 700);
        mainGrid.setBackground(Color.BLUE);
        this.add(mainGrid);

        buttonPanel.setBounds(0, 760, 760, 50);
        buttonPanel.setBackground(new Color(0, 0, 128));
        buttonPanel.add(clearButton);
        buttonPanel.add(runButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(save);
        buttonPanel.add(saveLabel);
        buttonPanel.add(load);
        buttonPanel.add(framesPerSecond);
        this.add(buttonPanel);
        buttonPanel.setVisible(true);


        setVisible(true);
    }

    public void makeMenuReady() {
        starter.setBounds(100, 100, 100, 50);
        add(starter);

        JLabel gridLabel = new JLabel("Size of Grid to start with");
        gridLabel.setBounds(200, 100, 100, 50);
        add(gridLabel);

        gridField.setBounds(300, 100, 100, 50);
        add(gridField);

        setVisible(true);
    }
}
