#version 420

out vec4 FragColor;

uniform sampler2D Depth;
uniform sampler2D Color;
uniform vec2 ScreenSize;
uniform vec3 CameraPos;
uniform mat4 ProjMat;
uniform mat4 ModelViewMat;
uniform mat4 ModelView2;

vec3 projectAndDivide(mat4 projectionMatrix, vec3 position) {
    vec4 homogeneousPos = projectionMatrix * vec4(position, 1.0);
    return homogeneousPos.xyz / homogeneousPos.w;
}

void main() {

    vec2 normCoords = gl_FragCoord.xy / ScreenSize;
    vec3 screenPos = vec3(normCoords, texture(Depth, normCoords));
    //vec3 screenspace = vec3(normCoords, depth.r);
    vec3 ndcPos = screenPos * 2.0 - 1.0;
    vec3 viewPos = projectAndDivide(inverse(ProjMat), ndcPos);
    vec3 eyePlayerPos = vec3(mat3(inverse(ModelView2)) * viewPos);
    vec3 eyeCameraPosition = CameraPos + inverse(ModelView2)[3].xyz;
    vec3 worldPos = eyePlayerPos + eyeCameraPosition;
    float distanceFromCamera = length(viewPos);
    float distanceFromWorld = length(worldPos);
   // float distanceworld = distance(eyeCameraPosition, worldPos);

    vec3 fogColorClose = vec3(0.8,0.8,0.8);
    vec3 fogColorFar = vec3(0.8,0.8,0.8);

    float fogMinimum = 100.;
    float fogMaximum = 150.;

    float fogStrength = float(max(min((distanceFromWorld - fogMinimum) / (fogMaximum - fogMinimum), 1.0), 0));



    vec3 fogColorBlend = mix(fogColorClose, fogColorFar, fogStrength);

    FragColor = vec4(fogColorBlend, fogStrength);;

    if (gl_FragCoord.z == 1.0) {
       FragColor = vec4(fogColorBlend, fogStrength);
    }

    //FragColor = vec4(distanceFromCamera, distanceFromCamera, distanceFromCamera, 1.0);
}
