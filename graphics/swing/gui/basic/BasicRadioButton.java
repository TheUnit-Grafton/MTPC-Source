/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JRadioButton;

/**
 *
 * @author lars
 */
public class BasicRadioButton extends JRadioButton {

    public static int MARGIN = 4;
    public static int ROUNDNESS = 20;
    public static Color B = Color.BLACK;
    public static Color BON = Color.BLACK;
    public static Color BP = Color.BLACK;
    public static Color BD = Color.GRAY;
    public static Color BG = Color.WHITE;
    public static Color FG = Color.BLACK;
    public static Color BGON = new Color(0, 255, 0);
    public static Color FGON = Color.BLACK;
    public static Color BGP = new Color(0, 195, 0);
    public static Color FGP = Color.BLACK;
    public static Color BGD = Color.GRAY;
    public static Color FGD = Color.DARK_GRAY;
    public static HashMap AA = new HashMap();
    public boolean triggered = false;

    static {
        AA.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        AA.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private static final long serialVersionUID = 1L;

    public BasicRadioButton(String s) {
        super(s);
        super.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), MARGIN));
        super.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /*public void paintComponent(Graphics gw) {
        Graphics2D g = (Graphics2D) gw;
        g.setRenderingHints(AA);
        Color fg;
        Color bg;
        Color b;
        g.setPaintMode();
        g.clearRect(0, 0, super.getWidth(), super.getHeight());
        boolean point = true;
        if (!super.getModel().isEnabled()) {
            bg = BGD;
            fg = FGD;
            b = BD;
        } else if (super.getModel().isSelected()) {
            fg = FGP;
            bg = BGP;
            b = BP;
        } else if (super.getModel().isRollover()) {
            fg = FGON;
            bg = BGON;
            b = BON;

        } else {
            fg = FG;
            bg = BG;
            b = B;
            point = false;
        }
        g.setColor(bg);
        g.fillOval(MARGIN, MARGIN, super.getHeight() - MARGIN * 2, super.getHeight() - MARGIN * 2);
        if (point) {
            if (!super.getModel().isSelected()) {
            g.setColor(new Color(0,0,0,127));
            }
            else {
                g.setColor(Color.BLACK);
            }
            g.fillOval(MARGIN + 5, MARGIN + 5, super.getHeight() - MARGIN * 2 - 10, super.getHeight() - MARGIN * 2 - 10);
        }
        g.setColor(b);
        g.drawOval(MARGIN, MARGIN, super.getHeight() - MARGIN * 2, super.getHeight() - MARGIN * 2);
        FontMetrics m = g.getFontMetrics();
        int w = m.stringWidth(super.getText());
        g.setColor(fg);
        g.drawString(super.getText(), super.getHeight() - 1 + MARGIN, m.getHeight());
        //super.paintComponent(g);
    }
     */
    public void paintComponent(Graphics gw) {
        Graphics2D g = (Graphics2D) gw;
        g.setRenderingHints(AA);
        g.setPaintMode();
        g.clearRect(0, 0, super.getWidth(), super.getHeight());
        boolean point = true;
        g.setColor(!super.getModel().isRollover() ? (super.getModel().isSelected() ? BasicColorScheme.CS.bg : BasicColorScheme.CS.alt_bg) : BasicColorScheme.CS.bgon);
        g.fillOval(MARGIN, MARGIN, super.getHeight() - MARGIN * 2, super.getHeight() - MARGIN * 2);
        label0: {
            if (super.getModel().isRollover()) {
                g.setColor(new Color(0, 0, 0, 127));
            } else if (super.getModel().isSelected()) {
                g.setColor(Color.BLACK);
            }
            else {
                break label0;
            }
            g.fillOval(MARGIN + 5, MARGIN + 5, super.getHeight() - MARGIN * 2 - 10, super.getHeight() - MARGIN * 2 - 10);
        }
        g.setColor(BasicColorScheme.CS.b);
        g.drawOval(MARGIN, MARGIN, super.getHeight() - MARGIN * 2, super.getHeight() - MARGIN * 2);
        FontMetrics m = g.getFontMetrics();
        int w = m.stringWidth(super.getText());
        g.setColor(BasicColorScheme.CS.fg);
        g.drawString(super.getText(), super.getHeight() - 1 + MARGIN, m.getHeight());
        //super.paintComponent(g);
    }

}
