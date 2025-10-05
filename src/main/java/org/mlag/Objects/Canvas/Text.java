package org.mlag.Objects.Canvas;

import org.lwjgl.nanovg.NVGColor;
import org.mlag.Core.Window;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

public class Text {

    public  static long id;
    private String text = "";
    public void  init(){
     //   System.load("C:\\Users\\disw\\IdeaProjects\\BlockEngine\\lib\\lwjgl_nanovg.dll");
           id = nvgCreate(NVG_ANTIALIAS|NVG_STENCIL_STROKES);
    }


    public  void print(String text){
        nvgBeginFrame(id, Window.WIDTH,Window.HEIGHT,1);
        nvgFontSize(id,32f);
        nvgFontFace(id,"default");

        NVGColor color = NVGColor.create(); // или new NVGColor();
        nvgRGBA((byte)255,(byte)192,(byte)0,(byte)255, color);
        nvgFillColor(id, color);
        nvgText(id, 20, 40, text);

        nvgEndFrame(id);
    }




}
