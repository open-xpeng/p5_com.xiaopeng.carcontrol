package com.xiaopeng.lib.apirouter;

import android.util.Pair;
import java.util.List;

/* loaded from: classes2.dex */
public class RemoteMethod {
    private int mId;
    private String mMethodName;
    private List<Pair<String, String>> mParamsList;

    public RemoteMethod(String str, int i, List<Pair<String, String>> list) {
        this.mMethodName = str;
        this.mId = i;
        this.mParamsList = list;
    }

    public String getMethodName() {
        return this.mMethodName;
    }

    public int getId() {
        return this.mId;
    }

    public List<Pair<String, String>> getParamsList() {
        return this.mParamsList;
    }
}
