package org.mlag.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final Logger log = LogManager.getLogger(this.getClass());

    private int programID, vertexShaderID, fragmentShaderID;


    public Shader(String vertexPath, String fragmentPath) {
        String vertexCode = loadShaderFile(vertexPath);
        String fragmentCode = loadShaderFile(fragmentPath);

        vertexShaderID = compileShader(vertexCode, GL_VERTEX_SHADER);
        fragmentShaderID = compileShader(fragmentCode, GL_FRAGMENT_SHADER);

        programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        glLinkProgram(programID);
        checkCompileErrors(programID, "PROGRAM");
    }


    private String loadShaderFile(String path) {
        try {
            File file = new File(path);
            if  (!file.isFile()){
                log.info("its false, path : "+file.getAbsolutePath());
                //testcode
            }
            return new String(Files.readAllBytes(Paths.get(path)));

        } catch (IOException e) {
            log.error("Failed to load shader, to = " + path);
            throw new RuntimeException("Failed to load shader");
        }
    }

    private int compileShader(String shaderCode, int shaderType) {
        int shaderID = glCreateShader(shaderType);
        glShaderSource(shaderID, shaderCode);
        glCompileShader(shaderID);
        checkCompileErrors(shaderID, shaderType == GL_VERTEX_SHADER ? "VERTEX" : "FRAGMENT");
        return shaderID;
    }


    private void checkCompileErrors(int shaderID, String type) {
        if (type.equals("PROGRAM")) {
            int success = glGetProgrami(shaderID, GL_LINK_STATUS);
            if (success == GL_FALSE) {
                String infoLog = glGetProgramInfoLog(shaderID);
                log.error("Program linking error:\n" + infoLog);
                throw new RuntimeException("Shader program linking failed");
            }
        } else {
            int success = glGetShaderi(shaderID, GL_COMPILE_STATUS);
            if (success == GL_FALSE) {
                String infoLog = glGetShaderInfoLog(shaderID);
                log.error("Shader compilation error (" + type + "):\n" + infoLog);
                throw new RuntimeException("Shader compilation failed");
            }
        }
    }

    public void use() {
        glUseProgram(programID);
    }

    public void setUniform1f(String name, float value) {
        int location = glGetUniformLocation(programID, name);
        glUniform1f(location, value);
    }
    public void setUniform1i(String name,int value){
        int location = glGetUniformLocation(programID, name);
        glUniform1i(location, value);
    }

    public void setUniform3f(String name, float x, float y, float z) {
        int location = glGetUniformLocation(programID, name);
        glUniform3f(location, x, y, z);
    }

    public void cleanUP() {
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteProgram(programID);
    }
}


