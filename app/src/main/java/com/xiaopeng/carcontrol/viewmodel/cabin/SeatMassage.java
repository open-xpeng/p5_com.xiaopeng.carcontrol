package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public class SeatMassage {
    private String effect;
    private final int index;
    private int intensity;
    private boolean running;

    public SeatMassage(int index, String effect, int intensity, boolean running) {
        this.index = index;
        this.effect = effect;
        this.intensity = intensity;
        this.running = running;
    }

    public int getIndex() {
        return this.index;
    }

    public String getEffect() {
        return this.effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
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
        return "SeatMassage{index=" + this.index + ", effect='" + this.effect + "', intensity=" + this.intensity + ", running=" + this.running + '}';
    }
}
