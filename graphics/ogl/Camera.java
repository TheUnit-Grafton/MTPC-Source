package appguru.graphics.ogl;

import appguru.helper.Saver;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Camera {
    public com.jogamp.opengl.math.Matrix4 matrix;
    public com.jogamp.opengl.math.Quaternion rotation;
    public appguru.math.Vector3 position;
    public appguru.math.Vector3 scale;
    public appguru.math.Vector3 center;
    public appguru.math.Vector2 frictionx;
    public appguru.math.Vector2 frictiony;
    public appguru.math.Vector2 frictionz;
    
    public Camera() {
        super();
        this.matrix = new com.jogamp.opengl.math.Matrix4();
        this.rotation = new com.jogamp.opengl.math.Quaternion();
        this.position = new appguru.math.Vector3(0.0f, 0.0f, 0.0f);
        this.scale = new appguru.math.Vector3(1f, 1f, 1f);
        this.center = new appguru.math.Vector3(0.0f, 0.0f, 0.0f);
        this.frictionx = new appguru.math.Vector2(0.0f, 0.0f);
        this.frictiony = new appguru.math.Vector2(0.0f, 0.0f);
        this.frictionz = new appguru.math.Vector2(0.0f, 0.0f);
    }
    
    public String toString() {
        try {
            return matrix.toString()+ " ; "+rotation.toString()+" ; "+position.toString()+" ; "+scale.toString()+" ; "+center.toString();
        } catch (Exception e) {}
        return "FAIL";
    }
    
    public float tryFric(float f, float f0) {
        float f1 = f / f0;
        if (f1 == 0.0f / 0.0f) {
            return 0.0f;
        }
        return f1;
    }
    
    public void getFrictions(appguru.math.Vector3 a) {
        this.frictionx.x = this.tryFric(a.y, a.x);
        this.frictionx.y = this.tryFric(a.z, a.x);
        this.frictiony.x = this.tryFric(a.x, a.y);
        this.frictiony.y = this.tryFric(a.z, a.y);
        this.frictionz.x = this.tryFric(a.x, a.z);
        this.frictionz.y = this.tryFric(a.y, a.z);
    }
    
    public appguru.math.Vector3 getPointing(appguru.math.Vector3 a) {
        float[] a0 = new float[3];
        a0[0] = a.x;
        a0[1] = a.y;
        a0[2] = a.z;
        float[] a1 = new float[3];
        this.rotation.conjugate();
        this.rotation.rotateVector(a1, 0, a0, 0);
        this.rotation.conjugate();
        appguru.math.Vector3 a2 = new appguru.math.Vector3(a1[0], a1[1], a1[2]);
        this.getFrictions(a2);
        return a2;
    }
    
    public void move(float f, float f0, float f1) {
        appguru.math.Vector3 a = this.position;
        a.x = a.x + f;
        appguru.math.Vector3 a0 = this.position;
        a0.y = a0.y + f0;
        appguru.math.Vector3 a1 = this.position;
        a1.z = a1.z + f1;
    }
    
    public void setPosition(float f, float f0, float f1) {
        this.position.x = f;
        this.position.y = f0;
        this.position.z = f1;
    }
    
    public void moveCenter(float f, float f0, float f1) {
        appguru.math.Vector3 a = this.center;
        a.x = a.x + f;
        appguru.math.Vector3 a0 = this.center;
        a0.y = a0.y + f0;
        appguru.math.Vector3 a1 = this.center;
        a1.z = a1.z + f1;
    }
    
    public void setCenter(float f, float f0, float f1) {
        this.center.x = f;
        this.center.y = f0;
        this.center.z = f1;
    }
    
    public void scale(float f, float f0, float f1) {
        appguru.math.Vector3 a = this.scale;
        a.x = a.x * f;
        appguru.math.Vector3 a0 = this.scale;
        a0.y = a0.y * f0;
        appguru.math.Vector3 a1 = this.scale;
        a1.z = a1.z * f1;
    }
    
    public void setScale(float f, float f0, float f1) {
        this.scale.x = f;
        this.scale.y = f0;
        this.scale.z = f1;
    }
    
    public void rotateQuaternionD(float f, float f0, float f1, float f2) {
        com.jogamp.opengl.math.Quaternion a = new com.jogamp.opengl.math.Quaternion();
        a.rotateByAngleY((float)Math.toRadians((double)(f * f1)));
        com.jogamp.opengl.math.Quaternion a0 = new com.jogamp.opengl.math.Quaternion();
        a.rotateByAngleX((float)Math.toRadians((double)(f * f0)));
        com.jogamp.opengl.math.Quaternion a1 = new com.jogamp.opengl.math.Quaternion();
        a.rotateByAngleZ((float)Math.toRadians((double)(f * f2)));
        this.rotation.mult(a1);
        this.rotation.mult(a);
        this.rotation.mult(a0);
        this.rotation.normalize();
    }
    
    public void rotateEuler(float f, float f0, float f1, float f2) {
        this.rotation.rotateByEuler(f * f0, f * f1, f * f2);
    }
    
    public void rotateQuaternion(float f, float f0, float f1, float f2) {
        com.jogamp.opengl.math.Quaternion a = new com.jogamp.opengl.math.Quaternion();
        a.rotateByAngleY(f * f1);
        com.jogamp.opengl.math.Quaternion a0 = new com.jogamp.opengl.math.Quaternion();
        a.rotateByAngleX(f * f0);
        com.jogamp.opengl.math.Quaternion a1 = new com.jogamp.opengl.math.Quaternion();
        a.rotateByAngleZ(f * f2);
        this.rotation.mult(a1);
        this.rotation.mult(a);
        this.rotation.mult(a0);
        this.rotation.normalize();
    }
    
    public void rotateEulerD(float f, float f0, float f1, float f2) {
        this.rotation.rotateByEuler((float)Math.toRadians((double)(f * f0)), (float)Math.toRadians((double)(f * f1)), (float)Math.toRadians((double)(f * f2)));
    }
    
    public appguru.math.Vector3 getPointing() {
        com.jogamp.opengl.math.Matrix4 a = new com.jogamp.opengl.math.Matrix4();
        a.loadIdentity();
        a.rotate(this.rotation);
        float[] a0 = new float[3];
        float[] a1 = new float[3];
        a1[0] = 0.0f;
        a1[1] = 0.0f;
        a1[2] = -1f;
        a.multVec(a1, a0);
        return new appguru.math.Vector3(a0[0], a0[1], a0[2]);
    }
    
    public com.jogamp.opengl.math.Matrix4 getMatrix() {
        com.jogamp.opengl.math.Matrix4 a = new com.jogamp.opengl.math.Matrix4();
        a.loadIdentity();
        a.translate(-this.center.x, -this.center.y, -this.center.z);
        a.rotate(this.rotation);
        a.translate(this.center.x, this.center.y, this.center.z);
        com.jogamp.opengl.math.Matrix4 a0 = new com.jogamp.opengl.math.Matrix4();
        a0.loadIdentity();
        a0.scale(this.scale.x, this.scale.y, this.scale.z);
        a0.translate(this.position.x, this.position.y, this.position.z);
        a0.multMatrix(a);
        this.matrix = a0;
        return a0;
    }
}
