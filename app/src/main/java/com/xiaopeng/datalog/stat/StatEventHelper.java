package com.xiaopeng.datalog.stat;

import android.content.Context;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEvent;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IStatEvent;
import java.util.List;

/* loaded from: classes2.dex */
public class StatEventHelper {
    private static final String TAG = "StatEventHelper";
    private static volatile StatEventHelper sInstance;
    private AbstractStatDelegate mStatDelegate;

    private StatEventHelper(Context context) {
        this.mStatDelegate = new StatMqttDelegate(context);
    }

    public static void init(Context context) {
        sInstance = new StatEventHelper(context);
    }

    public static StatEventHelper getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("has not init the StatEventHeler");
        }
        return sInstance;
    }

    public void uploadCdu(IStatEvent iStatEvent) {
        this.mStatDelegate.uploadCdu(iStatEvent);
    }

    public void uploadCdu(IMoleEvent iMoleEvent) {
        this.mStatDelegate.uploadCdu(iMoleEvent);
    }

    public void uploadCan(String str) {
        this.mStatDelegate.uploadCan(str);
    }

    public void uploadLogImmediately(String str, String str2) {
        this.mStatDelegate.uploadLogImmediately(str, str2);
    }

    public void uploadCduWithFiles(IStatEvent iStatEvent, List<String> list) {
        this.mStatDelegate.uploadCduWithFiles(iStatEvent, list);
    }

    public void uploadFiles(List<String> list) {
        this.mStatDelegate.uploadFiles(list);
    }

    public String uploadRecentSystemLog() {
        return this.mStatDelegate.uploadRecentSystemLog();
    }

    public void uploadLogOrigin(String str, String str2) {
        this.mStatDelegate.uploadLogOrigin(str, str2);
    }
}
