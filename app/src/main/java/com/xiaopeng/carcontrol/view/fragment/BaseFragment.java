package com.xiaopeng.carcontrol.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.direct.ElementDirectManager;
import com.xiaopeng.carcontrol.direct.IElementDirect;
import com.xiaopeng.carcontrol.direct.OnPageDirectShowListener;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.scene.AbstractScene;
import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XTabLayout;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class BaseFragment extends VuiFragment implements ILoadingFragment, OnPageDirectShowListener {
    private static final String DIALOG_TITLE_DIRTY = "\n";
    protected static final long LOADING_DURATION_ANIM = 500;
    protected AlphaAnimation mFadeInAnim;
    private final CountDownLatch mInitCdl;
    protected volatile View mPreloadLayout;
    private boolean mIsNeedFreshUiNext = false;
    protected Context mContext = null;
    private final Runnable mShowLoadingRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$AjjZalAl78NQJmg1Z9njlhuMVSM
        @Override // java.lang.Runnable
        public final void run() {
            BaseFragment.this.lambda$new$0$BaseFragment();
        }
    };
    private final Runnable mHideLoadingRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$UYPauduDYQX7Y1SpLk2hHTVJDG0
        @Override // java.lang.Runnable
        public final void run() {
            BaseFragment.this.lambda$new$1$BaseFragment();
        }
    };
    private XDialog mConfirmDialog = null;
    private volatile boolean mIsVuiSceneCreated = false;
    private final Runnable mElementDirectRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$zRj6ZWNVvgmZc51koqcIGFEqt1E
        @Override // java.lang.Runnable
        public final void run() {
            BaseFragment.this.lambda$new$13$BaseFragment();
        }
    };

    protected int getListItemIdx(Intent intent) {
        return -1;
    }

    protected long getLoadingDurationAnim() {
        return LOADING_DURATION_ANIM;
    }

    protected int getPreLoadLayoutId() {
        return 0;
    }

    protected void initViewModels() {
    }

    protected void initViews() {
    }

    protected boolean isListFragment() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.ILoadingFragment
    public boolean needLoading() {
        return false;
    }

    protected boolean needPreLoadLayout() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.direct.OnPageDirectShowListener
    public void onPageDirectShow(String name) {
    }

    protected void refreshUi() {
    }

    protected void resumeViews() {
    }

    protected String[] supportSecondPageForElementDirect() {
        return null;
    }

    public /* synthetic */ void lambda$new$0$BaseFragment() {
        ((IActivityControl) this.mContext).showLoading();
    }

    public /* synthetic */ void lambda$new$1$BaseFragment() {
        ((IActivityControl) this.mContext).hideLoading();
    }

    public BaseFragment() {
        this.mInitCdl = new CountDownLatch(needPreLoadLayout() ? 2 : 1);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$9UIoIDL5PtCocK1r6PzTcQIutyY
            @Override // java.lang.Runnable
            public final void run() {
                BaseFragment.this.lambda$new$2$BaseFragment();
            }
        });
    }

    public /* synthetic */ void lambda$new$2$BaseFragment() {
        initViewModels();
        this.mInitCdl.countDown();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void addScene(AbstractScene scene) {
        getLifecycle().addObserver(scene);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public <T extends IBaseViewModel> T getViewModel(Class<?> modelClass) {
        return (T) ViewModelManager.getInstance().getViewModelImpl(modelClass);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public <T extends IBaseViewModel> T getViewModelSync(Class<?> modelClass) {
        return (T) ViewModelManager.getInstance().getViewModelImplSync(modelClass);
    }

    protected final <T extends View> T findViewById(View container, int viewId, View.OnClickListener listener) {
        return (T) findViewById(container, viewId, listener, true);
    }

    protected final <T extends View> T findViewById(View container, int viewId, View.OnClickListener listener, boolean show) {
        T t;
        if (container == null || (t = (T) container.findViewById(viewId)) == null) {
            return null;
        }
        t.setVisibility(show ? 0 : 8);
        if (show) {
            t.setOnClickListener(listener);
        }
        return t;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void updateXTabLayout(XTabLayout tabLayout, int index) {
        if (tabLayout == null || !tabLayout.isEnabled()) {
            return;
        }
        tabLayout.selectTab(index, false);
    }

    protected void showDialog(String message, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener) {
        showDialog(message, negative, negativeListener, positive, positiveListener, (String) null);
    }

    protected void showDialog(String message, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener, String sceneId) {
        if (getActivity() != null) {
            XDialog positiveButton = new XDialog(getActivity()).setMessage(message).setNegativeButton(negative, negativeListener).setPositiveButton(positive, positiveListener);
            if (!TextUtils.isEmpty(sceneId)) {
                VuiManager.instance().initVuiDialog(positiveButton, getActivity(), sceneId);
            }
            positiveButton.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showDialog(int titleId, int messageId, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener) {
        showDialog(titleId, messageId, negative, negativeListener, positive, positiveListener, null, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showDialog(int titleId, int messageId, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener, String sceneId, int style) {
        showDialog(titleId, messageId, negative, negativeListener, positive, positiveListener, null, sceneId, style);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showDialog(int titleId, int messageId, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener, String sceneId) {
        showDialog(titleId, messageId, negative, negativeListener, positive, positiveListener, null, sceneId, 0);
    }

    protected void showDialog(int titleId, int messageId, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener, DialogInterface.OnDismissListener onDismissListener) {
        showDialog(titleId, messageId, negative, negativeListener, positive, positiveListener, onDismissListener, null, 0);
    }

    protected void showDialog(int titleId, int messageId, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener, final DialogInterface.OnDismissListener onDismissListener, String sceneId, int style) {
        XDialog xDialog;
        dismissDialog();
        if (getActivity() != null) {
            if (style > 0) {
                xDialog = new XDialog(getActivity(), style);
            } else {
                xDialog = new XDialog(getActivity(), R.style.x_dialog_style);
            }
            xDialog.setTitle(getTitleStr(titleId)).setMessage(messageId).setNegativeButton(negative, negativeListener).setPositiveButton(positive, positiveListener);
            xDialog.setSystemDialog(2048);
            if (!TextUtils.isEmpty(sceneId)) {
                VuiUtils.addHasFeedbackProp((XButton) xDialog.getContentView().findViewById(R.id.x_dialog_button1));
                xDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$lSBPucLxL1sk4r7TmeZhQwrTFgs
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        BaseFragment.lambda$showDialog$3(onDismissListener, dialogInterface);
                    }
                });
            } else if (onDismissListener != null) {
                xDialog.getDialog().setOnDismissListener(onDismissListener);
            }
            if (!TextUtils.isEmpty(sceneId)) {
                VuiManager.instance().initVuiDialog(xDialog, getActivity(), sceneId);
            }
            xDialog.show();
            this.mConfirmDialog = xDialog;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showDialog$3(final DialogInterface.OnDismissListener onDismissListener, DialogInterface dialog) {
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showCustomConfirmDialog(int titleResId, CharSequence negativeBtnText, XDialogInterface.OnClickListener negativeListener, CharSequence positiveBtnText, XDialogInterface.OnClickListener positiveListener, int confirmPic1ResId, int confirmText1ResId, int confirmPic2ResId, int confirmText2ResId, int delayTimeInSecond, String sceneId) {
        showCustomConfirmDialog(getTitleStr(titleResId), negativeBtnText, negativeListener, positiveBtnText, positiveListener, confirmPic1ResId, getStringById(confirmText1ResId), confirmPic2ResId, getStringById(confirmText2ResId), delayTimeInSecond, sceneId);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showCustomConfirmDialog(String title, CharSequence negativeBtnText, XDialogInterface.OnClickListener negativeListener, final CharSequence positiveBtnText, XDialogInterface.OnClickListener positiveListener, int confirmPic1ResId, String confirmText1, int confirmPic2ResId, String confirmText2, int delayTimeInSecond, final String sceneId) {
        if (getActivity() != null) {
            final XDialog positiveButtonEnable = new XDialog(getActivity(), R.style.XDialogView_Large).setTitle(title).setNegativeButton(negativeBtnText, negativeListener).setPositiveButton(getPositiveBtnText(positiveBtnText, delayTimeInSecond), positiveListener).setPositiveButtonEnable(false);
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_xpilot_info_dialog_view, (ViewGroup) null);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.help_pic_1);
            if (confirmPic1ResId == 0) {
                imageView.setVisibility(8);
            } else {
                imageView.setVisibility(0);
                imageView.setImageResource(confirmPic1ResId);
            }
            TextView textView = (TextView) inflate.findViewById(R.id.help_text_1);
            if (TextUtils.isEmpty(confirmText1)) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
                textView.setText(confirmText1);
            }
            ImageView imageView2 = (ImageView) inflate.findViewById(R.id.help_pic_2);
            if (confirmPic2ResId == 0) {
                imageView2.setVisibility(8);
            } else {
                imageView2.setVisibility(0);
                imageView2.setImageResource(confirmPic2ResId);
            }
            TextView textView2 = (TextView) inflate.findViewById(R.id.help_text_2);
            if (TextUtils.isEmpty(confirmText2)) {
                textView2.setVisibility(8);
            } else {
                textView2.setVisibility(0);
                textView2.setText(confirmText2);
            }
            positiveButtonEnable.setCustomView(inflate, false);
            positiveButtonEnable.setSystemDialog(2048);
            XButton xButton = (XButton) positiveButtonEnable.getContentView().findViewById(R.id.x_dialog_button1);
            xButton.setVuiLabel((String) positiveBtnText);
            VuiUtils.addHasFeedbackProp(xButton);
            VuiManager.instance().setVuiLabelUnSupportText(getActivity(), xButton);
            final CountDownTimer countDownTimer = new CountDownTimer(300 + (delayTimeInSecond * 1000), 1000L) { // from class: com.xiaopeng.carcontrol.view.fragment.BaseFragment.1
                @Override // android.os.CountDownTimer
                public void onTick(long millisUntilFinished) {
                    positiveButtonEnable.setPositiveButton(BaseFragment.getPositiveBtnText(positiveBtnText, ((int) millisUntilFinished) / 1000));
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    positiveButtonEnable.setPositiveButton(positiveBtnText);
                    positiveButtonEnable.setPositiveButtonEnable(true);
                    VuiManager.instance().updateVuiScene(sceneId, BaseFragment.this.getActivity(), positiveButtonEnable.getContentView());
                }
            };
            positiveButtonEnable.getDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$PsPUlik9YhAIiVJk_Bl_sRAU97s
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    countDownTimer.start();
                }
            });
            positiveButtonEnable.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$SOxm4IDmTT0YdASoQvDtlYLH98g
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    countDownTimer.cancel();
                }
            });
            positiveButtonEnable.show();
            VuiManager.instance().initVuiDialog(positiveButtonEnable, getContext(), sceneId);
            Window window = positiveButtonEnable.getDialog().getWindow();
            if (window != null) {
                window.setElevation(0.0f);
            }
        }
    }

    private String getTitleStr(int titleId) {
        String string = ResUtils.getString(titleId);
        return !TextUtils.isEmpty(string) ? string.replace(DIALOG_TITLE_DIRTY, "") : "";
    }

    protected static CharSequence getPositiveBtnText(CharSequence positiveBtnText, int delayTimeInSecond) {
        return String.format(Locale.getDefault(), "%1s(%2ds)", positiveBtnText, Integer.valueOf(delayTimeInSecond));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void dismissDialog() {
        XDialog xDialog = this.mConfirmDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mConfirmDialog = null;
        }
    }

    protected final boolean isFragmentLegal() {
        return (getActivity() == null || !isAdded() || isHidden() || isDetached()) ? false : true;
    }

    protected final void setFreshUINext(boolean fresh) {
        this.mIsNeedFreshUiNext = fresh;
    }

    protected final boolean isNeedFreshUI() {
        return this.mIsNeedFreshUiNext;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(this.TAG, " onCreate");
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.i(this.TAG, " onAttach");
        this.mContext = context;
        if (needPreLoadLayout()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$O361Y_La_ErN2a7k9wS06aF2cXk
                @Override // java.lang.Runnable
                public final void run() {
                    BaseFragment.this.lambda$onAttach$7$BaseFragment();
                }
            });
        }
    }

    public /* synthetic */ void lambda$onAttach$7$BaseFragment() {
        this.mPreloadLayout = preLoadLayout();
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$57zBceaVt8GE60IlNOEjaKohbL8
            @Override // java.lang.Runnable
            public final void run() {
                BaseFragment.this.lambda$null$6$BaseFragment();
            }
        });
    }

    public /* synthetic */ void lambda$null$6$BaseFragment() {
        showLayout(this.mRootContainer, this.mPreloadLayout);
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            initViews();
            onGuiInitEnd();
            return;
        }
        LogUtils.e(this.TAG, "initViews failed for " + getLifecycle().getCurrentState());
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i(this.TAG, " onViewCreated");
        if (needPreLoadLayout()) {
            showLayout(this.mRootContainer, this.mPreloadLayout);
        } else {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$uyJdE9OBLGBZSzEB1_BfWSEhiUY
                @Override // java.lang.Runnable
                public final void run() {
                    BaseFragment.this.lambda$onViewCreated$9$BaseFragment();
                }
            });
        }
    }

    public /* synthetic */ void lambda$onViewCreated$9$BaseFragment() {
        try {
            this.mInitCdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$5EyCi7yJKaxUT2lOnP7XGj1YjmY
            @Override // java.lang.Runnable
            public final void run() {
                BaseFragment.this.lambda$null$8$BaseFragment();
            }
        });
    }

    public /* synthetic */ void lambda$null$8$BaseFragment() {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            initViews();
            onGuiInitEnd();
            return;
        }
        LogUtils.e(this.TAG, "initViews failed for " + getLifecycle().getCurrentState());
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        LogUtils.i(this.TAG, " onStart");
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$G9UKqgz41YJEQlgGnZ0hC1bSeTg
            @Override // java.lang.Runnable
            public final void run() {
                BaseFragment.this.lambda$onResume$11$BaseFragment();
            }
        });
    }

    public /* synthetic */ void lambda$onResume$11$BaseFragment() {
        try {
            this.mInitCdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$UQdJsiGfYaODAuGOUYsRdrH0AP4
            @Override // java.lang.Runnable
            public final void run() {
                BaseFragment.this.lambda$null$10$BaseFragment();
            }
        });
        ThreadUtils.runOnMainThreadDelay(this.mElementDirectRunnable, LOADING_DURATION_ANIM);
    }

    public /* synthetic */ void lambda$null$10$BaseFragment() {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            if (needPreLoadLayout()) {
                showLayout(this.mRootContainer, this.mPreloadLayout);
            }
            if (!this.mIsVuiSceneCreated && !this.mIsDialog) {
                initVuiScene();
                this.mIsVuiSceneCreated = true;
            }
            resumeViews();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        ThreadUtils.getHandler(1).removeCallbacks(this.mElementDirectRunnable);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        LogUtils.i(this.TAG, " onStop");
        for (long count = this.mInitCdl.getCount(); count > 0; count--) {
            this.mInitCdl.countDown();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i(this.TAG, " onDestroyView");
        if (this.mRootContainer != null) {
            this.mRootContainer.removeAllViews();
            this.mRootContainer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onGuiInitEnd() {
        LogUtils.d(this.TAG, "onGuiInitEnd");
        this.mInitCdl.countDown();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean isGuiInited() {
        return this.mInitCdl.getCount() <= 0;
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.i(this.TAG, " onHiddenChanged hidden=" + hidden + " isNeedFreshUI=" + isNeedFreshUI());
        if (hidden || !isNeedFreshUI()) {
            return;
        }
        refreshUi();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        liveData.observe(this, observer);
    }

    public <T> void removeObservers(LiveData<T> liveData) {
        liveData.removeObservers(this);
    }

    private View preLoadLayout() {
        if (getActivity() == null) {
            LogUtils.w(this.TAG, "preLoadLayout activity is null");
            return null;
        }
        return LayoutInflater.from(getActivity()).inflate(getPreLoadLayoutId(), (ViewGroup) null, false);
    }

    protected void initAnimations() {
        if (this.mFadeInAnim == null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            this.mFadeInAnim = alphaAnimation;
            alphaAnimation.setFillAfter(true);
            this.mFadeInAnim.setDuration(getLoadingDurationAnim());
            this.mFadeInAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaopeng.carcontrol.view.fragment.BaseFragment.2
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showLayout(ViewGroup parent, View view) {
        showLayout(parent, view, true);
    }

    protected void showLayout(ViewGroup parent, View view, boolean hasAnim) {
        LogUtils.d(this.TAG, "showLayout: parent=" + parent + ", view=" + view + ", view.getParent()=" + (view == null ? null : view.getParent()));
        if (parent == null || view == null || view.getParent() != null) {
            return;
        }
        parent.addView(view);
        if (hasAnim) {
            initAnimations();
            view.startAnimation(this.mFadeInAnim);
        }
    }

    public void showLoading() {
        Context context = this.mContext;
        if (context == null || !(context instanceof IActivityControl)) {
            return;
        }
        ThreadUtils.runOnMainThread(this.mShowLoadingRunnable);
    }

    public void hideLoading() {
        Context context = this.mContext;
        if (context == null || !(context instanceof IActivityControl)) {
            return;
        }
        ThreadUtils.runOnMainThread(this.mHideLoadingRunnable);
    }

    public void dispatchElementDirect() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$BaseFragment$8ht1jWNl1rkRwfmn60b-I8oAz80
            @Override // java.lang.Runnable
            public final void run() {
                BaseFragment.this.lambda$dispatchElementDirect$12$BaseFragment();
            }
        });
    }

    public /* synthetic */ void lambda$dispatchElementDirect$12$BaseFragment() {
        try {
            this.mInitCdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$6ZjMwICYuQEt55cibqFbpGALY40
            @Override // java.lang.Runnable
            public final void run() {
                BaseFragment.this.lambda$new$13$BaseFragment();
            }
        }, 50L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: handleElementDirect */
    public void lambda$new$13$BaseFragment() {
        boolean showElementItemView;
        boolean showSecondPageDirect;
        LogUtils.i(this.TAG, "handleElementDirect");
        if (getActivity() == null || !getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            return;
        }
        dismissDialog();
        Intent intent = getActivity().getIntent();
        IElementDirect elementDirect = ElementDirectManager.getElementDirect();
        if (elementDirect != null) {
            if (isListFragment()) {
                showElementItemView = elementDirect.showElementItemViewForList(intent, getClass().getName(), (RecyclerView) this.mRootContainer, getListItemIdx(intent), this);
            } else {
                showElementItemView = elementDirect.showElementItemView(intent, getClass().getName(), this.mRootContainer, this);
            }
            if (showElementItemView) {
                getActivity().getIntent().setData(null);
                LogUtils.d(this.TAG, "element direct isElement true");
                return;
            }
            if (isListFragment()) {
                showSecondPageDirect = elementDirect.showSecondPageDirectForList(intent, supportSecondPageForElementDirect(), (RecyclerView) this.mRootContainer, getListItemIdx(intent), this);
            } else {
                showSecondPageDirect = elementDirect.showSecondPageDirect(intent, supportSecondPageForElementDirect(), this);
            }
            if (showSecondPageDirect) {
                getActivity().getIntent().setData(null);
                LogUtils.d(this.TAG, "element direct isSecondPage true");
            }
        }
    }

    private JSONObject getVuiPropsJson(VuiView view) {
        JSONObject vuiProps = view.getVuiProps();
        return vuiProps == null ? new JSONObject() : vuiProps;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void addVuiProp(VuiView view, String prop, String value) {
        if (TextUtils.isEmpty(prop) || TextUtils.isEmpty(value)) {
            LogUtils.e(this.TAG, "addVuiProps failed with prop=" + prop + ", value=" + value, false);
            return;
        }
        LogUtils.i(this.TAG, "addVuiProps prop=" + prop + ", value=" + value, false);
        JSONObject vuiPropsJson = getVuiPropsJson(view);
        try {
            vuiPropsJson.put(prop, value);
        } catch (Exception e) {
            LogUtils.e(this.TAG, "addVuiProps " + prop + " failed :" + e.getMessage(), false);
        }
        view.setVuiProps(vuiPropsJson);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void removeVuiProps(VuiView view, String prop) {
        if (TextUtils.isEmpty(prop)) {
            LogUtils.e(this.TAG, "removeVuiProps failed with prop=" + prop, false);
            return;
        }
        JSONObject vuiPropsJson = getVuiPropsJson(view);
        vuiPropsJson.remove(prop);
        view.setVuiProps(vuiPropsJson);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void vuiFeedback(int content, View view) {
        if (view != null) {
            VuiEngine.getInstance(getContext()).vuiFeedback(view, new VuiFeedback.Builder().state(1).content(getString(content)).build());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void vuiFeedbackClick(int content, View view) {
        if ((view instanceof VuiView) && ((VuiView) view).isPerformVuiAction()) {
            vuiFeedback(content, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void vuiFeedbackClickForXpilot(int content, View view) {
        if (view instanceof VuiView) {
            vuiFeedback(content, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getStringById(int resId) {
        return ResUtils.getString(resId);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String getStringById(int resId, Object... formatArgs) {
        return ResUtils.getString(resId, formatArgs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Resources getResourcesById() {
        Context context = this.mContext;
        if (context != null) {
            return context.getResources();
        }
        return null;
    }
}
