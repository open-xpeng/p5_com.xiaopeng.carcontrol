package com.xiaopeng.speech.vui.cache;

import android.text.TextUtils;
import com.xiaopeng.speech.vui.cache.VuiSceneCacheFactory;
import com.xiaopeng.speech.vui.utils.SceneMergeUtils;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public class VuiSceneCache {
    private String TAG = "VuiSceneCache";
    public ConcurrentMap<String, Boolean> mUploadedMap = null;
    public String mPre = null;
    private String BUILD_PRE = "build_";
    private String UPDATE_PRE = "update_";
    private String REMOVE_PRE = "remove_";
    public ConcurrentMap<String, List<VuiElement>> buildElementsMap = new ConcurrentHashMap();
    public ConcurrentMap<String, List<VuiElement>> updateElementsMap = new ConcurrentHashMap();
    public ConcurrentMap<String, List<String>> removeElementsMap = new ConcurrentHashMap();

    public void setUploadedState(String str, boolean z) {
        ConcurrentMap<String, Boolean> concurrentMap;
        if (str == null || (concurrentMap = this.mUploadedMap) == null) {
            return;
        }
        concurrentMap.put(str, Boolean.valueOf(z));
    }

    public boolean getUploadedState(String str) {
        ConcurrentMap<String, Boolean> concurrentMap;
        Boolean bool;
        if (str == null || (concurrentMap = this.mUploadedMap) == null || !concurrentMap.containsKey(str) || (bool = this.mUploadedMap.get(str)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public void removeUploadState(String str) {
        ConcurrentMap<String, Boolean> concurrentMap;
        if (str == null || (concurrentMap = this.mUploadedMap) == null || !concurrentMap.containsKey(str)) {
            return;
        }
        this.mUploadedMap.remove(str);
    }

    public synchronized void setCache(String str, String str2) {
    }

    public synchronized String getDisplayCache(String str) {
        return "";
    }

    public synchronized void setCache(String str, List<VuiElement> list) {
        ConcurrentMap<String, List<VuiElement>> concurrentMap;
        if (list == null && str == null) {
            return;
        }
        if (this.BUILD_PRE.equals(this.mPre)) {
            ConcurrentMap<String, List<VuiElement>> concurrentMap2 = this.buildElementsMap;
            if (concurrentMap2 != null) {
                concurrentMap2.put(str, list);
            }
        } else if (this.UPDATE_PRE.equals(this.mPre) && (concurrentMap = this.updateElementsMap) != null) {
            concurrentMap.put(str, list);
        }
    }

    public synchronized List<VuiElement> getCache(String str) {
        ConcurrentMap<String, List<VuiElement>> concurrentMap;
        if (str == null) {
            return null;
        }
        if (this.BUILD_PRE.equals(this.mPre)) {
            ConcurrentMap<String, List<VuiElement>> concurrentMap2 = this.buildElementsMap;
            if (concurrentMap2 != null && concurrentMap2.containsKey(str)) {
                return this.buildElementsMap.get(str);
            }
        } else if (this.UPDATE_PRE.equals(this.mPre) && (concurrentMap = this.updateElementsMap) != null && concurrentMap.containsKey(str)) {
            return this.updateElementsMap.get(str);
        }
        return null;
    }

    public VuiElement getVuiElementById(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        return getTagetElement(getCache(str), str2);
    }

    private VuiElement getTagetElement(List<VuiElement> list, String str) {
        if (TextUtils.isEmpty(str) || list == null) {
            return null;
        }
        VuiElement vuiElement = new VuiElement();
        vuiElement.setElements(list);
        vuiElement.setId("ab123");
        return findNode(vuiElement, str);
    }

    private VuiElement findNode(VuiElement vuiElement, String str) {
        if (vuiElement != null && !TextUtils.isEmpty(str)) {
            if (str.equals(vuiElement.getId())) {
                return vuiElement;
            }
            List<VuiElement> elements = vuiElement.getElements();
            if (elements != null) {
                for (VuiElement vuiElement2 : elements) {
                    VuiElement findNode = findNode(vuiElement2, str);
                    if (findNode != null) {
                        return findNode;
                    }
                }
            }
        }
        return null;
    }

    public synchronized List<VuiElement> getFusionCache(String str, List<VuiElement> list, boolean z) {
        if (!TextUtils.isEmpty(str) && list != null && list.size() != 0) {
            List<VuiElement> cache = getCache(str);
            if (cache != null) {
                return SceneMergeUtils.merge(cache, list, z);
            }
            return list;
        }
        return null;
    }

    public synchronized List<VuiElement> getUpdateFusionCache(String str, List<VuiElement> list, boolean z) {
        if (!TextUtils.isEmpty(str) && list != null && list.size() != 0) {
            List<VuiElement> cache = getCache(str);
            if (cache != null) {
                return SceneMergeUtils.updateMerge(cache, list, z);
            }
            return list;
        }
        return null;
    }

    public synchronized List<VuiElement> addElementGroupToCache(String str, List<VuiElement> list) {
        if (!TextUtils.isEmpty(str) && list != null) {
            List<VuiElement> cache = getCache(str);
            if (cache != null) {
                cache.addAll(list);
            }
            return cache;
        }
        return null;
    }

    public synchronized List<VuiElement> removeElementFromCache(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            List<VuiElement> cache = getCache(str);
            if (cache != null) {
                SceneMergeUtils.removeElementById(cache, Arrays.asList(str2.split(",")));
            }
            return cache;
        }
        return null;
    }

    public int getFusionType(String str) {
        List<String> removeCache;
        List<VuiElement> cache;
        if (str == null) {
            return VuiSceneCacheFactory.CacheType.DEFAULT.getType();
        }
        VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
        if (sceneCache != null && !sceneCache.getUploadedState(str)) {
            return VuiSceneCacheFactory.CacheType.BUILD.getType();
        }
        VuiSceneCache sceneCache2 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.UPDATE.getType());
        if (sceneCache2 != null && (cache = sceneCache2.getCache(str)) != null && !cache.isEmpty()) {
            return VuiSceneCacheFactory.CacheType.UPDATE.getType();
        }
        VuiSceneCache sceneCache3 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.REMOVE.getType());
        if (sceneCache3 != null && (removeCache = ((VuiSceneRemoveCache) sceneCache3).getRemoveCache(str)) != null && !removeCache.isEmpty()) {
            return VuiSceneCacheFactory.CacheType.REMOVE.getType();
        }
        VuiSceneCache sceneCache4 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.DISPLAY_LOCATION.getType());
        if (sceneCache4 != null && !TextUtils.isEmpty(sceneCache4.getDisplayCache(str))) {
            return VuiSceneCacheFactory.CacheType.DISPLAY_LOCATION.getType();
        }
        return VuiSceneCacheFactory.CacheType.DEFAULT.getType();
    }

    public void removeCache(String str) {
        if (str == null) {
            return;
        }
        if (this.BUILD_PRE.equals(this.mPre)) {
            if (this.buildElementsMap.containsKey(str)) {
                this.buildElementsMap.remove(str);
            }
        } else if (this.UPDATE_PRE.equals(this.mPre)) {
            if (this.updateElementsMap.containsKey(str)) {
                this.updateElementsMap.remove(str);
            }
        } else if (this.REMOVE_PRE.equals(this.mPre) && this.removeElementsMap.containsKey(str)) {
            this.removeElementsMap.remove(str);
        }
    }
}
