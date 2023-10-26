package com.xiaopeng.speech.vui.task;

import com.xiaopeng.speech.vui.task.base.BaseTask;
import com.xiaopeng.speech.vui.utils.LogUtils;
import java.util.List;

/* loaded from: classes2.dex */
public class TaskDispatcher {
    private String TAG = getClass().getSimpleName();

    /* loaded from: classes2.dex */
    public enum TaskType {
        BUILD,
        UPDATE,
        ADD,
        REMOVE,
        UPDATEATTRIBUTE,
        UPDATERECYCLEVIEWITEM
    }

    public void dispatchTask(List<TaskWrapper> list) {
        BaseTask addSceneTask;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (TaskWrapper taskWrapper : list) {
            switch (AnonymousClass1.$SwitchMap$com$xiaopeng$speech$vui$task$TaskDispatcher$TaskType[taskWrapper.getTaskType().ordinal()]) {
                case 1:
                    addSceneTask = new AddSceneTask(taskWrapper);
                    break;
                case 2:
                    addSceneTask = new BuildSceneTask(taskWrapper);
                    break;
                case 3:
                    addSceneTask = new RemoveTask(taskWrapper);
                    break;
                case 4:
                    addSceneTask = new UpdateSceneTask(taskWrapper);
                    break;
                case 5:
                    addSceneTask = new UpdateSceneAttributeTask(taskWrapper);
                    break;
                case 6:
                    addSceneTask = new UpdateRecyclerItemTask(taskWrapper);
                    break;
                default:
                    addSceneTask = new BuildSceneTask(taskWrapper);
                    break;
            }
            submitTask(addSceneTask);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.speech.vui.task.TaskDispatcher$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$speech$vui$task$TaskDispatcher$TaskType;

        static {
            int[] iArr = new int[TaskType.values().length];
            $SwitchMap$com$xiaopeng$speech$vui$task$TaskDispatcher$TaskType = iArr;
            try {
                iArr[TaskType.ADD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$speech$vui$task$TaskDispatcher$TaskType[TaskType.BUILD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$speech$vui$task$TaskDispatcher$TaskType[TaskType.REMOVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$speech$vui$task$TaskDispatcher$TaskType[TaskType.UPDATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$speech$vui$task$TaskDispatcher$TaskType[TaskType.UPDATEATTRIBUTE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$speech$vui$task$TaskDispatcher$TaskType[TaskType.UPDATERECYCLEVIEWITEM.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public void submitTask(BaseTask baseTask) {
        if (baseTask == null || baseTask.wrapper == null) {
            return;
        }
        try {
            ThreadPoolProxyFactory.getThreadPool().execute(baseTask);
        } catch (Exception e) {
            LogUtils.e(this.TAG, "submitTask e:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeTask(String str) {
        ThreadPoolProxyFactory.getThreadPool().removeTask(str);
    }
}
