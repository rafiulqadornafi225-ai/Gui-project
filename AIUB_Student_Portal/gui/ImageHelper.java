package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
public class ImageHelper {

    // AIUB Brand Colors
    public static final Color AIUB_BLUE = new Color(13, 37, 71);    // #0D2547 Deep AIUB Navy
    public static final Color AIUB_GOLD = new Color(244, 208, 111); // #F4D06F Golden Yellow
    public static final Color AIUB_CYAN = new Color(74, 144, 226);  // Accent Blue

    //  AIUB Logo (size: width x height)
    public static ImageIcon getAIUBLogo(int width, int height) {
        String[] possiblePaths = {
            "assets/logo.png",
            "assets/aiub_logo.png",
            "images/logo.png",
            "logo.png"
        };

        for (String p : possiblePaths) {
            File f = new File(p);
            if (f.exists()) {
                ImageIcon icon = new ImageIcon(p);
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        }
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int cx = width / 2;
        int cy = height / 2;
        int radius = Math.min(width, height) / 2 - 4;
        g2.setColor(AIUB_BLUE);
        g2.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);

        g2.setColor(AIUB_GOLD);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(cx - radius + 3, cy - radius + 3, (radius - 3) * 2, (radius - 3) * 2);

        
        int innerR = (int)(radius * 0.72);
        g2.setColor(new Color(225, 240, 255));
        g2.fillOval(cx - innerR, cy - innerR, innerR * 2, innerR * 2);
        g2.setColor(AIUB_BLUE);
        int capW = (int)(innerR * 0.9);
        int capH = (int)(innerR * 0.35);
        int[] xPoints = { cx, cx + capW / 2, cx, cx - capW / 2 };
        int[] yPoints = { cy - capH, cy - capH / 2, cy, cy - capH / 2 };
        g2.fillPolygon(xPoints, yPoints, 4);
        g2.setColor(AIUB_GOLD);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(cx, cy - capH / 2, cx + capW / 2 + 4, cy);
        g2.fillOval(cx + capW / 2 + 2, cy - 1, 5, 5);

        // Text AIUB
        g2.setColor(AIUB_BLUE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, Math.max(12, width / 7)));
        FontMetrics fm = g2.getFontMetrics();
        String text = "AIUB";
        int tx = cx - (fm.stringWidth(text) / 2);
        int ty = cy + fm.getAscent() / 2 + (innerR / 3);
        g2.drawString(text, tx, ty);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, Math.max(8, width / 14)));
        FontMetrics fm2 = g2.getFontMetrics();
        String yr = "EST. 1994";
        g2.drawString(yr, cx - (fm2.stringWidth(yr) / 2), cy + innerR - 2);

        g2.dispose();
        return new ImageIcon(img);
    }
    public static ImageIcon getCampusBanner(int width, int height) {
        BufferedImage banner = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = banner.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, new Color(13, 37, 71), width, height, new Color(24, 76, 140));
        g2.setPaint(gp);
        g2.fillRect(0, 0, width, height);
        g2.setColor(new Color(255, 255, 255, 25));
        g2.fillOval(width - height * 2, -height / 2, height * 3, height * 2);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(width - height * 2, -height / 2, height * 3, height * 2);
        g2.setColor(AIUB_GOLD);
        g2.fillRect(0, height - 4, width, 4);

        g2.dispose();
        return new ImageIcon(banner);
    }
}
