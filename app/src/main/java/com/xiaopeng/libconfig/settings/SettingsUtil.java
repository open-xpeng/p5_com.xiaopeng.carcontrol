package com.xiaopeng.libconfig.settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes2.dex */
public class SettingsUtil {
    private static final String EXTRA_PAGE = "extra_page";
    public static final String PACKAGE_SETTINGS = "com.android.settings";
    public static final String PAGE_ABOUT = "About";
    public static final String PAGE_BLUETOOTH = "Bluetooth";
    public static final String PAGE_DATE_AND_TIME = "Date";
    public static final String PAGE_DISPALY = "Display";
    public static final String PAGE_OTA_UPDATE = "OTAUpdate";
    public static final String PAGE_SOUND = "Sound";
    public static final String PAGE_WIFI = "WiFi";
    public static final String PAGE_WLAN = "CarWLAN";
    private static final ComponentName SETTINGS_COMPONENT = new ComponentName("com.android.settings", "com.android.settings.Settings");

    public static void openSettings(Context context, String str) {
        Intent intent = new Intent();
        intent.setComponent(SETTINGS_COMPONENT);
        intent.putExtra(EXTRA_PAGE, str);
        context.startActivity(intent);
    }
}
