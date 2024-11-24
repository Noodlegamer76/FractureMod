#version 330 core

in vec3 Position;

uniform mat4 ModelViewMat;  // Combined model-view matrix
uniform mat4 ProjMat;  // Projection matrix

void main()
{
    // Calculate the position in view space
    vec3 fragPos = (ModelViewMat * vec4(Position, 1.0)).xyz;

    // Transform the position to clip space
    gl_Position = ProjMat * vec4(fragPos, 1.0);
}