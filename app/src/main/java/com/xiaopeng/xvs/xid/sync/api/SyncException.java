package com.xiaopeng.xvs.xid.sync.api;

import com.xiaopeng.xvs.xid.base.BaseException;

/* loaded from: classes2.dex */
public class SyncException extends BaseException {
    public static final int ERROR_CODE_SYNC_APP_ID_ERROR = 40001;
    public static final int ERROR_CODE_SYNC_CHANNEL_FATAL = 40006;
    public static final int ERROR_CODE_SYNC_DATABASE_FATAL = 40005;
    public static final int ERROR_CODE_SYNC_ILLEGAL_KEY = 40003;
    public static final int ERROR_CODE_SYNC_NO_LOGIN = 40004;
    public static final int ERROR_CODE_SYNC_SAVE_EMPTY = 40002;

    public SyncException() {
    }

    public SyncException(int i) {
        super(i);
    }

    public SyncException(int i, String str) {
        super(i, str);
    }

    @Override // com.xiaopeng.xvs.xid.base.AbsException, com.xiaopeng.xvs.xid.base.IException
    public String getMessage() {
        switch (getCode()) {
            case ERROR_CODE_SYNC_APP_ID_ERROR /* 40001 */:
                return "当前AppId不匹配";
            case ERROR_CODE_SYNC_SAVE_EMPTY /* 40002 */:
                return "当前数据已同步，暂无新数据上传";
            case ERROR_CODE_SYNC_ILLEGAL_KEY /* 40003 */:
                return "当前Key格式非法";
            case ERROR_CODE_SYNC_NO_LOGIN /* 40004 */:
                return "当前账号未登录";
            case ERROR_CODE_SYNC_DATABASE_FATAL /* 40005 */:
                return "数据加载失败";
            case ERROR_CODE_SYNC_CHANNEL_FATAL /* 40006 */:
                return "数据通道创建失败";
            default:
                return super.getMessage();
        }
    }
}
