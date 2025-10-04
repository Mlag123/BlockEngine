#version 330 core

layout(location = 0) in vec3 aPos;   // позиция вершины
layout(location = 1) in vec3 aNormal; // нормаль вершины

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

out vec3 FragPos;   // позиция фрагмента в мировых координатах
out vec3 Normal;    // нормаль фрагмента

void main()
{
    FragPos = vec3(model * vec4(aPos, 1.0));
    Normal = mat3(transpose(inverse(model))) * aNormal; // корректно для масштабирования
    gl_Position = projection * view * vec4(FragPos, 1.0);
}
