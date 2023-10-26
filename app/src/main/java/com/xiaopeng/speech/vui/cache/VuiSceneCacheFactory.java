package com.xiaopeng.speech.vui.cache;

/* loaded from: classes2.dex */
public class VuiSceneCacheFactory {
    private static final String TAG = "VuiSceneCacheFactory";
    private VuiSceneBuildCache mBuildCache;
    private VuiDisplayLocationInfoCache mDisplayLocationCache;
    private VuiSceneRemoveCache mRemoveCache;
    private VuiSceneCache mSceneCache;
    private VuiSceneUpdateCache mUpdateCache;

    /* loaded from: classes2.dex */
    public enum CacheType {
        BUILD(0),
        UPDATE(1),
        ADD(2),
        REMOVE(3),
        DEFAULT(4),
        DISPLAY_LOCATION(5);
        
        private int type;

        CacheType(int i) {
            this.type = i;
        }

        public int getType() {
            return this.type;
        }
    }

    private VuiSceneCacheFactory() {
        this.mBuildCache = null;
        this.mUpdateCache = null;
        this.mRemoveCache = null;
        this.mSceneCache = null;
    }

    public static final VuiSceneCacheFactory instance() {
        return Holder.Instance;
    }

    /* loaded from: classes2.dex */
    private static class Holder {
        private static final VuiSceneCacheFactory Instance = new VuiSceneCacheFactory();

        private Holder() {
        }
    }

    public VuiSceneCache getSceneCache(int i) {
        if (i == CacheType.BUILD.getType()) {
            if (this.mBuildCache == null) {
                this.mBuildCache = new VuiSceneBuildCache();
            }
            return this.mBuildCache;
        } else if (i == CacheType.UPDATE.getType()) {
            if (this.mUpdateCache == null) {
                this.mUpdateCache = new VuiSceneUpdateCache();
            }
            return this.mUpdateCache;
        } else if (i == CacheType.REMOVE.getType()) {
            if (this.mRemoveCache == null) {
                this.mRemoveCache = new VuiSceneRemoveCache();
            }
            return this.mRemoveCache;
        } else if (i == CacheType.DISPLAY_LOCATION.getType()) {
            if (this.mDisplayLocationCache == null) {
                this.mDisplayLocationCache = new VuiDisplayLocationInfoCache();
            }
            return this.mDisplayLocationCache;
        } else {
            if (this.mSceneCache == null) {
                this.mSceneCache = new VuiSceneCache();
            }
            return this.mSceneCache;
        }
    }

    public void removeAllCache(String str) {
        if (str == null) {
            return;
        }
        VuiSceneBuildCache vuiSceneBuildCache = this.mBuildCache;
        if (vuiSceneBuildCache != null) {
            vuiSceneBuildCache.removeCache(str);
            this.mBuildCache.removeUploadState(str);
            this.mBuildCache.removeDisplayLocation(str);
        }
        VuiSceneUpdateCache vuiSceneUpdateCache = this.mUpdateCache;
        if (vuiSceneUpdateCache != null) {
            vuiSceneUpdateCache.removeCache(str);
        }
        VuiSceneRemoveCache vuiSceneRemoveCache = this.mRemoveCache;
        if (vuiSceneRemoveCache != null) {
            vuiSceneRemoveCache.removeCache(str);
        }
        VuiDisplayLocationInfoCache vuiDisplayLocationInfoCache = this.mDisplayLocationCache;
        if (vuiDisplayLocationInfoCache != null) {
            vuiDisplayLocationInfoCache.removeDisplayCache(str);
        }
    }

    public void removeOtherCache(String str) {
        if (str == null) {
            return;
        }
        VuiSceneUpdateCache vuiSceneUpdateCache = this.mUpdateCache;
        if (vuiSceneUpdateCache != null) {
            vuiSceneUpdateCache.removeCache(str);
        }
        VuiSceneRemoveCache vuiSceneRemoveCache = this.mRemoveCache;
        if (vuiSceneRemoveCache != null) {
            vuiSceneRemoveCache.removeCache(str);
        }
        VuiDisplayLocationInfoCache vuiDisplayLocationInfoCache = this.mDisplayLocationCache;
        if (vuiDisplayLocationInfoCache != null) {
            vuiDisplayLocationInfoCache.removeDisplayCache(str);
        }
    }
}
