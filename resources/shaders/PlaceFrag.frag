#version 330 core

in vec3 vertexPos;
out vec4 FragColor;

uniform float time;

void main() {
    float t = vertexPos.y + 0.5; // [0..1] по высоте

    // Радуга с анимацией
    float r = 0.1 + 0.5 * sin(6.2831 * (t + time));
    float g = 0.5+ 0.5 * sin(6.2831 * (t + time));
    float b = 0.7+ 0.5 * sin(6.2831 * (t + time));

    FragColor = vec4(r, g, b, 1.0);
}
