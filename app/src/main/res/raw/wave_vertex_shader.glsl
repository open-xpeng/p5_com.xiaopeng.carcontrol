uniform mat4 u_Matrix;
uniform float u_AngleHor;

attribute vec3 a_Position;
attribute vec2 a_TextureCoordinates;

varying vec2 v_TextureCoordinates;

void main()
{
    v_TextureCoordinates = a_TextureCoordinates;

    vec3 currentPosition = a_Position;

    currentPosition.x = currentPosition.x + currentPosition.z * sin(u_AngleHor);
    currentPosition.z = currentPosition.z * cos(u_AngleHor);

    gl_Position = u_Matrix * vec4(currentPosition, 1.0);
    gl_PointSize = 5.0;
}
