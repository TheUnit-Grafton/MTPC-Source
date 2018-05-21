package appguru.graphics.ogl;

public class ShaderProgram {
    public int vertex;
    public int fragment;
    public int geometry;
    public int run;
    
    public ShaderProgram(int i, int i0, int i1) {
        super();
        this.vertex = i;
        this.fragment = i0;
        this.geometry = i1;
    }
    
    public ShaderProgram(int i, int i0) {
        super();
        this.vertex = i;
        this.fragment = i0;
        this.geometry = -1;
    }
    
    public ShaderProgram(int i, boolean b) {
        super();
        this.vertex = appguru.Main.shaders.get(i).location;
        this.fragment = appguru.Main.shaders.get(i + 1).location;
        if (b) {
            this.geometry = appguru.Main.shaders.get(i + 2).location;
        } else
        {
            this.geometry = -1;
        }
    }
    
    public ShaderProgram() {
        super();
        this.vertex = -1;
        this.fragment = -1;
        this.geometry = -1;
    }
    
    public void start() {
        this.run = appguru.graphics.ogl.OGL.gl.glCreateProgram();
        appguru.graphics.ogl.OGL.gl.glAttachShader(this.run, this.vertex);
        appguru.graphics.ogl.OGL.gl.glAttachShader(this.run, this.fragment);
        if (this.geometry > 0) {
            appguru.graphics.ogl.OGL.gl.glAttachShader(this.run, this.geometry);
        }
        appguru.graphics.ogl.OGL.gl.glBindAttribLocation(this.run, 0, "attribute_Position");
        appguru.graphics.ogl.OGL.gl.glBindAttribLocation(this.run, 1, "texcoords");
        appguru.graphics.ogl.OGL.gl.glBindAttribLocation(this.run, 2, "normal");
        appguru.graphics.ogl.OGL.gl.glBindAttribLocation(this.run, 3, "transform");
        appguru.graphics.ogl.OGL.gl.glLinkProgram(this.run);
    }
    
    public void display() {
        appguru.graphics.ogl.OGL.gl.glUseProgram(this.run);
    }
    
    public void stop() {
        appguru.graphics.ogl.OGL.gl.glUseProgram(0);
        appguru.graphics.ogl.OGL.gl.glDetachShader(this.run, this.vertex);
        appguru.graphics.ogl.OGL.gl.glDeleteShader(this.vertex);
        appguru.graphics.ogl.OGL.gl.glDetachShader(this.run, this.fragment);
        appguru.graphics.ogl.OGL.gl.glDeleteShader(this.fragment);
        appguru.graphics.ogl.OGL.gl.glDetachShader(this.run, this.geometry);
        appguru.graphics.ogl.OGL.gl.glDeleteShader(this.geometry);
        appguru.graphics.ogl.OGL.gl.glDeleteProgram(this.run);
    }
}
