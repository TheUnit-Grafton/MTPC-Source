package appguru.graphics.ogl;

public class IBO {
    public java.nio.IntBuffer id;
    
    public IBO() {
        super();
        this.id = java.nio.IntBuffer.allocate(1);
        appguru.graphics.ogl.OGL.gl.glGenBuffers(1, this.id);
    }
    
    public void bind() {
        appguru.graphics.ogl.OGL.gl.glBindBuffer(34963, this.id.get(0));
    }
    
    public void load(int[] a) {
        this.bind();
        appguru.graphics.ogl.OGL.gl.glBufferData(34963, (long)(a.length * 4), (java.nio.Buffer)com.jogamp.common.nio.Buffers.newDirectIntBuffer(a), 35044);
    }
    
    public void delete() {
        appguru.graphics.ogl.OGL.gl.glDeleteBuffers(1, this.id);
    }
}
