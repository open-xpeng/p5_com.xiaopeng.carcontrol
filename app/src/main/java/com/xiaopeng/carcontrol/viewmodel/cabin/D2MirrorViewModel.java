package com.xiaopeng.carcontrol.viewmodel.cabin;

import com.xiaopeng.lib.bughunter.anr.UILooperObserver;

/* loaded from: classes2.dex */
public class D2MirrorViewModel extends MirrorViewModel {
    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected long getMirrorWaitTimeout() {
        return UILooperObserver.ANR_TRIGGER_TIME;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleMirrorAutoDownChanged(boolean enabled) {
        this.mMirrorAutoDownData.postValue(Boolean.valueOf(enabled));
    }
}
