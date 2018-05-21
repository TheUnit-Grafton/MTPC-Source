package appguru.graphics.swing.gui;

public class BoundingBox {
    public appguru.math.Rect bounds;
    public appguru.math.Rect padding;
    
    public BoundingBox(appguru.graphics.swing.gui.BoundingBox a) {
        super();
        this.bounds = a.bounds;
        this.padding = a.padding;
    }
    
    public BoundingBox(float f, float f0, float f1, float f2) {
        super();
        this.bounds = new appguru.math.Rect(f, f0, f1, f2);
        this.padding = new appguru.math.Rect(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public appguru.math.Rect getPadding() {
        return new appguru.math.Rect(this.bounds.x - this.padding.x, this.bounds.y - this.padding.y, this.bounds.w + this.padding.w, this.bounds.h + this.padding.h);
    }
    
    public appguru.graphics.swing.gui.BoundingBox arrange(appguru.graphics.swing.gui.BoundingBox a, short a0) {
        appguru.graphics.swing.gui.BoundingBox a1 = new appguru.graphics.swing.gui.BoundingBox(a);
        this.getPadding();
        a.getPadding();
        int i = a0;
        switch(i) {
            case 3: {
                a1.bounds.y = this.bounds.y + this.bounds.h;
                a1.bounds.x = this.bounds.x + this.bounds.w / 2f - a1.bounds.w / 2f;
                break;
            }
            case 2: {
                a1.bounds.x = this.bounds.x + this.bounds.w;
                a1.bounds.y = this.bounds.y + this.bounds.h / 2f - a1.bounds.h / 2f;
                break;
            }
            case 1: {
                a1.bounds.y = this.bounds.y - a1.bounds.h;
                a1.bounds.x = this.bounds.x + this.bounds.w / 2f - a1.bounds.w / 2f;
                break;
            }
            case 0: {
                a1.bounds.x = this.bounds.x - a1.bounds.w;
                a1.bounds.y = this.bounds.y + this.bounds.h / 2f - a1.bounds.h / 2f;
                break;
            }
        }
        return null;
    }
}
