package com.xiaopeng.speech.protocol.node.carcontrol.bean;

import android.text.TextUtils;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class UserBookValue {
    private String keyword;

    public static UserBookValue fromJson(String str) {
        UserBookValue userBookValue = new UserBookValue();
        try {
            String optString = new JSONObject(str).optString("keyword");
            if (!TextUtils.isEmpty(optString)) {
                userBookValue.keyword = optString;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userBookValue;
    }

    public String getKeyword() {
        return this.keyword;
    }
}
