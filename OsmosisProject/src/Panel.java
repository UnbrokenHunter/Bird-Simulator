
import java.awt.Graphics;
import javax.swing.JPanel;

import java.awt.Color;

public class Panel extends JPanel {

    Looper looper;
    BirdManager manager;

    public Panel() {
        manager = new BirdManager();
        BirdManager.CreateBirdManager();
        looper = new Looper(this, "update");
    }

    public void update() {
        updateBirds();
        repaint();
    }

    private void updateBirds() {

        manager.Advance(true, false);

        for (Bird bird : BirdManager.Birds) {
            bird.Location = Vector2.add(bird.Location,
                    bird.Velocity.mult(bird.Speed).mult((float) Looper.getDeltaTime()));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Bird bird : BirdManager.Birds) {

            Vector2 loc = bird.Location;
            Vector2 dir = bird.Velocity.normalize();
            double angle = Math.toRadians(Math.atan2(-dir.y, -dir.x)); // Angle in radians

            Vector2 front = bird.Location;
            Vector2 back = rotatePoints(8, loc, angle);
            Vector2 backLeft = rotatePoints(8, loc, angle + 1);
            Vector2 backRight = rotatePoints(8, loc, angle - 1);

            // System.out.println(loc + " " + backLeft + " Angle: " + angle);

            // g.setColor(Color.red);
            // g.drawOval((int) front.x - 3, (int) front.y - 3, 6, 6);

            g.setColor(Color.black);
            g.drawLine((int) front.x, (int) front.y, (int) backLeft.x, (int) backLeft.y);
            g.drawLine((int) backLeft.x, (int) backLeft.y, (int) backRight.x, (int) backRight.y);
            g.drawLine((int) backRight.x, (int) backRight.y, (int) front.x, (int) front.y);

            g.drawLine((int) front.x, (int) front.y, (int) back.x, (int) back.y);
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
