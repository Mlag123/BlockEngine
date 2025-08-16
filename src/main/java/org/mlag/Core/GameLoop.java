package org.mlag.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.mlag.Shapes.Square;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;

public class GameLoop {

    private long window = NULL;
    private final Logger log = LogManager.getLogger(this.getClass());

    public GameLoop(long window) {
        this.window = window;
        if (window == NULL) {
            log.info("Window is not init");
            throw new RuntimeException("Window is not init");
        }
    }


    public void loop() {
        GL.createCapabilities();
        Texture2D texture2D = new Texture2D("resources/textures/boykisser.png");
        Shader shader = new Shader("resources/shaders/vertex.vert", "resources/shaders/fragment.frag");
        Square square = new Square();
        square.init();

        glActiveTexture(GL_TEXTURE0);
        texture2D.bind();
        shader.use();
        shader.setUniform1i("ourTexture", 0);

        // glClearColor(1.0f,0.0f,0.0f,0.0f);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //    glColor3f(0.5f,0.0f,0.0f);
            // Отрисовка квадрата

            shader.use();
            texture2D.bind();
            square.draw();


            glfwSwapBuffers(window);
            glfwPollEvents();
        }

    }

}
