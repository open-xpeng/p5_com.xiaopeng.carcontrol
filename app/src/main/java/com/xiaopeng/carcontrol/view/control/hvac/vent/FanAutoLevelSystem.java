package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.opengl.GLES20;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class FanAutoLevelSystem {
    private static final float LINE_LEFT_LOCATION_X = -3.35f;
    private static final int LINE_POINT_COUNT = 30;
    private static final float LINE_TOP_LOCATION_Y = 0.25f;
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = 24;
    private static final int STRIP_COUNT = 3;
    private static final float STRIP_HEIGHT = 0.5f;
    private static final float STRIP_WIDTH = 6.7f;
    private static final String TAG = "FanAutoLevelSystem";
    private static final int TEXTURE_COMPONENT_COUNT = 2;
    private static final int TEXTURE_TYPE_COMPONENT_COUNT = 1;
    private static final int TOTAL_COMPONENT_COUNT = 6;
    private static final int TOTAL_POINT_COUNT = 180;
    private static final int WAVE_DRAW_COUNT = 60;
    private static final float X_STEP = 0.23103447f;
    private static final float X_TEXTURE_STEP = 0.03448276f;
    private final VertexArray mVertexArray;
    private List<WaveLine> mWaveLineList = new ArrayList();
    private float[] mVertexData = new float[1080];

    public FanAutoLevelSystem() {
        initVertexData();
        this.mVertexArray = new VertexArray(this.mVertexData);
    }

    private void initVertexData() {
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            WaveLine waveLine = new WaveLine(30);
            this.mWaveLineList.add(waveLine);
            int i3 = 0;
            while (i3 < 30) {
                WavePoint wavePoint = new WavePoint(i3);
                WavePoint wavePoint2 = new WavePoint(i3);
                waveLine.add(wavePoint);
                waveLine.add(wavePoint2);
                float f = i3;
                float f2 = (X_STEP * f) + LINE_LEFT_LOCATION_X;
                float f3 = f * X_TEXTURE_STEP;
                int i4 = i + 1;
                this.mVertexData[i] = wavePoint.setValueX(i, f2);
                int i5 = i4 + 1;
                this.mVertexData[i4] = wavePoint.setValueY(i4, LINE_TOP_LOCATION_Y);
                int i6 = i5 + 1;
                this.mVertexData[i5] = wavePoint.setValueZ(i5, 0.0f);
                int i7 = i6 + 1;
                this.mVertexData[i6] = wavePoint.setValueTextureX(i6, f3);
                int i8 = i7 + 1;
                this.mVertexData[i7] = wavePoint.setValueTextureY(i7, 0.0f);
                float[] fArr = this.mVertexData;
                int i9 = i8 + 1;
                float f4 = i2 + 1;
                fArr[i8] = f4;
                int i10 = i9 + 1;
                fArr[i9] = wavePoint2.setValueX(i9, f2);
                int i11 = i10 + 1;
                this.mVertexData[i10] = wavePoint2.setValueY(i10, -0.25f);
                int i12 = i11 + 1;
                this.mVertexData[i11] = wavePoint2.setValueZ(i11, 0.0f);
                int i13 = i12 + 1;
                this.mVertexData[i12] = wavePoint2.setValueTextureX(i12, f3);
                int i14 = i13 + 1;
                this.mVertexData[i13] = wavePoint2.setValueTextureY(i13, 1.0f);
                this.mVertexData[i14] = f4;
                i3++;
                i = i14 + 1;
            }
        }
    }

    public void updateWaveLine(float currentTime) {
        for (int i = 0; i < this.mWaveLineList.size(); i++) {
            this.mWaveLineList.get(i).updateAuto(currentTime, this.mVertexData, i * 0.3f);
        }
        VertexArray vertexArray = this.mVertexArray;
        float[] fArr = this.mVertexData;
        vertexArray.updateBuffer(fArr, 0, fArr.length);
    }

    public void resetAndClearWave() {
        int i = 0;
        for (int i2 = 0; i2 < this.mWaveLineList.size(); i2++) {
            if (this.mWaveLineList.get(i2).resetAndClearWave(this.mVertexData)) {
                i++;
            }
        }
        if (i > 0) {
            VertexArray vertexArray = this.mVertexArray;
            float[] fArr = this.mVertexData;
            vertexArray.updateBuffer(fArr, 0, fArr.length);
        }
    }

    public void bindData(BackgroundShaderProgram shaderProgram) {
        this.mVertexArray.setVertexAttrPointer(0, shaderProgram.getPositionLocation(), 3, 24);
        this.mVertexArray.setVertexAttrPointer(3, shaderProgram.getTextureCooLocation(), 2, 24);
        this.mVertexArray.setVertexAttrPointer(5, shaderProgram.getTextureTypeLocation(), 1, 24);
    }

    public void draw() {
        for (int i = 0; i < 3; i++) {
            GLES20.glDrawArrays(5, i * 60, 60);
        }
    }
}
