package android.support.rastermill.data;

import android.net.Uri;
import android.text.TextUtils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/* loaded from: classes.dex */
public class RequestUrl {
    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    private final Headers headers;
    private String safeStringUrl;
    private URL safeUrl;
    private final String stringUrl;
    private final URL url;

    public RequestUrl(URL url) {
        this(url, Headers.DEFAULT);
    }

    public RequestUrl(String str) {
        this(str, Headers.DEFAULT);
    }

    public RequestUrl(URL url, Headers headers) {
        if (url == null) {
            throw new IllegalArgumentException("URL must not be null!");
        }
        if (headers == null) {
            throw new IllegalArgumentException("Headers must not be null");
        }
        this.url = url;
        this.stringUrl = null;
        this.headers = headers;
    }

    public RequestUrl(String str, Headers headers) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("String url must not be empty or null: " + str);
        }
        if (headers == null) {
            throw new IllegalArgumentException("Headers must not be null");
        }
        this.stringUrl = str;
        this.url = null;
        this.headers = headers;
    }

    public URL toURL() throws MalformedURLException {
        return getSafeUrl();
    }

    private URL getSafeUrl() throws MalformedURLException {
        if (this.safeUrl == null) {
            this.safeUrl = new URL(getSafeStringUrl());
        }
        return this.safeUrl;
    }

    public String toStringUrl() {
        return getSafeStringUrl();
    }

    private String getSafeStringUrl() {
        if (TextUtils.isEmpty(this.safeStringUrl)) {
            String str = this.stringUrl;
            if (TextUtils.isEmpty(str)) {
                str = this.url.toString();
            }
            this.safeStringUrl = Uri.encode(str, ALLOWED_URI_CHARS);
        }
        return this.safeStringUrl;
    }

    public Map<String, String> getHeaders() {
        return this.headers.getHeaders();
    }

    public String getCacheKey() {
        String str = this.stringUrl;
        return str != null ? str : this.url.toString();
    }

    public String toString() {
        return getCacheKey() + '\n' + this.headers.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof RequestUrl) {
            RequestUrl requestUrl = (RequestUrl) obj;
            return getCacheKey().equals(requestUrl.getCacheKey()) && this.headers.equals(requestUrl.headers);
        }
        return false;
    }

    public int hashCode() {
        return (getCacheKey().hashCode() * 31) + this.headers.hashCode();
    }
}
