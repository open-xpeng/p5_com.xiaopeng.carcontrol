package com.xiaopeng.lib.framework.aiassistantmodule.notification;

import android.content.Context;
import android.os.Bundle;
import com.xiaopeng.lib.framework.ipcmodule.IpcModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.notification.INotification;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.notification.INotificationCallback;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes2.dex */
public class AINotification implements INotification {
    private static final String TAG = "AINotification";
    private IIpcService mIIpcService;
    private INotificationCallback mINotificationCallback;

    public AINotification(Context context) {
        Module.register(IpcModuleEntry.class, new IpcModuleEntry(context));
        IIpcService iIpcService = (IIpcService) Module.get(IpcModuleEntry.class).get(IIpcService.class);
        this.mIIpcService = iIpcService;
        iIpcService.init();
        EventBus.getDefault().register(this);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.notification.INotification
    public void send(String str) {
        LogUtils.i(TAG, "send message : " + str);
        Bundle bundle = new Bundle();
        bundle.putString(IpcConfig.IPCKey.STRING_MSG, str);
        this.mIIpcService.sendData(IpcConfig.MessageCenterConfig.IPC_ID_APP_PUSH_MESSAGE, bundle, IpcConfig.App.MESSAGE_CENTER);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.notification.INotification
    public void close(String str) {
        LogUtils.i(TAG, "close message id : " + str);
        Bundle bundle = new Bundle();
        bundle.putString(IpcConfig.IPCKey.STRING_MSG, str);
        this.mIIpcService.sendData(IpcConfig.AIAssistantConfig.IPC_MESSAGE_CLOSE, bundle, "com.xiaopeng.aiassistant");
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.notification.INotification
    public void setOnNotificationCallback(INotificationCallback iNotificationCallback) {
        this.mINotificationCallback = iNotificationCallback;
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(IIpcService.IpcMessageEvent ipcMessageEvent) {
        try {
            if (ipcMessageEvent.getMsgID() == 10011) {
                String string = ipcMessageEvent.getPayloadData().getString(IpcConfig.IPCKey.STRING_MSG);
                LogUtils.i(TAG, "onMessageClick data : " + string);
                INotificationCallback iNotificationCallback = this.mINotificationCallback;
                if (iNotificationCallback != null) {
                    iNotificationCallback.onMessageClick(string);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
