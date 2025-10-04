package org.mlag.Maths;

public abstract class Mesh {

    private float[] mesh;

    public void setMesh(float[] mesh) {
        this.mesh = mesh;
    }

    public float[] getMesh() {
        return mesh;
    }

    public Mesh(float[] mesh) {
        this.mesh = mesh;
    }

    public Mesh() {

    }

}
