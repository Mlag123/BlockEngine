#version 330 core

out vec4 FragColor;

in vec3 FragPos;      // позиция фрагмента в мировых координатах
in vec3 Normal;       // нормаль фрагмента в мировых координатах

uniform vec3 lightDir; // направление солнечного света (должно быть нормализовано)
uniform vec3 lightColor; // цвет солнца
uniform float power;   // сила света
uniform vec3 objectColor; // цвет объекта

void main()
{
    // Нормализуем нормаль
    vec3 norm = normalize(Normal);

    // Расчёт косинуса угла между нормалью и направлением света
    float diff = max(dot(norm, -lightDir), 0.0);

    // Затенение по углам: добавляем мягкий градиент
    float shadow = pow(1.0 - diff, 2.0); // чем меньше diff, тем темнее

    // Финальный цвет
    vec3 color = objectColor * (diff * power + 0.1); // базовое освещение
    color *= 1.0 - shadow; // применяем затемнение по углам

    FragColor = vec4(color * lightColor, 1.0);
}
