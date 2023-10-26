package com.xiaopeng.lib.framework.moduleinterface.accountmodule;

import android.app.Application;

/* loaded from: classes2.dex */
public interface IAccount {
    IUserInfo getUserInfo() throws AbsException;

    void init(Application application, String str) throws AbsException;

    void init(Application application, String str, String str2) throws AbsException;

    void login() throws AbsException;

    void logout() throws AbsException;

    void requestOAuth(ICallback<IAuthInfo, IError> iCallback);

    void requestOAuth(String str, ICallback<IAuthInfo, IError> iCallback);

    void requestOTP(String str, ICallback<IOTPInfo, IError> iCallback);
}
