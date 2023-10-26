package com.xiaopeng.xui.widget.quicksidebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.sound.XSoundEffectManager;
import com.xiaopeng.xui.view.XView;
import com.xiaopeng.xui.view.XViewDelegate;
import com.xiaopeng.xui.view.font.XFontScaleHelper;
import com.xiaopeng.xui.widget.quicksidebar.listener.OnQuickSideBarTouchListener;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class XQuickSideBarView extends XView implements XViewDelegate.onFontScaleChangeCallback {
    private OnQuickSideBarTouchListener listener;
    private boolean mAlwaysHighlight;
    private int mChoose;
    private int mHeight;
    private boolean mIsTouchMove;
    private float mItemHeight;
    private final float mItemStartY;
    private float mItemWidth;
    private List<String> mLetters;
    private Paint mPaint;
    private int mTextColor;
    private int mTextColorChoose;
    private float mTextSize;
    private float mTextSizeChoose;
    private int mWidth;

    @Override // com.xiaopeng.xui.view.XViewDelegate.onFontScaleChangeCallback
    public void onFontScaleChanged() {
    }

    public XQuickSideBarView(Context context) {
        this(context, null);
    }

    public XQuickSideBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XQuickSideBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChoose = -1;
        this.mPaint = new Paint();
        this.mItemStartY = 14.0f;
        this.mIsTouchMove = false;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mLetters = Arrays.asList(context.getResources().getStringArray(R.array.x_quick_side_bar_letters));
        initDefaultColor();
        this.mTextSize = context.getResources().getDimensionPixelSize(R.dimen.x_sidebar_textsize);
        this.mTextSizeChoose = context.getResources().getDimensionPixelSize(R.dimen.x_sidebar_choose_textsize);
        this.mItemHeight = context.getResources().getDimension(R.dimen.x_sidebar_item_height);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.XQuickSideBarView);
            this.mTextColor = obtainStyledAttributes.getColor(R.styleable.XQuickSideBarView_sidebarTextColor, this.mTextColor);
            this.mTextColorChoose = obtainStyledAttributes.getColor(R.styleable.XQuickSideBarView_sidebarTextColorChoose, this.mTextColorChoose);
            this.mTextSize = obtainStyledAttributes.getDimension(R.styleable.XQuickSideBarView_sidebarTextSize, this.mTextSize);
            this.mTextSizeChoose = obtainStyledAttributes.getDimension(R.styleable.XQuickSideBarView_sidebarTextSizeChoose, this.mTextSizeChoose);
            this.mAlwaysHighlight = obtainStyledAttributes.getBoolean(R.styleable.XQuickSideBarView_sidebarHighlight, false);
            final XFontScaleHelper create = XFontScaleHelper.create(obtainStyledAttributes, R.styleable.XQuickSideBarView_sidebarTextSize, R.dimen.x_sidebar_textsize);
            final XFontScaleHelper create2 = XFontScaleHelper.create(obtainStyledAttributes, R.styleable.XQuickSideBarView_sidebarTextSizeChoose, R.dimen.x_sidebar_choose_textsize);
            if (this.mXViewDelegate != null) {
                this.mXViewDelegate.setFontScaleChangeCallback(new XViewDelegate.onFontScaleChangeCallback() { // from class: com.xiaopeng.xui.widget.quicksidebar.-$$Lambda$XQuickSideBarView$u4uxzFxLEjJao2LY-DKHZVIpygU
                    @Override // com.xiaopeng.xui.view.XViewDelegate.onFontScaleChangeCallback
                    public final void onFontScaleChanged() {
                        XQuickSideBarView.this.lambda$init$0$XQuickSideBarView(create, create2);
                    }
                });
            }
            this.mItemHeight = obtainStyledAttributes.getDimension(R.styleable.XQuickSideBarView_sidebarItemHeight, this.mItemHeight);
            obtainStyledAttributes.recycle();
        }
        if (this.mXViewDelegate != null && this.mXViewDelegate.getThemeViewModel() != null) {
            this.mXViewDelegate.getThemeViewModel().setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.xui.widget.quicksidebar.-$$Lambda$XQuickSideBarView$ZPkE7D2FNYYDmFTRL9rGUctQ7G8
                @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
                public final void onThemeChanged() {
                    XQuickSideBarView.this.lambda$init$1$XQuickSideBarView();
                }
            });
        }
        setSoundEffectsEnabled(false);
    }

    public /* synthetic */ void lambda$init$0$XQuickSideBarView(XFontScaleHelper xFontScaleHelper, XFontScaleHelper xFontScaleHelper2) {
        if (xFontScaleHelper != null) {
            this.mTextSize = xFontScaleHelper.getCurrentFontSize(getResources().getDisplayMetrics());
        }
        if (xFontScaleHelper2 != null) {
            this.mTextSizeChoose = xFontScaleHelper2.getCurrentFontSize(getResources().getDisplayMetrics());
        }
        invalidate();
    }

    public /* synthetic */ void lambda$init$1$XQuickSideBarView() {
        initDefaultColor();
        invalidate();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mHeight = getMeasuredHeight();
        this.mWidth = getMeasuredWidth();
        float size = this.mHeight / this.mLetters.size();
        float f = this.mItemHeight;
        if (f <= size) {
            size = f;
        }
        this.mItemHeight = size;
        this.mItemWidth = size;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = 0;
        while (i < this.mLetters.size()) {
            Paint paint = new Paint();
            if (i == this.mChoose) {
                this.mPaint.setTextSize(this.mTextSizeChoose);
                this.mPaint.setColor(this.mTextColorChoose);
                paint.setColor(getColor(R.color.x_side_bar_item_choose_bg_color));
            } else {
                this.mPaint.setTextSize(this.mTextSize);
                this.mPaint.setColor(this.mTextColor);
                paint.setColor(getColor(R.color.x_side_bar_item_bg_color));
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            float f = (this.mWidth - this.mItemWidth) / 2.0f;
            float f2 = this.mItemHeight;
            int i2 = i + 1;
            RectF rectF = new RectF(f, (i * f2) + 14.0f, this.mItemWidth + f, (i2 * f2) + 14.0f);
            canvas.drawRoundRect(rectF, 4.0f, 4.0f, paint);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            this.mPaint.setFakeBoldText(true);
            this.mPaint.setTypeface(Typeface.create(getResources().getString(R.string.x_font_typeface_medium), 0));
            Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
            canvas.drawText(this.mLetters.get(i), rectF.centerX(), rectF.centerY() + (((fontMetrics.bottom - fontMetrics.top) / 2.0f) - fontMetrics.bottom), this.mPaint);
            this.mPaint.reset();
            i = i2;
        }
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float y = motionEvent.getY();
        int i = this.mChoose;
        int i2 = (int) ((y - 14.0f) / this.mItemHeight);
        if (action != 0) {
            if (action == 1) {
                this.mIsTouchMove = false;
                this.mChoose = -1;
                OnQuickSideBarTouchListener onQuickSideBarTouchListener = this.listener;
                if (onQuickSideBarTouchListener != null) {
                    onQuickSideBarTouchListener.onLetterTouched(false);
                }
                if (!this.mAlwaysHighlight) {
                    invalidate();
                }
            } else if (action != 2) {
                if (action == 3) {
                    this.mIsTouchMove = false;
                    XSoundEffectManager.get().release(2);
                    OnQuickSideBarTouchListener onQuickSideBarTouchListener2 = this.listener;
                    if (onQuickSideBarTouchListener2 != null) {
                        onQuickSideBarTouchListener2.onLetterTouched(false);
                    }
                    this.mChoose = -1;
                    invalidate();
                }
            }
            return true;
        }
        this.mIsTouchMove = true;
        if (i != i2 && i2 >= 0 && i2 < this.mLetters.size()) {
            this.mChoose = i2;
            invalidate();
            XSoundEffectManager.get().play(2);
            OnQuickSideBarTouchListener onQuickSideBarTouchListener3 = this.listener;
            if (onQuickSideBarTouchListener3 != null) {
                onQuickSideBarTouchListener3.onLetterTouched(true);
                this.listener.onLetterScrolling(this.mLetters.get(i2), this.mChoose);
            }
        }
        return true;
    }

    public OnQuickSideBarTouchListener getListener() {
        return this.listener;
    }

    public void setOnQuickSideBarTouchListener(OnQuickSideBarTouchListener onQuickSideBarTouchListener) {
        this.listener = onQuickSideBarTouchListener;
    }

    public List<String> getLetters() {
        return this.mLetters;
    }

    public void setLetters(List<String> list) {
        this.mLetters = list;
        invalidate();
    }

    private void initDefaultColor() {
        this.mTextColor = getColor(R.color.x_theme_text_03);
        this.mTextColorChoose = getColor(R.color.x_side_bar_text_choose_color);
    }

    private int getColor(int i) {
        return getResources().getColor(i, getContext().getTheme());
    }

    public void setSideBarHighLight(String str) {
        setSideBarHighLight(str, false);
    }

    public void setSideBarHighLight(String str, boolean z) {
        int indexOf;
        if (this.mAlwaysHighlight && !this.mIsTouchMove) {
            logD("setSideBarHighLight");
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (!z) {
                indexOf = this.mLetters.indexOf(str.toUpperCase());
            } else {
                indexOf = this.mLetters.indexOf(str);
            }
            if (indexOf < 0 || this.mChoose == indexOf) {
                return;
            }
            this.mChoose = indexOf;
            invalidate();
        }
    }

    private void setSideBarHighLight(int i) {
        if (this.mAlwaysHighlight && !this.mIsTouchMove) {
            int size = this.mLetters.size();
            if (i < 0 || i >= size || this.mChoose == i) {
                return;
            }
            this.mChoose = i;
            invalidate();
        }
    }
}
