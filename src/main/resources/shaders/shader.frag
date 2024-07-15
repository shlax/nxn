#version 460

layout(set = 0, binding = 0) uniform sampler2D textSampler;

layout(location = 0) in vec2 textCoords;

layout(location = 0) out vec4 outColor;

void main() {
    outColor = texture(textSampler, textCoords);
}
