package com.xiaopeng.carcontrol;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.view.dialog.panel.ControlPanelManager;
import com.xiaopeng.xui.app.XDialog;

/* loaded from: classes.dex */
public class CapsuleDialogService extends Service {
    public static final int SLEEP_ALARM_STOP_DISMISS_TIME_OUT = 600000;
    public static final String SLEEP_ALARM_TIME = "alarm_time";
    public static final String TAG = "CapsuleDialogService";
    private XDialog mDcChargeSecurityDialog;
    private XDialog mLowBatteryDialog;
    private XDialog mPassiveExitDialog;
    private XDialog mSecurityDialog;
    private XDialog mSleepAlarmStopDialog;
    private TextView mStopTargetTimeTv;
    private Runnable mSleepAlarmStopDialogRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleDialogService$AA7TTYfpKQoL-HaOkKSN3t_3OuA
        @Override // java.lang.Runnable
        public final void run() {
            CapsuleDialogService.this.dismissSleepAlarmStopDialog();
        }
    };
    private final Handler mHandler = new Handler();

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG, "onCreate");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void handleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        LogUtils.d(TAG, "handleIntent action: " + action);
        if (action == null) {
            return;
        }
        int intExtra = intent.getIntExtra(GlobalConstant.EXTRA.KEY_MODE_TYPE, 1);
        action.hashCode();
        char c = 65535;
        switch (action.hashCode()) {
            case -1575243113:
                if (action.equals(GlobalConstant.ACTION.ACTION_CAPSULE_DC_CHARGE_SECURITY_CONFIRM_SHOW)) {
                    c = 0;
                    break;
                }
                break;
            case -608309685:
                if (action.equals(GlobalConstant.ACTION.ACTION_CAPSULE_SLEEP_ALARM_STOP_DISMISS_DIALOG)) {
                    c = 1;
                    break;
                }
                break;
            case 25106637:
                if (action.equals(GlobalConstant.ACTION.ACTION_CAPSULE_PASSIVE_EXIT_SHOW)) {
                    c = 2;
                    break;
                }
                break;
            case 659728014:
                if (action.equals(GlobalConstant.ACTION.ACTION_CAPSULE_LOW_BATTERY_TIPS_SHOW)) {
                    c = 3;
                    break;
                }
                break;
            case 882249442:
                if (action.equals(GlobalConstant.ACTION.ACTION_CAPSULE_SECURITY_CONFIRM_SHOW)) {
                    c = 4;
                    break;
                }
                break;
            case 1838822923:
                if (action.equals(GlobalConstant.ACTION.ACTION_CAPSULE_SLEEP_ALARM_STOP_SHOW)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                showDialogDCChargeSecurityTips(intExtra == 1);
                return;
            case 1:
                dismissSleepAlarmStopDialog();
                return;
            case 2:
                showPassiveExitDialogTips(intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_VIP_SEAT, false), intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_IN_SUB_MODE, false), intExtra == 1);
                return;
            case 3:
                showLowBatteryTips(intExtra == 1);
                return;
            case 4:
                showDialogSecurityTips(intent.getBooleanExtra(GlobalConstant.EXTRA.KEY_SPACE_EXIT_HEAD_REST, false));
                return;
            case 5:
                showSleepAlarmStopDialog(intent.getStringExtra(SLEEP_ALARM_TIME));
                return;
            default:
                return;
        }
    }

    private void showSleepAlarmStopDialog(String showTime) {
        if (this.mSleepAlarmStopDialog == null) {
            setTheme(R.style.AppTheme);
            XDialog xDialog = new XDialog(this, 2131886921);
            this.mSleepAlarmStopDialog = xDialog;
            xDialog.setSystemDialog(ControlPanelManager.SUPER_SYSTEM_TYPE);
            View inflate = LayoutInflater.from(this).inflate(R.layout.space_sleep_time_dialog, this.mSleepAlarmStopDialog.getContentView(), false);
            this.mStopTargetTimeTv = (TextView) inflate.findViewById(R.id.space_sleep_alarm_time);
            inflate.findViewById(R.id.space_sleep_alarm_time_tips).setVisibility(8);
            final LottieAnimationView lottieAnimationView = (LottieAnimationView) inflate.findViewById(R.id.space_alarm_animator);
            lottieAnimationView.setAnimation("capsule_alarm_stop.json");
            lottieAnimationView.setRepeatCount(-1);
            this.mSleepAlarmStopDialog.setCustomView(inflate);
            this.mSleepAlarmStopDialog.setTitle(R.string.space_sleep_alarm_msg);
            this.mSleepAlarmStopDialog.setPositiveButton(R.string.space_sleep_alarm_stop);
            this.mSleepAlarmStopDialog.getContentView().setBackgroundResource(R.drawable.space_capsule_dialog_bg);
            this.mSleepAlarmStopDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleDialogService$-YJsmjA6Ymwx3-nJvjWWbd5wqsQ
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    CapsuleDialogService.this.lambda$showSleepAlarmStopDialog$0$CapsuleDialogService(lottieAnimationView, dialogInterface);
                }
            });
            this.mSleepAlarmStopDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaopeng.carcontrol.CapsuleDialogService.1
                @Override // android.content.DialogInterface.OnShowListener
                public void onShow(DialogInterface dialog) {
                    LogUtils.i(CapsuleDialogService.TAG, "mSleepAlarmStopDialog show");
                    lottieAnimationView.playAnimation();
                    CapsuleDialogService.this.mHandler.removeCallbacks(CapsuleDialogService.this.mSleepAlarmStopDialogRunnable);
                    CapsuleDialogService.this.mHandler.postDelayed(CapsuleDialogService.this.mSleepAlarmStopDialogRunnable, 600000L);
                }
            });
        }
        if (!TextUtils.isEmpty(showTime)) {
            this.mStopTargetTimeTv.setText(showTime);
        }
        this.mSleepAlarmStopDialog.show();
    }

    public /* synthetic */ void lambda$showSleepAlarmStopDialog$0$CapsuleDialogService(final LottieAnimationView lottieAnimationView, DialogInterface dialog) {
        notifyCapsuleAlarmStop();
        if (lottieAnimationView != null && lottieAnimationView.isAnimating()) {
            lottieAnimationView.cancelAnimation();
        }
        this.mSleepAlarmStopDialog = null;
        if (isNeedStopSelf()) {
            stopSelf();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissSleepAlarmStopDialog() {
        XDialog xDialog = this.mSleepAlarmStopDialog;
        if (xDialog == null || !xDialog.isShowing()) {
            return;
        }
        LogUtils.i(TAG, "mSleepAlarmStopDialog dismiss");
        this.mSleepAlarmStopDialog.dismiss();
    }

    private void notifyCapsuleAlarmStop() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(GlobalConstant.ACTION.ACTION_CAPSULE_SLEEP_ALARM_STOP_DISMISS));
    }

    private void showPassiveExitDialogTips(boolean isVipSeat, boolean subMode, boolean isSleepMode) {
        if (isVipSeat) {
            NotificationHelper.getInstance().showToast(R.string.vip_seat_exit_active);
            return;
        }
        int i = R.string.space_capsule_sleep_exit_passive;
        if (subMode) {
            if (this.mPassiveExitDialog == null) {
                Context applicationContext = App.getInstance().getApplicationContext();
                applicationContext.setTheme(R.style.AppTheme);
                XDialog xDialog = new XDialog(applicationContext);
                this.mPassiveExitDialog = xDialog;
                xDialog.getDialog().getWindow().setType(2008);
                XDialog xDialog2 = this.mPassiveExitDialog;
                if (!isSleepMode) {
                    i = R.string.space_capsule_cinema_exit_passive;
                }
                xDialog2.setTitle(i);
                this.mPassiveExitDialog.setMessage(isSleepMode ? R.string.space_capsule_sleep_exit_passive_tts : R.string.space_capsule_cinema_exit_passive_tts);
                this.mPassiveExitDialog.setPositiveButton(R.string.prompt_ok);
                this.mPassiveExitDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleDialogService$kD2A46-9XqAn7ivVxMWmCV_1icA
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        CapsuleDialogService.this.lambda$showPassiveExitDialogTips$1$CapsuleDialogService(dialogInterface);
                    }
                });
            }
            this.mPassiveExitDialog.show();
        } else {
            NotificationHelper notificationHelper = NotificationHelper.getInstance();
            if (!isSleepMode) {
                i = R.string.space_capsule_cinema_exit_passive;
            }
            notificationHelper.showToast(i);
        }
        PageEnum pageEnum = PageEnum.SPACE_CAPSULE_PAGE;
        BtnEnum btnEnum = BtnEnum.SPACE_CAPSULE_PAGE_EXIT;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(isSleepMode ? 1 : 2);
        objArr[1] = 6;
        StatisticUtils.sendSpaceCapsuleStatistic(pageEnum, btnEnum, objArr);
    }

    public /* synthetic */ void lambda$showPassiveExitDialogTips$1$CapsuleDialogService(DialogInterface dialog) {
        this.mPassiveExitDialog = null;
        if (isNeedStopSelf()) {
            stopSelf();
        }
    }

    private void showDialogSecurityTips(boolean headrest) {
        if (!headrest) {
            if (this.mSecurityDialog == null) {
                Context applicationContext = App.getInstance().getApplicationContext();
                applicationContext.setTheme(R.style.AppTheme);
                XDialog xDialog = new XDialog(applicationContext);
                this.mSecurityDialog = xDialog;
                xDialog.getDialog().getWindow().setType(2008);
                this.mSecurityDialog.setTitle(R.string.vip_seat_confirm_dialog_title);
                this.mSecurityDialog.setMessage(R.string.space_capsule_security_tips);
                this.mSecurityDialog.setPositiveButton(R.string.prompt_ok);
                this.mSecurityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleDialogService$qJXd98Kv0LFqdh74PkdRXlJwul0
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        CapsuleDialogService.this.lambda$showDialogSecurityTips$2$CapsuleDialogService(dialogInterface);
                    }
                });
            }
            this.mSecurityDialog.show();
            SpeechHelper.getInstance().speak(ResUtils.getString(R.string.space_capsule_security_tts));
            return;
        }
        NotificationHelper.getInstance().showToast(R.string.vip_seat_notice_gear_p);
        SpeechHelper.getInstance().speak(ResUtils.getString(R.string.vip_seat_notice_gear_p));
    }

    public /* synthetic */ void lambda$showDialogSecurityTips$2$CapsuleDialogService(DialogInterface dialog) {
        this.mSecurityDialog = null;
        if (isNeedStopSelf()) {
            stopSelf();
        }
    }

    private void showDialogDCChargeSecurityTips(boolean isSleepMode) {
        notifyCapsuleAlarmStop();
        XDialog xDialog = this.mDcChargeSecurityDialog;
        int i = R.string.space_capsule_sleep_dc_charge_tips;
        if (xDialog == null) {
            Context applicationContext = App.getInstance().getApplicationContext();
            applicationContext.setTheme(R.style.AppTheme);
            XDialog xDialog2 = new XDialog(applicationContext);
            this.mDcChargeSecurityDialog = xDialog2;
            xDialog2.getDialog().getWindow().setType(2008);
            this.mDcChargeSecurityDialog.setTitle(R.string.vip_seat_confirm_dialog_title);
            this.mDcChargeSecurityDialog.setMessage(isSleepMode ? R.string.space_capsule_sleep_dc_charge_tips : R.string.space_capsule_cinema_dc_charge_tips);
            this.mDcChargeSecurityDialog.setPositiveButton(R.string.prompt_ok);
            this.mDcChargeSecurityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleDialogService$bI5ZyGyuwB-SIJPf6x9R8wKoiys
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    CapsuleDialogService.this.lambda$showDialogDCChargeSecurityTips$3$CapsuleDialogService(dialogInterface);
                }
            });
        }
        this.mDcChargeSecurityDialog.show();
        SpeechHelper speechHelper = SpeechHelper.getInstance();
        if (!isSleepMode) {
            i = R.string.space_capsule_cinema_dc_charge_tips;
        }
        speechHelper.speak(ResUtils.getString(i));
    }

    public /* synthetic */ void lambda$showDialogDCChargeSecurityTips$3$CapsuleDialogService(DialogInterface dialog) {
        this.mDcChargeSecurityDialog = null;
        if (isNeedStopSelf()) {
            stopSelf();
        }
    }

    private void showLowBatteryTips(boolean isSleepMode) {
        XDialog xDialog = this.mLowBatteryDialog;
        int i = R.string.space_capsule_sleep_low_battery_enter_tips;
        if (xDialog == null) {
            Context applicationContext = App.getInstance().getApplicationContext();
            applicationContext.setTheme(R.style.AppTheme);
            XDialog xDialog2 = new XDialog(applicationContext);
            this.mLowBatteryDialog = xDialog2;
            xDialog2.getDialog().getWindow().setType(2008);
            this.mLowBatteryDialog.setTitle(R.string.vip_seat_confirm_dialog_title);
            this.mLowBatteryDialog.setMessage(isSleepMode ? R.string.space_capsule_sleep_low_battery_enter_tips : R.string.space_capsule_cinema_low_battery_enter_tips);
            this.mLowBatteryDialog.setPositiveButton(R.string.prompt_ok);
            this.mLowBatteryDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CapsuleDialogService$jH4ZY2N-oLOj-8iyWwEjUW7vpSA
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    CapsuleDialogService.this.lambda$showLowBatteryTips$4$CapsuleDialogService(dialogInterface);
                }
            });
        }
        this.mLowBatteryDialog.show();
        SpeechHelper speechHelper = SpeechHelper.getInstance();
        if (!isSleepMode) {
            i = R.string.space_capsule_cinema_low_battery_enter_tips;
        }
        speechHelper.speak(ResUtils.getString(i));
    }

    public /* synthetic */ void lambda$showLowBatteryTips$4$CapsuleDialogService(DialogInterface dialog) {
        this.mLowBatteryDialog = null;
        if (isNeedStopSelf()) {
            stopSelf();
        }
    }

    private boolean isNeedStopSelf() {
        XDialog xDialog = this.mSecurityDialog;
        if (xDialog == null || !xDialog.isShowing()) {
            XDialog xDialog2 = this.mPassiveExitDialog;
            if (xDialog2 == null || !xDialog2.isShowing()) {
                XDialog xDialog3 = this.mSleepAlarmStopDialog;
                if (xDialog3 == null || !xDialog3.isShowing()) {
                    XDialog xDialog4 = this.mDcChargeSecurityDialog;
                    if (xDialog4 == null || !xDialog4.isShowing()) {
                        XDialog xDialog5 = this.mLowBatteryDialog;
                        return xDialog5 == null || !xDialog5.isShowing();
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy");
    }
}
