package com.xiaopeng.xui.vui;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.VuiFeedbackType;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.vui.commons.VuiPriority;
import com.xiaopeng.vui.commons.VuiUpdateType;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xui.utils.XLogUtils;
import com.xiaopeng.xui.utils.XuiUtils;
import com.xiaopeng.xui.vui.utils.VuiCommonUtils;
import com.xiaopeng.xui.vui.utils.VuiViewUtils;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public interface VuiView extends IVuiElement {
    public static final SparseArray<XAttr> msMap = new SparseArray<>();

    default void initVui(View view, AttributeSet attributeSet) {
        if (!Xui.isVuiEnable() || view == null || attributeSet == null) {
            return;
        }
        XAttr xAttr = new XAttr();
        TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, R.styleable.vui);
        xAttr.vuiAction = obtainStyledAttributes.getString(R.styleable.vui_vuiAction);
        xAttr.vuiElementType = VuiCommonUtils.getElementType(obtainStyledAttributes.getInteger(R.styleable.vui_vuiElementType, -1));
        if (xAttr.vuiElementType == VuiElementType.UNKNOWN) {
            xAttr.vuiElementType = VuiViewUtils.getElementType(view);
        }
        xAttr.vuiPosition = Integer.valueOf(obtainStyledAttributes.getInteger(R.styleable.vui_vuiPosition, -1));
        xAttr.vuiFatherElementId = obtainStyledAttributes.getString(R.styleable.vui_vuiFatherElementId);
        xAttr.vuiLabel = obtainStyledAttributes.getString(R.styleable.vui_vuiLabel);
        xAttr.vuiFatherLabel = obtainStyledAttributes.getString(R.styleable.vui_vuiFatherLabel);
        xAttr.vuiElementId = obtainStyledAttributes.getString(R.styleable.vui_vuiElementId);
        xAttr.vuiLayoutLoadable = obtainStyledAttributes.getBoolean(R.styleable.vui_vuiLayoutLoadable, false);
        xAttr.vuiMode = VuiCommonUtils.getVuiMode(obtainStyledAttributes.getInteger(R.styleable.vui_vuiMode, 4));
        xAttr.vuiBizId = obtainStyledAttributes.getString(R.styleable.vui_vuiBizId);
        xAttr.vuiPriority = VuiCommonUtils.getViewLeveByPriority(obtainStyledAttributes.getInt(R.styleable.vui_vuiPriority, 2));
        xAttr.vuiFeedbackType = VuiCommonUtils.getFeedbackType(obtainStyledAttributes.getInteger(R.styleable.vui_vuiFeedbackType, 1));
        xAttr.vuiDisableHitEffect = obtainStyledAttributes.getBoolean(R.styleable.vui_vuiDisableHitEffect, false);
        xAttr.vuiEnableViewVuiMode = obtainStyledAttributes.getBoolean(R.styleable.vui_vuiEnableViewVuiMode, false);
        obtainStyledAttributes.recycle();
        xAttr.mVuiVisibility = view.getVisibility();
        xAttr.mVuiSelected = view.isSelected();
        SparseArray<XAttr> sparseArray = msMap;
        synchronized (sparseArray) {
            sparseArray.put(hashCode(), xAttr);
        }
    }

    default XAttr checkVuiExit() {
        XAttr xAttr;
        SparseArray<XAttr> sparseArray = msMap;
        synchronized (sparseArray) {
            xAttr = sparseArray.get(hashCode());
        }
        if (xAttr == null) {
            logD("xAttr is null");
            xAttr = new XAttr();
            if (xAttr.vuiElementType == VuiElementType.UNKNOWN) {
                xAttr.vuiElementType = VuiViewUtils.getElementType(this);
            }
            synchronized (sparseArray) {
                sparseArray.put(hashCode(), xAttr);
            }
        }
        return xAttr;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default boolean isVuiLayoutLoadable() {
        return checkVuiExit().vuiLayoutLoadable;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiLayoutLoadable(boolean z) {
        checkVuiExit().vuiLayoutLoadable = z;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default VuiPriority getVuiPriority() {
        return checkVuiExit().vuiPriority;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiPriority(VuiPriority vuiPriority) {
        checkVuiExit().vuiPriority = vuiPriority;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default String getVuiAction() {
        return checkVuiExit().vuiAction;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiAction(String str) {
        checkVuiExit().vuiAction = str;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default VuiElementType getVuiElementType() {
        return checkVuiExit().vuiElementType;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiElementType(VuiElementType vuiElementType) {
        checkVuiExit().vuiElementType = vuiElementType;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default String getVuiFatherElementId() {
        return checkVuiExit().vuiFatherElementId;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiFatherElementId(String str) {
        checkVuiExit().vuiFatherElementId = str;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default String getVuiFatherLabel() {
        return checkVuiExit().vuiFatherLabel;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiFatherLabel(String str) {
        checkVuiExit().vuiFatherLabel = str;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default String getVuiLabel() {
        return checkVuiExit().vuiLabel;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiLabel(String str) {
        checkVuiExit().vuiLabel = str;
        if (isVuiLayoutLoadable() && (this instanceof View)) {
            updateVui((View) this);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default String getVuiElementId() {
        return checkVuiExit().vuiElementId;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiElementId(String str) {
        checkVuiExit().vuiElementId = str;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiPosition(int i) {
        checkVuiExit().vuiPosition = Integer.valueOf(i);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default int getVuiPosition() {
        return checkVuiExit().vuiPosition.intValue();
    }

    default void releaseVui() {
        SparseArray<XAttr> sparseArray = msMap;
        synchronized (sparseArray) {
            sparseArray.remove(hashCode());
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default VuiFeedbackType getVuiFeedbackType() {
        return checkVuiExit().vuiFeedbackType;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiFeedbackType(VuiFeedbackType vuiFeedbackType) {
        checkVuiExit().vuiFeedbackType = vuiFeedbackType;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default boolean isPerformVuiAction() {
        return checkVuiExit().performVuiAction;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setPerformVuiAction(boolean z) {
        checkVuiExit().performVuiAction = z;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiProps(JSONObject jSONObject) {
        checkVuiExit().vuiProps = jSONObject;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default JSONObject getVuiProps() {
        return checkVuiExit().vuiProps;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default VuiMode getVuiMode() {
        return checkVuiExit().vuiMode;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiMode(VuiMode vuiMode) {
        checkVuiExit().vuiMode = vuiMode;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiBizId(String str) {
        checkVuiExit().vuiBizId = str;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default String getVuiBizId() {
        return checkVuiExit().vuiBizId;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiDisableHitEffect(boolean z) {
        checkVuiExit().vuiDisableHitEffect = z;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default boolean getVuiDisableHitEffect() {
        XAttr checkVuiExit = checkVuiExit();
        if (checkVuiExit.vuiDisableHitEffect || !(VuiAction.SCROLLBYY.getName().equals(checkVuiExit.vuiAction) || VuiAction.SCROLLBYX.getName().equals(checkVuiExit.vuiAction))) {
            return checkVuiExit.vuiDisableHitEffect;
        }
        return true;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void enableViewVuiMode(boolean z) {
        checkVuiExit().vuiEnableViewVuiMode = z;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default boolean isVuiModeEnabled() {
        return checkVuiExit().vuiEnableViewVuiMode;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiValue(Object obj) {
        checkVuiExit().vuiValue = obj;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiValue(Object obj, View view) {
        checkVuiExit().vuiValue = obj;
        if (!VuiElementType.STATEFULBUTTON.getType().equals(getVuiElementType().getType()) || view == null) {
            return;
        }
        updateVui(view);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default Object getVuiValue() {
        return checkVuiExit().vuiValue;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default void setVuiElementChangedListener(IVuiElementChangedListener iVuiElementChangedListener) {
        checkVuiExit().mVuiElementChangedListener = new WeakReference(iVuiElementChangedListener);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElement
    default IVuiElementChangedListener getVuiElementChangedListener() {
        XAttr checkVuiExit = checkVuiExit();
        if (checkVuiExit.mVuiElementChangedListener != null) {
            synchronized (msMap) {
                if (checkVuiExit.mVuiElementChangedListener != null) {
                    return (IVuiElementChangedListener) checkVuiExit.mVuiElementChangedListener.get();
                }
                return null;
            }
        }
        return null;
    }

    default void setVuiVisibility(View view, int i) {
        XAttr checkVuiExit = checkVuiExit();
        if (checkVuiExit.mVuiVisibility != i) {
            if (XLogUtils.isLogLevelEnabled(2)) {
                logD("setVuiVisibility; xAttr.mVuiVisibility : " + XuiUtils.formatVisibility(checkVuiExit.mVuiVisibility) + ",visibility " + XuiUtils.formatVisibility(i));
            }
            checkVuiExit.mVuiVisibility = i;
            try {
                JSONObject vuiProps = getVuiProps();
                if (vuiProps != null && vuiProps.has(VuiConstants.PROPS_VOICECONTROL)) {
                    if (vuiProps.getBoolean(VuiConstants.PROPS_VOICECONTROL)) {
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateVui(view);
        }
    }

    default void setVuiSelected(View view, boolean z) {
        XAttr checkVuiExit = checkVuiExit();
        if (checkVuiExit.mVuiSelected != z) {
            checkVuiExit.mVuiSelected = z;
            String type = getVuiElementType().getType();
            if (VuiElementType.CHECKBOX.getType().equals(type) || VuiElementType.SWITCH.getType().equals(type) || VuiElementType.RADIOBUTTON.getType().equals(type)) {
                updateVui(view);
            }
        }
    }

    default void updateVui(View view) {
        updateVui(view, VuiUpdateType.UPDATE_VIEW_ATTRIBUTE);
    }

    default void updateVui(View view, VuiUpdateType vuiUpdateType) {
        IVuiElementChangedListener vuiElementChangedListener = getVuiElementChangedListener();
        if (vuiElementChangedListener != null) {
            VuiViewUtils.updateVui(vuiElementChangedListener, view, vuiUpdateType);
        } else if (XLogUtils.isLogLevelEnabled(2)) {
            logD("listener is null");
        }
    }

    default void logD(String str) {
        XLogUtils.d("xpui", "%s %s hashCode:%s", getClass().getSimpleName(), str, Integer.valueOf(hashCode()));
    }

    default void logI(String str) {
        XLogUtils.i("xpui", "%s %s hashCode:%s", getClass().getSimpleName(), str, Integer.valueOf(hashCode()));
    }

    /* loaded from: classes2.dex */
    public static class XAttr {
        private volatile WeakReference<IVuiElementChangedListener> mVuiElementChangedListener;
        private int mVuiVisibility;
        private boolean performVuiAction;
        private String vuiAction;
        private String vuiBizId;
        private boolean vuiDisableHitEffect;
        private String vuiElementId;
        private String vuiFatherElementId;
        private String vuiFatherLabel;
        private VuiFeedbackType vuiFeedbackType;
        private String vuiLabel;
        private boolean vuiLayoutLoadable;
        private Object vuiValue;
        private boolean mVuiSelected = false;
        private VuiElementType vuiElementType = VuiCommonUtils.getElementType(-1);
        private Integer vuiPosition = -1;
        private VuiMode vuiMode = VuiMode.NORMAL;
        private boolean vuiEnableViewVuiMode = false;
        private VuiPriority vuiPriority = VuiCommonUtils.getViewLeveByPriority(2);
        private JSONObject vuiProps = null;

        XAttr() {
        }
    }
}
