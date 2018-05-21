package appguru.graphics.ogl;

import appguru.info.Console;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Mesh {

    public float[] vertices;
    public float[] texcoords;
    public float[] normals;
    public int[] indices;
    public appguru.math.Cube hitbox;

    public Mesh(float[] a, float[] a0, float[] a1, int[] a2) {
        super();
        this.vertices = a;
        this.texcoords = a0;
        this.normals = a1;
        this.indices = a2;
    }

    public Mesh(String s) {
        java.io.BufferedReader a;
        try {
            a = new java.io.BufferedReader((java.io.Reader) new java.io.FileReader(new java.io.File(s)));
        } catch (FileNotFoundException ex) {
            Console.errorMessage(this.getClass().getName(), "FILE NOT FOUND");
            return;
        }
        try {
            java.util.ArrayList a0 = new java.util.ArrayList();
            java.util.ArrayList a1 = new java.util.ArrayList();
            java.util.ArrayList a2 = new java.util.ArrayList();
            java.util.ArrayList a3 = new java.util.ArrayList();
            java.util.ArrayList a4 = new java.util.ArrayList();
            java.util.ArrayList a5 = new java.util.ArrayList();
            String s0 = a.readLine();
            while (s0 != null) {
                String[] a6 = s0.split(" ");
                if (a6[0].equals((Object) "v")) {
                    a0.add((Object) Float.valueOf(Float.parseFloat(a6[1])));
                    a0.add((Object) Float.valueOf(Float.parseFloat(a6[2])));
                    a0.add((Object) Float.valueOf(Float.parseFloat(a6[3])));
                } else if (a6[0].equals((Object) "vt")) {
                    a2.add((Object) Float.valueOf(Float.parseFloat(a6[1])));
                    a2.add((Object) Float.valueOf(Float.parseFloat(a6[2])));
                } else if (a6[0].equals((Object) "vn")) {
                    a1.add((Object) Float.valueOf(Float.parseFloat(a6[1])));
                    a1.add((Object) Float.valueOf(Float.parseFloat(a6[2])));
                    a1.add((Object) Float.valueOf(Float.parseFloat(a6[3])));
                } else if (a6[0].equals((Object) "f")) {
                    int i = 1;
                    while (i < a6.length) {
                        String s1 = a6[i];
                        String[] a7 = s1.split("//");
                        if (a7.length <= 1) {
                            String[] a8 = s1.split("/");
                            int i0 = Integer.parseInt(a8[0]);
                            a5.add((Object) Integer.valueOf(i0 - 1));
                            if (a8.length <= 2) {
                                if (a8.length != 1) {
                                    if (a8.length == 2) {
                                        a4.add((Object) Integer.valueOf(i0 - 1));
                                        a3.add((Object) Integer.valueOf(Integer.parseInt(a8[1]) - 1));
                                    }
                                } else {
                                    a4.add((Object) Integer.valueOf(i0 - 1));
                                    a3.add((Object) Integer.valueOf(i0 - 1));
                                }
                            } else {
                                a4.add((Object) Integer.valueOf(Integer.parseInt(a8[1]) - 1));
                                a3.add((Object) Integer.valueOf(Integer.parseInt(a8[2]) - 1));
                            }
                        } else {
                            a3.add((Object) Integer.valueOf(Integer.parseInt(a7[0]) - 1));
                            a4.add((Object) Integer.valueOf(Integer.parseInt(a7[1]) - 1));
                        }
                        i = i + 1;
                    }
                }
                s0 = a.readLine();
            }
            this.indices = new int[a5.size()];
            this.vertices = new float[a5.size() * 3];
            this.texcoords = new float[a5.size() * 2];
            this.normals = new float[a5.size() * 3];
            appguru.math.Vector3 a9 = new appguru.math.Vector3(3.4028234663852886e+38f, 3.4028234663852886e+38f, 3.4028234663852886e+38f);
            appguru.math.Vector3 a10 = new appguru.math.Vector3(1.4013e-45f, 1.4013e-45f, 1.4013e-45f);
            int i1 = 0;
            int i2 = 0;
            int i3 = 0;
            while (i3 < this.indices.length) {
                this.indices[i3] = i3;
                int i4 = ((Integer) a5.get(i3)).intValue() * 3;
                int i5 = ((Integer) a4.get(i3)).intValue() * 2;
                int i6 = ((Integer) a3.get(i3)).intValue() * 3;
                this.vertices[i1] = ((Float) a0.get(i4)).floatValue();
                this.vertices[i1 + 1] = ((Float) a0.get(i4 + 1)).floatValue();
                this.vertices[i1 + 2] = ((Float) a0.get(i4 + 2)).floatValue();
                if (this.vertices[i1] < a9.x) {
                    a9.x = this.vertices[i1];
                }
                if (this.vertices[i1 + 1] < a9.y) {
                    a9.y = this.vertices[i1 + 1];
                }
                if (this.vertices[i1 + 2] < a9.z) {
                    a9.z = this.vertices[i1 + 2];
                }
                if (this.vertices[i1] > a10.x) {
                    a10.x = this.vertices[i1];
                }
                if (this.vertices[i1 + 1] > a10.y) {
                    a10.y = this.vertices[i1 + 1];
                }
                if (this.vertices[i1 + 2] > a10.z) {
                    a10.z = this.vertices[i1 + 2];
                }
                this.texcoords[i2] = ((Float) a2.get(i5)).floatValue();
                this.texcoords[i2 + 1] = ((Float) a2.get(i5 + 1)).floatValue();
                this.normals[i1] = ((Float) a1.get(i6)).floatValue();
                this.normals[i1 + 1] = ((Float) a1.get(i6 + 1)).floatValue();
                this.normals[i1 + 2] = ((Float) a1.get(i6 + 2)).floatValue();
                i1 = i1 + 3;
                i2 = i2 + 2;
                i3 = i3 + 1;
            }
            this.hitbox = new appguru.math.Cube(a9.x, a9.y, a9.z, Math.abs(a10.x - a9.x), Math.abs(a10.y - a9.y), Math.abs(a10.z - a9.z));
        } catch (IOException ex) {
            Console.errorMessage(this.getClass().getName(), "CORRUPTED FILE");
            setNull(this);
        }
    }
    
    public void setNull(Mesh m) {
        m=null;
    }
    public static void setTex(String s, int i) {
        appguru.Main.textures.getTexture(i).enable();
        appguru.Main.textures.getTexture(i).bind();
        appguru.Main.shaders.setUniform(s, (Object) Integer.valueOf(appguru.Main.textures.getTexture(i).index));
        appguru.Main.textures.getTexture(i).disable();
    }
}
