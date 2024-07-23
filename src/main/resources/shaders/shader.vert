#version 460

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec3 inNormal;
layout(location = 2) in vec2 inUv;

layout(push_constant) uniform Transformations {
    mat4 viewMatrix;
} transformations;

layout(location = 0) out vec2 outUv;

void main() {
    gl_Position = transformations.viewMatrix * vec4(inPosition, 1.0);
    outUv = inUv;
}
