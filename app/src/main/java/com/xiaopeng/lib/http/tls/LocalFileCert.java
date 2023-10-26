package com.xiaopeng.lib.http.tls;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaopeng.lib.InitEventHolder;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.systemdelegate.ISystemDelegate;
import com.xiaopeng.lib.http.FileUtils;
import com.xiaopeng.lib.http.tls.SSLHelper;
import com.xiaopeng.lib.security.SecurityCommon;
import com.xiaopeng.lib.security.xmartv1.XmartV1Constants;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.system.delegate.module.SystemDelegateModuleEntry;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes2.dex */
public class LocalFileCert {
    public static final String CERT_PATH = "/private/sec/xp0109.png";
    private static final String KEY_STORE_PASSWORD = "chengzi";
    private static final String KEY_STORE_TRUST_PASSWORD = "chengzi";
    private static final String KEY_STORE_TRUST_PATH = "index_kstp.html";
    private static final String KEY_STORE_TYPE_BKS = "bks";
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";
    private static final String TAG = "LocalFileCert";
    private static KeyManager[] sKeyManagers;
    private static TrustManager[] sTrustManagers;

    private static synchronized TrustManager[] getTrustManagers(Context context) {
        TrustManager[] trustManagerArr;
        synchronized (LocalFileCert.class) {
            trustManagerArr = sTrustManagers;
            if (trustManagerArr == null) {
                try {
                    KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);
                    InputStream open = context.getResources().getAssets().open(KEY_STORE_TRUST_PATH);
                    try {
                        try {
                            keyStore.load(open, "chengzi".toCharArray());
                        } catch (Exception e) {
                            InitEventHolder.get().onInitException(6005, e.getLocalizedMessage());
                            e.printStackTrace();
                        }
                        try {
                            open.close();
                        } catch (Exception unused) {
                            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
                            trustManagerFactory.init(keyStore);
                            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                            sTrustManagers = trustManagers;
                            return trustManagers;
                        }
                    } catch (Throwable th) {
                        try {
                            open.close();
                        } catch (Exception unused2) {
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    InitEventHolder.get().onInitException(6005, e2.getLocalizedMessage());
                    e2.printStackTrace();
                    return null;
                }
            }
        }
        return trustManagerArr;
    }

    private static synchronized KeyManager[] getKeyManagers(Context context) {
        KeyManager[] keyManagerArr;
        String certificate;
        synchronized (LocalFileCert.class) {
            keyManagerArr = sKeyManagers;
            if (keyManagerArr == null) {
                try {
                    if (SecurityCommon.checkSystemUid(context)) {
                        LogUtils.i(TAG, "get cert content from file");
                        certificate = FileUtils.readTextFile(new File(CERT_PATH), 0, null);
                    } else {
                        LogUtils.i(TAG, "get cert content from SystemDelegate");
                        certificate = ((ISystemDelegate) Module.get(SystemDelegateModuleEntry.class).get(ISystemDelegate.class)).getCertificate();
                    }
                    if (!TextUtils.isEmpty(certificate)) {
                        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(certificate, 0));
                        try {
                            try {
                                keyStore.load(byteArrayInputStream, "chengzi".toCharArray());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                byteArrayInputStream.close();
                            } catch (Exception unused) {
                                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
                                keyManagerFactory.init(keyStore, "chengzi".toCharArray());
                                KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
                                sKeyManagers = keyManagers;
                                return keyManagers;
                            }
                        } catch (Throwable th) {
                            try {
                                byteArrayInputStream.close();
                            } catch (Exception unused2) {
                            }
                            throw th;
                        }
                    } else {
                        throw new RuntimeException("certContent can't be empty");
                    }
                } catch (Exception e2) {
                    LogUtils.w(TAG, "getKeyManagers error!", e2);
                    return null;
                }
            }
        }
        return keyManagerArr;
    }

    public static SSLSocketFactory getTLS2SocketFactory(Context context) {
        try {
            SSLContext sSLContext = SSLContext.getInstance(XmartV1Constants.TLS_REVISION_1_2);
            sSLContext.init(getKeyManagers(context), getTrustManagers(context), new SecureRandom());
            return new SSLHelper.TLS2SocketFactory(sSLContext.getSocketFactory());
        } catch (Exception e) {
            InitEventHolder.get().onInitException(6006, e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static X509TrustManager getX509TrustManager(Context context) {
        try {
            TrustManager[] trustManagers = getTrustManagers(context);
            if (trustManagers != null) {
                for (TrustManager trustManager : trustManagers) {
                    if (trustManager instanceof X509TrustManager) {
                        return (X509TrustManager) trustManager;
                    }
                }
                return null;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
