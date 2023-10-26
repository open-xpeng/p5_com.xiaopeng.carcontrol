package com.xiaopeng.carcontrol.carmanager.ambient;

import android.util.Pair;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.light.AtlColorType;
import com.xiaopeng.xuimanager.XUIManager;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.ambientlight.AmbientLightManager;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class AmbientManagerOld extends AbstractAmbientManager {
    private static final String TAG = "AmbientManagerOld";
    AmbientLightManager.AmbientLightEventListener callback = new AmbientLightManager.AmbientLightEventListener() { // from class: com.xiaopeng.carcontrol.carmanager.ambient.AmbientManagerOld.1
        public void onLightEffectTypeSetEvent(String effectType) {
            LogUtils.i(AmbientManagerOld.TAG, "onLightEffectTypeSetEvent:" + effectType, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerOld.this.mListeners) {
                ambientEventListener.onChangeMode("all", effectType);
            }
        }

        public void onLightDoubleColorEnableEvent(String effectType, boolean enable) {
            LogUtils.i(AmbientManagerOld.TAG, "onLightDoubleColorEnableEvent:" + effectType + ",enable:" + enable, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerOld.this.mListeners) {
                ambientEventListener.onChangeColorType("all", (enable ? AtlColorType.Double : AtlColorType.Mono).toAtlCommand());
            }
        }

        public void onLightMonoColorSetEvent(String effectType, int color) {
            LogUtils.i(AmbientManagerOld.TAG, "onLightMonoColorSetEvent:" + effectType + ",color:" + color, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerOld.this.mListeners) {
                ambientEventListener.onChangeMonoColor("all", new AmbientColor(false, Integer.toHexString(color)));
            }
        }

        public void onLightDoubleColorSetEvent(String effectType, int first_color, int second_color) {
            LogUtils.i(AmbientManagerOld.TAG, "onLightDoubleColorSetEvent:" + effectType + ",first_color:" + first_color + ",second_color:" + second_color, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerOld.this.mListeners) {
                ambientEventListener.onChangeDoubleColor("all", new Pair<>(new AmbientColor(false, Integer.toHexString(first_color)), new AmbientColor(false, Integer.toHexString(second_color))));
            }
        }

        public void onLightBrightSetEvent(String effectType, int bright) {
            LogUtils.i(AmbientManagerOld.TAG, "onLightBrightSetEvent:" + effectType + ",bright:" + bright, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerOld.this.mListeners) {
                ambientEventListener.onChangeBright("all", bright);
            }
        }

        public void onErrorEvent(int i, int i1) {
            LogUtils.i(AmbientManagerOld.TAG, "onErrorEvent:" + i + "," + i1, false);
        }
    };
    AmbientLightManager mManager;

    public AmbientManagerOld(XUIManager xuiManager) {
        try {
            AmbientLightManager ambientLightManager = (AmbientLightManager) xuiManager.getXUIServiceManager("ambientlight");
            this.mManager = ambientLightManager;
            ambientLightManager.registerListener(this.callback);
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
    }

    public static AmbientManagerOld newInstance(XUIManager xuiManager) {
        LogUtils.i(TAG, "newInstance");
        AmbientManagerOld ambientManagerOld = new AmbientManagerOld(xuiManager);
        if (ambientManagerOld.mManager != null) {
            return ambientManagerOld;
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightEnable(boolean enable) throws XUIServiceNotConnectedException {
        LogUtils.i(TAG, "setAmbientLightEnable:enable:" + enable, false);
        this.mManager.setAmbientLightOpen(enable);
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public boolean getAmbientLightEnable() throws XUIServiceNotConnectedException {
        boolean ambientLightOpen = this.mManager.getAmbientLightOpen();
        LogUtils.i(TAG, "getAmbientLightEnable:result:" + ambientLightOpen, false);
        return ambientLightOpen;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public Set<String> showAmbientLightEffectPartitions() throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public Set<String> showAmbientLightEffects() throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int playAmbientLightEffect(String partition, String effect) throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int playAmbientLightEffect(String partition, AmbientEffect effect) throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int stopAmbientLightEffect() throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightMode(String partition, String mode) throws XUIServiceNotConnectedException {
        LogUtils.i(TAG, "setAmbientLightMode:partition:" + partition + ",mode:" + mode, false);
        this.mManager.setAmbientLightEffectType(mode);
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int subAmbientLightMode(String partition, String mode) throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public String getAmbientLightMode(String partition) throws XUIServiceNotConnectedException {
        String ambientLightEffectType = this.mManager.getAmbientLightEffectType();
        LogUtils.i(TAG, "getAmbientLightMode:partition:" + partition + ",result:" + ambientLightEffectType, false);
        return ambientLightEffectType;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public Map<String, String> getAmbientLightPartitionModes() throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightBright(String partition, int bright) throws XUIServiceNotConnectedException {
        LogUtils.i(TAG, "setAmbientLightBright:partition:" + partition + ",bright:" + bright, false);
        this.mManager.setAmbientLightBright(bright);
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int getAmbientLightBright(String partition) throws XUIServiceNotConnectedException {
        int ambientLightBright = this.mManager.getAmbientLightBright();
        LogUtils.i(TAG, "getAmbientLightBright:partition:" + partition + ",result:" + ambientLightBright, false);
        return ambientLightBright;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public Set<String> showAmbientLightColorTypes() throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightColorType(String partition, String colorType) throws XUIServiceNotConnectedException {
        LogUtils.i(TAG, "setAmbientLightColorType:partition:" + partition + ",colorType:" + colorType, false);
        String ambientLightEffectType = this.mManager.getAmbientLightEffectType();
        if (AtlColorType.Mono.match(colorType)) {
            this.mManager.setDoubleThemeColorEnable(ambientLightEffectType, false);
        } else if (AtlColorType.Double.match(colorType)) {
            this.mManager.setDoubleThemeColorEnable(ambientLightEffectType, true);
        } else {
            throw new RuntimeException("not support");
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public String getAmbientLightColorType(String partition) throws XUIServiceNotConnectedException {
        String name = (this.mManager.getDoubleThemeColorEnable(this.mManager.getAmbientLightEffectType()) ? AtlColorType.Double : AtlColorType.Mono).name();
        LogUtils.i(TAG, "getAmbientLightColorType:partition:" + partition + ",result:" + name, false);
        return name;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightMonoColor(String partition, AmbientColor color) throws XUIServiceNotConnectedException {
        LogUtils.i(TAG, "setAmbientLightMonoColor:partition:" + partition + ",color:" + color, false);
        this.mManager.setAmbientLightMonoColor(this.mManager.getAmbientLightEffectType(), color.predef);
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public AmbientColor getAmbientLightMonoColor(String partition) throws XUIServiceNotConnectedException {
        int ambientLightMonoColor = this.mManager.getAmbientLightMonoColor(this.mManager.getAmbientLightEffectType());
        LogUtils.i(TAG, "getAmbientLightMonoColor:partition:" + partition + ",color:" + ambientLightMonoColor, false);
        return new AmbientColor(false, Integer.toHexString(ambientLightMonoColor));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightDoubleColor(String partition, Pair<AmbientColor, AmbientColor> color) throws XUIServiceNotConnectedException {
        LogUtils.i(TAG, "setAmbientLightDoubleColor:partition:" + partition + ",color:" + color, false);
        this.mManager.setAmbientLightDoubleColor(this.mManager.getAmbientLightEffectType(), ((AmbientColor) color.first).predef, ((AmbientColor) color.second).predef);
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public Pair<AmbientColor, AmbientColor> getAmbientLightDoubleColor(String partition) throws XUIServiceNotConnectedException {
        String ambientLightEffectType = this.mManager.getAmbientLightEffectType();
        int ambientLightDoubleFirstColor = this.mManager.getAmbientLightDoubleFirstColor(ambientLightEffectType);
        int ambientLightDoubleSecondColor = this.mManager.getAmbientLightDoubleSecondColor(ambientLightEffectType);
        LogUtils.i(TAG, "getAmbientLightDoubleColor:partition:" + partition + ",firstColor:" + ambientLightDoubleFirstColor + ",secondColor:" + ambientLightDoubleSecondColor, false);
        return new Pair<>(new AmbientColor(false, Integer.toHexString(ambientLightDoubleFirstColor)), new AmbientColor(false, Integer.toHexString(ambientLightDoubleSecondColor)));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightThemeColor(String partition, String themeColor) throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public String getAmbientLightThemeColor(String partition) throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightRegionColor(String partition, String region, AmbientColor color) throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public AmbientColor getAmbientLightRegionColor(String partition, String region) throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightRegionBright(String partition, String region, int bright) throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int getAmbientLightRegionBright(String partition, String region) throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public boolean isSupportDoubleThemeColor(String mode) throws XUIServiceNotConnectedException {
        boolean isSupportDoubleThemeColor = this.mManager.isSupportDoubleThemeColor(mode);
        LogUtils.i(TAG, "isSupportDoubleThemeColor:mode:" + mode + ",result:" + isSupportDoubleThemeColor, false);
        return isSupportDoubleThemeColor;
    }
}
