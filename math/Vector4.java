package appguru.math;

public class Vector4 {
    public float x;
    public float y;
    public float z;
    public float w;
    
    public Vector4(float f, float f0, float f1, float f2) {
        super();
        this.x = f;
        this.y = f0;
        this.z = f1;
        this.w = f2;
    }
    
    public void normalize() {
        float f = (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w));
        this.x = this.x / f;
        this.y = this.y / f;
        this.z = this.z / f;
        this.w = this.w / f;
    }
    
    public float[] getVector() {
        float[] a = new float[4];
        a[0] = this.x;
        a[1] = this.y;
        a[2] = this.z;
        a[3] = this.w;
        return a;
    }
}
