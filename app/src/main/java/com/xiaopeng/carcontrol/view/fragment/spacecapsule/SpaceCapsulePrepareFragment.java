package com.xiaopeng.carcontrol.view.fragment.spacecapsule;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.rastermill.FrameSequenceUtil;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.VipSeatAct;
import com.xiaopeng.carcontrol.viewmodel.space.VipSeatStatus;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class SpaceCapsulePrepareFragment extends SpaceBaseFragment {
    private static final int MSG_WHAT_DRV_MOVE = 1;
    private static final String TAG = "SpaceCapsulePrepareFragment";
    private static final long TITLE_ANIM_SWITCH_TIME = 500;
    private String[] animStrArray;
    private boolean isResume;
    private boolean isSpaceCapsuleGuide;
    private boolean isVipSeat;
    private XImageView mBackSeatImg;
    private ViewGroup mBackSeatTipsLayout;
    private XImageView mBgImg;
    private XTextView mDescTv;
    private XButton mDriverFlatBtn;
    private XImageView mDriverHeadImg;
    private XButton mDriverRecoverBtn;
    private XDialog mEnterInterruptDialog;
    private XDialog mExitInterruptDialog;
    private XImageView mHeadImg;
    private ViewGroup mHeadTipsLayout;
    private XDialog mLowBatteryDialog;
    private boolean mLowBatteryTips;
    private int mModeIndex;
    private XButton mPsnFlatBtn;
    private XImageView mPsnHeadImg;
    private XButton mPsnRecoverBtn;
    private XImageView mSeatMoveImg;
    private SeatViewModel mSeatViewModel;
    private boolean mSingleSleepMode;
    private SpaceCapsuleViewModel mSpaceCapsuleViewModel;
    private ISpaceCapsuleInterface mSpaceListener;
    private XButton mSpacePrepareControlBtn;
    private boolean mStartSpace;
    private XTextView mTitleAnimTv;
    private XTextView mTitleTv;
    private VcuViewModel mVcuViewModel;
    private int mAnimCount = 0;
    private final Handler mHandler = new Handler(ThreadUtils.getHandler(1).getLooper()) { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceCapsulePrepareFragment.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg == null || msg.what != 1) {
                return;
            }
            SpaceCapsulePrepareFragment.this.updateVipSeatStatus();
        }
    };
    private final Runnable titleAnimRun = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceCapsulePrepareFragment.2
        @Override // java.lang.Runnable
        public void run() {
            SpaceCapsulePrepareFragment.this.mTitleAnimTv.setText(SpaceCapsulePrepareFragment.this.animStrArray[SpaceCapsulePrepareFragment.access$108(SpaceCapsulePrepareFragment.this) % 4]);
            SpaceCapsulePrepareFragment.this.mHandler.postDelayed(this, SpaceCapsulePrepareFragment.TITLE_ANIM_SWITCH_TIME);
        }
    };

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_space_prepare;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    protected void initViewModelObserver() {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    public void setClickSelected(int position) {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    protected boolean supportVui() {
        return false;
    }

    static /* synthetic */ int access$108(SpaceCapsulePrepareFragment spaceCapsulePrepareFragment) {
        int i = spaceCapsulePrepareFragment.mAnimCount;
        spaceCapsulePrepareFragment.mAnimCount = i + 1;
        return i;
    }

    public static SpaceCapsulePrepareFragment newInstance(boolean vipSeat, boolean start, int index, boolean lowBatteryTips, int sleepBed) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(GlobalConstant.EXTRA.KEY_VIP_SEAT, vipSeat);
        bundle.putBoolean(GlobalConstant.EXTRA.KEY_SPACE_MODE_STATUS, start);
        bundle.putInt(GlobalConstant.EXTRA.KEY_SPACE_SUB_MODE_INDEX, index);
        bundle.putBoolean(GlobalConstant.EXTRA.KEY_LOW_BATTERY_TIPS_STATUS, lowBatteryTips);
        bundle.putInt(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, sleepBed);
        SpaceCapsulePrepareFragment spaceCapsulePrepareFragment = new SpaceCapsulePrepareFragment();
        spaceCapsulePrepareFragment.setArguments(bundle);
        return spaceCapsulePrepareFragment;
    }

    private SpaceCapsulePrepareFragment() {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISpaceCapsuleInterface) {
            this.mSpaceListener = (ISpaceCapsuleInterface) context;
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (supportVui()) {
            initVuiScene();
        }
        initData();
        initView(view);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    protected void initViewModel() {
        this.mSpaceCapsuleViewModel = (SpaceCapsuleViewModel) ViewModelManager.getInstance().getViewModelImpl(ISpaceCapsuleViewModel.class);
        this.mSeatViewModel = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        this.mVcuViewModel = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        dismissDialog();
        hideTipsImg(this.mHeadImg);
        hideTipsImg(this.mDriverHeadImg);
        hideTipsImg(this.mPsnHeadImg);
        hideTipsImg(this.mBackSeatImg);
        hideTipsImg(this.mSeatMoveImg);
    }

    private void initData() {
        int i;
        Bundle arguments = getArguments();
        this.isVipSeat = arguments.getBoolean(GlobalConstant.EXTRA.KEY_VIP_SEAT, false);
        this.mStartSpace = arguments.getBoolean(GlobalConstant.EXTRA.KEY_SPACE_MODE_STATUS, false);
        this.mLowBatteryTips = arguments.getBoolean(GlobalConstant.EXTRA.KEY_LOW_BATTERY_TIPS_STATUS, false);
        this.mModeIndex = arguments.getInt(GlobalConstant.EXTRA.KEY_SPACE_SUB_MODE_INDEX, 1);
        this.isSpaceCapsuleGuide = this.mSpaceCapsuleViewModel.isFirstEnterSpaceCapsule() && !isSpaceSleepMode();
        this.mSingleSleepMode = arguments.getInt(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, 2) == 1;
        LogUtils.d(TAG, "initData, isVipSeat: " + this.isVipSeat + ", isSpaceCapsuleGuide: " + this.isSpaceCapsuleGuide + ", mStartSpace: " + this.mStartSpace + ", mModeIndex: " + this.mModeIndex + ", mSingleSleepMode: " + this.mSingleSleepMode, false);
        this.mAnimCount = 0;
        this.isResume = false;
        this.animStrArray = ResUtils.getStringArray(R.array.space_title_anim_array);
        if (!this.isVipSeat) {
            SpaceCapsuleViewModel spaceCapsuleViewModel = this.mSpaceCapsuleViewModel;
            if (this.mStartSpace) {
                i = isSpaceSleepMode() ? 3 : 5;
            } else {
                i = isSpaceSleepMode() ? 4 : 6;
            }
            spaceCapsuleViewModel.setCurrentSubMode(i);
            return;
        }
        this.mSpaceCapsuleViewModel.setCurrentSubMode(7);
    }

    private void initView(View rootView) {
        rootView.findViewById(R.id.close_iv).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$mImkz1cWb5iQsJdw17_lQ2GWj8A
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpaceCapsulePrepareFragment.this.lambda$initView$0$SpaceCapsulePrepareFragment(view);
            }
        });
        this.mBgImg = (XImageView) rootView.findViewById(R.id.space_capsule_prepare_bg_img);
        this.mTitleTv = (XTextView) rootView.findViewById(R.id.space_capsule_prepare_title);
        this.mTitleAnimTv = (XTextView) rootView.findViewById(R.id.space_capsule_prepare_title_anim);
        this.mDescTv = (XTextView) rootView.findViewById(R.id.space_capsule_prepare_summary);
        XButton xButton = (XButton) rootView.findViewById(R.id.space_capsule_control_btn);
        this.mSpacePrepareControlBtn = xButton;
        xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$4TXqYaFMI7QHe4yKuUF-JOTh98U
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpaceCapsulePrepareFragment.this.lambda$initView$1$SpaceCapsulePrepareFragment(view);
            }
        });
        XButton xButton2 = (XButton) rootView.findViewById(R.id.driver_seat_lie_down_btn);
        this.mDriverFlatBtn = xButton2;
        xButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$PfE6ybkcRkkic96_PXgvhm5Xa_A
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpaceCapsulePrepareFragment.this.lambda$initView$2$SpaceCapsulePrepareFragment(view);
            }
        });
        XButton xButton3 = (XButton) rootView.findViewById(R.id.driver_seat_recover_btn);
        this.mDriverRecoverBtn = xButton3;
        xButton3.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$ezEPGs7Iac0vnniCR4-sXvgkxCA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpaceCapsulePrepareFragment.this.lambda$initView$3$SpaceCapsulePrepareFragment(view);
            }
        });
        XButton xButton4 = (XButton) rootView.findViewById(R.id.psn_seat_lie_down_btn);
        this.mPsnFlatBtn = xButton4;
        xButton4.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$iar6SfKgW5HDu96XjtVRAnwWNdM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpaceCapsulePrepareFragment.this.lambda$initView$4$SpaceCapsulePrepareFragment(view);
            }
        });
        XButton xButton5 = (XButton) rootView.findViewById(R.id.psn_seat_recover_btn);
        this.mPsnRecoverBtn = xButton5;
        xButton5.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$dShRZRGXZBI9EZ6NFjntkmmQ9J4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpaceCapsulePrepareFragment.this.lambda$initView$5$SpaceCapsulePrepareFragment(view);
            }
        });
        this.mDriverHeadImg = (XImageView) rootView.findViewById(R.id.driver_head_tips_img);
        this.mPsnHeadImg = (XImageView) rootView.findViewById(R.id.psn_head_tips_img);
        this.mHeadImg = (XImageView) rootView.findViewById(R.id.head_tips_img);
        this.mBackSeatImg = (XImageView) rootView.findViewById(R.id.back_seat_tips_img);
        this.mSeatMoveImg = (XImageView) rootView.findViewById(R.id.seat_move_tips_img);
        this.mHeadTipsLayout = (ViewGroup) rootView.findViewById(R.id.head_tips_layout);
        this.mBackSeatTipsLayout = (ViewGroup) rootView.findViewById(R.id.back_seat_tips_layout);
        boolean z = this.isVipSeat;
        int i = R.drawable.space_capsule_single_prepare_bg;
        int i2 = R.drawable.space_capsule_prepare_bg;
        if (z) {
            if (this.mSeatViewModel.isDrvSeatLieFlat()) {
                this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.Flat);
            }
            if (this.mSeatViewModel.isPsnSeatLieFlat()) {
                this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.Flat);
            }
            if (this.mSingleSleepMode) {
                this.mBgImg.setImageResource(R.drawable.space_capsule_single_prepare_bg);
            } else {
                this.mBgImg.setImageResource(R.drawable.space_capsule_prepare_bg);
            }
            this.mTitleTv.setText(R.string.vip_seat_title);
            this.mDescTv.setText(R.string.vip_seat_title);
            this.mSpacePrepareControlBtn.setVisibility(8);
            this.mDriverFlatBtn.setVisibility(0);
            this.mDriverRecoverBtn.setVisibility(0);
            this.mPsnFlatBtn.setVisibility(0);
            this.mPsnRecoverBtn.setVisibility(0);
            setLiveDataObserver(this.mSpaceCapsuleViewModel.getVipDrvSeatStatusData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$wCAIP0wp3y2O5TBW8Tm8mypkzkQ
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SpaceCapsulePrepareFragment.this.lambda$initView$6$SpaceCapsulePrepareFragment((VipSeatStatus) obj);
                }
            });
            setLiveDataObserver(this.mSpaceCapsuleViewModel.getVipPsnSeatStatusData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$ncSVrkwk96Hu0s1UUD2dDSMBuCI
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SpaceCapsulePrepareFragment.this.lambda$initView$7$SpaceCapsulePrepareFragment((VipSeatStatus) obj);
                }
            });
            setLiveDataObserver(this.mSeatViewModel.getDriverSeatData(1), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$0Jbhx88nZAseEHp1MNp5fv5xjBw
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SpaceCapsulePrepareFragment.this.lambda$initView$8$SpaceCapsulePrepareFragment((Integer) obj);
                }
            });
            setLiveDataObserver(this.mSeatViewModel.getDriverSeatData(2), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$46HFPnxqJ5oHUvInHIXkYcr86CE
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SpaceCapsulePrepareFragment.this.lambda$initView$9$SpaceCapsulePrepareFragment((Integer) obj);
                }
            });
            setLiveDataObserver(this.mSeatViewModel.getDriverSeatData(3), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$BJZeGyO8t88pTh2tqNG_2XIragQ
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SpaceCapsulePrepareFragment.this.lambda$initView$10$SpaceCapsulePrepareFragment((Integer) obj);
                }
            });
            this.mSeatMoveImg.setVisibility(8);
        } else {
            this.mDriverFlatBtn.setVisibility(8);
            this.mDriverRecoverBtn.setVisibility(8);
            this.mPsnFlatBtn.setVisibility(8);
            this.mPsnRecoverBtn.setVisibility(8);
            if (this.isSpaceCapsuleGuide && !isSpaceSleepMode()) {
                this.mBgImg.setImageResource(R.drawable.space_capsule_start_bg);
                this.mTitleTv.setText(isSpaceSleepMode() ? R.string.space_capsule_sleep_title : R.string.space_capsule_cinema_title);
                this.mSpacePrepareControlBtn.setVisibility(0);
                this.mSpacePrepareControlBtn.setText(R.string.space_capsule_mode_start_title);
                this.mSpaceCapsuleViewModel.setNotFirstEnterSpaceCapsule();
            } else {
                if (this.mSingleSleepMode) {
                    XImageView xImageView = this.mBgImg;
                    if (!this.mStartSpace) {
                        i = R.drawable.space_capsule_single_restore_bg;
                    }
                    xImageView.setImageResource(i);
                } else {
                    XImageView xImageView2 = this.mBgImg;
                    if (!this.mStartSpace) {
                        i2 = R.drawable.space_capsule_restore_bg;
                    }
                    xImageView2.setImageResource(i2);
                }
                updateSpaceCapsuleStatus();
            }
            setLiveDataObserver(this.mSpaceCapsuleViewModel.getStartMoveStatusData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$wvHgGV-Al2bL4gYacltWlJV3v7k
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SpaceCapsulePrepareFragment.this.lambda$initView$11$SpaceCapsulePrepareFragment((Integer) obj);
                }
            });
        }
        setLiveDataObserver(this.mSeatViewModel.getRlSeatOccupiedData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$S7LPBHdEQUJ4Cg7ArNZ06nB5LU4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsulePrepareFragment.this.lambda$initView$12$SpaceCapsulePrepareFragment((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mSeatViewModel.getRmSeatOccupiedData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$Ae_VQm9_1NsYtjJhHJuFRNHufLY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsulePrepareFragment.this.lambda$initView$13$SpaceCapsulePrepareFragment((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mSeatViewModel.getRrSeatOccupiedData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$_-aFQXsixumVcYyRciG_UWpOgL0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsulePrepareFragment.this.lambda$initView$14$SpaceCapsulePrepareFragment((Boolean) obj);
            }
        });
        if (this.mSingleSleepMode) {
            setLiveDataObserver(this.mSpaceCapsuleViewModel.getVipCapsuleSingleSeatStatusData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$5PMYMlIeNj1TsOaEpbqvsUq8i9o
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SpaceCapsulePrepareFragment.this.lambda$initView$15$SpaceCapsulePrepareFragment((VipSeatStatus) obj);
                }
            });
        } else {
            setLiveDataObserver(this.mSeatViewModel.getDrvHeadrestNormal(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$RT6YTW-JR6pLAtSdlaV19xo0KJg
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SpaceCapsulePrepareFragment.this.lambda$initView$16$SpaceCapsulePrepareFragment((Boolean) obj);
                }
            });
            setLiveDataObserver(this.mSpaceCapsuleViewModel.getVipCapsuleSeatStatusData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$NaJfmYAQOoBEKOb0LMV1VNXMgIo
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SpaceCapsulePrepareFragment.this.lambda$initView$17$SpaceCapsulePrepareFragment((VipSeatStatus) obj);
                }
            });
        }
        setLiveDataObserver(this.mSeatViewModel.getPsnHeadrestNormal(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$m1mOnieisBBl55aPmZFQIzp2GPw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsulePrepareFragment.this.lambda$initView$18$SpaceCapsulePrepareFragment((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$SpaceCapsulePrepareFragment(View v) {
        startToExit();
    }

    public /* synthetic */ void lambda$initView$1$SpaceCapsulePrepareFragment(View v) {
        if (this.isSpaceCapsuleGuide) {
            if (this.mSingleSleepMode) {
                this.mBgImg.setImageResource(R.drawable.space_capsule_single_prepare_bg);
            } else {
                this.mBgImg.setImageResource(R.drawable.space_capsule_prepare_bg);
            }
            updateSpaceCapsuleStatus();
            startToPrepareMode(this.mLowBatteryTips);
            this.isSpaceCapsuleGuide = false;
            return;
        }
        VipSeatStatus capsuleSingleVipSeatStatus = this.mSingleSleepMode ? this.mSpaceCapsuleViewModel.getCapsuleSingleVipSeatStatus() : this.mSpaceCapsuleViewModel.getCapsuleVipSeatStatus();
        if (capsuleSingleVipSeatStatus == VipSeatStatus.FlatMoving) {
            if (this.mSingleSleepMode) {
                this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.LayFlatPause);
            } else {
                this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.LayFlatPause);
            }
        } else if (capsuleSingleVipSeatStatus == VipSeatStatus.RestoreMoving) {
            if (this.mSingleSleepMode) {
                this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.RestorePause);
            } else {
                this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.RestorePause);
            }
        } else if (capsuleSingleVipSeatStatus == VipSeatStatus.Flat || capsuleSingleVipSeatStatus == VipSeatStatus.RestorePause) {
            if (this.mSingleSleepMode) {
                this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.Restore);
            } else {
                this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.Restore);
            }
        } else if (capsuleSingleVipSeatStatus == VipSeatStatus.FlatPause || capsuleSingleVipSeatStatus == VipSeatStatus.Normal) {
            if (this.mSingleSleepMode) {
                this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.LayFlat);
            } else {
                this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.LayFlat);
            }
        }
    }

    public /* synthetic */ void lambda$initView$2$SpaceCapsulePrepareFragment(View v) {
        if (this.mSpaceCapsuleViewModel.getDrvVipSeatStatus() == VipSeatStatus.FlatMoving) {
            this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.LayFlatPause);
            StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_DRIVER_STOP, new Object[0]);
            return;
        }
        this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.LayFlat);
        StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_DRIVER_FLAT, new Object[0]);
    }

    public /* synthetic */ void lambda$initView$3$SpaceCapsulePrepareFragment(View v) {
        if (this.mSpaceCapsuleViewModel.getDrvVipSeatStatus() == VipSeatStatus.RestoreMoving) {
            this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.RestorePause);
            StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_DRIVER_STOP, new Object[0]);
            return;
        }
        this.mSpaceCapsuleViewModel.setDrvVipSeatAct(VipSeatAct.Restore);
        StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_DRIVER_RESTORE, new Object[0]);
    }

    public /* synthetic */ void lambda$initView$4$SpaceCapsulePrepareFragment(View v) {
        if (this.mSpaceCapsuleViewModel.getPsnVipSeatStatus() == VipSeatStatus.FlatMoving) {
            this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.LayFlatPause);
            StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_PSN_STOP, new Object[0]);
            return;
        }
        this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.LayFlat);
        StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_PSN_FLAT, new Object[0]);
    }

    public /* synthetic */ void lambda$initView$5$SpaceCapsulePrepareFragment(View v) {
        if (this.mSpaceCapsuleViewModel.getPsnVipSeatStatus() == VipSeatStatus.RestoreMoving) {
            this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.RestorePause);
            StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_PSN_STOP, new Object[0]);
            return;
        }
        this.mSpaceCapsuleViewModel.setPsnVipSeatAct(VipSeatAct.Restore);
        StatisticUtils.sendStatistic(PageEnum.SPACE_CAPSULE_VIP_SEAT_PAGE, BtnEnum.SPACE_CAPSULE_VIP_SEAT_PAGE_PSN_RESTORE, new Object[0]);
    }

    public /* synthetic */ void lambda$initView$6$SpaceCapsulePrepareFragment(VipSeatStatus status) {
        updateVipSeatStatus();
    }

    public /* synthetic */ void lambda$initView$7$SpaceCapsulePrepareFragment(VipSeatStatus status) {
        updateVipSeatStatus();
    }

    public /* synthetic */ void lambda$initView$8$SpaceCapsulePrepareFragment(Integer integer) {
        onDrvSeatMove();
    }

    public /* synthetic */ void lambda$initView$9$SpaceCapsulePrepareFragment(Integer integer) {
        onDrvSeatMove();
    }

    public /* synthetic */ void lambda$initView$10$SpaceCapsulePrepareFragment(Integer integer) {
        onDrvSeatMove();
    }

    public /* synthetic */ void lambda$initView$11$SpaceCapsulePrepareFragment(Integer status) {
        if (status != null) {
            showSeatMoveTips(status.intValue());
        }
    }

    public /* synthetic */ void lambda$initView$12$SpaceCapsulePrepareFragment(Boolean data) {
        updateView();
    }

    public /* synthetic */ void lambda$initView$13$SpaceCapsulePrepareFragment(Boolean data) {
        updateView();
    }

    public /* synthetic */ void lambda$initView$14$SpaceCapsulePrepareFragment(Boolean data) {
        updateView();
    }

    public /* synthetic */ void lambda$initView$15$SpaceCapsulePrepareFragment(VipSeatStatus status) {
        updateSpaceCapsuleStatus();
    }

    public /* synthetic */ void lambda$initView$16$SpaceCapsulePrepareFragment(Boolean data) {
        updateView();
    }

    public /* synthetic */ void lambda$initView$17$SpaceCapsulePrepareFragment(VipSeatStatus status) {
        updateSpaceCapsuleStatus();
    }

    public /* synthetic */ void lambda$initView$18$SpaceCapsulePrepareFragment(Boolean data) {
        updateView();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.isResume) {
            return;
        }
        this.isResume = true;
        if (!this.mStartSpace) {
            startToRecoverMode();
        } else if (this.isSpaceCapsuleGuide || this.isVipSeat) {
        } else {
            startToPrepareMode(this.mLowBatteryTips);
        }
    }

    private void startToPrepareMode(boolean tips) {
        LogUtils.i(TAG, "startToPrepareMode", false);
        if (tips) {
            SpeechHelper speechHelper = SpeechHelper.getInstance();
            boolean isSpaceSleepMode = isSpaceSleepMode();
            int i = R.string.space_capsule_sleep_low_battery_enter_tips;
            speechHelper.speak(ResUtils.getString(isSpaceSleepMode ? R.string.space_capsule_sleep_low_battery_enter_tips : R.string.space_capsule_cinema_low_battery_enter_tips));
            if (this.mLowBatteryDialog == null) {
                XDialog xDialog = new XDialog(getContext());
                this.mLowBatteryDialog = xDialog;
                xDialog.setTitle(R.string.vip_seat_confirm_dialog_title);
                XDialog xDialog2 = this.mLowBatteryDialog;
                if (!isSpaceSleepMode()) {
                    i = R.string.space_capsule_cinema_low_battery_enter_tips;
                }
                xDialog2.setMessage(i);
                this.mLowBatteryDialog.setPositiveButton(R.string.prompt_ok);
                this.mLowBatteryDialog.setPositiveButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$V5EI-6O8erK02FdAvx9CuSWRQXU
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog3, int i2) {
                        SpaceCapsulePrepareFragment.this.lambda$startToPrepareMode$20$SpaceCapsulePrepareFragment(xDialog3, i2);
                    }
                });
                this.mLowBatteryDialog.setCanceledOnTouchOutside(false);
                this.mLowBatteryDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$UxUTc28eFWXqsNskFBb-7aw2dDs
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        SpaceCapsulePrepareFragment.this.lambda$startToPrepareMode$21$SpaceCapsulePrepareFragment(dialogInterface);
                    }
                });
                this.mLowBatteryDialog.show();
                return;
            }
            return;
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$2q_PCKtSm5BS-UuSnxKjzppSkSs
            @Override // java.lang.Runnable
            public final void run() {
                SpaceCapsulePrepareFragment.this.lambda$startToPrepareMode$22$SpaceCapsulePrepareFragment();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$startToPrepareMode$20$SpaceCapsulePrepareFragment(XDialog xDialog, int i) {
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$K26LVwDy6WBVM8fzAhCqOI5Y1Vk
            @Override // java.lang.Runnable
            public final void run() {
                SpaceCapsulePrepareFragment.this.lambda$null$19$SpaceCapsulePrepareFragment();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$startToPrepareMode$21$SpaceCapsulePrepareFragment(DialogInterface dialog) {
        this.mLowBatteryDialog = null;
    }

    private void startToRecoverMode() {
        LogUtils.i(TAG, "startToRecoverMode", false);
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$NcBCcdn1vjQifx_a0SDvIm4KJcA
            @Override // java.lang.Runnable
            public final void run() {
                SpaceCapsulePrepareFragment.this.lambda$startToRecoverMode$23$SpaceCapsulePrepareFragment();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: layFlatSpaceCapsuleSeat */
    public void lambda$startToPrepareMode$22$SpaceCapsulePrepareFragment() {
        if (this.mSingleSleepMode) {
            if (this.mSpaceCapsuleViewModel.getCapsuleSingleVipSeatStatus() != VipSeatStatus.Flat) {
                this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.LayFlat);
            }
        } else if (this.mSpaceCapsuleViewModel.getCapsuleVipSeatStatus() != VipSeatStatus.Flat) {
            this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.LayFlat);
        }
        this.mHandler.postDelayed(this.titleAnimRun, TITLE_ANIM_SWITCH_TIME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: restoreSpaceCapsuleSeat */
    public void lambda$startToRecoverMode$23$SpaceCapsulePrepareFragment() {
        if (this.mSingleSleepMode) {
            if (this.mSpaceCapsuleViewModel.getCapsuleSingleVipSeatStatus() != VipSeatStatus.Normal) {
                this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.Restore);
            }
        } else if (this.mSpaceCapsuleViewModel.getCapsuleVipSeatStatus() != VipSeatStatus.Normal) {
            this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.Restore);
        }
        this.mHandler.postDelayed(this.titleAnimRun, TITLE_ANIM_SWITCH_TIME);
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

    private void updateSpaceCapsuleStatus() {
        if (this.isSpaceCapsuleGuide) {
            return;
        }
        VipSeatStatus capsuleSingleVipSeatStatus = this.mSingleSleepMode ? this.mSpaceCapsuleViewModel.getCapsuleSingleVipSeatStatus() : this.mSpaceCapsuleViewModel.getCapsuleVipSeatStatus();
        boolean isDrvHeadrestNormal = this.mSeatViewModel.isDrvHeadrestNormal();
        boolean isPsnHeadrestNormal = this.mSeatViewModel.isPsnHeadrestNormal();
        boolean isRearSeatOccupied = isRearSeatOccupied();
        LogUtils.d(TAG, "updateSpaceCapsuleStatus: " + capsuleSingleVipSeatStatus + ", mDvrHeadrest: " + isDrvHeadrestNormal + ", mPsnHeadrest: " + isPsnHeadrestNormal + ", mRearSeatOccupied: " + isRearSeatOccupied + ", mStartSpace: " + this.mStartSpace);
        int i = AnonymousClass3.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatStatus[capsuleSingleVipSeatStatus.ordinal()];
        int i2 = R.string.space_capsule_sleep_exit_title;
        int i3 = R.string.space_capsule_sleep_prepare_title;
        switch (i) {
            case 1:
                if (this.mStartSpace) {
                    hideTipsImg(this.mHeadImg);
                    hideTipsImg(this.mDriverHeadImg);
                    hideTipsImg(this.mPsnHeadImg);
                    hideTipsImg(this.mBackSeatImg);
                    hideTipsImg(this.mSeatMoveImg);
                    this.mSpacePrepareControlBtn.setVisibility(8);
                    this.mHeadTipsLayout.setVisibility(8);
                    this.mBackSeatImg.setVisibility(8);
                    this.mBackSeatTipsLayout.setVisibility(8);
                    XTextView xTextView = this.mTitleTv;
                    if (!isSpaceSleepMode()) {
                        i3 = R.string.space_capsule_cinema_prepare_title;
                    }
                    xTextView.setText(i3);
                    this.mSpacePrepareControlBtn.setVisibility(8);
                    this.mDescTv.setVisibility(8);
                    return;
                } else if (this.mSpaceListener == null || this.mVcuViewModel.getGearLevelValue() != GearLevel.P) {
                    return;
                } else {
                    SpeechHelper.getInstance().speak(App.getInstance().getString(isSpaceSleepMode() ? R.string.space_capsule_sleep_exit_active : R.string.space_capsule_cinema_exit_active), true);
                    this.mSpaceListener.onSpaceCapsuleEnd();
                    return;
                }
            case 2:
                updateHeadrestView(isDrvHeadrestNormal, isPsnHeadrestNormal);
                if (isRearSeatOccupied) {
                    showTipsImg(this.mBackSeatImg, R.drawable.img_space_capsule_back_seat_tips);
                    this.mBackSeatImg.setVisibility(0);
                    this.mBackSeatTipsLayout.setVisibility(0);
                } else {
                    hideTipsImg(this.mBackSeatImg);
                    this.mBackSeatImg.setVisibility(8);
                    this.mBackSeatTipsLayout.setVisibility(8);
                }
                this.mSpacePrepareControlBtn.setVisibility(8);
                XTextView xTextView2 = this.mTitleTv;
                if (!isSpaceSleepMode()) {
                    i3 = R.string.space_capsule_cinema_prepare_title;
                }
                xTextView2.setText(i3);
                this.mDescTv.setVisibility(0);
                this.mDescTv.setText(R.string.space_capsule_prepare_summary);
                return;
            case 3:
                hideTipsImg(this.mHeadImg);
                hideTipsImg(this.mDriverHeadImg);
                hideTipsImg(this.mPsnHeadImg);
                hideTipsImg(this.mBackSeatImg);
                this.mHeadTipsLayout.setVisibility(8);
                this.mBackSeatImg.setVisibility(8);
                this.mBackSeatTipsLayout.setVisibility(8);
                XTextView xTextView3 = this.mTitleTv;
                if (!isSpaceSleepMode()) {
                    i3 = R.string.space_capsule_cinema_prepare_title;
                }
                xTextView3.setText(i3);
                this.mDescTv.setVisibility(0);
                this.mDescTv.setText(R.string.space_capsule_prepare_tips);
                setControlStatus(true, false);
                return;
            case 4:
                updateHeadrestView(isDrvHeadrestNormal, isPsnHeadrestNormal);
                if (isRearSeatOccupied) {
                    showTipsImg(this.mBackSeatImg, R.drawable.img_space_capsule_back_seat_tips);
                    this.mBackSeatImg.setVisibility(0);
                    this.mBackSeatTipsLayout.setVisibility(0);
                } else {
                    hideTipsImg(this.mBackSeatImg);
                    this.mBackSeatImg.setVisibility(8);
                    this.mBackSeatTipsLayout.setVisibility(8);
                }
                XTextView xTextView4 = this.mTitleTv;
                if (!isSpaceSleepMode()) {
                    i3 = R.string.space_capsule_cinema_prepare_title;
                }
                xTextView4.setText(i3);
                this.mDescTv.setVisibility(0);
                this.mDescTv.setText(R.string.space_capsule_prepare_tips);
                setControlStatus(true, true);
                return;
            case 5:
                if (this.mStartSpace) {
                    ISpaceCapsuleInterface iSpaceCapsuleInterface = this.mSpaceListener;
                    if (iSpaceCapsuleInterface != null) {
                        iSpaceCapsuleInterface.onSpaceCapsuleReady();
                        PageEnum pageEnum = PageEnum.SPACE_CAPSULE_PAGE;
                        BtnEnum btnEnum = BtnEnum.SPACE_CAPSULE_PREPARE_COMPLETE;
                        Object[] objArr = new Object[1];
                        objArr[0] = Integer.valueOf(isSpaceSleepMode() ? 1 : 2);
                        StatisticUtils.sendSpaceCapsuleStatistic(pageEnum, btnEnum, objArr);
                        return;
                    }
                    return;
                }
                hideTipsImg(this.mHeadImg);
                hideTipsImg(this.mDriverHeadImg);
                hideTipsImg(this.mPsnHeadImg);
                hideTipsImg(this.mBackSeatImg);
                hideTipsImg(this.mSeatMoveImg);
                this.mSpacePrepareControlBtn.setVisibility(8);
                this.mHeadTipsLayout.setVisibility(8);
                this.mBackSeatImg.setVisibility(8);
                this.mBackSeatTipsLayout.setVisibility(8);
                this.mSpacePrepareControlBtn.setVisibility(8);
                XTextView xTextView5 = this.mTitleTv;
                if (!isSpaceSleepMode()) {
                    i2 = R.string.space_capsule_cinema_exit_title;
                }
                xTextView5.setText(i2);
                this.mDescTv.setVisibility(8);
                return;
            case 6:
                if (isRearSeatOccupied) {
                    showTipsImg(this.mBackSeatImg, R.drawable.img_space_capsule_back_seat_tips);
                    this.mBackSeatImg.setVisibility(0);
                    this.mBackSeatTipsLayout.setVisibility(0);
                } else {
                    hideTipsImg(this.mBackSeatImg);
                    this.mBackSeatImg.setVisibility(8);
                    this.mBackSeatTipsLayout.setVisibility(8);
                }
                hideTipsImg(this.mHeadImg);
                hideTipsImg(this.mDriverHeadImg);
                hideTipsImg(this.mPsnHeadImg);
                XTextView xTextView6 = this.mTitleTv;
                if (!isSpaceSleepMode()) {
                    i2 = R.string.space_capsule_cinema_exit_title;
                }
                xTextView6.setText(i2);
                this.mDescTv.setVisibility(0);
                this.mDescTv.setText(R.string.space_capsule_prepare_summary);
                this.mSpacePrepareControlBtn.setVisibility(8);
                return;
            case 7:
                hideTipsImg(this.mHeadImg);
                hideTipsImg(this.mDriverHeadImg);
                hideTipsImg(this.mPsnHeadImg);
                hideTipsImg(this.mBackSeatImg);
                this.mHeadTipsLayout.setVisibility(8);
                this.mBackSeatImg.setVisibility(8);
                this.mBackSeatTipsLayout.setVisibility(8);
                XTextView xTextView7 = this.mTitleTv;
                if (!isSpaceSleepMode()) {
                    i2 = R.string.space_capsule_cinema_exit_title;
                }
                xTextView7.setText(i2);
                this.mDescTv.setVisibility(0);
                this.mDescTv.setText(R.string.space_capsule_prepare_tips);
                setControlStatus(false, false);
                return;
            case 8:
                if (isRearSeatOccupied) {
                    showTipsImg(this.mBackSeatImg, R.drawable.img_space_capsule_back_seat_tips);
                    this.mBackSeatImg.setVisibility(0);
                    this.mBackSeatTipsLayout.setVisibility(0);
                } else {
                    hideTipsImg(this.mBackSeatImg);
                    this.mBackSeatImg.setVisibility(8);
                    this.mBackSeatTipsLayout.setVisibility(8);
                }
                hideTipsImg(this.mHeadImg);
                hideTipsImg(this.mDriverHeadImg);
                hideTipsImg(this.mPsnHeadImg);
                XTextView xTextView8 = this.mTitleTv;
                if (!isSpaceSleepMode()) {
                    i2 = R.string.space_capsule_cinema_exit_title;
                }
                xTextView8.setText(i2);
                this.mDescTv.setVisibility(0);
                this.mDescTv.setText(R.string.space_capsule_prepare_tips);
                setControlStatus(false, true);
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceCapsulePrepareFragment$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatStatus;

        static {
            int[] iArr = new int[VipSeatStatus.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatStatus = iArr;
            try {
                iArr[VipSeatStatus.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatStatus[VipSeatStatus.FlatWaiting.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatStatus[VipSeatStatus.FlatMoving.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatStatus[VipSeatStatus.FlatPause.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatStatus[VipSeatStatus.Flat.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatStatus[VipSeatStatus.RestoreWaiting.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatStatus[VipSeatStatus.RestoreMoving.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatStatus[VipSeatStatus.RestorePause.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private void updateHeadrestView(boolean drvHeadrestNormal, boolean psnHeadrestNormal) {
        if (this.mSingleSleepMode) {
            hideTipsImg(this.mHeadImg);
            if (psnHeadrestNormal) {
                showTipsImg(this.mPsnHeadImg, R.drawable.img_space_capsule_psn_head_tips);
            } else {
                hideTipsImg(this.mPsnHeadImg);
            }
            this.mHeadTipsLayout.setVisibility(psnHeadrestNormal ? 0 : 8);
        } else if (drvHeadrestNormal && psnHeadrestNormal) {
            showTipsImg(this.mHeadImg, R.drawable.img_space_capsule_head_tips);
            hideTipsImg(this.mDriverHeadImg);
            hideTipsImg(this.mPsnHeadImg);
            this.mHeadTipsLayout.setVisibility(0);
        } else if (drvHeadrestNormal) {
            showTipsImg(this.mDriverHeadImg, R.drawable.img_space_capsule_driver_head_tips);
            hideTipsImg(this.mHeadImg);
            hideTipsImg(this.mPsnHeadImg);
            this.mHeadTipsLayout.setVisibility(0);
        } else if (psnHeadrestNormal) {
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
    }

    private void showSeatMoveTips(int status) {
        LogUtils.d(TAG, "showSeatMoveTips, status: " + status + ", mCapsuleSeatStatus: " + (this.mSingleSleepMode ? this.mSpaceCapsuleViewModel.getCapsuleSingleVipSeatStatus() : this.mSpaceCapsuleViewModel.getCapsuleVipSeatStatus()), false);
        if (status == 1 && this.mStartSpace) {
            if (this.mSingleSleepMode) {
                showTipsImg(this.mSeatMoveImg, R.drawable.img_space_capsule_single_seat_flat_tips);
            } else {
                showTipsImg(this.mSeatMoveImg, R.drawable.img_space_capsule_seat_flat_tips);
            }
        } else if (status != 2 || this.mStartSpace) {
        } else {
            if (this.mSingleSleepMode) {
                showTipsImg(this.mSeatMoveImg, R.drawable.img_space_capsule_single_seat_restore_tips);
            } else {
                showTipsImg(this.mSeatMoveImg, R.drawable.img_space_capsule_seat_restore_tips);
            }
        }
    }

    private void setControlStatus(boolean prepare, boolean pause) {
        this.mSpacePrepareControlBtn.setVisibility(0);
        if (pause) {
            this.mSpacePrepareControlBtn.setBackground(ResUtils.getDrawable(R.drawable.x_btn_real_positive_selector));
            this.mSpacePrepareControlBtn.setTextColor(ResUtils.getColor(R.color.x_btn_real_color_selector));
            if (prepare) {
                this.mSpacePrepareControlBtn.setText(R.string.space_capsule_seat_lie_down);
                return;
            } else {
                this.mSpacePrepareControlBtn.setText(R.string.space_capsule_seat_recover);
                return;
            }
        }
        this.mSpacePrepareControlBtn.setBackground(ResUtils.getDrawable(R.drawable.space_control_pause_bg));
        this.mSpacePrepareControlBtn.setTextColor(ResUtils.getColor(R.color.space_prepare_control_text_color));
        if (prepare) {
            this.mSpacePrepareControlBtn.setText(R.string.space_capsule_seat_lie_down_pause);
        } else {
            this.mSpacePrepareControlBtn.setText(R.string.space_capsule_seat_recover_pause);
        }
    }

    private void updateView() {
        if (this.isVipSeat) {
            updateVipSeatStatus();
        } else {
            updateSpaceCapsuleStatus();
        }
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
        if (this.isVipSeat || this.isSpaceCapsuleGuide) {
            ISpaceCapsuleInterface iSpaceCapsuleInterface = this.mSpaceListener;
            if (iSpaceCapsuleInterface != null) {
                iSpaceCapsuleInterface.onSpaceCapsuleEnd();
                return;
            }
            return;
        }
        VipSeatStatus capsuleSingleVipSeatStatus = this.mSingleSleepMode ? this.mSpaceCapsuleViewModel.getCapsuleSingleVipSeatStatus() : this.mSpaceCapsuleViewModel.getCapsuleVipSeatStatus();
        LogUtils.d(TAG, "exitSpaceCapsule, mSpaceStatus: " + capsuleSingleVipSeatStatus);
        if (this.mStartSpace) {
            if (capsuleSingleVipSeatStatus == VipSeatStatus.FlatMoving) {
                if (this.mSingleSleepMode) {
                    this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.LayFlatPause);
                } else {
                    this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.LayFlatPause);
                }
            }
            showEnterInterruptDialog();
            return;
        }
        if (capsuleSingleVipSeatStatus == VipSeatStatus.RestoreMoving) {
            if (this.mSingleSleepMode) {
                this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.RestorePause);
            } else {
                this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.RestorePause);
            }
        }
        showExitInterruptDialog();
    }

    private void showEnterInterruptDialog() {
        if (this.mEnterInterruptDialog == null) {
            XDialog xDialog = new XDialog(getContext());
            this.mEnterInterruptDialog = xDialog;
            xDialog.setTitle(R.string.space_capsule_interrupt_dialog_title);
            this.mEnterInterruptDialog.setMessage(R.string.space_capsule_enter_interrupt_dialog_content);
            this.mEnterInterruptDialog.setPositiveButton(R.string.space_capsule_interrupt_positive);
            this.mEnterInterruptDialog.setNegativeButton(R.string.space_capsule_interrupt_negative);
            this.mEnterInterruptDialog.setPositiveButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$odyk5CLQRAOmvrCdQqAnscuDK0M
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SpaceCapsulePrepareFragment.this.lambda$showEnterInterruptDialog$24$SpaceCapsulePrepareFragment(xDialog2, i);
                }
            });
            this.mEnterInterruptDialog.setNegativeButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$AhwfSPfCRPqxxA9PlrGJwJsXuu4
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SpaceCapsulePrepareFragment.this.lambda$showEnterInterruptDialog$25$SpaceCapsulePrepareFragment(xDialog2, i);
                }
            });
            this.mEnterInterruptDialog.setCloseVisibility(false);
        }
        this.mEnterInterruptDialog.show();
    }

    public /* synthetic */ void lambda$showEnterInterruptDialog$24$SpaceCapsulePrepareFragment(XDialog xDialog, int i) {
        if (this.mSingleSleepMode) {
            this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.Stop);
        } else {
            this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.Stop);
        }
        this.mSpaceCapsuleViewModel.restoreSpaceCapsuleSettings();
        ISpaceCapsuleInterface iSpaceCapsuleInterface = this.mSpaceListener;
        if (iSpaceCapsuleInterface != null) {
            iSpaceCapsuleInterface.onSpaceCapsuleEnd();
        }
        PageEnum pageEnum = PageEnum.SPACE_CAPSULE_PAGE;
        BtnEnum btnEnum = BtnEnum.SPACE_CAPSULE_PAGE_EXIT;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(isSpaceSleepMode() ? 1 : 2);
        objArr[1] = 1;
        StatisticUtils.sendSpaceCapsuleStatistic(pageEnum, btnEnum, objArr);
    }

    public /* synthetic */ void lambda$showEnterInterruptDialog$25$SpaceCapsulePrepareFragment(XDialog xDialog, int i) {
        if (this.mSingleSleepMode) {
            this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.LayFlat);
        } else {
            this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.LayFlat);
        }
    }

    private void showExitInterruptDialog() {
        if (this.mExitInterruptDialog == null) {
            XDialog xDialog = new XDialog(getContext());
            this.mExitInterruptDialog = xDialog;
            xDialog.setTitle(R.string.space_capsule_interrupt_dialog_title);
            this.mExitInterruptDialog.setMessage(R.string.space_capsule_exit_interrupt_dialog_content);
            this.mExitInterruptDialog.setPositiveButton(R.string.space_capsule_interrupt_positive);
            this.mExitInterruptDialog.setNegativeButton(R.string.space_capsule_interrupt_negative);
            this.mExitInterruptDialog.setPositiveButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$uzs6uHrt47AtXs-IA1Mwb6hCE58
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SpaceCapsulePrepareFragment.this.lambda$showExitInterruptDialog$26$SpaceCapsulePrepareFragment(xDialog2, i);
                }
            });
            this.mExitInterruptDialog.setNegativeButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SpaceCapsulePrepareFragment$jGj8zBBNn1X8UgQHrW2khulio6w
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SpaceCapsulePrepareFragment.this.lambda$showExitInterruptDialog$27$SpaceCapsulePrepareFragment(xDialog2, i);
                }
            });
            this.mExitInterruptDialog.setCloseVisibility(false);
        }
        this.mExitInterruptDialog.show();
    }

    public /* synthetic */ void lambda$showExitInterruptDialog$26$SpaceCapsulePrepareFragment(XDialog xDialog, int i) {
        if (this.mSingleSleepMode) {
            this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.Stop);
        } else {
            this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.Stop);
        }
        this.mSpaceCapsuleViewModel.restoreSpaceCapsuleSettings();
        ISpaceCapsuleInterface iSpaceCapsuleInterface = this.mSpaceListener;
        if (iSpaceCapsuleInterface != null) {
            iSpaceCapsuleInterface.onSpaceCapsuleEnd();
        }
        PageEnum pageEnum = PageEnum.SPACE_CAPSULE_PAGE;
        BtnEnum btnEnum = BtnEnum.SPACE_CAPSULE_PAGE_EXIT;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(isSpaceSleepMode() ? 1 : 2);
        objArr[1] = 1;
        StatisticUtils.sendSpaceCapsuleStatistic(pageEnum, btnEnum, objArr);
    }

    public /* synthetic */ void lambda$showExitInterruptDialog$27$SpaceCapsulePrepareFragment(XDialog xDialog, int i) {
        if (this.mSingleSleepMode) {
            this.mSpaceCapsuleViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.Restore);
        } else {
            this.mSpaceCapsuleViewModel.setCapsuleVipSeatAct(VipSeatAct.Restore);
        }
    }

    private void dismissDialog() {
        XDialog xDialog = this.mEnterInterruptDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mEnterInterruptDialog = null;
        }
        XDialog xDialog2 = this.mExitInterruptDialog;
        if (xDialog2 != null) {
            xDialog2.dismiss();
            this.mExitInterruptDialog = null;
        }
        XDialog xDialog3 = this.mLowBatteryDialog;
        if (xDialog3 != null) {
            xDialog3.dismiss();
            this.mLowBatteryDialog = null;
        }
    }

    private boolean isSpaceSleepMode() {
        return !this.isVipSeat && this.mModeIndex == 1;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.titleAnimRun);
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (XThemeManager.isThemeChanged(newConfig)) {
            if (this.isVipSeat) {
                updateVipSeatStatus();
            } else {
                updateSpaceCapsuleStatus();
            }
        }
    }
}
