package com.irdeto.securesdk.core;

/* loaded from: classes.dex */
public class SSUtils {
    public static final String O000000o = "config";
    public static final String O00000Oo = "pin";
    public static final String O00000o = "remember_chooose_map";
    public static final String O00000o0 = "cache";
    public static final String O00000oO = "data_loading";
    public static final String O00000oo = "pin_current_error_times";
    public static final String O0000O0o = "pin_error_save_time";
    public static final String O0000OOo = "pin_error_code";
    public static final String O0000Oo = "pin_pv_code";
    public static final String O0000Oo0 = "pin_error_lock_time";
    public static final String O0000OoO = "refresh_time";
    public static final String O0000Ooo = "vin";
    public static final String O0000o = "is_set_push_";
    public static final String O0000o0 = "ble_key";
    public static final String O0000o00 = "zxqsettings";
    public static final String O0000o0O = "cache_size";
    public static final String O0000o0o = "set_push";
    public static final String O0000oO = "is_whistle_on";
    public static final String O0000oO0 = "preference_setting";
    public static final String O0000oOO = "is_smart_key_on";
    public static final String O0000oOo = "car_wallet_settings";
    public static final String O0000oo = "Inspection_nifty";
    public static final String O0000oo0 = "is_join_insurance_complete";
    public static final String O0000ooO = "Inspection_sixty";
    public static final String O0000ooo = "Inspection_thirty";
    public static final String O000O00o = "air_conditioning_first_hint";
    public static final String O000O0OO = "auto_update";
    public static final String O000O0Oo = "software_update";
    public static final String O000O0o = "is_first_in_all_orders";
    public static final String O000O0o0 = "all_orders";
    public static final String O000O0oO = "is_need_to_refresh";
    public static final String O000O0oo = "is_need_to_add_item";
    public static final String O000OO = "is_frist";
    public static final String O000OO00 = "channel_id";
    public static final String O000OO0o = "start_newbie_guide";
    public static final String O000OOOo = "version_code";
    public static final String O000OOo = "zebra_coin_balance";
    public static final String O000OOo0 = "zebra_coin";
    public static final String O000OOoO = "zebra_coin_income";
    public static final String O000OOoo = "zebra_coin_cost";
    public static final String O000Oo0 = "city_rank";
    public static final String O000Oo00 = "zebra_coin_bean";
    public static final String O000Oo0O = "odometer";
    public static final String O000Oo0o = "vehicle_security";
    public static final String O000OoO = "vehicle_resource_download";
    public static final String O000OoO0 = "vehicle_status_result_";
    public static final String O000OoOO = "wx_share_info";
    public static final String O000OoOo = "is_safe_driver_share";
    public static final String O000Ooo = "vincode_three_min";
    public static final String O000Ooo0 = "is_wx_share_status";
    public static final String O000OooO = "notifation_msg";
    public static final String O000Oooo = "notifation_msg_type";
    public static final String O000o000 = "";
    public static final String O00O0Oo = "vehicle_status_address_";
    public static final String O00oOoOo = "is_first_maintain";
    public static final String O00oOooO = "currentIndex";
    public static final String O00oOooo = "default_linkman";

    public static Long O000000o(String str, String str2, long j) {
        return Long.valueOf(_getLong(str, str2, j));
    }

    public static String O000000o(int i) {
        return _formatBookingId(i);
    }

    public static void O000000o() {
        _clearUserStoreContext();
    }

    public static void O000000o(String str, String str2) {
        _removeItem(str, str2);
    }

    public static void O000000o(String str, String str2, int i) {
        _putInt(str, str2, i);
    }

    public static void O000000o(String str, String str2, Long l) {
        _putLong(str, str2, l.longValue());
    }

    public static void O000000o(String str, String str2, String str3) {
        _putString(str, str2, str3);
    }

    public static void O000000o(String str, String str2, boolean z) {
        _putBoolean(str, str2, z);
    }

    public static void O000000o(String str, byte[] bArr) {
        _putByteArray(str, bArr);
    }

    public static int O00000Oo(String str, String str2, int i) {
        return _getInt(str, str2, i);
    }

    public static String O00000Oo(String str, String str2, String str3) {
        return _getString(str, str2, str3);
    }

    public static boolean O00000Oo(String str, String str2, boolean z) {
        return _getBoolean(str, str2, z);
    }

    public static byte[] O00000Oo(String str, byte[] bArr) {
        return _getByteArray(str, bArr);
    }

    public static String O00000o0(String str, String str2, int i) {
        return _stringFromJNI(str, str2, i);
    }

    private static native void _clearUserStoreContext();

    private static native String _formatBookingId(int i);

    private static native boolean _getBoolean(String str, String str2, boolean z);

    private static native byte[] _getByteArray(String str, byte[] bArr);

    private static native int _getInt(String str, String str2, int i);

    private static native long _getLong(String str, String str2, long j);

    private static native String _getString(String str, String str2, String str3);

    private static native void _putBoolean(String str, String str2, boolean z);

    private static native void _putByteArray(String str, byte[] bArr);

    private static native void _putInt(String str, String str2, int i);

    private static native void _putLong(String str, String str2, long j);

    private static native void _putString(String str, String str2, String str3);

    private static native void _removeItem(String str, String str2);

    private static native String _stringFromJNI(String str, String str2, int i);
}
