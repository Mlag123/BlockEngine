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
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v": // вершина
                    mesh.vertices.add(new float[]{
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    });
                    break;

                case "vt": // UV
                    mesh.texCoords.add(new float[]{
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2])
                    });
                    break;

                case "vn": // нормаль
                    mesh.normals.add(new float[]{
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    });
                    break;

                case "f": // грань (треугольник или квадрат)
                    int[] face = new int[tokens.length - 1];

                    for (int i = 1; i < tokens.length; i++) {
                        String[] parts = tokens[i].split("/");
                        int vertexIndex = Integer.parseInt(parts[0]) - 1; // индексы OBJ начинаются с 1

                        if (vertexIndex < 0 || vertexIndex >= mesh.vertices.size()) {
                            throw new RuntimeException("Некорректный индекс вершины: " + vertexIndex);
                        }
                        face[i - 1] = vertexIndex;
                    }

                    // если треугольник
                    if (face.length == 3) {
                        mesh.faces.add(face);
                    }
                    // если квадрат — разбиваем на два треугольника
                    else if (face.length == 4) {
                        mesh.faces.add(new int[]{face[0], face[1], face[2]});
                        mesh.faces.add(new int[]{face[0], face[2], face[3]});
                    } else {
                        throw new RuntimeException("Грань с более чем 4 вершинами не поддерживается: " + face.length);
                    }
                    break;
            }
        }

        br.close();
        return mesh;
    }




}
