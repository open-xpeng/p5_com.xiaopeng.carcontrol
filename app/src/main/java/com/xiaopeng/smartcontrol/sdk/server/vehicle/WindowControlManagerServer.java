package com.xiaopeng.smartcontrol.sdk.server.vehicle;

import android.text.TextUtils;
import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class WindowControlManagerServer extends BaseManagerServer<Implementation> {
    private Callback mCallback = new Callback();

    /* loaded from: classes2.dex */
    public interface Implementation extends BaseManagerServer.Implementation {
        void controlWindow(int i);

        float getWindowPos(int i);

        boolean isWindowLockActive();

        void setWindowLockActive(boolean z);

        void setWindowPos(int i, float f);
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final WindowControlManagerServer INSTANCE = new WindowControlManagerServer();

        private SingletonHolder() {
        }
    }

    public static WindowControlManagerServer getInstance() {
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
            case -1327256213:
                if (str.equals("isWindowLockActive")) {
                    c = 0;
                    break;
                }
                break;
            case -226621042:
                if (str.equals("getWindowPos")) {
                    c = 1;
                    break;
                }
                break;
            case 988541869:
                if (str.equals("controlWindow")) {
                    c = 2;
                    break;
                }
                break;
            case 1322371586:
                if (str.equals("setWindowPos")) {
                    c = 3;
                    break;
                }
                break;
            case 1389358243:
                if (str.equals("setWindowLockActive")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isWindowLockActive());
                break;
            case 1:
                obj = Float.valueOf(((Implementation) this.mImpl).getWindowPos(Integer.parseInt(split[0])));
                break;
            case 2:
                ((Implementation) this.mImpl).controlWindow(Integer.parseInt(split[0]));
                break;
            case 3:
                ((Implementation) this.mImpl).setWindowPos(Integer.parseInt(split[0]), Float.parseFloat(split[1]));
                break;
            case 4:
                ((Implementation) this.mImpl).setWindowLockActive(Boolean.parseBoolean(split[0]));
                break;
        }
        return obj != null ? String.valueOf(obj) : "";
    }

    /* loaded from: classes2.dex */
    public class Callback {
        public Callback() {
        }

        public void onWindowStateChanged(int i) {
            LogUtils.i(WindowControlManagerServer.this.TAG, "onWindowStateChanged state: " + i);
            WindowControlManagerServer.this.sendIpc("onWindowStateChanged", Integer.valueOf(i));
        }

        public void onWindowPosChanged(int i, float f) {
            String str = i + "," + f;
            LogUtils.i(WindowControlManagerServer.this.TAG, "onWindowPosChanged: " + str);
            WindowControlManagerServer.this.sendIpc("onWindowPosChanged", str);
        }

        public void onWindowLockStateChanged(boolean z) {
            LogUtils.i(WindowControlManagerServer.this.TAG, "onWindowLockStateChanged: " + z);
            WindowControlManagerServer.this.sendIpc("onWindowLockStateChanged", Boolean.valueOf(z));
        }
    }
}
