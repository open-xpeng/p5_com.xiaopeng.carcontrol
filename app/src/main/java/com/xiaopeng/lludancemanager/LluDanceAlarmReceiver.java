package com.xiaopeng.lludancemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lludancemanager.helper.LluDanceAlarmHelper;
import com.xiaopeng.lludancemanager.view.LluDanceActivityNew;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes2.dex */
public class LluDanceAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "LluDanceAlarmReceiver";
    private boolean mIsSuperPanel = false;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String str = TAG;
        LogUtils.d(str, "llu dance alarm receiver, action = " + intent.getAction() + "    receiver instance = " + this + "    is super panel = " + this.mIsSuperPanel);
        if (LluDanceAlarmHelper.DANCE_ORDER_TIME_ALARM_ACTION.equals(intent.getAction())) {
            LluDanceAlarmHelper.cancelAlarmForDance(context);
            if (!this.mIsSuperPanel) {
                if (((LluViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluViewModel.class)).isLLuSwEnabled()) {
                    if (LluDanceHelper.checkLightDanceAvailable()) {
                        NotificationHelper.getInstance().showToast(R.string.llu_dance_fragment_order_time_out_toast);
                        String string = intent.getExtras().getString(LluDanceAlarmHelper.ORDER_PLAY_DANCE_ITEM_RES_ID);
                        intent.getExtras().getString(LluDanceAlarmHelper.ORDER_PLAY_DANCE_ITEM_RES_EFFECT_NAME);
                        LogUtils.d(str, "Karl log open time optimize llu dance alarm receiver, orderedItemId = " + string);
                        Intent intent2 = new Intent(context, LluDanceActivityNew.class);
                        intent2.setAction(LluDanceActivityNew.LLU_DANCE_ORDER_PLAY_ACTION);
                        intent2.setFlags(ClientDefaults.MAX_MSG_SIZE);
                        intent2.putExtra(LluDanceAlarmHelper.ORDER_PLAY_DANCE_ITEM_RES_ID, string);
                        context.startActivity(intent2);
                        return;
                    }
                    return;
                }
                NotificationHelper.getInstance().showToast(R.string.llu_effect_sw_disable);
                return;
            }
            LogUtils.d(str, "llu dance alarm order play time out, but in super panel model ignore event");
            NotificationHelper.getInstance().showToast(R.string.llu_dance_fragment_order_time_out_not_suit);
        } else if (LluDanceAlarmHelper.ACTIVITY_CHANGED_RECEIVER_ACTION.equals(intent.getAction())) {
            this.mIsSuperPanel = intent.getIntExtra(LluDanceAlarmHelper.EXTRA_WINDOW_LEVEL, 0) > 0;
            LogUtils.d(str, "activity changed , is super panel = " + this.mIsSuperPanel);
        }
    }
}
