package appguru.math;

public class Cube {

    public appguru.math.Vector3 pos;
    public appguru.math.Vector3 dim;
    public appguru.math.Rect surface;
    public appguru.math.Rect side;
    public appguru.math.Rect front;

    public Cube(float f, float f0, float f1, float f2, float f3, float f4) {
        super();
        this.pos = new appguru.math.Vector3(f, f0, f1);
        this.dim = new appguru.math.Vector3(f2, f3, f4);
        this.surface = new appguru.math.Rect(this.pos.x, this.pos.z, this.dim.x, this.dim.z);
        this.side = new appguru.math.Rect(this.pos.y, this.pos.z, this.dim.y, this.dim.z);
        this.front = new appguru.math.Rect(this.pos.x, this.pos.y, this.dim.x, this.dim.y);
    }

    public boolean pointingAt(appguru.graphics.ogl.Camera a) {
        boolean b = false;
        this.surface = new appguru.math.Rect(this.pos.x, this.pos.z, this.dim.x, this.dim.z);
        this.side = new appguru.math.Rect(this.pos.y, this.pos.z, this.dim.y, this.dim.z);
        this.front = new appguru.math.Rect(this.pos.x, this.pos.y, this.dim.x, this.dim.y);
        float f = -(a.position.z - this.pos.z);
        appguru.math.Vector2 a0 = new appguru.math.Vector2(f, f).multiply(a.frictionz).add(new appguru.math.Vector2(a.position.x, a.position.y));
        float f0 = -(a.position.y - this.pos.y);
        appguru.math.Vector2 a1 = new appguru.math.Vector2(f0, f0).multiply(a.frictiony).add(new appguru.math.Vector2(a.position.x, a.position.z));
        float f1 = -(a.position.x - this.pos.x);
        appguru.math.Vector2 a2 = new appguru.math.Vector2(f1, f1).multiply(a.frictionx).add(new appguru.math.Vector2(a.position.y, a.position.z));
        boolean b0 = this.front.collidepoint(a0);
        label2:
        {
            label0:
            {
                label1:
                {
                    if (b0) {
                        break label1;
                    }
                    if (this.surface.collidepoint(a1)) {
                        break label1;
                    }
                    if (!this.side.collidepoint(a2)) {
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

    public boolean collidecube(appguru.math.Cube a) {
        boolean b = false;
        boolean b0 = this.surface.colliderect(a.surface);
        label2:
        {
            label0:
            {
                label1:
                {
                    if (!b0) {
                        break label1;
                    }
                    if (!this.side.colliderect(a.side)) {
                        break label1;
                    }
                    if (this.front.colliderect(a.front)) {
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

    public boolean collidepoint(appguru.math.Vector3 a) {
        boolean b = false;
        float f = a.x;
        float f0 = this.pos.x;
        int i = (f > f0) ? 1 : (f == f0) ? 0 : -1;
        label2:
        {
            label0:
            {
                label1:
                {
                    if (i <= 0) {
                        break label1;
                    }
                    if (!(a.x < this.pos.x + this.dim.x)) {
                        break label1;
                    }
                    if (!(a.y > this.pos.y)) {
                        break label1;
                    }
                    if (!(a.y < this.pos.y + this.dim.y)) {
                        break label1;
                    }
                    if (!(a.z > this.pos.z)) {
                        break label1;
                    }
                    if (a.z < this.pos.z + this.dim.z) {
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

    public float scaleTo(float f) {
        return f / this.longestSide();
    }

    public float longestSide() {
        return Math.max(Math.max(this.dim.x, this.dim.y), this.dim.z);
    }

    public appguru.math.Vector3 translateCenter() {
        return this.dim.divide(2f).negateC().minus(this.pos);
    }

    @Override
    public String toString() {
        label0:
        {
            try {
                return appguru.helper.Saver.toString(this);
            } catch (Exception a1) {
                break label0;
            }
        }
        return "APPGURU : MATH : CUBE : IMPOSSIBLE ERROR - PLEASE CONTACT THE DEVELOPER";
    }
}
