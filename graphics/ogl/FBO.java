package appguru.graphics.ogl;

public class FBO {
    public java.nio.IntBuffer id;
    public java.nio.IntBuffer color_attachment_id;
    public java.nio.IntBuffer stencil_attachment_id;
    public java.nio.IntBuffer depth_attachment_id;
    public int res;
    
    public FBO(int i) {
        super();
        this.res = i;
        this.id = java.nio.IntBuffer.allocate(1);
        this.color_attachment_id = java.nio.IntBuffer.allocate(3);
        this.stencil_attachment_id = java.nio.IntBuffer.allocate(1);
        this.depth_attachment_id = java.nio.IntBuffer.allocate(1);
        appguru.graphics.ogl.OGL.gl.glGenFramebuffers(1, this.id);
        this.bind();
        appguru.graphics.ogl.OGL.gl.glGenTextures(1, this.color_attachment_id);
        appguru.graphics.ogl.OGL.gl.glActiveTexture(33991);
        appguru.graphics.ogl.OGL.gl.glBindTexture(3553, this.color_attachment_id.get(0));
        appguru.graphics.ogl.OGL.gl.glTexParameteri(3553, 10241, 9728);
        appguru.graphics.ogl.OGL.gl.glTexParameteri(3553, 10240, 9728);
        appguru.graphics.ogl.OGL.gl.glTexImage2D(3553, 0, 6408, i, i, 0, 6408, 5126, (java.nio.Buffer)null);
        appguru.graphics.ogl.OGL.gl.glGenTextures(1, this.depth_attachment_id);
        appguru.graphics.ogl.OGL.gl.glActiveTexture(33992);
        appguru.graphics.ogl.OGL.gl.glBindTexture(3553, this.depth_attachment_id.get(0));
        appguru.graphics.ogl.OGL.gl.glTexParameteri(3553, 10241, 9728);
        appguru.graphics.ogl.OGL.gl.glTexParameteri(3553, 10240, 9728);
        appguru.graphics.ogl.OGL.gl.glTexImage2D(3553, 0, 6402, i, i, 0, 6402, 5126, (java.nio.Buffer)null);
        appguru.graphics.ogl.OGL.gl.glFramebufferTexture2D(36160, 36064, 3553, this.color_attachment_id.get(0), 0);
        appguru.graphics.ogl.OGL.gl.glFramebufferTexture2D(36160, 36064, 3553, this.color_attachment_id.get(0), 0);
        appguru.graphics.ogl.OGL.gl.glFramebufferTexture2D(36160, 36096, 3553, this.depth_attachment_id.get(0), 0);
        appguru.graphics.ogl.OGL.gl.glDrawBuffers(1, this.color_attachment_id);
        appguru.graphics.ogl.OGL.gl.glDrawBuffers(1, this.depth_attachment_id);
        this.check();
    }
    
    public void check() {
        if (appguru.graphics.ogl.OGL.gl.glCheckFramebufferStatus(36160) != 36053) {
            appguru.info.Console.errorMessage(((Object)this).getClass().getName(), "INITIALISATION FAILURE");
        } else
        {
            appguru.info.Console.successMessage(((Object)this).getClass().getName(), "INITIALISATION");
        }
    }
    
    public void attachTexture(int i, int i0) {
        this.color_attachment_id = java.nio.IntBuffer.allocate(1);
        appguru.graphics.ogl.OGL.gl.glGenTextures(1, this.color_attachment_id);
        appguru.graphics.ogl.OGL.gl.glActiveTexture(33990);
        appguru.graphics.ogl.OGL.gl.glBindTexture(3553, this.color_attachment_id.get(0));
        appguru.graphics.ogl.OGL.gl.glTexParameteri(3553, 10241, 9728);
        appguru.graphics.ogl.OGL.gl.glTexParameteri(3553, 10240, 9728);
        appguru.graphics.ogl.OGL.gl.glTexImage2D(3553, 0, 6408, i, i0, 0, 6408, 5126, (java.nio.Buffer)null);
        this.bind();
        appguru.graphics.ogl.OGL.gl.glFramebufferTexture2D(36160, 36064, 3553, this.color_attachment_id.get(0), 0);
        appguru.graphics.ogl.OGL.gl.glDrawBuffers(1, this.color_attachment_id);
        this.check();
        this.disable();
    }
    
    public void attachDepth(int i, int i0) {
        this.depth_attachment_id = java.nio.IntBuffer.allocate(1);
        appguru.graphics.ogl.OGL.gl.glGenRenderbuffers(1, this.depth_attachment_id);
        appguru.graphics.ogl.OGL.gl.glBindRenderbuffer(36161, this.depth_attachment_id.get(0));
        appguru.graphics.ogl.OGL.gl.glRenderbufferStorage(36161, 33190, i, i0);
        this.bind();
        appguru.graphics.ogl.OGL.gl.glFramebufferRenderbuffer(36160, 36096, 36161, this.depth_attachment_id.get(0));
        this.check();
        this.disable();
    }
    
    public void bindDepth() {
        appguru.graphics.ogl.OGL.gl.glEnable(3553);
        appguru.graphics.ogl.OGL.gl.glActiveTexture(33991);
        appguru.graphics.ogl.OGL.gl.glBindTexture(3553, this.depth_attachment_id.get(0));
        appguru.Main.shaders.setUniform("shadowmap", (Object)Integer.valueOf(7));
        appguru.graphics.ogl.OGL.gl.glDisable(3553);
    }
    
    public void attachStencil(int i, int i0) {
        this.bind();
        this.stencil_attachment_id = java.nio.IntBuffer.allocate(1);
        appguru.graphics.ogl.OGL.gl.glGenRenderbuffers(1, this.stencil_attachment_id);
        appguru.graphics.ogl.OGL.gl.glBindRenderbuffer(36161, this.stencil_attachment_id.get(0));
        appguru.graphics.ogl.OGL.gl.glRenderbufferStorage(36161, 36013, i, i0);
        appguru.graphics.ogl.OGL.gl.glFramebufferRenderbuffer(36160, 33306, 36161, this.stencil_attachment_id.get(0));
        this.check();
        this.disable();
    }
    
    public java.awt.image.BufferedImage readTexture() {
        java.nio.FloatBuffer a = java.nio.FloatBuffer.allocate(4 * this.res * this.res);
        appguru.graphics.ogl.OGL.gl.glReadBuffer(this.id.get(0));
        appguru.graphics.ogl.OGL.gl.glReadPixels(0, 0, this.res, this.res, 6408, 5126, (java.nio.Buffer)a);
        float[] a0 = a.array();
        java.awt.image.BufferedImage a1 = new java.awt.image.BufferedImage(this.res, this.res, 2);
        int i = 0;
        while(i < this.res) {
            int i0 = 0;
            while(i0 < this.res) {
                int i1 = (i * this.res + i0) * 4;
                a1.setRGB(i, i0, new java.awt.Color((int)(a0[i1] * 255f), (int)(a0[i1 + 1] * 255f), (int)(a0[i1 + 2] * 255f), (int)(a0[i1 + 3] * 255f)).getRGB());
                i0 = i0 + 1;
            }
            i = i + 1;
        }
        return a1;
    }
    
    public java.awt.image.BufferedImage readTextureRotatedOld() {
        java.nio.FloatBuffer a = java.nio.FloatBuffer.allocate(4 * this.res * this.res);
        appguru.graphics.ogl.OGL.gl.glReadBuffer(this.id.get(0));
        appguru.graphics.ogl.OGL.gl.glReadPixels(0, 0, this.res, this.res, 6408, 5126, (java.nio.Buffer)a);
        float[] a0 = a.array();
        java.awt.image.BufferedImage a1 = new java.awt.image.BufferedImage(this.res, this.res, 2);
        int i = 0;
        while(i < this.res) {
            int i0 = 0;
            while(i0 < this.res) {
                int i1 = (i * this.res + i0) * 4;
                int i2 = (int)(a0[i1] * 255f);
                int i3 = (int)(a0[i1 + 1] * 255f);
                int i4 = (int)(a0[i1 + 2] * 255f);
                int i5 = (int)(a0[i1 + 3] * 255f);
                a1.setRGB(i0, this.res - i - 1, new java.awt.Color(i2, i3, i4, i5).getRGB());
                i0 = i0 + 1;
            }
            i = i + 1;
        }
        return a1;
    }
    
    public java.awt.image.BufferedImage readTextureRotated() {
        java.nio.IntBuffer a = java.nio.IntBuffer.allocate(4 * this.res * this.res);
        appguru.graphics.ogl.OGL.gl.glReadBuffer(this.id.get(0));
        appguru.graphics.ogl.OGL.gl.glReadPixels(0, 0, this.res, this.res, 6408, 5124, (java.nio.Buffer)a);
        int[] a0 = a.array();
        java.awt.image.BufferedImage a1 = new java.awt.image.BufferedImage(this.res, this.res, 2);
        int i = 0;
        while(i < this.res) {
            int i0 = 0;
            while(i0 < this.res) {
                int i1 = (i * this.res + i0) * 4;
                boolean b = a0[i1] != 0;
                boolean b0 = a0[i1 + 1] != 0;
                boolean b1 = a0[i1 + 2] != 0;
                boolean b2 = a0[i1 + 3] != 0;
                a1.setRGB(i0, this.res - i - 1, new java.awt.Color(b ? 1 : 0, b0 ? 1 : 0, b1 ? 1 : 0, b2 ? 1 : 0).getRGB());
                i0 = i0 + 1;
            }
            i = i + 1;
        }
        return a1;
    }
    
    public void bind() {
        appguru.graphics.ogl.OGL.gl.glBindFramebuffer(36160, this.id.get(0));
    }
    
    public void enable() {
        this.bind();
    }
    
    public void enableTexture() {
        appguru.graphics.ogl.OGL.gl.glEnable(3553);
    }
    
    public void bindTexture() {
        appguru.graphics.ogl.OGL.gl.glActiveTexture(33990);
        appguru.graphics.ogl.OGL.gl.glBindTexture(3553, this.color_attachment_id.get(0));
    }
    
    public void disableTexture() {
        appguru.graphics.ogl.OGL.gl.glDisable(3553);
    }
    
    public void disable() {
        appguru.graphics.ogl.OGL.gl.glBindFramebuffer(36160, 0);
    }
    
    public void delete() {
        appguru.graphics.ogl.OGL.gl.glDeleteFramebuffers(1, this.id);
        appguru.info.Console.successMessage(((Object)this).getClass().getName(), "DELETION");
    }
}
