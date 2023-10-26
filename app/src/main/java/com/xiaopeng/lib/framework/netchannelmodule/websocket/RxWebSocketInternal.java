package com.xiaopeng.lib.framework.netchannelmodule.websocket;

import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IWebSocketInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class RxWebSocketInternal {
    private static final int CLOSE_BY_CANCELLATION = 3000;
    private static final int CLOSE_CLIENT_NORMAL = 1000;
    private static final int DEFAULT_RETRY_INTERVAL = 1000;
    private OkHttpClient mClient;
    private Map<String, String> mHeaders;
    private String mLogTag;
    private Map<String, Observable<IWebSocketInfo>> mObservableMap;
    private long mPingInterval;
    private long mReconnectInterval;
    private boolean mShowLog;
    private Map<String, WebSocket> mWebSocketMap;

    private RxWebSocketInternal() {
        this.mLogTag = "RxWebSocket";
        this.mReconnectInterval = 1000L;
        this.mPingInterval = 1000L;
        try {
            Class.forName("okhttp3.OkHttpClient");
            try {
                Class.forName("io.reactivex.Observable");
                try {
                    Class.forName("io.reactivex.android.schedulers.AndroidSchedulers");
                    this.mObservableMap = new ConcurrentHashMap();
                    this.mWebSocketMap = new ConcurrentHashMap();
                    this.mHeaders = new ConcurrentHashMap();
                    this.mClient = new OkHttpClient();
                } catch (ClassNotFoundException unused) {
                    throw new RuntimeException("Must be dependency rxandroid 2.x");
                }
            } catch (ClassNotFoundException unused2) {
                throw new RuntimeException("Must be dependency rxjava 2.x");
            }
        } catch (ClassNotFoundException unused3) {
            throw new RuntimeException("Must be dependency okhttp3 !");
        }
    }

    public static RxWebSocketInternal getInstance() {
        return Holder.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class Holder {
        private static final RxWebSocketInternal INSTANCE = new RxWebSocketInternal();

        private Holder() {
        }
    }

    public void client(OkHttpClient okHttpClient) {
        Objects.requireNonNull(okHttpClient, "mClient == null");
        this.mClient = okHttpClient.newBuilder().pingInterval(this.mPingInterval, TimeUnit.MILLISECONDS).build();
    }

    public void header(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        this.mHeaders.put(str, str2);
    }

    public void sslSocketFactory(SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
        this.mClient = this.mClient.newBuilder().sslSocketFactory(sSLSocketFactory, x509TrustManager).build();
    }

    public void showLog(boolean z) {
        this.mShowLog = z;
    }

    public void showLog(boolean z, String str) {
        showLog(z);
        this.mLogTag = str;
    }

    public void reconnectInterval(long j) {
        this.mReconnectInterval = j;
    }

    public void pingInterval(long j) {
        this.mPingInterval = j;
        this.mClient = this.mClient.newBuilder().pingInterval(j, TimeUnit.MILLISECONDS).build();
    }

    public Observable<IWebSocketInfo> getWebSocketInfo(final String str, long j, TimeUnit timeUnit) {
        Observable<IWebSocketInfo> observable = this.mObservableMap.get(str);
        if (observable == null) {
            observable = Observable.create(new WebSocketOnSubscribe(str)).timeout(j, timeUnit).retry(new Predicate<Throwable>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.3
                @Override // io.reactivex.functions.Predicate
                public boolean test(Throwable th) throws Exception {
                    if (RxWebSocketInternal.this.mShowLog) {
                        Log.d(RxWebSocketInternal.this.mLogTag, "predicate retry for " + th);
                    }
                    return (th instanceof IOException) || (th instanceof TimeoutException);
                }
            }).doOnDispose(new Action() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.2
                @Override // io.reactivex.functions.Action
                public void run() throws Exception {
                    RxWebSocketInternal.this.mObservableMap.remove(str);
                    RxWebSocketInternal.this.mWebSocketMap.remove(str);
                    if (RxWebSocketInternal.this.mShowLog) {
                        Log.d(RxWebSocketInternal.this.mLogTag, "OnDispose");
                    }
                }
            }).doOnNext(new Consumer<IWebSocketInfo>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.1
                @Override // io.reactivex.functions.Consumer
                public void accept(IWebSocketInfo iWebSocketInfo) throws Exception {
                    if (iWebSocketInfo.isOnOpen()) {
                        RxWebSocketInternal.this.mWebSocketMap.put(str, ((WebSocketInfo) iWebSocketInfo).getWebSocket());
                    }
                }
            }).share().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            this.mObservableMap.put(str, observable);
        } else {
            WebSocket webSocket = this.mWebSocketMap.get(str);
            if (webSocket != null) {
                observable = observable.startWith((Observable<IWebSocketInfo>) new WebSocketInfo(webSocket, IWebSocketInfo.STATE.OPEN));
            }
        }
        return observable.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<IWebSocketInfo> getWebSocketInfo(String str) {
        return getWebSocketInfo(str, 1L, TimeUnit.HOURS);
    }

    public Observable<String> getWebSocketString(String str) {
        return getWebSocketInfo(str).filter(new Predicate<IWebSocketInfo>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.5
            @Override // io.reactivex.functions.Predicate
            public boolean test(IWebSocketInfo iWebSocketInfo) throws Exception {
                return iWebSocketInfo.stringMessage() != null;
            }
        }).map(new Function<IWebSocketInfo, String>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.4
            @Override // io.reactivex.functions.Function
            public String apply(IWebSocketInfo iWebSocketInfo) throws Exception {
                return iWebSocketInfo.stringMessage();
            }
        });
    }

    public Observable<ByteString> getWebSocketByteString(String str) {
        return getWebSocketInfo(str).filter(new Predicate<IWebSocketInfo>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.7
            @Override // io.reactivex.functions.Predicate
            public boolean test(IWebSocketInfo iWebSocketInfo) throws Exception {
                return iWebSocketInfo.byteStringMessage() != null;
            }
        }).map(new Function<IWebSocketInfo, ByteString>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.6
            @Override // io.reactivex.functions.Function
            public ByteString apply(IWebSocketInfo iWebSocketInfo) throws Exception {
                return iWebSocketInfo.byteStringMessage();
            }
        });
    }

    public Observable<WebSocket> getWebSocket(String str) {
        return getWebSocketInfo(str).filter(new Predicate<IWebSocketInfo>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.9
            @Override // io.reactivex.functions.Predicate
            public boolean test(IWebSocketInfo iWebSocketInfo) throws Exception {
                return ((WebSocketInfo) iWebSocketInfo).getWebSocket() != null;
            }
        }).map(new Function<IWebSocketInfo, WebSocket>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.8
            @Override // io.reactivex.functions.Function
            public WebSocket apply(IWebSocketInfo iWebSocketInfo) throws Exception {
                return ((WebSocketInfo) iWebSocketInfo).getWebSocket();
            }
        });
    }

    public void send(String str, String str2) {
        WebSocket webSocket = this.mWebSocketMap.get(str);
        if (webSocket != null) {
            webSocket.send(str2);
            return;
        }
        throw new IllegalStateException("WebSocket channel not open");
    }

    public void send(String str, ByteString byteString) {
        WebSocket webSocket = this.mWebSocketMap.get(str);
        if (webSocket != null) {
            webSocket.send(byteString);
            return;
        }
        throw new IllegalStateException("WebSocket channel not open");
    }

    public void asyncSend(String str, final String str2) {
        getWebSocket(str).take(1L).subscribe(new Consumer<WebSocket>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.10
            @Override // io.reactivex.functions.Consumer
            public void accept(WebSocket webSocket) throws Exception {
                webSocket.send(str2);
            }
        });
    }

    public void asyncSend(String str, final ByteString byteString) {
        getWebSocket(str).take(1L).subscribe(new Consumer<WebSocket>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.RxWebSocketInternal.11
            @Override // io.reactivex.functions.Consumer
            public void accept(WebSocket webSocket) throws Exception {
                webSocket.send(byteString);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Request getRequest(String str) {
        Request.Builder url = new Request.Builder().get().url(str);
        if (this.mHeaders.size() > 0) {
            for (String str2 : this.mHeaders.keySet()) {
                url.addHeader(str2, this.mHeaders.get(str2));
            }
        }
        return url.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class WebSocketOnSubscribe implements ObservableOnSubscribe<IWebSocketInfo> {
        private String url;
        private WebSocket webSocket;

        public WebSocketOnSubscribe(String str) {
            this.url = str;
        }

        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(ObservableEmitter<IWebSocketInfo> observableEmitter) throws Exception {
            if (this.webSocket != null && Looper.getMainLooper().getThread() != Thread.currentThread()) {
                long j = RxWebSocketInternal.this.mReconnectInterval;
                if (j == 0) {
                    j = 1000;
                }
                SystemClock.sleep(j);
                observableEmitter.onNext(WebSocketInfo.createReconnect());
            }
            initWebSocket(observableEmitter);
        }

        private void initWebSocket(ObservableEmitter<IWebSocketInfo> observableEmitter) {
            this.webSocket = RxWebSocketInternal.this.mClient.newWebSocket(RxWebSocketInternal.this.getRequest(this.url), new WebSocketListenerInternal(observableEmitter, RxWebSocketInternal.this.mWebSocketMap, this.url, RxWebSocketInternal.this.mShowLog, RxWebSocketInternal.this.mLogTag));
            observableEmitter.setCancellable(new CancelableInternal(this.webSocket, this.url, RxWebSocketInternal.this.mShowLog, RxWebSocketInternal.this.mLogTag));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class CancelableInternal implements Cancellable {
        private boolean showLog;
        private String tag;
        private String url;
        private WebSocket webSocket;

        public CancelableInternal(WebSocket webSocket, String str, boolean z, String str2) {
            this.webSocket = webSocket;
            this.url = str;
            this.showLog = z;
            this.tag = str2;
        }

        @Override // io.reactivex.functions.Cancellable
        public void cancel() throws Exception {
            this.webSocket.close(3000, "close WebSocket from rx cancel");
            if (this.showLog) {
                Log.d(this.tag, this.url + " --> cancel for rx cancel");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class WebSocketListenerInternal extends WebSocketListener {
        private ObservableEmitter<IWebSocketInfo> emitter;
        private boolean showLog;
        private String tag;
        private String url;
        private Map<String, WebSocket> webSocketMap;

        public WebSocketListenerInternal(ObservableEmitter<IWebSocketInfo> observableEmitter, Map<String, WebSocket> map, String str, boolean z, String str2) {
            this.emitter = observableEmitter;
            this.webSocketMap = map;
            this.url = str;
            this.showLog = z;
            this.tag = str2;
        }

        @Override // okhttp3.WebSocketListener
        public void onOpen(WebSocket webSocket, Response response) {
            if (this.showLog) {
                Log.d(this.tag, this.url + " --> onOpen");
            }
            this.webSocketMap.put(this.url, webSocket);
            if (this.emitter.isDisposed()) {
                return;
            }
            this.emitter.onNext(new WebSocketInfo(webSocket, IWebSocketInfo.STATE.OPEN));
        }

        @Override // okhttp3.WebSocketListener
        public void onMessage(WebSocket webSocket, String str) {
            if (this.emitter.isDisposed()) {
                return;
            }
            this.emitter.onNext(new WebSocketInfo(webSocket, str));
        }

        @Override // okhttp3.WebSocketListener
        public void onMessage(WebSocket webSocket, ByteString byteString) {
            if (this.emitter.isDisposed()) {
                return;
            }
            this.emitter.onNext(new WebSocketInfo(webSocket, byteString));
        }

        @Override // okhttp3.WebSocketListener
        public void onFailure(WebSocket webSocket, Throwable th, Response response) {
            if (this.showLog && !(th instanceof UnknownHostException)) {
                Log.e(this.tag, th.toString() + webSocket.request().url().uri().getPath());
            }
            if (this.emitter.isDisposed()) {
                return;
            }
            this.emitter.onError(th);
        }

        @Override // okhttp3.WebSocketListener
        public void onClosing(WebSocket webSocket, int i, String str) {
            webSocket.close(1000, null);
        }

        @Override // okhttp3.WebSocketListener
        public void onClosed(WebSocket webSocket, int i, String str) {
            if (this.showLog) {
                Log.d(this.tag, this.url + " --> onClosed:code= " + i + "reason:" + str);
            }
            if (this.emitter.isDisposed()) {
                return;
            }
            WebSocketInfo webSocketInfo = new WebSocketInfo(webSocket, IWebSocketInfo.STATE.CLOSED);
            webSocketInfo.setClosedReason(i, str);
            this.emitter.onNext(webSocketInfo);
        }
    }

    public void close(String str) {
        WebSocket webSocket = this.mWebSocketMap.get(str);
        if (webSocket != null) {
            this.mObservableMap.remove(str);
            this.mWebSocketMap.remove(str);
            webSocket.close(1000, null);
        }
    }
}
