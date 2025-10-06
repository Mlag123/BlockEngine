package org.mlag.Objects;

public class BlockJson {

    private String tag;
    private String scale;
    private String texture_name;
    private String shader_name;

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public void setTexture_name(String texture_name) {
        this.texture_name = texture_name;
    }

    public void setShader_name(String shader_name) {
        this.shader_name = shader_name;
    }

    public String getTag() {
        return tag;
    }

    public String getScale() {
        return scale;
    }

    public String getTexture_name() {
        return texture_name;
    }

    public String getShader_name() {
        return shader_name;
    }

    public BlockJson(String tag, String scale, String texture_name, String shader_name) {
        this.tag = tag;
        this.scale = scale;
        this.texture_name = texture_name;
        this.shader_name = shader_name;
    }
}
