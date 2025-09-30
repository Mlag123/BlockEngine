package org.mlag.Core;

import org.joml.Matrix4f;

public class Camera {

    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;

    public Camera(float fov,float aspect,float near,float far,float eyeX,float eyeY,float eyeZ){
        viewMatrix = new Matrix4f().lookAt(
                eyeX,eyeY,eyeZ,
                0,0,0,
                0,1,0
        );

        projectionMatrix = new Matrix4f().perspective(
                (float) Math.toRadians(fov),
                aspect,
                near,
                far
        );
    }
    public Matrix4f getViewMatrix(){return viewMatrix;}
    public Matrix4f getProjectionMatrix(){return projectionMatrix;}

    public void translate(float x,float y,float z){viewMatrix.translate(x,y,z);}
    public void rotate(float angle,float ax,float ay,float az){viewMatrix.rotate(angle,ax,ay,az);}

}
