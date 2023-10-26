package com.xiaopeng.speech.protocol.query.personalCenter;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface IPersonalCenterCaller extends IQueryCaller {
    default int getControlProfileHabitNumSupport(int i) {
        return -1;
    }

    default int getControlProfileHabitSupport() {
        return -1;
    }

    default int getGuiPageOpenState(String str) {
        return -1;
    }
}
