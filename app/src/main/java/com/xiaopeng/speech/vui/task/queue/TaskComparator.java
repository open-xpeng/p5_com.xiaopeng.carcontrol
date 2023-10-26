package com.xiaopeng.speech.vui.task.queue;

import com.xiaopeng.speech.vui.VuiEngineImpl;
import com.xiaopeng.speech.vui.task.base.BaseTask;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import java.util.Comparator;

/* loaded from: classes2.dex */
public class TaskComparator implements Comparator<BaseTask> {
    private static final String TAG = "TaskComparator";

    @Override // java.util.Comparator
    public int compare(BaseTask baseTask, BaseTask baseTask2) {
        if (baseTask != null && baseTask2 != null) {
            LogUtils.logDebug(TAG, "Comparator: activeScene:  --  currentTask:" + baseTask.getSceneId() + " -- queueTask : " + baseTask2.getSceneId());
            if (VuiEngineImpl.mActiveSceneIds.size() == 0 && VuiEngineImpl.mRightPopPanelStack.size() == 0 && VuiEngineImpl.mLeftPopPanelStack.size() == 0) {
                LogUtils.logDebug(TAG, "compare: activeScene is null");
                return 0;
            } else if (baseTask.getSceneId().equals(baseTask2.getSceneId())) {
                LogUtils.logDebug(TAG, "compare: currentTask == queueTask");
                return 0;
            } else if (VuiUtils.isActiveSceneId(baseTask.getSceneId())) {
                LogUtils.logDebug(TAG, "compare: currentTask is activeScene");
                return -1;
            } else if (VuiUtils.isActiveSceneId(baseTask2.getSceneId())) {
                LogUtils.logDebug(TAG, "compare: queueTask is activeScene");
                return 1;
            }
        }
        return 0;
    }
}
