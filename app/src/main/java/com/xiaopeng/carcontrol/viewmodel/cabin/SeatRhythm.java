package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public class SeatRhythm {
    private final int index;
    private int intensity;
    private int mode;
    private boolean running;

    public SeatRhythm(int index, int mode, int intensity, boolean running) {
        this.index = index;
        this.mode = mode;
        this.intensity = intensity;
        this.running = running;
    }

    public int getIndex() {
        return this.index;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getIntensity() {
        return this.intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String toString() {
        return "SeatRhythm{index=" + this.index + ", mode=" + this.mode + ", intensity=" + this.intensity + ", running=" + this.running + '}';
    }
}
