package com.xiaopeng.vui.commons;

import android.view.View;
import com.xiaopeng.lludancemanager.Constant;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public interface IVuiElement {
    default void enableViewVuiMode(boolean z) {
    }

    default String getVuiAction() {
        return "";
    }

    default String getVuiBizId() {
        return null;
    }

    default boolean getVuiDisableHitEffect() {
        return false;
    }

    default IVuiElementChangedListener getVuiElementChangedListener() {
        return null;
    }

    default String getVuiElementId() {
        return "";
    }

    default String getVuiFatherElementId() {
        return Constant.DEFAULT_ERROR_RSC_ID;
    }

    default String getVuiFatherLabel() {
        return "";
    }

    default String getVuiLabel() {
        return null;
    }

    default int getVuiPosition() {
        return -1;
    }

    default JSONObject getVuiProps() {
        return null;
    }

    default Object getVuiValue() {
        return null;
    }

    default boolean isPerformVuiAction() {
        return false;
    }

    default boolean isVuiLayoutLoadable() {
        return false;
    }

    default boolean isVuiModeEnabled() {
        return false;
    }

    default void setPerformVuiAction(boolean z) {
    }

    default void setVuiAction(String str) {
    }

    default void setVuiBizId(String str) {
    }

    default void setVuiDisableHitEffect(boolean z) {
    }

    default void setVuiElementChangedListener(IVuiElementChangedListener iVuiElementChangedListener) {
    }

    default void setVuiElementId(String str) {
    }

    default void setVuiElementType(VuiElementType vuiElementType) {
    }

    default void setVuiFatherElementId(String str) {
    }

    default void setVuiFatherLabel(String str) {
    }

    default void setVuiFeedbackType(VuiFeedbackType vuiFeedbackType) {
    }

    default void setVuiLabel(String str) {
    }

    default void setVuiLayoutLoadable(boolean z) {
    }

    default void setVuiMode(VuiMode vuiMode) {
    }

    default void setVuiPosition(int i) {
    }

    default void setVuiPriority(VuiPriority vuiPriority) {
    }

    default void setVuiProps(JSONObject jSONObject) {
    }

    default void setVuiValue(Object obj) {
    }

    default void setVuiValue(Object obj, View view) {
    }

    default VuiPriority getVuiPriority() {
        return VuiPriority.LEVEL3;
    }

    default VuiElementType getVuiElementType() {
        return VuiElementType.UNKNOWN;
    }

    default VuiFeedbackType getVuiFeedbackType() {
        return VuiFeedbackType.TTS;
    }

    default VuiMode getVuiMode() {
        return VuiMode.NORMAL;
    }
}
