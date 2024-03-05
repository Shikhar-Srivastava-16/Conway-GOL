import java.awt.Color;
import java.awt.Dimension;
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

    public Frame() {

        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        getContentPane().add(mainPanel);

        JPanel mainGrid = new JPanel();
        mainGrid.setPreferredSize(new Dimension(1000, 600));
        mainGrid.setBackground(Color.BLUE);
        mainPanel.add(mainGrid);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(1000, 150));
        buttonPanel.setBackground(Color.MAGENTA);
        mainPanel.add(buttonPanel);

        JButton save = new JButton("Save");
        final JLabel saveLabel = new JLabel();
        JButton reload = new JButton("Reload");
    
        buttonPanel.add(save);
        buttonPanel.add(saveLabel);
        buttonPanel.add(reload);
        
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Game of Life Files", "gol");
                fileChooser.setFileFilter(extensionFilter);
                int option = fileChooser.showSaveDialog(Frame.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    // check to see if the file ends with ".gol" by extracting the absolute path and appending it
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
    public static void main(String[] args) {
        Frame gameFrame = new Frame();
    }
       
    
}