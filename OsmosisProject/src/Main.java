
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static int Width = 700;
    public static int Height = 700;

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
        JFrame frame = new JFrame("Bird Simulator");
        frame.setSize(Width, Height);

        Panel panel = new Panel();
        frame.add(panel);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Width = frame.getWidth();
                Height = frame.getHeight();
                // System.out.println("New size - Width: " + Width + ", Height: " + Height);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
