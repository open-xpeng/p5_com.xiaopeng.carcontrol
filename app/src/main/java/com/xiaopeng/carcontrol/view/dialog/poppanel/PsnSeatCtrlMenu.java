package com.xiaopeng.carcontrol.view.dialog.poppanel;

import android.view.View;
import android.widget.TextView;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.widget.XButton;

/* loaded from: classes2.dex */
public class PsnSeatCtrlMenu extends AbstractPopPanelMenu {
    private View mCloseBtnBg;
    private XButton mDoneTv;
    private XButton mResetBtn;
    private XButton mSaveBtn;
    private SeatViewModel mSeatVm;
    private TextView mTimeTxt;
    private TextView mTitle;
    private VcuViewModel mVcuVm;

    @Override // com.xiaopeng.carcontrol.view.dialog.poppanel.AbstractPopPanelMenu
    public int getDropdownMenuGroupId() {
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    public int getLayoutId() {
        return R.layout.layout_psn_seat_ctrl_menu;
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_PSN_POP_PANEL_MENU;
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected boolean isEqulPreStatus() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected boolean needInitVm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected void onRegisterLiveData() {
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel
    protected void onInitViewModel() {
        this.mSeatVm = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        this.mVcuVm = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.poppanel.AbstractPopPanelMenu, com.xiaopeng.carcontrol.view.AbstractPopPanel
    public void onInitView() {
        super.onInitView();
        this.mCloseBtnBg = findViewById(R.id.close_bg);
        findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$PsnSeatCtrlMenu$_yBjxrmgOvNM7O8nAWGs00OS_Jc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PsnSeatCtrlMenu.this.lambda$onInitView$0$PsnSeatCtrlMenu(view);
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
        this.mSaveBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$PsnSeatCtrlMenu$KeMlAzWNICBKLaaNzn8glOiz0LA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PsnSeatCtrlMenu.this.lambda$onInitView$1$PsnSeatCtrlMenu(view);
            }
        });
        this.mResetBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$PsnSeatCtrlMenu$1mcVLBJoXdefkfL7skh0YKIGFsU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PsnSeatCtrlMenu.this.lambda$onInitView$2$PsnSeatCtrlMenu(view);
            }
        });
    }

    public /* synthetic */ void lambda$onInitView$0$PsnSeatCtrlMenu(View v) {
        dismiss();
    }

    public /* synthetic */ void lambda$onInitView$1$PsnSeatCtrlMenu(View v) {
        this.mSaveBtn.setVisibility(8);
        this.mResetBtn.setVisibility(8);
        this.mDoneTv.setVisibility(0);
        this.mDoneTv.setText(ResUtils.getString(R.string.seat_saved_new));
        this.mSeatVm.memoryPsnSeatData();
        this.mCloseBtnBg.setEnabled(false);
    }

    public /* synthetic */ void lambda$onInitView$2$PsnSeatCtrlMenu(View v) {
        this.mSaveBtn.setVisibility(8);
        this.mResetBtn.setVisibility(8);
        this.mDoneTv.setVisibility(0);
        this.mDoneTv.setText(ResUtils.getString(R.string.seat_psn_resuming));
        this.mSeatVm.restorePsnSeatPos();
        StatisticUtils.sendStatistic(PageEnum.SEAT_SAVE_SMALL_PAGE, BtnEnum.SEAT_SAVE_SMALL_PAGE_RESTORE_BTN, new Object[0]);
    }

    @Override // com.xiaopeng.carcontrol.view.AbstractPopPanel, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        return super.onInterceptVuiEvent(view, vuiEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.poppanel.AbstractPopPanelMenu, com.xiaopeng.carcontrol.view.AbstractPopPanel
    public void onRefresh() {
        super.onRefresh();
        this.mSaveBtn.setVisibility(0);
        this.mSaveBtn.setEnabled(true);
        this.mResetBtn.setVisibility(0);
        this.mResetBtn.setEnabled(true);
        this.mDoneTv.setVisibility(8);
        this.mCloseBtnBg.setEnabled(true);
        this.mTitle.setText(ResUtils.getString(R.string.seat_mirror_ctrl_title_with_login, ResUtils.getString(R.string.psn_seat_memory) + convertPsnHabitId(this.mSeatVm.getCurrentSelectedPsnHabit())));
    }

    private String convertPsnHabitId(int id) {
        if (id != 0) {
            if (id != 1) {
                return id != 2 ? "" : ResUtils.getString(R.string.psn_three);
            }
            return ResUtils.getString(R.string.psn_two);
        }
        return ResUtils.getString(R.string.psn_one);
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
}
