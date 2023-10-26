package com.xiaopeng.lib.framework.aiassistantmodule.sensor.item;

import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor;

/* loaded from: classes2.dex */
public abstract class CDUSensor extends BaseSensor {
    private String frontPackage;
    private boolean hasOpenBioMode;
    private boolean hasOpenFastFridgeMode;
    private boolean hasOpenInvisibleMode;
    private boolean hasOpenVentilateMode;
    private boolean hasOpenWaitMode;
    private boolean isBioMode;
    private boolean isFastFridgeMode;
    private boolean isInvisibleMode;
    private boolean isVentilateMode;
    private boolean isWaitMode;
    private String localPackages;

    public String getLocalPackages() {
        return this.localPackages;
    }

    public void setLocalPackages(String str) {
        this.localPackages = str;
    }

    public String getFrontPackage() {
        return this.frontPackage;
    }

    public void setFrontPackage(String str) {
        this.frontPackage = str;
    }

    public boolean isWaitMode() {
        return this.isWaitMode;
    }

    public void setWaitMode(boolean z) {
        this.isWaitMode = z;
    }

    public boolean isHasOpenWaitMode() {
        return this.hasOpenWaitMode;
    }

    public void setHasOpenWaitMode(boolean z) {
        this.hasOpenWaitMode = z;
    }

    public boolean isBioMode() {
        return this.isBioMode;
    }

    public void setBioMode(boolean z) {
        this.isBioMode = z;
    }

    public boolean isHasOpenBioMode() {
        return this.hasOpenBioMode;
    }

    public void setHasOpenBioMode(boolean z) {
        this.hasOpenBioMode = z;
    }

    public boolean isVentilateMode() {
        return this.isVentilateMode;
    }

    public void setVentilateMode(boolean z) {
        this.isVentilateMode = z;
    }

    public boolean isHasOpenVentilateMode() {
        return this.hasOpenVentilateMode;
    }

    public void setHasOpenVentilateMode(boolean z) {
        this.hasOpenVentilateMode = z;
    }

    public boolean isFastFridgeMode() {
        return this.isFastFridgeMode;
    }

    public void setFastFridgeMode(boolean z) {
        this.isFastFridgeMode = z;
    }

    public boolean isHasOpenFastFridgeMode() {
        return this.hasOpenFastFridgeMode;
    }

    public void setHasOpenFastFridgeMode(boolean z) {
        this.hasOpenFastFridgeMode = z;
    }

    public boolean isInvisibleMode() {
        return this.isInvisibleMode;
    }

    public void setInvisibleMode(boolean z) {
        this.isInvisibleMode = z;
    }

    public boolean isHasOpenInvisibleMode() {
        return this.hasOpenInvisibleMode;
    }

    public void setHasOpenInvisibleMode(boolean z) {
        this.hasOpenInvisibleMode = z;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.BaseSensor
    public String sensorName() {
        return CDUSensor.class.getSimpleName();
    }
}
