package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.content.Context;
import android.opengl.GLES20;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class BackgroundShaderProgram extends ShaderProgram {
    private static final String A_TEXTURE_TYPE = "a_TextureType";
    private static final String U_TEXTURE_UNIT_BG = "u_TextureUnitBg";
    private static final String U_TEXTURE_UNIT_BLOW = "u_TextureUnitBlow";
    private static final String U_TEXTURE_UNIT_VENT = "u_TextureUnitVent";
    private final int aPositionLocation;
    private final int aTextureCooLocation;
    private final int aTextureTypeLocation;
    private final int uMatrixLocation;
    private final int uTextureUnitBgLocation;
    private final int uTextureUnitBlowLocation;
    private final int uTextureUnitVentLocation;

    public BackgroundShaderProgram(Context context) {
        super(context, R.raw.background_vertex_shader, R.raw.background_fragment_shader);
        this.uMatrixLocation = GLES20.glGetUniformLocation(this.mProgramId, "u_Matrix");
        this.uTextureUnitBgLocation = GLES20.glGetUniformLocation(this.mProgramId, U_TEXTURE_UNIT_BG);
        this.uTextureUnitVentLocation = GLES20.glGetUniformLocation(this.mProgramId, U_TEXTURE_UNIT_VENT);
        this.uTextureUnitBlowLocation = GLES20.glGetUniformLocation(this.mProgramId, U_TEXTURE_UNIT_BLOW);
        this.aPositionLocation = GLES20.glGetAttribLocation(this.mProgramId, "a_Position");
        this.aTextureTypeLocation = GLES20.glGetAttribLocation(this.mProgramId, A_TEXTURE_TYPE);
        this.aTextureCooLocation = GLES20.glGetAttribLocation(this.mProgramId, "a_TextureCoordinates");
    }

    public int getPositionLocation() {
        return this.aPositionLocation;
    }

    public int getTextureCooLocation() {
        return this.aTextureCooLocation;
    }

    public int getTextureTypeLocation() {
        return this.aTextureTypeLocation;
    }

    public void setUniformMatrix(float[] matrix) {
        GLES20.glUniformMatrix4fv(this.uMatrixLocation, 1, false, matrix, 0);
    }

    public void setUniformTexture(int textureBg, int textureVent, int textureBlow) {
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, textureBg);
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, textureVent);
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, textureBlow);
        GLES20.glUniform1i(this.uTextureUnitBgLocation, 0);
        GLES20.glUniform1i(this.uTextureUnitVentLocation, 1);
        GLES20.glUniform1i(this.uTextureUnitBlowLocation, 2);
    }
}
