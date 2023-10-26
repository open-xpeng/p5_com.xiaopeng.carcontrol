package com.xiaopeng.xpmeditation.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioMode;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.view.UIUtils;
import com.xiaopeng.xpmeditation.model.MeditationItemBean;
import com.xiaopeng.xpmeditation.util.TimeUtil;
import com.xiaopeng.xpmeditation.view.adapter.BaseRecyclerViewAdapter;
import com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel;
import com.xiaopeng.xpmeditation.viewModel.MeditationViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Random;

/* loaded from: classes2.dex */
public class MeditationLayout extends XFrameLayout {
    private static final int COUNT_DOWN_INTERVAL = 1000;
    private static final int COUNT_DOWN_TIME = 3000;
    private static final int PREPARE_COUNT_DOWN_TIME = 5000;
    private static final String TAG = "MeditationLayout";
    private ArrayMap<LottieAnimationView, String> mAnimMap;
    private LottieAnimationView mBgAnimView;
    private XImageView mBgImg;
    private int mCountDown;
    private Runnable mCountDownRun;
    private CountDownTimer mCountDownTimer;
    private Runnable mDelayLoadAnimRun;
    private XDialog mDialog;
    private Handler mHandler;
    private boolean mIsShowPlay;
    private LottieAnimationView mMeteorAnimView;
    private MeditationPlayView mPlayView;
    private MeditationPrepareView mPrepareView;
    private Runnable mShowMeteorRun;
    private XTextView mTimeTxt;
    private View mView;
    private MeditationViewModel mViewModel;

    static /* synthetic */ int access$610(MeditationLayout meditationLayout) {
        int i = meditationLayout.mCountDown;
        meditationLayout.mCountDown = i - 1;
        return i;
    }

    public MeditationLayout(Context context) {
        this(context, null);
    }

    public MeditationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeditationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAnimMap = new ArrayMap<>();
        this.mHandler = new Handler();
        this.mDelayLoadAnimRun = new Runnable() { // from class: com.xiaopeng.xpmeditation.view.MeditationLayout.1
            @Override // java.lang.Runnable
            public void run() {
                MeditationLayout.this.triggerAnimLoad();
                if (MeditationLayout.this.mAnimMap.size() > 0) {
                    MeditationLayout.this.mHandler.postDelayed(MeditationLayout.this.mDelayLoadAnimRun, 100L);
                }
            }
        };
        this.mShowMeteorRun = new Runnable() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationLayout$6wXkOqEXLEiupg6TBFliGBq3tC4
            @Override // java.lang.Runnable
            public final void run() {
                MeditationLayout.this.lambda$new$1$MeditationLayout();
            }
        };
        this.mCountDownRun = new Runnable() { // from class: com.xiaopeng.xpmeditation.view.MeditationLayout.3
            @Override // java.lang.Runnable
            public void run() {
                MeditationLayout.access$610(MeditationLayout.this);
                if (MeditationLayout.this.mCountDown > 0) {
                    MeditationLayout meditationLayout = MeditationLayout.this;
                    meditationLayout.refreshCountDownTime(meditationLayout.mCountDown);
                    MeditationLayout.this.mHandler.postDelayed(this, 1000L);
                    return;
                }
                LogUtils.i(MeditationLayout.TAG, "mCountDownRun onFinish = ");
                MeditationLayout.this.changeToPlayView();
                MeditationLayout.this.showPlayView();
                MeditationLayout.this.refreshTime(TimeUtil.parseTimeHMWithGMT(System.currentTimeMillis()));
                MeditationLayout.this.mViewModel.play(MeditationLayout.this.mViewModel.getLastPlayIndex());
            }
        };
        init();
    }

    private void init() {
        initView();
        initViewModel();
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_meditation, this);
        this.mView = inflate;
        this.mTimeTxt = (XTextView) inflate.findViewById(R.id.txt_time);
        this.mPrepareView = (MeditationPrepareView) this.mView.findViewById(R.id.view_prepare);
        this.mMeteorAnimView = (LottieAnimationView) this.mView.findViewById(R.id.lottie_meteor);
        this.mBgAnimView = (LottieAnimationView) this.mView.findViewById(R.id.lottie_bg);
        this.mBgImg = (XImageView) this.mView.findViewById(R.id.img_bg);
        this.mTimeTxt.setVisibility(8);
        this.mBgAnimView.setRepeatCount(-1);
        setBackgroundColor(getResources().getColor(R.color.meditation_play_bg_color_e28, getContext().getTheme()));
        asyncLoadBackground(R.drawable.bg_meditation_center, this.mBgImg);
        this.mPrepareView.setOnFinishListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationLayout$Gu2GL7GMonilZ5lYK1E87BPeFSM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MeditationLayout.this.lambda$initView$0$MeditationLayout(view);
            }
        });
        refreshTime(TimeUtil.parseTimeHMWithGMT(System.currentTimeMillis()));
        this.mAnimMap.put(this.mBgAnimView, "anim_meditation_bg.json");
        this.mHandler.postDelayed(this.mDelayLoadAnimRun, 100L);
        initPrepareCountDown();
    }

    public /* synthetic */ void lambda$initView$0$MeditationLayout(View v) {
        finish();
    }

    private void initViewModel() {
        this.mViewModel = (MeditationViewModel) ViewModelManager.getInstance().getViewModelImpl(IMeditationViewModel.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void triggerAnimLoad() {
        for (Map.Entry<LottieAnimationView, String> entry : this.mAnimMap.entrySet()) {
            if (entry.getKey() != null) {
                LogUtils.d(TAG, "triggerAnimLoad: value = " + entry.getValue());
                entry.getKey().setAnimation(entry.getValue());
                this.mAnimMap.remove(entry.getKey());
                return;
            }
        }
    }

    private void initPrepareCountDown() {
        new Thread(this.mShowMeteorRun).start();
        CountDownTimer countDownTimer = new CountDownTimer(UILooperObserver.ANR_TRIGGER_TIME, 1000L) { // from class: com.xiaopeng.xpmeditation.view.MeditationLayout.2
            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                LogUtils.i(MeditationLayout.TAG, "mCountDownTimer onFinish = ");
                MeditationLayout.this.changeToCountDownView();
                MeditationLayout.this.initCountDown();
                MeditationLayout.this.preloadPlayView();
            }
        };
        this.mCountDownTimer = countDownTimer;
        countDownTimer.start();
    }

    public /* synthetic */ void lambda$new$1$MeditationLayout() {
        doRandomShowMeteor();
        postShowRun();
    }

    private void doRandomShowMeteor() {
        this.mMeteorAnimView.setAnimation(Double.valueOf(Math.random() * 2.0d).doubleValue() > 1.0d ? "anim_meditation_meteor_2.json" : "anim_meditation_meteor_1.json");
        this.mMeteorAnimView.playAnimation();
    }

    private void postShowRun() {
        this.mHandler.removeCallbacks(this.mShowMeteorRun);
        this.mHandler.postDelayed(this.mShowMeteorRun, (new Random(System.currentTimeMillis()).nextInt(6) + 7) * 1000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCountDown() {
        LogUtils.i(TAG, "COUNT_DOWN_TIME = 3000");
        this.mCountDown = 3;
        refreshCountDownTime(3);
        this.mHandler.removeCallbacks(this.mCountDownRun);
        this.mHandler.postDelayed(this.mCountDownRun, 1000L);
    }

    public void refreshCountDownTime(long second) {
        MeditationPrepareView meditationPrepareView = this.mPrepareView;
        if (meditationPrepareView != null) {
            meditationPrepareView.refreshCountDownTime(second);
        }
    }

    public void changeToCountDownView() {
        MeditationPrepareView meditationPrepareView = this.mPrepareView;
        if (meditationPrepareView != null) {
            meditationPrepareView.switchCountDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void preloadPlayView() {
        LogUtils.i(TAG, "preloadPlayView: ");
        createPlayView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeToPlayView() {
        LogUtils.i(TAG, "changeToPlayView");
        MeditationPrepareView meditationPrepareView = this.mPrepareView;
        if (meditationPrepareView != null && meditationPrepareView.getParent() != null && (this.mPrepareView.getParent() instanceof ViewGroup)) {
            ((ViewGroup) this.mPrepareView.getParent()).removeView(this.mPrepareView);
            this.mPrepareView = null;
        }
        createPlayView();
        refreshMusicList();
        this.mTimeTxt.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPlayView() {
        LogUtils.i(TAG, "showPlayView");
        MeditationPlayView meditationPlayView = this.mPlayView;
        if (meditationPlayView != null) {
            meditationPlayView.setVisibility(0);
        }
        this.mIsShowPlay = true;
    }

    private void createPlayView() {
        if (this.mPlayView != null) {
            return;
        }
        this.mPlayView = ResUtils.isScreenOrientationLand() ? new FlavorMeditationPlayView(getContext()) : new MeditationPlayView(getContext());
        addView(this.mPlayView, new ViewGroup.LayoutParams(-1, -1));
        this.mPlayView.setOnControllerListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationLayout$sWzTEND_kG3dagm-Un2Mzeu0x4I
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MeditationLayout.this.lambda$createPlayView$2$MeditationLayout(view);
            }
        });
        this.mPlayView.setOnFinishListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationLayout$gMMgUnAHw0jgO_3zD4j9wk4UXk4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MeditationLayout.this.lambda$createPlayView$3$MeditationLayout(view);
            }
        });
        this.mPlayView.setOnItemClick(new BaseRecyclerViewAdapter.ItemClickListener() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationLayout$uckgWALXVUYQYgjG7vuWwhVDtlY
            @Override // com.xiaopeng.xpmeditation.view.adapter.BaseRecyclerViewAdapter.ItemClickListener
            public final void click(int i, int i2) {
                MeditationLayout.this.lambda$createPlayView$4$MeditationLayout(i, i2);
            }
        });
        this.mPlayView.setVisibility(8);
        LogUtils.i(TAG, "createPlayView");
    }

    public /* synthetic */ void lambda$createPlayView$2$MeditationLayout(View v) {
        this.mViewModel.playOrPause();
    }

    public /* synthetic */ void lambda$createPlayView$3$MeditationLayout(View v) {
        finish();
    }

    public /* synthetic */ void lambda$createPlayView$4$MeditationLayout(int type, int position) {
        this.mViewModel.play(position);
    }

    private void refreshMusicList() {
        MeditationPlayView meditationPlayView = this.mPlayView;
        if (meditationPlayView != null) {
            meditationPlayView.refreshMeditationList(this.mViewModel.getMusicList());
            if (this.mIsShowPlay) {
                this.mPlayView.setVisibility(0);
                MeditationPrepareView meditationPrepareView = this.mPrepareView;
                if (meditationPrepareView != null) {
                    meditationPrepareView.setVisibility(8);
                }
            }
        }
    }

    private void finish() {
        if (this.mDialog == null) {
            XDialog negativeButton = new XDialog(getContext()).setTitle(R.string.meditation_finish_title).setMessage(R.string.meditation_finish_confirm).setPositiveButton(getResources().getString(R.string.meditation_sure), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationLayout$iPP8Yh_0CWnIm6j84jkR_WetSWc
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    ((ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class)).startScenario(false, ScenarioMode.Meditation);
                }
            }).setNegativeButton(getResources().getString(R.string.meditation_cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationLayout$vbwywgngNu_6-WmRf05kbj41-ic
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    MeditationLayout.this.lambda$finish$6$MeditationLayout(xDialog, i);
                }
            });
            this.mDialog = negativeButton;
            Window window = negativeButton.getDialog().getWindow();
            if (window != null) {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.y = -UIUtils.dip2px(App.getInstance().getApplicationContext(), getResources().getDimensionPixelOffset(R.dimen.meditation_dialog_margin_top));
                window.setAttributes(attributes);
            }
            if (this.mDialog.getDialog() != null && this.mDialog.getDialog().getWindow() != null) {
                WindowManager.LayoutParams attributes2 = this.mDialog.getDialog().getWindow().getAttributes();
                attributes2.flags |= 1024;
                attributes2.systemUiVisibility |= 1024;
                attributes2.systemUiVisibility |= 2;
                attributes2.systemUiVisibility |= 512;
                this.mDialog.getDialog().getWindow().setAttributes(attributes2);
            }
        }
        this.mDialog.show();
    }

    public /* synthetic */ void lambda$finish$6$MeditationLayout(XDialog xDialog, int i) {
        this.mDialog = null;
    }

    public void refreshTime(String time) {
        LogUtils.d(TAG, "refreshTim: " + time + ", mIsShowPlay: " + this.mIsShowPlay);
        if (time != null) {
            MeditationPlayView meditationPlayView = this.mPlayView;
            if (meditationPlayView != null && this.mIsShowPlay) {
                meditationPlayView.refreshTime(time);
                if (this.mIsShowPlay) {
                    this.mPlayView.setVisibility(0);
                }
                this.mTimeTxt.setVisibility(8);
                MeditationPrepareView meditationPrepareView = this.mPrepareView;
                if (meditationPrepareView != null) {
                    meditationPrepareView.setVisibility(8);
                    return;
                }
                return;
            }
            this.mTimeTxt.setText(time);
        }
    }

    public void updatePlayIndex(int index) {
        if (this.mPlayView != null) {
            List<MeditationItemBean> musicList = this.mViewModel.getMusicList();
            if (musicList != null) {
                this.mPlayView.refreshMeditationList(musicList);
                this.mPlayView.refreshInfo(musicList.get(index));
                this.mPlayView.scrollToIndex(index);
            }
            if (this.mIsShowPlay) {
                this.mPlayView.setVisibility(0);
                MeditationPrepareView meditationPrepareView = this.mPrepareView;
                if (meditationPrepareView != null) {
                    meditationPrepareView.setVisibility(8);
                }
            }
        }
    }

    public void updatePlayState(int state) {
        MeditationPlayView meditationPlayView = this.mPlayView;
        if (meditationPlayView != null) {
            meditationPlayView.refreshStatus(state);
            if (this.mIsShowPlay) {
                this.mPlayView.setVisibility(0);
                MeditationPrepareView meditationPrepareView = this.mPrepareView;
                if (meditationPrepareView != null) {
                    meditationPrepareView.setVisibility(8);
                }
            }
        }
    }

    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == 0) {
            this.mMeteorAnimView.playAnimation();
        }
        LogUtils.d(TAG, "visible = " + visibility);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtils.d(TAG, "onAttachedToWindow = ");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.d(TAG, "onDetachedFromWindow = ");
        this.mHandler.removeCallbacks(this.mDelayLoadAnimRun);
        this.mHandler.removeCallbacks(this.mCountDownRun);
        XDialog xDialog = this.mDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mDialog = null;
        }
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mCountDownTimer = null;
        }
    }

    private void asyncLoadBackground(final int resId, View view) {
        final WeakReference weakReference = new WeakReference(view);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationLayout$DGkGg5RkMRqiQsxOJpWZTfxX9JQ
            @Override // java.lang.Runnable
            public final void run() {
                MeditationLayout.lambda$asyncLoadBackground$8(resId, weakReference);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$asyncLoadBackground$8(final int resId, final WeakReference viewRef) {
        final Drawable drawable = App.getInstance().getDrawable(resId);
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationLayout$LEQr67SYUuNRet76nn4v3Igj-cA
            @Override // java.lang.Runnable
            public final void run() {
                MeditationLayout.lambda$null$7(viewRef, drawable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$null$7(final WeakReference viewRef, final Drawable drawable) {
        View view = (View) viewRef.get();
        if (view != null) {
            view.setBackground(drawable);
        }
    }
}
