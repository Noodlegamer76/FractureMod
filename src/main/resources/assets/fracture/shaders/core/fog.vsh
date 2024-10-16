#version 150


in vec3 Position;
in vec2 UV0;
in vec2 UV1;

out vec2 texCoord0;

uniform mat4 ProjMat;
uniform mat4 ModelViewMat;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    texCoord0 = UV1;
}
