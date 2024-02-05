import java.awt.Color;
import java.awt.Graphics;

public class UI {

    private Vector2 position = Vector2.zero;

    private boolean HideUI = false;
    private boolean BirdsUI = false;
    private boolean ColorUI = false;
    private boolean PowerUI = false;
    private boolean DistanceUI = false;
    private boolean HawksUI = false;
    private boolean BarrierUI = false;

    public void DrawButtons(Graphics g) {

        position = new Vector2(Settings.Width - Settings.bButtonSize.x * 1.3f - 5, 5f);

        if (HideUI)
            HideUI(g);
        else if (BirdsUI)
            BirdsUI(g);
        else if (ColorUI)
            ColorUI(g);
        else if (PowerUI)
            PowerUI(g);
        else if (DistanceUI)
            DistanceUI(g);
        else if (HawksUI)
            HawksUI(g);
        else if (BarrierUI)
            BarrierUI(g);
        else
            GeneralUI(g);

        UIUtilities.EndGroup();
    }

    private void GeneralUI(Graphics g) {

        UIUtilities.StartGroup(g, position, new Vector2(0, 54), 9);

        HideUI = UIUtilities.DrawButton(g, "Hide", BirdsUI, false);
        Settings.Pause = UIUtilities.DrawButton(g, "Pause", Settings.Pause, false, Settings.cGreen);
        if (UIUtilities.DrawButton(g, "Restart", false, false, Settings.cPink))
            Field.Restart();
        BirdsUI = UIUtilities.DrawButton(g, "Birds", BirdsUI, false, Settings.cMagenta);
        ColorUI = UIUtilities.DrawButton(g, "Colors", ColorUI, false, Settings.cBlue);
        PowerUI = UIUtilities.DrawButton(g, "Power", PowerUI, false, Settings.cPurple);
        DistanceUI = UIUtilities.DrawButton(g, "Distance", DistanceUI, false, Settings.cOrange);
        HawksUI = UIUtilities.DrawButton(g, "Hawks", HawksUI, false, Settings.cYellow);
        BarrierUI = UIUtilities.DrawButton(g, "Barriers", BarrierUI, false, Settings.cRed);
    }

    private void HideUI(Graphics g) {
        UIUtilities.StartGroup(g, position, new Vector2(0, 54), 1);

        HideUI = UIUtilities.DrawButton(g, "Show", HideUI, false);
    }

    private void BirdsUI(Graphics g) {
        UIUtilities.StartGroup(g, position, new Vector2(0, 54), 3);

        ReturnToMainUI(UIUtilities.DrawButton(g, "Return",
                false, false));

        // Birds
        Settings.Bounce = UIUtilities.DrawButton(g, "Bounce", Settings.Bounce, Settings.cGreen);
        Settings.UpdateBirdCount(
                (int) UIUtilities.DrawSwitch(g, "Bird Count", Settings.BirdCount, 100, Settings.cBlue));
    }

    private void ColorUI(Graphics g) {
        UIUtilities.StartGroup(g, position, new Vector2(0, 54), 4);

        ReturnToMainUI(UIUtilities.DrawButton(g, "Return",
                false, false));

        // Colors
        Settings.SetNextColor((int) UIUtilities.DrawSwitch(g, "Next Color",
                Settings.ColorIndex, 1, Settings.cRed));
        Settings.NumberOfColors += (int) UIUtilities.DrawSwitch(g, "Num Colors",
                Settings.NumberOfColors, 10, Settings.cPink);
        Settings.DoFancyColor = UIUtilities.DrawButton(g, "Fancy Colors",
                Settings.DoFancyColor, Settings.cMagenta);
    }

    private void PowerUI(Graphics g) {
        UIUtilities.StartGroup(g, position, new Vector2(0, 54), 6);

        ReturnToMainUI(UIUtilities.DrawButton(g, "Return",
                false, false));

        // Power
        Settings.FlockPower += (int) UIUtilities.DrawSwitch(g, "Flock",
                Settings.FlockPower, 0.0001, Settings.cPink);
        Settings.AlignPower += (int) UIUtilities.DrawSwitch(g, "Align",
                Settings.AlignPower, 0.01, Settings.cMagenta);
        Settings.AvoidPower += (int) UIUtilities.DrawSwitch(g, "Avoid",
                Settings.AvoidPower, 0.001, Settings.cOrange);
        Settings.PredatorPower += (int) UIUtilities.DrawSwitch(g, "Fear",
                Settings.PredatorPower, 0.1, Settings.cYellow);
        Settings.BarrierPower += (int) UIUtilities.DrawSwitch(g, "Barrier",
                Settings.BarrierPower, 0.0001, Settings.cPink);
    }

    private void DistanceUI(Graphics g) {
        UIUtilities.StartGroup(g, position, new Vector2(0, 54), 6);

        ReturnToMainUI(UIUtilities.DrawButton(g, "Return",
                false, false));

        // Distance
        Settings.FlockDistance += (int) UIUtilities.DrawSwitch(g, "Flock",
                Settings.FlockDistance, 10, Settings.cPink);
        Settings.AlignDistance += (int) UIUtilities.DrawSwitch(g, "Align",
                Settings.AlignDistance, 10, Settings.cMagenta);
        Settings.AvoidDistance += (int) UIUtilities.DrawSwitch(g, "Avoid",
                Settings.AvoidDistance, 10, Settings.cOrange);
        Settings.PredatorDistance += (int) UIUtilities.DrawSwitch(g, "Fear",
                Settings.PredatorDistance, 10, Settings.cYellow);
        Settings.BarrierDistance += (int) UIUtilities.DrawSwitch(g, "Barrier",
                Settings.BarrierDistance, 10, Settings.cPink);

    }

    private void HawksUI(Graphics g) {
        UIUtilities.StartGroup(g, position, new Vector2(0, 54), 3);

        ReturnToMainUI(UIUtilities.DrawButton(g, "Return",
                false, false));

        // Hawks
        Settings.UpdatePredatorCount((int) UIUtilities.DrawSwitch(g, "Hawks",
                Settings.PredatorCount, 1, Settings.cBlue));
        Settings.BecomePredatorHelper(UIUtilities.DrawButton(g, "Control",
                false, Settings.cRed));
    }

    private void BarrierUI(Graphics g) {
        UIUtilities.StartGroup(g, position, new Vector2(0, 54), 2);

        ReturnToMainUI(UIUtilities.DrawButton(g, "Return",
                false, false));

        // Barriers
        if (UIUtilities.DrawButton(g, "Undo Barrier", false, Settings.cBlue))
            Settings.Barriers.removeLast();
    }

    private void ReturnToMainUI(boolean doReturn) {
        if (!doReturn)
            return;

        BirdsUI = false;
        ColorUI = false;
        PowerUI = false;
        DistanceUI = false;
        HawksUI = false;
        BarrierUI = false;
    }

}
