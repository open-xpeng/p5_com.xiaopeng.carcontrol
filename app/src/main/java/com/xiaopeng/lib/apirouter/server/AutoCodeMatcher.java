package com.xiaopeng.lib.apirouter.server;

import android.os.IBinder;
import android.util.Log;
import android.util.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
class AutoCodeMatcher {
    private static HashMap<String, Pair<IBinder, String>> mapping;
    private static List<IManifestHandler> sManifestHandlerList = new LinkedList();
    private static Object sLock = new Object();

    public Pair<IBinder, String> match(String str) {
        if (mapping == null) {
            mapping = ManifestHelperMapping.reflectMapping();
            initManifestHandler();
        }
        HashMap<String, Pair<IBinder, String>> hashMap = mapping;
        Pair<IBinder, String> pair = hashMap == null ? null : hashMap.get(str);
        return pair == null ? new Pair<>(null, null) : pair;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void addManifestHandler(IManifestHandler iManifestHandler) {
        synchronized (sLock) {
            if (!sManifestHandlerList.contains(iManifestHandler)) {
                sManifestHandlerList.add(iManifestHandler);
            }
        }
    }

    private void initManifestHandler() {
        synchronized (sLock) {
            if (!sManifestHandlerList.isEmpty()) {
                for (IManifestHandler iManifestHandler : sManifestHandlerList) {
                    initManifestHandler(iManifestHandler);
                }
            }
        }
    }

    private void initManifestHandler(IManifestHandler iManifestHandler) {
        IManifestHelper[] manifestHelpers;
        if (iManifestHandler == null || (manifestHelpers = iManifestHandler.getManifestHelpers()) == null || manifestHelpers.length == 0) {
            return;
        }
        if (mapping == null) {
            mapping = new HashMap<>();
        }
        HashMap<String, Pair<IBinder, String>> hashMap = mapping;
        for (IManifestHelper iManifestHelper : manifestHelpers) {
            try {
                HashMap<String, Pair<IBinder, String>> mapping2 = iManifestHelper.getMapping();
                if (mapping2 != null && !mapping2.isEmpty()) {
                    hashMap.putAll(mapping2);
                }
            } catch (Exception e) {
                Log.e("AutoCodeMatcher", "initManifestHandler:" + iManifestHelper.getClass(), e);
            }
        }
    }
}
