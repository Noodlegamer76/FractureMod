#version 330 core

out vec4 FragColor;

uniform sampler2D Depth;
uniform sampler2D Color;
uniform vec2 ScreenSize;
uniform mat4 ProjMat;

vec3 projectAndDivide(mat4 projectionMatrix, vec3 position) {
    vec4 homogeneousPos = projectionMatrix * vec4(position, 1.0);
    return homogeneousPos.xyz / homogeneousPos.w;
}

void main() {
    vec2 normCoords = gl_FragCoord.xy / ScreenSize;
    vec4 depth = texture(Depth, normCoords);
    vec3 color = texture(Color, normCoords).xyz;
    vec3 screenspace = vec3(normCoords, depth.r);
    vec3 ndcPos = screenspace * 2.0 - 1.0;
    vec3 viewPos = projectAndDivide(inverse(ProjMat), ndcPos);
    float distanceFromCamera = length(viewPos);

    vec3 fogColorClose = vec3(0.8,0.8,0.8);
    vec3 fogColorFar = vec3(0.8,0.8,0.8);

    float fogMinimum = 0.;
    float fogMaximum = 15.;

    float fogStrength = float(max(min((distanceFromCamera - fogMinimum) / (fogMaximum - fogMinimum), 1.0), 0));

    vec3 fogColorBlend = mix(fogColorClose, fogColorFar, fogStrength);

    vec3 final = mix(color, fogColorBlend, fogStrength);

    //fog disabled by setting to Color
    FragColor = vec4(final, 1.0);

    if (gl_FragCoord.z == 1.0) {
       FragColor = vec4(final, 1.0);
    }

    //FragColor = vec4(distanceFromCamera, distanceFromCamera, distanceFromCamera, 1.0);
}
