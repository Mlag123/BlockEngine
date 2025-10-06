package org.mlag.Graphic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL30C.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture2D {
    private final Logger log = LogManager.getLogger(this.getClass());

    private int id;


    public Texture2D(String pathTexture) {


        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);


        int[] width = new int[1], height = new int[1], nrChannels = new int[1];

        ByteBuffer data = stbi_load(pathTexture, width, height, nrChannels, 3);
        if (data != null) {
            int format = nrChannels[0] == 4 ? GL_RGBA : GL_RGB;
            glTexImage2D(GL_TEXTURE_2D, 0, format, width[0], height[0], 0, format, GL_UNSIGNED_BYTE, data);
            glGenerateMipmap(GL_TEXTURE_2D);
            stbi_image_free(data);

        } else {
            log.error("Failed to load texture : " + pathTexture);
            throw new RuntimeException("Failed to load texture : " + pathTexture);
        }

    }
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void cleanUP() {
        glDeleteTextures(id);
    }
}
