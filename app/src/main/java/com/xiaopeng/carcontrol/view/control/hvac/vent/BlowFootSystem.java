package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.opengl.GLES20;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class BlowFootSystem {
    private static final float BLOW_FOOT_LEFT = -0.15f;
    private static final float BLOW_FOOT_WIDTH = 0.3f;
    private static final int LAYER_COUNT = 1;
    private static final int LINE_POINT_COUNT = 20;
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = 20;
    private static final int STRIP_COUNT = 7;
    private static final String TAG = "BlowFootSystem";
    private static final int TEXTURE_COMPONENT_COUNT = 2;
    private static final int TOTAL_COMPONENT_COUNT = 5;
    private static final int TOTAL_LINE_COUNT = 14;
    private static final int TOTAL_POINT_COUNT = 280;
    private static final int WAVE_DRAW_COUNT = 40;
    private static final int WAVE_LINE_COUNT = 8;
    private static final float X_STEP = 0.042857144f;
    private static final float X_TEXTURE_STEP = 0.14285715f;
    private static final float Y_TEXTURE_STEP = 0.05263158f;
    private boolean mIsClearAndReset;
    private float mStartTime;
    private final VertexArray mVertexArray;
    private List<WaveLine> waveLineList = new ArrayList();
    private float bezierEndZ = 0.1f;
    private float bezierEndY = -0.2f;
    private float bezierP1Z = -0.1f;
    private float bezierP1Y = -0.1f;
    private float[] mVertexData = new float[IpcConfig.DeviceCommunicationConfig.SEND_CAR_CONTROL_TOPIC];

    public BlowFootSystem() {
        initData();
        this.mVertexArray = new VertexArray(this.mVertexData);
    }

    private void initData() {
        int i;
        int i2 = 0;
        for (int i3 = 0; i3 < 1; i3++) {
            ArrayList arrayList = new ArrayList();
            int i4 = 0;
            while (true) {
                i = 20;
                if (i4 >= 8) {
                    break;
                }
                arrayList.add(new WaveLine(20));
                i4++;
            }
            this.waveLineList.addAll(arrayList);
            int i5 = 0;
            while (i5 < 7) {
                WaveLine waveLine = this.waveLineList.get(i5);
                int i6 = i5 + 1;
                WaveLine waveLine2 = this.waveLineList.get(i6);
                float f = i5;
                float f2 = (f * X_STEP) + BLOW_FOOT_LEFT;
                float f3 = X_STEP + f2;
                float f4 = f * X_TEXTURE_STEP;
                float f5 = X_TEXTURE_STEP + f4;
                int i7 = 0;
                while (i7 < i) {
                    float f6 = i7;
                    float f7 = f6 / 19.0f;
                    float f8 = this.bezierP1Y;
                    float f9 = f8 * f7;
                    float f10 = f8 - ((f8 - this.bezierEndY) * f7);
                    float f11 = this.bezierP1Z;
                    float f12 = f11 * f7;
                    float f13 = (((f11 - ((f11 - this.bezierEndZ) * f7)) - f12) * f7) + f12;
                    float f14 = ((f10 - f9) * f7) + f9;
                    float f15 = f6 * Y_TEXTURE_STEP;
                    WavePoint wavePoint = new WavePoint(i7);
                    WavePoint wavePoint2 = new WavePoint(i7);
                    waveLine.add(wavePoint);
                    waveLine2.add(wavePoint2);
                    WaveLine waveLine3 = waveLine;
                    WaveLine waveLine4 = waveLine2;
                    int i8 = i2 + 1;
                    this.mVertexData[i2] = wavePoint.setValueX(i2, f2);
                    int i9 = i8 + 1;
                    this.mVertexData[i8] = wavePoint.setValueY(i8, f14);
                    int i10 = i9 + 1;
                    this.mVertexData[i9] = wavePoint.setValueZ(i9, f13);
                    int i11 = i10 + 1;
                    this.mVertexData[i10] = wavePoint.setValueTextureX(i10, f4);
                    int i12 = i11 + 1;
                    this.mVertexData[i11] = wavePoint.setValueTextureY(i11, f15);
                    int i13 = i12 + 1;
                    this.mVertexData[i12] = wavePoint2.setValueX(i12, f3);
                    int i14 = i13 + 1;
                    this.mVertexData[i13] = wavePoint2.setValueY(i13, f14);
                    int i15 = i14 + 1;
                    this.mVertexData[i14] = wavePoint2.setValueZ(i14, f13);
                    int i16 = i15 + 1;
                    this.mVertexData[i15] = wavePoint2.setValueTextureX(i15, f5);
                    this.mVertexData[i16] = wavePoint2.setValueTextureY(i16, f15);
                    i7++;
                    i2 = i16 + 1;
                    waveLine = waveLine3;
                    waveLine2 = waveLine4;
                    i = 20;
                }
                i5 = i6;
            }
        }
    }

    public void updateWaveLine(float currentTime) {
        if (this.mIsClearAndReset) {
            this.mStartTime = currentTime;
        }
        this.mIsClearAndReset = false;
        for (int i = 0; i < this.waveLineList.size(); i++) {
            this.waveLineList.get(i).updateFoot(currentTime - this.mStartTime, this.mVertexData);
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
        for (int i = 0; i < 7; i++) {
            GLES20.glDrawArrays(5, i * 40, 40);
        }
    }
}
