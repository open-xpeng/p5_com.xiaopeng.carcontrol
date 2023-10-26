package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.opengl.GLES20;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class BlowWindowSystem {
    private static final float BLOW_WINDOW_BOTTOM;
    private static final float BLOW_WINDOW_HEIGHT;
    private static final float BLOW_WINDOW_LEFT = -0.31f;
    private static final float BLOW_WINDOW_WIDTH;
    private static final int LINE_COUNT = 40;
    private static final int LINE_POINT = 10;
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = 20;
    private static final int STRIP_COUNT = 20;
    private static final float STRIP_STEP_X;
    private static final float STRIP_STEP_Y;
    private static final String TAG = "BlowWindowSystem";
    private static final int TEXTURE_COMPONENT_COUNT = 2;
    private static final float TEXTURE_STEP_X = 0.05f;
    private static final float TEXTURE_STEP_Y = 0.11111111f;
    private static final int TOTAL_COMPONENT_COUNT = 5;
    private static final int TOTAL_POINT_COUNT = 400;
    private static final int WAVE_DRAW_COUNT = 20;
    private boolean mIsClearAndReset;
    private float mStartTime;
    private final VertexArray mVertexArray;
    private List<WaveLine> waveLineList = new ArrayList();
    private float[] mVertexData = new float[2000];

    static {
        BLOW_WINDOW_BOTTOM = BaseFeatureOption.getInstance().isDrvRightVentOnTop() ? 0.309f : 0.297f;
        float f = BaseFeatureOption.getInstance().isDrvRightVentOnTop() ? 0.628f : 0.615f;
        BLOW_WINDOW_WIDTH = f;
        float f2 = (BaseFeatureOption.getInstance().isDrvRightVentOnTop() ? 0.235f : 0.2085f) * f;
        BLOW_WINDOW_HEIGHT = f2;
        STRIP_STEP_X = f / 20.0f;
        STRIP_STEP_Y = f2 / 9.0f;
    }

    public BlowWindowSystem() {
        initData();
        this.mVertexArray = new VertexArray(this.mVertexData);
    }

    private void initData() {
        int i;
        int i2;
        int i3 = 0;
        while (true) {
            i = 10;
            i2 = 20;
            if (i3 > 20) {
                break;
            }
            this.waveLineList.add(new WaveLine(10));
            i3++;
        }
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            WaveLine waveLine = this.waveLineList.get(i4);
            int i6 = i4 + 1;
            WaveLine waveLine2 = this.waveLineList.get(i6);
            float f = i4;
            float f2 = STRIP_STEP_X;
            float f3 = (f * f2) + BLOW_WINDOW_LEFT;
            float f4 = f2 + f3;
            float f5 = f * TEXTURE_STEP_X;
            float f6 = i6 * TEXTURE_STEP_X;
            int i7 = 0;
            while (i7 < i) {
                WavePoint wavePoint = new WavePoint(i7);
                waveLine.add(wavePoint);
                WavePoint wavePoint2 = new WavePoint(i7);
                waveLine2.add(wavePoint2);
                float f7 = i7;
                float f8 = BLOW_WINDOW_BOTTOM + (STRIP_STEP_Y * f7);
                float f9 = 1.0f - (f7 * TEXTURE_STEP_Y);
                float f10 = 0.01f;
                if (f9 >= 0.01f) {
                    f10 = f9;
                }
                int i8 = i5 + 1;
                this.mVertexData[i5] = wavePoint.setValueX(i5, f3);
                int i9 = i8 + 1;
                this.mVertexData[i8] = wavePoint.setValueY(i8, f8);
                int i10 = i9 + 1;
                WaveLine waveLine3 = waveLine;
                this.mVertexData[i9] = wavePoint.setValueZ(i9, 0.0f);
                int i11 = i10 + 1;
                this.mVertexData[i10] = wavePoint.setValueTextureX(i10, f5);
                int i12 = i11 + 1;
                this.mVertexData[i11] = wavePoint.setValueTextureY(i11, f10);
                int i13 = i12 + 1;
                this.mVertexData[i12] = wavePoint2.setValueX(i12, f4);
                int i14 = i13 + 1;
                this.mVertexData[i13] = wavePoint2.setValueY(i13, f8);
                int i15 = i14 + 1;
                this.mVertexData[i14] = wavePoint2.setValueZ(i14, 0.0f);
                int i16 = i15 + 1;
                this.mVertexData[i15] = wavePoint2.setValueTextureX(i15, f6);
                this.mVertexData[i16] = wavePoint2.setValueTextureY(i16, f10);
                i7++;
                i5 = i16 + 1;
                waveLine = waveLine3;
                i = 10;
                i2 = 20;
            }
            i4 = i6;
        }
    }

    public void updateWaveLine(float currentTime) {
        if (this.mIsClearAndReset) {
            this.mStartTime = currentTime;
        }
        this.mIsClearAndReset = false;
        for (int i = 2; i < this.waveLineList.size() - 2; i++) {
            this.waveLineList.get(i).updateWindow(currentTime - this.mStartTime, this.mVertexData);
        }
        VertexArray vertexArray = this.mVertexArray;
        float[] fArr = this.mVertexData;
        vertexArray.updateBuffer(fArr, 0, fArr.length);
    }

    public void resetAndClearWave() {
        if (this.mIsClearAndReset) {
            return;
        }
        this.mIsClearAndReset = true;
        int i = 0;
        for (int i2 = 0; i2 < this.waveLineList.size(); i2++) {
            if (this.waveLineList.get(i2).resetAndClearWave(this.mVertexData)) {
                i++;
            }
        }
        if (i > 0) {
            VertexArray vertexArray = this.mVertexArray;
            float[] fArr = this.mVertexData;
            vertexArray.updateBuffer(fArr, 0, fArr.length);
        }
    }

    public void bindData(WaveShaderProgram shaderProgram) {
        this.mVertexArray.setVertexAttrPointer(0, shaderProgram.getPositionLocation(), 3, 20);
        this.mVertexArray.setVertexAttrPointer(3, shaderProgram.getTextureCooLocation(), 2, 20);
    }

    public void setSpeed(int speed) {
        for (WaveLine waveLine : this.waveLineList) {
            waveLine.setSpeed(speed);
        }
    }

    public void draw() {
        for (int i = 0; i < 20; i++) {
            GLES20.glDrawArrays(5, i * 20, 20);
        }
    }
}
