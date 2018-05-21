package appguru.graphics.swing.gui;

public class Image extends appguru.graphics.swing.gui.Content {
    private double scalex;
    private double scaley;
    private double rotation;
    private java.awt.image.BufferedImage photo;
    
    public Image(java.awt.image.BufferedImage a) {
        super(new appguru.graphics.swing.gui.BoundingBox(0.0f, 0.0f, 0.0f, 0.0f));
        this.scalex = 1.0;
        this.scaley = 1.0;
        this.rotation = 0.0;
        this.setPhoto(a);
    }
    
    public Image(java.awt.image.BufferedImage a, double d, double d0, double d1) {
        super(new appguru.graphics.swing.gui.BoundingBox(0.0f, 0.0f, 0.0f, 0.0f));
        this.scalex = d;
        this.scaley = d0;
        this.rotation = d1;
        this.setPhoto(a);
    }
    
    public void setScale(double d, double d0) {
        this.scalex = d;
        this.scaley = d0;
        this.applyTransformations();
    }
    
    public void setTransformations(double d, double d0, double d1) {
        this.scalex = d;
        this.scaley = d0;
        this.rotation = d1;
        this.applyTransformations();
    }
    
    public void refresh(java.awt.image.BufferedImage a, double d, double d0, double d1) {
        this.scalex = d;
        this.scaley = d0;
        this.rotation = d1;
        this.photo = a;
        this.applyTransformations();
    }
    
    public double getScalex() {
        return this.scalex;
    }
    
    public void setScalex(double d) {
        this.scalex = d;
        this.applyTransformations();
    }
    
    public double getScaley() {
        return this.scaley;
    }
    
    public void setScaley(double d) {
        this.scaley = d;
        this.applyTransformations();
    }
    
    public double getRotation() {
        return this.rotation;
    }
    
    public void setRotation(double d) {
        this.rotation = d;
        this.applyTransformations();
    }
    
    public void applyTransformations() {
        java.awt.geom.AffineTransform a = new java.awt.geom.AffineTransform();
        a.scale(this.scalex, this.scaley);
        java.awt.image.AffineTransformOp a0 = new java.awt.image.AffineTransformOp(a, antialiasing);
        java.awt.geom.Rectangle2D a1 = a0.getBounds2D(this.photo);
        java.awt.image.BufferedImage a2 = new java.awt.image.BufferedImage((int)a1.getWidth(), (int)a1.getHeight(), 2);
        a0.filter(this.photo, a2);
        java.awt.geom.AffineTransform a3 = new java.awt.geom.AffineTransform();
        a3.translate((double)(a2.getWidth() / 2), (double)(a2.getHeight() / 2));
        a3.rotate(Math.toRadians(this.rotation));
        a3.translate((double)(-a2.getWidth() / 2), (double)(-a2.getHeight() / 2));
        java.awt.geom.Rectangle2D a4 = new java.awt.image.AffineTransformOp(a3, antialiasing).getBounds2D(a2);
        a3.translate(a4.getX(), a4.getY());
        java.awt.image.AffineTransformOp a5 = new java.awt.image.AffineTransformOp(a3, antialiasing);
        this.image = new java.awt.image.BufferedImage((int)a4.getWidth(), (int)a4.getHeight(), 2);
        a5.filter(a2, this.image);
        this.bounds.w = (float)this.image.getWidth();
        this.bounds.h = (float)this.image.getHeight();
        try {
            javax.imageio.ImageIO.write((java.awt.image.RenderedImage)(Object)this.image, "PNG", new java.io.File("test.png"));
        } catch(java.io.IOException a6) {
            java.util.logging.Logger.getLogger(appguru.graphics.swing.gui.Text.class.getName()).log(java.util.logging.Level.SEVERE, (String)null, (Throwable)a6);
        }
    }
    
    public java.awt.image.BufferedImage getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(java.awt.image.BufferedImage a) {
        this.photo = a;
        this.applyTransformations();
    }
}
