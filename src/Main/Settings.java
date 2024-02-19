package Main;

import java.awt.Color;
import java.util.ArrayList;

import Barriers.Barrier;
import Boids.Bird;
import Boids.Field;
import Utilities.ColorInterpolator;
import Utilities.Vector2;

public class Settings {

    public static Settings Instance;

    public Settings() {
        Instance = this;
        System.out.println("New Settings Created");
    }

    public static void ResetSettings() {
        var Width = Settings.Instance.Width;
        var Height = Settings.Instance.Height;

        new Settings();

        Settings.Instance.Width = Width;
        Settings.Instance.Height = Height;

        Settings.Instance.SetBirdCount(Settings.Instance.BirdCount);
        Settings.Instance.SetPredatorCount(Settings.Instance.PredatorCount);
    }

    // Settings
    public int Width = 700;
    public int Height = 700;
    public boolean Bounce = false;

    // Counts
    public int BirdCount = 400;
    public int PredatorCount = 0;

    // Spawn
    public Vector2 SpawnPosition() {
        return new Vector2(Width / 2, Height / 2);
    }

    public boolean RandomSpawn = false;

    public Vector2 SpawnDirection() {
        return new Vector2(Width / 2, Height / 2);
    }

    public boolean RandomDirection = true;

    // Color
    public int ColorRadius = 50;
    public int NumberOfColors = 40;
    public Color[] ColorPalatte = ColorInterpolator.RAINBOW;
    public int ColorIndex = 0;
    public boolean DoFancyColor = true;
    public ColorInterpolator ColorInterp = new ColorInterpolator(ColorPalatte, NumberOfColors);

    // Balance
    public boolean CanReproduce = false;
    public double ReproductionChance = 5d;
    public boolean PredatorCanKill = false;

    // Barriers
    public boolean DoGrid = false;
    public int GridSize = 30;
    public int ShapeIndex = 0;
    public int ModeIndex = 0;

    // Speed
    public double MinSpeed = 2;
    public double MaxSpeed = 5;

    // Rules
    public double FlockPower = 0.0003;
    public double AlignPower = 0.01;
    public double AvoidPower = 0.001;
    public double PredatorPower = 0.00005;
    public double BarrierPower = 0.5;

    public double FlockDistance = 50;
    public double AlignDistance = 50;
    public double AvoidDistance = 20;
    public double PredatorDistance = 150;
    public double BarrierDistance = 30;
    public double KillDistance = 1;
    public double ReproductionDistance = 1;

    // Music
    public boolean MusicEnabled = false;
    public int MusicIndex = 0;
    public int[][] MusicScale = {
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
    public double BarrierSoundChance = 100d;
    public double SaftySoundChance = 75d;
    public double KillSoundChance = 75d;

    public double BarrierVolume = 0.8d;
    public double SaftyVolume = 0.8d;
    public double KillVolume = 0.8d;

    // Other
    public ArrayList<Barrier> Barriers = new ArrayList<Barrier>();
    public boolean BecomePredator;
    public boolean Pause = false;
    public int BirdInViewIndex = 0;

    // UI
    public Vector2 MouseClick = Vector2.zero;
    public Vector2 bButtonSize = new Vector2(50, 50);
    public Vector2 UIStartBounds = Vector2.zero;
    public Vector2 UISizeBounds = Vector2.zero;
    public Vector2 UIEndBounds = Vector2.zero;

    // UI Colors
    public Color cDefaultColor = new Color(48, 85, 122);
    public Color cDefaultBackground = new Color(23, 56, 90);
    public Color cGreen = new Color(114, 242, 192, 95);
    public Color cBlue = new Color(114, 167, 242, 95);
    public Color cRed = new Color(245, 128, 144, 96);
    public Color cYellow = new Color(245, 245, 140, 96);
    public Color cOrange = new Color(244, 209, 139, 96);
    public Color cPink = new Color(230, 153, 247, 97);
    public Color cPurple = new Color(175, 173, 247, 97);
    public Color cMagenta = new Color(231, 133, 244, 96);

    // Helpers
    public void BecomePredatorHelper(boolean change) {
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

    public void SetBirdCount(int count) {
        var difference = BirdCount - Field.Birds.size();

        UpdateBirdCount(difference);
    }

    public void UpdateBirdCount(int difference) {
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

    public void SetPredatorCount(int count) {
        for (Bird bird : Field.Birds) {
            bird.isPredator = false;
        }

        UpdatePredatorCount(count);
    }

    public void UpdatePredatorCount(int difference) {
        if (PredatorCount + difference < 0)
            return;
        if (difference == 0)
            return;

        PredatorCount += difference;
        System.out.println("Predator Count: " + PredatorCount);
        Field.Birds.get(PredatorCount).isPredator = false;
    }

    public void SetNextColor(int difference) {
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

    public void SetNextBarrierMode(int difference) {
        ModeIndex += difference;

        if (ModeIndex > 1)
            ModeIndex = 0;
        if (ModeIndex < 0)
            ModeIndex = 1;
    }

    public void SetNextShape(int difference) {
        ShapeIndex += difference;

        if (ShapeIndex > 1)
            ShapeIndex = 0;
        if (ShapeIndex < 0)
            ShapeIndex = 1;
    }

}
