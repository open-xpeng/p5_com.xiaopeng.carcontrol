package com.xiaopeng.speech.vui.utils;

import android.view.View;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.VuiPriority;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.Map;

/* loaded from: classes2.dex */
public class XpVuiUtils {
    public static void setValueAttribute(View view, VuiElement vuiElement) {
        VuiUtils.setValueAttribute(view, vuiElement);
    }

    public static void addScrollProps(VuiElement vuiElement, View view) {
        VuiUtils.addScrollProps(vuiElement, view);
    }

    public static void addProps(VuiElement vuiElement, Map<String, Boolean> map) {
        VuiUtils.addProps(vuiElement, map);
    }

    public static VuiElement generateCommonVuiElement(String str, VuiElementType vuiElementType, String str2, String str3) {
        return VuiUtils.generateCommonVuiElement(str, vuiElementType, str2, str3);
    }

    public static VuiElement generateCommonVuiElement(int i, VuiElementType vuiElementType, String str, String str2) {
        return VuiUtils.generateCommonVuiElement("" + i, vuiElementType, str, str2);
    }

    public static VuiElement generateCommonVuiElement(int i, VuiElementType vuiElementType, String str) {
        return VuiUtils.generateCommonVuiElement("" + i, vuiElementType, str, (String) null);
    }

    public static VuiElement generateCommonVuiElement(String str, VuiElementType vuiElementType, String str2) {
        return VuiUtils.generateCommonVuiElement(str, vuiElementType, str2, (String) null);
    }

    public static VuiElement generateCommonVuiElement(String str, VuiElementType vuiElementType, String str2, boolean z) {
        return VuiUtils.generateCommonVuiElement(str, vuiElementType, str2, null, z, VuiPriority.LEVEL3);
    }

    public static VuiElement generateLayoutLoadableVuiElement(String str, VuiElementType vuiElementType, String str2) {
        return VuiUtils.generateCommonVuiElement(str, vuiElementType, str2, null, true, VuiPriority.LEVEL3);
    }

    public static VuiElement generateLayoutLoadableVuiElement(String str, VuiElementType vuiElementType, String str2, String str3) {
        return VuiUtils.generateCommonVuiElement(str, vuiElementType, str2, str3, true, VuiPriority.LEVEL3);
    }

    public static VuiElement generatePriorityVuiElement(String str, VuiElementType vuiElementType, String str2, VuiPriority vuiPriority) {
        return VuiUtils.generateCommonVuiElement(str, vuiElementType, str2, null, false, vuiPriority);
    }

    public static VuiElement generateVideoVuiElement(String str, VuiElementType vuiElementType, String str2, String str3) {
        return VuiUtils.generateCommonVuiElement(str, vuiElementType, str2, str3, false, VuiPriority.LEVEL2, VuiUtils.LIST_VEDIO_TYPE);
    }

    public static VuiElement generatePriorityVuiElement(String str, VuiElementType vuiElementType, String str2, String str3, VuiPriority vuiPriority) {
        return VuiUtils.generateCommonVuiElement(str, vuiElementType, str2, str3, false, vuiPriority);
    }

    public static VuiElement generateStatefulButtonElement(int i, String[] strArr, int i2) {
        return VuiUtils.generateStatefulButtonElement(i, strArr, VuiAction.SETVALUE.getName(), "" + i2, "");
    }

    public static VuiElement generateStatefulButtonElement(int i, String[] strArr, String str) {
        return VuiUtils.generateStatefulButtonElement(i, strArr, VuiAction.SETVALUE.getName(), str, "");
    }

    public static VuiElement generateStatefulButtonElement(int i, String[] strArr, String str, String str2) {
        return VuiUtils.generateStatefulButtonElement(i, strArr, str, str2, "");
    }

    public static VuiElement generateStatefulButtonElement(int i, String[] strArr, String str, int i2) {
        return VuiUtils.generateStatefulButtonElement(i, strArr, str, "" + i2, "");
    }

    public static VuiElement generateStatefulButtonElement(int i, String[] strArr, String str, String str2, String str3) {
        return VuiUtils.generateStatefulButtonElement(i, strArr, str, str2, str3);
    }

    public static <T> T getValueByName(VuiElement vuiElement, String str) {
        return (T) VuiUtils.getValueByName(vuiElement, str);
    }
}
