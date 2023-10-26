package com.xiaopeng.xpmeditation.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class GlideUtil {
    private static boolean isDestroyed(Context context) {
        return (context instanceof Activity) && ((Activity) context).isDestroyed();
    }

    public static void load(Context context, int destId, ImageView imageView) {
        if (isDestroyed(context)) {
            return;
        }
        try {
            imageView.setImageResource(destId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadDrawable(Context context, int url, ImageView imageView) {
        if (isDestroyed(context)) {
            return;
        }
        try {
            Glide.with(context).load(Integer.valueOf(url)).apply(new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadWithOriginSize(Context context, String url, ImageView imageView) {
        if (isDestroyed(context)) {
            return;
        }
        try {
            Glide.with(context).load(url).apply(new RequestOptions().override(Integer.MIN_VALUE, Integer.MIN_VALUE)).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadWithOriginSize(Context context, String url, int defaultId, ImageView imageView) {
        if (isDestroyed(context)) {
            return;
        }
        try {
            Glide.with(context).load(url).apply(new RequestOptions().placeholder(defaultId).override(Integer.MIN_VALUE, Integer.MIN_VALUE)).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load(Context context, String url, ImageView imageView) {
        if (isDestroyed(context)) {
            return;
        }
        try {
            Glide.with(context).load(url).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean load(Context context, String url, SimpleTarget target) {
        if (isDestroyed(context)) {
            return false;
        }
        try {
            Glide.with(context).load(url).into((RequestBuilder<Drawable>) target);
            return true;
        } catch (Exception e) {
            LogUtils.e("GlideUtil", "load", e);
            return false;
        }
    }

    public static void asyncLoadSrc(final int resId, ImageView view) {
        final WeakReference weakReference = new WeakReference(view);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.xpmeditation.util.GlideUtil.1
            @Override // java.lang.Runnable
            public void run() {
                final Drawable drawable = App.getInstance().getApplicationContext().getDrawable(resId);
                ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.xpmeditation.util.GlideUtil.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ImageView imageView = (ImageView) weakReference.get();
                        if (imageView != null) {
                            imageView.setImageDrawable(drawable);
                        }
                    }
                });
            }
        });
    }

    public static void asyncLoadBackground(final int resId, View view) {
        final WeakReference weakReference = new WeakReference(view);
        com.xiaopeng.lib.utils.ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.xpmeditation.util.-$$Lambda$GlideUtil$VMzyrMhm7z8b4lvdQujCRady7Mc
            @Override // java.lang.Runnable
            public final void run() {
                GlideUtil.lambda$asyncLoadBackground$1(resId, weakReference);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$asyncLoadBackground$1(final int resId, final WeakReference viewRef) {
        final Drawable drawable = App.getInstance().getDrawable(resId);
        com.xiaopeng.lib.utils.ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.xpmeditation.util.-$$Lambda$GlideUtil$qbbS-uc0moYVC2BOBRnSVp3ruvU
            @Override // java.lang.Runnable
            public final void run() {
                GlideUtil.lambda$null$0(viewRef, drawable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$null$0(final WeakReference viewRef, final Drawable drawable) {
        View view = (View) viewRef.get();
        if (view != null) {
            view.setBackground(drawable);
        }
    }

    public static String getResource(String fileName) {
        return String.format("file:///android_asset/meditation/%s", fileName);
    }
}
