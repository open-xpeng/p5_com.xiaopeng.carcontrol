package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.content.Context;
import android.opengl.GLES20;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class ParticleShaderProgram extends ShaderProgram {
    private static final String TAG = "ParticleShaderProgram";
    private static final String U_SPEED = "u_Speed";
    private final int aDirectionLocation;
    private final int aPositionLocation;
    private final int aStartTimeLocation;
    private final int uColorLocation;
    private final int uMatrixLocation;
    private final int uSpeedLocation;
    private final int uTimeLocation;

    public ParticleShaderProgram(Context context) {
        super(context, R.raw.particle_vertex_shader, R.raw.particle_fragment_shader);
        this.uMatrixLocation = GLES20.glGetUniformLocation(this.mProgramId, "u_Matrix");
        this.uTimeLocation = GLES20.glGetUniformLocation(this.mProgramId, "u_Time");
        this.uSpeedLocation = GLES20.glGetUniformLocation(this.mProgramId, U_SPEED);
        this.uColorLocation = GLES20.glGetUniformLocation(this.mProgramId, "u_Color");
        this.aPositionLocation = GLES20.glGetAttribLocation(this.mProgramId, "a_Position");
        this.aDirectionLocation = GLES20.glGetAttribLocation(this.mProgramId, "a_DirectionVector");
        this.aStartTimeLocation = GLES20.glGetAttribLocation(this.mProgramId, "a_ParticleStartTime");
    }

    public int getPositionLocation() {
        return this.aPositionLocation;
    }

    public int getDirectionLocation() {
        return this.aDirectionLocation;
    }

    public int getStartTimeLocation() {
        return this.aStartTimeLocation;
    }

    public void setUniformMatrix(float[] matrix) {
        GLES20.glUniformMatrix4fv(this.uMatrixLocation, 1, false, matrix, 0);
    }

    public void setUniformTime(float time) {
        GLES20.glUniform1f(this.uTimeLocation, time);
    }

    public void setUniformColor(float red, float green, float blue) {
        GLES20.glUniform3f(this.uColorLocation, red, green, blue);
    }

    public void setUniformSpeed(float speed) {
        GLES20.glUniform1f(this.uSpeedLocation, speed);
    }
}
