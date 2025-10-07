package org.mlag.Maths.Engine;

import org.joml.Vector3f;
import org.mlag.Maths.Collaiders.AABBCollaider;
import org.mlag.Shapes.SceneObject;

import java.util.List;


public class Raycast {

    private final Vector3f origin;       // Точка, откуда летит луч
    private final Vector3f direction;    // Нормализованное направление луча

    public Raycast(Vector3f origin, Vector3f direction) {
        this.origin = new Vector3f(origin);
        this.direction = new Vector3f(direction).normalize();
    }

    /**
     * Проверяет пересечение луча с AABB коллайдером.
     * Возвращает расстояние до пересечения или -1, если пересечения нет.
     */
    public float intersectAABB(AABBCollaider aabb) {
        float tMin = (aabb.min.x - origin.x) / direction.x;
        float tMax = (aabb.max.x - origin.x) / direction.x;
        if (tMin > tMax) {
            float tmp = tMin;
            tMin = tMax;
            tMax = tmp;
        }

        float tyMin = (aabb.min.y - origin.y) / direction.y;
        float tyMax = (aabb.max.y - origin.y) / direction.y;
        if (tyMin > tyMax) {
            float tmp = tyMin;
            tyMin = tyMax;
            tyMax = tmp;
        }

        if ((tMin > tyMax) || (tyMin > tMax)) return -1;
        if (tyMin > tMin) tMin = tyMin;
        if (tyMax < tMax) tMax = tyMax;

        float tzMin = (aabb.min.z - origin.z) / direction.z;
        float tzMax = (aabb.max.z - origin.z) / direction.z;
        if (tzMin > tzMax) {
            float tmp = tzMin;
            tzMin = tzMax;
            tzMax = tmp;
        }

        if ((tMin > tzMax) || (tzMin > tMax)) return -1;
        if (tzMin > tMin) tMin = tzMin;
        if (tzMax < tMax) tMax = tzMax;

        // tMin < 0 означает, что луч стартует внутри бокса
        return tMin < 0 ? tMax : tMin;
    }

    /**
     * Проверяет пересечение луча со списком коллайдеров и возвращает ближайший.
     */
    public AABBCollaider raycastClosest(List<AABBCollaider> colliders) {
        AABBCollaider closest = null;
        float closestDist = Float.MAX_VALUE;

        for (AABBCollaider aabb : colliders) {
            float dist = intersectAABB(aabb);
            if (dist > 0 && dist < closestDist) {
                closestDist = dist;
                closest = aabb;
            }
        }

        return closest;
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public Vector3f getDirection() {
        return direction;
    }
}



