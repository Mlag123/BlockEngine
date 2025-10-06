package org.mlag.Shapes;

import org.mlag.Core.OBJLoader;
import org.mlag.Core.Shader;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sphere  extends SceneObject
{


    public Sphere(Shader shader) {
        super(shader,"sphere");
    }

    //testcode
    @Override
    protected void setupMesh() {

        OBJLoader.Mesh m = new OBJLoader.Mesh();
        String path = "resources/obj/sphere.obj";

        try {
            m = OBJLoader.loadOBJ(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Собираем все вершины в один массив
        float[] vertices = new float[m.faces.size() * 3 * 3]; // faces * 3 вершины * 3 координаты
        int index = 0;

        for (int[] face : m.faces) {        // перебираем все треугольники
            for (int vertexIndex : face) {  // перебираем вершины в грани
                float[] v = m.vertices.get(vertexIndex);
                vertices[index++] = v[0];
                vertices[index++] = v[1];
                vertices[index++] = v[2];
            }
        }

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
