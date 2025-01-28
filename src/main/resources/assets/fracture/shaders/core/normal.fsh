#version 330

out vec4 FragColor;

uniform sampler2D Depth;
uniform vec2 ScreenSize;
uniform mat4 ProjMat;

vec3 reconstructViewSpacePos(vec2 normCoords, float depth) {
    // Clip space position
    vec4 clipPos = vec4(normCoords * 2.0 - 1.0, depth, 1.0);

    // View space position
    vec4 viewPos = inverse(ProjMat) * clipPos;
    viewPos /= viewPos.w; // Perspective divide
    return viewPos.xyz;
}

void main() {
    vec2 normCoords = gl_FragCoord.xy / ScreenSize;
    float depth = texture2D(Depth, normCoords).r;

    vec2 texelSize = 1.0 / ScreenSize;
    float depthRight = texture2D(Depth, normCoords + vec2(texelSize.x, 0.0)).r;
    float depthUp = texture2D(Depth, normCoords + vec2(0.0, texelSize.y)).r;

    vec3 pos = reconstructViewSpacePos(normCoords, depth);
    vec3 posRight = reconstructViewSpacePos(normCoords + vec2(texelSize.x, 0.0), depthRight);
    vec3 posUp = reconstructViewSpacePos(normCoords + vec2(0.0, texelSize.y), depthUp);

    vec3 normal = normalize(cross(posUp - pos, posRight - pos));
    FragColor = vec4(normal * 0.5 + 0.5, 1.0); // Encode normal to [0, 1]
}