package org.mlag.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.mlag.Input.MouseInput;
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
    private float TIME = 0;

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
        MouseInput mouseInput = new MouseInput();
        mouseInput.attachToWindow(window);
        Shader cubeShaderRainbow = new Shader("resources/shaders/CubeVertex.vert", "resources/shaders/CubeFrag.frag");
        Shader staticShader = new Shader("resources/shaders/CubeVertexBlue.vert", "resources/shaders/CubeFragBlue.frag");
        Cube RainbowCube = new Cube(cubeShaderRainbow);
        glEnable(GL_DEPTH_TEST);
        camera.translate(1,0,0);

        Cube staticCube = new Cube(staticShader);
   //     glActiveTexture(GL_TEXTURE0);
        staticCube.setPosition(2,2,2);
        staticCube.setScale(100);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            TIME = (float)glfwGetTime();
            log.info(TIME);
            //   glColor3f(0.5f,0.0f,0.0f);
            // Отрисовка квадрата
            mouseInput.resetDeltas();
            glfwPollEvents();

        //    cubeAIR.setUniform1f("time",TIME);
            camera.rotate(mouseInput.getDeltaX()*0.1f,mouseInput.getDeltaY()*0.1f);


            float speed = 0.05f;


            //код выносим за gameLoop!!
            if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) camera.move(0,0,speed);
            if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) camera.move(0,0,-speed);
            if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) camera.move(-speed,0,0);
            if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) camera.move(speed,0,0);
            if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) camera.move(0,speed,0);
            if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) camera.move(0,-speed,0);
            //код выносим за gameLoop!!
            cubeShaderRainbow.use();
            cubeShaderRainbow.setUniform1f("time",TIME);
            RainbowCube.render(camera.getViewMatrix(),camera.getProjectionMatrix(),fb);


            staticShader.use();
            staticCube.render(camera.getViewMatrix(),camera.getProjectionMatrix(),fb);

            // texture2D.bind();


            glfwSwapBuffers(window);
        }
        MemoryUtil.memFree(fb);

    }

}
