package appguru.graphics.swing.gui;

public class Text extends appguru.graphics.swing.gui.Content {
    final public static java.awt.Color MARKING_FG;
    final public static java.awt.Color MARKING_BG;
    final public static boolean DRAWTYPE_BOXLIKE = false;
    public int font;
    private String text;
    private String[] lines;
    private int[] widths;
    
    public Text(String s, int i) {
        super(new appguru.graphics.swing.gui.BoundingBox(0.0f, 0.0f, 0.0f, 0.0f));
        this.font = i;
        this.setText(s);
    }
    
    public int getFont() {
        return this.font;
    }
    
    public void setFont(int i) {
        this.font = i;
        int[] a = this.getBoundingBox();
        this.bounds.w = (float)(a[0]);
        this.bounds.h = (float)(a[1]);
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(String s) {
        this.text = s;
        this.lines = s.split("\n");
        int[] a = this.getBoundingBox();
        this.bounds.w = (float)(a[0]);
        this.bounds.h = (float)(a[1]);
        this.image = new java.awt.image.BufferedImage((a[0] != 0) ? 1 : 0, (a[1] != 0) ? 1 : 0, 2);
        this.graphics = this.image.createGraphics();
        this.graphics.setRenderingHints((java.util.Map)(Object)antialiasing);
        this.mark(2, 6);
        try {
            javax.imageio.ImageIO.write((java.awt.image.RenderedImage)(Object)this.image, "PNG", new java.io.File("test.png"));
        } catch(java.io.IOException a0) {
            java.util.logging.Logger.getLogger(appguru.graphics.swing.gui.Text.class.getName()).log(java.util.logging.Level.SEVERE, (String)null, (Throwable)a0);
        }
    }
    
    public void mark(int i, int i0) {
        int i1 = appguru.graphics.swing.gui.Gui.fonts.getHeight(this.font);
        float f = (float)i1 * 0.25f;
        this.widths = new int[this.lines.length];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while(i4 < this.lines.length) {
            String s = this.lines[i4];
            i2 = i2 + i1;
            int i5 = 0;
            int i6 = 0;
            while(i6 < s.length()) {
                boolean b = i3 >= i && i3 < i0;
                int i7 = s.charAt(i6);
                if (b) {
                    this.graphics.setColor(MARKING_BG);
                } else
                {
                    this.graphics.setColor(BG);
                }
                this.graphics.fillRect((int)this.bounds.x + i5, (int)this.bounds.y + i2 - i1, appguru.graphics.swing.gui.Gui.fonts.getWidth(this.font, (char)i7), i1);
                if (b) {
                    this.graphics.setColor(MARKING_FG);
                } else
                {
                    this.graphics.setColor(FG);
                }
                java.awt.Graphics2D a = this.graphics;
                char[] a0 = new char[1];
                a0[0] = (char)i7;
                a.drawString(new String(a0), (float)((int)this.bounds.x + i5), (float)((int)this.bounds.y + i2) - f);
                i5 = i5 + appguru.graphics.swing.gui.Gui.fonts.getWidth(this.font, (char)i7);
                i3 = i3 + 1;
                i6 = i6 + 1;
            }
            this.widths[i4] = i5;
            i4 = i4 + 1;
        }
    }
    
    public int[] getBoundingBox() {
        String[] a = this.text.split("\n");
        int i = appguru.graphics.swing.gui.Gui.fonts.getHeight(this.font) * a.length;
        int i0 = 0;
        int i1 = 0;
        while(true) {
            int i2 = a.length;
            int i3 = i0;
            if (i1 >= i2) {
                int[] a0 = new int[2];
                a0[0] = i0;
                a0[1] = i;
                return a0;
            }
            String s = a[i1];
            i0 = 0;
            int i4 = 0;
            while(i4 < s.length()) {
                int i5 = s.charAt(i4);
                i0 = i0 + appguru.graphics.swing.gui.Gui.fonts.getWidth(this.font, (char)i5);
                i4 = i4 + 1;
            }
            if (i0 <= i3) {
                i0 = i3;
            }
            i1 = i1 + 1;
        }
    }
    
    public void blit() {
        int i = appguru.graphics.swing.gui.Gui.fonts.getHeight(this.font);
        float f = (float)i * 0.25f;
        this.widths = new int[this.lines.length];
        int i0 = 0;
        int i1 = 0;
        while(i1 < this.lines.length) {
            String s = this.lines[i1];
            i0 = i0 + i;
            int i2 = 0;
            int i3 = 0;
            while(i3 < s.length()) {
                int i4 = s.charAt(i3);
                this.graphics.setColor(BG);
                this.graphics.fillRect((int)this.bounds.x + i2, (int)this.bounds.y + i0 - i, appguru.graphics.swing.gui.Gui.fonts.getWidth(this.font, (char)i4), i);
                this.graphics.setColor(FG);
                java.awt.Graphics2D a = this.graphics;
                char[] a0 = new char[1];
                a0[0] = (char)i4;
                a.drawString(new String(a0), (float)((int)this.bounds.x + i2), (float)((int)this.bounds.y + i0) - f);
                i2 = i2 + appguru.graphics.swing.gui.Gui.fonts.getWidth(this.font, (char)i4);
                i3 = i3 + 1;
            }
            this.widths[i1] = i2;
            i1 = i1 + 1;
        }
    }
    
    public int cursor(appguru.math.Vector2 a) {
        int i = appguru.graphics.swing.gui.Gui.fonts.getHeight(this.font);
        if (!this.bounds.collidepoint(a)) {
            return -2;
        }
        int i0 = 0;
        int i1 = 0;
        while(i1 < this.lines.length) {
            if (new appguru.math.Rect(this.bounds.x, this.bounds.y + (float)(i1 * i), (float)(this.widths[i1]), (float)i).collidepoint(a)) {
                double d = (double)this.bounds.x;
                int i2 = 0;
                while(i2 < this.lines[i1].length()) {
                    int i3 = this.lines[i1].charAt(i2);
                    double d0 = (double)appguru.graphics.swing.gui.Gui.fonts.getWidth(this.font, (char)i3);
                    if ((double)a.x > d && (double)a.x < d + d0) {
                        if ((double)a.x < d + d0 / 2.0) {
                            return i0;
                        }
                        return i0 + 1;
                    }
                    i0 = i0 + 1;
                    d = d + d0;
                    i2 = i2 + 1;
                }
            } else
            {
                i0 = i0 + this.lines[i1].length();
            }
            i1 = i1 + 1;
        }
        return -1;
    }
    
    static {
        MARKING_FG = java.awt.Color.RED;
        MARKING_BG = java.awt.Color.WHITE;
    }
}
