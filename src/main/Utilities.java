package main;

public class Utilities {

    public static Vector2 rotatePoints(double distance, Vector2 location, double angle) {
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

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double Lerp(double n0, double n1, double a) {
        return (1.0 - a) * n0 + (a * n1);
    }

}
