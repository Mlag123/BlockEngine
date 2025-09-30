package org.mlag.Shapes;

import org.joml.Matrix4f;
import org.mlag.Core.Shader;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

import java.nio.FloatBuffer;

public abstract class SceneObject {

    protected VAO vao;
    protected VBO vbo;
    protected Shader shader;
    protected Matrix4f modelMatrix;
    protected float scale = 1.0f;

    public SceneObject(Shader shader){
        this.shader  =shader;
        this.modelMatrix = new Matrix4f().identity();
        setupMesh();
    }

    protected abstract void setupMesh();

    public void render(Matrix4f viewMatrix, Matrix4f projectionMatrix, FloatBuffer fb) {
        shader.use();
        shader.setUniformMat4f("model", modelMatrix.get(fb));
        shader.setUniformMat4f("view", viewMatrix.get(fb));
        shader.setUniformMat4f("projection", projectionMatrix.get(fb));
        vao.draw();
    }

    public void translate(float x, float y, float z) {
        modelMatrix.translate(x, y, z);
    }

    public void rotate(float angle, float ax, float ay, float az) {
        modelMatrix.rotate((float)Math.toRadians(angle), ax, ay, az);
    }

    public void setScale(float s) {
        this.scale = s;
        modelMatrix.scale(s);
    }

    public void setPosition(float x, float y, float z) {
        modelMatrix.identity().translate(x, y, z).scale(scale);
    }

    public void cleanUp() {
        vao.cleanUP();
        vbo.cleanUp();
    }

}
