package appguru.graphics.swing.gui;

public class Gui {
    public static appguru.graphics.swing.gui.FontRegistry fonts;
    
    public Gui() {
        super();
    }
    
    public void init() {
        fonts = new appguru.graphics.swing.gui.FontRegistry();
        fonts.loadFont(new java.awt.Font("sans", 0, 12));
    }
}
