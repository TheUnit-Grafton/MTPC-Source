package appguru.graphics.ogl;

public class Morphing {
    public float[] transformations;
    
    public Morphing(appguru.graphics.ogl.Mesh a, appguru.graphics.ogl.Mesh a0, boolean b) {
        super();
        this.transformations = new float[a.vertices.length];
        int i = 0;
        while(i < this.transformations.length) {
            this.transformations[i] = a.vertices[i] - a0.vertices[i];
            i = i + 1;
        }
    }
    
    public Morphing(appguru.graphics.ogl.Mesh a, appguru.math.Vector3 a0) {
        super();
        this.transformations = new float[a.vertices.length];
        int i = 0;
        while(i < this.transformations.length / 3) {
            float f = 0.0f;
            float f0 = 0.0f;
            float f1 = 0.0f;
            float f2 = a.vertices[i] - a0.x;
            float f3 = a.vertices[i + 1] - a0.y;
            float f4 = a.vertices[i + 2] - a0.z;
            float f5 = (float)Math.sqrt((double)(f2 * f2 + f3 * f3 + f4 * f4));
            if (f5 == 0.0f) {
                f = (float)Math.random();
                f0 = (float)Math.random();
                f1 = (float)Math.random();
            } else
            {
                f = f2 / f5;
                f1 = f4 / f5;
                f0 = f3 / f5;
            }
            this.transformations[i] = -f;
            this.transformations[i + 1] = -f0;
            this.transformations[i + 2] = -f1;
            i = i + 3;
        }
    }
    
    public Morphing(appguru.graphics.ogl.Mesh a, appguru.graphics.ogl.Mesh a0) {
        super();
        this.transformations = new float[a.vertices.length];
        boolean[] a1 = new boolean[a0.vertices.length];
        int i = 0;
        while(true) {
            if (i < this.transformations.length / 3) {
                float f = a.vertices[i * 3];
                float f0 = a.vertices[i * 3 + 1];
                float f1 = a.vertices[i * 3 + 2];
                float f2 = 3.4028234663852886e+38f;
                int i0 = -1;
                int i1 = 0;
                while(i1 < a0.vertices.length / 3) {
                    boolean b = a1[i1];
                    float f3 = f2;
                    if (!b) {
                        float f4 = a0.vertices[i1 * 3];
                        float f5 = a0.vertices[i1 * 3 + 1];
                        float f6 = a0.vertices[i1 * 3 + 2];
                        float f7 = f - f4;
                        float f8 = f0 - f5;
                        float f9 = f1 - f6;
                        f2 = (float)Math.sqrt((double)(f7 * f7 + f8 * f8 + f9 * f9));
                        if (f2 < f3) {
                            i0 = i1;
                        } else
                        {
                            f2 = f3;
                        }
                    }
                    i1 = i1 + 3;
                }
                if (i0 != -1) {
                    a1[i0] = true;
                    float f10 = a0.vertices[i0 * 3];
                    float f11 = a0.vertices[i0 * 3 + 1];
                    float f12 = a0.vertices[i0 * 3 + 2];
                    this.transformations[i * 3] = f10 - f;
                    this.transformations[i * 3 + 1] = f11 - f0;
                    this.transformations[i * 3 + 2] = f12 - f1;
                    i = i + 3;
                    continue;
                }
            }
            return;
        }
    }
    
    public Morphing(appguru.graphics.ogl.Mesh a, appguru.graphics.ogl.Mesh a0, float f) {
        super();
        java.util.HashMap a1 = new java.util.HashMap();
        this.transformations = new float[a.vertices.length];
        int i = 0;
        while(true) {
            label0: if (i < this.transformations.length / 3) {
                float f0 = 0.0f;
                float f1 = 0.0f;
                float f2 = 0.0f;
                if (f == 0.0f) {
                    f0 = a.vertices[i * 3];
                    f1 = a.vertices[i * 3 + 1];
                    f2 = a.vertices[i * 3 + 2];
                } else
                {
                    f0 = (float)Math.round(a.vertices[i * 3] / f) * f;
                    f1 = (float)Math.round(a.vertices[i * 3 + 1] / f) * f;
                    f2 = (float)Math.round(a.vertices[i * 3 + 2] / f) * f;
                }
                appguru.math.Vector3 a2 = new appguru.math.Vector3(f0, f1, f2);
                appguru.math.Vector3 a3 = (appguru.math.Vector3)a1.get((Object)a2);
                {
                    label2: {
                        label1: {
                            if (a3 == null) {
                                break label1;
                            }
                            this.transformations[i * 3] = a3.x;
                            this.transformations[i * 3 + 1] = a3.y;
                            this.transformations[i * 3 + 2] = a3.z;
                            break label2;
                        }
                        float f3 = 3.4028234663852886e+38f;
                        int i0 = -1;
                        int i1 = 0;
                        while(i1 < a0.vertices.length / 3) {
                            float f4 = a0.vertices[i1 * 3];
                            float f5 = a0.vertices[i1 * 3 + 1];
                            float f6 = a0.vertices[i1 * 3 + 2];
                            float f7 = f0 - f4;
                            float f8 = f1 - f5;
                            float f9 = f2 - f6;
                            float f10 = (float)Math.sqrt((double)(f7 * f7 + f8 * f8 + f9 * f9));
                            if (f10 < f3) {
                                f3 = f10;
                                i0 = i1;
                            }
                            i1 = i1 + 3;
                        }
                        if (i0 == -1) {
                            break label0;
                        }
                        float f11 = a0.vertices[i0 * 3];
                        float f12 = a0.vertices[i0 * 3 + 1];
                        float f13 = a0.vertices[i0 * 3 + 2];
                        this.transformations[i * 3] = f11 - f0;
                        this.transformations[i * 3 + 1] = f12 - f1;
                        this.transformations[i * 3 + 2] = f13 - f2;
                        a1.put((Object)a2, (Object)new appguru.math.Vector3(f11 - f0, f12 - f1, f13 - f2));
                    }
                    i = i + 3;
                    continue;
                }
            }
            return;
        }
    }
    
    public Morphing(appguru.graphics.ogl.Mesh a) {
        super();
        this.transformations = new float[a.vertices.length];
    }
}
