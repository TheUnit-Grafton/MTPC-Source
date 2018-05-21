/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JSlider;
import static javax.swing.SwingConstants.VERTICAL;

/**
 *
 * @author lars
 */
public class BasicSlider extends JSlider {

    public static int MARGIN = 20;
    public static int ROUNDNESS = 10;
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
    public boolean vertical = false;
    public BufferedImage sliding_thing;
    public BufferedImage rendered_rect;

    static {
        //AA.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        AA.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        AA.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
    }

    private static final long serialVersionUID = 1L;

    public BasicSlider(int i) {
        super(i);
        vertical = i == VERTICAL;
        super.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), MARGIN));
        super.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //sliding_thing=new BufferedImage(super.getWidth() - MARGIN*2+1,super.getWidth() - MARGIN*2+1,BufferedImage.TYPE_4BYTE_ABGR);
    }

    @Override
    public void paintImmediately(int x, int y, int w, int h) {
        this.getGraphics().setClip(x, y, w, h);
        this.getGraphics().clearRect(x, y, w, h);
        //this.getGraphics().clearRect(0, 0, super.getWidth(), super.getHeight());
        this.paintComponent(this.getGraphics());
    }

    @Override
    public void paintImmediately(Rectangle r) {
        paintImmediately(r.x, r.y, r.width, r.height);
    }

    @Override
    public void paint(Graphics g) {
        if (vertical) {
            sliding_thing = new BufferedImage(super.getWidth() - MARGIN * 2 + 1, super.getWidth() - MARGIN * 2 + 1, BufferedImage.TYPE_4BYTE_ABGR);
        } else {
            sliding_thing = new BufferedImage(super.getHeight() - MARGIN * 2 + 1, super.getHeight() - MARGIN * 2 + 1, BufferedImage.TYPE_4BYTE_ABGR);

        }
        Graphics2D stg = sliding_thing.createGraphics();
        stg.setRenderingHints(AA);
        stg.setColor(BasicColorScheme.CS.bgp);
        stg.fillRoundRect(0, 0, sliding_thing.getWidth() - 1, sliding_thing.getHeight() - 1, ROUNDNESS * 2, ROUNDNESS * 2);
        stg.setColor(BasicColorScheme.CS.b);
        stg.drawRoundRect(0, 0, sliding_thing.getWidth() - 1, sliding_thing.getHeight() - 1, ROUNDNESS * 2, ROUNDNESS * 2);
        rendered_rect = new BufferedImage(super.getWidth(), super.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D rrg = rendered_rect.createGraphics();
        rrg.setRenderingHints(AA);
        rrg.setColor(BasicColorScheme.CS.b);
        rrg.drawRoundRect(MARGIN, MARGIN, super.getWidth() - MARGIN * 2, super.getHeight() - MARGIN * 2, ROUNDNESS, ROUNDNESS);
        g.setClip(null);
        g.clearRect(0, 0, super.getWidth(), super.getHeight());
        paintComponent(g);
    }

    @Override
    public void paintComponent(Graphics gw) {
        //this.getGraphics().setClip(0, 0, super.getWidth(), super.getHeight());
        /*Point p=MouseInfo.getPointerInfo().getLocation();
        Point q=this.getLocationOnScreen();
        p.x-=q.x;
        p.y-=q.y;
        System.out.println(p);*/
        Graphics2D g = (Graphics2D) gw;
        g.clearRect(0, 0, super.getWidth(), super.getHeight());
        Rectangle r = g.getClipBounds();
        g.setColor(BasicColorScheme.CS.alt_bg);
        g.fillRoundRect(MARGIN, MARGIN, super.getWidth() - MARGIN * 2, super.getHeight() - MARGIN * 2, ROUNDNESS, ROUNDNESS);
        g.setColor(BasicColorScheme.CS.bgon);
        if (vertical) {
            g.clipRect(MARGIN, MARGIN, super.getWidth() - MARGIN * 2, (int) ((super.getHeight() - MARGIN * 2) - (super.getValue() / (float) (super.getMaximum() - super.getMinimum())) * (super.getHeight() - MARGIN * 2)));
        } else {
            g.clipRect(MARGIN, MARGIN, (int) ((super.getValue() / (float) (super.getMaximum() - super.getMinimum())) * (super.getWidth() - MARGIN * 2)), super.getHeight() - MARGIN * 2);

        }
        //g.clipRect(MARGIN, MARGIN, super.getWidth() - MARGIN * 2, (int) ((super.getHeight() - MARGIN * 2) - (super.getValue() / (float) (super.getMaximum() - super.getMinimum())) * (super.getHeight() - MARGIN * 2)));
        g.fillRoundRect(MARGIN, MARGIN, super.getWidth() - MARGIN * 2, super.getHeight() - MARGIN * 2, ROUNDNESS, ROUNDNESS);
        //int w = m.stringWidth(super.getText());
        g.setClip(r);
        g.drawImage(rendered_rect, 0, 0, null);
        if (vertical) {
            g.drawImage(sliding_thing, MARGIN, -10 + MARGIN + (int) ((super.getHeight() - MARGIN * 2) - (super.getValue() / (float) (super.getMaximum() - super.getMinimum())) * (super.getHeight() - MARGIN * 2)), null);
            //g.drawRoundRect(2, 2+(int)(super.getHeight()-(super.getValue()/(float)(super.getMaximum()-super.getMinimum()))*super.getHeight()), super.getWidth() - 5, super.getWidth() - 5, ROUNDNESS*2, ROUNDNESS*2);
        } else {
            g.drawImage(sliding_thing, -10 + MARGIN + (int) ((super.getValue() / (float) (super.getMaximum() - super.getMinimum())) * (super.getWidth() - MARGIN * 2)), MARGIN, null);
        }
        //g.drawString(super.getText(), super.getHeight() - 1+MARGIN, m.getHeight());
        //super.paintComponent(g);
    }
}
