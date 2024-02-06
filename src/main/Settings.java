package main;

import java.awt.Color;
import java.util.ArrayList;

public class Settings {

    // Settings
    public static int Width = 700;
    public static int Height = 700;
    public static boolean Bounce = false;

    // Counts
    public static int BirdCount = 400;
    public static int PredatorCount = 0;

    // Spawn
    public static Vector2 SpawnPosition() {
        return new Vector2(Width / 2, Height / 2);
    }

    public static boolean RandomSpawn = false;

    public static Vector2 SpawnDirection() {
        return new Vector2(Width / 2, Height / 2);
    }

    public static boolean RandomDirection = true;

    // Color
    public static ColorInterpolator ColorInterp;
    public static int ColorRadius = 50;
    public static int NumberOfColors = 40;
    public static Color[] ColorPalatte = ColorInterpolator.RAINBOW;
    public static int ColorIndex = 0;
    public static boolean DoFancyColor = true;

    // Speed
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

    // Music
    public static boolean PentatonicScale = true;
    public static boolean MajorScale = false;

    // Audio
    public static double BarrierSoundChance = 100d;

    // Other
    public static ArrayList<Barrier> Barriers = new ArrayList<Barrier>();
    public static boolean BecomePredator;
    public static boolean Pause = false;
    public static int BirdInViewIndex = 0;

    // UI
    public static Vector2 MouseClick = Vector2.zero;
    public static Vector2 bButtonSize = new Vector2(50, 50);
    public static Vector2 UIStartBounds = Vector2.zero;
    public static Vector2 UISizeBounds = Vector2.zero;
    public static Vector2 UIEndBounds = Vector2.zero;

    // UI Colors
    public static Color cDefaultColor = new Color(48, 85, 122);
    public static Color cDefaultBackground = new Color(23, 56, 90);
    public static Color cGreen = new Color(114, 242, 192, 95);
    public static Color cBlue = new Color(114, 167, 242, 95);
    public static Color cRed = new Color(245, 128, 144, 96);
    public static Color cYellow = new Color(245, 245, 140, 96);
    public static Color cOrange = new Color(244, 209, 139, 96);
    public static Color cPink = new Color(230, 153, 247, 97);
    public static Color cPurple = new Color(175, 173, 247, 97);
    public static Color cMagenta = new Color(231, 133, 244, 96);

    // Helpers
    public static void SetScale(int scale) {
        if (scale == 0)
            return;

        if (scale == 1) {
            PentatonicScale = true;
            MajorScale = false;
        } else if (scale == 2) {
            PentatonicScale = false;
            MajorScale = true;
        }

    }

    public static void BecomePredatorHelper(boolean change) {
        if (!change)
            return;

        System.out.println("Become Predator: " + BecomePredator);

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

    public static void SetNextColor(int difference) {
        if (difference == 0)
            return;

        var pallates = ColorInterpolator.getPalettes();
        ColorIndex += difference;

        if (ColorIndex > pallates.size())
            ColorIndex = 0;

        if (ColorIndex < 0)
            ColorIndex = pallates.size();

        int count = 0;
        for (Color[] colors : pallates.values()) {

            System.out.println("Index: " + count + ", Color Index: " + ColorIndex);
            if (count == ColorIndex) {
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
