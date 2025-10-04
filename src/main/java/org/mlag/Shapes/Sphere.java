package org.mlag.Shapes;

import org.mlag.Core.Shader;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

import java.util.ArrayList;
import java.util.List;

public class Sphere  extends SceneObject
{


    public Sphere(Shader shader) {
        super(shader);
    }

    //testcode
    @Override
    protected void setupMesh() {
        float radius = 1.0f;
        int sectorCount = 36;  // долготы
        int stackCount = 18;   // широты

        List<Float> vertices = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i <= stackCount; i++) {
            float stackAngle = (float) Math.PI / 2 - i * (float)Math.PI / stackCount;
            float xy = (float) Math.cos(stackAngle);
            float z = (float) Math.sin(stackAngle);

            for (int j = 0; j <= sectorCount; j++) {
                float sectorAngle = j * 2.0f * (float)Math.PI / sectorCount;

                float x = xy * (float)Math.cos(sectorAngle);
                float y = xy * (float)Math.sin(sectorAngle);

                // позиция
                vertices.add(x * radius);
                vertices.add(y * radius);
                vertices.add(z * radius);

                // нормаль (единичный вектор)
                vertices.add(x);
                vertices.add(y);
                vertices.add(z);
            }
        }

        for (int i = 0; i < stackCount; i++) {
            int k1 = i * (sectorCount + 1);
            int k2 = k1 + sectorCount + 1;

            for (int j = 0; j < sectorCount; j++, k1++, k2++) {
                if (i != 0) {
                    indices.add(k1);
                    indices.add(k2);
                    indices.add(k1 + 1);
                }
                if (i != (stackCount - 1)) {
                    indices.add(k1 + 1);
                    indices.add(k2);
                    indices.add(k2 + 1);
                }
            }
        }

        // Преобразуем в массивы
        float[] vertexArray = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) vertexArray[i] = vertices.get(i);

        int[] indexArray = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) indexArray[i] = indices.get(i);

        // загружаем данные как в других мешах

        vao = new VAO();
        vbo = new VBO();
        vbo.uploadData(vertexArray);
        vao.linkVBO(vbo, 0, 3, 3 * Float.BYTES, 0);
        vao.setVertexCount(vertexArray.length / 3);
    }

    @Override
    public void updateBody(float dt) {

    }
}
