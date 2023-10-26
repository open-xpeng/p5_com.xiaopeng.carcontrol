package com.xiaopeng.xui.widget.dialogview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.widget.dialogview.XDialogViewInterface;

/* loaded from: classes2.dex */
public abstract class XDialogViewDelegate {
    protected Context mContext;
    protected XDialogView mXDelegator;

    public abstract ViewGroup getContentView();

    public abstract boolean isCloseShowing();

    public abstract boolean isNegativeButtonEnable();

    public abstract boolean isNegativeButtonShowing();

    public abstract boolean isPositiveButtonEnable();

    public abstract boolean isPositiveButtonShowing();

    public abstract void onBuildScenePrepare();

    public abstract boolean onKey(int i, KeyEvent keyEvent);

    public abstract void setCloseVisibility(boolean z);

    public abstract void setCustomView(int i);

    public abstract void setCustomView(int i, boolean z);

    public abstract void setCustomView(View view);

    public abstract void setCustomView(View view, boolean z);

    public abstract void setIcon(int i);

    public abstract void setIcon(Drawable drawable);

    public abstract void setMessage(int i);

    public abstract void setMessage(CharSequence charSequence);

    public abstract void setNegativeButton(int i);

    public abstract void setNegativeButton(int i, XDialogViewInterface.OnClickListener onClickListener);

    public abstract void setNegativeButton(CharSequence charSequence);

    public abstract void setNegativeButton(CharSequence charSequence, XDialogViewInterface.OnClickListener onClickListener);

    public abstract void setNegativeButtonEnable(boolean z);

    public abstract void setNegativeButtonInterceptDismiss(boolean z);

    public abstract void setNegativeButtonListener(XDialogViewInterface.OnClickListener onClickListener);

    public abstract void setOnCloseListener(XDialogViewInterface.OnCloseListener onCloseListener);

    public abstract void setOnCountDownListener(XDialogViewInterface.OnCountDownListener onCountDownListener);

    public abstract void setOnDismissListener(XDialogViewInterface.OnDismissListener onDismissListener);

    public abstract void setPositiveButton(int i);

    public abstract void setPositiveButton(int i, XDialogViewInterface.OnClickListener onClickListener);

    public abstract void setPositiveButton(CharSequence charSequence);

    public abstract void setPositiveButton(CharSequence charSequence, XDialogViewInterface.OnClickListener onClickListener);

    public abstract void setPositiveButtonEnable(boolean z);

    public abstract void setPositiveButtonInterceptDismiss(boolean z);

    public abstract void setPositiveButtonListener(XDialogViewInterface.OnClickListener onClickListener);

    @Deprecated
    public abstract void setSingleChoiceItems(CharSequence[] charSequenceArr, int i, XDialogViewInterface.OnClickListener onClickListener);

    public abstract void setThemeCallback(ThemeViewModel.OnCallback onCallback);

    public abstract void setTitle(int i);

    public abstract void setTitle(CharSequence charSequence);

    public abstract void setTitleBarVisibility(boolean z);

    @Deprecated
    public abstract void setTitleVisibility(boolean z);

    public abstract void startNegativeButtonCountDown(int i);

    public abstract void startPositiveButtonCountDown(int i);

    public static XDialogViewDelegate create(XDialogView xDialogView, Context context, int i) {
        return new XDialogViewDelegateImpl(xDialogView, context, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public XDialogViewDelegate(XDialogView xDialogView, Context context, int i) {
        this.mXDelegator = xDialogView;
        this.mContext = new ContextThemeWrapper(context, i <= 0 ? R.style.XDialogView : i);
    }
}
