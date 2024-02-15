package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Panel extends JPanel {

    private Looper looper;
    private Field field;
    private UI ui;
    private BirdViewer birdViewer;

    public Panel() {
        ui = new UI();
        birdViewer = new BirdViewer();
        Settings.ColorInterp = new ColorInterpolator(Settings.ColorPalatte, Settings.NumberOfColors);
        field = new Field(Settings.BirdCount);
        looper = new Looper(this, "update");

        this.setBackground(new Color(0, 8, 27));
    }

    public void update() {
        updateBirds();
        repaint();
    }

    private void updateBirds() {
        field.advance(Settings.Bounce, !Settings.Bounce);
    }

    private void MoveBird(Bird bird, Graphics g) {
        if (bird == null)
            return;

        Vector2 location = new Vector2((float) bird.X, (float) bird.Y);
        Vector2 direction = new Vector2((float) bird.Xvel, (float) bird.Yvel).normalize();
        double angle = Math.toRadians(Math.atan2(direction.y, direction.x)); // Angle in radians

        Vector2 front = location;
        Vector2 backLeft = Utilities.rotatePoints(15, location, angle + 0.5f);
        Vector2 backRight = Utilities.rotatePoints(15, location, angle - 0.5f);

        // Trail Calculation
        if (bird.isPredator) {
            bird.Tick++;
            if (bird.Tick > bird.MaxTick && !Settings.Pause) {
                bird.Tick = 0;
                for (int i = bird.LastPosition.length - 1; i > 0; i--) {
                    bird.LastPosition[i] = bird.LastPosition[i - 1];
                }
                bird.LastPosition[0] = new Vector2((int) bird.X, (int) bird.Y);
            }
            g.setColor(Color.red);
        }

        if (bird.isPredator)
            DrawTrail(g, bird);

        // Draw Colors
        if (Settings.DoFancyColor) {
            bird.inRadius = field.birdsInRadius(bird, Settings.ColorRadius);
            bird.color = Settings.ColorInterp.getColor(bird.inRadius, Settings.NumberOfColors);
            g.setColor(bird.color);
        } else
            g.setColor(Color.white);

        if (bird.isPredator)
            g.setColor(Color.red);

        // Draw Lines
        g.drawLine((int) front.x, (int) front.y, (int) backLeft.x, (int) backLeft.y);
        g.drawLine((int) backLeft.x, (int) backLeft.y, (int) backRight.x, (int) backRight.y);
        g.drawLine((int) backRight.x, (int) backRight.y, (int) front.x, (int) front.y);

        // Draw Polygons
        int[] xPoints = { (int) front.x, (int) backLeft.x, (int) backRight.x };
        int[] yPoints = { (int) front.y, (int) backLeft.y, (int) backRight.y };

        g.fillPolygon(xPoints, yPoints, 3);
    }

    private void DrawTrail(Graphics g, Bird bird) {
        Graphics2D g2 = (Graphics2D) g;

        float threshold = 500;

        g2.setColor(Settings.cDefaultColor);
        for (int i = 2; i < bird.LastPosition.length - 1; i++) {

            Vector2 first = bird.LastPosition[i];
            Vector2 second = bird.LastPosition[i + 1];

            g2.setStroke(new BasicStroke((bird.LastPosition.length - i) / 2));

            // Left Wall
            if (Math.abs(second.x - first.x) > Math.abs(Settings.Width - second.x)
                    && Math.abs(second.x - first.x) > threshold) {
                g2.drawLine((int) Settings.Width,
                        (int) first.y,
                        (int) second.x,
                        (int) second.y);

                g2.drawLine((int) first.x,
                        (int) first.y,
                        (int) 0,
                        (int) second.y);

            }
            // Right Wall
            else if (Math.abs(second.x - first.x) < Math.abs(0 - first.x)
                    && Math.abs(second.x - first.x) > threshold) {
                g2.drawLine((int) 0,
                        (int) first.y,
                        (int) second.x,
                        (int) second.y);

                g2.drawLine((int) first.x,
                        (int) first.y,
                        (int) Settings.Width,
                        (int) second.y);
            }
            // Down Wall
            else if (Math.abs(second.y - first.y) > Math.abs(Settings.Height - second.y)
                    && Math.abs(second.y - first.y) > threshold) {
                g2.drawLine((int) first.x,
                        (int) Settings.Height,
                        (int) second.x,
                        (int) second.y);

                g2.drawLine((int) first.x,
                        (int) first.y,
                        (int) second.x,
                        (int) 0);
            }
            // Up Wall
            else if (Math.abs(second.y - first.y) < Math.abs(0 - first.y)
                    && Math.abs(second.y - first.y) > threshold) {
                g2.drawLine((int) first.x,
                        (int) 0,
                        (int) second.x,
                        (int) second.y);

                g2.drawLine((int) first.x,
                        (int) first.y,
                        (int) second.x,
                        (int) Settings.Height);
            }

            else
                g2.drawLine((int) first.x,
                        (int) first.y,
                        (int) second.x,
                        (int) second.y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (Settings.DoGrid) {
            g.setColor(Settings.cDefaultBackground);
            for (int i = 0; i < Settings.Width; i += Settings.GridSize) {
                g.drawLine(i, 0, i, Settings.Height);
            }
            for (int j = 0; j < Settings.Height; j += Settings.GridSize) {
                g.drawLine(0, j, Settings.Width, j);
            }

        }

        BarrierManager.PreviewBarrier(g);

        for (Barrier barrier : Settings.Barriers) {
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
            MoveBird(bird, g);
        }

        ui.DrawButtons(g);
        birdViewer.DrawBirdViewer(g);

    }

}
