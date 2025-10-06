package org.mlag.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.mlag.Graphic.Texture2D;
import org.mlag.Input.KeyboardManager;
import org.mlag.Input.MouseInput;
import org.mlag.Logic.ObjectMangers;
import org.mlag.Objects.Canvas.Text;
import org.mlag.Objects.GameObjects.Block;
import org.mlag.Objects.GameObjects.Cross;
import org.mlag.Objects.GameObjects.SkyBox;
import org.mlag.Objects.GameObjects.Test;
import org.mlag.Shapes.*;
import org.mlag.Utils.CpuMonitor;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;


public class GameLoop {

    private Texture2D boykiserSture;
    public static long window = 0;
    private final Logger log = LogManager.getLogger(this.getClass());
    private float TIME = 0;
    private long lastTime = System.nanoTime();
    private int frames = 0;
    private int fps = 0;


    ObjectMangers objectMangers;

    public static List<SceneObject> gameObjectArrays = new ArrayList<>();
    public static Shader cubeGreen;
    public static Shader cubeRed;
    public static Shader skyboxShader;
    public static Camera camera;
    List<Block> blocks;
    Cube testCube;

    public static Shader crossShader;
    public static Cross cross;
    Text text = new Text();
    Test testMesh;

    Shader shadGreenVec, shadBlueVec, shadRedVec;
    Cube RedVecCube, BlueVecCube, GreenVecCube;
    Sphere sphere;

    public GameLoop(long window) {
        this.window = window;
        if (window == 0) {
            log.info("Window is not init");
            throw new RuntimeException("Window is not init");
        }
    }

    public void init() {


        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        objectMangers = new ObjectMangers();
        objectMangers.init();
        objectMangers.readBlocks();

        blocks = objectMangers.getBlocks();
        initShader();
        camera = new Camera(60f, 1280 / 720f, 0.1f, 100f, 2, 2, 2);
        Camera.CheckCameraExisting(this.camera);
        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
        System.out.println("Renderer: " + glGetString(GL_RENDERER));
        System.out.println("Vendor: " + glGetString(GL_VENDOR));

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        text.init();
        System.out.println("NanoVG ID = " + text.id);




        gameObjectInit();
    }


    public void initShader(){
        cubeRed = new Shader("resources/shapes/shaders/RedCubeVert.vert", "resources/shapes/shaders/RedCubeFrag.frag");
        cubeGreen = new Shader("resources/shapes/shaders/GreenCubeVert.vert", "resources/shapes/shaders/GreenCubeFrag.frag");
        skyboxShader = new Shader("resources/shapes/shaders/SkyBoxVert.vert", "resources/shapes/shaders/SkyBoxFrag.frag");
        crossShader = new Shader("resources/shapes/shaders/CrossVert.vert", "resources/shapes/shaders/CrossFrag.frag");
        boykiserSture = new Texture2D("resources/textures/SkyBox.png");

        shadGreenVec = new Shader("resources/shapes/shaders/GreenVec.vert", "resources/shapes/shaders/GreenVec.frag");
        shadRedVec = new Shader("resources/shapes/shaders/RedVec.vert", "resources/shapes/shaders/RedVec.frag");
        shadBlueVec = new Shader("resources/shapes/shaders/BlueVec.vert", "resources/shapes/shaders/BlueVec.frag");
    }


    public void fpsUpdate() {
        long now = System.nanoTime();
        frames++;

        if (now - lastTime >= 1_000_000_000) { // прошло 1 секунда
            fps = frames;
            frames = 0;
            lastTime = now;
        }
    }

    public void gameObjectInit() {
        cross = new Cross(crossShader);
        testCube = new Cube(crossShader);
        RedVecCube = new Cube(shadRedVec);
        GreenVecCube = new Cube(shadGreenVec);
        BlueVecCube = new Cube(shadBlueVec);
        testMesh = new Test(crossShader);
        sphere = new Sphere(shadBlueVec);

    }


    public void draw(){
        sphere.render();
    }

    public void loop() {
        SkyBox skyBox = new SkyBox(skyboxShader);
        Chunk chunk = new Chunk();
        Block c[][][] = chunk.generateChunk();
        System.out.println();

        MouseInput mouseInput = new MouseInput();
        mouseInput.attachToWindow(window);
        glEnable(GL_DEPTH_TEST);
        camera.translate(1, 0, 0);

        log.info("Game object count:= {}", GameLoop.gameObjectArrays.size());


        while (!glfwWindowShouldClose(window)) {
            //     glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
            fpsUpdate();
            TIME = (float) glfwGetTime();

            KeyboardManager.cameraMove();


            mouseInput.resetDeltas();
            glfwPollEvents();


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


                            c[i][j][z].setPosition(0 + i * 2 + 3, 0 + j * 2, 0 + z * 2 + 3 * (-1));

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


            cross.rotateForCam(mouseInput.getDeltaX(), mouseInput.getDeltaY());
            cross.setPosition(camera.getPosition());


            if (KeyboardManager.getKeyPress(GLFW_KEY_X)) {
                testCube.rotate(mouseInput.getDeltaX(), new Vector3f(0f, 1, 0));
                testCube.rotate(mouseInput.getDeltaY(), new Vector3f(0f, 0, 1));

            }
            if (KeyboardManager.getKeyPress(GLFW_KEY_Z)) {
                testCube.setPosition(0, 0, 0);
            }


            for (Block bl : blocks) {
                if (bl.getTag_object().equalsIgnoreCase("eblan_tag")) {
                    bl.setPosition(0, -6, 0);
                    bl.render();
                } else {
                    bl.setPosition(0, -3, 0);
                    bl.render();

                }
            }


            BlueVecCube.setPosition(0, 1, 0);
            RedVecCube.setPosition(1, 0, 0);
            GreenVecCube.setPosition(0, 0, 1);

            BlueVecCube.render();
            RedVecCube.render();
            GreenVecCube.render();

            testCube.render();
            testMesh.render();

            sphere.setPosition(-2,0,0);
            draw();

            text.print("CPU Load: " + CpuMonitor.getCpuLoad());
            text.setPosition(20, 60);
            text.print("FPS: " + fps);
            text.setPosition(20, 80);
            text.print("BlockEngine");
            text.setPosition(20, 100);
            text.print("Cam.x = "+camera.getPosition().x+"| Cam.y = "+camera.getPosition().y+"| Cam.z = "+camera.getPosition().z);
            text.setPosition(20, 120);

            glfwSwapBuffers(window);
        }
        Camera.memFree();
    }

}
