package com.xiaopeng.lib.apirouter.server;

import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public class VuiUnityObserver_Manifest {
    public static final String DESCRIPTOR = "com.xiaopeng.carcontrol.VuiUnityObserver";
    public static final int TRANSACTION_getElementState = 0;
    public static final int TRANSACTION_onEvent = 1;

    public static String toJsonManifest() {
        return "{\"authority\":\"com.xiaopeng.carcontrol.VuiUnityObserver\",\"DESCRIPTOR\":\"com.xiaopeng.carcontrol.VuiUnityObserver\",\"TRANSACTION\":[{\"path\":\"getElementState\",\"METHOD\":\"getElementState\",\"ID\":0,\"parameter\":[{\"alias\":\"sceneId\",\"name\":\"sceneId\"},{\"alias\":\"elementId\",\"name\":\"elementId\"}]},{\"path\":\"onEvent\",\"METHOD\":\"onEvent\",\"ID\":1,\"parameter\":[{\"alias\":\"event\",\"name\":\"event\"},{\"alias\":\"data\",\"name\":\"data\"}]}]}";
    }

    public static Set<String> getKey() {
        HashSet hashSet = new HashSet(2);
        hashSet.add("VuiUnityObserver");
        return hashSet;
    }
}
