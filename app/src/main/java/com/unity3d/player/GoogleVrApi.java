package com.unity3d.player;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class GoogleVrApi {
    private static AtomicReference a = new AtomicReference();

    private GoogleVrApi() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a() {
        a.set(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(f fVar) {
        a.compareAndSet(null, new GoogleVrProxy(fVar));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static GoogleVrProxy b() {
        return (GoogleVrProxy) a.get();
    }

    public static GoogleVrVideo getGoogleVrVideo() {
        return (GoogleVrVideo) a.get();
    }
}
