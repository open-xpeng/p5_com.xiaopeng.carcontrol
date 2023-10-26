package com.xiaopeng.carcontrol.viewmodel.avas;

import android.content.ContentResolver;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IAvasController;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xuimanager.soundresource.data.BootSoundResource;

/* loaded from: classes2.dex */
public abstract class AvasBaseViewModel implements IAvasViewModel, IAvasController.Callback {
    private static final String TAG = "AvasBaseViewModel";
    private Boolean mExternalSw = true;
    private Integer mExternalVolume = null;
    IAvasController mAvasController = (IAvasController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_AVAS_SERVICE);

    /* JADX INFO: Access modifiers changed from: package-private */
    public AvasBaseViewModel() {
        if (App.isMainProcess()) {
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.avas.-$$Lambda$AvasBaseViewModel$z5T6FfExad3PENaYetnCd4iPuxE
            @Override // java.lang.Runnable
            public final void run() {
                AvasBaseViewModel.this.lambda$new$0$AvasBaseViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$new$0$AvasBaseViewModel() {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        this.mExternalSw = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.AVAS_EXTERNAL_SW, true));
        this.mExternalVolume = Integer.valueOf(CarControl.PrivateControl.getInt(contentResolver, CarControl.PrivateControl.AVAS_EXTERNAL_VOLUME, 100));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setLowSpdEnable(boolean enable) {
        this.mAvasController.setLowSpdSoundEnable(enable);
        if (enable) {
            return;
        }
        SpeechHelper.getInstance().speak(ResUtils.getString(R.string.avas_low_spd_close));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean isLowSpdEnabled() {
        return this.mAvasController.isLowSpdSoundEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setLowSpdEffect(int type) {
        this.mAvasController.setLowSpdSoundType(type);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public int getLowSpdEffect() {
        return this.mAvasController.getLowSpdSoundType();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setLowSpdVolume(int volume) {
        this.mAvasController.setLowSpdSoundVolume(volume);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public int getLowSpdVolume() {
        int lowSpdSoundVolume = this.mAvasController.getLowSpdSoundVolume();
        if (lowSpdSoundVolume < 1) {
            lowSpdSoundVolume = 1;
        }
        if (lowSpdSoundVolume > 100) {
            return 100;
        }
        return lowSpdSoundVolume;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setFriendEffect(int type) {
        this.mAvasController.setFriendSoundType(type);
        if (App.isMainProcess()) {
            return;
        }
        CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.SAY_HI_EFFECT, type);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public int getFriendEffect() {
        return this.mAvasController.getFriendSoundType();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasWakeWaitSwitch(boolean enable) {
        this.mAvasController.setAvasWakeWaitSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean isAvasWakeWaitEnable() {
        return this.mAvasController.isAvasWakeWaitEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasWakeWaitFullChargeSwitch(boolean enable) {
        this.mAvasController.setAvasWakeWaitFullChargeSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean isAvasWakeWaitFullChargeEnable() {
        return this.mAvasController.isAvasWakeWaitFullChargeEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasSleepSwitch(boolean enable) {
        this.mAvasController.setAvasSleepSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean isAvasSleepEnable() {
        return this.mAvasController.isAvasSleepEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasAcChargingSwitch(boolean enable) {
        this.mAvasController.setAvasAcChargingSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean isAvasAcChargingEnable() {
        return this.mAvasController.isAvasAcChargingEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasDcChargingSwitch(boolean enable) {
        this.mAvasController.setAvasDcChargingSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean isAvasDcChargingEnable() {
        return this.mAvasController.isAvasDcChargingEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasDisconnectChargingSwitch(boolean enable) {
        this.mAvasController.setAvasDisconnectChargingSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean isAvasDisconnectChargingEnable() {
        return this.mAvasController.isAvasDisconnectChargingEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasExternalSw(boolean enable) {
        this.mAvasController.setAvasExternalSw(enable);
        if (App.isMainProcess()) {
            return;
        }
        this.mExternalSw = Boolean.valueOf(enable);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.AVAS_EXTERNAL_SW, enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean isAvasExternalSwEnabled() {
        if (App.isMainProcess()) {
            return this.mAvasController.isAvasExternalEnabled();
        }
        return this.mExternalSw.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasExternalVolume(int volume) {
        this.mAvasController.setAvasExternalVolume(volume);
        if (App.isMainProcess()) {
            return;
        }
        this.mExternalVolume = Integer.valueOf(volume);
        CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.AVAS_EXTERNAL_VOLUME, volume);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public int getAvasExternalVolume() {
        if (App.isMainProcess()) {
            return this.mAvasController.getAvasExternalVolume();
        }
        return this.mExternalVolume.intValue();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasTakePhotoSwitch(boolean enable) {
        this.mAvasController.setAvasTakePhotoSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean isAvasTakePhotoEnable() {
        return this.mAvasController.isAvasTakePhotoEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasExternalSound(int type) {
        this.mAvasController.setAvasExternalSound(type);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setExternalSoundModeCmd(int mode) {
        this.mAvasController.setExternalSoundModeCmd(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setBootSoundEffect(int type) {
        int bootSoundEffect;
        if (type == 0 && (bootSoundEffect = getBootSoundEffect()) != 0) {
            setBootEffectBeforeSw(bootSoundEffect);
        }
        this.mAvasController.setBootSoundEffect(type);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public int getBootSoundEffect() {
        return this.mAvasController.getBootSoundEffect();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setBootEffectBeforeSw(int type) {
        this.mAvasController.setBootEffectBeforeSw(type);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public int getBootEffectBeforeSw() {
        return this.mAvasController.getBootEffectBeforeSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public String getBootSoundPreviewPath(int type) {
        BootSoundResource bootSoundResource;
        String[] resPath;
        BootSoundResource[] bootSoundResource2 = this.mAvasController.getBootSoundResource();
        return (bootSoundResource2 == null || type > bootSoundResource2.length || type <= 0 || (bootSoundResource = bootSoundResource2[type + (-1)]) == null || (resPath = bootSoundResource.getResPath()) == null || resPath.length <= 0) ? "" : resPath[0];
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasSayHiSw(boolean enable) {
        this.mAvasController.setAvasSayHiSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean getAvasSayHiSw() {
        return this.mAvasController.getAvasSayHiSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public void setAvasSpeakerSw(boolean enable) {
        this.mAvasController.setAvasSpeakerSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel
    public boolean getAvasSpeakerSw() {
        return this.mAvasController.getAvasSpeakerSw();
    }
}
