package org.mlag.Core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(2,2,2);
    private Matrix4f viewMatrix  = new Matrix4f();
    private Matrix4f projectionMatrix;
    private float yaw = -45;
    private float pitch = -30;

    public Camera(float fov,float aspect,float near,float far,float eyeX,float eyeY,float eyeZ){
/*
        viewMatrix = new Matrix4f().lookAt(
                eyeX,eyeY,eyeZ,
                0,0,0,
                0,1,0
        );
*/

        projectionMatrix = new Matrix4f().perspective(
                (float) Math.toRadians(fov),
                aspect,
                near,
                far
        );
        updateViewMatrix();
    }


    private void updateViewMatrix() {
        // вычисляем куда смотрим
        Vector3f target = new Vector3f(
                (float)Math.cos(Math.toRadians(pitch)) * (float)Math.cos(Math.toRadians(yaw)),
                (float)Math.sin(Math.toRadians(pitch)),
                (float)Math.cos(Math.toRadians(pitch)) * (float)Math.sin(Math.toRadians(yaw))
        ).add(position);
        viewMatrix.identity().lookAt(position, target, new Vector3f(0,1,0));
    }

    public Matrix4f getViewMatrix(){return viewMatrix;}
    public Matrix4f getProjectionMatrix(){return projectionMatrix;}


    public void rotate(float dx, float dy) {
        yaw += dx;
        pitch += dy;
        pitch = Math.max(-89, Math.min(89, pitch));
        updateViewMatrix();
    }
    public Vector3f getPosition() { return position; }
    public void setPosition(Vector3f pos) { position.set(pos); updateViewMatrix(); }
    public void translate(float x,float y,float z){viewMatrix.translate(x,y,z);}
    public void rotate(float angle,float ax,float ay,float az){viewMatrix.rotate(angle,ax,ay,az);}

    public void move(float dx, float dy, float dz) {
        Vector3f front = new Vector3f(
                (float)Math.cos(Math.toRadians(pitch)) * (float)Math.cos(Math.toRadians(yaw)),
                (float)Math.sin(Math.toRadians(pitch)),
                (float)Math.cos(Math.toRadians(pitch)) * (float)Math.sin(Math.toRadians(yaw))
        );

        front.normalize();

        Vector3f right = new Vector3f();
        front.cross(new Vector3f(0,1,0), right).normalize();

        Vector3f up = new Vector3f(0,1,0);

        Vector3f moveVec = new Vector3f();
        moveVec.fma(dx, right); // moveVec += dx * right
        moveVec.fma(dy, up);    // moveVec += dy * up
        moveVec.fma(dz, front); // moveVec += dz * front

        position.add(moveVec);

        updateViewMatrix();
    }

}
