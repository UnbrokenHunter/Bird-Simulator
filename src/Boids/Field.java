package Boids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import Barriers.Barrier;
import Main.Main;
import Main.Settings;

public class Field {
    public final static List<Bird> Birds = new ArrayList<>();
    private ArrayList<Integer> toRemove = new ArrayList<Integer>();
    private ArrayList<Integer> toAdd = new ArrayList<Integer>();
    private Random random;

    public Field(int birdCount) {
        for (int i = 0; i < birdCount; i++) {
            Bird bird = new Bird();
            bird.Name = "bird" + i;
            Birds.add(bird);
        }

        random = new Random();
    }

    public static void Restart() {
        Birds.clear();

        for (int i = 0; i < Settings.Instance.BirdCount; i++) {
            Bird bird = new Bird();
            bird.Name = "bird" + i;
            Birds.add(bird);
        }

    }

    public static int birdsInRadius(Bird bird, int radius) {
        int count = 0;

        for (Bird otherBird : Birds) {
            if (bird.getDistance(otherBird) < radius) {
                count++;
            }
        }

        return count;
    }

    public void advance(boolean bounceOffWalls, boolean wrapAroundEdges) {
        if (Settings.Instance.Pause)
            return;

        // Update bird speed and direction (velocity) based on rules
        Birds.forEach(bird -> {
            double[] flock = flock(bird, Settings.Instance.FlockDistance, Settings.Instance.FlockPower);
            double[] align = align(bird, Settings.Instance.AlignDistance, Settings.Instance.AlignPower);
            double[] avoid = avoid(bird, Settings.Instance.AvoidDistance, Settings.Instance.AvoidPower);
            double[] predator = predator(bird, Settings.Instance.PredatorDistance, Settings.Instance.PredatorPower);
            kill(bird, Settings.Instance.KillDistance);
            reproduce(bird, Settings.Instance.ReproductionDistance);

            if (bird != null) {
                bird.Xvel += flock[0] + avoid[0] + align[0] + predator[0];
                bird.Yvel += flock[1] + avoid[1] + align[1] + predator[1];
            }
        });

        // Move all birds forward in time
        Birds.forEach(bird -> {
            if (!(Settings.Instance.BecomePredator && Birds.getFirst().equals(bird))) {
                bird.moveForward(Settings.Instance.MinSpeed, Settings.Instance.MaxSpeed);

                bounceOffBarriers(bird);

                if (bounceOffWalls) {
                    bounceOffWalls(bird);
                }
                if (wrapAroundEdges) {
                    wrapAround(bird);
                }
            }
        });

        for (int index : toRemove) {
            if (!Birds.get(index).isPredator) {
                System.out.println("Removed: " + Birds.get(index));
                Settings.Instance.BirdCount--;
                Birds.remove(index);

                if (random.nextDouble(0, 1) < Settings.Instance.BarrierSoundChance / 100d)
                    Main.sound.PlayDrums();
            }
        }
        toRemove.clear();

        for (int index : toAdd) {
            Bird bird = Birds.get(index);

            System.out.println("Added: " + Birds.get(index));
            Settings.Instance.BirdCount++;

            Birds.add(new Bird(bird.X, bird.Y, bird.Xvel, bird.Yvel));
        }
        toAdd.clear();
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

        for (int i = 0; i < Settings.Instance.PredatorCount; i++) {
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

    private void kill(Bird bird, double distance) {
        if (!Settings.Instance.PredatorCanKill)
            return;

        for (int i = 0; i < Settings.Instance.PredatorCount; i++) {
            Bird predator = Birds.get(i);

            double distanceAway = bird.getDistance(predator);
            if (distanceAway < distance) {
                int index = Birds.indexOf(bird);
                toRemove.add(index);
            }
        }
    }

    private void reproduce(Bird bird, double distance) {
        if (!Settings.Instance.CanReproduce)
            return;

        for (Bird otherBird : Birds) {
            double distanceAway = bird.getDistance(otherBird);

            if (distanceAway < distance && !bird.equals(otherBird)) {

                if (!bird.onCooldown && !otherBird.onCooldown) {
                    bird.onCooldown = true;
                    otherBird.onCooldown = true;

                    int index = Birds.indexOf(bird);
                    toAdd.add(index);
                }
            }
        }
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
            bird.Xvel += Settings.Instance.BarrierPower;
        if (bird.X > Settings.Instance.Width - pad)
            bird.Xvel -= Settings.Instance.BarrierPower;
        if (bird.Y < pad)
            bird.Yvel += Settings.Instance.BarrierPower;
        if (bird.Y > Settings.Instance.Height - pad)
            bird.Yvel -= Settings.Instance.BarrierPower;
    }

    private void bounceOffBarriers(Bird bird) {
        for (Barrier barrier : Settings.Instance.Barriers) {
            var turn = barrier.CalculateTurnOnBarrier(bird);
            bird.Xvel += turn.x;
            bird.Yvel += turn.y;
        }
    }

    private void wrapAround(Bird bird) {
        if (bird.X < 0)
            bird.X += Settings.Instance.Width;
        if (bird.X > Settings.Instance.Width)
            bird.X -= Settings.Instance.Width;
        if (bird.Y < 0)
            bird.Y += Settings.Instance.Height;
        if (bird.Y > Settings.Instance.Height)
            bird.Y -= Settings.Instance.Height;
    }

}
