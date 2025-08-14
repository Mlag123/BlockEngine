package org.mlag.ljwgl;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;

public class VAO {

    private int vaoID =0;
    public VAO(){
        vaoID  = glGenVertexArrays();
        glBindVertexArray(vaoID);
    }
}
