package com.xiaopeng.speech.vui.event;

import android.content.Context;
import androidx.recyclerview.widget.LinearSmoothScroller;

/* loaded from: classes2.dex */
public class EndSmoothScroller extends LinearSmoothScroller {
    @Override // androidx.recyclerview.widget.LinearSmoothScroller
    protected int getHorizontalSnapPreference() {
        return 1;
    }

    @Override // androidx.recyclerview.widget.LinearSmoothScroller
    protected int getVerticalSnapPreference() {
        return 1;
    }

    public EndSmoothScroller(Context context) {
        super(context);
    }
}
