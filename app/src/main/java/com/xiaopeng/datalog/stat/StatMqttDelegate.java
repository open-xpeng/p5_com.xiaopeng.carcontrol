package com.xiaopeng.datalog.stat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.xiaopeng.datalog.StatEvent;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEvent;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IStatEvent;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class StatMqttDelegate extends AbstractStatDelegate implements Handler.Callback {
    private static final int MSG_UPLOAD_CAN = 1;
    private static final int MSG_UPLOAD_CDU = 2;
    private static final int MSG_UPLOAD_CDU_IMMEDIATELY = 3;
    private static final int MSG_UPLOAD_CDU_WITH_FILES = 4;
    private static final int MSG_UPLOAD_FILES = 5;
    private static final int MSG_UPLOAD_LOG_ORIGIN = 6;
    private static final String TAG = "StatMqttDelegate";
    private Handler handler;

    public StatMqttDelegate(Context context) {
        super(context);
        DataCollectorHelper.getInstance().init(context);
        HandlerThread handlerThread = new HandlerThread(TAG, 10);
        handlerThread.start();
        this.handler = new Handler(handlerThread.getLooper(), this);
    }

    @Override // com.xiaopeng.datalog.stat.AbstractStatDelegate
    public void uploadCdu(final IStatEvent iStatEvent) {
        ThreadUtils.post(0, new Runnable() { // from class: com.xiaopeng.datalog.stat.StatMqttDelegate.1
            @Override // java.lang.Runnable
            public void run() {
                LogUtils.d(StatMqttDelegate.TAG, "uploadCdu stat:" + iStatEvent.toString());
                StatMqttDelegate.this.uploadCdu(iStatEvent.toJson());
            }
        });
    }

    @Override // com.xiaopeng.datalog.stat.AbstractStatDelegate
    public void uploadCdu(final IMoleEvent iMoleEvent) {
        ThreadUtils.post(0, new Runnable() { // from class: com.xiaopeng.datalog.stat.StatMqttDelegate.2
            @Override // java.lang.Runnable
            public void run() {
                LogUtils.d(StatMqttDelegate.TAG, "uploadCdu mole:" + iMoleEvent.toString());
                StatMqttDelegate.this.uploadCdu(iMoleEvent.toJson());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadCdu(String str) {
        if (!DataCollectorHelper.getInstance().isConnected()) {
            this.handler.sendMessageDelayed(Message.obtain(this.handler, 2, str), 1000L);
            return;
        }
        this.handler.sendMessage(Message.obtain(this.handler, 2, str));
    }

    @Override // com.xiaopeng.datalog.stat.AbstractStatDelegate
    public void uploadCan(String str) {
        if (!DataCollectorHelper.getInstance().isConnected()) {
            this.handler.sendMessageDelayed(Message.obtain(this.handler, 1, str), 1000L);
            return;
        }
        this.handler.sendMessage(Message.obtain(this.handler, 1, str));
    }

    @Override // com.xiaopeng.datalog.stat.AbstractStatDelegate
    public void uploadLogImmediately(String str, String str2) {
        List asList = Arrays.asList(str, str2);
        if (!DataCollectorHelper.getInstance().isConnected()) {
            this.handler.sendMessageDelayed(Message.obtain(this.handler, 3, asList), 1000L);
        } else {
            this.handler.sendMessage(Message.obtain(this.handler, 3, asList));
        }
    }

    @Override // com.xiaopeng.datalog.stat.AbstractStatDelegate
    public void uploadLogOrigin(String str, String str2) {
        List asList = Arrays.asList(str, str2);
        if (!DataCollectorHelper.getInstance().isConnected()) {
            this.handler.sendMessageDelayed(Message.obtain(this.handler, 6, asList), 1000L);
        } else {
            this.handler.sendMessage(Message.obtain(this.handler, 6, asList));
        }
    }

    @Override // com.xiaopeng.datalog.stat.AbstractStatDelegate
    public void uploadCduWithFiles(IStatEvent iStatEvent, List<String> list) {
        StatEventWithFiles statEventWithFiles = new StatEventWithFiles((StatEvent) iStatEvent, list);
        if (!DataCollectorHelper.getInstance().isConnected()) {
            this.handler.sendMessageDelayed(Message.obtain(this.handler, 4, statEventWithFiles), 1000L);
            return;
        }
        this.handler.sendMessage(Message.obtain(this.handler, 4, statEventWithFiles));
    }

    @Override // com.xiaopeng.datalog.stat.AbstractStatDelegate
    public void uploadFiles(List<String> list) {
        if (!DataCollectorHelper.getInstance().isConnected()) {
            this.handler.sendMessageDelayed(Message.obtain(this.handler, 5, list), 1000L);
            return;
        }
        this.handler.sendMessage(Message.obtain(this.handler, 5, list));
    }

    @Override // com.xiaopeng.datalog.stat.AbstractStatDelegate
    public String uploadRecentSystemLog() {
        return DataCollectorHelper.getInstance().uploadRecentSystemLog();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                DataCollectorHelper.getInstance().uploadCan((String) message.obj);
                break;
            case 2:
                DataCollectorHelper.getInstance().uploadCdu((String) message.obj);
                break;
            case 3:
                List list = (List) message.obj;
                DataCollectorHelper.getInstance().uploadLogImmediately((String) list.get(0), (String) list.get(1));
                break;
            case 4:
                StatEventWithFiles statEventWithFiles = (StatEventWithFiles) message.obj;
                DataCollectorHelper.getInstance().uploadCduWithFiles(statEventWithFiles.event, statEventWithFiles.filePaths);
                break;
            case 5:
                DataCollectorHelper.getInstance().uploadFiles((List) message.obj);
                break;
            case 6:
                List list2 = (List) message.obj;
                DataCollectorHelper.getInstance().uploadLogOrigin((String) list2.get(0), (String) list2.get(1));
                break;
        }
        return true;
    }

    /* loaded from: classes2.dex */
    private class StatEventWithFiles {
        StatEvent event;
        List<String> filePaths;

        public StatEventWithFiles(StatEvent statEvent, List<String> list) {
            this.event = statEvent;
            this.filePaths = list;
        }
    }
}
