package appguru.graphics.ogl;

public class Light {
    final public static float QUADRATIC = 0.02f;
    final public static float CONSTANT = 0.0f;
    final public static float LINEAR = 0.04f;
    final public static appguru.math.Vector3 DIFFUSE;
    final public static appguru.math.Vector3 SPECULAR;
    final public static appguru.math.Vector3 GAUSSIAN;
    final public static float[] BIAS_MATRIX;
    public float quadratic;
    public float constant;
    public float linear;
    public appguru.math.Vector3 diffuse;
    public appguru.math.Vector3 specular;
    public appguru.math.Vector3 gaussian;
    public appguru.graphics.ogl.Camera camera;
    
    public Light(appguru.math.Vector3 a, float f) {
        super();
        this.constant = 1f;
        this.diffuse = DIFFUSE;
        this.specular = SPECULAR;
        this.gaussian = GAUSSIAN;
        this.quadratic = 0.02f;
        this.linear = 0.04f;
        this.camera = new appguru.graphics.ogl.Camera();
        this.camera.move(a.x, a.y, a.z);
    }
    
    public float[] getVertexMatrix() {
        com.jogamp.opengl.math.Matrix4 a = this.camera.getMatrix();
        a.multMatrix(BIAS_MATRIX);
        return a.getMatrix();
    }
    
    public void setUniforms() {
        appguru.Main.shaders.setUniform("lightQuadratic", (Object)Float.valueOf(this.quadratic));
        appguru.Main.shaders.setUniform("lightConstant", (Object)Float.valueOf(this.constant));
        appguru.Main.shaders.setUniform("lightLinear", (Object)Float.valueOf(this.linear));
        appguru.Main.shaders.setUniform("lightPos", (Object)this.camera.position.getVector());
        appguru.Main.shaders.setUniform("lightDiffuse", (Object)this.diffuse.getVector());
        appguru.Main.shaders.setUniform("lightSpecular", (Object)this.specular.getVector());
        appguru.Main.shaders.setUniform("lightGaussian", (Object)this.gaussian.getVector());
        appguru.Main.shaders.setUniform("lightMatrix", (Object)this.getVertexMatrix());
    }
    
    static {
        DIFFUSE = new appguru.math.Vector3(0.5f, 0.5f, 0.5f);
        SPECULAR = new appguru.math.Vector3(0.2f, 0.2f, 0.2f);
        GAUSSIAN = new appguru.math.Vector3(0.3f, 0.3f, 0.3f);
        float[] a = new float[16];
        a[0] = 0.5f;
        a[1] = 0.0f;
        a[2] = 0.0f;
        a[3] = 0.5f;
        a[4] = 0.0f;
        a[5] = 0.5f;
        a[6] = 0.0f;
        a[7] = 0.5f;
        a[8] = 0.0f;
        a[9] = 0.0f;
        a[10] = 0.5f;
        a[11] = 0.5f;
        a[12] = 0.0f;
        a[13] = 0.0f;
        a[14] = 0.0f;
        a[15] = 1f;
        BIAS_MATRIX = a;
    }
}
