package com.xiaopeng.xvs.xid.base;

import android.text.TextUtils;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
import com.xiaopeng.xvs.xid.utils.ResourceUtils;

/* loaded from: classes2.dex */
public abstract class AbsException implements IException {
    public static final int ERROR_CODE_BASE_COMMON = 90000;
    public static final int ERROR_CODE_BASE_HABIT = 50000;
    public static final int ERROR_CODE_BASE_LOGIN = 30000;
    public static final int ERROR_CODE_BASE_OAUTH = 10000;
    public static final int ERROR_CODE_BASE_PAY = 20000;
    public static final int ERROR_CODE_BASE_SYNC = 40000;
    public static final int ERROR_CODE_COMMON_NOT_SUPPORT = 90007;
    public static final int ERROR_CODE_COMMON_PARAM_ERROR = 90003;
    public static final int ERROR_CODE_COMMON_PARSE_FAIL = 90001;
    public static final int ERROR_CODE_COMMON_SERVER_DISCONNECTION = 90004;
    public static final int ERROR_CODE_COMMON_SERVER_UNBIND = 90005;
    public static final int ERROR_CODE_COMMON_SERVER_UNKNOWN = 90100;
    public static final int ERROR_CODE_COMMON_TIME_OUT = 90006;
    public static final int ERROR_CODE_COMMON_USER_LOGOUT = 90002;
    protected int mCode;
    protected String mMsg;

    public AbsException() {
    }

    public AbsException(int i) {
        this.mCode = i;
    }

    public AbsException(int i, String str) {
        this.mCode = i;
        this.mMsg = str;
    }

    @Override // com.xiaopeng.xvs.xid.base.IException
    public int getCode() {
        return this.mCode;
    }

    @Override // com.xiaopeng.xvs.xid.base.IException
    public String getMessage() {
        if (TextUtils.isEmpty(this.mMsg)) {
            switch (getCode()) {
                case 0:
                    return "请求成功";
                case ERROR_CODE_COMMON_PARSE_FAIL /* 90001 */:
                    return "数据解析错误";
                case ERROR_CODE_COMMON_USER_LOGOUT /* 90002 */:
                    return IError.STR_USER_LOGOUT;
                case 90006:
                    return "网络请求超时";
                case 90007:
                    return "not support";
                default:
                    return "网络正在开小差，请稍后重试";
            }
        }
        return this.mMsg;
    }

    @Override // com.xiaopeng.xvs.xid.base.IException
    public String toString() {
        return "" + getClass().getSimpleName() + "{mCode=" + getCode() + ", mMsg='" + getMessage() + "'}";
    }

    protected String getString(int i, Object... objArr) {
        return ResourceUtils.getString(i, objArr);
    }
}
