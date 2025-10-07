package org.mlag.Shapes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.mlag.Core.Camera;
import org.mlag.Core.GameLoop;
import org.mlag.Core.Shader;
import org.mlag.Maths.Collaiders.AABBCollaider;
import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

import java.nio.FloatBuffer;

public abstract class SceneObject {
    private  final Logger log = LogManager.getLogger(SceneObject.class);
    private String tag_object;
    public AABBCollaider collider;
    protected VAO vao;
    protected VBO vbo;
    public  Shader shader;
    public Matrix4f modelMatrix;
    protected float scale = 1.0f;
    public Vector3f position = new Vector3f();
    public float angle;
    public Vector3f vecangle3f = new Vector3f();
    public void useShader(){
        shader.use();
/*        if (shader.equals(GameLoop.cubeGreen)){
            System.out.println("its green shader");
        }else if (shader.equals(GameLoop.cubeRed)){
            System.out.println("its red shader");

        }*/
    }

    public AABBCollaider getCollider(){
        return collider;
    }

    public SceneObject(Shader shader,String tag_object){
        this.tag_object = tag_object;
        this.shader  =shader;
        this.modelMatrix = new Matrix4f().identity();
        GameLoop.gameObjectArrays.add(this);

        setupMesh();
    }

    public String getTag_object() {
        return tag_object;
    }

    public void setTag_object(String tag_object) {
        this.tag_object = tag_object;
    }

    protected abstract void setupMesh();

    public void render(Matrix4f viewMatrix, Matrix4f projectionMatrix, FloatBuffer fb) {
     //   shader.use();
        shader.setUniformMat4f("model", modelMatrix.get(fb));
        shader.setUniformMat4f("view", viewMatrix.get(fb));
        shader.setUniformMat4f("projection", projectionMatrix.get(fb));
        vao.draw();
    }
    public void render() {
         shader.use();
        shader.setUniformMat4f("model", modelMatrix.get(Camera.getFloatBuffer()));
        shader.setUniformMat4f("view", Camera.getViewMatrix().get(Camera.getFloatBuffer()));
        shader.setUniformMat4f("projection", Camera.getProjectionMatrix().get(Camera.getFloatBuffer()));
        vao.draw();
    }

    public void translate(float x, float y, float z) {
        Vector3f deltaPos = new Vector3f(x,y,z);
        position.add(deltaPos);

        modelMatrix.identity().translate(position).scale(scale);
    }
    public void translate(Vector3f delta){
        position.add(delta);
        modelMatrix.identity().translate(position).scale(scale);
    }
    @Deprecated
    private void _translate(Vector3f vector3f){
        this.modelMatrix.translate(vector3f);
    }

    public void rotate(float angleDegrees, float ax, float ay, float az) {
        this.angle = angleDegrees;
        Vector3f axis = new Vector3f(ax,ay,az);
        // 1. Получаем угол в радианах
        float angleRad = (float) Math.toRadians(angleDegrees);

        // 2. Сбрасываем матрицу и снова применяем позицию
        modelMatrix.identity()
                .translate(position)  // вернуть объект на место
                .rotate(angleRad, axis) // повернуть относительно собственного центра
                .scale(scale); // если использовался scale
    }
    public void rotate(float angleDegrees, Vector3f axis) {
        this.angle = angleDegrees;

        // 1. Получаем угол в радианах
        float angleRad = (float) Math.toRadians(angleDegrees);

        // 2. Сбрасываем матрицу и снова применяем позицию
        modelMatrix.identity()
                .translate(position)  // вернуть объект на место
                .rotate(angleRad, axis) // повернуть относительно собственного центра
                .scale(scale); // если использовался scale
    }
    public void rotateX(float angleDegrees) {
        // Вращение вокруг оси X → (1, 0, 0)
        this.angle = angleDegrees;
        modelMatrix.rotate(
                (float) Math.toRadians(angleDegrees),
                1f, 0f, 0f
        );
    }

    public void rotateY(float angleDegrees) {
        // Вращение вокруг оси Y → (0, 1, 0)
        this.angle = angleDegrees;
        modelMatrix.rotate(
                (float) Math.toRadians(angleDegrees),
                0f, 1f, 0f
        );
    }

    public void rotateZ(float angleDegrees) {
        // Вращение вокруг оси Z → (0, 0, 1)
        this.angle = angleDegrees;
        modelMatrix.rotate(
                (float) Math.toRadians(angleDegrees),
                0f, 0f, 1f
        );
    }


    public void setScale(float s) {
        this.scale = s;
        modelMatrix.scale(s);
    }
    public void setPosition(Vector3f vector3f){
        this.position = vector3f;
        modelMatrix.identity().translate(position).scale(scale);
    }

    public void setPosition(float x, float y, float z) {
        modelMatrix.identity().translate(x, y, z).scale(scale);
    }

    public void cleanUP() {
        vao.cleanUP();
        vbo.cleanUP();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void updateCollide(){
        if (collider != null) {
            Vector3f halfSize = new Vector3f(
                    (collider.max.x - collider.min.x) / 2f,
                    (collider.max.y - collider.min.y) / 2f,
                    (collider.max.z - collider.min.z) / 2f
            );
            collider.update(position, halfSize);
        }
    }

    public abstract void updateBody(float dt);

    //tescode
    protected void initColliderFromMesh(float[] vertices) {
        if (vertices == null || vertices.length == 0) return;

        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE, minZ = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE, maxZ = Float.MIN_VALUE;

        for (int i = 0; i < vertices.length; i += 3) {
            float x = vertices[i];
            float y = vertices[i + 1];
            float z = vertices[i + 2];

            if (x < minX) minX = x;
            if (y < minY) minY = y;
            if (z < minZ) minZ = z;

            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
            if (z > maxZ) maxZ = z;
        }

        Vector3f min = new Vector3f(minX, minY, minZ).add(position);
        Vector3f max = new Vector3f(maxX, maxY, maxZ).add(position);

        collider = new org.mlag.Maths.Collaiders.AABBCollaider(min, max);
    }


}
