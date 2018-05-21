package appguru.math;

public class Sphere {
    public appguru.math.Vector3 pos;
    public float r;
    public static appguru.math.Sphere NULL;
    
    public Sphere(float f, float f0, float f1, float f2) {
        super();
        this.pos.x = f;
        this.pos.y = f0;
        this.pos.z = f1;
        this.r = f2;
    }
    
    public float distance(appguru.math.Sphere a) {
        float f = this.pos.x - a.pos.x;
        float f0 = this.pos.y - a.pos.y;
        float f1 = this.pos.z - a.pos.z;
        return (float)Math.sqrt(Math.pow((double)f, 2.0) + Math.pow((double)f0, 2.0) + Math.pow((double)f1, 2.0));
    }
    
    public float distance(appguru.math.Vector3 a) {
        float f = this.pos.x - a.x;
        float f0 = this.pos.y - a.y;
        float f1 = this.pos.z - a.z;
        return (float)Math.sqrt(Math.pow((double)f, 2.0) + Math.pow((double)f0, 2.0) + Math.pow((double)f1, 2.0));
    }
    
    public boolean colliding(appguru.math.Sphere a) {
        return this.distance(a) < this.r + a.r;
    }
    
    public boolean pointingAt(appguru.graphics.ogl.Camera a) {
        boolean b = false;
        appguru.math.Circle a0 = new appguru.math.Circle(new appguru.math.Vector2(this.pos.x, this.pos.y), this.r);
        appguru.math.Circle a1 = new appguru.math.Circle(new appguru.math.Vector2(this.pos.x, this.pos.z), this.r);
        appguru.math.Circle a2 = new appguru.math.Circle(new appguru.math.Vector2(this.pos.y, this.pos.z), this.r);
        float f = -(a.position.z - this.pos.z);
        appguru.math.Vector2 a3 = new appguru.math.Vector2(f, f).multiply(a.frictionz).add(new appguru.math.Vector2(a.position.x, a.position.y));
        float f0 = -(a.position.y - this.pos.y);
        appguru.math.Vector2 a4 = new appguru.math.Vector2(f0, f0).multiply(a.frictiony).add(new appguru.math.Vector2(a.position.x, a.position.z));
        float f1 = -(a.position.x - this.pos.x);
        appguru.math.Vector2 a5 = new appguru.math.Vector2(f1, f1).multiply(a.frictionx).add(new appguru.math.Vector2(a.position.y, a.position.z));
        boolean b0 = a0.oncircle(a3);
        label2: {
            label0: {
                label1: {
                    if (b0) {
                        break label1;
                    }
                    if (a1.oncircle(a4)) {
                        break label1;
                    }
                    if (!a2.oncircle(a5)) {
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
    
    public float getMass() {
        return (float)(4.1887902047863905 * Math.pow((double)this.r, 3.0));
    }
    
    public float getSurface() {
        return (float)(12.566370614359172 * Math.pow((double)this.r, 2.0));
    }
    
    static {
        NULL = new appguru.math.Sphere(0.0f, 0.0f, 0.0f, 0.0f);
    }
}
