package com.xiaopeng.speech.vui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.utils.LogUtils;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class VuiBroadCastReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        IBinder binder;
        String action = intent.getAction();
        LogUtils.i("VuiBroadCastReceiver", "onReceive:" + action);
        if ("carspeechservice.SpeechServer.Start".contains(action)) {
            VuiSceneManager.instance().setInSpeech(false);
            VuiSceneManager.instance().sendBroadCast();
            VuiSceneManager.instance().subscribe(true);
            VuiSceneManager.instance().updateListIndexState();
        } else if (VuiConstants.INTENT_ACTION_ENV_CHANGED.contains(action)) {
            VuiSceneManager.instance().setInSpeech(false);
            VuiSceneManager.instance().handleAllSceneCache(false);
            VuiSceneManager.instance().handleSceneDataInfo();
        } else if (VuiConstants.INTENT_ACTION_VUIPROVIDER_DEATH.contains(action)) {
            Bundle bundleExtra = intent.getBundleExtra("bundle");
            if (bundleExtra == null || (binder = bundleExtra.getBinder("client")) == null) {
                return;
            }
            try {
                binder.linkToDeath(new VuiAppDeathRecipient(binder), 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (VuiConstants.INTENT_ACTION_LOCAL_SCENE_NOT_FOUND.contains(action)) {
            ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("scene_id");
            LogUtils.d("VuiBroadCastReceiver", "接收到resourcemanager获取失败 sceneIds = " + stringArrayListExtra);
            if (stringArrayListExtra == null || stringArrayListExtra.isEmpty()) {
                return;
            }
            for (String str : stringArrayListExtra) {
                VuiSceneManager.instance().reBuildSceneToVuiProvider(str);
            }
        }
    }
}
