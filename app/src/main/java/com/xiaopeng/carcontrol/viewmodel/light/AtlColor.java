package com.xiaopeng.carcontrol.viewmodel.light;

/* loaded from: classes2.dex */
public enum AtlColor {
    Color1,
    Color2,
    Color3,
    Color4,
    Color5,
    Color6,
    Color7,
    Color8,
    Color9,
    Color10,
    Color11,
    Color12,
    Color13,
    Color14,
    Color15,
    Color16,
    Color17,
    Color18,
    Color19,
    Color20;

    public static AtlColor fromAtlStatus(int value) {
        try {
            return values()[value - 1];
        } catch (IndexOutOfBoundsException unused) {
            throw new IllegalArgumentException("Unknown atl color value: " + value);
        }
    }

    public int toAtlCmd() {
        return ordinal() + 1;
    }
}
