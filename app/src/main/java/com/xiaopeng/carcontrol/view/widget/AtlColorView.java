package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.vui.VuiView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class AtlColorView extends View implements VuiView, IVuiElementListener {
    public static final int COLOR_MODE_DOUBLE = 2;
    public static final int COLOR_MODE_SINGLE = 1;
    private static final String[] SINGLE_COLOR_ARRAY = {"#FF3D3D", "#FF433D", "#FF773D", "#FFAA47", "#FFEB61", "#F4F4E9", "#93FF7E", "#2DFF5E", "#2DFF9D", "#2DFFFF", "#2DD5FF", "#1FB9FF", "#1F90FF", "#0B6EFF", "#0B36FF", "#4F0BFF", "#7E0BFF", "#B16CFF", "#FFA9CC", "#FF267D"};
    private static final String TAG = "AtlColorView";
    private final float COLOR_WIDTH_STEP;
    private final int DIMEN_12;
    private final int DIMEN_138;
    private final int DIMEN_18;
    private final int DIMEN_22;
    private final int DIMEN_25;
    private final int DIMEN_30;
    private final int DIMEN_33;
    private final int DIMEN_42;
    private final int DIMEN_45;
    private final int DIMEN_6;
    private final int DIMEN_62;
    private final int DIMEN_65;
    private final int DIMEN_7;
    private final int DIMEN_FIVE_LEFT;
    private final int DIMEN_FIVE_RIGHT;
    private final int DIMEN_FOUR_LEFT;
    private final int DIMEN_FOUR_RIGHT;
    private final int DIMEN_ONE_RIGHT;
    private final int DIMEN_SEVEN_LEFT;
    private final int DIMEN_SEVEN_RIGHT;
    private final int DIMEN_SIX_LEFT;
    private final int DIMEN_SIX_RIGHT;
    private final int DIMEN_THREE_LEFT;
    private final int DIMEN_THREE_RIGHT;
    private final int DIMEN_TWO_LEFT;
    private final int DIMEN_TWO_RIGHT;
    private final int MAX_CENTER_X;
    private final int MIN_CENTER_X;
    private final int SLIDE_WIDTH;
    private final int VIEW_HEIGHT;
    private final int VIEW_WIDTH;
    private Drawable mBgDrawable;
    private float mCenterY;
    private int mColorAlpha;
    private int mColorMode;
    private int mColorWhite;
    private int mDoubleColor;
    private Drawable mDrawableFive;
    private Drawable mDrawableFour;
    private Drawable mDrawableOne;
    private Drawable mDrawableSeven;
    private Drawable mDrawableSix;
    private Drawable mDrawableThree;
    private Drawable mDrawableTwo;
    private boolean mIsControl;
    private OnStatusChangeListener mOnStatusChangeListener;
    private Paint mPaint;
    private String mSceneId;
    private int mSingleColor;
    private Drawable mSingleDrawable;
    private float mTouchX;
    private List<String> vuiDoubleColor;
    private List<String> vuiSingleColor;
    private final List<Integer> vuiSingleColorIndex;

    /* loaded from: classes2.dex */
    public interface OnStatusChangeListener {
        void onDoubleColorChanged(int colorFirst, int colorSecond, int index);

        void onSingleColorChanged(int color);

        void onSlideColorChanged(int color);
    }

    public AtlColorView(Context context) {
        super(context);
        this.mColorMode = 1;
        int dp = dp(64);
        this.MIN_CENTER_X = dp;
        int dp2 = dp(624);
        this.MAX_CENTER_X = dp2;
        this.VIEW_WIDTH = dp(688);
        this.VIEW_HEIGHT = dp(80);
        int i = dp2 - dp;
        this.SLIDE_WIDTH = i;
        this.COLOR_WIDTH_STEP = i / 20.0f;
        this.vuiSingleColorIndex = new ArrayList();
        this.DIMEN_30 = dp(30);
        this.DIMEN_25 = dp(25);
        int dp3 = dp(138);
        this.DIMEN_138 = dp3;
        this.DIMEN_62 = dp(62);
        this.DIMEN_65 = dp(65);
        this.DIMEN_33 = dp(33);
        int dp4 = dp(7);
        this.DIMEN_7 = dp4;
        this.DIMEN_45 = dp(45);
        this.DIMEN_42 = dp(42);
        this.DIMEN_22 = dp(22);
        this.DIMEN_18 = dp(18);
        this.DIMEN_12 = dp(12);
        this.DIMEN_6 = dp(6);
        int i2 = dp4 + dp3;
        this.DIMEN_ONE_RIGHT = i2;
        this.DIMEN_TWO_LEFT = i2;
        int i3 = i2 + dp3;
        this.DIMEN_TWO_RIGHT = i3;
        this.DIMEN_THREE_LEFT = i3;
        int i4 = i3 + dp3;
        this.DIMEN_THREE_RIGHT = i4;
        this.DIMEN_FOUR_LEFT = i4;
        int i5 = i4 + dp3;
        this.DIMEN_FOUR_RIGHT = i5;
        this.DIMEN_FIVE_LEFT = i5;
        int i6 = i5 + dp3;
        this.DIMEN_FIVE_RIGHT = i6;
        this.DIMEN_SIX_LEFT = i6;
        int i7 = i6 + dp3;
        this.DIMEN_SIX_RIGHT = i7;
        this.DIMEN_SEVEN_LEFT = i7;
        this.DIMEN_SEVEN_RIGHT = i7 + dp3;
        this.mIsControl = false;
        init();
        initVui(this, null);
    }

    public AtlColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mColorMode = 1;
        int dp = dp(64);
        this.MIN_CENTER_X = dp;
        int dp2 = dp(624);
        this.MAX_CENTER_X = dp2;
        this.VIEW_WIDTH = dp(688);
        this.VIEW_HEIGHT = dp(80);
        int i = dp2 - dp;
        this.SLIDE_WIDTH = i;
        this.COLOR_WIDTH_STEP = i / 20.0f;
        this.vuiSingleColorIndex = new ArrayList();
        this.DIMEN_30 = dp(30);
        this.DIMEN_25 = dp(25);
        int dp3 = dp(138);
        this.DIMEN_138 = dp3;
        this.DIMEN_62 = dp(62);
        this.DIMEN_65 = dp(65);
        this.DIMEN_33 = dp(33);
        int dp4 = dp(7);
        this.DIMEN_7 = dp4;
        this.DIMEN_45 = dp(45);
        this.DIMEN_42 = dp(42);
        this.DIMEN_22 = dp(22);
        this.DIMEN_18 = dp(18);
        this.DIMEN_12 = dp(12);
        this.DIMEN_6 = dp(6);
        int i2 = dp4 + dp3;
        this.DIMEN_ONE_RIGHT = i2;
        this.DIMEN_TWO_LEFT = i2;
        int i3 = i2 + dp3;
        this.DIMEN_TWO_RIGHT = i3;
        this.DIMEN_THREE_LEFT = i3;
        int i4 = i3 + dp3;
        this.DIMEN_THREE_RIGHT = i4;
        this.DIMEN_FOUR_LEFT = i4;
        int i5 = i4 + dp3;
        this.DIMEN_FOUR_RIGHT = i5;
        this.DIMEN_FIVE_LEFT = i5;
        int i6 = i5 + dp3;
        this.DIMEN_FIVE_RIGHT = i6;
        this.DIMEN_SIX_LEFT = i6;
        int i7 = i6 + dp3;
        this.DIMEN_SIX_RIGHT = i7;
        this.DIMEN_SEVEN_LEFT = i7;
        this.DIMEN_SEVEN_RIGHT = i7 + dp3;
        this.mIsControl = false;
        init();
        initVui(this, attrs);
    }

    public AtlColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mColorMode = 1;
        int dp = dp(64);
        this.MIN_CENTER_X = dp;
        int dp2 = dp(624);
        this.MAX_CENTER_X = dp2;
        this.VIEW_WIDTH = dp(688);
        this.VIEW_HEIGHT = dp(80);
        int i = dp2 - dp;
        this.SLIDE_WIDTH = i;
        this.COLOR_WIDTH_STEP = i / 20.0f;
        this.vuiSingleColorIndex = new ArrayList();
        this.DIMEN_30 = dp(30);
        this.DIMEN_25 = dp(25);
        int dp3 = dp(138);
        this.DIMEN_138 = dp3;
        this.DIMEN_62 = dp(62);
        this.DIMEN_65 = dp(65);
        this.DIMEN_33 = dp(33);
        int dp4 = dp(7);
        this.DIMEN_7 = dp4;
        this.DIMEN_45 = dp(45);
        this.DIMEN_42 = dp(42);
        this.DIMEN_22 = dp(22);
        this.DIMEN_18 = dp(18);
        this.DIMEN_12 = dp(12);
        this.DIMEN_6 = dp(6);
        int i2 = dp4 + dp3;
        this.DIMEN_ONE_RIGHT = i2;
        this.DIMEN_TWO_LEFT = i2;
        int i3 = i2 + dp3;
        this.DIMEN_TWO_RIGHT = i3;
        this.DIMEN_THREE_LEFT = i3;
        int i4 = i3 + dp3;
        this.DIMEN_THREE_RIGHT = i4;
        this.DIMEN_FOUR_LEFT = i4;
        int i5 = i4 + dp3;
        this.DIMEN_FOUR_RIGHT = i5;
        this.DIMEN_FIVE_LEFT = i5;
        int i6 = i5 + dp3;
        this.DIMEN_FIVE_RIGHT = i6;
        this.DIMEN_SIX_LEFT = i6;
        int i7 = i6 + dp3;
        this.DIMEN_SIX_RIGHT = i7;
        this.DIMEN_SEVEN_LEFT = i7;
        this.DIMEN_SEVEN_RIGHT = i7 + dp3;
        this.mIsControl = false;
        init();
        initVui(this, attrs);
    }

    private void init() {
        this.mPaint = new Paint(1);
        this.mColorWhite = getContext().getColor(17170443);
        this.vuiSingleColor = Arrays.asList(getResources().getStringArray(R.array.atl_single_colors));
        for (int i : getResources().getIntArray(R.array.atl_single_color_index)) {
            this.vuiSingleColorIndex.add(Integer.valueOf(i));
        }
        this.vuiDoubleColor = Arrays.asList(getResources().getStringArray(R.array.atl_double_colors));
        initDrawable();
    }

    private void initDrawable() {
        this.mColorAlpha = getContext().getColor(R.color.atl_double_selected_inner);
        Drawable drawable = getResources().getDrawable(R.drawable.atl_item_bg_low, getContext().getTheme());
        this.mBgDrawable = drawable;
        drawable.setBounds(0, 0, this.VIEW_WIDTH, this.VIEW_HEIGHT);
        Drawable drawable2 = getResources().getDrawable(R.drawable.lamp_color_normal, getContext().getTheme());
        this.mSingleDrawable = drawable2;
        int i = this.DIMEN_18;
        drawable2.setBounds(i, i, this.VIEW_WIDTH - i, drawable2.getIntrinsicHeight() + i);
        Drawable drawable3 = getResources().getDrawable(R.drawable.lamp_color1, getContext().getTheme());
        this.mDrawableOne = drawable3;
        drawable3.setBounds(this.DIMEN_7, 0, this.DIMEN_ONE_RIGHT, drawable3.getIntrinsicHeight() + 0);
        Drawable drawable4 = getResources().getDrawable(R.drawable.lamp_color2, getContext().getTheme());
        this.mDrawableTwo = drawable4;
        drawable4.setBounds(this.DIMEN_TWO_LEFT, 0, this.DIMEN_TWO_RIGHT, drawable4.getIntrinsicHeight() + 0);
        Drawable drawable5 = getResources().getDrawable(R.drawable.lamp_color3, getContext().getTheme());
        this.mDrawableThree = drawable5;
        drawable5.setBounds(this.DIMEN_THREE_LEFT, 0, this.DIMEN_THREE_RIGHT, drawable5.getIntrinsicHeight() + 0);
        Drawable drawable6 = getResources().getDrawable(R.drawable.lamp_color4, getContext().getTheme());
        this.mDrawableFour = drawable6;
        drawable6.setBounds(this.DIMEN_FOUR_LEFT, 0, this.DIMEN_FOUR_RIGHT, drawable6.getIntrinsicHeight() + 0);
        Drawable drawable7 = getResources().getDrawable(R.drawable.lamp_color5, getContext().getTheme());
        this.mDrawableFive = drawable7;
        drawable7.setBounds(this.DIMEN_FIVE_LEFT, 0, this.DIMEN_FIVE_RIGHT, drawable7.getIntrinsicHeight() + 0);
        Drawable drawable8 = getResources().getDrawable(R.drawable.lamp_color6, getContext().getTheme());
        this.mDrawableSix = drawable8;
        drawable8.setBounds(this.DIMEN_SIX_LEFT, 0, this.DIMEN_SIX_RIGHT, drawable8.getIntrinsicHeight() + 0);
        Drawable drawable9 = getResources().getDrawable(R.drawable.lamp_color7, getContext().getTheme());
        this.mDrawableSeven = drawable9;
        drawable9.setBounds(this.DIMEN_SEVEN_LEFT, 0, this.DIMEN_SEVEN_RIGHT, drawable9.getIntrinsicHeight() + 0);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(this.VIEW_WIDTH, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(this.VIEW_HEIGHT, BasicMeasure.EXACTLY));
        this.mCenterY = getMeasuredHeight() / 2.0f;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mBgDrawable.setAlpha(isEnabled() ? 255 : 92);
        this.mBgDrawable.draw(canvas);
        if (1 == this.mColorMode) {
            drawSingle(canvas);
        } else {
            drawDouble(canvas);
        }
        callbackSlideColor();
    }

    private void callbackSlideColor() {
        int currentColor = getCurrentColor();
        OnStatusChangeListener onStatusChangeListener = this.mOnStatusChangeListener;
        if (onStatusChangeListener == null || currentColor == 0) {
            return;
        }
        onStatusChangeListener.onSlideColorChanged(currentColor);
    }

    private void drawSingle(Canvas canvas) {
        this.mSingleDrawable.setAlpha(isEnabled() ? 255 : 92);
        this.mSingleDrawable.draw(canvas);
        float f = this.mTouchX;
        int i = this.MIN_CENTER_X;
        if (f < i) {
            this.mTouchX = i;
        } else {
            int i2 = this.MAX_CENTER_X;
            if (f > i2) {
                this.mTouchX = i2;
            }
        }
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.DIMEN_6);
        this.mPaint.setColor(this.mColorWhite);
        this.mPaint.setAlpha(isEnabled() ? 255 : 92);
        float f2 = this.mTouchX;
        int i3 = this.DIMEN_45;
        float f3 = this.mCenterY;
        int i4 = this.DIMEN_25;
        canvas.drawRoundRect(f2 - i3, f3 - i4, f2 + i3, f3 + i4, i4, i4, this.mPaint);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(getTouchColor());
        this.mPaint.setAlpha(isEnabled() ? 255 : 0);
        float f4 = this.mTouchX;
        int i5 = this.DIMEN_42;
        float f5 = this.mCenterY;
        int i6 = this.DIMEN_22;
        canvas.drawRoundRect(f4 - i5, f5 - i6, f4 + i5, f5 + i6, i6, i6, this.mPaint);
    }

    private void drawDouble(Canvas canvas) {
        int i;
        int i2 = 77;
        this.mDrawableOne.setAlpha((isEnabled() && this.mDoubleColor == 0) ? 255 : 77);
        this.mDrawableOne.draw(canvas);
        this.mDrawableTwo.setAlpha((isEnabled() && this.mDoubleColor == 1) ? 255 : 77);
        this.mDrawableTwo.draw(canvas);
        this.mDrawableThree.setAlpha((isEnabled() && this.mDoubleColor == 2) ? 255 : 77);
        this.mDrawableThree.draw(canvas);
        this.mDrawableFour.setAlpha((isEnabled() && this.mDoubleColor == 3) ? 255 : 77);
        this.mDrawableFour.draw(canvas);
        this.mDrawableFive.setAlpha((isEnabled() && this.mDoubleColor == 4) ? 255 : 77);
        this.mDrawableFive.draw(canvas);
        this.mDrawableSix.setAlpha((isEnabled() && this.mDoubleColor == 5) ? 255 : 77);
        this.mDrawableSix.draw(canvas);
        Drawable drawable = this.mDrawableSeven;
        if (isEnabled() && this.mDoubleColor == 6) {
            i2 = 255;
        }
        drawable.setAlpha(i2);
        this.mDrawableSeven.draw(canvas);
        this.mPaint.setStyle(Paint.Style.STROKE);
        int i3 = this.DIMEN_7;
        float f = i3 + (this.mDoubleColor * i) + (this.DIMEN_138 / 2.0f);
        this.mPaint.setColor(this.mColorAlpha);
        this.mPaint.setAlpha(isEnabled() ? this.mPaint.getAlpha() : (int) (this.mPaint.getAlpha() * 0.36f));
        this.mPaint.setStrokeWidth(this.DIMEN_12);
        int i4 = this.DIMEN_62;
        float f2 = this.mCenterY;
        int i5 = this.DIMEN_30;
        canvas.drawRoundRect(f - i4, f2 - i5, f + i4, f2 + i5, i5, i5, this.mPaint);
        this.mPaint.setColor(this.mColorWhite);
        this.mPaint.setAlpha(isEnabled() ? 255 : 92);
        this.mPaint.setStrokeWidth(this.DIMEN_6);
        int i6 = this.DIMEN_65;
        float f3 = this.mCenterY;
        int i7 = this.DIMEN_33;
        canvas.drawRoundRect(f - i6, f3 - i7, f + i6, f3 + i7, i7, i7, this.mPaint);
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (action == 1 || action == 3) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(ev);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0015, code lost:
        if (r0 != 3) goto L12;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.isEnabled()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            int r0 = r5.getAction()
            r2 = 1
            if (r0 == 0) goto L23
            if (r0 == r2) goto L20
            r3 = 2
            if (r0 == r3) goto L18
            r5 = 3
            if (r0 == r5) goto L20
            goto L2c
        L18:
            float r5 = r5.getX()
            r4.centerChange(r5)
            goto L2c
        L20:
            r4.mIsControl = r1
            goto L2c
        L23:
            r4.mIsControl = r2
            float r5 = r5.getX()
            r4.centerChange(r5)
        L2c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.widget.AtlColorView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0013, code lost:
        if (r5 > r0) goto L5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void centerChange(float r5) {
        /*
            r4 = this;
            int r0 = r4.mColorMode
            r1 = 1
            if (r0 != r1) goto L28
            int r0 = r4.MIN_CENTER_X
            float r1 = (float) r0
            int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r1 >= 0) goto Le
        Lc:
            float r5 = (float) r0
            goto L16
        Le:
            int r0 = r4.MAX_CENTER_X
            float r1 = (float) r0
            int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r1 <= 0) goto L16
            goto Lc
        L16:
            r4.mTouchX = r5
            int r5 = r4.getTouchSingleColor()
            int r0 = r4.mSingleColor
            if (r5 == r0) goto L23
            r4.onColorChange(r5)
        L23:
            r4.invalidate()
            goto La8
        L28:
            int r0 = r4.DIMEN_30
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            r2 = 0
            r3 = -1
            if (r0 <= 0) goto L3a
            int r0 = r4.DIMEN_ONE_RIGHT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 >= 0) goto L3a
            r1 = r2
            goto L9a
        L3a:
            int r0 = r4.DIMEN_TWO_LEFT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 <= 0) goto L49
            int r0 = r4.DIMEN_TWO_RIGHT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 >= 0) goto L49
            goto L9a
        L49:
            int r0 = r4.DIMEN_THREE_LEFT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 <= 0) goto L59
            int r0 = r4.DIMEN_THREE_RIGHT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 >= 0) goto L59
            r1 = 2
            goto L9a
        L59:
            int r0 = r4.DIMEN_FOUR_LEFT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 <= 0) goto L69
            int r0 = r4.DIMEN_FOUR_RIGHT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 >= 0) goto L69
            r1 = 3
            goto L9a
        L69:
            int r0 = r4.DIMEN_FIVE_LEFT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 <= 0) goto L79
            int r0 = r4.DIMEN_FIVE_RIGHT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 >= 0) goto L79
            r1 = 4
            goto L9a
        L79:
            int r0 = r4.DIMEN_SIX_LEFT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 <= 0) goto L89
            int r0 = r4.DIMEN_SIX_RIGHT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 >= 0) goto L89
            r1 = 5
            goto L9a
        L89:
            int r0 = r4.DIMEN_SEVEN_LEFT
            float r0 = (float) r0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 <= 0) goto L99
            int r0 = r4.DIMEN_SEVEN_RIGHT
            float r0 = (float) r0
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 >= 0) goto L99
            r1 = 6
            goto L9a
        L99:
            r1 = r3
        L9a:
            if (r1 == r3) goto La8
            int r5 = r4.mDoubleColor
            if (r1 == r5) goto La8
            r4.mDoubleColor = r1
            r4.invalidate()
            r4.onColorChange(r2)
        La8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.widget.AtlColorView.centerChange(float):void");
    }

    private int getCurrentColor() {
        if (this.mColorMode == 1) {
            return getTouchColor();
        }
        switch (this.mDoubleColor) {
            case 0:
                return Color.parseColor("#FFDDDD");
            case 1:
                return Color.parseColor("#FF433D");
            case 2:
                return Color.parseColor("#FFEB61");
            case 3:
                return Color.parseColor("#0B6EFF");
            case 4:
                return Color.parseColor("#2DD5FF");
            case 5:
                return Color.parseColor("#B16CFF");
            case 6:
                return Color.parseColor("#FF267D");
            default:
                return 0;
        }
    }

    private int getTouchSingleColor() {
        int i = (int) ((this.mTouchX - this.MIN_CENTER_X) / this.COLOR_WIDTH_STEP);
        if (i < 0) {
            return 0;
        }
        if (i > 19) {
            return 19;
        }
        return i;
    }

    private int getTouchColor() {
        return Color.parseColor(SINGLE_COLOR_ARRAY[this.mSingleColor]);
    }

    private void onColorChange(int color) {
        if (this.mColorMode == 1) {
            if (color != this.mSingleColor) {
                this.mSingleColor = color;
                LogUtils.d(TAG, "singleColor " + this.mSingleColor);
                OnStatusChangeListener onStatusChangeListener = this.mOnStatusChangeListener;
                if (onStatusChangeListener != null) {
                    onStatusChangeListener.onSingleColorChanged(this.mSingleColor + 1);
                }
            }
        } else {
            int[] doubleColor = getDoubleColor();
            OnStatusChangeListener onStatusChangeListener2 = this.mOnStatusChangeListener;
            if (onStatusChangeListener2 != null && doubleColor != null) {
                onStatusChangeListener2.onDoubleColorChanged(doubleColor[0], doubleColor[1], this.mDoubleColor + 1);
            }
        }
        upDateScene();
    }

    private int[] getDoubleColor() {
        int[] iArr = new int[2];
        switch (this.mDoubleColor) {
            case 0:
                iArr[0] = 1;
                iArr[1] = 6;
                return iArr;
            case 1:
                iArr[0] = 2;
                iArr[1] = 5;
                return iArr;
            case 2:
                iArr[0] = 5;
                iArr[1] = 11;
                return iArr;
            case 3:
                iArr[0] = 14;
                iArr[1] = 6;
                return iArr;
            case 4:
                iArr[0] = 15;
                iArr[1] = 11;
                return iArr;
            case 5:
                iArr[0] = 15;
                iArr[1] = 18;
                return iArr;
            case 6:
                iArr[0] = 17;
                iArr[1] = 20;
                return iArr;
            default:
                return null;
        }
    }

    public void setDoubleColor(int firstColor, int secondColor) {
        LogUtils.d(TAG, "firstColor:" + firstColor + ",secondColor:" + secondColor + ",mIsControl:" + this.mIsControl);
        if (this.mIsControl || firstColor < 1 || firstColor > 20 || secondColor < 1 || secondColor > 20) {
            return;
        }
        if (firstColor == 1 && secondColor == 6) {
            this.mDoubleColor = 0;
        } else if (firstColor == 2 && secondColor == 5) {
            this.mDoubleColor = 1;
        } else if (firstColor == 5 && secondColor == 11) {
            this.mDoubleColor = 2;
        } else if (firstColor == 14 && secondColor == 6) {
            this.mDoubleColor = 3;
        } else if (firstColor == 15 && secondColor == 11) {
            this.mDoubleColor = 4;
        } else if (firstColor == 15 && secondColor == 18) {
            this.mDoubleColor = 5;
        } else if (firstColor == 17 && secondColor == 20) {
            this.mDoubleColor = 6;
        }
        upDateScene();
        postInvalidate();
    }

    public void setSingleColor(int color) {
        int i;
        LogUtils.d(TAG, "setSingleColor:" + color + ",mSingleColor:" + this.mSingleColor);
        if (this.mIsControl || color < 1 || color > 20 || this.mSingleColor == (i = color - 1)) {
            return;
        }
        this.mSingleColor = i;
        float f = this.COLOR_WIDTH_STEP;
        this.mTouchX = this.MIN_CENTER_X + (i * f) + (f / 2.0f);
        upDateScene();
        postInvalidate();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (XThemeManager.isThemeChanged(newConfig)) {
            initDrawable();
            callbackSlideColor();
            invalidate();
        }
    }

    public void setSelectorMode(int mode, String sceneId) {
        this.mColorMode = mode;
        this.mSceneId = sceneId;
        upDateScene();
        postInvalidate();
    }

    private int dp(int dp) {
        return (int) TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    public void setOnStatusChangeListener(OnStatusChangeListener onStatusChangeListener) {
        this.mOnStatusChangeListener = onStatusChangeListener;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent vuiEvent) {
        String str = (String) vuiEvent.getEventValue(vuiEvent);
        if (str != null) {
            LogUtils.d(TAG, "onVuiElementEvent:" + str, false);
            if (this.mColorMode == 1) {
                r0 = this.vuiSingleColor.contains(str) ? this.vuiSingleColor.indexOf(str) : -1;
                if (r0 < this.vuiSingleColorIndex.size()) {
                    setSingleColor(this.vuiSingleColorIndex.get(r0).intValue());
                    OnStatusChangeListener onStatusChangeListener = this.mOnStatusChangeListener;
                    if (onStatusChangeListener != null) {
                        onStatusChangeListener.onSingleColorChanged(this.mSingleColor + 1);
                    }
                }
            } else {
                for (String str2 : this.vuiDoubleColor) {
                    String[] split = str2.split("\\|");
                    int length = split.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        } else if (split[i].equals(str)) {
                            r0 = this.vuiDoubleColor.indexOf(str2);
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                if (r0 >= 0 && r0 < 7) {
                    this.mDoubleColor = r0;
                    onColorChange(r0);
                }
            }
            invalidate();
        }
        return true;
    }

    private void upDateScene() {
        String[] strArr;
        int indexOf = this.vuiSingleColorIndex.contains(Integer.valueOf(this.mSingleColor + 1)) ? this.vuiSingleColorIndex.indexOf(Integer.valueOf(this.mSingleColor + 1)) : this.vuiSingleColorIndex.size();
        LogUtils.d(TAG, "upDateScene:" + this.mColorMode + ",singleIndex:" + indexOf + "mDoubleColor:" + this.mDoubleColor, false);
        int i = this.mColorMode;
        if (i != 1) {
            indexOf = this.mDoubleColor;
        }
        if (i == 1) {
            strArr = (String[]) this.vuiSingleColor.toArray(new String[0]);
        } else {
            strArr = (String[]) this.vuiDoubleColor.toArray(new String[0]);
        }
        VuiUtils.setStatefulButtonAttr(this, indexOf, strArr, null, true);
    }
}
