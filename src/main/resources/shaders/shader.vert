#version 460

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec3 inNormal;
layout(location = 2) in vec2 inUv;

layout(push_constant) uniform Transformations {
    mat4 viewMatrix;
    mat4 rotationMatrix;
} transformations;

layout(location = 0) out float surfaceNormal;
layout(location = 1) out vec2 textCoords;

void main() {
    gl_Position = transformations.viewMatrix * vec4(inPosition, 1.0);
    vec4 nv = transformations.rotationMatrix * vec4(inNormal, 1.0);
    surfaceNormal = dot(nv.xyz, vec3(0, 0, -1));
    textCoords = inUv;
}
