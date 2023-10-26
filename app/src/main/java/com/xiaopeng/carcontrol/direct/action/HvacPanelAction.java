package com.xiaopeng.carcontrol.direct.action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.direct.message.DirectHvacEventMsg;
import com.xiaopeng.carcontrol.speech.HvacControlSpeechModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.control.BaseViewControl;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes2.dex */
public class HvacPanelAction implements PageAction {
    @Override // com.xiaopeng.carcontrol.direct.action.PageAction
    public void doAction(Context context, Uri uri) {
        LogUtils.i("HvacPanelAction", "HvacPanelAction:" + uri);
        if (HvacControlSpeechModel.IS_HVAC_PANEL_SHOWING) {
            if (uri == null || !uri.getPath().contains(BaseViewControl.ACTION_SHOW_SEAT_HEAT_VENT_DIRECT)) {
                return;
            }
            DirectHvacEventMsg directHvacEventMsg = new DirectHvacEventMsg();
            directHvacEventMsg.setUrl(uri);
            EventBus.getDefault().post(directHvacEventMsg);
            return;
        }
        Intent intent = new Intent(GlobalConstant.ACTION.ACTION_SHOW_HVAC_PANEL);
        intent.putExtra("from", "speech");
        if (uri != null && uri.getPath().contains(BaseViewControl.ACTION_SHOW_SEAT_HEAT_VENT_DIRECT)) {
            intent.putExtra(GlobalConstant.EXTRA.KEY_SHOW_SEAT_HEAT_DIALOG, true);
        }
        intent.setFlags(270532608);
        context.startActivity(intent);
    }
}
