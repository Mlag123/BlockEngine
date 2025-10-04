package org.mlag.Shapes;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.mlag.Core.Camera;
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
    public Vector3f position = new Vector3f();

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
    public void render() {
        shader.use();
        shader.setUniformMat4f("model", modelMatrix.get(Camera.getFloatBuffer()));
        shader.setUniformMat4f("view", Camera.getViewMatrix().get(Camera.getFloatBuffer()));
        shader.setUniformMat4f("projection", Camera.getProjectionMatrix().get(Camera.getFloatBuffer()));
        vao.draw();
    }

    public void translate(float x, float y, float z) {
        Vector3f deltaPos = new Vector3f(x,y,z);
        position.add(deltaPos);

        modelMatrix.identity().translate(position).scale(scale);
    }
    public void translate(Vector3f delta){
        position.add(delta);
        modelMatrix.identity().translate(position).scale(scale);
    }
    @Deprecated
    private void _translate(Vector3f vector3f){
        this.modelMatrix.translate(vector3f);
    }

    public void rotate(float angle, float ax, float ay, float az) {
        modelMatrix.rotate((float)Math.toRadians(angle), ax, ay, az);
    }
    public void rotate(float angle,Vector3f vector3f){
        modelMatrix.rotate((float) Math.toRadians(angle),vector3f);
    }

    public void setScale(float s) {
        this.scale = s;
        modelMatrix.scale(s);
    }
    public void setPosition(Vector3f vector3f){
        this.position = vector3f;
        modelMatrix.identity().translate(position).scale(scale);
    }

    public void setPosition(float x, float y, float z) {
        modelMatrix.identity().translate(x, y, z).scale(scale);
    }

    public void cleanUp() {
        vao.cleanUP();
        vbo.cleanUp();
    }

    public Vector3f getPosition() {
        return position;
    }

    public abstract void updateBody(float dt);

    //tescode


}
