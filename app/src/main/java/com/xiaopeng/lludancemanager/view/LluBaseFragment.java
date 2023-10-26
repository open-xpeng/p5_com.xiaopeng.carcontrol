package com.xiaopeng.lludancemanager.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.view.fragment.VuiFragment;
import com.xiaopeng.lludancemanager.viewmodel.LluDanceViewModel;
import com.xiaopeng.vui.commons.model.VuiEvent;

/* loaded from: classes2.dex */
public abstract class LluBaseFragment extends VuiFragment {
    private static final String TAG = "LluBaseFragment";
    protected LluDanceViewModel mDanceViewModel;
    protected String mFragmentTitle = "";

    protected abstract void initViewModel();

    protected abstract void initViewModelObserver();

    protected abstract boolean supportVui();

    protected String vuiSceneId() {
        return null;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper, com.xiaopeng.vui.commons.IVuiSceneListener
    public void onBuildScene() {
        super.onBuildScene();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        super.onVuiEvent(view, vuiEvent);
    }
}
