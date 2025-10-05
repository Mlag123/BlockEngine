package org.mlag.Input;

import org.lwjgl.glfw.GLFW;
import org.mlag.Core.GameLoop;
import org.mlag.Core.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.mlag.Core.GameLoop.camera;

public class KeyboardManager {

    public static float speed = 0.15f;



    public static boolean getKeyPress(int key){
        if(glfwGetKey(GameLoop.window,key)==GLFW_PRESS) return true;
        return false;
    }


    public static void cameraMove(){

        if (getKeyPress(GLFW_KEY_W)) camera.move(0, 0, speed);
        if(getKeyPress(GLFW_KEY_S)) camera.move(0,0,-speed);
        if (getKeyPress(GLFW_KEY_A)) camera.move(-speed, 0, 0);
        if(getKeyPress(GLFW_KEY_D)) camera.move(speed,0,0);
        if (getKeyPress(GLFW_KEY_SPACE)) camera.move(0, speed,0);
        if(getKeyPress(GLFW_KEY_LEFT_SHIFT)) camera.move(0,-speed,0);



    }
}
