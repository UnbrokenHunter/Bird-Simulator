package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
            kill(bird, Settings.KillDistance);
            reproduce(bird, Settings.ReproductionDistance);

            if (bird != null) {
                bird.Xvel += flock[0] + avoid[0] + align[0] + predator[0];
                bird.Yvel += flock[1] + avoid[1] + align[1] + predator[1];
            }
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

        for (int index : toRemove) {
            if (!Birds.get(index).isPredator) {
                System.out.println("Removed: " + Birds.get(index));
                Settings.BirdCount--;
                Birds.remove(index);

                if (random.nextDouble(0, 1) < Settings.BarrierSoundChance / 100d)
                    Main.sound.PlayDrums();
            }
        }
        toRemove.clear();

        for (int index : toAdd) {
            Bird bird = Birds.get(index);

            System.out.println("Added: " + Birds.get(index));
            Settings.BirdCount++;

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

    private void kill(Bird bird, double distance) {
        if (!Settings.PredatorCanKill)
            return;

        for (int i = 0; i < Settings.PredatorCount; i++) {
            Bird predator = Birds.get(i);

            double distanceAway = bird.getDistance(predator);
            if (distanceAway < distance) {
                int index = Birds.indexOf(bird);
                toRemove.add(index);
            }
        }
    }

    private void reproduce(Bird bird, double distance) {
        if (!Settings.CanReproduce)
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
