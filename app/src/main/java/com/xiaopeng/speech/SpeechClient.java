package com.xiaopeng.speech;

import android.content.Context;
import android.os.HandlerThread;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.actor.Actor;
import com.xiaopeng.speech.actor.ActorBridge;
import com.xiaopeng.speech.asr.Recognizer;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import com.xiaopeng.speech.proxy.ASREngineProxy;
import com.xiaopeng.speech.proxy.AgentProxy;
import com.xiaopeng.speech.proxy.AppMgrProxy;
import com.xiaopeng.speech.proxy.CarSystemPropertyProxy;
import com.xiaopeng.speech.proxy.HotwordEngineProxy;
import com.xiaopeng.speech.proxy.QueryInjectorProxy;
import com.xiaopeng.speech.proxy.RecordEngineProxy;
import com.xiaopeng.speech.proxy.SoundLockStateProxy;
import com.xiaopeng.speech.proxy.SpeechStateProxy;
import com.xiaopeng.speech.proxy.SubscriberProxy;
import com.xiaopeng.speech.proxy.TTSEngineProxy;
import com.xiaopeng.speech.proxy.VADEngineProxy;
import com.xiaopeng.speech.proxy.WakeupEngineProxy;
import com.xiaopeng.speech.proxy.WindowEngineProxy;

/* loaded from: classes2.dex */
public class SpeechClient implements ConnectManager.OnConnectCallback {
    private ASREngineProxy mASREngineProxy;
    private ActorBridge mActorBridge;
    private AgentProxy mAgentProxy;
    private AppMgrProxy mAppMgrProxy;
    private CarSystemPropertyProxy mCarSystemPropertyProxy;
    private ConnectManager mConnectManager;
    private Context mContext;
    private HotwordEngineProxy mHotwordEngineProxy;
    private NodeManager mNodeManager;
    private QueryInjectorProxy mQueryInjectorProxy;
    private QueryManager mQueryManager;
    private Recognizer mRecognizer;
    private RecordEngineProxy mRecordEngineProxy;
    private SoundLockStateProxy mSoundLockStateProxy;
    private SpeechStateProxy mSpeechStateProxy;
    private SubscriberProxy mSubscriberProxy;
    private TTSEngineProxy mTTSEngineProxy;
    private VADEngineProxy mVADEngineProxy;
    private WakeupEngineProxy mWakeupEngineProxy;
    private WindowEngineProxy mWindowEngineProxy;
    private WorkerHandler mWorkerHandler;

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        private static final SpeechClient Instance = new SpeechClient();

        private Holder() {
        }
    }

    public static final SpeechClient instance() {
        return Holder.Instance;
    }

    public void init(Context context) {
        init(context, null);
    }

    public void init(Context context, ConnectManager.OnConnectCallback onConnectCallback) {
        LogUtils.i(this, "SpeechClient(%s) Start In %s, connectCallback: %s", "1.0", context.getPackageName(), onConnectCallback);
        HandlerThread handlerThread = new HandlerThread("NodeWorker");
        handlerThread.start();
        this.mWorkerHandler = new WorkerHandler(handlerThread.getLooper());
        this.mContext = context;
        ConnectManager connectManager = new ConnectManager(context);
        this.mConnectManager = connectManager;
        connectManager.init(this.mWorkerHandler);
        NodeManager nodeManager = new NodeManager();
        this.mNodeManager = nodeManager;
        nodeManager.init(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mNodeManager);
        QueryManager queryManager = new QueryManager();
        this.mQueryManager = queryManager;
        queryManager.init(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mQueryManager);
        ActorBridge actorBridge = new ActorBridge(context);
        this.mActorBridge = actorBridge;
        actorBridge.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mActorBridge);
        TTSEngineProxy tTSEngineProxy = new TTSEngineProxy();
        this.mTTSEngineProxy = tTSEngineProxy;
        tTSEngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mTTSEngineProxy);
        WakeupEngineProxy wakeupEngineProxy = new WakeupEngineProxy();
        this.mWakeupEngineProxy = wakeupEngineProxy;
        wakeupEngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mWakeupEngineProxy);
        HotwordEngineProxy hotwordEngineProxy = new HotwordEngineProxy();
        this.mHotwordEngineProxy = hotwordEngineProxy;
        hotwordEngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mHotwordEngineProxy);
        AgentProxy agentProxy = new AgentProxy();
        this.mAgentProxy = agentProxy;
        agentProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mAgentProxy);
        SubscriberProxy subscriberProxy = new SubscriberProxy();
        this.mSubscriberProxy = subscriberProxy;
        subscriberProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mSubscriberProxy);
        AppMgrProxy appMgrProxy = new AppMgrProxy(context);
        this.mAppMgrProxy = appMgrProxy;
        appMgrProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mAppMgrProxy);
        SpeechStateProxy speechStateProxy = new SpeechStateProxy();
        this.mSpeechStateProxy = speechStateProxy;
        this.mConnectManager.addCallback(speechStateProxy);
        SoundLockStateProxy soundLockStateProxy = new SoundLockStateProxy();
        this.mSoundLockStateProxy = soundLockStateProxy;
        this.mConnectManager.addCallback(soundLockStateProxy);
        ASREngineProxy aSREngineProxy = new ASREngineProxy();
        this.mASREngineProxy = aSREngineProxy;
        aSREngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mASREngineProxy);
        RecordEngineProxy recordEngineProxy = new RecordEngineProxy();
        this.mRecordEngineProxy = recordEngineProxy;
        recordEngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mRecordEngineProxy);
        QueryInjectorProxy queryInjectorProxy = new QueryInjectorProxy();
        this.mQueryInjectorProxy = queryInjectorProxy;
        queryInjectorProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mQueryInjectorProxy);
        WindowEngineProxy windowEngineProxy = new WindowEngineProxy();
        this.mWindowEngineProxy = windowEngineProxy;
        this.mConnectManager.addCallback(windowEngineProxy);
        Recognizer recognizer = new Recognizer(this.mWorkerHandler);
        this.mRecognizer = recognizer;
        this.mConnectManager.addCallback(recognizer.getConnectCallback());
        CarSystemPropertyProxy carSystemPropertyProxy = new CarSystemPropertyProxy();
        this.mCarSystemPropertyProxy = carSystemPropertyProxy;
        this.mConnectManager.addCallback(carSystemPropertyProxy);
        VADEngineProxy vADEngineProxy = new VADEngineProxy();
        this.mVADEngineProxy = vADEngineProxy;
        vADEngineProxy.setHandler(this.mWorkerHandler);
        this.mConnectManager.addCallback(this.mVADEngineProxy);
        if (onConnectCallback == null) {
            this.mConnectManager.addCallback(this);
        } else {
            this.mConnectManager.addCallback(onConnectCallback);
        }
        this.mConnectManager.connect();
        this.mConnectManager.registerReceiver();
    }

    public void setAppName(String... strArr) {
        for (String str : strArr) {
            getAppMgr().registerApp(this.mContext.getPackageName(), str);
        }
    }

    public ActorBridge getActorBridge() {
        return this.mActorBridge;
    }

    public SubscriberProxy getSubscriber() {
        return this.mSubscriberProxy;
    }

    public TTSEngineProxy getTTSEngine() {
        return this.mTTSEngineProxy;
    }

    public WakeupEngineProxy getWakeupEngine() {
        return this.mWakeupEngineProxy;
    }

    public HotwordEngineProxy getHotwordEngine() {
        return this.mHotwordEngineProxy;
    }

    public AgentProxy getAgent() {
        return this.mAgentProxy;
    }

    public AppMgrProxy getAppMgr() {
        return this.mAppMgrProxy;
    }

    public SpeechStateProxy getSpeechState() {
        return this.mSpeechStateProxy;
    }

    public SoundLockStateProxy getSoundLockState() {
        return this.mSoundLockStateProxy;
    }

    public ASREngineProxy getASREngine() {
        return this.mASREngineProxy;
    }

    public QueryInjectorProxy getQueryInjector() {
        return this.mQueryInjectorProxy;
    }

    public RecordEngineProxy getRecordEngine() {
        return this.mRecordEngineProxy;
    }

    public WindowEngineProxy getWindowEngine() {
        return this.mWindowEngineProxy;
    }

    public Recognizer getRecognizer() {
        return this.mRecognizer;
    }

    public VADEngineProxy getVadEngine() {
        return this.mVADEngineProxy;
    }

    public NodeManager getNodeManager() {
        return this.mNodeManager;
    }

    public QueryManager getQueryManager() {
        return this.mQueryManager;
    }

    public CarSystemPropertyProxy getCarSystemProperty() {
        return this.mCarSystemPropertyProxy;
    }

    public void sendActor(Actor actor) {
        this.mActorBridge.send(actor);
    }
}
