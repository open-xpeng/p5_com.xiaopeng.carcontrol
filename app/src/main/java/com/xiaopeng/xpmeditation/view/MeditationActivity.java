package com.xiaopeng.xpmeditation.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioMode;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xpmeditation.util.TimeUtil;
import com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel;
import com.xiaopeng.xpmeditation.viewModel.MeditationViewModel;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.utils.XActivityUtils;

/* loaded from: classes2.dex */
public class MeditationActivity extends XActivity {
    private static final String TAG = "MeditationActivity";
    private BroadcastReceiver mFinishReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.xpmeditation.view.MeditationActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.d(MeditationActivity.TAG, "BroadcastReceiver: " + action);
            if ("android.intent.action.TIME_TICK".equals(action)) {
                if (MeditationActivity.this.mLayout != null) {
                    MeditationActivity.this.mLayout.refreshTime(TimeUtil.parseTimeHMWithGMT(System.currentTimeMillis()));
                }
            } else if (GlobalConstant.ACTION.ACTION_MEDITATION_MODE_EXIT.equals(action)) {
                XActivityUtils.finish(MeditationActivity.this);
            }
        }
    };
    private HvacViewModel mHvacViewModel;
    private MeditationLayout mLayout;
    private MeditationViewModel mMeditationViewModel;
    private ScenarioViewModel mScenarioViewModel;
    private SeatViewModel mSeatViewModel;
    private WindowDoorViewModel mWindowDoorViewModel;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(14);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_meditation_activity_meditation);
        initView();
        initViewMode();
        processIntent(getIntent());
        LogUtils.i(TAG, "onCreate: ====================");
    }

    private void initView() {
        this.mLayout = (MeditationLayout) findViewById(R.id.layout_main);
    }

    private void initViewMode() {
        this.mMeditationViewModel = (MeditationViewModel) ViewModelManager.getInstance().getViewModelImpl(IMeditationViewModel.class);
        this.mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
        this.mHvacViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
        this.mWindowDoorViewModel = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
        this.mSeatViewModel = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        setLiveDataObserver(this.mMeditationViewModel.getPlayIndexData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationActivity$S8qx0hHUjAAi2QzE4JNQ9Zg7yUI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationActivity.this.lambda$initViewMode$0$MeditationActivity((Integer) obj);
            }
        });
        setLiveDataObserver(this.mMeditationViewModel.getPlayStateData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationActivity$HTzFZJvRDs5E5hAPqKxr38SZcf0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationActivity.this.lambda$initViewMode$1$MeditationActivity((Integer) obj);
            }
        });
        this.mMeditationViewModel.init();
    }

    public /* synthetic */ void lambda$initViewMode$0$MeditationActivity(Integer index) {
        MeditationLayout meditationLayout;
        LogUtils.d(TAG, "mViewModel.getPlayIndexData(): " + index);
        if (index == null || (meditationLayout = this.mLayout) == null) {
            return;
        }
        meditationLayout.updatePlayIndex(index.intValue());
    }

    public /* synthetic */ void lambda$initViewMode$1$MeditationActivity(Integer state) {
        MeditationLayout meditationLayout;
        LogUtils.d(TAG, "mViewModel.getPlayStateData(): " + state);
        if (state == null || (meditationLayout = this.mLayout) == null) {
            return;
        }
        meditationLayout.updatePlayState(state.intValue());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processIntent(getIntent());
    }

    private void processIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        if (intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_MEDITATION_MODE_STATUS, false)) {
            this.mScenarioViewModel.registerBinderObserver();
            this.mScenarioViewModel.reportScenarioStatus(ScenarioMode.Meditation.toScenarioStr(), 2);
            ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationActivity$UUEewuGiU2TBRmd3OYjHXJ7pvd0
                @Override // java.lang.Runnable
                public final void run() {
                    MeditationActivity.this.lambda$processIntent$2$MeditationActivity();
                }
            }, 1000L);
            return;
        }
        XActivityUtils.finish(this);
    }

    public /* synthetic */ void lambda$processIntent$2$MeditationActivity() {
        if (!this.mHvacViewModel.isHvacPowerModeOn()) {
            this.mHvacViewModel.setHvacPowerMode(true);
        }
        this.mSeatViewModel.enterMeditationState(CarBaseConfig.getInstance().getMeditationSeatPos());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        registerFinishReceiver();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        unregisterReceiver(this.mFinishReceiver);
        this.mSeatViewModel.exitMeditationState();
        if (!isFinishing()) {
            XActivityUtils.finish(this);
        }
        this.mScenarioViewModel.startScenario(false, ScenarioMode.Meditation);
        this.mMeditationViewModel.release();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == 4 || event.getKeyCode() == 3) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (r2 != 1014) goto L9;
     */
    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(int r2, android.view.KeyEvent r3) {
        /*
            r1 = this;
            r3 = 87
            if (r2 == r3) goto L17
            r3 = 88
            if (r2 == r3) goto L11
            r3 = 1013(0x3f5, float:1.42E-42)
            if (r2 == r3) goto L11
            r3 = 1014(0x3f6, float:1.421E-42)
            if (r2 == r3) goto L17
            goto L1c
        L11:
            com.xiaopeng.xpmeditation.viewModel.MeditationViewModel r3 = r1.mMeditationViewModel
            r3.playPre()
            goto L1c
        L17:
            com.xiaopeng.xpmeditation.viewModel.MeditationViewModel r3 = r1.mMeditationViewModel
            r3.playNext()
        L1c:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r0 = "onKeyDown: ==============="
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "MeditationActivity"
            com.xiaopeng.carcontrol.util.LogUtils.i(r3, r2)
            r2 = 1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.xpmeditation.view.MeditationActivity.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    private void registerFinishReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalConstant.ACTION.ACTION_MEDITATION_MODE_EXIT);
        intentFilter.addAction("android.intent.action.TIME_TICK");
        registerReceiver(this.mFinishReceiver, intentFilter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        liveData.observe(this, observer);
    }
}
