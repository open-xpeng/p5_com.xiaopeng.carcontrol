package com.xiaopeng.lib.apirouter.server;

import android.os.IBinder;
import android.util.Pair;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class ManifestHelper {
    public static HashMap<String, Pair<IBinder, String>> mapping = new HashMap<>();

    static {
        Pair<IBinder, String> pair = new Pair<>(new VuiMainObserver_Stub(), VuiMainObserver_Manifest.toJsonManifest());
        for (String str : VuiMainObserver_Manifest.getKey()) {
            mapping.put(str, pair);
        }
        Pair<IBinder, String> pair2 = new Pair<>(new IpcRouterService_Stub(), IpcRouterService_Manifest.toJsonManifest());
        for (String str2 : IpcRouterService_Manifest.getKey()) {
            mapping.put(str2, pair2);
        }
        Pair<IBinder, String> pair3 = new Pair<>(new VuiUnityObserver_Stub(), VuiUnityObserver_Manifest.toJsonManifest());
        for (String str3 : VuiUnityObserver_Manifest.getKey()) {
            mapping.put(str3, pair3);
        }
        Pair<IBinder, String> pair4 = new Pair<>(new CarControlSpeechObserver_Stub(), CarControlSpeechObserver_Manifest.toJsonManifest());
        for (String str4 : CarControlSpeechObserver_Manifest.getKey()) {
            mapping.put(str4, pair4);
        }
    }
}
