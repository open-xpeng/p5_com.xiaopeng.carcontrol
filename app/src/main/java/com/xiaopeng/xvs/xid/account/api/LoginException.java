package com.xiaopeng.xvs.xid.account.api;

import android.text.TextUtils;
import com.xiaopeng.xvs.xid.base.BaseException;

/* loaded from: classes2.dex */
public class LoginException extends BaseException {
    public static final int ERROR_CODE_LOGIN_ACCOUNT_NOT_FOUNDED = 30004;
    public static final int ERROR_CODE_LOGIN_FAILED = 30008;
    public static final int ERROR_CODE_LOGIN_FATAL_ERROR = 30009;
    public static final int ERROR_CODE_LOGIN_INVALID_TOKEN = 30006;
    public static final int ERROR_CODE_LOGIN_INVALID_UID = 30005;
    public static final int ERROR_CODE_LOGIN_NOT_ALLOW = 30002;
    public static final int ERROR_CODE_LOGIN_REFRESH_TOKEN_FAIL = 30011;
    public static final int ERROR_CODE_LOGIN_REFRESH_TOKEN_FREQUENTLY = 30010;
    public static final int ERROR_CODE_LOGIN_SAME_UID = 30001;
    public static final int ERROR_CODE_LOGIN_TOKEN_SAVE_FAIL = 30003;
    public static final int ERROR_CODE_LOGIN_TYPE_INVALID = 30007;

    public LoginException() {
    }

    public LoginException(int i) {
        super(i);
    }

    public LoginException(int i, String str) {
        super(i, str);
    }

    @Override // com.xiaopeng.xvs.xid.base.AbsException, com.xiaopeng.xvs.xid.base.IException
    public String getMessage() {
        if (!TextUtils.isEmpty(this.mMsg)) {
            return this.mMsg;
        }
        switch (getCode()) {
            case 30001:
                return "您已登录该账号，不需要重复登录";
            case 30002:
                return "请在P档进行操作";
            case 30003:
                return "Token存储失败";
            case 30004:
                return "账号不存在";
            case 30005:
                return "账号UID无效";
            case ERROR_CODE_LOGIN_INVALID_TOKEN /* 30006 */:
                return "账号TOKEN无效";
            case ERROR_CODE_LOGIN_TYPE_INVALID /* 30007 */:
                return "登录方式无效";
            case ERROR_CODE_LOGIN_FAILED /* 30008 */:
                return "账号登录失败";
            case ERROR_CODE_LOGIN_FATAL_ERROR /* 30009 */:
                return "账号登录出现异常";
            case ERROR_CODE_LOGIN_REFRESH_TOKEN_FREQUENTLY /* 30010 */:
                return "refresh token is too frequently";
            default:
                return super.getMessage();
        }
    }
}
