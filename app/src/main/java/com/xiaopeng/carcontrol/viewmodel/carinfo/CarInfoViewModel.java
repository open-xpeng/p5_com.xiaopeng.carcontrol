package com.xiaopeng.carcontrol.viewmodel.carinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ICarInfoController;

/* loaded from: classes2.dex */
public class CarInfoViewModel implements ICarInfoViewModel {
    private final ICarInfoController.Callback mCarInfoCallback;
    private ICarInfoController mCarInfoController;
    protected final String TAG = "CarInfoViewModel";
    private final MutableLiveData<String> mCarLicensePlateData = new MutableLiveData<>();

    public CarInfoViewModel() {
        ICarInfoController.Callback callback = new ICarInfoController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.carinfo.-$$Lambda$CarInfoViewModel$W7RDMq38yt5bBK_UVamm09UcM8c
            @Override // com.xiaopeng.carcontrol.carmanager.controller.ICarInfoController.Callback
            public final void onCarLicensePlateChanged(String str) {
                CarInfoViewModel.this.handleCarLicensePlate(str);
            }
        };
        this.mCarInfoCallback = callback;
        ICarInfoController iCarInfoController = (ICarInfoController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_CAR_INFO_SERVICE);
        this.mCarInfoController = iCarInfoController;
        iCarInfoController.registerCallback(callback);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carinfo.ICarInfoViewModel
    public String getCarLicensePlate() {
        this.mCarInfoController.requestCarLicensePlate();
        return this.mCarInfoController.getCarLicensePlate();
    }

    public LiveData<String> getCarLicensePlateData() {
        return this.mCarLicensePlateData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCarLicensePlate(String licensePlate) {
        this.mCarLicensePlateData.postValue(licensePlate);
    }
}
