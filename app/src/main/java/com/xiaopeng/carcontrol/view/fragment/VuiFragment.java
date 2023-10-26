package com.xiaopeng.carcontrol.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.vui.Helper.IVuiSceneHelper;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.VuiUpdateType;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class VuiFragment extends Fragment implements IVuiSceneHelper {
    private boolean mLastVuiEnable;
    protected ViewGroup mRootContainer;
    protected final String TAG = getClass().getSimpleName();
    protected String sceneId = null;
    protected String fatherId = null;
    protected boolean mIsWholeScene = true;
    private List<String> mSubSceneList = null;
    protected boolean mIsDialog = false;
    protected final List<View> mVuiUpdateViews = new ArrayList();
    private final IVuiElementChangedListener mElementChangedListener = new IVuiElementChangedListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$VuiFragment$iPUM3M9_BU_O6H_9OiUt9zOIOms
        @Override // com.xiaopeng.vui.commons.IVuiElementChangedListener
        public final void onVuiElementChaned(View view, VuiUpdateType vuiUpdateType) {
            VuiFragment.this.lambda$new$0$VuiFragment(view, vuiUpdateType);
        }
    };

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public List<View> getBuildViews() {
        return null;
    }

    protected abstract int getRootLayoutId();

    public /* synthetic */ void lambda$new$0$VuiFragment(View view, VuiUpdateType type) {
        VuiManager.instance().updateScene(getContext(), getSceneId(), view, type);
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public List<String> getSubSceneList() {
        return this.mSubSceneList;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mLastVuiEnable = VuiEngine.getInstance(getContext()).isVuiFeatureDisabled();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(this.TAG, " onCreateView");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(getRootLayoutId(), container, false);
        this.mRootContainer = viewGroup;
        return viewGroup;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        LogUtils.i(this.TAG, "onResume:" + this.sceneId);
        boolean isVuiFeatureDisabled = VuiEngine.getInstance(getContext()).isVuiFeatureDisabled();
        if (isVuiFeatureDisabled != this.mLastVuiEnable) {
            this.mLastVuiEnable = isVuiFeatureDisabled;
            onVuiEnableChanged();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        LogUtils.d(this.TAG, "onPause:" + this.sceneId);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        if (view != null) {
            VuiFloatingLayerManager.show(view);
        }
    }

    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        if (view != null) {
            VuiFloatingLayerManager.show(view);
            return false;
        }
        return false;
    }

    public boolean isVuiAction(View view) {
        return isVuiAction(view, true);
    }

    public boolean isVuiAction(View view, boolean reset) {
        return VuiManager.instance().isVuiAction(view, reset);
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return this.sceneId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public void setIsWholeScene(boolean isWholeScene) {
        this.mIsWholeScene = isWholeScene;
    }

    public void setIsDialog(boolean isDialog) {
        this.mIsDialog = isDialog;
    }

    public void setSubSceneList(List<String> subSceneList) {
        this.mSubSceneList = subSceneList;
    }

    public void initVuiScene() {
        if (this.mIsDialog) {
            return;
        }
        LogUtils.i(this.TAG, "initVuiScene: " + this.sceneId);
        VuiManager.instance().initScene(getContext(), getLifecycle(), this.sceneId, this.mRootContainer, this, this.mElementChangedListener, this.fatherId);
    }

    public void updateVuiScene(View... views) {
        VuiManager.instance().updateVuiScene(this.sceneId, getContext(), views);
    }

    public void updateVuiScene() {
        VuiManager.instance().updateVuiScene(this.sceneId, getContext(), this.mVuiUpdateViews);
    }

    public void setVuiElementUnSupport(View view, boolean isUnSupport) {
        VuiManager.instance().setVuiElementUnSupport(getContext(), view, isUnSupport);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setVuiLabelUnSupportText(View... views) {
        VuiManager.instance().setVuiLabelUnSupportText(getContext(), views);
    }

    public void setVuiElementVisible(View view, boolean isVisible) {
        VuiManager.instance().setVuiElementVisible(getContext(), isVisible, view);
    }

    public Boolean getVuiElementVisible(View view) {
        return VuiManager.instance().getVuiElementVisible(getContext(), view);
    }

    protected void onVuiEnableChanged() {
        LogUtils.i(this.TAG, "onVuiEnableChanged:" + this.sceneId);
    }
}
