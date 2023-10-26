package com.xiaopeng.carcontrol.view.control.hvac;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.view.widget.BlowModeInter;
import com.xiaopeng.carcontrol.view.widget.D21BlowModeView;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.widget.XLinearLayout;

/* loaded from: classes2.dex */
public class BlowModeView extends XLinearLayout implements BlowModeInter {
    private BlowModeInter mBlowModeView;

    public BlowModeView(Context context) {
        super(context);
        addView();
    }

    public BlowModeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView();
    }

    public BlowModeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView();
    }

    private void addView() {
        if (CarStatusUtils.isD55CarType()) {
            this.mBlowModeView = (BlowModeInter) LayoutInflater.from(getContext()).inflate(R.layout.d55_blow_mode_view, (ViewGroup) this, true).findViewById(R.id.d55_blow_view);
            return;
        }
        D21BlowModeView d21BlowModeView = new D21BlowModeView(getContext());
        d21BlowModeView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        addView(d21BlowModeView);
        this.mBlowModeView = d21BlowModeView;
    }

    @Override // com.xiaopeng.carcontrol.view.widget.BlowModeInter
    public void initView() {
        this.mBlowModeView.initView();
    }
}
