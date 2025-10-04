package org.mlag.Maths.Collaiders;

import org.joml.Vector3f;

public class AABBCollaider {

    public Vector3f min,max;

    public AABBCollaider(Vector3f min,Vector3f max){
        this.max = max;
        this.min = min;
    }
    public void update(Vector3f position,Vector3f halfSize){
        this.min.set(position.x-halfSize.x,position.y-halfSize.y,position.z-halfSize.z);
        this.max.set(position.x+halfSize.x,position.y+halfSize.y,position.z+halfSize.z);
    }
    public boolean intersects(AABBCollaider other) {
        return (this.max.x >= other.min.x && this.min.x <= other.max.x) &&
                (this.max.y >= other.min.y && this.min.y <= other.max.y) &&
                (this.max.z >= other.min.z && this.min.z <= other.max.z);
    }
    public boolean contains(Vector3f point) {
        return (point.x >= min.x && point.x <= max.x) &&
                (point.y >= min.y && point.y <= max.y) &&
                (point.z >= min.z && point.z <= max.z);
    }
    public boolean rayIntersects(Vector3f origin, Vector3f dir) {
        float tMin = (min.x - origin.x) / dir.x;
        float tMax = (max.x - origin.x) / dir.x;

        if (tMin > tMax) { float tmp = tMin; tMin = tMax; tMax = tmp; }

        float tyMin = (min.y - origin.y) / dir.y;
        float tyMax = (max.y - origin.y) / dir.y;

        if (tyMin > tyMax) { float tmp = tyMin; tyMin = tyMax; tyMax = tmp; }

        if ((tMin > tyMax) || (tyMin > tMax))
            return false;

        if (tyMin > tMin) tMin = tyMin;
        if (tyMax < tMax) tMax = tyMax;

        float tzMin = (min.z - origin.z) / dir.z;
        float tzMax = (max.z - origin.z) / dir.z;

        if (tzMin > tzMax) { float tmp = tzMin; tzMin = tzMax; tzMax = tmp; }

        return !((tMin > tzMax) || (tzMin > tMax));
    }


}
