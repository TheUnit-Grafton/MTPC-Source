/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import java.awt.Color;

/**
 *
 * @author lars
 */
public class BasicColorScheme {
    public static boolean CS_ENABLED=true;
    public static BasicColorScheme CS=new BasicColorScheme(new Color(0,0,225));
    public Color bg;
    public Color alt_bg;
    public Color bgp;
    public Color bgon;
    public Color b;
    public Color fg;
    public Color dis_bg;
    public Color dis_fg;
    public Color dis_b;

    public BasicColorScheme(Color bg, Color bgp, Color bgon, Color b, Color fg) {
        this.bg = bg;
        this.bgp = bgp;
        this.bgon = bgon;
        this.b = b;
        this.fg = fg;
    }
    
    public BasicColorScheme(Color c) {
        this.bg=c;
        this.bgon=new Color(Math.min(255,c.getRed()+30),Math.min(255,c.getGreen()+30),Math.min(255,c.getBlue()+30));
        this.bgp=new Color(Math.max(0,c.getRed()-60),Math.max(0,c.getGreen()-60),Math.max(0,c.getBlue()-60));
        this.b=Color.BLACK;
        this.fg=Color.BLACK;
        this.alt_bg=Color.WHITE;
        this.dis_bg=Color.LIGHT_GRAY;
        this.dis_fg=new Color(75,75,75);
        this.dis_b=new Color(75,75,75);
    }
    
}
