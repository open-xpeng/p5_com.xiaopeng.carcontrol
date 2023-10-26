package com.xiaopeng.speech.vui.utils;

import android.text.TextUtils;
import com.xiaopeng.lludancemanager.Constant;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SceneMergeUtils {
    public static synchronized List<VuiElement> updateMerge(List<VuiElement> list, List<VuiElement> list2, boolean z) {
        synchronized (SceneMergeUtils.class) {
            List<VuiElement> merge = merge(list, list2, z);
            if (merge != null && !merge.isEmpty()) {
                return duplicateRemoval(merge);
            }
            return merge;
        }
    }

    private static List<VuiElement> duplicateRemoval(List<VuiElement> list) {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        VuiElement vuiElement = new VuiElement();
        vuiElement.setElements(list);
        vuiElement.setId(Constant.DEFAULT_ERROR_RSC_ID);
        findNode(vuiElement, null, hashMap, hashMap2);
        return vuiElement.getElements();
    }

    private static void findNode(VuiElement vuiElement, VuiElement vuiElement2, Map<String, VuiElement> map, Map<String, VuiElement> map2) {
        if (vuiElement == null) {
            return;
        }
        if (!TextUtils.isEmpty(vuiElement.getId())) {
            if (map.containsKey(vuiElement.getId())) {
                VuiElement vuiElement3 = map.get(vuiElement.getId());
                if (vuiElement3 == null) {
                    return;
                }
                if (vuiElement.getTimestamp() >= vuiElement3.getTimestamp()) {
                    VuiElement vuiElement4 = map2.get(vuiElement.getId());
                    if (vuiElement4 == null) {
                        return;
                    }
                    if (vuiElement4.getElements() != null && !vuiElement4.getElements().isEmpty()) {
                        vuiElement4.getElements().remove(map.get(vuiElement.getId()));
                    }
                    map.put(vuiElement.getId(), vuiElement);
                    map2.put(vuiElement.getId(), vuiElement2);
                }
            } else {
                map.put(vuiElement.getId(), vuiElement);
                map2.put(vuiElement.getId(), vuiElement2);
            }
        }
        List<VuiElement> elements = vuiElement.getElements();
        if (elements != null) {
            for (int i = 0; i < elements.size(); i++) {
                findNode(elements.get(i), vuiElement, map, map2);
            }
        }
    }

    public static synchronized List<VuiElement> merge(List<VuiElement> list, List<VuiElement> list2, boolean z) {
        synchronized (SceneMergeUtils.class) {
            if (list2 != null) {
                if (!list2.isEmpty()) {
                    if (list != null && !list.isEmpty()) {
                        ArrayList arrayList = new ArrayList();
                        for (VuiElement vuiElement : list2) {
                            mergeElement(list, vuiElement, arrayList, z);
                        }
                        if (list2.size() != arrayList.size() && list2.size() > arrayList.size()) {
                            for (VuiElement vuiElement2 : list2) {
                                if (vuiElement2.getId() != null && !arrayList.contains(vuiElement2.getId())) {
                                    list.add(vuiElement2);
                                }
                            }
                        }
                        return list;
                    }
                    return list2;
                }
            }
            return list;
        }
    }

    public static synchronized List<VuiElement> removeElementById(List<VuiElement> list, List<String> list2) {
        synchronized (SceneMergeUtils.class) {
            if (list != null) {
                if (!list.isEmpty()) {
                    if (list2 != null && !list2.isEmpty()) {
                        for (String str : list2) {
                            removeElement(list, str);
                        }
                        return list;
                    }
                    return list;
                }
            }
            return list;
        }
    }

    public static String removeElementById(String str, List<String> list) {
        if (TextUtils.isEmpty(str) || list == null || list.isEmpty()) {
            return str;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(VuiConstants.SCENE_ELEMENTS)) {
                JSONArray jSONArray = jSONObject.getJSONArray(VuiConstants.SCENE_ELEMENTS);
                for (String str2 : list) {
                    removeElement(jSONArray, str2);
                }
                return String.valueOf(jSONArray);
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void removeElement(List<VuiElement> list, String str) {
        if (TextUtils.isEmpty(str) || list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            VuiElement vuiElement = list.get(i);
            if (vuiElement != null) {
                if (str.equals(vuiElement.getId())) {
                    list.remove(i);
                    return;
                } else if (vuiElement.getElements() != null && !vuiElement.getElements().isEmpty()) {
                    removeElement(vuiElement.getElements(), str);
                }
            }
        }
    }

    private static void mergeElement(List<VuiElement> list, VuiElement vuiElement, List<String> list2, boolean z) {
        if (vuiElement == null || list == null || vuiElement.getId() == null) {
            return;
        }
        for (VuiElement vuiElement2 : list) {
            if (vuiElement.getId().equals(vuiElement2.getId())) {
                int indexOf = list.indexOf(vuiElement2);
                if (z && vuiElement.getElements() == null && vuiElement2.getElements() != null) {
                    vuiElement.setElements(vuiElement2.getElements());
                }
                list.set(indexOf, vuiElement);
                list2.add(vuiElement.getId());
                return;
            } else if (vuiElement2.getElements() != null && !vuiElement2.getElements().isEmpty()) {
                mergeElement(vuiElement2.getElements(), vuiElement, list2, z);
            }
        }
    }

    private static void removeElement(JSONArray jSONArray, String str) throws Exception {
        if (str == null || jSONArray == null) {
            return;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (jSONObject != null && jSONObject.has("id")) {
                if (!TextUtils.isEmpty(jSONObject.optString("id")) && jSONObject.optString("id").equals(str)) {
                    jSONArray.remove(i);
                    return;
                } else if (jSONObject.has(VuiConstants.SCENE_ELEMENTS) && jSONObject.optJSONArray(VuiConstants.SCENE_ELEMENTS).length() > 0) {
                    removeElement(jSONObject.optJSONArray(VuiConstants.SCENE_ELEMENTS), str);
                }
            }
        }
    }
}
