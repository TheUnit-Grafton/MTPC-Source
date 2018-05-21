/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author lars
 */
public class BasicEntry extends JTextField {

    public static int ROUNDNESS = 5;
    public static Color B = Color.BLACK;
    public static Color BON = Color.BLUE;
    public static Color BP = Color.GREEN;
    public static Color BD = Color.GRAY;
    public static Color BG = Color.WHITE;
    public static Color FG = Color.BLACK;
    public static Color BGON = Color.WHITE;
    public static Color FGON = Color.BLACK;
    public static Color BGP = Color.WHITE;
    public static Color FGP = Color.BLACK;
    public static Color BGD = Color.GRAY;
    public static Color FGD = Color.DARK_GRAY;
    public static Color M = Color.GREEN;
    public static Color C = Color.BLACK;

    class RoundBorder implements Border {

        public int r;
        public Color color;

        public RoundBorder(Color c, int radius) {
            this.color = c;
            this.r = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics gw, int x, int y, int width, int height) {
            Graphics2D g = (Graphics2D) gw;
            g.setColor(color);
            g.setRenderingHints(BasicRadioButton.AA);
            g.drawRoundRect(x, y, width - 1, height - 1, ROUNDNESS, ROUNDNESS);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(r, r, r, r);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

    }

    public BasicEntry(String s) {
        super(s);
        super.setBorder(new RoundBorder(BasicColorScheme.CS.b, 5));
        super.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        super.setCaretColor(BasicColorScheme.CS.fg);
        super.setForeground(BasicColorScheme.CS.fg);
        super.setBackground(BasicColorScheme.CS.alt_bg);
        super.setSelectionColor(BasicColorScheme.CS.bg);
        //addCL(this);
    }

    /*public static void addCL(BasicEntry b){
        b.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                b.setFont(b.getFont().deriveFont(b.getGraphics().getFontMetrics().getHeight()/((float)b.getHeight()-8)));
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }*/
    
    @Override
    public void paintComponent(Graphics gw) {
        if (super.hasFocus()) {
            super.setBorder(new RoundBorder(BasicColorScheme.CS.bg, 5));
        } else {
            super.setBorder(new RoundBorder(BasicColorScheme.CS.b, 5));
        }
        super.paintComponent(gw);
    }
    /*
        Color fg;
        Color bg;
        Color b;
        //g.setPaintMode();
        //g.clearRect(0, 0, super.getWidth(), super.getHeight());
        if (!super.isEnabled()) {
            bg = BGD;
            fg = FGD;
            b = BD;
        } else if (super.hasFocus()) {
            fg = FGP;
            bg = BGP;
            b = BP;

         else if (mp != null && this.contains(mp)) {
            fg = FGON;
            bg = BGON;
            b = BON;
        } else {
            fg = FG;
            bg = BG;
            b = B;
        }
        //g.setColor(bg);
        //g.fillRoundRect(0, 0, super.getWidth() - 1, super.getHeight() - 1, ROUNDNESS, ROUNDNESS);
        g.setRenderingHints(BasicTextField.AA);
        g.setColor(b);
        g.drawRoundRect(0, 0, super.getWidth() - 1, super.getHeight() - 1, ROUNDNESS, ROUNDNESS);
    }*/
}
