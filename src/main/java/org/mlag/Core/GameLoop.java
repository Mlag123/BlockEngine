package org.mlag.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.mlag.Shapes.Cube;
import org.mlag.Shapes.Square;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;

public class GameLoop {

    private long window = 0;
    private final Logger log = LogManager.getLogger(this.getClass());

    public GameLoop(long window) {
        this.window = window;
        if (window == 0) {
            log.info("Window is not init");
            throw new RuntimeException("Window is not init");
        }
    }

    public void init() {
        GL.createCapabilities();

    }





    public void loop() {
        FloatBuffer fb = MemoryUtil.memAllocFloat(16);
        Camera camera = new Camera(60f,800f/600f,0.1f,100f,2,2,2);
        Shader cubeShader = new Shader("resources/shaders/CubeVertex.vert", "resources/shaders/CubeFrag.frag");
        Cube cube = new Cube(cubeShader);
        glEnable(GL_DEPTH_TEST);
   //     glActiveTexture(GL_TEXTURE0);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //   glColor3f(0.5f,0.0f,0.0f);
            // Отрисовка квадрата

            cube.render(camera.getViewMatrix(),camera.getProjectionMatrix(),fb);

            // texture2D.bind();


            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        MemoryUtil.memFree(fb);

    }

}
