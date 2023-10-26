package com.xiaopeng.carcontrol.view.dialog.poppanel;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.AbstractPopPanel;
import com.xiaopeng.carcontrol.view.dialog.poppanel.AbstractPopPanelMenu;
import com.xiaopeng.xui.app.XPopPanel;

/* loaded from: classes2.dex */
public abstract class AbstractPopPanelMenu extends AbstractPopPanel {
    public static final int DRV_SEAT_DROPDOWN_MENU_GROUP = 1;
    public static final int PARK_GEAR_DROPDOWN_MENU_GROUP = 0;
    public static final int PSN_SEAT_DROPDOWN_MENU_GROUP = 2;
    private static final long TIME_OUT = 12000;
    private XPopPanel.XPopPanelBuilder builder;
    private final Runnable mAutoCloseTask = new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$ATAR3qwGMBhLbjV9lxWea96cClA
        @Override // java.lang.Runnable
        public final void run() {
            AbstractPopPanelMenu.this.dismiss();
        }
    };
    private CountDownTimer mCountDownTimer;

    public abstract int getDropdownMenuGroupId();

    /* JADX INFO: Access modifiers changed from: protected */
    public void unRegisterLiveData() {
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected XPopPanel.XPopPanelBuilder onCreatePanelBuilder() {
        XPopPanel.XPopPanelBuilder xPopPanelBuilder = new XPopPanel.XPopPanelBuilder();
        this.builder = xPopPanelBuilder;
        return xPopPanelBuilder;
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected void onShow() {
        startAutoCloseTask();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    public void onDismiss() {
        super.onDismiss();
        unRegisterLiveData();
        stopAutoCloseTask();
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected View onCreateView() {
        return LayoutInflater.from(App.getInstance()).inflate(getLayoutId(), (ViewGroup) null, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    public void onInitView() {
        getRootView().setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$AbstractPopPanelMenu$B-_atj1L3-JoIGEHP6W_nyRHAcc
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AbstractPopPanelMenu.this.lambda$onInitView$0$AbstractPopPanelMenu(view, motionEvent);
            }
        });
    }

    public /* synthetic */ boolean lambda$onInitView$0$AbstractPopPanelMenu(View v, MotionEvent ev) {
        LogUtils.d(this.TAG, "onTouch: " + ev.getAction(), false);
        ev.getActionMasked();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    public void onViewAttach() {
        super.onViewAttach();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    public void onRefresh() {
        if (isShow()) {
            startAutoCloseTask();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void startAutoCloseTask() {
        startAutoCloseTask(TIME_OUT);
    }

    protected final void startAutoCloseTask(long delay) {
        LogUtils.d(this.TAG, "startAutoCloseTask, delay: " + delay, false);
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(delay, 1000L);
        this.mCountDownTimer = anonymousClass1;
        anonymousClass1.start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.dialog.poppanel.AbstractPopPanelMenu$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 extends CountDownTimer {
        AnonymousClass1(long x0, long x1) {
            super(x0, x1);
        }

        public /* synthetic */ void lambda$onTick$0$AbstractPopPanelMenu$1(final long millisUntilFinished) {
            AbstractPopPanelMenu.this.onShowTimeUpdate(millisUntilFinished);
        }

        @Override // android.os.CountDownTimer
        public void onTick(final long millisUntilFinished) {
            AbstractPopPanelMenu.this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$AbstractPopPanelMenu$1$gbKlNOrs7eVbJvf-anHqlq0Slqg
                @Override // java.lang.Runnable
                public final void run() {
                    AbstractPopPanelMenu.AnonymousClass1.this.lambda$onTick$0$AbstractPopPanelMenu$1(millisUntilFinished);
                }
            });
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            AbstractPopPanelMenu.this.dismiss();
        }
    }

    protected final void stopAutoCloseTask() {
        LogUtils.d(this.TAG, "stopAutoCloseTask", false);
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this.mCountDownTimer = null;
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    public void keepPanelShow() {
        super.keepPanelShow();
    }
}
