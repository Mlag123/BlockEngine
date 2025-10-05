package org.mlag.Shapes;

import org.joml.Matrix4f;
import org.mlag.Core.Camera;
import org.mlag.Core.Shader;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

public class Square extends SceneObject{


    public Square(Shader shader, String tag_object) {
        super(shader, tag_object);
    }

    @Override
    protected void setupMesh() {
        float[] vertices = {
                // Передняя грань
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,

        };

        vao = new VAO();
        vbo = new VBO();
        vbo.uploadData(vertices);
        vao.linkVBO(vbo, 0, 3, 3 * Float.BYTES, 0);
        vao.setVertexCount(vertices.length / 3);
        initColliderFromMesh(vertices);
    }

    @Override
    public void updateBody(float dt) {

    }


}
