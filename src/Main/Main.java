package Main;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Barriers.BarrierManager;
import Boids.Field;
import Utilities.Sound;
import Utilities.UIUtilities;
import Utilities.Utilities;
import Utilities.Vector2;

public class Main {

    public static Sound sound;

    public static void main(String[] args) {

        Main main = new Main();

        new Settings();

        sound = new Sound();
        sound.PlayClick();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                main.createAndShowGUI();
            }
        });
    }

    // Create the window
    public void createAndShowGUI() {

        int xOffset = -7;
        int yOffset = -30;

        JFrame frame = new JFrame("Bird Simulator");
        frame.setSize(Settings.Instance.Width, Settings.Instance.Height);

        Panel panel = new Panel();
        frame.add(panel);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Settings.Instance.Width = frame.getWidth();
                Settings.Instance.Height = frame.getHeight();
            }
        });

        // Create and add a MouseListener
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the mouse coordinates
                Vector2 mouse = new Vector2(e.getX() + xOffset, e.getY() + yOffset);

                Settings.Instance.MouseClick = mouse;
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Get the mouse coordinates
                Vector2 mouse = new Vector2(e.getX() + xOffset, e.getY() + yOffset);

                if (!Settings.Instance.BecomePredator && !UIUtilities.PositionIsUI(mouse)) {
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

                // Get the mouse coordinates
                double mouseX = e.getX() + xOffset;
                double mouseY = e.getY() + yOffset;
                Vector2 mouse = new Vector2((float) mouseX, (float) mouseY);

                if (!Settings.Instance.BecomePredator && !UIUtilities.PositionIsUI(mouse))
                    BarrierManager.DragPosition = new Vector2(mouse.x, mouse.y);

                if (Settings.Instance.BecomePredator && !Settings.Instance.Pause) {
                    var bird = Field.Birds.getFirst();
                    bird.Xvel = Utilities.Lerp(bird.Xvel, mouseX - bird.X, 0.1d);
                    bird.Yvel = Utilities.Lerp(bird.Yvel, mouseY - bird.Y, 0.1d);
                    bird.X = mouseX;
                    bird.Y = mouseY;
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // Get the mouse coordinates
                double mouseX = e.getX() + xOffset;
                double mouseY = e.getY() + yOffset;
                Vector2 mouse = new Vector2((float) mouseX, (float) mouseY);

                if (!Settings.Instance.BecomePredator && !UIUtilities.PositionIsUI(mouse))
                    BarrierManager.ClickPosition = new Vector2((float) mouseX, (float) mouseY);

                if (Settings.Instance.BecomePredator && !Settings.Instance.Pause) {
                    var bird = Field.Birds.getFirst();
                    bird.Xvel = Utilities.Lerp(bird.Xvel, mouseX - bird.X, 0.1d);
                    bird.Yvel = Utilities.Lerp(bird.Yvel, mouseY - bird.Y, 0.1d);
                    bird.X = mouseX;
                    bird.Y = mouseY;
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
