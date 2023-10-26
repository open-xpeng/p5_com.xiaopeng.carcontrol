package com.xiaopeng.lib.framework.netchannelmodule.http.statistic;

import com.xiaopeng.lib.framework.netchannelmodule.common.ContextNetStatusProvider;
import com.xiaopeng.lib.utils.NetUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class EventData {
    private static final int CALL_END = 17;
    private static final int CALL_START = 0;
    private static final int CONNECTION_ACQUIRED = 7;
    private static final int CONNECTION_RELEASED = 16;
    private static final int CONNECT_END = 6;
    private static final int CONNECT_START = 3;
    private static final int DNS_END = 2;
    private static final int DNS_START = 1;
    private static final int REQUEST_BODY_END = 11;
    private static final int REQUEST_BODY_START = 10;
    private static final int REQUEST_HEADERS_END = 9;
    private static final int REQUEST_HEADERS_START = 8;
    private static final int RESPONSE_BODY_END = 15;
    private static final int RESPONSE_BODY_START = 14;
    private static final int RESPONSE_HEADERS_END = 13;
    private static final int RESPONSE_HEADERS_START = 12;
    private static final int SECURE_CONNECT_END = 5;
    private static final int SECURE_CONNECT_START = 4;
    private String mCallException;
    private String mCallUrl;
    private String mMethod;
    private long mRespCode = 0;
    private long mRespBodySize = 0;
    private long[] mTimeArray = new long[18];
    private int mNetType = 0;
    private int mNetStrength = 0;
    private boolean mReadyPublish = false;
    private long mCreateTime = System.currentTimeMillis();

    public void connectFailed(InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol, IOException iOException) {
    }

    public EventData(Call call) {
        this.mCallUrl = call.request().url().toString();
        if (this.mCallUrl.length() > 70) {
            this.mCallUrl = this.mCallUrl.substring(0, 70);
        }
    }

    public void callStart() {
        this.mTimeArray[0] = timeDiv();
    }

    public void dnsStart(String str) {
        this.mTimeArray[1] = timeDiv();
    }

    public void dnsEnd(String str, List<InetAddress> list) {
        this.mTimeArray[2] = timeDiv();
    }

    public void connectStart(InetSocketAddress inetSocketAddress, Proxy proxy) {
        this.mTimeArray[3] = timeDiv();
    }

    public void secureConnectStart() {
        this.mTimeArray[4] = timeDiv();
    }

    public void secureConnectEnd(Handshake handshake) {
        this.mTimeArray[5] = timeDiv();
    }

    public void connectEnd(InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        this.mTimeArray[6] = timeDiv();
    }

    public void connectionAcquired(Connection connection) {
        this.mTimeArray[7] = timeDiv();
    }

    public void requestHeadersStart() {
        this.mTimeArray[8] = timeDiv();
    }

    public void requestHeadersEnd(Request request) {
        this.mMethod = request.method();
        this.mTimeArray[9] = timeDiv();
    }

    public void requestBodyStart() {
        this.mTimeArray[10] = timeDiv();
    }

    public void requestBodyEnd(long j) {
        this.mTimeArray[11] = timeDiv();
    }

    public void responseHeadersStart() {
        this.mTimeArray[12] = timeDiv();
    }

    public void responseHeadersEnd(Response response) {
        this.mTimeArray[13] = timeDiv();
        this.mRespCode = response.code();
    }

    public void responseBodyStart() {
        this.mTimeArray[14] = timeDiv();
    }

    public void responseBodyEnd(long j) {
        this.mRespBodySize = j;
        this.mTimeArray[15] = timeDiv();
    }

    public void connectionReleased(Connection connection) {
        this.mTimeArray[16] = timeDiv();
    }

    public void callEnd() {
        this.mTimeArray[17] = timeDiv();
        this.mReadyPublish = true;
        this.mNetType = ContextNetStatusProvider.getNetType();
        this.mNetStrength = ContextNetStatusProvider.getNetStrength();
    }

    public void callFailed(IOException iOException) {
        this.mTimeArray[17] = timeDiv();
        this.mCallException = getExceptionString(iOException);
        this.mReadyPublish = true;
        this.mNetType = ContextNetStatusProvider.getNetType();
        this.mNetStrength = ContextNetStatusProvider.getNetStrength();
    }

    private String getExceptionString(Exception exc) {
        String exc2 = exc.toString();
        return exc2.length() > 70 ? exc2.substring(0, 70) : exc2;
    }

    public boolean readyPublish() {
        return this.mReadyPublish || System.currentTimeMillis() - this.mCreateTime > 600000;
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ti", this.mCreateTime);
            jSONObject.put("u", this.mCallUrl);
            jSONObject.put("ne", this.mNetType);
            jSONObject.put("ns", this.mNetStrength);
            long j = this.mRespBodySize;
            if (j > 0) {
                jSONObject.put("bs", j);
            }
            long j2 = this.mRespCode;
            if (j2 > 0) {
                jSONObject.put("cd", j2);
            }
            String str = this.mCallException;
            if (str != null) {
                jSONObject.put("ce", str);
                jSONObject.put("ta", NetUtils.getTrafficStatus());
            }
            String str2 = this.mMethod;
            if (str2 != null) {
                jSONObject.put("md", str2);
            }
            jSONObject.put("tt", array(this.mTimeArray));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private long timeDiv() {
        return System.currentTimeMillis() - this.mCreateTime;
    }

    private JSONArray array(long[] jArr) {
        JSONArray jSONArray = new JSONArray();
        for (long j : jArr) {
            jSONArray.put(j);
        }
        return jSONArray;
    }
}
