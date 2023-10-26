package com.xiaopeng.carcontrol.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.CapsuleDialogService;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.fragment.spacecapsule.ISpaceCapsuleInterface;
import com.xiaopeng.carcontrol.view.fragment.spacecapsule.SleepCapsuleChooseFragment;
import com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceCapsuleFragment;
import com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceCapsulePrepareFragment;
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
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.spacecapsule.util.ScreenOnUtil;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.utils.XActivityUtils;

/* loaded from: classes2.dex */
public class SpaceCapsuleActivity extends XActivity implements ISpaceCapsuleInterface {
    private static final int REQUEST_CODE = 1;
    private static final String TAG = "SpaceCapsuleActivity";
    private Observer<Integer> mAvailableMileageObserver;
    private boolean mAvailableMileageTips;
    private Observer<Boolean> mDCChargeObserver;
    private XDialog mExitDialog;
    private Observer<GearLevel> mGearObserver;
    private Observer<Boolean> mMcuIgOffObserver;
    private boolean mRestoreSeat;
    private ScenarioViewModel mScenarioViewModel;
    private ScreenOnUtil.ScreenEventBroadCastReceiver mScreenEventBroadCastReceiver;
    private SeatViewModel mSeatViewModel;
    private int mSubModeIndex;
    protected VcuViewModel mVcuViewModel;
    private SpaceCapsuleViewModel mViewModel;
    private boolean mVipSeat;
    private boolean mVoiceSource;
    private boolean mSelfExit = false;
    private boolean mIsPlayLowBatteryTts = false;
    private boolean mInSubMode = false;
    private int mSleepBed = -1;
    private boolean mLayoutFlat = false;
    private boolean mSleepLayoutRecover = false;
    private final long CHANGE_TO_NIGHT_DELAY = 2000;
    private final Runnable mChangeToNightRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.SpaceCapsuleActivity.1
        @Override // java.lang.Runnable
        public void run() {
            SpaceCapsuleActivity.this.mViewModel.changeDay2NightTry();
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        setContentView(R.layout.activity_space_capsule);
        this.mViewModel = (SpaceCapsuleViewModel) ViewModelManager.getInstance().getViewModelImpl(ISpaceCapsuleViewModel.class);
        this.mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
        this.mSeatViewModel = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        this.mVcuViewModel = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        this.mGearObserver = new Observer() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$SpaceCapsuleActivity$ebwaPTX9elhFc3YYGvuWY5fjiqI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsuleActivity.this.lambda$onCreate$0$SpaceCapsuleActivity((GearLevel) obj);
            }
        };
        this.mMcuIgOffObserver = new Observer() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$SpaceCapsuleActivity$HCyj0YJtNtLcoG6YHD59UI2K46A
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsuleActivity.this.lambda$onCreate$1$SpaceCapsuleActivity((Boolean) obj);
            }
        };
        this.mDCChargeObserver = new Observer() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$SpaceCapsuleActivity$7v7MM1oWNjdhs0oLM6MZZNKGT2I
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpaceCapsuleActivity.this.lambda$onCreate$2$SpaceCapsuleActivity((Boolean) obj);
            }
        };
        boolean z = this.mVcuViewModel.getAvailableMileage() >= 120;
        this.mAvailableMileageTips = z;
        if (z) {
            this.mAvailableMileageObserver = new Observer() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$SpaceCapsuleActivity$hk2UKOfrZML93fWNwEyB_EGwhtA
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SpaceCapsuleActivity.this.lambda$onCreate$3$SpaceCapsuleActivity((Integer) obj);
                }
            };
        }
        processIntent(getIntent());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.xiaopeng.broadcast.ACTION_SCREEN_STATUS_CHANGE");
        ScreenOnUtil.ScreenEventBroadCastReceiver screenEventBroadCastReceiver = new ScreenOnUtil.ScreenEventBroadCastReceiver();
        this.mScreenEventBroadCastReceiver = screenEventBroadCastReceiver;
        registerReceiver(screenEventBroadCastReceiver, intentFilter);
    }

    public /* synthetic */ void lambda$onCreate$0$SpaceCapsuleActivity(GearLevel gearLevel) {
        if (gearLevel != GearLevel.P) {
            boolean z = false;
            LogUtils.i(TAG, "exit Space Capsule when gear is not P", false);
            if (this.mSeatViewModel.isDrvHeadrestNormal() && this.mSeatViewModel.isPsnHeadrestNormal()) {
                z = true;
            }
            Intent intent = new Intent(this, CapsuleDialogService.class);
            intent.setAction(GlobalConstant.ACTION.ACTION_CAPSULE_SECURITY_CONFIRM_SHOW);
            intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_EXIT_HEAD_REST, z);
            this.mSelfExit = true;
            startService(intent);
            if (isFinishing()) {
                return;
            }
            XActivityUtils.finish(this);
        }
    }

    public /* synthetic */ void lambda$onCreate$1$SpaceCapsuleActivity(Boolean igOff) {
        if (igOff.booleanValue()) {
            LogUtils.i(TAG, "exit Space Capsule when ig off", false);
            this.mSelfExit = true;
            if (isFinishing()) {
                return;
            }
            XActivityUtils.finish(this);
        }
    }

    public /* synthetic */ void lambda$onCreate$2$SpaceCapsuleActivity(Boolean isInDcCharge) {
        if (isInDcCharge.booleanValue()) {
            LogUtils.i(TAG, "in Dc Charge", false);
            VipSeatStatus capsuleVipSeatStatus = this.mViewModel.getCapsuleVipSeatStatus();
            if (capsuleVipSeatStatus == VipSeatStatus.FlatMoving) {
                if (isSingleSleepMode()) {
                    this.mViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.LayFlatPause);
                } else {
                    this.mViewModel.setCapsuleVipSeatAct(VipSeatAct.LayFlatPause);
                }
            } else if (capsuleVipSeatStatus == VipSeatStatus.RestoreMoving) {
                if (isSingleSleepMode()) {
                    this.mViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.RestorePause);
                } else {
                    this.mViewModel.setCapsuleVipSeatAct(VipSeatAct.RestorePause);
                }
            }
            Intent intent = new Intent(this, CapsuleDialogService.class);
            intent.setAction(GlobalConstant.ACTION.ACTION_CAPSULE_DC_CHARGE_SECURITY_CONFIRM_SHOW);
            intent.putExtra(GlobalConstant.EXTRA.KEY_MODE_TYPE, this.mSubModeIndex);
            startService(intent);
        }
    }

    public /* synthetic */ void lambda$onCreate$3$SpaceCapsuleActivity(Integer availableMileage) {
        if (this.mIsPlayLowBatteryTts || availableMileage.intValue() >= 120) {
            return;
        }
        LogUtils.i(TAG, "Low battery tips, only Tips", false);
        this.mIsPlayLowBatteryTts = true;
        Intent intent = new Intent(this, CapsuleDialogService.class);
        intent.setAction(GlobalConstant.ACTION.ACTION_CAPSULE_LOW_BATTERY_TIPS_SHOW);
        intent.putExtra(GlobalConstant.EXTRA.KEY_MODE_TYPE, this.mSubModeIndex);
        startService(intent);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment findFragmentById = supportFragmentManager.findFragmentById(R.id.space_capsule_prepare_container);
        LogUtils.i(TAG, "replaceFragment:" + (findFragmentById != null ? findFragmentById.getClass().getSimpleName() : "null") + " to:" + (fragment != null ? fragment.getClass().getSimpleName() : "null") + " index:" + this.mSubModeIndex);
        if ((fragment instanceof SpaceCapsuleFragment) && (findFragmentById instanceof SpaceCapsuleFragment)) {
            ((SpaceCapsuleFragment) findFragmentById).changeSubMode(this.mSubModeIndex);
            return;
        }
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.space_capsule_enter, R.anim.space_capsule_exit);
        beginTransaction.replace(R.id.space_capsule_prepare_container, fragment);
        beginTransaction.commit();
    }

    @Override // com.xiaopeng.xui.app.XActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            LogUtils.i(TAG, "action down");
            touchEvent(ev);
        } else if (ev.getAction() == 1) {
            LogUtils.i(TAG, "action up");
            touchEvent(ev);
        }
        ScreenOnUtil.setXpIcmScreenOnOrOff(true);
        ScreenOnUtil.setDriverScreenOnOrOff(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        LogUtils.i(TAG, "dispatchKeyEvent - event: " + event);
        ScreenOnUtil.setXpIcmScreenOnOrOff(true);
        ScreenOnUtil.setDriverScreenOnOrOff(true);
        return super.dispatchKeyEvent(event);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LogUtils.d(TAG, "onNewIntent");
        processIntent(getIntent());
    }

    private void processIntent(Intent intent) {
        String scenarioStr;
        String scenarioStr2;
        if (intent == null) {
            return;
        }
        this.mSubModeIndex = intent.getIntExtra(GlobalConstant.EXTRA.KEY_MODE_TYPE, 1);
        boolean booleanExtra = intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_SPACE_MODE_STATUS, false);
        this.mVipSeat = intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_VIP_SEAT, false);
        this.mVoiceSource = intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_SPACE_SOURCE, false);
        boolean booleanExtra2 = intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_SPACE_EXIT_REASON, false);
        LogUtils.d(TAG, "processIntent, start: " + booleanExtra + ", mVipSeat: " + this.mVipSeat + ",voiceSource: " + this.mVoiceSource + ", mSubModeIndex: " + this.mSubModeIndex);
        if (booleanExtra) {
            this.mScenarioViewModel.registerBinderObserver();
            ScenarioViewModel scenarioViewModel = this.mScenarioViewModel;
            if (this.mVipSeat) {
                scenarioStr2 = ScenarioMode.VipSeat.toScenarioStr();
            } else {
                scenarioStr2 = (this.mSubModeIndex == 1 ? ScenarioMode.SpaceCapsuleSleep : ScenarioMode.SpaceCapsuleCinema).toScenarioStr();
            }
            scenarioViewModel.reportScenarioStatus(scenarioStr2, 2);
            Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.space_capsule_prepare_container);
            if (this.mLayoutFlat) {
                if (findFragmentById != null && findFragmentById.isVisible() && (findFragmentById instanceof SpaceCapsulePrepareFragment)) {
                    this.mLayoutFlat = false;
                    replaceFragment(SpaceCapsuleFragment.newInstance(this.mSubModeIndex, this.mSleepBed));
                    return;
                }
                return;
            }
            this.mViewModel.onModeStart(this.mVipSeat, this.mVoiceSource);
            if (!this.mVipSeat && VipSeatStatus.Flat == this.mViewModel.getCapsuleVipSeatStatus()) {
                this.mInSubMode = true;
                replaceFragment(SpaceCapsuleFragment.newInstance(this.mSubModeIndex, this.mSleepBed));
                return;
            }
            this.mInSubMode = false;
            if (isSpaceSleepMode()) {
                replaceFragment(SleepCapsuleChooseFragment.newInstance());
                this.mViewModel.setCurrentSubMode(3);
                return;
            }
            replaceFragment(SpaceCapsulePrepareFragment.newInstance(this.mVipSeat, true, this.mSubModeIndex, !this.mAvailableMileageTips, this.mSleepBed));
            return;
        }
        ScenarioViewModel scenarioViewModel2 = this.mScenarioViewModel;
        if (this.mVipSeat) {
            scenarioStr = ScenarioMode.VipSeat.toScenarioStr();
        } else {
            scenarioStr = (this.mSubModeIndex == 1 ? ScenarioMode.SpaceCapsuleSleep : ScenarioMode.SpaceCapsuleCinema).toScenarioStr();
        }
        scenarioViewModel2.reportScenarioStatus(scenarioStr, 0);
        exitSpace(booleanExtra2);
    }

    private void exitSpace(boolean notGearP) {
        if (this.mVipSeat) {
            this.mViewModel.setDrvVipSeatAct(VipSeatAct.Stop);
            this.mViewModel.setPsnVipSeatAct(VipSeatAct.Stop);
        } else if (isSingleSleepMode()) {
            this.mViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.Stop);
        } else {
            this.mViewModel.setCapsuleVipSeatAct(VipSeatAct.Stop);
        }
        if (notGearP) {
            this.mSelfExit = true;
            boolean z = this.mSeatViewModel.isDrvHeadrestNormal() && this.mSeatViewModel.isPsnHeadrestNormal();
            Intent intent = new Intent(this, CapsuleDialogService.class);
            intent.setAction(GlobalConstant.ACTION.ACTION_CAPSULE_SECURITY_CONFIRM_SHOW);
            intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_EXIT_HEAD_REST, z);
            startService(intent);
        }
        if (!isFinishing()) {
            XActivityUtils.finish(this);
        }
        ScreenOnUtil.setXpIcmScreenOnOrOff(true);
        ScreenOnUtil.setDriverScreenOnOrOff(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        Fragment findFragmentById;
        super.onResume();
        LogUtils.d(TAG, "ON RESUME");
        if (this.mLayoutFlat) {
            Fragment findFragmentById2 = getSupportFragmentManager().findFragmentById(R.id.space_capsule_prepare_container);
            if (findFragmentById2 != null && findFragmentById2.isVisible() && (findFragmentById2 instanceof SpaceCapsulePrepareFragment)) {
                this.mLayoutFlat = false;
                replaceFragment(SpaceCapsuleFragment.newInstance(this.mSubModeIndex, this.mSleepBed));
            }
        } else if (this.mSleepLayoutRecover && (findFragmentById = getSupportFragmentManager().findFragmentById(R.id.space_capsule_prepare_container)) != null && findFragmentById.isVisible() && (findFragmentById instanceof SpaceCapsuleFragment) && isSpaceSleepMode()) {
            showExitDialog(false);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ISpaceCapsuleInterface
    public void onSpaceSleepSelect(int bedType) {
        this.mSleepBed = bedType;
        replaceFragment(SpaceCapsulePrepareFragment.newInstance(this.mVipSeat, true, this.mSubModeIndex, !this.mAvailableMileageTips, bedType));
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ISpaceCapsuleInterface
    public void onSpaceCapsuleReady() {
        if (this.mInSubMode) {
            return;
        }
        this.mInSubMode = true;
        if (isSpaceSleepMode()) {
            this.mLayoutFlat = true;
            if (!SharedPreferenceUtil.isSpaceCapsuleUGEnterShowCheckBoxSelected()) {
                startSleepGuide(true);
            } else {
                replaceFragment(SpaceCapsuleFragment.newInstance(this.mSubModeIndex, this.mSleepBed));
            }
        } else if (this.mViewModel.isProjectorConnected()) {
            replaceFragment(SpaceCapsuleFragment.newInstance(this.mSubModeIndex, this.mSleepBed));
        } else {
            this.mLayoutFlat = true;
            startCinemaGuide();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ISpaceCapsuleInterface
    public void onSpaceCapsuleRecover() {
        if (isSpaceSleepMode()) {
            this.mSleepLayoutRecover = true;
            if (!SharedPreferenceUtil.isSpaceCapsuleUGExitShowCheckBoxSelected()) {
                startSleepGuideOnExitForResult();
                return;
            } else {
                showExitDialog(false);
                return;
            }
        }
        showExitDialog(false);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ISpaceCapsuleInterface
    public void onSpaceCapsuleEnd() {
        this.mSelfExit = true;
        if (isFinishing()) {
            return;
        }
        XActivityUtils.finish(this);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogUtils.d(TAG, "onWindowFocusChanged hasFocus:" + hasFocus);
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.space_capsule_prepare_container);
        if (findFragmentById != null && (findFragmentById instanceof SpaceCapsuleFragment) && findFragmentById.isVisible()) {
            ((SpaceCapsuleFragment) findFragmentById).onWindowFocusChanged(hasFocus);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
        this.mViewModel.setAutoPowerOffConfigOff();
        if (this.mVipSeat) {
            return;
        }
        ThreadUtils.runOnMainThreadDelay(this.mChangeToNightRunnable, 2000L);
        this.mVcuViewModel.getDCChargeStatusData().observeForever(this.mDCChargeObserver);
        if (this.mAvailableMileageTips) {
            this.mVcuViewModel.getAvailableMileageData().observeForever(this.mAvailableMileageObserver);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "onActivityResult requestCode is " + requestCode + " result code  " + resultCode);
        if (requestCode == 1 && resultCode == -1) {
            showExitDialog(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
        SoundHelper.stopAllSound();
        this.mViewModel.restoreAutoPowerOffConfig();
        this.mViewModel.setCurrentSubMode(-1);
        if (this.mVipSeat) {
            this.mViewModel.setDrvVipSeatAct(VipSeatAct.Stop);
            this.mViewModel.setPsnVipSeatAct(VipSeatAct.Stop);
        } else {
            if (isSingleSleepMode()) {
                this.mViewModel.setCapsuleSingleVipSeatAct(VipSeatAct.Stop);
            } else {
                this.mViewModel.setCapsuleVipSeatAct(VipSeatAct.Stop);
            }
            ThreadUtils.getHandler(1).removeCallbacks(this.mChangeToNightRunnable);
            this.mViewModel.restoreDayNightTry();
            this.mVcuViewModel.getDCChargeStatusData().removeObserver(this.mDCChargeObserver);
            if (this.mAvailableMileageTips) {
                this.mVcuViewModel.getAvailableMileageData().removeObserver(this.mAvailableMileageObserver);
            }
        }
        dismissDialog();
        exitSpaceActivity("stop");
        this.mScenarioViewModel.startScenario(false, this.mVipSeat ? ScenarioMode.VipSeat : isSpaceSleepMode() ? ScenarioMode.SpaceCapsuleSleep : ScenarioMode.SpaceCapsuleCinema);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
        this.mSelfExit = false;
        this.mInSubMode = false;
        unregisterReceiver(this.mScreenEventBroadCastReceiver);
    }

    private void dismissDialog() {
        XDialog xDialog = this.mExitDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mExitDialog = null;
        }
    }

    private void touchEvent(MotionEvent ev) {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.space_capsule_prepare_container);
        if (findFragmentById != null && (findFragmentById instanceof SpaceCapsuleFragment) && findFragmentById.isVisible()) {
            ((SpaceCapsuleFragment) findFragmentById).touchEvent(ev);
        }
    }

    private void exitSpaceActivity(String from) {
        LogUtils.d(TAG, "existing intent not null, move task to back " + from);
        if (!this.mVipSeat && !this.mSelfExit) {
            this.mViewModel.restoreSpaceCapsuleSettings();
            Intent intent = new Intent(this, CapsuleDialogService.class);
            intent.setAction(GlobalConstant.ACTION.ACTION_CAPSULE_PASSIVE_EXIT_SHOW);
            intent.putExtra(GlobalConstant.EXTRA.KEY_VIP_SEAT, this.mVipSeat);
            intent.putExtra(GlobalConstant.EXTRA.KEY_MODE_TYPE, this.mSubModeIndex);
            intent.putExtra(GlobalConstant.EXTRA.KEY_IN_SUB_MODE, this.mInSubMode);
            startService(intent);
        }
        if (isFinishing()) {
            return;
        }
        XActivityUtils.finish(this);
    }

    private void exitSpaceCapsule(boolean isRestoreChair) {
        this.mRestoreSeat = isRestoreChair;
        if (isRestoreChair) {
            this.mInSubMode = false;
            replaceFragment(SpaceCapsulePrepareFragment.newInstance(false, false, this.mSubModeIndex, false, this.mSleepBed));
        } else {
            this.mViewModel.restoreSpaceCapsuleSettings();
            NotificationHelper notificationHelper = NotificationHelper.getInstance();
            boolean isSpaceSleepMode = isSpaceSleepMode();
            int i = R.string.space_capsule_sleep_exit_active;
            notificationHelper.showToast(isSpaceSleepMode ? R.string.space_capsule_sleep_exit_active : R.string.space_capsule_cinema_exit_active);
            SpeechHelper speechHelper = SpeechHelper.getInstance();
            if (!isSpaceSleepMode()) {
                i = R.string.space_capsule_cinema_exit_active;
            }
            speechHelper.speak(ResUtils.getString(i));
            onSpaceCapsuleEnd();
        }
        PageEnum pageEnum = PageEnum.SPACE_CAPSULE_PAGE;
        BtnEnum btnEnum = BtnEnum.SPACE_CAPSULE_PAGE_EXIT;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(isSpaceSleepMode() ? 1 : 2);
        objArr[1] = Integer.valueOf(isRestoreChair ? 2 : 3);
        StatisticUtils.sendSpaceCapsuleStatistic(pageEnum, btnEnum, objArr);
    }

    private void showExitDialog(boolean formTts) {
        this.mSleepLayoutRecover = false;
        if (this.mExitDialog == null) {
            XDialog xDialog = new XDialog(this);
            this.mExitDialog = xDialog;
            xDialog.setMessage(R.string.space_capsule_exit_popup_msg);
            this.mExitDialog.setPositiveButton(R.string.space_capsule_chair_restore_yes);
            this.mExitDialog.setNegativeButton(R.string.space_capsule_chair_restore_no);
            this.mExitDialog.setPositiveButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$SpaceCapsuleActivity$OkNFbo-JzilN264mbdzkjmAPa7E
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SpaceCapsuleActivity.this.lambda$showExitDialog$4$SpaceCapsuleActivity(xDialog2, i);
                }
            });
            this.mExitDialog.setNegativeButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$SpaceCapsuleActivity$SiDpkFd9B1T6gliMXcRBcoBifW4
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SpaceCapsuleActivity.this.lambda$showExitDialog$5$SpaceCapsuleActivity(xDialog2, i);
                }
            });
            this.mExitDialog.setCloseVisibility(true);
        }
        this.mExitDialog.setTitle(isSpaceSleepMode() ? R.string.space_capsule_sleep_exit_popup_title : R.string.space_capsule_cinema_exit_popup_title);
        this.mExitDialog.show();
        if (formTts) {
            SpeechHelper.getInstance().speak(getString(R.string.space_capsule_chair_restore_tts));
        }
    }

    public /* synthetic */ void lambda$showExitDialog$4$SpaceCapsuleActivity(XDialog xDialog, int i) {
        exitSpaceCapsule(true);
    }

    public /* synthetic */ void lambda$showExitDialog$5$SpaceCapsuleActivity(XDialog xDialog, int i) {
        exitSpaceCapsule(false);
    }

    private boolean isSpaceSleepMode() {
        return !this.mVipSeat && this.mSubModeIndex == 1;
    }

    private boolean isSingleSleepMode() {
        return !this.mVipSeat && this.mSubModeIndex == 1 && this.mSleepBed == 1;
    }

    private void startCinemaGuide() {
        Intent intent = new Intent(GlobalConstant.ACTION.ACTION_START_CINEMA_USE_GUIDE);
        intent.addFlags(268468224);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_CINEMA_GUIDE_FLAT, true);
        intent.addFlags(1024);
        startActivity(intent);
    }

    private void startSleepGuide(boolean enter) {
        Intent intent = new Intent(GlobalConstant.ACTION.ACTION_SPACE_CAPSULE_GUIDE);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_SLEEP_GUIDE_FLAT, enter);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_SLEEP_GUIDE_AUTO, true);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, this.mSleepBed);
        startActivity(intent);
    }

    private void startSleepGuideOnExitForResult() {
        Intent intent = new Intent(GlobalConstant.ACTION.ACTION_SPACE_CAPSULE_GUIDE);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_SLEEP_GUIDE_FLAT, false);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_SLEEP_GUIDE_AUTO, true);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, this.mSleepBed);
        startActivityForResult(intent, 1);
    }
}
