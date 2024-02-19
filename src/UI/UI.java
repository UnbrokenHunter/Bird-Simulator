package UI;

import java.awt.Graphics;

import Boids.Field;
import Main.Settings;
import Utilities.UIUtilities;
import Utilities.Vector2;

public class UI {

        private Vector2 generalPosition = Vector2.zero;
        private Vector2 menuPosition = Vector2.zero;
        private Vector2 namePosition = Vector2.zero;
        private Vector2 firstPosition = Vector2.zero;
        private Vector2 secondPosition = Vector2.zero;

        private boolean HideUI = false;
        private boolean BirdsUI = false;
        private boolean ColorUI = false;
        private boolean PowerUI = false;
        private boolean DistanceUI = false;
        private boolean HawksUI = false;
        private boolean BarrierUI = false;
        private boolean MusicUI = false;
        private boolean PresetsUI = false;

        public void DrawButtons(Graphics g) {

                generalPosition = new Vector2(Settings.Instance.Width - Settings.Instance.bButtonSize.x * 1.3f - 5, 5f);
                menuPosition = new Vector2(Settings.Instance.Width - Settings.Instance.bButtonSize.x * 1.3f - 5,
                                5f + Settings.Instance.bButtonSize.y * 3.5f);
                namePosition = new Vector2(Settings.Instance.Width - Settings.Instance.bButtonSize.x * 2.5f - 5,
                                5f + Settings.Instance.bButtonSize.y * 3.5f);
                firstPosition = new Vector2(Settings.Instance.Width - Settings.Instance.bButtonSize.x * 2.5f - 5,
                                5f + Settings.Instance.bButtonSize.y * 4.8f);
                secondPosition = new Vector2(Settings.Instance.Width - Settings.Instance.bButtonSize.x * 3.8f - 5,
                                5f + Settings.Instance.bButtonSize.y * 4.8f);

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
                        else if (PresetsUI) // ADDING PRESETS
                                PresetsUI(g);
                }
                UIUtilities.EndGroup();
        }

        private void MenuUI(Graphics g) {
                UIUtilities.StartGroup(g, menuPosition, new Vector2(0, 54), 8);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Colors", ColorUI, false, Settings.Instance.cPurple) ? 2 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Birds", BirdsUI, false, Settings.Instance.cPink) ? 1 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Hawks", HawksUI, false, Settings.Instance.cBlue) ? 5 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Barriers", BarrierUI, false, Settings.Instance.cRed) ? 6 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Power", PowerUI, false, Settings.Instance.cYellow) ? 3 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Distance", DistanceUI, false, Settings.Instance.cOrange) ? 4
                                : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Music", MusicUI, false, Settings.Instance.cMagenta) ? 7 : 0);
                ReturnToMainUI(UIUtilities.DrawButton(g, "Presets", PresetsUI, false, Settings.Instance.cPurple) ? 8
                                : 0);
        }

        private void GeneralUI(Graphics g) {

                UIUtilities.StartGroup(g, generalPosition, new Vector2(0, 54), 3);

                HideUI = UIUtilities.DrawButton(g, "Hide", HideUI, false);
                Settings.Instance.Pause = UIUtilities.DrawButton(g, "Pause", Settings.Instance.Pause, false,
                                Settings.Instance.cGreen);
                if (UIUtilities.DrawButton(g, "Restart", false, false, Settings.Instance.cPink))
                        Field.Restart();
        }

        private void HideUI(Graphics g) {
                UIUtilities.StartGroup(g, generalPosition, new Vector2(0, 54), 1);

                HideUI = UIUtilities.DrawButton(g, "Show", HideUI, false);
        }

        private void PresetsUI(Graphics g) {
                UIUtilities.StartGroup(g, namePosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Presets", false, false, Settings.Instance.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, firstPosition, new Vector2(0, 54), 4);

                Presets.Default(UIUtilities.DrawButton(g, "Default",
                                false, Settings.Instance.cRed));
                Presets.Organized(UIUtilities.DrawButton(g, "Organized",
                                false, Settings.Instance.cGreen));
                Presets.Music(UIUtilities.DrawButton(g, "Music",
                                false, Settings.Instance.cMagenta));
                Presets.Maze(UIUtilities.DrawButton(g, "Maze",
                                false, Settings.Instance.cBlue));

        }

        private void MusicUI(Graphics g) {
                UIUtilities.StartGroup(g, namePosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Music", false, false, Settings.Instance.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, firstPosition, new Vector2(0, 54), 8);

                Settings.Instance.MusicEnabled = UIUtilities.DrawButton(g, "Enabled",
                                Settings.Instance.MusicEnabled, true, Settings.Instance.cYellow);

                Settings.Instance.MusicIndex = (UIUtilities.DrawButton(g, "Pentatonic Scale",
                                Settings.Instance.MusicIndex == 0, true, Settings.Instance.cMagenta) ? 0
                                                : Settings.Instance.MusicIndex);
                Settings.Instance.MusicIndex = (UIUtilities.DrawButton(g, "Major Scale",
                                Settings.Instance.MusicIndex == 1, true, Settings.Instance.cGreen) ? 1
                                                : Settings.Instance.MusicIndex);
                Settings.Instance.MusicIndex = (UIUtilities.DrawButton(g, "Chromatic Scale",
                                Settings.Instance.MusicIndex == 2, true, Settings.Instance.cBlue) ? 2
                                                : Settings.Instance.MusicIndex);
                Settings.Instance.MusicIndex = (UIUtilities.DrawButton(g, "5 Tone Scale",
                                Settings.Instance.MusicIndex == 3, true, Settings.Instance.cRed) ? 3
                                                : Settings.Instance.MusicIndex);
                Settings.Instance.MusicIndex = (UIUtilities.DrawButton(g, "Minor Blues Scale",
                                Settings.Instance.MusicIndex == 6, true, Settings.Instance.cRed) ? 6
                                                : Settings.Instance.MusicIndex);
                Settings.Instance.MusicIndex = (UIUtilities.DrawButton(g, "Quartertone Scale",
                                Settings.Instance.MusicIndex == 4, true, Settings.Instance.cYellow) ? 4
                                                : Settings.Instance.MusicIndex);
                Settings.Instance.MusicIndex = (UIUtilities.DrawButton(g, "17/10 Scale",
                                Settings.Instance.MusicIndex == 5, true, Settings.Instance.cPurple) ? 5
                                                : Settings.Instance.MusicIndex);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, secondPosition, new Vector2(0, 54), 3);

                Settings.Instance.BarrierVolume += UIUtilities.DrawSwitch(g, "Melody Volume",
                                Settings.Instance.BarrierVolume, .1, Settings.Instance.cBlue);
                Settings.Instance.SaftyVolume += UIUtilities.DrawSwitch(g, "Bass Volume",
                                Settings.Instance.SaftyVolume, .1, Settings.Instance.cPink);
                Settings.Instance.KillVolume += UIUtilities.DrawSwitch(g, "Drum Volume",
                                Settings.Instance.KillVolume, .1, Settings.Instance.cGreen);

        }

        private void BirdsUI(Graphics g) {
                UIUtilities.StartGroup(g, namePosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Birds", false, false, Settings.Instance.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, firstPosition, new Vector2(0, 54), 4);

                Settings.Instance.UpdateBirdCount(
                                (int) UIUtilities.DrawSwitch(g, "Bird Count", Settings.Instance.BirdCount, 100,
                                                Settings.Instance.cBlue));

                Settings.Instance.CanReproduce = UIUtilities.DrawButton(g, "Can Balance",
                                Settings.Instance.CanReproduce, true, Settings.Instance.cMagenta);
                Settings.Instance.MinSpeed += (int) UIUtilities.DrawSwitch(g, "Min Speed",
                                Settings.Instance.MinSpeed, 1, Settings.Instance.cGreen);
                Settings.Instance.MaxSpeed += (int) UIUtilities.DrawSwitch(g, "Max Speed",
                                Settings.Instance.MaxSpeed, 1, Settings.Instance.cOrange);
        }

        private void ColorUI(Graphics g) {
                UIUtilities.StartGroup(g, namePosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Color", false, false, Settings.Instance.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, firstPosition, new Vector2(0, 54), 3);

                // Colors
                Settings.Instance.SetNextColor((int) UIUtilities.DrawSwitch(g, "Next Color",
                                Settings.Instance.ColorIndex, 1, Settings.Instance.cRed));
                Settings.Instance.NumberOfColors += (int) UIUtilities.DrawSwitch(g, "Num Colors",
                                Settings.Instance.NumberOfColors, 10, Settings.Instance.cPink);
                Settings.Instance.DoFancyColor = UIUtilities.DrawButton(g, "Fancy Colors",
                                Settings.Instance.DoFancyColor, Settings.Instance.cMagenta);
        }

        private void PowerUI(Graphics g) {
                UIUtilities.StartGroup(g, namePosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Power", false, false, Settings.Instance.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, firstPosition, new Vector2(0, 54), 5);

                // Power
                Settings.Instance.FlockPower += UIUtilities.DrawSwitch(g, "Flock",
                                Settings.Instance.FlockPower, 0.0001d, Settings.Instance.cPink);
                Settings.Instance.AlignPower += UIUtilities.DrawSwitch(g, "Align",
                                Settings.Instance.AlignPower, 0.01d, Settings.Instance.cMagenta);
                Settings.Instance.AvoidPower += UIUtilities.DrawSwitch(g, "Avoid",
                                Settings.Instance.AvoidPower, 0.001d, Settings.Instance.cOrange);
                Settings.Instance.PredatorPower += UIUtilities.DrawSwitch(g, "Fear",
                                Settings.Instance.PredatorPower, 0.00015d, Settings.Instance.cYellow);
                Settings.Instance.BarrierPower += UIUtilities.DrawSwitch(g, "Barrier",
                                Settings.Instance.BarrierPower, 0.1, Settings.Instance.cPink);
        }

        private void DistanceUI(Graphics g) {
                UIUtilities.StartGroup(g, namePosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Distance", false, false, Settings.Instance.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, firstPosition, new Vector2(0, 54), 7);

                // Distance
                Settings.Instance.FlockDistance += (int) UIUtilities.DrawSwitch(g, "Flock",
                                Settings.Instance.FlockDistance, 10, Settings.Instance.cPink);
                Settings.Instance.AlignDistance += (int) UIUtilities.DrawSwitch(g, "Align",
                                Settings.Instance.AlignDistance, 10, Settings.Instance.cMagenta);
                Settings.Instance.AvoidDistance += (int) UIUtilities.DrawSwitch(g, "Avoid",
                                Settings.Instance.AvoidDistance, 10, Settings.Instance.cOrange);
                Settings.Instance.PredatorDistance += (int) UIUtilities.DrawSwitch(g, "Fear",
                                Settings.Instance.PredatorDistance, 10, Settings.Instance.cYellow);
                Settings.Instance.BarrierDistance += (int) UIUtilities.DrawSwitch(g, "Barrier",
                                Settings.Instance.BarrierDistance, 10, Settings.Instance.cPink);
                Settings.Instance.KillDistance += (int) UIUtilities.DrawSwitch(g, "Kill",
                                Settings.Instance.KillDistance, 1, Settings.Instance.cGreen);
                Settings.Instance.ReproductionDistance += (int) UIUtilities.DrawSwitch(g, "Reproduction",
                                Settings.Instance.ReproductionDistance, 1, Settings.Instance.cBlue);

        }

        private void HawksUI(Graphics g) {
                UIUtilities.StartGroup(g, namePosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Hawks", false, false, Settings.Instance.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, firstPosition, new Vector2(0, 54), 3);

                // Hawks
                Settings.Instance.UpdatePredatorCount((int) UIUtilities.DrawSwitch(g, "Hawks",
                                Settings.Instance.PredatorCount, 1, Settings.Instance.cBlue));
                Settings.Instance.BecomePredatorHelper(UIUtilities.DrawButton(g, "Control",
                                false, Settings.Instance.cRed));
                Settings.Instance.PredatorCanKill = UIUtilities.DrawButton(g, "Can Kill",
                                Settings.Instance.PredatorCanKill, true, Settings.Instance.cMagenta);
        }

        private void BarrierUI(Graphics g) {
                UIUtilities.StartGroup(g, namePosition, new Vector2(0, 54), 1);

                ReturnToMainUI(UIUtilities.DrawButton(g, "Barrier", false, false, Settings.Instance.cOrange) ? -1 : 0);

                UIUtilities.EndGroup();

                UIUtilities.StartGroup(g, firstPosition, new Vector2(0, 54), 5);

                // Bounce
                Settings.Instance.Bounce = UIUtilities.DrawButton(g, "Bounce", Settings.Instance.Bounce,
                                Settings.Instance.cGreen);

                Settings.Instance.DoGrid = UIUtilities.DrawButton(g, "Grid", Settings.Instance.DoGrid, true,
                                Settings.Instance.cMagenta);

                Settings.Instance.SetNextShape((int) UIUtilities.DrawSwitch(g, "Shape",
                                Settings.Instance.ShapeIndex, 1, Settings.Instance.cOrange));

                Settings.Instance.SetNextBarrierMode((int) UIUtilities.DrawSwitch(g, "Mode",
                                Settings.Instance.ModeIndex, 1, Settings.Instance.cBlue));

                // Barriers
                if (UIUtilities.DrawButton(g, "Undo Barrier", false, Settings.Instance.cPurple))
                        Settings.Instance.Barriers.removeLast();
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
                PresetsUI = false;

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
                        case 8:
                                PresetsUI = true;
                                break;

                }
        }
}
