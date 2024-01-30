
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static final int ScreenSizeX = 700;
    public static final int ScreenSizeY = 700;

    public static void main(String[] args) {

        Main main = new Main();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                main.createAndShowGUI();
            }
        });
    }

    // Create the window
    public void createAndShowGUI() {
        JFrame frame = new JFrame("Osmosis");
        frame.setSize(ScreenSizeX, ScreenSizeY);

        Panel panel = new Panel();
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
