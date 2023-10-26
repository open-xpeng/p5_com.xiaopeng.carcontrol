package com.xiaopeng.carcontrol.view.dialog.dropdownmenu;

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
public class SeatCtrlMenu extends AbstractDropDownMenu {
    private String groupName;
    private boolean isLogin;
    private AccountViewModel mAccountVm;
    private XButton mDoneTv;
    private XButton mResetBtn;
    private XButton mSaveAsNewBtn;
    private XButton mSaveBtn;
    private SeatViewModel mSeatVm;
    private TextView mTitle;
    private VcuViewModel mVcuVm;

    @Override // com.xiaopeng.carcontrol.view.dialog.dropdownmenu.AbstractDropDownMenu
    public int getDropdownMenuGroupId() {
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.layout_seat_ctrl_menu;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_SEAT_DROP_DOWN_MENU;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean needInitVm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitViewModel() {
        this.mSeatVm = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        this.mVcuVm = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        this.mAccountVm = (AccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.dropdownmenu.AbstractDropDownMenu, com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onInitView() {
        super.onInitView();
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
        XButton xButton3 = (XButton) findViewById(R.id.save_as_new_btn);
        this.mSaveAsNewBtn = xButton3;
        VuiUtils.addHasFeedbackProp(xButton3);
        this.mSaveBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$SeatCtrlMenu$TsAmVJc_aTqiJS76fsh7cuhdZyw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SeatCtrlMenu.this.lambda$onInitView$0$SeatCtrlMenu(view);
            }
        });
        this.mResetBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$SeatCtrlMenu$QHe_tBIsIItZnDzV6FD4nTKLdAE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SeatCtrlMenu.this.lambda$onInitView$1$SeatCtrlMenu(view);
            }
        });
        this.mSaveAsNewBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$SeatCtrlMenu$4BVKPi66BXXFh-KhNgxElGHsfXc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SeatCtrlMenu.this.lambda$onInitView$2$SeatCtrlMenu(view);
            }
        });
    }

    public /* synthetic */ void lambda$onInitView$0$SeatCtrlMenu(View v) {
        this.mSaveBtn.setVisibility(8);
        this.mResetBtn.setVisibility(8);
        this.mSaveAsNewBtn.setEnabled(false);
        this.mDoneTv.setVisibility(0);
        this.mDoneTv.setText(ResUtils.getString(R.string.seat_saved_new));
        this.mSeatVm.memoryDrvSeatData();
        StatisticUtils.sendStatistic(PageEnum.SEAT_SAVE_SMALL_PAGE, BtnEnum.SEAT_SAVE_SMALL_PAGE_SAVE_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$onInitView$1$SeatCtrlMenu(View v) {
        if (checkChairMovable(false)) {
            this.mSaveBtn.setVisibility(8);
            this.mResetBtn.setVisibility(8);
            this.mSaveAsNewBtn.setEnabled(false);
            this.mDoneTv.setVisibility(0);
            this.mDoneTv.setText(ResUtils.getString(R.string.seat_resuming, Integer.valueOf(this.mSeatVm.getDrvSeatPosIdx() + 1)));
            this.mSeatVm.restoreDrvSeatPos();
            StatisticUtils.sendStatistic(PageEnum.SEAT_SAVE_SMALL_PAGE, BtnEnum.SEAT_SAVE_SMALL_PAGE_RESTORE_BTN, new Object[0]);
        }
    }

    public /* synthetic */ void lambda$onInitView$2$SeatCtrlMenu(View v) {
        this.mAccountVm.requestCreateNewSyncGroup();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        if (vuiEvent.getHitVuiElement().getId().contains(String.valueOf((int) R.id.seat_reset_btn))) {
            return true ^ checkChairMovable(true);
        }
        return super.onInterceptVuiEvent(view, vuiEvent);
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onRegisterLiveData() {
        setLiveDataObserver(this.mVcuVm.getCarSpeedData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$SeatCtrlMenu$j5GmwwDQJHfPxm9uVO-5MwvUHM8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatCtrlMenu.this.lambda$onRegisterLiveData$3$SeatCtrlMenu((Float) obj);
            }
        });
        setLiveDataObserver(this.mAccountVm.getCreateSyncGroupData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.dropdownmenu.-$$Lambda$SeatCtrlMenu$msKIfIol4EMMdnjx25KernTqVCg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatCtrlMenu.this.lambda$onRegisterLiveData$4$SeatCtrlMenu((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onRegisterLiveData$3$SeatCtrlMenu(Float curSpeed) {
        if (curSpeed == null || getRootView() == null) {
            return;
        }
        getRootView().findViewById(R.id.seat_reset_btn).setEnabled(curSpeed.floatValue() <= 3.0f);
    }

    public /* synthetic */ void lambda$onRegisterLiveData$4$SeatCtrlMenu(Integer state) {
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
            } else if (intValue != 2) {
            } else {
                this.mSaveBtn.setEnabled(false);
                this.mResetBtn.setEnabled(false);
                this.mSaveAsNewBtn.setEnabled(false);
                this.mSaveAsNewBtn.setText(R.string.save_drv_seat);
            }
        } else {
            this.mSaveBtn.setVisibility(0);
            this.mSaveBtn.setEnabled(true);
            this.mResetBtn.setVisibility(0);
            this.mResetBtn.setEnabled(true);
            this.mDoneTv.setVisibility(8);
            this.mSaveAsNewBtn.setVisibility(this.isLogin ? 0 : 8);
            this.groupName = this.mAccountVm.getCurrentSyncGroupName();
            this.mSaveAsNewBtn.setEnabled(true);
            this.mSaveAsNewBtn.setText(R.string.btn_seat_memory_new);
            TextView textView2 = this.mTitle;
            if (this.isLogin && !TextUtils.isEmpty(this.groupName)) {
                string2 = ResUtils.getString(R.string.seat_mirror_ctrl_title_with_login, this.groupName);
            } else {
                string2 = ResUtils.getString(R.string.seat_mirror_ctrl_title);
            }
            textView2.setText(string2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.dropdownmenu.AbstractDropDownMenu, com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onRefresh() {
        String string;
        super.onRefresh();
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
        TextView textView = this.mTitle;
        if (this.isLogin && !TextUtils.isEmpty(this.groupName)) {
            string = ResUtils.getString(R.string.seat_mirror_ctrl_title_with_login, this.groupName);
        } else {
            string = ResUtils.getString(R.string.seat_mirror_ctrl_title);
        }
        textView.setText(string);
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
}
