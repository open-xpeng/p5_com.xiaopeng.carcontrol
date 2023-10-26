package com.xiaopeng.xui.widget.dialogview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.widget.XFrameLayout;

/* loaded from: classes2.dex */
class XDialogMessage extends XFrameLayout {
    private ImageView mImageView;
    private TextView mTextMessage;

    public XDialogMessage(Context context) {
        this(context, null);
    }

    public XDialogMessage(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XDialogMessage(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XDialogMessage(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        LayoutInflater.from(context).inflate(R.layout.x_dialog_message, this);
        initView();
    }

    private void initView() {
        this.mTextMessage = (TextView) findViewById(R.id.x_dialog_message);
        this.mImageView = (ImageView) findViewById(R.id.x_dialog_icon);
    }

    public void setMessage(int i) {
        this.mTextMessage.setText(i);
        checkLines();
    }

    public void setMessage(CharSequence charSequence) {
        this.mTextMessage.setText(charSequence);
        checkLines();
    }

    private void checkLines() {
        if (this.mTextMessage.getWidth() > 0) {
            _checkLines();
        } else {
            this.mTextMessage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.xiaopeng.xui.widget.dialogview.XDialogMessage.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    XDialogMessage.this.mTextMessage.getViewTreeObserver().removeOnPreDrawListener(this);
                    XDialogMessage.this._checkLines();
                    return true;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _checkLines() {
        if (this.mTextMessage.getLineCount() < 2) {
            this.mTextMessage.setGravity(1);
        } else {
            this.mTextMessage.setGravity(GravityCompat.START);
        }
        this.mTextMessage.setVisibility(0);
    }

    public void setIcon(int i) {
        this.mImageView.setImageResource(i);
        this.mImageView.setVisibility(i != 0 ? 0 : 8);
    }

    public void setIcon(Drawable drawable) {
        this.mImageView.setImageResource(0);
        this.mImageView.setImageDrawable(drawable);
        this.mImageView.setVisibility(drawable == null ? 8 : 0);
    }
}
