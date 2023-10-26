package com.lzy.okgo.request;

import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.request.base.NoBodyRequest;
import okhttp3.Request;
import okhttp3.RequestBody;

/* loaded from: classes.dex */
public class GetRequest<T> extends NoBodyRequest<T, GetRequest<T>> {
    public GetRequest(String str) {
        super(str);
    }

    @Override // com.lzy.okgo.request.base.Request
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override // com.lzy.okgo.request.base.Request
    public Request generateRequest(RequestBody requestBody) {
        return generateRequestBuilder(requestBody).get().url(this.url).tag(this.tag).build();
    }
}
