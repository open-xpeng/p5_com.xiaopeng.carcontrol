package com.xiaopeng.lib.security.irdeto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.irdeto.securesdk.ISFException;
import com.irdeto.securesdk.SecureSDKManager;
import com.irdeto.securesdk.isf;
import com.xiaopeng.lib.http.FileUtils;
import com.xiaopeng.lib.http.ICallback;
import com.xiaopeng.lib.http.Security;
import com.xiaopeng.lib.http.systemdelegate.DelegateHelper;
import com.xiaopeng.lib.security.ISecurityModule;
import com.xiaopeng.lib.security.SecurityCommon;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.io.File;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class IrdetoSecurity implements ISecurityModule {
    private static final String ACTION_BROADCAST_ISF_SECURE_STORE_RELOAD = "com.xiaopeng.action.ISF_SECURE_STORE_RELOAD";
    private static final String IRSS_FLAG_FILE_NAME = "si_flag.dat";
    private static final int ISF_MGR_ROOTDETECTED = 10;
    private static final int ISF_MGR_SHARED_SECURE_STORE_INVALID = 14;
    private static final String MCU_FLAG_FILE_NAME = "si_mcu.dat";
    private static final String NLSS0_FILE_NAME = "irss0.dat";
    private static final String NLSS1_FILE_NAME = "irss1.dat";
    private static final String NLSS2_FILE_NAME = "irss2.dat";
    public static final String NLSS_FILE_PATH = "/private/sec/";
    private static final String TAG = "IrdetoSecurity";
    private static Context sContext;
    private static boolean sIsInitSuccess;
    private static SecureSDKManager sSecureSDKManager;
    private volatile String sInitErrorMsg;

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void setUidListener(ISecurityModule.UidListener uidListener) {
    }

    private IrdetoSecurity() {
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public Security.EncryptionType getEncryptionType() {
        return Security.EncryptionType.IRDETO;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public IrdetoSecurity init(Context context) throws Exception {
        initInternal(context, false);
        return this;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public IrdetoSecurity initForIndividual(Context context) throws Exception {
        initInternal(context, true);
        return this;
    }

    private void initInternal(Context context, boolean z) {
        sContext = context;
        if (z) {
            File file = new File(NLSS_FILE_PATH);
            if (!file.exists()) {
                file.mkdir();
            }
            checkSaveIndividualFlag();
        } else {
            registerISFSecureStoreReloadReceiver();
        }
        sIsInitSuccess = false;
        try {
            SecureSDKManager secureSDKManager = new SecureSDKManager();
            sSecureSDKManager = secureSDKManager;
            secureSDKManager.isfInitialize(context, NLSS_FILE_PATH);
            sIsInitSuccess = true;
            LogUtils.d(TAG, "irdeto sdk init success");
        } catch (ISFException e) {
            LogUtils.e(TAG, e);
            int errorCode = e.getErrorCode();
            if (errorCode != 10) {
                if (errorCode == 14) {
                    LogUtils.e(TAG, "irdeto sdk init failed : secure store error!!!!");
                    clearFlag();
                }
            } else if (BuildInfoUtils.isEngVersion()) {
                sIsInitSuccess = true;
                LogUtils.d(TAG, "irdeto sdk init success for eng root");
            }
            this.sInitErrorMsg = "irdeto init error for " + e.getMessage() + " error code is " + e.getErrorCode();
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void destroy() {
        SecureSDKManager secureSDKManager = sSecureSDKManager;
        if (secureSDKManager != null) {
            secureSDKManager.isfCleanup();
            sSecureSDKManager = null;
        }
        sIsInitSuccess = false;
    }

    private void checkSaveIndividualFlag() {
        if (SecurityCommon.getBuildInfoFlag().equals(getIndividualFlag())) {
            return;
        }
        LogUtils.e(TAG, "irdeto delete indiv files");
        clearFlag();
    }

    private void clearFlag() {
        try {
            FileUtils.deleteFile("/private/sec/irss0.dat");
            FileUtils.deleteFile("/private/sec/irss1.dat");
            FileUtils.deleteFile("/private/sec/irss2.dat");
            FileUtils.deleteFile("/private/sec/si_flag.dat");
            FileUtils.deleteFile("/private/sec/si_mcu.dat");
            File file = new File(sContext.getFilesDir().getPath() + MqttTopic.TOPIC_LEVEL_SEPARATOR + NLSS0_FILE_NAME);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void clearIndividualData() {
        try {
            FileUtils.deleteFile("/private/sec/irss2.dat");
            FileUtils.deleteFile("/private/sec/si_flag.dat");
            FileUtils.deleteFile("/private/sec/si_mcu.dat");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String getIndividualRequestStr() throws Exception {
        return isf.isfGetProvisionRequest();
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String generateIndividualDataForServer() throws Exception {
        return isf.isfGenerateOpaqueData();
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public boolean individualWithData(String str) throws Exception {
        isf.isfProvisionResponse(str);
        return isf.isfProvisioned();
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void saveIndividualFlag() {
        try {
            FileUtils.stringToFile("/private/sec/si_flag.dat", SecurityCommon.getBuildInfoFlag());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void saveMCUSecurityKey(boolean z) {
        if (z || !new File("/private/sec/si_mcu.dat").exists()) {
            try {
                FileUtils.stringToFile("/private/sec/si_mcu.dat", SecurityCommon.parseByte2HexStr(isf.isfGetMCUSecurityAsset()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void asyncGetMCUSecurityKey(final ICallback<byte[], String> iCallback) {
        LogUtils.v(TAG, "asyncGetMCUSecurityKey");
        new Thread(new Runnable() { // from class: com.xiaopeng.lib.security.irdeto.IrdetoSecurity.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    byte[] parseHexStr2Byte = SecurityCommon.parseHexStr2Byte(FileUtils.readTextFile(new File("/private/sec/si_mcu.dat"), 0, null));
                    if (parseHexStr2Byte == null && parseHexStr2Byte.length != 0) {
                        iCallback.onError("null mcu security key");
                    }
                    iCallback.onSuccess(parseHexStr2Byte);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.v(IrdetoSecurity.TAG, "null mcu security key");
                    iCallback.onError("null mcu security key");
                }
            }
        }).start();
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String encode(String str) {
        synchronized (IrdetoSecurity.class) {
            if (!sIsInitSuccess) {
                printErrorReason("encode error, ");
                return "";
            }
            try {
                String isfEncrypt = isf.isfEncrypt(SecurityCommon.base64UrlEncode(str.getBytes()));
                LogUtils.d(TAG, "irdeto encode result " + isfEncrypt);
                return isfEncrypt;
            } catch (Exception e) {
                LogUtils.e(TAG, e);
                return "";
            }
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String decode(String str) {
        synchronized (IrdetoSecurity.class) {
            if (!sIsInitSuccess) {
                printErrorReason("decode error, ");
                return "";
            }
            try {
                String isfDecrypt = isf.isfDecrypt(str);
                LogUtils.d(TAG, "irdeto decode result " + isfDecrypt);
                return isfDecrypt;
            } catch (Exception e) {
                LogUtils.e(TAG, e);
                return "";
            }
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public synchronized String getString(String str) {
        synchronized (IrdetoSecurity.class) {
            if (sIsInitSuccess) {
                try {
                    return new String(isf.isfStoreGet(str));
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            }
            return "";
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public synchronized void setString(String str, String str2) {
        synchronized (IrdetoSecurity.class) {
            if (sIsInitSuccess) {
                try {
                    isf.isfStorePut(str, str2.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public synchronized void deleteString(String str) {
        synchronized (IrdetoSecurity.class) {
            if (sIsInitSuccess) {
                try {
                    isf.isfStoreDelete(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public String buildTokenData(String[] strArr, byte[] bArr) {
        if (!SecurityCommon.checkSystemUid(sContext)) {
            return DelegateHelper.callBuildTokenDataThroughSystemDelegate(sContext, strArr, bArr);
        }
        synchronized (IrdetoSecurity.class) {
            if (!sIsInitSuccess) {
                printErrorReason("build token data error, ");
                return null;
            }
            try {
                return isf.isfTokenOperate(strArr, SecurityCommon.base64UrlEncode(bArr).getBytes());
            } catch (Exception e) {
                LogUtils.e(TAG, e);
                return null;
            }
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
    public String getIndividualFlag() {
        try {
            return FileUtils.readTextFile(new File("/private/sec/si_flag.dat"), 1, null);
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public boolean isInitAndIndivSuccess() {
        synchronized (IrdetoSecurity.class) {
            if (!sIsInitSuccess) {
                LogUtils.d(TAG, "Irdeto sdk not init " + this.sInitErrorMsg);
                return false;
            }
            try {
                if (isf.isfProvisioned()) {
                    if (SecurityCommon.getBuildInfoFlag().equals(getIndividualFlag())) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public boolean isInitSuccess() {
        return sIsInitSuccess;
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public synchronized void asyncSaveToken(final String[] strArr, final String[] strArr2, final Runnable runnable) {
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.security.irdeto.IrdetoSecurity.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    IrdetoSecurity.saveToken(strArr, strArr2);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                runnable.run();
            }
        });
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public synchronized void asyncSaveTokenWithListener(final String[] strArr, final String[] strArr2, final ISecurityModule.ResultListener resultListener) {
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.security.irdeto.IrdetoSecurity.3
            @Override // java.lang.Runnable
            public void run() {
                String str;
                ISecurityModule.ResultListener.RESULT result = ISecurityModule.ResultListener.RESULT.FAIL;
                try {
                    IrdetoSecurity.saveToken(strArr, strArr2);
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
    public void asyncSaveToken(String str, String[] strArr, String[] strArr2, Runnable runnable) {
        asyncSaveToken(strArr, strArr2, runnable);
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public void asyncSaveTokenWithListener(String str, String[] strArr, String[] strArr2, ISecurityModule.ResultListener resultListener) {
        asyncSaveTokenWithListener(strArr, strArr2, resultListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void saveToken(String[] strArr, String[] strArr2) throws Exception {
        synchronized (IrdetoSecurity.class) {
            if (sIsInitSuccess) {
                try {
                    isf.isfTokenSave(strArr, strArr2);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    @Override // com.xiaopeng.lib.security.ISecurityModule
    public byte[] cryptoDecodeInByteArray(String str, String str2, byte[] bArr) {
        synchronized (IrdetoSecurity.class) {
            if (sIsInitSuccess) {
                try {
                    return isf.isfCryptoOperate("IR_CRYPTO_AES_CBC_DECRYPT", str, str2, bArr);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        }
    }

    private static void registerISFSecureStoreReloadReceiver() {
        LogUtils.d(TAG, "registerISFSecureStoreReloadReceiver");
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_BROADCAST_ISF_SECURE_STORE_RELOAD);
            sContext.registerReceiver(new BroadcastReceiver() { // from class: com.xiaopeng.lib.security.irdeto.IrdetoSecurity.4
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (IrdetoSecurity.ACTION_BROADCAST_ISF_SECURE_STORE_RELOAD.equals(intent.getAction())) {
                        try {
                            LogUtils.d(IrdetoSecurity.TAG, "registerISFSecureStoreReloadReceiver ACTION_BROADCAST_ISF_SECURE_STORE_RELOAD");
                            isf.isfSecureStoreReload();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class Holder {
        private static final IrdetoSecurity INSTANCE = new IrdetoSecurity();

        private Holder() {
        }
    }

    public static IrdetoSecurity getInstance() {
        return Holder.INSTANCE;
    }

    private void printErrorReason(String str) {
        LogUtils.d(TAG, str + this.sInitErrorMsg);
    }
}
