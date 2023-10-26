package com.xiaopeng.carcontrol.viewmodel.light;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public enum AtlColorType {
    Mono("mono"),
    Double("double");
    
    List<String> alias;
    String command;

    AtlColorType(String command) {
        ArrayList arrayList = new ArrayList();
        this.alias = arrayList;
        this.command = command;
        arrayList.add(command);
    }

    public boolean match(String text) {
        for (String str : this.alias) {
            if (str.equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }

    public String toAtlCommand() {
        return this.command;
    }
}
