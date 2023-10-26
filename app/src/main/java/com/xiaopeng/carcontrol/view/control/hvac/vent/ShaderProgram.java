package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.content.Context;
import android.opengl.GLES20;

/* loaded from: classes2.dex */
public class ShaderProgram {
    protected static final String A_COLOR = "a_Color";
    protected static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    protected static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";
    protected static final String A_POSITION = "a_Position";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    protected static final String A_WAVE = "a_Wave";
    protected static final String U_COLOR = "u_Color";
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_TIME = "u_Time";
    protected int mProgramId;

    public ShaderProgram(Context context, int vertexShaderResource, int fragmentShaderResource) {
        int linkProgram = ShaderHelper.linkProgram(ShaderHelper.compileVertexShader(TextResourceReader.readTextFromFile(context, vertexShaderResource)), ShaderHelper.compileFragmentShader(TextResourceReader.readTextFromFile(context, fragmentShaderResource)));
        this.mProgramId = linkProgram;
        ShaderHelper.validateProgram(linkProgram);
    }

    public void useProgram() {
        GLES20.glUseProgram(this.mProgramId);
    }
}
