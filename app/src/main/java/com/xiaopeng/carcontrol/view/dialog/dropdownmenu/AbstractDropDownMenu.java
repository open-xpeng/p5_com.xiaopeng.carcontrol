package com.xiaopeng.carcontrol.view.dialog.dropdownmenu;

import android.app.Dialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.view.dialog.AbstractPanel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialogPure;

/* loaded from: classes2.dex */
public abstract class AbstractDropDownMenu extends AbstractPanel {
    public static final int DRV_SEAT_DROPDOWN_MENU_GROUP = 1;
    public static final int PARK_GEAR_DROPDOWN_MENU_GROUP = 0;
    public static final int PSN_SEAT_DROPDOWN_MENU_GROUP = 2;
    private static final long TIME_OUT = 10000;
    private final Runnable mAutoCloseTask = new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$DroSKZ9rOBJLFr2gwLpkInsX0Uc
        @Override // java.lang.Runnable
        public final void run() {
            AbstractDropDownMenu.this.dismiss();
        }
    };
    private Handler mHandler;
    private XDialogPure mMenu;

    public abstract int getDropdownMenuGroupId();

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected Dialog onCreatePanel() {
        XDialogPure xDialogPure = new XDialogPure(App.getInstance(), XDialogPure.Parameters.Builder().setTheme(R.style.Drop_Down_Menu_Style));
        this.mMenu = xDialogPure;
        Window window = xDialogPure.getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = BadgeDrawable.TOP_START;
            attributes.x = ResUtils.getDimensionPixelSize(R.dimen.drop_down_menu_offset_x);
            attributes.y = ResUtils.getDimensionPixelSize(R.dimen.drop_down_menu_offset_y);
            window.setAttributes(attributes);
        }
        return this.mMenu.getDialog();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onShow() {
        startAutoCloseTask();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onDismiss() {
        stopAutoCloseTask();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected View onCreateView() {
        return LayoutInflater.from(App.getInstance()).inflate(getLayoutId(), (ViewGroup) null, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onInitView() {
        getRootView().setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$AbstractDropDownMenu$v3og6-M9zLl9KDyyPGy4usP0CMw
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AbstractDropDownMenu.this.lambda$onInitView$0$AbstractDropDownMenu(view, motionEvent);
            }
        });
    }

    public /* synthetic */ boolean lambda$onInitView$0$AbstractDropDownMenu(View v, MotionEvent ev) {
        LogUtils.d(this.TAG, "onTouch: " + ev.getAction(), false);
        int actionMasked = ev.getActionMasked();
        if (actionMasked == 0) {
            stopAutoCloseTask();
        } else if (actionMasked == 1 || actionMasked == 3) {
            startAutoCloseTask();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onViewAttach() {
        this.mMenu.setContentView(getRootView());
        super.onViewAttach();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onRefresh() {
        if (isShow()) {
            startAutoCloseTask();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void startAutoCloseTask() {
        startAutoCloseTask(TIME_OUT);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void startAutoCloseTask(long delay) {
        LogUtils.d(this.TAG, "startAutoCloseTask: delay=" + delay, false);
        if (this.mHandler == null) {
            this.mHandler = new Handler();
        }
        this.mHandler.removeCallbacks(this.mAutoCloseTask);
        this.mHandler.postDelayed(this.mAutoCloseTask, delay);
    }

    final void stopAutoCloseTask() {
        LogUtils.d(this.TAG, "stopAutoCloseTask", false);
        this.mHandler.removeCallbacks(this.mAutoCloseTask);
    }
}
