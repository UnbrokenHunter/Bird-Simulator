package main;

import java.awt.Graphics;

public class UI {

        private Vector2 generalPosition = Vector2.zero;
        private Vector2 menuPosition = Vector2.zero;
        private Vector2 otherPosition = Vector2.zero;
        private Vector2 belowOtherPosition = Vector2.zero;

        private boolean HideUI = false;
        private boolean BirdsUI = false;
        private boolean ColorUI = false;
        private boolean PowerUI = false;
        private boolean DistanceUI = false;
        private boolean HawksUI = false;
        private boolean BarrierUI = false;
        private boolean MusicUI = false;

        public void DrawButtons(Graphics g) {

                generalPosition = new Vector2(Settings.Width - Settings.bButtonSize.x * 1.3f - 5, 5f);
                menuPosition = new Vector2(Settings.Width - Settings.bButtonSize.x * 1.3f - 5,
                                5f + Settings.bButtonSize.y * 3.5f);
                otherPosition = new Vector2(Settings.Width - Settings.bButtonSize.x * 2.5f - 5,
                                5f + Settings.bButtonSize.y * 3.5f);
                belowOtherPosition = new Vector2(Settings.Width - Settings.bButtonSize.x * 2.5f - 5,
                                5f + Settings.bButtonSize.y * 4.8f);

                if (HideUI)
                        HideUI(g);
                else {

                        GeneralUI(g);

                        UIUtilities.EndGroup();

                        MenuUI(g);

                        UIUtilities.EndGroup();

                        if (BirdsUI)
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
                        else if (MusicUI)
                                MusicUI(g);
                }
                UIUtilities.EndGroup();
        }

        private void MenuUI(Graphics g) {
                UIUtilities.StartGroup(g, menuPosition, new Vector2(0, 54), 7);

                // RESET ALL OTHERS BEFORE CHANGING

                ReturnToMainUI(UIUtilities.DrawButton(g, "Birds", BirdsUI, false, Settings.cMagenta) ? 1 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Colors", ColorUI, false, Settings.cBlue) ? 2 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Power", PowerUI, false, Settings.cPurple) ? 3 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Distance", DistanceUI, false, Settings.cOrange) ? 4 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Hawks", HawksUI, false, Settings.cYellow) ? 5 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Barriers", BarrierUI, false, Settings.cRed) ? 6 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Music", MusicUI, false, Settings.cMagenta) ? 7 : 0);
        }

        private void GeneralUI(Graphics g) {

                UIUtilities.StartGroup(g, generalPosition, new Vector2(0, 54), 3);

                HideUI = UIUtilities.DrawButton(g, "Hide", HideUI, false);
                Settings.Pause = UIUtilities.DrawButton(g, "Pause", Settings.Pause, false, Settings.cGreen);
                if (UIUtilities.DrawButton(g, "Restart", false, false, Settings.cPink))
                        Field.Restart();
        }

        private void HideUI(Graphics g) {
                UIUtilities.StartGroup(g, generalPosition, new Vector2(0, 54), 1);

                HideUI = UIUtilities.DrawButton(g, "Show", HideUI, false);
        }

        private void MusicUI(Graphics g) {
                UIUtilities.StartGroup(g, otherPosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Music", false, false, Settings.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, belowOtherPosition, new Vector2(0, 54), 3);

                Settings.MusicEnabled = UIUtilities.DrawButton(g, "Enabled",
                                Settings.MusicEnabled, true, Settings.cYellow);

                Settings.MusicIndex = (UIUtilities.DrawButton(g, "Pentatonic Scale",
                                Settings.MusicIndex == 0, true, Settings.cMagenta) ? 0 : Settings.MusicIndex);
                Settings.MusicIndex = (UIUtilities.DrawButton(g, "Major Scale",
                                Settings.MusicIndex == 1, true, Settings.cMagenta) ? 1 : Settings.MusicIndex);

        }

        private void BirdsUI(Graphics g) {
                UIUtilities.StartGroup(g, otherPosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Birds", false, false, Settings.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, belowOtherPosition, new Vector2(0, 54), 3);

                Settings.UpdateBirdCount(
                                (int) UIUtilities.DrawSwitch(g, "Bird Count", Settings.BirdCount, 100, Settings.cBlue));
                Settings.MinSpeed += (int) UIUtilities.DrawSwitch(g, "Min Speed",
                                Settings.MinSpeed, 1, Settings.cGreen);
                Settings.MaxSpeed += (int) UIUtilities.DrawSwitch(g, "Max Speed",
                                Settings.MaxSpeed, 1, Settings.cOrange);
        }

        private void ColorUI(Graphics g) {
                UIUtilities.StartGroup(g, otherPosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Color", false, false, Settings.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, belowOtherPosition, new Vector2(0, 54), 3);

                // Colors
                Settings.SetNextColor((int) UIUtilities.DrawSwitch(g, "Next Color",
                                Settings.ColorIndex, 1, Settings.cRed));
                Settings.NumberOfColors += (int) UIUtilities.DrawSwitch(g, "Num Colors",
                                Settings.NumberOfColors, 10, Settings.cPink);
                Settings.DoFancyColor = UIUtilities.DrawButton(g, "Fancy Colors",
                                Settings.DoFancyColor, Settings.cMagenta);
        }

        private void PowerUI(Graphics g) {
                UIUtilities.StartGroup(g, otherPosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Power", false, false, Settings.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, belowOtherPosition, new Vector2(0, 54), 5);

                // Power
                Settings.FlockPower += UIUtilities.DrawSwitch(g, "Flock",
                                Settings.FlockPower, 0.0001d, Settings.cPink);
                Settings.AlignPower += UIUtilities.DrawSwitch(g, "Align",
                                Settings.AlignPower, 0.01d, Settings.cMagenta);
                Settings.AvoidPower += UIUtilities.DrawSwitch(g, "Avoid",
                                Settings.AvoidPower, 0.001d, Settings.cOrange);
                Settings.PredatorPower += UIUtilities.DrawSwitch(g, "Fear",
                                Settings.PredatorPower, 0.00015d, Settings.cYellow);
                Settings.BarrierPower += UIUtilities.DrawSwitch(g, "Barrier",
                                Settings.BarrierPower, 0.1, Settings.cPink);
        }

        private void DistanceUI(Graphics g) {
                UIUtilities.StartGroup(g, otherPosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Distance", false, false, Settings.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, belowOtherPosition, new Vector2(0, 54), 6);

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
                Settings.KillDistance += (int) UIUtilities.DrawSwitch(g, "Kill",
                                Settings.KillDistance, 1, Settings.cGreen);

        }

        private void HawksUI(Graphics g) {
                UIUtilities.StartGroup(g, otherPosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Hawks", false, false, Settings.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, belowOtherPosition, new Vector2(0, 54), 3);

                // Hawks
                Settings.UpdatePredatorCount((int) UIUtilities.DrawSwitch(g, "Hawks",
                                Settings.PredatorCount, 1, Settings.cBlue));
                Settings.BecomePredatorHelper(UIUtilities.DrawButton(g, "Control",
                                false, Settings.cRed));
                Settings.PredatorCanKill = UIUtilities.DrawButton(g, "Can Kill",
                                Settings.PredatorCanKill, Settings.cMagenta);
        }

        private void BarrierUI(Graphics g) {
                UIUtilities.StartGroup(g, otherPosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Barrier", false, false, Settings.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, belowOtherPosition, new Vector2(0, 54), 3);

                // Bounce
                Settings.Bounce = UIUtilities.DrawButton(g, "Bounce", Settings.Bounce, Settings.cGreen);

                Settings.SetNextBarrierMode((int) UIUtilities.DrawSwitch(g, "Mode",
                                Settings.ModeIndex, 1, Settings.cBlue));

                // Barriers
                if (UIUtilities.DrawButton(g, "Undo Barrier", false, Settings.cBlue))
                        Settings.Barriers.removeLast();
        }

        private void ReturnToMainUI(int UI) {
                if (UI == 0)
                        return;

                BirdsUI = false;
                ColorUI = false;
                PowerUI = false;
                DistanceUI = false;
                HawksUI = false;
                BarrierUI = false;
                MusicUI = false;

                if (UI == -1)
                        return;

                switch (UI) {
                        case 1:
                                BirdsUI = true;
                                break;
                        case 2:
                                ColorUI = true;
                                break;
                        case 3:
                                PowerUI = true;
                                break;
                        case 4:
                                DistanceUI = true;
                                break;
                        case 5:
                                HawksUI = true;
                                break;
                        case 6:
                                BarrierUI = true;
                                break;
                        case 7:
                                MusicUI = true;
                                break;

                }
        }
}
