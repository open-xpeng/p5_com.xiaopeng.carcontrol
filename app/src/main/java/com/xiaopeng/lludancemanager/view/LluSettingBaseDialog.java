package com.xiaopeng.lludancemanager.view;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.widget.XSwitch;

/* loaded from: classes2.dex */
public abstract class LluSettingBaseDialog extends XDialog implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "LluSettingBaseDialog";
    private XSwitch mAutoVolumeSwitch;
    private XSwitch mAutoWindowSwitch;
    protected Context mContext;
    private View mRootView;

    abstract View getLayout(Context context);

    /* JADX INFO: Access modifiers changed from: package-private */
    public LluSettingBaseDialog(Context context) {
        this(context, 0);
    }

    public LluSettingBaseDialog(Context context, int dialogViewStyle) {
        super(context, dialogViewStyle, XDialog.Parameters.Builder().setFullScreen(true));
        this.mContext = context;
        initView(context);
        initCustomDialog(this.mRootView);
        setListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setListener() {
        this.mAutoWindowSwitch.setOnCheckedChangeListener(this);
        this.mAutoVolumeSwitch.setOnCheckedChangeListener(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initCustomDialog(View view) {
        this.mAutoVolumeSwitch.setChecked(SharedPreferenceUtil.getLluAutoVolume());
        this.mAutoWindowSwitch.setChecked(SharedPreferenceUtil.getLluAutoWindow());
    }

    private void initView(Context context) {
        View layout = getLayout(context);
        this.mRootView = layout;
        this.mAutoVolumeSwitch = (XSwitch) layout.findViewById(R.id.llu_double_confirm_dialog_auto_volume);
        this.mAutoWindowSwitch = (XSwitch) this.mRootView.findViewById(R.id.llu_double_confirm_dialog_auto_window);
        setCustomView(this.mRootView, false);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == this.mAutoVolumeSwitch) {
            SharedPreferenceUtil.setLluAutoVolume(isChecked);
            this.mAutoVolumeSwitch.setChecked(isChecked);
        } else if (buttonView == this.mAutoWindowSwitch) {
            SharedPreferenceUtil.setLluAutoWindow(isChecked);
        }
    }

    public XSwitch getAutoVolumeSwitch() {
        return this.mAutoVolumeSwitch;
    }

    public XSwitch getAutoWindowSwitch() {
        return this.mAutoWindowSwitch;
    }
}
