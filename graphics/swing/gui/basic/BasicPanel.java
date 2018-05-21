/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
/**
 *
 * @author lars
 */
public class BasicPanel extends JPanel{
    public boolean rounded=false;
    public boolean border=false;
    public BasicPanel() {
        super();
        super.setBackground(Color.WHITE);
    }
    public BasicPanel(FlowLayout fl) {
        super(fl);
        super.setBackground(Color.WHITE);
    }
    public BasicPanel(boolean border) {
        super();
        //rounded=true;
        //this.border=border;
        //setBorder(new BasicRoundBorder(BasicColorScheme.CS.b,5));
        super.setBackground(Color.WHITE);
    }
    public BasicPanel(FlowLayout fl,boolean rounded) {
        super(fl);
        this.rounded=rounded;
        super.setBackground(Color.WHITE);
    }
    public BasicPanel(Component... comps) {
        super(new FlowLayout(FlowLayout.CENTER));
        super.setBackground(Color.WHITE);
        for (Component comp:comps) {
            add(comp);
        }
    }
    public void add(Component... comps) {
        for (Component comp:comps) {
            this.add(comp);
        }
    }
    @Override
    public void paintComponents(Graphics g) {
        if (rounded) {
        g.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
        }
        super.paintComponents(g);
        if (border) {
            g.setColor(Color.YELLOW);
            g.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 7,7);
        }
    }
    @Override
    public void paint(Graphics g) {
        if (rounded) {
        g.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
        }
        super.paint(g);
        if (border) {
            g.setColor(Color.YELLOW);
            g.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 7,7);
        }
    }
    @Override
    public void paintAll(Graphics g) {
        if (rounded) {
        g.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
        }
        super.paintAll(g);
        if (border) {
            g.setColor(Color.YELLOW);
            g.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 7,7);
        }
    }
    @Override
    public void paintImmediately(Rectangle r) {
        paintImmediately(r.x,r.y,r.width,r.height);
    }
    @Override
    public void paintImmediately(int x, int y, int w, int h)  {
        System.out.println("PAINTED IMMEDIATELY");
        super.paintImmediately(x,y,w,h);
    }
}
