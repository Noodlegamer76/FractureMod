#version 330 core

out vec4 FragColor;

uniform sampler2D Depth;

in vec2 texCoord0;

vec3 projectAndDivide(mat4 projectionMatrix, vec3 position) {
    vec4 homogeneousPos = projectionMatrix * vec4(position, 1.0);
    return homogeneousPos.xyz / homogeneousPos.w;
}

void main() {
    ivec2 coord = ivec2(gl_FragCoord.xy);
    vec4 depth = texture(Depth, gl_FragCoord.xy);
    FragColor = depth;
}
