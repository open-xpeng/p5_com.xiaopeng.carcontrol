package com.xiaopeng.carcontrol.sdkImpl;

import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatMassage;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer;

/* loaded from: classes2.dex */
public class SeatManagerImpl extends AbstractManagerImpl implements SeatManagerServer.Implementation {
    private static final String TAG = "CarBodyImpl";
    private SeatManagerServer.Callback mCallback;
    private CarBodyViewModel mCarBodyVm;
    private SeatViewModel mSeatVm;

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getDSeatCushionExtPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getDSeatHorzPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getDSeatLegPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getDSeatTiltPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getDSeatVerPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getPSeatHorzPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getPSeatLegPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getPSeatTiltPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getPSeatVerPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getRLSeatHorPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getRLSeatLegHorPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public String getRLSeatName(int i) {
        return null;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getRLSeatSelectId() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getRLSeatTiltPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getRRSeatHorPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getRRSeatLegHorPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public String getRRSeatName(int i) {
        return null;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getRRSeatSelectId() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getRRSeatTiltPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getSecRowLtSeatZeroGrav() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getSecRowRtSeatZeroGrav() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getTrdRowLtSeatTiltPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getTrdRowRtSeatTiltPos() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setDSeatCushionExtPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setDSeatHorzPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setDSeatLegPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setDSeatTiltPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setDSeatVerPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setPSeatHorzPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setPSeatLegPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setPSeatTiltPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setPSeatVerPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setRLSeatHorPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setRLSeatLegHorPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setRLSeatTiltPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setRRSeatHorPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setRRSeatLegHorPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setRRSeatTiltPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setSecRowLtSeatZeroGrav(boolean b) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setSecRowRtSeatZeroGrav(boolean b) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setTrdRowLtSeatTiltPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setTrdRowRtSeatTiltPos(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void switchRLSeatId(int i) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void switchRRSeatId(int i) {
    }

    @Override // com.xiaopeng.carcontrol.sdkImpl.AbstractManagerImpl
    protected void initInternal() {
        this.mSeatVm = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        this.mCallback = SeatManagerServer.getInstance().getCallback();
    }

    @Override // com.xiaopeng.carcontrol.sdkImpl.AbstractManagerImpl
    protected void observeData() {
        setLiveDataObserver(this.mSeatVm.getMassageData(0), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$SeatManagerImpl$no_nzXCzTjfWBoUQYlA498g83u4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatManagerImpl.this.handleMassagerDataChange((SeatMassage) obj);
            }
        });
        setLiveDataObserver(this.mSeatVm.getMassageData(1), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$SeatManagerImpl$no_nzXCzTjfWBoUQYlA498g83u4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatManagerImpl.this.handleMassagerDataChange((SeatMassage) obj);
            }
        });
        setLiveDataObserver(this.mSeatVm.getMassageData(2), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$SeatManagerImpl$no_nzXCzTjfWBoUQYlA498g83u4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatManagerImpl.this.handleMassagerDataChange((SeatMassage) obj);
            }
        });
        setLiveDataObserver(this.mSeatVm.getMassageData(3), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$SeatManagerImpl$no_nzXCzTjfWBoUQYlA498g83u4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SeatManagerImpl.this.handleMassagerDataChange((SeatMassage) obj);
            }
        });
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void startMassager(int index, String effectId) {
        this.mSeatVm.startMassager(index, effectId);
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void stopMassager(int index) {
        this.mSeatVm.stopMassager(index);
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public void setMassagerIntensity(int index, int intensity) {
        this.mSeatVm.setMassagerIntensity(index, intensity);
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.SeatManagerServer.Implementation
    public int getMassagerIntensity(int index) {
        return this.mSeatVm.getMassagerIntensity(index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMassagerDataChange(SeatMassage data) {
        SeatManagerServer.Callback callback;
        if (data == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onMassagerStatusChange(data.getIndex(), data.getEffect(), data.getIntensity(), data.isRunning());
    }
}
