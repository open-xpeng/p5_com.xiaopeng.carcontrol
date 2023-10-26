package com.lzy.okgo.utils;

import android.text.TextUtils;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.base.Request;
import java.util.Locale;
import java.util.StringTokenizer;
import okhttp3.Headers;

/* loaded from: classes.dex */
public class HeaderParser {
    public static <T> CacheEntity<T> createCacheEntity(Headers headers, T t, CacheMode cacheMode, String str) {
        long currentTimeMillis;
        long j;
        if (cacheMode == CacheMode.DEFAULT) {
            long date = HttpHeaders.getDate(headers.get("Date"));
            currentTimeMillis = HttpHeaders.getExpiration(headers.get("Expires"));
            String cacheControl = HttpHeaders.getCacheControl(headers.get("Cache-Control"), headers.get(HttpHeaders.HEAD_KEY_PRAGMA));
            if (TextUtils.isEmpty(cacheControl) && currentTimeMillis <= 0) {
                return null;
            }
            if (TextUtils.isEmpty(cacheControl)) {
                j = 0;
            } else {
                StringTokenizer stringTokenizer = new StringTokenizer(cacheControl, ",");
                j = 0;
                while (stringTokenizer.hasMoreTokens()) {
                    String lowerCase = stringTokenizer.nextToken().trim().toLowerCase(Locale.getDefault());
                    if (lowerCase.equals("no-cache") || lowerCase.equals("no-store")) {
                        return null;
                    }
                    if (lowerCase.startsWith("max-age=")) {
                        try {
                            j = Long.parseLong(lowerCase.substring(8));
                            if (j <= 0) {
                                return null;
                            }
                        } catch (Exception e) {
                            OkLogger.printStackTrace(e);
                        }
                    }
                }
            }
            long currentTimeMillis2 = System.currentTimeMillis();
            if (date <= 0) {
                date = currentTimeMillis2;
            }
            if (j > 0) {
                currentTimeMillis = date + (j * 1000);
            } else if (currentTimeMillis < 0) {
                currentTimeMillis = 0;
            }
        } else {
            currentTimeMillis = System.currentTimeMillis();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        for (String str2 : headers.names()) {
            httpHeaders.put(str2, headers.get(str2));
        }
        CacheEntity<T> cacheEntity = new CacheEntity<>();
        cacheEntity.setKey(str);
        cacheEntity.setData(t);
        cacheEntity.setLocalExpire(currentTimeMillis);
        cacheEntity.setResponseHeaders(httpHeaders);
        return cacheEntity;
    }

    public static <T> void addCacheHeaders(Request request, CacheEntity<T> cacheEntity, CacheMode cacheMode) {
        HttpHeaders responseHeaders;
        if (cacheEntity == null || cacheMode != CacheMode.DEFAULT || (responseHeaders = cacheEntity.getResponseHeaders()) == null) {
            return;
        }
        String str = responseHeaders.get("ETag");
        if (str != null) {
            request.headers("If-None-Match", str);
        }
        long lastModified = HttpHeaders.getLastModified(responseHeaders.get("Last-Modified"));
        if (lastModified > 0) {
            request.headers("If-Modified-Since", HttpHeaders.formatMillisToGMT(lastModified));
        }
    }
}
