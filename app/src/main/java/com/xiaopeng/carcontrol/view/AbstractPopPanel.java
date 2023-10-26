package com.xiaopeng.carcontrol.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
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
import com.xiaopeng.carcontrol.view.AbstractPopPanel;
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
import com.xiaopeng.xui.app.XPopPanel;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import com.xiaopeng.xvs.xid.utils.ResourceUtils;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class AbstractPopPanel implements LifecycleOwner, IVuiSceneHelper, IVuiElementListener, OnPageDirectShowListener {
    private static final int ELEMENT_DIRECT_EVENT = 5;
    private static final int INIT_PANEL_EVENT = 2;
    private static final int INIT_VIEW_EVENT = 3;
    protected static final String SCROLL_TO_TOP = "scroll_to_top";
    private static final int SHOW_EVENT = 4;
    protected Context mContext;
    protected final boolean mIsVuiEnabled;
    protected final Handler mMainHandler;
    protected XPopPanel mPanel;
    private XPopPanel.XPopPanelBuilder mPanelBuilder;
    private View mRootView;
    private int mRootViewHigh;
    private int mRootViewWidth;
    protected final String TAG = getClass().getSimpleName();
    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    private final CountDownLatch mCountDownLatch = new CountDownLatch(1);
    protected boolean mNeedPlayTts = true;
    private final IVuiElementChangedListener mElementChangedListener = new IVuiElementChangedListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$AbstractPopPanel$vsL3l1M8pL6WsLSXGudj4r_G0Mg
        @Override // com.xiaopeng.vui.commons.IVuiElementChangedListener
        public final void onVuiElementChaned(View view, VuiUpdateType vuiUpdateType) {
            AbstractPopPanel.this.lambda$new$0$AbstractPopPanel(view, vuiUpdateType);
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

    protected boolean isEqulPreStatus() {
        return false;
    }

    protected boolean isPanelSupportScroll() {
        return false;
    }

    protected abstract boolean needInitVm();

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String s, IVuiElementBuilder iVuiElementBuilder) {
        return null;
    }

    protected abstract XPopPanel.XPopPanelBuilder onCreatePanelBuilder();

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

    protected void onRefresh() {
    }

    protected void onRegisterLiveData() {
    }

    protected void onShow() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onShowTimeUpdate(long millisUntilFinished) {
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

    public /* synthetic */ void lambda$new$0$AbstractPopPanel(View view, VuiUpdateType type) {
        VuiManager.instance().updateScene(this.mContext, getSceneId(), view, type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractPopPanel() {
        boolean z = true;
        Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.xiaopeng.carcontrol.view.AbstractPopPanel.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                int i = msg.what;
                if (i == 2) {
                    AbstractPopPanel.this.initPanel();
                } else if (i == 3) {
                    AbstractPopPanel.this.onInitView();
                    AbstractPopPanel.this.onViewAttach();
                } else if (i != 4) {
                } else {
                    AbstractPopPanel.this.handleShowEvent(msg.arg1, msg.arg2);
                }
            }
        };
        this.mMainHandler = handler;
        this.mContext = App.getInstance().getApplicationContext();
        this.mIsVuiEnabled = (!Xui.isVuiEnable() || shouldDisableVui()) ? false : z;
        handler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$AbstractPopPanel$pYNw5Q6xVBuG-G2iL18hwHDqJsk
            @Override // java.lang.Runnable
            public final void run() {
                AbstractPopPanel.this.lambda$new$2$AbstractPopPanel();
            }
        });
        if (needInitVm()) {
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$AbstractPopPanel$PIbY1vcCK14d--CyG0cDguzYp7c
                @Override // java.lang.Runnable
                public final void run() {
                    AbstractPopPanel.this.lambda$new$3$AbstractPopPanel();
                }
            }, 50L);
        } else {
            handler.sendEmptyMessage(2);
        }
    }

    public /* synthetic */ void lambda$new$2$AbstractPopPanel() {
        this.mLifecycleRegistry.addObserver(new LifecycleEventObserver() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$AbstractPopPanel$AWjGqTmtGK2T2TfOY9NHzFOb0sY
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                AbstractPopPanel.this.lambda$null$1$AbstractPopPanel(lifecycleOwner, event);
            }
        });
    }

    public /* synthetic */ void lambda$null$1$AbstractPopPanel(LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_STOP) {
            LogUtils.d(this.TAG, "cancelPendingInputEvents", false);
            View view = this.mRootView;
            if (view != null) {
                view.cancelPendingInputEvents();
            }
        }
    }

    public /* synthetic */ void lambda$new$3$AbstractPopPanel() {
        if (this.isVmInit || !CarClientWrapper.getInstance().isCarServiceConnected()) {
            return;
        }
        onInitViewModel();
        this.mMainHandler.sendEmptyMessage(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initPanel() {
        this.isVmInit = true;
        XPopPanel.XPopPanelBuilder onCreatePanelBuilder = onCreatePanelBuilder();
        this.mPanelBuilder = onCreatePanelBuilder;
        if (onCreatePanelBuilder == null) {
            LogUtils.e(this.TAG, "create PanelBuilder failed, can not init view");
            return;
        }
        handleLifecycleChanged(Lifecycle.Event.ON_CREATE);
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$AbstractPopPanel$B43iMjvPhxM2r40Iy8CsJsUwMR4
            @Override // java.lang.Runnable
            public final void run() {
                AbstractPopPanel.this.lambda$initPanel$4$AbstractPopPanel();
            }
        }, 0L);
    }

    public /* synthetic */ void lambda$initPanel$4$AbstractPopPanel() {
        LogUtils.d(this.TAG, "onCreateView", false);
        this.mRootView = onCreateView();
        this.mMainHandler.sendEmptyMessage(3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.AbstractPopPanel$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass2 implements XPopPanel.PopPanelInterface {
        AnonymousClass2() {
        }

        @Override // com.xiaopeng.xui.app.XPopPanel.PopPanelInterface
        public void onShow() {
            LogUtils.d(AbstractPopPanel.this.TAG, "mPanel onShow: " + AbstractPopPanel.this.mPanel.toString(), false);
            AbstractPopPanel.this.isShow = true;
            AbstractPopPanel.this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$AbstractPopPanel$2$T77UF7vWqqSmOZyn1fEOED1cf6Y
                @Override // java.lang.Runnable
                public final void run() {
                    AbstractPopPanel.AnonymousClass2.this.lambda$onShow$0$AbstractPopPanel$2();
                }
            });
        }

        public /* synthetic */ void lambda$onShow$0$AbstractPopPanel$2() {
            AbstractPopPanel.this.handleLifecycleChanged(Lifecycle.Event.ON_RESUME);
        }

        @Override // com.xiaopeng.xui.app.XPopPanel.PopPanelInterface
        public void onHide() {
            LogUtils.d(AbstractPopPanel.this.TAG, "mPanel onHide: " + AbstractPopPanel.this.mPanel.toString(), false);
            AbstractPopPanel.this.isShow = false;
            AbstractPopPanel.this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$AbstractPopPanel$2$OGt5dFvQSib2_omeTunSI6unIKE
                @Override // java.lang.Runnable
                public final void run() {
                    AbstractPopPanel.AnonymousClass2.this.lambda$onHide$1$AbstractPopPanel$2();
                }
            });
        }

        public /* synthetic */ void lambda$onHide$1$AbstractPopPanel$2() {
            AbstractPopPanel.this.handleLifecycleChanged(Lifecycle.Event.ON_PAUSE);
            AbstractPopPanel.this.onDismiss();
        }
    }

    private void initPanelListener() {
        this.mPanel.setOnStateListener(new AnonymousClass2());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onViewAttach() {
        this.mCountDownLatch.countDown();
    }

    public final void show(int windowType, int mRegionId) {
        show(windowType, true, mRegionId);
    }

    public final void show(final int windowType, final boolean playTts, final int mRegionId) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$AbstractPopPanel$k9VN3m6BpYYcFM-umVZZiI0RYyI
            @Override // java.lang.Runnable
            public final void run() {
                AbstractPopPanel.this.lambda$show$5$AbstractPopPanel(playTts, windowType, mRegionId);
            }
        });
    }

    public /* synthetic */ void lambda$show$5$AbstractPopPanel(final boolean playTts, final int windowType, final int mRegionId) {
        try {
            this.mCountDownLatch.await();
            if (onInterceptShow()) {
                return;
            }
            this.mNeedPlayTts = playTts;
            Message obtain = Message.obtain();
            obtain.what = 4;
            obtain.arg1 = windowType;
            obtain.arg2 = mRegionId;
            this.mMainHandler.sendMessage(obtain);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected boolean onInterceptShow() {
        if (needInitVm()) {
            return !this.isVmInit;
        }
        return false;
    }

    private int dp(int dp) {
        return (int) TypedValue.applyDimension(1, dp, ResourceUtils.getResources().getDisplayMetrics());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShowEvent(int windowType, int mRegionId) {
        if (!isEqulPreStatus() || this.mPanel == null) {
            XPopPanel xPopPanel = this.mPanel;
            if (xPopPanel != null) {
                xPopPanel.setOnStateListener(null);
                this.mPanel.removeItemView();
            }
            this.mPanel = null;
            XPopPanel.XPopPanelBuilder xPopPanelBuilder = new XPopPanel.XPopPanelBuilder();
            this.mPanelBuilder = xPopPanelBuilder;
            xPopPanelBuilder.setContext(App.getInstance().getApplicationContext());
            this.mPanelBuilder.setRegionId(String.valueOf(mRegionId)).setDurationLength(10000).setType(windowType);
            this.mPanelBuilder.setClickable(true);
            this.mRootView.measure(0, 0);
            this.mRootViewWidth = this.mRootView.getMeasuredWidth();
            this.mRootViewHigh = this.mRootView.getMeasuredHeight();
            LogUtils.d(this.TAG, "mRootView  Width：High = " + dp(this.mRootViewWidth) + " : " + dp(this.mRootViewHigh) + "  windowType = " + windowType + "   mRegionId = " + mRegionId, false);
            this.mPanelBuilder.setWidth(dp(this.mRootViewWidth));
            this.mPanelBuilder.setHeight(dp(this.mRootViewHigh));
            this.mPanelBuilder.setItemView(this.mRootView);
            this.mPanelBuilder.setBackground(R.drawable.pop_panel_bg);
            this.mPanel = this.mPanelBuilder.create();
            initPanelListener();
            handleLifecycleChanged(Lifecycle.Event.ON_START);
            if (this.mIsVuiEnabled) {
                VuiManager.instance().initScene(this.mContext, getLifecycle(), getSceneId(), getRootView(), this, this.mElementChangedListener, null);
            }
        }
        LogUtils.d(this.TAG, "popPanel show success !isEqulPreStatus() = " + (!isEqulPreStatus()) + "   (mPanel == null):" + (this.mPanel == null));
        this.mPanel.show();
        onRegisterLiveData();
        onRefresh();
    }

    public final void dismiss() {
        XPopPanel xPopPanel = this.mPanel;
        if (xPopPanel != null) {
            xPopPanel.cancel();
        }
    }

    public void keepPanelShow() {
        XPopPanel xPopPanel = this.mPanel;
        if (xPopPanel != null) {
            xPopPanel.handleUserEvent();
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
    /* renamed from: com.xiaopeng.carcontrol.view.AbstractPopPanel$3  reason: invalid class name */
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

    protected void vuiFeedbackClick(int content, View view) {
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

    private void handleElementDirectEvent() {
        XPopPanel xPopPanel = this.mPanel;
        if (xPopPanel == null || !xPopPanel.isShowing()) {
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
