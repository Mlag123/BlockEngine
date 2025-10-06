package org.mlag.Core;

import com.google.errorprone.annotations.DoNotCall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;
import org.mlag.Logic.Disposable;
import org.mlag.Logic.ResourceManager;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class Shader implements Disposable {
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
        ResourceManager.register(this);

    }

    public Shader(String vertex, String fragment, boolean a) {
        String vertexCode = vertex;
        String fragmentCode = fragment;


        vertexShaderID = compileShader(vertexCode, GL_VERTEX_SHADER);
        fragmentShaderID = compileShader(fragmentCode, GL_FRAGMENT_SHADER);

        programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        glLinkProgram(programID);
        checkCompileErrors(programID, "PROGRAM");
        ResourceManager.register(this);

    }


    private String loadShaderFile(String path) {
        try {
            File file = new File(path);
            if (!file.isFile()) {
                log.info("\"Shader file not found: " + file.getAbsolutePath());
                throw new RuntimeException("Shader file not found: " + file.getAbsolutePath());
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
        checkCompileErrors(shaderID, shaderType == GL_VERTEX_SHADER ? "VERTEX" : "FRAGMENT", shaderCode);
        return shaderID;
    }


    private void checkCompileErrors(int shaderID, String type, String shaderCode) {
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

                String[] lines = shaderCode.split("\n");
                StringBuilder preview = new StringBuilder();
                for (int i = 0; i < Math.min(10, lines.length); i++) {
                    preview.append(i + 1).append(": ").append(lines[i]).append("\n");
                }

                log.error("Shader compilation error (" + type + "):\n" + infoLog + "\nShader preview:\n" + preview);
                throw new RuntimeException("Shader compilation failed");
            }
        }
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

    public void setUniform1i(String name, int value) {
        int location = glGetUniformLocation(programID, name);
        if (location == -1) {
            log.warn("Uniform not found {}", name);
        } else {

            glUniform1i(location, value);
        }
    }

    public void setUniform3f(String name, float x, float y, float z) {
        int location = glGetUniformLocation(programID, name);
        if (location == -1) {
            log.warn("Uniform not found {}", name);

        }else {

            glUniform3f(location, x, y, z);
        }
    }

    public void setUniform3f(String name, Vector3f vector3f){
        int location =  glGetUniformLocation(programID,name);
        if(location ==-1){
            log.warn("Uniform not found {}", name);

        }else {
            glUniform3f(location,vector3f.x,vector3f.y,vector3f.y);
        }
    }
    public void setUniformMat4f(String name, FloatBuffer matrix) {
        int location = glGetUniformLocation(programID, name);
        if(location != -1)
            glUniformMatrix4fv(location, false, matrix);
    }

    public void setUniformVec4(String name, float x, float y, float z, float w) {
        int location = glGetUniformLocation(programID, name);
        if(location != -1)
            glUniform4f(location, x, y, z, w);
    }


    public void cleanUP() {
        if(programID != 0) {
            glDetachShader(programID, vertexShaderID);
            glDetachShader(programID, fragmentShaderID);
            glDeleteShader(vertexShaderID);
            glDeleteShader(fragmentShaderID);
            glDeleteProgram(programID);
            programID = vertexShaderID = fragmentShaderID = 0;
        }
    }
}


