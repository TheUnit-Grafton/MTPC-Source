/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.ogl;

import appguru.info.Console;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;

/**
 *
 * @author lars
 */
public class Texture {
    public static final int big=GL.GL_LINEAR;
    public static final int small=GL.GL_LINEAR;
    public boolean hasAlpha;
    public IntBuffer id;
    public int index;
    public static int[] textures=new int[] {GL.GL_TEXTURE0,GL.GL_TEXTURE1,GL.GL_TEXTURE2,GL.GL_TEXTURE3,GL.GL_TEXTURE4,GL.GL_TEXTURE5,GL.GL_TEXTURE6,GL.GL_TEXTURE7,GL.GL_TEXTURE8,GL.GL_TEXTURE9,GL.GL_TEXTURE10,GL.GL_TEXTURE11,GL.GL_TEXTURE12,GL.GL_TEXTURE13,GL.GL_TEXTURE14,GL.GL_TEXTURE15,GL.GL_TEXTURE16,GL.GL_TEXTURE17,GL.GL_TEXTURE18,GL.GL_TEXTURE19,GL.GL_TEXTURE20,GL.GL_TEXTURE21,GL.GL_TEXTURE22,GL.GL_TEXTURE23,GL.GL_TEXTURE24,GL.GL_TEXTURE25,GL.GL_TEXTURE26};
    public BufferedImage im;
    public String file;
    public Texture(String filename,int indexi) {
        id=IntBuffer.allocate(1);
        hasAlpha=false;
        file=filename;
        try {
        im=ImageIO.read(new File(file));
        } catch (Exception e) {
            Console.setColor(Console.RED);
            Console.printLn("ERROR READING IMAGE : "+filename);
        }
        index=indexi;
        OGL.gl.glGenTextures(1,id);
        bind();
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, small);
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, big);
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_R, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, im.getWidth(), im.getHeight(), 0, GL.GL_RGBA, GL.GL_FLOAT, Buffers.newDirectFloatBuffer(toFloats()));
        im=null;
    }
    public Texture(URL filename,int indexi) throws IOException {
        id=IntBuffer.allocate(1);
        hasAlpha=false;
        file="?";
        im=ImageIO.read(filename);
        index=indexi;
        OGL.gl.glGenTextures(1,id);
        bind();
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, small);
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, big);
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_R, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, im.getWidth(), im.getHeight(), 0, GL.GL_RGBA, GL.GL_FLOAT, Buffers.newDirectFloatBuffer(toFloats()));
        im=null;
    }
    public Texture(BufferedImage ima,int indexi) {
        id=IntBuffer.allocate(1);
        hasAlpha=false;
        file="?";
        im=ima;
        index=indexi;
        OGL.gl.glGenTextures(1,id);
        bind();
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, small);
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, big);
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_R, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, im.getWidth(), im.getHeight(), 0, GL.GL_RGBA, GL.GL_FLOAT, Buffers.newDirectFloatBuffer(toFloats()));
        im=null;
    }
    public void update(BufferedImage ima) {
        im=ima;
        this.bind();
        OGL.gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, im.getWidth(), im.getHeight(), 0, GL.GL_RGBA, GL.GL_FLOAT, Buffers.newDirectFloatBuffer(toFloats()));
        im=null;
    }
    public void bind() {
        OGL.gl.glActiveTexture(textures[index]);
        OGL.gl.glBindTexture(GL.GL_TEXTURE_2D,id.get(0));
    }
    public void enable() {
        OGL.gl.glEnable(GL.GL_TEXTURE_2D);
    }
    public void disable() {
        OGL.gl.glDisable(GL.GL_TEXTURE_2D);
    }
    public void delete() {
        OGL.gl.glDeleteTextures(1,id);
    }
    public float[] toFloats() {
        float[] pixels=new float[im.getWidth()*im.getHeight()*4];
        for (int x=0; x < im.getWidth(); x++) {
            for (int y=0; y < im.getHeight(); y++) {
                Color c=new Color(im.getRGB(x,y),true);
                if (c.getAlpha() != 255) {
                    hasAlpha=true;
                }
                float r=(float)c.getRed()/255.0f;
                float g=(float)c.getGreen()/255.0f;
                float b=(float)c.getBlue()/255.0f;
                pixels[(y*im.getWidth()+x)*4]=r;
                pixels[(y*im.getWidth()+x)*4+1]=g;
                pixels[(y*im.getWidth()+x)*4+2]=b;
                pixels[(y*im.getWidth()+x)*4+3]=(float)c.getAlpha()/255.0f;
            }
        }
        return pixels;
    }
    public Texture(Texture t1, Texture t2, float p1, float p2) {
        im=new BufferedImage(t1.im.getWidth(),t1.im.getHeight(),BufferedImage.TYPE_INT_ARGB);
        float[] pixels=new float[t1.im.getWidth()*t1.im.getHeight()*4];
        for (int x=0; x < t1.im.getWidth() && x < t2.im.getWidth(); x++) {
            for (int y=0; y < t1.im.getHeight() && x < t2.im.getWidth(); y++) {
                Color c=new Color(t1.im.getRGB(x,y));
                Color c2=new Color(t2.im.getRGB(x,y));
                float r=(float)c.getRed()*p1+(float)c2.getRed()*p2;
                float g=(float)c.getGreen()*p1+(float)c2.getGreen()*p2;
                float b=(float)c.getBlue()*p1+(float)c2.getBlue()*p2;
                float a=(float)c.getAlpha()*p1+(float)c2.getAlpha()*p2;
                pixels[(y*t1.im.getWidth()+x)*4]=r/255.0f;
                pixels[(y*t1.im.getWidth()+x)*4+1]=g/255.0f;
                pixels[(y*t1.im.getWidth()+x)*4+2]=b/255.0f;
                pixels[(y*t1.im.getWidth()+x)*4+3]=a/255.0f;
                im.setRGB((int)r,(int)g,(int)b);
            }
        }
        id=IntBuffer.allocate(1);
        hasAlpha=false;
        index=0;
        OGL.gl.glGenTextures(1,id);
        bind();
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, small);
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, big);
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_R, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_MIRRORED_REPEAT); 
        OGL.gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, im.getWidth(), im.getHeight(), 0, GL.GL_RGBA, GL.GL_FLOAT, Buffers.newDirectFloatBuffer(toFloats()));
    }
}
