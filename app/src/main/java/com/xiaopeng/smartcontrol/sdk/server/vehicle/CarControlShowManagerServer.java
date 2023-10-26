package com.xiaopeng.smartcontrol.sdk.server.vehicle;

import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class CarControlShowManagerServer extends BaseManagerServer<Implementation> {
    private Callback mCallback = new Callback();

    /* loaded from: classes2.dex */
    public interface Implementation extends BaseManagerServer.Implementation {
        void showCarControlPage(int i);

        void showVehicleFaultDetailPage(String str);

        void showVehicleFaultListPage();
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final CarControlShowManagerServer INSTANCE = new CarControlShowManagerServer();

        private SingletonHolder() {
        }
    }

    public static CarControlShowManagerServer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Callback getCallback() {
        return this.mCallback;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0045, code lost:
        if (r4.equals("showCarControlPage") == false) goto L11;
     */
    @Override // com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String onIpcCall(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            T extends com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer$Implementation r0 = r3.mImpl
            if (r0 != 0) goto Ld
            java.lang.String r4 = r3.TAG
            java.lang.String r5 = "mImpl is null"
            com.xiaopeng.smartcontrol.utils.LogUtils.e(r4, r5)
            r4 = 0
            return r4
        Ld:
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            r1 = 0
            if (r0 == 0) goto L17
            java.lang.String[] r0 = new java.lang.String[r1]
            goto L1c
        L17:
            java.lang.String r0 = ","
            r5.split(r0)
        L1c:
            r4.hashCode()
            r0 = -1
            int r2 = r4.hashCode()
            switch(r2) {
                case -1933563531: goto L3f;
                case 185243859: goto L34;
                case 1856289632: goto L29;
                default: goto L27;
            }
        L27:
            r1 = r0
            goto L48
        L29:
            java.lang.String r1 = "showVehicleFaultListPage"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L32
            goto L27
        L32:
            r1 = 2
            goto L48
        L34:
            java.lang.String r1 = "showVehicleFaultDetailPage"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L3d
            goto L27
        L3d:
            r1 = 1
            goto L48
        L3f:
            java.lang.String r2 = "showCarControlPage"
            boolean r4 = r4.equals(r2)
            if (r4 != 0) goto L48
            goto L27
        L48:
            switch(r1) {
                case 0: goto L5c;
                case 1: goto L54;
                case 2: goto L4c;
                default: goto L4b;
            }
        L4b:
            goto L67
        L4c:
            T extends com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer$Implementation r4 = r3.mImpl
            com.xiaopeng.smartcontrol.sdk.server.vehicle.CarControlShowManagerServer$Implementation r4 = (com.xiaopeng.smartcontrol.sdk.server.vehicle.CarControlShowManagerServer.Implementation) r4
            r4.showVehicleFaultListPage()
            goto L67
        L54:
            T extends com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer$Implementation r4 = r3.mImpl
            com.xiaopeng.smartcontrol.sdk.server.vehicle.CarControlShowManagerServer$Implementation r4 = (com.xiaopeng.smartcontrol.sdk.server.vehicle.CarControlShowManagerServer.Implementation) r4
            r4.showVehicleFaultDetailPage(r5)
            goto L67
        L5c:
            T extends com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer$Implementation r4 = r3.mImpl
            com.xiaopeng.smartcontrol.sdk.server.vehicle.CarControlShowManagerServer$Implementation r4 = (com.xiaopeng.smartcontrol.sdk.server.vehicle.CarControlShowManagerServer.Implementation) r4
            int r5 = java.lang.Integer.parseInt(r5)
            r4.showCarControlPage(r5)
        L67:
            java.lang.String r4 = ""
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.smartcontrol.sdk.server.vehicle.CarControlShowManagerServer.onIpcCall(java.lang.String, java.lang.String):java.lang.String");
    }

    /* loaded from: classes2.dex */
    public class Callback {
        public Callback() {
        }

        public void onEnterVehicleFaultPage(String str) {
            LogUtils.i(CarControlShowManagerServer.this.TAG, "onEnterVehicleFaultPage info: " + str);
            CarControlShowManagerServer.this.sendIpc("onEnterVehicleFaultPage", str);
        }

        public void onExitVehicleFaultPage() {
            LogUtils.i(CarControlShowManagerServer.this.TAG, "onExitVehicleFaultPage");
            CarControlShowManagerServer.this.sendIpc("onExitVehicleFaultPage", null);
        }
    }
}
