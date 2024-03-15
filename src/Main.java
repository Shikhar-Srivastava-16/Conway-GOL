import javax.swing.UIManager;

public class Main {

    public static GOL gol;

    public static void main(String[] args) {
        // try catch where the look and feel is applied to the ui manager
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // running new instance of game of life, by default uses (2,3,3) as rules
        gol = new GOL(2, 3, 3);

        // add action listeners to buttons and fields on screen.
        gol.addButtonActions();
    }
}
