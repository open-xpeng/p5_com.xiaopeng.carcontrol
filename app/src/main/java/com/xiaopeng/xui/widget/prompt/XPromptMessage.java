package com.xiaopeng.xui.widget.prompt;

/* loaded from: classes2.dex */
public class XPromptMessage {
    private CharSequence mButton;
    private int mDuration;
    private int mIcon;
    private String mId;
    private CharSequence mText;

    public XPromptMessage(CharSequence charSequence) {
        this.mText = charSequence;
    }

    public XPromptMessage(int i, CharSequence charSequence) {
        this.mDuration = i;
        this.mText = charSequence;
    }

    public XPromptMessage(int i, CharSequence charSequence, int i2, String str) {
        this.mDuration = i;
        this.mText = charSequence;
        this.mIcon = i2;
        this.mId = str;
    }

    public XPromptMessage(int i, CharSequence charSequence, CharSequence charSequence2, String str) {
        this.mDuration = i;
        this.mText = charSequence;
        this.mButton = charSequence2;
        this.mId = str;
    }

    public XPromptMessage setDuration(int i) {
        this.mDuration = i;
        return this;
    }

    public XPromptMessage setText(CharSequence charSequence) {
        this.mText = charSequence;
        return this;
    }

    public XPromptMessage setId(String str) {
        this.mId = str;
        return this;
    }

    public int getIcon() {
        return this.mIcon;
    }

    public XPromptMessage setIcon(int i) {
        this.mIcon = i;
        return this;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public String getId() {
        return this.mId;
    }

    public CharSequence getButton() {
        return this.mButton;
    }

    public void setButton(CharSequence charSequence) {
        this.mButton = charSequence;
    }
}
