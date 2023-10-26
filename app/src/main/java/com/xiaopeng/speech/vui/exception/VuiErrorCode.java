package com.xiaopeng.speech.vui.exception;

/* loaded from: classes2.dex */
public enum VuiErrorCode {
    UNSPECIFIED(1000, "未知错误"),
    ID_REPEAT(100, "ID重复"),
    ID_ILLEGAL(101, "ID不合法"),
    ID_EMPTY(100, "ID为空"),
    UPDATE_OPERATE_ILLEGAL(100, "更新操作不合法");
    
    private int code;
    private String description;

    VuiErrorCode(int i, String str) {
        this.code = i;
        this.description = str;
    }

    public static VuiErrorCode getByCode(int i) {
        VuiErrorCode[] values;
        for (VuiErrorCode vuiErrorCode : values()) {
            if (i == vuiErrorCode.getCode()) {
                return vuiErrorCode;
            }
        }
        return UNSPECIFIED;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }
}
