package com.xiaopeng.carcontrol.viewmodel.sfs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ISfsController;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class SfsViewModel implements ISfsViewModel, ISfsController.Callback {
    private static final String TAG = "SfsViewModel";
    private final ISfsController mSfsController;
    private final MutableLiveData<Boolean> mSfsSwData = new MutableLiveData<>();
    private final MutableLiveData<SfsType> mSfsTypeInChannel1 = new MutableLiveData<>();
    private final MutableLiveData<SfsType> mSfsTypeInChannel2 = new MutableLiveData<>();
    private final MutableLiveData<SfsType> mSfsTypeInChannel3 = new MutableLiveData<>();
    private final MutableLiveData<SfsChannel> mSfsActivatedChannel = new MutableLiveData<>();

    public SfsViewModel() {
        ISfsController iSfsController = (ISfsController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_SFS_SERVICE);
        this.mSfsController = iSfsController;
        iSfsController.registerCallback(this);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ISfsController.Callback
    public void onSfsSwChanged(boolean enabled) {
        this.mSfsSwData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ISfsController.Callback
    public void onSfsTypeChanged(int[] typesInChannel) {
        if (typesInChannel == null || typesInChannel.length < 2) {
            LogUtils.w(TAG, "Invalid result for sfs types in channel: " + Arrays.toString(typesInChannel));
            return;
        }
        this.mSfsTypeInChannel1.postValue(SfsType.fromHvacSfsType(typesInChannel[0]));
        this.mSfsTypeInChannel2.postValue(SfsType.fromHvacSfsType(typesInChannel[1]));
        this.mSfsTypeInChannel3.postValue(SfsType.fromHvacSfsType(typesInChannel[2]));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ISfsController.Callback
    public void onSfsChannelChanged(int channel) {
        this.mSfsActivatedChannel.postValue(SfsChannel.fromHvacSfsChannel(channel));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.sfs.ISfsViewModel
    public void setSwEnable(boolean enable) {
        this.mSfsController.setSwEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.sfs.ISfsViewModel
    public boolean isSwEnabled() {
        return this.mSfsController.isSwEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.sfs.ISfsViewModel
    public SfsType[] getTypesInChannel() {
        int[] typesInChannel = this.mSfsController.getTypesInChannel();
        if (typesInChannel == null || typesInChannel.length < 2) {
            LogUtils.w(TAG, "Invalid result for sfs types in channel: " + Arrays.toString(typesInChannel));
            return null;
        }
        return new SfsType[]{SfsType.fromHvacSfsType(typesInChannel[0]), SfsType.fromHvacSfsType(typesInChannel[1]), SfsType.fromHvacSfsType(typesInChannel[2])};
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.sfs.ISfsViewModel
    public void setActiveChannel(SfsChannel sfsChannel) {
        this.mSfsController.setActiveChannel(sfsChannel.toSfsCmd());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.sfs.ISfsViewModel
    public SfsChannel getActivatedChannel() {
        return SfsChannel.fromHvacSfsChannel(this.mSfsController.getActivatedChannel());
    }

    public LiveData<Boolean> getSfsSwData() {
        return this.mSfsSwData;
    }
}
