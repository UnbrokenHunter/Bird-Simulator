package Barriers;

import java.util.ArrayList;
import java.util.Random;

import Boids.Bird;
import Main.Main;
import Main.Settings;
import Utilities.Vector2;

public class Barrier {

    public Vector2 Start;
    public Vector2 End;
    public int shapeIndex;
    public int modeIndex;
    private Random random;
    private ArrayList<Bird> birdsInBarrier = new ArrayList<Bird>();

    public Barrier(Vector2 start, Vector2 end, int shapeIndex, int modeIndex) {
        this.Start = start;
        this.End = end;
        this.shapeIndex = shapeIndex;
        this.modeIndex = modeIndex;

        random = new Random();
    }

    public Vector2 CalculateTurnOnBarrier(Bird bird) {
        Vector2 calculateTurn = new Vector2(0, 0);

        // Barrier
        if (modeIndex == 0) {

        }

        // Safty
        else if (modeIndex == 1) {
            if (!bird.isPredator)
                return Vector2.zero;
        }

        if (bird.X > Start.x - Settings.Instance.BarrierDistance
                && bird.X < End.x + Settings.Instance.BarrierDistance) {
            if (bird.Y > Start.y - Settings.Instance.BarrierDistance
                    && bird.Y < End.y + Settings.Instance.BarrierDistance) {

                if (bird.X < End.x)
                    calculateTurn.x -= Settings.Instance.BarrierPower;
                if (bird.X > Start.x)
                    calculateTurn.x += Settings.Instance.BarrierPower;
                if (bird.Y < End.y)
                    calculateTurn.y -= Settings.Instance.BarrierPower;
                if (bird.Y > Start.y)
                    calculateTurn.y += Settings.Instance.BarrierPower;

                if (random.nextDouble(0, 1) < Settings.Instance.BarrierSoundChance / Settings.Instance.BirdCount
                        && birdsInBarrier.contains(bird) == false) {
                    if (modeIndex == 0)
                        Main.sound.PlayPush();
                }
                if (random.nextDouble(0, 1) < Settings.Instance.BarrierSoundChance / 100d
                        && birdsInBarrier.contains(bird) == false) {
                    if (modeIndex == 1)
                        Main.sound.PlayBass();
                }

                birdsInBarrier.add(bird);
            } else
                birdsInBarrier.remove(bird);
        } else
            birdsInBarrier.remove(bird);

        return calculateTurn;
    }
}