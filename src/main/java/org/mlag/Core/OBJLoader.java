package org.mlag.Core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {



    ///test Code!
    public static class Mesh {
        public List<float[]> vertices = new ArrayList<>();
        public List<float[]> texCoords = new ArrayList<>();
        public List<float[]> normals = new ArrayList<>();
        public List<int[]> faces = new ArrayList<>();
    }

    public static Mesh loadOBJ(String path) throws IOException {
        Mesh mesh = new Mesh();
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split("\\s+");
            if (tokens.length == 0) continue;

            switch (tokens[0]) {
                case "v": // вершина
                    mesh.vertices.add(new float[] {
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    });
                    break;

                case "vt": // UV
                    mesh.texCoords.add(new float[] {
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2])
                    });
                    break;

                case "vn": // нормаль
                    mesh.normals.add(new float[] {
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    });
                    break;

                case "f": // face (треугольник или квад)
                    int[] face = new int[tokens.length - 1];
                    for (int i = 1; i < tokens.length; i++) {
                        String[] parts = tokens[i].split("/");
                        face[i-1] = Integer.parseInt(parts[0]) - 1; // индексы OBJ начинаются с 1
                    }
                    mesh.faces.add(face);
                    break;
            }
        }
        br.close();
        return mesh;
    }

    public static void main(String[] args) {
        Mesh m  = new Mesh();
        String path = "resources/obj/fonar.obj";
        try {
            m = OBJLoader.loadOBJ(path);

            for (float f[]:m.vertices){

                for (int i = 0;i<f.length;i++){
                    System.out.println(f[i]);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
