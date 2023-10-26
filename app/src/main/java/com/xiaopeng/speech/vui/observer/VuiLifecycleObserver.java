package com.xiaopeng.speech.vui.observer;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiSceneListener;

/* loaded from: classes2.dex */
public class VuiLifecycleObserver implements LifecycleObserver {
    private static final String TAG = "VuiLifecycleObserver";
    private Context mContext;
    private IVuiElementChangedListener mElementChangedListener;
    private boolean mKeepCache;
    private Lifecycle mLifeCycle;
    private IVuiSceneListener mListener;
    private boolean mNeedBuild;
    private View mRootView;
    private String mSceneId;

    public VuiLifecycleObserver(Context context, Lifecycle lifecycle, String str, View view, IVuiSceneListener iVuiSceneListener, IVuiElementChangedListener iVuiElementChangedListener, boolean z, boolean z2) {
        this.mSceneId = str;
        this.mListener = iVuiSceneListener;
        this.mContext = context;
        this.mLifeCycle = lifecycle;
        this.mRootView = view;
        this.mElementChangedListener = iVuiElementChangedListener;
        this.mKeepCache = z2;
        this.mNeedBuild = z;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        LogUtils.d(TAG, "onCreate: " + this.mSceneId + ",rootView:" + this.mRootView);
        if (this.mContext == null || TextUtils.isEmpty(this.mSceneId) || this.mRootView == null) {
            return;
        }
        VuiEngine.getInstance(this.mContext).addVuiSceneListener(this.mSceneId, this.mRootView, this.mListener, this.mElementChangedListener, this.mNeedBuild);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        LogUtils.d(TAG, "onResume: " + this.mSceneId);
        if (this.mContext == null || TextUtils.isEmpty(this.mSceneId) || this.mSceneId.contains("Local") || this.mSceneId.contains("local")) {
            return;
        }
        VuiEngine.getInstance(this.mContext).enterScene(this.mSceneId);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        LogUtils.d(TAG, "onPause: " + this.mSceneId);
        if (this.mContext == null || TextUtils.isEmpty(this.mSceneId) || this.mSceneId.contains("Local") || this.mSceneId.contains("local")) {
            return;
        }
        VuiEngine.getInstance(this.mContext).exitScene(this.mSceneId);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        LogUtils.d(TAG, "onDestroy: " + this.mSceneId);
        if (this.mContext != null && !TextUtils.isEmpty(this.mSceneId)) {
            VuiEngine.getInstance(this.mContext).removeVuiSceneListener(this.mSceneId, this.mListener, this.mKeepCache);
        }
        Lifecycle lifecycle = this.mLifeCycle;
        if (lifecycle != null) {
            lifecycle.removeObserver(this);
        }
    }
}
