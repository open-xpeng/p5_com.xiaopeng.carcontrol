package com.xiaopeng.lib.security;

import android.content.Context;
import com.xiaopeng.lib.http.ICallback;
import com.xiaopeng.lib.http.Security;

/* loaded from: classes2.dex */
public interface ISecurityModule {

    /* loaded from: classes2.dex */
    public interface ResultListener {

        /* loaded from: classes2.dex */
        public enum RESULT {
            FAIL,
            SUCCEED
        }

        void onResult(RESULT result, String str);
    }

    /* loaded from: classes2.dex */
    public interface UidListener {
        String getCurrentUid();
    }

    void asyncGetMCUSecurityKey(ICallback<byte[], String> iCallback);

    void asyncSaveToken(String str, String[] strArr, String[] strArr2, Runnable runnable);

    void asyncSaveToken(String[] strArr, String[] strArr2, Runnable runnable);

    void asyncSaveTokenWithListener(String str, String[] strArr, String[] strArr2, ResultListener resultListener);

    void asyncSaveTokenWithListener(String[] strArr, String[] strArr2, ResultListener resultListener);

    String buildTokenData(String[] strArr, byte[] bArr);

    void clearIndividualData();

    String cryptoDecode(String str, String str2, byte[] bArr);

    byte[] cryptoDecodeInByteArray(String str, String str2, byte[] bArr);

    String decode(String str);

    void deleteString(String str);

    void destroy();

    String encode(String str);

    String generateIndividualDataForServer() throws Exception;

    Security.EncryptionType getEncryptionType();

    String getIndividualFlag();

    String getIndividualRequestStr() throws Exception;

    String getString(String str);

    boolean individualWithData(String str) throws Exception;

    ISecurityModule init(Context context) throws Exception;

    ISecurityModule initForIndividual(Context context) throws Exception;

    boolean isInitAndIndivSuccess();

    boolean isInitSuccess();

    void saveIndividualFlag();

    void saveMCUSecurityKey(boolean z);

    void setString(String str, String str2);

    void setUidListener(UidListener uidListener);
}
