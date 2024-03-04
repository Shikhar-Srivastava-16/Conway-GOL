import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
        JButton reload = new JButton("Reload");
    
        buttonPanel.add(save);
        buttonPanel.add(reload);
        
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
            
        });

        setVisible(true);
    }
    public static void main(String[] args) {
        Frame gameFrame = new Frame();
    }
       
    
}