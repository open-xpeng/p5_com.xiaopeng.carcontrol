package com.xiaopeng.xui.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.utils.XCharacterUtils;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class XToast {
    public static final int ID_SHARED_PRIMARY = 0;
    public static final int ID_SHARED_SECONDARY = 1;
    public static final int LENGTH_LONGER = 2;

    private XToast() {
    }

    private static Context getApplicationContext() {
        return Xui.getContext();
    }

    public static void show(int i) {
        show(Xui.getContext().getText(i));
    }

    public static void showAt(int i, int i2) {
        showAt(Xui.getContext().getText(i), i2);
    }

    public static void show(CharSequence charSequence) {
        if (charactersSize(charSequence) > 8) {
            showLong(charSequence);
        } else {
            showShort(charSequence);
        }
    }

    public static void showAt(CharSequence charSequence, int i) {
        if (charactersSize(charSequence) > 8) {
            showLong(charSequence, i);
        } else {
            showShort(charSequence, i);
        }
    }

    public static void showShort(int i) {
        showShort(Xui.getContext().getText(i));
    }

    public static void showShort(int i, int i2) {
        showShort(Xui.getContext().getText(i), i2);
    }

    public static void showShort(CharSequence charSequence) {
        show(charSequence, 0);
    }

    public static void showShort(CharSequence charSequence, int i) {
        show(charSequence, 0, i);
    }

    public static void showLong(int i) {
        showLong(Xui.getContext().getText(i));
    }

    public static void showLong(int i, int i2) {
        showLong(Xui.getContext().getText(i), i2);
    }

    public static void showLong(CharSequence charSequence) {
        show(charSequence, 1);
    }

    public static void showLong(CharSequence charSequence, int i) {
        show(charSequence, 1, i);
    }

    public static void showLonger(int i) {
        showLonger(Xui.getContext().getText(i));
    }

    public static void showLonger(int i, int i2) {
        showLonger(Xui.getContext().getText(i), i2);
    }

    public static void showLonger(CharSequence charSequence) {
        show(charSequence, 2);
    }

    public static void showLonger(CharSequence charSequence, int i) {
        show(charSequence, 2, i);
    }

    public static void show(CharSequence charSequence, int i) {
        show(charSequence, i, -1);
    }

    public static void show(CharSequence charSequence, int i, int i2) {
        show(charSequence, i, i2, 0);
    }

    public static void show(CharSequence charSequence, int i, int i2, int i3) {
        Toast makeToast = makeToast(R.layout.x_toast);
        XToastTextView xToastTextView = (XToastTextView) makeToast.getView().findViewById(R.id.textView);
        xToastTextView.setIcon(i3);
        makeToast.setDuration(i);
        if (Xui.isSharedDisplay()) {
            xToastTextView.setElevation(0.0f);
        } else {
            xToastTextView.setElevation(12.0f);
        }
        xToastTextView.setText(charSequence);
        if (i2 != -1) {
            invokeShared(makeToast, i2);
        }
        makeToast.show();
    }

    private static void invokeShared(Toast toast, int i) {
        try {
            Toast.class.getMethod("setSharedId", Integer.TYPE).invoke(toast, Integer.valueOf(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Toast makeToast(int i) {
        Context applicationContext = getApplicationContext();
        View inflate = LayoutInflater.from(applicationContext).inflate(i, (ViewGroup) null);
        Toast toast = new Toast(applicationContext);
        toast.setGravity(BadgeDrawable.TOP_END, 0, 0);
        toast.setView(inflate);
        return toast;
    }

    private static int charactersSize(CharSequence charSequence) {
        String[] split;
        if (charSequence == null) {
            return 0;
        }
        int i = 0;
        for (String str : charSequence.toString().trim().split(" ")) {
            if (str.trim().length() != 0) {
                char c = 65535;
                boolean z = false;
                for (int i2 = 0; i2 < str.length(); i2++) {
                    if (XCharacterUtils.isFullAngle(str.charAt(i2))) {
                        if (c == 0) {
                            i++;
                        }
                        i++;
                        c = 1;
                        z = true;
                    } else {
                        c = 0;
                    }
                }
                if (!z || c == 0) {
                    i++;
                }
            }
        }
        return i;
    }

    /* loaded from: classes2.dex */
    public static class XToastTextView extends XTextView {
        private int mIcon;

        public XToastTextView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            if (this.mXViewDelegate == null || this.mXViewDelegate.getThemeViewModel() == null) {
                return;
            }
            this.mXViewDelegate.getThemeViewModel().addCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.xui.app.-$$Lambda$XToast$XToastTextView$VTxi_g91Xth0IHIezAMIARrvgog
                @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
                public final void onThemeChanged() {
                    XToast.XToastTextView.this.lambda$new$0$XToast$XToastTextView();
                }
            });
        }

        public /* synthetic */ void lambda$new$0$XToast$XToastTextView() {
            if (this.mIcon != 0) {
                logD("XToast onThemeChanged !!! ");
                setCompoundDrawablesWithIntrinsicBounds(this.mIcon, 0, 0, 0);
            }
        }

        public void setIcon(int i) {
            this.mIcon = i;
            setCompoundDrawablesWithIntrinsicBounds(i, 0, 0, 0);
        }
    }
}
