package org.mlag.Shapes;

import org.mlag.Core.Shader;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

public class Place extends SceneObject{
    public Place(Shader shader) {
        super(shader);
    }

    @Override
    protected void setupMesh() {
        float[] vertices = {
                // X, Y, Z
                -0.5f, 0f, -0.5f,
                0.5f, 0f, -0.5f,
                0.5f, 0f,  0.5f,

                -0.5f, 0f, -0.5f,
                0.5f, 0f,  0.5f,
                -0.5f, 0f,  0.5f
        };

        vao = new VAO();
        vbo = new VBO();
        vbo.uploadData(vertices);
        vao.linkVBO(vbo, 0, 3, 3 * Float.BYTES, 0);
        vao.setVertexCount(vertices.length / 3);
    }
}
