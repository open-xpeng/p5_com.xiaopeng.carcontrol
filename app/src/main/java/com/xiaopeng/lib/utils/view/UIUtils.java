package com.xiaopeng.lib.utils.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.xiaopeng.lib.utils.LogUtils;

/* loaded from: classes2.dex */
public class UIUtils {
    private static int height;
    private static int width;

    public static void hideSystemUi(Context context, Window window) {
    }

    public static void showSystemUi(Context context, Window window) {
    }

    public static void init(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
    }

    public static int getScreenWidth() {
        return width;
    }

    public static int getScreenHeight() {
        return height;
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int px2sp(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static float getTextWidth(String str) {
        return new Paint(1).measureText(str);
    }

    public static float getTextHeight(String str) {
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, 1, rect);
        return rect.height();
    }

    public static int setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < adapter.getCount(); i2++) {
            View view = adapter.getView(i2, null, listView);
            view.measure(0, 0);
            i += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = (listView.getDividerHeight() * (adapter.getCount() - 1)) + i;
        listView.setLayoutParams(layoutParams);
        return i;
    }

    public static int getStatusBarHeight(Context context) {
        try {
            return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android")) + context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_bottom_height", "dimen", "android"));
        } catch (Exception e) {
            LogUtils.d("UIUtils", "e: " + e.getMessage());
            return 0;
        }
    }

    public static int getStatusBarBottomHeight(Context context) {
        try {
            return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_bottom_height", "dimen", "android"));
        } catch (Exception e) {
            LogUtils.d("UIUtils", "e: " + e.getMessage());
            return 0;
        }
    }
}
