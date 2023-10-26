package com.xiaopeng.carcontrol.viewmodel.cabin;

/* loaded from: classes2.dex */
public enum MirrorFoldState {
    Folded,
    Middle,
    Unfolded;

    public static MirrorFoldState fromValue(int foldState) {
        if (foldState != 0) {
            if (foldState == 1) {
                return Middle;
            }
            return Unfolded;
        }
        return Folded;
    }
}
