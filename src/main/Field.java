package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Field {
    public final static List<Bird> Birds = new ArrayList<>();

    public Field(int birdCount) {
        for (int i = 0; i < birdCount; i++) {
            Bird bird = new Bird();
            bird.Name = "bird" + i;
            Birds.add(bird);
        }
    }

    public static void Restart() {
        Birds.clear();

        for (int i = 0; i < Settings.BirdCount; i++) {
            Bird bird = new Bird();
            bird.Name = "bird" + i;
            Birds.add(bird);
        }

    }

    public int birdsInRadius(Bird bird, int radius) {
        int count = 0;

        for (Bird otherBird : Birds) {
            if (bird.getDistance(otherBird) < radius) {
                count++;
            }
        }

        return count;
    }

    public void advance(boolean bounceOffWalls, boolean wrapAroundEdges) {
        if (Settings.Pause)
            return;

        // Update bird speed and direction (velocity) based on rules
        Birds.forEach(bird -> {
            double[] flock = flock(bird, Settings.FlockDistance, Settings.FlockPower);
            double[] align = align(bird, Settings.AlignDistance, Settings.AlignPower);
            double[] avoid = avoid(bird, Settings.AvoidDistance, Settings.AvoidPower);
            double[] predator = predator(bird, Settings.PredatorDistance, Settings.PredatorPower);

            bird.Xvel += flock[0] + avoid[0] + align[0] + predator[0];
            bird.Yvel += flock[1] + avoid[1] + align[1] + predator[1];
        });

        // Move all birds forward in time
        Birds.forEach(bird -> {
            if (!(Settings.BecomePredator && Birds.getFirst().equals(bird))) {
                bird.moveForward(Settings.MinSpeed, Settings.MaxSpeed);

                bounceOffBarriers(bird);

                if (bounceOffWalls) {
                    bounceOffWalls(bird);
                }
                if (wrapAroundEdges) {
                    wrapAround(bird);
                }
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

        for (int i = 0; i < Settings.PredatorCount; i++) {
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

        if (bird.X < pad)
            bird.Xvel += Settings.BarrierPower;
        if (bird.X > Settings.Width - pad)
            bird.Xvel -= Settings.BarrierPower;
        if (bird.Y < pad)
            bird.Yvel += Settings.BarrierPower;
        if (bird.Y > Settings.Height - pad)
            bird.Yvel -= Settings.BarrierPower;
    }

    private void bounceOffBarriers(Bird bird) {
        for (Barrier barrier : Settings.Barriers) {
            var turn = barrier.CalculateTurnOnBarrier(bird);
            bird.Xvel += turn.x;
            bird.Yvel += turn.y;
        }
    }

    private void wrapAround(Bird bird) {
        if (bird.X < 0)
            bird.X += Settings.Width;
        if (bird.X > Settings.Width)
            bird.X -= Settings.Width;
        if (bird.Y < 0)
            bird.Y += Settings.Height;
        if (bird.Y > Settings.Height)
            bird.Y -= Settings.Height;
    }

}
