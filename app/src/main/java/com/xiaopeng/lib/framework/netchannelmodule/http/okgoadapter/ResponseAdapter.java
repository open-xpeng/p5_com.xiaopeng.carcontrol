package com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter;

import com.lzy.okgo.model.Response;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpCounter;
import com.xiaopeng.lib.framework.netchannelmodule.http.traffic.CountingInputStream;
import com.xiaopeng.lib.framework.netchannelmodule.http.traffic.ICollector;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class ResponseAdapter implements IResponse {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static volatile boolean mEnableStat;
    private ICollector mCollector;
    private String mDomain;
    private Response<String> mInternalResponse;
    private okhttp3.Response mRawResponse;

    public ResponseAdapter(Response response) {
        this.mCollector = new ICollector() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.ResponseAdapter.1
            @Override // com.xiaopeng.lib.framework.netchannelmodule.http.traffic.ICollector
            public String getDomain() {
                return ResponseAdapter.this.mDomain;
            }
        };
        this.mInternalResponse = response;
        this.mRawResponse = null;
        this.mDomain = getDomain(null);
    }

    public ResponseAdapter(okhttp3.Response response) {
        this.mCollector = new ICollector() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.ResponseAdapter.1
            @Override // com.xiaopeng.lib.framework.netchannelmodule.http.traffic.ICollector
            public String getDomain() {
                return ResponseAdapter.this.mDomain;
            }
        };
        this.mRawResponse = response;
        Response<String> response2 = new Response<>();
        this.mInternalResponse = response2;
        response2.setRawResponse(response);
        this.mDomain = getDomain(this.mRawResponse);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse
    public int code() {
        okhttp3.Response response = this.mRawResponse;
        if (response != null) {
            return response.code();
        }
        return this.mInternalResponse.code();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse
    public String body() {
        okhttp3.Response response = this.mRawResponse;
        if (response == null) {
            String body = this.mInternalResponse.body();
            if (body != null && mEnableStat) {
                HttpCounter.getInstance().addReceivedSize(this.mDomain, body.length());
            }
            return body;
        }
        String str = null;
        if (response.body() == null) {
            return null;
        }
        try {
            str = this.mRawResponse.body().string();
            if (mEnableStat) {
                HttpCounter.getInstance().addReceivedSize(this.mDomain, str.length());
            }
        } catch (Exception unused) {
        }
        return str;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse
    public InputStream byteStream() {
        okhttp3.Response response = this.mRawResponse;
        CountingInputStream countingInputStream = null;
        if (response == null || response.body() == null) {
            return null;
        }
        try {
            CountingInputStream countingInputStream2 = new CountingInputStream(this.mCollector, this.mRawResponse.body().byteStream());
            try {
                countingInputStream2.setStatisticCounter(HttpCounter.getInstance());
                return countingInputStream2;
            } catch (Exception unused) {
                countingInputStream = countingInputStream2;
                return countingInputStream;
            }
        } catch (Exception unused2) {
        }
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse
    public String header(String str) {
        okhttp3.Response response = this.mRawResponse;
        if (response == null) {
            return null;
        }
        return response.header(str);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse
    public String message() {
        okhttp3.Response response = this.mRawResponse;
        if (response == null) {
            return this.mInternalResponse.message();
        }
        return response.message();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse
    public Throwable getException() {
        return this.mInternalResponse.getException();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse
    public okhttp3.Response getRawResponse() {
        okhttp3.Response response = this.mRawResponse;
        return response != null ? response : this.mInternalResponse.getRawResponse();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse
    public Map<String, List<String>> headers() {
        okhttp3.Response rawResponse = getRawResponse();
        if (rawResponse == null || rawResponse.headers() == null) {
            return null;
        }
        return rawResponse.headers().toMultimap();
    }

    private String getDomain(okhttp3.Response response) {
        if (response == null || !response.isSuccessful() || response.request() == null) {
            return null;
        }
        return response.request().url().host();
    }

    public static void enableStat(boolean z) {
        mEnableStat = z;
    }
}
