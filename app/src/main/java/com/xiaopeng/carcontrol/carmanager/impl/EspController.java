package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.esp.CarEspManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IEspController;
import com.xiaopeng.carcontrol.carmanager.impl.oldarch.EspOldController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class EspController extends BaseCarController<CarEspManager, IEspController.Callback> implements IEspController {
    protected static final String TAG = "EspController";
    private ContentObserver mContentObserver;
    private final CarEspManager.CarEspEventCallback mCarEspEventCallback = new CarEspManager.CarEspEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.EspController.1
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(EspController.TAG, "onChangeEvent: " + carPropertyValue, false);
            EspController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int errorCode) {
            if (errorCode == 6) {
                LogUtils.e(EspController.TAG, "onErrorEvent: " + propertyId + ", errorCode: " + errorCode, false);
                EspController.this.handleLossEventsUpdate(propertyId);
            }
        }
    };
    private Boolean mEspSw = null;
    private Boolean mAvhSw = null;

    private int convertToEpbCmd(boolean enable) {
        return enable ? 2 : 1;
    }

    private boolean parseHdcStatus(int status) {
        return status == 1 || status == 2;
    }

    protected int convertToEspCmd(boolean enable) {
        return enable ? 1 : 3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    protected void mockExtEspProperty() {
    }

    protected boolean parseEspStatus(int status) {
        return status == 1 || status == 2;
    }

    public EspController(Car carClient) {
    }

    /* loaded from: classes.dex */
    public static class EspControllerFactory {
        public static EspController createCarController(Car carClient) {
            if (CarBaseConfig.getInstance().isNewEspArch()) {
                return new EspController(carClient);
            }
            return new EspOldController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarEspManager) carClient.getCarManager(CarClientWrapper.XP_ESP_SERVICE);
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarEspEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        if (!this.mIsMainProcess) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$EspController$bSbYCl8hv-vW6OONJkx5JQTeeqc
                @Override // java.lang.Runnable
                public final void run() {
                    EspController.this.lambda$initCarManager$0$EspController();
                }
            });
            registerContentObserver();
        }
        LogUtils.d(TAG, "Init end");
    }

    public /* synthetic */ void lambda$initCarManager$0$EspController() {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        this.mEspSw = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.ESP_SW, true));
        this.mAvhSw = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.AVH_SW, true));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(557851650);
        arrayList.add(557851657);
        arrayList.add(557851651);
        arrayList.add(557851659);
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        if (this.mCarConfig.isSupportEpbWarning() && this.mIsMainProcess) {
            propertyIds.add(557851668);
        }
        if (this.mCarConfig.isSupportHdc()) {
            propertyIds.add(557851656);
            propertyIds.add(557851658);
        }
        if (this.mCarConfig.isSupportEpbSetting() && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess))) {
            propertyIds.add(557851667);
        }
        if (this.mCarConfig.isSupportEspMudMode()) {
            propertyIds.add(557851670);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarEspEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557851650:
                handleEspUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557851651:
                handleAvhUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557851656:
                handleHdcChanged(((Integer) getValue(value)).intValue());
                return;
            case 557851657:
                handleEspFaultChanged(((Integer) getValue(value)).intValue());
                return;
            case 557851658:
                handleHdcFaultChanged(((Integer) getValue(value)).intValue());
                return;
            case 557851659:
                handleAvhFaultChanged(((Integer) getValue(value)).intValue());
                return;
            case 557851660:
                handleEpbWarning(((Integer) getValue(value)).intValue());
                return;
            case 557851667:
                handleApbSystemStatus(((Integer) getValue(value)).intValue());
                return;
            case 557851668:
                handleHbcRequest(((Integer) getValue(value)).intValue());
                return;
            case 557851670:
                handleEspOffRoadStatus(((Integer) getValue(value)).intValue());
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLossEventsUpdate(int propertyId) {
        if (propertyId == 557851650) {
            handleCarEventsUpdate(new CarPropertyValue<>(propertyId, 3));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public int getEsp() {
        try {
            try {
                return getIntProperty(557851650);
            } catch (Exception e) {
                LogUtils.e(TAG, "getEsp: " + e.getMessage(), false);
                return 3;
            }
        } catch (Exception unused) {
            return this.mCarManager.getEsp();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public boolean getEspFault() {
        int i;
        try {
            i = this.mCarManager.getEspFault();
        } catch (Exception e) {
            LogUtils.e(TAG, "getEspFault: " + e.getMessage(), false);
            i = 1;
        }
        return i != 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setEsp(boolean enable) {
        try {
            this.mCarManager.setEsp(convertToEspCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setEsp: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setEspModeSport() {
        try {
            this.mCarManager.setEsp(2);
            LogUtils.i(TAG, "setEspModeSport ", false);
        } catch (Exception e) {
            LogUtils.e(TAG, "setEspModeSport: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setEspMudStatus(boolean enable) {
        if (this.mCarConfig.isSupportEspMudMode()) {
            try {
                this.mCarManager.setOffRoadSwitch(enable ? 1 : 0);
                LogUtils.i(TAG, "setEspMudStatus: " + enable, false);
            } catch (Exception e) {
                LogUtils.e(TAG, "setEspMudStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public boolean getEspMudStatus() {
        int i;
        if (this.mCarConfig.isSupportEspMudMode()) {
            try {
                try {
                    i = getIntProperty(557851670);
                } catch (Exception unused) {
                    i = this.mCarManager.getOffRoadSwitchStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getEspMudStatus: " + e.getMessage(), false);
                i = 0;
            }
            return i == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public boolean getEspSw() {
        return this.mIsMainProcess ? this.mDataSync.getEsp() : ((Boolean) getContentProviderValue(CarControl.PrivateControl.ESP_SW, this.mEspSw, true)).booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setEspSw(final boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setEsp(enable);
            if (BaseFeatureOption.getInstance().isSupportNapa()) {
                return;
            }
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$EspController$up3TX7hlWVX_RhBovPCKzSOe1lc
                @Override // java.lang.Runnable
                public final void run() {
                    EspController.this.lambda$setEspSw$1$EspController(enable);
                }
            });
            return;
        }
        this.mEspSw = Boolean.valueOf(enable);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ESP_SW, enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public int getAvh() {
        try {
            try {
                return getIntProperty(557851651);
            } catch (Exception e) {
                LogUtils.e(TAG, "getAvh: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getAvh();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setAvh(boolean enable) {
        try {
            this.mCarManager.setAvh(enable ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "setAvh: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public boolean getAvhFault() {
        int i;
        try {
            i = this.mCarManager.getAvhFault();
        } catch (Exception e) {
            LogUtils.e(TAG, "getAvhFault: " + e.getMessage(), false);
            i = 1;
        }
        return i != 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public boolean getAvhSw() {
        return this.mIsMainProcess ? this.mDataSync.getAvh() : ((Boolean) getContentProviderValue(CarControl.PrivateControl.AVH_SW, this.mAvhSw, true)).booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setAvhSw(final boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setAvh(enable);
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$EspController$LRMXiC98WS6gjhkQefK5xwLddX0
                @Override // java.lang.Runnable
                public final void run() {
                    EspController.this.lambda$setAvhSw$2$EspController(enable);
                }
            });
            return;
        }
        this.mAvhSw = Boolean.valueOf(enable);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.AVH_SW, enable);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$EspController$YSDhzVmetdErNEmplJpmf0Fkf40
            @Override // java.lang.Runnable
            public final void run() {
                EspController.this.lambda$setAvhSw$3$EspController(enable);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public boolean getHdc() {
        int i = 0;
        if (this.mCarConfig.isSupportHdc()) {
            try {
                try {
                    i = getIntProperty(557851656);
                } catch (Exception unused) {
                    i = this.mCarManager.getHdc();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getHdc: " + e.getMessage(), false);
            }
            return parseHdcStatus(i);
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setHdc(boolean enable) {
        if (this.mCarConfig.isSupportHdc()) {
            try {
                this.mCarManager.setHdc(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setHdc: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public boolean getHdcFault() {
        int i;
        if (this.mCarConfig.isSupportHdc()) {
            try {
                i = this.mCarManager.getHdcFault();
            } catch (Exception e) {
                LogUtils.e(TAG, "getHdcFault: " + e.getMessage(), false);
                i = 1;
            }
            return i != 0;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public boolean getEpsWarningState() {
        int i;
        try {
            i = this.mCarManager.getEpsWarninglampStatus();
        } catch (Exception unused) {
            i = 0;
        }
        return i != 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public int getApbSystemDisplayMessage() {
        try {
            return this.mCarManager.getApbSystemDisplayMessage();
        } catch (Exception e) {
            LogUtils.e(TAG, "getApbSystemDisplayMessage: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public int getApbSystemStatus() {
        try {
            try {
                return getIntProperty(557851667);
            } catch (Exception e) {
                LogUtils.e(TAG, "getApbSystemStatus: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getApbSystemStatus();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setEpbSystemStatus(boolean enable) {
        if (CarBaseConfig.getInstance().isSupportEpbSetting()) {
            try {
                this.mCarManager.setEpbSystemSwitch(convertToEpbCmd(enable));
            } catch (Exception e) {
                LogUtils.e(TAG, "setEpbSystemStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public int getHbcRequestStatus() {
        if (this.mCarConfig.isSupportEpbWarning()) {
            try {
                try {
                    return getIntProperty(557851668);
                } catch (Exception unused) {
                    return this.mCarManager.getHbcRequestStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getHbcRequestStatus: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setEspTsmSwitchStatus(boolean enable) {
        if (CarBaseConfig.getInstance().isSupportTrailerRv()) {
            try {
                this.mCarManager.setTsmSwitchStatus(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTsmSwitchStatus: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public int getEspTsmSwitchStatus() {
        if (CarBaseConfig.getInstance().isSupportTrailerRv()) {
            try {
                return this.mCarManager.getTsmSwitchStatus();
            } catch (Exception e) {
                LogUtils.e(TAG, "getEspTsmSwitchStatus: " + e.getMessage(), false);
                return 0;
            }
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getTsmFaultStatus() {
        /*
            r4 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportTrailerHook()
            r1 = 0
            if (r0 == 0) goto L31
            C extends android.car.hardware.CarEcuManager r0 = r4.mCarManager     // Catch: java.lang.Exception -> L14
            android.car.hardware.esp.CarEspManager r0 = (android.car.hardware.esp.CarEspManager) r0     // Catch: java.lang.Exception -> L14
            int r0 = r0.getTsmFaultStatus()     // Catch: java.lang.Exception -> L14
            goto L32
        L14:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getTsmFaultStatus: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "EspController"
            com.xiaopeng.carcontrol.util.LogUtils.e(r2, r0, r1)
        L31:
            r0 = r1
        L32:
            r2 = 1
            if (r0 != r2) goto L36
            r1 = r2
        L36:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.EspController.getTsmFaultStatus():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public boolean getDtcFaultStatus() {
        int i;
        try {
            i = this.mCarManager.getDtcFaultStatus();
        } catch (Exception e) {
            LogUtils.e(TAG, "getDtcFaultStatus: " + e.getMessage(), false);
            i = 0;
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setEspCstSw(boolean enable) {
        if (this.mCarConfig.isSupportEspCst()) {
            try {
                this.mCarManager.setCstStatus(enable ? 1 : 0);
                if (this.mIsMainProcess) {
                    this.mDataSync.setEspCstSw(enable);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setEspCstSw failed: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public boolean getEspCstSw() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getEspCstSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setEspBpfMode(int mode) {
        if (this.mCarConfig.isSupportEspCst()) {
            if (mode < 1 || mode > 3) {
                LogUtils.w(TAG, "setEspBpfMode failed with invalid mode: " + mode, false);
                return;
            }
            try {
                this.mCarManager.setBpfStatus(mode);
                if (this.mIsMainProcess) {
                    this.mDataSync.setEspBpfMode(mode);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setBpfStatus failed: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public int getEspBpfMode() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getEspBpfMode();
        }
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public int getEpbDriverOffWarningMsg() {
        if (this.mCarConfig.isSupportEpbSetting()) {
            try {
                return this.mCarManager.getEpbDriverOffWarningMsg();
            } catch (Exception e) {
                LogUtils.w(TAG, "getEpbDriverOffWarningMsg failed: " + e.getMessage(), false);
            }
        }
        return 0;
    }

    void registerContentObserver() {
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.carmanager.impl.EspController.2
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    String lastPathSegment = uri.getLastPathSegment();
                    if (CarControl.PrivateControl.ESP_SW_NOTIFY.equals(lastPathSegment)) {
                        boolean bool = CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ESP_SW, false);
                        LogUtils.d(EspController.TAG, "onChange: " + lastPathSegment + ", esp sw=" + bool);
                        EspController.this.mEspSw = Boolean.valueOf(bool);
                        EspController.this.lambda$setEspSw$1$EspController(bool);
                    } else if (CarControl.PrivateControl.AVH_SW_NOTIFY.equals(lastPathSegment)) {
                        boolean bool2 = CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.AVH_SW, false);
                        LogUtils.d(EspController.TAG, "onChange: " + lastPathSegment + ", avh sw=" + bool2);
                        EspController.this.mAvhSw = Boolean.valueOf(bool2);
                        EspController.this.lambda$setAvhSw$3$EspController(bool2);
                    }
                }
            };
        }
        LogUtils.d(TAG, "registerContentObserver");
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.ESP_SW_NOTIFY), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.AVH_SW_NOTIFY), false, this.mContentObserver);
    }

    private void mockEspProperty() {
        this.mCarPropertyMap.put(557851650, new CarPropertyValue<>(557851650, true));
        this.mCarPropertyMap.put(557851657, new CarPropertyValue<>(557851657, 0));
        this.mCarPropertyMap.put(557851651, new CarPropertyValue<>(557851651, 0));
        this.mCarPropertyMap.put(557851659, new CarPropertyValue<>(557851659, 0));
        this.mCarPropertyMap.put(557851660, new CarPropertyValue<>(557851660, 0));
        mockExtEspProperty();
    }

    private void handleEspUpdate(int espMode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onEspChanged(parseEspStatus(espMode));
            }
        }
    }

    private void handleEspFaultChanged(int status) {
        boolean z = status != 0;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onEspFaultChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleEspSwChanged */
    public void lambda$setEspSw$1$EspController(boolean enabled) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onEspSwChanged(enabled);
            }
        }
    }

    private void handleAvhUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onAvhStatusChanged(status);
            }
        }
    }

    private void handleAvhFaultChanged(int status) {
        boolean z = status != 0;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onAvhFaultChanged(z);
            }
        }
    }

    private void handleHdcChanged(int status) {
        boolean parseHdcStatus = parseHdcStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onHdcChanged(parseHdcStatus);
            }
        }
    }

    private void handleHdcFaultChanged(int status) {
        boolean z = status != 0;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onHdcFaultChanged(z);
            }
        }
    }

    private void handleEpbWarning(int status) {
        boolean z = status != 0;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onEpbFaultChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleAvhSwChanged */
    public void lambda$setAvhSw$3$EspController(boolean enabled) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onAvhSwChanged(enabled);
            }
        }
    }

    private void handleHbcRequest(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onHbcRequestStatusChanged(status);
            }
        }
    }

    private void handleApbSystemStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEspController.Callback) it.next()).onApbSystemStatusChanged(status);
            }
        }
    }

    private void handleEspOffRoadStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IEspController.Callback callback = (IEspController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onEspOffRoadStatusChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void getContentProviderValueBySync(String key) {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        key.hashCode();
        if (key.equals(CarControl.PrivateControl.AVH_SW)) {
            Boolean valueOf = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, key, true));
            this.mAvhSw = valueOf;
            lambda$setAvhSw$3$EspController(valueOf.booleanValue());
        } else if (key.equals(CarControl.PrivateControl.ESP_SW)) {
            Boolean valueOf2 = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, key, true));
            this.mEspSw = valueOf2;
            lambda$setEspSw$1$EspController(valueOf2.booleanValue());
        }
    }
}
