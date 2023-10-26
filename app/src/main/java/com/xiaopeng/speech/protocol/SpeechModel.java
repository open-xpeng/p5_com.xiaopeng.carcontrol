package com.xiaopeng.speech.protocol;

import android.os.Handler;
import android.os.Looper;
import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.IQueryCaller;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.actor.Actor;

/* loaded from: classes2.dex */
public class SpeechModel {
    protected Handler mMainHandler = new Handler(Looper.getMainLooper());

    public void subscribe(Class<? extends SpeechNode> cls, INodeListener iNodeListener) {
        SpeechUtils.subscribe(cls, iNodeListener);
    }

    public void unsubscribe(Class<? extends SpeechNode> cls, INodeListener iNodeListener) {
        SpeechUtils.unsubscribe(cls, iNodeListener);
    }

    public void subscribe(Class<? extends SpeechQuery> cls, IQueryCaller iQueryCaller) {
        SpeechUtils.subscribe(cls, iQueryCaller);
    }

    public void unsubscribe(Class<? extends SpeechQuery> cls) {
        SpeechUtils.unsubscribe(cls);
    }

    public void sendActor(Actor actor) {
        SpeechUtils.sendActor(actor);
    }

    public String speak(String str) {
        return SpeechUtils.speak(str);
    }

    public void triggerIntent(String str, String str2, String str3, String str4) {
        SpeechUtils.triggerIntent(str, str2, str3, str4);
    }

    public <T extends SpeechNode> T getNode(Class<T> cls) {
        return (T) SpeechUtils.getNode(cls);
    }

    public <T extends SpeechQuery> T getQuery(Class<T> cls) {
        return (T) SpeechUtils.getQuery(cls);
    }
}
