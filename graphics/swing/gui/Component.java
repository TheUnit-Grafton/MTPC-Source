package appguru.graphics.swing.gui;

public class Component extends appguru.graphics.swing.gui.BoundingBox {
    public byte state;
    public byte alignment;
    public appguru.graphics.swing.gui.ComponentListener listener;
    public Object value;
    public appguru.graphics.swing.gui.ContentPane content;
    
    public Component(appguru.graphics.swing.gui.BoundingBox a) {
        super(a);
    }
}
