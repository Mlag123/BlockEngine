package org.mlag.Input;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    private double lastX,lastY;
    private double deltaX,deltaY;
    private boolean firstMove = true;


    public void cursorPosCallback(long window, double xpos, double ypos) {
        if (firstMove) {
            lastX = xpos;
            lastY = ypos;
            firstMove = false;
        }
        deltaX = xpos - lastX;
        deltaY = lastY - ypos;
        lastX = xpos;
        lastY = ypos;
    }

    public float getDeltaX() { return (float)deltaX; }
    public float getDeltaY() { return (float)deltaY; }

    public void resetDeltas() {
        deltaX = 0;
        deltaY = 0;
    }

    public void attachToWindow(long window) {
        glfwSetCursorPosCallback(window, this::cursorPosCallback);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }
}
