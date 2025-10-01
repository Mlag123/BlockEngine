package org.mlag.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;

public class Window {
    private final Logger log = LogManager.getLogger(this.getClass());

    private long window;

    public static int WIDTH = 1280,HEIGHT = 720;
    private String title = "BlockEngine";
    private GameLoop gameLoop;

    public Window(){
        log.info("Helo LJWGL "+ Version.getVersion()+"!");


    }

    public void run(){
        init();
        gameLoop = new GameLoop(window);
        gameLoop.init();
        gameLoop.loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit())
            throw new IllegalStateException("Unable init glwf");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);

        window = glfwCreateWindow(WIDTH,HEIGHT,title,NULL,NULL);
        if(window == NULL){
            throw  new RuntimeException("Failed create window");
        }


        //nnn
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        glfwSetFramebufferSizeCallback(window, (win, width, height) -> {
            glViewport(0, 0, width, height);
            if(gameLoop != null && gameLoop.getCamera() != null){
                gameLoop.getCamera().setAspectRatio((float)width / height);
            }
        });

        try(MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window,pWidth,pHeight);
            GLFWVidMode vidMode  = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2
            );
            glfwMakeContextCurrent(window);
            glfwSwapInterval(1);
            log.info("windows id = "+window);
            glfwShowWindow(window);


        }
    }


}
