package com.xiaopeng.carcontrol.carmanager.ambient;

import android.util.Pair;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public abstract class AbstractAmbientManager {
    public static final String PARTITION_ALL = "all";
    protected String TAG = getClass().getSimpleName();
    List<AmbientEventListener> mListeners = new CopyOnWriteArrayList();

    public abstract int getAmbientLightBright(String partition) throws XUIServiceNotConnectedException;

    public abstract String getAmbientLightColorType(String partition) throws XUIServiceNotConnectedException;

    public abstract Pair<AmbientColor, AmbientColor> getAmbientLightDoubleColor(String partition) throws XUIServiceNotConnectedException;

    public abstract boolean getAmbientLightEnable() throws XUIServiceNotConnectedException;

    public abstract String getAmbientLightMode(String partition) throws XUIServiceNotConnectedException;

    public abstract AmbientColor getAmbientLightMonoColor(String partition) throws XUIServiceNotConnectedException;

    public abstract Map<String, String> getAmbientLightPartitionModes() throws XUIServiceNotConnectedException;

    public abstract int getAmbientLightRegionBright(String partition, String region) throws XUIServiceNotConnectedException;

    public abstract AmbientColor getAmbientLightRegionColor(String partition, String region) throws XUIServiceNotConnectedException;

    public abstract String getAmbientLightThemeColor(String partition) throws XUIServiceNotConnectedException;

    public abstract boolean isSupportDoubleThemeColor(String mode) throws XUIServiceNotConnectedException;

    public abstract int playAmbientLightEffect(String partition, AmbientEffect effect) throws XUIServiceNotConnectedException;

    public abstract int playAmbientLightEffect(String partition, String effect) throws XUIServiceNotConnectedException;

    public abstract int setAmbientLightBright(String partition, int bright) throws XUIServiceNotConnectedException;

    public abstract int setAmbientLightColorType(String partition, String colorType) throws XUIServiceNotConnectedException;

    public abstract int setAmbientLightDoubleColor(String partition, Pair<AmbientColor, AmbientColor> color) throws XUIServiceNotConnectedException;

    public abstract int setAmbientLightEnable(boolean enable) throws XUIServiceNotConnectedException;

    public abstract int setAmbientLightMode(String partition, String mode) throws XUIServiceNotConnectedException;

    public abstract int setAmbientLightMonoColor(String partition, AmbientColor color) throws XUIServiceNotConnectedException;

    public abstract int setAmbientLightRegionBright(String partition, String region, int bright) throws XUIServiceNotConnectedException;

    public abstract int setAmbientLightRegionColor(String partition, String region, AmbientColor color) throws XUIServiceNotConnectedException;

    public abstract int setAmbientLightThemeColor(String partition, String themeColor) throws XUIServiceNotConnectedException;

    public abstract Set<String> showAmbientLightColorTypes() throws XUIServiceNotConnectedException;

    public abstract Set<String> showAmbientLightEffectPartitions() throws XUIServiceNotConnectedException;

    public abstract Set<String> showAmbientLightEffects() throws XUIServiceNotConnectedException;

    public abstract int stopAmbientLightEffect() throws XUIServiceNotConnectedException;

    public abstract int subAmbientLightMode(String partition, String mode) throws XUIServiceNotConnectedException;

    public void registerListener(AmbientEventListener listener) {
        this.mListeners.add(listener);
    }

    public void unregisterListener(AmbientEventListener listener) {
        this.mListeners.remove(listener);
    }
}
