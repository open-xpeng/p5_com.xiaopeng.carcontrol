package com.xiaopeng.carcontrol.view.fragment.controlscene.space;

import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public abstract class AbsSpaceModeView {
    private static final String TAG = "AbsSpaceModeView";
    private boolean mIsActive = false;

    protected abstract int getLayoutRes();

    protected abstract void onConfigurationChanged(Configuration newConfig);

    /* JADX INFO: Access modifiers changed from: protected */
    public View onCreateView(LayoutInflater inflater) {
        LogUtils.i(TAG, "onCreateView " + getClass().getSimpleName());
        return inflater.inflate(getLayoutRes(), (ViewGroup) null, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void enter(LifecycleOwner lifecycleOwner) {
        LogUtils.i(TAG, "enter " + getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void exit() {
        LogUtils.i(TAG, "exit " + getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onResume() {
        LogUtils.i(TAG, "onResume " + getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPause() {
        LogUtils.i(TAG, "onPause " + getClass().getSimpleName());
    }

    protected void onDestroyView() {
        LogUtils.i(TAG, "onDestroyView " + getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isActive() {
        return this.mIsActive;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setActive(boolean active) {
        this.mIsActive = active;
    }

    public static void setViewLayoutParams(View view, int nWidth) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams.width != nWidth) {
            layoutParams.width = nWidth;
            view.setLayoutParams(layoutParams);
        }
    }
}
