package com.xiaopeng.carcontrol.direct.action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaopeng.carcontrol.view.dialog.panel.ControlPanelManager;
import com.xiaopeng.lludancemanager.view.LluDanceActivityNew;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes2.dex */
public class LluDancePageAction implements PageAction {
    @Override // com.xiaopeng.carcontrol.direct.action.PageAction
    public void doAction(Context context, Uri uri) {
        Intent intent = new Intent(context, LluDanceActivityNew.class);
        intent.addFlags(ClientDefaults.MAX_MSG_SIZE);
        context.startActivity(intent);
        ControlPanelManager.getInstance().dismissAllPanels();
    }
}
