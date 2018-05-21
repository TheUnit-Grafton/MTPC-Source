/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.ogl;

/**
 *
 * @author lars
 */
public class Material {
    public int texture,bumpmap,diffusemap,specularmap,gaussianmap;
    public Material(int texture, int bumpmap, int diffusemap, int specularmap, int gaussianmap) {
        this.texture = texture;
        this.bumpmap = bumpmap;
        this.diffusemap = diffusemap;
        this.specularmap = specularmap;
        this.gaussianmap = gaussianmap;
    }
}
