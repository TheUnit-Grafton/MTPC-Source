package appguru.math;

import appguru.helper.Saver;

public class Vector3 {
    public float x;
    public float y;
    public float z;
    
    public Vector3(float f, float f0, float f1) {
        super();
        this.x = f;
        this.y = f0;
        this.z = f1;
    }
    
    public appguru.math.Vector3 divide(float f) {
        return new appguru.math.Vector3(this.x / f, this.y / f, this.z / f);
    }
    
    public appguru.math.Vector3 divide(appguru.math.Vector3 a) {
        return new appguru.math.Vector3(this.x / a.x, this.y / a.y, this.z / a.z);
    }
    
    public appguru.math.Vector3 multiply(appguru.math.Vector3 a) {
        return new appguru.math.Vector3(this.x * a.x, this.y * a.y, this.z * a.z);
    }
    
    public appguru.math.Vector3 multiply(float f) {
        return new appguru.math.Vector3(this.x * f, this.y * f, this.z * f);
    }
    
    public appguru.math.Vector3 minus(appguru.math.Vector3 a) {
        return new appguru.math.Vector3(this.x - a.x, this.y - a.y, this.z - a.z);
    }
    
    public appguru.math.Vector3 plus(appguru.math.Vector3 a) {
        return new appguru.math.Vector3(this.x + a.x, this.y + a.y, this.z + a.z);
    }
    
    public appguru.math.Vector3 negateC() {
        return new appguru.math.Vector3(-this.x, -this.y, -this.z);
    }
    
    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }
    
    public void normalize() {
        float f = (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
        this.x = this.x / f;
        this.y = this.y / f;
        this.z = this.z / f;
    }
    
    public float[] getVector() {
        float[] a = new float[3];
        a[0] = this.x;
        a[1] = this.y;
        a[2] = this.z;
        return a;
    }
    
    public int hashCode() {
        float[] a = new float[3];
        a[0] = this.x;
        a[1] = this.y;
        a[2] = this.z;
        return java.util.Arrays.hashCode(a);
    }
    
    public boolean equals(Object a) {
        if (this == a) {
            return true;
        }
        if (a == null) {
            return false;
        }
        if (((Object)this).getClass() != a.getClass()) {
            return false;
        }
        appguru.math.Vector3 a0 = (appguru.math.Vector3)a;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(a0.x)) {
            return false;
        }
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(a0.y)) {
            return false;
        }
        if (Float.floatToIntBits(this.z) == Float.floatToIntBits(a0.z)) {
            return true;
        }
        return false;
    }
    
    public String toString() {
        return Float.toString(x)+", "+Float.toString(y)+", "+Float.toString(z);
    }
}
