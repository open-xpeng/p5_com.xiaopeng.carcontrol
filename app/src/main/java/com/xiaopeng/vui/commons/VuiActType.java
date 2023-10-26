package com.xiaopeng.vui.commons;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public enum VuiActType {
    SEARCH("Search"),
    SELECT("Select"),
    EDIT("Edit"),
    OPEN("Open"),
    DELETE("Delete"),
    DETAIL("Detail"),
    EXPANDFOLD("ExpandFold"),
    ROLL("Roll"),
    TAB("Tab"),
    SELECTTAB("SelectTab"),
    SLIDE("Slide"),
    UP("Up"),
    DOWN("Down"),
    LEFT("Left"),
    RIGHT("Right"),
    SET("Set"),
    SORT("Sort"),
    EXPAND("Expand"),
    ADD("Add"),
    PLAY("Play"),
    NULL("Null");
    
    private String type;

    VuiActType(String str) {
        this.type = str;
    }

    public String getType() {
        return this.type;
    }

    public static List<String> getVuiActTypeList() {
        ArrayList arrayList = new ArrayList();
        for (VuiActType vuiActType : values()) {
            arrayList.add(vuiActType.getType());
        }
        return arrayList;
    }
}
