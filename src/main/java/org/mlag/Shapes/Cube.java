package org.mlag.Shapes;

import org.joml.Matrix4f;
import org.mlag.Core.Shader;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

import java.nio.FloatBuffer;

public class Cube {

    private VAO vao;
    private VBO vbo;
    private Shader shader;
    private float[] position = {0, 0, 0};
    private float[] rotation = {0, 0, 0};
    private float scale = 1.0f;
    private Matrix4f modelMatrix;

    public Cube(Shader shader){
        this.shader = shader;
        modelMatrix = new Matrix4f().identity() ;
        setupMesh();
    }

    private void setupMesh(){
        float[] vertices = {
                // Задняя грань
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                -0.5f,  0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,

                // Передняя грань
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f,  0.5f, 0.5f,
                0.5f,  0.5f, 0.5f,
                -0.5f,  0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,

                // Левая грань
                -0.5f,  0.5f,  0.5f,
                -0.5f,  0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f,  0.5f,
                -0.5f,  0.5f,  0.5f,

                // Правая грань
                0.5f,  0.5f,  0.5f,
                0.5f,  0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,

                // Верхняя грань
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, 0.5f,  0.5f,
                0.5f, 0.5f,  0.5f,
                -0.5f, 0.5f,  0.5f,
                -0.5f, 0.5f, -0.5f,

                // Нижняя грань
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f,  0.5f,
                0.5f, -0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,
                -0.5f, -0.5f, -0.5f
        };

        vao = new VAO();
        vbo = new VBO();
        vbo.uploadData(vertices);
        vao.linkVBO(vbo,0,3,3*Float.BYTES,0);
        vao.setVertexCount(vertices.length/3);
    }

    public void translate(float x,float y,float z){
        modelMatrix.translate(x,y,z);
    }
    public void rotate(float angle,float ax,float ay,float az){
        modelMatrix.rotate((float) Math.toRadians(angle),ax,ay,az);
    }

    public void render(Matrix4f viewMatrix, Matrix4f projectionMatrix, FloatBuffer fb){
        shader.use();
        shader.setUniformMat4f("model",modelMatrix.get(fb));
        shader.setUniformMat4f("view",viewMatrix.get(fb));
        shader.setUniformMat4f("projection",projectionMatrix.get(fb));
        vao.draw();
    }

    public void setPosition(float x,float y,float z){
        position[0] = x;
        position[1] = y;
        position[2] = z;
    }
    public void rotate(float rx, float ry, float rz) {
        rotation[0] += rx;
        rotation[1] += ry;
        rotation[2] += rz;
    }

    public void setScale(float s) {
        scale = s;
    }

    public void cleanUp() {
        vao.cleanUP();
        vbo.cleanUp();
    }

}
