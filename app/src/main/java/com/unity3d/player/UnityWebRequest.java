package com.unity3d.player;

import com.unity3d.player.b;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes.dex */
class UnityWebRequest implements Runnable {
    private static final HostnameVerifier k;
    private long a;
    private String b;
    private String c;
    private Map d;
    private boolean e;
    private int f;
    private long g;
    private long h;
    private boolean i;
    private boolean j;

    static {
        if (CookieHandler.getDefault() == null) {
            CookieHandler.setDefault(new CookieManager());
        }
        k = new HostnameVerifier() { // from class: com.unity3d.player.UnityWebRequest.1
            @Override // javax.net.ssl.HostnameVerifier
            public final boolean verify(String str, SSLSession sSLSession) {
                return true;
            }
        };
    }

    UnityWebRequest(long j, String str, Map map, String str2, boolean z, int i) {
        this.a = j;
        this.b = str2;
        this.c = str;
        this.d = map;
        this.e = z;
        this.f = i;
    }

    private static native void contentLengthCallback(long j, int i);

    private static native boolean downloadCallback(long j, ByteBuffer byteBuffer, int i);

    private static native void errorCallback(long j, int i, String str);

    private boolean hasTimedOut() {
        return this.f > 0 && System.currentTimeMillis() - this.g >= ((long) this.f);
    }

    private static native void headerCallback(long j, String str, String str2);

    private static native void responseCodeCallback(long j, int i);

    private void runSafe() {
        b.AbstractC0020b abstractC0020b;
        this.g = System.currentTimeMillis();
        try {
            URL url = new URL(this.b);
            URLConnection openConnection = url.openConnection();
            openConnection.setConnectTimeout(this.f);
            openConnection.setReadTimeout(this.f);
            InputStream inputStream = null;
            if (openConnection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) openConnection;
                if (this.e) {
                    abstractC0020b = new b.AbstractC0020b() { // from class: com.unity3d.player.UnityWebRequest.2
                        @Override // com.unity3d.player.b.AbstractC0020b, javax.net.ssl.X509TrustManager
                        public final void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
                            if (!UnityWebRequest.this.validateCertificateCallback((x509CertificateArr == null || x509CertificateArr.length <= 0) ? new byte[0] : x509CertificateArr[0].getEncoded())) {
                                throw new CertificateException();
                            }
                        }
                    };
                    httpsURLConnection.setHostnameVerifier(k);
                } else {
                    abstractC0020b = null;
                }
                SSLSocketFactory a = b.a(abstractC0020b);
                if (a != null) {
                    httpsURLConnection.setSSLSocketFactory(a);
                }
            }
            if (url.getProtocol().equalsIgnoreCase("file") && !url.getHost().isEmpty()) {
                malformattedUrlCallback("file:// must use an absolute path");
                return;
            }
            boolean z = openConnection instanceof HttpURLConnection;
            if (z) {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
                    httpURLConnection.setRequestMethod(this.c);
                    httpURLConnection.setInstanceFollowRedirects(false);
                    long j = this.h;
                    if (j > 0) {
                        if (this.j) {
                            httpURLConnection.setChunkedStreamingMode(0);
                        } else {
                            httpURLConnection.setFixedLengthStreamingMode((int) j);
                        }
                        if (this.i) {
                            httpURLConnection.addRequestProperty("Expect", "100-continue");
                        }
                    }
                } catch (ProtocolException e) {
                    badProtocolCallback(e.toString());
                    return;
                }
            }
            Map map = this.d;
            if (map != null) {
                for (Map.Entry entry : map.entrySet()) {
                    openConnection.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                }
            }
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(131072);
            if (uploadCallback(null) > 0) {
                openConnection.setDoOutput(true);
                try {
                    OutputStream outputStream = openConnection.getOutputStream();
                    while (true) {
                        int uploadCallback = uploadCallback(allocateDirect);
                        if (uploadCallback <= 0) {
                            break;
                        } else if (hasTimedOut()) {
                            outputStream.close();
                            errorCallback(this.a, 14, "WebRequest timed out.");
                            return;
                        } else {
                            outputStream.write(allocateDirect.array(), allocateDirect.arrayOffset(), uploadCallback);
                        }
                    }
                } catch (Exception e2) {
                    errorCallback(e2.toString());
                    return;
                }
            }
            if (z) {
                try {
                    responseCodeCallback(((HttpURLConnection) openConnection).getResponseCode());
                } catch (SocketTimeoutException e3) {
                    errorCallback(this.a, 14, e3.toString());
                    return;
                } catch (UnknownHostException e4) {
                    unknownHostCallback(e4.toString());
                    return;
                } catch (SSLException e5) {
                    sslCannotConnectCallback(e5);
                    return;
                } catch (IOException e6) {
                    errorCallback(e6.toString());
                    return;
                }
            }
            Map<String, List<String>> headerFields = openConnection.getHeaderFields();
            headerCallback(headerFields);
            if ((headerFields == null || !headerFields.containsKey("content-length")) && openConnection.getContentLength() != -1) {
                headerCallback("content-length", String.valueOf(openConnection.getContentLength()));
            }
            if ((headerFields == null || !headerFields.containsKey("content-type")) && openConnection.getContentType() != null) {
                headerCallback("content-type", openConnection.getContentType());
            }
            contentLengthCallback(openConnection.getContentLength());
            try {
                if (openConnection instanceof HttpURLConnection) {
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) openConnection;
                    responseCodeCallback(httpURLConnection2.getResponseCode());
                    inputStream = httpURLConnection2.getErrorStream();
                }
                if (inputStream == null) {
                    inputStream = openConnection.getInputStream();
                }
                ReadableByteChannel newChannel = Channels.newChannel(inputStream);
                while (true) {
                    int read = newChannel.read(allocateDirect);
                    if (read == -1) {
                        break;
                    } else if (hasTimedOut()) {
                        newChannel.close();
                        errorCallback(this.a, 14, "WebRequest timed out.");
                        return;
                    } else {
                        if (!downloadCallback(allocateDirect, read)) {
                            break;
                        }
                        allocateDirect.clear();
                    }
                }
                newChannel.close();
            } catch (SocketTimeoutException e7) {
                errorCallback(this.a, 14, e7.toString());
            } catch (UnknownHostException e8) {
                unknownHostCallback(e8.toString());
            } catch (SSLException e9) {
                sslCannotConnectCallback(e9);
            } catch (IOException e10) {
                errorCallback(this.a, 14, e10.toString());
            } catch (Exception e11) {
                errorCallback(e11.toString());
            }
        } catch (MalformedURLException e12) {
            malformattedUrlCallback(e12.toString());
        } catch (IOException e13) {
            errorCallback(e13.toString());
        }
    }

    private static native int uploadCallback(long j, ByteBuffer byteBuffer);

    private static native boolean validateCertificateCallback(long j, byte[] bArr);

    protected void badProtocolCallback(String str) {
        errorCallback(this.a, 4, str);
    }

    protected void contentLengthCallback(int i) {
        contentLengthCallback(this.a, i);
    }

    protected boolean downloadCallback(ByteBuffer byteBuffer, int i) {
        return downloadCallback(this.a, byteBuffer, i);
    }

    protected void errorCallback(String str) {
        errorCallback(this.a, 2, str);
    }

    protected void headerCallback(String str, String str2) {
        headerCallback(this.a, str, str2);
    }

    protected void headerCallback(Map map) {
        if (map == null || map.size() == 0) {
            return;
        }
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            if (str == null) {
                str = "Status";
            }
            for (String str2 : (List) entry.getValue()) {
                headerCallback(str, str2);
            }
        }
    }

    protected void malformattedUrlCallback(String str) {
        errorCallback(this.a, 5, str);
    }

    protected void responseCodeCallback(int i) {
        responseCodeCallback(this.a, i);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            runSafe();
        } catch (Exception e) {
            errorCallback(e.toString());
        }
    }

    void setupTransferSettings(long j, boolean z, boolean z2) {
        this.h = j;
        this.i = z;
        this.j = z2;
    }

    protected void sslCannotConnectCallback(SSLException sSLException) {
        int i;
        String sSLException2 = sSLException.toString();
        SSLException sSLException3 = sSLException;
        while (true) {
            if (sSLException3 == null) {
                i = 16;
                break;
            } else if (sSLException3 instanceof SSLKeyException) {
                i = 23;
                break;
            } else if ((sSLException3 instanceof SSLPeerUnverifiedException) || (sSLException3 instanceof CertPathValidatorException)) {
                break;
            } else {
                sSLException3 = sSLException3.getCause();
            }
        }
        i = 25;
        errorCallback(this.a, i, sSLException2);
    }

    protected void unknownHostCallback(String str) {
        errorCallback(this.a, 7, str);
    }

    protected int uploadCallback(ByteBuffer byteBuffer) {
        return uploadCallback(this.a, byteBuffer);
    }

    protected boolean validateCertificateCallback(byte[] bArr) {
        return validateCertificateCallback(this.a, bArr);
    }
}
