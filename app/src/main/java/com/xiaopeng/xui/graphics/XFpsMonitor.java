package com.xiaopeng.xui.graphics;

import com.xiaopeng.xui.utils.XLogUtils;
import java.util.Locale;

/* loaded from: classes2.dex */
public class XFpsMonitor {
    private int currentFps;
    private String currentFpsInfo;
    private float currentTfp;
    private long lastLog;
    private final long logInterval;
    private long start;
    private int sumFrames;
    private long sumTime;
    private String tag;

    public XFpsMonitor() {
        this("XFpsMonitor");
    }

    public XFpsMonitor(String str) {
        this.tag = str;
        this.lastLog = -1L;
        this.logInterval = 1000L;
        this.start = -1L;
        this.currentFpsInfo = "";
    }

    public final void frameStart() {
        if (this.start != -1) {
            throw new RuntimeException("Do you forget to call frameEnd? ");
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.start = currentTimeMillis;
        if (this.lastLog == -1) {
            this.lastLog = currentTimeMillis;
        }
    }

    public final String frameEnd() {
        if (this.start == -1) {
            throw new RuntimeException("Do you forget to call frameStart? ");
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.sumTime += currentTimeMillis - this.start;
        int i = this.sumFrames + 1;
        this.sumFrames = i;
        if (currentTimeMillis - this.lastLog > this.logInterval) {
            this.currentFps = i;
            StringBuilder append = new StringBuilder().append(this.sumFrames).append(" FPS\t\t");
            this.currentTfp = (((float) this.sumTime) * 1.0f) / this.sumFrames;
            String sb = append.append(String.format(Locale.getDefault(), "%.2f", Float.valueOf(this.currentTfp))).append(" ms/f").toString();
            this.currentFpsInfo = sb;
            XLogUtils.d(this.tag, sb);
            this.sumTime = 0L;
            this.sumFrames = 0;
            this.lastLog = currentTimeMillis;
        }
        this.start = -1L;
        return this.currentFpsInfo;
    }

    public final String getCurrentFpsInfo() {
        return this.currentFpsInfo;
    }

    public final float getCurrentTfp() {
        return this.currentTfp;
    }

    public final int getCurrentFps() {
        return this.currentFps;
    }
}
