package com.lzy.okgo.exception;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.utils.HttpUtils;

/* loaded from: classes.dex */
public class HttpException extends RuntimeException {
    private static final long serialVersionUID = 8773734741709178425L;
    private int code;
    private String message;
    private transient Response<?> response;

    public HttpException(String str) {
        super(str);
    }

    public HttpException(Response<?> response) {
        super(getMessage(response));
        this.code = response.code();
        this.message = response.message();
        this.response = response;
    }

    private static String getMessage(Response<?> response) {
        HttpUtils.checkNotNull(response, "response == null");
        return "HTTP " + response.code() + " " + response.message();
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public Response<?> response() {
        return this.response;
    }

    public static HttpException NET_ERROR() {
        return new HttpException("network error! http response code is 404 or 5xx!");
    }

    public static HttpException COMMON(String str) {
        return new HttpException(str);
    }
}
