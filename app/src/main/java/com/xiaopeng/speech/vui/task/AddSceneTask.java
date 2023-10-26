package com.xiaopeng.speech.vui.task;

import android.os.Build;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.speech.vui.VuiSceneManager;
import com.xiaopeng.speech.vui.cache.VuiSceneCache;
import com.xiaopeng.speech.vui.cache.VuiSceneCacheFactory;
import com.xiaopeng.speech.vui.model.VuiScene;
import com.xiaopeng.speech.vui.model.VuiSceneInfo;
import com.xiaopeng.speech.vui.task.base.BaseTask;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.VuiPriority;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class AddSceneTask extends BaseTask {
    private String TAG;
    private TaskWrapper viewWrapper;

    public AddSceneTask(TaskWrapper taskWrapper) {
        super(taskWrapper);
        this.TAG = "VuiEngine_AddSceneTask";
        this.viewWrapper = taskWrapper;
    }

    @Override // com.xiaopeng.speech.vui.task.base.Task
    public void execute() {
        if (this.viewWrapper.isContainNotChildrenView()) {
            addNotChildViewToScene(this.viewWrapper.getViewList(), this.viewWrapper.getSceneId(), this.viewWrapper.getPriority());
        } else if (this.viewWrapper.getPriority() != null) {
            addSceneElementGroup(this.viewWrapper.getView(), this.viewWrapper.getSceneId(), this.viewWrapper.getPriority(), this.viewWrapper.getListener());
        } else {
            addSceneElement(this.viewWrapper.getView(), this.viewWrapper.getParentElementId(), this.viewWrapper.getSceneId());
        }
    }

    private void addNotChildViewToScene(List<SoftReference<View>> list, String str, VuiPriority vuiPriority) {
        AddSceneTask addSceneTask;
        int i;
        VuiSceneCache vuiSceneCache;
        ArrayList arrayList;
        VuiSceneInfo vuiSceneInfo;
        ArrayList arrayList2;
        VuiScene vuiScene;
        long j;
        String str2;
        String str3;
        ArrayList arrayList3;
        AddSceneTask addSceneTask2 = this;
        List<SoftReference<View>> list2 = list;
        String str4 = str;
        String str5 = "user";
        if (list2 == null || str4 == null) {
            return;
        }
        try {
            if (VuiSceneManager.instance().canUpdateScene(str4)) {
                ArrayList arrayList4 = new ArrayList();
                long currentTimeMillis = System.currentTimeMillis();
                VuiScene newVuiScene = addSceneTask2.getNewVuiScene(str4, currentTimeMillis);
                ArrayList arrayList5 = new ArrayList();
                List<String> sceneIdList = VuiSceneManager.instance().getSceneIdList(str4);
                ArrayList arrayList6 = new ArrayList();
                ArrayList arrayList7 = new ArrayList();
                VuiSceneInfo sceneInfo = VuiSceneManager.instance().getSceneInfo(str4);
                if (sceneInfo == null) {
                    return;
                }
                IVuiElementChangedListener elementChangedListener = sceneInfo.getElementChangedListener();
                sceneInfo.setContainNotChildrenView(true);
                ArrayList arrayList8 = new ArrayList();
                VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                boolean z = false;
                int i2 = 0;
                while (i2 < list.size()) {
                    SoftReference<View> softReference = list2.get(i2);
                    if (softReference != null && (softReference.get() instanceof IVuiElement)) {
                        boolean z2 = (softReference == null || !(softReference.get() instanceof RecyclerView)) ? z : true;
                        i = i2;
                        vuiSceneCache = sceneCache;
                        ArrayList arrayList9 = arrayList8;
                        ArrayList arrayList10 = arrayList7;
                        vuiSceneInfo = sceneInfo;
                        ArrayList arrayList11 = arrayList5;
                        vuiScene = newVuiScene;
                        long j2 = currentTimeMillis;
                        str2 = str5;
                        try {
                            VuiElement buildView = buildView(softReference, arrayList5, null, null, arrayList4, currentTimeMillis, sceneIdList, arrayList6, null, 0, (softReference == null || !(softReference.get() instanceof IVuiElement)) ? false : ((IVuiElement) softReference.get()).isVuiLayoutLoadable(), z2, z2 ? null : elementChangedListener);
                            if (buildView == null || buildView.getId() == null) {
                                addSceneTask = this;
                                str3 = str;
                                arrayList3 = arrayList9;
                                arrayList = arrayList10;
                                arrayList2 = arrayList11;
                                j = j2;
                            } else {
                                j = j2;
                                buildView.setTimestamp(j);
                                addSceneTask = this;
                                try {
                                    addSceneTask.setVuiTag(softReference, buildView.getId());
                                    str3 = str;
                                    VuiElement vuiElementById = vuiSceneCache.getVuiElementById(str3, buildView.getId());
                                    if (vuiElementById == null || !buildView.equals(vuiElementById)) {
                                        arrayList2 = arrayList11;
                                        arrayList2.add(buildView);
                                        arrayList3 = arrayList9;
                                        arrayList3.add(softReference);
                                        arrayList = arrayList10;
                                        arrayList.add(buildView.getId());
                                    } else {
                                        arrayList3 = arrayList9;
                                        arrayList = arrayList10;
                                        arrayList2 = arrayList11;
                                    }
                                } catch (Exception e) {
                                    e = e;
                                    LogUtils.e(addSceneTask.TAG, "addNotChildViewToScene e:" + e.getMessage());
                                    return;
                                }
                            }
                            z = z2;
                            currentTimeMillis = j;
                            addSceneTask2 = addSceneTask;
                            str4 = str3;
                            arrayList8 = arrayList3;
                            i2 = i + 1;
                            newVuiScene = vuiScene;
                            str5 = str2;
                            sceneInfo = vuiSceneInfo;
                            arrayList5 = arrayList2;
                            arrayList7 = arrayList;
                            sceneCache = vuiSceneCache;
                            list2 = list;
                        } catch (Exception e2) {
                            e = e2;
                            addSceneTask = this;
                        }
                    }
                    i = i2;
                    vuiSceneCache = sceneCache;
                    arrayList = arrayList7;
                    vuiSceneInfo = sceneInfo;
                    arrayList2 = arrayList5;
                    vuiScene = newVuiScene;
                    j = currentTimeMillis;
                    str2 = str5;
                    str3 = str4;
                    arrayList3 = arrayList8;
                    addSceneTask = addSceneTask2;
                    currentTimeMillis = j;
                    addSceneTask2 = addSceneTask;
                    str4 = str3;
                    arrayList8 = arrayList3;
                    i2 = i + 1;
                    newVuiScene = vuiScene;
                    str5 = str2;
                    sceneInfo = vuiSceneInfo;
                    arrayList5 = arrayList2;
                    arrayList7 = arrayList;
                    sceneCache = vuiSceneCache;
                    list2 = list;
                }
                VuiSceneCache vuiSceneCache2 = sceneCache;
                ArrayList arrayList12 = arrayList7;
                VuiSceneInfo vuiSceneInfo2 = sceneInfo;
                ArrayList arrayList13 = arrayList5;
                VuiScene vuiScene2 = newVuiScene;
                long j3 = currentTimeMillis;
                String str6 = str5;
                String str7 = str4;
                ArrayList arrayList14 = arrayList8;
                AddSceneTask addSceneTask3 = addSceneTask2;
                int i3 = 0;
                while (i3 < arrayList13.size()) {
                    VuiElement vuiElement = arrayList13.get(i3);
                    VuiElement vuiElementById2 = vuiSceneCache2.getVuiElementById(str7, vuiElement.getId());
                    if (vuiElementById2 == null || !vuiElement.equals(vuiElementById2)) {
                        i3++;
                    } else {
                        arrayList13.remove(vuiElement);
                    }
                }
                if (arrayList13.size() > 0) {
                    vuiScene2.setElements(arrayList13);
                    if (!str6.equals(Build.TYPE) && LogUtils.getLogLevel() <= LogUtils.LOG_DEBUG_LEVEL) {
                        LogUtils.logDebug(addSceneTask3.TAG, "addNotChildViewToScene completed time:" + (System.currentTimeMillis() - j3) + "," + VuiUtils.vuiUpdateSceneConvertToString(vuiScene2));
                    }
                    if (vuiSceneInfo2.getNotChildrenViewList() != null && vuiSceneInfo2.getNotChildrenViewIdList() != null) {
                        ArrayList arrayList15 = new ArrayList();
                        if (arrayList14.size() < vuiSceneInfo2.getNotChildrenViewList().size()) {
                            for (int i4 = 0; i4 < vuiSceneInfo2.getNotChildrenViewIdList().size(); i4++) {
                                String str8 = vuiSceneInfo2.getNotChildrenViewIdList().get(i4);
                                if (!arrayList12.contains(str8)) {
                                    arrayList15.add(str8);
                                    VuiSceneManager.instance().sendSceneData(3, true, str7 + "," + str8);
                                }
                            }
                        }
                        if (arrayList15.size() == vuiSceneInfo2.getNotChildrenViewIdList().size()) {
                            VuiSceneManager.instance().sendSceneData(2, true, vuiScene2);
                        } else {
                            VuiSceneCache sceneCache2 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                            if (sceneCache2 != null) {
                                List<VuiElement> fusionCache = sceneCache2.getFusionCache(str7, arrayList13, false);
                                sceneCache2.setCache(str7, fusionCache);
                                if (!str6.equals(Build.TYPE) && LogUtils.getLogLevel() <= LogUtils.LOG_DEBUG_LEVEL) {
                                    VuiScene newVuiScene2 = addSceneTask3.getNewVuiScene(str7, System.currentTimeMillis());
                                    newVuiScene2.setElements(fusionCache);
                                    LogUtils.logDebug(addSceneTask3.TAG, "addNotChildViewToScene full_scene_info" + VuiUtils.vuiSceneConvertToString(newVuiScene2));
                                }
                            }
                            VuiSceneManager.instance().sendSceneData(1, true, vuiScene2);
                        }
                        vuiSceneInfo2.setNotChildrenViewList(arrayList14);
                        vuiSceneInfo2.setNotChildrenViewIdList(arrayList12);
                        return;
                    }
                    vuiSceneInfo2.setNotChildrenViewList(arrayList14);
                    vuiSceneInfo2.setNotChildrenViewIdList(arrayList12);
                    VuiSceneManager.instance().sendSceneData(2, true, vuiScene2);
                }
            }
        } catch (Exception e3) {
            e = e3;
            addSceneTask = addSceneTask2;
        }
    }

    public void addSceneElementGroup(SoftReference<View> softReference, String str, VuiPriority vuiPriority, IVuiSceneListener iVuiSceneListener) {
        AddSceneTask addSceneTask;
        VuiSceneInfo sceneInfo;
        String str2;
        ArrayList arrayList;
        long j;
        try {
            ArrayList arrayList2 = new ArrayList();
            if (!(softReference instanceof IVuiElement) || !VuiSceneManager.instance().canUpdateScene(str) || (sceneInfo = VuiSceneManager.instance().getSceneInfo(str)) == null) {
                return;
            }
            ((IVuiElement) softReference).setVuiPriority(vuiPriority);
            long currentTimeMillis = System.currentTimeMillis();
            VuiScene newVuiScene = getNewVuiScene(str, currentTimeMillis);
            ArrayList arrayList3 = new ArrayList();
            List<String> sceneIdList = VuiSceneManager.instance().getSceneIdList(str);
            ArrayList arrayList4 = new ArrayList();
            VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
            try {
                VuiElement buildView = buildView(softReference, arrayList3, null, null, arrayList2, currentTimeMillis, sceneIdList, arrayList4, null, 0, (softReference == null || !(softReference.get() instanceof IVuiElement)) ? false : ((IVuiElement) softReference.get()).isVuiLayoutLoadable(), softReference != null && (softReference.get() instanceof RecyclerView), (softReference == null || !(softReference.get() instanceof RecyclerView)) ? sceneInfo.getElementChangedListener() : null);
                if (buildView == null || buildView.getId() == null) {
                    addSceneTask = this;
                    str2 = str;
                    arrayList = arrayList3;
                    j = currentTimeMillis;
                } else {
                    j = currentTimeMillis;
                    buildView.setTimestamp(j);
                    addSceneTask = this;
                    try {
                        addSceneTask.setVuiTag(softReference, buildView.getId());
                        str2 = str;
                        VuiElement vuiElementById = sceneCache.getVuiElementById(str2, buildView.getId());
                        if (vuiElementById != null) {
                            if (buildView.equals(vuiElementById)) {
                                LogUtils.logDebug(addSceneTask.TAG, "addNotChildViewToScene element same");
                                arrayList = arrayList3;
                            } else {
                                arrayList = arrayList3;
                                arrayList.add(buildView);
                            }
                        } else {
                            arrayList = arrayList3;
                            arrayList.add(buildView);
                        }
                    } catch (Exception e) {
                        e = e;
                        LogUtils.e(addSceneTask.TAG, "addSceneElementGroup e:" + e.getMessage());
                        return;
                    }
                }
                if (arrayList.size() > 0) {
                    newVuiScene.setElements(arrayList);
                    LogUtils.logInfo(addSceneTask.TAG, "addSceneElementGroup completed time:" + (System.currentTimeMillis() - j));
                    if (!"user".equals(Build.TYPE) && LogUtils.getLogLevel() <= LogUtils.LOG_DEBUG_LEVEL) {
                        LogUtils.logDebug(addSceneTask.TAG, "addSceneElementGroup:" + VuiUtils.vuiUpdateSceneConvertToString(newVuiScene));
                    }
                    String str3 = str2 + "_" + ((softReference == null || softReference.get() == null) ? -1 : softReference.get().getId());
                    VuiSceneManager.instance().setSceneIdList(str3, arrayList2);
                    VuiSceneManager.instance().addSubSceneIds(str2, Arrays.asList(str3));
                    if (softReference != null && softReference.get() != null && iVuiSceneListener != null) {
                        VuiSceneManager.instance().setSceneIdList(str2, sceneIdList);
                        VuiSceneManager.instance().addVuiSceneListener(str3, softReference.get(), iVuiSceneListener, null, true);
                    }
                    VuiSceneManager.instance().sendSceneData(2, true, newVuiScene);
                }
            } catch (Exception e2) {
                e = e2;
                addSceneTask = this;
            }
        } catch (Exception e3) {
            e = e3;
            addSceneTask = this;
        }
    }

    public void addSceneElement(SoftReference<View> softReference, String str, String str2) {
        VuiSceneInfo sceneInfo;
        ArrayList arrayList;
        long j;
        ArrayList arrayList2 = new ArrayList();
        if ((softReference instanceof IVuiElement) && VuiSceneManager.instance().canUpdateScene(str2) && (sceneInfo = VuiSceneManager.instance().getSceneInfo(str2)) != null) {
            long currentTimeMillis = System.currentTimeMillis();
            VuiScene newVuiScene = getNewVuiScene(str2, currentTimeMillis);
            ArrayList arrayList3 = new ArrayList();
            List<String> sceneIdList = VuiSceneManager.instance().getSceneIdList(str2);
            ArrayList arrayList4 = new ArrayList();
            boolean z = false;
            boolean isVuiLayoutLoadable = (softReference == null || !(softReference.get() instanceof IVuiElement)) ? false : ((IVuiElement) softReference.get()).isVuiLayoutLoadable();
            if (softReference != null && (softReference.get() instanceof RecyclerView)) {
                z = true;
            }
            VuiElement buildView = buildView(softReference, arrayList3, null, null, arrayList2, currentTimeMillis, sceneIdList, arrayList4, null, 0, isVuiLayoutLoadable, z, (softReference == null || !(softReference.get() instanceof RecyclerView)) ? sceneInfo.getElementChangedListener() : null);
            if (buildView == null || buildView.getId() == null) {
                arrayList = arrayList3;
                j = currentTimeMillis;
            } else {
                j = currentTimeMillis;
                buildView.setTimestamp(j);
                arrayList = arrayList3;
                arrayList.add(buildView);
            }
            newVuiScene.setElements(arrayList);
            VuiSceneManager.instance().setSceneIdList(str2, sceneIdList);
            if (!"user".equals(Build.TYPE) && LogUtils.getLogLevel() <= LogUtils.LOG_DEBUG_LEVEL) {
                LogUtils.logDebug(this.TAG, "addSceneElement:" + VuiUtils.vuiSceneConvertToString(newVuiScene) + "time:" + (System.currentTimeMillis() - j));
            }
            VuiSceneManager.instance().addSceneElement(newVuiScene, str);
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
