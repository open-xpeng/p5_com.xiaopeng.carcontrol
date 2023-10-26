package com.xiaopeng.xui.app;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

@Deprecated
/* loaded from: classes2.dex */
public class XFullscreenActivity extends AppCompatActivity {
    public static final int FEATURE_XUI_FULLSCREEN = 14;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        enterFullscreen(this, 14);
    }

    private static void enterFullscreen(Activity activity, int i) {
        if (i > 0) {
            activity.requestWindowFeature(i);
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(5894);
    }

    private static void exitFullscreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(-1);
    }
}
