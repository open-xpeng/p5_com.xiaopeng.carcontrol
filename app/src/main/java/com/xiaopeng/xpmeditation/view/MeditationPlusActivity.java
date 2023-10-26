package com.xiaopeng.xpmeditation.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioMode;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xpmeditation.view.plus.MeditationPlusScene;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.utils.XActivityUtils;

/* loaded from: classes2.dex */
public class MeditationPlusActivity extends XActivity {
    private static final String TAG = "MeditationPlusActivity";
    private BroadcastReceiver mFinishReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.xpmeditation.view.MeditationPlusActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.TIME_TICK".equals(action)) {
                MeditationPlusActivity.this.mMeditationScene.refreshTime(System.currentTimeMillis());
            } else if (GlobalConstant.ACTION.ACTION_MEDITATION_MODE_EXIT.equals(action)) {
                LogUtils.d(MeditationPlusActivity.TAG, "BroadcastReceiver: " + action);
                XActivityUtils.finish(MeditationPlusActivity.this);
            }
        }
    };
    private BaseMeditationScene mMeditationScene;
    private ScenarioViewModel mScenarioViewModel;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(14);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_meditation_activity_meditation_p);
        initView();
        this.mMeditationScene.onCreate();
        this.mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
        processIntent(getIntent());
        LogUtils.i(TAG, "onCreate: ====================");
    }

    private void initView() {
        this.mMeditationScene = new MeditationPlusScene(findViewById(R.id.meditation_content), this);
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
            this.mMeditationScene.preEnterMeditation();
            return;
        }
        XActivityUtils.finish(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.mMeditationScene.onResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.mMeditationScene.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        this.mMeditationScene.onStart();
        registerFinishReceiver();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        this.mMeditationScene.onStop();
        unregisterReceiver(this.mFinishReceiver);
        if (isFinishing()) {
            return;
        }
        XActivityUtils.finish(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mMeditationScene.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == 4 || event.getKeyCode() == 3) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override // com.xiaopeng.xui.app.XActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.mMeditationScene.onTouch(ev);
        return super.dispatchTouchEvent(ev);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0011, code lost:
        if (r2 != 1014) goto L11;
     */
    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(int r2, android.view.KeyEvent r3) {
        /*
            r1 = this;
            r3 = 4
            if (r2 == r3) goto L20
            r3 = 87
            if (r2 == r3) goto L1a
            r3 = 88
            if (r2 == r3) goto L14
            r3 = 1013(0x3f5, float:1.42E-42)
            if (r2 == r3) goto L14
            r3 = 1014(0x3f6, float:1.421E-42)
            if (r2 == r3) goto L1a
            goto L25
        L14:
            com.xiaopeng.xpmeditation.view.BaseMeditationScene r3 = r1.mMeditationScene
            r3.playPre()
            goto L25
        L1a:
            com.xiaopeng.xpmeditation.view.BaseMeditationScene r3 = r1.mMeditationScene
            r3.playNext()
            goto L25
        L20:
            com.xiaopeng.xpmeditation.view.BaseMeditationScene r3 = r1.mMeditationScene
            r3.onClose()
        L25:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r0 = "onKeyDown: ==============="
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "MeditationPlusActivity"
            com.xiaopeng.carcontrol.util.LogUtils.i(r3, r2)
            r2 = 1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.xpmeditation.view.MeditationPlusActivity.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    private void registerFinishReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalConstant.ACTION.ACTION_MEDITATION_MODE_EXIT);
        intentFilter.addAction("android.intent.action.TIME_TICK");
        registerReceiver(this.mFinishReceiver, intentFilter);
    }
}
