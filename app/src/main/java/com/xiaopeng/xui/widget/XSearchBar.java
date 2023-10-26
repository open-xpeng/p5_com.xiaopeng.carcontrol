package com.xiaopeng.xui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.xiaopeng.xpui.R;

/* loaded from: classes2.dex */
public class XSearchBar extends XTextInput {
    public XSearchBar(Context context) {
        this(context, null);
    }

    public XSearchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XSearchBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XSearchBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.xiaopeng.xui.widget.XTextInput
    protected void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.x_searchbar, this);
        this.mResetView = (ImageButton) findViewById(R.id.x_search_reset);
        this.mEditText = (EditText) findViewById(R.id.x_search_edit);
        this.mErrorTextView = (TextView) findViewById(R.id.x_search_error);
        this.mStatusView = findViewById(R.id.x_search_status);
    }
}
