package com.lzy.okgo.request.base;

import android.text.TextUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.AdapterParam;
import com.lzy.okgo.adapter.CacheCall;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.adapter.CallAdapter;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cache.policy.CachePolicy;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.ProgressRequestBody;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.HttpUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes.dex */
public abstract class Request<T, R extends Request> implements Serializable {
    private static final long serialVersionUID = -7174118653689916252L;
    protected String baseUrl;
    protected String cacheKey;
    protected CacheMode cacheMode;
    protected transient CachePolicy<T> cachePolicy;
    protected long cacheTime;
    protected transient Call<T> call;
    protected transient Callback<T> callback;
    protected transient OkHttpClient client;
    protected transient Converter<T> converter;
    protected transient okhttp3.Request mRequest;
    protected int retryCount;
    protected transient Object tag;
    protected transient ProgressRequestBody.UploadInterceptor uploadInterceptor;
    protected String url;
    protected HttpParams params = new HttpParams();
    protected HttpHeaders headers = new HttpHeaders();

    public abstract okhttp3.Request generateRequest(RequestBody requestBody);

    protected abstract RequestBody generateRequestBody();

    public abstract HttpMethod getMethod();

    public Request(String str) {
        this.url = str;
        this.baseUrl = str;
        OkGo okGo = OkGo.getInstance();
        String acceptLanguage = HttpHeaders.getAcceptLanguage();
        if (!TextUtils.isEmpty(acceptLanguage)) {
            headers(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
        }
        String userAgent = HttpHeaders.getUserAgent();
        if (!TextUtils.isEmpty(userAgent)) {
            headers("User-Agent", userAgent);
        }
        if (okGo.getCommonParams() != null) {
            params(okGo.getCommonParams());
        }
        if (okGo.getCommonHeaders() != null) {
            headers(okGo.getCommonHeaders());
        }
        this.retryCount = okGo.getRetryCount();
        this.cacheMode = okGo.getCacheMode();
        this.cacheTime = okGo.getCacheTime();
    }

    public R tag(Object obj) {
        this.tag = obj;
        return this;
    }

    public R retryCount(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("retryCount must > 0");
        }
        this.retryCount = i;
        return this;
    }

    public R client(OkHttpClient okHttpClient) {
        HttpUtils.checkNotNull(okHttpClient, "OkHttpClient == null");
        this.client = okHttpClient;
        return this;
    }

    public R call(Call<T> call) {
        HttpUtils.checkNotNull(call, "call == null");
        this.call = call;
        return this;
    }

    public R converter(Converter<T> converter) {
        HttpUtils.checkNotNull(converter, "converter == null");
        this.converter = converter;
        return this;
    }

    public R cacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return this;
    }

    public R cachePolicy(CachePolicy<T> cachePolicy) {
        HttpUtils.checkNotNull(cachePolicy, "cachePolicy == null");
        this.cachePolicy = cachePolicy;
        return this;
    }

    public R cacheKey(String str) {
        HttpUtils.checkNotNull(str, "cacheKey == null");
        this.cacheKey = str;
        return this;
    }

    public R cacheTime(long j) {
        if (j <= -1) {
            j = -1;
        }
        this.cacheTime = j;
        return this;
    }

    public R headers(HttpHeaders httpHeaders) {
        this.headers.put(httpHeaders);
        return this;
    }

    public R headers(String str, String str2) {
        this.headers.put(str, str2);
        return this;
    }

    public R removeHeader(String str) {
        this.headers.remove(str);
        return this;
    }

    public R removeAllHeaders() {
        this.headers.clear();
        return this;
    }

    public R params(HttpParams httpParams) {
        this.params.put(httpParams);
        return this;
    }

    public R params(Map<String, String> map, boolean... zArr) {
        this.params.put(map, zArr);
        return this;
    }

    public R params(String str, String str2, boolean... zArr) {
        this.params.put(str, str2, zArr);
        return this;
    }

    public R params(String str, int i, boolean... zArr) {
        this.params.put(str, i, zArr);
        return this;
    }

    public R params(String str, float f, boolean... zArr) {
        this.params.put(str, f, zArr);
        return this;
    }

    public R params(String str, double d, boolean... zArr) {
        this.params.put(str, d, zArr);
        return this;
    }

    public R params(String str, long j, boolean... zArr) {
        this.params.put(str, j, zArr);
        return this;
    }

    public R params(String str, char c, boolean... zArr) {
        this.params.put(str, c, zArr);
        return this;
    }

    public R params(String str, boolean z, boolean... zArr) {
        this.params.put(str, z, zArr);
        return this;
    }

    public R addUrlParams(String str, List<String> list) {
        this.params.putUrlParams(str, list);
        return this;
    }

    public R removeParam(String str) {
        this.params.remove(str);
        return this;
    }

    public R removeAllParams() {
        this.params.clear();
        return this;
    }

    public R uploadInterceptor(ProgressRequestBody.UploadInterceptor uploadInterceptor) {
        this.uploadInterceptor = uploadInterceptor;
        return this;
    }

    public String getUrlParam(String str) {
        List<String> list = this.params.urlParamsMap.get(str);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public HttpParams.FileWrapper getFileParam(String str) {
        List<HttpParams.FileWrapper> list = this.params.fileParamsMap.get(str);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public HttpParams getParams() {
        return this.params;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public String getUrl() {
        return this.url;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public Object getTag() {
        return this.tag;
    }

    public CacheMode getCacheMode() {
        return this.cacheMode;
    }

    public CachePolicy<T> getCachePolicy() {
        return this.cachePolicy;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public long getCacheTime() {
        return this.cacheTime;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public okhttp3.Request getRequest() {
        return this.mRequest;
    }

    public void setCallback(Callback<T> callback) {
        this.callback = callback;
    }

    public Converter<T> getConverter() {
        if (this.converter == null) {
            this.converter = this.callback;
        }
        HttpUtils.checkNotNull(this.converter, "converter == null, do you forget to call Request#converter(Converter<T>) ?");
        return this.converter;
    }

    public okhttp3.Call getRawCall() {
        RequestBody generateRequestBody = generateRequestBody();
        if (generateRequestBody != null) {
            ProgressRequestBody progressRequestBody = new ProgressRequestBody(generateRequestBody, this.callback);
            progressRequestBody.setInterceptor(this.uploadInterceptor);
            this.mRequest = generateRequest(progressRequestBody);
        } else {
            this.mRequest = generateRequest(null);
        }
        if (this.client == null) {
            this.client = OkGo.getInstance().getOkHttpClient();
        }
        return this.client.newCall(this.mRequest);
    }

    public Call<T> adapt() {
        Call<T> call = this.call;
        return call == null ? new CacheCall(this) : call;
    }

    public <E> E adapt(CallAdapter<T, E> callAdapter) {
        Call<T> call = this.call;
        if (call == null) {
            call = new CacheCall<>(this);
        }
        return callAdapter.adapt(call, null);
    }

    public <E> E adapt(AdapterParam adapterParam, CallAdapter<T, E> callAdapter) {
        Call<T> call = this.call;
        if (call == null) {
            call = new CacheCall<>(this);
        }
        return callAdapter.adapt(call, adapterParam);
    }

    public Response execute() throws IOException {
        return getRawCall().execute();
    }

    public void execute(Callback<T> callback) {
        HttpUtils.checkNotNull(callback, "callback == null");
        this.callback = callback;
        adapt().execute(callback);
    }
}
