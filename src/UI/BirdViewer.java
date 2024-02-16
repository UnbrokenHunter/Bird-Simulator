package UI;

import java.awt.Color;
import java.awt.Graphics;

import Boids.Bird;
import Boids.Field;
import Main.Settings;
import Utilities.UIUtilities;
import Utilities.Vector2;

public class BirdViewer {

        public static Vector2 mouse;
        private static Bird birdInView;
        private static int epsilon = 15;

        public void DrawBirdViewer(Graphics g) {

                if (Field.Birds.size() > Settings.BirdInViewIndex)
                        birdInView = Field.Birds.get(Settings.BirdInViewIndex);

                if (Settings.Pause == false)
                        return;

                if (birdInView == null)
                        return;

                g.setColor(Color.white);
                g.drawOval((int) birdInView.X - epsilon, (int) birdInView.Y - epsilon, epsilon * 2, epsilon * 2);

                Vector2 size = new Vector2(80, 50);

                UIUtilities.StartGroup(g, new Vector2(5, 5), new Vector2(0, 60), size, 8);

                Settings.BirdInViewIndex += UIUtilities.DrawSwitch(g, Vector2.zero, size, "Name: " + (birdInView.Name),
                                Settings.BirdInViewIndex,
                                1, true, Settings.cBlue);
                birdInView.X -= UIUtilities.DrawSwitch(g, Vector2.zero, size, "X: " + ((int) birdInView.X),
                                birdInView.X, 10d, false, Settings.cBlue);
                birdInView.Y -= UIUtilities.DrawSwitch(g, Vector2.zero, size, "Y: " + ((int) birdInView.Y),
                                birdInView.Y, 10d, false, Settings.cGreen);

                birdInView.isPredator = UIUtilities.DrawButton(g, Vector2.zero, size, "Predator", birdInView.isPredator,
                                birdInView.isPredator, Settings.cPink);

                UIUtilities.DrawText(g, Vector2.zero, size, "Birds in Radius: " + (birdInView.inRadius),
                                Settings.cBlue);
                UIUtilities.DrawText(g, Vector2.zero, size,
                                "Color: " + birdInView.color.getRed() + ", " +
                                                birdInView.color.getGreen() + ", " +
                                                birdInView.color.getBlue(),
                                Settings.cBlue);

                UIUtilities.DrawText(g, Vector2.zero, size,
                                "On Cooldown: " + birdInView.onCooldown,
                                Settings.cBlue);

                UIUtilities.DrawText(g, Vector2.zero, size,
                                "Tick: " + birdInView.Tick + " / " + birdInView.MaxTick,
                                Settings.cBlue);

                UIUtilities.EndGroup();
        }

}
