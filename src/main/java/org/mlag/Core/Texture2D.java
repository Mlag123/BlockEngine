package org.mlag.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL30C.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Texture2D {
    private final Logger log = LogManager.getLogger(this.getClass());

    private int id;


    public Texture2D(String pathTexture) {

        if (!GL.getCapabilities().OpenGL11) {
            throw new RuntimeException("OpenGL 1.1 not supported");
        }

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

// Поведение на краях текстуры
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            ByteBuffer image = stbi_load(pathTexture, width, height, channels, 4);
            if (image == null) {
                throw new RuntimeException("Error reading image");
            }

            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA , GL_UNSIGNED_BYTE, image);
            glGenerateMipmap(GL_TEXTURE_2D);
     //       stbi_image_free(image);

        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void cleanUP() {
        glDeleteTextures(id);
    }
}
