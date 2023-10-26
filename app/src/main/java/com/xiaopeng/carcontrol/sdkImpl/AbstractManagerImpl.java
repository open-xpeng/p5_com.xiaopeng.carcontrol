package com.xiaopeng.carcontrol.sdkImpl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;

/* loaded from: classes2.dex */
public abstract class AbstractManagerImpl {
    protected final String TAG = getClass().getSimpleName();
    private volatile boolean isInit = false;

    protected abstract void initInternal();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void observeData();

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractManagerImpl() {
        init();
    }

    private final void init() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$AbstractManagerImpl$kHDb5MHZ8sWZQiM2J-ftk0WaQiU
            @Override // java.lang.Runnable
            public final void run() {
                AbstractManagerImpl.this.lambda$init$0$AbstractManagerImpl();
            }
        });
    }

    public /* synthetic */ void lambda$init$0$AbstractManagerImpl() {
        LogUtils.d(this.TAG, "init start");
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            initInternal();
            this.isInit = true;
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$dtAy4OKw2Phkf4MHUagbAZiWb9M
                @Override // java.lang.Runnable
                public final void run() {
                    AbstractManagerImpl.this.observeData();
                }
            });
        }
        LogUtils.d(this.TAG, "init end");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final <T> void setLiveDataObserver(final LiveData<T> liveData, final Observer<T> observer) {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$AbstractManagerImpl$LR3IA0j_JZ0RNp4lZN6o7Xx24gM
            @Override // java.lang.Runnable
            public final void run() {
                LiveData.this.observeForever(observer);
            }
        });
    }
}
