import java.awt.Color;
import java.util.ArrayList;

public class Settings {

    // Settings
    public static int Width = 700;
    public static int Height = 700;
    public static boolean Bounce = false;

    public static int BirdCount = 400;
    public static int PredatorCount = 0;

    public static Vector2 SpawnPosition() {
        return new Vector2(Width / 2, Height / 2);
    }

    public static boolean RandomSpawn = false;

    public static Vector2 SpawnDirection() {
        return new Vector2(Width / 2, Height / 2);
    }

    public static boolean RandomDirection = true;

    public static ColorInterpolator ColorInterp;
    public static int ColorRadius = 50;
    public static int NumberOfColors = 40;
    public static Color[] ColorPalatte = ColorInterpolator.RAINBOW;
    public static boolean DoFancyColor = true;

    public static double MinSpeed = 2;
    public static double MaxSpeed = 5;

    // Rules
    public static double FlockPower = 0.0003;
    public static double AlignPower = 0.01;
    public static double AvoidPower = 0.001;
    public static double PredatorPower = 0.00005;
    public static double BarrierPower = 0.5;

    public static double FlockDistance = 50;
    public static double AlignDistance = 50;
    public static double AvoidDistance = 20;
    public static double PredatorDistance = 150;
    public static double BarrierDistance = 30;

    // Other
    public static ArrayList<Barrier> Barriers = new ArrayList<Barrier>();
    public static boolean BecomePredator;
    public static boolean Pause = false;
    public static int BirdInViewIndex = 0;

    // UI
    public static Vector2 bButtonSize = new Vector2(45, 45);
    public static int bButtonPositionYStart = 0;

    // Helpers
    public static void BecomePredatorHelper() {
        if (!BecomePredator) {
            BecomePredator = true;
            UpdatePredatorCount(1);
        } else {
            Field.Birds.removeFirst();
            BecomePredator = false;
            UpdatePredatorCount(-1);
        }
    }

    public static void UpdateBirdCount(int difference) {
        if (BirdCount + difference < 0)
            return;

        BirdCount += difference;

        for (int i = 0; i < Math.abs(difference); i++) {
            if (difference > 0) {
                Bird bird = new Bird();
                bird.Name = "bird" + i + Field.Birds.size();
                Field.Birds.add(bird);
            } else {
                Field.Birds.removeLast();

            }
        }
    }

    public static void UpdatePredatorCount(int difference) {
        if (PredatorCount + difference < 0)
            return;

        PredatorCount += difference;
        Field.Birds.get(PredatorCount).isPredator = !Field.Birds.get(PredatorCount).isPredator;
    }

    private static int colorIndex = 0;

    public static void SetNextColor() {
        var pallates = ColorInterpolator.getPalettes();
        colorIndex++;

        if (colorIndex > pallates.size())
            colorIndex = 0;

        int count = 0;
        for (Color[] colors : pallates.values()) {

            System.out.println("Index: " + count + ", Color Index: " + colorIndex);
            if (count == colorIndex) {
                ColorInterp.SetColors(colors);
                return;
            }
            count++;
        }

        System.out.println("Returning Default");

        ColorInterp.SetColors(ColorInterpolator.STANDARD);

        ColorPalatte = ColorInterp.GetColors();
    }
}
