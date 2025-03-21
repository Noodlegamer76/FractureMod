#version 330

out vec4 FragColor;

uniform sampler2D Depth;
uniform sampler2D Sampler1;
uniform sampler2D Sampler2;
uniform sampler2D Normal;
uniform mat4 ProjMat;
uniform mat4 ModelViewMat;
uniform mat4 ModelView2;
uniform vec2 ScreenSize;
uniform vec3 CameraPos;
uniform vec3 FColor;
uniform float MinFogDistance;
uniform float MaxFogDistance;
uniform float DoSkyFog;
uniform float GameTime;

vec3 projectAndDivide(mat4 projectionMatrix, vec3 position) {
    vec4 homogeneousPos = projectionMatrix * vec4(position, 1.0);
    return homogeneousPos.xyz / homogeneousPos.w;
}

void main() {

    vec2 normCoords = gl_FragCoord.xy / ScreenSize;
    vec3 screenPos = vec3(normCoords, texture(Depth, normCoords));
    vec3 ndcPos = screenPos * 2.0 - 1.0;
    vec3 viewPos = projectAndDivide(inverse(ProjMat), ndcPos);
    vec3 eyePlayerPos = vec3(mat3(inverse(ModelView2)) * viewPos);
    vec3 eyeCameraPosition = CameraPos + inverse(ModelView2)[3].xyz;
    vec3 worldPos = eyePlayerPos + eyeCameraPosition;
    float distanceFromCamera = length(viewPos);

    float noise = texture(Sampler1, (vec2((worldPos.x + worldPos.y), (worldPos.z + worldPos.y)) * 0.075)).r * 0.5;
    noise += texture(Sampler2, (vec2((worldPos.x + worldPos.y), (worldPos.z + worldPos.y)) * 0.075)).r * 0.5;

    float fogStrength = float(max(min((distanceFromCamera - MinFogDistance) / (MaxFogDistance - MinFogDistance), 1.0), 0.));

    fogStrength += noise * fogStrength;

    FragColor = vec4(FColor, fogStrength);
}
