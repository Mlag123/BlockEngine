package org.mlag.Shapes;

import org.joml.Vector3f;
import org.mlag.Core.GameLoop;
import org.mlag.Objects.GameObjects.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chunk {


    private static int w = 5, h = 16,z = 5;
    Block chunk[][][] = new Block[w][h][z];
    private List<Block> blockArray = new ArrayList<>();
    public Block[][][] generateChunk() {
        for (int i = 0; i < w; i++) {
            for (int j = 0;j<h;j++){
                for(int w=  0;w<z;w++){
                    if(j%2==0){
                        chunk[i][j][w] = new Block(GameLoop.cubeGreen);
                    }else {
                        chunk[i][j][w] = new Block(GameLoop.cubeRed);
                    }

                    chunk[i][j][w].setPosition(0+i,0+j,0+w);
                }
            }
        }
        return chunk;
    }
}
