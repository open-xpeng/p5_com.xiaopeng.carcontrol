package com.xiaopeng.lib.utils.view;

import android.content.Context;
import android.widget.Toast;

/* loaded from: classes2.dex */
public class ToastUtils {
    private static Toast mToast;

    public static void showToast(Context context, String str) {
        Toast toast;
        if (context == null || (toast = getToast(context, str)) == null) {
            return;
        }
        toast.show();
    }

    public static void showToast(Context context, int i) {
        Toast toast;
        if (context == null || (toast = getToast(context, context.getResources().getString(i))) == null) {
            return;
        }
        toast.show();
    }

    public static Toast getToast(Context context, String str) {
        Toast toast = mToast;
        if (toast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), str, 0);
        } else {
            toast.setText(str);
        }
        return mToast;
    }
}
