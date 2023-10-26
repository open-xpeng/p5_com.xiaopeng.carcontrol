package com.xiaopeng.carcontrol.view.control.hvac.vent;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class WaveLine {
    private static final int MIN_WIND_SPEED = 11;
    private static int SPEED = 11;
    private static final String TAG = "WaveLine";
    private static int WAVE_LENGTH = 10;
    private static float WAVE_TIME = 10 / 11;
    private float mLastAddTime;
    private int mPointCount;
    private List<Wave> waveList = new ArrayList();
    private List<WavePoint> mWavePointList = new ArrayList();
    private float mTimeOffset = ((float) Math.random()) * WAVE_TIME;

    /* JADX INFO: Access modifiers changed from: package-private */
    public WaveLine(int pointCount) {
        this.mPointCount = pointCount;
    }

    public void add(WavePoint wavePoint) {
        this.mWavePointList.add(wavePoint);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void update(float currentTime, float[] vertexData) {
        float f = currentTime - this.mTimeOffset;
        if (f - this.mLastAddTime >= WAVE_TIME) {
            this.mLastAddTime = (((float) Math.random()) * WAVE_TIME) + f;
            getOutWave().reset(f);
        }
        for (int i = 0; i < this.waveList.size(); i++) {
            Wave wave = this.waveList.get(i);
            if (!wave.isOut()) {
                wave.update(f);
                for (int i2 = 0; i2 < this.mWavePointList.size(); i2++) {
                    WavePoint wavePoint = this.mWavePointList.get(i2);
                    if (wave.isPointIn(wavePoint)) {
                        vertexData[wavePoint.getIndexY()] = wave.getPointY(wavePoint);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateWindow(float currentTime, float[] vertexData) {
        float f = currentTime - this.mTimeOffset;
        if (f - this.mLastAddTime >= WAVE_TIME) {
            this.mLastAddTime = (((float) Math.random()) * WAVE_TIME) + f;
            getOutWave().reset(f);
        }
        for (int i = 0; i < this.waveList.size(); i++) {
            Wave wave = this.waveList.get(i);
            if (!wave.isOut()) {
                wave.update(f);
                for (int i2 = 0; i2 < this.mWavePointList.size(); i2++) {
                    WavePoint wavePoint = this.mWavePointList.get(i2);
                    if (wave.isPointIn(wavePoint)) {
                        vertexData[wavePoint.getIndexZ()] = wave.getPointZ(wavePoint);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateAuto(float currentTime, float[] vertexData, float offset) {
        float f = currentTime - offset;
        if (f - this.mLastAddTime >= WAVE_TIME) {
            this.mLastAddTime = f;
            getOutWave().reset(f);
        }
        for (int i = 0; i < this.waveList.size(); i++) {
            Wave wave = this.waveList.get(i);
            if (!wave.isOut()) {
                wave.update(f);
                for (int i2 = 0; i2 < this.mWavePointList.size(); i2++) {
                    WavePoint wavePoint = this.mWavePointList.get(i2);
                    if (wave.isPointIn(wavePoint)) {
                        vertexData[wavePoint.getIndexY()] = wave.getAutoPointY(wavePoint);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateFoot(float currentTime, float[] vertexData) {
        float f = currentTime - this.mTimeOffset;
        if (f - this.mLastAddTime >= WAVE_TIME) {
            this.mLastAddTime = (((float) Math.random()) * WAVE_TIME) + f;
            getOutWave().reset(f);
        }
        for (int i = 0; i < this.waveList.size(); i++) {
            Wave wave = this.waveList.get(i);
            if (!wave.isOut()) {
                wave.update(f);
                for (int i2 = 0; i2 < this.mWavePointList.size(); i2++) {
                    WavePoint wavePoint = this.mWavePointList.get(i2);
                    if (wave.isPointIn(wavePoint)) {
                        vertexData[wavePoint.getIndexY()] = wave.getFootPointY(wavePoint);
                    }
                }
            }
        }
    }

    public WavePoint getPointByIndex(int index) {
        for (int i = 0; i < this.mWavePointList.size(); i++) {
            if (this.mWavePointList.get(i).getPointIndex() == index) {
                return this.mWavePointList.get(i);
            }
        }
        return null;
    }

    public void setSpeed(int speed) {
        SPEED = speed + 11;
    }

    private Wave getOutWave() {
        for (int i = 0; i < this.waveList.size(); i++) {
            Wave wave = this.waveList.get(i);
            if (wave.isOut()) {
                wave.setSpeed(SPEED);
                return wave;
            }
        }
        int i2 = WAVE_LENGTH;
        Wave wave2 = new Wave(-i2, SPEED, this.mPointCount - 1, i2);
        this.waveList.add(wave2);
        return wave2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean resetAndClearWave(float[] vertexData) {
        this.mLastAddTime = 0.0f;
        List<Wave> list = this.waveList;
        if (list == null || list.size() <= 0) {
            return false;
        }
        this.waveList.clear();
        for (int i = 0; i < this.mWavePointList.size(); i++) {
            WavePoint wavePoint = this.mWavePointList.get(i);
            vertexData[wavePoint.getIndexY()] = wavePoint.getValueY();
            vertexData[wavePoint.getIndexZ()] = wavePoint.getValueZ();
        }
        return true;
    }
}
