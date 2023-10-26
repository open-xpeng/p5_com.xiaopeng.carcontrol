package com.airbnb.lottie.network;

import android.content.Context;
import androidx.core.util.Pair;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieResult;
import com.airbnb.lottie.utils.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public class NetworkFetcher {
    private final Context appContext;
    private final NetworkCache networkCache;
    private final String url;

    public static LottieResult<LottieComposition> fetchSync(Context context, String str, String str2) {
        return new NetworkFetcher(context, str, str2).fetchSync();
    }

    private NetworkFetcher(Context context, String str, String str2) {
        Context applicationContext = context.getApplicationContext();
        this.appContext = applicationContext;
        this.url = str;
        if (str2 == null) {
            this.networkCache = null;
        } else {
            this.networkCache = new NetworkCache(applicationContext);
        }
    }

    public LottieResult<LottieComposition> fetchSync() {
        LottieComposition fetchFromCache = fetchFromCache();
        if (fetchFromCache != null) {
            return new LottieResult<>(fetchFromCache);
        }
        Logger.debug("Animation for " + this.url + " not found in cache. Fetching from network.");
        return fetchFromNetwork();
    }

    private LottieComposition fetchFromCache() {
        Pair<FileExtension, InputStream> fetch;
        LottieResult<LottieComposition> fromJsonInputStreamSync;
        NetworkCache networkCache = this.networkCache;
        if (networkCache == null || (fetch = networkCache.fetch(this.url)) == null) {
            return null;
        }
        FileExtension fileExtension = fetch.first;
        InputStream inputStream = fetch.second;
        if (fileExtension == FileExtension.ZIP) {
            fromJsonInputStreamSync = LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(inputStream), this.url);
        } else {
            fromJsonInputStreamSync = LottieCompositionFactory.fromJsonInputStreamSync(inputStream, this.url);
        }
        if (fromJsonInputStreamSync.getValue() != null) {
            return fromJsonInputStreamSync.getValue();
        }
        return null;
    }

    private LottieResult<LottieComposition> fetchFromNetwork() {
        try {
            return fetchFromNetworkInternal();
        } catch (IOException e) {
            return new LottieResult<>(e);
        }
    }

    private LottieResult<LottieComposition> fetchFromNetworkInternal() throws IOException {
        Logger.debug("Fetching " + this.url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
        httpURLConnection.setRequestMethod("GET");
        try {
            httpURLConnection.connect();
            if (httpURLConnection.getErrorStream() == null && httpURLConnection.getResponseCode() == 200) {
                LottieResult<LottieComposition> resultFromConnection = getResultFromConnection(httpURLConnection);
                Logger.debug("Completed fetch from network. Success: " + (resultFromConnection.getValue() != null));
                return resultFromConnection;
            }
            return new LottieResult<>(new IllegalArgumentException("Unable to fetch " + this.url + ". Failed with " + httpURLConnection.getResponseCode() + "\n" + getErrorFromConnection(httpURLConnection)));
        } catch (Exception e) {
            return new LottieResult<>(e);
        } finally {
            httpURLConnection.disconnect();
        }
    }

    private String getErrorFromConnection(HttpURLConnection httpURLConnection) throws IOException {
        httpURLConnection.getResponseCode();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        sb.append(readLine).append('\n');
                    } else {
                        try {
                            break;
                        } catch (Exception unused) {
                        }
                    }
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Exception unused2) {
                    }
                    throw th;
                }
            } catch (Exception e) {
                throw e;
            }
        }
        bufferedReader.close();
        return sb.toString();
    }

    private LottieResult<LottieComposition> getResultFromConnection(HttpURLConnection httpURLConnection) throws IOException {
        FileExtension fileExtension;
        LottieResult<LottieComposition> fromJsonInputStreamSync;
        String contentType = httpURLConnection.getContentType();
        if (contentType == null) {
            contentType = "application/json";
        }
        if (contentType.contains("application/zip")) {
            Logger.debug("Handling zip response.");
            fileExtension = FileExtension.ZIP;
            NetworkCache networkCache = this.networkCache;
            if (networkCache == null) {
                fromJsonInputStreamSync = LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(httpURLConnection.getInputStream()), null);
            } else {
                fromJsonInputStreamSync = LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(new FileInputStream(networkCache.writeTempCacheFile(this.url, httpURLConnection.getInputStream(), fileExtension))), this.url);
            }
        } else {
            Logger.debug("Received json response.");
            fileExtension = FileExtension.JSON;
            NetworkCache networkCache2 = this.networkCache;
            if (networkCache2 == null) {
                fromJsonInputStreamSync = LottieCompositionFactory.fromJsonInputStreamSync(httpURLConnection.getInputStream(), null);
            } else {
                fromJsonInputStreamSync = LottieCompositionFactory.fromJsonInputStreamSync(new FileInputStream(new File(networkCache2.writeTempCacheFile(this.url, httpURLConnection.getInputStream(), fileExtension).getAbsolutePath())), this.url);
            }
        }
        if (this.networkCache != null && fromJsonInputStreamSync.getValue() != null) {
            this.networkCache.renameTempFile(this.url, fileExtension);
        }
        return fromJsonInputStreamSync;
    }
}
