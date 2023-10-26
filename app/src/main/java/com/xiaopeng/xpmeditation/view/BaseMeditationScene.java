package com.xiaopeng.xpmeditation.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public abstract class BaseMeditationScene {
    protected final String TAG = getClass().getSimpleName();
    private LifecycleOwner mOwner;
    private View mParent;

    public abstract void onClose();

    public abstract boolean onTouch(MotionEvent event);

    public abstract void playNext();

    public abstract void playPre();

    public abstract void preEnterMeditation();

    public abstract void refreshTime(long time);

    public BaseMeditationScene(View parent, LifecycleOwner owner) {
        this.mParent = parent;
        this.mOwner = owner;
    }

    public final View getContent() {
        return this.mParent;
    }

    public final Context getContext() {
        return this.mParent.getContext();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void setLdo(LiveData<T> liveData, Observer<T> observer) {
        LifecycleOwner lifecycleOwner = this.mOwner;
        if (lifecycleOwner != null) {
            liveData.observe(lifecycleOwner, observer);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCreate() {
        LogUtils.d(this.TAG, "onCreate");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStart() {
        LogUtils.d(this.TAG, "onStart");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onResume() {
        LogUtils.d(this.TAG, "onResume");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPause() {
        LogUtils.d(this.TAG, "onPause");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStop() {
        LogUtils.d(this.TAG, "onStop");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDestroy() {
        LogUtils.d(this.TAG, "onDestroy");
        this.mParent = null;
        this.mOwner = null;
    }
}
