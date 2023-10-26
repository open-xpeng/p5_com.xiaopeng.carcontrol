package com.xiaopeng.carcontrol.view.control.hvac;

import android.app.ActivityManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.control.BaseViewControl;
import com.xiaopeng.carcontrol.view.control.hvac.vent.MyGLSurfaceView;
import com.xiaopeng.carcontrol.view.control.hvac.vent.WindRenderer;
import com.xiaopeng.carcontrol.view.widget.AirLevelSeekBar;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacWindBlowMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.VuiEngine;

/* loaded from: classes2.dex */
public class HvacVentViewControl extends BaseViewControl {
    private static final long LONG_CLICK_TIME = 300;
    private static final String TAG = "HvacVentViewControl";
    private Runnable fanSpeedRun = new Runnable() { // from class: com.xiaopeng.carcontrol.view.control.hvac.HvacVentViewControl.2
        @Override // java.lang.Runnable
        public void run() {
            HvacVentViewControl.this.mIsFanSpeedLongClick = true;
            if (HvacVentViewControl.this.mIsControlFanSpeedMin) {
                HvacVentViewControl.this.mViewModel.setHvacWindSpeedDown();
            } else {
                HvacVentViewControl.this.mViewModel.setHvacWindSpeedUp();
            }
            HvacVentViewControl.this.mFanSpeedHandler.postDelayed(HvacVentViewControl.this.fanSpeedRun, HvacVentViewControl.LONG_CLICK_TIME);
        }
    };
    private Handler mFanSpeedHandler;
    private AirLevelSeekBar mFanSpeedSeekBar;
    private MyGLSurfaceView mGLSurfaceView;
    private ImageView mImgFanSpeedAdd;
    private ImageView mImgFanSpeedMinus;
    private boolean mIsControlFanSpeedMin;
    private boolean mIsFanSpeedLongClick;
    private boolean mRendererSet;
    private HvacViewModel mViewModel;
    private BlowModeView mWindModeTabView;
    private WindRenderer mWindRenderer;

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void onCreate(AppCompatActivity activity, View view) {
        super.onCreate(activity, view);
        initVentView();
    }

    private void initVentView() {
        MyGLSurfaceView myGLSurfaceView = (MyGLSurfaceView) this.mRootView.findViewById(R.id.bg_surface_view);
        this.mGLSurfaceView = myGLSurfaceView;
        myGLSurfaceView.setPreserveEGLContextOnPause(true);
        if (supportsEs2()) {
            this.mRendererSet = true;
            this.mGLSurfaceView.setEGLContextClientVersion(2);
            WindRenderer windRenderer = new WindRenderer(this.mActivity);
            this.mWindRenderer = windRenderer;
            this.mGLSurfaceView.setRenderer(windRenderer);
            return;
        }
        this.mGLSurfaceView.setVisibility(8);
        this.mRootView.setBackgroundResource(R.drawable.bg_pressed);
        LogUtils.e(TAG, "Device unSupport ES20");
    }

    private boolean supportsEs2() {
        return ((ActivityManager) this.mActivity.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getDeviceConfigurationInfo().reqGlEsVersion >= 131072 || Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86");
    }

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void initView() {
        super.initView();
        this.mViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
        initFanSpeed();
        initBlowMode();
        onIonizerChanged();
        onPowerChanged();
    }

    private void initFanSpeed() {
        ImageView imageView = (ImageView) this.mRootView.findViewById(R.id.img_fan_speed_minus);
        this.mImgFanSpeedMinus = imageView;
        imageView.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacVentViewControl$My9fFfxsQmHVAXPaqvwXgVu9UTQ
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return HvacVentViewControl.this.lambda$initFanSpeed$0$HvacVentViewControl(view, motionEvent);
            }
        });
        ImageView imageView2 = (ImageView) this.mRootView.findViewById(R.id.img_fan_speed_add);
        this.mImgFanSpeedAdd = imageView2;
        imageView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacVentViewControl$Ax2ZN_ufuQVgzcieL82tdHfoxfs
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return HvacVentViewControl.this.lambda$initFanSpeed$1$HvacVentViewControl(view, motionEvent);
            }
        });
        AirLevelSeekBar airLevelSeekBar = (AirLevelSeekBar) this.mRootView.findViewById(R.id.fan_speed_seek_bar);
        this.mFanSpeedSeekBar = airLevelSeekBar;
        airLevelSeekBar.setOnVolumeChangeListener(new AirLevelSeekBar.OnVolumeChangeListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.HvacVentViewControl.1
            @Override // com.xiaopeng.carcontrol.view.widget.AirLevelSeekBar.OnVolumeChangeListener
            public void onTouched() {
            }

            @Override // com.xiaopeng.carcontrol.view.widget.AirLevelSeekBar.OnVolumeChangeListener
            public void onVolumeChanged(int volume) {
                HvacVentViewControl.this.mViewModel.setHvacWindSpeedLevel(volume);
                StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PAGE, BtnEnum.WIND_SPEED_BTN, Integer.valueOf(volume));
            }
        });
        setFanSpeed(this.mViewModel.getHvacWindSpeedLevel());
        this.mViewModel.getHvacWindLevelData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacVentViewControl$vALuTs4xVIdieu_DF-tDzLd5KFs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HvacVentViewControl.this.lambda$initFanSpeed$2$HvacVentViewControl((Integer) obj);
            }
        });
    }

    public /* synthetic */ boolean lambda$initFanSpeed$0$HvacVentViewControl(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == 0) {
            this.mIsControlFanSpeedMin = true;
            this.mIsFanSpeedLongClick = false;
            startControlFanSpeed();
        } else if (action == 1 || action == 3) {
            stopControlFanSpeed();
            if (!this.mIsFanSpeedLongClick) {
                this.mViewModel.setHvacWindSpeedDown();
                v.playSoundEffect(0);
            }
        }
        return true;
    }

    public /* synthetic */ boolean lambda$initFanSpeed$1$HvacVentViewControl(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == 0) {
            this.mIsControlFanSpeedMin = false;
            this.mIsFanSpeedLongClick = false;
            startControlFanSpeed();
        } else if (action == 1 || action == 3) {
            stopControlFanSpeed();
            if (!this.mIsFanSpeedLongClick) {
                this.mViewModel.setHvacWindSpeedUp();
                v.playSoundEffect(0);
            }
        }
        return true;
    }

    public /* synthetic */ void lambda$initFanSpeed$2$HvacVentViewControl(Integer integer) {
        setFanSpeed(this.mViewModel.getHvacWindSpeedLevel());
    }

    private void startControlFanSpeed() {
        if (this.mFanSpeedHandler == null) {
            this.mFanSpeedHandler = new Handler();
        }
        this.mFanSpeedHandler.removeCallbacks(this.fanSpeedRun);
        this.mFanSpeedHandler.postDelayed(this.fanSpeedRun, LONG_CLICK_TIME);
    }

    private void stopControlFanSpeed() {
        Handler handler = this.mFanSpeedHandler;
        if (handler != null) {
            handler.removeCallbacks(this.fanSpeedRun);
        }
    }

    public void setFanSpeed(int level) {
        if (this.mViewModel.isHvacPowerModeOn()) {
            if (level >= 1 && level <= this.mViewModel.getFanMaxLevel()) {
                this.mFanSpeedSeekBar.setCurrentVolume(level);
                if (this.mRendererSet) {
                    this.mWindRenderer.setSpeed(level);
                }
            }
        } else {
            this.mFanSpeedSeekBar.setCurrentVolume(0);
        }
        VuiEngine.getInstance(this.mActivity.getApplicationContext()).updateScene("hvac", this.mFanSpeedSeekBar);
    }

    private void initBlowMode() {
        BlowModeView blowModeView = (BlowModeView) this.mRootView.findViewById(R.id.wind_mode_tab_view);
        this.mWindModeTabView = blowModeView;
        blowModeView.initView();
        onBlowModeChanged();
        this.mViewModel.getHvacWindModeData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacVentViewControl$AbeHNLF4Lf0UVhqTB_st89lmKYQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HvacVentViewControl.this.lambda$initBlowMode$3$HvacVentViewControl((HvacWindBlowMode) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initBlowMode$3$HvacVentViewControl(HvacWindBlowMode hvacWindBlowMode) {
        onBlowModeChanged();
    }

    public void onIonizerChanged() {
        if (this.mRendererSet) {
            this.mWindRenderer.setIonizerEnable(this.mViewModel.isHvacQualityPurgeEnable());
        }
    }

    public void onPowerChanged() {
        if (this.mRendererSet) {
            this.mWindRenderer.setIsOpen(this.mViewModel.isHvacPowerModeOn());
        }
    }

    public void onBlowModeChanged() {
        HvacWindBlowMode windBlowMode;
        if (!this.mRendererSet || (windBlowMode = this.mViewModel.getWindBlowMode()) == null) {
            return;
        }
        boolean z = false;
        this.mWindRenderer.setIsBlowFace(HvacWindBlowMode.Face == windBlowMode || HvacWindBlowMode.FaceAndFoot == windBlowMode || HvacWindBlowMode.FaceWindshield == windBlowMode || HvacWindBlowMode.FaceFootWindshield == windBlowMode);
        this.mWindRenderer.setIsBlowFoot(HvacWindBlowMode.Foot == windBlowMode || HvacWindBlowMode.FaceAndFoot == windBlowMode || HvacWindBlowMode.FootWindshield == windBlowMode || HvacWindBlowMode.FaceFootWindshield == windBlowMode);
        WindRenderer windRenderer = this.mWindRenderer;
        if (HvacWindBlowMode.FootWindshield == windBlowMode || HvacWindBlowMode.FrontDefrost == windBlowMode || HvacWindBlowMode.Windshield == windBlowMode || HvacWindBlowMode.AutoDefrost == windBlowMode || HvacWindBlowMode.FaceWindshield == windBlowMode || HvacWindBlowMode.FaceFootWindshield == windBlowMode) {
            z = true;
        }
        windRenderer.setIsBlowWindow(z);
    }

    public void setFanSpeedLevel(int speed) {
        int currentVolume = this.mFanSpeedSeekBar.getCurrentVolume() + speed;
        if (currentVolume < 1) {
            currentVolume = 1;
        }
        this.mFanSpeedSeekBar.setCurrentVolumeFromUser(currentVolume);
    }

    public void intoImmerseMode(int mImmerseType) {
        if (mImmerseType != 1) {
            alphaHidden(this.mFanSpeedSeekBar);
            alphaHidden(this.mImgFanSpeedMinus);
            alphaHidden(this.mImgFanSpeedAdd);
        } else {
            alphaShow(this.mFanSpeedSeekBar);
            setFanSpeed(this.mViewModel.getHvacWindSpeedLevel());
        }
        alphaHidden(this.mWindModeTabView);
    }

    public void exitImmerseMode(int mImmerseType) {
        if (mImmerseType == 1) {
            int currentVolume = this.mFanSpeedSeekBar.getCurrentVolume();
            this.mViewModel.setHvacWindSpeedLevel(currentVolume);
            StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PAGE, BtnEnum.WIND_SPEED_BTN, Integer.valueOf(currentVolume));
        }
        alphaShow(this.mFanSpeedSeekBar);
        alphaShow(this.mImgFanSpeedMinus);
        alphaShow(this.mImgFanSpeedAdd);
        alphaShow(this.mWindModeTabView);
    }

    private void alphaHidden(View view) {
        if (view == null) {
            return;
        }
        view.animate().alpha(0.0f).start();
    }

    private void alphaShow(View view) {
        if (view == null) {
            return;
        }
        view.animate().alpha(1.0f).start();
    }

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mRendererSet) {
            this.mGLSurfaceView.queueEvent(new Runnable() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacVentViewControl$HHYtPxJsJGd453z4oL7_cBSpnps
                @Override // java.lang.Runnable
                public final void run() {
                    HvacVentViewControl.this.lambda$onConfigurationChanged$4$HvacVentViewControl();
                }
            });
        } else if (this.mRootView != null) {
            this.mRootView.setBackground(this.mActivity.getDrawable(R.drawable.bg_pressed));
        }
    }

    public /* synthetic */ void lambda$onConfigurationChanged$4$HvacVentViewControl() {
        this.mWindRenderer.onThemeChanged(null);
    }

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void onStart() {
        super.onStart();
        if (this.mRendererSet) {
            this.mGLSurfaceView.onResume();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void onStop() {
        super.onStop();
        if (this.mRendererSet) {
            this.mGLSurfaceView.onPause();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void onDestroy() {
        super.onDestroy();
        Handler handler = this.mFanSpeedHandler;
        if (handler != null) {
            handler.removeCallbacks(this.fanSpeedRun);
        }
    }
}
