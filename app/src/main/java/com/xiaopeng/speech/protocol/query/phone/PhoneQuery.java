package com.xiaopeng.speech.protocol.query.phone;

import com.xiaopeng.speech.SpeechQuery;

/* loaded from: classes2.dex */
public class PhoneQuery extends SpeechQuery<IPhoneQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getGuiPageOpenState(String str, String str2) {
        return ((IPhoneQueryCaller) this.mQueryCaller).getGuiPageOpenState(str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean onQueryBluetooth(String str, String str2) {
        return ((IPhoneQueryCaller) this.mQueryCaller).onQueryBluetooth();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onQueryContactSyncStatus(String str, String str2) {
        return ((IPhoneQueryCaller) this.mQueryCaller).onQueryContactSyncStatus();
    }
}
