package appguru.graphics.ogl;

public class OGL {
    public static com.jogamp.opengl.GL3 gl;
    
    public OGL() {
        super();
    }
    
    public static void updateGL(com.jogamp.opengl.GL3 a) {
        gl = a;
    }
    
    public static void getGL(com.jogamp.opengl.GLAutoDrawable a) {
        gl = a.getGL().getGL3();
    }
}
