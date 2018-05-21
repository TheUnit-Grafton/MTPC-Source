package appguru.math;

public class Rect {
    public float x;
    public float y;
    public float w;
    public float h;
    public appguru.math.Vector2[] p;
    
    public Rect(float f, float f0, float f1, float f2) {
        super();
        this.x = f;
        this.y = f0;
        this.w = f1;
        this.h = f2;
        this.p = new appguru.math.Vector2[4];
        this.p[0] = new appguru.math.Vector2(f, f0);
        this.p[1] = new appguru.math.Vector2(f + f1, f0);
        this.p[2] = new appguru.math.Vector2(f + f1, f0 + f2);
        this.p[3] = new appguru.math.Vector2(f, f0 + f2);
    }
    
    public static boolean cp(appguru.math.Rect a, appguru.math.Vector2 a0) {
        boolean b = false;
        float f = a0.x;
        float f0 = a.x;
        int i = (f > f0) ? 1 : (f == f0) ? 0 : -1;
        label2: {
            label0: {
                label1: {
                    if (i <= 0) {
                        break label1;
                    }
                    if (!(a0.x < a.x + a.w)) {
                        break label1;
                    }
                    if (!(a0.y > a.y)) {
                        break label1;
                    }
                    if (a0.y < a.y + a.h) {
                        break label0;
                    }
                }
                b = false;
                break label2;
            }
            b = true;
        }
        return b;
    }
    
    public static boolean cr(appguru.math.Rect a, appguru.math.Rect a0) {
        boolean b = false;
        boolean b0 = false;
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean b4 = appguru.math.Rect.cp(a, a0.p[0]);
        label20: {
            label18: {
                label19: {
                    if (b4) {
                        break label19;
                    }
                    if (appguru.math.Rect.cp(a, a0.p[1])) {
                        break label19;
                    }
                    if (appguru.math.Rect.cp(a, a0.p[2])) {
                        break label19;
                    }
                    if (appguru.math.Rect.cp(a, a0.p[3])) {
                        break label19;
                    }
                    if (appguru.math.Rect.cp(a0, a.p[0])) {
                        break label19;
                    }
                    if (appguru.math.Rect.cp(a0, a.p[1])) {
                        break label19;
                    }
                    if (appguru.math.Rect.cp(a0, a.p[2])) {
                        break label19;
                    }
                    if (!appguru.math.Rect.cp(a0, a.p[3])) {
                        break label18;
                    }
                }
                b = true;
                break label20;
            }
            b = false;
        }
        float f = a0.y;
        float f0 = a.y;
        int i = (f > f0) ? 1 : (f == f0) ? 0 : -1;
        label17: {
            label12: {
                label13: {
                    label15: {
                        label16: {
                            if (i <= 0) {
                                break label16;
                            }
                            if (a0.y < a.y + a.h) {
                                break label15;
                            }
                        }
                        if (!(a0.y + a0.h > a.y)) {
                            break label13;
                        }
                        if (!(a0.y + a0.h < a.y + a.h)) {
                            break label13;
                        }
                    }
                    float f1 = a0.x;
                    float f2 = a.x;
                    int i0 = (f1 > f2) ? 1 : (f1 == f2) ? 0 : -1;
                    label14: {
                        if (i0 <= 0) {
                            break label14;
                        }
                        if (a0.x < a.x + a.w) {
                            break label12;
                        }
                    }
                    if (!(a0.x + a0.w > a.x)) {
                        break label13;
                    }
                    if (a0.x + a0.w < a.x + a.w) {
                        break label12;
                    }
                }
                b0 = false;
                break label17;
            }
            b0 = true;
        }
        float f3 = a.y;
        float f4 = a0.y;
        int i1 = (f3 > f4) ? 1 : (f3 == f4) ? 0 : -1;
        label11: {
            label6: {
                label7: {
                    label9: {
                        label10: {
                            if (i1 <= 0) {
                                break label10;
                            }
                            if (a.y < a0.y + a0.h) {
                                break label9;
                            }
                        }
                        if (!(a.y + a.h > a0.y)) {
                            break label7;
                        }
                        if (!(a.y + a.h < a0.y + a0.h)) {
                            break label7;
                        }
                    }
                    float f5 = a.x;
                    float f6 = a0.x;
                    int i2 = (f5 > f6) ? 1 : (f5 == f6) ? 0 : -1;
                    label8: {
                        if (i2 <= 0) {
                            break label8;
                        }
                        if (a.x < a0.x + a0.w) {
                            break label6;
                        }
                    }
                    if (!(a.x + a.w > a0.x)) {
                        break label7;
                    }
                    if (a.x + a.w < a0.x + a0.w) {
                        break label6;
                    }
                }
                b1 = false;
                break label11;
            }
            b1 = true;
        }
        float f7 = a.x;
        float f8 = a0.x;
        int i3 = (f7 > f8) ? 1 : (f7 == f8) ? 0 : -1;
        label5: {
            label3: {
                label4: {
                    if (i3 != 0) {
                        break label4;
                    }
                    if (a.y != a0.y) {
                        break label4;
                    }
                    if (a.w != a0.w) {
                        break label4;
                    }
                    if (a.h == a0.h) {
                        break label3;
                    }
                }
                b2 = false;
                break label5;
            }
            b2 = true;
        }
        label2: {
            label0: {
                label1: {
                    if (b0) {
                        break label1;
                    }
                    if (b1) {
                        break label1;
                    }
                    if (b) {
                        break label1;
                    }
                    if (!b2) {
                        break label0;
                    }
                }
                b3 = true;
                break label2;
            }
            b3 = false;
        }
        return b3;
    }
    
    public boolean collidepoint(appguru.math.Vector2 a) {
        return appguru.math.Rect.cp(this, a);
    }
    
    public boolean colliderect(appguru.math.Rect a) {
        return appguru.math.Rect.cr(this, a);
    }
}
