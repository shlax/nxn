#version 460

#extension GL_EXT_debug_printf : enable

layout(set = 0, binding = 0) uniform sampler2D textSampler;

layout(location = 0) in float surfaceNormal;
layout(location = 1) in vec2 textCoords;

layout(location = 0) out vec4 outColor;

void main() {
    // outColor = vec4(surfaceNormal, surfaceNormal, surfaceNormal, 1);
    vec4 c = texture(textSampler, textCoords);
    outColor = vec4(c.xyz * surfaceNormal, 1);
}
