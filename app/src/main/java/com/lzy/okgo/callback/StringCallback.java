package com.lzy.okgo.callback;

import com.lzy.okgo.convert.StringConvert;
import okhttp3.Response;

/* loaded from: classes.dex */
public abstract class StringCallback extends AbsCallback<String> {
    private StringConvert convert = new StringConvert();

    @Override // com.lzy.okgo.convert.Converter
    public String convertResponse(Response response) throws Throwable {
        String convertResponse = this.convert.convertResponse(response);
        response.close();
        return convertResponse;
    }
}
