package com.xiaopeng.carcontrol.view.control.hvac.vent;

/* loaded from: classes2.dex */
public class Wave {
    private static final String TAG = "Wave";
    private float mCurrentIndex;
    private float mMaxIndex;
    private float mSpeed;
    private float mStartIndex;
    private float mStartTime;
    private float mWaveCrest;
    private float mWaveLength;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Wave(float startIndex, float speed, float maxIndex, float waveLength) {
        this.mSpeed = speed;
        this.mMaxIndex = maxIndex;
        this.mWaveLength = waveLength;
        this.mStartIndex = startIndex;
        this.mCurrentIndex = startIndex;
    }

    public void setSpeed(float speed) {
        this.mSpeed = speed;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void reset(float startTime) {
        this.mStartTime = startTime;
        this.mCurrentIndex = this.mStartIndex;
        this.mWaveCrest = (((float) Math.random()) * 0.003f) + 0.002f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void update(float currentTime) {
        this.mCurrentIndex = this.mStartIndex + ((currentTime - this.mStartTime) * this.mSpeed);
    }

    public float getCurrentIndex() {
        return this.mCurrentIndex;
    }

    public float getWaveCrest() {
        return this.mWaveCrest;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isOut() {
        return this.mCurrentIndex >= this.mMaxIndex;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isPointIn(WavePoint wavePoint) {
        float pointIndex = wavePoint.getPointIndex();
        float f = this.mCurrentIndex;
        return pointIndex >= f && pointIndex < f + this.mWaveLength;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getPointY(WavePoint wavePoint) {
        float valueY = wavePoint.getValueY();
        int pointIndex = wavePoint.getPointIndex();
        float f = pointIndex;
        float f2 = f - this.mCurrentIndex;
        if (pointIndex == 0) {
            f2 = 0.0f;
        }
        float f3 = (f2 * 3.1415927f) / this.mWaveLength;
        float f4 = this.mWaveCrest;
        return valueY + (((float) Math.sin(f3)) * (f4 + ((f4 / 5.0f) * f)));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getAutoPointY(WavePoint wavePoint) {
        return wavePoint.getValueY() + (((float) Math.sin(((wavePoint.getPointIndex() - this.mCurrentIndex) * 3.1415927f) / this.mWaveLength)) * this.mWaveCrest * 30.0f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getPointZ(WavePoint wavePoint) {
        float valueZ = wavePoint.getValueZ();
        int pointIndex = wavePoint.getPointIndex();
        float f = pointIndex;
        float f2 = f - this.mCurrentIndex;
        if (pointIndex < 2) {
            f2 = 0.0f;
        }
        float f3 = this.mWaveCrest * 3.0f;
        return valueZ + (((float) Math.sin((f2 * 3.1415927f) / this.mWaveLength)) * (f3 + ((f3 / 5.0f) * f)) * 5.0f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getFootPointY(WavePoint wavePoint) {
        float valueY = wavePoint.getValueY();
        int pointIndex = wavePoint.getPointIndex();
        float f = pointIndex;
        float f2 = f - this.mCurrentIndex;
        if (pointIndex == 0) {
            f2 = 0.0f;
        }
        float f3 = (f2 * 3.1415927f) / this.mWaveLength;
        float f4 = this.mWaveCrest;
        return valueY + (((float) Math.sin(f3)) * (f4 + ((f4 / 5.0f) * f)) * 1.3f);
    }
}
