uniform mat4 u_Matrix;

attribute vec3 a_Position;
attribute float a_TextureType;//1背景2风口3

attribute vec2 a_TextureCoordinates;

varying vec2 v_TextureCoordinates;
varying float v_TextureType;

void main()
{
    v_TextureCoordinates = a_TextureCoordinates;
    v_TextureType = a_TextureType;

    gl_Position = u_Matrix * vec4(a_Position, 1.0);
}