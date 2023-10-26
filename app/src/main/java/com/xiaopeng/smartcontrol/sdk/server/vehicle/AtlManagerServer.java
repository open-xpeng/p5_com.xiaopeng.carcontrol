package com.xiaopeng.smartcontrol.sdk.server.vehicle;

import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class AtlManagerServer extends BaseManagerServer<Implementation> {
    private Callback mCallback = new Callback();

    /* loaded from: classes2.dex */
    public interface Implementation extends BaseManagerServer.Implementation {
        int getAtlBrightness();

        int getAtlColorMode();

        int getAtlEffect();

        int getDoubleColor();

        int getSingleColor();

        boolean isAtlSwEnable();

        void setAtlBrightness(int i);

        void setAtlColorMode(int i);

        void setAtlEffect(int i);

        void setAtlSw(boolean z);

        void setDoubleColor(int i);

        void setSingleColor(int i);
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final AtlManagerServer INSTANCE = new AtlManagerServer();

        private SingletonHolder() {
        }
    }

    public static AtlManagerServer getInstance() {
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
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1706427768:
                if (str.equals("setAtlBrightness")) {
                    c = 0;
                    break;
                }
                break;
            case -1084192893:
                if (str.equals("getAtlColorMode")) {
                    c = 1;
                    break;
                }
                break;
            case -173440369:
                if (str.equals("setAtlColorMode")) {
                    c = 2;
                    break;
                }
                break;
            case 125015060:
                if (str.equals("getAtlBrightness")) {
                    c = 3;
                    break;
                }
                break;
            case 125231673:
                if (str.equals("setSingleColor")) {
                    c = 4;
                    break;
                }
                break;
            case 202406612:
                if (str.equals("getAtlEffect")) {
                    c = 5;
                    break;
                }
                break;
            case 239220816:
                if (str.equals("setDoubleColor")) {
                    c = 6;
                    break;
                }
                break;
            case 365139766:
                if (str.equals("isAtlSwEnable")) {
                    c = 7;
                    break;
                }
                break;
            case 1387856795:
                if (str.equals("setAtlSw")) {
                    c = '\b';
                    break;
                }
                break;
            case 1751399240:
                if (str.equals("setAtlEffect")) {
                    c = '\t';
                    break;
                }
                break;
            case 1896967877:
                if (str.equals("getSingleColor")) {
                    c = '\n';
                    break;
                }
                break;
            case 2010957020:
                if (str.equals("getDoubleColor")) {
                    c = 11;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((Implementation) this.mImpl).setAtlBrightness(Integer.parseInt(str2));
                break;
            case 1:
                obj = Integer.valueOf(((Implementation) this.mImpl).getAtlColorMode());
                break;
            case 2:
                ((Implementation) this.mImpl).setAtlColorMode(Integer.parseInt(str2));
                break;
            case 3:
                obj = Integer.valueOf(((Implementation) this.mImpl).getAtlBrightness());
                break;
            case 4:
                ((Implementation) this.mImpl).setSingleColor(Integer.parseInt(str2));
                break;
            case 5:
                obj = Integer.valueOf(((Implementation) this.mImpl).getAtlEffect());
                break;
            case 6:
                ((Implementation) this.mImpl).setDoubleColor(Integer.parseInt(str2));
                break;
            case 7:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isAtlSwEnable());
                break;
            case '\b':
                ((Implementation) this.mImpl).setAtlSw(Boolean.parseBoolean(str2));
                break;
            case '\t':
                ((Implementation) this.mImpl).setAtlEffect(Integer.parseInt(str2));
                break;
            case '\n':
                obj = Integer.valueOf(((Implementation) this.mImpl).getSingleColor());
                break;
            case 11:
                obj = Integer.valueOf(((Implementation) this.mImpl).getDoubleColor());
                break;
        }
        return obj != null ? String.valueOf(obj) : "";
    }

    /* loaded from: classes2.dex */
    public class Callback {
        public Callback() {
        }

        public void onAtlEffectChanged(int i) {
            LogUtils.i(AtlManagerServer.this.TAG, "onAtlEffectChanged effect: " + i);
            AtlManagerServer.this.sendIpc("onAtlEffectChanged", Integer.valueOf(i));
        }

        public void onSingleColorChanged(int i) {
            LogUtils.i(AtlManagerServer.this.TAG, "onSingleColorChanged: " + i);
            AtlManagerServer.this.sendIpc("onSingleColorChanged", Integer.valueOf(i));
        }

        public void onDoubleColorChanged(int i) {
            LogUtils.i(AtlManagerServer.this.TAG, "onDoubleColorChanged: " + i);
            AtlManagerServer.this.sendIpc("onDoubleColorChanged", Integer.valueOf(i));
        }

        public void onAtlBrightnessChanged(int i) {
            LogUtils.i(AtlManagerServer.this.TAG, "onAtlBrightnessChanged: " + i);
            AtlManagerServer.this.sendIpc("onAtlBrightnessChanged", Integer.valueOf(i));
        }

        public void onAtlSwChanged(boolean z) {
            LogUtils.i(AtlManagerServer.this.TAG, "onAtlSwChanged: " + z);
            AtlManagerServer.this.sendIpc("onAtlSwChanged", Boolean.valueOf(z));
        }

        public void onAtlColorModeChanged(int i) {
            LogUtils.i(AtlManagerServer.this.TAG, "onAtlColorModeChanged: " + i);
            AtlManagerServer.this.sendIpc("onAtlColorModeChanged", Integer.valueOf(i));
        }
    }
}
