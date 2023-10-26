package com.xiaopeng.speech.vui.filter;

import android.text.TextUtils;
import com.xiaopeng.carcontrol.view.speech.VuiActions;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class ListClickEventFaultFilter extends BaseFaultFilter {
    @Override // com.xiaopeng.speech.vui.filter.IFilter
    public VuiElement doFilter(VuiElement vuiElement) {
        String[] split;
        if (vuiElement != null && !TextUtils.isEmpty(vuiElement.getId()) && vuiElement.getResultActions().contains(VuiActions.CLICK) && (split = vuiElement.getId().split("_")) != null && split.length > 0) {
            for (String str : split) {
                if (str.length() <= 4) {
                    vuiElement.setResultActions(Arrays.asList("listItemClick"));
                }
            }
        }
        return vuiElement;
    }
}
