package main;

import java.awt.Color;
import java.util.Random;

public class Bird {
    public String Name;

    public double X;
    public double Y;
    public double Xvel;
    public double Yvel;

    public boolean isPredator = false;

    public int inRadius = 0;
    public Color color;

    public Bird(double x, double y, double xVel, double yVel) {
        this.X = x;
        this.Y = y;
        this.Xvel = xVel;
        this.Yvel = yVel;
    }

    public Bird() {
        Random rand = new Random();

        if (Settings.RandomSpawn) {
            this.X = rand.nextDouble() * Settings.Width;
            this.Y = rand.nextDouble() * Settings.Height;
        } else {
            this.X = Settings.SpawnPosition().x;
            this.Y = Settings.SpawnPosition().y;
        }
        if (Settings.RandomDirection) {
            this.Xvel = rand.nextDouble() - 0.5;
            this.Yvel = rand.nextDouble() - 0.5;
        } else {
            this.Xvel = Settings.SpawnDirection().x;
            this.Yvel = Settings.SpawnDirection().y;
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
}
