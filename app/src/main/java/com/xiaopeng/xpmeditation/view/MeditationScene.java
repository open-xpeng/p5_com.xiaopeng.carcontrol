package com.xiaopeng.xpmeditation.view;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
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
import com.xiaopeng.xui.widget.XFrameLayout;

/* loaded from: classes2.dex */
public class MeditationScene extends BaseMeditationScene {
    private HvacViewModel mHvacViewModel;
    private MeditationLayout mLayout;
    protected MeditationViewModel mMeditationViewModel;
    private ScenarioViewModel mScenarioViewModel;
    private SeatViewModel mSeatViewModel;
    private WindowDoorViewModel mWindowDoorViewModel;

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void onClose() {
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public boolean onTouch(MotionEvent event) {
        return false;
    }

    public MeditationScene(View parent, LifecycleOwner owner) {
        super(parent, owner);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void onCreate() {
        super.onCreate();
        initViewModels();
        initViews();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void onStop() {
        super.onStop();
        release();
    }

    protected void initViews() {
        this.mLayout = new MeditationLayout(getContext());
        ((XFrameLayout) getContent().findViewById(R.id.meditation_content)).addView(this.mLayout, new ViewGroup.LayoutParams(-1, -1));
    }

    protected void initViewModels() {
        this.mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
        this.mHvacViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
        this.mWindowDoorViewModel = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
        this.mSeatViewModel = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        MeditationViewModel meditationViewModel = (MeditationViewModel) ViewModelManager.getInstance().getViewModelImpl(IMeditationViewModel.class);
        this.mMeditationViewModel = meditationViewModel;
        meditationViewModel.init();
        setLdo(this.mMeditationViewModel.getPlayIndexData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationScene$Y_g8SehDekRmBhgrVqOzFtXMXzs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationScene.this.lambda$initViewModels$0$MeditationScene((Integer) obj);
            }
        });
        setLdo(this.mMeditationViewModel.getPlayStateData(), new Observer() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationScene$tVKl2HUA0Bi-NlFYqSoFxdp3e10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MeditationScene.this.lambda$initViewModels$1$MeditationScene((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initViewModels$0$MeditationScene(Integer index) {
        MeditationLayout meditationLayout;
        LogUtils.d(this.TAG, "mViewModel.getPlayIndexData(): " + index);
        if (index == null || (meditationLayout = this.mLayout) == null) {
            return;
        }
        meditationLayout.updatePlayIndex(index.intValue());
    }

    public /* synthetic */ void lambda$initViewModels$1$MeditationScene(Integer state) {
        MeditationLayout meditationLayout;
        LogUtils.d(this.TAG, "mViewModel.getPlayStateData(): " + state);
        if (state == null || (meditationLayout = this.mLayout) == null) {
            return;
        }
        meditationLayout.updatePlayState(state.intValue());
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void refreshTime(long time) {
        this.mLayout.refreshTime(TimeUtil.parseTimeHMWithGMT(System.currentTimeMillis()));
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void preEnterMeditation() {
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationScene$l7ked2xxjii49PJTIZyORsRI9QQ
            @Override // java.lang.Runnable
            public final void run() {
                MeditationScene.this.lambda$preEnterMeditation$2$MeditationScene();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$preEnterMeditation$2$MeditationScene() {
        if (!this.mHvacViewModel.isHvacPowerModeOn()) {
            this.mHvacViewModel.setHvacPowerMode(true);
        }
        this.mHvacViewModel.setHvacAutoMode(true);
        this.mWindowDoorViewModel.controlWindowClose();
        this.mSeatViewModel.enterMeditationState(CarBaseConfig.getInstance().getMeditationSeatPos());
    }

    private void release() {
        this.mSeatViewModel.exitMeditationState();
        this.mScenarioViewModel.startScenario(false, ScenarioMode.Meditation);
        this.mMeditationViewModel.release();
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void playNext() {
        this.mMeditationViewModel.playNext();
    }

    @Override // com.xiaopeng.xpmeditation.view.BaseMeditationScene
    public void playPre() {
        this.mMeditationViewModel.playPre();
    }
}
