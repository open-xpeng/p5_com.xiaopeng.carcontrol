package com.xiaopeng.carcontrol.statistic;

import com.xiaopeng.carcontrol.statistic.StatisticData;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import com.xiaopeng.speech.protocol.bean.recommend.RecommendBean;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class StatisticUtils {
    private static final boolean ENABLE_BI_STATISTIC = true;
    private static final String STATISTIC_CAR_LIFE = "carlife";
    private static final String STATISTIC_KEY_EVENT = "systemui";
    private static final String STATISTIC_MODULE_HVAC = "hvac";
    private static final String STATISTIC_MODULE_NAME = "carcontrol";
    private static final String STATISTIC_STATUS_MODULE_NAME = "carcontrol_status";
    public static final int SWITCH_OFF = 2;
    public static final int SWITCH_ON = 1;
    private static final String TAG = "StatisticUtils";
    private static final String[] sParamKeysArray = {"drive_model", "energy_model", "EPS_model", "Analogue sound_on-off", "Analogue sound_model", "Rear seat belt_on-off", "usher_on-off", "locked_on-off", "unlocked_on-off", "Unlock feedback_on-off", "Instrument warning tone_model", " custom_menu", "rate-limiting_on-off", "wiper_model", "wheel_model", "ldw_on-off", "bsa_on-off", "rlvw_on-off", "lsla_on-off", "fcp_on-off", "hdc_on-off", "esp_on-off", "ahv_on-off", "ebw_on-off", "Vent-Seat_on-off", "Heat-Seat_on-off", "Temperature-sync_on-off", "wind-force_mode", "Blowing_mode", "auto_on-off", "int-loop_on-off", "a/c_on-off", " air-condition_on-off", RecommendBean.SHOW_TIME_RESULT, VuiConstants.ELEMENT_TYPE, "count", "source", "name", "time", "mode"};

    public static int getSwitchOnOff(boolean enable) {
        return enable ? 1 : 2;
    }

    public static void sendStatistic(PageEnum page, BtnEnum btn, Object... args) {
        sendData(page, btn, "carcontrol", args);
    }

    public static void sendHvacStatistic(PageEnum page, BtnEnum btn, Object... args) {
        sendData(page, btn, "hvac", args);
    }

    public static void sendCarStatusStatistic(PageEnum page, BtnEnum btn, Object... args) {
        sendData(page, btn, STATISTIC_STATUS_MODULE_NAME, args);
    }

    public static void sendKeyEventStatistic(PageEnum page, BtnEnum btn, Object... args) {
        sendData(page, btn, STATISTIC_KEY_EVENT, args);
    }

    public static void sendSpaceCapsuleStatistic(PageEnum page, BtnEnum btn, Object... args) {
        sendData(page, btn, STATISTIC_KEY_EVENT, args);
    }

    public static void sendCarLifeStatistic(PageEnum page, BtnEnum btn, Object... args) {
        sendData(page, btn, STATISTIC_CAR_LIFE, args);
    }

    private static void sendData(final PageEnum page, final BtnEnum btn, final String module, final Object... args) {
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.statistic.-$$Lambda$StatisticUtils$6Ce4DbX_JqtVwhbIYYBqt78OlZ8
            @Override // java.lang.Runnable
            public final void run() {
                StatisticUtils.lambda$sendData$0(BtnEnum.this, args, page, module);
            }
        }, 0L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sendData$0(final BtnEnum btn, final Object[] args, final PageEnum page, final String module) {
        HashMap hashMap = new HashMap();
        int[] paramIndexArray = btn.getParamIndexArray();
        if (paramIndexArray != null && args != null) {
            if (paramIndexArray.length != args.length) {
                LogUtils.w(TAG, " params key and value number not match!");
                return;
            }
            for (int i = 0; i < args.length; i++) {
                Object obj = args[i];
                if (!(obj instanceof Boolean) && !(obj instanceof Character) && !(obj instanceof Number) && !(obj instanceof String)) {
                    LogUtils.w(TAG, "args type must be Boolen, Character, Number, String");
                    return;
                }
                hashMap.put(getParamKey(paramIndexArray[i]), obj);
            }
        }
        StatisticData.Builder buttonId = new StatisticData.Builder().pageId(page.getPageId()).pageName(page.getPageName()).buttonId(btn.getBtnId());
        if (hashMap.size() > 0) {
            buttonId.setParms(hashMap);
        }
        sendStatistic(buttonId.build(), module);
    }

    private static String getParamKey(int index) {
        String[] strArr = sParamKeysArray;
        if (index > strArr.length) {
            LogUtils.w(TAG, " index out of range sParamKeysArray.length");
            return "Valid Data";
        }
        return strArr[index];
    }

    private static IDataLog getDataLog() {
        return (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
    }

    private static void sendStatistic(StatisticData data, String module) {
        LogUtils.d(TAG, "sendStatistic pid=" + data.getPageId() + " bid=" + data.getButtonId() + " param=" + data.getParms() + " module=" + module);
        IDataLog dataLog = getDataLog();
        IMoleEventBuilder buttonId = dataLog.buildMoleEvent().setModule(module).setPageId(data.getPageId()).setButtonId(data.getButtonId());
        if (data.getParms() != null) {
            for (Map.Entry<String, Object> entry : data.getParms().entrySet()) {
                if (entry.getValue() instanceof String) {
                    buttonId.setProperty(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Number) {
                    buttonId.setProperty(entry.getKey(), (Number) entry.getValue());
                } else if (entry.getValue() instanceof Character) {
                    buttonId.setProperty(entry.getKey(), ((Character) entry.getValue()).charValue());
                } else if (entry.getValue() instanceof Boolean) {
                    buttonId.setProperty(entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
                } else {
                    LogUtils.w(TAG, " params type invalid");
                }
            }
        }
        dataLog.sendStatData(buttonId.build());
    }
}
