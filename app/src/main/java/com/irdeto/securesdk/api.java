package com.irdeto.securesdk;

import android.content.Context;
import android.text.TextUtils;
import com.O000000o.O000000o.O0000OOo;
import com.irdeto.android.sdk.R;
import com.irdeto.securesdk.core.EncryptionNativeUtil;
import java.io.UnsupportedEncodingException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class api {
    private static final String DEBUG_SHARED_LIB_PATH = "/mnt/sdcard/irdeto";
    private static final String TAG = "securesdk";

    api() {
    }

    public static String getSharedSoPath(Object obj) {
        if (obj == null) {
            return "";
        }
        Context context = (Context) obj;
        return (!com.irdeto.securesdk.core.O000000o.O000000o(context) || TextUtils.isEmpty(com.irdeto.securesdk.core.O000000o.O000000o(DEBUG_SHARED_LIB_PATH, "(libirmsdk_)(\\d+\\.\\d+\\.\\d+)(_.+)*(\\.so)"))) ? context.getString(R.string.shared_lib_path) : DEBUG_SHARED_LIB_PATH;
    }

    public static int isfCleanup() {
        return nisfCleanup();
    }

    public static byte[] isfCryptoOperate(String str, String str2, byte[] bArr, byte[] bArr2) throws ISFException {
        Feedback feedback = new Feedback();
        byte[] nisfCryptoOperate = nisfCryptoOperate(str, str2, bArr, bArr2, feedback);
        if (feedback.errorCode == 0) {
            return nisfCryptoOperate;
        }
        throw new ISFException(O000000o.ISF_CS_DECRYPT_FAILED, feedback.message + "(" + feedback.errorCode + ")");
    }

    public static byte[] isfDecrypt(String str) throws ISFException {
        Feedback feedback = new Feedback();
        byte[] nisfDecrypt = nisfDecrypt(str, "AES-DIR", feedback);
        if (feedback.errorCode == 0) {
            return nisfDecrypt;
        }
        throw new ISFException(O000000o.ISF_CS_DECRYPT_FAILED, feedback.message + "(" + feedback.errorCode + ")");
    }

    public static String isfGenerateOpaqueData(Feedback feedback) {
        return nisfGenerateOpaqueData(feedback);
    }

    public static byte[] isfGetMCUSecurityAsset(Feedback feedback) {
        return nisfGetMCUSecurityAsset(feedback);
    }

    public static String isfGetProvisionRequest(Feedback feedback) {
        return nisfGetProvisionRequest(feedback);
    }

    public static int isfInitialize() {
        return nisfInitialize();
    }

    public static byte[] isfMsgPack(byte[] bArr) {
        String str;
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = {0};
        try {
            str = EncryptionNativeUtil.encryptData(bArr);
        } catch (CryptoException e) {
            e.printStackTrace();
            str = "";
        }
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return bArr2;
        }
    }

    public static void isfProvisionResponse(String str, Feedback feedback) {
        nisfProvisionResponse(str, feedback);
    }

    public static boolean isfProvisioned(Feedback feedback) {
        return nisfProvisioned(feedback);
    }

    public static int isfSecureStoreReload() {
        return nisfSecureStoreReload();
    }

    public static int isfSetIndivStorePath(String str) {
        return nisfSetStorePath(str);
    }

    public static Object isfSipcDecrypt(ISFSecureEvent iSFSecureEvent) throws ISFException {
        Feedback feedback = new Feedback();
        feedback.errorCode = 0;
        feedback.message = null;
        try {
            Class<?> cls = Class.forName(iSFSecureEvent.classId);
            String navIsfSipcDecrypt = navIsfSipcDecrypt(iSFSecureEvent, feedback);
            if (feedback.errorCode != 1) {
                if (feedback.errorCode != 2) {
                    if (feedback.errorCode == 3 || feedback.errorCode == 4) {
                        throw new ISFException(O000000o.ISF_SIPC_OBJ_DECRYPT_FAIL);
                    }
                    try {
                        return new O0000OOo().O000000o(navIsfSipcDecrypt, (Class<Object>) cls);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ISFException(O000000o.ISF_SIPC_OBJ_DSRL_FAIL);
                    }
                }
                throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
            }
            throw new ISFException(O000000o.ISF_SIPC_NOT_INIT);
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
    }

    public static ISFSecureEvent isfSipcEncrypt(Object obj) throws ISFException {
        Feedback feedback = new Feedback();
        feedback.errorCode = 0;
        feedback.message = null;
        try {
            ISFSecureEvent navIsfSipcEncrypt = navIsfSipcEncrypt(new O0000OOo().O00000Oo(obj), feedback);
            if (feedback.errorCode != 1) {
                if (feedback.errorCode != 2) {
                    if (feedback.errorCode != 3) {
                        if (navIsfSipcEncrypt != null) {
                            navIsfSipcEncrypt.classId = obj.getClass().getName();
                        }
                        return navIsfSipcEncrypt;
                    }
                    throw new ISFException(O000000o.ISF_SIPC_OBJ_PRT_FAIL);
                }
                throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
            }
            throw new ISFException(O000000o.ISF_SIPC_NOT_INIT);
        } catch (Exception unused) {
            com.irdeto.securesdk.core.O000000o.O00000o0("securesdk", "GSON err.");
            throw new ISFException(O000000o.ISF_SIPC_OBJ_SRL_FAIL);
        }
    }

    public static void isfSipcInitialize(String str, boolean z) throws ISFException {
        int navIsfSipcInitialize = navIsfSipcInitialize(str, z);
        if (navIsfSipcInitialize != 0) {
            if (navIsfSipcInitialize != 2) {
                throw new ISFException(O000000o.ISF_SIPC_INIT_FAIL);
            }
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
    }

    public static boolean isfSipcInitialized() {
        return navIsfSipcInitialized();
    }

    public static byte[] isfStoreGet(String str, FileIndex fileIndex, Feedback feedback) {
        return nisfStoreGet(str, fileIndex.getValue(), feedback);
    }

    public static String isfTokenOperate(String[] strArr, byte[] bArr, Feedback feedback) {
        return nisfTokenOperate(strArr, bArr, feedback);
    }

    public static int isfTokenSave(String[] strArr, String[] strArr2) {
        return nisfTokenSave(strArr, strArr2);
    }

    private static native String navIsfSipcDecrypt(ISFSecureEvent iSFSecureEvent, Feedback feedback);

    private static native ISFSecureEvent navIsfSipcEncrypt(String str, Feedback feedback);

    private static native int navIsfSipcInitialize(String str, boolean z);

    private static native boolean navIsfSipcInitialized();

    private static native int nisfCleanup();

    private static native byte[] nisfCryptoOperate(String str, String str2, byte[] bArr, byte[] bArr2, Feedback feedback);

    private static native byte[] nisfDecrypt(String str, String str2, Feedback feedback);

    private static native String nisfGenerateOpaqueData(Feedback feedback);

    private static native byte[] nisfGetMCUSecurityAsset(Feedback feedback);

    private static native String nisfGetProvisionRequest(Feedback feedback);

    private static native int nisfInitialize();

    private static native byte[] nisfMsgPack(byte[] bArr);

    private static native void nisfProvisionResponse(String str, Feedback feedback);

    private static native boolean nisfProvisioned(Feedback feedback);

    private static native int nisfSecureStoreReload();

    private static native int nisfSetStorePath(String str);

    private static native byte[] nisfStoreGet(String str, int i, Feedback feedback);

    private static native String nisfTokenOperate(String[] strArr, byte[] bArr, Feedback feedback);

    private static native int nisfTokenSave(String[] strArr, String[] strArr2);
}
