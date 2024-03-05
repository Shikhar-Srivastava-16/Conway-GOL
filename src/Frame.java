import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Frame extends JFrame {
    JPanel mainGrid = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton save = new JButton("Save");
    final JLabel saveLabel = new JLabel();
    JButton reload = new JButton("Reload");

    public Frame() {

        this.setLayout(null);
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 1080);
        setBackground(Color.BLACK);

        mainGrid.setLayout(new GridLayout(50, 50, 0, 0));
        mainGrid.setBounds(10, 10, 500, 500);
        mainGrid.setBackground(Color.BLUE);
        this.add(mainGrid);

        buttonPanel.setBounds(10, 1070, 10, 10);
        buttonPanel.setBackground(Color.MAGENTA);
        buttonPanel.add(save);
        buttonPanel.add(saveLabel);
        buttonPanel.add(reload);
        this.add(buttonPanel);
        buttonPanel.setVisible(true);

        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Game of Life Files", "gol");
                fileChooser.setFileFilter(extensionFilter);
                int option = fileChooser.showSaveDialog(Frame.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    // check to see if the file ends with ".gol" by extracting the absolute path and
                    // appending it
                    if (!file.getAbsolutePath().endsWith(".gol")) {
                        file = new File(file + ".gol");
                    }
                    saveLabel.setText("File saved as: " + file.getName());
                } else {
                    saveLabel.setText("Save command did not work");
                }
            }

        });

        setVisible(true);
    }

}
