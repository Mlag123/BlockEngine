package org.mlag.Maths;

import org.joml.Vector3f;

public class Body {
    public Vector3f position = new Vector3f();
    public Vector3f velocity = new Vector3f();
    public Vector3f force = new Vector3f();
    public float mass = 1.0f;
    public float invmass = 1.0f;
    public boolean immovable = false;

    public void applyForce(Vector3f f){
        force.add(f);
    }

    public void forceZero(){
        force.zero();
    }

    public Vector3f intergrade(float dt){
 /*       if(immovable){
            force.zero();
            return;
        }*/
        Vector3f accel = new Vector3f(force).mul(invmass);
        velocity.fma(dt,accel);
        position.fma(dt,velocity);

        float damping = 0.99f;
        velocity.mul(damping);
        force.zero();
        return position;
    }

}
