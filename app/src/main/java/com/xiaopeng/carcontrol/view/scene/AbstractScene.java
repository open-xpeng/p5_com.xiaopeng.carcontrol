package com.xiaopeng.carcontrol.view.scene;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.xui.widget.XTabLayout;

/* loaded from: classes2.dex */
public abstract class AbstractScene implements LifecycleObserver, VuiUtilHelper {
    protected final String TAG = getClass().getSimpleName();
    private LifecycleOwner mOwner;
    private View mParent;
    private final String mSceneId;

    protected abstract void initViewModels();

    protected abstract void initViews();

    /* JADX INFO: Access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration newConfig) {
    }

    public AbstractScene(String sceneId, View parent, LifecycleOwner owner) {
        this.mSceneId = sceneId;
        this.mParent = parent;
        this.mOwner = owner;
    }

    public final View getContent() {
        return this.mParent;
    }

    @Override // com.xiaopeng.carcontrol.view.scene.VuiUtilHelper
    public final Context getContext() {
        return this.mParent.getContext();
    }

    @Override // com.xiaopeng.carcontrol.view.scene.VuiUtilHelper
    public final String getSceneId() {
        return this.mSceneId;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final <V extends View> V findViewById(int id) {
        return (V) this.mParent.findViewById(id);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        LogUtils.d(this.TAG, "onCreate");
        initViewModels();
        initViews();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onStart() {
        LogUtils.d(this.TAG, "onStart");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        LogUtils.d(this.TAG, "onResume");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        LogUtils.d(this.TAG, "onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void onStop() {
        LogUtils.d(this.TAG, "onStop");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        LogUtils.d(this.TAG, "onDestroy");
        this.mParent = null;
        this.mOwner = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void setLdo(LiveData<T> liveData, Observer<T> observer) {
        LifecycleOwner lifecycleOwner = this.mOwner;
        if (lifecycleOwner != null) {
            liveData.observe(lifecycleOwner, observer);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void updateXTabLayout(XTabLayout tabLayout, int index) {
        if (tabLayout.isEnabled()) {
            tabLayout.selectTab(index, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean isVuiAction(View view) {
        return VuiManager.instance().isVuiAction(view);
    }
}
