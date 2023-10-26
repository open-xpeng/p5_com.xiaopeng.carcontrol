package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.opengl.GLES20;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class WaveSystem {
    private static final float INCREASE_LENGTH = 0.6f;
    private static final float LINE_LENGTH = 1.0f;
    private static final int LINE_LINE_COUNT = 2;
    private static final float LINE_LINE_WIDTH = 0.002f;
    private static final float LINE_Z_STEP = 0.028571427f;
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int REAL_LINE_COUNT = 9;
    private static final int STRIDE = 20;
    private static final int STRIP_COUNT = 4;
    private static final int STRIP_POINT_COUNT = 270;
    private static final float STRIP_WIDTH = 0.002f;
    private static final String TAG = "WaveSystem";
    private static final int TEXTURE_COMPONENT_COUNT = 2;
    private static final float TEXTURE_LINE_WIDTH = 0.050000004f;
    private static final float TEXTURE_SPACE_WIDTH = 0.18749999f;
    private static final float TEXTURE_WIDTH = 0.050000004f;
    private static final int TOTAL_COMPONENT_COUNT = 5;
    private static final int TOTAL_POINT_COUNT = 1080;
    private static final float VENT_RADIUS = 0.017453294f;
    private static final float VENT_RADIUS_STEP = 0.005817765f;
    private static final float VENT_WIDTH = 0.04f;
    private static final int WAVE_DRAW_COUNT = 30;
    private static final int WAVE_LINE_COUNT = 5;
    private static final int WAVE_LINE_POINT_COUNT = 15;
    private static final float WAVE_SPACE_WIDTH = 0.0074999994f;
    private static final float Z_STEP_INCREASE = 0.0065934067f;
    private boolean mIsClearAndReset;
    private float mStartTime;
    private final VertexArray mVertexArray;
    private List<WaveLine> waveLineList = new ArrayList();
    private float[] mVertexData = new float[5400];

    public WaveSystem() {
        initVertexData();
        this.mVertexArray = new VertexArray(this.mVertexData);
    }

    private void initVertexData() {
        int i;
        float f;
        float f2;
        float f3;
        float f4;
        WaveLine waveLine;
        WaveLine waveLine2;
        float f5;
        int i2 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            ArrayList arrayList = new ArrayList();
            int i4 = 0;
            while (true) {
                i = 15;
                if (i4 >= 5) {
                    break;
                }
                arrayList.add(new WaveLine(15));
                i4++;
            }
            this.waveLineList.addAll(arrayList);
            double d = 0.008726647f - (i3 * VENT_RADIUS_STEP);
            float sin = (float) Math.sin(d);
            float cos = (float) Math.cos(d);
            float f6 = i3 % 2 > 0 ? 0.0039999997f : 0.0f;
            int i5 = 0;
            while (i5 < 9) {
                int i6 = i5 % 2;
                if (i6 < 1) {
                    int i7 = i5 / 2;
                    float f7 = i7;
                    float f8 = i6;
                    f = (0.0095f * f7) + (f8 * 0.002f) + f6;
                    f2 = 0.002f + f;
                    f3 = (f7 * 0.23749998f) + (f8 * 0.050000004f);
                    f4 = 0.050000004f + f3;
                    waveLine2 = (WaveLine) arrayList.get(i7);
                    waveLine = waveLine2;
                } else {
                    int i8 = (i5 - 1) / 2;
                    float f9 = i8;
                    f = (0.0095f * f9) + 0.002f + f6;
                    f2 = WAVE_SPACE_WIDTH + f;
                    f3 = (f9 * 0.23749998f) + 0.050000004f;
                    f4 = f3 + TEXTURE_SPACE_WIDTH;
                    waveLine = (WaveLine) arrayList.get((i5 + 1) / 2);
                    waveLine2 = (WaveLine) arrayList.get(i8);
                }
                int i9 = 0;
                float f10 = 0.0f;
                while (i9 < i) {
                    WavePoint wavePoint = new WavePoint(i9);
                    waveLine2.add(wavePoint);
                    ArrayList arrayList2 = arrayList;
                    WavePoint wavePoint2 = new WavePoint(i9);
                    waveLine.add(wavePoint2);
                    if (i9 == 0) {
                        f5 = f6;
                        f10 = 0.0f;
                    } else {
                        f5 = f6;
                        f10 = f10 + LINE_Z_STEP + ((i9 - 1) * Z_STEP_INCREASE);
                    }
                    if (f10 > 1.0f) {
                        f10 = 1.0f;
                    }
                    WaveLine waveLine3 = waveLine2;
                    float f11 = sin * f10;
                    float f12 = f10 / 1.0f;
                    if (f12 > 0.99f) {
                        f12 = 0.99f;
                    }
                    float f13 = sin;
                    float f14 = cos * f10;
                    float f15 = cos;
                    WaveLine waveLine4 = waveLine;
                    int i10 = i2 + 1;
                    this.mVertexData[i2] = wavePoint.setValueX(i2, f);
                    int i11 = i10 + 1;
                    this.mVertexData[i10] = wavePoint.setValueY(i10, f11);
                    int i12 = i11 + 1;
                    this.mVertexData[i11] = wavePoint.setValueZ(i11, f14);
                    int i13 = i12 + 1;
                    this.mVertexData[i12] = wavePoint.setValueTextureX(i12, f3);
                    int i14 = i13 + 1;
                    this.mVertexData[i13] = wavePoint.setValueTextureY(i13, f12);
                    int i15 = i14 + 1;
                    this.mVertexData[i14] = wavePoint2.setValueX(i14, f2);
                    int i16 = i15 + 1;
                    this.mVertexData[i15] = wavePoint2.setValueY(i15, f11);
                    int i17 = i16 + 1;
                    this.mVertexData[i16] = wavePoint2.setValueZ(i16, f14);
                    int i18 = i17 + 1;
                    this.mVertexData[i17] = wavePoint2.setValueTextureX(i17, f4);
                    this.mVertexData[i18] = wavePoint2.setValueTextureY(i18, f12);
                    i9++;
                    i2 = i18 + 1;
                    arrayList = arrayList2;
                    f6 = f5;
                    waveLine2 = waveLine3;
                    sin = f13;
                    cos = f15;
                    waveLine = waveLine4;
                    i = 15;
                }
                i5++;
                i = 15;
            }
        }
    }

    public void updateWaveLine(float currentTime) {
        if (this.mIsClearAndReset) {
            this.mStartTime = currentTime;
        }
        this.mIsClearAndReset = false;
        for (int i = 0; i < this.waveLineList.size(); i++) {
            this.waveLineList.get(i).update(currentTime - this.mStartTime, this.mVertexData);
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

    private void printData() {
        for (int i = 0; i < TOTAL_POINT_COUNT; i++) {
            int i2 = i * 5;
            LogUtils.d(TAG, "point x " + this.mVertexData[i2] + ", y " + this.mVertexData[i2 + 1] + ", z " + this.mVertexData[i2 + 2]);
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
        for (int i = 0; i < 36; i++) {
            GLES20.glDrawArrays(5, i * 30, 30);
        }
    }
}
