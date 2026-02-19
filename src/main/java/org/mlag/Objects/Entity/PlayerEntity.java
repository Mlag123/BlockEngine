package org.mlag.Objects.Entity;

import org.joml.Vector3f;
import org.mlag.Core.GameLoop;
import org.mlag.Core.Shader;
import org.mlag.Maths.Collaiders.AABB;
import org.mlag.Shapes.SceneObject;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

public class PlayerEntity extends SceneObject {

    private int health = 100;
    private float speed = 0.7f;
    private float deltaTime;

    private Vector3f velocity = new Vector3f(0f, 0f, 0f);
    private boolean isGrounded = false;
    private float gravityStrength = -0.50f;
    private float groundY = -10f;
    private float jumpStrength = 20f;

    public PlayerEntity(Shader shader, String tag_object) {
        super(shader, tag_object);
    }

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

        this.initCollider(vertices);
    }


/*    @Override
    public void setPosition(Vector3f vector3f) {
        this.position = vector3f;
        float x = vector3f.x,y = vector3f.y-1.0f,z = vector3f.z;

        modelMatrix.identity().translate(x,y,z).scale(scale);
        updateCollider();
    }*/

    @Override
    public void updateBody(float dt) {
        this.deltaTime = dt;
        velocity.y += gravityStrength * deltaTime;

        Vector3f oldPos = this.position;

        //   position.y +=-(gravityStrength/2)*dt;


        if (!isGrounded) {
                this.translate(velocity);

        }


        if (AABB.intersects(this.collider, GameLoop.place.collider)) {
            this.position.set(oldPos);
            updateCollider();
            velocity.y = 0;
            isGrounded = true;
        } else {
            isGrounded = false;
        }
    }

    public void jump(){
        velocity.y +=speed*deltaTime;
        isGrounded = false;

        //  this.translate(velocity);
    }
}

