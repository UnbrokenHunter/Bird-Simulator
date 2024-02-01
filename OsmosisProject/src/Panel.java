
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Panel extends JPanel {

    private Looper looper;
    private Field field;
    private UI ui;

    public Panel() {
        ui = new UI();
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
        Vector2 location = new Vector2((float) bird.X, (float) bird.Y);
        Vector2 direction = new Vector2((float) bird.Xvel, (float) bird.Yvel).normalize();
        double angle = Math.toRadians(Math.atan2(direction.y, direction.x)); // Angle in radians

        Vector2 front = location;
        Vector2 backLeft = rotatePoints(15, location, angle + 0.5f);
        Vector2 backRight = rotatePoints(15, location, angle - 0.5f);

        // g.setColor(Color.red);
        // g.drawOval((int) front.x - 3, (int) front.y - 3, 6, 6);

        // Draw Colors
        if (Settings.DoFancyColor) {
            int inRadius = field.birdsInRadius(bird, Settings.ColorRadius);
            g.setColor(Settings.ColorInterp.getColor(inRadius, Settings.NumberOfColors));
        } else
            g.setColor(Color.white);

        // Color Predator
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Bird bird : Field.Birds) {
            MoveBird(bird, g);
        }

        ui.DrawButtons(g);

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
