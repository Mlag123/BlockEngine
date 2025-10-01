#version 330 core

in vec3 vertexPos;
out vec4 FragColor;

uniform float time;

void main() {
    float t = vertexPos.y + 0.5; // [0..1] по высоте

    // Радуга с анимацией
    float r = 0.5 + 0.5 * sin(6.2831 * (t + time/2));
    float g = 0.5 + 0.5 * sin(6.2831 * (t + time/2 + 0.33));
    float b = 0.5 + 0.5 * sin(6.2831 * (t + time/2 + 0.66));


    FragColor = vec4(r, g, b, 1.0);
}
