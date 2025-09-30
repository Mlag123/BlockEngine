#version 330 core

in vec3 aPos;
in vec3 aTexCoord;

out vec3 vTexCoord;
out vec2 vColor;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
    vTexCoord = aTexCoord;
    vColor = vec2(aTexCoord.x, aTexCoord.y); // Преобразуем координаты в цвет
    gl_Position = projection * view * model * vec4(aPos, 1.0);
}
