package com.xiaopeng.smartcontrol.sdk.server.vehicle;

import android.text.TextUtils;
import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class XpilotManagerServer extends BaseManagerServer<Implementation> {
    private Callback mCallback = new Callback();

    /* loaded from: classes2.dex */
    public interface Implementation extends BaseManagerServer.Implementation {
        int getAutoParkState();

        int getMemParkState();

        void setAutoParkEnable(boolean z);

        void setMemParkEnable(boolean z);
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final XpilotManagerServer INSTANCE = new XpilotManagerServer();

        private SingletonHolder() {
        }
    }

    public static XpilotManagerServer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Callback getCallback() {
        return this.mCallback;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer
    public String onIpcCall(String str, String str2) {
        Integer num = null;
        if (this.mImpl == 0) {
            LogUtils.e(this.TAG, "mImpl is null");
            return null;
        }
        String[] split = TextUtils.isEmpty(str2) ? new String[0] : str2.split(",");
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1412268928:
                if (str.equals("setMemParkEnable")) {
                    c = 0;
                    break;
                }
                break;
            case -1199686114:
                if (str.equals("setAutoParkEnable")) {
                    c = 1;
                    break;
                }
                break;
            case 4736546:
                if (str.equals("getAutoParkState")) {
                    c = 2;
                    break;
                }
                break;
            case 1689198312:
                if (str.equals("getMemParkState")) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((Implementation) this.mImpl).setMemParkEnable(Boolean.parseBoolean(split[0]));
                break;
            case 1:
                ((Implementation) this.mImpl).setAutoParkEnable(Boolean.parseBoolean(split[0]));
                break;
            case 2:
                num = Integer.valueOf(((Implementation) this.mImpl).getAutoParkState());
                break;
            case 3:
                num = Integer.valueOf(((Implementation) this.mImpl).getMemParkState());
                break;
        }
        return num != null ? String.valueOf(num) : "";
    }

    /* loaded from: classes2.dex */
    public class Callback {
        public Callback() {
        }

        public void onAutoParkChanged(int i) {
            LogUtils.i(XpilotManagerServer.this.TAG, "onAutoParkChanged state: " + i);
            XpilotManagerServer.this.sendIpc("onAutoParkChanged", Integer.valueOf(i));
        }

        public void onMemParkChanged(int i) {
            LogUtils.i(XpilotManagerServer.this.TAG, "onMemParkChanged state: " + i);
            XpilotManagerServer.this.sendIpc("onMemParkChanged", Integer.valueOf(i));
        }
    }
}
