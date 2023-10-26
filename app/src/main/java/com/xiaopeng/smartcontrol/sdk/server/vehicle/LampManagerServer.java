package com.xiaopeng.smartcontrol.sdk.server.vehicle;

import android.text.TextUtils;
import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class LampManagerServer extends BaseManagerServer<Implementation> {
    private Callback mCallback = new Callback();

    /* loaded from: classes2.dex */
    public interface Implementation extends BaseManagerServer.Implementation {
        boolean getBrakeLightOnOff();

        int getDaytimeRunningLightsOutputStatus();

        int getDoubleFlashBeamState();

        int getIhbState();

        int getSaberMode();

        boolean isHighBeamOn();

        boolean isLowBeamOn();

        boolean isPositionBeamOn();

        boolean isRearFogLampOn();

        boolean isReverseLampOn();

        void setIhbSw(boolean z);
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final LampManagerServer INSTANCE = new LampManagerServer();

        private SingletonHolder() {
        }
    }

    public static LampManagerServer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Callback getCallback() {
        return this.mCallback;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer
    public String onIpcCall(String str, String str2) {
        Object obj = null;
        if (this.mImpl == 0) {
            LogUtils.e(this.TAG, "mImpl is null");
            return null;
        }
        String[] split = TextUtils.isEmpty(str2) ? new String[0] : str2.split(",");
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1648148753:
                if (str.equals("getBrakeLightOnOff")) {
                    c = 0;
                    break;
                }
                break;
            case -641537457:
                if (str.equals("isReverseLampOn")) {
                    c = 1;
                    break;
                }
                break;
            case -379282930:
                if (str.equals("getSaberMode")) {
                    c = 2;
                    break;
                }
                break;
            case -350162856:
                if (str.equals("isLowBeamOn")) {
                    c = 3;
                    break;
                }
                break;
            case 385832633:
                if (str.equals("getDoubleFlashBeamState")) {
                    c = 4;
                    break;
                }
                break;
            case 644069441:
                if (str.equals("isPositionBeamOn")) {
                    c = 5;
                    break;
                }
                break;
            case 1086355620:
                if (str.equals("getIhbState")) {
                    c = 6;
                    break;
                }
                break;
            case 1322283770:
                if (str.equals("isHighBeamOn")) {
                    c = 7;
                    break;
                }
                break;
            case 1394877861:
                if (str.equals("setIhbSw")) {
                    c = '\b';
                    break;
                }
                break;
            case 1497345511:
                if (str.equals("isRearFogLampOn")) {
                    c = '\t';
                    break;
                }
                break;
            case 1732500028:
                if (str.equals("getDaytimeRunningLightsOutputStatus")) {
                    c = '\n';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                obj = Boolean.valueOf(((Implementation) this.mImpl).getBrakeLightOnOff());
                break;
            case 1:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isReverseLampOn());
                break;
            case 2:
                obj = Integer.valueOf(((Implementation) this.mImpl).getSaberMode());
                break;
            case 3:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isLowBeamOn());
                break;
            case 4:
                obj = Integer.valueOf(((Implementation) this.mImpl).getDoubleFlashBeamState());
                break;
            case 5:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isPositionBeamOn());
                break;
            case 6:
                obj = Integer.valueOf(((Implementation) this.mImpl).getIhbState());
                break;
            case 7:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHighBeamOn());
                break;
            case '\b':
                ((Implementation) this.mImpl).setIhbSw(Boolean.parseBoolean(split[0]));
                break;
            case '\t':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isRearFogLampOn());
                break;
            case '\n':
                obj = Integer.valueOf(((Implementation) this.mImpl).getDaytimeRunningLightsOutputStatus());
                break;
        }
        return obj != null ? String.valueOf(obj) : "";
    }

    /* loaded from: classes2.dex */
    public class Callback {
        public Callback() {
        }

        public void onDoubleFlashBeamChanged(int i) {
            LogUtils.i(LampManagerServer.this.TAG, "onDoubleFlashBeamChanged: " + i);
            LampManagerServer.this.sendIpc("onDoubleFlashBeamChanged", Integer.valueOf(i));
        }

        public void onLowBeamChanged(boolean z) {
            LogUtils.i(LampManagerServer.this.TAG, "onLowBeamChanged: " + z);
            LampManagerServer.this.sendIpc("onLowBeamChanged", Boolean.valueOf(z));
        }

        public void onHighBeamChanged(boolean z) {
            LogUtils.i(LampManagerServer.this.TAG, "onHighBeamChanged: " + z);
            LampManagerServer.this.sendIpc("onHighBeamChanged", Boolean.valueOf(z));
        }

        public void onPositionBeamChanged(boolean z) {
            LogUtils.i(LampManagerServer.this.TAG, "onPositionBeamChanged: " + z);
            LampManagerServer.this.sendIpc("onPositionBeamChanged", Boolean.valueOf(z));
        }

        public void onSaberModeChanged(int i) {
            LogUtils.i(LampManagerServer.this.TAG, "onSaberModeChanged: " + i);
            LampManagerServer.this.sendIpc("onSaberModeChanged", Integer.valueOf(i));
        }

        public void onRearFogLampChanged(boolean z) {
            LogUtils.i(LampManagerServer.this.TAG, "onRearFogLampChanged: " + z);
            LampManagerServer.this.sendIpc("onRearFogLampChanged", Boolean.valueOf(z));
        }

        public void onReverseLampChanged(boolean z) {
            LogUtils.i(LampManagerServer.this.TAG, "onReverseLampChanged: " + z);
            LampManagerServer.this.sendIpc("onReverseLampChanged", Boolean.valueOf(z));
        }

        public void onBrakeLightChanged(boolean z) {
            LogUtils.i(LampManagerServer.this.TAG, "onBrakeLightChanged: " + z);
            LampManagerServer.this.sendIpc("onBrakeLightChanged", Boolean.valueOf(z));
        }

        public void onDaytimeRunningLightChanged(int i) {
            LogUtils.i(LampManagerServer.this.TAG, "onDaytimeRunningLightChanged: " + i);
            LampManagerServer.this.sendIpc("onDaytimeRunningLightChanged", Integer.valueOf(i));
        }

        public void onIhbStateChanged(int i) {
            LogUtils.i(LampManagerServer.this.TAG, "onIhbStateChanged: " + i);
            LampManagerServer.this.sendIpc("onIhbStateChanged", Integer.valueOf(i));
        }
    }
}
