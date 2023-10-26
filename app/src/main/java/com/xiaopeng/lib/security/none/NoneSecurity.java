package com.xiaopeng.lib.security.none;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaopeng.lib.http.ICallback;
import com.xiaopeng.lib.http.Security;
import com.xiaopeng.lib.security.ISecurityModule;
import com.xiaopeng.lib.security.SecurityCommon;
import com.xiaopeng.lib.security.xmartv1.KeyStoreHelper;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.MD5Utils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class NoneSecurity implements ISecurityModule {
    private static final String NONE_ENCODING_ALGORITHM = "AES";
    private static final String TAG = "NoneSecurity";
    private static String mEncodingPsw = MD5Utils.getMD5(BuildInfoUtils.getHardwareId());
    private static boolean sIsInitSuccess;
    private SharedPreferences mSecureStore;
    private ISecurityModule.UidListener mUidListener;

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void asyncGetMCUSecurityKey(ICallback<byte[], String> iCallback) {
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void clearIndividualData() {
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String decode(String str) {
        return str;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void destroy() {
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String encode(String str) {
        return str;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String generateIndividualDataForServer() throws Exception {
        return "";
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String getIndividualFlag() {
        return "";
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String getIndividualRequestStr() throws Exception {
        return "";
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public boolean individualWithData(String str) throws Exception {
        return true;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public ISecurityModule initForIndividual(Context context) throws Exception {
        return this;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void saveIndividualFlag() {
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void saveMCUSecurityKey(boolean z) {
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void setUidListener(ISecurityModule.UidListener uidListener) {
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public Security.EncryptionType getEncryptionType() {
        return Security.EncryptionType.NONE_ENCODING;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public ISecurityModule init(Context context) throws Exception {
        synchronized (this) {
            sIsInitSuccess = true;
            this.mSecureStore = context.getSharedPreferences(TAG, 0);
        }
        return this;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public boolean isInitAndIndivSuccess() {
        return sIsInitSuccess;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public boolean isInitSuccess() {
        return sIsInitSuccess;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String getString(String str) {
        String string = this.mSecureStore.getString(str, "");
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        byte[] bArr = null;
        try {
            byte[] parseHexStr2Byte = SecurityCommon.parseHexStr2Byte(string);
            SecretKeySpec secretKeySpec = new SecretKeySpec(mEncodingPsw.getBytes(), NONE_ENCODING_ALGORITHM);
            Cipher cipher = Cipher.getInstance(NONE_ENCODING_ALGORITHM);
            cipher.init(2, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            bArr = cipher.doFinal(parseHexStr2Byte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        try {
            return new String(bArr, "UTF-8");
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void setString(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            this.mSecureStore.edit().putString(str, str2).commit();
            return;
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(mEncodingPsw.getBytes(), NONE_ENCODING_ALGORITHM);
            Cipher cipher = Cipher.getInstance(NONE_ENCODING_ALGORITHM);
            cipher.init(1, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] doFinal = cipher.doFinal(str2.getBytes("UTF-8"));
            if (doFinal == null || doFinal.length <= 0) {
                return;
            }
            this.mSecureStore.edit().putString(str, SecurityCommon.parseByte2HexStr(doFinal)).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void deleteString(String str) {
        this.mSecureStore.edit().remove(str).commit();
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String buildTokenData(String[] strArr, byte[] bArr) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            ISecurityModule.UidListener uidListener = this.mUidListener;
            String currentUid = uidListener != null ? uidListener.getCurrentUid() : null;
            int i = 0;
            for (int i2 = 0; i2 < strArr.length; i2++) {
                String str = strArr[i2];
                if (!TextUtils.isEmpty(currentUid)) {
                    str = SecurityCommon.getUidIdsKey(currentUid, strArr[i2]);
                }
                String string = getString(str);
                if (!TextUtils.isEmpty(string)) {
                    linkedHashMap.put(strArr[i2], string);
                    i += strArr[i2].length() + 2 + string.length();
                }
            }
            if (i > 0) {
                ByteBuffer put = ByteBuffer.allocate(bArr.length + i).put(bArr);
                for (int i3 = 0; i3 < strArr.length; i3++) {
                    put.put((byte) 38).put(strArr[i3].getBytes("UTF-8")).put((byte) 61).put(((String) linkedHashMap.get(strArr[i3])).getBytes("UTF-8"));
                }
                bArr = put.array();
            }
            return new String(Base64.encode(bArr, 2), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String cryptoDecode(String str, String str2, byte[] bArr) {
        byte[] cryptoDecodeInByteArray = cryptoDecodeInByteArray(str, str2, bArr);
        if (cryptoDecodeInByteArray != null) {
            return new String(cryptoDecodeInByteArray);
        }
        return null;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public byte[] cryptoDecodeInByteArray(String str, String str2, byte[] bArr) {
        return KeyStoreHelper.decryptStringWithKey(bArr, str, str2.getBytes());
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void asyncSaveToken(final String[] strArr, final String[] strArr2, final Runnable runnable) {
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.security.none.NoneSecurity.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    NoneSecurity.this.saveToken(strArr, strArr2);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                runnable.run();
            }
        });
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void asyncSaveTokenWithListener(final String[] strArr, final String[] strArr2, final ISecurityModule.ResultListener resultListener) {
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.security.none.NoneSecurity.2
            @Override // java.lang.Runnable
            public void run() {
                String str;
                ISecurityModule.ResultListener.RESULT result = ISecurityModule.ResultListener.RESULT.FAIL;
                try {
                    NoneSecurity.this.saveToken(strArr, strArr2);
                    result = ISecurityModule.ResultListener.RESULT.SUCCEED;
                    str = "";
                } catch (Throwable th) {
                    String message = th.getMessage();
                    th.printStackTrace();
                    str = message;
                }
                resultListener.onResult(result, str);
            }
        });
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void asyncSaveToken(final String str, final String[] strArr, final String[] strArr2, final Runnable runnable) {
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.security.none.NoneSecurity.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    NoneSecurity.this.saveToken(strArr, strArr2);
                    NoneSecurity.this.saveToken(str, strArr, strArr2);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                runnable.run();
            }
        });
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void asyncSaveTokenWithListener(final String str, final String[] strArr, final String[] strArr2, final ISecurityModule.ResultListener resultListener) {
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.security.none.NoneSecurity.4
            @Override // java.lang.Runnable
            public void run() {
                String str2;
                ISecurityModule.ResultListener.RESULT result = ISecurityModule.ResultListener.RESULT.FAIL;
                try {
                    NoneSecurity.this.saveToken(strArr, strArr2);
                    NoneSecurity.this.saveToken(str, strArr, strArr2);
                    result = ISecurityModule.ResultListener.RESULT.SUCCEED;
                    str2 = "";
                } catch (Throwable th) {
                    String message = th.getMessage();
                    th.printStackTrace();
                    str2 = message;
                }
                resultListener.onResult(result, str2);
            }
        });
    }

    public void saveToken(String[] strArr, String[] strArr2) {
        if (strArr.length != strArr2.length) {
            LogUtils.d(TAG, "Invalid tokens.");
            return;
        }
        for (int i = 0; i < strArr.length; i++) {
            setString(strArr[i], strArr2[i]);
        }
    }

    public void saveToken(String str, String[] strArr, String[] strArr2) {
        if (strArr.length != strArr2.length) {
            LogUtils.d(TAG, "Invalid tokens.");
            return;
        }
        for (int i = 0; i < strArr.length; i++) {
            String uidIdsKey = SecurityCommon.getUidIdsKey(str, strArr[i]);
            LogUtils.i(TAG, "saveToken key = " + uidIdsKey);
            setString(uidIdsKey, strArr2[i]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class Holder {
        private static final NoneSecurity INSTANCE = new NoneSecurity();

        private Holder() {
        }
    }

    public static NoneSecurity getInstance() {
        return Holder.INSTANCE;
    }

    private NoneSecurity() {
        this.mUidListener = null;
    }
}
