package org.mlag.Shapes;

import org.joml.Vector3f;
import org.mlag.Core.Shader;
import org.mlag.Maths.Collider;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

public class Place extends SceneObject{
    public Place(Shader shader) {
        super(shader,"place");
        setupMesh();
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




        initCollider(vertices);
    }

    @Override
    public void updateBody(float dt) {
    // this.collider.setPosition(this.position,1f,1f,1f);
    }
    @Override
    protected void updateCollider() {
        if (collider != null) {
            // Базовые размеры (без масштаба)
            float baseWidth = 1.0f;
            float baseHeight = 0.1f;
            float baseDepth = 1.0f;

            // Применяем масштаб к размерам коллайдера
            collider.setPosition(
                    this.position.x,
                    this.position.y,
                    this.position.z,
                    baseWidth * scale,   // ширина с учетом масштаба
                    baseHeight,  // толщина с учетом масштаба
                    baseDepth * scale    // глубина с учетом масштаба
            );
        }
    }
}
