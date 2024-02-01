import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;

/**
 * A utility class dedicated to text manipulation and measurement within
 * Java Swing graphical contexts.
 * 
 * <p>
 * TextUtilities offers a comprehensive set of tools optimized for interacting
 * with text in Swing-based graphical interfaces. This encompasses
 * functionalities
 * such as determining the most suitable font sizes, aligning text within
 * specified
 * regions, segmenting text according to given widths, and refining text to
 * ensure
 * a precise fit within a predetermined zone.
 * </p>
 * 
 * <p>
 * <b>Note:</b> This utility integrates the Vector2 class, another creation of
 * mine.
 * You can either adapt this utility to bypass its dependency on Vector2 or
 * choose
 * to integrate Vector2 into your project.
 * </p>
 *
 * <p>
 * Key methods like {@link #calcFontSize(String, Vector2, Vector2, Graphics)}
 * and
 * {@link #adjustTextToFit(String, Vector2, Vector2, Graphics)} are instrumental
 * in ensuring text content aligns seamlessly with the designated design
 * constraints.
 * This proves essential in situations where UI components require dynamic
 * resizing
 * or when adaptive text arrangements are necessary.
 * </p>
 * 
 * <p>
 * Usage Example:
 * 
 * <pre>
 * {@code
 * String sampleText = "Hello, World!";
 * Vector2 position = new Vector2(0, 0);
 * Vector2 size = new Vector2(100, 50);
 * Graphics graphics = ...; // An initialized Swing graphics context
 * 
 * int optimalFontSize = TextUtilities.calcFontSize(sampleText, position, size, graphics);
 * String adjustedText = TextUtilities.adjustTextToFit(sampleText, position, size, graphics);
 * }
 * </pre>
 * </p>
 * 
 * @author Hunter
 * @version 1.0
 * @since 1.0
 */
public class TextUtilities {

    /**
     * Calculates the optimal font size for a given text to fit inside a
     * specified area. This overloaded method assumes no padding.
     *
     * @param text Text whose font size needs to be calculated.
     * @param pos  Starting position of the text.
     * @param size Desired size of the area in which text should fit.
     * @param g    Graphics context for measuring text dimensions.
     * @return Optimal font size.
     */
    public static int calcFontSize(String text, Vector2 pos, Vector2 size, Graphics g) {
        return calcFontSize(text, pos, size, Vector2.zero, g);
    }

    /**
     * Calculates the optimal font size for a given text to fit inside a
     * specified area considering padding. It uses a binary search approach
     * to efficiently find the right font size.
     *
     * @param text    Text whose font size needs to be calculated.
     * @param pos     Starting position of the text.
     * @param size    Desired size of the area in which text should fit.
     * @param padding Padding to be considered from each side.
     * @param g       Graphics context for measuring text dimensions.
     * @return Optimal font size.
     */
    public static int calcFontSize(String text, Vector2 pos, Vector2 size, Vector2 padding, Graphics g) {
        int maxWidth = (int) (size.x - (2 * padding.x));
        int maxHeight = (int) (size.y - (2 * padding.y));

        int minFontSize = 1;
        int maxFontSize = 100;

        Font font = g.getFont();

        while (minFontSize <= maxFontSize) {
            int midFontSize = (minFontSize + maxFontSize) / 2;
            Font newFont = new Font(font.getFontName(), font.getStyle(), midFontSize);
            FontMetrics fm = g.getFontMetrics(newFont);

            if (fm.stringWidth(text) <= maxWidth && fm.getHeight() <= maxHeight) {
                minFontSize = midFontSize + 1;
            } else {
                maxFontSize = midFontSize - 1;
            }
        }

        return maxFontSize;
    }

    /**
     * Calculates the center position for a given text to be displayed
     * inside a specified area.
     *
     * @param text Text that needs to be centered.
     * @param pos  Starting position of the area.
     * @param size Desired size of the area.
     * @param g    Graphics context for measuring text dimensions.
     * @return Center position for the text.
     */
    public static Vector2 calcMidText(String text, Vector2 pos, Vector2 size, Graphics g) {
        FontMetrics fm = g.getFontMetrics();

        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        int x = (int) (pos.x + (size.x - textWidth) / 2);
        int y = (int) (pos.y + ((size.y - textHeight) / 2) + fm.getAscent());

        return new Vector2(x, y);
    }

    /**
     * Breaks a given text string into multiple lines such that each line
     * fits within the specified maximum width.
     *
     * @param text     The text string to be wrapped.
     * @param maxWidth The maximum allowable width for each line, in pixels.
     * @param g        The Graphics context, which provides font metrics for text
     *                 measurement.
     * @return A list of strings where each string is a line that fits within the
     *         specified width.
     */
    public static List<String> wrapText(String text, int maxWidth, Graphics g) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");

        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            // Measure the width of the line with the next word
            int lineWidth = g.getFontMetrics().stringWidth(currentLine + " " + word);

            // If the line can fit the next word or if it's the first word, add it
            if (lineWidth < maxWidth || currentLine.length() == 0) {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            } else {
                // Otherwise, store the current line and start a new one
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }

        // Add any remaining text as the final line
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    /**
     * Checks if a given text would be completely visible
     * within the specified position and size, for a given font size.
     *
     * @param text     The text string to be checked.
     * @param pos      The starting position of the text.
     * @param size     The size in which the text should fit.
     * @param fontSize The font size to be used for the text.
     * @param g        The Graphics context, which provides font metrics for text
     *                 measurement.
     * @return True if the text is completely visible, false otherwise.
     */
    public static boolean isTextVisible(String text, Vector2 pos, Vector2 size, int fontSize, Graphics g) {
        Font font = new Font("Serif", Font.PLAIN, fontSize);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();

        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        return textWidth <= size.x && textHeight <= size.y;
    }

    /**
     * Adjusts a given text to fit within the specified position and size.
     * If the text doesn't fit, this method will first attempt to reduce the font
     * size.
     * If the text still doesn't fit, it will be truncated.
     *
     * @param text The original text string to be adjusted.
     * @param pos  The starting position of the text.
     * @param size The size in which the text should fit.
     * @param g    The Graphics context, which provides font metrics for text
     *             measurement.
     * @return A string that fits within the specified size. This might be the
     *         original, resized or truncated text.
     */
    public static String adjustTextToFit(String text, Vector2 pos, Vector2 size, Graphics g) {
        // Check if the original text fits
        if (isTextVisible(text, pos, size, g.getFont().getSize(), g)) {
            return text;
        }

        // If the text doesn't fit, try to find an appropriate font size
        int adjustedFontSize = calcFontSize(text, pos, size, Vector2.zero, g);
        Font adjustedFont = new Font("Serif", Font.PLAIN, adjustedFontSize);
        g.setFont(adjustedFont);

        // Check if the text fits with the adjusted font size
        if (isTextVisible(text, pos, size, adjustedFontSize, g)) {
            return text;
        }

        // If the text still doesn't fit, start truncating it
        StringBuilder truncatedText = new StringBuilder(text);
        while (!isTextVisible(truncatedText.toString(), pos, size, adjustedFontSize, g) && truncatedText.length() > 0) {
            truncatedText.deleteCharAt(truncatedText.length() - 1);
        }

        // Add ellipsis to indicate truncation, but only if there's enough space for it
        if (truncatedText.length() > 3) {
            truncatedText.setLength(truncatedText.length() - 3);
            truncatedText.append("...");
        }

        return truncatedText.toString();
    }

    /**
     * Aligns the given text to the left of the specified area.
     *
     * @param text The text to align.
     * @param pos  The top-left position of the area.
     * @param size The size of the area.
     * @param g    The graphics context.
     * @return The position where the text should start to achieve left alignment.
     */
    public static Vector2 alignTextLeft(String text, Vector2 pos, Vector2 size, Graphics g) {
        return new Vector2(pos.x, calcMidTextY(text, pos, size, g));
    }

    /**
     * Aligns the given text to the right of the specified area.
     *
     * @param text The text to align.
     * @param pos  The top-left position of the area.
     * @param size The size of the area.
     * @param g    The graphics context.
     * @return The position where the text should start to achieve right alignment.
     */
    public static Vector2 alignTextRight(String text, Vector2 pos, Vector2 size, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        return new Vector2(pos.x + size.x - fm.stringWidth(text), calcMidTextY(text, pos, size, g));
    }

    /**
     * Aligns the given text to the center of the specified area.
     *
     * @param text The text to align.
     * @param pos  The top-left position of the area.
     * @param size The size of the area.
     * @param g    The graphics context.
     * @return The position where the text should start to achieve center alignment.
     */
    public static Vector2 alignTextCenter(String text, Vector2 pos, Vector2 size, Graphics g) {
        return calcMidText(text, pos, size, g);
    }

    /**
     * Calculates the total height required for rendering multiple lines of text.
     *
     * @param lines A list of text lines.
     * @param g     The graphics context.
     * @return The total height in pixels.
     */
    public static int getHeightOfMultilineText(List<String> lines, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        return fm.getHeight() * lines.size();
    }

    /**
     * Determines the optimal font size for rendering multiple lines of text within
     * a specified area.
     *
     * @param lines A list of text lines.
     * @param pos   The top-left position of the area.
     * @param size  The size of the area.
     * @param g     The graphics context.
     * @return The optimal font size.
     */
    public static int getOptimalFontSizeForMultiline(List<String> lines, Vector2 pos, Vector2 size, Graphics g) {
        int maxWidth = 0;
        for (String line : lines) {
            int lineWidth = g.getFontMetrics().stringWidth(line);
            if (lineWidth > maxWidth) {
                maxWidth = lineWidth;
            }
        }
        return calcFontSize(String.join("", lines), pos, new Vector2(maxWidth, size.y), g);
    }

    /**
     * Rotates and renders text at a specified angle.
     *
     * @param text  The text to render.
     * @param pos   The position where the text should start.
     * @param angle The angle of rotation in degrees.
     * @param g     The graphics context.
     */
    public static void rotateText(String text, Vector2 pos, double angle, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform originalTransform = g2d.getTransform();
        g2d.rotate(Math.toRadians(angle), pos.x, pos.y);
        g2d.drawString(text, pos.x, pos.y);
        g2d.setTransform(originalTransform);
    }

    /**
     * Renders text with a shadow effect.
     *
     * @param text         The text to render.
     * @param pos          The position where the text should start.
     * @param shadowOffset The offset distance of the shadow.
     * @param shadowColor  The color of the shadow.
     * @param g            The graphics context.
     */
    public static void addShadowToText(String text, Vector2 pos, int shadowOffset, Color shadowColor, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color originalColor = g2d.getColor();
        g2d.setColor(shadowColor);
        g2d.drawString(text, pos.x + shadowOffset, pos.y + shadowOffset);
        g2d.setColor(originalColor);
        g2d.drawString(text, pos.x, pos.y);
    }

    /**
     * Renders text with an outline effect.
     *
     * @param text         The text to render.
     * @param pos          The position where the text should start.
     * @param outlineColor The color of the outline.
     * @param g            The graphics context.
     */
    public static void addOutlineToText(String text, Vector2 pos, Color outlineColor, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D ig = image.createGraphics();
        FontRenderContext frc = ig.getFontRenderContext();
        Shape textShape = g2d.getFont().createGlyphVector(frc, text).getOutline();
        g2d.translate(pos.x, pos.y);
        g2d.setColor(outlineColor);
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(textShape);
        g2d.setColor(g2d.getColor());
        g2d.fill(textShape);
        g2d.translate(-pos.x, -pos.y);
    }

    /**
     * Sets the graphics context to use high quality rendering for text.
     *
     * @param g The graphics context.
     */
    public static void setHighQualityRendering(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * Sets the transparency level for rendering operations in the graphics context.
     *
     * @param g     The graphics context.
     * @param alpha The transparency level (0.0 = fully transparent, 1.0 = fully
     *              opaque).
     */
    public static void setAlpha(Graphics g, float alpha) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    /**
     * Sets the graphics context to use a gradient fill.
     *
     * @param g          The graphics context.
     * @param startPos   The start position of the gradient.
     * @param endPos     The end position of the gradient.
     * @param startColor The start color of the gradient.
     * @param endColor   The end color of the gradient.
     */
    public static void setGradientFill(Graphics g, Vector2 startPos, Vector2 endPos, Color startColor, Color endColor) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(startPos.x, startPos.y, startColor, endPos.x, endPos.y, endColor);
        g2d.setPaint(gradient);
    }

    // Private helper method to calculate vertical alignment for text.
    private static int calcMidTextY(String text, Vector2 pos, Vector2 size, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textHeight = fm.getHeight();
        return (int) (pos.y + ((size.y - textHeight) / 2) + fm.getAscent());
    }

}