package appguru.math;

public class Vector2 {
    public float x;
    public float y;
    
    public Vector2(float f, float f0) {
        super();
        this.x = f;
        this.y = f0;
    }
    
    public appguru.math.Vector2 multiply(appguru.math.Vector2 a) {
        return new appguru.math.Vector2(this.x * a.x, this.y * a.y);
    }
    
    public appguru.math.Vector2 add(appguru.math.Vector2 a) {
        return new appguru.math.Vector2(this.x + a.x, this.y + a.y);
    }
    
    public String toString() {
        return Float.toString(x)+", "+Float.toString(y);
    }
}
