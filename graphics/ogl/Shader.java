/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.ogl;

import appguru.helper.FileHelper;
import appguru.info.Console;
import com.jogamp.opengl.GL3;

/**
 *
 * @author lars
 */
public class Shader {
    public int type;
    public int location;
    public String filepath;
    public String shader;
    public Shader(String file, int type) {
        this.type=type;
        location = OGL.gl.glCreateShader(this.type);
        filepath=file;
        shader=FileHelper.readFile(file);
    }
    public void compile() {
        OGL.gl.glShaderSource(location, 1, new String[] {shader}, new int[] {shader.length()}, 0);
        OGL.gl.glCompileShader(location);
        int[] error = new int[1];
        OGL.gl.glGetShaderiv(location, GL3.GL_COMPILE_STATUS, error,0);
        if (error[0] == 0) {
            int[] debug_length = new int[1];
            OGL.gl.glGetShaderiv(location, GL3.GL_INFO_LOG_LENGTH, debug_length, 0);
            byte[] debug = new byte[debug_length[0]];
            OGL.gl.glGetShaderInfoLog(location, debug_length[0], null, 0, debug, 0);
            Console.setColor(Console.RED);
            if (type==GL3.GL_VERTEX_SHADER) {
                Console.print("VERTEX");
            }
            else if (type==GL3.GL_FRAGMENT_SHADER) {
                Console.print("FRAGMENT");
            }
            else if (type==GL3.GL_GEOMETRY_SHADER) {
                Console.print("GEOMETRY");
            }
            Console.print(" SHADER : ERROR : " + new String(debug));
            System.exit(1);
        }
        else {
            Console.setColor(Console.GREEN);
            if (type==GL3.GL_VERTEX_SHADER) {
                Console.print("VERTEX");
            }
            else if (type==GL3.GL_FRAGMENT_SHADER) {
                Console.print("FRAGMENT");
            }
            else if (type==GL3.GL_GEOMETRY_SHADER) {
                Console.print("GEOMETRY");
            }
            Console.printLn(" SHADER : COMPILATION SUCCESSFULL");
        }
    }
}
