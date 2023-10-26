package com.xiaopeng.carcontrol.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.direct.message.DirectHvacEventMsg;
import com.xiaopeng.carcontrol.speech.HvacControlSpeechModel;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.control.BaseViewControl;
import com.xiaopeng.carcontrol.view.control.hvac.HvacFuncViewControl;
import com.xiaopeng.carcontrol.view.control.hvac.HvacNavViewControl;
import com.xiaopeng.carcontrol.view.control.hvac.HvacVentViewControl;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacWindBlowMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.listener.IVuiEventListener;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.app.delegate.XActivityBind;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@XActivityBind(1)
/* loaded from: classes2.dex */
public class HvacActivity extends XActivity implements IVuiSceneListener, IVuiElementListener {
    public static final String SCENE_ID = "hvac";
    private static final String TAG = "HvacActivity";
    public static List<Integer> VUI_IDS = new ArrayList();
    Runnable exitImmerseRun = new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$HvacActivity$wHht7prvMyQ05aj1ZdywkbpORKk
        @Override // java.lang.Runnable
        public final void run() {
            HvacActivity.this.exitImmerseMode();
        }
    };
    private Connect2CarTask mConnect2CarTask;
    private ConstraintLayout mHvacContainer;
    private HvacFuncViewControl mHvacFuncViewControl;
    private HvacVentViewControl mHvacVentViewControl;
    private HvacNavViewControl mHvacViewControl;
    private Handler mImmerseHandler;
    private int mImmerseType;
    private boolean mIsConnected2CarSvc;
    private ScenarioViewModel mScenarioViewModel;
    private float mTouchDownX;
    private float mTouchDownY;
    private HvacViewModel mViewModel;

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent event) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        StartPerfUtils.onCreateBegin();
        HvacControlSpeechModel.IS_HVAC_PANEL_SHOWING = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hvac);
        if (BaseFeatureOption.getInstance().isSupportFullscreenPanel()) {
            getWindowAttributes().setGravity(17);
        }
        if (getIntent() != null && (getIntent().getFlags() & 1024) == 1024) {
            getWindowAttributes().setGravity(17).setFlags(1024).setSystemUiVisibility(1538);
        }
        this.mHvacViewControl = new HvacNavViewControl();
        this.mHvacFuncViewControl = new HvacFuncViewControl();
        this.mHvacVentViewControl = new HvacVentViewControl();
        this.mHvacViewControl.onCreate(this, findViewById(R.id.common_nav_view));
        this.mHvacFuncViewControl.onCreate(this, findViewById(R.id.common_func_view));
        this.mHvacVentViewControl.onCreate(this, findViewById(R.id.common_vent_control));
        getWindowVisible().setAutoVisibleEnableOnPause(true);
        this.mHvacContainer = (ConstraintLayout) findViewById(R.id.hvac_container);
        VuiEngine.getInstance(getApplicationContext()).addVuiSceneListener("hvac", this.mHvacContainer, this);
        VuiEngine.getInstance(getApplicationContext()).addVuiEventListener("hvac", new IVuiEventListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$HvacActivity$M3dbGh0cPOdFismpsO2Ojwt0piA
            @Override // com.xiaopeng.speech.vui.listener.IVuiEventListener
            public final void onVuiEventExecutioned() {
                HvacActivity.this.lambda$onCreate$0$HvacActivity();
            }
        });
        if (this.mConnect2CarTask == null) {
            this.mConnect2CarTask = new Connect2CarTask();
        }
        this.mConnect2CarTask.execute(new Void[0]);
        StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PAGE, BtnEnum.HVAC_PANEL_EXPAND, new Object[0]);
    }

    public /* synthetic */ void lambda$onCreate$0$HvacActivity() {
        LogUtils.d(TAG, "dispatchUserEvent when vui event done", false);
        dispatchUserEvent();
    }

    /* loaded from: classes2.dex */
    class Connect2CarTask extends AsyncTask<Void, Void, Void> {
        Connect2CarTask() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void[] objects) {
            LogUtils.d(HvacActivity.TAG, "Check if car service connected");
            if (CarClientWrapper.getInstance().isCarServiceConnected()) {
                HvacActivity.this.mIsConnected2CarSvc = true;
            }
            LogUtils.d(HvacActivity.TAG, "mIsConnected2CarSvc:" + HvacActivity.this.mIsConnected2CarSvc);
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void o) {
            HvacActivity.this.mViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
            HvacActivity.this.mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
            HvacActivity.this.initView();
            HvacActivity.this.mConnect2CarTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(DirectHvacEventMsg event) {
        HvacFuncViewControl hvacFuncViewControl;
        Uri url = event.getUrl();
        LogUtils.i(TAG, "Receive DirectEventMsg: " + url);
        if (url == null || !url.getPath().contains(BaseViewControl.ACTION_SHOW_SEAT_HEAT_VENT_DIRECT) || (hvacFuncViewControl = this.mHvacFuncViewControl) == null) {
            return;
        }
        hvacFuncViewControl.showSeatHeatVentDialog();
    }

    @Override // android.app.Activity
    protected void onRestart() {
        StartPerfUtils.onReStartBegin();
        super.onRestart();
        StartPerfUtils.onReStartEnd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        StartPerfUtils.onStartBegin();
        super.onStart();
        EventBus.getDefault().register(this);
        this.mHvacVentViewControl.onStart();
        StartPerfUtils.onStartEnd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        StartPerfUtils.onResumeBegin();
        super.onResume();
        StartPerfUtils.onResumeEnd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        VuiEngine.getInstance(getApplicationContext()).exitScene("hvac");
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        this.mHvacVentViewControl.onStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        HvacControlSpeechModel.IS_HVAC_PANEL_SHOWING = false;
        super.onDestroy();
        Connect2CarTask connect2CarTask = this.mConnect2CarTask;
        if (connect2CarTask != null && !connect2CarTask.isCancelled()) {
            this.mConnect2CarTask.cancel(true);
        }
        this.mHvacViewControl.onDestroy();
        this.mHvacFuncViewControl.onDestroy();
        this.mHvacVentViewControl.onDestroy();
        VuiEngine.getInstance(getApplicationContext()).removeVuiSceneListener("hvac", this);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView() {
        this.mHvacViewControl.initView();
        this.mHvacFuncViewControl.initView();
        this.mHvacVentViewControl.initView();
        this.mViewModel.getHvacQualityPurgeData().observe(this, new Observer() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$HvacActivity$82Lq5fQuFthNBEEgMcS-F4MYwko
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HvacActivity.this.lambda$initView$1$HvacActivity((Boolean) obj);
            }
        });
        initBottomMenu();
        this.mScenarioViewModel.getScenarioStateData().observe(this, new Observer() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$HvacActivity$S0twcw6DePOaXZWP3tD7xjsDznc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HvacActivity.this.lambda$initView$2$HvacActivity((Boolean) obj);
            }
        });
        VuiEngine.getInstance(getApplicationContext()).enterScene("hvac");
    }

    public /* synthetic */ void lambda$initView$1$HvacActivity(Boolean aBoolean) {
        this.mHvacFuncViewControl.onAirPurgeChanged();
        this.mHvacVentViewControl.onIonizerChanged();
    }

    public /* synthetic */ void lambda$initView$2$HvacActivity(Boolean aBoolean) {
        if (aBoolean == null || aBoolean.booleanValue() || getIntent() == null || (getIntent().getFlags() & 1024) != 1024) {
            return;
        }
        finish();
    }

    @Override // com.xiaopeng.xui.app.XActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent ev) {
        HvacFuncViewControl hvacFuncViewControl;
        if (ev.getActionMasked() == 0 && (hvacFuncViewControl = this.mHvacFuncViewControl) != null) {
            hvacFuncViewControl.analyzeXFreeBreathPanel(ev.getX(), ev.getY());
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // com.xiaopeng.xui.app.XActivity, android.app.Activity
    public boolean onTouchEvent(MotionEvent event) {
        onImmerseTouchEvent(event);
        if (this.mImmerseType != 0) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void initBottomMenu() {
        this.mViewModel.getHvacPowerData().observe(this, new Observer() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$HvacActivity$rIh87xICZBX_Apg1GjBFIhRHdDc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HvacActivity.this.lambda$initBottomMenu$3$HvacActivity((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initBottomMenu$3$HvacActivity(Boolean aBoolean) {
        this.mHvacViewControl.onHvacPowerChange();
        this.mHvacVentViewControl.setFanSpeed(this.mViewModel.getHvacWindSpeedLevel());
        HvacVentViewControl hvacVentViewControl = this.mHvacVentViewControl;
        if (hvacVentViewControl != null) {
            hvacVentViewControl.onPowerChanged();
        }
        HvacVentViewControl hvacVentViewControl2 = this.mHvacVentViewControl;
        if (hvacVentViewControl2 != null) {
            hvacVentViewControl2.onBlowModeChanged();
        }
    }

    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ThemeManager.create(getApplicationContext(), getWindow().getDecorView(), "hvac_activity.xml", null).onConfigurationChanged(newConfig);
        if (XThemeManager.isThemeChanged(newConfig)) {
            this.mHvacVentViewControl.onConfigurationChanged(newConfig);
            this.mHvacViewControl.onConfigurationChanged(newConfig);
            this.mHvacFuncViewControl.onConfigurationChanged(newConfig);
        }
    }

    public void onImmerseTouchEvent(MotionEvent event) {
        int i;
        int action = event.getAction();
        if (action == 0) {
            LogUtils.d(TAG, "onImmerseTouchEvent down:" + this.mImmerseType);
            Handler handler = this.mImmerseHandler;
            if (handler != null) {
                handler.removeCallbacks(this.exitImmerseRun);
            }
            if (this.mImmerseType == 0) {
                this.mTouchDownX = event.getX();
                this.mTouchDownY = event.getY();
                return;
            }
            return;
        }
        if (action != 1) {
            if (action == 2) {
                int i2 = this.mImmerseType;
                if (i2 == 0) {
                    float x = event.getX() - this.mTouchDownX;
                    float y = event.getY() - this.mTouchDownY;
                    if (Math.abs(x) > 50.0f) {
                        this.mImmerseType = 1;
                    } else if (Math.abs(y) > 50.0f) {
                        if (this.mTouchDownX < (getWindow().getDecorView().getWidth() >> 1)) {
                            this.mImmerseType = 2;
                        } else {
                            this.mImmerseType = 3;
                        }
                    }
                    if (this.mImmerseType != 0) {
                        this.mTouchDownX = event.getX();
                        this.mTouchDownY = event.getY();
                        intoImmerseMode();
                        return;
                    }
                    return;
                } else if (i2 == 1) {
                    float x2 = event.getX() - this.mTouchDownX;
                    if (Math.abs(x2) > 50.0f) {
                        this.mTouchDownX = event.getX();
                        i = x2 <= 0.0f ? -1 : 1;
                        HvacVentViewControl hvacVentViewControl = this.mHvacVentViewControl;
                        if (hvacVentViewControl != null) {
                            hvacVentViewControl.setFanSpeedLevel(i);
                            return;
                        }
                        return;
                    }
                    return;
                } else if (i2 == 2) {
                    float y2 = event.getY() - this.mTouchDownY;
                    if (Math.abs(y2) > 100.0f) {
                        this.mTouchDownY = event.getY();
                        i = y2 > 0.0f ? -1 : 1;
                        HvacFuncViewControl hvacFuncViewControl = this.mHvacFuncViewControl;
                        if (hvacFuncViewControl != null) {
                            hvacFuncViewControl.drvTempSmoothNew(i);
                            return;
                        }
                        return;
                    }
                    return;
                } else if (i2 == 3) {
                    float y3 = event.getY() - this.mTouchDownY;
                    if (Math.abs(y3) > 100.0f) {
                        this.mTouchDownY = event.getY();
                        i = y3 > 0.0f ? -1 : 1;
                        HvacFuncViewControl hvacFuncViewControl2 = this.mHvacFuncViewControl;
                        if (hvacFuncViewControl2 != null) {
                            hvacFuncViewControl2.psnTempSmoothNew(i);
                            return;
                        }
                        return;
                    }
                    return;
                } else {
                    return;
                }
            } else if (action != 3) {
                return;
            }
        }
        LogUtils.d(TAG, "onImmerseTouchEvent up:" + this.mImmerseType);
        if (this.mImmerseType != 0) {
            exitImmerseHandler();
        }
    }

    private void exitImmerseHandler() {
        if (this.mImmerseHandler == null) {
            this.mImmerseHandler = new Handler();
        }
        this.mImmerseHandler.removeCallbacks(this.exitImmerseRun);
        this.mImmerseHandler.postDelayed(this.exitImmerseRun, 100L);
    }

    private void intoImmerseMode() {
        HvacNavViewControl hvacNavViewControl = this.mHvacViewControl;
        if (hvacNavViewControl != null) {
            hvacNavViewControl.intoImmerseMode();
            this.mHvacFuncViewControl.intoImmerseMode(this.mImmerseType);
            this.mHvacVentViewControl.intoImmerseMode(this.mImmerseType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitImmerseMode() {
        HvacNavViewControl hvacNavViewControl = this.mHvacViewControl;
        if (hvacNavViewControl != null) {
            hvacNavViewControl.exitImmerseMode();
            this.mHvacFuncViewControl.exitImmerseMode(this.mImmerseType);
            this.mHvacVentViewControl.exitImmerseMode(this.mImmerseType);
        }
        this.mImmerseType = 0;
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent event) {
        LogUtils.d(TAG, "onVuiEvent:" + view.getId(), false);
        VuiElement hitVuiElement = event.getHitVuiElement();
        if (hitVuiElement == null || TextUtils.isEmpty(hitVuiElement.getId())) {
            return;
        }
        this.mHvacFuncViewControl.onVuiEvent(view, event);
        this.mHvacViewControl.onVuiEvent(view, event);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent event) {
        if (view != null) {
            LogUtils.d(TAG, "onInterceptVuiEvent:" + view.getId() + ",faceId:" + R.id.img_blow_face + ",footId:" + R.id.img_blow_foot + ",winId:" + R.id.img_blow_win + ",faceFoot:" + R.id.img_blow_face_foot + ",winFoot:" + R.id.img_blow_window_foot + ",value:" + event.getEventValue(event), false);
            if (view.getId() == R.id.img_blow_face) {
                if (!((Boolean) event.getEventValue(event)).booleanValue()) {
                    this.mViewModel.closeHvacWindBlowFace();
                    return true;
                }
            } else if (view.getId() == R.id.img_blow_foot) {
                if (!((Boolean) event.getEventValue(event)).booleanValue()) {
                    this.mViewModel.closeHvacWindBlowFoot();
                    return true;
                }
            } else if (view.getId() == R.id.img_blow_win) {
                if (!((Boolean) event.getEventValue(event)).booleanValue()) {
                    this.mViewModel.closeHvacWindBlowWin();
                    return true;
                }
            } else if (view.getId() == R.id.img_blow_face_foot) {
                if (!((Boolean) event.getEventValue(event)).booleanValue()) {
                    this.mViewModel.setHvacWindMode(HvacWindBlowMode.Foot);
                    return true;
                }
            } else if (view.getId() == R.id.img_blow_window_foot && !((Boolean) event.getEventValue(event)).booleanValue()) {
                this.mViewModel.setHvacWindMode(HvacWindBlowMode.FaceAndFoot);
                return true;
            }
            showVuiFloatingLayer(view);
        }
        return false;
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onBuildScene() {
        VUI_IDS.clear();
        this.mHvacFuncViewControl.onBuildScene(VUI_IDS);
        this.mHvacViewControl.onBuildScene(VUI_IDS);
        VuiEngine.getInstance(getApplicationContext()).buildScene("hvac", this.mHvacContainer, VUI_IDS, this);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String vuiElementId, IVuiElementBuilder builder) {
        VuiElement onBuildVuiElement = this.mHvacFuncViewControl.onBuildVuiElement(vuiElementId, builder);
        return onBuildVuiElement == null ? this.mHvacViewControl.onBuildVuiElement(vuiElementId, builder) : onBuildVuiElement;
    }

    private void showVuiFloatingLayer(View view) {
        if (view != null) {
            VuiFloatingLayerManager.show(view);
        }
    }
}
