#version 330 core

in vec3 FragPos;
in vec3 Normal;

out vec4 FragColor;

struct Light {
    vec3 direction;
    vec3 color;
};

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
};

uniform Light light;
uniform Material material;
uniform vec3 viewPos;

void main()
{
    // Ambient
    vec3 ambient = material.ambient * light.color;

    // Diffuse
    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(-light.direction); // Directional Light
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * material.diffuse * light.color;

    // Specular
    vec3 viewDir = normalize(viewPos - FragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = spec * material.specular * light.color;

    vec3 result = ambient + diffuse + specular;
    FragColor = vec4(result, 1.0);
}
