package com.xiaopeng.xui.app.delegate;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.xiaopeng.xui.utils.XLogUtils;

/* loaded from: classes2.dex */
public class XActivityWindowVisible implements XActivityLifecycle {
    private static final String TAG = "XActivityWindowVisible";
    private Activity mActivity;
    private boolean mAutoVisibility;
    private float mDimAlpha;

    public XActivityWindowVisible(Activity activity) {
        this.mActivity = activity;
    }

    public void setAutoVisibleEnableOnPause(boolean z) {
        if (z != this.mAutoVisibility) {
            XLogUtils.i(TAG, "setAutoVisibleEnableOnPause: " + z);
        }
        this.mAutoVisibility = z;
    }

    public void changeWindowVisible(boolean z) {
        if (this.mAutoVisibility) {
            XLogUtils.i(TAG, "changeWindowVisible: " + z);
            Window window = this.mActivity.getWindow();
            if (window != null) {
                WindowManager.LayoutParams attributes = window.getAttributes();
                if (attributes != null) {
                    attributes.alpha = z ? 1.0f : 0.0f;
                    attributes.dimAmount = z ? this.mDimAlpha : 0.0f;
                }
                window.setAttributes(attributes);
            }
        }
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
    public void onCreate(Bundle bundle) {
        TypedArray obtainStyledAttributes = this.mActivity.getTheme().obtainStyledAttributes(new int[]{16842802});
        this.mDimAlpha = obtainStyledAttributes.getFloat(0, 1.0f);
        obtainStyledAttributes.recycle();
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
    public void onResume() {
        changeWindowVisible(true);
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
    public void onPause() {
        changeWindowVisible(false);
    }

    public String toString() {
        return "{mDimAlpha=" + this.mDimAlpha + ", mAutoVisibility=" + this.mAutoVisibility + ", mActivity=" + this.mActivity + '}';
    }
}
