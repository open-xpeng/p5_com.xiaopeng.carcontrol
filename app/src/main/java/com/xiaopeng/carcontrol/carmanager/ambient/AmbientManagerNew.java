package com.xiaopeng.carcontrol.carmanager.ambient;

import android.util.Pair;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.light.AtlEffect;
import com.xiaopeng.xuimanager.XUIManager;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.ambient.AmbientManager;
import com.xiaopeng.xuimanager.ambient.Color;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class AmbientManagerNew extends AbstractAmbientManager {
    private static final String TAG = "AmbientManagerNew";
    AmbientManager.EventListener callback = new AmbientManager.EventListener() { // from class: com.xiaopeng.carcontrol.carmanager.ambient.AmbientManagerNew.1
        public void onErrorPlay(String partition, String effect) {
            LogUtils.i(AmbientManagerNew.TAG, "onErrorPlay:partition:" + partition + ",effect:" + effect, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerNew.this.mListeners) {
                ambientEventListener.onErrorPlay(partition, effect);
            }
        }

        public void onErrorStop(String partition, String effect) {
            LogUtils.i(AmbientManagerNew.TAG, "onErrorStop:partition:" + partition + ",effect:" + effect, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerNew.this.mListeners) {
                ambientEventListener.onErrorStop(partition, effect);
            }
        }

        public void onChangeMode(String partition, String mode) {
            LogUtils.i(AmbientManagerNew.TAG, "onChangeMode:partition:" + partition + ",mode:" + mode, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerNew.this.mListeners) {
                ambientEventListener.onChangeMode(partition, mode);
            }
        }

        public void onErrorSet(String partition, String mode) {
            LogUtils.i(AmbientManagerNew.TAG, "onErrorSet:partition:" + partition + ",mode:" + mode, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerNew.this.mListeners) {
                ambientEventListener.onErrorSet(partition, mode);
            }
        }

        public void onErrorSub(String partition, String mode) {
            LogUtils.i(AmbientManagerNew.TAG, "onErrorSub:partition:" + partition + ",mode:" + mode, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerNew.this.mListeners) {
                ambientEventListener.onErrorSub(partition, mode);
            }
        }

        public void onChangeBright(String partition, int bright) {
            LogUtils.i(AmbientManagerNew.TAG, "onChangeBright:partition:" + partition + ",bright:" + bright, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerNew.this.mListeners) {
                ambientEventListener.onChangeBright(partition, bright);
            }
        }

        public void onChangeColorType(String partition, String type) {
            LogUtils.i(AmbientManagerNew.TAG, "onChangeColorType:partition:" + partition + ",type:" + type, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerNew.this.mListeners) {
                ambientEventListener.onChangeColorType(partition, type);
            }
        }

        public void onChangeMonoColor(String partition, Color color) {
            LogUtils.i(AmbientManagerNew.TAG, "onChangeMonoColor:partition:" + partition + ",color:" + color, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerNew.this.mListeners) {
                ambientEventListener.onChangeMonoColor(partition, AmbientManagerNew.transform(color));
            }
        }

        public void onChangeDoubleColor(String partition, Pair<Color, Color> color) {
            LogUtils.i(AmbientManagerNew.TAG, "onChangeDoubleColor:partition:" + partition + ",color:" + color, false);
            for (AmbientEventListener ambientEventListener : AmbientManagerNew.this.mListeners) {
                ambientEventListener.onChangeDoubleColor(partition, color == null ? null : new Pair<>(AmbientManagerNew.transform((Color) color.first), AmbientManagerNew.transform((Color) color.second)));
            }
        }
    };
    AmbientManager mManager;

    public AmbientManagerNew(XUIManager xuiManager) {
        try {
            AmbientManager ambientManager = (AmbientManager) xuiManager.getXUIServiceManager("ambient");
            this.mManager = ambientManager;
            ambientManager.registerListener(this.callback);
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
    }

    public static AmbientManagerNew newInstance(XUIManager xuiManager) {
        LogUtils.i(TAG, "newInstance");
        AmbientManagerNew ambientManagerNew = new AmbientManagerNew(xuiManager);
        if (ambientManagerNew.mManager != null) {
            return ambientManagerNew;
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightEnable(boolean enable) throws XUIServiceNotConnectedException {
        int ambientLightEnable = this.mManager.setAmbientLightEnable(enable);
        LogUtils.i(TAG, "setAmbientLightEnable:enable:" + enable + ",result:" + ambientLightEnable, false);
        return ambientLightEnable;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public boolean getAmbientLightEnable() throws XUIServiceNotConnectedException {
        boolean ambientLightEnable = this.mManager.getAmbientLightEnable();
        LogUtils.i(TAG, "getAmbientLightEnable:result:" + ambientLightEnable, false);
        return ambientLightEnable;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public Set<String> showAmbientLightEffectPartitions() throws XUIServiceNotConnectedException {
        LogUtils.i(TAG, "showAmbientLightEffectPartitions", false);
        return this.mManager.showAmbientLightEffectPartitions();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public Set<String> showAmbientLightEffects() throws XUIServiceNotConnectedException {
        LogUtils.i(TAG, "showAmbientLightEffects", false);
        return this.mManager.showAmbientLightEffects();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int playAmbientLightEffect(String partition, String effect) throws XUIServiceNotConnectedException {
        int playAmbientLightEffect = this.mManager.playAmbientLightEffect(partition, effect);
        LogUtils.i(TAG, "playAmbientLightEffect:partition:" + partition + ",effect:" + effect + "result:" + playAmbientLightEffect, false);
        return playAmbientLightEffect;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int playAmbientLightEffect(String partition, AmbientEffect effect) throws XUIServiceNotConnectedException {
        throw new RuntimeException("not support");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int stopAmbientLightEffect() throws XUIServiceNotConnectedException {
        int stopAmbientLightEffect = this.mManager.stopAmbientLightEffect();
        LogUtils.i(TAG, "stopAmbientLightEffect:result:" + stopAmbientLightEffect, false);
        return stopAmbientLightEffect;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightMode(String partition, String mode) throws XUIServiceNotConnectedException {
        int ambientLightMode = this.mManager.setAmbientLightMode(partition, mode);
        LogUtils.i(TAG, "setAmbientLightMode:partition:" + partition + ",mode:" + mode + ",result:" + ambientLightMode, false);
        return ambientLightMode;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int subAmbientLightMode(String partition, String mode) throws XUIServiceNotConnectedException {
        int subAmbientLightMode = this.mManager.subAmbientLightMode(partition, mode);
        LogUtils.i(TAG, "subAmbientLightMode:partition:" + partition + ",mode:" + mode + ",result:" + subAmbientLightMode, false);
        return subAmbientLightMode;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public String getAmbientLightMode(String partition) throws XUIServiceNotConnectedException {
        String ambientLightMode = this.mManager.getAmbientLightMode(partition);
        LogUtils.i(TAG, "getAmbientLightMode:partition:" + partition + ",result:" + ambientLightMode, false);
        return ambientLightMode;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public Map<String, String> getAmbientLightPartitionModes() throws XUIServiceNotConnectedException {
        LogUtils.i(TAG, "getAmbientLightPartitionModes", false);
        return this.mManager.getAmbientLightPartitionModes();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightBright(String partition, int bright) throws XUIServiceNotConnectedException {
        int ambientLightBright = this.mManager.setAmbientLightBright(partition, bright);
        LogUtils.i(TAG, "setAmbientLightBright:partition:" + partition + ",bright:" + bright + ",result:" + ambientLightBright, false);
        return ambientLightBright;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int getAmbientLightBright(String partition) throws XUIServiceNotConnectedException {
        int ambientLightBright = this.mManager.getAmbientLightBright(partition);
        LogUtils.i(TAG, "getAmbientLightBright:partition:" + partition + "result:" + ambientLightBright, false);
        return ambientLightBright;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public Set<String> showAmbientLightColorTypes() throws XUIServiceNotConnectedException {
        LogUtils.i(TAG, "showAmbientLightColorTypes", false);
        return this.mManager.showAmbientLightColorTypes();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightColorType(String partition, String colorType) throws XUIServiceNotConnectedException {
        int ambientLightColorType = this.mManager.setAmbientLightColorType(partition, colorType);
        LogUtils.i(TAG, "setAmbientLightColorType:partition:" + partition + ",colorType:" + colorType + ",result:" + ambientLightColorType, false);
        return ambientLightColorType;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public String getAmbientLightColorType(String partition) throws XUIServiceNotConnectedException {
        String ambientLightColorType = this.mManager.getAmbientLightColorType(partition);
        LogUtils.i(TAG, "getAmbientLightColorType:partition:" + partition + ",result:" + ambientLightColorType, false);
        return ambientLightColorType;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightMonoColor(String partition, AmbientColor color) throws XUIServiceNotConnectedException {
        int ambientLightMonoColor = this.mManager.setAmbientLightMonoColor(partition, transform(color));
        LogUtils.i(TAG, "setAmbientLightMonoColor:partition:" + partition + ",color:" + color + ",result:" + ambientLightMonoColor, false);
        return ambientLightMonoColor;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public AmbientColor getAmbientLightMonoColor(String partition) throws XUIServiceNotConnectedException {
        AmbientColor transform = transform(this.mManager.getAmbientLightMonoColor(partition));
        LogUtils.i(TAG, "getAmbientLightMonoColor:partition:" + partition + ",result:" + transform, false);
        return transform;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightDoubleColor(String partition, Pair<AmbientColor, AmbientColor> color) throws XUIServiceNotConnectedException {
        int ambientLightDoubleColor = this.mManager.setAmbientLightDoubleColor(partition, color == null ? null : new Pair(transform((AmbientColor) color.first), transform((AmbientColor) color.second)));
        LogUtils.i(TAG, "setAmbientLightDoubleColor:partition:" + partition + ",color:" + color + ",result:" + ambientLightDoubleColor, false);
        return ambientLightDoubleColor;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public Pair<AmbientColor, AmbientColor> getAmbientLightDoubleColor(String partition) throws XUIServiceNotConnectedException {
        Pair ambientLightDoubleColor = this.mManager.getAmbientLightDoubleColor(partition);
        LogUtils.i(TAG, "getAmbientLightDoubleColor:partition:" + partition + ",color:" + ambientLightDoubleColor, false);
        if (ambientLightDoubleColor == null) {
            return null;
        }
        return new Pair<>(transform((Color) ambientLightDoubleColor.first), transform((Color) ambientLightDoubleColor.second));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightThemeColor(String partition, String themeColor) throws XUIServiceNotConnectedException {
        int ambientLightThemeColor = this.mManager.setAmbientLightThemeColor(partition, themeColor);
        LogUtils.i(TAG, "setAmbientLightThemeColor:partition:" + partition + ",themeColor:" + themeColor + ",result:" + ambientLightThemeColor, false);
        return ambientLightThemeColor;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public String getAmbientLightThemeColor(String partition) throws XUIServiceNotConnectedException {
        String ambientLightThemeColor = this.mManager.getAmbientLightThemeColor(partition);
        LogUtils.i(TAG, "getAmbientLightThemeColor:partition:" + partition + ",result:" + ambientLightThemeColor, false);
        return ambientLightThemeColor;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightRegionColor(String partition, String region, AmbientColor color) throws XUIServiceNotConnectedException {
        int ambientLightRegionColor = this.mManager.setAmbientLightRegionColor(partition, region, transform(color));
        LogUtils.i(TAG, "setAmbientLightRegionColor:partition:" + partition + ",region:" + region + ",color:" + color + ",result:" + ambientLightRegionColor, false);
        return ambientLightRegionColor;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public AmbientColor getAmbientLightRegionColor(String partition, String region) throws XUIServiceNotConnectedException {
        AmbientColor transform = transform(this.mManager.getAmbientLightRegionColor(partition, region));
        LogUtils.i(TAG, "getAmbientLightRegionColor:partition:" + partition + ",region:" + region + ",result:" + transform, false);
        return transform;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int setAmbientLightRegionBright(String partition, String region, int bright) throws XUIServiceNotConnectedException {
        int ambientLightRegionBright = this.mManager.setAmbientLightRegionBright(partition, region, bright);
        LogUtils.i(TAG, "setAmbientLightRegionBright:partition:" + partition + ",region:" + region + ",bright:" + bright + ",result:" + ambientLightRegionBright, false);
        return ambientLightRegionBright;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public int getAmbientLightRegionBright(String partition, String region) throws XUIServiceNotConnectedException {
        int ambientLightRegionBright = this.mManager.getAmbientLightRegionBright(partition, region);
        LogUtils.i(TAG, "getAmbientLightRegionBright:partition:" + partition + ",region:" + region + ",result:" + ambientLightRegionBright, false);
        return ambientLightRegionBright;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.ambient.AbstractAmbientManager
    public boolean isSupportDoubleThemeColor(String mode) throws XUIServiceNotConnectedException {
        boolean z = AtlEffect.None.match(mode) || AtlEffect.Breathing.match(mode);
        LogUtils.i(TAG, "isSupportDoubleThemeColor:mode:" + mode + ",result:" + z, false);
        return z;
    }

    static Color transform(AmbientColor ac) {
        if (ac == null) {
            return null;
        }
        return new Color(ac.hasRgb, ac.toHexString());
    }

    static AmbientColor transform(Color ac) {
        if (ac == null) {
            return null;
        }
        return new AmbientColor(ac.hasRgb, ac.toHexString());
    }
}
