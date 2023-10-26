package com.xiaopeng.lib.http.logging;

import com.lzy.okgo.utils.IOUtils;
import com.xiaopeng.lib.http.logging.LogCacher;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

/* loaded from: classes2.dex */
public class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private java.util.logging.Level colorLevel;
    private final String tag;
    private volatile Level printLevel = Level.NONE;
    private LogCacher cacher = new LogCacher();

    /* loaded from: classes2.dex */
    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public HttpLoggingInterceptor(String str) {
        this.tag = str;
    }

    public void setPrintLevel(Level level) {
        Objects.requireNonNull(this.printLevel, "printLevel == null. Use Level.NONE instead.");
        this.printLevel = level;
    }

    public void setColorLevel(java.util.logging.Level level) {
        this.colorLevel = level;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        if (this.printLevel == Level.NONE) {
            return chain.proceed(request);
        }
        LogCacher.ILog logger = this.cacher.getLogger();
        logForRequest(request, chain.connection(), logger);
        long nanoTime = System.nanoTime();
        try {
            Response logForResponse = logForResponse(chain.proceed(request), TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime), logger);
            logger.end();
            return logForResponse;
        } catch (Exception e) {
            logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
    }

    private void logForRequest(Request request, Connection connection, LogCacher.ILog iLog) throws IOException {
        StringBuilder sb;
        boolean z = this.printLevel == Level.BODY;
        boolean z2 = this.printLevel == Level.BODY || this.printLevel == Level.HEADERS;
        RequestBody body = request.body();
        boolean z3 = body != null;
        try {
            try {
                iLog.log("--> " + request.method() + ' ' + request.url() + ' ' + (connection != null ? connection.protocol() : Protocol.HTTP_1_1));
                if (z2) {
                    if (z3) {
                        if (body.contentType() != null) {
                            iLog.log("\tContent-Type: " + body.contentType());
                        }
                        if (body.contentLength() != -1) {
                            iLog.log("\tContent-Length: " + body.contentLength());
                        }
                    }
                    Headers headers = request.headers();
                    int size = headers.size();
                    for (int i = 0; i < size; i++) {
                        String name = headers.name(i);
                        if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                            iLog.log("\t" + name + ": " + headers.value(i));
                        }
                    }
                    iLog.log(" ");
                    if (z && z3) {
                        if (isPlaintext(body.contentType())) {
                            bodyToString(request, iLog);
                        } else {
                            iLog.log("\tbody: maybe [binary body], omitted!");
                        }
                    }
                }
                sb = new StringBuilder();
            } catch (Exception e) {
                LogUtils.w(this.tag, "logForRequest error!", e);
                sb = new StringBuilder();
            }
            iLog.log(sb.append("--> END ").append(request.method()).toString());
        } catch (Throwable th) {
            iLog.log("--> END " + request.method());
            throw th;
        }
    }

    private Response logForResponse(Response response, long j, LogCacher.ILog iLog) {
        Headers headers;
        Response build = response.newBuilder().build();
        ResponseBody body = build.body();
        boolean z = true;
        boolean z2 = this.printLevel == Level.BODY;
        if (this.printLevel != Level.BODY && this.printLevel != Level.HEADERS) {
            z = false;
        }
        try {
            try {
                int code = build.code();
                iLog.log("<-- " + code + ' ' + build.message() + ' ' + build.request().url() + " (" + j + "ms）");
                iLog.code(code);
                if (z) {
                    int size = build.headers().size();
                    for (int i = 0; i < size; i++) {
                        iLog.log("\t" + headers.name(i) + ": " + headers.value(i));
                    }
                    iLog.log(" ");
                    if (z2 && HttpHeaders.hasBody(build)) {
                        if (body == null) {
                            return response;
                        }
                        if (isPlaintext(body.contentType())) {
                            byte[] byteArray = IOUtils.toByteArray(body.byteStream());
                            String str = new String(byteArray, getCharset(body.contentType()));
                            iLog.log("\tbody:" + str);
                            if (str.contains("\"code\":")) {
                                if (!str.contains("\"code\":0,") && !str.contains("\"code\":0}") && !str.contains("\"code\":200,") && !str.contains("\"code\":200}")) {
                                    if (code <= 299) {
                                        code = -1;
                                    }
                                    iLog.code(code);
                                }
                                code = 200;
                                iLog.code(code);
                            }
                            return response.newBuilder().body(ResponseBody.create(body.contentType(), byteArray)).build();
                        }
                        iLog.log("\tbody: maybe [binary body], omitted!");
                    }
                }
            } catch (Exception e) {
                LogUtils.w(this.tag, "logForResponse error!", e);
            }
            return response;
        } finally {
            iLog.log("<-- END HTTP");
        }
    }

    private static Charset getCharset(MediaType mediaType) {
        Charset charset = mediaType != null ? mediaType.charset(UTF8) : UTF8;
        return charset == null ? UTF8 : charset;
    }

    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        if (mediaType.type() == null || !mediaType.type().equals("text")) {
            String subtype = mediaType.subtype();
            if (subtype != null) {
                String lowerCase = subtype.toLowerCase();
                if (lowerCase.contains("x-www-form-urlencoded") || lowerCase.contains("json") || lowerCase.contains("xml") || lowerCase.contains("html")) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private void bodyToString(Request request, LogCacher.ILog iLog) {
        try {
            RequestBody body = request.newBuilder().build().body();
            if (body == null) {
                return;
            }
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            iLog.log("\tbody:" + buffer.readString(getCharset(body.contentType())));
        } catch (Exception e) {
            LogUtils.w(this.tag, "bodyToString error!", e);
        }
    }
}
