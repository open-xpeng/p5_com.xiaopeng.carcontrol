package com.xiaopeng.xvs.xid.auth.api;

import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
import com.xiaopeng.xvs.xid.base.AbsException;
import com.xiaopeng.xvs.xid.base.BaseException;

/* loaded from: classes2.dex */
public class OAuthException extends BaseException {
    public static final int ERROR_CODE_AUTH_DENIED = 10002;
    public static final int ERROR_CODE_AUTH_REQUEST_FAIL = 10005;
    public static final int ERROR_CODE_AUTH_TIMEOUT = 10004;
    public static final int ERROR_CODE_CAR_NOT_STOPPED = 10006;
    public static final int ERROR_CODE_USER_CANCEL = 10001;
    @Deprecated
    public static final int ERROR_CODE_USER_CLOSE_QR = 10007;
    public static final int ERROR_CODE_USER_LOGOUT = 10003;

    public OAuthException() {
    }

    public OAuthException(int i) {
        super(i);
    }

    public OAuthException(int i, String str) {
        super(i, str);
    }

    @Override // com.xiaopeng.xvs.xid.base.AbsException, com.xiaopeng.xvs.xid.base.IException
    public String getMessage() {
        int code = getCode();
        if (code != 0) {
            switch (code) {
                case 10001:
                    return "用户取消授权";
                case 10002:
                    return "用户拒绝授权";
                case 10003:
                    return IError.STR_USER_LOGOUT;
                case 10004:
                    return "用户授权超时";
                case 10005:
                    return "网络授权失败";
                case 10006:
                    return "车辆行驶中，暂不能进行该操作";
                case 10007:
                    return "操作已取消";
                default:
                    switch (code) {
                        case AbsException.ERROR_CODE_COMMON_SERVER_DISCONNECTION /* 90004 */:
                            return IError.STR_SERVICE_DISCONNECTED;
                        case AbsException.ERROR_CODE_COMMON_SERVER_UNBIND /* 90005 */:
                            return "账号服务已取消绑定";
                        default:
                            return super.getMessage();
                    }
            }
        }
        return "请求成功";
    }
}
