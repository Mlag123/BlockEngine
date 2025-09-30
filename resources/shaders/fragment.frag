#version 330 core

in vec2 vColor;
out vec4 FragColor;

void main() {
    FragColor = vec4(vColor, 1.0);
    gl_Position = vec4(aPos, 1.0); // Важно: gl_Position должен быть vec4
}
