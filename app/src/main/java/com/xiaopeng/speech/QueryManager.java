package com.xiaopeng.speech;

import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class QueryManager implements ConnectManager.OnConnectCallback {
    private static final String TAG = "QueryManager";
    private volatile IQueryInjector mIQueryInjector;
    private Map<Class<? extends SpeechQuery>, SpeechQuery> mQueryMap = new HashMap();
    private WorkerHandler mWorkerHandler;

    public void init(WorkerHandler workerHandler) {
        this.mWorkerHandler = workerHandler;
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        LogUtils.i(TAG, "onConnect " + iSpeechEngine);
        if (iSpeechEngine == null) {
            return;
        }
        try {
            this.mIQueryInjector = iSpeechEngine.getQueryInjector();
            this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.QueryManager.1
                @Override // java.lang.Runnable
                public void run() {
                    QueryManager.this.injectQueryList(true);
                }
            });
        } catch (RemoteException e) {
            LogUtils.e(this, e);
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mIQueryInjector = null;
    }

    public <T extends SpeechQuery> T getQuery(Class<T> cls) {
        return (T) lazyInitQuery(cls);
    }

    public void inject(final Class<? extends SpeechQuery> cls, final IQueryCaller iQueryCaller) {
        this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.QueryManager.2
            @Override // java.lang.Runnable
            public void run() {
                QueryManager.this.lazyInitQuery(cls).setQueryCaller(iQueryCaller);
            }
        });
    }

    public void unInject(final Class<? extends SpeechQuery> cls) {
        this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.QueryManager.3
            @Override // java.lang.Runnable
            public void run() {
                SpeechQuery speechQuery = (SpeechQuery) QueryManager.this.mQueryMap.get(cls);
                if (speechQuery == null) {
                    return;
                }
                QueryManager.this.unRegister(speechQuery);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SpeechQuery lazyInitQuery(Class<? extends SpeechQuery> cls) {
        SpeechQuery speechQuery;
        SpeechQuery speechQuery2 = this.mQueryMap.get(cls);
        if (speechQuery2 != null) {
            return speechQuery2;
        }
        synchronized (this.mQueryMap) {
            speechQuery = this.mQueryMap.get(cls);
            if (speechQuery == null) {
                try {
                    speechQuery = cls.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e(String.format("create %s error", cls));
                }
                if (speechQuery != null) {
                    register(speechQuery);
                    speechQuery.setWorkerHandler(this.mWorkerHandler);
                }
            }
        }
        return speechQuery;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void register(SpeechQuery speechQuery) {
        if (this.mQueryMap.containsKey(speechQuery.getClass())) {
            LogUtils.e(String.format("query %s had register", speechQuery));
            return;
        }
        LogUtils.i(String.format("register query:%s", speechQuery));
        this.mQueryMap.put(speechQuery.getClass(), speechQuery);
        this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.QueryManager.4
            @Override // java.lang.Runnable
            public void run() {
                QueryManager.this.injectQueryList(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unRegister(SpeechQuery speechQuery) {
        if (this.mQueryMap.containsKey(speechQuery.getClass()) && this.mIQueryInjector != null) {
            try {
                this.mIQueryInjector.unRegisterDataSensor(speechQuery.getQueryEvents());
                this.mQueryMap.remove(speechQuery.getClass());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void injectQueryList(boolean z) {
        if (this.mIQueryInjector == null) {
            LogUtils.e("mIQueryInjector == null");
            return;
        }
        for (Map.Entry<Class<? extends SpeechQuery>, SpeechQuery> entry : this.mQueryMap.entrySet()) {
            SpeechQuery value = entry.getValue();
            if (!value.isSubscribed() || z) {
                LogUtils.i(String.format("do inject query:%s", value));
                if (value.getQueryEvents() == null || value.getQueryEvents().length <= 0) {
                    LogUtils.e("getInjectEvents.length == 0");
                } else {
                    try {
                        if (this.mIQueryInjector != null) {
                            this.mIQueryInjector.registerDataSensor(value.getQueryEvents(), value);
                            value.setSubscribed(true);
                        }
                    } catch (RemoteException e) {
                        LogUtils.e(this, "inject error ", e);
                    }
                }
            }
        }
    }
}
