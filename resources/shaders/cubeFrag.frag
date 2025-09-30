#version 330 core

in vec3 vertexPos;
out vec4 FragColor;

void main() {
    // градиент от синего (низу) к красному (верху)
    float t = (vertexPos.y + 0.5); // нормируем Y: [-0.5..0.5] → [0..1]
    FragColor = vec4(t, 0.0, 1.0 - t, 1.0);
}
