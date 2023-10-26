package com.xiaopeng.lib.http;

import android.content.Context;
import com.xiaopeng.lib.security.ISecurityModule;
import com.xiaopeng.lib.security.SecurityCommon;
import com.xiaopeng.lib.security.irdeto.IrdetoSecurity;
import com.xiaopeng.lib.utils.LogUtils;
import java.util.Map;

@Deprecated
/* loaded from: classes2.dex */
public class IrdetoUtils {
    private static final String TAG = "IrdetoUtils";
    private static boolean sIsInitSuccess;
    public static final String[] TOKEN_ALL = {"accessToken", "refreshToken"};
    public static final String[] TOKEN_AC = {"accessToken"};
    private static ISecurityModule sSecurityUtils = IrdetoSecurity.getInstance();

    /* loaded from: classes2.dex */
    public interface ResultListener extends ISecurityModule.ResultListener {
    }

    public static synchronized void init(Context context) {
        synchronized (IrdetoUtils.class) {
            if (sIsInitSuccess) {
                LogUtils.d(TAG, "sSecureSDKManager has init");
                return;
            }
            try {
                sSecurityUtils.init(context);
                sIsInitSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isIrdetoInitSuccess() {
        return sSecurityUtils.isInitAndIndivSuccess();
    }

    public static synchronized boolean isIrdetoInitSuccessWithoutIndiv() {
        boolean z;
        synchronized (IrdetoUtils.class) {
            z = sIsInitSuccess;
        }
        return z;
    }

    public static synchronized void destroy() {
        synchronized (IrdetoUtils.class) {
            sSecurityUtils.destroy();
            sIsInitSuccess = false;
        }
    }

    public static String encode(String str) {
        return sSecurityUtils.encode(str);
    }

    public static String decode(String str) {
        return sSecurityUtils.decode(str);
    }

    public static String getString(String str) {
        return sSecurityUtils.getString(str);
    }

    public static void setString(String str, String str2) {
        sSecurityUtils.setString(str, str2);
    }

    public static void deleteString(String str) {
        sSecurityUtils.deleteString(str);
    }

    public static String buildTokenData(String[] strArr, byte[] bArr) {
        return sSecurityUtils.buildTokenData(strArr, bArr);
    }

    public static String parseByte2HexStr(byte[] bArr) {
        return SecurityCommon.parseByte2HexStr(bArr);
    }

    public static byte[] parseHexStr2Byte(String str) {
        return SecurityCommon.parseHexStr2Byte(str);
    }

    public static String cryptoDecode(String str, String str2, byte[] bArr) {
        return sSecurityUtils.cryptoDecode(str, str2, bArr);
    }

    public static byte[] cryptoDecodeInByteArray(String str, String str2, byte[] bArr) {
        return sSecurityUtils.cryptoDecodeInByteArray(str, str2, bArr);
    }

    public static String base64UrlEncode(byte[] bArr) {
        return SecurityCommon.base64UrlEncode(bArr);
    }

    public static String sign(Context context, Map<String, String> map, long j) {
        return Security.sign(context, map, j);
    }

    public static void asyncGetMCUSecurityKey(ICallback<byte[], String> iCallback) {
        sSecurityUtils.asyncGetMCUSecurityKey(iCallback);
    }

    public static synchronized void asyncSaveToken(String[] strArr, String[] strArr2, Runnable runnable) {
        synchronized (IrdetoUtils.class) {
            sSecurityUtils.asyncSaveToken(strArr, strArr2, runnable);
        }
    }

    public static synchronized void asyncSaveTokenWithListener(String[] strArr, String[] strArr2, ResultListener resultListener) {
        synchronized (IrdetoUtils.class) {
            sSecurityUtils.asyncSaveTokenWithListener(strArr, strArr2, resultListener);
        }
    }
}
