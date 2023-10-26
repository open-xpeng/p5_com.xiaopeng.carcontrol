package com.xiaopeng.xvs.xid.account.api;

import android.accounts.Account;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Deprecated
/* loaded from: classes2.dex */
public interface IAccountInfo2 {
    public static final String USER_DATA_ACCOUNT_USER_TYPE = "user_type";
    public static final String USER_DATA_EXTRA_AVATAR = "avatar";
    public static final String USER_DATA_EXTRA_MUSIC_VIP = "music_vip";
    public static final String USER_DATA_EXTRA_NAME = "name";
    public static final String USER_DATA_EXTRA_UID = "uid";
    public static final String USER_DATA_EXTRA_UPDATE = "update";
    public static final String USER_DATA_SYNC_SLOT_ALIAS = "user_data_sync_slot_alias";
    public static final String USER_DATA_SYNC_SLOT_ID = "user_data_sync_slot_id";
    public static final String USER_DATA_TRAFFIC_REMAIN_DATA = "user_data_traffic_remain_data";
    public static final String USER_DATA_TRAFFIC_TOTAL_DATA = "user_data_traffic_total_data";
    public static final String USER_DATA_WALLET_FREEZE_AMOUNT = "user_data_wallet_freeze_amount";
    public static final String USER_DATA_WALLET_TOTAL_AMOUNT = "wallet_total_amount";
    public static final String USER_DATA_WALLET_VALID_AMOUNT = "user_data_wallet_valid_amount";
    public static final int USER_TYPE_DRIVER = 4;
    public static final int USER_TYPE_OWNER = 1;
    public static final int USER_TYPE_TEMP = 0;
    public static final int USER_TYPE_TENANT = 3;
    public static final int USER_TYPE_USER = 2;
    public static final long USER_UID_TEMP = -1;

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface UserType {
    }

    static boolean isUidValid(long j) {
        return j > 0;
    }

    Account getAccount();

    AccountType getAccountType();

    String getAvatar();

    long getUid();

    String getUserName();

    int getUserType();

    boolean isUpdate();

    void setAccount(Account account);

    void setAccountType(AccountType accountType);

    void setAvatar(String str);

    void setUid(long j);

    IAccountInfo2 setUpdate(boolean z);

    void setUserName(String str);

    void setUserType(int i);

    String toString();
}
