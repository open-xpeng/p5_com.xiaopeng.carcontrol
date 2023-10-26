package com.xiaopeng.carcontrol.bean.xpilot;

/* loaded from: classes.dex */
public class XPilotEmptyCardItem extends XPilotItem<Boolean> {
    public XPilotEmptyCardItem(int index, String key, int titleResId, int descResId, int drawableResId, int manualIdx) {
        this(index, key, titleResId, descResId, drawableResId, manualIdx, 0);
    }

    public XPilotEmptyCardItem(int index, String key, int titleResId, int descResId, int drawableResId, int manualIdx, int keywordId) {
        super(index, key, 6, titleResId, descResId, drawableResId, manualIdx, keywordId);
    }
}
