package com.xiaopeng.speech.protocol.node.meter;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface MeterListener extends INodeListener {
    default void onDashboardLightsStatus() {
    }

    default void setLeftCard(int i) {
    }

    default void setRightCard(int i) {
    }
}
