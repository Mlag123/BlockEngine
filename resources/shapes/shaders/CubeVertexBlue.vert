#version 330 core

layout(location = 0) in vec3 aPos;  // координаты вершины

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

out vec3 vertexPos; // передаем в фрагментный шейдер

void main() {
    vertexPos = aPos;
    gl_Position = projection * view * model * vec4(aPos, 1.0);
}
