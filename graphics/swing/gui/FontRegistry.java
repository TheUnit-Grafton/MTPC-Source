package appguru.graphics.swing.gui;

public class FontRegistry {
    public static java.awt.Graphics2D graphics;
    public java.util.ArrayList fonts;
    public java.util.ArrayList metrics;
    
    public FontRegistry() {
        super();
        this.fonts = new java.util.ArrayList();
        this.metrics = new java.util.ArrayList();
        graphics = new java.awt.image.BufferedImage(1, 1, 2).createGraphics();
        graphics.setRenderingHints((java.util.Map)(Object)appguru.graphics.swing.gui.Content.antialiasing);
    }
    
    public void loadFont(java.awt.Font a) {
        this.fonts.add((Object)a);
        this.metrics.add((Object)graphics.getFontMetrics(a));
    }
    
    public int getHeight(int i) {
        return ((java.awt.FontMetrics)this.metrics.get(i)).getHeight();
    }
    
    public int getWidth(int i, char a) {
        java.awt.FontMetrics a0 = (java.awt.FontMetrics)this.metrics.get(i);
        int i0 = a;
        return a0.charWidth((char)i0);
    }
}
