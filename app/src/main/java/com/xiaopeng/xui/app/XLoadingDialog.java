package com.xiaopeng.xui.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XLogUtils;

/* loaded from: classes2.dex */
public class XLoadingDialog extends Dialog implements Runnable {
    public static final int BOTTOM = 80;
    public static final int CENTER_VERTICAL = 16;
    public static final int TOP = 48;
    private ImageButton mCloseView;
    private Handler mHandler;
    private TextView mMessageView;
    private OnTimeOutListener mOnTimeOutListener;
    private int mTimeOut;
    private boolean mTimeOutCheck;

    /* loaded from: classes2.dex */
    public interface OnTimeOutListener {
        void onTimeOut(XLoadingDialog xLoadingDialog);
    }

    public XLoadingDialog(Context context, int i) {
        super(context, i);
        this.mHandler = new Handler();
    }

    public static XLoadingDialog show(Context context) {
        return show(context, false, null);
    }

    public static XLoadingDialog show(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        return show(context, context.getString(R.string.x_loading_dialog_message), z, onCancelListener);
    }

    public static XLoadingDialog show(Context context, CharSequence charSequence) {
        return show(context, charSequence, false, (DialogInterface.OnCancelListener) null);
    }

    public static XLoadingDialog show(Context context, CharSequence charSequence, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        return show(context, charSequence, z, onCancelListener, false, 0, null);
    }

    public static XLoadingDialog show(Context context, boolean z, int i, OnTimeOutListener onTimeOutListener) {
        return show(context, context.getString(R.string.x_loading_dialog_message), z, i, onTimeOutListener);
    }

    public static XLoadingDialog show(Context context, CharSequence charSequence, boolean z, int i, OnTimeOutListener onTimeOutListener) {
        return show(context, charSequence, false, null, z, i, onTimeOutListener);
    }

    public static XLoadingDialog show(Context context, CharSequence charSequence, boolean z, DialogInterface.OnCancelListener onCancelListener, boolean z2, int i, OnTimeOutListener onTimeOutListener) {
        XLoadingDialog xLoadingDialog = new XLoadingDialog(context, R.style.XAppTheme_XDialog_Loading);
        xLoadingDialog.create();
        xLoadingDialog.setMessage(charSequence);
        xLoadingDialog.setCancelable(z);
        xLoadingDialog.setOnCancelListener(onCancelListener);
        xLoadingDialog.setOnTimeOutListener(onTimeOutListener);
        xLoadingDialog.setTimeOutCheck(z2);
        xLoadingDialog.setTimeOut(i);
        xLoadingDialog.show();
        return xLoadingDialog;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.x_loading_dialog, (ViewGroup) null);
        this.mMessageView = (TextView) inflate.findViewById(R.id.x_loading_dialog_text);
        ImageButton imageButton = (ImageButton) inflate.findViewById(R.id.x_loading_dialog_close);
        this.mCloseView = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xui.app.XLoadingDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                XLoadingDialog.this.log("close is click ");
                XLoadingDialog.this.cancel();
            }
        });
        setContentView(inflate);
        super.onCreate(bundle);
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessageView.setText(charSequence);
    }

    @Override // android.app.Dialog
    public void setCancelable(boolean z) {
        super.setCancelable(false);
        this.mCloseView.setVisibility(z ? 0 : 8);
    }

    public void setOnTimeOutListener(OnTimeOutListener onTimeOutListener) {
        this.mOnTimeOutListener = onTimeOutListener;
    }

    public void setTimeOut(int i) {
        this.mTimeOut = i;
    }

    public void setTimeOutCheck(boolean z) {
        this.mTimeOutCheck = z;
        if (z) {
            return;
        }
        this.mHandler.removeCallbacks(this);
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        if (this.mTimeOutCheck) {
            this.mHandler.removeCallbacks(this);
            this.mHandler.postDelayed(this, this.mTimeOut);
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        log("dismiss");
        this.mHandler.removeCallbacks(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        OnTimeOutListener onTimeOutListener = this.mOnTimeOutListener;
        if (onTimeOutListener != null) {
            onTimeOutListener.onTimeOut(this);
        }
        log("time out");
        dismiss();
    }

    public void setVerticalTopDefault() {
        setVertical(48, (int) getContext().getResources().getDimension(R.dimen.x_loading_dialog_top));
    }

    public void setVertical(int i) {
        setVertical(48, i);
    }

    public void setVertical(int i, int i2) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = i;
            attributes.y = i2;
            window.setAttributes(attributes);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str) {
        XLogUtils.i("xpui-XLoadingDialog", str);
    }
}
