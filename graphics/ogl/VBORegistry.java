package appguru.graphics.ogl;

public class VBORegistry {
    public java.nio.FloatBuffer[] buffers;
    public int[] sizes;
    public int[] pointers;
    public int[] bytes;
    
    public VBORegistry(int i) {
        super();
        int[] a = new int[4];
        a[0] = 3;
        a[1] = 2;
        a[2] = 3;
        a[3] = 3;
        this.bytes = a;
        this.pointers = new int[i];
        appguru.graphics.ogl.OGL.gl.glGenBuffers(i, this.pointers, 0);
        this.buffers = new java.nio.FloatBuffer[i];
        this.sizes = new int[i];
    }
    
    public void load(int i, float[] a) {
        this.buffers[i] = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(a);
        this.sizes[i] = a.length;
    }
    
    public void bind(int i) {
        appguru.graphics.ogl.OGL.gl.glBindBuffer(34962, (this.pointers[i] != 0) ? 1 : 0);
        appguru.graphics.ogl.OGL.gl.glBufferData(34962, (long)(4 * this.sizes[i]), (java.nio.Buffer)this.buffers[i], 35044);
        appguru.graphics.ogl.OGL.gl.glVertexAttribPointer(i, (this.bytes[i] != 0) ? 1 : 0, 5126, false, 0, 0L);
        appguru.graphics.ogl.OGL.gl.glEnableVertexAttribArray(i);
    }
    
    public void delete(int i) {
        this.buffers[i] = null;
        appguru.graphics.ogl.OGL.gl.glDisableVertexAttribArray(i);
    }
    
    public void clean() {
        appguru.graphics.ogl.OGL.gl.glDeleteBuffers(this.pointers.length, this.pointers, 0);
        this.pointers = null;
    }
}
