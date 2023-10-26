package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.UIUtils;
import com.xiaopeng.carcontrol.view.speech.FictitiousElementsConstructor;
import com.xiaopeng.carcontrol.view.speech.SliderFictitiousElementsConstructor;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;

/* loaded from: classes2.dex */
public class AirLevelSeekBar extends View implements IVuiElementListener {
    public static final int MAX_VOLUME;
    public static final int MIN_VOLUME = 1;
    private static final String TAG = "AirLevelSeekBar";
    private FictitiousElementsConstructor constructor;
    private int mCurrentVolume;
    private Drawable[] mDrawablePressedArray;
    private int[] mDrawableResourceArray;
    private Drawable mDrawableVolumeBg;
    private int mOffsetLayout;
    private float mOffsetTouch;
    private OnMovingChangeListener onMovingChangeListener;
    private OnVolumeChangeListener onVolumeChangeListener;

    /* loaded from: classes2.dex */
    public interface OnMovingChangeListener {
        void onVolumeChanged(int volume);
    }

    /* loaded from: classes2.dex */
    public interface OnVolumeChangeListener {
        void onTouched();

        void onVolumeChanged(int volume);
    }

    static {
        MAX_VOLUME = CarStatusUtils.isD55CarType() ? 10 : 7;
    }

    public AirLevelSeekBar(Context context) {
        super(context);
        init();
    }

    public AirLevelSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AirLevelSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnVolumeChangeListener(OnVolumeChangeListener onVolumeChangeListener) {
        this.onVolumeChangeListener = onVolumeChangeListener;
    }

    public void setOnMovingChangeListener(OnMovingChangeListener onMovingChangeListener) {
        this.onMovingChangeListener = onMovingChangeListener;
    }

    private void init() {
        if (CarStatusUtils.isD55CarType()) {
            this.mOffsetLayout = UIUtils.dp2px(getContext(), 60);
            this.mOffsetTouch = UIUtils.dp2px(getContext(), 63);
            this.mDrawableResourceArray = new int[]{R.drawable.ic_55air_volume_pressed1, R.drawable.ic_55air_volume_pressed2, R.drawable.ic_55air_volume_pressed3, R.drawable.ic_55air_volume_pressed4, R.drawable.ic_55air_volume_pressed5, R.drawable.ic_55air_volume_pressed6, R.drawable.ic_55air_volume_pressed7, R.drawable.ic_55air_volume_pressed8, R.drawable.ic_55air_volume_pressed9, R.drawable.ic_55air_volume_pressed10};
            this.constructor = new SliderFictitiousElementsConstructor(1, 10, 1.0f);
        } else {
            this.mOffsetLayout = UIUtils.dp2px(getContext(), 74);
            this.mOffsetTouch = UIUtils.dp2px(getContext(), 76);
            this.mDrawableResourceArray = new int[]{R.drawable.ic_21air_volume_pressed1, R.drawable.ic_21air_volume_pressed2, R.drawable.ic_21air_volume_pressed3, R.drawable.ic_21air_volume_pressed4, R.drawable.ic_21air_volume_pressed5, R.drawable.ic_21air_volume_pressed6, R.drawable.ic_21air_volume_pressed7};
            this.constructor = new SliderFictitiousElementsConstructor(1, 7, 1.0f);
        }
        this.mDrawablePressedArray = new Drawable[MAX_VOLUME];
        initDrawable();
    }

    private void initDrawable() {
        if (CarStatusUtils.isD55CarType()) {
            this.mDrawableVolumeBg = getResources().getDrawable(R.drawable.ic_55air_volume_normal, getContext().getTheme());
        } else {
            this.mDrawableVolumeBg = getResources().getDrawable(R.drawable.ic_21air_volume_normal, getContext().getTheme());
        }
        this.mDrawableVolumeBg.setBounds(0, getPaddingTop(), this.mDrawableVolumeBg.getIntrinsicWidth(), this.mDrawableVolumeBg.getIntrinsicHeight() + getPaddingTop());
        for (int i = 0; i < this.mDrawableResourceArray.length; i++) {
            Drawable drawable = getResources().getDrawable(this.mDrawableResourceArray[i], getContext().getTheme());
            drawable.setBounds(this.mOffsetLayout * i, getPaddingTop(), (this.mOffsetLayout * i) + drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight() + getPaddingTop());
            this.mDrawablePressedArray[i] = drawable;
        }
    }

    public void setSelectedTintColor(int color) {
        int i = 0;
        while (true) {
            Drawable[] drawableArr = this.mDrawablePressedArray;
            if (i < drawableArr.length) {
                drawableArr[i].setTint(color);
                i++;
            } else {
                postInvalidate();
                return;
            }
        }
    }

    public void clearSelectedTintColor() {
        int i = 0;
        while (true) {
            Drawable[] drawableArr = this.mDrawablePressedArray;
            if (i >= drawableArr.length) {
                return;
            }
            drawableArr[i].setTintList(null);
            i++;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(this.mDrawableVolumeBg.getIntrinsicWidth(), View.MeasureSpec.getMode(widthMeasureSpec)), View.MeasureSpec.makeMeasureSpec(this.mDrawableVolumeBg.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom(), View.MeasureSpec.getMode(heightMeasureSpec)));
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = this.mDrawableVolumeBg;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        for (int i = 0; i < this.mCurrentVolume; i++) {
            Drawable drawable2 = this.mDrawablePressedArray[i];
            if (drawable2 != null) {
                drawable2.draw(canvas);
            }
        }
    }

    public void setCurrentVolume(int currentVolume) {
        if (currentVolume < 0) {
            currentVolume = 0;
        }
        int i = MAX_VOLUME;
        if (currentVolume > i) {
            currentVolume = i;
        }
        this.mCurrentVolume = currentVolume;
        postInvalidate();
    }

    public void setCurrentVolumeFromUser(int currentVolume) {
        if (this.mCurrentVolume != currentVolume && currentVolume <= MAX_VOLUME) {
            SoundHelper.playNotification(SoundHelper.PATH_WHEEL_SCROLL_7);
        }
        setCurrentVolume(currentVolume);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0017, code lost:
        if (r1 != 3) goto L12;
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
            if (r0 != 0) goto L8
            r5 = 0
            return r5
        L8:
            int r0 = r4.mCurrentVolume
            int r1 = r5.getAction()
            r2 = 1
            if (r1 == 0) goto L2c
            if (r1 == r2) goto L22
            r3 = 2
            if (r1 == r3) goto L1a
            r5 = 3
            if (r1 == r5) goto L22
            goto L3a
        L1a:
            float r5 = r5.getX()
            r4.analyticVolume(r5)
            goto L3a
        L22:
            com.xiaopeng.carcontrol.view.widget.AirLevelSeekBar$OnVolumeChangeListener r5 = r4.onVolumeChangeListener
            if (r5 == 0) goto L3a
            int r1 = r4.mCurrentVolume
            r5.onVolumeChanged(r1)
            goto L3a
        L2c:
            com.xiaopeng.carcontrol.view.widget.AirLevelSeekBar$OnVolumeChangeListener r1 = r4.onVolumeChangeListener
            if (r1 == 0) goto L33
            r1.onTouched()
        L33:
            float r5 = r5.getX()
            r4.analyticVolume(r5)
        L3a:
            int r5 = r4.mCurrentVolume
            if (r0 == r5) goto L43
            java.lang.String r5 = "/system/media/audio/xiaopeng/cdu/wav/CDU_wheel_scroll_7.wav"
            com.xiaopeng.carcontrol.helper.SoundHelper.playNotification(r5)
        L43:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.widget.AirLevelSeekBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void analyticVolume(float touchX) {
        int i = ((int) (touchX / this.mOffsetTouch)) + 1;
        int i2 = MAX_VOLUME;
        if (i > i2) {
            i = i2;
        }
        int i3 = i >= 1 ? i : 1;
        if (i3 != this.mCurrentVolume) {
            this.mCurrentVolume = i3;
            invalidate();
        }
    }

    public void setThemeChanged(int resId) {
        LogUtils.d(TAG, "resId " + resId);
        initDrawable();
        invalidate();
    }

    public int getCurrentVolume() {
        return this.mCurrentVolume;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String id, IVuiElementBuilder iVuiElementBuilder) {
        return this.constructor.generateElement(id, Integer.valueOf(getCurrentVolume()), getResources().getString(R.string.hvac_wind_level_label));
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent vuiEvent) {
        Double d;
        if (this.onVolumeChangeListener == null || vuiEvent == null || (d = (Double) vuiEvent.getEventValue(vuiEvent)) == null) {
            return false;
        }
        this.onVolumeChangeListener.onVolumeChanged(d.intValue());
        return false;
    }
}
