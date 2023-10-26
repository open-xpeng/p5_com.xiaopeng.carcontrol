package com.xiaopeng.carcontrol.view.dialog.poppanel;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.account.AccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.widget.XButton;

/* loaded from: classes2.dex */
public class SeatMirrorCtrlMenu extends AbstractPopPanelMenu {
    private String groupName;
    private boolean isLogin;
    private boolean isPreLogin;
    private AccountViewModel mAccountVm;
    private View mCloseBtnBg;
    private XButton mDoneTv;
    private XButton mResetBtn;
    private XButton mSaveAsNewBtn;
    private XButton mSaveBtn;
    private SeatViewModel mSeatVm;
    private TextView mTimeTxt;
    private TextView mTitle;
    private VcuViewModel mVcuVm;
    Observer<Float> mCarSpeedObserver = new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$SeatMirrorCtrlMenu$8G3oH6wjwz8gdmrP2tUmduGC3JA
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            SeatMirrorCtrlMenu.this.lambda$new$4$SeatMirrorCtrlMenu((Float) obj);
        }
    };
    Observer<Integer> mCreateSyncGroupObserver = new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$SeatMirrorCtrlMenu$MgEorqdoowyyb94bsSPcI3PZ5k4
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            SeatMirrorCtrlMenu.this.lambda$new$5$SeatMirrorCtrlMenu((Integer) obj);
        }
    };

    @Override // com.xiaopeng.carcontrol.view.dialog.poppanel.AbstractPopPanelMenu
    public int getDropdownMenuGroupId() {
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    public int getLayoutId() {
        return R.layout.layout_seat_mirror_ctrl_menu;
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_SEAT_POP_PANEL_MENU;
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected boolean needInitVm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected void onInitViewModel() {
        this.mSeatVm = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        this.mVcuVm = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        this.mAccountVm = (AccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.poppanel.AbstractPopPanelMenu, com.xiaopeng.carcontrol.view.AbstractPopPanel
    public void onInitView() {
        super.onInitView();
        this.mCloseBtnBg = findViewById(R.id.close_bg);
        findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$SeatMirrorCtrlMenu$ok4H6F5SbUBRPghjxQ5VW6V-99o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SeatMirrorCtrlMenu.this.lambda$onInitView$0$SeatMirrorCtrlMenu(view);
            }
        });
        this.mTimeTxt = (TextView) findViewById(R.id.time);
        TextView textView = (TextView) findViewById(R.id.title);
        this.mTitle = textView;
        textView.setSelected(true);
        XButton xButton = (XButton) findViewById(R.id.seat_save_btn);
        this.mSaveBtn = xButton;
        VuiUtils.addHasFeedbackProp(xButton);
        XButton xButton2 = (XButton) findViewById(R.id.seat_reset_btn);
        this.mResetBtn = xButton2;
        VuiUtils.addHasFeedbackProp(xButton2);
        this.mDoneTv = (XButton) findViewById(R.id.seat_done_tv);
        this.mSaveBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$SeatMirrorCtrlMenu$Ntgfx7iiaK7iCOuy8HOfXngsrus
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SeatMirrorCtrlMenu.this.lambda$onInitView$1$SeatMirrorCtrlMenu(view);
            }
        });
        this.mResetBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$SeatMirrorCtrlMenu$w1lDsFCDmFV-TPeom1fTYHoe0Sw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SeatMirrorCtrlMenu.this.lambda$onInitView$2$SeatMirrorCtrlMenu(view);
            }
        });
        XButton xButton3 = (XButton) findViewById(R.id.save_as_new_btn);
        this.mSaveAsNewBtn = xButton3;
        VuiUtils.addHasFeedbackProp(xButton3);
        this.mSaveAsNewBtn.setVisibility(0);
        this.mSaveAsNewBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$SeatMirrorCtrlMenu$huPLmOVu_afRMa6hkzMbx9izsq4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SeatMirrorCtrlMenu.this.lambda$onInitView$3$SeatMirrorCtrlMenu(view);
            }
        });
    }

    public /* synthetic */ void lambda$onInitView$0$SeatMirrorCtrlMenu(View v) {
        dismiss();
    }

    public /* synthetic */ void lambda$onInitView$1$SeatMirrorCtrlMenu(View v) {
        this.mSaveBtn.setVisibility(8);
        this.mResetBtn.setVisibility(8);
        this.mSaveAsNewBtn.setEnabled(false);
        this.mCloseBtnBg.setEnabled(false);
        this.mDoneTv.setVisibility(0);
        this.mDoneTv.setText(ResUtils.getString(R.string.seat_saved, Integer.valueOf(this.mSeatVm.getDrvSeatPosIdx() + 1)));
        this.mSeatVm.memoryDrvSeatData();
        startAutoCloseTask();
        StatisticUtils.sendStatistic(PageEnum.SEAT_SAVE_SMALL_PAGE, BtnEnum.SEAT_SAVE_SMALL_PAGE_SAVE_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$onInitView$2$SeatMirrorCtrlMenu(View v) {
        if (checkChairMovable(false)) {
            this.mSaveBtn.setVisibility(8);
            this.mResetBtn.setVisibility(8);
            if (this.isLogin) {
                this.mSaveAsNewBtn.setEnabled(false);
            }
            this.mDoneTv.setVisibility(0);
            this.mDoneTv.setText(ResUtils.getString(R.string.seat_resuming, Integer.valueOf(this.mSeatVm.getDrvSeatPosIdx() + 1)));
            this.mSeatVm.restoreDrvSeatPos();
            StatisticUtils.sendStatistic(PageEnum.SEAT_SAVE_SMALL_PAGE, BtnEnum.SEAT_SAVE_SMALL_PAGE_RESTORE_BTN, new Object[0]);
        }
    }

    public /* synthetic */ void lambda$onInitView$3$SeatMirrorCtrlMenu(View v) {
        this.mCloseBtnBg.setEnabled(false);
        this.mAccountVm.requestCreateNewSyncGroup();
        startAutoCloseTask();
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        if (vuiEvent.getHitVuiElement().getId().contains(String.valueOf((int) R.id.seat_reset_btn))) {
            return true ^ checkChairMovable(true);
        }
        return super.onInterceptVuiEvent(view, vuiEvent);
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected void onRegisterLiveData() {
        setLiveDataObserver(this.mVcuVm.getCarSpeedData(), this.mCarSpeedObserver);
        setLiveDataObserver(this.mAccountVm.getCreateSyncGroupData(), this.mCreateSyncGroupObserver);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.poppanel.AbstractPopPanelMenu
    public void unRegisterLiveData() {
        super.unRegisterLiveData();
        this.mVcuVm.getCarSpeedData().removeObserver(this.mCarSpeedObserver);
        this.mAccountVm.getCreateSyncGroupData().removeObserver(this.mCreateSyncGroupObserver);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.poppanel.AbstractPopPanelMenu, com.xiaopeng.carcontrol.view.AbstractPopPanel
    public void onRefresh() {
        String string;
        super.onRefresh();
        this.isPreLogin = this.isLogin;
        this.isLogin = this.mAccountVm.checkLogin();
        this.mSaveBtn.setVisibility(0);
        this.mSaveBtn.setEnabled(true);
        this.mResetBtn.setVisibility(0);
        this.mResetBtn.setEnabled(this.mVcuVm.getCarSpeed() <= 3.0f);
        this.mDoneTv.setVisibility(8);
        this.mSaveAsNewBtn.setVisibility(this.isLogin ? 0 : 8);
        this.mSaveAsNewBtn.setEnabled(this.isLogin);
        this.mCloseBtnBg.setEnabled(true);
        if (this.isLogin) {
            this.groupName = this.mAccountVm.getCurrentSyncGroupName();
            this.mSaveAsNewBtn.setText(R.string.btn_seat_memory_new);
        } else {
            this.groupName = null;
        }
        TextView textView = this.mTitle;
        if (this.isLogin && !TextUtils.isEmpty(this.groupName)) {
            string = ResUtils.getString(R.string.seat_mirror_ctrl_title_with_login, this.groupName);
        } else {
            string = ResUtils.getString(R.string.seat_mirror_ctrl_title);
        }
        textView.setText(string);
        this.mTitle.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    public void onShowTimeUpdate(long millisUntilFinished) {
        TextView textView;
        double d = (millisUntilFinished - 1100) / 1000.0d;
        if (d < 0.0d || (textView = this.mTimeTxt) == null) {
            return;
        }
        textView.setText(((long) Math.floor(d)) + ResUtils.getString(R.string.seconds));
    }

    private boolean checkChairMovable(boolean isVuiAction) {
        if (this.mVcuVm.getCarSpeed() >= 3.0f) {
            NotificationHelper.getInstance().showToast(R.string.smart_chair_speed_error);
            if (isVuiAction) {
                vuiFeedback(R.string.smart_chair_speed_error, this.mResetBtn);
            }
            return false;
        } else if (this.mSeatVm.isDrvDoorOpened()) {
            NotificationHelper.getInstance().showToast(R.string.smart_chair_door_error);
            if (isVuiAction) {
                vuiFeedback(R.string.smart_chair_door_error, this.mResetBtn);
            }
            return false;
        } else if (this.mSeatVm.isDrvSeatOccupied()) {
            return true;
        } else {
            NotificationHelper.getInstance().showToast(R.string.smart_chair_driver_error);
            if (isVuiAction) {
                vuiFeedback(R.string.smart_chair_driver_error, this.mResetBtn);
            }
            return false;
        }
    }

    public /* synthetic */ void lambda$new$4$SeatMirrorCtrlMenu(Float curSpeed) {
        if (curSpeed == null || getRootView() == null) {
            return;
        }
        getRootView().findViewById(R.id.seat_reset_btn).setEnabled(curSpeed.floatValue() <= 3.0f);
    }

    public /* synthetic */ void lambda$new$5$SeatMirrorCtrlMenu(Integer state) {
        String string;
        String string2;
        if (state == null) {
            return;
        }
        int intValue = state.intValue();
        if (intValue == -1) {
            TextView textView = this.mTitle;
            if (this.isLogin && !TextUtils.isEmpty(this.groupName)) {
                string = ResUtils.getString(R.string.seat_mirror_ctrl_title_with_login, this.groupName);
            } else {
                string = ResUtils.getString(R.string.seat_mirror_ctrl_title);
            }
            textView.setText(string);
            this.mSaveBtn.setEnabled(true);
            this.mResetBtn.setEnabled(true);
            this.mSaveAsNewBtn.setEnabled(true);
            this.mSaveAsNewBtn.setText(R.string.btn_seat_memory_new);
        } else if (intValue != 0) {
            if (intValue == 1) {
                this.mSaveBtn.setEnabled(false);
                this.mResetBtn.setEnabled(false);
                this.mSaveAsNewBtn.setEnabled(false);
                this.mSaveAsNewBtn.setText(R.string.saving_drv_seat);
            } else if (intValue == 2 && !this.mSaveAsNewBtn.isEnabled()) {
                this.mSaveAsNewBtn.setText(R.string.save_drv_seat);
            }
        } else {
            this.isPreLogin = this.isLogin;
            this.isLogin = this.mAccountVm.checkLogin();
            this.mSaveBtn.setVisibility(0);
            this.mSaveBtn.setEnabled(true);
            this.mResetBtn.setVisibility(0);
            this.mResetBtn.setEnabled(true);
            this.mDoneTv.setVisibility(8);
            this.mSaveAsNewBtn.setVisibility(this.isLogin ? 0 : 8);
            if (this.isLogin) {
                this.groupName = this.mAccountVm.getCurrentSyncGroupName();
                this.mSaveAsNewBtn.setEnabled(true);
                this.mSaveAsNewBtn.setText(R.string.btn_seat_memory_new);
            }
            TextView textView2 = this.mTitle;
            if (this.isLogin && !TextUtils.isEmpty(this.groupName)) {
                string2 = ResUtils.getString(R.string.seat_mirror_ctrl_title_with_login, this.groupName);
            } else {
                string2 = ResUtils.getString(R.string.seat_mirror_ctrl_title);
            }
            textView2.setText(string2);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected boolean isEqulPreStatus() {
        return this.isLogin == this.isPreLogin;
    }
}
