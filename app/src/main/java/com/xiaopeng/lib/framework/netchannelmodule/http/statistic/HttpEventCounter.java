package com.xiaopeng.lib.framework.netchannelmodule.http.statistic;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.netchannelmodule.common.ContextNetStatusProvider;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;
import com.xiaopeng.xpmeditation.util.TimeUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public enum HttpEventCounter {
    INSTANCE;
    
    private static final String EVENT_NAME = "net_http_event";
    private static final long MAX_VALUE_COUNT = 10;
    private static final String TAG = "HttpEventCounter";
    private IDataLog mDataLog;
    private Map<String, EventData> mEventData = new HashMap();
    private EventListener mEventListener = new EventListener() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1
        @Override // okhttp3.EventListener
        public void callStart(final Call call) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.1
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).callStart();
                }
            }, "callStart");
        }

        @Override // okhttp3.EventListener
        public void dnsStart(final Call call, final String str) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.2
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).dnsStart(str);
                }
            }, "dnsStart");
        }

        @Override // okhttp3.EventListener
        public void dnsEnd(final Call call, final String str, final List<InetAddress> list) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.3
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).dnsEnd(str, list);
                }
            }, "dnsEnd");
        }

        @Override // okhttp3.EventListener
        public void connectStart(final Call call, final InetSocketAddress inetSocketAddress, final Proxy proxy) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.4
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).connectStart(inetSocketAddress, proxy);
                }
            }, "connectStart");
        }

        @Override // okhttp3.EventListener
        public void secureConnectStart(final Call call) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.5
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).secureConnectStart();
                }
            }, "secureConnectStart");
        }

        @Override // okhttp3.EventListener
        public void secureConnectEnd(final Call call, final Handshake handshake) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.6
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).secureConnectEnd(handshake);
                }
            }, "secureConnectEnd");
        }

        @Override // okhttp3.EventListener
        public void connectEnd(final Call call, final InetSocketAddress inetSocketAddress, final Proxy proxy, final Protocol protocol) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.7
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).connectEnd(inetSocketAddress, proxy, protocol);
                }
            }, "connectEnd");
        }

        @Override // okhttp3.EventListener
        public void connectFailed(final Call call, final InetSocketAddress inetSocketAddress, final Proxy proxy, final Protocol protocol, final IOException iOException) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.8
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).connectFailed(inetSocketAddress, proxy, protocol, iOException);
                }
            }, "connectFailed");
        }

        @Override // okhttp3.EventListener
        public void connectionAcquired(final Call call, final Connection connection) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.9
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).connectionAcquired(connection);
                }
            }, "connectionAcquired");
        }

        @Override // okhttp3.EventListener
        public void connectionReleased(final Call call, final Connection connection) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.10
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).connectionReleased(connection);
                }
            }, "connectionReleased");
        }

        @Override // okhttp3.EventListener
        public void requestHeadersStart(final Call call) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.11
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).requestHeadersStart();
                }
            }, "requestHeadersStart");
        }

        @Override // okhttp3.EventListener
        public void requestHeadersEnd(final Call call, final Request request) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.12
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).requestHeadersEnd(request);
                }
            }, "requestHeadersEnd");
        }

        @Override // okhttp3.EventListener
        public void requestBodyStart(final Call call) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.13
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).requestBodyStart();
                }
            }, "requestBodyStart");
        }

        @Override // okhttp3.EventListener
        public void requestBodyEnd(final Call call, final long j) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.14
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).requestBodyEnd(j);
                }
            }, "requestBodyEnd");
        }

        @Override // okhttp3.EventListener
        public void responseHeadersStart(final Call call) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.15
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).responseHeadersStart();
                }
            }, "responseHeadersStart");
        }

        @Override // okhttp3.EventListener
        public void responseHeadersEnd(final Call call, final Response response) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.16
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).responseHeadersEnd(response);
                }
            }, "responseHeadersEnd");
        }

        @Override // okhttp3.EventListener
        public void responseBodyStart(final Call call) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.17
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).responseBodyStart();
                }
            }, "responseBodyStart");
        }

        @Override // okhttp3.EventListener
        public void responseBodyEnd(final Call call, final long j) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.18
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).responseBodyEnd(j);
                }
            }, "responseBodyEnd");
        }

        @Override // okhttp3.EventListener
        public void callEnd(final Call call) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.19
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).callEnd();
                    HttpEventCounter.this.commitData();
                }
            }, "callEnd");
        }

        @Override // okhttp3.EventListener
        public void callFailed(final Call call, final IOException iOException) {
            HttpEventCounter.this.runOnWorkThread(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpEventCounter.1.20
                @Override // java.lang.Runnable
                public void run() {
                    HttpEventCounter.this.getEventData(call).callFailed(iOException);
                    HttpEventCounter.this.commitData();
                }
            }, "callFailed");
        }
    };
    private final Handler mHandler;
    private long mLastHour;

    HttpEventCounter() {
        HandlerThread handlerThread = new HandlerThread(TAG, 10);
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void commitData() {
        Context applicationContext = ContextNetStatusProvider.getApplicationContext();
        if (applicationContext == null) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis() / TimeUtil.ONE_HOUR;
        if (this.mLastHour == 0) {
            this.mLastHour = currentTimeMillis;
        }
        if (this.mEventData.size() > MAX_VALUE_COUNT || this.mLastHour < currentTimeMillis) {
            Iterator<Map.Entry<String, EventData>> it = this.mEventData.entrySet().iterator();
            JSONArray jSONArray = new JSONArray();
            while (it.hasNext()) {
                EventData value = it.next().getValue();
                if (value.readyPublish()) {
                    jSONArray.put(value.toJson());
                    it.remove();
                }
            }
            if (this.mEventData.size() > MAX_VALUE_COUNT) {
                this.mEventData.clear();
            }
            if (jSONArray.length() > 0 && !DeviceInfoUtils.isInternationalVer()) {
                if (this.mDataLog == null) {
                    this.mDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                }
                this.mDataLog.sendStatOriginData(EVENT_NAME, buildData(applicationContext, jSONArray));
            }
            this.mLastHour = currentTimeMillis;
        }
    }

    private String buildData(Context context, JSONArray jSONArray) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("pk", context.getPackageName());
            jSONObject.put("dt", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public EventListener getEventListener() {
        return this.mEventListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EventData getEventData(Call call) {
        EventData eventData = this.mEventData.get(call.toString());
        if (eventData == null) {
            eventData = new EventData(call);
        }
        this.mEventData.put(call.toString(), eventData);
        return eventData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runOnWorkThread(Runnable runnable, String str) {
        this.mHandler.post(runnable);
    }
}
