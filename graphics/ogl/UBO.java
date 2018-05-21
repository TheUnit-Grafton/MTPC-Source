package appguru.graphics.ogl;

public class UBO {
    public java.nio.IntBuffer id;
    
    public UBO(int i) {
        super();
        this.id = java.nio.IntBuffer.allocate(1);
        appguru.graphics.ogl.OGL.gl.glGenBuffers(1, this.id);
        appguru.graphics.ogl.OGL.gl.glBindBuffer(35345, this.id.get(0));
        com.jogamp.opengl.GL3 a = appguru.graphics.ogl.OGL.gl;
        float[] a0 = new float[3];
        a0[0] = 0.0f;
        a0[1] = 0.0f;
        a0[2] = 1f;
        a.glBufferData(35345, 12L, (java.nio.Buffer)com.jogamp.common.nio.Buffers.newDirectFloatBuffer(a0), 35048);
        appguru.graphics.ogl.OGL.gl.glBindBuffer(35345, 0);
    }
    
    public void write(float[] a) {
        appguru.graphics.ogl.OGL.gl.glBindBufferBase(35345, 2, this.id.get(0));
        appguru.graphics.ogl.OGL.gl.glBindBuffer(35345, this.id.get(0));
        java.nio.ByteBuffer a0 = appguru.graphics.ogl.OGL.gl.glMapBuffer(35345, 35002);
        int i = 0;
        while(i < a.length) {
            int i0 = Float.floatToIntBits(a[i]);
            int i1 = (byte)(i0 & 255);
            a0.put(0, (byte)i1);
            int i2 = (byte)(i0 >> 8 & 255);
            a0.put(1, (byte)i2);
            int i3 = (byte)(i0 >> 16 & 255);
            a0.put(2, (byte)i3);
            int i4 = (byte)(i0 >> 24 & 255);
            a0.put(3, (byte)i4);
            i = i + 1;
        }
        appguru.graphics.ogl.OGL.gl.glUnmapBuffer(35345);
    }
}
