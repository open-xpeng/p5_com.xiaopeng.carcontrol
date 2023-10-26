package com.xiaopeng.carcontrol.view.scene;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.xui.vui.VuiView;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public interface VuiUtilHelper {
    public static final String TAG = "VuiUtilHelper";

    Context getContext();

    String getSceneId();

    default void setVuiLabelUnSupportText(View... views) {
        VuiManager.instance().setVuiLabelUnSupportText(getContext(), views);
    }

    default void setStatefulButtonAttr(View view, int currIndex, String[] vuilabels, String action) {
        VuiUtils.setStatefulButtonAttr(view, currIndex, vuilabels, action);
    }

    default void setStatefulButtonValue(View view, int currIndex) {
        VuiUtils.setStatefulButtonValue(view, currIndex);
    }

    default void addVuiProps(IVuiElement element, Map<String, Object> propsMap) {
        VuiUtils.addProps(element, propsMap);
    }

    default void vuiFeedback(int content, View view) {
        if (view != null) {
            VuiEngine.getInstance(getContext()).vuiFeedback(view, new VuiFeedback.Builder().state(1).content(ResUtils.getString(content)).build());
        }
    }

    default void vuiFeedbackClick(int content, View view) {
        if ((view instanceof VuiView) && ((VuiView) view).isPerformVuiAction()) {
            vuiFeedback(content, view);
        }
    }

    default void addHasFeedbackProp(IVuiElement vuiView) {
        VuiUtils.addHasFeedbackProp(vuiView);
    }

    default void addVuiProp(VuiView view, String prop, String value) {
        if (TextUtils.isEmpty(prop) || TextUtils.isEmpty(value)) {
            LogUtils.e(TAG, "addVuiProps failed with prop=" + prop + ", value=" + value, false);
            return;
        }
        LogUtils.i(TAG, "addVuiProps prop=" + prop + ", value=" + value, false);
        JSONObject vuiPropsJson = getVuiPropsJson(view);
        try {
            vuiPropsJson.put(prop, value);
        } catch (Exception e) {
            LogUtils.e(TAG, "addVuiProps " + prop + " failed :" + e.getMessage(), false);
        }
        view.setVuiProps(vuiPropsJson);
    }

    default void removeVuiProps(VuiView view, String prop) {
        if (TextUtils.isEmpty(prop)) {
            LogUtils.e(TAG, "removeVuiProps failed with prop=" + prop, false);
            return;
        }
        JSONObject vuiPropsJson = getVuiPropsJson(view);
        vuiPropsJson.remove(prop);
        view.setVuiProps(vuiPropsJson);
    }

    default JSONObject getVuiPropsJson(VuiView view) {
        JSONObject vuiProps = view.getVuiProps();
        return vuiProps == null ? new JSONObject() : vuiProps;
    }

    default void updateVuiScene(View... views) {
        VuiManager.instance().updateVuiScene(getSceneId(), getContext(), views);
    }
}
