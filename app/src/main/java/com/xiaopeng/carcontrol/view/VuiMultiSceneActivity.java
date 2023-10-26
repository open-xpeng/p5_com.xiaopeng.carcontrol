package com.xiaopeng.carcontrol.view;

import android.view.View;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.fragment.VuiFragment;
import com.xiaopeng.speech.vui.Helper.IVuiSceneHelper;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class VuiMultiSceneActivity extends XActivity implements IVuiSceneHelper {
    public String mSceneId;
    public View mRootView = null;
    public final List<String> mSubScenes = new ArrayList();

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public List<View> getBuildViews() {
        return null;
    }

    public abstract void initVui();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        VuiFloatingLayerManager.hide(1);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        if (view != null) {
            VuiFloatingLayerManager.show(view);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        if (view != null) {
            VuiFloatingLayerManager.show(view);
            return false;
        }
        return false;
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return this.mSceneId;
    }

    public void initVuiScene() {
        LogUtils.d("Activity", "initVuiScene: " + getSceneId());
        VuiManager.instance().initScene(this, getLifecycle(), getSceneId(), this.mRootView, this, null, null);
    }

    public void initFragmentVuiParams(VuiFragment fragment, String sceneId, boolean isWholeScene, List<String> subSceneList, String fatherId) {
        if (fragment == null) {
            return;
        }
        fragment.setSceneId(sceneId);
        fragment.setIsWholeScene(isWholeScene);
        fragment.setSubSceneList(subSceneList);
        fragment.setFatherId(fatherId);
        fragment.setIsDialog(false);
    }
}
