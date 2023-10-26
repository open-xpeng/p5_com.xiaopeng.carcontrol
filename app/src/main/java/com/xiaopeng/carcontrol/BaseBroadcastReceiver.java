package com.xiaopeng.carcontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioSmartControl;
import com.xiaopeng.carcontrol.viewmodel.selfcheck.SelfCheckUtil;
import com.xiaopeng.util.FeatureOption;

/* loaded from: classes.dex */
public class BaseBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BaseBroadcastReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        LogUtils.d(TAG, "action=" + intent.getAction(), false);
        String action = intent.getAction();
        Intent intent2 = null;
        action.hashCode();
        char c = 65535;
        switch (action.hashCode()) {
            case -1944263263:
                if (action.equals(GlobalConstant.ACTION.ACTION_BOSSKEY_USER)) {
                    c = 0;
                    break;
                }
                break;
            case -1398145583:
                if (action.equals("com.xiaopeng.xui.userscenario")) {
                    c = 1;
                    break;
                }
                break;
            case -1201244584:
                if (action.equals(GlobalConstant.ACTION.ACTION_BOSSKEY)) {
                    c = 2;
                    break;
                }
                break;
            case -495195386:
                if (action.equals(GlobalConstant.ACTION.ACTION_GREET_QUIT)) {
                    c = 3;
                    break;
                }
                break;
            case -102362148:
                if (action.equals(GlobalConstant.ACTION.ACTION_GREET_TTS_COMPLETE)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                Intent intent3 = new Intent(context, CarControlService.class);
                intent3.setAction(GlobalConstant.ACTION.ACTION_SHOW_CUSTOM_DOOR_KEY_PANEL);
                App.getInstance().startService(intent3);
                break;
            case 1:
                ScenarioSmartControl.getInstance().parseScenarioIntent(intent);
                break;
            case 2:
                String stringExtra = intent.getStringExtra(GlobalConstant.EXTRA.KEY_KEY_TYPE);
                int intExtra = intent.getIntExtra(GlobalConstant.EXTRA.KEY_KEY_FUNC, 0);
                LogUtils.d(TAG, "keyType: " + stringExtra + ", keyFunc: " + intExtra);
                if (stringExtra.equals(GlobalConstant.EXTRA.VALUE_X_SHORT) && intExtra == 0) {
                    NotificationHelper.getInstance().showToast(com.xiaopeng.carcontrolmodule.R.string.custom_door_key_no_setting);
                    break;
                }
                break;
            case 3:
                if (BaseFeatureOption.getInstance().isSupportSelfCheck() && !BaseFeatureOption.getInstance().isSupportNewSelfCheckArch()) {
                    SelfCheckUtil.stopCheck();
                    break;
                }
                break;
            case 4:
                if (BaseFeatureOption.getInstance().isSupportSelfCheck()) {
                    if (BaseFeatureOption.getInstance().isSupportNewSelfCheckArch()) {
                        SelfCheckUtil.receiveGerrt();
                        break;
                    } else {
                        SelfCheckUtil.startCheck();
                        break;
                    }
                }
                break;
            default:
                intent2 = new Intent(context, CarControlService.class);
                if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                    intent2.setAction(GlobalConstant.ACTION.ACTION_START_SERVICE);
                    break;
                } else {
                    intent2.setAction(action);
                    break;
                }
        }
        if (intent2 != null) {
            intent2.setPackage(context.getPackageName());
            intent2.putExtras(intent);
            context.startService(intent2);
        }
    }

    protected boolean shouldNotPreloadUnity() {
        return FeatureOption.FO_BOOT_POLICY_ENABLED;
    }
}
