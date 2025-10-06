package org.mlag.Graphic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mlag.Core.Shader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderManager {

    private static final Logger log = LogManager.getLogger(ShaderManager.class);
    private static String shaderPath = "resources/shapes/shaders/";
    private List<File> fileList = new ArrayList<>();
    private Map<String, String> vertices_shaders = new HashMap<>();
    private Map<String, String> fragment_shaders = new HashMap<>();
    private List<String> nameShader = new ArrayList<>();
    private String vert_extens = ".vert";
    private String frag_extens = ".frag";

    public void readAllShaders() throws IOException {
        File _shadfile = new File(shaderPath);

        if (!_shadfile.isDirectory()) {
            log.error("Shaders is not founded!");
            throw new RuntimeException("Shaders is not founded!");
        }

        for (File _f : _shadfile.listFiles()) {
            fileList.add(_f);
        }

        for (File _f2 : fileList) {

            nameShader.add(_f2.getName());
            if (_f2.getName().toLowerCase().contains(vert_extens)) {
                vertices_shaders.put(_f2.getName(), readFileShader(_f2));
                log.info("File:= {}, extens:= {}", _f2.getName(), vert_extens);
            } else if (_f2.getName().toLowerCase().contains(frag_extens)) {
                fragment_shaders.put(_f2.getName(), readFileShader(_f2));
                log.info("File:= {}, extens:= {}", _f2.getName(), frag_extens);

            }


        }
        log.info("");
        log.info("Count vertical shaders:= {}", vertices_shaders.size());
        log.info("Count fragment shaders:= {}", fragment_shaders.size());
        log.info(" Count Vert+Frag shaders:= {}", vertices_shaders.size() + fragment_shaders.size());

    }

    public String getVerticesShader(String name) {
//log.info("Searching shader name: {}", name);

        for (String _nm : nameShader) {
            // Удаляем расширение .vert из имени файла для сравнения
            String baseName = _nm.replace(".vert", "").toLowerCase().trim();
            String searchName = name.toLowerCase().trim();

            //   log.info("Comparing: '{}' with '{}'", baseName, searchName);

            if (baseName.equals(searchName)) {
                return vertices_shaders.get(_nm);
            }
        }
        return null;
    }

    public String getFragmentShader(String name) {
        log.info("Searching shader name: {}", name);

        for (String _nm : nameShader) {
            // Удаляем расширение .vert из имени файла для сравнения
            String baseName = _nm.replace(".frag", "").toLowerCase().trim();
            String searchName = name.toLowerCase().trim();

            log.info("Comparing: '{}' with '{}'", baseName, searchName);

            if (baseName.equals(searchName)) {
                return fragment_shaders.get(_nm);
            }
        }
        return null;
    }

    public String readFileShader(File file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }


    public static void main(String[] args) throws IOException {
        new ShaderManager().readAllShaders();
    }


}
