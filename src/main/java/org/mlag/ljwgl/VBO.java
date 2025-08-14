package org.mlag.ljwgl;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15C.*;

public class VBO
{
    private int vboID = 0;

    public VBO(){
        vboID  = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
    }

    public FloatBuffer getVBO(float [] vertices){
        FloatBuffer floatBuffer = MemoryUtil.memAllocFloat(vertices.length);
        floatBuffer.put(vertices).flip();
        glBufferData(GL_ARRAY_BUFFER,floatBuffer,GL_STATIC_DRAW);
        MemoryUtil.memFree(floatBuffer);
        return floatBuffer;
    }
}
