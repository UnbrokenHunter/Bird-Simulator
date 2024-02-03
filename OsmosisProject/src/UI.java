import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class UI {

    public static MouseEvent me;

    public void DrawButtons(Graphics g) {

        DrawBounceButton(g, 0, "Bounce");
        DrawNextColorButton(g, 1, "Next Color");
        DrawNumberOfColorsButton(g, 2, "Num Colors");
        DrawFancyColorsButton(g, 3, "Fancy Colors");
        DrawBirdCountSwitch(g, 4, "Bird Count");

        DrawFlockPowerSwitch(g, 5, "Flock");
        DrawAlignPowerSwitch(g, 6, "Align");
        DrawAvoidPowerSwitch(g, 7, "Avoid");
        DrawPredatorPowerSwitch(g, 8, "Fear");
        DrawBarrierPowerSwitch(g, 9, "Barrier");

        DrawPredatorCountSwitch(g, 10, "Hawks");
        DrawBecomePredatorButton(g, 11, "Control");

        DrawDestroyLastBarrierButton(g, 12, "Destroy Last Barrier");
        DrawPauseButton(g, 13, "Pause");
        DrawRestartButton(g, 14, "Restart");
    }

    private void DrawRestartButton(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        if (ButtonHelper(0, buttonPositionY, Settings.bButtonSize, me, g, "")) {
            Field.Restart();
            System.out.println("Restart");
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawDestroyLastBarrierButton(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        if (ButtonHelper(0, buttonPositionY, Settings.bButtonSize, me, g, "")) {
            Settings.Barriers.removeLast();
            System.out.println("Destroy Last Barrier");
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawPauseButton(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        if (ButtonHelper(0, buttonPositionY, Settings.bButtonSize, me, g, Settings.Pause)) {
            Settings.Pause = !Settings.Pause;
            System.out.println("Pause");
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawBecomePredatorButton(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        if (ButtonHelper(0, buttonPositionY, Settings.bButtonSize, me, g, Settings.BecomePredator)) {
            System.out.println("Become Predator");
            Settings.BecomePredatorHelper();
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawPredatorCountSwitch(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        double switchAmount = SwitchHelper(0, buttonPositionY, Settings.bButtonSize, me, g, 1, Settings.PredatorCount);
        if (switchAmount != 0) {
            Settings.UpdatePredatorCount((int) switchAmount);
            System.out.println("Predator Count Toggled: " + (int) Settings.PredatorCount);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawBarrierPowerSwitch(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        double switchAmount = SwitchHelper(0, buttonPositionY, Settings.bButtonSize, me, g, 0.1,
                Settings.BarrierPower);
        if (switchAmount != 0) {
            if (Settings.BarrierPower + switchAmount >= 0)
                Settings.BarrierPower += switchAmount;
            else
                Settings.BarrierPower = 0;
            System.out.println("Barrier Toggled: " + Settings.BarrierPower);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawPredatorPowerSwitch(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        double switchAmount = SwitchHelper(0, buttonPositionY, Settings.bButtonSize, me, g, 0.0001,
                Settings.PredatorPower);
        if (switchAmount != 0) {
            if (Settings.PredatorPower + switchAmount >= 0)
                Settings.PredatorPower += switchAmount;
            else
                Settings.PredatorPower = 0;
            System.out.println("Flock Toggled: " + Settings.PredatorPower);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawFlockPowerSwitch(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        double switchAmount = SwitchHelper(0, buttonPositionY, Settings.bButtonSize, me, g, 0.0001,
                Settings.FlockPower);
        if (switchAmount != 0) {
            if (Settings.FlockPower + switchAmount >= 0)
                Settings.FlockPower += switchAmount;
            else
                Settings.FlockPower = 0;
            System.out.println("Flock Toggled: " + Settings.FlockPower);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawAlignPowerSwitch(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        double switchAmount = SwitchHelper(0, buttonPositionY, Settings.bButtonSize, me, g, 0.01, Settings.AlignPower);
        if (switchAmount != 0) {
            if (Settings.AlignPower + switchAmount >= 0)
                Settings.AlignPower += switchAmount;
            else
                Settings.AlignPower = 0;
            System.out.println("Align Toggled: " + Settings.AlignPower);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawAvoidPowerSwitch(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        double switchAmount = SwitchHelper(0, buttonPositionY, Settings.bButtonSize, me, g, 0.001, Settings.AvoidPower);
        if (switchAmount != 0) {
            if (Settings.AvoidPower + switchAmount >= 0)
                Settings.AvoidPower += switchAmount;
            else
                Settings.AvoidPower = 0;
            System.out.println("Avoid Toggled: " + Settings.AvoidPower);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawBirdCountSwitch(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        int switchAmount = (int) SwitchHelper(0, buttonPositionY, Settings.bButtonSize, me, g, 100, Settings.BirdCount);
        if (switchAmount != 0) {
            System.out.println("Bird Count Toggled: " + switchAmount);
            Settings.UpdateBirdCount(switchAmount);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawFancyColorsButton(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        if (ButtonHelper(0, buttonPositionY, Settings.bButtonSize, me, g, Settings.DoFancyColor)) {
            System.out.println("Fancy Colors Toggled");
            Settings.DoFancyColor = !Settings.DoFancyColor;
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawNumberOfColorsButton(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        int switchAmount = (int) SwitchHelper(0, buttonPositionY, Settings.bButtonSize, me, g, 1,
                Settings.NumberOfColors);
        if (switchAmount != 0) {
            if (Settings.NumberOfColors + switchAmount >= 0)
                Settings.NumberOfColors += switchAmount;
            else
                Settings.NumberOfColors = 0;

            Settings.ColorInterp.SetNumColors(Settings.NumberOfColors);
            System.out.println("Num Colors Colors Toggled");
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawNextColorButton(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        if (ButtonHelper(0, buttonPositionY, Settings.bButtonSize, me, g, Settings.DoFancyColor)) {
            Settings.SetNextColor();
            System.out.println("Next Color Toggle: " + Settings.ColorPalatte);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawBounceButton(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        if (ButtonHelper(0, buttonPositionY, Settings.bButtonSize, me, g, Settings.Bounce)) {
            System.out.println("Bounce Toggled");
            Settings.Bounce = !Settings.Bounce;
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    public static void DrawTextHelper(Graphics g, int buttonPositionY, String text, Vector2 offset) {
        Vector2 position = new Vector2(Settings.Width - (int) (Settings.bButtonSize.x * 1.5),
                Settings.bButtonPositionYStart + buttonPositionY * Settings.bButtonSize.y);

        Font font = new Font("Arial", Font.PLAIN, TextUtilities.calcFontSize(text, position, Settings.bButtonSize, g));
        g.setFont(font);

        Vector2 drawPosition = TextUtilities.alignTextCenter(text, position, Settings.bButtonSize, g);
        g.drawString(text, (int) drawPosition.x + (int) offset.x, (int) drawPosition.y + (int) offset.y);
    }

    public static void DrawTextHelperRight(Graphics g, int buttonPositionY, String text, Vector2 offset) {
        Vector2 position = new Vector2(Settings.Width - (int) (Settings.bButtonSize.x * 1.5),
                Settings.bButtonPositionYStart + buttonPositionY * Settings.bButtonSize.y);

        Font font = new Font("Arial", Font.PLAIN, TextUtilities.calcFontSize(text, position, Settings.bButtonSize, g));
        g.setFont(font);

        Vector2 drawPosition = TextUtilities.alignTextRight(text, position, Settings.bButtonSize, g);
        g.drawString(text, (int) drawPosition.x + (int) offset.x, (int) drawPosition.y + (int) offset.y);
    }

    public static double SwitchHelper(int xPositionOffset, int yPositionOffset, Vector2 size, MouseEvent mouse,
            Graphics g, double amount,
            double value) {
        Vector2 position = new Vector2(Settings.Width - (int) (Settings.bButtonSize.x * 1.5),
                Settings.bButtonPositionYStart + yPositionOffset * size.y);

        // Draw First Button
        g.fillRect((int) position.x, (int) position.y + yPositionOffset, (int) size.x, (int) size.y / 2 - 1);

        // Draw Second Button
        g.fillRect((int) position.x, (int) position.y + (int) size.y / 2 + yPositionOffset, (int) size.x,
                (int) size.y / 2 - 1);

        g.setColor(Color.black);
        // Arrow One
        g.drawLine((int) position.x + (int) size.x / 4,
                (int) position.y + yPositionOffset + (int) size.y / 2 - 6,
                (int) position.x + (int) size.x / 2,
                (int) position.y + yPositionOffset + (int) size.y / 3 - 6);

        g.drawLine((int) position.x + (int) size.x - (int) size.x / 4,
                (int) position.y + yPositionOffset + (int) size.y / 2 - 6,
                (int) position.x + (int) size.x - (int) size.x / 2,
                (int) position.y + yPositionOffset + (int) size.y / 3 - 6);

        // Arrow Two
        g.drawLine((int) position.x + (int) size.x / 4,
                (int) position.y + yPositionOffset + (int) size.y / 3 - 6 + (int) size.y / 2,
                (int) position.x + (int) size.x / 2,
                (int) position.y + yPositionOffset + (int) size.y / 2 - 6 + (int) size.y / 2);

        g.drawLine((int) position.x + (int) size.x - (int) size.x / 4,
                (int) position.y + yPositionOffset + (int) size.y / 3 - 6 + (int) size.y / 2,
                (int) position.x + (int) size.x - (int) size.x / 2,
                (int) position.y + yPositionOffset + (int) size.y / 2 - 6 + (int) size.y / 2);

        var number = (round(((double) value), 4));
        g.setColor(Color.white);
        DrawTextHelperRight(g, yPositionOffset, (number == 0 ? (int) number : number) + "", new Vector2(-60, 0));

        if (me == null)
            return 0;

        int xOffset = -7;
        int yOffset = -28;

        // Get the mouse coordinates
        double mouseX = mouse.getX() + xOffset;
        double mouseY = mouse.getY() + yOffset;

        // Check if the mouse coordinates are within the top button bounds
        boolean topButtonClick = mouseX >= position.x && mouseX <= (position.x + size.x) &&
                mouseY >= position.y && mouseY <= (position.y + size.y / 2);

        // Check if the mouse coordinates are within the bottom button bounds
        boolean bottomButtonClick = mouseX >= position.x && mouseX <= (position.x + size.x) &&
                mouseY >= position.y + size.y / 2 && mouseY <= (position.y + size.y);

        if (topButtonClick || bottomButtonClick == true)
            me = null;

        if (topButtonClick)
            return amount;

        if (bottomButtonClick)
            return -amount;

        return 0;
    }

    public static boolean ButtonHelper(int xPositionOffset, int yPositionOffset, Vector2 size, MouseEvent mouse,
            Graphics g, Object value) {

        Vector2 position = new Vector2(Settings.Width - (int) (Settings.bButtonSize.x * 1.5),
                Settings.bButtonPositionYStart + yPositionOffset * size.y);

        // Draw Button
        g.fillRect((int) position.x, (int) position.y + yPositionOffset, (int) size.x, (int) size.y);

        if (value != null)
            value = value.toString();

        g.setColor(Color.white);
        DrawTextHelperRight(g, yPositionOffset, value + "", new Vector2(-60, 0));

        if (me == null)
            return false;

        int xOffset = -7;
        int yOffset = -28;

        // Get the mouse coordinates
        double mouseX = mouse.getX() + xOffset;
        double mouseY = mouse.getY() + yOffset;

        // Check if the mouse coordinates are within the button bounds
        boolean buttonClick = mouseX >= position.x && mouseX <= (position.x + size.x) &&
                mouseY >= position.y && mouseY <= (position.y + size.y);

        if (buttonClick == true)
            me = null;

        return buttonClick;
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
