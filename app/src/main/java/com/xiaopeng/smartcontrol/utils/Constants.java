package com.xiaopeng.smartcontrol.utils;

import com.xiaopeng.smartcontrol.sdk.server.hvac.HvacManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.powercenter.PowerCenterManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.AtlManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.CarControlShowManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.WindowControlManagerServer;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.XpilotManagerServer;

/* loaded from: classes2.dex */
public class Constants {
    public static final String BIND_SERVICE_ACTION = "com.xiaopeng.smartcontrol.BindAidlService";
    public static final String SDK_BINDER_PREFFIX = "SmartControlSDKServiceBinder_";

    /* loaded from: classes2.dex */
    public static class APPID {
        public static final String CARCONTROL = "carcontrol";
        public static final String HVAC = "hvac";
        public static final String POWERCENTER = "powercenter";
        public static final String SETTINGS = "settings";
    }

    /* loaded from: classes2.dex */
    public static class CAR_CONTROL_MODULE {
        public static final String SEAT_IMPL = SeatManagerServer.class.getName();
        public static final String HVAC_IMPL = HvacManagerServer.class.getName();
        public static final String CAR_BODY_IMPL = CarBodyManagerServer.class.getName();
        public static final String LAMP_IMPL = LampManagerServer.class.getName();
        public static final String PowerCenter_IMPL = PowerCenterManagerServer.class.getName();
        public static final String WINDOW_IMPL = WindowControlManagerServer.class.getName();
        public static final String ATL_IMPL = AtlManagerServer.class.getName();
        public static final String SHOW_IMPL = CarControlShowManagerServer.class.getName();
        public static final String XPILOT_IMPL = XpilotManagerServer.class.getName();
    }
}
