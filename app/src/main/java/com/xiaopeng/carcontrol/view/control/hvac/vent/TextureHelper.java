package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes2.dex */
public class TextureHelper {
    private static final String TAG = "TextureHelper";

    public static int[] loadTexture(Context context, int[] resourceIds) {
        int length = resourceIds.length;
        int[] iArr = new int[length];
        GLES20.glGenTextures(resourceIds.length, iArr, 0);
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            String str = TAG;
            LogUtils.d(str, "objectId " + i2);
            if (i2 != 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), resourceIds[i], options);
                if (decodeResource == null) {
                    LogUtils.d(str, "bitmap null " + resourceIds[i]);
                    GLES20.glDeleteTextures(i, iArr, 0);
                    return iArr;
                }
                GLES20.glBindTexture(3553, iArr[i]);
                GLES20.glTexParameteri(3553, 10241, 9987);
                GLES20.glTexParameteri(3553, TarConstants.DEFAULT_BLKSIZE, 9729);
                GLUtils.texImage2D(3553, 0, decodeResource, 0);
                decodeResource.recycle();
                GLES20.glGenerateMipmap(3553);
                GLES20.glBindTexture(3553, 0);
            }
        }
        return iArr;
    }

    public static void changeTexture(Context context, int textureObjectId, int resourceId) {
        LogUtils.d(TAG, "changeTexture " + textureObjectId + ", resourceId " + resourceId);
        if (textureObjectId != 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
            if (decodeResource == null) {
                return;
            }
            GLES20.glBindTexture(3553, textureObjectId);
            GLES20.glTexParameteri(3553, 10241, 9987);
            GLES20.glTexParameteri(3553, TarConstants.DEFAULT_BLKSIZE, 9729);
            GLUtils.texSubImage2D(3553, 0, 0, 0, decodeResource);
            decodeResource.recycle();
            GLES20.glGenerateMipmap(3553);
            GLES20.glBindTexture(3553, 0);
        }
    }
}
