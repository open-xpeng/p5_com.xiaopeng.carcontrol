package com.xiaopeng.carcontrol.view.control.hvac.vent;

/* loaded from: classes2.dex */
public class WavePoint {
    private int mIndexTextureX;
    private int mIndexTextureY;
    private int mIndexX;
    private int mIndexY;
    private int mIndexZ;
    private int mPointIndex;
    private float mValueTextureX;
    private float mValueTextureY;
    private float mValueX;
    private float mValueY;
    private float mValueZ;

    public WavePoint(int pointIndex) {
        this.mPointIndex = pointIndex;
    }

    public float setValueX(int index, float value) {
        this.mIndexX = index;
        this.mValueX = value;
        return value;
    }

    public float getValueX() {
        return this.mValueX;
    }

    public float setValueY(int index, float value) {
        this.mIndexY = index;
        this.mValueY = value;
        return value;
    }

    public float getValueY() {
        return this.mValueY;
    }

    public int getIndexY() {
        return this.mIndexY;
    }

    public float setValueZ(int index, float value) {
        this.mIndexZ = index;
        this.mValueZ = value;
        return value;
    }

    public int getIndexZ() {
        return this.mIndexZ;
    }

    public float getValueZ() {
        return this.mValueZ;
    }

    public float setValueTextureX(int index, float value) {
        this.mIndexTextureX = index;
        this.mValueTextureX = value;
        return value;
    }

    public float getValueTextureX() {
        return this.mValueTextureX;
    }

    public float setValueTextureY(int index, float value) {
        this.mIndexTextureY = index;
        this.mValueTextureY = value;
        return value;
    }

    public float getValueTextureY() {
        return this.mValueTextureY;
    }

    public int getPointIndex() {
        return this.mPointIndex;
    }
}
