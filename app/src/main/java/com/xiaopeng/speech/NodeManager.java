package com.xiaopeng.speech;

import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import com.xiaopeng.speech.coreapi.ISubscriber;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class NodeManager implements ConnectManager.OnConnectCallback {
    private Map<Class<? extends SpeechNode>, SpeechNode> mNodeMap = new HashMap();
    private volatile ISubscriber mSubscriber;
    private WorkerHandler mWorkerHandler;

    public void init(WorkerHandler workerHandler) {
        this.mWorkerHandler = workerHandler;
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        LogUtils.i("onConnect " + iSpeechEngine);
        if (iSpeechEngine == null) {
            return;
        }
        try {
            this.mSubscriber = iSpeechEngine.getSubscriber();
            this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.NodeManager.1
                @Override // java.lang.Runnable
                public void run() {
                    NodeManager.this.subscribeNodeList(true);
                }
            });
        } catch (RemoteException e) {
            LogUtils.e(this, e);
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mSubscriber = null;
    }

    public <T extends SpeechNode> T getNode(Class<T> cls) {
        return (T) lazyInitNode(cls);
    }

    public void subscribe(final Class<? extends SpeechNode> cls, final INodeListener iNodeListener) {
        this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.NodeManager.2
            @Override // java.lang.Runnable
            public void run() {
                SpeechNode lazyInitNode = NodeManager.this.lazyInitNode(cls);
                if (lazyInitNode != null) {
                    lazyInitNode.addListener(iNodeListener);
                }
            }
        });
    }

    public void unSubscribe(final Class<? extends SpeechNode> cls) {
        this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.NodeManager.3
            @Override // java.lang.Runnable
            public void run() {
                SpeechNode speechNode = (SpeechNode) NodeManager.this.mNodeMap.get(cls);
                if (speechNode == null) {
                    return;
                }
                NodeManager.this.unRegister(speechNode);
            }
        });
    }

    public void unSubscribe(final Class<? extends SpeechNode> cls, final INodeListener iNodeListener) {
        this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.NodeManager.4
            @Override // java.lang.Runnable
            public void run() {
                SpeechNode speechNode = (SpeechNode) NodeManager.this.mNodeMap.get(cls);
                if (speechNode == null) {
                    return;
                }
                speechNode.removeListener(iNodeListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SpeechNode lazyInitNode(Class<? extends SpeechNode> cls) {
        SpeechNode speechNode;
        SpeechNode speechNode2 = this.mNodeMap.get(cls);
        if (speechNode2 != null) {
            return speechNode2;
        }
        synchronized (this.mNodeMap) {
            speechNode = this.mNodeMap.get(cls);
            if (speechNode == null) {
                try {
                    speechNode = cls.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e(String.format("create %s error", cls));
                }
                if (speechNode != null) {
                    register(speechNode);
                    speechNode.setWorkerHandler(this.mWorkerHandler);
                }
            }
        }
        return speechNode;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void register(SpeechNode speechNode) {
        if (this.mNodeMap.containsKey(speechNode.getClass())) {
            LogUtils.e(String.format("node %s had register", speechNode));
            return;
        }
        LogUtils.i(String.format("register node:%s", speechNode));
        this.mNodeMap.put(speechNode.getClass(), speechNode);
        this.mWorkerHandler.optPost(new Runnable() { // from class: com.xiaopeng.speech.NodeManager.5
            @Override // java.lang.Runnable
            public void run() {
                NodeManager.this.subscribeNodeList(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unRegister(SpeechNode speechNode) {
        if (this.mNodeMap.containsKey(speechNode.getClass()) && this.mSubscriber != null) {
            try {
                this.mSubscriber.unSubscribe(speechNode);
                this.mNodeMap.remove(speechNode.getClass());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subscribeNodeList(boolean z) {
        if (this.mSubscriber == null) {
            LogUtils.e("mSubscriber == null");
            return;
        }
        for (Map.Entry<Class<? extends SpeechNode>, SpeechNode> entry : this.mNodeMap.entrySet()) {
            SpeechNode value = entry.getValue();
            if (!value.isSubscribed() || z) {
                LogUtils.i(String.format("do subscribe node:%s", value));
                if (value.getSubscribeEvents() == null || value.getSubscribeEvents().length <= 0) {
                    LogUtils.e("getSubscribeEvents.length == 0");
                } else {
                    try {
                        if (this.mSubscriber != null) {
                            this.mSubscriber.subscribe(value.getSubscribeEvents(), value);
                            value.setSubscribed(true);
                        }
                    } catch (RemoteException e) {
                        LogUtils.e(this, "subscribe error ", e);
                    }
                }
            }
        }
    }
}
