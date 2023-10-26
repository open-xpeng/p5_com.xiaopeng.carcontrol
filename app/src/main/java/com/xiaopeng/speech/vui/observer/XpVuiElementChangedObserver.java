package com.xiaopeng.speech.vui.observer;

import android.text.TextUtils;
import android.view.View;
import com.xiaopeng.speech.XpSpeechEngine;
import com.xiaopeng.speech.vui.VuiEngineImpl;
import com.xiaopeng.speech.vui.cache.VuiSceneCache;
import com.xiaopeng.speech.vui.cache.VuiSceneCacheFactory;
import com.xiaopeng.speech.vui.listener.IXpVuiElementChanged;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.List;

/* loaded from: classes2.dex */
public class XpVuiElementChangedObserver implements IXpVuiElementChanged {
    private IVuiSceneListener mListener;

    public XpVuiElementChangedObserver(IVuiSceneListener iVuiSceneListener) {
        this.mListener = null;
        this.mListener = iVuiSceneListener;
    }

    public XpVuiElementChangedObserver() {
        this.mListener = null;
    }

    @Override // com.xiaopeng.speech.vui.listener.IXpVuiElementChanged
    public void onVuiElementChanged(String str, View view) {
        onVuiElementChanged(str, view, null, null, -1);
    }

    @Override // com.xiaopeng.speech.vui.listener.IXpVuiElementChanged
    public void onVuiElementChanged(String str, View view, String[] strArr, int i) {
        onVuiElementChanged(str, view, null, strArr, i);
    }

    @Override // com.xiaopeng.speech.vui.listener.IXpVuiElementChanged
    public void onVuiElementChanged(String str, View view, List<VuiElement> list) {
        onVuiElementChanged(str, view, list, null, -1);
    }

    private void onVuiElementChanged(String str, View view, List<VuiElement> list, String[] strArr, int i) {
        LogUtils.logDebug("XpVuiElementChangedObserver", "onVuiElementChanged:" + str + ",elementList:" + list);
        if (this.mListener != null && !TextUtils.isEmpty(str)) {
            str = this.mListener.toString() + "-" + str;
        }
        if (view != null) {
            VuiElement vuiElement = XpSpeechEngine.getVuiElement(str, "" + view.getId());
            if (vuiElement != null) {
                if (VuiElementType.RADIOBUTTON.getType().equals(vuiElement.getType()) || VuiElementType.CHECKBOX.getType().equals(vuiElement.getType())) {
                    VuiUtils.setValueAttribute(view, vuiElement);
                } else if (VuiElementType.RECYCLEVIEW.getType().equals(vuiElement.getType())) {
                    VuiUtils.addScrollProps(vuiElement, view);
                    vuiElement.setElements(list);
                } else if (VuiElementType.STATEFULBUTTON.getType().equals(vuiElement.getType())) {
                    VuiUtils.setStatefulButtonValues(i, strArr, vuiElement);
                }
                vuiElement.setVisible(view.getVisibility() == 0 ? null : false);
                XpSpeechEngine.setUpdateElement(str, vuiElement);
                return;
            }
            XpSpeechEngine.setBuildElement(str, list);
            return;
        }
        VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
        if (sceneCache != null) {
            List<VuiElement> cache = sceneCache.getCache(VuiEngineImpl.mSceneIdPrefix + "-" + str);
            if (cache != null && !cache.isEmpty()) {
                XpSpeechEngine.setUpdateElement(str, list);
                return;
            }
            LogUtils.logInfo("XpVuiElementChangedObserver", "cacheList is empty");
            XpSpeechEngine.setBuildElement(str, list);
        }
    }
}
