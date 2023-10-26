package com.xiaopeng.speech.vui.task.queue;

import android.text.TextUtils;
import android.view.View;
import com.xiaopeng.speech.vui.VuiSceneManager;
import com.xiaopeng.speech.vui.task.TaskDispatcher;
import com.xiaopeng.speech.vui.task.base.BaseTask;
import com.xiaopeng.speech.vui.utils.LogUtils;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class TaskWrapperQueue extends DeDuplicationQueue<String, BaseTask> {
    private String TAG = "TaskWrapperQueue";

    @Override // com.xiaopeng.speech.vui.task.queue.DeDuplicationQueue, java.util.concurrent.BlockingQueue, java.util.Queue
    public boolean offer(BaseTask baseTask) {
        return putTaskToQueue(baseTask, false);
    }

    @Override // com.xiaopeng.speech.vui.task.queue.DeDuplicationQueue, java.util.Queue
    public BaseTask poll() {
        return takeTaskFromQueue(false);
    }

    @Override // com.xiaopeng.speech.vui.task.queue.DeDuplicationQueue, java.util.concurrent.BlockingQueue
    public void put(BaseTask baseTask) {
        putTaskToQueue(baseTask, true);
    }

    @Override // com.xiaopeng.speech.vui.task.queue.DeDuplicationQueue, java.util.concurrent.BlockingQueue
    public BaseTask take() {
        return takeTaskFromQueue(true);
    }

    private boolean putTaskToQueue(BaseTask baseTask, boolean z) {
        try {
        } catch (Exception e) {
            LogUtils.e(this.TAG, "putTaskToQueue e:" + e.getMessage());
        }
        if (baseTask.wrapper == null) {
            return false;
        }
        LogUtils.d(this.TAG, "队列缓存大小: offer:" + this.queue.size() + ",data" + this.queue.toString() + "," + Thread.currentThread().getName());
        String vid = baseTask.wrapper.getVid();
        if (!TextUtils.isEmpty(vid) && this.map.containsKey(vid)) {
            LogUtils.i(this.TAG, "发现队列中相同id有数据");
            BaseTask baseTask2 = (BaseTask) this.map.get(vid);
            if (baseTask2 != null && baseTask2.wrapper != null) {
                if (baseTask2.wrapper.getTaskType() == baseTask.wrapper.getTaskType()) {
                    this.queue.remove(baseTask2);
                    this.map.remove(vid);
                } else if (baseTask2.wrapper.getTaskType() == TaskDispatcher.TaskType.ADD && baseTask.wrapper.getTaskType() == TaskDispatcher.TaskType.REMOVE) {
                    this.queue.remove(baseTask2);
                    this.map.remove(vid);
                    return true;
                }
                removeTaskFromSceneMap(baseTask2);
            }
            this.map.put(vid, baseTask);
        } else {
            SoftReference<View> view = baseTask.wrapper.getView();
            if (view != null && this.viewMap.containsKey(view)) {
                LogUtils.i(this.TAG, "发现队列中有相同view数据");
                BaseTask baseTask3 = (BaseTask) this.viewMap.get(view);
                if (baseTask3 != null && baseTask3.wrapper != null) {
                    if (baseTask3.wrapper.getTaskType() == baseTask.wrapper.getTaskType()) {
                        this.queue.remove(baseTask3);
                        this.viewMap.remove(view);
                    } else if (baseTask3.wrapper.getTaskType() == TaskDispatcher.TaskType.ADD && baseTask.wrapper.getTaskType() == TaskDispatcher.TaskType.REMOVE) {
                        this.queue.remove(baseTask3);
                        this.viewMap.remove(view);
                        return true;
                    }
                    removeTaskFromSceneMap(baseTask3);
                }
                this.viewMap.put(view, baseTask);
            }
        }
        List<BaseTask> list = this.sceneMap.get(baseTask.wrapper.getSceneId());
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(baseTask);
        this.sceneMap.put(baseTask.wrapper.getSceneId(), list);
        if (z) {
            this.queue.put(baseTask);
        } else {
            this.queue.offer(baseTask);
        }
        return true;
    }

    private BaseTask takeTaskFromQueue(boolean z) {
        BaseTask baseTask;
        BaseTask baseTask2 = null;
        try {
            if (z) {
                baseTask = (BaseTask) this.queue.take();
            } else {
                baseTask = (BaseTask) this.queue.poll();
            }
            if (baseTask != null) {
                try {
                    if (baseTask.wrapper != null) {
                        String vid = baseTask.wrapper.getVid();
                        if (!TextUtils.isEmpty(vid)) {
                            this.map.remove(vid);
                        }
                        SoftReference<View> view = baseTask.wrapper.getView();
                        if (view != null && this.viewMap.containsKey(view)) {
                            this.viewMap.remove(view);
                        }
                        removeTaskFromSceneMap(baseTask);
                        if (baseTask.wrapper.getTaskType() == TaskDispatcher.TaskType.BUILD || VuiSceneManager.instance().canUpdateScene(baseTask.wrapper.getSceneId())) {
                            return baseTask;
                        }
                        this.queue.remove(baseTask);
                        LogUtils.d(this.TAG, "takeTaskFromQueue no cache:" + baseTask.wrapper.getSceneId());
                        return null;
                    }
                    return baseTask;
                } catch (Exception e) {
                    baseTask2 = baseTask;
                    e = e;
                    LogUtils.e(this.TAG, "takeTaskFromQueue e:" + e.getMessage());
                    return baseTask2;
                }
            }
            return baseTask;
        } catch (Exception e2) {
            e = e2;
        }
    }
}
