package org.mlag.Maths;

import org.joml.Vector3f;

public class Collider {

    private Vector3f position;   // центр
    private Vector3f halfSize;   // половина размеров по осям

    public Collider(Vector3f position) {
        this.position = position;
        this.halfSize = new Vector3f(0.5f, 0.5f, 0.5f); // дефолт куб 1x1x1
    }

    public void setHalfSize(Vector3f halfSize) {
        this.halfSize = halfSize;
    }

    public Vector3f getHalfSize() {
        return halfSize;
    }

    public void setPosition(Vector3f pos) {
        this.position.set(pos);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getMinX() { return position.x - halfSize.x; }
    public float getMaxX() { return position.x + halfSize.x; }
    public float getMinY() { return position.y - halfSize.y; }
    public float getMaxY() { return position.y + halfSize.y; }
    public float getMinZ() { return position.z - halfSize.z; }
    public float getMaxZ() { return position.z + halfSize.z; }

    // простой метод столкновения с другим AABB
    public boolean intersects(Collider other) {
        return (getMaxX() > other.getMinX() && getMinX() < other.getMaxX()) &&
                (getMaxY() > other.getMinY() && getMinY() < other.getMaxY()) &&
                (getMaxZ() > other.getMinZ() && getMinZ() < other.getMaxZ());
    }

    public static boolean AABBIntersect(Vector3f posA, Vector3f halfA, Vector3f posB, Vector3f halfB) {
        return (Math.abs(posA.x - posB.x) <= (halfA.x + halfB.x)) &&
                (Math.abs(posA.y - posB.y) <= (halfA.y + halfB.y)) &&
                (Math.abs(posA.z - posB.z) <= (halfA.z + halfB.z));
    }
}
