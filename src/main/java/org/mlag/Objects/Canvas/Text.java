package org.mlag.Objects.Canvas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.nanovg.NVGColor;
import org.mlag.Core.GameLoop;
import org.mlag.Core.Window;

import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11C.glEnable;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Text {

    private static final Logger log = LogManager.getLogger(Text.class);
    public   long id;
    private String text = "";
    private float x,y;
    public void  init(){
     //   System.load("C:\\Users\\disw\\IdeaProjects\\BlockEngine\\lib\\lwjgl_nanovg.dll");
           id = nvgCreate(NVG_ANTIALIAS|NVG_STENCIL_STROKES);


       int font = nvgCreateFont(id, "default", "resources/fonts/Pixel-UniCode.ttf");
        if (font == -1) {
            log.error("Не удалось загрузить шрифт!");
        }

    }

    public void setPosition(float x,float y){
        this.x = x;
        this.y = y;
    }

    public void print(String text) {
        glUseProgram(0);

        int[] fbWidth = new int[1];
        int[] fbHeight = new int[1];
        glfwGetFramebufferSize(GameLoop.window, fbWidth, fbHeight);

        nvgBeginFrame(id, fbWidth[0], fbHeight[0], 1);

        nvgFontSize(id, 32f);
        nvgFontFace(id,"default");

        NVGColor color = NVGColor.create();
        nvgRGBA((byte)255,(byte)255,(byte)0,(byte)255, color);
        nvgFillColor(id, color);
        nvgText(id, x, y, text);


        nvgEndFrame(id);
        glEnable(GL_DEPTH_TEST);
    }


/*    public  void print(String text){
        nvgBeginFrame(id, Window.WIDTH,Window.HEIGHT,1);
        nvgFontSize(id,32f);
        nvgFontFace(id,"default");

        NVGColor color = NVGColor.create(); // или new NVGColor();
        nvgRGBA((byte)255,(byte)192,(byte)0,(byte)255, color);
        nvgFillColor(id, color);
        nvgText(id, 20, 40, text);

        nvgEndFrame(id);
        glEnable(GL_DEPTH_TEST);
    }*/




}
