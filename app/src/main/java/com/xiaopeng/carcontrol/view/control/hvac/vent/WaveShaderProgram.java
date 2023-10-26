package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.content.Context;
import android.opengl.GLES20;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class WaveShaderProgram extends ShaderProgram {
    private static final String U_ANGLE_Hor = "u_AngleHor";
    private static final String U_TEXTURE_ALPHA = "u_TextureAlpha";
    private final int aPositionLocation;
    private final int aTextureCooLocation;
    private final int uAngleHorLocation;
    private final int uMatrixLocation;
    private final int uTextureAlpha;
    private final int uTextureUniteLocation;

    public WaveShaderProgram(Context context) {
        super(context, R.raw.wave_vertex_shader, R.raw.wave_fragment_shader);
        this.uMatrixLocation = GLES20.glGetUniformLocation(this.mProgramId, "u_Matrix");
        this.uAngleHorLocation = GLES20.glGetUniformLocation(this.mProgramId, U_ANGLE_Hor);
        this.uTextureUniteLocation = GLES20.glGetUniformLocation(this.mProgramId, "u_TextureUnit");
        this.uTextureAlpha = GLES20.glGetUniformLocation(this.mProgramId, U_TEXTURE_ALPHA);
        this.aPositionLocation = GLES20.glGetAttribLocation(this.mProgramId, "a_Position");
        this.aTextureCooLocation = GLES20.glGetAttribLocation(this.mProgramId, "a_TextureCoordinates");
    }

    public int getPositionLocation() {
        return this.aPositionLocation;
    }

    public int getTextureCooLocation() {
        return this.aTextureCooLocation;
    }

    public void setUniformAngle(float[] matrix, float angleHor) {
        GLES20.glUniformMatrix4fv(this.uMatrixLocation, 1, false, matrix, 0);
        GLES20.glUniform1f(this.uAngleHorLocation, angleHor);
    }

    public void setUniformTextureAlpha(float alpha) {
        GLES20.glUniform1f(this.uTextureAlpha, alpha);
    }

    public void setUniformTexture(int texture) {
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, texture);
        GLES20.glUniform1i(this.uTextureUniteLocation, 0);
    }
}
