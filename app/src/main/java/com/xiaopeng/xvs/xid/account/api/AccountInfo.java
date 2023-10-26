package com.xiaopeng.xvs.xid.account.api;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.xiaopeng.xvs.xid.utils.L;

/* loaded from: classes2.dex */
public class AccountInfo {
    private static final String TAG = "AccountInfo";
    public static final String USER_DATA_ACCOUNT_TYPE = "user_type";
    public static final String USER_DATA_AVATAR = "avatar";
    public static final String USER_DATA_ENCRYPTED_PHONE = "user_data_encrypted_phone";
    public static final String USER_DATA_MUSIC_VIP = "music_vip";
    public static final String USER_DATA_NAME = "name";
    public static final String USER_DATA_SYNC_GROUP_ID = "user_data_sync_group_id";
    public static final String USER_DATA_SYNC_GROUP_NAME = "user_data_sync_group_name";
    public static final String USER_DATA_TRAFFIC_REMAIN_DATA = "user_data_traffic_remain_data";
    public static final String USER_DATA_TRAFFIC_TOTAL_DATA = "user_data_traffic_total_data";
    public static final String USER_DATA_UID = "uid";
    public static final String USER_DATA_UPDATE = "update";
    public static final String USER_DATA_WALLET_AVAILABLE_AMOUNT = "user_data_wallet_available_amount";
    public static final String USER_DATA_WALLET_FREEZE_AMOUNT = "user_data_wallet_freeze_amount";
    public static final String USER_DATA_WALLET_TOTAL_AMOUNT = "wallet_total_amount";
    public static final long USER_UID_TEMP = -1;
    private Account mAccount;
    private AccountManager mAccountManager;

    public static boolean isUidValid(long j) {
        return j > 0;
    }

    public AccountInfo(AccountManager accountManager, Account account) {
        this.mAccountManager = accountManager;
        this.mAccount = account;
    }

    public void update(Account account) {
        this.mAccount = account;
    }

    public String getName() {
        return this.mAccountManager.getUserData(this.mAccount, "name");
    }

    public long getUid() {
        long j;
        try {
            j = Long.parseLong(getUserData("uid", "0"));
        } catch (Exception e) {
            L.d(TAG, "getUid: e=" + e.getMessage());
            j = 0;
        }
        if (!isUidValid(j)) {
            L.e(TAG, "getAccountType:throw new IllegalAccountTypeException() uid=" + j);
        }
        return j;
    }

    public String getAvatar() {
        return getUserData("avatar");
    }

    public boolean isUpdate() {
        return "1".equals(getUserData("update", "1"));
    }

    public AccountType getAccountType() {
        int i;
        int i2;
        int i3 = 1;
        try {
            i = Integer.parseInt(getUserData("user_type", "1"));
            try {
            } catch (Exception e) {
                int i4 = i2;
                e = e;
                i3 = i4;
                L.e(TAG, "getAccountType: parseInt e=" + e.getMessage());
                i = i3;
                AccountType accountType = AccountType.values()[i];
                L.d(TAG, "getAccountType: accountType=" + accountType);
                return accountType;
            }
        } catch (Exception e2) {
            e = e2;
        }
        if (i >= AccountType.values().length) {
            i3 = AccountType.values().length - 1;
            L.e(TAG, "getAccountType:throw new ArrayIndexOutOfBoundsException() index=" + i3);
            i = i3;
        }
        AccountType accountType2 = AccountType.values()[i];
        L.d(TAG, "getAccountType: accountType=" + accountType2);
        return accountType2;
    }

    public boolean isMusicVip() {
        int i;
        try {
            i = Integer.parseInt(getUserData("music_vip", "0"));
        } catch (Exception e) {
            L.d(TAG, "isMusicVip: parseInt e=" + e.getMessage());
            i = 0;
        }
        return i == 1;
    }

    public String getEncryptedPhone() {
        return getUserData(USER_DATA_ENCRYPTED_PHONE);
    }

    public String getTrafficTotalData() {
        return getUserData("user_data_traffic_total_data");
    }

    public String getTrafficRemainData() {
        return getUserData("user_data_traffic_remain_data");
    }

    public String getWalletTotalAmount() {
        return getUserData("wallet_total_amount");
    }

    public String getWalletFreezeAmount() {
        return getUserData("user_data_wallet_freeze_amount");
    }

    public String getWalletAvailableAmount() {
        return getUserData(USER_DATA_WALLET_AVAILABLE_AMOUNT);
    }

    public String getSyncGroupName() {
        return getUserData(USER_DATA_SYNC_GROUP_NAME);
    }

    public String getSyncGroupId() {
        return getUserData(USER_DATA_SYNC_GROUP_ID);
    }

    private String getUserData(String str) {
        String userData = this.mAccountManager.getUserData(this.mAccount, str);
        return userData == null ? "" : userData;
    }

    private String getUserData(String str, String str2) {
        String userData = this.mAccountManager.getUserData(this.mAccount, str);
        return userData == null ? str2 : userData;
    }

    public String toString() {
        return "AccountInfo{name=" + getName() + ";uid=" + getUid() + ";avatar=" + getAvatar() + ";isUpdate=" + isUpdate() + ";type=" + getAccountType() + ";isMusicVip=" + isMusicVip() + ";trafficTotalData=" + getTrafficTotalData() + ";trafficRemainData=" + getTrafficRemainData() + ";walletTotalAmount=" + getWalletTotalAmount() + ";walletFreezeAmount=" + getWalletFreezeAmount() + ";walletAvailableAmount=" + getWalletAvailableAmount() + ";syncGroupName=" + getSyncGroupName() + ";syncGroupId=" + getSyncGroupId() + '}';
    }
}
