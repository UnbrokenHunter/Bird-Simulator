package Boids;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import Main.Settings;
import Utilities.Utilities;
import Utilities.Vector2;

public class Bird {
    public String Name;

    public double X;
    public double Y;
    public double Xvel;
    public double Yvel;

    public boolean isPredator = false;

    public int inRadius = 0;
    public Color color;

    public Vector2[] LastPosition = {
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
            Vector2.zero,
    };

    public int MaxTick = 0;
    public int Tick = 0;
    public boolean onCooldown = true;
    private int cooldownTick = 0;
    private int maxCooldownTock = 1000;

    public Bird(double x, double y, double xVel, double yVel) {
        this.X = x;
        this.Y = y;
        this.Xvel = xVel;
        this.Yvel = yVel;
    }

    public Bird() {
        Random rand = new Random();

        if (Settings.Instance.RandomSpawn) {
            this.X = rand.nextDouble() * Settings.Instance.Width;
            this.Y = rand.nextDouble() * Settings.Instance.Height;
        } else {
            this.X = Settings.Instance.SpawnPosition().x;
            this.Y = Settings.Instance.SpawnPosition().y;
        }
        if (Settings.Instance.RandomDirection) {
            this.Xvel = rand.nextDouble() - 0.5;
            this.Yvel = rand.nextDouble() - 0.5;
        } else {
            this.Xvel = Settings.Instance.SpawnDirection().x;
            this.Yvel = Settings.Instance.SpawnDirection().y;
        }
    }

    public void MoveBird(Graphics g) {
        Vector2 location = new Vector2((float) X, (float) Y);
        Vector2 direction = new Vector2((float) Xvel, (float) Yvel).normalize();
        double angle = Math.toRadians(Math.atan2(direction.y, direction.x)); // Angle in radians

        Vector2 front = location;
        Vector2 backLeft = Utilities.rotatePoints(15, location, angle + 0.5f);
        Vector2 backRight = Utilities.rotatePoints(15, location, angle - 0.5f);

        // Trail Calculation
        if (isPredator) {
            Tick++;
            if (Tick > MaxTick && !Settings.Instance.Pause) {
                Tick = 0;
                for (int i = LastPosition.length - 1; i > 0; i--) {
                    LastPosition[i] = LastPosition[i - 1];
                }
                LastPosition[0] = new Vector2((int) X, (int) Y);
            }
            g.setColor(Color.red);
        }

        if (isPredator)
            DrawTrail(g);

        // Draw Colors
        if (Settings.Instance.DoFancyColor) {
            inRadius = Field.birdsInRadius(this, Settings.Instance.ColorRadius);
            color = Settings.Instance.ColorInterp.getColor(inRadius, Settings.Instance.NumberOfColors);
            g.setColor(color);
        } else
            g.setColor(Color.white);

        if (isPredator)
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

    private void DrawTrail(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        float threshold = 500;

        g2.setColor(Settings.Instance.cDefaultColor);
        for (int i = 2; i < LastPosition.length - 1; i++) {

            Vector2 first = LastPosition[i];
            Vector2 second = LastPosition[i + 1];

            if (Vector2.distance(LastPosition[0], first) < 15)
                continue;

            var size = (LastPosition.length - i) / 3;
            g2.setStroke(new BasicStroke(size));

            // Left Wall
            if (Math.abs(second.x - first.x) > Math.abs(Settings.Instance.Width - second.x)
                    && Math.abs(second.x - first.x) > threshold) {
                g2.drawLine((int) Settings.Instance.Width,
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
                        (int) Settings.Instance.Width,
                        (int) second.y);
            }
            // Down Wall
            else if (Math.abs(second.y - first.y) > Math.abs(Settings.Instance.Height - second.y)
                    && Math.abs(second.y - first.y) > threshold) {
                g2.drawLine((int) first.x,
                        (int) Settings.Instance.Height,
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
                        (int) Settings.Instance.Height);
            }

            else {
                g2.drawLine((int) first.x,
                        (int) first.y,
                        (int) second.x,
                        (int) second.y);
                // g2.fillOval((int) first.x - size / 2, (int) first.y - size / 2, (int) size,
                // (int) size);
            }
        }
    }

    public void moveForward(double minSpeed, double maxSpeed) {
        this.X += this.Xvel;
        this.Y += this.Yvel;

        double speed = getSpeed();
        if (speed > maxSpeed) {
            this.Xvel = (this.Xvel / speed) * maxSpeed;
            this.Yvel = (this.Yvel / speed) * maxSpeed;
        } else if (speed < minSpeed) {
            this.Xvel = (this.Xvel / speed) * minSpeed;
            this.Yvel = (this.Yvel / speed) * minSpeed;
        }

        if (Double.isNaN(this.Xvel))
            this.Xvel = 0;
        if (Double.isNaN(this.Yvel))
            this.Yvel = 0;

        if (onCooldown) {
            cooldownTick += 1;

            if (cooldownTick > maxCooldownTock) {
                onCooldown = false;
                cooldownTick = 0;
            }
        }
    }

    public double[] getPosition(double time) {
        return new double[] { this.X + this.Xvel * time, this.Y + this.Yvel * time };
    }

    public void accelerate(double scale) {
        this.Xvel *= scale;
        this.Yvel *= scale;
    }

    public double getAngle() {
        if (Double.isNaN(this.Xvel) || Double.isNaN(this.Yvel))
            return 0;
        if (this.Xvel == 0 && this.Yvel == 0)
            return 0;

        double angle = Math.atan2(this.Yvel, this.Xvel) * 180 / Math.PI;
        return angle;
    }

    public double getSpeed() {
        return Math.sqrt(this.Xvel * this.Xvel + this.Yvel * this.Yvel);
    }

    public double getDistance(Bird otherBoid) {
        double dX = otherBoid.X - this.X;
        double dY = otherBoid.Y - this.Y;
        return Math.sqrt(dX * dX + dY * dY);
    }

    public double getDistance(Vector2 otherPosition) {
        double dX = otherPosition.x - this.X;
        double dY = otherPosition.y - this.Y;
        return Math.sqrt(dX * dX + dY * dY);
    }

}
