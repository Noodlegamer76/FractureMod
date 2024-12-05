#version 330 core

in vec2 texCoord0;

uniform sampler2D Color;     // Main scene texture
uniform vec2 ScreenSize;     // Screen resolution
uniform float BlurRadius;    // Blur radius in pixels

out vec4 FragColor;

void main() {
    // Compute normalized coordinates
    vec2 normCoords = gl_FragCoord.xy / ScreenSize;

    // Convert pixel radius to normalized screen space
    vec2 texOffset = BlurRadius / ScreenSize;

    vec4 color = vec4(0.0);

    // Gaussian weights for a 9-sample kernel
    float weights[5] = float[](0.227027, 0.1945946, 0.1216216, 0.054054, 0.016216);

    // Accumulate color with weights
    for (int i = 0; i < 5; ++i) {
        vec2 offset = float(i) * texOffset;

        // Sample texture in both directions
        color += texture(Color, normCoords + offset) * weights[i];
        if (i > 0) {
            color += texture(Color, normCoords - offset) * weights[i];
        }
    }

    FragColor = color;
}
