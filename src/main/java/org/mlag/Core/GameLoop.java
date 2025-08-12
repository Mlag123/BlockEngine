package org.mlag.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;

public class GameLoop {

    private long window = NULL;
    private final Logger log = LogManager.getLogger(this.getClass());

    public GameLoop(long window){
        this.window = window;
        if(window == NULL){
            log.info("Window is not init");
            throw  new RuntimeException("Window is not init");
        }
    }

    public void loop(){
        log.info("started gameloop");
        GL.createCapabilities();
        glClearColor(1.0f,0.0f,0.0f,0.0f);
        while(!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(window);
            glfwPollEvents();
        }

    }

}
