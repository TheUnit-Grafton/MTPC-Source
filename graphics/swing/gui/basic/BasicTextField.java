/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;

/**
 *
 * @author lars
 */
public class BasicTextField extends JTextPane {

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
    public static int DEF_H = 40;
    public static int DEF_W = 100;

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

    public BasicTextField(String s) {
        super();
        DefaultStyledDocument d = new DefaultStyledDocument(new StyleContext());
        try {
            d.insertString(0, s, null);
        } catch (BadLocationException ex) {
            System.out.println("FAGGED");
        }
        super.setPreferredSize(new Dimension(DEF_W, DEF_H));
        super.setStyledDocument(d);
        super.setBorder(new RoundBorder(B, 5));
        super.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        super.setCaretColor(C);
        super.setForeground(FG);
        super.setBackground(BG);
        super.setSelectionColor(Color.GREEN);
    }

    @Override
    public void paintComponent(Graphics gw) {
        if (super.hasFocus()) {
            super.setBorder(new RoundBorder(BP, 5));
        } else {
            super.setBorder(new RoundBorder(B, 5));
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
