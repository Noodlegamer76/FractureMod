#version 150

uniform mat4 ProjMat;
uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;
uniform vec2 ScreenSize;

out vec4 fragColor;

noperspective in vec3 v_ndc;
in vec2 texCoord;

vec3 projectAndDivide(mat4 projectionMatrix, vec3 position) {
    vec4 homogeneousPos = projectionMatrix * vec4(position, 1.0);
    return homogeneousPos.xyz / homogeneousPos.w;
}

void main() {
    vec2 normCoords = gl_FragCoord.xy / ScreenSize;
    vec3 color = vec3( texture( DiffuseSampler, normCoords ).rgb);
    vec3 depth = vec3( texture( DiffuseDepthSampler, normCoords ).rgb);
    vec3 screenspace = vec3(normCoords, depth.r);
    vec3 ndcPos = screenspace * 2.0 - 1.0;
    vec3 viewPos = projectAndDivide(inverse(ProjMat), ndcPos);
    float distanceFromCamera = length(viewPos);

    vec3 fogColorClose = vec3(0.8,0.8,0.8);
    vec3 fogColorFar = vec3(0.8,0.8,0.8);

    float fogMinimum = 10.;
    float fogMaximum = 15.;

    float fogStrength = float(max(min((distanceFromCamera - fogMinimum) / (fogMaximum - fogMinimum), 1.0), 0));

    vec3 fogColorBlend = mix(fogColorClose, fogColorFar, fogStrength);

    vec3 final = mix(color, fogColorBlend, fogStrength);

    fragColor = vec4(final, 1.0);
}
