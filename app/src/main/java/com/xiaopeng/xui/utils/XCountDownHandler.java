package com.xiaopeng.xui.utils;

import android.os.Handler;
import android.os.Message;

@Deprecated
/* loaded from: classes2.dex */
public class XCountDownHandler extends Handler {
    private int count;
    private int index;
    private CallBack mCallBack;
    private String mTag;

    /* loaded from: classes2.dex */
    public interface CallBack {
        void onCountDown(String str, int i, int i2);

        void onCountDownOver(String str);

        void onCountDownStop(String str);
    }

    public XCountDownHandler(CallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        CallBack callBack;
        CallBack callBack2 = this.mCallBack;
        if (callBack2 != null) {
            callBack2.onCountDown(this.mTag, this.count, this.index);
        }
        if (this.index == 0 && (callBack = this.mCallBack) != null) {
            callBack.onCountDownOver(this.mTag);
        }
        int i = this.index;
        if (i > 0) {
            this.index = i - 1;
            sendEmptyMessageDelayed(0, 1000L);
        }
    }

    public void start(int i) {
        start(null, i);
    }

    public void start(String str, int i) {
        this.mTag = str;
        stop();
        this.count = i;
        this.index = i;
        sendEmptyMessage(0);
    }

    public void stop() {
        removeMessages(0);
        CallBack callBack = this.mCallBack;
        if (callBack != null) {
            callBack.onCountDownStop(this.mTag);
        }
    }
}
