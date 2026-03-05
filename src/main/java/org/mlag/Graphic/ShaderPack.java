package org.mlag.Graphic;

import org.mlag.Core.Shader;
import org.mlag.Utils.Constants;

public class ShaderPack {
    public final Shader cubeRed;
    public final Shader cubeGreen;
    public final Shader skybox;
    public final Shader cross;
    public final Shader greenVec;
    public final Shader redVec;
    public final Shader blueVec;
    public final Shader sun;
    public final Shader place;

    public ShaderPack() {
        cubeRed = new Shader("resources/shapes/shaders/RedCubeVert.vert", "resources/shapes/shaders/RedCubeFrag.frag");
        cubeGreen = new Shader("resources/shapes/shaders/GreenCubeVert.vert", "resources/shapes/shaders/GreenCubeFrag.frag");
        skybox = new Shader("resources/shapes/shaders/SkyBoxVert.vert", "resources/shapes/shaders/SkyBoxFrag.frag");
        cross = new Shader("resources/shapes/shaders/CrossVert.vert", "resources/shapes/shaders/CrossFrag.frag");

        greenVec = new Shader("resources/shapes/shaders/GreenVec.vert", "resources/shapes/shaders/GreenVec.frag");
        redVec = new Shader("resources/shapes/shaders/RedVec.vert", "resources/shapes/shaders/RedVec.frag");
        blueVec = new Shader("resources/shapes/shaders/BlueVec.vert", "resources/shapes/shaders/BlueVec.frag");
        sun = new Shader(Constants.PATH_SHADERS + "/SunShader.vert", Constants.PATH_SHADERS + "/SunShader.frag");
        place = new Shader(Constants.PATH_SHADERS + "/PlaceShader.vert", Constants.PATH_SHADERS + "/PlaceShader.frag");
    }
}
