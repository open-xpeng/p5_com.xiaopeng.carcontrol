package com.xiaopeng.carcontrol.util;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.view.WindowManagerFactory;

/* loaded from: classes2.dex */
public class AmsUtils {
    private static final String LABEL = "application";
    private static final String TAG = "AmsUtils";

    public static boolean isTopActivityFullscreen() {
        if (BaseFeatureOption.getInstance().isSupportNapa()) {
            return false;
        }
        if (XuiClientWrapper.getInstance().getXAppManager() != null) {
            try {
                FullWindowJsonBean[] fullWindowJsonBeanArr = (FullWindowJsonBean[]) new Gson().fromJson(WindowManagerFactory.create(App.getInstance()).getTopWindow(), (Class<Object>) FullWindowJsonBean[].class);
                if (fullWindowJsonBeanArr == null || fullWindowJsonBeanArr[0] == null) {
                    return false;
                }
                LogUtils.i(TAG, "window =  " + fullWindowJsonBeanArr[0].getFullscreen() + "   " + fullWindowJsonBeanArr[0].getLabel(), false);
                if (fullWindowJsonBeanArr[0].getFullscreen().booleanValue()) {
                    return LABEL.equals(fullWindowJsonBeanArr[0].getLabel());
                }
                return false;
            } catch (Exception e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
                return false;
            }
        }
        LogUtils.w(TAG, "XAppManager is null", false);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isNapaTopViewFullscreen(int r5) {
        /*
            com.xiaopeng.carcontrol.config.feature.BaseFeatureOption r5 = com.xiaopeng.carcontrol.config.feature.BaseFeatureOption.getInstance()
            boolean r5 = r5.isSupportNapa()
            r0 = 0
            if (r5 != 0) goto Lc
            return r0
        Lc:
            com.xiaopeng.carcontrol.App r5 = com.xiaopeng.carcontrol.App.getInstance()
            android.content.Context r5 = r5.getApplicationContext()
            com.xiaopeng.view.WindowManagerFactory r5 = com.xiaopeng.view.WindowManagerFactory.create(r5)
            java.lang.String r5 = r5.getTopWindow()
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            com.xiaopeng.carcontrol.util.AmsUtils$1 r2 = new com.xiaopeng.carcontrol.util.AmsUtils$1
            r2.<init>()
            java.lang.reflect.Type r2 = r2.getType()
            java.lang.Object r5 = r1.fromJson(r5, r2)
            java.util.List r5 = (java.util.List) r5
            java.util.Iterator r5 = r5.iterator()
        L34:
            boolean r1 = r5.hasNext()
            if (r1 == 0) goto L81
            java.lang.Object r1 = r5.next()
            com.xiaopeng.carcontrol.bean.TopViewBean r1 = (com.xiaopeng.carcontrol.bean.TopViewBean) r1
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "bean = "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r1.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = "\n"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "AmsUtils"
            com.xiaopeng.carcontrol.util.LogUtils.d(r3, r2)
            java.lang.String r2 = r1.getPackageName()
            java.lang.String r4 = "XPPlugin_DolbyMusic.App"
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L7a
            java.lang.String r1 = r1.getPackageName()
            java.lang.String r2 = "XPPlugin_ApParkApp.App"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L34
        L7a:
            java.lang.String r5 = " isTopViewFullscreen = true"
            com.xiaopeng.carcontrol.util.LogUtils.d(r3, r5)
            r5 = 1
            return r5
        L81:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.util.AmsUtils.isNapaTopViewFullscreen(int):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isInParkTopView(int r5) {
        /*
            com.xiaopeng.carcontrol.config.feature.BaseFeatureOption r5 = com.xiaopeng.carcontrol.config.feature.BaseFeatureOption.getInstance()
            boolean r5 = r5.isSupportNapa()
            r0 = 0
            if (r5 != 0) goto Lc
            return r0
        Lc:
            com.xiaopeng.carcontrol.App r5 = com.xiaopeng.carcontrol.App.getInstance()
            android.content.Context r5 = r5.getApplicationContext()
            com.xiaopeng.view.WindowManagerFactory r5 = com.xiaopeng.view.WindowManagerFactory.create(r5)
            java.lang.String r5 = r5.getTopWindow()
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            com.xiaopeng.carcontrol.util.AmsUtils$2 r2 = new com.xiaopeng.carcontrol.util.AmsUtils$2
            r2.<init>()
            java.lang.reflect.Type r2 = r2.getType()
            java.lang.Object r5 = r1.fromJson(r5, r2)
            java.util.List r5 = (java.util.List) r5
            java.util.Iterator r5 = r5.iterator()
        L34:
            boolean r1 = r5.hasNext()
            if (r1 == 0) goto L94
            java.lang.Object r1 = r5.next()
            com.xiaopeng.carcontrol.bean.TopViewBean r1 = (com.xiaopeng.carcontrol.bean.TopViewBean) r1
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "isInParkTopView  bean.getPackageName() = "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r1.getPackageName()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "AmsUtils"
            com.xiaopeng.carcontrol.util.LogUtils.d(r3, r2, r0)
            java.lang.String r2 = r1.getPackageName()
            java.lang.String r4 = "XPPlugin_ApParkApp.App"
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L72
            java.lang.String r2 = r1.getMixedType()
            boolean r2 = isParkingStatus(r2)
            if (r2 == 0) goto L34
        L72:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r0 = "bean = "
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r0 = "\n isInParkTopView = true"
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.xiaopeng.carcontrol.util.LogUtils.d(r3, r5)
            r5 = 1
            return r5
        L94:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.util.AmsUtils.isInParkTopView(int):boolean");
    }

    private static boolean isParkingStatus(String status) {
        String[] split;
        if (TextUtils.isEmpty(status)) {
            return false;
        }
        LogUtils.d(TAG, "isParkingStatus " + status);
        for (String str : status.split(",")) {
            if (str.equals("18") || str.equals("36") || str.equals("34") || str.equals("45")) {
                return true;
            }
        }
        return false;
    }
}
