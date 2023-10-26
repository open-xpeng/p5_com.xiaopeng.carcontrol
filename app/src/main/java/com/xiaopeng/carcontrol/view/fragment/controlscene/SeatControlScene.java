package com.xiaopeng.carcontrol.view.fragment.controlscene;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.rastermill.FrameSequenceUtil;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.SmartSeatUtil;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.view.BaseSeatControlView;
import com.xiaopeng.carcontrol.view.fragment.controlscene.SeatControlScene;
import com.xiaopeng.carcontrol.view.scene.AbstractScene;
import com.xiaopeng.carcontrol.view.widget.SeatControlView;
import com.xiaopeng.carcontrol.view.widget.SmartChairDialog;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.account.AccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioMode;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XSegmented;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class SeatControlScene extends AbstractScene {
    private TextView mAccountGroupTip;
    private AccountViewModel mAccountVm;
    private XTextView mAdjustTip;
    private XButton mBtnSeatMemory;
    private XButton mBtnSeatMemoryNew;
    private XButton mBtnSeatRecover;
    private CountDownTimer mCountDownTimer;
    private SeatControlView mDrvControlView;
    private XTextView mDrvSeatTip;
    private String mGroupName;
    private boolean mIsDrvSelected;
    private boolean mIsLogin;
    protected Handler mMainHandler;
    private SeatControlView mPsnControlView;
    private XSegmented mPsnSeg;
    private XTextView mPsnTip;
    private XImageView mPsnTipImg;
    private ViewGroup mSeatSaveContainer;
    private SeatViewModel mSeatVm;
    private final Runnable mShowBtnRunnable;
    private final Runnable mShowPsnBtnRunnable;
    private XTabLayout mSideTab;
    private SmartChairDialog mSmartChairDialog;
    private SmartSeatUtil mSmartSeatUtil;
    private VcuViewModel mVcuVm;
    private long psnTipDelayTime;

    private int getSeatUnmovableMsg(int seatMovableStatus) {
        if (seatMovableStatus != 1) {
            if (seatMovableStatus != 2) {
                if (seatMovableStatus != 3) {
                    return 0;
                }
                return R.string.smart_chair_driver_error;
            }
            return R.string.smart_chair_door_error;
        }
        return R.string.smart_chair_speed_error;
    }

    protected void onShowTimeUpdate(long millisUntilFinished) {
    }

    public SeatControlScene(String sceneId, View parent, LifecycleOwner owner) {
        super(sceneId, parent, owner);
        this.mSmartSeatUtil = null;
        this.mShowBtnRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$nkxzvp9m0TqBH6nyaz_j5LvqhXo
            @Override // java.lang.Runnable
            public final void run() {
                SeatControlScene.this.compareSeatPosition();
            }
        };
        this.mShowPsnBtnRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$CpZRJT_M0yWnyJva83uBmWHuWs4
            @Override // java.lang.Runnable
            public final void run() {
                SeatControlScene.this.compareSeatPsnPosition();
            }
        };
        this.mIsDrvSelected = true;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.psnTipDelayTime = UILooperObserver.ANR_TRIGGER_TIME;
    }

    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    protected void initViewModels() {
        this.mSeatVm = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        this.mAccountVm = (AccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class);
        this.mVcuVm = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    public void onResume() {
        super.onResume();
        if (CarBaseConfig.getInstance().isSupportSeatCtrl()) {
            this.mSeatVm.setSeatUiCtrlResume(true);
            initLogin(Boolean.valueOf(this.mAccountVm.checkLogin()));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    public void onPause() {
        super.onPause();
        Handler handler = this.mMainHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mShowBtnRunnable);
        }
        this.mSeatVm.setSeatUiCtrlResume(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    public void onDestroy() {
        super.onDestroy();
        Handler handler = this.mMainHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    protected void initViews() {
        if (CarBaseConfig.getInstance().isSupportSeatCtrl()) {
            this.mPsnTip = (XTextView) findViewById(R.id.seat_psn_tip);
            this.mPsnTipImg = (XImageView) findViewById(R.id.img_psn_tip);
            XButton xButton = (XButton) findViewById(R.id.seat_auto_control);
            if (CarBaseConfig.getInstance().isSupportSeatSmartControl()) {
                xButton.setVisibility(0);
                VuiUtils.addHasFeedbackProp(xButton);
                xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$2KRX36VgnjKFjXOMv1qtbzPG_O8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SeatControlScene.this.lambda$initViews$0$SeatControlScene(view);
                    }
                });
            }
            this.mAccountGroupTip = (TextView) findViewById(R.id.account_group_tip);
            if (CarBaseConfig.getInstance().isSupportVipSeat()) {
                XButton xButton2 = (XButton) findViewById(R.id.vip_seat_btn);
                xButton2.setVisibility(0);
                VuiUtils.addHasFeedbackProp(xButton2);
                xButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$2DQ0WfzsvjrBA6aS9-h8cymVzP8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SeatControlScene.this.lambda$initViews$1$SeatControlScene(view);
                    }
                });
            }
            this.mSideTab = (XTabLayout) findViewById(R.id.side_tab);
            if (CarBaseConfig.getInstance().isSupportMsmP()) {
                this.mSideTab.setVisibility(0);
                this.mSideTab.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.SeatControlScene.1
                    @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                    public void onTabChangeEnd(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
                        if (index == 0) {
                            SeatControlScene.this.mMainHandler.removeCallbacks(SeatControlScene.this.mShowPsnBtnRunnable);
                            SeatControlScene.this.hiddenMemoryRestoreBtn();
                            SeatControlScene.this.mPsnSeg.setVisibility(8);
                            SeatControlScene.this.mPsnTip.setVisibility(4);
                            SeatControlScene.this.mPsnTipImg.setVisibility(4);
                            SeatControlScene.this.mIsDrvSelected = true;
                            SeatControlScene.this.mPsnControlView.animate().alpha(0.0f);
                            SeatControlScene.this.mDrvControlView.animate().alpha(1.0f);
                            SeatControlScene.this.stopSeatControl();
                            SeatControlScene.this.compareSeatPosition();
                            SeatControlScene.this.enablePanel();
                            SeatControlScene.this.mAccountGroupTip.setText((!SeatControlScene.this.mIsLogin || TextUtils.isEmpty(SeatControlScene.this.mGroupName)) ? ResUtils.getString(R.string.seat_summary_without_login) : ResUtils.getString(R.string.seat_summary_format, SeatControlScene.this.mGroupName));
                        } else if (index != 1) {
                        } else {
                            SeatControlScene.this.mMainHandler.removeCallbacks(SeatControlScene.this.mShowBtnRunnable);
                            SeatControlScene.this.mDrvSeatTip.setVisibility(4);
                            SeatControlScene.this.hiddenMemoryRestoreBtn();
                            SeatControlScene.this.mPsnSeg.setVisibility(0);
                            SeatControlScene.this.mPsnSeg.setSelection(SeatControlScene.this.mSeatVm.getCurrentSelectedPsnHabit());
                            SeatControlScene.this.mIsDrvSelected = false;
                            SeatControlScene.this.mPsnControlView.animate().alpha(1.0f);
                            SeatControlScene.this.mDrvControlView.animate().alpha(0.0f);
                            SeatControlScene.this.stopSeatControl();
                            SeatControlScene.this.mAccountGroupTip.setText(ResUtils.getString(R.string.seat_summary_without_login));
                            SeatControlScene.this.showPsnBtnHandle();
                        }
                    }
                });
            }
            SeatControlView seatControlView = (SeatControlView) findViewById(R.id.drv_seat_view);
            this.mDrvControlView = seatControlView;
            seatControlView.setDriver(true);
            this.mDrvControlView.setControlModeChair();
            this.mDrvControlView.setSupportMove(true);
            this.mDrvControlView.setTiltPosition(this.mSeatVm.getDSeatTiltPos());
            this.mDrvControlView.setHorPosition(this.mSeatVm.getDSeatHorzPos());
            this.mDrvControlView.setVerPosition(this.mSeatVm.getDSeatVerPos());
            this.mDrvControlView.setOnButtonClickListener(new BaseSeatControlView.OnButtonClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.SeatControlScene.2
                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onLegTouched(boolean isTouch, int direction) {
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onLumbarHorTouched(boolean isTouch, int direction) {
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onLumbarVerTouched(boolean isTouch, int direction) {
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onSeatVerTouched(boolean isTouch, int direction) {
                    if (!isTouch) {
                        SeatControlScene.this.mSeatVm.controlDriverSeatEnd(2);
                        return;
                    }
                    StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, direction == 1 ? BtnEnum.DRIVER_SEAT_UP_BTN : BtnEnum.DRIVER_SEAT_DOWN_BTN, new Object[0]);
                    SeatControlScene.this.mSeatVm.controlDriverSeatStart(2, direction);
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onSeatHorTouched(boolean isTouch, int direction) {
                    if (!isTouch) {
                        SeatControlScene.this.mSeatVm.controlDriverSeatEnd(1);
                        return;
                    }
                    StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, direction == 1 ? BtnEnum.DRIVER_SEAT_FRONT_BTN : BtnEnum.DRIVER_SEAT_REAR_BTN, new Object[0]);
                    SeatControlScene.this.mSeatVm.controlDriverSeatStart(1, direction);
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onTiltTouched(boolean isTouch, int direction) {
                    if (!isTouch) {
                        SeatControlScene.this.mSeatVm.controlDriverSeatEnd(3);
                        return;
                    }
                    StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, direction == 1 ? BtnEnum.DRIVER_SEAT_TILT_FRONT_BTN : BtnEnum.DRIVER_SEAT_TILT_REAR_BTN, new Object[0]);
                    SeatControlScene.this.mSeatVm.controlDriverSeatStart(3, direction);
                }
            });
            this.mDrvControlView.setOnButtonTouchListener(new BaseSeatControlView.OnButtonTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.SeatControlScene.3
                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonTouchListener
                public void onButtonTouchDown() {
                    SeatControlScene.this.disablePanel();
                    ViewParent parent = SeatControlScene.this.getContent().getParent();
                    if (parent instanceof MainScrollView) {
                        ((MainScrollView) parent).setScrollable(false);
                    }
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonTouchListener
                public void onButtonTouchUp() {
                    SeatControlScene.this.enablePanel();
                    ViewParent parent = SeatControlScene.this.getContent().getParent();
                    if (parent instanceof MainScrollView) {
                        ((MainScrollView) parent).setScrollable(true);
                    }
                }
            });
            SeatControlView seatControlView2 = (SeatControlView) findViewById(R.id.psn_seat_view);
            this.mPsnControlView = seatControlView2;
            seatControlView2.setDriver(false);
            this.mPsnControlView.setControlModeChair();
            this.mPsnControlView.setSupportMove(true);
            this.mPsnControlView.setHorPosition(this.mSeatVm.getPSeatHorzPos());
            this.mPsnControlView.setVerPosition(this.mSeatVm.getPSeatVerPos());
            this.mPsnControlView.setTiltPosition(this.mSeatVm.getPSeatTiltPos());
            this.mPsnControlView.setOnButtonClickListener(new BaseSeatControlView.OnButtonClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.SeatControlScene.4
                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onLegTouched(boolean isTouch, int direction) {
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onLumbarHorTouched(boolean isTouch, int direction) {
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onLumbarVerTouched(boolean isTouch, int direction) {
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onSeatVerTouched(boolean isTouch, int direction) {
                    if (isTouch) {
                        SeatControlScene.this.mSeatVm.controlPsnSeatStart(2, direction);
                    } else {
                        SeatControlScene.this.mSeatVm.controlPsnSeatEnd(2);
                    }
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onSeatHorTouched(boolean isTouch, int direction) {
                    if (isTouch) {
                        SeatControlScene.this.mSeatVm.controlPsnSeatStart(1, direction);
                    } else {
                        SeatControlScene.this.mSeatVm.controlPsnSeatEnd(1);
                    }
                }

                @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView.OnButtonClickListener
                public void onTiltTouched(boolean isTouch, int direction) {
                    if (isTouch) {
                        SeatControlScene.this.mSeatVm.controlPsnSeatStart(3, direction);
                    } else {
                        SeatControlScene.this.mSeatVm.controlPsnSeatEnd(3);
                    }
                }
            });
            final boolean isSupportUserPortfolioPage = CarBaseConfig.getInstance().isSupportUserPortfolioPage();
            XTextView xTextView = (XTextView) findViewById(R.id.seat_drv_tip);
            this.mDrvSeatTip = xTextView;
            if (!isSupportUserPortfolioPage) {
                xTextView.setVisibility(4);
            }
            this.mAdjustTip = (XTextView) findViewById(R.id.seat_adjust_tip);
            this.mSeatSaveContainer = (ViewGroup) findViewById(R.id.save_container);
            XButton xButton3 = (XButton) findViewById(R.id.btn_seat_recover);
            this.mBtnSeatRecover = xButton3;
            VuiUtils.addHasFeedbackProp(xButton3);
            this.mBtnSeatRecover.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$8PbSttRMMoTMkwgDXsH_dBbV7s4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SeatControlScene.this.lambda$initViews$2$SeatControlScene(isSupportUserPortfolioPage, view);
                }
            });
            XButton xButton4 = (XButton) findViewById(R.id.btn_seat_memory);
            this.mBtnSeatMemory = xButton4;
            xButton4.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$34Sjw7ijMeGepL3anLolftPDwIU
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SeatControlScene.this.lambda$initViews$3$SeatControlScene(isSupportUserPortfolioPage, view);
                }
            });
            XButton xButton5 = (XButton) findViewById(R.id.btn_seat_memory_new);
            this.mBtnSeatMemoryNew = xButton5;
            xButton5.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$Vry8ZBKU99gLw6i7lBKYACxliU4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SeatControlScene.this.lambda$initViews$4$SeatControlScene(isSupportUserPortfolioPage, view);
                }
            });
            XSegmented xSegmented = (XSegmented) findViewById(R.id.seg_psn_position);
            this.mPsnSeg = xSegmented;
            xSegmented.setSegmentListener(new XSegmented.SegmentListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.SeatControlScene.5
                @Override // com.xiaopeng.xui.widget.XSegmented.SegmentListener
                public boolean onInterceptChange(XSegmented xSegmented2, int i) {
                    return false;
                }

                @Override // com.xiaopeng.xui.widget.XSegmented.SegmentListener
                public void onSelectionChange(XSegmented xSegmented2, int i, boolean b) {
                    if (!b) {
                        LogUtils.d(SeatControlScene.this.TAG, "from user:" + b, false);
                        return;
                    }
                    SeatControlScene.this.mSeatSaveContainer.setVisibility(4);
                    SeatControlScene.this.mPsnTip.setVisibility(4);
                    SeatControlScene.this.mPsnTipImg.setVisibility(4);
                    SeatControlScene.this.disablePanel();
                    SeatControlScene.this.mSeatVm.restorePsnSelectSeatPos(i, true);
                }
            });
            setDataObserver();
            initLogin(Boolean.valueOf(this.mAccountVm.checkLogin()));
        }
    }

    public /* synthetic */ void lambda$initViews$0$SeatControlScene(View v) {
        if (ClickHelper.isFastClick()) {
            return;
        }
        int checkChairMovable = checkChairMovable();
        if (checkChairMovable == 0) {
            showSmartChairDialog();
            StatisticUtils.sendStatistic(PageEnum.CONTROL_PAGE, BtnEnum.SMART_ADJUST_BTN, new Object[0]);
            return;
        }
        int seatUnmovableMsg = getSeatUnmovableMsg(checkChairMovable);
        if (seatUnmovableMsg != 0) {
            NotificationHelper.getInstance().showToast(seatUnmovableMsg);
            if ((v instanceof VuiView) && ((VuiView) v).isPerformVuiAction()) {
                vuiFeedbackClick(seatUnmovableMsg, v);
            }
        }
    }

    public /* synthetic */ void lambda$initViews$1$SeatControlScene(View v) {
        if (isVuiAction(v) && this.mVcuVm.getGearLevelValue() != GearLevel.P) {
            vuiFeedback(R.string.vip_seat_notice_gear_p, v);
        }
        ((ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class)).startScenario(true, ScenarioMode.VipSeat);
    }

    public /* synthetic */ void lambda$initViews$2$SeatControlScene(final boolean isSupportUserPortfolioPage, View v) {
        int checkChairMovable = checkChairMovable();
        boolean z = this.mIsDrvSelected;
        if (!z) {
            this.mPsnTip.setText(ResUtils.getString(R.string.seat_psn_resuming));
            this.mPsnTip.setVisibility(0);
            this.mPsnTipImg.setVisibility(4);
            startAutoCloseTask(this.psnTipDelayTime);
            if (this.mSeatVm.restorePsnSelectSeatPos(this.mPsnSeg.getSelection(), false)) {
                hiddenMemoryRestoreBtn();
            }
        } else if (checkChairMovable == 0) {
            if (z && this.mSeatVm.restoreDrvSeatPos()) {
                hiddenMemoryRestoreBtn();
                NotificationHelper.getInstance().showToast(R.string.restore_drv_seat);
                if (isSupportUserPortfolioPage) {
                    this.mDrvSeatTip.setVisibility(0);
                } else {
                    this.mDrvSeatTip.setVisibility(4);
                }
            }
        } else {
            int seatUnmovableMsg = getSeatUnmovableMsg(checkChairMovable);
            if (seatUnmovableMsg != 0) {
                NotificationHelper.getInstance().showToast(seatUnmovableMsg);
                if ((v instanceof VuiView) && ((VuiView) v).isPerformVuiAction()) {
                    vuiFeedbackClick(seatUnmovableMsg, v);
                }
            }
        }
    }

    public /* synthetic */ void lambda$initViews$3$SeatControlScene(final boolean isSupportUserPortfolioPage, View v) {
        if (this.mIsDrvSelected && this.mSeatVm.memoryDrvSeatData()) {
            hiddenMemoryRestoreBtn();
            if (isSupportUserPortfolioPage) {
                this.mDrvSeatTip.setVisibility(0);
            } else {
                this.mDrvSeatTip.setVisibility(4);
            }
        }
        if (this.mIsDrvSelected) {
            return;
        }
        this.mPsnTip.setText(ResUtils.getString(R.string.seat_psn_saved, "位置" + convertPsnHabitId(this.mPsnSeg.getSelection())));
        this.mPsnTip.setVisibility(0);
        this.mPsnTipImg.setVisibility(0);
        this.mPsnTipImg.setImageResource(R.drawable.ic_finish);
        startAutoCloseTask(this.psnTipDelayTime);
        if (this.mSeatVm.saveSelectPsnSeatData(this.mPsnSeg.getSelection())) {
            hiddenMemoryRestoreBtn();
        }
    }

    public /* synthetic */ void lambda$initViews$4$SeatControlScene(final boolean isSupportUserPortfolioPage, View v) {
        this.mAccountVm.requestCreateNewSyncGroup();
        hiddenMemoryRestoreBtn();
        if (isSupportUserPortfolioPage) {
            this.mDrvSeatTip.setVisibility(0);
        } else {
            this.mDrvSeatTip.setVisibility(4);
        }
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

    private String convertPsnHabitId(int id) {
        if (id != 0) {
            if (id != 1) {
                return id != 2 ? "" : ResUtils.getString(R.string.psn_three);
            }
            return ResUtils.getString(R.string.psn_two);
        }
        return ResUtils.getString(R.string.psn_one);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopSeatControl() {
        LogUtils.d(this.TAG, "stopSeatControl", false);
        SeatControlView seatControlView = this.mDrvControlView;
        if (seatControlView == null || this.mPsnControlView == null) {
            return;
        }
        seatControlView.stopControl();
        this.mPsnControlView.stopControl();
    }

    private int checkChairMovable() {
        if (this.mVcuVm.getCarSpeed() >= 3.0f) {
            return 1;
        }
        if (this.mSeatVm.isDrvDoorOpened()) {
            return 2;
        }
        return !this.mSeatVm.isDrvSeatOccupied() ? 3 : 0;
    }

    private void setDataObserver() {
        setLdo(this.mSeatVm.getDriverSeatData(3), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$Wf3GZlYeKxXXHBmfJwqhrbm4vDM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatControlScene.this.lambda$setDataObserver$5$SeatControlScene((Integer) obj);
            }
        });
        setLdo(this.mSeatVm.getDriverSeatData(1), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$MqVE0_-SSLaXjY7RsqojkZ41jXI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatControlScene.this.lambda$setDataObserver$6$SeatControlScene((Integer) obj);
            }
        });
        setLdo(this.mSeatVm.getDriverSeatData(2), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$VWtADhKe_gtosKn0deIoccU4Cic
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatControlScene.this.lambda$setDataObserver$7$SeatControlScene((Integer) obj);
            }
        });
        setLdo(this.mSeatVm.getPsnSeatHorzData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$M1jEOUHGF1FU6wUOi-ef8jBIq2k
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatControlScene.this.lambda$setDataObserver$8$SeatControlScene((Integer) obj);
            }
        });
        setLdo(this.mSeatVm.getPsnSeatTiltData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$DdffLmcZ5w_gGel9gv1DK1cNmlU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatControlScene.this.lambda$setDataObserver$9$SeatControlScene((Integer) obj);
            }
        });
        setLdo(this.mAccountVm.getIsLogin(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$j4NN4QxKMKesee27p-H7VnR07pY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatControlScene.this.initLogin((Boolean) obj);
            }
        });
        setLdo(this.mAccountVm.getCreateSyncGroupData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$1vPHkg_cLwCthyREUyfiTRQZzXM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatControlScene.this.lambda$setDataObserver$10$SeatControlScene((Integer) obj);
            }
        });
        setLdo(this.mSeatVm.getInPsnSelectProcessData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$da5PHTiTxLXVpx7pl9B8Ir5N-8Y
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatControlScene.this.lambda$setDataObserver$11$SeatControlScene((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setDataObserver$5$SeatControlScene(Integer integer) {
        this.mDrvControlView.setTiltPosition(integer.intValue());
        if (this.mIsDrvSelected) {
            showBtnHandle();
        }
    }

    public /* synthetic */ void lambda$setDataObserver$6$SeatControlScene(Integer integer) {
        this.mDrvControlView.setHorPosition(integer.intValue());
        if (this.mIsDrvSelected) {
            showBtnHandle();
        }
    }

    public /* synthetic */ void lambda$setDataObserver$7$SeatControlScene(Integer integer) {
        this.mDrvControlView.setVerPosition(integer.intValue());
        if (this.mIsDrvSelected) {
            showBtnHandle();
        }
    }

    public /* synthetic */ void lambda$setDataObserver$8$SeatControlScene(Integer pos) {
        this.mPsnControlView.setHorPosition(pos.intValue());
        if (this.mIsDrvSelected) {
            return;
        }
        showPsnBtnHandle();
    }

    public /* synthetic */ void lambda$setDataObserver$9$SeatControlScene(Integer pos) {
        this.mPsnControlView.setTiltPosition(pos.intValue());
        if (this.mIsDrvSelected) {
            return;
        }
        showPsnBtnHandle();
    }

    public /* synthetic */ void lambda$setDataObserver$10$SeatControlScene(Integer result) {
        if (result.intValue() == 2) {
            initLogin(Boolean.valueOf(this.mAccountVm.checkLogin()));
        }
    }

    public /* synthetic */ void lambda$setDataObserver$11$SeatControlScene(Boolean inPsnSelectProcess) {
        Handler handler;
        if (!inPsnSelectProcess.booleanValue() || (handler = this.mMainHandler) == null) {
            return;
        }
        handler.removeCallbacks(this.mShowPsnBtnRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initLogin(Boolean isLogin) {
        String string;
        if (isLogin == null) {
            return;
        }
        boolean booleanValue = isLogin.booleanValue();
        this.mIsLogin = booleanValue;
        if (booleanValue) {
            this.mGroupName = this.mAccountVm.getCurrentSyncGroupName();
        }
        TextView textView = this.mAccountGroupTip;
        if (this.mIsLogin && !TextUtils.isEmpty(this.mGroupName)) {
            string = ResUtils.getString(R.string.seat_summary_format, this.mGroupName);
        } else {
            string = ResUtils.getString(R.string.seat_summary_without_login);
        }
        textView.setText(string);
        this.mBtnSeatMemoryNew.setVisibility(this.mIsLogin ? 0 : 8);
    }

    private void showSmartChairDialog() {
        if (this.mSmartSeatUtil == null) {
            this.mSmartSeatUtil = new SmartSeatUtil();
        }
        SmartChairDialog smartChairDialog = this.mSmartChairDialog;
        if (smartChairDialog != null) {
            smartChairDialog.dismiss();
        } else {
            SmartChairDialog smartChairDialog2 = new SmartChairDialog(getContext());
            this.mSmartChairDialog = smartChairDialog2;
            smartChairDialog2.setTitle(R.string.smart_chair_title);
            this.mSmartChairDialog.setPositiveButton(R.string.chair_dialog_yes, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$TSeyDKZIUH36KlbfVdmi2jshH2k
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    SeatControlScene.this.lambda$showSmartChairDialog$12$SeatControlScene(xDialog, i);
                }
            });
            this.mSmartChairDialog.setNegativeButton(R.string.chair_dialog_cancle, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$iPjV1UZwxWqPr3mYzbkzjheMTfU
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    SeatControlScene.this.lambda$showSmartChairDialog$13$SeatControlScene(xDialog, i);
                }
            });
        }
        this.mSmartChairDialog.setDefaultValue(FunctionModel.getInstance().getDriverGender(), FunctionModel.getInstance().getDriverHeight(), FunctionModel.getInstance().getDriverWeight());
        this.mSmartChairDialog.show();
        VuiManager.instance().initVuiDialog(this.mSmartChairDialog, getContext(), VuiManager.SCENE_SEAT_SMART_CONTROL);
    }

    public /* synthetic */ void lambda$showSmartChairDialog$12$SeatControlScene(XDialog xDialog, int i) {
        onClickSaveSmartAdjust();
    }

    public /* synthetic */ void lambda$showSmartChairDialog$13$SeatControlScene(XDialog xDialog, int i) {
        this.mSmartChairDialog.dismiss();
    }

    private void onClickSaveSmartAdjust() {
        LogUtils.d(this.TAG, " onClickSaveSmartAdjust");
        int[] position = this.mSmartChairDialog.getPosition();
        int i = position[0];
        int i2 = position[1];
        int i3 = position[2];
        try {
            int[] position2 = this.mSmartSeatUtil.getPosition(i, i2, i3);
            if (position2.length >= 3) {
                LogUtils.d(this.TAG, "SmartChairDialog pos: " + position2[0] + "," + position2[1] + "," + position2[2]);
                FunctionModel.getInstance().setSmartSeatParmData(i, i2, i3);
                this.mSeatVm.setDrvSeatAllPositions(position2[0], position2[1], position2[2], 0, 0);
            }
        } catch (Exception e) {
            LogUtils.e(this.TAG, "smartSeatUtil get position crash");
            e.printStackTrace();
        }
        this.mSmartChairDialog.dismiss();
    }

    private synchronized void showBtnHandle() {
        this.mMainHandler.removeCallbacks(this.mShowBtnRunnable);
        this.mMainHandler.postDelayed(this.mShowBtnRunnable, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void compareSeatPosition() {
        String string;
        boolean isSupportUserPortfolioPage = CarBaseConfig.getInstance().isSupportUserPortfolioPage();
        int i = 4;
        if (this.mSideTab.getSelectedTabIndex() == 0) {
            boolean isDrvSeatEqualMemory = this.mSeatVm.isDrvSeatEqualMemory();
            XTextView xTextView = this.mDrvSeatTip;
            if (isDrvSeatEqualMemory && isSupportUserPortfolioPage) {
                i = 0;
            }
            xTextView.setVisibility(i);
            if (!this.mSeatVm.isDrvHeadrestNormal()) {
                NotificationHelper.getInstance().showToast(R.string.seat_save_headrest_notice);
            }
            if (isDrvSeatEqualMemory || !this.mSeatVm.isDrvHeadrestNormal()) {
                hiddenMemoryRestoreBtn();
                return;
            }
            enablePanel();
            this.mSeatSaveContainer.setVisibility(0);
            this.mBtnSeatMemoryNew.setVisibility(0);
            XTextView xTextView2 = this.mAdjustTip;
            if (this.mIsLogin && !TextUtils.isEmpty(this.mGroupName)) {
                string = ResUtils.getString(R.string.seat_control_scene_adjust_tip_format, this.mGroupName);
            } else {
                string = ResUtils.getString(R.string.seat_control_scene_adjust_tip);
            }
            xTextView2.setText(string);
            this.mAdjustTip.setVisibility(0);
            return;
        }
        this.mDrvSeatTip.setVisibility(4);
        hiddenMemoryRestoreBtn();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void showPsnBtnHandle() {
        this.mMainHandler.removeCallbacks(this.mShowPsnBtnRunnable);
        this.mMainHandler.postDelayed(this.mShowPsnBtnRunnable, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void compareSeatPsnPosition() {
        this.mPsnTipImg.setVisibility(4);
        if (!this.mIsDrvSelected && !this.mSeatVm.isPsnSeatEqualMemory()) {
            this.mBtnSeatMemoryNew.setVisibility(8);
            this.mSeatSaveContainer.setVisibility(0);
            psnEnablePanel();
            this.mPsnTip.setText(ResUtils.getString(R.string.seat_mirror_ctrl_title, "位置" + convertPsnHabitId(this.mSeatVm.getCurrentSelectedPsnHabit())));
            this.mPsnTip.setVisibility(0);
            return;
        }
        this.mPsnTip.setVisibility(4);
        this.mSeatSaveContainer.setVisibility(4);
        disablePanel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hiddenMemoryRestoreBtn() {
        this.mSeatSaveContainer.setVisibility(4);
        this.mAdjustTip.setVisibility(4);
        disablePanel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disablePanel() {
        this.mBtnSeatMemory.setClickable(false);
        this.mBtnSeatRecover.setClickable(false);
        this.mBtnSeatMemoryNew.setClickable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enablePanel() {
        this.mBtnSeatMemory.setClickable(true);
        this.mBtnSeatRecover.setClickable(true);
        this.mBtnSeatMemoryNew.setClickable(true);
    }

    private void psnEnablePanel() {
        this.mBtnSeatMemory.setClickable(true);
        this.mBtnSeatRecover.setClickable(true);
    }

    protected final void startAutoCloseTask(long delay) {
        LogUtils.d(this.TAG, "startAutoCloseTask, delay: " + delay, false);
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        AnonymousClass6 anonymousClass6 = new AnonymousClass6(delay, 1000L);
        this.mCountDownTimer = anonymousClass6;
        anonymousClass6.start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.fragment.controlscene.SeatControlScene$6  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass6 extends CountDownTimer {
        AnonymousClass6(long x0, long x1) {
            super(x0, x1);
        }

        public /* synthetic */ void lambda$onTick$0$SeatControlScene$6(final long millisUntilFinished) {
            SeatControlScene.this.onShowTimeUpdate(millisUntilFinished);
        }

        @Override // android.os.CountDownTimer
        public void onTick(final long millisUntilFinished) {
            SeatControlScene.this.mMainHandler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.controlscene.-$$Lambda$SeatControlScene$6$6LwghwBEV0bnn9WSy-hGqW-fozU
                @Override // java.lang.Runnable
                public final void run() {
                    SeatControlScene.AnonymousClass6.this.lambda$onTick$0$SeatControlScene$6(millisUntilFinished);
                }
            });
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            SeatControlScene.this.onDismiss();
        }
    }

    protected void onDismiss() {
        this.mPsnTip.setVisibility(4);
        this.mPsnTipImg.setVisibility(4);
    }
}
