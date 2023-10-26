package com.xiaopeng.carcontrol.view.dialog.panel;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.VipSeatAct;
import com.xiaopeng.carcontrol.viewmodel.space.VipSeatStatus;
import com.xiaopeng.xui.widget.XButton;

/* loaded from: classes2.dex */
public class VipSeatControlPanel extends AbstractControlPanel {
    private static final int MSG_WHAT_DRV_MOVE = 1;
    private XButton mDrvLieDownBtn;
    private XButton mDrvPauseBtn;
    private XButton mDrvRecoverBtn;
    private final Handler mHandler = new Handler(ThreadUtils.getHandler(1).getLooper()) { // from class: com.xiaopeng.carcontrol.view.dialog.panel.VipSeatControlPanel.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg == null || msg.what != 1) {
                return;
            }
            VipSeatControlPanel.this.updateDrvSeatControlBtn();
        }
    };
    private XButton mPsnLieDownBtn;
    private XButton mPsnPauseBtn;
    private XButton mPsnRecoverBtn;
    private SeatViewModel mSeatVm;
    private SpaceCapsuleViewModel mSpaceCapsuleViewModel;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.layout_vip_seat;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean needInitVm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitViewModel() {
        this.mSeatVm = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        this.mSpaceCapsuleViewModel = (SpaceCapsuleViewModel) ViewModelManager.getInstance().getViewModelImpl(ISpaceCapsuleViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitView() {
        XButton xButton = (XButton) findViewById(R.id.drv_lie_down_btn);
        this.mDrvLieDownBtn = xButton;
        xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$5JRhGk_Wk_Qvxhy1A_Q352mGjrc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatControlPanel.this.lambda$onInitView$0$VipSeatControlPanel(view);
            }
        });
        XButton xButton2 = (XButton) findViewById(R.id.drv_pause_btn);
        this.mDrvPauseBtn = xButton2;
        xButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$QfMi-n9k2VoitYMx1_6ccCl3x7k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatControlPanel.this.lambda$onInitView$1$VipSeatControlPanel(view);
            }
        });
        XButton xButton3 = (XButton) findViewById(R.id.drv_restore_btn);
        this.mDrvRecoverBtn = xButton3;
        xButton3.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$Hr-q0Yn658-nIPALUL363jg_-zM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatControlPanel.this.lambda$onInitView$2$VipSeatControlPanel(view);
            }
        });
        XButton xButton4 = (XButton) findViewById(R.id.psn_lie_down_btn);
        this.mPsnLieDownBtn = xButton4;
        xButton4.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$3VWY6cdPZ0yIMMSuWCfKMoEibaA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatControlPanel.this.lambda$onInitView$3$VipSeatControlPanel(view);
            }
        });
        XButton xButton5 = (XButton) findViewById(R.id.psn_pause_btn);
        this.mPsnPauseBtn = xButton5;
        xButton5.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$tQzSSi28fryKuvmxX9XDE51VjoI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatControlPanel.this.lambda$onInitView$4$VipSeatControlPanel(view);
            }
        });
        XButton xButton6 = (XButton) findViewById(R.id.psn_restore_btn);
        this.mPsnRecoverBtn = xButton6;
        xButton6.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$svJDhCZcTFIxSRAHvdkVyRpCcOA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatControlPanel.this.lambda$onInitView$5$VipSeatControlPanel(view);
            }
        });
        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$I75A4pfsMrPyAz_DtjdEH1cpPvY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatControlPanel.this.lambda$onInitView$6$VipSeatControlPanel(view);
            }
        });
    }

    public /* synthetic */ void lambda$onInitView$0$VipSeatControlPanel(View v) {
        this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.LayFlat);
    }

    public /* synthetic */ void lambda$onInitView$1$VipSeatControlPanel(View v) {
        this.mSeatVm.stopDrvSeatMove();
    }

    public /* synthetic */ void lambda$onInitView$2$VipSeatControlPanel(View v) {
        this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.Restore);
    }

    public /* synthetic */ void lambda$onInitView$3$VipSeatControlPanel(View v) {
        this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.LayFlat);
    }

    public /* synthetic */ void lambda$onInitView$4$VipSeatControlPanel(View v) {
        this.mSeatVm.stopPsnSeatMove();
    }

    public /* synthetic */ void lambda$onInitView$5$VipSeatControlPanel(View v) {
        this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.Restore);
    }

    public /* synthetic */ void lambda$onInitView$6$VipSeatControlPanel(View v) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onRefresh() {
        if (this.mSeatVm.isDrvSeatLieFlat()) {
            this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.Flat);
        }
        if (this.mSeatVm.isPsnSeatLieFlat()) {
            this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.Flat);
        }
        updateDrvSeatControlBtn();
        updatePsnSeatControlBtn();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onDismiss() {
        super.onDismiss();
        this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.Stop);
        this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.Stop);
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onRegisterLiveData() {
        setLiveDataObserver(this.mSpaceCapsuleViewModel.getVipDrvSeatStatusData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$DerFWaD4INJw0u1869N3kqoVm_Y
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatControlPanel.this.lambda$onRegisterLiveData$7$VipSeatControlPanel((VipSeatStatus) obj);
            }
        });
        setLiveDataObserver(this.mSpaceCapsuleViewModel.getVipPsnSeatStatusData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$PzgobvNGj2MoleB8l54xZkdiJsw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatControlPanel.this.lambda$onRegisterLiveData$8$VipSeatControlPanel((VipSeatStatus) obj);
            }
        });
        setLiveDataObserver(this.mSeatVm.getDriverSeatData(1), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$V8dskOnLeXt18WsWsZXaIKpkE6Y
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatControlPanel.this.lambda$onRegisterLiveData$9$VipSeatControlPanel((Integer) obj);
            }
        });
        setLiveDataObserver(this.mSeatVm.getDriverSeatData(2), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$Tv_atLLMllRfNM_X82HCcKBwMXU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatControlPanel.this.lambda$onRegisterLiveData$10$VipSeatControlPanel((Integer) obj);
            }
        });
        setLiveDataObserver(this.mSeatVm.getDriverSeatData(3), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$VipSeatControlPanel$qQ9s0q0OkrcgYYho8bCnu6TxGQc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatControlPanel.this.lambda$onRegisterLiveData$11$VipSeatControlPanel((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onRegisterLiveData$7$VipSeatControlPanel(VipSeatStatus status) {
        updateDrvSeatControlBtn();
    }

    public /* synthetic */ void lambda$onRegisterLiveData$8$VipSeatControlPanel(VipSeatStatus status) {
        updatePsnSeatControlBtn();
    }

    public /* synthetic */ void lambda$onRegisterLiveData$9$VipSeatControlPanel(Integer integer) {
        onDrvSeatMove();
    }

    public /* synthetic */ void lambda$onRegisterLiveData$10$VipSeatControlPanel(Integer integer) {
        onDrvSeatMove();
    }

    public /* synthetic */ void lambda$onRegisterLiveData$11$VipSeatControlPanel(Integer integer) {
        onDrvSeatMove();
    }

    private void onDrvSeatMove() {
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDrvSeatControlBtn() {
        VipSeatStatus drvVipSeatStatus = this.mSpaceCapsuleViewModel.getDrvVipSeatStatus();
        boolean z = true;
        this.mDrvLieDownBtn.setEnabled((drvVipSeatStatus == VipSeatStatus.Flat || drvVipSeatStatus == VipSeatStatus.FlatMoving || drvVipSeatStatus == VipSeatStatus.RestoreMoving) ? false : true);
        XButton xButton = this.mDrvRecoverBtn;
        if (drvVipSeatStatus != VipSeatStatus.FlatPause && drvVipSeatStatus != VipSeatStatus.RestorePause && drvVipSeatStatus != VipSeatStatus.Flat && (drvVipSeatStatus != VipSeatStatus.Normal || this.mSeatVm.isDrvSeatEqualMemory())) {
            z = false;
        }
        xButton.setEnabled(z);
    }

    private void updatePsnSeatControlBtn() {
        VipSeatStatus psnVipSeatStatus = this.mSpaceCapsuleViewModel.getPsnVipSeatStatus();
        boolean z = true;
        this.mPsnLieDownBtn.setEnabled((psnVipSeatStatus == VipSeatStatus.Flat || psnVipSeatStatus == VipSeatStatus.FlatMoving || psnVipSeatStatus == VipSeatStatus.RestoreMoving) ? false : true);
        XButton xButton = this.mPsnRecoverBtn;
        if (psnVipSeatStatus != VipSeatStatus.RestoreWaiting && psnVipSeatStatus != VipSeatStatus.FlatPause && psnVipSeatStatus != VipSeatStatus.RestorePause && psnVipSeatStatus != VipSeatStatus.Flat && (psnVipSeatStatus != VipSeatStatus.Normal || this.mSeatVm.isPsnSeatEqualMemory())) {
            z = false;
        }
        xButton.setEnabled(z);
    }
}
