package com.xiaopeng.lib.framework.aiassistantmodule.interactive;

import android.os.Bundle;
import android.util.Log;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaopeng.lib.framework.ipcmodule.IpcModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsg;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgBuilder;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgService;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.lib.utils.ThreadUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes2.dex */
public class InteractiveMsgService implements IInteractiveMsgService {
    private static final String TAG = "InteractiveMsgService";
    private IIpcService mIpcService = (IIpcService) Module.get(IpcModuleEntry.class).get(IIpcService.class);

    public InteractiveMsgService() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(IIpcService.IpcMessageEvent ipcMessageEvent) {
        if (ipcMessageEvent != null && ipcMessageEvent.getMsgID() == 11012 && "com.xiaopeng.aiassistant".equals(ipcMessageEvent.getSenderPackageName())) {
            InteractiveMsg from = InteractiveMsg.from(ipcMessageEvent.getPayloadData());
            if (from.getMsgId() == 3) {
                try {
                    JsonObject asJsonObject = new JsonParser().parse(from.getData()).getAsJsonObject();
                    EventBus.getDefault().post(new IInteractiveMsgService.SpeakEndEvent(asJsonObject.get("id").getAsString(), asJsonObject.get(Constants.END_STATE).getAsInt()));
                } catch (Exception e) {
                    Log.e(TAG, "onEvent", e);
                }
            } else if (from.getMsgId() >= 100) {
                EventBus.getDefault().post(new IInteractiveMsgService.InteractiveMsgEvent(from));
            }
        }
    }

    public void sendMessage(int i, String str) {
        final Bundle bundle = new Bundle();
        bundle.putString("data", str);
        bundle.putInt(Constants.MSG_ID, i);
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.framework.aiassistantmodule.interactive.InteractiveMsgService.1
            @Override // java.lang.Runnable
            public void run() {
                InteractiveMsgService.this.mIpcService.sendData(11012, bundle, "com.xiaopeng.aiassistant");
            }
        });
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgService
    public void sendMessage(IInteractiveMsg iInteractiveMsg) {
        if (iInteractiveMsg.getMsgId() < 100) {
            throw new RuntimeException("MsgId is illegal. (" + iInteractiveMsg.getMsgId() + ") is not >= 100");
        }
        sendMessage(iInteractiveMsg.getMsgId(), iInteractiveMsg.getData());
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgService
    public void speak(String str, String str2) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", str);
        jsonObject.addProperty("id", str2);
        sendMessage(2, jsonObject.toString());
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgService
    public void shutup(String str) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", str);
        sendMessage(4, jsonObject.toString());
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgService
    public void close() {
        sendMessage(1, null);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgService
    public IInteractiveMsgBuilder interactiveMsgBuilder() {
        return new InteractiveMsgBuilder();
    }
}
