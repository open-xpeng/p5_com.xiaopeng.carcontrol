package com.xiaopeng.lib.framework.moduleinterface.ipcmodule;

import android.os.Bundle;

/* loaded from: classes2.dex */
public interface IIpcService {
    void init();

    void sendData(int i, Bundle bundle, String... strArr);

    /* loaded from: classes2.dex */
    public static class IpcMessageEvent {
        int mMsgID;
        Bundle mPayloadData;
        String mSenderPackageName;

        public String getSenderPackageName() {
            return this.mSenderPackageName;
        }

        public void setSenderPackageName(String str) {
            this.mSenderPackageName = str;
        }

        public int getMsgID() {
            return this.mMsgID;
        }

        public void setMsgID(int i) {
            this.mMsgID = i;
        }

        public Bundle getPayloadData() {
            return this.mPayloadData;
        }

        public void setPayloadData(Bundle bundle) {
            this.mPayloadData = bundle;
        }

        public String toString() {
            return "IpcMessageEvent{mSenderPackageName='" + this.mSenderPackageName + "', mMsgID=" + this.mMsgID + ", mPayloadData=" + this.mPayloadData + '}';
        }
    }
}
