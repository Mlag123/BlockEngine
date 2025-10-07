package org.mlag.Maths.Engine;

import org.joml.Vector3f;
import org.mlag.Shapes.SceneObject;

public class Ray {

    private Vector3f direction;
    private Vector3f position;
    private SceneObject gameObject;


    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getPosition() {
        return position;
    }

    public SceneObject getGameObject() {
        return gameObject;
    }
}
