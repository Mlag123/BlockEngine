package org.mlag.Shapes;

import org.joml.Vector3f;
import org.mlag.Core.GameLoop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chunk {


    private static int w = 5, h = 16,z = 5;
    Cube chunk[][][] = new Cube[w][h][z];
    private List<Cube> blockArray = new ArrayList<>();
    public Cube[][][] generateChunk() {
        for (int i = 0; i < w; i++) {
            for (int j = 0;j<h;j++){
               for(int w=  0;w<z;w++){
                   if(j%2==0){
                       chunk[i][j][w] = new Cube(GameLoop.cubeGreen);
                   }else {
                       chunk[i][j][w] = new Cube(GameLoop.cubeRed);
                   }
                    Vector3f vec;
                   float rand =0;
                   float offset = 0;
                   rand = (float) Math.random()*j*2;

                   if(rand %2==0){
                       rand = rand *(-2);
                       offset = rand/3;
                   }



                   vec = new Vector3f((float)Math.random()*2+i,rand,(float)Math.random()*2+w);
                   chunk[i][j][w].setPosition(vec);
               }
            }
        }
        return chunk;
    }
}
