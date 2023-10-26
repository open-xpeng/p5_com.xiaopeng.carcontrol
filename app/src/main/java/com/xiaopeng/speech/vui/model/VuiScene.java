package com.xiaopeng.speech.vui.model;

import android.text.TextUtils;
import com.xiaopeng.speech.vui.VuiSceneManager;
import com.xiaopeng.speech.vui.cache.VuiSceneBuildCache;
import com.xiaopeng.speech.vui.cache.VuiSceneCacheFactory;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.List;

/* loaded from: classes2.dex */
public class VuiScene {
    private String appVersion;
    private String displayLocation;
    private List<VuiElement> elements;
    private String packageName;
    private String sceneId;
    private long timestamp;
    private String vuiVersion;

    public String getDisplayLocation() {
        return this.displayLocation;
    }

    public void setDisplayLocation(String str) {
        this.displayLocation = str;
    }

    public String getVuiVersion() {
        return this.vuiVersion;
    }

    public void setVuiVersion(String str) {
        this.vuiVersion = str;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public long getTimeStamp() {
        return this.timestamp;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public List<VuiElement> getElements() {
        return this.elements;
    }

    public String getSceneId() {
        return this.sceneId;
    }

    public void setSceneId(String str) {
        this.sceneId = str;
    }

    public void setElements(List<VuiElement> list) {
        this.elements = list;
    }

    public void setVersion(String str) {
        this.appVersion = str;
    }

    public String getVersion() {
        return this.appVersion;
    }

    public String toString() {
        return "VuiScene{sceneId='" + this.sceneId + "', elements=" + this.elements + ", packageName='" + this.packageName + "', appVersion='" + this.appVersion + "', timestamp=" + this.timestamp + '}';
    }

    private VuiScene(Builder builder) {
        this.sceneId = null;
        this.elements = null;
        this.packageName = null;
        this.appVersion = null;
        this.vuiVersion = "2.0";
        this.displayLocation = VuiConstants.SCREEN_DISPLAY_LF;
        this.sceneId = builder.sceneId;
        this.packageName = builder.packageName;
        this.appVersion = builder.appVersion;
        this.timestamp = builder.timestamp;
        this.displayLocation = VuiConstants.SCREEN_DISPLAY_LF;
        VuiSceneBuildCache vuiSceneBuildCache = (VuiSceneBuildCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
        if (vuiSceneBuildCache != null) {
            String displayCache = vuiSceneBuildCache.getDisplayCache(this.sceneId);
            if (TextUtils.isEmpty(displayCache)) {
                return;
            }
            this.displayLocation = displayCache;
        }
    }

    public VuiScene() {
        this.sceneId = null;
        this.elements = null;
        this.packageName = null;
        this.appVersion = null;
        this.vuiVersion = "2.0";
        this.displayLocation = VuiConstants.SCREEN_DISPLAY_LF;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private long timestamp;
        private String sceneId = null;
        private String packageName = null;
        private String appVersion = null;

        public Builder sceneId(String str) {
            this.sceneId = str;
            return this;
        }

        public Builder packageName(String str) {
            this.packageName = str;
            return this;
        }

        public Builder appVersion(String str) {
            this.appVersion = str;
            return this;
        }

        public Builder timestamp(long j) {
            this.timestamp = j;
            return this;
        }

        public VuiScene build() {
            return new VuiScene(this);
        }
    }

    public void publish() {
        VuiSceneManager.instance().buildScene(this, true, true);
    }
}
