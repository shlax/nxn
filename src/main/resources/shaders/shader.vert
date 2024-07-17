#version 460

// vec2(0.0, -0.5), vec2(-0.5, 0.5), vec2(0.5, 0.5)
layout(location = 0) in vec3 inPosition;

/* layout(binding = 0) uniform UniformBufferObject{
    mat4 viewMatrix;
} ubo; */

layout(push_constant) uniform Transformations {
    mat4 viewMatrix;
} transformations;

vec2 colors[6] = vec2[](
        vec2(0.5, 0.0),
        vec2(0.0, 1.0),
        vec2(1.0, 1.0),

        vec2(0.5, 0.0),
        vec2(0.0, 1.0),
        vec2(1.0, 1.0)
    );

layout(location = 0) out vec2 textCoords;

void main() {
    gl_Position = transformations.viewMatrix * vec4(inPosition, 1.0);
    textCoords = colors[gl_VertexIndex];
}
