package com.xiaopeng.smartcontrol.sdk.server.powercenter;

import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer;
import java.util.Map;

/* loaded from: classes2.dex */
public class PowerCenterManagerServer extends BaseManagerServer<Implementation> {
    private static final String TAG = "PowerCenterManagerServer";
    private Callback mCallback = new Callback();

    /* loaded from: classes2.dex */
    public interface Implementation extends BaseManagerServer.Implementation {
        int getChargeGunStatus();

        int getChargeLimit();

        int getChargeStatus();

        int getDischargeLimit();

        Map<String, Float> getPowerData();

        float getRemainingDistance();

        int getSoc();

        boolean isSuperCharge();

        void startCharge();

        void startDischarge();

        void startPowerCenter();

        void startPreheat();

        void stopCharge();

        void stopDischarge();

        void stopPreheat();
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final PowerCenterManagerServer INSTANCE = new PowerCenterManagerServer();

        private SingletonHolder() {
        }
    }

    public static PowerCenterManagerServer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Callback getCallback() {
        return this.mCallback;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00bf, code lost:
        if (r4.equals("stopDischarge") == false) goto L11;
     */
    @Override // com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String onIpcCall(java.lang.String r4, java.lang.String r5) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.smartcontrol.sdk.server.powercenter.PowerCenterManagerServer.onIpcCall(java.lang.String, java.lang.String):java.lang.String");
    }

    /* loaded from: classes2.dex */
    public class Callback {
        public Callback() {
        }

        public void onPowerCenterPageStatusChanged(int i) {
            PowerCenterManagerServer.this.sendIpc("onPowerCenterPageStatusChanged", String.valueOf(i));
        }

        public void onPowerDataChanged(Map<String, Float> map) {
            PowerCenterManagerServer.this.sendIpc("onPowerDataChanged", String.valueOf(map));
        }

        public void onChargeGunStatusChanged(int i) {
            PowerCenterManagerServer.this.sendIpc("onChargeGunStatusChanged", String.valueOf(i));
        }

        public void onChargeStatusChanged(int i) {
            PowerCenterManagerServer.this.sendIpc("onChargeStatusChanged", String.valueOf(i));
        }

        public void onRemainingDistanceChanged(float f) {
            PowerCenterManagerServer.this.sendIpc("onRemainingDistanceChanged", String.valueOf(f));
        }

        public void onChargeLimitChanged(int i) {
            PowerCenterManagerServer.this.sendIpc("onChargeLimitChanged", String.valueOf(i));
        }

        public void onDisChargeLimitChanged(int i) {
            PowerCenterManagerServer.this.sendIpc("onDisChargeLimitChanged", String.valueOf(i));
        }

        public void onKeepTempModeChanged(boolean z) {
            PowerCenterManagerServer.this.sendIpc("onKeepTempModeChanged", String.valueOf(z));
        }

        public void onSuperChargeFlagChanged(boolean z) {
            PowerCenterManagerServer.this.sendIpc("onSuperChargeFlagChanged", String.valueOf(z));
        }

        public void onSocChanged(int i) {
            PowerCenterManagerServer.this.sendIpc("onSocChanged", String.valueOf(i));
        }
    }
}
