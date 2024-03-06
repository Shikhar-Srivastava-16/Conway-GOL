import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class Frame extends JFrame {
    JPanel mainGrid = new JPanel();
    JPanel buttonPanel = new JPanel();
    final JLabel saveLabel = new JLabel();
    JButton save = new JButton("Save");
    JButton reload = new JButton("Reload");
    JButton stepButton = new JButton("Step");
    JButton runButton = new JButton("Run");
    JButton clearButton = new JButton("Clear");
    JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 250, 1000, 750);
    private static Timer timer;
    int delay;

    public Frame(int gridSize) {
        setResizable(false);
        setLayout(null);
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 832);
        getContentPane().setBackground(Color.black);

        mainGrid.setLayout(new GridLayout(50, 50, 0, 0));
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
        buttonPanel.add(reload);
        buttonPanel.add(framesPerSecond);
        this.add(buttonPanel);
        buttonPanel.setVisible(true);

        
        setVisible(true);
    }

}
