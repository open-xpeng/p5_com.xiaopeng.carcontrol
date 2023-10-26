package com.xiaopeng.lib.http.tls;

import android.os.Build;
import android.util.Log;
import com.xiaopeng.lib.InitEventHolder;
import com.xiaopeng.lib.apirouter.ClientConstants;
import com.xiaopeng.lib.http.tls.SSLHelper;
import com.xiaopeng.lib.security.xmartv1.XmartV1Constants;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes2.dex */
public class KeyStoreCert {
    private static final String CA_CERT_PATH = "/system/etc/index_kstp.html";
    private static final String KEYSTORE_BKS = "BKS";
    private static final String KEY_STORE_CACERT_PASSWORD = "chengzi";
    private static final String TAG = "KeyStoreCert";

    public static SSLSocketFactory getTLS2SocketFactory() {
        CompositeX509TrustManager compositeX509TrustManager;
        try {
            SSLContext sSLContext = SSLContext.getInstance(XmartV1Constants.TLS_REVISION_1_2);
            KeyStore keyStore = KeyStore.getInstance(XmartV1Constants.KEY_STORE_TYPE);
            keyStore.load(null);
            if (Build.VERSION.SDK_INT > 28) {
                compositeX509TrustManager = new CompositeX509TrustManager();
            } else {
                compositeX509TrustManager = new CompositeX509TrustManager(keyStore);
            }
            if (!compositeX509TrustManager.isHasValidCert()) {
                InitEventHolder.get().onInitException(6003, "no valid ssl factory");
            }
            sSLContext.init(new KeyManager[]{new CompositeX509KeyManager(keyStore)}, new TrustManager[]{compositeX509TrustManager}, null);
            return new SSLHelper.TLS2SocketFactory(sSLContext.getSocketFactory());
        } catch (Exception e) {
            InitEventHolder.get().onInitException(6003, e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static X509TrustManager getX509TrustManager() {
        try {
            if (Build.VERSION.SDK_INT > 28) {
                CompositeX509TrustManager compositeX509TrustManager = new CompositeX509TrustManager();
                Log.i(TAG, "getX509TrustManager from  Android  Q ");
                return compositeX509TrustManager;
            }
            Log.i(TAG, "getX509TrustManager from  Android  P ");
            KeyStore keyStore = KeyStore.getInstance(XmartV1Constants.KEY_STORE_TYPE);
            keyStore.load(null);
            CompositeX509TrustManager compositeX509TrustManager2 = new CompositeX509TrustManager(keyStore);
            if (!compositeX509TrustManager2.isHasValidCert()) {
                InitEventHolder.get().onInitException(6004, "no valid trust manager");
            }
            return compositeX509TrustManager2;
        } catch (Exception e) {
            InitEventHolder.get().onInitException(6004, e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    /* loaded from: classes2.dex */
    private static class CompositeX509TrustManager implements X509TrustManager {
        private List<X509Certificate> certificates;
        private boolean hasValidCert;
        private KeyStore keyStore;
        private final List<X509TrustManager> trustManagers;

        public CompositeX509TrustManager(KeyStore keyStore) {
            this.certificates = null;
            this.hasValidCert = false;
            this.keyStore = null;
            Log.i(KeyStoreCert.TAG, "init Android P  CompositeX509TrustManager ");
            this.trustManagers = new ArrayList();
            this.keyStore = keyStore;
            try {
                KeyStore keyStore2 = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore2.load(null);
                String[] strArr = {"ca", "client", "server", ClientConstants.ALIAS.P_ALIAS};
                for (int i = 0; i < 4; i++) {
                    String str = strArr[i];
                    try {
                        Certificate certificate = keyStore.getCertificate(str);
                        if (certificate instanceof X509Certificate) {
                            keyStore2.setCertificateEntry(str, certificate);
                            this.hasValidCert = true;
                        }
                    } catch (KeyStoreException e) {
                        e.printStackTrace();
                    }
                }
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
                trustManagerFactory.init(keyStore2);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers != null) {
                    for (TrustManager trustManager : trustManagers) {
                        if (trustManager instanceof X509TrustManager) {
                            this.trustManagers.add((X509TrustManager) trustManager);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        public CompositeX509TrustManager() {
            this.certificates = null;
            this.hasValidCert = false;
            this.keyStore = null;
            Log.i(KeyStoreCert.TAG, "init Android Q  CompositeX509TrustManager ");
            this.trustManagers = new ArrayList();
            this.certificates = new ArrayList();
            try {
                KeyStore keyStore = KeyStore.getInstance(KeyStoreCert.KEYSTORE_BKS);
                FileInputStream fileInputStream = new FileInputStream(KeyStoreCert.CA_CERT_PATH);
                keyStore.load(fileInputStream, KeyStoreCert.KEY_STORE_CACERT_PASSWORD.toCharArray());
                fileInputStream.close();
                Enumeration<String> aliases = keyStore.aliases();
                while (aliases.hasMoreElements()) {
                    Certificate certificate = keyStore.getCertificate(aliases.nextElement());
                    if (certificate instanceof X509Certificate) {
                        this.certificates.add((X509Certificate) certificate);
                        this.hasValidCert = true;
                    }
                }
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
                trustManagerFactory.init(keyStore);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers != null) {
                    for (TrustManager trustManager : trustManagers) {
                        if (trustManager instanceof X509TrustManager) {
                            this.trustManagers.add((X509TrustManager) trustManager);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            for (X509TrustManager x509TrustManager : this.trustManagers) {
                try {
                    x509TrustManager.checkClientTrusted(x509CertificateArr, str);
                    return;
                } catch (CertificateException unused) {
                }
            }
            throw new CertificateException("None of the TrustManagers trust this certificate chain");
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            for (X509TrustManager x509TrustManager : this.trustManagers) {
                try {
                    x509TrustManager.checkServerTrusted(x509CertificateArr, str);
                    return;
                } catch (CertificateException unused) {
                }
            }
            throw new CertificateException("None of the TrustManagers trust this certificate chain");
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            if (Build.VERSION.SDK_INT > 28) {
                Log.i(KeyStoreCert.TAG, "getAcceptedIssuers from ANdroid Q ");
                return (X509Certificate[]) this.certificates.toArray(new X509Certificate[0]);
            }
            Log.i(KeyStoreCert.TAG, "getAcceptedIssuers from ANdroid P ");
            ArrayList arrayList = new ArrayList();
            String[] strArr = {"ca", "client", "server", ClientConstants.ALIAS.P_ALIAS};
            for (int i = 0; i < 4; i++) {
                try {
                    Certificate certificate = this.keyStore.getCertificate(strArr[i]);
                    if (certificate instanceof X509Certificate) {
                        arrayList.add((X509Certificate) certificate);
                    }
                } catch (NullPointerException | KeyStoreException e) {
                    e.printStackTrace();
                }
            }
            return (X509Certificate[]) arrayList.toArray(new X509Certificate[0]);
        }

        public boolean isHasValidCert() {
            return this.hasValidCert;
        }
    }

    /* loaded from: classes2.dex */
    private static class CompositeX509KeyManager implements X509KeyManager {
        private static final String ALIAS_client = "client";
        private static final String ALIAS_server = "server";
        private Certificate cert;
        private final KeyStore keyStore;
        private PrivateKey privateKey;

        @Override // javax.net.ssl.X509KeyManager
        public String chooseClientAlias(String[] strArr, Principal[] principalArr, Socket socket) {
            return ALIAS_client;
        }

        @Override // javax.net.ssl.X509KeyManager
        public String chooseServerAlias(String str, Principal[] principalArr, Socket socket) {
            return ALIAS_server;
        }

        public CompositeX509KeyManager(KeyStore keyStore) {
            this.keyStore = keyStore;
        }

        @Override // javax.net.ssl.X509KeyManager
        public String[] getClientAliases(String str, Principal[] principalArr) {
            return new String[]{ALIAS_client};
        }

        @Override // javax.net.ssl.X509KeyManager
        public String[] getServerAliases(String str, Principal[] principalArr) {
            return new String[]{ALIAS_server};
        }

        @Override // javax.net.ssl.X509KeyManager
        public X509Certificate[] getCertificateChain(String str) {
            Certificate certificate;
            try {
                certificate = this.keyStore.getCertificate(ALIAS_client);
            } catch (KeyStoreException e) {
                e.printStackTrace();
                certificate = null;
            }
            return new X509Certificate[]{(X509Certificate) certificate};
        }

        @Override // javax.net.ssl.X509KeyManager
        public PrivateKey getPrivateKey(String str) {
            try {
                return (PrivateKey) this.keyStore.getKey(ALIAS_client, null);
            } catch (KeyStoreException e) {
                e.printStackTrace();
                return null;
            } catch (NoSuchAlgorithmException e2) {
                e2.printStackTrace();
                return null;
            } catch (UnrecoverableKeyException e3) {
                e3.printStackTrace();
                return null;
            }
        }
    }
}
