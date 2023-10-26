package com.xiaopeng.carcontrol.view.dialog.panel;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.view.widget.SunShadeCtrlView;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;

/* loaded from: classes2.dex */
public class SunshadeControlPanel extends AbstractControlPanel {
    private static final long DELAY_TIME = 3000;
    private Button mCloseBtn;
    private long mLastDownTime;
    private Button mOpenBtn;
    private final Runnable mResetTask = new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$SunshadeControlPanel$1ny_b0orbASZ7wLjvIfZoZNOTUo
        @Override // java.lang.Runnable
        public final void run() {
            SunshadeControlPanel.this.lambda$new$0$SunshadeControlPanel();
        }
    };
    private SunShadeCtrlView mSunShadeCtrlView;
    private WindowDoorViewModel mWinDoorVm;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.layout_sun_shade_panel;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_SUN_SHADE_CONTROL;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean needInitVm() {
        return true;
    }

    public /* synthetic */ void lambda$new$0$SunshadeControlPanel() {
        this.mWinDoorVm.resetSunShade();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitViewModel() {
        this.mWinDoorVm = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
    }

    public /* synthetic */ void lambda$onInitView$1$SunshadeControlPanel(View v) {
        dismiss();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitView() {
        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$SunshadeControlPanel$qRg_bngcD17YecsQP719GB2q6Cw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SunshadeControlPanel.this.lambda$onInitView$1$SunshadeControlPanel(view);
            }
        });
        SunShadeCtrlView sunShadeCtrlView = (SunShadeCtrlView) findViewById(R.id.ctrl_view);
        this.mSunShadeCtrlView = sunShadeCtrlView;
        sunShadeCtrlView.setPosition(this.mWinDoorVm.getSunShadePos());
        this.mSunShadeCtrlView.setTPosChangeListener(new SunShadeCtrlView.onTargetPosChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$SunshadeControlPanel$n7RMQ0wJ2hoIkxbo3htrBcI48ww
            @Override // com.xiaopeng.carcontrol.view.widget.SunShadeCtrlView.onTargetPosChangeListener
            public final void onTargetPosChanged(int i) {
                SunshadeControlPanel.this.lambda$onInitView$2$SunshadeControlPanel(i);
            }
        });
        Button button = (Button) findViewById(R.id.open_sunshade_btn);
        this.mOpenBtn = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$SunshadeControlPanel$9ntmHuBDmocSVNFxCNDpcuP6M6E
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SunshadeControlPanel.this.lambda$onInitView$3$SunshadeControlPanel(view);
            }
        });
        Button button2 = (Button) findViewById(R.id.close_sunshade_btn);
        this.mCloseBtn = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$SunshadeControlPanel$wwgbrU_ph_nq3xOtoN_SQiLCUWo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SunshadeControlPanel.this.lambda$onInitView$4$SunshadeControlPanel(view);
            }
        });
        this.mCloseBtn.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$SunshadeControlPanel$oLKx9oRor05BzFA245Q5PtiNe8I
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return SunshadeControlPanel.this.lambda$onInitView$5$SunshadeControlPanel(view, motionEvent);
            }
        });
        findViewById(R.id.pause_sunshade_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$SunshadeControlPanel$oyH-IXDnx3Lyf7Ue_axN97-spUc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SunshadeControlPanel.this.lambda$onInitView$6$SunshadeControlPanel(view);
            }
        });
    }

    public /* synthetic */ void lambda$onInitView$2$SunshadeControlPanel(int tPos) {
        this.mWinDoorVm.setSunShadePos(tPos);
    }

    public /* synthetic */ void lambda$onInitView$3$SunshadeControlPanel(View v) {
        this.mWinDoorVm.openSunShade();
    }

    public /* synthetic */ void lambda$onInitView$4$SunshadeControlPanel(View v) {
        this.mWinDoorVm.closeSunShade();
    }

    public /* synthetic */ boolean lambda$onInitView$5$SunshadeControlPanel(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == 0) {
            this.mLastDownTime = System.currentTimeMillis();
            this.mMainHandler.postDelayed(this.mResetTask, DELAY_TIME);
        } else if (action == 1 || action == 3) {
            this.mMainHandler.removeCallbacks(this.mResetTask);
            if (System.currentTimeMillis() - this.mLastDownTime >= DELAY_TIME) {
                v.setPressed(false);
                return true;
            }
        }
        return false;
    }

    public /* synthetic */ void lambda$onInitView$6$SunshadeControlPanel(View v) {
        this.mWinDoorVm.stopSunShade();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onRegisterLiveData() {
        setLiveDataObserver(this.mWinDoorVm.getSunShadePosData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$SunshadeControlPanel$XG9LMtmYU7OKMHJ2j4D2ThrD1iY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SunshadeControlPanel.this.lambda$onRegisterLiveData$7$SunshadeControlPanel((Integer) obj);
            }
        });
        setLiveDataObserver(this.mWinDoorVm.getSunShadeLoadingData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$SunshadeControlPanel$VBN7XrLFz9bhGA6DUkY-7R-I-Sw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SunshadeControlPanel.this.lambda$onRegisterLiveData$8$SunshadeControlPanel((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onRegisterLiveData$7$SunshadeControlPanel(Integer pos) {
        if (pos != null) {
            this.mSunShadeCtrlView.setPosition(pos.intValue());
        }
    }

    public /* synthetic */ void lambda$onRegisterLiveData$8$SunshadeControlPanel(Boolean loading) {
        if (loading != null) {
            this.mOpenBtn.setEnabled(!loading.booleanValue());
            this.mCloseBtn.setEnabled(!loading.booleanValue());
        }
    }
}
