package com.xiaopeng.speech.vui.cache;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class VuiSceneRemoveCache extends VuiSceneCache {
    public VuiSceneRemoveCache() {
        this.mPre = "remove_";
    }

    public List<String> getRemoveCache(String str) {
        if (this.removeElementsMap.containsKey(str)) {
            return this.removeElementsMap.get(str);
        }
        return null;
    }

    @Override // com.xiaopeng.speech.vui.cache.VuiSceneCache
    public void setCache(String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return;
        }
        String[] split = str2.split(",");
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(split));
        this.removeElementsMap.put(str, arrayList);
    }

    public void addRemoveIdToCache(String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return;
        }
        List<String> removeCache = getRemoveCache(str);
        if (removeCache == null) {
            removeCache = new ArrayList<>();
        }
        removeCache.add(str2);
        this.removeElementsMap.put(str, removeCache);
    }

    public void deleteRemoveIdFromCache(String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return;
        }
        List<String> removeCache = getRemoveCache(str);
        if (removeCache != null && removeCache.contains(str2)) {
            removeCache.remove(str2);
        }
        this.removeElementsMap.put(str, removeCache);
    }
}
