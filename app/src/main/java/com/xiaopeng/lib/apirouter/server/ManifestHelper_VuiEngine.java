package com.xiaopeng.lib.apirouter.server;

import android.os.IBinder;
import android.util.Pair;
import com.xiaopeng.lib.apirouter.server.vuiengine.ApiRouterOverallService_Manifest;
import com.xiaopeng.lib.apirouter.server.vuiengine.ApiRouterOverallService_Stub;
import com.xiaopeng.lib.apirouter.server.vuiengine.ApiRouterSceneService_Manifest;
import com.xiaopeng.lib.apirouter.server.vuiengine.ApiRouterSceneService_Stub;
import com.xiaopeng.lib.apirouter.server.vuiengine.ApiRouterUnitySceneService_Manifest;
import com.xiaopeng.lib.apirouter.server.vuiengine.ApiRouterUnitySceneService_Stub;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class ManifestHelper_VuiEngine implements IManifestHelper {
    public HashMap<String, Pair<IBinder, String>> mapping = new HashMap<>();

    @Override // com.xiaopeng.lib.apirouter.server.IManifestHelper
    public HashMap<String, Pair<IBinder, String>> getMapping() {
        Pair<IBinder, String> pair = new Pair<>(new ApiRouterSceneService_Stub(), ApiRouterSceneService_Manifest.toJsonManifest());
        for (String str : ApiRouterSceneService_Manifest.getKey()) {
            this.mapping.put(str, pair);
        }
        Pair<IBinder, String> pair2 = new Pair<>(new ApiRouterOverallService_Stub(), ApiRouterOverallService_Manifest.toJsonManifest());
        for (String str2 : ApiRouterOverallService_Manifest.getKey()) {
            this.mapping.put(str2, pair2);
        }
        Pair<IBinder, String> pair3 = new Pair<>(new ApiRouterUnitySceneService_Stub(), ApiRouterUnitySceneService_Manifest.toJsonManifest());
        for (String str3 : ApiRouterUnitySceneService_Manifest.getKey()) {
            this.mapping.put(str3, pair3);
        }
        return this.mapping;
    }
}
