package org.mlag.Objects;

public class ObjectJson {
    private String tag;
    private String type;
    private String shaderName;

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setType(String type) {
        this.type = type;
    }



    public void setShaderName(String shaderName) {
        this.shaderName = shaderName;
    }

    public String getTag() {
        return tag;
    }

    public String getType() {
        return type;
    }


    public String getShaderName() {
        return shaderName;
    }

    public ObjectJson(String tag, String type, String shaderName) {
        this.tag = tag;
        this.type = type;
        this.shaderName = shaderName;
    }
}
