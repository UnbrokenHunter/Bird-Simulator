import java.util.ArrayList;

public class BirdManager {

    public static Vector2 Size;

    public static Bird[] Birds;

    public static int BirdCount = 100;

    public static void CreateBirdManager() {
        System.out.println("Bird Manager Created");

        Birds = new Bird[BirdCount];

        for (int i = 0; i < BirdCount; i++) {

            Birds[i] = new Bird();
        }
    }

    public static ArrayList<Bird> NumberOfBirdsAround(Vector2 position, int range) {
        ArrayList<Bird> count = new ArrayList<Bird>();

        for (Bird bird : Birds) {
            Vector2 check = bird.Location;

            if (Math.abs(position.x - check.x) < range && Math.abs(position.y - check.y) < range) {
                count.add(bird);
            }
        }
        return count;
    }

    public static Bird FindNearestBird(Vector2 position) {
        double distance = Double.MAX_VALUE;
        Bird nearest = null;

        for (Bird bird : Birds) {
            if (Vector2.distance(position, bird.Location) < distance) {
                nearest = bird;
                distance = Vector2.distance(position, bird.Location);
            }
        }
        return nearest;
    }

    public void Advance(boolean bounceOffWalls, boolean wrapAroundEdges) {
        // update void speed and direction (velocity) based on rules
        for (Bird bird : BirdManager.Birds) {
            Vector2 flockVel = Flock(bird, 50, .0003);
            Vector2 alignVel = Align(bird, 50, .01);
            Vector2 avoidVel = Avoid(bird, 20, .001);
            Vector2 predVel = Predator(bird, 150, .00005);
            bird.Velocity.x += flockVel.x + avoidVel.x + alignVel.x + predVel.x;
            bird.Velocity.y += flockVel.y + avoidVel.y + alignVel.y + predVel.y;
        }

        // move all boids forward in time
        for (Bird bird : BirdManager.Birds) {
            bird.MoveForward(1, 5);
            if (bounceOffWalls)
                BounceOffWalls(bird);
            if (wrapAroundEdges)
                WrapAround(bird);
        }
    }

    private Vector2 Flock(Bird bird, double distance, double power) {
        ArrayList<Bird> neighbors = BirdManager.NumberOfBirdsAround(bird.Location, (int) distance);

        Vector2 mean = Vector2.zero;
        for (Bird neighbor : neighbors) {
            mean = mean.plus(neighbor.Location);
        }
        mean = mean.div(neighbors.size());

        float deltaCenterX = mean.x - bird.Location.x;
        float deltaCenterY = mean.y - bird.Location.y;
        Vector2 deltaCenter = new Vector2(deltaCenterX, deltaCenterY);

        return deltaCenter;
    }

    private Vector2 Align(Bird bird, double distance, double power) {
        ArrayList<Bird> neighbors = BirdManager.NumberOfBirdsAround(bird.Location, (int) distance);

        Vector2 meanVel = Vector2.zero;
        for (Bird neighbor : neighbors) {
            meanVel = meanVel.plus(neighbor.Velocity);
        }
        meanVel = meanVel.div(neighbors.size());

        float dXvel = meanVel.x - bird.Velocity.x;
        float dYvel = meanVel.y - bird.Velocity.y;
        Vector2 newVelocity = new Vector2(dXvel, dYvel);

        return newVelocity;
    }

    private Vector2 Avoid(Bird bird, double distance, double power) {
        ArrayList<Bird> neighbors = BirdManager.NumberOfBirdsAround(bird.Location, (int) distance);

        Vector2 sumCloseness = Vector2.zero;
        for (Bird neighbor : neighbors) {
            double closeness = distance - Vector2.distance(bird.Location, neighbor.Location);
            sumCloseness.x += (bird.Location.x - neighbor.Location.x) * closeness;
            sumCloseness.y += (bird.Location.y - neighbor.Location.y) * closeness;
        }
        return sumCloseness.mult((float) power);
    }

    private void BounceOffWalls(Bird bird) {
        double pad = 50;
        double turn = .5;
        if (bird.Location.x < pad)
            bird.Velocity.x += turn;
        if (bird.Location.x > Main.ScreenSizeX - pad)
            bird.Velocity.x -= turn;
        if (bird.Location.y < pad)
            bird.Velocity.y += turn;
        if (bird.Location.y > Main.ScreenSizeY - pad)
            bird.Velocity.y -= turn;
    }

    private void WrapAround(Bird bird) {
        if (bird.Location.x < 0)
            bird.Location.x += Main.ScreenSizeX;
        if (bird.Location.x > Main.ScreenSizeX)
            bird.Location.x -= Main.ScreenSizeX;
        if (bird.Location.y < 0)
            bird.Location.y += Main.ScreenSizeY;
        if (bird.Location.y > Main.ScreenSizeY)
            bird.Location.y -= Main.ScreenSizeY;
    }

    public int PredatorCount = 3;

    private Vector2 Predator(Bird bird, double distance, double power) {
        Vector2 sumCloseness = Vector2.zero;
        for (int i = 0; i < PredatorCount; i++) {
            Bird predator = BirdManager.Birds[i];
            double distanceAway = Vector2.distance(bird.Location, predator.Location);
            if (distanceAway < distance) {
                double closeness = distance - distanceAway;
                sumCloseness.x += (bird.Location.x - predator.Location.x) * closeness;
                sumCloseness.y += (bird.Location.y - predator.Location.y) * closeness;
            }
        }
        return sumCloseness.mult((float) power);
    }

}