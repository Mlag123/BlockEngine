package org.mlag.Objects.GameObjects;

import org.mlag.Core.Shader;
import org.mlag.Shapes.Cube;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

public class SkyBox extends Cube {
    public SkyBox(Shader shader) {
        super(shader);
        setTag_object("SkyBox");
        setupMesh();
        setScale(120f);

    }

    @Override
    protected void setupMesh() {
        float[] vertices = {
                // x, y, z, u, v
                // Задняя грань
                -0.5f, -0.5f, -0.5f, 0f, 0f,
                0.5f, -0.5f, -0.5f, 1f, 0f,
                0.5f,  0.5f, -0.5f, 1f, 1f,
                0.5f,  0.5f, -0.5f, 1f, 1f,
                -0.5f,  0.5f, -0.5f, 0f, 1f,
                -0.5f, -0.5f, -0.5f, 0f, 0f,

                // Передняя грань
                -0.5f, -0.5f, 0.5f, 0f, 0f,
                0.5f, -0.5f, 0.5f, 1f, 0f,
                0.5f,  0.5f, 0.5f, 1f, 1f,
                0.5f,  0.5f, 0.5f, 1f, 1f,
                -0.5f,  0.5f, 0.5f, 0f, 1f,
                -0.5f, -0.5f, 0.5f, 0f, 0f,

                // Левая грань
                -0.5f, 0.5f,  0.5f, 1f, 1f,
                -0.5f, 0.5f, -0.5f, 0f, 1f,
                -0.5f, -0.5f,-0.5f, 0f, 0f,
                -0.5f, -0.5f,-0.5f, 0f, 0f,
                -0.5f, -0.5f, 0.5f, 1f, 0f,
                -0.5f, 0.5f,  0.5f, 1f, 1f,

                // Правая грань
                0.5f, 0.5f,  0.5f, 1f, 1f,
                0.5f, 0.5f, -0.5f, 0f, 1f,
                0.5f,-0.5f, -0.5f, 0f, 0f,
                0.5f,-0.5f, -0.5f, 0f, 0f,
                0.5f,-0.5f,  0.5f, 1f, 0f,
                0.5f, 0.5f,  0.5f, 1f, 1f,

                // Верхняя грань
                -0.5f, 0.5f, -0.5f, 0f, 1f,
                0.5f, 0.5f, -0.5f, 1f, 1f,
                0.5f, 0.5f,  0.5f, 1f, 0f,
                0.5f, 0.5f,  0.5f, 1f, 0f,
                -0.5f, 0.5f,  0.5f, 0f, 0f,
                -0.5f, 0.5f, -0.5f, 0f, 1f,

                // Нижняя грань
                -0.5f,-0.5f, -0.5f, 0f, 1f,
                0.5f,-0.5f, -0.5f, 1f, 1f,
                0.5f,-0.5f,  0.5f, 1f, 0f,
                0.5f,-0.5f,  0.5f, 1f, 0f,
                -0.5f,-0.5f,  0.5f, 0f, 0f,
                -0.5f,-0.5f, -0.5f, 0f, 1f,
        };

        vao = new VAO();
        vbo = new VBO();
        vbo.uploadData(vertices);

        // Вершины (location = 0)
        vao.linkVBO(vbo, 0, 3, 5 * Float.BYTES, 0);
        // UV (location = 1)
        vao.linkVBO(vbo, 1, 2, 5 * Float.BYTES, 3 * Float.BYTES);

        vao.setVertexCount(vertices.length / 5);
        initColliderFromMesh(vertices);
    }

}
