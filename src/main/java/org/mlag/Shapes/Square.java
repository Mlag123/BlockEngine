package org.mlag.Shapes;

import org.mlag.ljwgl.VAO;
import org.mlag.ljwgl.VBO;

public class Square {

    private VAO vao;
    private VBO vbo;
    float[] sqVertices = {
            // Первый треугольник
            -1.0f,  1.0f, 0.0f,  // верхний левый
            1.0f,  1.0f, 0.0f,   // верхний правый
            -1.0f, -1.0f, 0.0f,   // нижний левый

            // Второй треугольник
            1.0f,  1.0f, 0.0f,   // верхний правый
            1.0f, -1.0f, 0.0f,   // нижний правый
            -1.0f, -1.0f, 0.0f    // нижний левый
    };

    public Square(){
        vao = new VAO();
        vbo = new VBO();
    }
    public void init(){
        vbo.uploadData(sqVertices);
        vao.linkVBO(vbo,0,3,0,0);
        vao.setVertexCount(6);

    }
    public void draw(){
        vao.draw();
    }

}
