precision mediump float;

uniform vec3 u_Color;

//uniform sampler2D u_TextureParticle;

//varying vec2 v_TextureCoordinates;
varying float v_Alpha;

void main()
{
    gl_FragColor = vec4(u_Color, 1.0).rgba * v_Alpha;

    //gl_FragColor = texture2D(u_TextureParticle, v_TextureCoordinates).rgba * v_Alpha;
}