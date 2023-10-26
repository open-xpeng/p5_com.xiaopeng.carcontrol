package com.xiaopeng.speech.vui.task;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.speech.vui.VuiSceneManager;
import com.xiaopeng.speech.vui.model.VuiSceneInfo;
import com.xiaopeng.speech.vui.task.base.BaseTask;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class RemoveTask extends BaseTask {
    private String TAG;
    private String sceneId;
    private TaskWrapper viewWrapper;

    public RemoveTask(TaskWrapper taskWrapper) {
        super(taskWrapper);
        this.TAG = "VuiEngine_RemoveTask";
        this.viewWrapper = taskWrapper;
        this.sceneId = taskWrapper.getSceneId();
    }

    @Override // com.xiaopeng.speech.vui.task.base.Task
    public void execute() {
        RemoveTask removeTask;
        int i;
        RemoveTask removeTask2;
        List<SoftReference<View>> list;
        List<String> list2;
        String str;
        char c;
        boolean z;
        boolean z2;
        IVuiElementChangedListener iVuiElementChangedListener;
        RemoveTask removeTask3 = this;
        try {
            String str2 = ",";
            char c2 = 3;
            boolean z3 = true;
            IVuiElementChangedListener iVuiElementChangedListener2 = null;
            boolean z4 = false;
            try {
                if (removeTask3.viewWrapper.getElementGroupId() == null) {
                    VuiSceneInfo sceneInfo = VuiSceneManager.instance().getSceneInfo(removeTask3.sceneId);
                    if (sceneInfo != null) {
                        sceneInfo.setLastAddStr(null);
                    }
                    if (removeTask3.viewWrapper.getViewList() == null) {
                        LogUtils.logInfo(removeTask3.TAG, "RemoveTask: sceneId" + removeTask3.sceneId);
                        if (sceneInfo != null && sceneInfo.isContainNotChildrenView()) {
                            List<String> notChildrenViewIdList = sceneInfo.getNotChildrenViewIdList();
                            if (notChildrenViewIdList != null) {
                                for (int i2 = 0; i2 < notChildrenViewIdList.size(); i2++) {
                                    VuiSceneManager.instance().sendSceneData(3, true, removeTask3.sceneId + "," + notChildrenViewIdList.get(i2));
                                }
                            }
                            sceneInfo.setContainNotChildrenView(false);
                            sceneInfo.setNotChildrenViewIdList(null);
                            sceneInfo.setNotChildrenViewList(null);
                        }
                    } else {
                        LogUtils.logDebug(removeTask3.TAG, "RemoveTask: view list" + removeTask3.sceneId);
                        ArrayList arrayList = new ArrayList();
                        long currentTimeMillis = System.currentTimeMillis();
                        List<String> sceneIdList = VuiSceneManager.instance().getSceneIdList(removeTask3.sceneId);
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();
                        if (sceneInfo != null && sceneInfo.isContainNotChildrenView()) {
                            List<String> notChildrenViewIdList2 = sceneInfo.getNotChildrenViewIdList();
                            List<SoftReference<View>> notChildrenViewList = sceneInfo.getNotChildrenViewList();
                            int i3 = 0;
                            while (i3 < removeTask3.viewWrapper.getViewList().size()) {
                                SoftReference<View> softReference = removeTask3.viewWrapper.getViewList().get(i3);
                                if (notChildrenViewList.contains(softReference) && softReference != null && (softReference.get() instanceof IVuiElement)) {
                                    List<SoftReference<View>> list3 = notChildrenViewList;
                                    i = i3;
                                    List<String> list4 = notChildrenViewIdList2;
                                    String str3 = str2;
                                    try {
                                        VuiElement buildView = buildView(softReference, arrayList3, null, null, arrayList, currentTimeMillis, sceneIdList, arrayList2, null, 0, (softReference == null || !(softReference.get() instanceof IVuiElement)) ? z4 : ((IVuiElement) softReference.get()).isVuiLayoutLoadable(), (softReference == null || !(softReference.get() instanceof RecyclerView)) ? z4 : z3, (softReference == null || !(softReference.get() instanceof RecyclerView)) ? sceneInfo.getElementChangedListener() : iVuiElementChangedListener2);
                                        if (buildView == null || buildView.getId() == null) {
                                            removeTask2 = this;
                                            list = list3;
                                            list2 = list4;
                                            str = str3;
                                            c = 3;
                                            z = true;
                                        } else {
                                            list2 = list4;
                                            if (list2.contains(buildView.getId())) {
                                                removeTask2 = this;
                                                str = str3;
                                                c = 3;
                                                z = true;
                                                VuiSceneManager.instance().sendSceneData(3, true, removeTask2.sceneId + str + buildView.getId());
                                                list2.remove(buildView.getId());
                                                list = list3;
                                                list.remove(softReference);
                                            } else {
                                                c = 3;
                                                z = true;
                                                removeTask2 = this;
                                                list = list3;
                                                str = str3;
                                            }
                                        }
                                        if (list2.isEmpty() && list.isEmpty()) {
                                            z2 = false;
                                            sceneInfo.setContainNotChildrenView(false);
                                            iVuiElementChangedListener = null;
                                            sceneInfo.setNotChildrenViewIdList(null);
                                            sceneInfo.setNotChildrenViewList(null);
                                        } else {
                                            z2 = false;
                                            iVuiElementChangedListener = null;
                                            sceneInfo.setNotChildrenViewIdList(list2);
                                            sceneInfo.setNotChildrenViewList(list);
                                        }
                                        z4 = z2;
                                        iVuiElementChangedListener2 = iVuiElementChangedListener;
                                        removeTask3 = removeTask2;
                                        str2 = str;
                                        c2 = c;
                                        z3 = z;
                                        i3 = i + 1;
                                        notChildrenViewList = list;
                                        notChildrenViewIdList2 = list2;
                                    } catch (Exception e) {
                                        e = e;
                                        removeTask = this;
                                        LogUtils.e(removeTask.TAG, "e:" + e.getMessage());
                                        return;
                                    }
                                }
                                list = notChildrenViewList;
                                i = i3;
                                list2 = notChildrenViewIdList2;
                                z2 = z4;
                                iVuiElementChangedListener = iVuiElementChangedListener2;
                                z = z3;
                                c = c2;
                                str = str2;
                                removeTask2 = removeTask3;
                                z4 = z2;
                                iVuiElementChangedListener2 = iVuiElementChangedListener;
                                removeTask3 = removeTask2;
                                str2 = str;
                                c2 = c;
                                z3 = z;
                                i3 = i + 1;
                                notChildrenViewList = list;
                                notChildrenViewIdList2 = list2;
                            }
                        }
                    }
                    return;
                }
                String str4 = removeTask3.sceneId + "_" + removeTask3.viewWrapper.getElementGroupId();
                LogUtils.logInfo(removeTask3.TAG, "RemoveTask: subSceneid" + str4);
                List<String> sceneIdList2 = VuiSceneManager.instance().getSceneIdList(removeTask3.sceneId);
                List<String> sceneIdList3 = VuiSceneManager.instance().getSceneIdList(str4);
                if (sceneIdList3 != null) {
                    sceneIdList2.removeAll(sceneIdList3);
                    VuiSceneManager.instance().setSceneIdList(removeTask3.sceneId, sceneIdList2);
                    VuiSceneManager.instance().removeSubSceneIds(removeTask3.sceneId, str4);
                    VuiSceneManager.instance().removeVuiSceneListener(str4, false, false, null);
                }
                VuiSceneManager.instance().sendSceneData(3, true, removeTask3.sceneId + "," + removeTask3.viewWrapper.getElementGroupId());
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            removeTask = removeTask3;
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementBuilder
    public List<VuiElement> build(int i, View view) {
        return build(i, view);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementBuilder
    public List<VuiElement> build(int i, List<View> list) {
        return build(i, list);
    }
}
