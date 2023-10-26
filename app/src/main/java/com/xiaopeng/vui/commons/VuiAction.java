package com.xiaopeng.vui.commons;

import com.xiaopeng.carcontrol.view.speech.VuiActions;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public enum VuiAction {
    CLICK(VuiActions.CLICK),
    SETVALUE(VuiActions.SETVALUE),
    SCROLLBYX("ScrollByX"),
    SCROLLBYY("ScrollByY"),
    SETCHECK(VuiActions.SETCHECK),
    ITEMCLICK("ItemClick"),
    SELECTTAB("SelectTab"),
    SCROLLTO("ScrollTo"),
    SETSELECTED("SetSelected");
    
    private String name;

    VuiAction(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public static List<String> getVuiActionList() {
        ArrayList arrayList = new ArrayList();
        for (VuiAction vuiAction : values()) {
            arrayList.add(vuiAction.getName());
        }
        return arrayList;
    }
}
