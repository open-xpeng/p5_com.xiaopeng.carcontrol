package com.xiaopeng.libtheme;

import android.app.IUiModeManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.xiaopeng.lludancemanager.util.ResUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class ThemeManager {
    public static final boolean DEBUG;
    public static final String KEY_APPEND = "extra_append";
    public static final String KEY_GLOBAL = "extra_global";
    public static final int MODE_NIGHT_AUTO = 0;
    public static final int MODE_NIGHT_NO = 1;
    public static final int MODE_NIGHT_YES = 2;
    private static final String TAG = "ThemeManager";
    public static final boolean THEME;
    public static final long THEME_ANIMATION_INTERVAL;
    public static final long THEME_TIMEOUT_DELAY;
    public static final int UI_MODE_NIGHT_MASK = 48;
    public static final int UI_MODE_NIGHT_NO = 16;
    public static final int UI_MODE_NIGHT_UNDEFINED = 0;
    public static final int UI_MODE_NIGHT_YES = 32;
    public static final int UI_MODE_THEME_CLEAR = 63;
    public static final int UI_MODE_THEME_MASK = 192;
    public static final int UI_MODE_THEME_UNDEFINED = 0;
    public static final int VERSION = 3;
    private Context mContext;
    private ThemeData mThemeData;

    static {
        boolean z = SystemProperties.getInt("persist.sys.theme.logger", 0) == 1;
        THEME = z;
        DEBUG = z;
        THEME_ANIMATION_INTERVAL = UiModeManager.THEME_ANIMATION_INTERVAL;
        THEME_TIMEOUT_DELAY = UiModeManager.THEME_TIMEOUT_DELAY;
    }

    private ThemeManager(Context context, View view, String str, List<ThemeView> list) {
        this.mContext = context.getApplicationContext();
        ThemeData themeData = new ThemeData();
        this.mThemeData = themeData;
        themeData.xml = str;
        this.mThemeData.root = view;
        this.mThemeData.list = list;
    }

    public static ThemeManager create(Context context, View view, String str, List<ThemeView> list) {
        return new ThemeManager(context, view, str, list);
    }

    public void onConfigurationChanged(Configuration configuration) {
        ThemeWrapper.getInstance().onConfigurationChanged(this.mContext, this.mThemeData, configuration);
    }

    public static boolean isThemeChanged(Configuration configuration) {
        return (configuration == null || (configuration.uiMode & UI_MODE_THEME_MASK) == 0) ? false : true;
    }

    public static int getThemeMode(Context context) {
        return ((UiModeManager) context.getSystemService("uimode")).getThemeMode();
    }

    public static int getDayNightMode(Context context) {
        return ((UiModeManager) context.getSystemService("uimode")).getDayNightMode();
    }

    public static int getDayNightAutoMode(Context context) {
        return ((UiModeManager) context.getSystemService("uimode")).getDayNightAutoMode();
    }

    public static int getUiMode(Context context) {
        Context applicationContext = context != null ? context.getApplicationContext() : null;
        Configuration configuration = applicationContext != null ? applicationContext.getResources().getConfiguration() : null;
        if (configuration != null) {
            return configuration.uiMode;
        }
        return 0;
    }

    public static boolean isNightMode(Context context) {
        Context applicationContext = context != null ? context.getApplicationContext() : null;
        Configuration configuration = applicationContext != null ? applicationContext.getResources().getConfiguration() : null;
        return configuration != null && (configuration.uiMode & 48) == 32;
    }

    public static boolean isThemeWorking(Context context) {
        return ((UiModeManager) context.getSystemService("uimode")).isThemeWorking();
    }

    public static void applyThemeMode(Context context, int i) {
        ((UiModeManager) context.getSystemService("uimode")).applyThemeMode(i);
    }

    public static void applyDayNightMode(Context context, int i) {
        ((UiModeManager) context.getSystemService("uimode")).applyDayNightMode(i);
    }

    public static IUiModeManager getUiModeManager() {
        return IUiModeManager.Stub.asInterface(ServiceManager.getService("uimode"));
    }

    public static void setWindowBackgroundResource(Configuration configuration, Window window, int i) {
        if (!isThemeChanged(configuration) || window == null) {
            return;
        }
        try {
            window.setBackgroundDrawableResource(i);
        } catch (Exception unused) {
        }
    }

    public static void setWindowBackgroundDrawable(Configuration configuration, Window window, Drawable drawable) {
        if (!isThemeChanged(configuration) || window == null) {
            return;
        }
        try {
            window.setBackgroundDrawable(drawable);
        } catch (Exception unused) {
        }
    }

    public static HashMap<String, Integer> resolveAttribute(Context context, android.util.AttributeSet attributeSet) {
        return ThemeWrapper.resolveAttribute(context, attributeSet, 0, 0, null);
    }

    public static HashMap<String, Integer> resolveAttribute(Context context, android.util.AttributeSet attributeSet, int i, int i2, HashMap<String, List<String>> hashMap) {
        return ThemeWrapper.resolveAttribute(context, attributeSet, i, i2, hashMap);
    }

    public static void setAttributeValue(HashMap<String, Integer> hashMap, String str, int i) {
        ThemeWrapper.setAttributeValue(hashMap, str, i);
    }

    public static void updateAttribute(View view, HashMap<String, Integer> hashMap) {
        ThemeWrapper.updateAttribute(view, hashMap);
    }

    /* loaded from: classes2.dex */
    public static class Logger {
        public static void log(String str) {
            if (ThemeManager.DEBUG) {
                Log.i(ThemeManager.TAG, str);
            }
        }

        public static void log(String str, String str2) {
            Log.i(str, str2);
        }
    }

    /* loaded from: classes2.dex */
    public static class ViewBuilder {
        private List<ThemeView> list;

        private ViewBuilder() {
            ArrayList arrayList = new ArrayList();
            this.list = arrayList;
            arrayList.clear();
        }

        public static ViewBuilder create() {
            return new ViewBuilder();
        }

        public ViewBuilder add(int i, String str, int i2) {
            return add(i, str, -1, i2);
        }

        public ViewBuilder add(int i, String str, int i2, int i3) {
            if (i > 0) {
                try {
                    if (!TextUtils.isEmpty(str) && i3 >= 0) {
                        ThemeView themeView = new ThemeView();
                        themeView.resId = i;
                        themeView.resAttr = str;
                        themeView.resRoot = i2;
                        themeView.resValue = Integer.valueOf(i3);
                        this.list.add(themeView);
                    }
                } catch (Exception unused) {
                }
            }
            return this;
        }

        public List<ThemeView> get() {
            return this.list;
        }
    }

    /* loaded from: classes2.dex */
    public static class ResourceType {
        public static final int ANIM = 0;
        public static final int ARRAY = 1;
        public static final int ATTR = 2;
        public static final int BOOL = 3;
        public static final int COLOR = 4;
        public static final int DIMEN = 5;
        public static final int DRAWABLE = 6;
        public static final int ID = 7;
        public static final int INTEGER = 8;
        public static final int LAYOUT = 9;
        public static final int MIPMAP = 10;
        public static final int STRING = 11;
        public static final int STYLE = 12;
        public static final int STYLEABLE = 13;
        public static final HashMap<String, Integer> sResourceType;

        static {
            HashMap<String, Integer> hashMap = new HashMap<>();
            sResourceType = hashMap;
            hashMap.clear();
            hashMap.put("anim", 0);
            hashMap.put("array", 1);
            hashMap.put("attr", 2);
            hashMap.put("bool", 3);
            hashMap.put(TypedValues.Custom.S_COLOR, 4);
            hashMap.put("dimen", 5);
            hashMap.put(ResUtil.DRAWABLE, 6);
            hashMap.put("id", 7);
            hashMap.put(TypedValues.Custom.S_INT, 8);
            hashMap.put("layout", 9);
            hashMap.put("mipmap", 10);
            hashMap.put(TypedValues.Custom.S_STRING, 11);
            hashMap.put(AttributeSet.STYLE, 12);
            hashMap.put("styleable", 13);
        }

        public static int getType(String str) {
            if (TextUtils.isEmpty(str)) {
                return -1;
            }
            return sResourceType.get(str).intValue();
        }
    }

    /* loaded from: classes2.dex */
    public static class AttributeSet {
        public static final String ALPHA = "alpha";
        public static final String BACKGROUND = "background";
        public static final String BUTTON = "button";
        public static final String DIVIDER = "divider";
        public static final String DRAWABLE_BOTTOM = "drawableBottom";
        public static final String DRAWABLE_END = "drawableEnd";
        public static final String DRAWABLE_LEFT = "drawableLeft";
        public static final String DRAWABLE_RIGHT = "drawableRight";
        public static final String DRAWABLE_START = "drawableStart";
        public static final String DRAWABLE_TOP = "drawableTop";
        public static final String FOREGROUND = "foreground";
        public static final String PROGRESS_DRAWABLE = "progressDrawable";
        public static final String SCROLLBAR_THUMB_VERTICAL = "scrollbarThumbVertical";
        public static final String SRC = "src";
        public static final String STYLE = "style";
        public static final String TEXT_COLOR = "textColor";
        public static final String TEXT_COLOR_HINT = "textColorHint";
        public static final String THEME = "theme";
        public static final String THUMB = "thumb";
        public static final HashMap<String, Integer> sAttributeMap;

        static {
            HashMap<String, Integer> hashMap = new HashMap<>();
            sAttributeMap = hashMap;
            hashMap.clear();
            hashMap.put(STYLE, 0);
            hashMap.put(THEME, 16842752);
            hashMap.put("alpha", 16843551);
            hashMap.put(FOREGROUND, 16843017);
            hashMap.put(BACKGROUND, 16842964);
            hashMap.put(SCROLLBAR_THUMB_VERTICAL, 16842853);
            hashMap.put(SRC, 16843033);
            hashMap.put(TEXT_COLOR, 16842904);
            hashMap.put(TEXT_COLOR_HINT, 16842906);
            hashMap.put(DRAWABLE_LEFT, 16843119);
            hashMap.put(DRAWABLE_TOP, 16843117);
            hashMap.put(DRAWABLE_RIGHT, 16843120);
            hashMap.put(DRAWABLE_BOTTOM, 16843118);
            hashMap.put(DRAWABLE_START, 16843666);
            hashMap.put(DRAWABLE_END, 16843667);
            hashMap.put(PROGRESS_DRAWABLE, 16843068);
            hashMap.put(THUMB, 16843074);
            hashMap.put(BUTTON, 16843015);
            hashMap.put(DIVIDER, 16843049);
        }

        public static boolean hasAttribute(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            return sAttributeMap.containsKey(str);
        }

        public static boolean isThemeAttribute(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            return THEME.equals(str);
        }

        public static boolean isStyleAttribute(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            return STYLE.equals(str);
        }

        public static boolean supportTransition(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            return BACKGROUND.equals(str) || SRC.equals(str) || TEXT_COLOR.equals(str);
        }
    }
}
