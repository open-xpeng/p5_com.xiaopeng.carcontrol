package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.atl.CarAtlManager;
import android.database.ContentObserver;
import android.net.Uri;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.controller.ICarInfoController;
import com.xiaopeng.carcontrol.carmanager.impl.CarInfoController;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ModuleUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class CarInfoController extends BaseCarController<CarAtlManager, ICarInfoController.Callback> implements ICarInfoController {
    private static final long REQUEST_CARE_LICENSE_PLATE_INTERVAL = 120000;
    private static final String TAG = "CarInfoController";
    private ContentObserver mContentObserver;
    private String mCarLicensePlate = null;
    private long mLastRequestTimeStamp = 0;

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public CarInfoController(Car carClient) {
        if (this.mIsMainProcess) {
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$CarInfoController$8UdS9Bq3kXuHIy-r9Ph9Ttgfrzc
            @Override // java.lang.Runnable
            public final void run() {
                CarInfoController.this.lambda$new$0$CarInfoController();
            }
        });
    }

    public /* synthetic */ void lambda$new$0$CarInfoController() {
        String string = CarControl.PrivateControl.getString(App.getInstance().getContentResolver(), CarControl.PrivateControl.CAR_LICENSE_PLATE);
        this.mCarLicensePlate = string;
        handleCarLicensePlateChanged(string);
        registerContentObserver();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICarInfoController
    public void setCarLicensePlate(final String licensePlate) {
        if (this.mIsMainProcess) {
            this.mDataSync.setCarLicensePlate(licensePlate);
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$CarInfoController$xAtTZTJNdNSLd5gg0pmsiQFSSlk
                @Override // java.lang.Runnable
                public final void run() {
                    CarControl.PrivateControl.putString(App.getInstance().getContentResolver(), CarControl.PrivateControl.CAR_LICENSE_PLATE, licensePlate);
                }
            });
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICarInfoController
    public String getCarLicensePlate() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getCarLicensePlate();
        }
        return this.mCarLicensePlate;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICarInfoController
    public void requestCarLicensePlate() {
        if (CarStatusUtils.isEURegion()) {
            return;
        }
        if (this.mIsMainProcess) {
            if (FunctionModel.getInstance().isRequestCarPlateAllowed()) {
                if (this.mLastRequestTimeStamp == 0 || System.currentTimeMillis() - this.mLastRequestTimeStamp > REQUEST_CARE_LICENSE_PLATE_INTERVAL) {
                    ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$CarInfoController$eMCBpEbqO1ynIy6NU9VE_3aQ9GE
                        @Override // java.lang.Runnable
                        public final void run() {
                            CarInfoController.this.requestCarLicensePlateImpl();
                        }
                    }, 50L);
                    return;
                } else {
                    LogUtils.i(TAG, "Request car license plate within 2 minutes, ignore request");
                    return;
                }
            }
            LogUtils.i(TAG, "Car license plate has been retrieved in this local igon cycle, ignore further request");
            return;
        }
        CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.REQUEST_CAR_LICENSE_PLATE, 1);
    }

    protected void registerContentObserver() {
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.carmanager.impl.CarInfoController.1
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    String lastPathSegment = uri.getLastPathSegment();
                    if (CarControl.PrivateControl.CAR_LICENSE_PLATE.equals(lastPathSegment)) {
                        CarInfoController.this.mCarLicensePlate = CarControl.PrivateControl.getString(App.getInstance().getContentResolver(), CarControl.PrivateControl.CAR_LICENSE_PLATE);
                        LogUtils.d(CarInfoController.TAG, "onChange: " + lastPathSegment + ", value =" + CarInfoController.this.mCarLicensePlate);
                        CarInfoController carInfoController = CarInfoController.this;
                        carInfoController.handleCarLicensePlateChanged(carInfoController.mCarLicensePlate);
                    }
                }
            };
        }
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.CAR_LICENSE_PLATE), false, this.mContentObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestCarLicensePlateImpl() {
        String str = TAG;
        LogUtils.i(str, "Start to request car license plate", false);
        String str2 = GlobalConstant.URL.GET_PLATE;
        LogUtils.i(str, "Request car plate url: " + str2, false);
        IHttp http = ModuleUtils.getHttp();
        if (http == null) {
            LogUtils.e(str, "Http module is null", false);
        } else {
            http.bizHelper().get(str2).needAuthorizationInfo().enableSecurityEncoding().uid(String.valueOf(0)).build().execute(new AnonymousClass2());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.carmanager.impl.CarInfoController$2  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass2 implements Callback {
        AnonymousClass2() {
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onSuccess(IResponse iResponse) {
            CarInfoController.this.mLastRequestTimeStamp = 0L;
            if (iResponse == null) {
                LogUtils.e(CarInfoController.TAG, "requestCarLicensePlateImpl response null", false);
                return;
            }
            final String body = iResponse.body();
            LogUtils.i(CarInfoController.TAG, "requestCarLicensePlateImpl success, response:" + body, false);
            if (iResponse.code() == 200) {
                CarInfoController.this.mLastRequestTimeStamp = System.currentTimeMillis();
                FunctionModel.getInstance().setRequestCarPlateTs(CarInfoController.this.mLastRequestTimeStamp);
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$CarInfoController$2$6Z5_2cDa4YfazxBaVCvovhIh2ak
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarInfoController.AnonymousClass2.this.lambda$onSuccess$0$CarInfoController$2(body);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onSuccess$0$CarInfoController$2(final String body) {
            CarInfoController.this.parseCarLicensePlate(body);
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onFailure(IResponse iResponse) {
            LogUtils.e(CarInfoController.TAG, "requestCarLicensePlateImpl onFailure: " + iResponse.getException(), false);
            CarInfoController.this.mLastRequestTimeStamp = 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseCarLicensePlate(String response) {
        try {
            JSONObject jSONObject = new JSONObject(response);
            int optInt = jSONObject.optInt("code");
            if (optInt != 200) {
                LogUtils.e(TAG, "response error, code: " + optInt);
                return;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            String string = jSONObject2.getString("plateNo");
            LogUtils.i(TAG, "parseCarLicensePlate carPlateNo: " + string + ", vin: " + jSONObject2.getString("vin"), false);
            if (string == null || string.length() < 8) {
                return;
            }
            setCarLicensePlate(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCarLicensePlateChanged(String licensePlate) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ICarInfoController.Callback) it.next()).onCarLicensePlateChanged(licensePlate);
            }
        }
    }
}
