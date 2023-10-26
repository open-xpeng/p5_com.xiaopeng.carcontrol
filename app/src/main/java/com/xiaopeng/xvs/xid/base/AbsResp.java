package com.xiaopeng.xvs.xid.base;

import android.text.TextUtils;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;

/* loaded from: classes2.dex */
public abstract class AbsResp<T> implements IResp {
    public static final int ERROR_CODE_PARAM_ERROR = 10004;
    public static final int ERROR_CODE_PARSE_FAIL = 10000;
    public static final int ERROR_CODE_USER_LOGOUT = 10003;
    protected int mCode;
    protected T mData;
    protected String mMsg;

    public AbsResp() {
    }

    public AbsResp(int i) {
        this.mCode = i;
    }

    public AbsResp(int i, String str) {
        this(i);
        this.mMsg = str;
    }

    public AbsResp(int i, String str, T t) {
        this(i, str);
        this.mData = t;
    }

    @Override // com.xiaopeng.xvs.xid.base.IResp
    public int getCode() {
        return this.mCode;
    }

    @Override // com.xiaopeng.xvs.xid.base.IResp
    public String getMessage() {
        if (!TextUtils.isEmpty(this.mMsg)) {
            return this.mMsg;
        }
        int code = getCode();
        return code != 0 ? code != 10000 ? code != 10003 ? "网络正在开小差，请稍后重试" : IError.STR_USER_LOGOUT : "数据解析错误" : "请求成功";
    }

    @Override // com.xiaopeng.xvs.xid.base.IResp
    public T getData() {
        return this.mData;
    }
}
