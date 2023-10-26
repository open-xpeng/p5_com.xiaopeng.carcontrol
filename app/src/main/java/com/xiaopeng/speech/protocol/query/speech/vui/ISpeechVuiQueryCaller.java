package com.xiaopeng.speech.protocol.query.speech.vui;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface ISpeechVuiQueryCaller extends IQueryCaller {
    String getStatefulButtonValue(String str, String str2, String str3, String str4);

    int getXSliderIndex(String str, String str2, String str3, String str4);

    boolean isCheckBoxChecked(String str, String str2, String str3, String str4);

    boolean isElementCanScrolled(String str, String str2, String str3, String str4, String str5);

    boolean isElementEnabled(String str, String str2, String str3, String str4);

    default boolean isRadiobuttonChecked(String str, String str2, String str3, String str4) {
        return false;
    }

    boolean isStatefulButtonChecked(String str, String str2, String str3, String str4);

    boolean isSwitchChecked(String str, String str2, String str3, String str4);

    boolean isTableLayoutSelected(int i, String str, String str2, String str3, String str4);

    default String isViewVisibleByScrollView(String str, String str2, String str3, String str4) {
        return "";
    }

    boolean isVuiSwitchOpened();

    default boolean reBuildScene(String[] strArr) {
        return true;
    }
}
