package appguru.helper;

public class MathHelper {
    public MathHelper() {
        super();
    }
    
    public static float distance(float f, float f0, float f1, float f2) {
        float f3 = f1 - f;
        float f4 = f2 - f0;
        return (float)Math.sqrt((double)(f3 * f3 + f4 * f4));
    }
    
    public static float distance(float f, float f0) {
        return (float)Math.sqrt((double)(f * f + f0 * f0));
    }
}
