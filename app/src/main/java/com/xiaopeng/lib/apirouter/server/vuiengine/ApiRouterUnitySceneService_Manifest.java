package com.xiaopeng.lib.apirouter.server.vuiengine;

import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public class ApiRouterUnitySceneService_Manifest {
    public static final String DESCRIPTOR = "com.xiaopeng.speech.apirouter.ApiRouterUnitySceneService";
    public static final int TRANSACTION_getElementState = 0;
    public static final int TRANSACTION_onEvent = 1;
    public static final int TRANSACTION_onVuiQuery = 2;

    public static String toJsonManifest() {
        return "{\"authority\":\"com.xiaopeng.speech.apirouter.ApiRouterUnitySceneService\",\"DESCRIPTOR\":\"com.xiaopeng.speech.apirouter.ApiRouterUnitySceneService\",\"TRANSACTION\":[{\"path\":\"getElementState\",\"METHOD\":\"getElementState\",\"ID\":0,\"parameter\":[{\"alias\":\"sceneId\",\"name\":\"sceneId\"},{\"alias\":\"elementId\",\"name\":\"elementId\"}]},{\"path\":\"onEvent\",\"METHOD\":\"onEvent\",\"ID\":1,\"parameter\":[{\"alias\":\"event\",\"name\":\"event\"},{\"alias\":\"data\",\"name\":\"data\"}]},{\"path\":\"onVuiQuery\",\"METHOD\":\"onVuiQuery\",\"ID\":2,\"parameter\":[{\"alias\":\"event\",\"name\":\"event\"},{\"alias\":\"data\",\"name\":\"data\"}]}]}";
    }

    public static Set<String> getKey() {
        HashSet hashSet = new HashSet(2);
        hashSet.add("ApiRouterUnitySceneService");
        return hashSet;
    }
}
