package org.mlag.ljwgl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mlag.Logic.Disposable;
import org.mlag.Logic.ResourceManager;

import static org.lwjgl.opengl.ARBVertexArrayObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class VAO  implements Disposable {


    private int vaoID = 0;
    private boolean disposed = false;
    private int vertexCount;
    private final Logger log = LogManager.getLogger(this.getClass());

    public VAO() {
        vaoID = glGenVertexArrays();
        ResourceManager.register(this);
        //   glBindVertexArray(vaoID);
    }

    public void bind() {

        glBindVertexArray(vaoID);
    }

    public void unBind() {
        glBindVertexArray(0);
    }

    public void linkVBO(VBO vbo, int layout, int size, int stride, long offset) {
        bind();
        vbo.bind();
        glVertexAttribPointer(layout, size, GL_FLOAT, false, stride, offset);
        glEnableVertexAttribArray(layout);
        vbo.unbind();
        unBind();

    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;

    }

    public void draw() {
        bind();
        glDrawArrays(GL_TRIANGLES,0,vertexCount);
        unBind();
    }
/*    public void cleanUP(){
        if(!disposed){
            glDeleteVertexArrays(vaoID);
            disposed = true;
        }
    }*/

    public void cleanUP() {
        if (!disposed) {
            glDeleteVertexArrays(vaoID);
            disposed = true;
           // log.info("VAO " + vaoID + " cleaned up");
        }
    }

   /* @Override
    public void finalize() throws Throwable{
        try {
            if(!disposed){
                log.error("VAO "+vaoID+" is not removed");
                cleanUP();
            }
        }finally {
            super.finalize();
        }
    }*/

}
