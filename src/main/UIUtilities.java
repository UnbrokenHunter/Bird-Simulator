package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UIUtilities {

    private static Vector2 groupParentPosition = Vector2.zero;
    private static Vector2 groupOffset = Vector2.zero;
    private static int groupIndex = 0;

    public static void StartGroup(Graphics g, Vector2 parentPosition, Vector2 objectOffset, int numberOfObjects) {
        StartGroup(g, parentPosition, objectOffset, Settings.bButtonSize, numberOfObjects);
    }

    public static void StartGroup(Graphics g, Vector2 parentPosition, Vector2 objectOffset, Vector2 size,
            int numberOfObjects) {
        groupParentPosition = parentPosition;
        groupOffset = objectOffset;
        groupIndex = 0;

        HandleBackground(g, numberOfObjects, size);
    }

    public static void EndGroup() {
        groupParentPosition = Vector2.zero;
        groupOffset = Vector2.zero;
        groupIndex = 0;
    }

    public static boolean PositionIsUI(Vector2 position) {
        return !(((position.x < Settings.UIStartBounds.x || position.x > Settings.UIEndBounds.x)
                || (position.y < Settings.UIStartBounds.y || position.y > Settings.UIEndBounds.y)));
    }

    private static void HandleBackground(Graphics g, int numberOfObjects, Vector2 size) {
        Vector2 pad = new Vector2(4, 4);

        Settings.UIStartBounds = new Vector2(groupParentPosition.x - pad.x, groupParentPosition.y - pad.y);
        Settings.UISizeBounds = new Vector2(size.x + groupOffset.x * (numberOfObjects - 1) + pad.x * 2,
                size.y + groupOffset.y * (numberOfObjects - 1) + pad.y * 2);
        Settings.UIEndBounds = Vector2.add(Settings.UIStartBounds, Settings.UISizeBounds);

        g.setColor(Settings.cDefaultBackground);
        g.fillRoundRect(
                (int) Settings.UIStartBounds.x,
                (int) Settings.UIStartBounds.y,
                (int) Settings.UISizeBounds.x,
                (int) Settings.UISizeBounds.y,
                10,
                10);
    }

    private static Vector2 HandleGroup(Vector2 originPosition) {
        Vector2 newPosition = Vector2.add(originPosition,
                Vector2.add(groupParentPosition, Vector2.multiply(groupOffset, groupIndex)));
        groupIndex++;
        return newPosition;
    }

    public static double DrawSwitch(Graphics g, String displayNameText, double switchValue, double switchAmount) {
        return DrawSwitch(g, Vector2.zero, displayNameText, switchValue, switchAmount);
    }

    public static double DrawSwitch(Graphics g, String displayNameText, double switchValue, double switchAmount,
            boolean displayValue, Color buttonColor) {
        return DrawSwitch(g, Vector2.zero, displayNameText, switchValue, switchAmount, displayValue,
                buttonColor);
    }

    public static double DrawSwitch(Graphics g, String displayNameText, double switchValue, double switchAmount,
            Color buttonColor) {
        return DrawSwitch(g, Vector2.zero, displayNameText, switchValue, switchAmount, true,
                buttonColor);
    }

    public static double DrawSwitch(Graphics g, String displayNameText, double switchValue, double switchAmount,
            boolean displayValue) {
        return DrawSwitch(g, Vector2.zero, displayNameText, switchValue, switchAmount, displayValue,
                Settings.cDefaultColor);
    }

    public static double DrawSwitch(Graphics g, Vector2 buttonPosition, String displayNameText, double switchValue,
            double switchAmount) {
        return DrawSwitch(g, buttonPosition, displayNameText, switchValue, switchAmount, true, Settings.cDefaultColor);
    }

    public static double DrawSwitch(Graphics g, Vector2 buttonPosition, String displayNameText,
            double switchValue, double switchAmount, boolean displayValue, Color buttonColor) {
        return DrawSwitch(g, buttonPosition, Settings.bButtonSize, displayNameText, switchValue, switchAmount,
                displayValue, buttonColor);
    }

    public static double DrawSwitch(Graphics g, Vector2 buttonPosition, Vector2 buttonSize, String displayNameText,
            double switchValue, double switchAmount, boolean displayValue, Color buttonColor) {

        Vector2 newPosition = HandleGroup(buttonPosition);

        double difference = UIUtilities.SwitchHelper(g, newPosition, buttonSize, switchAmount,
                switchValue, displayValue, buttonColor);

        g.setColor(Color.black);
        Vector2 drawOffset = new Vector2(newPosition.x - buttonSize.x / 12, newPosition.y);
        UIUtilities.DrawTextHelper(g, drawOffset, buttonSize, displayNameText);

        return difference;
    }

    public static boolean DrawButton(Graphics g, String displayNameText, boolean booleanValue) {
        return DrawButton(g, Vector2.zero, displayNameText, booleanValue);
    }

    public static boolean DrawButton(Graphics g, String displayNameText, boolean booleanValue, Color buttonColor) {
        return DrawButton(g, Vector2.zero, displayNameText, booleanValue, false, buttonColor);
    }

    public static boolean DrawButton(Graphics g, String displayNameText, boolean booleanValue, boolean displayValue,
            Color buttonColor) {
        return DrawButton(g, Vector2.zero, displayNameText, booleanValue, displayValue, buttonColor);
    }

    public static boolean DrawButton(Graphics g, String displayNameText, boolean booleanValue, boolean displayValue) {
        return DrawButton(g, Vector2.zero, displayNameText, booleanValue, displayValue, Settings.cDefaultColor);
    }

    public static boolean DrawButton(Graphics g, Vector2 buttonPosition, String displayNameText, boolean displayValue,
            boolean booleanValue) {
        return DrawButton(g, buttonPosition, displayNameText, booleanValue, displayValue, Settings.cDefaultColor);
    }

    public static boolean DrawButton(Graphics g, Vector2 buttonPosition, String displayNameText, boolean booleanValue) {
        return DrawButton(g, buttonPosition, displayNameText, booleanValue, true, Settings.cDefaultColor);
    }

    public static boolean DrawButton(Graphics g, Vector2 buttonPosition, String displayNameText, boolean booleanValue,
            boolean displayValue, Color buttonColor) {
        return DrawButton(g, buttonPosition, Settings.bButtonSize, displayNameText, booleanValue, displayValue,
                buttonColor);
    }

    public static boolean DrawButton(Graphics g, Vector2 buttonPosition, Vector2 buttonSize, String displayNameText,
            boolean booleanValue,
            boolean displayValue, Color buttonColor) {

        Vector2 newPosition = HandleGroup(buttonPosition);

        boolean difference = UIUtilities.ButtonHelper(g, newPosition, buttonSize, booleanValue, displayValue,
                buttonColor)
                        ? !booleanValue
                        : booleanValue;

        g.setColor(Color.black);
        UIUtilities.DrawTextHelper(g, newPosition, buttonSize, displayNameText);

        return difference;
    }

    public static void DrawText(Graphics g, Vector2 buttonPosition, Vector2 buttonSize, String displayNameText,
            Color buttonColor) {

        Vector2 newPosition = HandleGroup(buttonPosition);

        UIUtilities.ButtonHelper(g, newPosition, buttonSize, false, false,
                buttonColor);

        g.setColor(Color.black);
        UIUtilities.DrawTextHelper(g, newPosition, buttonSize, displayNameText);

    }

    private static double SwitchHelper(Graphics g, Vector2 buttonPosition, Vector2 size,
            double amount,
            double value,
            boolean displayValue, Color buttonColor) {

        // Arrow
        DrawSwitch(g, buttonPosition, size, buttonColor);

        if (displayValue) {
            g.setColor(Color.black);
            var number = (Utilities.round(((double) value), 4));
            var text = (number == 0 ? (int) number : number) + "";
            DrawTextHelper(g, new Vector2(buttonPosition.x, buttonPosition.y + size.y / 3),
                    size, text, 9);
        }

        if (Settings.MouseClick == null)
            return 0;

        // Check if the mouse coordinates are within the top button bounds
        boolean topButtonClick = Settings.MouseClick.x >= buttonPosition.x
                && Settings.MouseClick.x <= (buttonPosition.x + size.x) &&
                Settings.MouseClick.y >= buttonPosition.y && Settings.MouseClick.y <= (buttonPosition.y + size.y / 2);

        // Check if the mouse coordinates are within the bottom button bounds
        boolean bottomButtonClick = Settings.MouseClick.x >= buttonPosition.x
                && Settings.MouseClick.x <= (buttonPosition.x + size.x) &&
                Settings.MouseClick.y >= buttonPosition.y + size.y / 2
                && Settings.MouseClick.y <= (buttonPosition.y + size.y);

        if (topButtonClick || bottomButtonClick) {
            Main.sound.PlayClick();
            Settings.MouseClick = null;
            return topButtonClick ? amount : -amount;
        }

        return 0;
    }

    private static boolean ButtonHelper(Graphics g, Vector2 buttonPosition, Vector2 size,
            boolean value, boolean displayValue, Color buttonColor) {
        DrawButton(g, buttonPosition, size, buttonColor);

        if (displayValue) {
            g.setColor(Color.black);
            DrawTextHelper(g, new Vector2(buttonPosition.x, buttonPosition.y + size.y / 3),
                    size, value + "", 9);
        }

        if (Settings.MouseClick == null)
            return false;

        // Check if the mouse coordinates are within the button bounds
        boolean buttonClick = Settings.MouseClick.x >= buttonPosition.x
                && Settings.MouseClick.x <= (buttonPosition.x + size.x) &&
                Settings.MouseClick.y >= buttonPosition.y && Settings.MouseClick.y <= (buttonPosition.y + size.y);

        if (buttonClick == true) {
            Main.sound.PlayClick();
            Settings.MouseClick = null;
        }

        return buttonClick;
    }

    public static void DrawTextHelper(Graphics g, Vector2 textPosition, Vector2 textSize, String text) {
        DrawTextHelper(g, textPosition, textSize, text, 12);
    }

    public static void DrawTextHelper(Graphics g, Vector2 textPosition, Vector2 textSize, String text, int fontSize) {
        Font font = new Font("Arial", Font.PLAIN, fontSize);
        g.setFont(font);
        var wrappedText = TextUtilities.wrapText(text, (int) textSize.x, g);

        var center = TextUtilities.calcCenterOfEachLine(wrappedText, textPosition, textSize, g);
        for (int i = 0; i < wrappedText.size(); i++) {
            String string = wrappedText.get(i);

            g.drawString(string, (int) center.get(i).x, (int) center.get(i).y);
        }
    }

    private static void DrawButton(Graphics g, Vector2 position, Vector2 size, Color buttonColor) {
        // Draw Button
        g.setColor(buttonColor);
        g.fillRoundRect((int) position.x, (int) position.y, (int) size.x, (int) size.y, 10, 10);
    }

    private static void DrawSwitch(Graphics g, Vector2 position, Vector2 size, Color buttonColor) {

        // Draw First Button
        g.setColor(buttonColor);
        g.fillRoundRect((int) position.x, (int) position.y, (int) size.x, (int) size.y / 2 - 1, 5, 5);

        // Draw Second Button
        g.fillRoundRect((int) position.x, (int) position.y + (int) size.y / 2, (int) size.x,
                (int) size.y / 2 - 1, 5, 5);

        Vector2 p1 = new Vector2(position.x + (int) size.x - 3,
                position.y + (int) size.y / 3);

        Vector2 p2 = new Vector2(position.x + (int) size.x - (int) size.x / 16 - 3,
                position.y + (int) size.y / 4);

        Vector2 p3 = new Vector2(position.x + (int) size.x - (int) size.x / 8 - 3,
                p1.y);

        // Arrow One
        g.setColor(Color.black);
        g.drawLine((int) p1.x,
                (int) p1.y,
                (int) p2.x,
                (int) p2.y);

        g.drawLine((int) p2.x,
                (int) p2.y,
                (int) p3.x,
                (int) p3.y);

        // Arrow Two
        g.drawLine((int) p1.x,
                (int) p2.y + (int) size.y / 2,
                (int) p2.x,
                (int) p1.y + (int) size.y / 2);

        g.drawLine((int) p2.x,
                (int) p3.y + (int) size.y / 2,
                (int) p3.x,
                (int) p2.y + (int) size.y / 2);

    }

}