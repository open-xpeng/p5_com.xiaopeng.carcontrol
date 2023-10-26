package com.xiaopeng.carcontrol.helper;

import android.text.TextUtils;
import com.android.internal.util.CollectionUtils;
import com.google.gson.Gson;
import com.xiaopeng.carcontrol.bean.message.ApiRouterMessageBean;
import com.xiaopeng.carcontrol.bean.message.PushedMessageBean;
import com.xiaopeng.carcontrol.bean.message.SDSMessageBean;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.account.AccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class PushedMessageHelper {
    private static final String TAG = "MessageHelper";
    private static final int X_SMART_DRIVE_SCORE_PASSED_SCENE = 18002;
    private static final int X_SMART_DRIVE_SCORE_SCENE = 18001;

    public static void receivedPushMessage(String routerMessage) {
        LogUtils.i(TAG, "Message: " + routerMessage);
        if (TextUtils.isEmpty(routerMessage)) {
            LogUtils.w(TAG, "Received message is empty.");
            return;
        }
        ApiRouterMessageBean apiRouterMessageBean = (ApiRouterMessageBean) new Gson().fromJson(routerMessage, (Class<Object>) ApiRouterMessageBean.class);
        if (apiRouterMessageBean == null) {
            LogUtils.w(TAG, "Router message bean is null.");
            return;
        }
        PushedMessageBean pushedMessageBean = (PushedMessageBean) new Gson().fromJson((String) apiRouterMessageBean.getString_msg(), (Class<Object>) PushedMessageBean.class);
        if (pushedMessageBean == null) {
            LogUtils.w(TAG, "Message bean is null.");
        } else if (pushedMessageBean.getScene() == X_SMART_DRIVE_SCORE_SCENE) {
            processSmartDriveScore(pushedMessageBean.getContent());
        }
    }

    public static void receivePushMessageByIpc(String message) {
        PushedMessageBean pushedMessageBean = (PushedMessageBean) new Gson().fromJson(message, (Class<Object>) PushedMessageBean.class);
        if (pushedMessageBean == null) {
            LogUtils.w(TAG, "Message bean is null.");
        } else if (pushedMessageBean.getScene() == X_SMART_DRIVE_SCORE_SCENE) {
            processSmartDriveScore(pushedMessageBean.getContent());
        }
    }

    private static void processSmartDriveScore(String content) {
        LogUtils.i(TAG, "Process Smart Drive Score: " + content);
        if (!CarBaseConfig.getInstance().isSupportXPilotSafeExam()) {
            LogUtils.w(TAG, "Not support xpilot exam ignore the message.");
            return;
        }
        SDSMessageBean sDSMessageBean = (SDSMessageBean) new Gson().fromJson(content, (Class<Object>) SDSMessageBean.class);
        if (sDSMessageBean == null) {
            LogUtils.w(TAG, "sdsMessageBean is null.");
            return;
        }
        long uid = sDSMessageBean.getUid();
        if (uid < 0) {
            LogUtils.w(TAG, "invalid uid: " + uid);
            return;
        }
        List<Long> taskIdList = sDSMessageBean.getTaskIdList();
        if (CollectionUtils.isEmpty(taskIdList)) {
            LogUtils.w(TAG, "Task list is empty!");
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (taskIdList.contains(1L) && CarBaseConfig.getInstance().isSupportNgp()) {
            arrayList.add(1L);
        }
        if (taskIdList.contains(2L) && CarBaseConfig.getInstance().isSupportLcc()) {
            arrayList.add(2L);
        }
        if (taskIdList.contains(8L) && CarBaseConfig.getInstance().isSupportLcc()) {
            arrayList.add(8L);
        }
        if (taskIdList.contains(3L) && CarBaseConfig.getInstance().isSupportMemPark()) {
            arrayList.add(3L);
        }
        if (taskIdList.contains(4L) && CarBaseConfig.getInstance().isSupportAutoPark()) {
            arrayList.add(4L);
        }
        if (taskIdList.contains(6L) && CarBaseConfig.getInstance().isSupportCNgp()) {
            arrayList.add(6L);
        }
        if (taskIdList.contains(9L) && CarBaseConfig.getInstance().isSupportLcc()) {
            arrayList.add(9L);
        }
        if (taskIdList.contains(7L) && CarBaseConfig.getInstance().isSupportMemPark()) {
            arrayList.add(7L);
        }
        if (CollectionUtils.isEmpty(arrayList)) {
            LogUtils.w(TAG, "Task filter list is empty!");
            return;
        }
        String valueOf = String.valueOf(uid);
        if (TextUtils.isEmpty(valueOf)) {
            LogUtils.w(TAG, "Uid is empty!");
        } else {
            ((AccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class)).resetExamResultBySDS(arrayList, valueOf);
        }
    }
}
