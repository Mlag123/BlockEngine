package org.mlag.Graphic;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.mlag.Core.Camera;
import org.mlag.Core.Shader;

import java.nio.FloatBuffer;

public class Sun {

    public void setPower(float power) {
        this.power = power;
    }

    public void setPosition(Vector3f position) {
        this.position.add(position);
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    private float power = 1.0f;
    private Vector3f position = new Vector3f(0.6f,1,0);
    private float angle = 90f;


    public float getPower() {
        return power;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getAngle() {
        return angle;
    }
    public void applyToShader(Shader shader, Matrix4f model, Matrix4f view, Matrix4f projection) {
        shader.use();

        // 1. Матрицы
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        shader.setUniform3f("objectColor", 1.0f, 0.0f, 0.0f);

        matrixBuffer.clear();
        model.get(matrixBuffer);
        shader.setUniformMat4f("model", matrixBuffer);

        matrixBuffer.clear();
        view.get(matrixBuffer);
        shader.setUniformMat4f("view", matrixBuffer);

        matrixBuffer.clear();
        projection.get(matrixBuffer);
        shader.setUniformMat4f("projection", matrixBuffer);

        // 2. Свет
        // Для directional light используем направление (нормализованное)
        Vector3f lightDir = new Vector3f(position).normalize().negate();
        shader.setUniform3f("lightDir", lightDir);

        // 3. Цвет и мощность
        shader.setUniform3f("lightColor", 1f, 1f, 1f); // белый свет
        shader.setUniform1f("lightPower", power);
    }
}
