package com.xiaopeng.lib.http;

import android.content.Context;
import android.os.Build;
import com.xiaopeng.lib.security.ISecurityModule;
import com.xiaopeng.lib.security.SecurityCommon;
import com.xiaopeng.lib.security.SecurityModuleFactory;
import com.xiaopeng.lib.security.irdeto.IrdetoSecurity;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.MD5Utils;
import com.xiaopeng.libconfig.ipc.AccountConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class Security {
    private static final String TAG = "Security";
    private static Context sContext;
    private static ISecurityModule sDepreciateUtils;
    private static boolean sIsInitSuccess;
    public static final String[] TOKEN_ALL = {"accessToken", "refreshToken"};
    public static final String[] TOKEN_AC = {"accessToken"};
    private static boolean sUseDepreciate = false;
    private static ISecurityModule sSecurityUtils = SecurityModuleFactory.getSecurityModule();

    /* loaded from: classes2.dex */
    public interface ResultListener extends ISecurityModule.ResultListener {
    }

    static {
        if (Build.VERSION.SDK_INT == 19) {
            sDepreciateUtils = IrdetoSecurity.getInstance();
        }
    }

    /* loaded from: classes2.dex */
    public enum EncryptionType {
        IRDETO(1),
        TEE_RANDOM_KEY(2),
        NONE_ENCODING(3);
        
        private int value;

        EncryptionType(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static void useIrdeto(boolean z) {
        sUseDepreciate = z;
    }

    public static EncryptionType getActiveEncryptionType() {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            return iSecurityModule.getEncryptionType();
        }
        return sSecurityUtils.getEncryptionType();
    }

    public static synchronized void init(Context context) {
        synchronized (Security.class) {
            if (sIsInitSuccess) {
                LogUtils.d(TAG, "sSecureSDKManager has init");
                return;
            }
            sContext = context;
            sIsInitSuccess = false;
            try {
                sSecurityUtils.init(context);
                sIsInitSuccess = true;
                ISecurityModule iSecurityModule = sDepreciateUtils;
                if (iSecurityModule != null) {
                    try {
                        iSecurityModule.init(context);
                        if (!sDepreciateUtils.isInitAndIndivSuccess()) {
                            sDepreciateUtils = null;
                        }
                    } catch (Exception e) {
                        sDepreciateUtils = null;
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static boolean isInitSuccess() {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            return iSecurityModule.isInitAndIndivSuccess();
        }
        return sSecurityUtils.isInitAndIndivSuccess();
    }

    public static synchronized boolean isInitSuccessWithoutIndiv() {
        boolean z;
        synchronized (Security.class) {
            z = sIsInitSuccess;
        }
        return z;
    }

    public static synchronized void destroy() {
        synchronized (Security.class) {
            sSecurityUtils.destroy();
            sIsInitSuccess = false;
            ISecurityModule iSecurityModule = sDepreciateUtils;
            if (iSecurityModule != null) {
                try {
                    iSecurityModule.destroy();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String encode(String str) {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            return iSecurityModule.encode(str);
        }
        return sSecurityUtils.encode(str);
    }

    public static String decode(String str) {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            return iSecurityModule.decode(str);
        }
        return sSecurityUtils.decode(str);
    }

    public static String getString(String str) {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            return iSecurityModule.getString(str);
        }
        return sSecurityUtils.getString(str);
    }

    public static void setString(String str, String str2) {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            iSecurityModule.setString(str, str2);
        } else {
            sSecurityUtils.setString(str, str2);
        }
    }

    public static void deleteString(String str) {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            iSecurityModule.deleteString(str);
        } else {
            sSecurityUtils.deleteString(str);
        }
    }

    public static String buildTokenData(String[] strArr, byte[] bArr) {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            return iSecurityModule.buildTokenData(strArr, bArr);
        }
        return sSecurityUtils.buildTokenData(strArr, bArr);
    }

    public static String parseByte2HexStr(byte[] bArr) {
        return SecurityCommon.parseByte2HexStr(bArr);
    }

    public static byte[] parseHexStr2Byte(String str) {
        return SecurityCommon.parseHexStr2Byte(str);
    }

    public static String cryptoDecode(String str, String str2, byte[] bArr) {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            return iSecurityModule.cryptoDecode(str, str2, bArr);
        }
        return sSecurityUtils.cryptoDecode(str, str2, bArr);
    }

    public static byte[] cryptoDecodeInByteArray(String str, String str2, byte[] bArr) {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            return iSecurityModule.cryptoDecodeInByteArray(str, str2, bArr);
        }
        return sSecurityUtils.cryptoDecodeInByteArray(str, str2, bArr);
    }

    public static String base64UrlEncode(byte[] bArr) {
        return SecurityCommon.base64UrlEncode(bArr);
    }

    public static String sign(Context context, Map<String, String> map, long j) {
        return MD5Utils.getMD5("xmart:appid:002" + j + sortParameterAndValues(map) + CommonUtils.CAR_APP_SEC).toLowerCase();
    }

    public static void asyncGetMCUSecurityKey(ICallback<byte[], String> iCallback) {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            iSecurityModule.asyncGetMCUSecurityKey(iCallback);
        } else {
            sSecurityUtils.asyncGetMCUSecurityKey(iCallback);
        }
    }

    private static String sortParameterAndValues(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        Set<String> keySet = map.keySet();
        ArrayList<String> arrayList = new ArrayList();
        for (String str : keySet) {
            arrayList.add(str);
        }
        Collections.sort(arrayList);
        StringBuffer stringBuffer = new StringBuffer();
        for (String str2 : arrayList) {
            if (!"app_id".equals(str2) && !"timestamp".equals(str2) && !AccountConfig.KEY_SIGN.equals(str2)) {
                stringBuffer.append(str2);
                stringBuffer.append(map.get(str2));
            }
        }
        return stringBuffer.toString();
    }

    public static synchronized void asyncSaveToken(String[] strArr, String[] strArr2, Runnable runnable) {
        ISecurityModule iSecurityModule;
        synchronized (Security.class) {
            if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
                iSecurityModule.asyncSaveToken(strArr, strArr2, runnable);
            } else {
                sSecurityUtils.asyncSaveToken(strArr, strArr2, runnable);
            }
        }
    }

    public static synchronized void asyncSaveTokenWithListener(String[] strArr, String[] strArr2, ResultListener resultListener) {
        ISecurityModule iSecurityModule;
        synchronized (Security.class) {
            if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
                iSecurityModule.asyncSaveTokenWithListener(strArr, strArr2, resultListener);
            } else {
                sSecurityUtils.asyncSaveTokenWithListener(strArr, strArr2, resultListener);
            }
        }
    }

    public static synchronized void asyncSaveToken(String str, String[] strArr, String[] strArr2, Runnable runnable) {
        ISecurityModule iSecurityModule;
        synchronized (Security.class) {
            if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
                iSecurityModule.asyncSaveToken(str, strArr, strArr2, runnable);
            } else {
                sSecurityUtils.asyncSaveToken(str, strArr, strArr2, runnable);
            }
        }
    }

    public static synchronized void asyncSaveTokenWithListener(String str, String[] strArr, String[] strArr2, ResultListener resultListener) {
        ISecurityModule iSecurityModule;
        synchronized (Security.class) {
            if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
                iSecurityModule.asyncSaveTokenWithListener(str, strArr, strArr2, resultListener);
            } else {
                sSecurityUtils.asyncSaveTokenWithListener(str, strArr, strArr2, resultListener);
            }
        }
    }

    public static void setUidListener(ISecurityModule.UidListener uidListener) {
        ISecurityModule iSecurityModule;
        if (sUseDepreciate && (iSecurityModule = sDepreciateUtils) != null) {
            iSecurityModule.setUidListener(uidListener);
        } else {
            sSecurityUtils.setUidListener(uidListener);
        }
    }
}
