package com.xiaopeng.lib.framework.moduleinterface.accountmodule;

/* loaded from: classes2.dex */
public interface IUserInfo {

    /* loaded from: classes2.dex */
    public enum InfoType {
        CHANGED,
        UPDATE
    }

    /* loaded from: classes2.dex */
    public enum UserType {
        TEMP,
        OWNER,
        USER,
        TENANT,
        DRIVER
    }

    String getAvatar();

    InfoType getInfoType();

    String getUserName();

    UserType getUserType();

    IUserInfo setAvatar(String str);

    IUserInfo setInfoType(InfoType infoType);

    IUserInfo setUserName(String str);

    @Deprecated
    IUserInfo setUserType(int i);

    IUserInfo setUserType(UserType userType);
}
