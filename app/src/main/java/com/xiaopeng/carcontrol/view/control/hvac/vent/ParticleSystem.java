package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.opengl.GLES20;

/* loaded from: classes2.dex */
public class ParticleSystem {
    private static final int DIRECTION_VECTOR_COUNT = 3;
    private static final int PARTICLE_START_TIME = 1;
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = 28;
    private static final String TAG = "ParticleSystem";
    private static final int TOTAL_COMPONENT_COUNT = 7;
    private int currentParticleCount;
    private final int mMaxParticleCount;
    private final VertexArray mVertexArray;
    private float[] mVertexData;
    private int nextParticle;

    public ParticleSystem(int maxParticleCount) {
        this.mVertexData = new float[maxParticleCount * 7];
        this.mVertexArray = new VertexArray(this.mVertexData);
        this.mMaxParticleCount = maxParticleCount;
    }

    public void shootParticle(int count, float startTime) {
        for (int i = 0; i < count; i++) {
            int i2 = this.nextParticle;
            int i3 = i2 * 7;
            int i4 = i2 + 1;
            this.nextParticle = i4;
            int i5 = this.currentParticleCount;
            int i6 = this.mMaxParticleCount;
            if (i5 < i6) {
                this.currentParticleCount = i5 + 1;
            }
            if (i4 == i6) {
                this.nextParticle = 0;
            }
            float random = 0.04f - (((float) Math.random()) * 0.04f);
            float random2 = 0.005f - (((float) Math.random()) * 0.01f);
            float[] fArr = this.mVertexData;
            int i7 = i3 + 1;
            fArr[i3] = random;
            int i8 = i7 + 1;
            fArr[i7] = random2;
            int i9 = i8 + 1;
            fArr[i8] = 0.0f;
            int i10 = i9 + 1;
            fArr[i9] = 0.0f;
            int i11 = i10 + 1;
            fArr[i10] = 0.0f;
            fArr[i11] = 0.5f;
            fArr[i11 + 1] = startTime;
            this.mVertexArray.updateBuffer(fArr, i3, 7);
        }
    }

    public void bindData(ParticleShaderProgram particleShaderProgram) {
        this.mVertexArray.setVertexAttrPointer(0, particleShaderProgram.getPositionLocation(), 3, 28);
        this.mVertexArray.setVertexAttrPointer(3, particleShaderProgram.getDirectionLocation(), 3, 28);
        this.mVertexArray.setVertexAttrPointer(6, particleShaderProgram.getStartTimeLocation(), 1, 28);
    }

    public void draw() {
        GLES20.glDrawArrays(0, 0, this.currentParticleCount);
    }
}
