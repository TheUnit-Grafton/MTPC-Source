package appguru.graphics.swing;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class GLPane extends GLCanvas {
    public static final GLCapabilities STANDARD_CAPS;
    public FPSAnimator animator;
    public GLEventListener run;
    static {
        STANDARD_CAPS = new GLCapabilities(GLProfile.get(GLProfile.GL3));
        STANDARD_CAPS.setBackgroundOpaque(true);
        STANDARD_CAPS.setHardwareAccelerated(true);
        STANDARD_CAPS.setSampleBuffers(false);
        STANDARD_CAPS.setNumSamples(8);
        
    }
    public GLPane(GLCapabilities caps, int fps, GLEventListener exec) {
        super(caps);
        run=exec;
        this.addGLEventListener(run);
        animator=new FPSAnimator(fps);
    }
    
    public void start() {
        animator.start();
    }
    
    public void stop() {
        animator.stop();
    }
}
