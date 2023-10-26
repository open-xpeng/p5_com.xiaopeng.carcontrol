package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XSwitch;

/* loaded from: classes2.dex */
public class XSwitchText extends XLinearLayout {
    private OnCheckedChangeListener mCheckedListener;
    private View mInfo;
    private OnInfoClickListener mInfoListener;
    private XSwitch mSwitch;

    /* loaded from: classes2.dex */
    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked, boolean fromUser);

        boolean onInterceptChecked(boolean isChecked, boolean fromUser);
    }

    /* loaded from: classes2.dex */
    public interface OnInfoClickListener {
        void onClick();
    }

    public XSwitchText(Context context) {
        this(context, null);
    }

    public XSwitchText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XSwitchText(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public XSwitchText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        setOrientation(0);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.XSwitchText, defStyleAttr, defStyleRes);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.XSwitchText_android_textColor);
        if (colorStateList != null) {
            setTextColor(colorStateList);
        } else {
            setTextColor(obtainStyledAttributes.getColor(R.styleable.XSwitchText_android_textColor, 0));
        }
        setText(obtainStyledAttributes.getString(R.styleable.XSwitchText_android_text));
        setInfoBtnVisibility(obtainStyledAttributes.getBoolean(R.styleable.XSwitchText_info_visible, false));
        obtainStyledAttributes.recycle();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.x_switch_text, this);
        this.mSwitch = (XSwitch) findViewById(R.id.x_switch);
        this.mInfo = findViewById(R.id.x_info);
        this.mSwitch.setSoundEffectsEnabled(true);
        this.mSwitch.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$XSwitchText$SnQqK2cOQFwsqaZeux_oh6JU3Xk
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view, boolean z) {
                return XSwitchText.this.lambda$init$0$XSwitchText(view, z);
            }
        });
        this.mInfo.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$XSwitchText$ZjG2OsfKewn4xnzyvVgrbp3XNVU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                XSwitchText.this.lambda$init$1$XSwitchText(view);
            }
        });
    }

    public /* synthetic */ boolean lambda$init$0$XSwitchText(View view, boolean b) {
        if (view.isPressed()) {
            this.mCheckedListener.onCheckedChanged(b, true);
        }
        return view.isPressed();
    }

    public /* synthetic */ void lambda$init$1$XSwitchText(View v) {
        OnInfoClickListener onInfoClickListener = this.mInfoListener;
        if (onInfoClickListener != null) {
            onInfoClickListener.onClick();
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mSwitch != null) {
            setTouchDelegate(new TouchDelegate(new Rect(0, 0, getWidth(), getHeight()), this.mSwitch));
        }
    }

    public void setTextColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.mSwitch.setTextColor(colorStateList);
        }
    }

    public void setTextColor(int colorId) {
        if (colorId != 0) {
            this.mSwitch.setTextColor(colorId);
        }
    }

    public void setText(CharSequence text) {
        this.mSwitch.setText(text);
    }

    public void setText(int textId) {
        this.mSwitch.setText(textId);
    }

    public void setChecked(boolean isChecked) {
        this.mSwitch.setChecked(isChecked);
    }

    public void setInfoBtnVisibility(boolean visible) {
        this.mInfo.setVisibility(visible ? 0 : 8);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mCheckedListener = listener;
    }

    public void setOnInfoClickListener(OnInfoClickListener listener) {
        this.mInfoListener = listener;
    }
}
