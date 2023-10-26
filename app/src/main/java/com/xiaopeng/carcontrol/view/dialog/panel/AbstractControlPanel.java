package com.xiaopeng.carcontrol.view.dialog.panel;

import android.app.Dialog;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.view.dialog.AbstractPanel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialogPure;

/* loaded from: classes2.dex */
public abstract class AbstractControlPanel extends AbstractPanel {
    protected XDialogPure mControlPanel;

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected Dialog onCreatePanel() {
        XDialogPure xDialogPure = new XDialogPure(App.getInstance(), XDialogPure.Parameters.Builder().setLayoutParams(getLayoutParameters()));
        this.mControlPanel = xDialogPure;
        Dialog dialog = xDialogPure.getDialog();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setElevation(0.0f);
        }
        return dialog;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected View onCreateView() {
        return LayoutInflater.from(App.getInstance()).inflate(getLayoutId(), (ViewGroup) null, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onViewAttach() {
        this.mControlPanel.setContentView(getRootView());
        super.onViewAttach();
    }

    private ViewGroup.LayoutParams getLayoutParameters() {
        Resources resources = this.mContext.getResources();
        return new ViewGroup.LayoutParams(resources.getDimensionPixelOffset(getPanelWidth()), resources.getDimensionPixelOffset(getPanelHeight()));
    }

    protected int getPanelWidth() {
        return R.dimen.x_dialog_large_width;
    }

    protected int getPanelHeight() {
        return R.dimen.x_dialog_large_height;
    }
}
