package com.xiaopeng.lib.http.systemdelegate;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.xiaopeng.lib.http.IrdetoUtils;

/* loaded from: classes2.dex */
public class DelegateHelper {
    private static final String CALLING_DATA = "data";
    private static final String CALLING_RESULT = "result";
    private static final String CONTENT_PATH_IRDETO_TOKEN_AC = "buildTokenDataAC";
    private static final String CONTENT_PATH_IRDETO_TOKEN_ALL = "buildTokenDataAll";
    private static final Uri DELEGATE_CONTENT_URI = Uri.parse("content://com.xiaopeng.system.delegate");

    public static String callBuildTokenDataThroughSystemDelegate(Context context, String[] strArr, byte[] bArr) {
        if (context == null) {
            return null;
        }
        ContentResolver contentResolver = context.getContentResolver();
        String str = strArr.equals(IrdetoUtils.TOKEN_ALL) ? CONTENT_PATH_IRDETO_TOKEN_ALL : CONTENT_PATH_IRDETO_TOKEN_AC;
        Bundle bundle = new Bundle();
        bundle.putByteArray("data", bArr);
        Bundle call = contentResolver.call(DELEGATE_CONTENT_URI, str, (String) null, bundle);
        if (call == null) {
            return null;
        }
        return call.getString("result");
    }
}
