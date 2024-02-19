package Barriers;

import java.awt.Graphics;

import Main.Settings;
import Utilities.Vector2;

public class BarrierManager {

        public static Vector2 ClickPosition;
        public static Vector2 DragPosition;

        public static void PreviewBarrier(Graphics g) {
                if (ClickPosition != null && DragPosition != null && Settings.Instance.BecomePredator == false) {

                        Vector2 startPos = new Vector2(Math.min(ClickPosition.x, DragPosition.x),
                                        Math.min(ClickPosition.y, DragPosition.y));
                        Vector2 endPos = new Vector2(Math.max(ClickPosition.x, DragPosition.x),
                                        Math.max(ClickPosition.y, DragPosition.y));

                        if (Settings.Instance.DoGrid) {
                                startPos = new Vector2(startPos.x - startPos.x % Settings.Instance.GridSize,
                                                startPos.y - startPos.y % Settings.Instance.GridSize);
                                endPos = new Vector2(endPos.x - endPos.x % Settings.Instance.GridSize,
                                                endPos.y - endPos.y % Settings.Instance.GridSize);
                        }

                        g.setColor(Settings.Instance.cBlue);
                        if (Settings.Instance.ShapeIndex == 0)
                                g.fillRoundRect((int) startPos.x, (int) startPos.y,
                                                (int) endPos.x - (int) startPos.x,
                                                (int) endPos.y - (int) startPos.y, 10, 10);

                        else if (Settings.Instance.ShapeIndex == 1)
                                g.fillOval((int) startPos.x, (int) startPos.y,
                                                (int) endPos.x - (int) startPos.x,
                                                (int) endPos.y - (int) startPos.y);

                }
        }

        public static void CreateBarrier(Vector2 start, Vector2 end) {
                ClickPosition = start;
                DragPosition = end;
                CreateBarrier();
        }

        public static void CreateBarrier() {
                if (Settings.Instance.DoGrid && ClickPosition != null && DragPosition == null)
                        DragPosition = new Vector2(ClickPosition.x + Settings.Instance.GridSize,
                                        ClickPosition.y + Settings.Instance.GridSize);

                if (ClickPosition != null && DragPosition != null && Settings.Instance.BecomePredator == false) {

                        Vector2 startPos = new Vector2(Math.min(ClickPosition.x, DragPosition.x),
                                        Math.min(ClickPosition.y, DragPosition.y));
                        Vector2 endPos = new Vector2(Math.max(ClickPosition.x, DragPosition.x),
                                        Math.max(ClickPosition.y, DragPosition.y));

                        if (Settings.Instance.DoGrid) {
                                startPos = new Vector2(startPos.x - startPos.x % Settings.Instance.GridSize,
                                                startPos.y - startPos.y % Settings.Instance.GridSize);
                                endPos = new Vector2(endPos.x - endPos.x % Settings.Instance.GridSize,
                                                endPos.y - endPos.y % Settings.Instance.GridSize);
                        }

                        Settings.Instance.Barriers.add(new Barrier(startPos, endPos, Settings.Instance.ShapeIndex,
                                        Settings.Instance.ModeIndex));
                }

                ClickPosition = null;
                DragPosition = null;

                System.out.println("// Barriers");
                for (Barrier b : Settings.Instance.Barriers) {
                        System.out.println("Settings.Instance.ModeIndex = " + b.modeIndex + ";");
                        System.out.println("Settings.Instance.ShapeIndex = " + b.shapeIndex + ";");
                        System.out.println("BarrierManager.CreateBarrier(new Vector2(" + b.Start.x + "f, " + b.Start.y
                                        + "f), new Vector2(" + b.End.x + "f, " + b.End.y + "f));\n");
                }

        }

}