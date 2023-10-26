package com.xiaopeng.lludancemanager.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.xiaopeng.lludancemanager.R;

/* loaded from: classes2.dex */
public class LluAiSettingDialog extends LluSettingBaseDialog {
    public LluAiSettingDialog(Context context) {
        this(context, 0);
    }

    public LluAiSettingDialog(Context context, int dialogViewStyle) {
        super(context, dialogViewStyle);
    }

    @Override // com.xiaopeng.lludancemanager.view.LluSettingBaseDialog
    View getLayout(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.llu_ai_confirm_dialog, getContentView(), false);
    }

    @Override // com.xiaopeng.lludancemanager.view.LluSettingBaseDialog
    public void initCustomDialog(View rootView) {
        super.initCustomDialog(rootView);
        setTitle(R.string.llu_ai_dialog_title);
    }
}
