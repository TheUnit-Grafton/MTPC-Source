package appguru.math;

public class Circle {
    public appguru.math.Vector2 cp;
    public float r;
    
    public Circle(appguru.math.Vector2 a, float f) {
        super();
        this.cp = a;
        this.r = f;
    }
    
    public boolean oncircle(appguru.math.Vector2 a) {
        float f = this.cp.x - a.x;
        float f0 = this.cp.y - a.y;
        return Math.sqrt((double)(f * f + f0 * f0)) <= (double)this.r;
    }
    
    public float distance_to_line(float f, float f0, float f1, float f2) {
        float f3 = appguru.helper.MathHelper.distance(this.cp.x - f, this.cp.y - f0);
        float f4 = appguru.helper.MathHelper.distance(this.cp.x - f1, this.cp.y - f2);
        float f5 = appguru.helper.MathHelper.distance(f - f1, f0 - f2);
        float f6 = (f3 + f4 + f5) / 2f;
        return (float)Math.sqrt((double)(f6 * (f6 - f3) * (f6 - f4) * (f6 - f5))) / f5 * 2f;
    }
    
    public boolean colliderect(appguru.math.Rect a) {
        boolean b = false;
        appguru.math.Rect a0 = new appguru.math.Rect(this.cp.x - this.r, this.cp.y - this.r, this.cp.x + this.r, this.cp.y + this.r);
        appguru.math.Vector2[] a1 = new appguru.math.Vector2[4];
        a1[0] = new appguru.math.Vector2(this.cp.x - this.r, this.cp.y);
        a1[1] = new appguru.math.Vector2(this.cp.x + this.r, this.cp.y);
        a1[2] = new appguru.math.Vector2(this.cp.x, this.cp.y - this.r);
        a1[3] = new appguru.math.Vector2(this.cp.x, this.cp.y + this.r);
        if (a.collidepoint(this.cp)) {
            return true;
        }
        int i = a1.length;
        int i0 = 0;
        while(i0 < i) {
            if (a.collidepoint(a1[i0])) {
                return true;
            }
            i0 = i0 + 1;
        }
        if (!a0.colliderect(a)) {
            return false;
        }
        float f = this.distance_to_line(a.x, a.y, a.x + a.w, a.y);
        float f0 = this.r;
        int i1 = (f < f0) ? -1 : (f == f0) ? 0 : 1;
        label2: {
            label0: {
                label1: {
                    if (i1 < 0) {
                        break label1;
                    }
                    if (this.distance_to_line(a.x + a.w, a.y, a.x + a.w, a.y + a.h) < this.r) {
                        break label1;
                    }
                    if (this.distance_to_line(a.x, a.y + a.h, a.x + a.w, a.y + a.h) < this.r) {
                        break label1;
                    }
                    if (!(this.distance_to_line(a.x, a.y, a.x, a.y + a.h) < this.r)) {
                        break label0;
                    }
                }
                b = true;
                break label2;
            }
            b = false;
        }
        return b;
    }
}
