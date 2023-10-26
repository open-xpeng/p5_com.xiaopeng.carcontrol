package com.xiaopeng.carcontrol.carmanager.ambient;

import android.util.Pair;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.xuimanager.XUIManager;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;

/* loaded from: classes.dex */
public class AmbientManagerCompat {
    private static final String TAG = "AmbientManagerCompat";
    protected AbstractAmbientManager mManager;

    public AmbientManagerCompat(XUIManager xuiManager) {
        if (isSupportNewApi(xuiManager)) {
            LogUtils.i(TAG, "AmbientManagerCompat: isSupportNewApi", false);
            this.mManager = AmbientManagerNew.newInstance(xuiManager);
            return;
        }
        LogUtils.i(TAG, "AmbientManagerCompat: is not SupportNewApi", false);
        this.mManager = AmbientManagerOld.newInstance(xuiManager);
    }

    public static final boolean isSupportNewApi(XUIManager xuiManager) {
        try {
            return xuiManager.getXUIServiceManager("ambient") != null;
        } catch (Throwable th) {
            LogUtils.e(TAG, "isSupportNewApi: " + th.getMessage(), false);
            return false;
        }
    }

    public void registerListener(AmbientEventListener listener) {
        AbstractAmbientManager abstractAmbientManager = this.mManager;
        if (abstractAmbientManager != null) {
            abstractAmbientManager.registerListener(listener);
        }
    }

    public void unregisterListener(AmbientEventListener listener) {
        AbstractAmbientManager abstractAmbientManager = this.mManager;
        if (abstractAmbientManager != null) {
            abstractAmbientManager.unregisterListener(listener);
        }
    }

    public boolean isSupportAtlApiControl() {
        return isSupportNewApi() || isSupportOldApi();
    }

    private boolean isSupportNewApi() {
        return this.mManager instanceof AmbientManagerNew;
    }

    private boolean isSupportOldApi() {
        return this.mManager instanceof AmbientManagerOld;
    }

    public boolean getAmbientLightEnable() throws XUIServiceNotConnectedException {
        return this.mManager.getAmbientLightEnable();
    }

    public void setAmbientLightEnable(boolean enable) throws XUIServiceNotConnectedException {
        this.mManager.setAmbientLightEnable(enable);
    }

    public String getAmbientLightMode(String partition) throws XUIServiceNotConnectedException {
        return this.mManager.getAmbientLightMode(partition);
    }

    public void setAmbientLightMode(String partition, String mode) throws XUIServiceNotConnectedException {
        this.mManager.setAmbientLightMode(partition, mode);
    }

    public int getAmbientLightBright(String partition) throws XUIServiceNotConnectedException {
        return this.mManager.getAmbientLightBright(partition);
    }

    public void setAmbientLightBright(String partition, int bright) throws XUIServiceNotConnectedException {
        this.mManager.setAmbientLightBright(partition, bright);
    }

    public String getAmbientLightColorType(String partition) throws XUIServiceNotConnectedException {
        return this.mManager.getAmbientLightColorType(partition);
    }

    public void setAmbientLightColorType(String partition, String colorType) throws XUIServiceNotConnectedException {
        this.mManager.setAmbientLightColorType(partition, colorType);
    }

    public AmbientColor getAmbientLightMonoColor(String partition) throws XUIServiceNotConnectedException {
        return this.mManager.getAmbientLightMonoColor(partition);
    }

    public void setAmbientLightMonoColor(String partition, AmbientColor color) throws XUIServiceNotConnectedException {
        this.mManager.setAmbientLightMonoColor(partition, color);
    }

    public Pair<AmbientColor, AmbientColor> getAmbientLightDoubleColor(String partition) throws XUIServiceNotConnectedException {
        return this.mManager.getAmbientLightDoubleColor(partition);
    }

    public void setAmbientLightDoubleColor(String partition, Pair<AmbientColor, AmbientColor> color) throws XUIServiceNotConnectedException {
        this.mManager.setAmbientLightDoubleColor(partition, color);
    }

    public boolean isSupportDoubleThemeColor(String mode) throws XUIServiceNotConnectedException {
        return this.mManager.isSupportDoubleThemeColor(mode);
    }
}
