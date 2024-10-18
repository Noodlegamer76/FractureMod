#version 330 core

out vec4 FragColor;

in vec2 texCoord0;

uniform sampler2D Depth;
uniform sampler2D Color;
uniform mat4 ProjMat;
uniform mat4 ModelViewMat;
uniform vec2 ScreenSize;

vec3 projectAndDivide(mat4 projectionMatrix, vec3 position) {
    vec4 homogeneousPos = projectionMatrix * vec4(position, 1.0);
    return homogeneousPos.xyz / homogeneousPos.w;
}

void main()
{
    ivec2 coord = ivec2(gl_FragCoord.xy);
    float depth = texelFetch(Depth, coord, 0).r;
    vec3 color = texelFetch(Color, coord, 0).rgb;
    float z_clip_space = depth * 2.0 - 1.0;

    vec2 ndc = (gl_FragCoord.xy / ScreenSize) * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(ndc, z_clip_space, 1.0);

    vec4 eyeCoords = inverse(ProjMat) * clipSpacePosition;

    float distanceFromCamera = abs(eyeCoords.z);

    float fogMinimum = 1.;
    float fogMaximum = 10.;

    float fogStrength = float(max(min((distanceFromCamera - fogMinimum) / (fogMaximum - fogMinimum), 1.0), 0.0));

    vec3 fogMix = mix(color, vec3(1.0, 1.0, 0.0), fogStrength);

     FragColor = vec4(vec3(distanceFromCamera / 10.0), 1.0);


   // ivec2 coord = ivec2(gl_FragCoord.xy);
   // vec4 depth = texelFetch(Depth, coord, 0);
   // vec3 color = texelFetch(Color, coord, 0).rgb;
   // vec3 fogColor = vec3(1.0, 1.0, 0.0);
//
   // vec4 ndc = depth * 2 - 1.0;
   // vec3 view = projectAndDivide(inverse(ProjMat), ndc.xyz);
//
   // float DistanceFromCamera = length(view);
//
   //     float fogMinimum = 0.;
   //     float fogMaximum = 5.;
//
   // float fogStrength = float(max(min((DistanceFromCamera - fogMinimum) / (fogMaximum - fogMinimum), 1.0), 0.0));
//
   // vec3 fogMix = mix(color, fogColor, fogStrength);
//
   // FragColor = vec4(fogMix, 1.0);


    //FragColor = depth;
}



//#version 150
//
//#moj_import <fog.glsl>
//
//uniform mat4 ModelViewMat;
//uniform mat4 ProjMat;
//uniform vec2 ScreenSize;
//
//out vec4 fragColor;
//
//noperspective in vec3 v_ndc;
//
//vec3 projectAndDivide(mat4 projectionMatrix, vec3 position) {
//    vec4 homogeneousPos = projectionMatrix * vec4(position, 1.0);
//    return homogeneousPos.xyz / homogeneousPos.w;
//}
//
//void main() {
//
//    //vec4 ndc = vec4(
//    //(gl_FragCoord.x / ScreenSize.x - 0.5) * 2.0,
//    //(gl_FragCoord.y / ScreenSize.y - 0.5) * 2.0,
//    //(gl_FragCoord.z - 0.5) * 2.0,
//    //1.0);
//
//    vec3 viewPos = projectAndDivide(inverse(ProjMat), v_ndc);
//    vec3 eyePlayerPos = mat3(inverse(ModelViewMat)) * viewPos;
//
//    float DistanceFromCamera = length(viewPos);
//
//    vec3 fogColorClose = vec3(1.0,0.,0.);
//    vec3 fogColorFar = vec3(1.0,0.,0.);
//
//    float fogMinimum = 0.;
//    float fogMaximum = 1.25;
//
//    float fogStrength = float(max(min((DistanceFromCamera - fogMinimum) / (fogMaximum - fogMinimum), 1.0), 0.0));
//
//    vec3 fogColorBlend = mix(fogColorClose, fogColorFar, fogStrength);
//
//    fragColor = vec4(fogColorBlend, fogStrength);
//}
