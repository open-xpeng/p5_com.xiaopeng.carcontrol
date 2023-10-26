package com.xiaopeng.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public class SendBroadCastUtil {
    private static final String TAG = "SendBroadCastUtil";
    private static SendBroadCastUtil mUtil;
    private WeakReference<Context> mContext;

    private SendBroadCastUtil(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public static synchronized SendBroadCastUtil getInstance(Context context) {
        SendBroadCastUtil sendBroadCastUtil;
        synchronized (SendBroadCastUtil.class) {
            SendBroadCastUtil sendBroadCastUtil2 = mUtil;
            if (sendBroadCastUtil2 == null || !sendBroadCastUtil2.isContextAlive()) {
                mUtil = new SendBroadCastUtil(context);
            }
            sendBroadCastUtil = mUtil;
        }
        return sendBroadCastUtil;
    }

    private boolean isContextAlive() {
        WeakReference<Context> weakReference = this.mContext;
        return (weakReference == null || weakReference.get() == null) ? false : true;
    }

    public void sendBroadCast(String str) {
        LogUtils.i(TAG, "action:" + str);
        Intent intent = new Intent();
        intent.setAction(str);
        Context context = this.mContext.get();
        if (context != null) {
            context.sendBroadcast(intent);
        }
    }

    public void sendBroadCast(String str, String str2, int i) {
        LogUtils.i(TAG, "action:" + str + ",data[key:" + str2 + ",value:" + i + "]");
        Intent intent = new Intent();
        intent.setAction(str);
        intent.putExtra(str2, i);
        Context context = this.mContext.get();
        if (context != null) {
            context.sendBroadcast(intent);
        }
    }

    public void sendBroadCast(String str, String str2, String str3) {
        LogUtils.i(TAG, "action:" + str + ",data[key:" + str2 + ",value:" + str3 + "]");
        Intent intent = new Intent();
        intent.setAction(str);
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            intent.putExtra(str2, str3);
        }
        Context context = this.mContext.get();
        if (context != null) {
            context.sendBroadcast(intent);
        }
    }

    public void sendBroadCast(String str, Bundle bundle) {
        LogUtils.i(TAG, "action:" + str);
        Intent intent = new Intent();
        intent.setAction(str);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        Context context = this.mContext.get();
        if (context != null) {
            context.sendBroadcast(intent);
        }
    }

    public void sendBroadCast(final String str, final String str2, final String str3, long j) {
        LogUtils.i(TAG, "延迟" + j + "毫秒广播");
        new Timer().schedule(new TimerTask() { // from class: com.xiaopeng.lib.utils.SendBroadCastUtil.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                SendBroadCastUtil.this.sendBroadCast(str, str2, str3);
            }
        }, j);
    }
}
