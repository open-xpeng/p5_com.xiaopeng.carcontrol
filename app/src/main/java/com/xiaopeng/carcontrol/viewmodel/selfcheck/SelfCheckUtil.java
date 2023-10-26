package com.xiaopeng.carcontrol.viewmodel.selfcheck;

import android.car.diagnostic.XpDiagnosticManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckDataResponse;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckItemKey;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckResult;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IDiagnosticController;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel;
import com.xiaopeng.carcontrolmodule.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class SelfCheckUtil {
    private static final String KEY_SETTINGS_SYSTEM_CAR_CHECKED = "info_car_check_enable";
    private static final String TAG = "SelfCheckUtil";
    private static final ContentObserver mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.SelfCheckUtil.1
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            LogUtils.d(SelfCheckUtil.TAG, "Selfcheck onChange: " + uri.getLastPathSegment() + ", selfChange: " + selfChange);
            SelfCheckUtil.checkSetting();
        }
    };

    public static String level2Str(int level) {
        String string = ResUtils.getString(R.string.selfcheck_level_no_fault);
        if (level == 1) {
            return ResUtils.getString(R.string.selfcheck_level_normal);
        }
        if (level == 2) {
            return ResUtils.getString(R.string.selfcheck_level_slight);
        }
        return level == 3 ? ResUtils.getString(R.string.selfcheck_level_severity) : string;
    }

    public static CheckDataResponse getCheckResult(String key) throws Exception {
        if (SystemProperties.getBoolean("persist.sys.selftest.mock", false)) {
            return mockResultData(key, !SystemProperties.getBoolean("persist.sys.selftest.ok", true));
        }
        if (BaseFeatureOption.getInstance().isSupportNewSelfCheckArch()) {
            return getRealResultForNewArch(key);
        }
        return getRealResultForOldArch(key);
    }

    public static void playTts(String tts) {
        LogUtils.i(TAG, " playTts tts=" + tts);
        SpeechHelper.getInstance().speak(tts);
    }

    private static CheckDataResponse mockResultData(String key, boolean ok) {
        CheckDataResponse checkDataResponse = new CheckDataResponse();
        checkDataResponse.setHasIssue(ok);
        checkDataResponse.addDetailResult(key, -1);
        return checkDataResponse;
    }

    private static CheckDataResponse getCommonResult(XpDiagnosticManager manager, String key) {
        char c;
        CheckDataResponse checkDataResponse = new CheckDataResponse();
        try {
            switch (key.hashCode()) {
                case -1854568371:
                    if (key.equals(CheckItemKey.ID_BCM_RIGHT_TRUN_LAMP_FAIL)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 359137267:
                    if (key.equals(CheckItemKey.ID_BCM_SYSTEM_ERROR)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 855187732:
                    if (key.equals(CheckItemKey.ID_ESP_ABS_FAULT)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1178783379:
                    if (key.equals(CheckItemKey.ID_BCM_LEFT_TRUN_LAMP_FAIL)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1361841427:
                    if (key.equals(CheckItemKey.ID_BCM_HIGH_BEAM_FAIL)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1519225041:
                    if (key.equals(CheckItemKey.ID_BCM_LOW_BEAM_FAIL)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1525365511:
                    if (key.equals(CheckItemKey.ID_VCU_GEAR_WARNING)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    int absFailureState = manager.getAbsFailureState();
                    checkDataResponse.setHasIssue(absFailureState == 1);
                    checkDataResponse.addDetailResult("1A7_ESP_ABSFault", absFailureState);
                    return checkDataResponse;
                case 1:
                    int gearWarningInfo = manager.getGearWarningInfo();
                    checkDataResponse.setHasIssue(gearWarningInfo == 2);
                    checkDataResponse.addDetailResult("182_VCU_GearWarning", gearWarningInfo);
                    return checkDataResponse;
                case 2:
                    int highBeamFailureState = manager.getHighBeamFailureState();
                    checkDataResponse.setHasIssue(highBeamFailureState == 1);
                    checkDataResponse.addDetailResult("318_BCM_HighBeamOutputFailure", highBeamFailureState);
                    return checkDataResponse;
                case 3:
                    int lowBeamFailureState = manager.getLowBeamFailureState();
                    checkDataResponse.setHasIssue(lowBeamFailureState == 1);
                    checkDataResponse.addDetailResult("318_BCM_LowBeamFailure", lowBeamFailureState);
                    return checkDataResponse;
                case 4:
                    int leftTurnLampFailureState = manager.getLeftTurnLampFailureState();
                    checkDataResponse.setHasIssue(leftTurnLampFailureState == 1);
                    checkDataResponse.addDetailResult("318_BCM_LTurnLampFailure", leftTurnLampFailureState);
                    return checkDataResponse;
                case 5:
                    int rightTurnLampFailureState = manager.getRightTurnLampFailureState();
                    checkDataResponse.setHasIssue(rightTurnLampFailureState == 1);
                    checkDataResponse.addDetailResult("318_BCM_RTurnLampFailure", rightTurnLampFailureState);
                    return checkDataResponse;
                case 6:
                    int bcmSystemFailureState = manager.getBcmSystemFailureState();
                    checkDataResponse.setHasIssue(bcmSystemFailureState == 1);
                    checkDataResponse.addDetailResult("266_PEPS_SysErrorWarming", bcmSystemFailureState);
                    return checkDataResponse;
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            checkDataResponse.setErrorCode(1);
            checkDataResponse.addDetailResult(key, -1);
            checkDataResponse.setHasIssue(false);
            return checkDataResponse;
        }
    }

    private static CheckDataResponse getRealResultForOldArch(String key) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        CheckDataResponse checkDataResponse = new CheckDataResponse();
        IDiagnosticController iDiagnosticController = (IDiagnosticController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_DIAGNOSTIC_SERVICE);
        char c = 65535;
        if (iDiagnosticController == null) {
            checkDataResponse.setErrorCode(2);
            checkDataResponse.setHasIssue(false);
            checkDataResponse.addDetailResult(key, -1);
            LogUtils.w(TAG, "DiagnosticController is null");
            return checkDataResponse;
        }
        XpDiagnosticManager manager = iDiagnosticController.getManager();
        if (manager == null) {
            LogUtils.w(TAG, "XpDiagnosticManager is null");
            return checkDataResponse;
        }
        CheckDataResponse commonResult = getCommonResult(manager, key);
        if (commonResult != null) {
            return commonResult;
        }
        int[] iArr = {-1};
        try {
            switch (key.hashCode()) {
                case -1726871167:
                    if (key.equals(CheckItemKey.ID_SCU_FCWAEB_SW)) {
                        c = 17;
                        break;
                    }
                    break;
                case -1654448954:
                    if (key.equals(CheckItemKey.ID_VCU_BATTERY_VOLTAGE_LOW)) {
                        c = 7;
                        break;
                    }
                    break;
                case -1504140257:
                    if (key.equals(CheckItemKey.ID_VCU_ELECTRIC_MOTOR_SYSTEM_OVERHEATING)) {
                        c = '\b';
                        break;
                    }
                    break;
                case -1286929214:
                    if (key.equals(CheckItemKey.ID_VCU_HVRLY_ADHESION_ST)) {
                        c = 11;
                        break;
                    }
                    break;
                case -1257148338:
                    if (key.equals(CheckItemKey.ID_VCU_WARNING_BATTERY_OVERHEATING)) {
                        c = 4;
                        break;
                    }
                    break;
                case -1042363756:
                    if (key.equals(CheckItemKey.ID_BCM_MSM_ERROR_INFO)) {
                        c = '\r';
                        break;
                    }
                    break;
                case -1036595255:
                    if (key.equals(CheckItemKey.ID_ESP_HDC_FAULT)) {
                        c = 0;
                        break;
                    }
                    break;
                case -829308812:
                    if (key.equals(CheckItemKey.ID_VCU_ELECTRIC_VACUUM_PUMP_ERROR)) {
                        c = '\n';
                        break;
                    }
                    break;
                case -769959596:
                    if (key.equals("ID_TPMS_SYSFAULTWARN")) {
                        c = 1;
                        break;
                    }
                    break;
                case -441873618:
                    if (key.equals("ID_SCU_BSD_SW")) {
                        c = 18;
                        break;
                    }
                    break;
                case 33432370:
                    if (key.equals(CheckItemKey.ID_VCU_BATTERY_PTC_ERROR_INFO)) {
                        c = 21;
                        break;
                    }
                    break;
                case 168263003:
                    if (key.equals(CheckItemKey.ID_CIU_SD_ST)) {
                        c = 15;
                        break;
                    }
                    break;
                case 460541645:
                    if (key.equals(CheckItemKey.ID_IPU_FAIL_ST)) {
                        c = '\t';
                        break;
                    }
                    break;
                case 651041954:
                    if (key.equals(CheckItemKey.ID_VCU_DCDC_ERROR)) {
                        c = 6;
                        break;
                    }
                    break;
                case 666167476:
                    if (key.equals(CheckItemKey.ID_HVAC_COMPRESSOR_ERROR_INFO)) {
                        c = 20;
                        break;
                    }
                    break;
                case 1091697309:
                    if (key.equals(CheckItemKey.ID_TPMS_TIRE_PRESSURE_SENSOR_STATUS_ALL)) {
                        c = 3;
                        break;
                    }
                    break;
                case 1110943716:
                    if (key.equals("ID_SCU_ISLC_SW")) {
                        c = 16;
                        break;
                    }
                    break;
                case 1332833546:
                    if (key.equals(CheckItemKey.ID_VCU_CCS_WORK_ERROR)) {
                        c = 5;
                        break;
                    }
                    break;
                case 1544474107:
                    if (key.equals(CheckItemKey.ID_TPMS_WARNING_TIRE_TEMPERATURE_ALL)) {
                        c = 2;
                        break;
                    }
                    break;
                case 1599012933:
                    if (key.equals(CheckItemKey.ID_CIU_DVR_STATUS)) {
                        c = 14;
                        break;
                    }
                    break;
                case 1724741693:
                    if (key.equals(CheckItemKey.ID_HVAC_PTC_ERROR)) {
                        c = 19;
                        break;
                    }
                    break;
                case 2120822715:
                    if (key.equals(CheckItemKey.ID_VCU_AGS_ERROR)) {
                        c = '\f';
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    iArr[0] = manager.getHdcFailureState();
                    checkDataResponse.setHasIssue(iArr[0] == 1);
                    checkDataResponse.addDetailResult("260_ESC_HDCFault", iArr[0]);
                    break;
                case 1:
                    iArr[0] = manager.getTpmsSysFailureState();
                    checkDataResponse.setHasIssue(iArr[0] == 1);
                    checkDataResponse.addDetailResult("TPMS_W_SystemFault", iArr[0]);
                    break;
                case 2:
                    iArr = manager.getAllTireTemperatureWarnings();
                    int length = iArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            z = false;
                        } else if (iArr[i] == 1) {
                            z = true;
                        } else {
                            i++;
                        }
                    }
                    checkDataResponse.setHasIssue(z);
                    checkDataResponse.setDetailResult(getDetailResult(key, iArr));
                    break;
                case 3:
                    iArr = manager.getAllTirePerssureSensorsFailureStates();
                    int length2 = iArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length2) {
                            z2 = false;
                        } else if (iArr[i2] == 1) {
                            z2 = true;
                        } else {
                            i2++;
                        }
                    }
                    checkDataResponse.setHasIssue(z2);
                    checkDataResponse.setDetailResult(getDetailResult(key, iArr));
                    break;
                case 4:
                    iArr[0] = manager.getBatteryOverheatingState();
                    checkDataResponse.setHasIssue(iArr[0] != 0);
                    checkDataResponse.addDetailResult("182_VCU_BatHot_Dsp", iArr[0]);
                    break;
                case 5:
                    iArr[0] = manager.getCcsWorkState();
                    checkDataResponse.setHasIssue(iArr[0] == 2);
                    checkDataResponse.addDetailResult("39C_CCS_WorkSt", iArr[0]);
                    break;
                case 6:
                    iArr[0] = manager.getDcdcFailureState();
                    checkDataResponse.setHasIssue(iArr[0] == 1);
                    checkDataResponse.addDetailResult("182_VCU_DCDCErr_Dsp", iArr[0]);
                    break;
                case 7:
                    iArr[0] = manager.getBatteryVoltageLowState();
                    checkDataResponse.setHasIssue(iArr[0] != 0);
                    checkDataResponse.addDetailResult("279_VCU_BatteryVoltLow_Dsp", iArr[0]);
                    break;
                case '\b':
                    iArr[0] = manager.getElectricMotorSystemHotState();
                    checkDataResponse.setHasIssue(iArr[0] != 0);
                    checkDataResponse.addDetailResult("182_VCU_EMotorSysHot_Dsp", iArr[0]);
                    break;
                case '\t':
                    iArr[0] = manager.getRearIpuFailureState();
                    checkDataResponse.setHasIssue(iArr[0] != 0);
                    checkDataResponse.addDetailResult("172_IPU_FailSt", iArr[0]);
                    break;
                case '\n':
                    iArr[0] = manager.getElectricVacuumPumpFailureState();
                    checkDataResponse.setHasIssue(iArr[0] != 0);
                    checkDataResponse.addDetailResult("182_VCU_EVacuumpumpErr_DSP", iArr[0]);
                    break;
                case 11:
                    iArr[0] = manager.getHighVoltageRelayAdhesionState();
                    checkDataResponse.setHasIssue(iArr[0] != 0);
                    checkDataResponse.addDetailResult("182_VCU_HVRlyAdhesionSt_Dsp", iArr[0]);
                    break;
                case '\f':
                    iArr[0] = manager.getAgsFailureState();
                    checkDataResponse.setHasIssue(iArr[0] != 0);
                    checkDataResponse.addDetailResult("279_VCU_AGS_Error_Dsp", iArr[0]);
                    break;
                case '\r':
                    iArr = manager.getAllMsmModulesFailureStates();
                    int length3 = iArr.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length3) {
                            z3 = false;
                        } else if (iArr[i3] != 0) {
                            z3 = true;
                        } else {
                            i3++;
                        }
                    }
                    checkDataResponse.setHasIssue(z3);
                    checkDataResponse.setDetailResult(getDetailResult(key, iArr));
                    break;
                case 14:
                    iArr[0] = manager.getDvrFailureState();
                    checkDataResponse.setHasIssue(iArr[0] != 0);
                    checkDataResponse.addDetailResult("311_CIU_CDU_DVR_St", iArr[0]);
                    break;
                case 15:
                    iArr[0] = manager.getCiuSdcardFailureState();
                    checkDataResponse.setHasIssue(iArr[0] != 0);
                    checkDataResponse.addDetailResult("311_CIU_CDU_SD_St", iArr[0]);
                    break;
                case 16:
                    iArr[0] = manager.getIslcState();
                    if (iArr[0] != 6 && iArr[0] != 7) {
                        z4 = false;
                        checkDataResponse.setHasIssue(z4);
                        checkDataResponse.addDetailResult("324_SCU_CDU_SLASt", iArr[0]);
                        break;
                    }
                    z4 = true;
                    checkDataResponse.setHasIssue(z4);
                    checkDataResponse.addDetailResult("324_SCU_CDU_SLASt", iArr[0]);
                    break;
                case 17:
                    iArr[0] = manager.getAebState();
                    if (iArr[0] != 2 && iArr[0] != 3) {
                        z5 = false;
                        checkDataResponse.setHasIssue(z5);
                        checkDataResponse.addDetailResult("324_SCU_CDU_AEBSt", iArr[0]);
                        break;
                    }
                    z5 = true;
                    checkDataResponse.setHasIssue(z5);
                    checkDataResponse.addDetailResult("324_SCU_CDU_AEBSt", iArr[0]);
                    break;
                case 18:
                    iArr[0] = manager.getBsdState();
                    checkDataResponse.setHasIssue(iArr[0] == 2);
                    checkDataResponse.addDetailResult("324_SCU_CDU_BSDSt", iArr[0]);
                    break;
                case 19:
                    iArr[0] = manager.getHvacPtcFailureState();
                    checkDataResponse.setHasIssue(iArr[0] != 0);
                    checkDataResponse.addDetailResult("522_HVAC_HeaterErrorH", iArr[0]);
                    break;
                case 20:
                    iArr = manager.getHvacCompressorFailureStates();
                    int length4 = iArr.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length4) {
                            z6 = false;
                        } else if (iArr[i4] != 0) {
                            z6 = true;
                        } else {
                            i4++;
                        }
                    }
                    checkDataResponse.setHasIssue(z6);
                    checkDataResponse.setDetailResult(getDetailResult(key, iArr));
                    break;
                case 21:
                    iArr = manager.getBatteryPtcFailureStates();
                    int length5 = iArr.length;
                    int i5 = 0;
                    while (true) {
                        if (i5 >= length5) {
                            z7 = false;
                        } else if (iArr[i5] != 0) {
                            z7 = true;
                        } else {
                            i5++;
                        }
                    }
                    checkDataResponse.setHasIssue(z7);
                    checkDataResponse.setDetailResult(getDetailResult(key, iArr));
                    break;
            }
            LogUtils.i(TAG, "Key = " + key + ", result = " + iArr[0], false);
        } catch (Exception e) {
            e.printStackTrace();
            checkDataResponse.setErrorCode(1);
            checkDataResponse.addDetailResult(key, iArr[0]);
            checkDataResponse.setHasIssue(false);
        }
        return checkDataResponse;
    }

    private static CheckDataResponse getRealResultForNewArch(String key) {
        char c;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        CheckDataResponse checkDataResponse = new CheckDataResponse();
        IDiagnosticController iDiagnosticController = (IDiagnosticController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_DIAGNOSTIC_SERVICE);
        int i = -1;
        if (iDiagnosticController == null) {
            checkDataResponse.setErrorCode(2);
            checkDataResponse.setHasIssue(false);
            checkDataResponse.addDetailResult(key, -1);
            LogUtils.w(TAG, "DiagnosticController is null");
            return checkDataResponse;
        }
        XpDiagnosticManager manager = iDiagnosticController.getManager();
        if (manager == null) {
            LogUtils.w(TAG, "XpDiagnosticManager is null");
            return checkDataResponse;
        }
        CheckDataResponse commonResult = getCommonResult(manager, key);
        if (commonResult != null) {
            return commonResult;
        }
        try {
            switch (key.hashCode()) {
                case -1273072856:
                    if (key.equals(CheckItemKey.ID_SRS_AIRBAG_FAULT)) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1248018042:
                    if (key.equals(CheckItemKey.ID_VCU_POWER_LIMITATION)) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -884201948:
                    if (key.equals(CheckItemKey.ID_ESP_ESP_FAULT)) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case -856356693:
                    if (key.equals(CheckItemKey.ID_MSMP_ECU_ERROR)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -788193105:
                    if (key.equals(CheckItemKey.ID_BCM_LRDTR_FAIL)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -769959596:
                    if (key.equals("ID_TPMS_SYSFAULTWARN")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -735678126:
                    if (key.equals(CheckItemKey.ID_SRR_RR_FAULT)) {
                        c = ',';
                        break;
                    }
                    c = 65535;
                    break;
                case -641739617:
                    if (key.equals(CheckItemKey.ID_MSMD_ECU_ERROR)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -575483676:
                    if (key.equals(CheckItemKey.ID_BCM_PARKING_LAMP_FAIL)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -535885482:
                    if (key.equals(CheckItemKey.ID_ESP_WARN_LAMP)) {
                        c = '!';
                        break;
                    }
                    c = 65535;
                    break;
                case -521061050:
                    if (key.equals(CheckItemKey.ID_SRR_FR_FAULT)) {
                        c = '*';
                        break;
                    }
                    c = 65535;
                    break;
                case -441873618:
                    if (key.equals("ID_SCU_BSD_SW")) {
                        c = '%';
                        break;
                    }
                    c = 65535;
                    break;
                case -393425249:
                    if (key.equals(CheckItemKey.ID_IPUF_FAULT)) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case -387743371:
                    if (key.equals(CheckItemKey.ID_SRR_DOW)) {
                        c = '&';
                        break;
                    }
                    c = 65535;
                    break;
                case -371412806:
                    if (key.equals(CheckItemKey.ID_SCU_ICM_APA_ERROR)) {
                        c = '0';
                        break;
                    }
                    c = 65535;
                    break;
                case -337376966:
                    if (key.equals(CheckItemKey.ID_XPU_XPU_FAULT)) {
                        c = '#';
                        break;
                    }
                    c = 65535;
                    break;
                case -332816843:
                    if (key.equals(CheckItemKey.ID_BCM_RRDTR_FAIL)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -323181207:
                    if (key.equals(CheckItemKey.ID_TPMS_ABNORMAL_PR_WARN)) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -251687874:
                    if (key.equals(CheckItemKey.ID_XPU_IHB)) {
                        c = '$';
                        break;
                    }
                    c = 65535;
                    break;
                case -232738682:
                    if (key.equals(CheckItemKey.ID_AVAS_FAULT)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -179560749:
                    if (key.equals(CheckItemKey.ID_VCU_EVERR_LAMP_DSP)) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 1982491:
                    if (key.equals(CheckItemKey.ID_SRR_RCW)) {
                        c = '(';
                        break;
                    }
                    c = 65535;
                    break;
                case 57804423:
                    if (key.equals(CheckItemKey.ID_SRR_RCTA)) {
                        c = '\'';
                        break;
                    }
                    c = 65535;
                    break;
                case 70032787:
                    if (key.equals(CheckItemKey.ID_ALS_ERROR)) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 179050165:
                    if (key.equals(CheckItemKey.ID_ESP_AVH_FAULT)) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case 312301735:
                    if (key.equals(CheckItemKey.ID_VCU_CHG_PORT_HOT)) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case 386326051:
                    if (key.equals(CheckItemKey.ID_VCU_EBS_ERROR)) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 479337471:
                    if (key.equals(CheckItemKey.ID_SCU_ACC_FAULT)) {
                        c = '-';
                        break;
                    }
                    c = 65535;
                    break;
                case 490862812:
                    if (key.equals(CheckItemKey.ID_MSMD_VENTILATION_MOTOR_ERROR)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 499672924:
                    if (key.equals(CheckItemKey.ID_VCU_WATER_SENSOR_ERROR)) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 503735636:
                    if (key.equals(CheckItemKey.ID_CDC_DATAUPLOAD_ST)) {
                        c = '\"';
                        break;
                    }
                    c = 65535;
                    break;
                case 578112485:
                    if (key.equals(CheckItemKey.ID_MSMD_HEATING_ERROR)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 596603425:
                    if (key.equals(CheckItemKey.ID_DHC_ERROR)) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 677013623:
                    if (key.equals(CheckItemKey.ID_VCU_OBC_MSG_LOST)) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case 688182067:
                    if (key.equals(CheckItemKey.ID_VCU_LIQUID_TEMP_HIGHT_ERROR)) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 844275853:
                    if (key.equals(CheckItemKey.ID_ESP_APB_ERROR)) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case 1073413018:
                    if (key.equals(CheckItemKey.ID_BCM_REARFLOG_FAIL)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1110943716:
                    if (key.equals("ID_SCU_ISLC_SW")) {
                        c = '.';
                        break;
                    }
                    c = 65535;
                    break;
                case 1124266902:
                    if (key.equals(CheckItemKey.ID_IPUR_FAULT)) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case 1352243697:
                    if (key.equals(CheckItemKey.ID_MSMP_HEATING_ERROR)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 1479327231:
                    if (key.equals(CheckItemKey.ID_SCU_ALC_FAULT)) {
                        c = '/';
                        break;
                    }
                    c = 65535;
                    break;
                case 1649292636:
                    if (key.equals(CheckItemKey.ATLS_ERROR)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1655946501:
                    if (key.equals(CheckItemKey.ID_VCU_THERMO_RUN_AWAY_ERROR)) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 1692361752:
                    if (key.equals(CheckItemKey.ID_SRR_RL_FAULT)) {
                        c = '+';
                        break;
                    }
                    c = 65535;
                    break;
                case 1710461341:
                    if (key.equals(CheckItemKey.ID_VCU_DEAD_BATTERY)) {
                        c = JSONLexer.EOI;
                        break;
                    }
                    c = 65535;
                    break;
                case 1752491413:
                    if (key.equals(CheckItemKey.ID_IBT_FAULT)) {
                        c = ' ';
                        break;
                    }
                    c = 65535;
                    break;
                case 1906978828:
                    if (key.equals(CheckItemKey.ID_SRR_FL_FAULT)) {
                        c = ')';
                        break;
                    }
                    c = 65535;
                    break;
                case 1931880937:
                    if (key.equals(CheckItemKey.LLU_ERROR)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2116591362:
                    if (key.equals(CheckItemKey.ID_VCU_CRUISE_ERROR)) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            String str = "224_iBCM_RDTROutputStFailure";
            switch (c) {
                case 0:
                    str = "224_iBCM_RearFogFailure";
                    i = manager.getRearFogLampFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 1:
                    i = manager.getRightDaytimeRunningLightFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 2:
                    i = manager.getLeftDaytimeRunningLightFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 3:
                    str = "224_iBCM_ParkingLampOutputFailure";
                    i = manager.getParkingLampFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 4:
                    str = "323_LLU_ErrorSt";
                    i = manager.getLluFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 5:
                    str = "31D_ATLS_ErrSt";
                    i = manager.getAtlsFailureSate();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 6:
                    str = "318_MSMD_ECUErr";
                    i = manager.getDriverMsmFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 7:
                    str = "318_MSMD_VentilationMotorErr";
                    i = manager.getDriverMsmVentilationMotorFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case '\b':
                    str = "318_MSMD_HeatingSysErr";
                    i = manager.getDriverMsmHeatSysFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case '\t':
                    str = "31A_MSMP_ECUErr";
                    i = manager.getPassengerMsmFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case '\n':
                    str = "31A_MSMP_HeatingSysErr";
                    i = manager.getPassengerMsmHeatSysFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 11:
                    str = "2D1_AVAS_FaultSt";
                    i = manager.getAvasFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case '\f':
                    str = "3B4_TPMS_SysFaultWarn";
                    i = manager.getTpmsSysFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case '\r':
                    str = "3B4_TPMS_AbnormalPrWarn";
                    i = manager.getAbnormalTirePressureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 14:
                    str = "180_SRS_AirbagFaultSt";
                    i = manager.getAirbagFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 15:
                    str = "322_ALS_ErrorSt";
                    i = manager.getAlsFailureState();
                    checkDataResponse.setHasIssue(i != 0);
                    break;
                case 16:
                    str = "31C_DHC_Err";
                    i = manager.getDhcFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 17:
                    str = "24A_VCU_EVErrLampDisp";
                    i = manager.getEvSysFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 18:
                    str = "24A_VCU_EBS_Error_Dsp";
                    i = manager.getEbsFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 19:
                    str = "24A_VCU_LiquidLevelHighTempErr";
                    i = manager.getLiquidHighTempState();
                    checkDataResponse.setHasIssue(i != 0);
                    break;
                case 20:
                    str = "24A_VCU_WaterSensorErr";
                    i = manager.getWaterSensorFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 21:
                    str = "24A_VCU_bCruiseErr_Dsp";
                    i = manager.getBCruiseFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 22:
                    str = "24A_VCU_ThermorunawaySt";
                    i = manager.getThermalRunawayState();
                    checkDataResponse.setHasIssue(i != 0);
                    break;
                case 23:
                    str = "24A_VCU_PowerLimitationDisp";
                    i = manager.getPowerLimitationState();
                    checkDataResponse.setHasIssue(i != 0);
                    break;
                case 24:
                    str = "24A_VCU_ChgPortHot_Dsp";
                    i = manager.getChargePortHotState();
                    checkDataResponse.setHasIssue(i != 0);
                    break;
                case 25:
                    str = "24A_VCU_OBCMsgLost";
                    i = manager.getObcMsgLostState();
                    checkDataResponse.setHasIssue(i != 0);
                    break;
                case 26:
                    str = "24A_VCU_DeadBatteryFlag";
                    i = manager.getBatteryDeadState();
                    checkDataResponse.setHasIssue(i != 0);
                    break;
                case 27:
                    str = "IPUR_FailSt";
                    i = manager.getRearIpuFailureState();
                    if (i != 1 && i != 2 && i != 3) {
                        z = false;
                        checkDataResponse.setHasIssue(z);
                        break;
                    }
                    z = true;
                    checkDataResponse.setHasIssue(z);
                case 28:
                    str = "IPUF_FailSt";
                    i = manager.getFrontIpuFailureState();
                    if (i != 1 && i != 2 && i != 3) {
                        z2 = false;
                        checkDataResponse.setHasIssue(z2);
                        break;
                    }
                    z2 = true;
                    checkDataResponse.setHasIssue(z2);
                case 29:
                    str = "1A7_ESP_ESPFault";
                    i = manager.getEspFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 30:
                    str = "1B2_ESP_APBErrSt";
                    i = manager.getApbFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case 31:
                    str = "1A7_ESP_AVHFault";
                    i = manager.getAvhFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case ' ':
                    str = "18A_IBT_FailureLampReq";
                    i = manager.getIbtFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case '!':
                    str = "1B0_EPS_WarnLamp";
                    i = manager.getEpsFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case '\"':
                    str = "2A6_CDC_DataUpload_St";
                    i = manager.getCdcFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case '#':
                    str = "234_XPU_XPU_FailureSt";
                    i = manager.getXpuFailureState();
                    checkDataResponse.setHasIssue(i == 1);
                    break;
                case '$':
                    str = "229_XPU_IHBSt";
                    i = manager.getIhbState();
                    if (i != 4 && i != 5) {
                        z3 = false;
                        checkDataResponse.setHasIssue(z3);
                        break;
                    }
                    z3 = true;
                    checkDataResponse.setHasIssue(z3);
                case '%':
                    str = "277_SRR_BSDSt";
                    i = manager.getBsdState();
                    checkDataResponse.setHasIssue(i == 2);
                    break;
                case '&':
                    str = "277_SRR_DOWSt";
                    i = manager.getDowState();
                    checkDataResponse.setHasIssue(i == 2);
                    break;
                case '\'':
                    str = "277_SRR_RCTASt";
                    i = manager.getRctaState();
                    checkDataResponse.setHasIssue(i == 2);
                    break;
                case '(':
                    str = "277_SRR_RCWSt";
                    i = manager.getRcwState();
                    checkDataResponse.setHasIssue(i == 2);
                    break;
                case ')':
                    str = "277_SRR_FLFailureSt";
                    i = manager.getFlSrrFailureState();
                    checkDataResponse.setHasIssue(i == 2);
                    break;
                case '*':
                    str = "277_SRR_FRFailureSt";
                    i = manager.getFrSrrFailureState();
                    checkDataResponse.setHasIssue(i == 2);
                    break;
                case '+':
                    str = "277_SRR_RLFailureSt";
                    i = manager.getRlSrrFailureState();
                    checkDataResponse.setHasIssue(i == 2);
                    break;
                case ',':
                    str = "277_SRR_RRFailureSt";
                    i = manager.getRrSrrFailureState();
                    checkDataResponse.setHasIssue(i == 2);
                    break;
                case '-':
                    str = "232_XPU_LongCtrlRemind";
                    i = manager.getScuLongCtrlRemindInfo();
                    checkDataResponse.setHasIssue(i == 9);
                    break;
                case '.':
                    str = "232_XPU_ISLCSt";
                    i = manager.getIslcState();
                    if (i != 6 && i != 7) {
                        z4 = false;
                        checkDataResponse.setHasIssue(z4);
                        break;
                    }
                    z4 = true;
                    checkDataResponse.setHasIssue(z4);
                case '/':
                    str = "232_XPU_ALCCtrlRemind";
                    i = manager.getAlcCtrlRemindInfo();
                    checkDataResponse.setHasIssue(i == 10);
                    break;
                case '0':
                    str = "2B8_SCU_ICM_APAErrorTips";
                    i = manager.getApaFailureState();
                    checkDataResponse.setHasIssue(i == 2);
                    break;
                default:
                    str = key;
                    break;
            }
            checkDataResponse.addDetailResult(str, i);
        } catch (Exception e) {
            LogUtils.e(TAG, " check error key=" + key);
            e.printStackTrace();
            checkDataResponse.setErrorCode(1);
            checkDataResponse.addDetailResult(key, i);
            checkDataResponse.setHasIssue(false);
        }
        return checkDataResponse;
    }

    public static void startCheck() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.-$$Lambda$SelfCheckUtil$Lq6MQjd1uZ2rnC7nVZUjrPM0J1E
            @Override // java.lang.Runnable
            public final void run() {
                SelfCheckUtil.lambda$startCheck$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$startCheck$0() {
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            ISelfCheckViewModel iSelfCheckViewModel = (ISelfCheckViewModel) ViewModelManager.getInstance().getViewModelImpl(ISelfCheckViewModel.class);
            if (((IMcuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_MCU_SERVICE)).getIgStatusFromMcu() != 1 || CheckCarMode.getInstance().isChecking()) {
                return;
            }
            iSelfCheckViewModel.startSelfCheck();
        }
    }

    public static void stopCheck() {
        if (CheckCarMode.getInstance().isChecking() || CheckCarMode.getInstance().isCheckComplete()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.-$$Lambda$SelfCheckUtil$rwzoBdgPx92DSJyMi1u-0j5DmNU
                @Override // java.lang.Runnable
                public final void run() {
                    SelfCheckUtil.lambda$stopCheck$1();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$stopCheck$1() {
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            ((ISelfCheckViewModel) ViewModelManager.getInstance().getViewModelImpl(ISelfCheckViewModel.class)).stopSelfCheck();
        }
    }

    public static void receiveGerrt() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.-$$Lambda$SelfCheckUtil$1wfYxukPc3wDHoxOHzbWvj3c7uE
            @Override // java.lang.Runnable
            public final void run() {
                SelfCheckUtil.lambda$receiveGerrt$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$receiveGerrt$2() {
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            CheckCarMode.getInstance().receiveGreet();
        }
    }

    private static List<CheckResult> getDetailResult(String key, int[] result) {
        ArrayList arrayList = new ArrayList();
        key.hashCode();
        char c = 65535;
        switch (key.hashCode()) {
            case -1042363756:
                if (key.equals(CheckItemKey.ID_BCM_MSM_ERROR_INFO)) {
                    c = 0;
                    break;
                }
                break;
            case 33432370:
                if (key.equals(CheckItemKey.ID_VCU_BATTERY_PTC_ERROR_INFO)) {
                    c = 1;
                    break;
                }
                break;
            case 666167476:
                if (key.equals(CheckItemKey.ID_HVAC_COMPRESSOR_ERROR_INFO)) {
                    c = 2;
                    break;
                }
                break;
            case 1091697309:
                if (key.equals(CheckItemKey.ID_TPMS_TIRE_PRESSURE_SENSOR_STATUS_ALL)) {
                    c = 3;
                    break;
                }
                break;
            case 1544474107:
                if (key.equals(CheckItemKey.ID_TPMS_WARNING_TIRE_TEMPERATURE_ALL)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (result.length != 13) {
                    LogUtils.e(TAG, " getDetailResult key=" + key + " result size is wrong, size=" + result.length);
                    return arrayList;
                }
                arrayList.add(new CheckResult("380_MSM_TiltingForwardSW_Err", result[0]));
                arrayList.add(new CheckResult("380_MSM_TiltingBackwardSW_Err", result[1]));
                arrayList.add(new CheckResult("380_MSM_HorizonalForwardSW_Err", result[2]));
                arrayList.add(new CheckResult("380_MSM_HorizonalBackwardSW_Err", result[3]));
                arrayList.add(new CheckResult("380_MSM_VerticalUpwardSW_Err", result[4]));
                arrayList.add(new CheckResult("380_MSM_VerticalDownwardSW_Err", result[5]));
                arrayList.add(new CheckResult("380_MSM_HorizonalMotor_Err", result[6]));
                arrayList.add(new CheckResult("380_MSM_VerticalMotor_Err", result[7]));
                arrayList.add(new CheckResult("380_MSM_TiltingMotor_Err", result[8]));
                arrayList.add(new CheckResult("380_MSM_HorizonalHall_Err", result[9]));
                arrayList.add(new CheckResult("380_MSM_VerticalHall_Err", result[10]));
                arrayList.add(new CheckResult("380_MSM_TiltingHall_Err", result[11]));
                arrayList.add(new CheckResult("380_MSM_ECU_Err", result[12]));
                break;
            case 1:
                if (result.length != 2) {
                    LogUtils.e(TAG, " getDetailResult key=" + key + " result size is wrong, size=" + result.length);
                    return arrayList;
                }
                arrayList.add(new CheckResult("36E_PTC025_InternalError", result[0]));
                arrayList.add(new CheckResult("36E_PTC025_ElementError", result[1]));
                break;
            case 2:
                if (result.length != 2) {
                    LogUtils.e(TAG, " getDetailResult key=" + key + " result size is wrong, size=" + result.length);
                    return arrayList;
                }
                arrayList.add(new CheckResult("369_Ecomp_CompStt_Fault", result[0]));
                arrayList.add(new CheckResult("369_Ecomp_FaultCause_SC", result[1]));
                break;
            case 3:
                if (result.length != 4) {
                    LogUtils.e(TAG, " getDetailResult key=" + key + " result size is wrong, size=" + result.length);
                    return arrayList;
                }
                arrayList.add(new CheckResult("33A_TPMS_FL_SensorStatus", result[0]));
                arrayList.add(new CheckResult("33A_TPMS_FR_SensorStatus", result[1]));
                arrayList.add(new CheckResult("33A_TPMS_RL_SensorStatus", result[2]));
                arrayList.add(new CheckResult("33A_TPMS_RR_SensorStatus", result[3]));
                break;
            case 4:
                if (result.length != 4) {
                    LogUtils.e(TAG, " getDetailResult key=" + key + " result size is wrong, size=" + result.length);
                    return arrayList;
                }
                arrayList.add(new CheckResult("TPMS_W_TempFL", result[0]));
                arrayList.add(new CheckResult("TPMS_W_TempFR", result[1]));
                arrayList.add(new CheckResult("TPMS_W_TempRL", result[2]));
                arrayList.add(new CheckResult("TPMS_W_TempRR", result[3]));
                break;
        }
        return arrayList;
    }

    public static void startMonitor() {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        checkSetting();
        contentResolver.registerContentObserver(Settings.System.getUriFor(KEY_SETTINGS_SYSTEM_CAR_CHECKED), false, mContentObserver);
    }

    public static void checkSetting() {
        if (BaseFeatureOption.getInstance().isSupportNewSelfCheckArch()) {
            int i = Settings.System.getInt(App.getInstance().getContentResolver(), KEY_SETTINGS_SYSTEM_CAR_CHECKED, 0);
            LogUtils.d(TAG, "checkSetting, value = " + i, false);
            if (i == 1) {
                startCheck();
            } else {
                stopCheck();
            }
        }
    }
}
