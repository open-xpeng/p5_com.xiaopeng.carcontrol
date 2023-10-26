package com.xiaopeng.carcontrol.carmanager.ambient;

import com.xiaopeng.xuimanager.ambient.Rgb;

/* loaded from: classes.dex */
public class AmbientColor {
    public boolean hasRgb;
    public int predef;
    public Rgb rgb;

    public AmbientColor(int predef) {
        this.hasRgb = false;
        this.predef = -1;
        this.rgb = null;
        this.predef = predef;
    }

    public AmbientColor(int red, int green, int blue) {
        this.hasRgb = false;
        this.predef = -1;
        this.rgb = null;
        this.hasRgb = true;
        this.rgb = new Rgb(red, green, blue);
    }

    public AmbientColor(boolean hasRgb, String hex) {
        this.hasRgb = false;
        this.predef = -1;
        this.rgb = null;
        this.hasRgb = hasRgb;
        if (hasRgb) {
            this.rgb = new Rgb(hex);
        } else {
            this.predef = Integer.valueOf(hex, 16).intValue();
        }
    }

    public String toHexString() {
        return this.hasRgb ? this.rgb.toHexString() : Integer.toHexString(this.predef);
    }

    public String toString() {
        return toHexString();
    }
}
