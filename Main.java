/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru;

import appguru.graphics.ogl.Camera;
import appguru.graphics.ogl.FBO;
import appguru.graphics.ogl.Light;
import appguru.graphics.ogl.MaterialRegistry;
import appguru.graphics.ogl.Mesh;
import appguru.graphics.ogl.Model;
import appguru.graphics.ogl.ModelRegistry;
import appguru.graphics.ogl.OGL;
import appguru.graphics.ogl.ShaderRegistry;
import appguru.graphics.ogl.TextureRegistry;
import appguru.graphics.ogl.VBORegistry;
import appguru.graphics.swing.EventHandler;
import appguru.info.Console;
import appguru.math.Cube;
import appguru.math.Vector3;
import appguru.sound.SoundRegistry;
import appguru.timer.SchedulerTest;
import appguru.timer.Timer;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.math.Matrix4;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author lars
 */
public class Main implements GLEventListener {

    public GLAutoDrawable glad1;
    public static int W = 800;
    public static int H = 800;
    public Model surface;
    public Runtime runtime;
    public FBO fbo;
    public static ModelRegistry models;
    public static TextureRegistry textures;
    public static ShaderRegistry shaders;
    public static SoundRegistry sound;
    public static VBORegistry vbos;
    public static MaterialRegistry materials;
    public static Timer scheduler;
    public Matrix4 projection;
    public ArrayList<Model> modellist;
    public ArrayList<Model> modellist2;
    public Camera camera;
    public Camera camera2;
    public Light light;
    public static EventHandler events;
    public float time = 0.0f;
    public static int FRAME = 1;
    public static short STEPS = 10;
    public static short RESOLUTION = 1024;
    public static float STEPSIZE;
    public static File MESH;
    public float PERCENTAGE = 0.0f;
    public short DIRT = 0;
    public Model custom_model;
    public float step;
    public float step1;
    public float step2;
    public int dirt1;
    public int dirt2;
    public float percentage1;
    public float percentage2;
    public boolean aglogo=false;

    public ArrayList<Model> buildDirt(BufferedImage logo, int block_id, int grass_id, int dirt_id) {
        ArrayList<Model> res = new ArrayList<Model>();
        for (int x = 0; x < logo.getWidth(); x++) {
            for (int z = 0; z < logo.getHeight(); z++) {
                Color c2 = null;
                if (z != 0) {
                    c2 = new Color(logo.getRGB(x, z - 1), true);
                }
                Color c = new Color(logo.getRGB(x, z), true);
                if (c.getRed() < 50 && c.getAlpha() > 20) {
                    //Model m=new Model(1,0);
                    Model m = new Model(block_id, dirt_id);
                    m.move(x * 3f - logo.getWidth() * 1.5f, z * 3f - logo.getHeight() * 1.5f, 0f);
                    if (c2 != null && c2.getRed() < 50 && c2.getAlpha() > 20) {
                        DIRT++;
                    } else {
                        m.rotateQuaternionD(180, 0, 0, 1);
                        m.material = grass_id;
                    }
                    //m.rotateQuaternion(180,1,0,0);
                    //m.rotateEuler(90, 1, 0, 0);
                    res.add(m);
                }
            }
        }
        return res;
    }

    public void move() {
        Vector3 p = camera.getPointing(new Vector3(0, 0, 1));
        if (events.keyDown(KeyEvent.VK_T)) {
            camera.move(p.x / 5.0f, p.y / 5.0f, p.z / 5.0f);
        }
        if (events.keyDown(KeyEvent.VK_W)) {
            camera.move(p.x / 5.0f, p.y / 5.0f, p.z / 5.0f);
        }
        if (events.keyDown(KeyEvent.VK_S)) {
            camera.move(-p.x / 5.0f, -p.y / 5.0f, -p.z / 5.0f);
        }
        if (events.keyDown(KeyEvent.VK_A)) {
            camera.move(0.01f, 0f, 0f);
        }
        if (events.keyDown(KeyEvent.VK_D)) {
            camera.move(-0.01f, 0f, 0f);
        }
        if (events.keyDown(KeyEvent.VK_UP)) {
            camera.rotateQuaternion(-0.01f, 1f, 1f, 1f);
        }
        if (events.keyDown(KeyEvent.VK_DOWN)) {
            camera.rotateQuaternion(0.01f, 1f, 1f, 1f);
        }
        if (events.keyDown(KeyEvent.VK_LEFT)) {
            camera.rotateQuaternion(-0.01f, 1f, 0f, 0f);
        }
        if (events.keyDown(KeyEvent.VK_RIGHT)) {
            camera.rotateQuaternion(0.01f, 1f, 0f, 0f);
        }
    }

    public Matrix4 frustum(int w, int h, float f1, float strange_factor, float range) {
        float verhaeltnis = (float) w / (float) h;
        float nh = strange_factor;
        float nw = strange_factor * verhaeltnis;
        Matrix4 result = new Matrix4();
        result.makeFrustum(-nw / 10, nw / 10, -nh / 10, nh / 10, f1, range);
        return result;
    }

    public Matrix4 ortho(int w, int h, float strange_factor, float range) {
        float verhaeltnis = (float) w / (float) h;
        float nh = strange_factor;
        float nw = strange_factor * verhaeltnis;
        Matrix4 result = new Matrix4();
        result.makeOrtho(-nw / 10, nw / 10, -nh / 10, nh / 10, 0.1f, 0.1f + range);
        return result;
    }

    public void init(GLAutoDrawable glad) {
        glad1=glad;
        runtime = Runtime.getRuntime();
        light = new Light(new Vector3(0, -3, 0), 1.0f);
        OGL.getGL(glad);
        OGL.gl.glDisable(OGL.gl.GL_CULL_FACE);
        models = new ModelRegistry();
        textures = new TextureRegistry();
        shaders = new ShaderRegistry();
        sound = new SoundRegistry();
        scheduler = new Timer(false);
        scheduler.start();
        scheduler.addEvent(10000, new SchedulerTest());
        projection = new Matrix4();
        camera2 = new Camera();
        camera = new Camera();
        camera.move(0, 0, -150.0f);
        camera.rotateQuaternionD(1, 1, 0, 0);
        //sound.start();
            sound.add("res/sounds/merlin.wav");
            sound.start();
        sound.play(0);
        vbos = new VBORegistry(4);
        modellist = new ArrayList<Model>();
        materials = new MaterialRegistry();

        fbo = new FBO(RESOLUTION);
        //fbo.attachDepth(800,800);
        //fbo.attachTexture(800, 800);
        surface = new Model(0, -1);
        surface.scale(0.08f, 0.08f, 0.08f);
        /*Model m2=new Model(1,0);
        m2.move(2, 0, 0);
        modellist.add(m2);
        Model m3=new Model(2,0);
        m3.move(4, 0, 0);
        modellist.add(m3);
        Model m4=new Model(3,0);
        m4.move(6, 0, 0);
        modellist.add(m4);*/
        try {
            shaders.addShaderProgram("3d_vs", "3d_fs", "3d_gs");
            shaders.addShaderProgram("shadow_vs", "shadow_fs");
            shaders.addShaderProgram("particle_vs", "particle_fs");
            shaders.addShaderProgram("2d_vs", "2d_fs");
            shaders.addShaderProgram("3d_plain_vs", "3d_plain_fs");
            //shaders.addShaderProgram("3d_depthmap_vs", "3d_depthmap_fs");
            //shaders.addShaderProgram("3d_normalmap_vs", "3d_normalmap_fs");
            //shaders.setShaderProgram(1);
            //shaders.start();
            /*shaders.setShaderProgram(1);
            shaders.start();
            shaders.setShaderProgram(3);
            shaders.start();
            shaders.setShaderProgram(0);
            shaders.start();*/
            materials.loadMaterial("metal.png", "normalmap.png", "lightmap.png", "lightmap.png", "lightmap.png");
            materials.loadMaterial("metal.png", "normalmap.png", "lightmap.png", "lightmap.png", "lightmap.png");
            materials.loadMaterial("metal.png", "normalmap.png", "lightmap.png", "lightmap.png", "lightmap.png");
            materials.loadMaterial("metal.png", "normalmap.png", "lightmap.png", "lightmap.png", "lightmap.png"); //CUSTOM TEXTURE
            /*materials.loadMaterial("metal.png", "normalmap.png", "lightmap.png", "lightmap.png", "lightmap.png");
            materials.loadMaterial("woodplanks.jpg", "normalmap.png", "lightmap.png", "lightmap.png", "lightmap.png");
            materials.loadMaterial("taube.png", "normalmap.png", "lightmap.png", "lightmap.png", "lightmap.png");*/
            //models.loadMesh("omg.obj","omg.obj");
            //models.loadMesh("bytes.obj", "bytes.obj");
            //models.loadMesh("ancient_shield.obj", "ancient_shield.obj");
            //models.loadMesh("mtlogo.obj","mtlogo.obj");
            float[] vertices = {
                -1f, 1f, 0f,
                -1f, -1f, 0f,
                1f, -1f, 0f,
                1f, 1f, 0f,};
            float[] normals = {
                1f, 1f, 1f,
                1f, 1f, 1f,
                1f, 1f, 1f,
                1f, 1f, 1f,};
            float[] texcoords = {
                0, 0,
                1, 0,
                1, 1,
                0, 1};
            /*float[] vertices={0,0,0,
                              1,0,0,
                              1,1,0,
                              0,1,0};
            float[] texcoords={0,0,0,
                              1,0,0,
                              1,1,0,
                              0,1,0};
            float[] normals={0,0,0,
                              1,0,0,
                              1,1,0,
                              0,1,0};
            int[] indices={0,1,2,
                             0,2,3};*/
            int[] indices = {0, 1, 2,
                0, 2, 3};

            models.loadMesh(new Mesh(vertices, texcoords, normals, indices));
            models.loadMesh("cube.obj", "cube.obj");
            models.loadMesh("mage.obj", "mage.obj"); //CUSTOM MODEL
            //models.loadMesh("KinjaDragern.obj", "KinjaDragern.obj");
            float sizew = 0.125f;
            /*Cube hb = models.meshes.get(2).hitbox;
            float scalefactor = hb.scaleTo(sizew);
            System.out.println(scalefactor);
            step = 100 / (RESOLUTION * ((hb.dim.x * scalefactor) / 0.125f + (hb.dim.y * scalefactor) / 0.125f + 0.125f / (hb.dim.z * scalefactor)));
            Vector3 translation = hb.translateCenter();
            Console.infoMessage(Main.class.getName(), Float.toString(hb.dim.z / 2.0f * scalefactor));
            camera2.move(0, 0, -(hb.dim.z / 2.0f * scalefactor));
            camera2.move(0, 0, -0.1f);
            custom_model = new Model(2, 3);
            custom_model.scale(scalefactor, scalefactor, scalefactor);
            custom_model.move(translation.x, translation.y, -translation.z);
            Console.infoMessage(Main.class.getName(), models.meshes.get(2).hitbox.toString());
            STEPSIZE = models.meshes.get(2).hitbox.dim.y / STEPS;*/
            //models.loadMesh("sphere.obj", "sphere.obj");
            /*models.loadMesh("mage.obj", "mage.obj");
            models.loadMesh("archer.obj", "barrack.obj");
            models.loadMesh("artillery.obj", "mage.obj");
            models.loadMesh("barrack.obj", "artillery.obj");*/
            DIRT = 0;
            //BufferedImage logo = ImageIO.read(new File("res/textures/blacknwhite.png"));
            modellist.addAll(buildDirt(ImageIO.read(new File("res/textures/blacknwhite.png")), 1, 0, 1));
            dirt1 = DIRT;
            step1 = 50.0f / dirt1;
            step = step1;
            DIRT = 0;
            modellist2 = new ArrayList();
            modellist2.addAll(buildDirt(ImageIO.read(new File("res/textures/blacknwhite.png")), 1, 0, 1));
            dirt2 = DIRT;
            step2 = 50.0f / dirt2;
            DIRT = (short) dirt1;
            camera.rotateQuaternionD(180.0f, 1, 0, 0);
            //models.loadMesh("res/models/barrack.obj");
            //models.loadMesh("res/models/archer.obj");
            //models.loadMesh("res/models/mage.obj");
            //models.loadMesh("res/models/barrack.obj");
            models.loadVBO();
            shaders.setShaderProgram(4);
            shaders.start();
            shaders.display();
            shaders.setShaderProgram(3);
            shaders.start();
            shaders.display();
            shaders.setShaderProgram(0);
            shaders.start();
            shaders.display();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*try {
            //System.out.println(Saver.toString(textures));
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public void display(GLAutoDrawable glad) {
        long free = runtime.freeMemory();
        long total = runtime.totalMemory();
        long mb = (total - free) / 1048576;
        long kb = ((total - free) % 1048576) / 1024;
        long b = (total - free) % 1024;
        String s = "RAM used : Byte : %d, Kilobyte : %d, Megabyte : %d";
        Console.infoMessage(Main.class.getName(), String.format(s, b, kb, mb));
        //System.out.println(String.format(s, b, kb, mb));
        events.work();
        if (events.releasedKey(KeyEvent.VK_T) || events.releasedKey(KeyEvent.VK_S)) {
            aglogo = !aglogo;
            if (aglogo) {
                camera.move(0f, 0f, -100.0f);
                AGE.w.setTitle("Appguru Minetest Logo");
                DIRT = (short) dirt2;
                PERCENTAGE = percentage2;
                step = step2;
            } else {
                camera.move(0f, 0f, +100.0f);
                AGE.w.setTitle("Appguru Minetest Package Control Logo");
                DIRT = (short) dirt1;
                PERCENTAGE = percentage1;
                step = step1;
            }
        }
        if (PERCENTAGE <= 100) {
            int si = (int) ((PERCENTAGE) / 100.0f * DIRT);
            if (si >= 0) {
                int di = 0;
                if (!aglogo) {
                    for (Model m : modellist) {
                        if (m.material == 2) {
                            di++;
                        }
                        if (m.material == 1) {
                            di++;
                            if (di - 1 == si) {
                                m.material = 2;
                            }
                        }

                    }
                } else {
                    for (Model m : modellist2) {
                        if (m.material == 2) {
                            di++;
                        }
                        if (m.material == 1) {
                            di++;
                            if (di - 1 == si) {
                                m.material = 2;
                            }
                        }

                    }
                }
            }
        } else if (PERCENTAGE < 200) {
            int si = (int) ((PERCENTAGE - 100.0f) / 100.0f * DIRT);
            if (si >= 0) {
                int di = 0;
                if (!aglogo) {
                    for (Model m : modellist) {
                        if (m.material == 1) {
                            di++;
                        }
                        if (m.material == 2) {
                            di++;
                            if (di - 1 == si) {
                                m.material = 1;
                            }
                        }

                    }
                } else {
                    for (Model m : modellist2) {
                        if (m.material == 1) {
                            di++;
                        }
                        if (m.material == 2) {
                            di++;
                            if (di - 1 == si) {
                                m.material = 1;
                            }
                        }

                    }
                }
            }
        }
        PERCENTAGE += step;
        if (PERCENTAGE > 200) {
            PERCENTAGE = 0.0f;
        }
        if (aglogo) {
            percentage2 = PERCENTAGE;
        } else {
            percentage1 = PERCENTAGE;
        }
        time += 0.05f;
        if (time > 1.0f) {
            time = 0;
        }
        /*projection=frustum(1024,1024);
        shaders.setShaderProgram(1);
        shaders.display();
        shaders.setUniform("uniform_Modelview",(Object)light.camera.getMatrix().getMatrix());
        shaders.setUniform("uniform_Projection",(Object)projection.getMatrix());
        fbo.bind();
        fbo.bindDepth();
        for (Model m:modellist) {
            models.blitShape(m);
        }
        fbo.disable();*/
        shaders.setShaderProgram(4);
        //shaders.start();
        shaders.display();
        //models.loadVBO();
        //fbo.bind();
        //OGL.gl.glBindFramebuffer(GL3.GL_DRAW_FRAMEBUFFER, fbo.id.get(0));
        //fbo.bindDepth();
        OGL.gl.glClear(GL3.GL_COLOR_BUFFER_BIT
                | GL3.GL_DEPTH_BUFFER_BIT);
        OGL.gl.glClearColor(1, 0, 0, 0);
        //OGL.gl.glViewport(0, 0, fbo.res, fbo.res);
        OGL.gl.glViewport(0, 0, W, H);
        move();
        //Matrix4 altprojection = frustum(fbo.res, fbo.res, 0.1f, 0.5f, 0.1f + 100f);
        /*light.setUniforms();
        shaders.setUniform("fogColor", new float[]{0.4f, 0.6f, 1.0f});
        shaders.setUniform("fogConstant", 0.01f);
        shaders.setUniform("fogLinear", 0.25f);
        shaders.setUniform("fogQuadratic", 0.05f);
        shaders.setUniform("shadowmap", 5);
        shaders.setUniform("time", time);
        shaders.setUniform("ambient", 0.2f);
        shaders.setUniform("eyePos", camera.position.getVector());*/
        Matrix4 altproj=frustum(W, H, 0.1f, 0.5f, 1000);
        shaders.setUniform("uniform_Projection", (Object) altproj.getMatrix());
        shaders.setUniform("uniform_Modelview", (Object) camera.getMatrix().getMatrix());
        //fbo.bindTexture();
        //fbo.bindDepth();
        camera.rotateQuaternionD(0.3f, 0, 0, 1);
        camera.rotateQuaternionD(1, 1, 0, 0);
        camera.rotateQuaternionD(3, 0, 1, 0);
        /*for (Model m: aglogo ? modellist2:modellist ) {
             models.blit(m);
        }*/
        //camera.scale(1.3f,1.3f,1.3f);
        /*for (Model m : modellist) {
            //m.setCenter(m.position.x,0,0);
            //m.rotateQuaternion(0.1f,0,1,0);
            //m.rotateQuaternion((float) (0.1 * Math.random()), 1, 0, 0);
            //m.rotateQuaternion((float) (0.1 * Math.random()), 0, 0, 1);
            //m.rotateQuaternion((float) (0.1 * Math.random()), 0, 1, 0);
            models.blitPlain(m);
        }*/
        //fbo.readTexture();
        /*try {
            ImageIO.write(fbo.readTextureRotatedOld(), "PNG", new File("test"+Integer.toString(FRAME)+".png"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //fbo.disable();
        //projection=frustum(800,800,1000);
        OGL.gl.glClear(GL3.GL_COLOR_BUFFER_BIT
                | GL3.GL_DEPTH_BUFFER_BIT);
        OGL.gl.glClearColor(0, 0, 0, 0);
        OGL.gl.glViewport(0, 0, W, H);
        move();
        /*light.setUniforms();
        shaders.setUniform("fogColor", new float[]{0.4f, 0.6f, 1.0f});
        shaders.setUniform("fogConstant", 0.01f);
        shaders.setUniform("fogLinear", 0.25f);
        shaders.setUniform("fogQuadratic", 0.05f);
        shaders.setUniform("shadowmap", 5);
        shaders.setUniform("time", time);
        shaders.setUniform("ambient", 0.2f);
        shaders.setUniform("eyePos", camera.position.getVector());*/
        shaders.setUniform("uniform_Projection", (Object) projection.getMatrix());
        shaders.setUniform("uniform_Modelview", (Object) camera.getMatrix().getMatrix());
        //fbo.bindTexture();
        //fbo.bindDepth();
        for (Model m: aglogo ? modellist2:modellist ) {
             models.blit(m);
        }
        /*projection = ortho(800, 800);
        //shaders.setShaderProgram(0);
        //shaders.display();
        shaders.setShaderProgram(3);
        //shaders.start();

        shaders.display();
        shaders.setUniform("uniform_Projection", (Object) projection.getMatrix());
        Matrix4 m = new Matrix4();
        m.loadIdentity();
        m.translate(0, 0, -10.0f);
        shaders.setUniform("uniform_Modelview", (Object) m.getMatrix());
        Matrix4 m2 = new Matrix4();
        m2.loadIdentity();
        //shaders.setUniform("uniform_Transformation",(Object)m2.getMatrix());
        OGL.gl.glBindFramebuffer(GL3.GL_READ_FRAMEBUFFER, fbo.id.get(0));
        OGL.gl.glClear(GL3.GL_COLOR_BUFFER_BIT
                | GL3.GL_DEPTH_BUFFER_BIT);
        OGL.gl.glClearColor(0, 0, 0, 0.0f);
        OGL.gl.glEnable(GL.GL_TEXTURE_2D);
        OGL.gl.glActiveTexture(GL.GL_TEXTURE0);
        OGL.gl.glBindTexture(GL.GL_TEXTURE_2D, fbo.color_attachment_id.get(0));
        OGL.gl.glActiveTexture(GL.GL_TEXTURE1);
        OGL.gl.glBindTexture(GL.GL_TEXTURE_2D, fbo.depth_attachment_id.get(0));
        //fbo.bind();
        //OGL.gl.glBindFramebuffer(GL3.GL_READ_FRAMEBUFFER, fbo.id.get(0));
        shaders.setUniform("tex", 0);
        shaders.setUniform("depthmap", 1);
        models.blit(surface, true);*/
 /*OGL.gl.glBindFramebuffer(GL3.GL_READ_FRAMEBUFFER, fbo.id.get(0));
        OGL.gl.glBlitFramebuffer(0, 0, 1024, 1024,             // src rect
                  0, 0, 1024, 1024,             // dst rect
                  GL3.GL_COLOR_BUFFER_BIT,             // buffer mask
                  GL3.GL_LINEAR); */
 /*OGL.gl.glActiveTexture(GL.GL_TEXTURE6);
        OGL.gl.glBindTexture(GL.GL_TEXTURE_2D,fbo.color_attachment_id.get(0));
        shaders.setUniform("tex", 6);
        OGL.gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
        models.blit(surface,true);*/
        events.reset();
        FRAME++;
        //scheduler.printTimeMeasure(index);
    }

    public void dispose(GLAutoDrawable glad) {
        fbo.delete();
        shaders.stop();
        textures.clean();
        //materials.clean();
        models.clean();
        sound.terminate();
        scheduler.terminate();
        System.gc();
        Console.successMessage(this.getClass().getName(), "SHUTDOWN");
        Console.saveLog();
    }

    public void reshape(GLAutoDrawable glad, int x, int y, int w, int h) {
        projection = frustum(w, h, 0.1f, 0.5f, 1000);
        W = w;
        H = h;
        OGL.gl.glViewport(0, 0, w, h);
    }

}
