import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class BirdViewer {

    public static Vector2 mouse;
    private static Bird birdInView;
    private int yOffset = 30;
    private static int xOffset = 150;
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

        g.setColor(Color.white);
        g.fillRect(0, (int) 0, 150, 200);

        Vector2 size = new Vector2(150, 25);
        DrawText("Name: " + (birdInView.Name), 0, size, g);
        DrawText("X: " + ((int) birdInView.X), 1, size, g);
        DrawText("Y: " + ((int) birdInView.Y), 2, size, g);
        DrawText("Predator: " + (birdInView.isPredator), 3, size, g);
        DrawText("In Radius: " + (birdInView.inRadius), 4, size, g);
        DrawText("Color: ("
                + (birdInView.color.getRed() + ", " + birdInView.color.getGreen() + ", " + birdInView.color.getBlue()
                        + ")"),
                5,
                size, g);

        DrawNextBirdSwitch(g, 0, "Next Bird");
        DrawXCoordSwitch(g, 1, "Change X");
        DrawYCoordSwitch(g, 2, "Change Y");
    }

    private void DrawNextBirdSwitch(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        double switchAmount = SwitchHelper(buttonPositionY, Settings.bButtonSize, mouse, g, 1,
                Settings.BirdInViewIndex);
        if (switchAmount != 0) {
            Settings.BirdInViewIndex += switchAmount;
            if (Settings.BirdInViewIndex < 0)
                Settings.BirdInViewIndex = 0;
            System.out.println("Bird In View Index: " + (int) Settings.BirdInViewIndex);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawXCoordSwitch(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        double switchAmount = SwitchHelper(buttonPositionY, Settings.bButtonSize, mouse, g, 10, birdInView.X);
        if (switchAmount != 0) {
            birdInView.X += switchAmount;
            System.out.println("Bird X: " + (int) birdInView.X);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawYCoordSwitch(Graphics g, int buttonPositionY, String text) {
        g.setColor(Color.white);
        double switchAmount = SwitchHelper(buttonPositionY, Settings.bButtonSize, mouse, g, 10, birdInView.Y);
        if (switchAmount != 0) {
            birdInView.Y += switchAmount;
            System.out.println("Bird Y: " + (int) birdInView.Y);
        }

        g.setColor(Color.black);
        DrawTextHelper(g, buttonPositionY, text, Vector2.zero);
    }

    private void DrawText(String text, int textIndex, Vector2 size, Graphics g) {
        g.setColor(Color.black);
        var position = new Vector2(0, textIndex * yOffset);
        Font font = new Font("Arial", Font.PLAIN, TextUtilities.calcFontSize(text, position, size, g));
        g.setFont(font);
        var textPos = TextUtilities.alignTextLeft("X: " + ((int) birdInView.X),
                position,
                size,
                g);
        g.drawString(text, (int) textPos.x, (int) textPos.y);
    }

    public static void DrawTextHelper(Graphics g, int buttonPositionY, String text, Vector2 offset) {
        Vector2 position = new Vector2(xOffset,
                Settings.bButtonPositionYStart + buttonPositionY * Settings.bButtonSize.y);

        Font font = new Font("Arial", Font.PLAIN, TextUtilities.calcFontSize(text, position, Settings.bButtonSize, g));
        g.setFont(font);

        Vector2 drawPosition = TextUtilities.alignTextCenter(text, position, Settings.bButtonSize, g);
        g.drawString(text, (int) drawPosition.x + (int) offset.x, (int) drawPosition.y + (int) offset.y);
    }

    public static double SwitchHelper(int yPositionOffset, Vector2 size, Vector2 mouse, Graphics g, double amount,
            double value) {
        Vector2 position = new Vector2(xOffset,
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

        double displayValue = value;

        g.setColor(Color.white);
        DrawTextHelper(g, yPositionOffset, ((int) displayValue) + "", new Vector2(-60, 0));

        if (mouse == null)
            return 0;

        // Check if the mouse coordinates are within the top button bounds
        boolean topButtonClick = mouse.x >= position.x && mouse.x <= (position.x + size.x) &&
                mouse.y >= position.y && mouse.y <= (position.y + size.y / 2);

        // Check if the mouse coordinates are within the bottom button bounds
        boolean bottomButtonClick = mouse.x >= position.x && mouse.x <= (position.x + size.x) &&
                mouse.y >= position.y + size.y / 2 && mouse.x <= (position.y + size.y);

        if (topButtonClick || bottomButtonClick == true)
            mouse = null;

        if (topButtonClick)
            return amount;

        if (bottomButtonClick)
            return -amount;

        return 0;
    }

}
