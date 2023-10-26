package com.xiaopeng.carcontrol.viewmodel.meter;

import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import com.google.gson.Gson;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.bean.MaintainInfo;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IIcmController;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ModuleUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.meter.MeterBaseViewModel;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.utils.SystemPropertyUtil;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class MeterBaseViewModel implements IMeterViewModel, IIcmController.Callback {
    private static final String TAG = "MeterBaseViewModel";
    private ContentObserver mContentObserver;
    private final SimpleDateFormat mSdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    IIcmController mIcmController = (IIcmController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_ICM_SERVICE);

    protected abstract void handleDoorKeyChanged(int keyValue);

    protected abstract void handleEcallAvailableChanged(boolean available);

    protected abstract void handleLastMaintainChanged(MaintainInfo last);

    protected abstract void handleNextMaintainChanged(MaintainInfo next);

    protected abstract void handleXKeyChanged(int keyValue);

    /* JADX INFO: Access modifiers changed from: package-private */
    public void registerContentObserver() {
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.viewmodel.meter.MeterBaseViewModel.1
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    LogUtils.d(MeterBaseViewModel.TAG, "onChange: " + uri);
                    String lastPathSegment = uri.getLastPathSegment();
                    if (lastPathSegment != null) {
                        lastPathSegment.hashCode();
                        char c = 65535;
                        switch (lastPathSegment.hashCode()) {
                            case -1642251909:
                                if (lastPathSegment.equals(CarControl.PrivateControl.CUSTOM_KEY_WHEEL)) {
                                    c = 0;
                                    break;
                                }
                                break;
                            case 500654478:
                                if (lastPathSegment.equals("key_door")) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case 1405062886:
                                if (lastPathSegment.equals(IMeterViewModel.SETTINGS_KEY_ECALL_AVAILABLE)) {
                                    c = 2;
                                    break;
                                }
                                break;
                        }
                        switch (c) {
                            case 0:
                                MeterBaseViewModel.this.handleXKeyChanged(MeterBaseViewModel.this.getIntValue(lastPathSegment));
                                return;
                            case 1:
                                MeterBaseViewModel.this.handleDoorKeyChanged(MeterBaseViewModel.this.getIntValue(lastPathSegment));
                                return;
                            case 2:
                                MeterBaseViewModel meterBaseViewModel = MeterBaseViewModel.this;
                                meterBaseViewModel.handleEcallAvailableChanged(meterBaseViewModel.isEcallAvailable());
                                return;
                            default:
                                return;
                        }
                    }
                }
            };
        }
        LogUtils.d(TAG, "registerContentObserver");
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.CUSTOM_KEY_WHEEL), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor("key_door"), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(Settings.Secure.getUriFor(IMeterViewModel.SETTINGS_KEY_ECALL_AVAILABLE), false, this.mContentObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getIntValue(String key) {
        return CarControl.PrivateControl.getInt(App.getInstance().getContentResolver(), key, 0);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public String getMileageTotal() {
        return formatMileage(this.mIcmController.getMileageTotal(), 0);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public String getMileageSinceLastCharge() {
        return formatMileage(this.mIcmController.getMileageSinceLastCharge(), 1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public String getMileageSinceStartUp() {
        return formatMileage(this.mIcmController.getMileageSinceStartUp(), 1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public String getMileageA() {
        return formatMileage(this.mIcmController.getMileageA(), 1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void resetMeterMileageA() {
        this.mIcmController.resetMeterMileageA();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public String getMileageB() {
        return formatMileage(this.mIcmController.getMileageB(), 1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void resetMeterMileageB() {
        this.mIcmController.resetMeterMileageB();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void requestMaintainData() {
        LogUtils.d(TAG, "Start to request maintain data", false);
        HashMap hashMap = new HashMap();
        hashMap.put("vin", SystemPropertyUtil.getVIN());
        String str = GlobalConstant.URL.MAINTENANCE_ADVICE;
        LogUtils.d(TAG, "Request maintain url: " + str, false);
        String json = new Gson().toJson(hashMap);
        IHttp http = ModuleUtils.getHttp();
        if (http == null) {
            LogUtils.e(TAG, "Http module is null", false);
        } else {
            http.bizHelper().post(str, json).needAuthorizationInfo().enableSecurityEncoding().uid(String.valueOf(0)).build().execute(new AnonymousClass2());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.viewmodel.meter.MeterBaseViewModel$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass2 implements Callback {
        AnonymousClass2() {
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onSuccess(IResponse iResponse) {
            if (iResponse == null) {
                LogUtils.e(MeterBaseViewModel.TAG, "requestMaintainData response null", false);
                return;
            }
            final String body = iResponse.body();
            LogUtils.d(MeterBaseViewModel.TAG, "requestMaintainData success, response:" + body, false);
            if (iResponse.code() == 200) {
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.meter.-$$Lambda$MeterBaseViewModel$2$K52X23hyFwQsXQZIooJgGFiUDQ8
                    @Override // java.lang.Runnable
                    public final void run() {
                        MeterBaseViewModel.AnonymousClass2.this.lambda$onSuccess$0$MeterBaseViewModel$2(body);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onSuccess$0$MeterBaseViewModel$2(final String body) {
            MeterBaseViewModel.this.parseMaintainData(body);
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onFailure(IResponse iResponse) {
            LogUtils.e(MeterBaseViewModel.TAG, "requestMaintainData onFailure: " + iResponse.getException(), false);
            MeterBaseViewModel.this.handleLastMaintainChanged(null);
            MeterBaseViewModel.this.handleNextMaintainChanged(null);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onXKeyChanged(int keyValue) {
        handleXKeyChanged(keyValue);
        CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.CUSTOM_KEY_WHEEL, keyValue);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setXKeyForCustomer(int keyFunc) {
        if (App.isMainProcess()) {
            LogUtils.d(TAG, "setXKeyForCustomer: " + keyFunc);
            this.mIcmController.setXKeyForCustomer(keyFunc);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public int getXKeyForCustomer() {
        return this.mIcmController.getXKeyForCustomer();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onDoorKeyChanged(int keyValue) {
        handleDoorKeyChanged(keyValue);
        CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), "key_door", keyValue);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setDoorKeyForCustomer(int keyFunc) {
        if (App.isMainProcess()) {
            LogUtils.d(TAG, "setDoorKeyForCustomer: " + keyFunc);
            this.mIcmController.setDoorKeyForCustomer(keyFunc);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public int getDoorKeyForCustomer() {
        return this.mIcmController.getDoorKeyForCustomer();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setDoorBossKeySw(boolean enable) {
        this.mIcmController.setDoorBossKeySw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public boolean getDoorBossKeySw() {
        return this.mIcmController.getDoorBossKeySw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setTouchRotationDirection(int direction) {
        this.mIcmController.setTouchRotationDirection(direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public int getTouchRotationDirection() {
        return this.mIcmController.getTouchRotationDirection();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setSpeedWarningSw(boolean enable) {
        this.mIcmController.setSpeedWarningSw(enable);
        int speedLimitValueFromDataSync = this.mIcmController.getSpeedLimitValueFromDataSync();
        LogUtils.d(TAG, "setSpeedWarningSw: enable=" + enable + ", value=" + speedLimitValueFromDataSync);
        if (!enable || speedLimitValueFromDataSync < 40 || speedLimitValueFromDataSync > 120) {
            return;
        }
        this.mIcmController.setSpeedWarningValue(speedLimitValueFromDataSync);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public boolean getSpeedWarningSw() {
        return this.mIcmController.getSpeedWarningSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setSpeedWarningValue(int speed) {
        this.mIcmController.setSpeedWarningValue(speed);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public int getSpeedWarningValue() {
        return this.mIcmController.getSpeedLimitValueFromDataSync();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setTempSw(boolean enable) {
        this.mIcmController.setTempSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public boolean getTempSw() {
        return this.mIcmController.getTempSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setWindPowerSw(boolean enable) {
        this.mIcmController.setWindPowerSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public boolean getWindPowerSw() {
        return this.mIcmController.getWindPowerSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setWindModeSw(boolean enable) {
        this.mIcmController.setWindModeSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public boolean getWindModeSw() {
        return this.mIcmController.getWindModeSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setBrightSw(boolean enable) {
        this.mIcmController.setBrightSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public boolean getBrightSw() {
        return this.mIcmController.getBrightSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setMediaSw(boolean enable) {
        this.mIcmController.setMediaSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public boolean getMediaSw() {
        return this.mIcmController.getMediaSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setWheelKeyProtectSw(boolean enable) {
        this.mIcmController.setWheelKeyProtectSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public boolean isWheelKeyProtectEnabled() {
        return this.mIcmController.isWheelKeyProtectEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setShowEcallOnStatus(boolean show) {
        Settings.System.putInt(App.getInstance().getContentResolver(), IMeterViewModel.SETTINGS_KEY_ECALL_SHOW, show ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public boolean shouldShowEcallOnStatus() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), IMeterViewModel.SETTINGS_KEY_ECALL_SHOW, 1) == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public boolean isEcallAvailable() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), IMeterViewModel.SETTINGS_KEY_ECALL_AVAILABLE, 0) == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel
    public void setUserScenarioInfo(int[] info) {
        this.mIcmController.setUserScenarioInfo(info);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseMaintainData(String response) {
        try {
            JSONObject jSONObject = new JSONObject(response);
            int optInt = jSONObject.optInt("code");
            if (optInt != 200) {
                LogUtils.e(TAG, "response error, code: " + optInt);
                handleLastMaintainChanged(null);
                handleNextMaintainChanged(null);
                return;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            long optLong = jSONObject2.optLong("lastMaintainMileage");
            long optLong2 = jSONObject2.optLong("lastMaintainDate");
            long optLong3 = jSONObject2.optLong("nextMaintainMileage");
            long optLong4 = jSONObject2.optLong("nextMaintainDate");
            MaintainInfo maintainInfo = new MaintainInfo();
            if (optLong != 0) {
                maintainInfo.mMileage = new DecimalFormat(",###").format(optLong);
            }
            if (optLong2 != 0) {
                maintainInfo.mDate = convertDate(optLong2);
            }
            handleLastMaintainChanged(maintainInfo);
            MaintainInfo maintainInfo2 = new MaintainInfo();
            if (optLong3 != 0) {
                maintainInfo2.mMileage = new DecimalFormat(",###").format(optLong3);
            }
            if (optLong4 != 0) {
                maintainInfo2.mDate = convertDate(optLong4);
            }
            handleNextMaintainChanged(maintainInfo2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertDate(long time) {
        Date date = new Date();
        date.setTime(time);
        return this.mSdf.format(date);
    }

    static String formatMileage(float mileage) {
        if (mileage < 0.0f) {
            return null;
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        numberFormat.setMinimumFractionDigits(1);
        return numberFormat.format(mileage);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String formatMileage(float mileage, int newValue) {
        if (mileage < 0.0f) {
            return null;
        }
        NumberFormat numberFormat = BaseFeatureOption.getInstance().isMileageEuFormat() ? NumberFormat.getInstance(Locale.ENGLISH) : NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(newValue);
        numberFormat.setMinimumFractionDigits(newValue);
        if (!BaseFeatureOption.getInstance().isSupportThousandSeparator()) {
            numberFormat.setGroupingUsed(false);
        }
        return numberFormat.format(mileage);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void registerBusiness(String... keys) {
        this.mIcmController.registerBusiness(keys);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void unregisterBusiness(String... keys) {
        this.mIcmController.unregisterBusiness(keys);
    }
}
