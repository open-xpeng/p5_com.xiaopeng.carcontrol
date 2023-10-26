package com.xiaopeng.speech.protocol.node.phone;

import com.xiaopeng.speech.protocol.node.phone.bean.PhoneBean;

/* loaded from: classes2.dex */
public abstract class AbsPhoneListener implements PhoneListener {
    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onCallback() {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onCallbackSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onInAccept() {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onInReject() {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onOut(String str, String str2, String str3) {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onOutCustomerservice(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onOutHelp(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onQueryBluetooth() {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onQueryContacts(String str, PhoneBean phoneBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onRedial() {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onRedialSupport() {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onSettingOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.phone.PhoneListener
    public void onSyncContactResult(int i) {
    }
}
