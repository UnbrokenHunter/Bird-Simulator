
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
            }
        });

        // Create and add a MouseListener
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UI.me = e;
                BirdViewer.me = e;
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!Settings.BecomePredator) {
                    System.out.println("Create Barrier");
                    BarrierManager.CreateBarrier();
                }
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
                int xOffset = -7;
                int yOffset = -28;

                // Get the mouse coordinates
                float mouseX = e.getX() + xOffset;
                float mouseY = e.getY() + yOffset;

                BarrierManager.DragPosition = new Vector2(mouseX, mouseY);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int xOffset = -7;
                int yOffset = -28;

                // Get the mouse coordinates
                float mouseX = e.getX() + xOffset;
                float mouseY = e.getY() + yOffset;

                if (!Settings.BecomePredator)
                    BarrierManager.ClickPosition = new Vector2(mouseX, mouseY);

                if (Settings.BecomePredator && !Settings.Pause) {
                    var bird = Field.Birds.getFirst();
                    bird.Xvel = Lerp(bird.Xvel, e.getX() + xOffset - bird.X, 0.1);
                    bird.Yvel = Lerp(bird.Yvel, e.getY() + yOffset - bird.Y, 0.1);
                    bird.X = e.getX() + xOffset;
                    bird.Y = e.getY() + yOffset;
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static double Lerp(double n0, double n1, double a) {
        return (1.0 - a) * n0 + (a * n1);
    }
}
