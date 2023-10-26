package com.xiaopeng.carcontrol.view.fragment.spacecapsule;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.fragment.VuiFragment;
import com.xiaopeng.vui.commons.model.VuiEvent;

/* loaded from: classes2.dex */
public abstract class SpaceBaseFragment extends VuiFragment {
    private final String TAG = getClass().getSimpleName();

    protected abstract void initViewModel();

    protected abstract void initViewModelObserver();

    public abstract void setClickSelected(int position);

    protected abstract boolean supportVui();

    protected String vuiSceneId() {
        return null;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(this.TAG, "onCreate " + getClass().getSimpleName());
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtils.i(this.TAG, "onViewCreated " + getClass().getSimpleName());
        initViewModelObserver();
        initViewModel();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        liveData.observe(this, observer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void removeLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        liveData.removeObserver(observer);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.sceneId = vuiSceneId();
        if (supportVui()) {
            initVuiScene();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        LogUtils.d(this.TAG, "onResume isHidden:" + isHidden() + " isVisible:" + isVisible() + " " + getClass().getSimpleName());
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper, com.xiaopeng.vui.commons.IVuiSceneListener
    public void onBuildScene() {
        super.onBuildScene();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        LogUtils.d(this.TAG, "onPause isHidden:" + isHidden() + " isVisible:" + isVisible() + " " + getClass().getSimpleName());
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        LogUtils.d(this.TAG, "onStart " + getClass().getSimpleName());
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d(this.TAG, "setUserVisibleHint isVisibleToUser:" + isVisibleToUser + " " + getClass().getSimpleName());
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.d(this.TAG, "onHiddenChanged hidden:" + hidden + " " + getClass().getSimpleName());
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        LogUtils.d(this.TAG, "onStop " + getClass().getSimpleName());
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d(this.TAG, "onDestroyView " + getClass().getSimpleName());
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(this.TAG, "onDestroy " + getClass().getSimpleName());
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        super.onVuiEvent(view, vuiEvent);
    }
}
