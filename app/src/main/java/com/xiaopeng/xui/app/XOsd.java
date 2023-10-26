package com.xiaopeng.xui.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaopeng.xui.Xui;

@Deprecated
/* loaded from: classes2.dex */
public class XOsd {
    public static final int ID_SHARED_PRIMARY = 0;
    public static final int ID_SHARED_SECONDARY = 1;

    public static void showOsd(CharSequence charSequence, Icon icon, int i, int i2, int i3) {
        showOsd(charSequence, null, null, icon, null, i, i2, i3);
    }

    public static void showOsd(CharSequence charSequence, Icon icon, CharSequence charSequence2) {
        showOsd(charSequence, null, null, icon, charSequence2, 0, 0, 0);
    }

    public static void showOsd(CharSequence charSequence, Icon icon, Icon icon2, Icon icon3, CharSequence charSequence2) {
        showOsd(charSequence, icon, icon2, icon3, charSequence2, 0, 0, 0);
    }

    public static void showOsd(CharSequence charSequence, Icon icon, Icon icon2, Icon icon3, CharSequence charSequence2, int i, int i2, int i3) {
        showOsd(charSequence, icon, icon2, icon3, charSequence2, i, i2, i3, -1);
    }

    public static void showOsd(CharSequence charSequence, Icon icon, Icon icon2, Icon icon3, CharSequence charSequence2, int i, int i2, int i3, int i4) {
        if (TextUtils.isEmpty(charSequence) || Xui.getContext() == null) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) Xui.getContext().getSystemService("notification");
        notificationManager.createNotificationChannel(new NotificationChannel("channel_id_osd", "channel_name_osd", 3));
        Notification.Builder builder = new Notification.Builder(Xui.getContext(), "channel_id_osd");
        builder.setSmallIcon(17301595);
        Bundle extras = builder.getExtras();
        extras.putCharSequence("android.osd.title", charSequence);
        extras.putParcelable("android.osd.title.icon.left", icon);
        extras.putParcelable("android.osd.title.icon.right", icon2);
        extras.putParcelable("android.osd.icon", icon3);
        extras.putCharSequence("android.osd.content", charSequence2);
        extras.putInt("android.osd.progress", i);
        extras.putInt("android.osd.progress.min", i2);
        extras.putInt("android.osd.progress.max", i3);
        extras.putInt("android.displayFlag", 2);
        if (i4 != -1) {
            extras.putInt("android.osd.shared.id", i4);
        }
        builder.setExtras(extras);
        notificationManager.notify(100, builder.build());
    }
}
