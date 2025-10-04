package org.mlag.Shapes;

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
    private String tag_object;
    public AABBCollaider collider;
    protected VAO vao;
    protected VBO vbo;
    public  Shader shader;
    protected Matrix4f modelMatrix;
    protected float scale = 1.0f;
    public Vector3f position = new Vector3f();

    public void useShader(){
        shader.use();
/*        if (shader.equals(GameLoop.cubeGreen)){
            System.out.println("its green shader");
        }else if (shader.equals(GameLoop.cubeRed)){
            System.out.println("its red shader");

        }*/
    }

    public SceneObject(Shader shader,String tag_object){
        this.tag_object = tag_object;
        this.shader  =shader;
        this.modelMatrix = new Matrix4f().identity();
        GameLoop.gameObjectArrays.add(this);
        System.out.println(GameLoop.gameObjectArrays.size());
        setupMesh();
    }

    public String getTag_object() {
        return tag_object;
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

    public void rotate(float angle, float ax, float ay, float az) {
        modelMatrix.rotate((float)Math.toRadians(angle), ax, ay, az);
    }
    public void rotate(float angle,Vector3f vector3f){
        modelMatrix.rotate((float) Math.toRadians(angle),vector3f);
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

    public void cleanUp() {
        vao.cleanUP();
        vbo.cleanUp();
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
