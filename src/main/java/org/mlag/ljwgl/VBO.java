package org.mlag.ljwgl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15C.*;

public class VBO {
    private int vboID = 0;
    private boolean dispose = false;
    private final Logger log = LogManager.getLogger(this.getClass());
    public VBO() {
        vboID = glGenBuffers();
        //     glBindBuffer(GL_ARRAY_BUFFER,vboID);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
    }


    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void uploadData(float[] vertices) {
        FloatBuffer floatBuffer = null;
        try {
            floatBuffer = MemoryUtil.memAllocFloat(vertices.length);
            floatBuffer.put(vertices).flip();
            bind();
            glBufferData(GL_ARRAY_BUFFER, floatBuffer, GL_STATIC_DRAW);


        } finally {
            if (floatBuffer != null) {
                MemoryUtil.memFree(floatBuffer);
            }
        }
    }

    public void updateData(float[] vertices, long offset) {
        FloatBuffer floatBuffer = null;
        try {
            floatBuffer = MemoryUtil.memAllocFloat(vertices.length);
            floatBuffer.put(vertices).flip();
            bind();
            glBufferSubData(GL_ARRAY_BUFFER, offset, floatBuffer);


        } finally {
            if (floatBuffer != null) {
                MemoryUtil.memFree(floatBuffer);
            }
        }

    }

    public void cleanUp() {
        if (!dispose) {
            glDeleteBuffers(vboID);
            dispose = true;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if(!dispose){
                log.error("VBO "+vboID+" not removed");
                cleanUp();
            }
        }finally {
            super.finalize();
        }
    }
}
