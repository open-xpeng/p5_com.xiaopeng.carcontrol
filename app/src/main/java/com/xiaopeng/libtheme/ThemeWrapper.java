package com.xiaopeng.libtheme;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import androidx.core.content.ThemeContextCompat;
import com.android.internal.util.ArrayUtils;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class ThemeWrapper {
    public static final boolean DEBUG = ThemeManager.DEBUG;
    private static final String TAG = "ThemeWrapper";
    private static final long TIMEOUT = 5000;
    private static final int TYPE_DRAWABLE_BOTTOM = 3;
    private static final int TYPE_DRAWABLE_END = 5;
    private static final int TYPE_DRAWABLE_LEFT = 0;
    private static final int TYPE_DRAWABLE_RIGHT = 2;
    private static final int TYPE_DRAWABLE_START = 4;
    private static final int TYPE_DRAWABLE_TOP = 1;
    private static HashMap<String, List<ThemeView>> sThemeCache;
    private static volatile ThemeWrapper sThemeWrapper;
    private Handler mHandler = new WorkHandler();

    private ThemeWrapper() {
        sThemeCache = new HashMap<>();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static ThemeWrapper getInstance() {
        if (sThemeWrapper == null) {
            synchronized (ThemeWrapper.class) {
                if (sThemeWrapper == null) {
                    sThemeWrapper = new ThemeWrapper();
                }
            }
        }
        return sThemeWrapper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeout() {
        this.mHandler.removeCallbacksAndMessages(null);
        HashMap<String, List<ThemeView>> hashMap = sThemeCache;
        if (hashMap != null) {
            hashMap.clear();
        }
        sThemeCache = null;
        sThemeWrapper = null;
        this.mHandler = null;
        if (DEBUG) {
            ThemeManager.Logger.log("onTimeout time=" + now());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void onConfigurationChanged(Context context, ThemeData themeData, Configuration configuration) {
        Context applicationContext = context.getApplicationContext();
        boolean isNightMode = ThemeManager.isNightMode(applicationContext);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        ThemeManager.Logger.log(TAG, "onConfigurationChanged isNightMode=" + isNightMode + " isThemeChanged=" + isThemeChanged);
        if (isThemeChanged) {
            handleThemeChanged(applicationContext, themeData);
        }
    }

    private synchronized void handleThemeChanged(Context context, ThemeData themeData) {
        if (DEBUG) {
            ThemeManager.Logger.log("handleThemeChanged");
        }
        put(context, themeData);
        timeout();
        refresh(context, themeData);
    }

    private void put(final Context context, final ThemeData themeData) {
        if (context == null || themeData == null) {
            return;
        }
        try {
            if (TextUtils.isEmpty(themeData.xml)) {
                return;
            }
            this.mHandler.post(new Runnable() { // from class: com.xiaopeng.libtheme.ThemeWrapper.1
                @Override // java.lang.Runnable
                public void run() {
                    ThemeWrapper.sThemeCache.put(themeData.xml, ThemeParser.parseXml(context, themeData.xml));
                }
            });
        } catch (Exception unused) {
        }
    }

    private void refresh(Context context, ThemeData themeData) {
        handleRefreshList(context, themeData);
        handleRefreshXml(context, themeData);
    }

    private void timeout() {
        this.mHandler.removeMessages(100);
        this.mHandler.sendEmptyMessageDelayed(100, 5000L);
    }

    private void handleRefreshList(Context context, ThemeData themeData) {
        if (themeData == null || themeData.list == null) {
            return;
        }
        for (ThemeView themeView : themeData.list) {
            updateViewResource(context, themeData.root, themeView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRefreshXml(final Context context, final ThemeData themeData) {
        if (themeData == null || TextUtils.isEmpty(themeData.xml)) {
            return;
        }
        boolean containsKey = sThemeCache.containsKey(themeData.xml);
        if (DEBUG) {
            ThemeManager.Logger.log("handleRefreshXml xmlCached=" + containsKey);
        }
        if (containsKey) {
            List<ThemeView> list = sThemeCache.get(themeData.xml);
            if (list != null) {
                for (ThemeView themeView : list) {
                    updateViewResource(context, themeData.root, themeView);
                }
                return;
            }
            return;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.libtheme.ThemeWrapper.2
            @Override // java.lang.Runnable
            public void run() {
                ThemeWrapper.this.handleRefreshXml(context, themeData);
            }
        }, 10L);
    }

    private View getThemeView(Context context, View view, ThemeView themeView) {
        View findViewById;
        View view2 = null;
        if (context == null || themeView == null || view == null) {
            return null;
        }
        if (themeView.resId > 0 && themeView.resRoot > 0 && (findViewById = view.findViewById(themeView.resRoot)) != null) {
            view2 = findViewById.findViewById(themeView.resId);
        }
        return (themeView.resId <= 0 || view2 != null) ? view2 : view.findViewById(themeView.resId);
    }

    private void updateViewResource(Context context, View view, ThemeView themeView) {
        try {
            View themeView2 = getThemeView(context, view, themeView);
            if (context != null && view != null && themeView != null && themeView2 != null && themeView.resValue != null && !TextUtils.isEmpty(themeView.resAttr)) {
                String str = themeView.resAttr;
                int i = -1;
                try {
                    i = Integer.parseInt(themeView.resValue.toString());
                } catch (Exception unused) {
                }
                if (ThemeManager.AttributeSet.hasAttribute(str)) {
                    setViewResource(context, themeView2, str, i);
                } else {
                    invokeViewResource(context, themeView2, str, i);
                }
            }
        } catch (Exception e) {
            ThemeManager.Logger.log(TAG, "updateViewResource e=" + e);
        }
    }

    /* loaded from: classes2.dex */
    private static class WorkHandler extends Handler {
        private static final int MSG_HANDLER_TIMEOUT = 100;

        private WorkHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 100) {
                return;
            }
            ThemeWrapper.getInstance().onTimeout();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static HashMap<String, Integer> resolveAttribute(Context context, AttributeSet attributeSet, int i, int i2, HashMap<String, List<String>> hashMap) {
        boolean z;
        boolean z2;
        HashMap<String, Integer> hashMap2 = ThemeManager.AttributeSet.sAttributeMap;
        HashMap<String, Integer> hashMap3 = new HashMap<>();
        List<String> list = (hashMap == null || !hashMap.containsKey(ThemeManager.KEY_APPEND)) ? null : hashMap.get(ThemeManager.KEY_APPEND);
        List<String> list2 = (hashMap == null || !hashMap.containsKey(ThemeManager.KEY_GLOBAL)) ? null : hashMap.get(ThemeManager.KEY_GLOBAL);
        if (attributeSet != null) {
            int attributeCount = attributeSet.getAttributeCount();
            z = false;
            z2 = false;
            for (int i3 = 0; i3 < attributeCount; i3++) {
                try {
                    String attributeName = attributeSet.getAttributeName(i3);
                    String attributeValue = attributeSet.getAttributeValue(i3);
                    if (DEBUG) {
                        ThemeManager.Logger.log("resolveAttribute common attr name=" + attributeName + " value=" + attributeValue);
                    }
                    if (!TextUtils.isEmpty(attributeName) && !TextUtils.isEmpty(attributeValue)) {
                        boolean z3 = (ThemeManager.AttributeSet.hasAttribute(attributeName) || (list != null && list.contains(attributeName))) && attributeValue.startsWith(ISync.EXTRA_SYNC_GROUP_KEY_SEPARATOR);
                        boolean isThemeAttribute = ThemeManager.AttributeSet.isThemeAttribute(attributeName);
                        boolean isStyleAttribute = ThemeManager.AttributeSet.isStyleAttribute(attributeName);
                        if (z3) {
                            if (isThemeAttribute) {
                                z = true;
                            }
                            if (isStyleAttribute) {
                                z2 = true;
                            }
                            if (!isThemeAttribute && !isStyleAttribute) {
                                hashMap3.put(attributeName, Integer.valueOf(Integer.parseInt(attributeValue.substring(1))));
                            }
                        }
                    }
                } catch (Exception unused) {
                }
            }
        } else {
            z = false;
            z2 = false;
        }
        if (z || z2) {
            for (String str : hashMap2.keySet()) {
                int intValue = hashMap2.get(str).intValue();
                if (intValue != 0 && !hashMap3.containsKey(str)) {
                    int themeResourceId = z ? getThemeResourceId(intValue, context, null, 0, 0) : 0;
                    if (z2) {
                        themeResourceId = getThemeResourceId(intValue, context, attributeSet, i, i2);
                    }
                    if (DEBUG) {
                        ThemeManager.Logger.log("resolveAttribute theme&style attr key=" + str + " resId=0x" + Integer.toHexString(themeResourceId) + " hasTheme=" + z + " hasStyle=" + z2);
                    }
                    if (themeResourceId != 0) {
                        hashMap3.put(str, Integer.valueOf(themeResourceId));
                    }
                }
            }
        }
        if (i > 0 || i2 > 0) {
            try {
                Set<String> keySet = hashMap2.keySet();
                int size = keySet.size();
                String[] strArr = (String[]) keySet.toArray(new String[size]);
                int[] convertToIntArray = ArrayUtils.convertToIntArray(new ArrayList(hashMap2.values()));
                for (int i4 = 0; i4 < size; i4++) {
                    String str2 = strArr[i4];
                    int i5 = convertToIntArray[i4];
                    if (!hashMap3.containsKey(str2)) {
                        int themeResourceId2 = getThemeResourceId(i5, context, null, i, i2);
                        if (DEBUG) {
                            ThemeManager.Logger.log("resolveAttribute def attr key=" + str2 + " resId=0x" + Integer.toHexString(themeResourceId2));
                        }
                        if (themeResourceId2 != 0) {
                            hashMap3.put(str2, Integer.valueOf(themeResourceId2));
                        }
                    }
                }
            } catch (Exception e) {
                ThemeManager.Logger.log(TAG, "resolveAttribute style e=" + e);
            }
        }
        if (list2 != null && !list2.isEmpty()) {
            for (String str3 : list2) {
                if (!TextUtils.isEmpty(str3) && !hashMap3.containsKey(str3) && hashMap2.containsKey(str3)) {
                    int themeResourceId3 = getThemeResourceId(hashMap2.get(str3).intValue(), context, null, 0, 0);
                    if (DEBUG) {
                        ThemeManager.Logger.log("resolveAttribute global attr key=" + str3 + " resId=0x" + Integer.toHexString(themeResourceId3));
                    }
                    if (themeResourceId3 != 0) {
                        hashMap3.put(str3, Integer.valueOf(themeResourceId3));
                    }
                }
            }
        }
        return hashMap3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void setAttributeValue(HashMap<String, Integer> hashMap, String str, int i) {
        if (hashMap == null || TextUtils.isEmpty(str) || i == 0) {
            return;
        }
        try {
            hashMap.put(str, Integer.valueOf(i));
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void updateAttribute(View view, HashMap<String, Integer> hashMap) {
        if (view == null || hashMap == null) {
            return;
        }
        for (String str : hashMap.keySet()) {
            if (ThemeManager.AttributeSet.hasAttribute(str)) {
                setViewResource(view.getContext(), view, str, hashMap.get(str).intValue());
            } else {
                invokeViewResource(view.getContext(), view, str, hashMap.get(str) != null ? hashMap.get(str).intValue() : 0);
            }
        }
    }

    private static void setViewResource(Context context, View view, String str, int i) {
        Context applicationContext = context != null ? context.getApplicationContext() : null;
        if (applicationContext == null || view == null || TextUtils.isEmpty(str)) {
            return;
        }
        Resources resources = applicationContext.getResources();
        applicationContext.getTheme();
        try {
            if ("alpha".equals(str)) {
                if (Build.VERSION.SDK_INT >= 29) {
                    view.setAlpha(resources.getFloat(i));
                }
            } else if (ThemeManager.AttributeSet.BACKGROUND.equals(str)) {
                view.setBackground(getOverlayDrawable(ThemeContextCompat.getDrawable(applicationContext, i), view.getBackground()));
            } else if (ThemeManager.AttributeSet.FOREGROUND.equals(str)) {
                if (Build.VERSION.SDK_INT >= 23) {
                    view.setForeground(getOverlayDrawable(ThemeContextCompat.getDrawable(applicationContext, i), view.getForeground()));
                }
            } else if (ThemeManager.AttributeSet.SCROLLBAR_THUMB_VERTICAL.equals(str)) {
                setVerticalThumbDrawable(view, ThemeContextCompat.getDrawable(applicationContext, i));
            } else if (ThemeManager.AttributeSet.SRC.equals(str)) {
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    imageView.setImageDrawable(getOverlayDrawable(ThemeContextCompat.getDrawable(applicationContext, i), imageView.getDrawable()));
                }
            } else if (ThemeManager.AttributeSet.TEXT_COLOR.equals(str)) {
                if (view instanceof android.widget.TextView) {
                    ((android.widget.TextView) view).setTextColor(ThemeContextCompat.getColorStateList(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.TEXT_COLOR_HINT.equals(str)) {
                if (view instanceof android.widget.TextView) {
                    ((android.widget.TextView) view).setHintTextColor(ThemeContextCompat.getColorStateList(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.DRAWABLE_LEFT.equals(str)) {
                if (view instanceof android.widget.TextView) {
                    setCompoundDrawables(0, (android.widget.TextView) view, ThemeContextCompat.getDrawable(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.DRAWABLE_TOP.equals(str)) {
                if (view instanceof android.widget.TextView) {
                    setCompoundDrawables(1, (android.widget.TextView) view, ThemeContextCompat.getDrawable(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.DRAWABLE_RIGHT.equals(str)) {
                if (view instanceof android.widget.TextView) {
                    setCompoundDrawables(2, (android.widget.TextView) view, ThemeContextCompat.getDrawable(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.DRAWABLE_BOTTOM.equals(str)) {
                if (view instanceof android.widget.TextView) {
                    setCompoundDrawables(3, (android.widget.TextView) view, ThemeContextCompat.getDrawable(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.DRAWABLE_START.equals(str)) {
                if (view instanceof android.widget.TextView) {
                    setCompoundDrawables(4, (android.widget.TextView) view, ThemeContextCompat.getDrawable(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.DRAWABLE_END.equals(str)) {
                if (view instanceof android.widget.TextView) {
                    setCompoundDrawables(5, (android.widget.TextView) view, ThemeContextCompat.getDrawable(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.PROGRESS_DRAWABLE.equals(str)) {
                if (view instanceof ProgressBar) {
                    ((ProgressBar) view).setProgressDrawable(ThemeContextCompat.getDrawable(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.THUMB.equals(str)) {
                if (view instanceof SeekBar) {
                    ((SeekBar) view).setThumb(ThemeContextCompat.getDrawable(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.BUTTON.equals(str)) {
                if (view instanceof CompoundButton) {
                    ((CompoundButton) view).setButtonDrawable(ThemeContextCompat.getDrawable(applicationContext, i));
                }
            } else if (ThemeManager.AttributeSet.DIVIDER.equals(str) && (view instanceof ListView)) {
                ((ListView) view).setDivider(ThemeContextCompat.getDrawable(applicationContext, i));
            }
            view.refreshDrawableState();
            if (DEBUG) {
                ThemeManager.Logger.log("setViewResource view info:" + getViewInfo(view, i, str));
            }
        } catch (Exception e) {
            if (DEBUG) {
                ThemeManager.Logger.log("setViewResource error attr=" + str + " view=" + view + " e=" + e);
            }
        }
    }

    private static void invokeViewResource(Context context, View view, String str, int i) {
        try {
            if (TextUtils.isEmpty(str) || str.length() <= 0) {
                return;
            }
            view.getClass().getPackage().getName();
            String name = view.getClass().getName();
            String str2 = "set" + str.substring(0, 1).toUpperCase() + str.substring(1);
            if (DEBUG) {
                ThemeManager.Logger.log("invokeViewResource view info:" + getViewInfo(view, i, str));
            }
            Class<?> cls = Class.forName(name);
            if (cls != null) {
                cls.getMethod(str2, Integer.TYPE).invoke(view, Integer.valueOf(i));
            }
        } catch (Exception e) {
            ThemeManager.Logger.log(TAG, "invokeViewResource view=" + view + " e=" + e);
        }
    }

    private static Drawable getOverlayDrawable(Drawable drawable, Drawable drawable2) {
        if (drawable != null && drawable2 != null && drawable.getLevel() != drawable2.getLevel()) {
            drawable.setLevel(drawable2.getLevel());
        }
        return drawable;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
        if (r2 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002a, code lost:
        if (r2 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x002c, code lost:
        r2.recycle();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int getThemeResourceId(int r2, android.content.Context r3, android.util.AttributeSet r4, int r5, int r6) {
        /*
            r0 = 0
            if (r3 == 0) goto L11
            android.content.res.Resources$Theme r3 = r3.getTheme()
            r1 = 1
            int[] r1 = new int[r1]
            r1[r0] = r2
            android.content.res.TypedArray r2 = r3.obtainStyledAttributes(r4, r1, r5, r6)
            goto L12
        L11:
            r2 = 0
        L12:
            if (r2 == 0) goto L2f
            int r3 = r2.getResourceId(r0, r0)     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L2a
            if (r3 == 0) goto L20
            if (r2 == 0) goto L1f
            r2.recycle()
        L1f:
            return r3
        L20:
            if (r2 == 0) goto L2f
            goto L2c
        L23:
            r3 = move-exception
            if (r2 == 0) goto L29
            r2.recycle()
        L29:
            throw r3
        L2a:
            if (r2 == 0) goto L2f
        L2c:
            r2.recycle()
        L2f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.libtheme.ThemeWrapper.getThemeResourceId(int, android.content.Context, android.util.AttributeSet, int, int):int");
    }

    private static void setCompoundDrawables(int i, android.widget.TextView textView, Drawable drawable) {
        if (drawable == null || textView == null) {
            return;
        }
        if (i == 0 || i == 1 || i == 2 || i == 3) {
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            if (compoundDrawables != null) {
                compoundDrawables[i] = getOverlayDrawable(drawable, compoundDrawables[i]);
                textView.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
            }
        } else if (i == 4 || i == 5) {
            Drawable[] compoundDrawablesRelative = textView.getCompoundDrawablesRelative();
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            if (compoundDrawablesRelative != null) {
                char c = i == 4 ? (char) 0 : (char) 2;
                if (compoundDrawablesRelative != null) {
                    compoundDrawablesRelative[c] = getOverlayDrawable(drawable, compoundDrawablesRelative[c]);
                    textView.setCompoundDrawablesRelative(compoundDrawablesRelative[0], compoundDrawablesRelative[1], compoundDrawablesRelative[2], compoundDrawablesRelative[3]);
                }
            }
        }
    }

    private static void setVerticalThumbDrawable(View view, Drawable drawable) {
        Class<?> cls;
        Field field;
        Object obj;
        Class<?> cls2;
        Field declaredField;
        try {
            Method declaredMethod = View.class.getDeclaredMethod("getScrollCache", new Class[0]);
            if (declaredMethod == null) {
                return;
            }
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(view, new Object[0]);
            if (invoke == null || (cls = invoke.getClass()) == null || (field = cls.getField("scrollBar")) == null || (obj = field.get(invoke)) == null || (cls2 = obj.getClass()) == null || (declaredField = cls2.getDeclaredField("mVerticalThumb")) == null) {
                return;
            }
            declaredField.setAccessible(true);
            declaredField.set(obj, drawable);
        } catch (Exception unused) {
        }
    }

    protected static void startTextColorTransition(final android.widget.TextView textView, final int i, final int i2) {
        Runnable runnable = new Runnable() { // from class: com.xiaopeng.libtheme.ThemeWrapper.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (textView != null) {
                        ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(i), Integer.valueOf(i2));
                        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.libtheme.ThemeWrapper.3.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                textView.setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                            }
                        });
                        ofObject.setDuration((int) (ThemeManager.THEME_ANIMATION_INTERVAL / 2));
                        ofObject.start();
                    }
                } catch (Exception e) {
                    if (ThemeWrapper.DEBUG) {
                        Log.d(ThemeWrapper.TAG, "startTextColorAnimation e=" + e);
                    }
                }
            }
        };
        if (textView != null) {
            textView.post(runnable);
        }
    }

    protected static void startTextColorTransition(final android.widget.TextView textView, final int i, final ColorStateList colorStateList) {
        Runnable runnable = new Runnable() { // from class: com.xiaopeng.libtheme.ThemeWrapper.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (textView == null || colorStateList == null) {
                        return;
                    }
                    ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(i), Integer.valueOf(colorStateList.getDefaultColor()));
                    ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.libtheme.ThemeWrapper.4.1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            textView.setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        }
                    });
                    ofObject.setDuration((int) (ThemeManager.THEME_ANIMATION_INTERVAL / 2));
                    ofObject.start();
                    textView.postDelayed(new Runnable() { // from class: com.xiaopeng.libtheme.ThemeWrapper.4.2
                        @Override // java.lang.Runnable
                        public void run() {
                            textView.setTextColor(colorStateList);
                        }
                    }, ThemeManager.THEME_ANIMATION_INTERVAL * 2);
                } catch (Exception e) {
                    if (ThemeWrapper.DEBUG) {
                        Log.d(ThemeWrapper.TAG, "startTextColorAnimation e=" + e);
                    }
                }
            }
        };
        if (textView != null) {
            textView.post(runnable);
        }
    }

    protected static void startImageDrawableTransition(final ImageView imageView, final Drawable drawable, final Drawable drawable2) {
        Runnable runnable = new Runnable() { // from class: com.xiaopeng.libtheme.ThemeWrapper.5
            @Override // java.lang.Runnable
            public void run() {
                Drawable drawable3;
                try {
                    if (imageView == null || drawable == null || (drawable3 = drawable2) == null) {
                        return;
                    }
                    if (drawable3.getLevel() != drawable.getLevel()) {
                        drawable2.setLevel(drawable.getLevel());
                    }
                    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{drawable, drawable2});
                    imageView.setImageDrawable(transitionDrawable);
                    transitionDrawable.startTransition((int) (ThemeManager.THEME_ANIMATION_INTERVAL / 2));
                    imageView.postDelayed(new Runnable() { // from class: com.xiaopeng.libtheme.ThemeWrapper.5.1
                        @Override // java.lang.Runnable
                        public void run() {
                            imageView.setImageDrawable(drawable2);
                        }
                    }, (int) (ThemeManager.THEME_ANIMATION_INTERVAL / 2));
                } catch (Exception e) {
                    if (ThemeWrapper.DEBUG) {
                        Log.d(ThemeWrapper.TAG, "startImageDrawableTransition e=" + e);
                    }
                }
            }
        };
        if (imageView != null) {
            imageView.post(runnable);
        }
    }

    protected static void startViewBackgroundTransition(final View view, final Drawable drawable, final Drawable drawable2) {
        Runnable runnable = new Runnable() { // from class: com.xiaopeng.libtheme.ThemeWrapper.6
            @Override // java.lang.Runnable
            public void run() {
                Drawable drawable3;
                try {
                    if (view == null || drawable == null || (drawable3 = drawable2) == null) {
                        return;
                    }
                    if (drawable3.getLevel() != drawable.getLevel()) {
                        drawable2.setLevel(drawable.getLevel());
                    }
                    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{drawable, drawable2});
                    view.setBackground(transitionDrawable);
                    transitionDrawable.startTransition((int) (ThemeManager.THEME_ANIMATION_INTERVAL / 2));
                    view.postDelayed(new Runnable() { // from class: com.xiaopeng.libtheme.ThemeWrapper.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            view.setBackground(drawable2);
                        }
                    }, (int) (ThemeManager.THEME_ANIMATION_INTERVAL / 2));
                } catch (Exception e) {
                    if (ThemeWrapper.DEBUG) {
                        Log.d(ThemeWrapper.TAG, "startViewBackgroundTransition e=" + e);
                    }
                }
            }
        };
        if (view != null) {
            view.post(runnable);
        }
    }

    protected static long now() {
        return System.currentTimeMillis();
    }

    protected static String getViewInfo(View view, int i, String str) {
        StringBuffer stringBuffer = new StringBuffer("getViewInfo");
        if (view != null) {
            try {
                Context applicationContext = view.getContext().getApplicationContext();
                if (applicationContext != null) {
                    Resources resources = applicationContext.getResources();
                    TypedValue typedValue = new TypedValue();
                    resources.getValue(i, typedValue, true);
                    stringBuffer.append(" view.id=0x" + Integer.toHexString(view.getId()));
                    stringBuffer.append(" view.resId=0x" + Integer.toHexString(i));
                    stringBuffer.append(" view.attr=" + str);
                    stringBuffer.append(" view.toString=" + view.toString());
                    stringBuffer.append(" view.value.string=" + ((Object) typedValue.string));
                    stringBuffer.append(" view.value=" + typedValue.toString());
                } else {
                    stringBuffer.append(" context null");
                }
            } catch (Exception e) {
                stringBuffer.append(" error=" + e);
            }
        }
        return stringBuffer.toString();
    }
}
