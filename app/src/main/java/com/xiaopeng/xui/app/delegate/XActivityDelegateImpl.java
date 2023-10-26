package com.xiaopeng.xui.app.delegate;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaopeng.xui.app.delegate.XActivityDismissCause;
import com.xiaopeng.xui.app.delegate.XActivityDismissExtend;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.utils.XLogUtils;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class XActivityDelegateImpl extends XActivityDelegate implements XActivityDismissCause.CallBack, XActivityDismissExtend.OnDismissListenerEx {
    private static final String TAG = "XActivityDelegate";
    private AppCompatActivity mActivity;
    private XActivityDismissExtend mActivityDismiss;
    private XActivityDismissCauseGroup mActivityDismissCauseGroup;
    private XActivityTemplateExtend mActivityTemplate;
    private XActivityWindowAttributes mWindowAttributes;
    private int mWindowBackgroundId;
    private XActivityWindowVisible mWindowVisible;
    private ArraySet<XActivityLifecycle> mActivityLifeCycles = new ArraySet<>();
    private Handler mHandler = new Handler();

    @Override // com.xiaopeng.xui.app.delegate.XActivityDismissExtend.OnDismissListenerEx
    public void afterDismiss(int i) {
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onSaveInstanceState(Bundle bundle) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public XActivityDelegateImpl(AppCompatActivity appCompatActivity) {
        this.mActivity = appCompatActivity;
        init();
    }

    private void init() {
        XTimeLogs create = XTimeLogs.create();
        create.start("XActivityDelegate-init");
        int analysisAnnotation = analysisAnnotation(this.mActivity.getClass());
        create.record("analysisAnnotation " + analysisAnnotation);
        this.mWindowAttributes = new XActivityWindowAttributes(this.mActivity);
        create.record("attributes");
        this.mWindowVisible = new XActivityWindowVisible(this.mActivity);
        create.record("visible");
        this.mActivityDismissCauseGroup = XActivityDismissCauseGroup.create(this.mActivity, this);
        create.record("cause");
        XActivityDismissExtend create2 = XActivityDismissExtend.create(this.mActivity);
        this.mActivityDismiss = create2;
        create2.setOnDismissListenerEx(this);
        create.record("dismiss");
        this.mActivityTemplate = XActivityTemplateExtend.create(this.mActivity, analysisAnnotation);
        create.record("template");
        this.mActivityTemplate.initDismiss(this.mActivityDismiss);
        this.mActivityTemplate.initDismissCauseGroup(this.mActivityDismissCauseGroup);
        this.mActivityTemplate.initWindowAttributes(this.mWindowAttributes);
        this.mActivityTemplate.initWindowVisible(this.mWindowVisible);
        create.record("template-init");
        this.mActivityLifeCycles.add(this.mWindowVisible);
        this.mActivityLifeCycles.add(this.mActivityDismiss);
        this.mActivityLifeCycles.add(this.mActivityTemplate);
        this.mActivityLifeCycles.add(this.mActivityDismissCauseGroup);
        create.end();
    }

    private int analysisAnnotation(Class<?> cls) {
        XActivityBind xActivityBind;
        if (cls.isAnnotationPresent(XActivityBind.class) && (xActivityBind = (XActivityBind) cls.getAnnotation(XActivityBind.class)) != null) {
            return xActivityBind.value();
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass == null || superclass.getName().equals(AppCompatActivity.class.getName())) {
            return 0;
        }
        return analysisAnnotation(superclass);
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public <T extends XActivityTemplate> T getActivityTemplate() {
        return this.mActivityTemplate;
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public XActivityDismiss getActivityDismiss() {
        return this.mActivityDismiss;
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public XActivityWindowVisible getWindowVisible() {
        return this.mWindowVisible;
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public XActivityWindowAttributes getWindowAttributes() {
        return this.mWindowAttributes;
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onPostCreate(Bundle bundle) {
        this.mWindowAttributes.apply();
        TypedArray obtainStyledAttributes = this.mActivity.getTheme().obtainStyledAttributes(new int[]{16842836});
        this.mWindowBackgroundId = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        this.mHandler.post(new Runnable() { // from class: com.xiaopeng.xui.app.delegate.-$$Lambda$XActivityDelegateImpl$JVbavtqGsDiZvTX3AahHZA1A3RM
            @Override // java.lang.Runnable
            public final void run() {
                XActivityDelegateImpl.this.lambda$onPostCreate$0$XActivityDelegateImpl();
            }
        });
    }

    public /* synthetic */ void lambda$onPostCreate$0$XActivityDelegateImpl() {
        XLogUtils.d(TAG, "activityTemplate: " + this.mActivityTemplate.toString());
        XLogUtils.d(TAG, "dismiss: " + this.mActivityDismiss.toString());
        XLogUtils.d(TAG, "windowVisible: " + this.mWindowVisible.toString());
        XLogUtils.d(TAG, "dismissCause: " + this.mActivityDismissCauseGroup.toString());
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onCreate(Bundle bundle) {
        Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
        while (it.hasNext()) {
            it.next().onCreate(bundle);
        }
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onStart() {
        Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
        while (it.hasNext()) {
            it.next().onStart();
        }
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onResume() {
        Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
        while (it.hasNext()) {
            it.next().onResume();
        }
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onRecreate() {
        Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
        while (it.hasNext()) {
            it.next().onRecreate();
        }
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onPause() {
        Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
        while (it.hasNext()) {
            it.next().onPause();
        }
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onStop() {
        Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
        while (it.hasNext()) {
            it.next().onStop();
        }
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onDestroy() {
        Iterator<XActivityLifecycle> it = this.mActivityLifeCycles.iterator();
        while (it.hasNext()) {
            it.next().onDestroy();
        }
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onConfigurationChanged(Configuration configuration) {
        if (this.mWindowBackgroundId > 0) {
            XThemeManager.setWindowBackgroundResource(configuration, this.mActivity.getWindow(), this.mWindowBackgroundId);
        }
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void installViewFactory() {
        this.mActivity.getLayoutInflater().setFactory2(new LayoutInflater.Factory2() { // from class: com.xiaopeng.xui.app.delegate.XActivityDelegateImpl.1
            @Override // android.view.LayoutInflater.Factory2
            public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
                return XActivityDelegateImpl.this.createView(view, str, context, attributeSet);
            }

            @Override // android.view.LayoutInflater.Factory
            public View onCreateView(String str, Context context, AttributeSet attributeSet) {
                return onCreateView(null, str, context, attributeSet);
            }
        });
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public View createView(View view, String str, Context context, AttributeSet attributeSet) {
        return this.mActivity.getDelegate().createView(view, str, context, attributeSet);
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mActivityDismissCauseGroup.onTouchEvent(motionEvent);
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.mActivityDismissCauseGroup.dispatchTouchEvent();
        return false;
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void dispatchUserEvent() {
        this.mActivityDismissCauseGroup.dispatchUserEvent();
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDelegate
    public void onBackPressed() {
        this.mActivityDismissCauseGroup.onBackPressed();
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDismissCause.CallBack
    public void onTriggerDismiss(int i) {
        this.mActivityDismiss.dismiss(i);
    }

    @Override // com.xiaopeng.xui.app.delegate.XActivityDismissExtend.OnDismissListenerEx
    public void beforeDismiss(int i) {
        this.mWindowVisible.changeWindowVisible(false);
        if (i == 2 || this.mActivityDismissCauseGroup.getOnPauseSceneInspect() == null) {
            return;
        }
        this.mActivityDismissCauseGroup.getOnPauseSceneInspect().ignoreDismissOneshot();
    }
}
