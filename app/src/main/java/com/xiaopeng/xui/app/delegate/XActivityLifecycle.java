package com.xiaopeng.xui.app.delegate;

import android.os.Bundle;

/* loaded from: classes2.dex */
interface XActivityLifecycle {
    default void onCreate(Bundle bundle) {
    }

    default void onDestroy() {
    }

    default void onPause() {
    }

    default void onRecreate() {
    }

    default void onResume() {
    }

    default void onStart() {
    }

    default void onStop() {
    }
}
