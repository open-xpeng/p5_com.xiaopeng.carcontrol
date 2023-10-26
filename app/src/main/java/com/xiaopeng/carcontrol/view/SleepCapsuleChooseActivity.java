package com.xiaopeng.carcontrol.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioMode;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioSmartControl;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.utils.XActivityUtils;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XRelativeLayout;

/* loaded from: classes2.dex */
public class SleepCapsuleChooseActivity extends XActivity implements View.OnClickListener {
    private static final String TAG = "SleepCapsuleChooseActivity";
    private XRelativeLayout mDoubleBedMode;
    private ScenarioViewModel mScenarioViewModel;
    private XRelativeLayout mSingleBedMode;
    private XButton mStartBtn;
    private boolean mVipSeat;
    private boolean mVoiceSource;
    private int mChooseBedType = -1;
    FinishReceiver mFinishReceiver = new FinishReceiver();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        StartPerfUtils.onCreateBegin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_capsule_choose);
        getWindowAttributes().setGravity(17).setFlags(1024).setSystemUiVisibility(1538);
        findViewById(R.id.close_iv).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$SleepCapsuleChooseActivity$RyOA7nJVSlsC_C5598uBMEP2Cpw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SleepCapsuleChooseActivity.this.lambda$onCreate$0$SleepCapsuleChooseActivity(view);
            }
        });
        XButton xButton = (XButton) findViewById(R.id.capsule_sleep_start);
        this.mStartBtn = xButton;
        xButton.setOnClickListener(this);
        this.mStartBtn.setEnabled(false);
        XRelativeLayout xRelativeLayout = (XRelativeLayout) findViewById(R.id.capsule_sleep_double_image);
        this.mDoubleBedMode = xRelativeLayout;
        xRelativeLayout.setOnClickListener(this);
        this.mDoubleBedMode.setBackgroundResource(R.drawable.sleep_capsule_double_bed);
        XRelativeLayout xRelativeLayout2 = (XRelativeLayout) findViewById(R.id.capsule_sleep_single_image);
        this.mSingleBedMode = xRelativeLayout2;
        xRelativeLayout2.setOnClickListener(this);
        this.mSingleBedMode.setBackgroundResource(R.drawable.sleep_capsule_single_bed);
        this.mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
        processIntent(getIntent());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalConstant.ACTION.ACTION_SPACE_CAPSULE_CHOOSE_SLEEP_FINISH);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mFinishReceiver, intentFilter);
        StartPerfUtils.onCreateEnd();
    }

    public /* synthetic */ void lambda$onCreate$0$SleepCapsuleChooseActivity(View v) {
        this.mScenarioViewModel.startScenario(false, ScenarioMode.SpaceCapsuleSleep);
        XActivityUtils.finish(this);
    }

    /* loaded from: classes2.dex */
    class FinishReceiver extends BroadcastReceiver {
        FinishReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            LogUtils.d(SleepCapsuleChooseActivity.TAG, "sleep choose finish Receiver " + intent.getAction());
            XActivityUtils.finish(SleepCapsuleChooseActivity.this);
        }
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
        if (intent == null) {
            return;
        }
        this.mVipSeat = intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_VIP_SEAT, false);
        this.mVoiceSource = intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_SPACE_SOURCE, false);
        LogUtils.d(TAG, "processIntent mVipSeat:" + this.mVipSeat + " mVoiceSource:" + this.mVoiceSource);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capsule_sleep_double_image /* 2131361995 */:
                selectOne(this.mDoubleBedMode);
                this.mChooseBedType = 2;
                return;
            case R.id.capsule_sleep_single_image /* 2131362019 */:
                selectOne(this.mSingleBedMode);
                this.mChooseBedType = 1;
                return;
            case R.id.capsule_sleep_start /* 2131362020 */:
                LogUtils.d(TAG, " mChooseBedType:" + this.mChooseBedType);
                ScenarioSmartControl.getInstance().startCapsuleActivity(true, this.mVipSeat, this.mVoiceSource, 1, null, this.mChooseBedType);
                XActivityUtils.finish(this);
                return;
            default:
                return;
        }
    }

    private void selectOne(View selectView) {
        this.mSingleBedMode.setBackgroundResource(R.drawable.capsule_sleep_single_mode_bg_selector);
        this.mDoubleBedMode.setBackgroundResource(R.drawable.capsule_sleep_double_mode_bg_selector);
        this.mStartBtn.setEnabled(true);
        XRelativeLayout xRelativeLayout = this.mDoubleBedMode;
        xRelativeLayout.setSelected(selectView == xRelativeLayout);
        XRelativeLayout xRelativeLayout2 = this.mSingleBedMode;
        xRelativeLayout2.setSelected(selectView == xRelativeLayout2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mFinishReceiver);
    }
}
