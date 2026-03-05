package org.mlag.Render;

import org.joml.Vector3f;
import org.mlag.Core.Shader;

public class LightManager {
    private Vector3f direction;
    private Vector3f color;

    public void apply(Shader shader){
        shader.setUniform3f("light.direction",direction);
        shader.setUniform3f("light.color",color);
    }

}
