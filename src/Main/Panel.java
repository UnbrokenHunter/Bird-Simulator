package Main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import Barriers.Barrier;
import Barriers.BarrierManager;
import Boids.Bird;
import Boids.Field;
import UI.BirdViewer;
import UI.UI;
import Utilities.Looper;

public class Panel extends JPanel {

    private Field field;
    private UI ui;
    private BirdViewer birdViewer;

    public Panel() {
        ui = new UI();
        birdViewer = new BirdViewer();
        field = new Field(Settings.Instance.BirdCount);
        new Looper(this, "update");

        this.setBackground(new Color(0, 8, 27));
    }

    public void update() {
        updateBirds();
        repaint();
    }

    private void updateBirds() {
        field.advance(Settings.Instance.Bounce, !Settings.Instance.Bounce);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (Settings.Instance.DoGrid) {
            g.setColor(Settings.Instance.cDefaultBackground);
            for (int i = 0; i < Settings.Instance.Width; i += Settings.Instance.GridSize) {
                g.drawLine(i, 0, i, Settings.Instance.Height);
            }
            for (int j = 0; j < Settings.Instance.Height; j += Settings.Instance.GridSize) {
                g.drawLine(0, j, Settings.Instance.Width, j);
            }

        }

        BarrierManager.PreviewBarrier(g);

        for (Barrier barrier : Settings.Instance.Barriers) {
            if (barrier.modeIndex == 0)
                g.setColor(new Color(5, 31, 69));
            if (barrier.modeIndex == 1)
                g.setColor(new Color(20, 60, 100));

            if (barrier.shapeIndex == 0)
                g.fillRoundRect((int) barrier.Start.x, (int) barrier.Start.y,
                        (int) barrier.End.x - (int) barrier.Start.x,
                        (int) barrier.End.y - (int) barrier.Start.y, 10, 10);
            if (barrier.shapeIndex == 1)
                g.fillOval((int) barrier.Start.x, (int) barrier.Start.y,
                        (int) barrier.End.x - (int) barrier.Start.x,
                        (int) barrier.End.y - (int) barrier.Start.y);

        }

        for (Bird bird : Field.Birds) {
            bird.MoveBird(g);
        }

        ui.DrawButtons(g);
        birdViewer.DrawBirdViewer(g);

    }

}
