package com.xiaopeng.carcontrol.view.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.UIUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacWindBlowMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLinearLayout;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class D21BlowModeView extends XLinearLayout implements BlowModeInter {
    private static final int ANIMATION_TIME = 300;
    private static final float DELAY_D_K = 0.7f;
    private static final float DELAY_K = 0.3f;
    private static final int[] ICONS = {R.drawable.ic_mid_blowchest, R.drawable.ic_mid_blowfoot, R.drawable.ic_mid_blowheadfoot, R.drawable.ic_mid_blowwinfoot};
    private static final int[] STRINGS = {R.string.hvac_fanface_vui_label, R.string.hvac_fanfoot_vui_label, R.string.hvac_face_foot_vui_label, R.string.hvac_win_foot_vui_label};
    private static final String TAG = "D21BlowModeView";
    private int mColorBg;
    private int mColorStroke;
    private int mCurrentIndex;
    private boolean mHiddenCheckedStatus;
    private float mLeftX;
    private ValueAnimator mMoveAnimator;
    private OnTabChangeListener mOnTabChangeListener;
    private Paint mPaint;
    private float mRightX;
    private HvacViewModel mViewModel;

    /* loaded from: classes2.dex */
    public interface OnTabChangeListener {
        void onChangeEnd(int fromIndex, int toIndex, boolean fromUser);

        void onChangeStart(int fromIndex, int toIndex, boolean fromUser);
    }

    public D21BlowModeView(Context context) {
        super(context);
        this.mCurrentIndex = -1;
        init();
    }

    public D21BlowModeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCurrentIndex = -1;
        init();
    }

    public D21BlowModeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentIndex = -1;
        init();
    }

    private void init() {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStrokeWidth(UIUtils.dp2px(getContext(), 3));
        initColor();
        addView();
    }

    private void initColor() {
        this.mColorBg = getResources().getColor(R.color.hvac_blow_mode_button, getContext().getTheme());
        this.mColorStroke = getResources().getColor(R.color.colorPrimary, getContext().getTheme());
    }

    private void addView() {
        int[] iArr = {R.id.img_blow_face, R.id.img_blow_foot, R.id.img_blow_face_foot, R.id.img_blow_window_foot};
        final int i = 0;
        while (true) {
            int[] iArr2 = ICONS;
            if (i >= iArr2.length) {
                return;
            }
            XImageView xImageView = new XImageView(getContext());
            xImageView.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
            xImageView.setId(iArr[i]);
            xImageView.setImageResource(iArr2[i]);
            xImageView.setScaleType(ImageView.ScaleType.CENTER);
            xImageView.setVuiAction(VuiAction.SETCHECK.getName());
            xImageView.setVuiElementType(VuiElementType.SWITCH);
            xImageView.setVuiLabel(getContext().getString(STRINGS[i]));
            xImageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$D21BlowModeView$vJLuF_YBKeWSaB_nvwmz045XrCQ
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    D21BlowModeView.this.lambda$addView$0$D21BlowModeView(i, view);
                }
            });
            addView(xImageView);
            i++;
        }
    }

    public /* synthetic */ void lambda$addView$0$D21BlowModeView(final int finalI, View v) {
        ValueAnimator valueAnimator = this.mMoveAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            selectedTab(finalI, true, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.widget.D21BlowModeView$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode;

        static {
            int[] iArr = new int[HvacWindBlowMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode = iArr;
            try {
                iArr[HvacWindBlowMode.Face.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.Foot.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.FaceAndFoot.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.FootWindshield.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int getIndexByBlowMode(HvacWindBlowMode hvacWindBlowMode) {
        int i = AnonymousClass3.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[hvacWindBlowMode.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return i != 4 ? -1 : 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public void selectedTab(int index, boolean needAnim) {
        selectedTab(index, needAnim, false);
    }

    private void selectedTab(int index, boolean needAnim, boolean fromUser) {
        HvacViewModel hvacViewModel;
        if (index == -1 && !fromUser) {
            this.mCurrentIndex = index;
            obtainCheckedValue();
            changeTabStatus();
            invalidate();
        } else if (index < 0 || index >= ICONS.length) {
        } else {
            int i = this.mCurrentIndex;
            if (i == index) {
                if (this.mOnTabChangeListener == null || (hvacViewModel = this.mViewModel) == null || hvacViewModel.isHvacPowerModeOn()) {
                    return;
                }
                this.mOnTabChangeListener.onChangeEnd(this.mCurrentIndex, index, fromUser);
                return;
            }
            if (needAnim && i >= 0) {
                activeAnimation(index, fromUser);
                this.mCurrentIndex = index;
            } else {
                OnTabChangeListener onTabChangeListener = this.mOnTabChangeListener;
                if (onTabChangeListener != null) {
                    onTabChangeListener.onChangeEnd(i, index, fromUser);
                }
                this.mCurrentIndex = index;
                obtainCheckedValue();
                invalidate();
            }
            changeTabStatus();
        }
    }

    private void obtainCheckedValue() {
        int width = getWidth();
        if (width > 0) {
            float f = width / 4.0f;
            float f2 = this.mCurrentIndex * f;
            this.mLeftX = f2;
            this.mRightX = f2 + f;
        }
    }

    private float obtainCheckedLeft(int index) {
        int width = getWidth();
        if (width > 0) {
            return index * (width / 4.0f);
        }
        return 0.0f;
    }

    private float obtainCheckedRight(int index) {
        int width = getWidth();
        if (width > 0) {
            return (index + 1) * (width / 4.0f);
        }
        return 0.0f;
    }

    private void activeAnimation(final int index, final boolean fromUser) {
        release();
        this.mMoveAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(300L);
        final int i = this.mCurrentIndex;
        final float f = this.mLeftX;
        final float f2 = this.mRightX;
        final float obtainCheckedLeft = obtainCheckedLeft(index);
        final float obtainCheckedRight = obtainCheckedRight(index);
        this.mMoveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$D21BlowModeView$wVUqnwtvaKSfqDHW8G1l_LU1VvI
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                D21BlowModeView.this.lambda$activeAnimation$1$D21BlowModeView(index, i, f, obtainCheckedLeft, f2, obtainCheckedRight, valueAnimator);
            }
        });
        this.mMoveAnimator.addListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.carcontrol.view.widget.D21BlowModeView.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                if (D21BlowModeView.this.mOnTabChangeListener != null) {
                    D21BlowModeView.this.mOnTabChangeListener.onChangeStart(D21BlowModeView.this.mCurrentIndex, index, fromUser);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (D21BlowModeView.this.mOnTabChangeListener != null) {
                    D21BlowModeView.this.mOnTabChangeListener.onChangeEnd(D21BlowModeView.this.mCurrentIndex, index, fromUser);
                }
            }
        });
        this.mMoveAnimator.start();
    }

    public /* synthetic */ void lambda$activeAnimation$1$D21BlowModeView(final int index, final int currentIndex, final float currentLeft, final float toLeft, final float currentRight, final float toRight, ValueAnimator animation) {
        float floatValue = ((Float) animation.getAnimatedValue()).floatValue();
        if (index > currentIndex) {
            if (floatValue >= DELAY_K) {
                this.mLeftX = currentLeft + (((floatValue - DELAY_K) / DELAY_D_K) * (toLeft - currentLeft));
            } else {
                this.mLeftX = currentLeft;
            }
            if (floatValue <= DELAY_D_K) {
                this.mRightX = currentRight + ((floatValue / DELAY_D_K) * (toRight - currentRight));
            } else {
                this.mRightX = toRight;
            }
        } else {
            if (floatValue >= DELAY_K) {
                this.mRightX = currentRight - (((floatValue - DELAY_K) / DELAY_D_K) * (currentRight - toRight));
            } else {
                this.mRightX = currentRight;
            }
            if (floatValue <= DELAY_D_K) {
                this.mLeftX = currentLeft - ((floatValue / DELAY_D_K) * (currentLeft - toLeft));
            } else {
                this.mLeftX = toLeft;
            }
        }
        invalidate();
    }

    public void hiddenCheckedStatus(boolean isHidden) {
        this.mHiddenCheckedStatus = isHidden;
        changeTabStatus();
        invalidate();
    }

    private void changeTabStatus() {
        int i = 0;
        while (i < getChildCount()) {
            getChildAt(i).setSelected(this.mCurrentIndex == i && !this.mHiddenCheckedStatus);
            i++;
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mHiddenCheckedStatus) {
            return;
        }
        if (this.mRightX == 0.0f) {
            obtainCheckedValue();
        }
        float height = getHeight() / 2.0f;
        float strokeWidth = this.mPaint.getStrokeWidth() / 2.0f;
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(this.mColorBg);
        canvas.drawRoundRect(this.mLeftX, 0.0f, this.mRightX, getHeight(), height, height, this.mPaint);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(this.mColorStroke);
        canvas.drawRoundRect(this.mLeftX + strokeWidth, strokeWidth, this.mRightX - strokeWidth, getHeight() - strokeWidth, height, height, this.mPaint);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (XThemeManager.isThemeChanged(newConfig)) {
            initColor();
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                ((XImageView) getChildAt(i)).setImageDrawable(getContext().getDrawable(ICONS[i]));
            }
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void release() {
        ValueAnimator valueAnimator = this.mMoveAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            return;
        }
        this.mMoveAnimator.removeAllUpdateListeners();
        this.mMoveAnimator.removeAllListeners();
        this.mMoveAnimator.cancel();
    }

    public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener;
    }

    @Override // com.xiaopeng.carcontrol.view.widget.BlowModeInter
    public void initView() {
        this.mViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
        setOnTabChangeListener(new OnTabChangeListener() { // from class: com.xiaopeng.carcontrol.view.widget.D21BlowModeView.2
            @Override // com.xiaopeng.carcontrol.view.widget.D21BlowModeView.OnTabChangeListener
            public void onChangeStart(int fromIndex, int toIndex, boolean fromUser) {
            }

            @Override // com.xiaopeng.carcontrol.view.widget.D21BlowModeView.OnTabChangeListener
            public void onChangeEnd(int fromIndex, int toIndex, boolean fromUser) {
                if (fromUser) {
                    HvacWindBlowMode hvacWindBlowMode = null;
                    if (toIndex == 0) {
                        hvacWindBlowMode = HvacWindBlowMode.Face;
                    } else if (toIndex == 1) {
                        hvacWindBlowMode = HvacWindBlowMode.Foot;
                    } else if (toIndex == 2) {
                        hvacWindBlowMode = HvacWindBlowMode.FaceAndFoot;
                    } else if (toIndex == 3) {
                        hvacWindBlowMode = HvacWindBlowMode.FootWindshield;
                    }
                    if (hvacWindBlowMode != null) {
                        try {
                            D21BlowModeView.this.mViewModel.setHvacWindMode(hvacWindBlowMode);
                        } catch (Exception e) {
                            LogUtils.e(D21BlowModeView.TAG, e.getMessage());
                        }
                    }
                    StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PAGE, BtnEnum.WIND_MODE_BTN, Integer.valueOf(toIndex + 1));
                }
            }
        });
        this.mViewModel.getHvacPowerData().observe((AppCompatActivity) getContext(), new Observer() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$D21BlowModeView$q53na-R1Efi7YQPKRx6qKj26z5o
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                D21BlowModeView.this.lambda$initView$2$D21BlowModeView((Boolean) obj);
            }
        });
        setWindBlowMode(false);
        this.mViewModel.getHvacWindModeData().observe((AppCompatActivity) getContext(), new Observer() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$D21BlowModeView$OlICAAVHU8d83VYfKj17GzxL40o
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                D21BlowModeView.this.lambda$initView$3$D21BlowModeView((HvacWindBlowMode) obj);
            }
        });
        for (int i = 0; i < getChildCount(); i++) {
            VuiEngine.getInstance(getContext().getApplicationContext()).setVuiElementUnStandardSwitch(getChildAt(i));
        }
    }

    public /* synthetic */ void lambda$initView$2$D21BlowModeView(Boolean aBoolean) {
        setWindBlowMode(true);
    }

    public /* synthetic */ void lambda$initView$3$D21BlowModeView(HvacWindBlowMode hvacWindBlowMode) {
        setWindBlowMode(true);
    }

    private void setWindBlowMode(boolean needAnim) {
        boolean isHvacPowerModeOn = this.mViewModel.isHvacPowerModeOn();
        hiddenCheckedStatus(!isHvacPowerModeOn);
        if (isHvacPowerModeOn) {
            HvacWindBlowMode windBlowMode = this.mViewModel.getWindBlowMode();
            if (windBlowMode != null) {
                selectedTab(getIndexByBlowMode(windBlowMode), needAnim);
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < getChildCount(); i++) {
                arrayList.add(getChildAt(i));
            }
            VuiEngine.getInstance(getContext().getApplicationContext()).updateScene("hvac", arrayList);
        }
    }
}
