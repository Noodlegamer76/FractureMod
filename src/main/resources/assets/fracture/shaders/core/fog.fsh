#version 420

out vec4 FragColor;

uniform sampler2D Depth;
uniform sampler2D Color;
uniform mat4 ProjMat;
uniform mat4 ModelViewMat;
uniform mat4 ModelView2;
uniform vec2 ScreenSize;
uniform vec3 CameraPos;
uniform vec3 FColor;
uniform float MinFogDistance;
uniform float MaxFogDistance;
uniform float DoSkyFog;

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
    //vec3 eyePlayerPos = vec3(mat3(inverse(ModelView2)) * viewPos);
    //vec3 eyeCameraPosition = CameraPos + inverse(ModelView2)[3].xyz;
    //vec3 worldPos = eyePlayerPos + eyeCameraPosition;
    float distanceFromCamera = length(viewPos);
    //float distanceFromWorld = length(worldPos);

    float fogStrength = float(max(min((distanceFromCamera - MinFogDistance) / (MaxFogDistance - MinFogDistance), 1.0), 0.));

    FragColor = vec4(FColor, fogStrength);
}
