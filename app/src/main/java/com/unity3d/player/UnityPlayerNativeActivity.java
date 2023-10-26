package com.unity3d.player;

import android.os.Bundle;
import android.util.Log;

/* loaded from: classes.dex */
public class UnityPlayerNativeActivity extends UnityPlayerActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Log.w("Unity", "UnityPlayerNativeActivity has been deprecated, please update your AndroidManifest to use UnityPlayerActivity instead");
        super.onCreate(bundle);
    }
}
