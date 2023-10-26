package com.xiaopeng.carcontrol.direct.action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.direct.DxElementDirect;
import com.xiaopeng.carcontrol.direct.message.DirectEventMsg;
import com.xiaopeng.carcontrol.view.dialog.panel.ControlPanelManager;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes2.dex */
public class NormalPageAction implements PageAction {
    @Override // com.xiaopeng.carcontrol.direct.action.PageAction
    public void doAction(Context context, Uri uri) {
        if (!CarBaseConfig.getInstance().isSupportUnity3D()) {
            if (DxElementDirect.getInstance().isMainActivityResumed()) {
                DirectEventMsg directEventMsg = new DirectEventMsg();
                directEventMsg.setUrl(uri);
                EventBus.getDefault().post(directEventMsg);
            } else {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(ClientDefaults.MAX_MSG_SIZE);
                intent.setData(uri);
                context.startActivity(intent);
            }
        }
        ControlPanelManager.getInstance().dismissAllPanels();
    }
}
