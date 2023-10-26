package com.xiaopeng.smartcontrol.sdk.server;

import android.content.Context;
import com.xiaopeng.smartcontrol.ipc.server.ServerListener;
import com.xiaopeng.smartcontrol.ipc.server.ServerManager;
import com.xiaopeng.smartcontrol.sdk.BuildConfig;
import com.xiaopeng.smartcontrol.sdk.base.ManagerFactory;
import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.ServerConfig;
import com.xiaopeng.smartcontrol.sdk.server.hvac.HvacManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.powercenter.PowerCenterManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.AtlManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.CarControlShowManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.WindowControlManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.XpilotManagerServer;
import com.xiaopeng.smartcontrol.utils.Apps;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class CarControlSDKImpl {
    private static final String TAG = "CarControlSDKImpl";
    private final ConcurrentHashMap<String, BaseManagerServer.Implementation> mImplMap;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        private static final CarControlSDKImpl INSTANCE = new CarControlSDKImpl();

        private SingletonHolder() {
        }
    }

    private CarControlSDKImpl() {
        this.mImplMap = new ConcurrentHashMap<>();
        LogUtils.setLogTag("server-");
    }

    public static CarControlSDKImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public SeatManagerServer.Callback getSeatCallback() {
        return ((SeatManagerServer) ManagerFactory.getManager(SeatManagerServer.class.getName())).getCallback();
    }

    public void registerSeatImpl(SeatManagerServer.Implementation implementation) {
        this.mImplMap.put(SeatManagerServer.class.getName(), implementation);
    }

    public HvacManagerServer.Callback getHvacCallback() {
        return ((HvacManagerServer) ManagerFactory.getManager(HvacManagerServer.class.getName())).getCallback();
    }

    public void registerHvacImpl(HvacManagerServer.Implementation implementation) {
        this.mImplMap.put(HvacManagerServer.class.getName(), implementation);
    }

    public CarBodyManagerServer.Callback getCarBodyCallback() {
        return ((CarBodyManagerServer) ManagerFactory.getManager(CarBodyManagerServer.class.getName())).getCallback();
    }

    public void registerCarBodyImpl(CarBodyManagerServer.Implementation implementation) {
        this.mImplMap.put(CarBodyManagerServer.class.getName(), implementation);
    }

    public LampManagerServer.Callback getLampCallback() {
        return ((LampManagerServer) ManagerFactory.getManager(LampManagerServer.class.getName())).getCallback();
    }

    public void registerLampImpl(LampManagerServer.Implementation implementation) {
        this.mImplMap.put(LampManagerServer.class.getName(), implementation);
    }

    public PowerCenterManagerServer.Callback getPowerCenterCallback() {
        return ((PowerCenterManagerServer) ManagerFactory.getManager(PowerCenterManagerServer.class.getName())).getCallback();
    }

    public void registerPowerCenterImpl(PowerCenterManagerServer.Implementation implementation) {
        this.mImplMap.put(PowerCenterManagerServer.class.getName(), implementation);
    }

    public WindowControlManagerServer.Callback getWindowCallback() {
        return ((WindowControlManagerServer) ManagerFactory.getManager(WindowControlManagerServer.class.getName())).getCallback();
    }

    public void registerWindowImpl(WindowControlManagerServer.Implementation implementation) {
        this.mImplMap.put(WindowControlManagerServer.class.getName(), implementation);
    }

    public AtlManagerServer.Callback getAtlCallback() {
        return ((AtlManagerServer) ManagerFactory.getManager(AtlManagerServer.class.getName())).getCallback();
    }

    public void registerAtlImpl(AtlManagerServer.Implementation implementation) {
        this.mImplMap.put(AtlManagerServer.class.getName(), implementation);
    }

    public CarControlShowManagerServer.Callback getCarControlShowCallback() {
        return ((CarControlShowManagerServer) ManagerFactory.getManager(CarControlShowManagerServer.class.getName())).getCallback();
    }

    public void registerCarControlShowImpl(CarControlShowManagerServer.Implementation implementation) {
        this.mImplMap.put(CarControlShowManagerServer.class.getName(), implementation);
    }

    public XpilotManagerServer.Callback getXpilotCallback() {
        return ((XpilotManagerServer) ManagerFactory.getManager(XpilotManagerServer.class.getName())).getCallback();
    }

    public void registerXpilotImpl(XpilotManagerServer.Implementation implementation) {
        this.mImplMap.put(XpilotManagerServer.class.getName(), implementation);
    }

    public void init(Context context) {
        init(context, new ServerConfig.Builder().setSendAsync(true).build());
    }

    public void init(Context context, ServerConfig serverConfig) {
        String appId = Apps.getAppId(context.getApplicationContext());
        if (appId == null) {
            LogUtils.e(TAG, "init appId is null !!!");
        } else {
            LogUtils.i(TAG, "init appId : " + appId + " ," + BuildConfig.BUILD_VERSION);
            LogUtils.setLogTag(appId + "-");
        }
        LogUtils.i(TAG, "init");
        ServerManager.get().init(context, serverConfig);
        ServerManager.get().setServerListener(new ServerListener() { // from class: com.xiaopeng.smartcontrol.sdk.server.-$$Lambda$CarControlSDKImpl$SDqG50maw4rbZMbH-5X1QCq9kZU
            @Override // com.xiaopeng.smartcontrol.ipc.server.ServerListener
            public final String onCall(String str, String str2, String str3) {
                return CarControlSDKImpl.this.lambda$init$0$CarControlSDKImpl(str, str2, str3);
            }
        });
    }

    public /* synthetic */ String lambda$init$0$CarControlSDKImpl(String str, String str2, String str3) {
        String str4 = TAG;
        LogUtils.i(str4, String.format("onCall--  module:%s, method:%s, param:%s", str, str2, str3));
        BaseManagerServer baseManagerServer = (BaseManagerServer) ManagerFactory.getManager(str);
        if (baseManagerServer != null) {
            baseManagerServer.setSignalImpl(this.mImplMap.get(str));
            return baseManagerServer.onIpcCall(str2, str3);
        }
        LogUtils.e(str4, "no manager!!!");
        return null;
    }

    public void destroy() {
        LogUtils.i(TAG, "destroy");
    }
}
