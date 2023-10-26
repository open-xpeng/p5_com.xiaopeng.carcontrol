package com.unity3d.player;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/* loaded from: classes.dex */
public class UnityPlayerProxyActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        Log.w("Unity", "UnityPlayerProxyActivity has been deprecated, please update your AndroidManifest to use UnityPlayerActivity instead");
        super.onCreate(bundle);
    }
}
