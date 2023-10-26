package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class TempImmerseView extends View {
    private static final String TAG = "TempImmerseView";
    private String[] mColors;
    private Drawable mDrawableColor;

    public TempImmerseView(Context context) {
        super(context);
        init();
    }

    public TempImmerseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TempImmerseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable drawable = getResources().getDrawable(R.drawable.temp_immerse_bg, getContext().getTheme());
        this.mDrawableColor = drawable;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mDrawableColor.getIntrinsicHeight());
        this.mColors = new String[]{"#FF576D", "#FF6A5A", "#FF794E", "#FF814D", "#FF8A48", "#FF9246", "#FF9B45", "#FFAB46", "#FFB948", "#FFC84A", "#FFD852", "#FFE85B", "#FCF469", "#F4FA84", "#E9FE9C", "#DEFF9D", "#D5FF9A", "#CAFF97", "#B5FF9F", "#98FFAB", "#77FFB9", "#46FFC0", "#15FFC8", "#01F6D3", "#00E6E0", "#00D7ED", "#00C7FA", "#00A9FF", "#0183FF"};
    }

    public void setTempValue(int value) {
        LogUtils.d(TAG, "value:" + value);
        try {
            this.mDrawableColor.setTint(Color.parseColor(this.mColors[value]));
            invalidate();
        } catch (Exception e) {
            LogUtils.e(TAG, value + "e:" + e.toString());
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mDrawableColor.draw(canvas);
    }
}
