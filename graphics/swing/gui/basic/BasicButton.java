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
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author lars
 */
public class BasicButton extends JButton {

    public static int MARGIN = 4;
    public static int ROUNDNESS = 10;
    public static Color B = Color.BLACK;
    public static Color BON = Color.BLACK;
    public static Color BP = Color.BLACK;
    public static Color BD = Color.GRAY;
    public static Color BG = new Color(0, 235, 0);
    public static Color FG = Color.BLACK;
    public static Color BGON = new Color(0, 255, 0);
    public static Color FGON = Color.BLACK;
    public static Color BGP = new Color(0, 195, 0);
    public static Color FGP = Color.BLACK;
    public static Color BGD = Color.GRAY;
    public static Color FGD = Color.DARK_GRAY;
    public static HashMap AA = new HashMap();

    static {
        AA.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        AA.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private static final long serialVersionUID = 1L;

    public BasicButton(String s) {
        super(s);
        super.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), MARGIN));
        super.setMargin(new Insets(MARGIN,MARGIN,MARGIN,MARGIN));
        super.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /*@Override
    public void paintComponent(Graphics gw) {
        Graphics2D g = (Graphics2D) gw;
        g.setRenderingHints(AA);
        Color fg;
        Color bg;
        Color b;
        g.setPaintMode();
        g.clearRect(0, 0, super.getWidth(), super.getHeight());
        if (!super.getModel().isEnabled()) {
            bg = BGD;
            fg = FGD;
            b = BD;
        } else if (super.getModel().isPressed()) {
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
        }
        g.setColor(bg);
        g.fillRoundRect(0, 0, super.getWidth() - 1, super.getHeight() - 1, ROUNDNESS, ROUNDNESS);
        g.setColor(b);
        g.drawRoundRect(0, 0, super.getWidth() - 1, super.getHeight() - 1, ROUNDNESS, ROUNDNESS);
        FontMetrics m = g.getFontMetrics();
        int w = m.stringWidth(super.getText());
        g.setColor(fg);
        g.drawString(super.getText(), (super.getWidth() - w) / 2,-3+m.getHeight()+((super.getHeight()-m.getHeight())/2.0f));
        //super.paintComponent(g);
    }*/
    
    @Override
    public void paintComponent(Graphics gw) {
        Graphics2D g = (Graphics2D) gw;
        g.setRenderingHints(AA);
        g.setPaintMode();
        g.setBackground(getParent().getBackground());
        g.clearRect(0, 0,super.getWidth(),super.getHeight());
        g.setColor(super.getModel().isRollover() ? (super.getModel().isPressed() ? BasicColorScheme.CS.bgp: BasicColorScheme.CS.bgon):BasicColorScheme.CS.bg);
        g.fillRoundRect(0, 0, super.getWidth() - 1, super.getHeight() - 1, ROUNDNESS, ROUNDNESS);
        g.setColor(BasicColorScheme.CS.b);
        g.drawRoundRect(0, 0, super.getWidth() - 1, super.getHeight() - 1, ROUNDNESS, ROUNDNESS);
        FontMetrics m = g.getFontMetrics();
        int w = m.stringWidth(super.getText());
        g.setColor(BasicColorScheme.CS.fg);
        g.drawString(super.getText(), (super.getWidth() - w) / 2,-3+m.getHeight()+((super.getHeight()-m.getHeight())/2.0f));
        //super.paintComponent(g);
    }

}
