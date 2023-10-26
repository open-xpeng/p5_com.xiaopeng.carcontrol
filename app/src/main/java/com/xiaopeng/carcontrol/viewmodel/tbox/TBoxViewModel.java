package com.xiaopeng.carcontrol.viewmodel.tbox;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ITboxController;

/* loaded from: classes2.dex */
public class TBoxViewModel implements ITboxViewModel, ITboxController.Callback {
    private static final String TAG = "TBoxViewModel";
    private ITboxController mTboxController;
    private final MutableLiveData<Integer> mSoldierSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAutoPowerOffData = new MutableLiveData<>();

    public TBoxViewModel() {
        ITboxController iTboxController = (ITboxController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_TBOX_SERVICE);
        this.mTboxController = iTboxController;
        iTboxController.registerCallback(this);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.tbox.ITboxViewModel
    public void setSoldierSw(int status) {
        this.mTboxController.setSoldierSw(status);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.tbox.ITboxViewModel
    public int getSoldierSwStatus() {
        return this.mTboxController.getSoldierSwStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.tbox.ITboxViewModel
    public void setNetWorkType(int type) {
        this.mTboxController.setNetWorkType(type);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController.Callback
    public void onSoldierStateChanged(int status) {
        this.mSoldierSwData.postValue(Integer.valueOf(status));
    }

    public LiveData<Integer> getSoldierSwData() {
        return this.mSoldierSwData;
    }
}
