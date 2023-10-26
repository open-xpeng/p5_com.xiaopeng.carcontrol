package com.irdeto.securesdk;

import android.text.TextUtils;
import com.irdeto.securesdk.core.EncryptionNativeUtil;
import com.irdeto.securesdk.core.SSUtils;
import java.io.File;

/* loaded from: classes.dex */
public class isf {
    private static final String TAG = "securesdk";

    public static byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public static byte[] isfCryptoOperate(String str, String str2, String str3, byte[] bArr) throws ISFException {
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty() || str3 == null || str3.isEmpty() || !str3.matches("[a-f0-9A-F]{32}") || bArr == null || bArr.length == 0 || bArr.length % 16 != 0) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        if (str.equals("IR_CRYPTO_AES_CBC_DECRYPT")) {
            return api.isfCryptoOperate(str, str2, hexStringToByteArray(str3), bArr);
        }
        throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
    }

    public static String isfDecrypt(String str) throws ISFException {
        if (str == null || str.isEmpty()) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        if (isfProvisioned()) {
            return new String(api.isfDecrypt(str));
        }
        throw new ISFException(O000000o.ISF_MGR_NOT_PROVISIONED);
    }

    public static String isfEncrypt(String str) throws ISFException {
        if (str == null || str.isEmpty()) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        if (isfProvisioned()) {
            try {
                return EncryptionNativeUtil.encryptData(str.getBytes());
            } catch (CryptoException e) {
                throw new ISFException(O000000o.ISF_CS_DECRYPT_FAILED, e.getMessage() + "(" + e.getErrorCode() + ")");
            }
        }
        throw new ISFException(O000000o.ISF_MGR_NOT_PROVISIONED);
    }

    public static String isfGenerateOpaqueData() throws ISFException {
        if (isfProvisioned()) {
            Feedback feedback = new Feedback();
            String isfGenerateOpaqueData = api.isfGenerateOpaqueData(feedback);
            if (feedback.errorCode == 0) {
                return isfGenerateOpaqueData;
            }
            throw new ISFException(O000000o.ISF_MGR_GENERATE_OPAQUE_DATA_FAILED, feedback.message + "(" + feedback.errorCode + ")");
        }
        throw new ISFException(O000000o.ISF_MGR_NOT_PROVISIONED);
    }

    public static byte[] isfGetMCUSecurityAsset() throws ISFException {
        Feedback feedback = new Feedback();
        byte[] isfGetMCUSecurityAsset = api.isfGetMCUSecurityAsset(feedback);
        if (feedback.errorCode == O000000o.ISF_MGR_NONE.O000000o() || feedback.errorCode == O000000o.ISF_MGR_ROOTDETECTED.O000000o()) {
            return isfGetMCUSecurityAsset;
        }
        throw new ISFException(O000000o.ISF_MGR_UNKNOWN, feedback.message + "(" + String.format("%x", Integer.valueOf(feedback.errorCode)) + ")");
    }

    public static String isfGetProvisionRequest() throws ISFException {
        Feedback feedback = new Feedback();
        String isfGetProvisionRequest = api.isfGetProvisionRequest(feedback);
        if (feedback.errorCode == 0) {
            return isfGetProvisionRequest;
        }
        try {
            throw new ISFException(O000000o.O00000Oo(feedback.errorCode));
        } catch (IllegalArgumentException unused) {
            throw new ISFException(O000000o.ISF_MGR_UNKNOWN, feedback.message + "(" + feedback.errorCode + ")");
        }
    }

    public static void isfProvisionResponse(String str) throws ISFException {
        Feedback feedback = new Feedback();
        api.isfProvisionResponse(str, feedback);
        if (feedback.errorCode == 0) {
            return;
        }
        try {
            throw new ISFException(O000000o.O00000Oo(feedback.errorCode));
        } catch (IllegalArgumentException unused) {
            throw new ISFException(O000000o.ISF_MGR_UNKNOWN, feedback.message + "(" + feedback.errorCode + ")");
        }
    }

    public static boolean isfProvisioned() throws ISFException {
        return api.isfProvisioned(new Feedback());
    }

    public static void isfSecureStoreReload() throws ISFException {
        if (!isfProvisioned()) {
            throw new ISFException(O000000o.ISF_MGR_NOT_PROVISIONED);
        }
        int isfSecureStoreReload = api.isfSecureStoreReload();
        if (isfSecureStoreReload != 0) {
            throw new ISFException(O000000o.ISF_MGR_UNKNOWN, String.valueOf(isfSecureStoreReload));
        }
    }

    public static Object isfSipcDecrypt(ISFSecureEvent iSFSecureEvent) throws ISFException {
        if (iSFSecureEvent != null) {
            if (TextUtils.isEmpty(iSFSecureEvent.classId) || TextUtils.isEmpty(iSFSecureEvent.data) || TextUtils.isEmpty(iSFSecureEvent.iv) || TextUtils.isEmpty(iSFSecureEvent.keyId)) {
                throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
            }
            return api.isfSipcDecrypt(iSFSecureEvent);
        }
        throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
    }

    public static ISFSecureEvent isfSipcEncrypt(Object obj) throws ISFException {
        if (obj != null) {
            return api.isfSipcEncrypt(obj);
        }
        throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
    }

    public static void isfSipcInitialize(String str, boolean z) throws ISFException {
        if (TextUtils.isEmpty(str)) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        if (!new File(str).exists()) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        api.isfSipcInitialize(str, z);
    }

    public static boolean isfSipcInitialized() {
        return api.isfSipcInitialized();
    }

    public static void isfStoreDelete(String str) throws ISFException {
        if (str == null || str.length() <= 0 || str.length() > 100) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        SSUtils.O000000o("", str);
    }

    public static byte[] isfStoreGet(String str) throws ISFException {
        if (str == null || str.length() <= 0 || str.length() > 100) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        return SSUtils.O00000Oo(str, null);
    }

    public static byte[] isfStoreGet(String str, FileIndex fileIndex) throws ISFException {
        if (str == null || str.length() == 0 || str.length() > 100) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        Feedback feedback = new Feedback();
        byte[] isfStoreGet = api.isfStoreGet(str, fileIndex, feedback);
        if (feedback.errorCode == O000000o.ISF_MGR_NONE.O000000o() || feedback.errorCode == O000000o.ISF_MGR_ROOTDETECTED.O000000o()) {
            return isfStoreGet;
        }
        throw new ISFException(O000000o.ISF_SS_READ_FAILED, feedback.message + "(" + String.format("%x", Integer.valueOf(feedback.errorCode)) + ")");
    }

    public static void isfStorePut(String str, byte[] bArr) throws ISFException {
        if (str == null || bArr == null || str.length() <= 0 || str.length() > 100 || bArr.length <= 0) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        SSUtils.O000000o(str, bArr);
    }

    public static String isfTokenOperate(String[] strArr, byte[] bArr) throws ISFException {
        if (bArr == null || strArr == null) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        for (String str : strArr) {
            if (str.equals("")) {
                throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
            }
        }
        if (isfProvisioned()) {
            Feedback feedback = new Feedback();
            String isfTokenOperate = api.isfTokenOperate(strArr, bArr, feedback);
            if (feedback.errorCode == 0) {
                return isfTokenOperate;
            }
            throw new ISFException(O000000o.ISF_MGR_TOKEN_OPERATE_FAILED, feedback.message + "(" + feedback.errorCode + ")");
        }
        throw new ISFException(O000000o.ISF_MGR_NOT_PROVISIONED);
    }

    public static void isfTokenSave(String[] strArr, String[] strArr2) throws ISFException {
        if (strArr2 == null || strArr == null) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        if (strArr.length != strArr2.length) {
            throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
        }
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals("") || strArr2[i].equals("")) {
                throw new ISFException(O000000o.ISF_MGR_INVALID_PARAMETER);
            }
        }
        if (!isfProvisioned()) {
            throw new ISFException(O000000o.ISF_MGR_NOT_PROVISIONED);
        }
        int isfTokenSave = api.isfTokenSave(strArr, strArr2);
        if (isfTokenSave != 0) {
            throw new ISFException(O000000o.ISF_MGR_TOKEN_SAVE_FAILED, String.valueOf(isfTokenSave));
        }
    }
}
