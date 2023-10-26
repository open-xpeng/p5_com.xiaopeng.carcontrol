package com.xiaopeng.speech.vui.cache;

import android.text.TextUtils;
import com.xiaopeng.speech.vui.utils.LogUtils;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public class VuiDisplayLocationInfoCache extends VuiSceneCache {
    private ConcurrentMap<String, String> mDisplayLocations = new ConcurrentHashMap();
    private boolean isSending = false;

    @Override // com.xiaopeng.speech.vui.cache.VuiSceneCache
    public synchronized void setCache(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            LogUtils.e("VuiDisplayLocationInfoCache", "sceneId is null");
        } else if (TextUtils.isEmpty(str2)) {
            LogUtils.e("VuiDisplayLocationInfoCache", "displayLocation is null");
        } else {
            LogUtils.i("VuiDisplayLocationInfoCache", "sceneid:" + str + ",displayLocation:" + str2);
            this.mDisplayLocations.put(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.vui.cache.VuiSceneCache
    public synchronized String getDisplayCache(String str) {
        return this.mDisplayLocations.get(str);
    }

    public void removeDisplayCache(String str) {
        if (this.mDisplayLocations.containsKey(str)) {
            this.mDisplayLocations.remove(str);
            LogUtils.i("VuiDisplayLocationInfoCache", "removeDisplayCache sceneid:" + str);
        }
    }

    public void setSendState(boolean z) {
        this.isSending = z;
    }

    public boolean isSending() {
        return this.isSending;
    }
}
