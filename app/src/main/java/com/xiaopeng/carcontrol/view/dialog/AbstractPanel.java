package com.xiaopeng.carcontrol.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.direct.ElementDirectManager;
import com.xiaopeng.carcontrol.direct.IElementDirect;
import com.xiaopeng.carcontrol.direct.OnPageDirectShowListener;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.Helper.IVuiSceneHelper;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.VuiUpdateType;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class AbstractPanel implements LifecycleOwner, IVuiSceneHelper, IVuiElementListener, OnPageDirectShowListener {
    private static final int ELEMENT_DIRECT_EVENT = 5;
    private static final int INIT_PANEL_EVENT = 2;
    private static final int INIT_VIEW_EVENT = 3;
    protected static final String SCROLL_TO_TOP = "scroll_to_top";
    private static final int SHOW_EVENT = 4;
    protected Context mContext;
    protected final boolean mIsVuiEnabled;
    protected final Handler mMainHandler;
    private Dialog mPanel;
    private View mRootView;
    protected boolean mShouldVuiInitScene;
    protected final String TAG = getClass().getSimpleName();
    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    private final CountDownLatch mCountDownLatch = new CountDownLatch(1);
    protected boolean mNeedPlayTts = true;
    private final IVuiElementChangedListener mElementChangedListener = new IVuiElementChangedListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$AbstractPanel$JW-epoXnoMiPmmzAmD8zYGGOO0c
        @Override // com.xiaopeng.vui.commons.IVuiElementChangedListener
        public final void onVuiElementChaned(View view, VuiUpdateType vuiUpdateType) {
            AbstractPanel.this.lambda$new$0$AbstractPanel(view, vuiUpdateType);
        }
    };
    protected Intent mDirectIntent = null;
    private boolean isVmInit = false;
    private boolean isShow = false;

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public List<View> getBuildViews() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract int getLayoutId();

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return null;
    }

    protected boolean isPanelSupportScroll() {
        return false;
    }

    protected abstract boolean needInitVm();

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String s, IVuiElementBuilder iVuiElementBuilder) {
        return null;
    }

    protected abstract Dialog onCreatePanel();

    protected abstract View onCreateView();

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDismiss() {
    }

    protected abstract void onInitView();

    protected void onInitViewModel() {
    }

    protected boolean onKey(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.direct.OnPageDirectShowListener
    public void onPageDirectShow(String name) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRefresh() {
    }

    protected void onRegisterLiveData() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onShow() {
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent vuiEvent) {
        return false;
    }

    protected boolean shouldDisableVui() {
        return false;
    }

    protected String[] supportSecondPageForElementDirect() {
        return null;
    }

    public /* synthetic */ void lambda$new$0$AbstractPanel(View view, VuiUpdateType type) {
        VuiManager.instance().updateScene(this.mContext, getSceneId(), view, type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractPanel() {
        boolean z = false;
        this.mShouldVuiInitScene = false;
        Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.xiaopeng.carcontrol.view.dialog.AbstractPanel.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                int i = msg.what;
                if (i == 2) {
                    AbstractPanel.this.initPanel();
                } else if (i == 3) {
                    AbstractPanel.this.onInitView();
                    AbstractPanel.this.onViewAttach();
                } else if (i == 4) {
                    AbstractPanel.this.handleShowEvent(msg.arg1);
                } else if (i != 5) {
                } else {
                    AbstractPanel.this.handleElementDirectEvent();
                }
            }
        };
        this.mMainHandler = handler;
        this.mContext = App.getInstance().getApplicationContext();
        if (Xui.isVuiEnable() && !shouldDisableVui()) {
            z = true;
        }
        this.mIsVuiEnabled = z;
        this.mShouldVuiInitScene = z;
        handler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$AbstractPanel$1baoyWlsxRejtm52vYK7yQm5vq0
            @Override // java.lang.Runnable
            public final void run() {
                AbstractPanel.this.lambda$new$2$AbstractPanel();
            }
        });
        if (needInitVm()) {
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$AbstractPanel$37M5Y5JUxNs-qGKUR8t4a-ruW-E
                @Override // java.lang.Runnable
                public final void run() {
                    AbstractPanel.this.lambda$new$3$AbstractPanel();
                }
            }, 50L);
        } else {
            handler.sendEmptyMessage(2);
        }
    }

    public /* synthetic */ void lambda$new$2$AbstractPanel() {
        this.mLifecycleRegistry.addObserver(new LifecycleEventObserver() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$AbstractPanel$5tYbrTWflEx2YgvVms6camz8xcw
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                AbstractPanel.this.lambda$null$1$AbstractPanel(lifecycleOwner, event);
            }
        });
    }

    public /* synthetic */ void lambda$null$1$AbstractPanel(LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_STOP) {
            LogUtils.d(this.TAG, "cancelPendingInputEvents", false);
            View view = this.mRootView;
            if (view != null) {
                view.cancelPendingInputEvents();
            }
        }
    }

    public /* synthetic */ void lambda$new$3$AbstractPanel() {
        if (this.isVmInit || !CarClientWrapper.getInstance().isCarServiceConnected()) {
            return;
        }
        onInitViewModel();
        this.mMainHandler.sendEmptyMessage(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initPanel() {
        this.isVmInit = true;
        Dialog onCreatePanel = onCreatePanel();
        this.mPanel = onCreatePanel;
        if (onCreatePanel == null) {
            LogUtils.e(this.TAG, "create Panel failed, can not init view");
            return;
        }
        handleLifecycleChanged(Lifecycle.Event.ON_CREATE);
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$AbstractPanel$dgJLb6E7QGbie00D__niYqEFJ5U
            @Override // java.lang.Runnable
            public final void run() {
                AbstractPanel.this.lambda$initPanel$4$AbstractPanel();
            }
        }, 0L);
    }

    public /* synthetic */ void lambda$initPanel$4$AbstractPanel() {
        initPanelListener();
        LogUtils.d(this.TAG, "onCreateView", false);
        this.mRootView = onCreateView();
        this.mMainHandler.sendEmptyMessage(3);
    }

    private void initPanelListener() {
        this.mPanel.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$AbstractPanel$M0k5rvShRZ0DJcD6rTCBywf-Z5Y
            @Override // android.content.DialogInterface.OnKeyListener
            public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return AbstractPanel.this.lambda$initPanelListener$5$AbstractPanel(dialogInterface, i, keyEvent);
            }
        });
        this.mPanel.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$AbstractPanel$T3hNTnngL3LhIytasIGtBvE6w08
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                AbstractPanel.this.lambda$initPanelListener$6$AbstractPanel(dialogInterface);
            }
        });
        this.mPanel.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$AbstractPanel$wZmnQ6wsAjRfR0rPyeB4DLVJpZc
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AbstractPanel.this.lambda$initPanelListener$7$AbstractPanel(dialogInterface);
            }
        });
        if (this.mPanel.getWindow() != null) {
            LogUtils.d(this.TAG, "addOnAttachStateChangeListener " + this);
            this.mPanel.getWindow().getDecorView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.AbstractPanel.2
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View v) {
                    AbstractPanel.this.handleLifecycleChanged(Lifecycle.Event.ON_RESUME);
                    AbstractPanel.this.mPanel.getWindow().setLocalFocus(true, true);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View v) {
                    AbstractPanel.this.isShow = false;
                    AbstractPanel.this.handleLifecycleChanged(Lifecycle.Event.ON_PAUSE);
                }
            });
        }
    }

    public /* synthetic */ boolean lambda$initPanelListener$5$AbstractPanel(DialogInterface dialog, int keyCode, KeyEvent event) {
        return onKey(keyCode, event);
    }

    public /* synthetic */ void lambda$initPanelListener$6$AbstractPanel(DialogInterface dialog) {
        LogUtils.d(this.TAG, "onShow", false);
        this.isShow = true;
        onShow();
        this.mMainHandler.sendEmptyMessageDelayed(5, 50L);
    }

    public /* synthetic */ void lambda$initPanelListener$7$AbstractPanel(DialogInterface dialog) {
        LogUtils.d(this.TAG, "onDismiss", false);
        onDismiss();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onViewAttach() {
        this.mCountDownLatch.countDown();
    }

    public final void show(int windowType) {
        show(windowType, true);
    }

    public final void show(final int windowType, final boolean playTts) {
        if (this.mPanel == null) {
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$AbstractPanel$I1OggzGvl8eRyYPzTqFzpUmIOfE
            @Override // java.lang.Runnable
            public final void run() {
                AbstractPanel.this.lambda$show$8$AbstractPanel(playTts, windowType);
            }
        });
    }

    public /* synthetic */ void lambda$show$8$AbstractPanel(final boolean playTts, final int windowType) {
        try {
            this.mCountDownLatch.await();
            if (onInterceptShow()) {
                return;
            }
            this.mNeedPlayTts = playTts;
            Message obtain = Message.obtain();
            obtain.what = 4;
            obtain.arg1 = windowType;
            this.mMainHandler.sendMessage(obtain);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setWindowType(int windowType) {
        if (windowType == -1 || this.mPanel.getWindow() == null) {
            return;
        }
        this.mPanel.getWindow().setType(windowType);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean onInterceptShow() {
        if (needInitVm()) {
            return !this.isVmInit;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShowEvent(int windowType) {
        setWindowType(windowType);
        onRegisterLiveData();
        handleLifecycleChanged(Lifecycle.Event.ON_START);
        if (this.mPanel.isShowing()) {
            handleLifecycleChanged(Lifecycle.Event.ON_RESUME);
        }
        LogUtils.d(this.TAG, "onRefresh", false);
        onRefresh();
        if (this.mPanel.isShowing()) {
            this.mMainHandler.sendEmptyMessageDelayed(5, 50L);
        }
        this.mPanel.show();
        if (this.mIsVuiEnabled && this.mShouldVuiInitScene) {
            VuiManager.instance().initScene(this.mContext, getLifecycle(), getSceneId(), getRootView(), this, this.mElementChangedListener, null);
            this.mShouldVuiInitScene = false;
        }
    }

    public final void dismiss() {
        Dialog dialog = this.mPanel;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public View getRootView() {
        return this.mRootView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final <T extends View> T findViewById(int id) {
        return (T) this.mRootView.findViewById(id);
    }

    public boolean isShow() {
        return this.isShow;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.dialog.AbstractPanel$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$Event;

        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$Event = iArr;
            try {
                iArr[Lifecycle.Event.ON_CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLifecycleChanged(Lifecycle.Event event) {
        int i = AnonymousClass3.$SwitchMap$androidx$lifecycle$Lifecycle$Event[event.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            this.mLifecycleRegistry.handleLifecycleEvent(event);
        } else if (i != 4) {
        } else {
            this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        if (liveData == null || observer == 0) {
            return;
        }
        liveData.observe(this, observer);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        if (!this.mIsVuiEnabled || view == null) {
            return;
        }
        VuiFloatingLayerManager.show(view);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        if (!this.mIsVuiEnabled || view == null || view.getId() == R.id.close_btn) {
            return false;
        }
        VuiFloatingLayerManager.show(view);
        return false;
    }

    protected void addVuiProp(VuiView view, String prop, String value) {
        if (this.mIsVuiEnabled) {
            if (TextUtils.isEmpty(prop) || TextUtils.isEmpty(value)) {
                LogUtils.e(this.TAG, "addVuiProps failed with prop=" + prop + ", value=" + value, false);
                return;
            }
            LogUtils.i(this.TAG, "addVuiProps prop=" + prop + ", value=" + value, false);
            JSONObject vuiPropsJson = getVuiPropsJson(view);
            try {
                vuiPropsJson.put(prop, value);
            } catch (Exception e) {
                LogUtils.e(this.TAG, "addVuiProps " + prop + " failed :" + e.getMessage(), false);
            }
            view.setVuiProps(vuiPropsJson);
        }
    }

    private JSONObject getVuiPropsJson(VuiView view) {
        JSONObject vuiProps = view.getVuiProps();
        return vuiProps == null ? new JSONObject() : vuiProps;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void vuiFeedback(int content, View view) {
        if (!this.mIsVuiEnabled || view == null) {
            return;
        }
        VuiEngine.getInstance(this.mContext.getApplicationContext()).vuiFeedback(view, new VuiFeedback.Builder().state(1).content(this.mContext.getString(content)).build());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void vuiFeedbackClick(int content, View view) {
        if (this.mIsVuiEnabled && (view instanceof VuiView) && ((VuiView) view).isPerformVuiAction()) {
            vuiFeedback(content, view);
        }
    }

    public boolean isVuiAction(View view) {
        return isVuiAction(view, true);
    }

    public boolean isVuiAction(View view, boolean reset) {
        return this.mIsVuiEnabled && VuiManager.instance().isVuiAction(view, reset);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleElementDirectEvent() {
        Dialog dialog = this.mPanel;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        if (this.mDirectIntent != null) {
            IElementDirect elementDirect = ElementDirectManager.getElementDirect();
            if (elementDirect != null) {
                if (elementDirect.showElementItemView(this.mDirectIntent, getClass().getName(), this.mRootView, this)) {
                    this.mDirectIntent = null;
                    LogUtils.i(this.TAG, "handleElementDirect isElement");
                    return;
                }
                if (elementDirect.showSecondPageDirect(this.mDirectIntent, supportSecondPageForElementDirect(), this)) {
                    this.mDirectIntent = null;
                    LogUtils.i(this.TAG, "handleElementDirect isSecondPage");
                }
                if (isPanelSupportScroll()) {
                    onPageDirectShow(SCROLL_TO_TOP);
                    LogUtils.i(this.TAG, "handleElementDirect not Element and SecondPage, just scroll the content to top");
                    return;
                }
                return;
            }
            return;
        }
        LogUtils.w(this.TAG, "handleElementDirect mDirectData is null");
    }
}
