package com.xiaopeng.xui.widget.dialogview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.vui.VuiViewScene;
import com.xiaopeng.xui.widget.dialogview.XDialogViewInterface;

/* loaded from: classes2.dex */
public class XDialogView extends VuiViewScene {
    private XDialogViewInterface.IScreenPositionCallback mScreenPositionCallback;
    private XDialogViewDelegate mXDialogViewDelegate;

    public XDialogView(Context context) {
        this(context, 0);
    }

    public XDialogView(Context context, int i) {
        XDialogViewDelegate create = XDialogViewDelegate.create(this, context, i);
        this.mXDialogViewDelegate = create;
        setVuiView(create.getContentView());
    }

    public XDialogView setTitle(CharSequence charSequence) {
        this.mXDialogViewDelegate.setTitle(charSequence);
        return this;
    }

    public XDialogView setTitle(int i) {
        this.mXDialogViewDelegate.setTitle(i);
        return this;
    }

    public XDialogView setIcon(int i) {
        this.mXDialogViewDelegate.setIcon(i);
        return this;
    }

    public XDialogView setIcon(Drawable drawable) {
        this.mXDialogViewDelegate.setIcon(drawable);
        return this;
    }

    public XDialogView setMessage(CharSequence charSequence) {
        this.mXDialogViewDelegate.setMessage(charSequence);
        return this;
    }

    public XDialogView setMessage(int i) {
        this.mXDialogViewDelegate.setMessage(i);
        return this;
    }

    public XDialogView setCustomView(View view) {
        this.mXDialogViewDelegate.setCustomView(view);
        return this;
    }

    public XDialogView setCustomView(View view, boolean z) {
        this.mXDialogViewDelegate.setCustomView(view, z);
        return this;
    }

    public XDialogView setCustomView(int i) {
        this.mXDialogViewDelegate.setCustomView(i);
        return this;
    }

    public XDialogView setCustomView(int i, boolean z) {
        this.mXDialogViewDelegate.setCustomView(i, z);
        return this;
    }

    public XDialogView setCloseVisibility(boolean z) {
        this.mXDialogViewDelegate.setCloseVisibility(z);
        return this;
    }

    public boolean isCloseShowing() {
        return this.mXDialogViewDelegate.isCloseShowing();
    }

    @Deprecated
    public XDialogView setTitleVisibility(boolean z) {
        setTitleBarVisibility(z);
        return this;
    }

    public XDialogView setTitleBarVisibility(boolean z) {
        this.mXDialogViewDelegate.setTitleBarVisibility(z);
        return this;
    }

    public XDialogView setPositiveButton(int i) {
        this.mXDialogViewDelegate.setPositiveButton(i);
        return this;
    }

    public XDialogView setPositiveButton(CharSequence charSequence) {
        this.mXDialogViewDelegate.setPositiveButton(charSequence);
        return this;
    }

    public XDialogView setPositiveButtonListener(XDialogViewInterface.OnClickListener onClickListener) {
        this.mXDialogViewDelegate.setPositiveButtonListener(onClickListener);
        return this;
    }

    public XDialogView setPositiveButton(int i, XDialogViewInterface.OnClickListener onClickListener) {
        this.mXDialogViewDelegate.setPositiveButton(i, onClickListener);
        return this;
    }

    public XDialogView setPositiveButton(CharSequence charSequence, XDialogViewInterface.OnClickListener onClickListener) {
        this.mXDialogViewDelegate.setPositiveButton(charSequence, onClickListener);
        return this;
    }

    public XDialogView setNegativeButton(int i) {
        this.mXDialogViewDelegate.setNegativeButton(i);
        return this;
    }

    public XDialogView setNegativeButton(CharSequence charSequence) {
        this.mXDialogViewDelegate.setNegativeButton(charSequence);
        return this;
    }

    public XDialogView setNegativeButtonListener(XDialogViewInterface.OnClickListener onClickListener) {
        this.mXDialogViewDelegate.setNegativeButtonListener(onClickListener);
        return this;
    }

    public XDialogView setNegativeButton(int i, XDialogViewInterface.OnClickListener onClickListener) {
        this.mXDialogViewDelegate.setNegativeButton(i, onClickListener);
        return this;
    }

    public XDialogView setNegativeButton(CharSequence charSequence, XDialogViewInterface.OnClickListener onClickListener) {
        this.mXDialogViewDelegate.setNegativeButton(charSequence, onClickListener);
        return this;
    }

    public XDialogView setPositiveButtonInterceptDismiss(boolean z) {
        this.mXDialogViewDelegate.setPositiveButtonInterceptDismiss(z);
        return this;
    }

    public XDialogView setNegativeButtonInterceptDismiss(boolean z) {
        this.mXDialogViewDelegate.setNegativeButtonInterceptDismiss(z);
        return this;
    }

    public XDialogView setPositiveButtonEnable(boolean z) {
        this.mXDialogViewDelegate.setPositiveButtonEnable(z);
        return this;
    }

    public XDialogView setNegativeButtonEnable(boolean z) {
        this.mXDialogViewDelegate.setNegativeButtonEnable(z);
        return this;
    }

    public boolean isPositiveButtonEnable() {
        return this.mXDialogViewDelegate.isPositiveButtonEnable();
    }

    public boolean isNegativeButtonEnable() {
        return this.mXDialogViewDelegate.isNegativeButtonEnable();
    }

    public boolean isPositiveButtonShowing() {
        return this.mXDialogViewDelegate.isPositiveButtonShowing();
    }

    public boolean isNegativeButtonShowing() {
        return this.mXDialogViewDelegate.isNegativeButtonShowing();
    }

    public void startPositiveButtonCountDown(int i) {
        this.mXDialogViewDelegate.startPositiveButtonCountDown(i);
    }

    public void startNegativeButtonCountDown(int i) {
        this.mXDialogViewDelegate.startNegativeButtonCountDown(i);
    }

    public ViewGroup getContentView() {
        return this.mXDialogViewDelegate.getContentView();
    }

    public XDialogView setOnCloseListener(XDialogViewInterface.OnCloseListener onCloseListener) {
        this.mXDialogViewDelegate.setOnCloseListener(onCloseListener);
        return this;
    }

    public XDialogView setOnCountDownListener(XDialogViewInterface.OnCountDownListener onCountDownListener) {
        this.mXDialogViewDelegate.setOnCountDownListener(onCountDownListener);
        return this;
    }

    public XDialogView setOnDismissListener(XDialogViewInterface.OnDismissListener onDismissListener) {
        this.mXDialogViewDelegate.setOnDismissListener(onDismissListener);
        return this;
    }

    public XDialogView setScreenPositionCallback(XDialogViewInterface.IScreenPositionCallback iScreenPositionCallback) {
        this.mScreenPositionCallback = iScreenPositionCallback;
        return this;
    }

    public void setThemeCallback(ThemeViewModel.OnCallback onCallback) {
        this.mXDialogViewDelegate.setThemeCallback(onCallback);
    }

    public boolean onKey(int i, KeyEvent keyEvent) {
        return this.mXDialogViewDelegate.onKey(i, keyEvent);
    }

    @Override // com.xiaopeng.xui.vui.VuiViewScene
    public void onBuildScenePrepare() {
        this.mXDialogViewDelegate.onBuildScenePrepare();
    }

    @Override // com.xiaopeng.xui.vui.IVuiViewScene
    public int getVuiDisplayLocation() {
        XDialogViewInterface.IScreenPositionCallback iScreenPositionCallback = this.mScreenPositionCallback;
        if (iScreenPositionCallback == null) {
            return 0;
        }
        return iScreenPositionCallback.getScreenPositionInfo();
    }
}
