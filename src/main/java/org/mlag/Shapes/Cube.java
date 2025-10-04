package org.mlag.Shapes;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.mlag.Core.Shader;
import org.mlag.Maths.Body;
import org.mlag.Maths.Collider;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Cube extends SceneObject {
    public Collider cubeCollader;
    public Cube(Shader shader) {
        super(shader);
        cubeCollader = new Collider(this.position);
        cubeCollader.setHalfSize(new Vector3f(0.5f,0.5f,0.5f));
    }

    private Body body = new Body();

    @Override
    protected void setupMesh() {
        float[] vertices = {
                // Задняя грань
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,

                // Передняя грань
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,

                // Левая грань
                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,

                // Правая грань
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,

                // Верхняя грань
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,

                // Нижняя грань
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f
        };

        vao = new VAO();
        vbo = new VBO();
        vbo.uploadData(vertices);
        vao.linkVBO(vbo, 0, 3, 3 * Float.BYTES, 0);
        vao.setVertexCount(vertices.length / 3);
    }

    boolean isPresed = false;

    public void setZeroCoordinate(boolean isPresed) {

    }

    @Override
    public void updateBody(float dt) {

        body.applyForce(new Vector3f(0, -0.01f,0f));

        // интеграция движения
        Vector3f deltaPos = body.intergrade(dt/128);
        translate(deltaPos);

        // обновление коллайдера
        cubeCollader.setPosition(new Vector3f(position));

        // столкновение с полом Y=0
        if(cubeCollader.getMinY() < 0){
            position.y = cubeCollader.getHalfSize().y; // ставим на пол
            body.velocity.y = 0; // останавливаем падение
            cubeCollader.setPosition(new Vector3f(position));
        }
        System.out.println(position.y);
    }


}
