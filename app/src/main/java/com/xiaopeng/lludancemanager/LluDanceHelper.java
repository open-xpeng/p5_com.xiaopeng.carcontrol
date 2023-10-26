package com.xiaopeng.lludancemanager;

import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel;
import com.xiaopeng.lludancemanager.viewmodel.LluDanceViewModel;

/* loaded from: classes2.dex */
public class LluDanceHelper {
    private static final int LLU_DANCE_AVAILABLE = 0;
    public static final int LLU_RES_NULL = -1;
    private static final String TAG = "LluDanceHelper";

    public static boolean checkLightDanceAvailable() {
        int lightDanceAvailable = ((LluDanceViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluDanceViewModel.class)).getLightDanceAvailable() * (-1);
        String str = TAG;
        LogUtils.d(str, "Karl log about start play video result = " + lightDanceAvailable);
        if (lightDanceAvailable == 0) {
            return true;
        }
        showAlertForStateNotMatch(lightDanceAvailable);
        LogUtils.d(str, "Karl log about checkLightDance NOT Available result = " + lightDanceAvailable);
        return false;
    }

    public static void showAlertForStateNotMatch(int previewResult) {
        int lluStateResByResult = getLluStateResByResult(previewResult);
        if (lluStateResByResult != -1) {
            NotificationHelper.getInstance().showToast(lluStateResByResult);
        }
    }

    public static int getLluStateTips() {
        int lightDanceAvailable = ((LluDanceViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluDanceViewModel.class)).getLightDanceAvailable() * (-1);
        LogUtils.d(TAG, "Karl log about start play video result = " + lightDanceAvailable);
        return getLluStateResByResult(lightDanceAvailable);
    }

    private static int getLluStateResByResult(int result) {
        if ((result >> 2) == 1) {
            LogUtils.d(TAG, "Karl log 播放灯舞条件不满足, 正在打电话");
            return R.string.llu_effect_condition_on_call_disable;
        } else if (result % 2 == 1) {
            LogUtils.d(TAG, "Karl log 播放灯舞条件不满足,当前非P档");
            return R.string.llu_effect_condition_disable;
        } else if ((result >> 1) % 2 == 1) {
            LogUtils.d(TAG, "Karl log 播放灯舞条件不满足, 当前转向灯激活中");
            return R.string.llu_effect_condition_disable;
        } else {
            return -1;
        }
    }
}
