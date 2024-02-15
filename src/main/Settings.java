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

    // Balance
    public static boolean CanReproduce = false;
    public static double ReproductionChance = 5d;
    public static boolean PredatorCanKill = false;

    // Barriers
    public static boolean DoGrid = false;
    public static int GridSize = 30;
    public static int ShapeIndex = 0;
    public static int ModeIndex = 0;

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
    public static double KillDistance = 1;
    public static double ReproductionDistance = 1;

    // Music
    public static boolean MusicEnabled = false;
    public static int MusicIndex = 0;
    public static int[][] MusicScale = {
            { 0, 200, 400, 700, 900, 1200 }, // Pentatonic
            { 0, 200, 400, 500, 700, 900, 1100, 1200 }, // Major
            { 0, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200 }, // Chromatic
            { 0, 240, 480, 720, 960, 1200 }, // 5 Tone
            { 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000,
                    1050, 1100, 1150, 1200 }, // Quartertone
            { 0, 75, 281, 356, 563, 637, 712, 919, 993, 1200 }, // Rank 2 17/10
            { 0, 300, 500, 600, 700, 1000, 1200 }, // Rank 2 17/10
    };

    // Audio
    public static double BarrierSoundChance = 100d;
    public static double SaftySoundChance = 75d;
    public static double KillSoundChance = 75d;

    public static double BarrierVolume = 0.8d;
    public static double SaftyVolume = 0.8d;
    public static double KillVolume = 0.8d;

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
        if (difference == 0)
            return;

        PredatorCount += difference;
        System.out.println("Predator Count: " + PredatorCount);
        Field.Birds.get(PredatorCount).isPredator = false;
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

    public static void SetNextBarrierMode(int difference) {
        ModeIndex += difference;

        if (ModeIndex > 1)
            ModeIndex = 0;
        if (ModeIndex < 0)
            ModeIndex = 1;
    }

    public static void SetNextShape(int difference) {
        ShapeIndex += difference;

        if (ShapeIndex > 1)
            ShapeIndex = 0;
        if (ShapeIndex < 0)
            ShapeIndex = 1;
    }

}
