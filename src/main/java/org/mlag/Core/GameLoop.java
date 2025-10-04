package org.mlag.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.mlag.Input.MouseInput;
import org.mlag.Shapes.*;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

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

    public static List<SceneObject> gameObjectArrays = new ArrayList<>();
    public static Shader shpereShader ;


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
        shpereShader = new Shader("resources/shapes/shaders/SphereVert.vert", "resources/shapes/shaders/SphereFrag.frag");
//
   //        test code
            Chunk chunk = new Chunk();
            Cube c[][] = chunk.generateChunk();
        System.out.println();
        //
        Camera camera = new Camera(60f, 800f / 600f, 0.1f, 100f, 2, 2, 2);
        MouseInput mouseInput = new MouseInput();
        mouseInput.attachToWindow(window);
        //  Shader staticShader = new Shader("resources/shaders/CubeVertexBlue.vert", "resources/shaders/CubeFragBlue.frag");
        glEnable(GL_DEPTH_TEST);
        camera.translate(1, 0, 0);

        //   Cube staticCube = new Cube(staticShader);
        //     glActiveTexture(GL_TEXTURE0);

        Shader placeShader = new Shader("resources/shapes/shaders/PlaceVert.vert", "resources/shapes/shaders/PlaceFrag.frag");
        Place place = new Place(placeShader);
        Cube sphere = new Cube(shpereShader);

        place.setScale(20);
        place.setPosition(-1, -1, -1);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            TIME = (float) glfwGetTime();
            //   glColor3f(0.5f,0.0f,0.0f);
            // Отрисовка квадрата
            mouseInput.resetDeltas();
            glfwPollEvents();


            //    cubeAIR.setUniform1f("time",TIME);
            camera.rotate(mouseInput.getDeltaX() * 0.1f, mouseInput.getDeltaY() * 0.1f);


            float speed = 0.15f;


            //код выносим за gameLoop!!
            if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) camera.move(0, 0, speed);
            if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) camera.move(0, 0, -speed);
            if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) camera.move(-speed, 0, 0);
            if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) camera.move(speed, 0, 0);
            if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) camera.move(0, speed, 0);
            if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) camera.move(0, -speed, 0);

            //cube moving
            if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) sphere.translate(-0.1f, 0, 0);
            if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) sphere.translate(0.1f, 0, 0);
            if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) sphere.translate(0, 0.1f, 0);
            if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) sphere.translate(0, -0.1f, 0);
            if (glfwGetKey(window,GLFW_KEY_R)==GLFW_PRESS){
                sphere.setZeroCoordinate(true);
            }else {
                sphere.setZeroCoordinate(false);
            }
            placeShader.use();
            placeShader.setUniform1f("time", TIME);
            place.render();

            shpereShader.use();
            sphere.render();
            sphere.updateBody(TIME);

            for (int i =0;i<3;i++){
                for(int  j = 00;j<15;j++){
                    shpereShader.use();
                    c[i][j].render();
                }
            }

            // texture2D.bind();


            glfwSwapBuffers(window);
        }
        Camera.memFree();
    }

}
