
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Panel extends JPanel {

    private Looper looper;
    private Field field;
    private ColorInterpolator color;

    private int BirdCount = 600;
    private int ColorRadius = 5;
    private int NumberOfColors = 5;

    public Panel() {
        color = new ColorInterpolator(ColorInterpolator.EARTHY_GREENS, NumberOfColors);
        field = new Field(BirdCount);
        looper = new Looper(this, "update");
    }

    public void update() {
        updateBirds();
        repaint();
    }

    private void updateBirds() {
        field.advance(true, false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Bird bird : field.Birds) {

            Vector2 loc = new Vector2((float) bird.X, (float) bird.Y);
            Vector2 dir = new Vector2((float) bird.Xvel, (float) bird.Yvel).normalize();
            double angle = Math.toRadians(Math.atan2(dir.y, dir.x)); // Angle in radians

            Vector2 front = loc;
            Vector2 back = rotatePoints(15, loc, angle);
            Vector2 backLeft = rotatePoints(15, loc, angle + 0.5f);
            Vector2 backRight = rotatePoints(15, loc, angle - 0.5f);

            // System.out.println(loc + " " + backLeft + " Angle: " + angle);

            // g.setColor(Color.red);
            // g.drawOval((int) front.x - 3, (int) front.y - 3, 6, 6);

            g.setColor(color.getColor(field.birdsInRadius(bird, ColorRadius), NumberOfColors));

            if (bird.isPredator)
                g.setColor(Color.red);

            g.drawLine((int) front.x, (int) front.y, (int) backLeft.x, (int) backLeft.y);
            g.drawLine((int) backLeft.x, (int) backLeft.y, (int) backRight.x, (int) backRight.y);
            g.drawLine((int) backRight.x, (int) backRight.y, (int) front.x, (int) front.y);

            int[] xPoints = { (int) front.x, (int) backLeft.x, (int) backRight.x };
            int[] yPoints = { (int) front.y, (int) backLeft.y, (int) backRight.y };

            g.fillPolygon(xPoints, yPoints, 3);

            // g.setColor(Color.blue);
            // g.drawLine((int) front.x, (int) front.y, (int) back.x, (int) back.y);
        }
    }

    private Vector2 rotatePoints(double distance, Vector2 location, double angle) {
        angle = Math.toDegrees(angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        // Calculate new positions without immediately modifying the original rotate
        // object
        double newX = ((distance) * cos) + location.x;
        double newY = ((distance) * sin) + location.y;

        // Update the rotate object with the new values
        Vector2 newLocation = new Vector2((float) newX, (float) newY);

        return newLocation;
    }

}
