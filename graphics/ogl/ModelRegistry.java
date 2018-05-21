package appguru.graphics.ogl;

public class ModelRegistry {
    public java.util.List meshes;
    public java.util.List morphings;
    public java.util.Map register;
    public int[][] indices;
    public appguru.graphics.ogl.IBO ibo;
    public int last_mesh;
    
    public ModelRegistry() {
        super();
        this.register = (java.util.Map)(Object)new java.util.HashMap();
        this.meshes = (java.util.List)(Object)new java.util.ArrayList();
        this.morphings = (java.util.List)(Object)new java.util.ArrayList();
        this.indices = null;
        this.ibo = new appguru.graphics.ogl.IBO();
        this.last_mesh = -1;
        appguru.info.Console.successMessage(((Object)this).getClass().getName(), "INITIALISATION");
    }
    
    public void blit(appguru.graphics.ogl.Model a) {
        appguru.graphics.ogl.Material a0 = appguru.Main.materials.getMaterial(a.material);
        if (appguru.Main.textures.getTexture(a0.texture).hasAlpha) {
            appguru.graphics.ogl.OGL.gl.glDepthMask(true);
            appguru.graphics.ogl.OGL.gl.glEnable(2929);
            appguru.graphics.ogl.OGL.gl.glDepthFunc(515);
            appguru.graphics.ogl.OGL.gl.glDisable(2884);
            appguru.graphics.ogl.OGL.gl.glEnable(3042);
            appguru.graphics.ogl.OGL.gl.glBlendFunc(770, 771);
        } else
        {
            appguru.graphics.ogl.OGL.gl.glDepthMask(true);
            appguru.graphics.ogl.OGL.gl.glEnable(2929);
            appguru.graphics.ogl.OGL.gl.glDepthFunc(515);
            appguru.graphics.ogl.OGL.gl.glEnable(2884);
            appguru.graphics.ogl.OGL.gl.glCullFace(1029);
            appguru.graphics.ogl.OGL.gl.glDisable(3042);
        }
        appguru.graphics.ogl.Mesh.setTex("tex", a0.texture);
        appguru.graphics.ogl.Mesh.setTex("bumpmap", a0.bumpmap);
        appguru.graphics.ogl.Mesh.setTex("diffusemap", a0.diffusemap);
        appguru.graphics.ogl.Mesh.setTex("specularmap", a0.specularmap);
        appguru.graphics.ogl.Mesh.setTex("gaussianmap", a0.gaussianmap);
        appguru.Main.shaders.setUniform("uniform_Transformation", (Object)a.getMatrix().getMatrix());
        this.ibo.load(this.indices[a.mesh]);
        appguru.graphics.ogl.OGL.gl.glDrawElements(4, this.indices[a.mesh].length, 5125, 0L);
        this.last_mesh = a.mesh;
    }
    
    public void blitPlain(appguru.graphics.ogl.Model model) {
        appguru.graphics.ogl.Material material = appguru.Main.materials.getMaterial(model.material);
        if (appguru.Main.textures.getTexture(material.texture).hasAlpha) {
            appguru.graphics.ogl.OGL.gl.glDepthMask(true);
            appguru.graphics.ogl.OGL.gl.glEnable(2929);
            appguru.graphics.ogl.OGL.gl.glDepthFunc(515);
            appguru.graphics.ogl.OGL.gl.glDisable(2884);
            appguru.graphics.ogl.OGL.gl.glEnable(3042);
            appguru.graphics.ogl.OGL.gl.glBlendFunc(770, 771);
        } else
        {
            appguru.graphics.ogl.OGL.gl.glDepthMask(true);
            appguru.graphics.ogl.OGL.gl.glEnable(2929);
            appguru.graphics.ogl.OGL.gl.glDepthFunc(515);
            appguru.graphics.ogl.OGL.gl.glEnable(2884);
            appguru.graphics.ogl.OGL.gl.glCullFace(1029);
            appguru.graphics.ogl.OGL.gl.glDisable(3042);
        }
        appguru.graphics.ogl.Mesh.setTex("tex", material.texture);
        appguru.Main.shaders.setUniform("uniform_Transformation", (Object)model.getMatrix().getMatrix());
        this.ibo.load(this.indices[model.mesh]);
        appguru.graphics.ogl.OGL.gl.glDrawElements(4, this.indices[model.mesh].length, 5125, 0L);
        this.last_mesh = model.mesh;
    }
    
    public void blit(appguru.graphics.ogl.Model a, boolean b) {
        appguru.graphics.ogl.OGL.gl.glDepthMask(true);
        appguru.graphics.ogl.OGL.gl.glEnable(2929);
        appguru.graphics.ogl.OGL.gl.glDepthFunc(515);
        appguru.graphics.ogl.OGL.gl.glEnable(2884);
        appguru.graphics.ogl.OGL.gl.glCullFace(1029);
        appguru.graphics.ogl.OGL.gl.glDisable(3042);
        appguru.Main.shaders.setUniform("uniform_Transformation", (Object)a.getMatrix().getMatrix());
        this.ibo.load(this.indices[a.mesh]);
        appguru.graphics.ogl.OGL.gl.glDrawElements(4, this.indices[a.mesh].length, 5125, 0L);
        this.last_mesh = a.mesh;
    }
    
    public void blitShape(appguru.graphics.ogl.Model a) {
        appguru.graphics.ogl.Material a0 = appguru.Main.materials.getMaterial(a.material);
        if (appguru.Main.textures.getTexture(a0.texture).hasAlpha) {
            appguru.graphics.ogl.OGL.gl.glDepthMask(true);
            appguru.graphics.ogl.OGL.gl.glEnable(2929);
            appguru.graphics.ogl.OGL.gl.glDisable(2884);
            appguru.graphics.ogl.OGL.gl.glEnable(3042);
            appguru.graphics.ogl.OGL.gl.glBlendFunc(770, 771);
        } else
        {
            appguru.graphics.ogl.OGL.gl.glDepthMask(true);
            appguru.graphics.ogl.OGL.gl.glEnable(2929);
            appguru.graphics.ogl.OGL.gl.glEnable(2884);
            appguru.graphics.ogl.OGL.gl.glDisable(3042);
        }
        appguru.graphics.ogl.Mesh.setTex("tex", a0.texture);
        appguru.Main.shaders.setUniform("uniform_Transformation", (Object)a.getMatrix().getMatrix());
        this.ibo.load(this.indices[a.mesh]);
        appguru.graphics.ogl.OGL.gl.glDrawElements(4, this.indices[a.mesh].length, 5125, 0L);
    }
    
    public int loadMesh(String s) {
        Integer a = null;
        Integer a0 = (Integer)this.register.get((Object)s);
        label1: {
            label0: if (a0 != null) {
                appguru.info.Console.warningMessage(((Object)this).getClass().getName(), new StringBuilder().append("ATTEMPT TO LOAD : ").append(s).append(" AGAIN").toString());
                a = a0;
            } else
            {
                    appguru.timer.Timer a1 = appguru.Main.scheduler;
                    a = a0;
                    int i = a1.startTimeMeasure();
                    appguru.graphics.ogl.Mesh a2 = new appguru.graphics.ogl.Mesh(s);
                    long j = appguru.Main.scheduler.endTimeMeasure((byte)i);
                    this.meshes.add((Object)a2);
                    a = Integer.valueOf(this.meshes.size() - 1);
                    this.register.put((Object)s, (Object)a);
                    appguru.info.Console.successMessage(((Object)this).getClass().getName(), new StringBuilder().append("LOADED ").append(s).append(" IN ").append(Long.toString(j)).append(" MS").toString());
                break label1;
            }
            appguru.info.Console.errorMessage(((Object)this).getClass().getName(), s+" MODELFILE NOT FOUND");
        }
        return a.intValue();
    }
    
    public void loadMesh(String s, String s0) {
        appguru.graphics.ogl.Mesh a = (appguru.graphics.ogl.Mesh)this.meshes.get(this.loadMesh(new StringBuilder().append("res/models/").append(s).toString()));
        appguru.graphics.ogl.Mesh a0 = (appguru.graphics.ogl.Mesh)this.meshes.get(this.loadMesh(new StringBuilder().append("res/models/").append(s0).toString()));
        this.morphings.add((Object)new appguru.graphics.ogl.Morphing(a, a0, 0.0f));
    }
    
    public void loadMesh(String s, appguru.math.Vector3 a) {
        appguru.graphics.ogl.Mesh a0 = (appguru.graphics.ogl.Mesh)this.meshes.get(this.loadMesh(s));
        this.morphings.add((Object)new appguru.graphics.ogl.Morphing(a0, a));
    }
    
    public void loadMesh(appguru.graphics.ogl.Mesh a) {
        this.meshes.add((Object)a);
        int i = this.meshes.size() - 1;
        this.register.put((Object)"?", (Object)Integer.valueOf(i));
        this.morphings.add((Object)new appguru.graphics.ogl.Morphing(a));
    }
    
    public void loadVBO() {
        this.indices = new int[this.meshes.size()][];
        java.util.Iterator a = this.meshes.iterator();
        int i = 0;
        int i0 = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        Object a0 = a;
        while(((java.util.Iterator)a0).hasNext()) {
            appguru.graphics.ogl.Mesh a1 = (appguru.graphics.ogl.Mesh)((java.util.Iterator)a0).next();
            this.indices[i3] = new int[a1.indices.length];
            int i4 = 0;
            while(i4 < a1.indices.length) {
                this.indices[i3][i4] = a1.indices[i4] + i2;
                i4 = i4 + 1;
            }
            i = i + a1.vertices.length;
            i0 = i0 + a1.normals.length;
            i1 = i1 + a1.texcoords.length;
            i2 = i2 + a1.vertices.length / 3;
            i3 = i3 + 1;
        }
        float[] a2 = new float[i];
        float[] a3 = new float[i];
        float[] a4 = new float[i0];
        float[] a5 = new float[i1];
        java.util.Iterator a6 = this.meshes.iterator();
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        Object a7 = a6;
        while(((java.util.Iterator)a7).hasNext()) {
            appguru.graphics.ogl.Mesh a8 = (appguru.graphics.ogl.Mesh)((java.util.Iterator)a7).next();
            appguru.graphics.ogl.Morphing a9 = (appguru.graphics.ogl.Morphing)this.morphings.get(i8);
            System.arraycopy((Object)a8.vertices, 0, (Object)a2, i5, a8.vertices.length);
            System.arraycopy((Object)a9.transformations, 0, (Object)a3, i5, a9.transformations.length);
            System.arraycopy((Object)a8.normals, 0, (Object)a4, i6, a8.normals.length);
            System.arraycopy((Object)a8.texcoords, 0, (Object)a5, i7, a8.texcoords.length);
            i5 = i5 + a8.vertices.length;
            i6 = i6 + a8.normals.length;
            i7 = i7 + a8.texcoords.length;
            i8 = i8 + 1;
        }
        appguru.Main.vbos.load(0, a2);
        appguru.Main.vbos.bind(0);
        appguru.Main.vbos.load(1, a5);
        appguru.Main.vbos.bind(1);
        appguru.Main.vbos.load(2, a4);
        appguru.Main.vbos.bind(2);
        appguru.Main.vbos.load(3, a3);
        appguru.Main.vbos.bind(3);
    }
    
    public void clean() {
        appguru.Main.vbos.delete(0);
        appguru.Main.vbos.delete(1);
        appguru.Main.vbos.delete(2);
        this.ibo.delete();
        appguru.info.Console.successMessage(((Object)this).getClass().getName(), "DELETION");
    }
}
