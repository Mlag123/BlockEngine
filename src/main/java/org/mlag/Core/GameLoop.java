package org.mlag.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.mlag.Input.KeyboardManager;
import org.mlag.Input.MouseInput;
import org.mlag.Objects.Canvas.Text;
import org.mlag.Objects.GameObjects.Cross;
import org.mlag.Objects.GameObjects.SkyBox;
import org.mlag.Shapes.*;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;
import java.security.Key;
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

    private Texture2D boykiserSture;
    public static long window = 0;
    private final Logger log = LogManager.getLogger(this.getClass());
    private float TIME = 0;

    public static List<SceneObject> gameObjectArrays = new ArrayList<>();
    //    public static Shader shpereShader ;
    public static Shader cubeGreen;
    public static Shader cubeRed;
    public static Shader skyboxShader;
    public static Camera camera;

    Cube testCube;

    public static Shader crossShader;
    public static Cross cross;
    Text text = new Text();

    public GameLoop(long window) {
        this.window = window;
        if (window == 0) {
            log.info("Window is not init");
            throw new RuntimeException("Window is not init");
        }
    }

    public void init() {


        GL.createCapabilities();
        camera = new Camera(60f, 1280 / 720f, 0.1f, 100f, 2, 2, 2);
        Camera.CheckCameraExisting(this.camera);
        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
        System.out.println("Renderer: " + glGetString(GL_RENDERER));
        System.out.println("Vendor: " + glGetString(GL_VENDOR));
        text.init();
        cubeRed = new Shader("resources/shapes/shaders/RedCubeVert.vert", "resources/shapes/shaders/RedCubeFrag.frag");
        cubeGreen = new Shader("resources/shapes/shaders/GreenCubeVert.vert", "resources/shapes/shaders/GreenCubeFrag.frag");
        skyboxShader = new Shader("resources/shapes/shaders/SkyBoxVert.vert", "resources/shapes/shaders/SkyBoxFrag.frag");
        crossShader = new Shader("resources/shapes/shaders/CrossVert.vert", "resources/shapes/shaders/CrossFrag.frag");
        boykiserSture = new Texture2D("resources/textures/SkyBox.png");
        gameObjectInit();
    }

    public void gameObjectInit() {
        cross = new Cross(crossShader);
        testCube = new Cube(crossShader);
    }


    public void loop() {

        SkyBox skyBox = new SkyBox(skyboxShader);


        //   shpereShader = new Shader("resources/shapes/shaders/SphereVert.vert", "resources/shapes/shaders/SphereFrag.frag");
//
        //        test code
        Chunk chunk = new Chunk();
        Cube c[][][] = chunk.generateChunk();
        System.out.println();
        //
        MouseInput mouseInput = new MouseInput();
        mouseInput.attachToWindow(window);
        //  Shader staticShader = new Shader("resources/shaders/CubeVertexBlue.vert", "resources/shaders/CubeFragBlue.frag");
        glEnable(GL_DEPTH_TEST);
        camera.translate(1, 0, 0);

        text.print("PIDARASIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        //   Cube staticCube = new Cube(staticShader);
        //     glActiveTexture(GL_TEXTURE0);
/*
        Shader placeShader = new Shader("resources/shapes/shaders/PlaceVert.vert", "resources/shapes/shaders/PlaceFrag.frag");
        Place place = new Place(placeShader);
        Cube sphere = new Cube(shpereShader);

        place.setScale(20);
        place.setPosition(-1, -1, -1);*/
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            TIME = (float) glfwGetTime();
            //   glColor3f(0.5f,0.0f,0.0f);
            // Отрисовка квадрата
            KeyboardManager.cameraMove();

            mouseInput.resetDeltas();
            glfwPollEvents();


            //    cubeAIR.setUniform1f("time",TIME);
            camera.rotate(mouseInput.getDeltaX() * 0.1f, mouseInput.getDeltaY() * 0.1f);


            if (glfwGetKey(window, GLFW_KEY_F) == GLFW_PRESS) {
                camera.setPosition(new Vector3f(0, 20, 0));
            }


            if (glfwGetKey(window, GLFW_KEY_R) == GLFW_PRESS) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 16; j++) {
                        for (int z = 0; z < 5; z++) {
                            Vector3f pos;
                            float rand = (float) (Math.random() % 3 * 100) + j;
                            float offset_x, offset_z;
                            offset_x = (float) Math.random() * 4 + i;
                            offset_z = (float) Math.random() * 3 + z;
                            pos = new Vector3f(offset_x, rand, offset_z);


                            c[i][j][z].setPosition(pos);

                        }
                    }
                }
            }

            if (glfwGetKey(window, GLFW_KEY_C) == GLFW_PRESS) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 16; j++) {
                        for (int z = 0; z < 5; z++) {


                            c[i][j][z].setPosition(0 + i, 0 + j, 0 + z);

                        }
                    }
                }
            }


            skyBox.setPosition(camera.getPosition());

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 16; j++) {
                    for (int z = 0; z < 5; z++) {
                        c[i][j][z].useShader();
                        c[i][j][z].render();

                    }
                }
            }

            boykiserSture.bind();
            skyBox.useShader();
            skyBox.render();
            boykiserSture.unbind();


            //cross.useShader();
            //cross.setScale(0.1f);
            cross.rotateForCam(mouseInput.getDeltaX(),mouseInput.getDeltaY());
            cross.setPosition(camera.getPosition());
            cross.render2D();
            // texture2D.bind();



            if(KeyboardManager.getKeyPress(GLFW_KEY_X)){
                testCube.rotate(mouseInput.getDeltaX(),new Vector3f(0f,1,0));
                testCube.rotate(mouseInput.getDeltaY(),new Vector3f(0f,0,1));

            }
            if(KeyboardManager.getKeyPress(GLFW_KEY_Z)){
                testCube.setPosition(0,0,0);
            }


            //cross.rotateX(mouseInput.getDeltaX());
            //cross.rotateY(mouseInput.getDeltaY());

            testCube.render();

            glfwSwapBuffers(window);
        }
        Camera.memFree();
    }

}
