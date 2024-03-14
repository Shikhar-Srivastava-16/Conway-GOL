
// https://stackoverflow.com/questions/12209801/how-to-change-file-extension-at-runtime-in-java
import javax.swing.UIManager;

public class Main {

    public static GOL gol;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        gol = new GOL(2, 3, 3);
        gol.addButtonActions();
    }
}
