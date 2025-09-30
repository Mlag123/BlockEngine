#version 330 core

in vec3 vertexPos;
out vec4 FragColor;


void main() {
    float t = vertexPos.y + 0.5; // [0..1] по высоте

    // Радуга с анимацией
    float r = 0;
    float g = 0.2;
    float b = 0.2;

    FragColor = vec4(r, g, b, 1.0);
}
