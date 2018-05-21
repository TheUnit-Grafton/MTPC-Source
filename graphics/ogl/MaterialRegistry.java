/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.ogl;

import appguru.Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author lars
 */
public class MaterialRegistry {
    public List<Material> materials;
    public HashMap<Integer,Integer> indexes;
    public int loadMaterial(String texture, String bumpmap, String diffusemap, String specularmap, String gaussianmap) {
        int tex=Main.textures.loadTexture("res/textures/"+texture, 0);
        int bump=Main.textures.loadTexture("res/textures/"+bumpmap, 1);
        int dif=Main.textures.loadTexture("res/textures/"+diffusemap, 2);
        int spec=Main.textures.loadTexture("res/textures/"+specularmap, 3);
        int gauss=Main.textures.loadTexture("res/textures/"+gaussianmap, 4);
        materials.add(new Material(tex,bump,dif,spec,gauss));
        indexes.put(materials.size()-1,materials.size()-1);
        return materials.size()-1;
    }
    public int loadMaterial(int texture, int bumpmap, int diffusemap, int specularmap, int gaussianmap) {
        materials.add(new Material(texture,bumpmap,diffusemap,specularmap,gaussianmap));
        indexes.put(materials.size()-1,materials.size()-1);
        return materials.size()-1;
    }
    public MaterialRegistry() {
        materials=new ArrayList<Material>();
        indexes=new HashMap();
    }
    public Material getMaterial(int i) {
        return materials.get(indexes.get(i));
    }
}
