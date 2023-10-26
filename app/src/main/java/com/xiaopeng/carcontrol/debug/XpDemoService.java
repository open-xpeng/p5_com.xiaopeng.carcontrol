package com.xiaopeng.carcontrol.debug;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.direct.ElementDirectManager;
import com.xiaopeng.carcontrol.direct.IElementDirect;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XToast;

/* loaded from: classes2.dex */
public class XpDemoService extends Service {
    private static final String TAG = "XpDemoService";
    private BroadcastReceiver mElementDirectReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.carcontrol.debug.XpDemoService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.d(XpDemoService.TAG, "action: " + action);
            if (GlobalConstant.ACTION.ACTION_DEMO_ELEMENT_DIRECT.equals(action)) {
                String stringExtra = intent.getStringExtra("url");
                IElementDirect elementDirect = ElementDirectManager.getElementDirect();
                if (elementDirect != null) {
                    String convertUrl = elementDirect.convertUrl(stringExtra);
                    if (elementDirect.checkSupport(convertUrl)) {
                        LogUtils.i(XpDemoService.TAG, "getGuiPageOpenState 0 : " + convertUrl, false);
                        elementDirect.showPageDirect(context, convertUrl);
                        return;
                    }
                    XToast.showLong(ResUtils.getString(R.string.demo_service_not_support_direct));
                    LogUtils.w(XpDemoService.TAG, "不支持页面直达 newPageUrl: " + convertUrl, false);
                    return;
                }
                XToast.showLong(ResUtils.getString(R.string.demo_service_not_support_direct));
                LogUtils.w(XpDemoService.TAG, "不支持页面直达: " + stringExtra, false);
            }
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.debug.-$$Lambda$XpDemoService$P73LVsoEIPa7q3b0Vtw2zNX0BPo
            @Override // java.lang.Runnable
            public final void run() {
                XpDemoService.this.lambda$onCreate$0$XpDemoService();
            }
        });
        LogUtils.i(TAG, "Service created");
    }

    public /* synthetic */ void lambda$onCreate$0$XpDemoService() {
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            initSyncFunction();
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d(TAG, "onStartCommand ");
        if (intent == null || intent.getAction() == null) {
            return 1;
        }
        handleIntent(intent);
        return 1;
    }

    private void initSyncFunction() {
        IntentFilter intentFilter = new IntentFilter("com.xiaopeng.settings.action.DEMO_ELEMENT_DIRECT");
        intentFilter.addAction(GlobalConstant.ACTION.ACTION_DEMO_ELEMENT_DIRECT);
        registerReceiver(this.mElementDirectReceiver, intentFilter);
    }

    private void handleIntent(Intent intent) {
        LogUtils.d(TAG, "handleIntent action: " + intent.getAction());
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mElementDirectReceiver);
    }
}
