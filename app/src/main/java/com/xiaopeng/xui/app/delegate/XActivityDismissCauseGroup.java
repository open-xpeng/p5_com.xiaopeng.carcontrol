package com.xiaopeng.xui.app.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.MotionEvent;
import com.xiaopeng.xui.app.delegate.XActivityDismissCause;
import java.util.Iterator;

/* loaded from: classes2.dex */
abstract class XActivityDismissCauseGroup implements XActivityLifecycle, XActivityDismissCause.OnPauseCause, XActivityDismissCause.SpeedTimeCause, XActivityDismissCause.OutSideCause, XActivityDismissCause.BackCause {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void enableBackScene();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void enableOnPauseScene();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void enableOutSideScene();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void enableSpeedTimeOutScene();

    abstract XActivityDismissCause.BackCause getBackSceneInspect();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract XActivityDismissCause.OnPauseCause getOnPauseSceneInspect();

    abstract XActivityDismissCause.OutSideCause getOutSideSceneInspect();

    abstract XActivityDismissCause.SpeedTimeCause getSpeedTimeOutSceneInspect();

    XActivityDismissCauseGroup() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static XActivityDismissCauseGroup create(Activity activity, XActivityDismissCause.CallBack callBack) {
        return new XActivityDismissCauseGroupImpl(activity, callBack);
    }

    /* loaded from: classes2.dex */
    private static class XActivityDismissCauseGroupImpl extends XActivityDismissCauseGroup {
        private Activity mActivity;
        private ArraySet<XActivityLifecycle> mActivityLifeCycles = new ArraySet<>();
        private XActivityDismissCause.BackCause mBackSceneInspect;
        private XActivityDismissCause.CallBack mCallBack;
        private XActivityDismissCause.OnPauseCause mOnPauseSceneInspect;
        private XActivityDismissCause.OutSideCause mOutSideSceneInspect;
        private XActivityDismissCause.SpeedTimeCause mSpeedTimeOutSceneInspect;

        XActivityDismissCauseGroupImpl(Activity activity, XActivityDismissCause.CallBack callBack) {
            this.mActivity = activity;
            this.mCallBack = callBack;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCauseGroup
        public void enableOnPauseScene() {
            XActivityDismissCause.OnPauseCause createOnPause = XActivityDismissCause.createOnPause(this.mActivity, this.mCallBack);
            this.mOnPauseSceneInspect = createOnPause;
            this.mActivityLifeCycles.add(createOnPause);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCauseGroup
        public void enableSpeedTimeOutScene() {
            XActivityDismissCause.SpeedTimeCause createSpeedTimeOut = XActivityDismissCause.createSpeedTimeOut(this.mActivity, this.mCallBack);
            this.mSpeedTimeOutSceneInspect = createSpeedTimeOut;
            this.mActivityLifeCycles.add(createSpeedTimeOut);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCauseGroup
        public void enableOutSideScene() {
            XActivityDismissCause.OutSideCause createOutSide = XActivityDismissCause.createOutSide(this.mActivity, this.mCallBack);
            this.mOutSideSceneInspect = createOutSide;
            this.mActivityLifeCycles.add(createOutSide);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCauseGroup
        public void enableBackScene() {
            XActivityDismissCause.BackCause createBack = XActivityDismissCause.createBack(this.mActivity, this.mCallBack);
            this.mBackSceneInspect = createBack;
            this.mActivityLifeCycles.add(createBack);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCauseGroup
        public XActivityDismissCause.OnPauseCause getOnPauseSceneInspect() {
            return this.mOnPauseSceneInspect;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCauseGroup
        public XActivityDismissCause.SpeedTimeCause getSpeedTimeOutSceneInspect() {
            return this.mSpeedTimeOutSceneInspect;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCauseGroup
        public XActivityDismissCause.OutSideCause getOutSideSceneInspect() {
            return this.mOutSideSceneInspect;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCauseGroup
        public XActivityDismissCause.BackCause getBackSceneInspect() {
            return this.mBackSceneInspect;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle bundle) {
            Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
            while (it.hasNext()) {
                it.next().onCreate(bundle);
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onStart() {
            Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
            while (it.hasNext()) {
                it.next().onStart();
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onResume() {
            Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
            while (it.hasNext()) {
                it.next().onResume();
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onRecreate() {
            Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
            while (it.hasNext()) {
                it.next().onRecreate();
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onPause() {
            Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
            while (it.hasNext()) {
                it.next().onPause();
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onStop() {
            Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
            while (it.hasNext()) {
                it.next().onStop();
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onDestroy() {
            Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
            while (it.hasNext()) {
                it.next().onDestroy();
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCause.BackCause
        public void onBackPressed() {
            XActivityDismissCause.BackCause backCause = this.mBackSceneInspect;
            if (backCause != null) {
                backCause.onBackPressed();
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCause.OnPauseCause
        public void ignoreDismissOneshot() {
            XActivityDismissCause.OnPauseCause onPauseCause = this.mOnPauseSceneInspect;
            if (onPauseCause != null) {
                onPauseCause.ignoreDismissOneshot();
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCause.OutSideCause
        public boolean onTouchEvent(MotionEvent motionEvent) {
            XActivityDismissCause.OutSideCause outSideCause = this.mOutSideSceneInspect;
            return outSideCause != null && outSideCause.onTouchEvent(motionEvent);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCause.SpeedTimeCause
        public void dispatchUserEvent() {
            XActivityDismissCause.SpeedTimeCause speedTimeCause = this.mSpeedTimeOutSceneInspect;
            if (speedTimeCause != null) {
                speedTimeCause.dispatchUserEvent();
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCause.SpeedTimeCause
        public void dispatchTouchEvent() {
            XActivityDismissCause.SpeedTimeCause speedTimeCause = this.mSpeedTimeOutSceneInspect;
            if (speedTimeCause != null) {
                speedTimeCause.dispatchTouchEvent();
            }
        }

        public String toString() {
            return "{ mBackSceneInspect=" + (this.mBackSceneInspect != null ? "has" : "no") + ", mOnPauseSceneInspect=" + (this.mOnPauseSceneInspect != null ? "has" : "no") + ", mOutSideSceneInspect=" + (this.mOutSideSceneInspect != null ? "has" : "no") + ", mSpeedTimeOutSceneInspect=" + (this.mSpeedTimeOutSceneInspect == null ? "no" : "has") + '}';
        }
    }
}
