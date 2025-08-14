package org.mlag.Maths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vector3D {

    public float x, y, z;
    public final static Vector3D zero_vector = new Vector3D(0.0f, 0.0f, 0.0f);


    public Vector3D() {

    }

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Vector3D vector3D) {
        this.x = vector3D.getX();
        this.y = vector3D.getY();
        this.z = vector3D.getZ();
    }


    public static float longVector(Vector3D vector3D){
        if(vector3D != null){
            float x,y,z,res;
            x = vector3D.getX();
            y = vector3D.getY();
            z = vector3D.getZ();
            res = (x*x+y*y+z*z);
            res = (float) Math.sqrt((float) res);
            return (float) Math.abs((float)res);
        }
        System.err.println("vector is null");
        throw new NullPointerException("Vector is null");
    }

    public static float getScalarVector(Vector3D vec1,Vector3D vec2,float angle){
        float longVec1,longVec2;
        longVec1 = longVector(vec1);
        longVec2 = longVector(vec2);

        return (longVec1*longVec2)*(float)Math.cos(angle);
    }
    public static Vector3D normalVector(Vector3D vector3D){
        if(vector3D != null){
            float x,y,z,longVec;
            x = vector3D.getX();
            y = vector3D.getY();
            z = vector3D.getZ();
            longVec = longVector(vector3D);
            return new Vector3D(x/longVec,y/longVec,z/longVec);
        }
        System.err.println("vector is null");
        throw new NullPointerException("Vector is null");
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
