package appguru.graphics.swing.gui;

public class Content extends appguru.graphics.swing.gui.BoundingBox {
    final public static java.awt.Color transparent;
    final public static java.awt.Color FG;
    final public static java.awt.Color BG;
    final public static java.awt.RenderingHints antialiasing;
    public java.awt.image.BufferedImage image;
    public java.awt.Graphics2D graphics;
    
    public Content(appguru.graphics.swing.gui.BoundingBox a) {
        super(a);
    }
    
    static {
        transparent = new java.awt.Color(0, 0, 0, 0);
        FG = java.awt.Color.BLACK;
        BG = java.awt.Color.GREEN;
        antialiasing = new java.awt.RenderingHints(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    }
}
