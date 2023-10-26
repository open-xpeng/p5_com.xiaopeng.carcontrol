package com.bumptech.glide.load.resource.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;

/* loaded from: classes.dex */
public final class DrawableDecoderCompat {
    private static volatile boolean shouldCallAppCompatResources = true;

    private DrawableDecoderCompat() {
    }

    public static Drawable getDrawable(Context context, int i) {
        return getDrawable(context, i, null);
    }

    public static Drawable getDrawable(Context context, int i, Resources.Theme theme) {
        try {
            if (shouldCallAppCompatResources) {
                return loadDrawableV7(context, i);
            }
        } catch (Resources.NotFoundException unused) {
        } catch (NoClassDefFoundError unused2) {
            shouldCallAppCompatResources = false;
        }
        if (theme == null) {
            theme = context.getTheme();
        }
        return loadDrawableV4(context, i, theme);
    }

    private static Drawable loadDrawableV7(Context context, int i) {
        return AppCompatResources.getDrawable(context, i);
    }

    private static Drawable loadDrawableV4(Context context, int i, Resources.Theme theme) {
        return ResourcesCompat.getDrawable(context.getResources(), i, theme);
    }
}
