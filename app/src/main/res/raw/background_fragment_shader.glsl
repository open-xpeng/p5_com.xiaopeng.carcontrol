precision mediump float;

uniform sampler2D u_TextureUnitBg;
uniform sampler2D u_TextureUnitVent;
uniform sampler2D u_TextureUnitBlow;

varying vec2 v_TextureCoordinates;
varying float v_TextureType;

void main()
{
    if (v_TextureType == 1.0) {
        gl_FragColor = texture2D(u_TextureUnitBg, v_TextureCoordinates);
    } else if (v_TextureType == 2.0) {
        gl_FragColor = texture2D(u_TextureUnitVent, v_TextureCoordinates);
    } else if (v_TextureType == 3.0) {
        gl_FragColor = texture2D(u_TextureUnitBlow, v_TextureCoordinates);
    }
}