#version 330 core

in vec2 TexCoord0;
in vec3 worldPos;

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
uniform vec2 ScreenSize;
uniform float GameTime;
uniform vec2 NoiseValues;

uniform vec3 u_LightPos[32];
uniform vec3 u_LightColor[32];
uniform float u_LightRadius[32];
uniform int u_LightCount;

out vec4 FragColor;

vec3 hash3( vec2 p ){
    vec3 q = vec3( dot(p,vec2(127.1,311.7)),
    dot(p,vec2(269.5,183.3)),
    dot(p,vec2(419.2,371.9)) );
    return fract(sin(q)*43758.5453);
}

float iqnoise( in vec2 x, float u, float v ){
    vec2 p = floor(x);
    vec2 f = fract(x);

    float k = 1.0+63.0*pow(1.0-v,4.0);

    float va = 0.0;
    float wt = 0.0;
    for( int j=-2; j<=2; j++ )
    for( int i=-2; i<=2; i++ )
    {
        vec2 g = vec2( float(i),float(j) );
        vec3 o = hash3( p + g )*vec3(u,u,1.0);
        vec2 r = g - f + o.xy;
        float d = dot(r,r);
        float ww = pow( 1.0-smoothstep(0.0,1.414,sqrt(d)), k );
        va += o.z*ww;
        wt += ww;
    }

    return va/wt;
}

void main() {
    vec2 uv = TexCoord0;
    float time = GameTime * 250;

    vec2 waterFlow = uv / 50 + vec2(GameTime * 20, GameTime * 60);
    float noiseFlow = texture(Sampler1, waterFlow).r;

    float noise1 = iqnoise(uv + time, NoiseValues.x, NoiseValues.y);
    float noise2 = iqnoise(uv - time * 0.5, NoiseValues.x, NoiseValues.y);

    float combinedNoise = (noise1 + noise2) * 0.5;

    float distortionStrength = 0.02;
    vec2 distortedUV = uv + (combinedNoise * distortionStrength) + noiseFlow;
    vec4 baseColor = texture(Sampler0, distortedUV) * vec4(1.0, 0.0, 0.0, 1.0);

    vec3 waterColor = vec3(0.25, 0.05, 0.1);

    vec3 finalRGB = mix(baseColor.rgb, waterColor, 0.3);

    float highlight = smoothstep(0.4, 0.8, combinedNoise);

    finalRGB += (highlight * 0.1);

    FragColor = vec4(finalRGB, 0.8);
}