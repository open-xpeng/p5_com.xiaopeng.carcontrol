package com.xiaopeng.lludancemanager.view;

import android.animation.LayoutTransition;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lludancemanager.LluDanceOrderTimeHelper;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.lludancemanager.helper.LluDanceAlarmHelper;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTextView;
import java.util.Calendar;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class LluDanceSettingDialog extends LluSettingBaseDialog implements View.OnClickListener, DialogInterface.OnShowListener, IVuiElementListener {
    private static final long ONE_DAY_TIME_IN_MILLS = 86400000;
    private static final float ORDER_PLAY_TIME_GAP = 5.0f;
    private static final float ORDER_PLAY_TIME_GAP_IN_MILLS = 300000.0f;
    private static final int ORDER_PLAY_TIME_INVALID = -1;
    private static final int ORDER_PLAY_TIME_STEP = 6;
    private static final String TAG = "LluDanceSettingDialog";
    private XSwitch mOrderPlaySwitch;
    private XTextView mOrderPlayTime;
    private XImageButton mOrderPlayTimePlus;
    private XImageButton mOrderPlayTimeReduce;
    private XRelativeLayout mOrderTimeLayout;
    private XImageView mOrderTimeTomorrow;
    private long mOrderedTimeInMills;
    private XTextView mPlayDirectlyTips;

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String s, IVuiElementBuilder iVuiElementBuilder) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LluDanceSettingDialog(Context context) {
        super(context);
        VuiManager.instance().initVuiDialog(this, App.getInstance(), VuiManager.SCENE_LLU_PLAY_SETTING_DIALOG, this);
    }

    public void setOrderPlaySwitchCheck(boolean check) {
        this.mOrderPlaySwitch.setChecked(check);
    }

    public boolean isOrderPlaySwitchCheck() {
        return this.mOrderPlaySwitch.isChecked();
    }

    public long getOrderedPlayTime() {
        return this.mOrderedTimeInMills;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.lludancemanager.view.LluSettingBaseDialog
    public void initCustomDialog(View rootView) {
        super.initCustomDialog(rootView);
        setTitle(R.string.llu_dance_double_confirm_dialog_title);
        ((ViewGroup) getDialog().getWindow().getDecorView()).setLayoutTransition(new LayoutTransition());
        this.mOrderPlaySwitch = (XSwitch) rootView.findViewById(R.id.llu_double_confirm_dialog_order_play);
        this.mPlayDirectlyTips = (XTextView) rootView.findViewById(R.id.llu_dance_main_fragment_confirm_dialog_play_tips);
        this.mOrderTimeLayout = (XRelativeLayout) rootView.findViewById(R.id.llu_double_confirm_dialog_order_time_container);
        this.mOrderPlayTimePlus = (XImageButton) rootView.findViewById(R.id.llu_double_confirm_dialog_plus_order_time);
        this.mOrderPlayTimeReduce = (XImageButton) rootView.findViewById(R.id.llu_double_confirm_dialog_reduce_order_time);
        this.mOrderPlayTime = (XTextView) rootView.findViewById(R.id.llu_double_confirm_dialog_order_time);
        this.mOrderTimeTomorrow = (XImageView) rootView.findViewById(R.id.llu_double_confirm_dialog_order_time_tomorrow);
        VuiUtils.addHasFeedbackProp(this.mOrderPlayTimeReduce);
        VuiUtils.addHasFeedbackProp(this.mOrderPlayTimePlus);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.lludancemanager.view.LluSettingBaseDialog
    public void setListener() {
        super.setListener();
        this.mOrderPlaySwitch.setOnCheckedChangeListener(this);
        this.mOrderPlaySwitch.setChecked(SharedPreferenceUtil.getLluOrderPlay());
        this.mOrderPlayTimePlus.setOnClickListener(this);
        this.mOrderPlayTimeReduce.setOnClickListener(this);
        setOnShowListener(this);
    }

    @Override // com.xiaopeng.lludancemanager.view.LluSettingBaseDialog
    View getLayout(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.llu_dance_confirm_dialog, getContentView(), false);
    }

    @Override // com.xiaopeng.lludancemanager.view.LluSettingBaseDialog, android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        super.onCheckedChanged(buttonView, isChecked);
        if (buttonView == this.mOrderPlaySwitch) {
            LogUtils.d(TAG, "order play switched check = " + isChecked);
            SharedPreferenceUtil.setLluOrderPlay(isChecked);
            if (isChecked) {
                this.mOrderTimeLayout.setVisibility(0);
                this.mPlayDirectlyTips.setVisibility(8);
                refreshOrderedTime();
            } else {
                this.mOrderTimeLayout.setVisibility(8);
                this.mPlayDirectlyTips.setVisibility(0);
            }
            VuiManager.instance().updateVuiScene(VuiManager.SCENE_LLU_PLAY_SETTING_DIALOG, this.mContext, buttonView, this.mOrderTimeLayout);
            return;
        }
        VuiManager.instance().updateVuiScene(VuiManager.SCENE_LLU_PLAY_SETTING_DIALOG, this.mContext, buttonView);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v.getId() == R.id.llu_double_confirm_dialog_reduce_order_time) {
            orderPlayTimeController(false);
        } else if (v.getId() == R.id.llu_double_confirm_dialog_plus_order_time) {
            orderPlayTimeController(true);
        }
    }

    private void orderPlayTimeController(boolean plus) {
        double floor;
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(this.mOrderedTimeInMills);
        int i = calendar2.get(12);
        if (plus) {
            floor = Math.ceil((i + 1) / ORDER_PLAY_TIME_GAP);
        } else {
            floor = Math.floor((i - 1) / ORDER_PLAY_TIME_GAP);
        }
        calendar2.set(12, (int) (floor * 5.0d));
        boolean z = ((float) (calendar2.getTimeInMillis() - calendar.getTimeInMillis())) > ORDER_PLAY_TIME_GAP_IN_MILLS;
        boolean z2 = calendar2.getTimeInMillis() - calendar.getTimeInMillis() < 86400000;
        String str = TAG;
        LogUtils.d(str, "target calendar time = " + calendar2.getTimeInMillis() + "    current time = " + calendar.getTimeInMillis() + "     reduce  enable = " + z + "    plus enable =" + z2);
        this.mOrderPlayTimeReduce.setEnabled(z);
        this.mOrderPlayTimePlus.setEnabled(z2);
        VuiManager.instance().updateVuiScene(VuiManager.SCENE_LLU_PLAY_SETTING_DIALOG, this.mContext, this.mOrderPlayTimePlus, this.mOrderPlayTimeReduce);
        if ((!plus && z) || (plus && z2)) {
            long timeInMillis = calendar2.getTimeInMillis();
            this.mOrderedTimeInMills = timeInMillis;
            this.mOrderPlayTime.setText(LluDanceOrderTimeHelper.getDateFormatForTimeInMills(timeInMillis));
        } else {
            LogUtils.d(str, "target time invalid, ignore it plus = " + plus);
            if (plus) {
                this.mOrderPlayTimePlus.setEnabled(false);
            } else {
                this.mOrderPlayTimeReduce.setEnabled(false);
            }
        }
        this.mOrderTimeTomorrow.setVisibility(timeInTomorrow(calendar2.getTimeInMillis()) ? 0 : 8);
        refreshControllerButtonEnable();
    }

    private boolean timeInTomorrow(long targetTime) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(targetTime);
        int i = calendar2.get(6);
        int i2 = calendar.get(6);
        LogUtils.d(TAG, "orderPlayTimeController target day" + i + "      current = " + i2);
        return i > i2;
    }

    private String getOrderTimeString() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(12, (int) (Math.ceil((calendar.get(12) + 6) / ORDER_PLAY_TIME_GAP) * 5.0d));
        calendar.set(13, 0);
        calendar.set(14, 0);
        this.mOrderedTimeInMills = calendar.getTimeInMillis();
        PendingIntent alarmUpForDance = LluDanceAlarmHelper.alarmUpForDance(this.mContext);
        if (alarmUpForDance != null) {
            long longExtra = alarmUpForDance.getIntent().getLongExtra(LluDanceAlarmHelper.ORDER_PLAY_DANCE_ITEM_TIME, -1L);
            String str = TAG;
            LogUtils.d(str, "get order time from intent long = " + longExtra);
            if (longExtra != -1) {
                this.mOrderedTimeInMills = longExtra;
            }
            LogUtils.d(str, "getOrderTimeString existing intent not null, ordered time in mills = " + this.mOrderedTimeInMills + "    long extra  = " + longExtra);
        }
        return LluDanceOrderTimeHelper.getDateFormatForTimeInMills(this.mOrderedTimeInMills);
    }

    @Override // android.content.DialogInterface.OnShowListener
    public void onShow(DialogInterface dialog) {
        refreshOrderedTime();
    }

    public void refreshOrderedTime() {
        String orderTimeString = getOrderTimeString();
        LogUtils.d(TAG, "dance setting dialog on show callback order time string = " + orderTimeString);
        this.mOrderPlayTime.setText(orderTimeString);
        refreshControllerButtonEnable();
        this.mOrderTimeTomorrow.setVisibility(timeInTomorrow(this.mOrderedTimeInMills) ? 0 : 8);
    }

    private void refreshControllerButtonEnable() {
        boolean z = (((float) this.mOrderedTimeInMills) - ORDER_PLAY_TIME_GAP_IN_MILLS) - ((float) System.currentTimeMillis()) > ORDER_PLAY_TIME_GAP_IN_MILLS;
        boolean z2 = (((float) this.mOrderedTimeInMills) + ORDER_PLAY_TIME_GAP_IN_MILLS) - ((float) System.currentTimeMillis()) < 8.64E7f;
        this.mOrderPlayTimeReduce.setEnabled(z);
        this.mOrderPlayTimePlus.setEnabled(z2);
        VuiManager.instance().updateVuiScene(VuiManager.SCENE_LLU_PLAY_SETTING_DIALOG, this.mContext, this.mOrderPlayTimePlus, this.mOrderPlayTimeReduce);
    }

    private JSONObject getVuiPropsJson(VuiView view) {
        JSONObject vuiProps = view.getVuiProps();
        return vuiProps == null ? new JSONObject() : vuiProps;
    }

    protected void addVuiProp(VuiView view, String prop, String value) {
        if (TextUtils.isEmpty(prop) || TextUtils.isEmpty(value)) {
            LogUtils.e(TAG, "addVuiProps failed with prop=" + prop + ", value=" + value, false);
            return;
        }
        LogUtils.i(TAG, "addVuiProps prop=" + prop + ", value=" + value, false);
        JSONObject vuiPropsJson = getVuiPropsJson(view);
        try {
            vuiPropsJson.put(prop, value);
        } catch (Exception e) {
            LogUtils.e(TAG, "addVuiProps " + prop + " failed :" + e.getMessage(), false);
        }
        view.setVuiProps(vuiPropsJson);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent vuiEvent) {
        if (view != null) {
            VuiFloatingLayerManager.show(view);
            return false;
        }
        return false;
    }

    @Override // com.xiaopeng.xui.app.XDialog
    public void show() {
        VuiManager.instance().initVuiDialog(this, App.getInstance(), VuiManager.SCENE_LLU_PLAY_SETTING_DIALOG, this);
        super.show();
    }
}
