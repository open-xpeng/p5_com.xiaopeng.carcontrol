package com.xiaopeng.lib.framework.netchannelmodule.http.tracing;

import android.os.Process;
import android.text.TextUtils;
import brave.ErrorParser;
import brave.Tracer;
import brave.Tracing;
import brave.internal.handler.ZipkinFinishedSpanHandler;
import brave.sampler.Sampler;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.SharedPreferencesUtils;
import com.xiaopeng.lib.utils.SystemPropertyUtil;

/* loaded from: classes2.dex */
class TracingHolder {
    private static final float DEFAULT_SAMPLE_RATE = 0.1f;
    private static final String KEY_SAMPLE_RATE = "logan_sample_rate";
    private static final String TAG = "Tracer";
    private ErrorParser mErrorParser;
    private ZipkinFinishedSpanHandler mHandler;
    private float mSampleRate;
    private Sampler mSampler;
    private Tracer mTracer;
    private Tracing mTracing;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class HOLDER {
        private static TracingHolder INSTANCE = new TracingHolder();

        private HOLDER() {
        }
    }

    private TracingHolder() {
        this.mSampleRate = SharedPreferencesUtils.getInstance(GlobalConfig.getApplicationContext()).getFloat(KEY_SAMPLE_RATE, 0.1f);
        LogUtils.d(TAG, "init TracingHolder, mSampleRate:" + this.mSampleRate);
        this.mSampler = Sampler.create(this.mSampleRate);
        this.mErrorParser = new ErrorParser();
        this.mHandler = new ZipkinFinishedSpanHandler(new TracingReporter(), this.mErrorParser, GlobalConfig.getApplicationContext().getPackageName(), "Client-" + SystemPropertyUtil.getHardwareId(), Process.myPid());
        buildTracer();
    }

    private void buildTracer() {
        this.mTracing = Tracing.newBuilder().errorParser(this.mErrorParser).sampler(this.mSampler).addFinishedSpanHandler(this.mHandler).build();
        this.mTracer = Tracing.currentTracer();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TracingHolder getInstance() {
        return HOLDER.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Tracer getTracer() {
        return this.mTracer;
    }

    Sampler getSampler() {
        return this.mSampler;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateSampleRate(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            Float valueOf = Float.valueOf(str);
            if (this.mSampleRate != valueOf.floatValue()) {
                LogUtils.d(TAG, "update rate from:" + this.mSampleRate + " to:" + valueOf);
                this.mSampleRate = valueOf.floatValue();
                this.mSampler = Sampler.create(valueOf.floatValue());
                SharedPreferencesUtils.getInstance(GlobalConfig.getApplicationContext()).putFloat(KEY_SAMPLE_RATE, valueOf.floatValue());
                Tracing tracing = this.mTracing;
                if (tracing != null) {
                    tracing.close();
                }
                buildTracer();
            }
        } catch (Exception unused) {
            LogUtils.w(TAG, "invalid header rate:" + str);
        }
    }
}
