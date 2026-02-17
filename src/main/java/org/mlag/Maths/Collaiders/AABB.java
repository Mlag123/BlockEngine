package org.mlag.Maths.Collaiders;

import org.joml.Vector3f;

public class AABB {

    private Vector3f position;

    public float minX, minY, minZ;
    public float maxX, maxY, maxZ;

    public AABB() {
        this.minX = Float.POSITIVE_INFINITY;
        this.minY = Float.POSITIVE_INFINITY;
        this.minZ = Float.POSITIVE_INFINITY;

        this.maxX = Float.NEGATIVE_INFINITY;
        this.maxY = Float.NEGATIVE_INFINITY;
        this.maxZ = Float.NEGATIVE_INFINITY;
    }

    public AABB(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public static boolean intersects(AABB a, AABB b) {
        return (
                a.minX <= b.maxX && a.maxX >= b.minX && a.minY <= b.maxY && a.maxY >= b.minY && a.minZ <= b.maxZ && a.maxZ >= b.minZ
        );
    }

    public boolean intersects(AABB other) {
        return intersects(this, other);
    }
    public void setPosition(Vector3f vec, float sizeX, float sizeY, float sizeZ) {
        float halfX = sizeX / 2.0f;
        float halfY = sizeY / 2.0f;
        float halfZ = sizeZ / 2.0f;

        this.minX = vec.x - halfX;
        this.maxX = vec.x + halfX;
        this.minY = vec.y - halfY;
        this.maxY = vec.y + halfY;
        this.minZ = vec.z - halfZ;
        this.maxZ = vec.z + halfZ;

        this.position = new Vector3f(vec.x, vec.y, vec.z);

    }

    public void setPosition(float x, float y, float z, float sizeX, float sizeY, float sizeZ) {
        float halfX = sizeX / 2.0f;
        float halfY = sizeY / 2.0f;
        float halfZ = sizeZ / 2.0f;

        this.minX = x - halfX;
        this.maxX = x + halfX;
        this.minY = y - halfY;
        this.maxY = y + halfY;
        this.minZ = z - halfZ;
        this.maxZ = z + halfZ;

        this.position = new Vector3f(x, y, z);

    }


    public Vector3f getCenter() {
        return new Vector3f(
                maxX - minX / 2.0f,
                maxY - minY / 2.0f,
                maxZ - minZ / 2.0f
        );
    }

    public Vector3f getSize() {
        return new Vector3f(
                maxX - minX,
                maxY - minY,
                maxZ - minZ
        );
    }

    public AABB getAABB() {
        return this;
    }

    public static AABB initColliderFromMesh(float[] vertices) {
        AABB aabb = new AABB();
        for (int i = 0; i < vertices.length; i += 3) {

            if (i + 2 >= vertices.length) break;
            float x = vertices[i];
            float y = vertices[i + 1];
            float z = vertices[i + 2];

            if (x < aabb.minX) aabb.minX = x;
            if (y < aabb.minY) aabb.minY = y;
            if (z < aabb.minZ) aabb.minZ = z;

            if (x > aabb.maxX) aabb.maxX = x;
            if (y > aabb.maxY) aabb.maxY = y;
            if (z > aabb.maxZ) aabb.maxZ = z;


        }
        return aabb;
    }

    public Vector3f getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return String.format("AABB[min(%.2f,%.2f,%.2f), max(%.2f,%.2f,%.2f)]",
                minX, minY, minZ, maxX, maxY, maxZ);
    }
}
