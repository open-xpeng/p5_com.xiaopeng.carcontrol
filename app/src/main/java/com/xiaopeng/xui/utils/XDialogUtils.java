package com.xiaopeng.xui.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import androidx.core.view.GravityCompat;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaopeng.xpui.R;

/* loaded from: classes2.dex */
public class XDialogUtils {
    @Deprecated
    public static void setHorizontalCenter(Dialog dialog) {
        Window window;
        if (dialog == null || (window = dialog.getWindow()) == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 1;
        attributes.x = 0;
        window.setAttributes(attributes);
    }

    @Deprecated
    public static void setHorizontal(Dialog dialog, int i) {
        Window window;
        if (dialog == null || (window = dialog.getWindow()) == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = GravityCompat.START;
        attributes.x = i;
        window.setAttributes(attributes);
    }

    @Deprecated
    public static void setVerticalCenter(Dialog dialog, int i) {
        Window window;
        if (dialog == null || (window = dialog.getWindow()) == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 16;
        attributes.y = 0;
        window.setAttributes(attributes);
    }

    @Deprecated
    public static void setVertical(Dialog dialog, int i) {
        Window window;
        if (dialog == null || (window = dialog.getWindow()) == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 48;
        attributes.y = i;
        window.setAttributes(attributes);
    }

    @Deprecated
    public static void setVerticalHorizontal(Dialog dialog, int i, int i2) {
        Window window;
        if (dialog == null || (window = dialog.getWindow()) == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = BadgeDrawable.TOP_START;
        attributes.x = i;
        attributes.y = i2;
        window.setAttributes(attributes);
    }

    public static Dialog createThemeDialog(Context context) {
        return new Dialog(context, R.style.XAppTheme_XDialog);
    }

    public static void setSystemDialog(Dialog dialog, int i) {
        if (dialog == null || dialog.getWindow() == null) {
            return;
        }
        dialog.getWindow().setType(i);
    }

    public static void handleSoftInput(Dialog dialog) {
        if (dialog == null || dialog.getWindow() == null) {
            return;
        }
        dialog.getWindow().setSoftInputMode(16);
    }

    public static void requestFullScreen(Dialog dialog) {
        if (dialog == null || dialog.getWindow() == null) {
            return;
        }
        dialog.getWindow().requestFeature(15);
    }

    public static int getScreenId(Dialog dialog) {
        try {
            return ((Integer) Dialog.class.getDeclaredMethod("getScreenId", new Class[0]).invoke(dialog, new Object[0])).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setScreenId(Dialog dialog, int i) {
        try {
            Dialog.class.getDeclaredMethod("setScreenId", Integer.TYPE).invoke(dialog, Integer.valueOf(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
