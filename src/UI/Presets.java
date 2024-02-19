package UI;

import Barriers.BarrierManager;
import Main.Settings;
import Utilities.Vector2;

public class Presets {

    public static void Default(boolean setPreset) {
        if (!setPreset)
            return;

        Settings.ResetSettings();

    }

    public static void Organized(boolean setPreset) {
        if (!setPreset)
            return;

        Settings.ResetSettings();

        Settings.Instance.FlockPower = 0.0024;
        Settings.Instance.AlignPower = 0.18;
        Settings.Instance.AvoidPower = 0.009;

        Settings.Instance.PredatorPower = 0.0014;

        Settings.Instance.FlockDistance = 170;
        Settings.Instance.AlignDistance = 110;

        Settings.Instance.NumberOfColors = 140;
    }

    public static void Music(boolean setPreset) {
        if (!setPreset)
            return;

        Settings.ResetSettings();

        Settings.Instance.MusicEnabled = true;

        Settings.Instance.UpdatePredatorCount(5);

        Settings.Instance.PredatorCanKill = true;

        // Barriers
        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(90.0f, 90.0f), new Vector2(180.0f, 180.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(270.0f, 90.0f), new Vector2(360.0f, 180.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(450.0f, 90.0f), new Vector2(540.0f, 180.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(90.0f, 270.0f), new Vector2(180.0f, 360.0f));

        Settings.Instance.ModeIndex = 1;
        Settings.Instance.ShapeIndex = 1;
        BarrierManager.CreateBarrier(new Vector2(270.0f, 270.0f), new Vector2(360.0f, 360.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(450.0f, 270.0f), new Vector2(540.0f, 360.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(90.0f, 450.0f), new Vector2(180.0f, 540.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(270.0f, 450.0f), new Vector2(360.0f, 540.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(450.0f, 450.0f), new Vector2(540.0f, 540.0f));
    }

    public static void Maze(boolean setPreset) {
        if (!setPreset)
            return;

        Settings.ResetSettings();

        Settings.Instance.Bounce = true;

        // Barriers
        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(30.0f, 30.0f), new Vector2(600.0f, 60.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(660.0f, 0.0f), new Vector2(690.0f, 30.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(30.0f, 120.0f), new Vector2(60.0f, 630.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(120.0f, 120.0f), new Vector2(150.0f, 540.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(120.0f, 600.0f), new Vector2(540.0f, 630.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(210.0f, 210.0f), new Vector2(240.0f, 630.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(210.0f, 120.0f), new Vector2(450.0f, 150.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(300.0f, 120.0f), new Vector2(330.0f, 540.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(300.0f, 210.0f), new Vector2(540.0f, 240.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(390.0f, 300.0f), new Vector2(420.0f, 630.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(510.0f, 30.0f), new Vector2(540.0f, 150.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(660.0f, 0.0f), new Vector2(690.0f, 30.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(480.0f, 210.0f), new Vector2(510.0f, 540.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(540.0f, 30.0f), new Vector2(630.0f, 60.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(600.0f, 30.0f), new Vector2(630.0f, 540.0f));

        Settings.Instance.ModeIndex = 0;
        Settings.Instance.ShapeIndex = 0;
        BarrierManager.CreateBarrier(new Vector2(630.0f, 0.0f), new Vector2(660.0f, 30.0f));

    }

}
