
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

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
        frame.setSize(Settings.Width, Settings.Height);

        Panel panel = new Panel();
        frame.add(panel);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Settings.Width = frame.getWidth();
                Settings.Height = frame.getHeight();
                // System.out.println("New size - Width: " + Width + ", Height: " + Height);
            }
        });

        // Create and add a MouseListener
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UI.me = e;
                System.out.println("Mouse Clicked at X: " + e.getX() + ", Y: " + e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        // Create and add a MouseMotionListener
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // System.out.println("Mouse Dragged to X: " + e.getX() + ", Y: " + e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (Settings.BecomePredator) {
                    int xOffset = -7;
                    int yOffset = -28;

                    var bird = Field.Birds.getFirst();
                    bird.Xvel = Lerp(bird.Xvel, e.getX() + xOffset - bird.X, 0.1);
                    bird.Yvel = Lerp(bird.Yvel, e.getY() + yOffset - bird.Y, 0.1);
                    bird.X = e.getX() + xOffset;
                    bird.Y = e.getY() + yOffset;
                }
                // System.out.println("Mouse Moved to X: " + e.getX() + ", Y: " + e.getY());
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static double Lerp(double n0, double n1, double a) {
        return (1.0 - a) * n0 + (a * n1);
    }
}
