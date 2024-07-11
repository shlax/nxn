#version 460

// vec2(0.0, -0.5), vec2(-0.5, 0.5), vec2(0.5, 0.5)
layout(location = 0) in vec2 inPosition;

/* layout(binding = 0) uniform UniformBufferObject{
    mat4 viewMatrix;
} ubo; */

layout(push_constant) uniform Transformations {
    mat4 viewMatrix;
} transformations;

vec3 colors[3] = vec3[](
        vec3(1.0, 0.0, 0.0),
        vec3(0.0, 1.0, 0.0),
        vec3(0.0, 0.0, 1.0)
    );

layout(location = 0) out vec3 fragColor;

void main() {
    gl_Position = transformations.viewMatrix * vec4(inPosition, 0.0, 1.0);
    fragColor = colors[gl_VertexIndex];
}
