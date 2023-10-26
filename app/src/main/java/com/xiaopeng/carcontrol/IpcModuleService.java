package com.xiaopeng.carcontrol;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.helper.PushedMessageHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lib.framework.ipcmodule.IpcModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes.dex */
public class IpcModuleService extends Service {
    private static final String MSG_KEY = "string_msg";
    private static final String TAG = "IpcModuleService";

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void init() {
        Module.register(IpcModuleEntry.class, new IpcModuleEntry(MainApp.getInstance()));
        ((IIpcService) Module.get(IpcModuleEntry.class).get(IIpcService.class)).init();
        MainApp.getInstance().startService(new Intent(MainApp.getInstance(), IpcModuleService.class));
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(IIpcService.IpcMessageEvent event) {
        String senderPackageName = event.getSenderPackageName();
        int msgID = event.getMsgID();
        LogUtils.i(TAG, "packageName: " + senderPackageName + "id: " + msgID);
        if (msgID == 10010) {
            Bundle payloadData = event.getPayloadData();
            if (payloadData == null) {
                LogUtils.w(TAG, "PayloadData is empty!");
                return;
            }
            String string = payloadData.getString("string_msg");
            if (TextUtils.isEmpty(string)) {
                LogUtils.w(TAG, "Content is empty!");
                return;
            }
            LogUtils.i(TAG, "payloadData, content: " + string);
            PushedMessageHelper.receivePushMessageByIpc(string);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
