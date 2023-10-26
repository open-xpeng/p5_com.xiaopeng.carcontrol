package com.xiaopeng.speech;

import android.os.Handler;
import com.xiaopeng.speech.IRemoteDataSensor;
import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.common.bean.Value;
import com.xiaopeng.speech.common.util.LogUtils;

/* loaded from: classes2.dex */
public abstract class SpeechQuery<T> extends IRemoteDataSensor.Stub {
    protected T mQueryCaller;
    protected Handler mWorkerHandler;
    private boolean mSubscribed = false;
    protected IQueryProcessor mQueryProcessor = bind(this);

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

    public void setQueryCaller(T t) {
        this.mQueryCaller = t;
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

    @Override // com.xiaopeng.speech.IRemoteDataSensor
    public Value onQuery(String str, String str2) {
        Value value = Value.VOID;
        if (this.mQueryProcessor != null) {
            Value performQuery = performQuery(str, str2);
            LogUtils.i("SpeechQuery", "dataApi = " + str + ",param data =" + str2 + ", query result value = " + performQuery.toString());
            return performQuery;
        }
        return value;
    }

    private Value performQuery(String str, String str2) {
        try {
            return new Value(this.mQueryProcessor.querySensor(str, str2));
        } catch (Exception e) {
            LogUtils.e(this, "performCommand error ", e);
            return Value.VOID;
        }
    }

    public String[] getQueryEvents() {
        IQueryProcessor iQueryProcessor = this.mQueryProcessor;
        if (iQueryProcessor != null) {
            return iQueryProcessor.getQueryEvents();
        }
        return null;
    }

    private IQueryProcessor bind(IRemoteDataSensor iRemoteDataSensor) {
        String str = iRemoteDataSensor.getClass().getName() + "_Processor";
        try {
            return (IQueryProcessor) Class.forName(str).getConstructor(iRemoteDataSensor.getClass()).newInstance(iRemoteDataSensor);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(this, String.format("bind %s error", str), e);
            return null;
        }
    }
}
