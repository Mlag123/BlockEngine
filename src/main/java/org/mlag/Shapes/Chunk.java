package org.mlag.Shapes;

import org.joml.Vector3f;
import org.mlag.Core.GameLoop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chunk {


    private static int w = 3, h = 16,z = 3;
    Cube chunk[][][] = new Cube[w][h][z];
    private List<Cube> blockArray = new ArrayList<>();
    public Cube[][][] generateChunk() {
        for (int i = 0; i < w; i++) {
            for (int j = 0;j<h;j++){
               for(int w=  0;w<z;w++){
                   chunk[i][j][w] = new Cube(GameLoop.shpereShader);

                   chunk[i][j][w].setPosition(new Vector3f(0+i,0+j,0+w));
               }
            }
        }
        return chunk;
    }
}
