package org.mlag.Logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mlag.Core.Shader;
import org.mlag.Graphic.ShaderManager;
import org.mlag.Objects.BlockJson;
import org.mlag.Objects.GameObjects.Block;
import org.mlag.Objects.ObjectJson;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ObjectMangers {

    private ShaderManager shaderManager;
    private static final Logger log = LogManager.getLogger(ObjectMangers.class);
    private String shader_path = "resources/";
    private static Gson gsonReader = new GsonBuilder().setPrettyPrinting().create();
    private static String objects_folder = "resources/objects/";
    private static String block_folders = "resources/blocks/";
    private List<ObjectJson> objectJsonList = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private ArrayList<BlockJson> blockJsons = new ArrayList<>();


    public void init() {
        shaderManager = new ShaderManager();
        try {
            shaderManager.readAllShaders();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public ObjectJson readObjects() {
        File file = new File(objects_folder);

        if (!file.isDirectory()) {
            log.error("Folder is not founded!");
            try {
                throw new FileNotFoundException("Folder is not founded!");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        for (File _f : file.listFiles()) {
            fileList.add(_f);
        }
        if (fileList.isEmpty()) {
            throw new RuntimeException("Files not found");
        }


        for (File _f1 : fileList) {
            try {
                Reader read = new FileReader(_f1);
                return gsonReader.fromJson(read, ObjectJson.class);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }


        return null;
    }

    public void readBlocks() {
        File file = new File(block_folders);
        List<File> json_block_files = new ArrayList<>();
        for (File _f1 : file.listFiles()) {
            json_block_files.add(_f1);
        }
        if (json_block_files.isEmpty()) {
            log.warn("Blocks folder is empty!");
        }

        Gson gs  = new Gson();
        for (File _f2 : json_block_files) {
            try {
                Reader read = new FileReader(_f2);
                blockJsons.add(gsonReader.fromJson(read,  BlockJson.class));
                //blockJsons = gs.fromJson(read, BlockJson.class);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


    }


    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (BlockJson bl : blockJsons) {
            String tag = bl.getTag();
            String scale = bl.getScale();
            String shader_name = bl.getShader_name();
            String texture_name = bl.getTexture_name();

            Block block = new Block(new Shader(shaderManager.getVerticesShader(shader_name), shaderManager.getFragmentShader(shader_name), true));
            block.setTag_object(tag);
            blocks.add(block);

        }
        return blocks;
    }


    public void generateExampleFile() {
        String test_path = "resources/blocks/Example.json";
        BlockJson bl = new BlockJson("ExampleName", "1.0f", "exampleTexture", "exampleShader");
        String json = gsonReader.toJson(bl);
        System.out.println(json);
        try {
            Writer fl = new FileWriter(test_path);
            fl.write(json);
            fl.flush();
            fl.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void getAllBlock() {

    }

    public static void main(String[] args) {
        new ObjectMangers().generateExampleFile();
    }
}
