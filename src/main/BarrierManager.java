import java.awt.Color;
import java.awt.Graphics;

public class BarrierManager {

    public static Vector2 ClickPosition;
    public static Vector2 DragPosition;

    public static void PreviewBarrier(Graphics g) {
        if (ClickPosition != null && DragPosition != null && Settings.BecomePredator == false) {

            Vector2 startPos = new Vector2(Math.min(ClickPosition.x, DragPosition.x),
                    Math.min(ClickPosition.y, DragPosition.y));
            Vector2 endPos = new Vector2(Math.max(ClickPosition.x, DragPosition.x),
                    Math.max(ClickPosition.y, DragPosition.y));

            g.setColor(Settings.cBlue);
            g.fillRect((int) startPos.x, (int) startPos.y,
                    (int) endPos.x - (int) startPos.x,
                    (int) endPos.y - (int) startPos.y);

        }
    }

    public static void CreateBarrier() {

        if (ClickPosition != null && DragPosition != null && Settings.BecomePredator == false) {

            Vector2 startPos = new Vector2(Math.min(ClickPosition.x, DragPosition.x),
                    Math.min(ClickPosition.y, DragPosition.y));
            Vector2 endPos = new Vector2(Math.max(ClickPosition.x, DragPosition.x),
                    Math.max(ClickPosition.y, DragPosition.y));

            System.out.println("\nClick Pos: " + ClickPosition + "\nDrag Pos: " + DragPosition + "\nStart: " + startPos
                    + "\nEnd: " + endPos + "\nWidth: " + (Settings.Width - Settings.bButtonSize.x));

            Settings.Barriers.add(new Barrier(startPos, endPos));
        }

        ClickPosition = null;
        DragPosition = null;
    }

}