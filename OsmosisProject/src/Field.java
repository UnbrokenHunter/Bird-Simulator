import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Field {
    public final List<Bird> Birds = new ArrayList<>();
    private final Random Rand = new Random();
    public int PredatorCount = 3;

    private double MinSpeed = 1;
    private double MaxSpeed = 5;

    public Field(int birdCount) {
        for (int i = 0; i < birdCount; i++) {
            this.Birds.add(new Bird(Rand));
        }
    }

    public int birdsInRadius(Bird bird, int radius) {

        int count = 0;

        for (Bird otherBird : Birds) {
            if (bird.getDistance(otherBird) > radius) {
                count++;
            }
        }

        return count;
    }

    public void advance(boolean bounceOffWalls, boolean wrapAroundEdges) {
        // Update bird speed and direction (velocity) based on rules
        Birds.forEach(bird -> {
            double[] flock = flock(bird, 50, 0.0003);
            double[] align = align(bird, 50, 0.01);
            double[] avoid = avoid(bird, 20, 0.001);
            double[] predator = predator(bird, 120, 0.00005);

            bird.Xvel += flock[0] + avoid[0] + align[0] + predator[0];
            bird.Yvel += flock[1] + avoid[1] + align[1] + predator[1];
        });

        // Move all birds forward in time
        Birds.forEach(bird -> {
            bird.moveForward(MinSpeed, MaxSpeed);
            if (bounceOffWalls) {
                bounceOffWalls(bird);
            }
            if (wrapAroundEdges) {
                wrapAround(bird);
            }
        });
    }

    private double[] flock(Bird bird, double distance, double power) {
        List<Bird> neighbors = Birds.stream()
                .filter(x -> x.getDistance(bird) < distance)
                .collect(Collectors.toList());

        double meanX = neighbors.stream().mapToDouble(x -> x.X).average().orElse(0);
        double meanY = neighbors.stream().mapToDouble(x -> x.Y).average().orElse(0);

        double deltaCenterX = meanX - bird.X;
        double deltaCenterY = meanY - bird.Y;

        return new double[] { deltaCenterX * power, deltaCenterY * power };
    }

    private double[] avoid(Bird bird, double distance, double power) {
        final double[] sumCloseness = { 0, 0 };

        Birds.stream()
                .filter(x -> x.getDistance(bird) < distance)
                .forEach(neighbor -> {
                    double closeness = distance - bird.getDistance(neighbor);
                    sumCloseness[0] += (bird.X - neighbor.X) * closeness;
                    sumCloseness[1] += (bird.Y - neighbor.Y) * closeness;
                });

        return new double[] { sumCloseness[0] * power, sumCloseness[1] * power };
    }

    private double[] predator(Bird bird, double distance, double power) {
        final double[] sumCloseness = { 0, 0 };

        for (int i = 0; i < PredatorCount; i++) {
            Bird predator = Birds.get(i);
            predator.isPredator = true;
            double distanceAway = bird.getDistance(predator);
            if (distanceAway < distance) {
                double closeness = distance - distanceAway;
                sumCloseness[0] += (bird.X - predator.X) * closeness;
                sumCloseness[1] += (bird.Y - predator.Y) * closeness;
            }
        }

        return new double[] { sumCloseness[0] * power, sumCloseness[1] * power };
    }

    private double[] align(Bird bird, double distance, double power) {
        List<Bird> neighbors = Birds.stream()
                .filter(x -> x.getDistance(bird) < distance)
                .collect(Collectors.toList());

        double meanXvel = neighbors.stream().mapToDouble(x -> x.Xvel).average().orElse(0);
        double meanYvel = neighbors.stream().mapToDouble(x -> x.Yvel).average().orElse(0);

        double dXvel = meanXvel - bird.Xvel;
        double dYvel = meanYvel - bird.Yvel;

        return new double[] { dXvel * power, dYvel * power };
    }

    private void bounceOffWalls(Bird bird) {
        double pad = 50;
        double turn = 0.5;

        if (bird.X < pad)
            bird.Xvel += turn;
        if (bird.X > Main.Width - pad)
            bird.Xvel -= turn;
        if (bird.Y < pad)
            bird.Yvel += turn;
        if (bird.Y > Main.Height - pad)
            bird.Yvel -= turn;
    }

    private void wrapAround(Bird bird) {
        if (bird.X < 0)
            bird.X += Main.Width;
        if (bird.X > Main.Width)
            bird.X -= Main.Width;
        if (bird.Y < 0)
            bird.Y += Main.Height;
        if (bird.Y > Main.Height)
            bird.Y -= Main.Height;
    }

}
