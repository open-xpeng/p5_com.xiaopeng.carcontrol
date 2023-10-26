package com.lzy.okgo.convert;

import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes.dex */
public class StringConvert implements Converter<String> {
    @Override // com.lzy.okgo.convert.Converter
    public String convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        return body.string();
    }
}
