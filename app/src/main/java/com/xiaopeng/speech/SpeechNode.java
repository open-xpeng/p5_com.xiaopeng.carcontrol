package com.xiaopeng.speech;

import android.os.Handler;
import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.SimpleCallbackList;
import com.xiaopeng.speech.coreapi.IEventObserver;
import com.xiaopeng.speech.jarvisproto.DMListening;
import com.xiaopeng.speech.jarvisproto.DMWait;

/* loaded from: classes2.dex */
public abstract class SpeechNode<T> extends IEventObserver.Stub {
    protected Handler mWorkerHandler;
    private boolean mSubscribed = false;
    protected SimpleCallbackList<T> mListenerList = new SimpleCallbackList<>();
    protected ICommandProcessor mCommandProcessor = bind(this);

    protected void onSubscribe() {
    }

    protected void onUnsubscribe() {
    }

    public void setSubscribed(boolean z) {
        this.mSubscribed = z;
        if (z) {
            onSubscribe();
        } else {
            onUnsubscribe();
        }
    }

    public boolean isSubscribed() {
        return this.mSubscribed;
    }

    public Handler getWorkerHandler() {
        return this.mWorkerHandler;
    }

    public void setWorkerHandler(Handler handler) {
        this.mWorkerHandler = handler;
    }

    public void addListener(T t) {
        this.mListenerList.addCallback(t);
    }

    public void removeListener(T t) {
        this.mListenerList.removeCallback(t);
    }

    @Override // com.xiaopeng.speech.coreapi.IEventObserver
    public void onMessage(final String str, final String str2) {
        if (this.mCommandProcessor != null) {
            Handler handler = this.mWorkerHandler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.xiaopeng.speech.SpeechNode.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SpeechNode.this.performCommand(str, str2);
                    }
                });
            } else {
                performCommand(str, str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performCommand(String str, String str2) {
        if (!DMListening.EVENT.equals(str) && !DMWait.EVENT.equals(str)) {
            LogUtils.i(this, "performCommand, command = " + str + ", data = " + str2);
        }
        try {
            this.mCommandProcessor.performCommand(str, str2);
        } catch (Exception e) {
            LogUtils.e(this, "performCommand error ", e);
        }
    }

    public String[] getSubscribeEvents() {
        ICommandProcessor iCommandProcessor = this.mCommandProcessor;
        if (iCommandProcessor != null) {
            return iCommandProcessor.getSubscribeEvents();
        }
        return null;
    }

    private ICommandProcessor bind(IEventObserver iEventObserver) {
        String str = iEventObserver.getClass().getName() + "_Processor";
        try {
            return (ICommandProcessor) Class.forName(str).getConstructor(iEventObserver.getClass()).newInstance(iEventObserver);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(this, String.format("bind %s error", str), e);
            return null;
        }
    }
}
