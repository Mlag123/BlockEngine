package org.mlag.Objects.GameObjects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.mlag.Core.Camera;
import org.mlag.Core.Shader;
import org.mlag.Main;
import org.mlag.Shapes.Cube;
import org.mlag.Shapes.Square;

import static org.mlag.Core.Window.HEIGHT;
import static org.mlag.Core.Window.WIDTH;

public class Cross extends Square {
    public Cross(Shader shader) {
        super(shader, "cross");

    }

    float yaw, pitch;

    public void rotateForCam(float dx, float dy) {
        yaw += dx;
        pitch += dy;
        pitch = Math.max(-89, Math.min(89, pitch));

        rotate(yaw, new Vector3f(0, 1, 0));
        rotate(pitch, new Vector3f(1, 0, 0));
        //    rotateX(yaw);
        //  rotateZ(pitch);
    }

    public void faceCamera(Camera camera) {
        Matrix4f modelMatrix = Camera.getViewMatrix();
        Matrix4f projectionMatrix = Camera.getProjectionMatrix();


    }


    public void render2D() {
        shader.use();
        Matrix4f ortho = new Matrix4f().ortho2D(0, WIDTH, 0, HEIGHT);
        shader.setUniformMat4f("projection", ortho.get(Camera.getFloatBuffer()));

        Matrix4f model = new Matrix4f()
                .identity()
                .translate(WIDTH / 2f, HEIGHT / 2f, 0) // по центру экрана
                .scale(20f); // настрой размер прицела

        shader.setUniformMat4f("model", model.get(Camera.getFloatBuffer()));

        vao.draw();
    }




    @Override
    public void updateBody(float dt) {

    }
}
