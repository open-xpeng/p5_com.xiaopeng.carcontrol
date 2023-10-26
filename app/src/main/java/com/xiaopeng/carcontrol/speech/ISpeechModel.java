package com.xiaopeng.carcontrol.speech;

import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.speech.overall.SpeechResult;
import com.xiaopeng.speech.protocol.bean.recommend.RecommendBean;

/* loaded from: classes2.dex */
public interface ISpeechModel {
    public static final int CUSTOMIZED = 1;
    public static final String TAG = "SpeechModel";

    void onEvent(String event, String data, int source);

    void onQuery(String event, String data, String callback);

    default void reply(String event, String callback, Object result) {
        if (TextUtils.isEmpty(event) || TextUtils.isEmpty(callback) || result == null) {
            return;
        }
        try {
            LogUtils.d(TAG, "reply=====event: " + event + ", result: " + result, false);
            ApiRouter.route(Uri.parse(callback).buildUpon().appendQueryParameter(RecommendBean.SHOW_TIME_RESULT, new SpeechResult(event, result).toString()).build());
        } catch (RemoteException e) {
            LogUtils.e(TAG, "reply: " + e.getMessage());
        }
    }
}
