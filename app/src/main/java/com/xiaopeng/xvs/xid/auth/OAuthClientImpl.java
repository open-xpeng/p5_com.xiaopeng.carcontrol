package com.xiaopeng.xvs.xid.auth;

import android.accounts.Account;
import android.app.Activity;
import android.os.Handler;
import com.xiaopeng.xvs.xid.XId;
import com.xiaopeng.xvs.xid.auth.api.IOAuth;
import com.xiaopeng.xvs.xid.auth.api.OAuthException;
import com.xiaopeng.xvs.xid.auth.api.OAuthResponse;
import com.xiaopeng.xvs.xid.base.ICallback;
import com.xiaopeng.xvs.xid.base.IClient;
import com.xiaopeng.xvs.xid.base.IException;
import com.xiaopeng.xvs.xid.base.IResponse;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class OAuthClientImpl extends IClient implements IOAuth {
    private static final String TAG = "OAuthClientImpl";
    private static volatile IOAuth mInstance;
    private OAuthContextWrapper mContextWrapper = new OAuthContextWrapper(XId.getAppId(), XId.getAccountManager());

    private OAuthClientImpl() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static IOAuth getInstance() {
        if (mInstance == null) {
            synchronized (OAuthClientImpl.class) {
                if (mInstance == null) {
                    mInstance = new OAuthClientImpl();
                }
            }
        }
        return mInstance;
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public void requestOAuth(Activity activity, ICallback<String, IException> iCallback) {
        requestOAuth(activity, iCallback, null);
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public void requestOAuth(final Activity activity, final ICallback<String, IException> iCallback, Handler handler) {
        Account account = XId.getAccountApi().getAccount();
        if (account == null) {
            if (activity != null) {
                XId.getAccountApi().login(activity);
            }
            iCallback.onFail(new OAuthException(10003));
            return;
        }
        this.mContextWrapper.getAuthToken(account, "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_CODE", new ICallback<String, IException>() { // from class: com.xiaopeng.xvs.xid.auth.OAuthClientImpl.1
            @Override // com.xiaopeng.xvs.xid.base.ICallback
            public void onSuccess(String str) {
                iCallback.onSuccess(str);
            }

            @Override // com.xiaopeng.xvs.xid.base.ICallback
            public void onFail(IException iException) {
                if (iException.getCode() == 10003) {
                    if (activity != null) {
                        XId.getAccountApi().login(activity);
                    }
                    iCallback.onFail(new OAuthException(10003));
                }
            }
        }, handler);
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public IResponse requestOAuth(Activity activity) {
        Account account = XId.getAccountApi().getAccount();
        if (account == null) {
            if (activity != null) {
                XId.getAccountApi().login(activity);
            }
            return new OAuthResponse(10003);
        }
        return this.mContextWrapper.getAuthToken(account, "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_CODE");
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public void requestOTP(ICallback<String, IException> iCallback) {
        requestOTP(iCallback, null);
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public void requestOTP(ICallback<String, IException> iCallback, Handler handler) {
        Account account = XId.getAccountApi().getAccount();
        if (account == null) {
            iCallback.onFail(new OAuthException(10003));
        } else {
            this.mContextWrapper.getAuthToken(account, "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_OTP", iCallback, handler);
        }
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public IResponse requestOTP() {
        if (XId.getAccountApi().getAccount() == null) {
            return new OAuthResponse(10003);
        }
        return this.mContextWrapper.getAuthToken(XId.getAccountApi().getAccount(), "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_OTP");
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public void requestCarBind(Activity activity, ICallback<String, IException> iCallback) {
        requestCarBind(activity, iCallback, null);
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public void requestCarBind(final Activity activity, final ICallback<String, IException> iCallback, Handler handler) {
        Account account = XId.getAccountApi().getAccount();
        if (account == null) {
            if (activity != null) {
                XId.getAccountApi().login(activity);
            }
            iCallback.onFail(new OAuthException(10003));
            return;
        }
        this.mContextWrapper.getAuthToken(account, IOAuth.AUTH_TYPE_CHECK_VE_DATA_AUTH, new ICallback<String, IException>() { // from class: com.xiaopeng.xvs.xid.auth.OAuthClientImpl.2
            @Override // com.xiaopeng.xvs.xid.base.ICallback
            public void onSuccess(String str) {
                iCallback.onSuccess(str);
            }

            @Override // com.xiaopeng.xvs.xid.base.ICallback
            public void onFail(IException iException) {
                if (iException.getCode() == 10003) {
                    if (activity != null) {
                        XId.getAccountApi().login(activity);
                    }
                    iCallback.onFail(new OAuthException(10003));
                }
            }
        }, handler);
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public IResponse requestCarBind(Activity activity) {
        Account account = XId.getAccountApi().getAccount();
        if (account == null) {
            if (activity != null) {
                XId.getAccountApi().login(activity);
            }
            return new OAuthResponse(10003);
        }
        return this.mContextWrapper.getAuthToken(account, "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_CODE");
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public void requestToBindCar(Activity activity, ICallback<String, IException> iCallback) {
        requestToBindCar(activity, iCallback, null);
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public void requestToBindCar(final Activity activity, final ICallback<String, IException> iCallback, Handler handler) {
        Account account = XId.getAccountApi().getAccount();
        if (account == null) {
            if (activity != null) {
                XId.getAccountApi().login(activity);
            }
            iCallback.onFail(new OAuthException(10003));
            return;
        }
        this.mContextWrapper.getAuthToken(account, IOAuth.AUTH_TYPE_BIND_VE_DATA_AUTH, new ICallback<String, IException>() { // from class: com.xiaopeng.xvs.xid.auth.OAuthClientImpl.3
            @Override // com.xiaopeng.xvs.xid.base.ICallback
            public void onSuccess(String str) {
                iCallback.onSuccess(str);
            }

            @Override // com.xiaopeng.xvs.xid.base.ICallback
            public void onFail(IException iException) {
                if (iException.getCode() == 10003) {
                    if (activity != null) {
                        XId.getAccountApi().login(activity);
                    }
                    iCallback.onFail(new OAuthException(10003));
                }
            }
        }, handler);
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public IResponse requestToBindCar(Activity activity) {
        Account account = XId.getAccountApi().getAccount();
        if (account == null) {
            if (activity != null) {
                XId.getAccountApi().login(activity);
            }
            return new OAuthResponse(10003);
        }
        return this.mContextWrapper.getAuthToken(account, "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_CODE");
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public void requestToDeleteBindCar(Activity activity, ICallback<String, IException> iCallback) {
        requestToDeleteBindCar(activity, iCallback, null);
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public void requestToDeleteBindCar(final Activity activity, final ICallback<String, IException> iCallback, Handler handler) {
        Account account = XId.getAccountApi().getAccount();
        if (account == null) {
            if (activity != null) {
                XId.getAccountApi().login(activity);
            }
            iCallback.onFail(new OAuthException(10003));
            return;
        }
        this.mContextWrapper.getAuthToken(account, IOAuth.AUTH_TYPE_DELETE_VE_DATA_AUTH_RECORD, new ICallback<String, IException>() { // from class: com.xiaopeng.xvs.xid.auth.OAuthClientImpl.4
            @Override // com.xiaopeng.xvs.xid.base.ICallback
            public void onSuccess(String str) {
                iCallback.onSuccess(str);
            }

            @Override // com.xiaopeng.xvs.xid.base.ICallback
            public void onFail(IException iException) {
                if (iException.getCode() == 10003) {
                    if (activity != null) {
                        XId.getAccountApi().login(activity);
                    }
                    iCallback.onFail(new OAuthException(10003));
                }
            }
        }, handler);
    }

    @Override // com.xiaopeng.xvs.xid.auth.api.IOAuth
    public IResponse requestToDeleteBindCar(Activity activity) {
        Account account = XId.getAccountApi().getAccount();
        if (account == null) {
            if (activity != null) {
                XId.getAccountApi().login(activity);
            }
            return new OAuthResponse(10003);
        }
        return this.mContextWrapper.getAuthToken(account, "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_CODE");
    }
}
