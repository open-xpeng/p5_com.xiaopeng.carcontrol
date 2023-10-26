package com.xiaopeng.xui.widget.prompt;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import com.xiaopeng.xpui.R;

@Deprecated
/* loaded from: classes2.dex */
public class XPrompt extends Prompt {
    private ViewGroup mHostView;

    public XPrompt(Context context) {
        super(context);
    }

    public static XPrompt makePrompt(Activity activity) {
        return makePrompt(activity, findHostViewFromActivity(activity));
    }

    public static XPrompt makePrompt(Activity activity, CharSequence charSequence) {
        return makePrompt(activity, charSequence, 0);
    }

    public static XPrompt makePrompt(Activity activity, CharSequence charSequence, int i) {
        return makePrompt(activity, findHostViewFromActivity(activity), charSequence, i);
    }

    public static XPrompt makePrompt(Context context, ViewGroup viewGroup, CharSequence charSequence, int i) {
        XPrompt makePrompt = makePrompt(context, viewGroup);
        makePrompt.addMessage(new XPromptMessage(i, charSequence));
        return makePrompt;
    }

    private static ViewGroup findHostViewFromActivity(Activity activity) {
        return (ViewGroup) activity.findViewById(16908290);
    }

    public static XPrompt makePrompt(Context context, ViewGroup viewGroup) {
        XPromptView xPromptView = (XPromptView) viewGroup.findViewById(R.id.x_prompt);
        XPrompt xPrompt = xPromptView != null ? (XPrompt) xPromptView.getPrompt() : null;
        if (xPrompt == null) {
            xPrompt = new XPrompt(context);
        }
        xPrompt.setHostView(viewGroup);
        return xPrompt;
    }

    public XPrompt setHostView(Activity activity) {
        setHostView(findHostViewFromActivity(activity));
        return this;
    }

    public XPrompt setHostView(ViewGroup viewGroup) {
        ViewGroup viewGroup2 = this.mHostView;
        if (viewGroup2 != null && !viewGroup2.equals(viewGroup)) {
            this.mHostView.removeView(this.mXPromptView);
        }
        this.mHostView = viewGroup;
        return this;
    }

    @Override // com.xiaopeng.xui.widget.prompt.Prompt
    protected boolean addView() {
        ViewGroup viewGroup = this.mHostView;
        if (viewGroup != null) {
            viewGroup.addView(this.mXPromptView);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.xui.widget.prompt.Prompt
    protected void removeView() {
        if (this.mXPromptView.getParent() instanceof ViewGroup) {
            ((ViewGroup) this.mXPromptView.getParent()).removeView(this.mXPromptView);
        }
    }
}
