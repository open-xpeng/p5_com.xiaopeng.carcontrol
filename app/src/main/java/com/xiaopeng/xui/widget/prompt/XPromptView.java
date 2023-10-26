package com.xiaopeng.xui.widget.prompt;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XTouchAreaUtils;
import com.xiaopeng.xui.widget.XFrameLayout;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class XPromptView extends XFrameLayout implements View.OnClickListener {
    private static final String TAG = "XPromptView";
    private ImageButton mActionView;
    private Button mActionView2;
    private ViewGroup mContentView;
    private Prompt mPrompt;
    private TextView mTextView;
    private int[] mTouchPadding;

    public XPromptView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.x_prompt, this);
        this.mContentView = (ViewGroup) findViewById(R.id.x_prompt_layout);
        this.mTextView = (TextView) findViewById(R.id.x_prompt_text);
        this.mActionView = (ImageButton) findViewById(R.id.x_prompt_btn);
        this.mActionView2 = (Button) findViewById(R.id.x_prompt_btn_2);
        findViewById(R.id.x_prompt_btn).setOnClickListener(this);
        findViewById(R.id.x_prompt_btn_2).setOnClickListener(this);
        setId(R.id.x_prompt);
        int dimension = (int) getResources().getDimension(R.dimen.x_prompt_padding_left);
        this.mTouchPadding = new int[]{dimension, dimension, dimension, dimension};
    }

    public View getContentView() {
        return this.mContentView;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Prompt prompt;
        if (view.getId() == R.id.x_prompt_btn) {
            Prompt prompt2 = this.mPrompt;
            if (prompt2 != null) {
                prompt2.onAction(0);
            }
        } else if (view.getId() != R.id.x_prompt_btn_2 || (prompt = this.mPrompt) == null) {
        } else {
            prompt.onAction(1);
        }
    }

    public void show(CharSequence charSequence, int i, CharSequence charSequence2) {
        this.mTextView.setText(charSequence);
        this.mActionView.setImageResource(i);
        this.mActionView.setVisibility(i > 0 ? 0 : 8);
        this.mActionView2.setText(charSequence2);
        this.mActionView2.setVisibility(TextUtils.isEmpty(charSequence2) ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        XTouchAreaUtils.extendTouchArea(this.mActionView, this.mContentView, this.mTouchPadding);
        XTouchAreaUtils.extendTouchArea(this.mActionView2, this.mContentView, this.mTouchPadding);
        super.onAttachedToWindow();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        Prompt prompt = this.mPrompt;
        if (prompt != null) {
            prompt.clearQueue();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        Prompt prompt = this.mPrompt;
        if (prompt != null) {
            prompt.setUseExitAnim(i == 0);
        }
    }

    public void setPrompt(Prompt prompt) {
        this.mPrompt = prompt;
    }

    public Prompt getPrompt() {
        return this.mPrompt;
    }
}
