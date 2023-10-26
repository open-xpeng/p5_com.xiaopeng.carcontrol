package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import com.xiaopeng.xpui.R;

/* loaded from: classes2.dex */
public class XListMultiple extends XRelativeLayout {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_TW0 = 1;
    private static final int TYPE_WRAP = 2;
    private ViewStub mBottomViewStub;
    private ViewStub mRightViewStub;
    private TextView mText;
    private TextView mTextSub;

    public XListMultiple(Context context) {
        this(context, null);
    }

    public XListMultiple(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XListMultiple(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XListMultiple(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        int i3;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XListMultiple);
        int i4 = obtainStyledAttributes.getInt(R.styleable.XListMultiple_list_multiple_type, 0);
        if (i4 == 1) {
            i3 = R.layout.x_list_multiple_two;
        } else if (i4 != 2) {
            i3 = R.layout.x_list_multiple;
        } else {
            i3 = R.layout.x_list_multiple_wrap;
        }
        LayoutInflater.from(context).inflate(i3, this);
        initView();
        this.mTextSub.setMaxLines(obtainStyledAttributes.getInt(R.styleable.XListMultiple_list_text_sub_lines, 1));
        setText(obtainStyledAttributes.getString(R.styleable.XListMultiple_list_multiple_text));
        setTextSub(obtainStyledAttributes.getString(R.styleable.XListMultiple_list_multiple_text_sub));
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.XListMultiple_list_multiple_bottom, -1);
        if (resourceId != -1) {
            this.mBottomViewStub.setLayoutResource(resourceId);
            this.mBottomViewStub.inflate();
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.XListMultiple_list_multiple_right, -1);
        if (resourceId2 != -1) {
            this.mRightViewStub.setLayoutResource(resourceId2);
            this.mRightViewStub.inflate();
        }
        obtainStyledAttributes.recycle();
    }

    private /* synthetic */ void lambda$new$0(View view) {
        logD("list-mul mBottomViewStub w " + view.getWidth() + " h " + view.getHeight());
    }

    private /* synthetic */ void lambda$new$1(int i, int i2) {
        logD("list-mul this w " + getWidth() + " h " + getHeight() + ", lines : " + i + ", type :" + i2);
    }

    private void initView() {
        this.mText = (TextView) findViewById(R.id.x_list_tv);
        this.mTextSub = (TextView) findViewById(R.id.x_list_tv_sub);
        this.mBottomViewStub = (ViewStub) findViewById(R.id.x_list_bottom);
        this.mRightViewStub = (ViewStub) findViewById(R.id.x_list_right);
    }

    public void setText(CharSequence charSequence) {
        this.mText.setText(charSequence);
    }

    public void setTextSub(CharSequence charSequence) {
        this.mTextSub.setText(charSequence);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        setEnabled(z, true);
    }

    public void setEnabled(boolean z, boolean z2) {
        super.setEnabled(z);
        if (z2) {
            setChildEnabled(this, z);
        }
    }

    private void setChildEnabled(ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                setChildEnabled((ViewGroup) childAt, z);
            }
            childAt.setEnabled(z);
        }
    }
}
