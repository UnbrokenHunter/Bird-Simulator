import java.util.ArrayList;
import java.util.Random;

public class Bird {

    public Vector2 Location;
    public Vector2 Velocity;
    public int Speed = 3;

    public Bird() {
        Random rand = new Random();
        Location = new Vector2(rand.nextInt(50, 650), rand.nextInt(50, 650));
        Velocity = new Vector2(rand.nextInt(-40, 40), rand.nextInt(-40, 40));
    }

    public void MoveForward(double minSpeed, double maxSpeed) {
        Location.x += Velocity.x;
        Location.y += Velocity.y;

        float speed = GetSpeed();
        if (speed > maxSpeed) {
            Velocity.x = (float) ((Velocity.x / speed) * maxSpeed);
            Velocity.y = (float) ((Velocity.y / speed) * maxSpeed);
        } else if (speed < minSpeed) {
            Velocity.x = (float) ((Velocity.x / speed) * minSpeed);
            Velocity.y = (float) ((Velocity.y / speed) * minSpeed);
        }

        if (Float.isNaN(Velocity.x))
            Velocity.x = 0;
        if (Float.isNaN(Velocity.y))
            Velocity.y = 0;
    }

    public float GetSpeed() {
        return (float) Math.sqrt(Velocity.x * Velocity.x + Velocity.y * Velocity.y);
    }

}
