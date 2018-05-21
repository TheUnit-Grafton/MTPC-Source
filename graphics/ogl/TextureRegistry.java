/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.ogl;

import appguru.helper.FileHelper;
import appguru.info.Console;
import com.jogamp.opengl.GL;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author lars
 */
public class TextureRegistry {
    public List<Texture> textures;
    public Map<String, Integer> register;
    public TextureRegistry() {
        textures=new ArrayList<Texture>();
        register=new HashMap();
    }
    public int[] loadFile(String file) throws FileNotFoundException, IOException{
        String[] split=FileHelper.readFile(file).split("\n");
        int size=textures.size();
        int textures_added=0;
        for (String line:split) {
            textures.add(new Texture(line,0));
            textures_added++;
        }
        int[] result=new int[textures_added];
        for (int i=0; i < textures_added; i++) {
            result[i]=size+i;
        }
        return result;
    }
    public int[] loadFile(File file) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line=reader.readLine();
        int size=textures.size();
        int textures_added=0;
        while (line != null) {
            textures.add(new Texture(line,0));
            textures_added++;
            line=reader.readLine();
        }
        int[] result=new int[textures_added];
        for (int i=0; i < textures_added; i++) {
            result[i]=size+i;
        }
        return result;
    }
    public int[] loadFile(URL url) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line=reader.readLine();
        int size=textures.size();
        int textures_added=0;
        while (line != null) {
            textures.add(new Texture(appguru.AGE.class.getClassLoader().getResource(line),0));
            textures_added++;
            line=reader.readLine();
        }
        int[] result=new int[textures_added];
        for (int i=0; i < textures_added; i++) {
            result[i]=size+i;
        }
        return result;
    }
    public int loadTexture(String filename, int index) {
        Integer i=register.get(filename);
        if (i==null) {
            textures.add(new Texture(filename,index));
            register.put(filename, textures.size()-1);
            i=textures.size()-1;
            Console.setColor(Console.GREEN);
            Console.printLn("SUCCESS : TEXTURE "+filename+" LOADED");
        }
        else {
            Console.setColor(Console.RED);
            Console.printLn("WARNING : ATTEMPT TO LOAD TEXTURE "+filename+" AGAIN");
        }
        return i;
    }
    public Texture getTexture(int texIndex) {
        return textures.get(texIndex);
    }
    public int loadTexture(Texture nw) {
        textures.add(nw);
        return textures.size()-1;
    }
    public void clean() {
        for (Texture t:textures) {
            t.delete();
        }
        textures=null;
        register=null;
    }
}
