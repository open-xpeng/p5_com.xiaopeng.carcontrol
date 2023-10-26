package com.xiaopeng.carcontrol.provider;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
final class SettingsState {
    private static final String LOG_TAG = "SettingsState";
    private static final String NULL_VALUE = "null";
    private static final int SETTINGS_TYPE_MASK = -268435456;
    static final int SETTINGS_TYPE_PRIVATE = 2;
    static final int SETTINGS_TYPE_QUICK = 0;
    private static final int SETTINGS_TYPE_SHIFT = 28;
    static final int SETTINGS_TYPE_SYSTEM = 1;
    public static final int VERSION_UNDEFINED = -1;
    private final int mKey;
    private final ConcurrentHashMap<String, Setting> mSettings = new ConcurrentHashMap<>();
    private final Setting mNullSetting = new Setting(null, null, false) { // from class: com.xiaopeng.carcontrol.provider.SettingsState.1
        @Override // com.xiaopeng.carcontrol.provider.SettingsState.Setting
        public boolean isNull() {
            return true;
        }
    };

    private static int getTypeFromKey(int key) {
        return key >>> 28;
    }

    private static int getUserIdFromKey(int key) {
        return key & 268435455;
    }

    private static int makeKey(int type, int userId) {
        return (type << 28) | userId;
    }

    private static String settingTypeToString(int type) {
        return type != 0 ? type != 1 ? "UNKNOWN" : "SETTINGS_SYSTEM" : "SETTINGS_QUICK";
    }

    public static String keyToString(int key) {
        return "Key[type=" + settingTypeToString(getTypeFromKey(key)) + "]";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SettingsState(int key) {
        this.mKey = key;
    }

    public Setting getNullSetting() {
        return this.mNullSetting;
    }

    public List<String> getSettingNamesLocked() {
        if (this.mSettings.size() > 0) {
            return new ArrayList(this.mSettings.keySet());
        }
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Setting getSettingLocked(String name) {
        if (TextUtils.isEmpty(name)) {
            return this.mNullSetting;
        }
        Setting setting = this.mSettings.get(name);
        if (setting != null) {
            return new Setting(setting);
        }
        return this.mNullSetting;
    }

    public boolean updateSettingLocked(String name, String value, String tag, boolean makeValue, String packageName) {
        if (hasSettingLocked(name)) {
            return insertSettingLocked(name, value, makeValue);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean insertSettingLocked(String name, String value, boolean makeDefault) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        this.mSettings.put(name, new Setting(name, value, makeDefault));
        return true;
    }

    public boolean deleteSettingLocked(String name) {
        if (TextUtils.isEmpty(name) || !hasSettingLocked(name)) {
            return false;
        }
        this.mSettings.remove(name);
        return true;
    }

    private boolean hasSettingLocked(String name) {
        return this.mSettings.containsKey(name);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class Setting {
        private String defaultValue;
        private String name;
        private String value;

        Setting(Setting other) {
            this.name = other.name;
            this.value = other.value;
            this.defaultValue = other.defaultValue;
        }

        Setting(String name, String value, boolean makeDefault) {
            this.name = name;
            update(value, makeDefault);
        }

        public Setting(String name, String value, String defaultValue) {
            init(name, SettingsState.NULL_VALUE.equals(value) ? null : value, defaultValue);
        }

        private void init(String name, String value, String defaultValue) {
            this.name = name;
            this.value = value;
            this.defaultValue = defaultValue;
        }

        public String getName() {
            return this.name;
        }

        public int getKey() {
            return SettingsState.this.mKey;
        }

        public String getValue() {
            return this.value;
        }

        public String getDefaultValue() {
            return this.defaultValue;
        }

        public boolean isNull() {
            return this.name == null || this.value == null;
        }

        public boolean reset() {
            return update(this.defaultValue, false);
        }

        public boolean update(String value, boolean setDefault) {
            if (SettingsState.NULL_VALUE.equals(value)) {
                value = null;
            }
            String str = this.defaultValue;
            if (setDefault && !Objects.equals(value, str)) {
                str = value;
            }
            if (Objects.equals(value, this.value) && Objects.equals(str, this.defaultValue)) {
                return false;
            }
            init(this.name, value, str);
            return true;
        }

        public String toString() {
            return "Setting{name=" + this.name + " value=" + this.value + (this.defaultValue != null ? " default=" + this.defaultValue : "") + "}";
        }
    }
}
