package com.xiaopeng.xvs.xid.auth.api;

import android.app.Activity;
import android.os.Handler;
import com.xiaopeng.xvs.xid.base.ICallback;
import com.xiaopeng.xvs.xid.base.IException;
import com.xiaopeng.xvs.xid.base.IResponse;

/* loaded from: classes2.dex */
public interface IOAuth {
    public static final String AUTH_INFO_EXTRA_APP_ID = "app_id";
    public static final String AUTH_INFO_EXTRA_APP_SECRET = "app_secret";
    public static final String AUTH_TYPE_AUTH_CODE = "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_CODE";
    public static final String AUTH_TYPE_AUTH_OTP = "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_OTP";
    public static final String AUTH_TYPE_BIND_VE_DATA_AUTH = "com.xiaopeng.accountservice.AUTH_TYPE_BIND_VE_DATA_AUTH";
    public static final String AUTH_TYPE_CHECK_VE_DATA_AUTH = "com.xiaopeng.accountservice.AUTH_TYPE_CHECK_VE_DATA_AUTH";
    public static final String AUTH_TYPE_DELETE_VE_DATA_AUTH_RECORD = "com.xiaopeng.accountservice.AUTH_TYPE_DELETE_VE_DATA_AUTH_RECORD";

    IResponse requestCarBind(Activity activity);

    void requestCarBind(Activity activity, ICallback<String, IException> iCallback);

    void requestCarBind(Activity activity, ICallback<String, IException> iCallback, Handler handler);

    IResponse requestOAuth(Activity activity);

    void requestOAuth(Activity activity, ICallback<String, IException> iCallback);

    void requestOAuth(Activity activity, ICallback<String, IException> iCallback, Handler handler);

    IResponse requestOTP();

    void requestOTP(ICallback<String, IException> iCallback);

    void requestOTP(ICallback<String, IException> iCallback, Handler handler);

    IResponse requestToBindCar(Activity activity);

    void requestToBindCar(Activity activity, ICallback<String, IException> iCallback);

    void requestToBindCar(Activity activity, ICallback<String, IException> iCallback, Handler handler);

    IResponse requestToDeleteBindCar(Activity activity);

    void requestToDeleteBindCar(Activity activity, ICallback<String, IException> iCallback);

    void requestToDeleteBindCar(Activity activity, ICallback<String, IException> iCallback, Handler handler);
}
