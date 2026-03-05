package org.mlag.Physics;

import org.joml.Vector3f;

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
  

    public Vector3f getOrigin() {
        return origin;
    }

    public Vector3f getDirection() {
        return direction;
    }
}



