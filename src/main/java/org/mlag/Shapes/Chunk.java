package org.mlag.Shapes;

import org.joml.Vector3f;
import org.mlag.Core.GameLoop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chunk {


    private static int w = 3, h = 16;
    Cube chunk[][] = new Cube[w][h];
    private List<Cube> blockArray = new ArrayList<>();
    public Cube[][] generateChunk() {
        for (int i = 0; i < w; i++) {
            for (int j = 0;j<h;j++){
                chunk[i][j] = new Cube(GameLoop.shpereShader);

                chunk[i][j].setPosition(new Vector3f(0+i,0+j,0+i));
            }
        }
        return chunk;
    }
}
