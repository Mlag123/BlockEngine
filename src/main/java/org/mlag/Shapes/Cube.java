package org.mlag.Shapes;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.mlag.Core.GameLoop;
import org.mlag.Core.Shader;
import org.mlag.Maths.Body;
import org.mlag.Maths.Collider;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Cube extends SceneObject {
   // public Collider cubeCollader;

    public Cube(Shader shader) {
        super(shader, "cube");
     //   cubeCollader = new Collider(this.position);
       // cubeCollader.setHalfSize(new Vector3f(0.5f, 0.5f, 0.5f));
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
        initColliderFromMesh(vertices);
    }

    boolean isPresed = false;

    public void setZeroCoordinate(boolean isPresed) {


    }

    @Override
    public void updateBody(float dt) {
        updateCollide();
        for (SceneObject sc : GameLoop.gameObjectArrays) {
            if (sc.getTag_object().equals("place")) {
                boolean hasCollied = this.collider.intersects(sc.collider);
                //  System.out.println(hasCollied);
            }
            if (this.collider.intersects(sc.getCollider())) {
                System.out.println("Cube collided with: " + sc.getTag_object());
            }
        }
    }


}
