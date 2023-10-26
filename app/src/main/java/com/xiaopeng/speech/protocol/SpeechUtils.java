package com.xiaopeng.speech.protocol;

import android.text.TextUtils;
import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.IQueryCaller;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.actor.Actor;
import com.xiaopeng.speech.actorapi.DataActor;
import com.xiaopeng.speech.actorapi.SupportActor;
import com.xiaopeng.speech.actorapi.ValueActor;
import com.xiaopeng.speech.speechwidget.SupportWidget;

/* loaded from: classes2.dex */
public class SpeechUtils {
    public static void replySupport(String str, boolean z) {
        replySupport(str, z, "");
    }

    public static void replySupport(String str, boolean z, String str2) {
        SupportWidget supportWidget = new SupportWidget();
        supportWidget.setSupport(z);
        supportWidget.setTTS(str2);
        SpeechClient.instance().getActorBridge().send(new SupportActor(str).setResult(supportWidget));
    }

    public static void replyData(String str, String str2) {
        SpeechClient.instance().getActorBridge().send(new DataActor(str).setResult(str2).setSupport(false));
    }

    public static void replyData(String str, String str2, boolean z) {
        SpeechClient.instance().getActorBridge().send(new DataActor(str).setResult(str2).setSupport(z));
    }

    public static void replyValue(String str, Object obj) {
        SpeechClient.instance().getActorBridge().send(new ValueActor(str).setValue(obj));
    }

    public static String speak(String str) {
        return SpeechClient.instance().getTTSEngine().speak(str);
    }

    public static void subscribe(Class<? extends SpeechNode> cls, INodeListener iNodeListener) {
        SpeechClient.instance().getNodeManager().subscribe(cls, iNodeListener);
    }

    public static void subscribe(Class<? extends SpeechQuery> cls, IQueryCaller iQueryCaller) {
        SpeechClient.instance().getQueryManager().inject(cls, iQueryCaller);
    }

    public static void unsubscribe(Class<? extends SpeechNode> cls, INodeListener iNodeListener) {
        SpeechClient.instance().getNodeManager().unSubscribe(cls, iNodeListener);
    }

    public static void unsubscribe(Class<? extends SpeechQuery> cls) {
        SpeechClient.instance().getQueryManager().unInject(cls);
    }

    public static void sendActor(Actor actor) {
        SpeechClient.instance().sendActor(actor);
    }

    public static void triggerIntent(String str, String str2, String str3, String str4) {
        SpeechClient.instance().getAgent().triggerIntent(str, str2, str3, str4);
    }

    public static <T extends SpeechNode> T getNode(Class<T> cls) {
        return (T) SpeechClient.instance().getNodeManager().getNode(cls);
    }

    public static <T extends SpeechQuery> T getQuery(Class<T> cls) {
        return (T) SpeechClient.instance().getQueryManager().getQuery(cls);
    }

    public static boolean isJson(String str) {
        if (!TextUtils.isEmpty(str)) {
            String trim = str.trim();
            if (trim.startsWith("{") && trim.endsWith("}")) {
                return true;
            }
            if (trim.startsWith("[") && trim.endsWith("]")) {
                return true;
            }
        }
        return false;
    }
}
