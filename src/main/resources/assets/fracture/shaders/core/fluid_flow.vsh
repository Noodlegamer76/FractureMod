#version 330 core

in vec3 Position;
in vec2 UV0;

uniform mat4 ModelViewMat;  // Combined model-view matrix
uniform mat4 ProjMat;  // Projection matrix
uniform vec3 CameraPos;

out vec3 worldPos;
out vec2 TexCoord0;

void main() {
    vec3 fragPos = (ModelViewMat * vec4(Position, 1.0)).xyz;
    worldPos = fragPos;

    gl_Position = ProjMat * vec4(fragPos, 1.0);

    TexCoord0 = UV0;
}