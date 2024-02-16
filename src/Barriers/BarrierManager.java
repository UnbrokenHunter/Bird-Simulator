package Barriers;

import java.awt.Graphics;

import Main.Settings;
import Utilities.Vector2;

public class BarrierManager {

        public static Vector2 ClickPosition;
        public static Vector2 DragPosition;

        public static void PreviewBarrier(Graphics g) {
                if (ClickPosition != null && DragPosition != null && Settings.BecomePredator == false) {

                        Vector2 startPos = new Vector2(Math.min(ClickPosition.x, DragPosition.x),
                                        Math.min(ClickPosition.y, DragPosition.y));
                        Vector2 endPos = new Vector2(Math.max(ClickPosition.x, DragPosition.x),
                                        Math.max(ClickPosition.y, DragPosition.y));

                        if (Settings.DoGrid) {
                                startPos = new Vector2(startPos.x - startPos.x % Settings.GridSize,
                                                startPos.y - startPos.y % Settings.GridSize);
                                endPos = new Vector2(endPos.x - endPos.x % Settings.GridSize,
                                                endPos.y - endPos.y % Settings.GridSize);
                        }

                        g.setColor(Settings.cBlue);
                        if (Settings.ShapeIndex == 0)
                                g.fillRoundRect((int) startPos.x, (int) startPos.y,
                                                (int) endPos.x - (int) startPos.x,
                                                (int) endPos.y - (int) startPos.y, 10, 10);

                        else if (Settings.ShapeIndex == 1)
                                g.fillOval((int) startPos.x, (int) startPos.y,
                                                (int) endPos.x - (int) startPos.x,
                                                (int) endPos.y - (int) startPos.y);

                }
        }

        public static void CreateBarrier() {
                if (Settings.DoGrid && ClickPosition != null && DragPosition == null)
                        DragPosition = new Vector2(ClickPosition.x + Settings.GridSize,
                                        ClickPosition.y + Settings.GridSize);

                if (ClickPosition != null && DragPosition != null && Settings.BecomePredator == false) {

                        Vector2 startPos = new Vector2(Math.min(ClickPosition.x, DragPosition.x),
                                        Math.min(ClickPosition.y, DragPosition.y));
                        Vector2 endPos = new Vector2(Math.max(ClickPosition.x, DragPosition.x),
                                        Math.max(ClickPosition.y, DragPosition.y));

                        if (Settings.DoGrid) {
                                startPos = new Vector2(startPos.x - startPos.x % Settings.GridSize,
                                                startPos.y - startPos.y % Settings.GridSize);
                                endPos = new Vector2(endPos.x - endPos.x % Settings.GridSize,
                                                endPos.y - endPos.y % Settings.GridSize);
                        }

                        Settings.Barriers.add(new Barrier(startPos, endPos, Settings.ShapeIndex, Settings.ModeIndex));
                }

                ClickPosition = null;
                DragPosition = null;
        }

}