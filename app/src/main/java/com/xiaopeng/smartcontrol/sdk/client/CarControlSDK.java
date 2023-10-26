package com.xiaopeng.smartcontrol.sdk.client;

import android.content.Context;
import com.xiaopeng.smartcontrol.ipc.client.ClientListener;
import com.xiaopeng.smartcontrol.ipc.client.ClientManager;
import com.xiaopeng.smartcontrol.sdk.BuildConfig;
import com.xiaopeng.smartcontrol.sdk.base.ManagerFactory;
import com.xiaopeng.smartcontrol.sdk.client.hvac.HvacManager;
import com.xiaopeng.smartcontrol.sdk.client.powercenter.PowerCenterManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.AtlManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.CarBodyManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.CarControlShowManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.LampManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.SeatManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.WindowControlManager;
import com.xiaopeng.smartcontrol.sdk.client.vehicle.XpilotManager;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class CarControlSDK {
    private static final String TAG = "CarControlSDK";

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final CarControlSDK INSTANCE = new CarControlSDK();

        private SingletonHolder() {
        }
    }

    private CarControlSDK() {
        LogUtils.setLogTag("client-");
    }

    public static CarControlSDK getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public SeatManager getSeatManager() {
        return (SeatManager) ManagerFactory.getManager(SeatManager.class.getName());
    }

    public HvacManager getHvacManager() {
        return (HvacManager) ManagerFactory.getManager(HvacManager.class.getName());
    }

    public CarBodyManager getCarBodyManager() {
        return (CarBodyManager) ManagerFactory.getManager(CarBodyManager.class.getName());
    }

    public LampManager getLampManager() {
        return (LampManager) ManagerFactory.getManager(LampManager.class.getName());
    }

    public PowerCenterManager getPowerCenterManager() {
        return (PowerCenterManager) ManagerFactory.getManager(PowerCenterManager.class.getName());
    }

    public WindowControlManager getWindowControlManager() {
        return (WindowControlManager) ManagerFactory.getManager(WindowControlManager.class.getName());
    }

    public AtlManager geAtlManager() {
        return (AtlManager) ManagerFactory.getManager(AtlManager.class.getName());
    }

    public CarControlShowManager getCarControlShowManager() {
        return (CarControlShowManager) ManagerFactory.getManager(CarControlShowManager.class.getName());
    }

    public XpilotManager getXpilotManager() {
        return (XpilotManager) ManagerFactory.getManager(XpilotManager.class.getName());
    }

    public void init(Context context) {
        init(context, null);
    }

    public void init(Context context, ClientConfig clientConfig) {
        LogUtils.i(TAG, String.format("init appId : %s, %s", context.getPackageName(), BuildConfig.BUILD_VERSION));
        ClientManager.get().init(context, clientConfig);
        ClientManager.get().setClientListener(new ClientListener() { // from class: com.xiaopeng.smartcontrol.sdk.client.-$$Lambda$CarControlSDK$bkwGN0sKyKdb1Q7UPt4R-QRYu68
            @Override // com.xiaopeng.smartcontrol.ipc.client.ClientListener
            public final void onReceived(String str, String str2, String str3, String str4) {
                CarControlSDK.lambda$init$0(str, str2, str3, str4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$init$0(String str, String str2, String str3, String str4) {
        String str5 = TAG;
        LogUtils.i(str5, String.format("onReceived-- appId:%s, module:%s, method:%s, data:%s", str, str2, str3, str4));
        BaseManager baseManager = (BaseManager) ManagerFactory.getManager(ManagerFactory.getClientClassName(str2));
        if (baseManager != null) {
            baseManager.onIpcReceived(str3, str4);
        } else {
            LogUtils.e(str5, "no manager!!!");
        }
    }

    public void destroy() {
        LogUtils.i(TAG, "destroy");
        ClientManager.get().destroy();
    }
}
