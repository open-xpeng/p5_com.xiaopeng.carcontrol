package com.xiaopeng.carcontrol.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/* loaded from: classes2.dex */
public final class CarControl {
    private static final String AUTHORITY = "com.xiaopeng.carcontrol";
    static final String CALL_METHOD_GET_PRIVATE = "GET_private";
    static final String CALL_METHOD_GET_QUICK = "GET_quick";
    static final String CALL_METHOD_GET_SYSTEM = "GET_system";
    static final String CALL_METHOD_PUT_PRIVATE = "PUT_private";
    static final String CALL_METHOD_PUT_QUICK = "PUT_quick";
    static final String CALL_METHOD_PUT_SYSTEM = "PUT_system";
    private static final String TAG = "CarControl";

    /* loaded from: classes2.dex */
    public static class NameValueTable {
        static final String NAME = "name";
        static final String VALUE = "value";

        protected static boolean putString(ContentResolver resolver, Uri uri, String name, String value) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", name);
                contentValues.put("value", value);
                resolver.insert(uri, contentValues);
                return true;
            } catch (Exception e) {
                Log.w("CarControl", "Can't set key " + name + " in " + uri, e);
                return false;
            }
        }

        static Uri getUriFor(Uri uri, String name) {
            return Uri.withAppendedPath(uri, name);
        }
    }

    /* loaded from: classes2.dex */
    public static final class System extends NameValueTable {
        public static final String AVAILABLE_DISTANCE = "available_distance";
        public static final String BATTERY_PERCENT = "battery_percent";
        public static final String CAR_CONTROL_READY = "cc_ready";
        public static final String CENTRAL_LOCK = "central_lock";
        public static final String CENTRAL_LOCK_SET = "central_lock_set";
        public static final String CHARGE_STATUS = "charge_status";
        public static final Uri CONTENT_URI;
        public static final String DRIVE_MODE = "drive_mode";
        public static final String DRIVE_MODE_SET = "drive_mode_set";
        public static final String DRV_OCCUPIED = "drv_occupied";
        public static final String HVAC_AIR_PURGE_MODE = "hvac_air_purge_mode";
        public static final String HVAC_AUTO = "hvac_auto";
        public static final String HVAC_BACK_DEFROST = "hvac_back_defrost";
        public static final String HVAC_BACK_DEFROST_SET = "hvac_back_defrost_set";
        public static final String HVAC_BLOWER_CTRL_TYPE = "hvac_blower_ctrl_type";
        public static final String HVAC_DRV_SYNC = "hvac_drv_sync";
        public static final String HVAC_DRV_SYNC_SET = "hvac_drv_sync_set";
        public static final String HVAC_DRV_TEMP = "hvac_drv_temp";
        public static final String HVAC_DRV_TEMP_SET = "hvac_drv_temp_set";
        public static final String HVAC_DRV_TEMP_STEP_SET = "hvac_drv_temp_step_set";
        public static final String HVAC_FRONT_DEFROST = "hvac_front_defrost";
        public static final String HVAC_FRONT_DEFROST_SET = "hvac_front_defrost_set";
        public static final String HVAC_OUT_TEMP = "hvac_out_temp";
        public static final String HVAC_PM25 = "hvac_pm25";
        public static final String HVAC_POWER = "hvac_power";
        public static final String HVAC_POWER_SET = "hvac_power_set";
        public static final String HVAC_PSN_SYNC = "hvac_psn_sync";
        public static final String HVAC_PSN_SYNC_SET = "hvac_psn_sync_set";
        public static final String HVAC_PSN_TEMP = "hvac_psn_temp";
        public static final String HVAC_PSN_TEMP_SET = "hvac_psn_temp_set";
        public static final String HVAC_PSN_TEMP_STEP_SET = "hvac_psn_temp_step_set";
        public static final String HVAC_WIND_LEVEL = "hvac_wind_level";
        public static final String HVAC_WIND_MODE_COLOR = "hvac_wind_mode_color";
        public static final String PSN_OCCUPIED = "psn_occupied";
        private static final NameValueCache sNameValueCache;

        static {
            Uri parse = Uri.parse("content://com.xiaopeng.carcontrol/system");
            CONTENT_URI = parse;
            sNameValueCache = new NameValueCache(parse, CarControl.CALL_METHOD_GET_SYSTEM, CarControl.CALL_METHOD_PUT_SYSTEM);
        }

        public static String getString(ContentResolver resolver, String name) {
            return sNameValueCache.getString(resolver, name);
        }

        public static boolean putString(ContentResolver resolver, String name, String value) {
            return sNameValueCache.putString(resolver, name, value);
        }

        public static int getInt(ContentResolver cr, String name, int def) {
            try {
                return Integer.parseInt(getString(cr, name));
            } catch (NumberFormatException unused) {
                return def;
            }
        }

        public static int getInt(ContentResolver cr, String name) throws Exception {
            try {
                return Integer.parseInt(getString(cr, name));
            } catch (NumberFormatException unused) {
                throw new Exception("Value not found for " + name);
            }
        }

        public static boolean putInt(ContentResolver cr, String name, int value) {
            return putString(cr, name, Integer.toString(value));
        }

        public static float getFloat(ContentResolver cr, String name, float def) {
            try {
                return Float.parseFloat(getString(cr, name));
            } catch (NullPointerException | NumberFormatException unused) {
                return def;
            }
        }

        public static float getFloat(ContentResolver cr, String name) throws Exception {
            try {
                return Float.parseFloat(getString(cr, name));
            } catch (NullPointerException | NumberFormatException unused) {
                throw new Exception("Value not found for " + name);
            }
        }

        public static boolean putFloat(ContentResolver cr, String name, float value) {
            return putString(cr, name, Float.toString(value));
        }

        public static boolean getBool(ContentResolver cr, String name, boolean def) {
            try {
                return Integer.parseInt(getString(cr, name)) == 1;
            } catch (NumberFormatException unused) {
                return def;
            }
        }

        public static boolean putBool(ContentResolver cr, String name, boolean value) {
            return putString(cr, name, Integer.toString(value ? 1 : 0));
        }

        public static Uri getUriFor(String name) {
            return getUriFor(CONTENT_URI, name);
        }
    }

    /* loaded from: classes2.dex */
    public static final class Quick extends NameValueTable {
        public static final Uri CONTENT_URI;
        public static final String LEFT_CHARGE_PORT = "left_charge_port";
        public static final String LEFT_CHARGE_PORT_STATE = "left_charge_port_state";
        public static final String MIRROR_FOLD = "mirror_fold";
        public static final String MIRROR_FOLD_STATE = "mirror_fold_state";
        public static final String OPEN_REAR_TRUNK = "open_rear_trunk";
        public static final String RESET_CHARGE_PORT = "reset_charge_port";
        public static final String RIGHT_CHARGE_PORT = "right_charge_port";
        public static final String RIGHT_CHARGE_PORT_STATE = "right_charge_port_state";
        public static final String TRAILER_HOOK_SW = "trailer_hook_sw";
        public static final String TRAILER_MODE_SW = "trailer_mode_sw";
        private static final NameValueCache sNameValueCache;

        static {
            Uri parse = Uri.parse("content://com.xiaopeng.carcontrol/quick");
            CONTENT_URI = parse;
            sNameValueCache = new NameValueCache(parse, CarControl.CALL_METHOD_GET_QUICK, CarControl.CALL_METHOD_PUT_QUICK);
        }

        public static String getString(ContentResolver resolver, String name) {
            return sNameValueCache.getString(resolver, name);
        }

        public static boolean putString(ContentResolver resolver, String name, String value) {
            return sNameValueCache.putString(resolver, name, value);
        }

        public static int getInt(ContentResolver cr, String name, int def) {
            try {
                return Integer.parseInt(getString(cr, name));
            } catch (NumberFormatException unused) {
                return def;
            }
        }

        public static int getInt(ContentResolver cr, String name) throws Exception {
            try {
                return Integer.parseInt(getString(cr, name));
            } catch (NumberFormatException unused) {
                throw new Exception("Value not found for " + name);
            }
        }

        public static boolean putInt(ContentResolver cr, String name, int value) {
            return putString(cr, name, Integer.toString(value));
        }

        public static boolean getBool(ContentResolver cr, String name, boolean def) {
            try {
                return Integer.parseInt(getString(cr, name)) == 1;
            } catch (NumberFormatException unused) {
                return def;
            }
        }

        public static boolean putBool(ContentResolver cr, String name, boolean value) {
            return putString(cr, name, Integer.toString(value ? 1 : 0));
        }

        public static Uri getUriFor(String name) {
            return getUriFor(CONTENT_URI, name);
        }
    }

    /* loaded from: classes2.dex */
    public static final class PrivateControl extends NameValueTable {
        public static final String APA_SAFE_EXAM_RESULT = "apa_safe_exam";
        public static final String APA_SAFE_EXAM_RESULT_NOTIFY = "apa_safe_exam_notify";
        public static final String AS_CAMPING_MODE = "camping_loading";
        public static final String AS_WELCOME_MODE = "as_welcome";
        public static final String ATL_BRIGHTNESS = "atl_brightness";
        public static final String ATL_DUAL_COLOR = "atl_dual_color";
        public static final String ATL_DUAL_COLOR_SW = "atl_dual_color_sw";
        public static final String ATL_EFFECT = "atl_effect";
        public static final String ATL_SINGLE_COLOR = "atl_single_color";
        public static final String ATL_SW = "atl_sw";
        public static final String AVAS_EFFECT = "avas_effect";
        public static final String AVAS_EXTERNAL_SW = "avas_external_sw";
        public static final String AVAS_EXTERNAL_VOLUME = "avas_external_volume";
        public static final String AVAS_VOLUME = "avas_volume";
        public static final String AVH_SW = "avh_sw";
        public static final String AVH_SW_NOTIFY = "avh_sw_notify";
        public static final String BOOT_EFFECT = "boot_effect";
        public static final String BOOT_EFFECT_BEFORE_SW = "boot_effect_before_sw";
        public static final String CAR_LICENSE_PLATE = "car_license_plate";
        public static final String CHILD_LEFT_DOOR_HOT_KEY = "child_left_door_hot_key";
        public static final String CHILD_LEFT_LOCK = "child_left_lock";
        public static final String CHILD_RIGHT_DOOR_HOT_KEY = "child_right_door_hot_key";
        public static final String CHILD_RIGHT_LOCK = "child_right_lock";
        public static final String CNGP_SAFE_EXAM_RESULT = "cngp_safe_exam";
        public static final String CNGP_SAFE_EXAM_RESULT_NOTIFY = "cngp_safe_exam_notify";
        public static final String CONNECT_RESOURCE_MGR = "connect_resource_mgr";
        public static final Uri CONTENT_URI;
        public static final String CUSTOM_KEY_DOOR = "key_door";
        public static final String CUSTOM_KEY_WHEEL = "key_wheel";
        public static final String DHC = "dhc";
        public static final String DRIVE_MODE_UI = "drive_mode_ui";
        public static final String DRIVE_MODE_USER = "drive_mode_user";
        public static final String DRV_SEAT_CHECK = "drv_seat_check";
        public static final String EASY_LOADING_MODE = "easy_loading";
        public static final String ENERGY_RECYCLE = "energy_recycle";
        public static final String ESB = "esb";
        public static final String ESB_NOTIFY = "esb_notify";
        public static final String ESP_SW = "esp_sw";
        public static final String ESP_SW_NOTIFY = "esp_sw_notify";
        public static final String LCC_SAFE_EXAM_RESULT = "lcc_safe_exam";
        public static final String LCC_SAFE_EXAM_RESULT_NOTIFY = "lcc_safe_exam_notify";
        public static final String LCC_VIDEO_WATCH_STATE = "lcc_video_watched";
        public static final String LLU_SW = "llu_sw";
        public static final String LOCK_CLOSE_WIN = "lock_close_win";
        public static final String MEM_PARK_SAFE_EXAM_RESULT = "mem_park_safe_exam";
        public static final String MEM_PARK_SAFE_EXAM_RESULT_NOTIFY = "mem_park_safe_exam_notify";
        public static final String MEM_PARK_VIDEO_WATCH_STATE = "mem_park_video_watched";
        public static final String NEW_DRIVE_MODE_VALUE = "new_drive_mode_value";
        public static final String NEW_DRIVE_XPEDAL_MODE = "new_drive_xpedal_mode";
        public static final String NFC_KEY_ENABLE = "nfc_key_enable";
        public static final String NGP_SAFE_EXAM_RESULT = "ngp_safe_exam";
        public static final String NGP_SAFE_EXAM_RESULT_NOTIFY = "ngp_safe_exam_notify";
        public static final String NORMAL_DRIVE_MODE = "normal_drive_mode";
        public static final String N_PROTECT = "n_protect";
        public static final String PARK_AUTO_UNLOCK = "park_auto_unlock";
        public static final String PSN_SEAT_WELCOME_SW = "psn_seat_welcome";
        public static final String PSN_SRS_ENABLE = "psn_srs_enable";
        public static final String P_DROPDOWN_SW = "p_drop_sw";
        public static final String QUICK_CONTROL_UI_RESUME = "quick_control_ui_resume";
        public static final String REAR_FOG_SW = "rear_fog_sw";
        public static final String REAR_SEATBELT_WARNING = "rsw";
        public static final String REAR_SEAT_WELCOME_SW = "rear_seat_welcome";
        public static final String REMOVE_DROPDOWN_MENU = "remove_dropdown_menu";
        public static final String REQUEST_CAR_LICENSE_PLATE = "request_car_license_plate";
        public static final String SAY_HI_EFFECT = "say_hi_effect";
        public static final String SDC_AUTO_WIN_DOWN = "sdc_auto_win_down";
        public static final String SDC_BRAKE_CLOSE_DOOR_CFG = "sdc_brake_close_door_cfg";
        public static final String SDC_KEY_CFG = "sdc_key_cfg";
        public static final String SDC_MAX_AUTO_DOOR_OPENING_ANGLE = "sdc_max_auto_door_opening_angle";
        public static final String SEAT_WELCOME_SW = "seat_welcome";
        public static final String SET_ALC_SW = "set_alc_sw";
        public static final String SET_AUTO_PARK_SW = "set_auto_park_sw";
        public static final String SET_CITY_NGP_SW = "set_cngp_sw";
        public static final String SET_CWC_SW = "set_cwc_sw";
        public static final String SET_HEAD_LAMP = "set_head_lamp";
        public static final String SET_LCC_SW = "set_lcc_sw";
        public static final String SET_LSS_STATE = "set_lss_state";
        public static final String SET_MEM_PARK_SW = "set_mem_park_sw";
        public static final String SET_NGP_SW = "set_ngp_sw";
        public static final String SET_NRA_STATE = "set_nra_state";
        public static final String SET_SNOW_MODE = "set_snow_mode";
        public static final String SPEC_DRIVE_MODE_UI = "special_drive_mode_ui";
        public static final String SPEC_DRIVE_MODE_VALUE = "special_drive_mode_value";
        public static final String STEERING_EPS = "eps";
        public static final String SUPER_LCC_SAFE_EXAM_RESULT = "super_lcc_safe_exam";
        public static final String SUPER_LCC_SAFE_EXAM_RESULT_NOTIFY = "super_lcc_safe_exam_notify";
        public static final String SUPER_VPA_SAFE_EXAM_RESULT = "super_vpa_safe_exam";
        public static final String SUPER_VPA_SAFE_EXAM_RESULT_NOTIFY = "super_vpa_safe_exam_notify";
        public static final String TTM_SWITCH_MODE = "ttm_switch";
        public static final String UNITY_PRELOAD = "unity_preload";
        public static final String UNLOCK_RESPONSE = "unlock_response";
        public static final String USER_DEFINE_DRIVE_MODE = "user_define_drive";
        public static final String USER_DEFINE_DRIVE_MODE_INFO = "user_define_drive_info";
        public static final String WHEEL_KEY_PROTECT = "wheel_key_protect";
        public static final String WIN_HIGH_SPD = "win_high_spd";
        public static final String WIN_HIGH_SPD_NOTIFY = "win_high_spd_notify";
        public static final String WIPER_SENSITIVE_LEVEL = "wiper_cfg_level";
        public static final String XNGP_SAFE_EXAM_RESULT = "xngp_safe_exam";
        public static final String XNGP_SAFE_EXAM_RESULT_NOTIFY = "xngp_safe_exam_notify";
        public static final String XPILOT_ISLA_SW = "isla_sw";
        private static final NameValueCache sNameValueCache;

        static {
            Uri parse = Uri.parse("content://com.xiaopeng.carcontrol/private");
            CONTENT_URI = parse;
            sNameValueCache = new NameValueCache(parse, CarControl.CALL_METHOD_GET_PRIVATE, CarControl.CALL_METHOD_PUT_PRIVATE);
        }

        public static String getString(ContentResolver resolver, String name) {
            return sNameValueCache.getString(resolver, name);
        }

        public static boolean putString(ContentResolver resolver, String name, String value) {
            return sNameValueCache.putString(resolver, name, value);
        }

        public static int getInt(ContentResolver cr, String name, int def) {
            try {
                return Integer.parseInt(getString(cr, name));
            } catch (NumberFormatException unused) {
                return def;
            }
        }

        public static int getInt(ContentResolver cr, String name) throws Exception {
            try {
                return Integer.parseInt(getString(cr, name));
            } catch (NumberFormatException unused) {
                throw new Exception("Value not found for " + name);
            }
        }

        public static boolean putInt(ContentResolver cr, String name, int value) {
            return putString(cr, name, Integer.toString(value));
        }

        public static boolean getBool(ContentResolver cr, String name, boolean def) {
            try {
                return Integer.parseInt(getString(cr, name)) == 1;
            } catch (NumberFormatException unused) {
                return def;
            }
        }

        public static boolean putBool(ContentResolver cr, String name, boolean value) {
            return putString(cr, name, Integer.toString(value ? 1 : 0));
        }

        public static Uri getUriFor(String name) {
            return getUriFor(CONTENT_URI, name);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class NameValueCache {
        private final String mCallGetCommand;
        private final String mCallSetCommand;
        private final Uri mUri;

        NameValueCache(Uri uri, String getCommand, String setCommand) {
            this.mUri = uri;
            this.mCallGetCommand = getCommand;
            this.mCallSetCommand = setCommand;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:33:0x008a  */
        /* JADX WARN: Type inference failed for: r4v0 */
        /* JADX WARN: Type inference failed for: r4v1, types: [android.database.Cursor] */
        /* JADX WARN: Type inference failed for: r4v2 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        java.lang.String getString(android.content.ContentResolver r7, java.lang.String r8) {
            /*
                r6 = this;
                java.lang.String r0 = " from "
                java.lang.String r1 = "Can't get key "
                java.lang.String r2 = "CarControl"
                java.lang.String r3 = r6.mCallGetCommand
                r4 = 0
                if (r3 == 0) goto L1b
                android.net.Uri r5 = r6.mUri     // Catch: java.lang.Exception -> L1b
                android.os.Bundle r3 = r7.call(r5, r3, r8, r4)     // Catch: java.lang.Exception -> L1b
                if (r3 == 0) goto L1b
                java.lang.String r5 = "value"
                java.lang.String r7 = r3.getString(r5)     // Catch: java.lang.Exception -> L1b
                return r7
            L1b:
                android.net.Uri r3 = r6.mUri     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60
                android.net.Uri r3 = android.net.Uri.withAppendedPath(r3, r8)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60
                android.database.Cursor r7 = r7.query(r3, r4, r4, r4)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60
                if (r7 != 0) goto L4b
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
                r3.<init>()     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
                java.lang.StringBuilder r3 = r3.append(r1)     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
                java.lang.StringBuilder r3 = r3.append(r8)     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
                java.lang.StringBuilder r3 = r3.append(r0)     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
                android.net.Uri r5 = r6.mUri     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
                java.lang.StringBuilder r3 = r3.append(r5)     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
                java.lang.String r3 = r3.toString()     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
                android.util.Log.w(r2, r3)     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
                if (r7 == 0) goto L4a
                r7.close()
            L4a:
                return r4
            L4b:
                boolean r3 = r7.moveToNext()     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
                if (r3 == 0) goto L56
                r3 = 0
                java.lang.String r4 = r7.getString(r3)     // Catch: java.lang.Exception -> L5c java.lang.Throwable -> L86
            L56:
                if (r7 == 0) goto L5b
                r7.close()
            L5b:
                return r4
            L5c:
                r3 = move-exception
                goto L62
            L5e:
                r8 = move-exception
                goto L88
            L60:
                r3 = move-exception
                r7 = r4
            L62:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L86
                r5.<init>()     // Catch: java.lang.Throwable -> L86
                java.lang.StringBuilder r1 = r5.append(r1)     // Catch: java.lang.Throwable -> L86
                java.lang.StringBuilder r8 = r1.append(r8)     // Catch: java.lang.Throwable -> L86
                java.lang.StringBuilder r8 = r8.append(r0)     // Catch: java.lang.Throwable -> L86
                android.net.Uri r0 = r6.mUri     // Catch: java.lang.Throwable -> L86
                java.lang.StringBuilder r8 = r8.append(r0)     // Catch: java.lang.Throwable -> L86
                java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L86
                android.util.Log.w(r2, r8, r3)     // Catch: java.lang.Throwable -> L86
                if (r7 == 0) goto L85
                r7.close()
            L85:
                return r4
            L86:
                r8 = move-exception
                r4 = r7
            L88:
                if (r4 == 0) goto L8d
                r4.close()
            L8d:
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.provider.CarControl.NameValueCache.getString(android.content.ContentResolver, java.lang.String):java.lang.String");
        }

        boolean putString(ContentResolver resolver, String name, String value) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("value", value);
                resolver.call(this.mUri, this.mCallSetCommand, name, bundle);
                return true;
            } catch (Exception e) {
                Log.w("CarControl", "Can't set key " + name + " in " + this.mUri, e);
                return false;
            }
        }
    }
}
