package org.mlag.Objects.GameObjects;

import org.mlag.Core.OBJLoader;
import org.mlag.Core.Shader;
import org.mlag.Shapes.SceneObject;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

import java.io.IOException;

public class Test extends SceneObject {
    public Test(Shader shader) {
        super(shader, "test");
    }

    @Override
    protected void setupMesh() {
        OBJLoader.Mesh m = new OBJLoader.Mesh();
        String path = "resources/obj/fonar.obj";

        try {
            m = OBJLoader.loadOBJ(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        float[] vertices = m.vertices.get(0);

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
