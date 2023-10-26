package com.xiaopeng.carcontrol.viewmodel.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.CarControlService;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;

/* loaded from: classes2.dex */
public class AudioViewModel implements IAudioViewModel {
    private static final String TAG = "AudioViewModel";
    private XDialog mMuteConfirmDialog;
    private XDialog mUnmuteConfirmDialog;
    private final BroadcastReceiver mMicrophoneMuteReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.carcontrol.viewmodel.audio.AudioViewModel.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                LogUtils.i(AudioViewModel.TAG, "onReceive: " + intent.getAction(), false);
                if ("android.media.action.MICROPHONE_MUTE_CHANGED".equals(intent.getAction())) {
                    AudioViewModel.this.handleMicrophoneMuteChanged();
                }
            }
        }
    };
    private final MutableLiveData<Boolean> mMicrophoneMuteData = new MutableLiveData<>();
    private boolean mUnmuteManualFlag = false;
    private int mMuteDialogDisplayId = -1;
    private final AudioManager mAudioManager = (AudioManager) App.getInstance().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);

    public AudioViewModel() {
        registerMicrophoneMute();
    }

    public int getMuteDialogDisplayId() {
        return this.mMuteDialogDisplayId;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel
    public void confirmMuteMicrophone() {
        confirmMuteMicrophone(-1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel
    public void confirmMuteMicrophone(int displayId) {
        if (!App.isMainProcess()) {
            Intent intent = new Intent(App.getInstance().getApplicationContext(), CarControlService.class);
            intent.setAction(GlobalConstant.ACTION.ACTION_SHOW_MICROPHONE_MUTE_DIALOG);
            intent.putExtra(GlobalConstant.EXTRA.KEY_MICROPHONE_DISPLAY_ID, displayId);
            App.getInstance().startService(intent);
            LogUtils.i(TAG, "startService ACTION_SHOW_MICROPHONE_MUTE_DIALOG");
            return;
        }
        LogUtils.i(TAG, "confirmMuteMicrophone");
        this.mMuteDialogDisplayId = displayId;
        if (this.mMuteConfirmDialog == null) {
            XDialog xDialog = new XDialog(App.getInstance(), R.style.XDialogView);
            xDialog.setTitle(R.string.settings_microphone_title).setMessage(R.string.settings_microphone_feature_feedback).setPositiveButton(R.string.btn_ok, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.audio.-$$Lambda$AudioViewModel$AuBJizKTmVFdpbFjCKZr_iycoQ8
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    AudioViewModel.this.lambda$confirmMuteMicrophone$0$AudioViewModel(xDialog2, i);
                }
            }).setNegativeButton(R.string.btn_cancel, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.audio.-$$Lambda$AudioViewModel$MH37WDnLNJcImkQNgyZDVmS971Q
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    AudioViewModel.lambda$confirmMuteMicrophone$1(xDialog2, i);
                }
            });
            xDialog.setSystemDialog(2008);
            xDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.xiaopeng.carcontrol.viewmodel.audio.-$$Lambda$AudioViewModel$vmh8YvSBLvqSV36YG82_wFhjets
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    AudioViewModel.lambda$confirmMuteMicrophone$2(dialogInterface);
                }
            });
            this.mMuteConfirmDialog = xDialog;
        }
        XDialog xDialog2 = this.mUnmuteConfirmDialog;
        if (xDialog2 != null && xDialog2.isShowing()) {
            this.mUnmuteConfirmDialog.dismiss();
        }
        if (this.mMuteConfirmDialog != null) {
            VuiManager.instance().initVuiDialog(this.mMuteConfirmDialog, App.getInstance(), VuiManager.SCENE_MICHO_PHONE_MUTE_SETTING);
            this.mMuteConfirmDialog.show();
        }
    }

    public /* synthetic */ void lambda$confirmMuteMicrophone$0$AudioViewModel(XDialog dialog, int i) {
        setMicrophoneMute(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$confirmMuteMicrophone$1(XDialog xDialog1, int i) {
        if (BaseFeatureOption.getInstance().isBroadcastWhenCancelMuteOrUnmute()) {
            App.getInstance().sendBroadcast(new Intent(GlobalConstant.ACTION.ACTION_CANCEL_MICROPHONE_MUTE));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$confirmMuteMicrophone$2(DialogInterface dialog) {
        if (BaseFeatureOption.getInstance().isBroadcastWhenCancelMuteOrUnmute()) {
            App.getInstance().sendBroadcast(new Intent(GlobalConstant.ACTION.ACTION_CANCEL_MICROPHONE_MUTE));
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel
    public void confirmUnmuteMicrophone(boolean shouldDismiss, int displayId) {
        LogUtils.i(TAG, "confirmUnmuteMicrophone shouldDismiss: " + shouldDismiss, false);
        if (shouldDismiss) {
            XDialog xDialog = this.mUnmuteConfirmDialog;
            if (xDialog != null) {
                xDialog.dismiss();
                return;
            }
            return;
        }
        this.mMuteDialogDisplayId = displayId;
        if (this.mUnmuteConfirmDialog == null) {
            XDialog xDialog2 = new XDialog(App.getInstance(), R.style.XDialogView);
            xDialog2.setTitle(R.string.settings_microphone_title).setMessage(R.string.settings_microphone_unmute_feature_feedback).setPositiveButton(R.string.btn_open, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.audio.-$$Lambda$AudioViewModel$mB7Qx7TI1tcQmkQvyM-qPJYjQjM
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog3, int i) {
                    AudioViewModel.this.lambda$confirmUnmuteMicrophone$3$AudioViewModel(xDialog3, i);
                }
            }).setNegativeButton(R.string.btn_cancel, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.audio.-$$Lambda$AudioViewModel$-kws0Q63Jf8Z7dpTqnM9MjQaOco
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog3, int i) {
                    AudioViewModel.lambda$confirmUnmuteMicrophone$4(xDialog3, i);
                }
            });
            xDialog2.setSystemDialog(2008);
            xDialog2.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.xiaopeng.carcontrol.viewmodel.audio.-$$Lambda$AudioViewModel$QicrY65WJoOejuJsK6NzuVJUx8A
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    AudioViewModel.lambda$confirmUnmuteMicrophone$5(dialogInterface);
                }
            });
            this.mUnmuteConfirmDialog = xDialog2;
        }
        XDialog xDialog3 = this.mMuteConfirmDialog;
        if (xDialog3 != null && xDialog3.isShowing()) {
            this.mMuteConfirmDialog.dismiss();
        }
        if (this.mUnmuteConfirmDialog != null) {
            VuiManager.instance().initVuiDialog(this.mUnmuteConfirmDialog, App.getInstance(), VuiManager.SCENE_MICHO_PHONE_UNMUTE_SETTING);
            this.mUnmuteConfirmDialog.show();
        }
    }

    public /* synthetic */ void lambda$confirmUnmuteMicrophone$3$AudioViewModel(XDialog dialog, int i) {
        setMicrophoneMute(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$confirmUnmuteMicrophone$4(XDialog xDialog1, int i) {
        if (BaseFeatureOption.getInstance().isBroadcastWhenCancelMuteOrUnmute()) {
            App.getInstance().sendBroadcast(new Intent(GlobalConstant.ACTION.ACTION_CANCEL_MICROPHONE_UNMUTE));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$confirmUnmuteMicrophone$5(DialogInterface dialog) {
        if (BaseFeatureOption.getInstance().isBroadcastWhenCancelMuteOrUnmute()) {
            App.getInstance().sendBroadcast(new Intent(GlobalConstant.ACTION.ACTION_CANCEL_MICROPHONE_UNMUTE));
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel
    public void dismissAllDialogs() {
        XDialog xDialog = this.mMuteConfirmDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
        XDialog xDialog2 = this.mUnmuteConfirmDialog;
        if (xDialog2 != null) {
            xDialog2.dismiss();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel
    public void setMicrophoneMute(boolean mute) {
        if (!App.isMainProcess()) {
            Intent intent = new Intent(App.getInstance().getApplicationContext(), CarControlService.class);
            intent.setAction(GlobalConstant.ACTION.ACTION_OPERATOR_MICROPHONE);
            intent.putExtra(GlobalConstant.EXTRA.KEY_IS_MUTE_MICROPHONE, mute);
            App.getInstance().startService(intent);
            LogUtils.i(TAG, "startService ACTION_OPERATOR_MICROPHONE: " + mute);
            return;
        }
        LogUtils.i(TAG, "setMicrophoneMute: " + mute, false);
        if (!mute) {
            try {
                this.mUnmuteManualFlag = true;
            } catch (Exception e) {
                LogUtils.e(TAG, "setMicrophoneMute failed", (Throwable) e, false);
                return;
            }
        }
        this.mAudioManager.setMicrophoneMute(mute);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel
    public boolean isMicrophoneMute() {
        try {
            boolean isMicrophoneMute = this.mAudioManager.isMicrophoneMute();
            LogUtils.i(TAG, "isMicrophoneMute: " + isMicrophoneMute, false);
            return isMicrophoneMute;
        } catch (Exception e) {
            LogUtils.e(TAG, "isMicrophoneMute failed", (Throwable) e, false);
            return true;
        }
    }

    private void registerMicrophoneMute() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.action.MICROPHONE_MUTE_CHANGED");
        try {
            App.getInstance().registerReceiver(this.mMicrophoneMuteReceiver, intentFilter);
        } catch (Exception e) {
            LogUtils.w(TAG, e.getMessage(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMicrophoneMuteChanged() {
        boolean isMicrophoneMute = isMicrophoneMute();
        this.mMicrophoneMuteData.postValue(Boolean.valueOf(isMicrophoneMute));
        if (!isMicrophoneMute && App.isMainProcess() && this.mUnmuteManualFlag) {
            this.mUnmuteManualFlag = false;
            NotificationHelper.getInstance().showToast(R.string.settings_microphone_unmuted_toast);
            SpeechHelper.getInstance().speak(ResUtils.getString(R.string.settings_microphone_unmuted_tts));
        }
    }

    public LiveData<Boolean> getMicrophoneMuteData() {
        return this.mMicrophoneMuteData;
    }
}
