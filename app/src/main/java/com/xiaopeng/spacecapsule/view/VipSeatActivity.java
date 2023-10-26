package com.xiaopeng.spacecapsule.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.rastermill.FrameSequenceUtil;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioMode;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.VipSeatAct;
import com.xiaopeng.carcontrol.viewmodel.space.VipSeatStatus;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.utils.XActivityUtils;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class VipSeatActivity extends XActivity {
    private static final int MSG_WHAT_DRV_MOVE = 1;
    private static final String TAG = "VipSeatActivity";
    private XImageView mBackSeatImg;
    private ViewGroup mBackSeatTipsLayout;
    private XTextView mDescTv;
    private XButton mDriverFlatBtn;
    private XImageView mDriverHeadImg;
    private XButton mDriverRecoverBtn;
    private final Handler mHandler = new Handler(ThreadUtils.getHandler(1).getLooper()) { // from class: com.xiaopeng.spacecapsule.view.VipSeatActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg == null || msg.what != 1) {
                return;
            }
            VipSeatActivity.this.updateVipSeatStatus();
        }
    };
    private XImageView mHeadImg;
    private ViewGroup mHeadTipsLayout;
    private XButton mPsnFlatBtn;
    private XImageView mPsnHeadImg;
    private XButton mPsnRecoverBtn;
    private ScenarioViewModel mScenarioViewModel;
    private SeatViewModel mSeatViewModel;
    private SpaceCapsuleViewModel mSpaceCapsuleViewModel;
    private XTextView mTitleTv;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        setContentView(R.layout.activity_vip_seat);
        this.mSpaceCapsuleViewModel = (SpaceCapsuleViewModel) ViewModelManager.getInstance().getViewModelImpl(ISpaceCapsuleViewModel.class);
        this.mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
        this.mSeatViewModel = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        this.mSpaceCapsuleViewModel.setCurrentSubMode(7);
        initView();
        processIntent(getIntent());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LogUtils.d(TAG, "onNewIntent");
        processIntent(getIntent());
    }

    private void initView() {
        findViewById(R.id.close_iv).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$8DW6ejyOaKvvq0l6hJxTPhmMn5w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatActivity.this.lambda$initView$0$VipSeatActivity(view);
            }
        });
        this.mTitleTv = (XTextView) findViewById(R.id.space_capsule_prepare_title);
        this.mDescTv = (XTextView) findViewById(R.id.space_capsule_prepare_summary);
        XButton xButton = (XButton) findViewById(R.id.driver_seat_lie_down_btn);
        this.mDriverFlatBtn = xButton;
        xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$E41tH7Tz-jQjhdfMVVzlZCaWD_E
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatActivity.this.lambda$initView$1$VipSeatActivity(view);
            }
        });
        XButton xButton2 = (XButton) findViewById(R.id.driver_seat_recover_btn);
        this.mDriverRecoverBtn = xButton2;
        xButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$WXLCJfkOUkO_8kYtDZNd4kE9iVY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatActivity.this.lambda$initView$2$VipSeatActivity(view);
            }
        });
        XButton xButton3 = (XButton) findViewById(R.id.psn_seat_lie_down_btn);
        this.mPsnFlatBtn = xButton3;
        xButton3.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$gZA6LtpjdJKrag2mVKUk6xN-nMc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatActivity.this.lambda$initView$3$VipSeatActivity(view);
            }
        });
        XButton xButton4 = (XButton) findViewById(R.id.psn_seat_recover_btn);
        this.mPsnRecoverBtn = xButton4;
        xButton4.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$dVwbBFWKsTnscYJd3YWB-srpdAs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipSeatActivity.this.lambda$initView$4$VipSeatActivity(view);
            }
        });
        this.mDriverHeadImg = (XImageView) findViewById(R.id.driver_head_tips_img);
        this.mPsnHeadImg = (XImageView) findViewById(R.id.psn_head_tips_img);
        this.mHeadImg = (XImageView) findViewById(R.id.head_tips_img);
        this.mBackSeatImg = (XImageView) findViewById(R.id.back_seat_tips_img);
        this.mHeadTipsLayout = (ViewGroup) findViewById(R.id.head_tips_layout);
        this.mBackSeatTipsLayout = (ViewGroup) findViewById(R.id.back_seat_tips_layout);
        if (this.mSeatViewModel.isDrvSeatLieFlat()) {
            this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.Flat);
        }
        if (this.mSeatViewModel.isPsnSeatLieFlat()) {
            this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.Flat);
        }
        this.mTitleTv.setText(R.string.vip_seat_title);
        this.mDescTv.setText(R.string.vip_seat_title);
        this.mDriverFlatBtn.setVisibility(0);
        this.mDriverRecoverBtn.setVisibility(0);
        this.mPsnFlatBtn.setVisibility(0);
        this.mPsnRecoverBtn.setVisibility(0);
        setLiveDataObserver(this.mSpaceCapsuleViewModel.getVipDrvSeatStatusData(), new Observer() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$ihhS12ib5Zu7zsBKn_qN17yOfZM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatActivity.this.lambda$initView$5$VipSeatActivity((VipSeatStatus) obj);
            }
        });
        setLiveDataObserver(this.mSpaceCapsuleViewModel.getVipPsnSeatStatusData(), new Observer() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$pNrBCzIE2Cu3Y2fTHfN65O3oaOU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatActivity.this.lambda$initView$6$VipSeatActivity((VipSeatStatus) obj);
            }
        });
        setLiveDataObserver(this.mSeatViewModel.getDriverSeatData(1), new Observer() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$aENrUtJvTKFZI-DVhsrarL0a8gE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatActivity.this.lambda$initView$7$VipSeatActivity((Integer) obj);
            }
        });
        setLiveDataObserver(this.mSeatViewModel.getDriverSeatData(2), new Observer() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$wVniGd74m0EUjzjJltC-1OVjn9I
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatActivity.this.lambda$initView$8$VipSeatActivity((Integer) obj);
            }
        });
        setLiveDataObserver(this.mSeatViewModel.getDriverSeatData(3), new Observer() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$go_i_5tjnywup4dzJ1SLswx7oh0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatActivity.this.lambda$initView$9$VipSeatActivity((Integer) obj);
            }
        });
        setLiveDataObserver(this.mSeatViewModel.getRlSeatOccupiedData(), new Observer() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$mRye1ymqyf8DiE1nOPXQrw6Iovs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatActivity.this.lambda$initView$10$VipSeatActivity((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mSeatViewModel.getRmSeatOccupiedData(), new Observer() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$wcAXmOa4PCd-fTAaxBQjg5Phg80
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatActivity.this.lambda$initView$11$VipSeatActivity((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mSeatViewModel.getRrSeatOccupiedData(), new Observer() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$XNDshtRcT3tZZW0xtUo3OUvAqqk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatActivity.this.lambda$initView$12$VipSeatActivity((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mSeatViewModel.getDrvHeadrestNormal(), new Observer() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$BvZzAj7T4Ws3Y-MzkvsypYfkLwU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatActivity.this.lambda$initView$13$VipSeatActivity((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mSeatViewModel.getPsnHeadrestNormal(), new Observer() { // from class: com.xiaopeng.spacecapsule.view.-$$Lambda$VipSeatActivity$80wT9_8bp2ozfwFq7TbSn-X934A
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatActivity.this.lambda$initView$14$VipSeatActivity((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$VipSeatActivity(View v) {
        startToExit();
    }

    public /* synthetic */ void lambda$initView$1$VipSeatActivity(View v) {
        if (this.mSpaceCapsuleViewModel.getDrvVipSeatStatus() == VipSeatStatus.FlatMoving) {
            this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.LayFlatPause);
            StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_DRIVER_STOP, new Object[0]);
            return;
        }
        this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.LayFlat);
        StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_DRIVER_FLAT, new Object[0]);
    }

    public /* synthetic */ void lambda$initView$2$VipSeatActivity(View v) {
        if (this.mSpaceCapsuleViewModel.getDrvVipSeatStatus() == VipSeatStatus.RestoreMoving) {
            this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.RestorePause);
            StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_DRIVER_STOP, new Object[0]);
            return;
        }
        this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.Restore);
        StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_DRIVER_RESTORE, new Object[0]);
    }

    public /* synthetic */ void lambda$initView$3$VipSeatActivity(View v) {
        if (this.mSpaceCapsuleViewModel.getPsnVipSeatStatus() == VipSeatStatus.FlatMoving) {
            this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.LayFlatPause);
            StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_PSN_STOP, new Object[0]);
            return;
        }
        this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.LayFlat);
        StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_PSN_FLAT, new Object[0]);
    }

    public /* synthetic */ void lambda$initView$4$VipSeatActivity(View v) {
        if (this.mSpaceCapsuleViewModel.getPsnVipSeatStatus() == VipSeatStatus.RestoreMoving) {
            this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.RestorePause);
            StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_PSN_STOP, new Object[0]);
            return;
        }
        this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.Restore);
        StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_PSN_RESTORE, new Object[0]);
    }

    public /* synthetic */ void lambda$initView$5$VipSeatActivity(VipSeatStatus status) {
        updateVipSeatStatus();
    }

    public /* synthetic */ void lambda$initView$6$VipSeatActivity(VipSeatStatus status) {
        updateVipSeatStatus();
    }

    public /* synthetic */ void lambda$initView$7$VipSeatActivity(Integer integer) {
        onDrvSeatMove();
    }

    public /* synthetic */ void lambda$initView$8$VipSeatActivity(Integer integer) {
        onDrvSeatMove();
    }

    public /* synthetic */ void lambda$initView$9$VipSeatActivity(Integer integer) {
        onDrvSeatMove();
    }

    public /* synthetic */ void lambda$initView$10$VipSeatActivity(Boolean data) {
        updateVipSeatStatus();
    }

    public /* synthetic */ void lambda$initView$11$VipSeatActivity(Boolean data) {
        updateVipSeatStatus();
    }

    public /* synthetic */ void lambda$initView$12$VipSeatActivity(Boolean data) {
        updateVipSeatStatus();
    }

    public /* synthetic */ void lambda$initView$13$VipSeatActivity(Boolean data) {
        updateVipSeatStatus();
    }

    public /* synthetic */ void lambda$initView$14$VipSeatActivity(Boolean data) {
        updateVipSeatStatus();
    }

    private void processIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        boolean booleanExtra = intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_SPACE_MODE_STATUS, false);
        boolean booleanExtra2 = intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_SPACE_EXIT_REASON, false);
        boolean booleanExtra3 = intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_SPACE_SOURCE, false);
        if (!booleanExtra) {
            this.mScenarioViewModel.reportScenarioStatus(ScenarioMode.VipSeat.toScenarioStr(), 0);
            exitSpace(booleanExtra2);
            return;
        }
        this.mScenarioViewModel.registerBinderObserver();
        this.mScenarioViewModel.reportScenarioStatus(ScenarioMode.VipSeat.toScenarioStr(), 2);
        this.mSpaceCapsuleViewModel.onModeStart(true, booleanExtra3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVipSeatStatus() {
        VipSeatStatus drvVipSeatStatus = this.mSpaceCapsuleViewModel.getDrvVipSeatStatus();
        VipSeatStatus psnVipSeatStatus = this.mSpaceCapsuleViewModel.getPsnVipSeatStatus();
        boolean isDrvHeadrestNormal = this.mSeatViewModel.isDrvHeadrestNormal();
        boolean isPsnHeadrestNormal = this.mSeatViewModel.isPsnHeadrestNormal();
        boolean isRearSeatOccupied = isRearSeatOccupied();
        LogUtils.d(TAG, "updateDrvVipSeatStatus: " + drvVipSeatStatus + ", mPsnVipSeatStatus: " + psnVipSeatStatus + ", mDvrHeadrest: " + isDrvHeadrestNormal + ", mPsnHeadrest: " + isPsnHeadrestNormal + ", mRearSeatOccupied: " + isRearSeatOccupied);
        boolean z = true;
        this.mDriverFlatBtn.setEnabled((drvVipSeatStatus == VipSeatStatus.RestoreMoving || this.mSeatViewModel.isDrvSeatLieFlat()) ? false : true);
        if (drvVipSeatStatus == VipSeatStatus.FlatMoving) {
            this.mDriverFlatBtn.setText(R.string.drv_seat_stop_flat);
        } else {
            this.mDriverFlatBtn.setText(R.string.drv_seat_lie_down);
        }
        this.mDriverRecoverBtn.setEnabled((drvVipSeatStatus == VipSeatStatus.FlatWaiting || drvVipSeatStatus == VipSeatStatus.FlatMoving || ((drvVipSeatStatus == VipSeatStatus.Normal || drvVipSeatStatus == VipSeatStatus.FlatPause) && this.mSeatViewModel.isDrvSeatEqualMemory())) ? false : true);
        if (drvVipSeatStatus == VipSeatStatus.RestoreMoving) {
            this.mDriverRecoverBtn.setText(R.string.drv_seat_stop_restore);
        } else {
            this.mDriverRecoverBtn.setText(R.string.drv_seat_recover);
        }
        this.mPsnFlatBtn.setEnabled((psnVipSeatStatus == VipSeatStatus.RestoreMoving || this.mSeatViewModel.isPsnSeatLieFlat()) ? false : true);
        if (psnVipSeatStatus == VipSeatStatus.FlatMoving) {
            this.mPsnFlatBtn.setText(R.string.psn_seat_stop_flat);
        } else {
            this.mPsnFlatBtn.setText(R.string.psn_seat_lie_down);
        }
        XButton xButton = this.mPsnRecoverBtn;
        if (psnVipSeatStatus == VipSeatStatus.FlatWaiting || psnVipSeatStatus == VipSeatStatus.FlatMoving || ((psnVipSeatStatus == VipSeatStatus.Normal || psnVipSeatStatus == VipSeatStatus.FlatPause) && this.mSeatViewModel.isPsnSeatEqualMemory())) {
            z = false;
        }
        xButton.setEnabled(z);
        if (psnVipSeatStatus == VipSeatStatus.RestoreMoving) {
            this.mPsnRecoverBtn.setText(R.string.psn_seat_stop_restore);
        } else {
            this.mPsnRecoverBtn.setText(R.string.psn_seat_recover);
        }
        if (drvVipSeatStatus == VipSeatStatus.FlatWaiting || drvVipSeatStatus == VipSeatStatus.FlatPause || drvVipSeatStatus == VipSeatStatus.RestoreWaiting || drvVipSeatStatus == VipSeatStatus.RestorePause || psnVipSeatStatus == VipSeatStatus.FlatWaiting || psnVipSeatStatus == VipSeatStatus.FlatPause || psnVipSeatStatus == VipSeatStatus.RestoreWaiting || psnVipSeatStatus == VipSeatStatus.RestorePause) {
            if ((drvVipSeatStatus == VipSeatStatus.FlatWaiting || drvVipSeatStatus == VipSeatStatus.FlatPause) && (psnVipSeatStatus == VipSeatStatus.FlatWaiting || psnVipSeatStatus == VipSeatStatus.FlatPause)) {
                if (isDrvHeadrestNormal && isPsnHeadrestNormal) {
                    showTipsImg(this.mHeadImg, R.drawable.img_space_capsule_head_tips);
                    hideTipsImg(this.mDriverHeadImg);
                    hideTipsImg(this.mPsnHeadImg);
                    this.mHeadTipsLayout.setVisibility(0);
                } else if (isDrvHeadrestNormal) {
                    showTipsImg(this.mDriverHeadImg, R.drawable.img_space_capsule_driver_head_tips);
                    hideTipsImg(this.mHeadImg);
                    hideTipsImg(this.mPsnHeadImg);
                    this.mHeadTipsLayout.setVisibility(0);
                } else if (isPsnHeadrestNormal) {
                    showTipsImg(this.mPsnHeadImg, R.drawable.img_space_capsule_psn_head_tips);
                    hideTipsImg(this.mHeadImg);
                    hideTipsImg(this.mDriverHeadImg);
                    this.mHeadTipsLayout.setVisibility(0);
                } else {
                    hideTipsImg(this.mHeadImg);
                    hideTipsImg(this.mDriverHeadImg);
                    hideTipsImg(this.mPsnHeadImg);
                    this.mHeadTipsLayout.setVisibility(8);
                }
            } else if (drvVipSeatStatus == VipSeatStatus.FlatWaiting || drvVipSeatStatus == VipSeatStatus.FlatPause) {
                if (isDrvHeadrestNormal) {
                    showTipsImg(this.mDriverHeadImg, R.drawable.img_space_capsule_driver_head_tips);
                    hideTipsImg(this.mHeadImg);
                    hideTipsImg(this.mPsnHeadImg);
                    this.mHeadTipsLayout.setVisibility(0);
                } else {
                    hideTipsImg(this.mHeadImg);
                    hideTipsImg(this.mDriverHeadImg);
                    hideTipsImg(this.mPsnHeadImg);
                    this.mHeadTipsLayout.setVisibility(8);
                }
            } else if (psnVipSeatStatus != VipSeatStatus.FlatWaiting && psnVipSeatStatus != VipSeatStatus.FlatPause) {
                hideTipsImg(this.mHeadImg);
                hideTipsImg(this.mDriverHeadImg);
                hideTipsImg(this.mPsnHeadImg);
                this.mHeadTipsLayout.setVisibility(8);
            } else if (isPsnHeadrestNormal) {
                showTipsImg(this.mPsnHeadImg, R.drawable.img_space_capsule_psn_head_tips);
                hideTipsImg(this.mHeadImg);
                hideTipsImg(this.mDriverHeadImg);
                this.mHeadTipsLayout.setVisibility(0);
            } else {
                hideTipsImg(this.mHeadImg);
                hideTipsImg(this.mDriverHeadImg);
                hideTipsImg(this.mPsnHeadImg);
                this.mHeadTipsLayout.setVisibility(8);
            }
            if (isRearSeatOccupied) {
                showTipsImg(this.mBackSeatImg, R.drawable.img_space_capsule_back_seat_tips);
                this.mBackSeatTipsLayout.setVisibility(0);
            } else {
                hideTipsImg(this.mBackSeatImg);
                this.mBackSeatTipsLayout.setVisibility(8);
            }
            this.mDescTv.setVisibility(0);
            if (drvVipSeatStatus == VipSeatStatus.FlatWaiting || drvVipSeatStatus == VipSeatStatus.RestoreWaiting || psnVipSeatStatus == VipSeatStatus.FlatWaiting || psnVipSeatStatus == VipSeatStatus.RestoreWaiting) {
                this.mDescTv.setText(R.string.space_capsule_prepare_summary);
                return;
            } else {
                this.mDescTv.setText(R.string.space_capsule_prepare_tips);
                return;
            }
        }
        if (drvVipSeatStatus == VipSeatStatus.FlatMoving || psnVipSeatStatus == VipSeatStatus.FlatMoving || drvVipSeatStatus == VipSeatStatus.RestoreMoving || psnVipSeatStatus == VipSeatStatus.RestoreMoving) {
            this.mDescTv.setVisibility(0);
            this.mDescTv.setText(R.string.space_capsule_prepare_tips);
        } else {
            this.mDescTv.setVisibility(8);
        }
        hideTipsImg(this.mHeadImg);
        hideTipsImg(this.mDriverHeadImg);
        hideTipsImg(this.mPsnHeadImg);
        hideTipsImg(this.mBackSeatImg);
        this.mHeadTipsLayout.setVisibility(8);
        this.mBackSeatTipsLayout.setVisibility(8);
    }

    private void onDrvSeatMove() {
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, 1000L);
    }

    protected boolean isRearSeatOccupied() {
        boolean z = this.mSeatViewModel.isRlSeatOccupied() || this.mSeatViewModel.isRmSeatOccupied() || this.mSeatViewModel.isRrSeatOccupied();
        LogUtils.d(TAG, "isRearSeatOccupied : " + z);
        return z;
    }

    private void showTipsImg(XImageView img, int imgId) {
        if (imgId == 0 || img == null) {
            return;
        }
        try {
            FrameSequenceUtil.destroy(img);
            FrameSequenceUtil.with(img).resourceId(imgId).decodingThreadId(0).loopBehavior(3).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideTipsImg(XImageView img) {
        if (img != null) {
            try {
                FrameSequenceUtil.destroy(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startToExit() {
        if (isFinishing()) {
            return;
        }
        XActivityUtils.finish(this);
    }

    private void exitSpace(boolean notGearP) {
        if (isFinishing()) {
            return;
        }
        XActivityUtils.finish(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
        SoundHelper.stopAllSound();
        this.mSpaceCapsuleViewModel.setCurrentSubMode(-1);
        this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.Stop);
        this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.Stop);
        if (!isFinishing()) {
            XActivityUtils.finish(this);
        }
        this.mScenarioViewModel.startScenario(false, ScenarioMode.VipSeat);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
    }

    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (XThemeManager.isThemeChanged(newConfig)) {
            updateVipSeatStatus();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        liveData.observe(this, observer);
    }
}
