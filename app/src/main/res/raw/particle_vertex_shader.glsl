uniform mat4 u_Matrix;
uniform float u_Time;
uniform float u_Speed;

attribute vec3 a_Position;
attribute vec3 a_DirectionVector;
attribute float a_ParticleStartTime;
//attribute vec2 a_TextureCoordinates;

//varying vec2 v_TextureCoordinates;
varying float v_Alpha;

void main()
{
    //v_TextureCoordinates = a_TextureCoordinates;
    float elapsedTime = u_Time - a_ParticleStartTime;
    v_Alpha = 2.3 - elapsedTime / u_Speed;
    if (v_Alpha > 1.0) {
        v_Alpha = 1.0;
    }
    vec3 currentPosition = a_Position + (a_DirectionVector * elapsedTime);
    gl_Position = u_Matrix * vec4(currentPosition, 1.0);
    gl_PointSize = 2.0;
}