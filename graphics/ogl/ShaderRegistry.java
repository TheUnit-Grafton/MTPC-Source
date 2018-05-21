package appguru.graphics.ogl;

public class ShaderRegistry {
    public java.util.List programs;
    public java.util.List shaders;
    public java.util.Map uniform_locations;
    public java.util.Map ubo_locations;
    public int run;
    public int run_location;
    public int vertex;
    public int fragment;
    
    public ShaderRegistry() {
        super();
        this.programs = (java.util.List)(Object)new java.util.ArrayList();
        this.shaders = (java.util.List)(Object)new java.util.ArrayList();
        this.uniform_locations = (java.util.Map)(Object)new java.util.HashMap();
        this.ubo_locations = (java.util.Map)(Object)new java.util.HashMap();
        this.run = 0;
        this.vertex = -1;
        this.fragment = -1;
        appguru.info.Console.successMessage(((Object)this).getClass().getName(), "INITIALISATION");
    }
    
    public int getUniformLocation(String s) {
        if ((Integer)this.uniform_locations.get((Object)s) != null) {
            return ((Integer)this.uniform_locations.get((Object)s)).intValue();
        }
        Integer a = Integer.valueOf(appguru.graphics.ogl.OGL.gl.glGetUniformLocation(this.run_location, s));
        this.uniform_locations.put((Object)s, (Object)a);
        return a.intValue();
    }
    
    public int getUBOLocation(String s) {
        if ((Integer)this.ubo_locations.get((Object)s) != null) {
            return ((Integer)this.ubo_locations.get((Object)s)).intValue();
        }
        Integer a = Integer.valueOf(appguru.graphics.ogl.OGL.gl.glGetUniformBlockIndex(this.run_location, s));
        this.ubo_locations.put((Object)s, (Object)a);
        return a.intValue();
    }
    
    public void connectUBO(appguru.graphics.ogl.UBO a, String s) {
        appguru.graphics.ogl.OGL.gl.glUniformBlockBinding(this.run, this.getUBOLocation(s), a.id.get(0));
    }
    
    public void set(int i, Object a) {
        if (a instanceof float[]) {
            float[] a0 = (float[])a;
            switch(a0.length) {
                case 16: {
                    appguru.graphics.ogl.OGL.gl.glUniformMatrix4fv(i, 1, false, a0, 0);
                    break;
                }
                case 9: {
                    appguru.graphics.ogl.OGL.gl.glUniformMatrix3fv(i, 1, false, a0, 0);
                    break;
                }
                case 4: {
                    appguru.graphics.ogl.OGL.gl.glUniform4f(i, a0[0], a0[1], a0[2], a0[3]);
                    break;
                }
                case 3: {
                    appguru.graphics.ogl.OGL.gl.glUniform3f(i, a0[0], a0[1], a0[2]);
                    break;
                }
                case 2: {
                    appguru.graphics.ogl.OGL.gl.glUniform2f(i, a0[0], a0[1]);
                    break;
                }
                default: {
                    appguru.graphics.ogl.OGL.gl.glUniform1fv(i, a0.length, a0, 0);
                }
            }
        } else if (a instanceof Float) {
            appguru.graphics.ogl.OGL.gl.glUniform1f(i, ((Float)a).floatValue());
        } else if (a instanceof Integer) {
            appguru.graphics.ogl.OGL.gl.glUniform1i(i, ((Integer)a).intValue());
        }
    }
    
    public void setUniform(String s, Object a) {
        this.set(this.getUniformLocation(s), a);
    }
    
    public int add(appguru.graphics.ogl.Shader a) {
        this.shaders.add((Object)a);
        return this.shaders.size() - 1;
    }
    
    public appguru.graphics.ogl.Shader get(int i) {
        return (appguru.graphics.ogl.Shader)this.shaders.get(i);
    }
    
    public appguru.graphics.ogl.ShaderProgram createShaderProgram() {
        return new appguru.graphics.ogl.ShaderProgram();
    }
    
    public appguru.graphics.ogl.ShaderProgram createShaderProgram(int i, boolean b) {
        return new appguru.graphics.ogl.ShaderProgram(i, b);
    }
    
    public appguru.graphics.ogl.ShaderProgram createShaderProgram(int i, int i0) {
        return new appguru.graphics.ogl.ShaderProgram(i, i0);
    }
    
    public int addShaderProgram(String s, String s0) {
        appguru.graphics.ogl.Shader a = new appguru.graphics.ogl.Shader(new StringBuilder().append("res/shaders/").append(s).append(".glsl").toString(), 35633);
        a.compile();
        appguru.graphics.ogl.Shader a0 = new appguru.graphics.ogl.Shader(new StringBuilder().append("res/shaders/").append(s0).append(".glsl").toString(), 35632);
        a0.compile();
        int i = this.add(a);
        this.add(a0);
        this.programs.add((Object)this.createShaderProgram(i, false));
        return this.programs.size() - 1;
    }
    
    public int addShaderProgram(String s, String s0, String s1) {
        appguru.graphics.ogl.Shader a = new appguru.graphics.ogl.Shader(new StringBuilder().append("res/shaders/").append(s).append(".glsl").toString(), 35633);
        a.compile();
        appguru.graphics.ogl.Shader a0 = new appguru.graphics.ogl.Shader(new StringBuilder().append("res/shaders/").append(s0).append(".glsl").toString(), 35632);
        a0.compile();
        appguru.graphics.ogl.Shader a1 = new appguru.graphics.ogl.Shader(new StringBuilder().append("res/shaders/").append(s1).append(".glsl").toString(), 36313);
        a1.compile();
        int i = this.add(a);
        this.add(a0);
        this.add(a1);
        this.programs.add((Object)this.createShaderProgram(i, true));
        return this.programs.size() - 1;
    }
    
    public int addShaderProgram() {
        this.programs.add((Object)this.createShaderProgram());
        return this.programs.size() - 1;
    }
    
    public int addShaderProgram(int i) {
        this.programs.add((Object)this.createShaderProgram(i, false));
        return this.programs.size() - 1;
    }
    
    public int addShaderProgram(int i, int i0) {
        this.programs.add((Object)this.createShaderProgram(i, i0));
        return this.programs.size() - 1;
    }
    
    public int addShaderProgram(appguru.graphics.ogl.ShaderProgram a) {
        this.programs.add((Object)a);
        return this.programs.size() - 1;
    }
    
    public appguru.graphics.ogl.ShaderProgram getShaderProgram(int i) {
        return (appguru.graphics.ogl.ShaderProgram)this.programs.get(i);
    }
    
    public void setShaderProgram(int i) {
        this.run = i;
    }
    
    public void start() {
        this.getShaderProgram(this.run).start();
        this.run_location = ((appguru.graphics.ogl.ShaderProgram)this.programs.get(this.run)).run;
    }
    
    public void display() {
        this.getShaderProgram(this.run).display();
    }
    
    public void stop() {
        int i = 0;
        while(i < this.programs.size()) {
            this.getShaderProgram(i).stop();
            i = i + 1;
        }
        appguru.info.Console.successMessage(((Object)this).getClass().getName(), "SHUTDOWN");
    }
}
