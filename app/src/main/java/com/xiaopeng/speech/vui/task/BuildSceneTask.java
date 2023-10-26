package com.xiaopeng.speech.vui.task;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.speech.vui.VuiSceneManager;
import com.xiaopeng.speech.vui.cache.VuiSceneBuildCache;
import com.xiaopeng.speech.vui.cache.VuiSceneCache;
import com.xiaopeng.speech.vui.cache.VuiSceneCacheFactory;
import com.xiaopeng.speech.vui.model.VuiScene;
import com.xiaopeng.speech.vui.model.VuiSceneInfo;
import com.xiaopeng.speech.vui.task.base.BaseTask;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.SceneMergeUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.speech.vui.vuiengine.R;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public class BuildSceneTask extends BaseTask {
    private String TAG;
    private List<String> bizIds;
    private IVuiElementChangedListener elementChangedListener;
    private List<VuiElement> elements;
    private VuiSceneInfo info;
    private boolean isWholeScene;
    private List<String> mIdList;
    private List<String> mainThreadSceneList;
    private SoftReference<View> rootView;
    private String sceneId;
    private List<SoftReference<View>> viewList;
    private TaskWrapper viewWrapper;

    @Override // com.xiaopeng.vui.commons.IVuiElementBuilder
    public List<VuiElement> build(int i, View view) {
        return build(i, view);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementBuilder
    public List<VuiElement> build(int i, List<View> list) {
        return build(i, list);
    }

    public BuildSceneTask(TaskWrapper taskWrapper) {
        super(taskWrapper);
        this.isWholeScene = true;
        this.TAG = "VuiEngine_BuildSceneTask";
        this.mIdList = new ArrayList();
        this.elements = new ArrayList();
        this.mainThreadSceneList = Arrays.asList("MainNetRadioConcentration", "MainProgramEditorChoice", "MainMusicConcentration", "MainMineCollect", "MainMineHistory", "MainMineVip", "MainMinePlaylist");
        this.info = null;
        this.bizIds = new ArrayList();
        this.elementChangedListener = null;
        this.viewWrapper = taskWrapper;
        this.rootView = taskWrapper.getView();
        this.viewList = taskWrapper.getViewList();
        this.sceneId = taskWrapper.getSceneId();
        this.isWholeScene = taskWrapper.isWholeScene();
    }

    @Override // com.xiaopeng.speech.vui.task.base.Task
    public void execute() {
        BuildSceneTask buildSceneTask;
        long j;
        VuiSceneInfo vuiSceneInfo;
        BuildSceneTask buildSceneTask2 = this;
        try {
            final long currentTimeMillis = System.currentTimeMillis();
            if (!buildSceneTask2.isWholeScene || VuiUtils.isActiveSceneId(buildSceneTask2.sceneId)) {
                String str = buildSceneTask2.TAG;
                StringBuilder append = new StringBuilder().append("buildScene-------------- sceneId:").append(buildSceneTask2.sceneId).append(",view:");
                SoftReference<View> softReference = buildSceneTask2.rootView;
                LogUtils.logDebug(str, append.append(softReference != null ? softReference.get() : null).append(",viewList:").append(buildSceneTask2.viewList).toString());
                buildSceneTask2.info = VuiSceneManager.instance().getSceneInfo(buildSceneTask2.sceneId);
                String str2 = buildSceneTask2.sceneId;
                boolean z = true;
                String substring = str2.substring(str2.lastIndexOf("-") + 1);
                if (buildSceneTask2.wrapper.getListener() != null && (vuiSceneInfo = buildSceneTask2.info) != null && vuiSceneInfo.getListener() != null && !buildSceneTask2.wrapper.getListener().equals(buildSceneTask2.info.getListener())) {
                    LogUtils.w(buildSceneTask2.TAG, "要build的场景和目前持有的场景数据不一致");
                    return;
                }
                VuiSceneInfo vuiSceneInfo2 = buildSceneTask2.info;
                if (vuiSceneInfo2 != null) {
                    vuiSceneInfo2.setBuild(true);
                    buildSceneTask2.elementChangedListener = buildSceneTask2.info.getElementChangedListener();
                }
                VuiSceneBuildCache vuiSceneBuildCache = (VuiSceneBuildCache) VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                if (vuiSceneBuildCache != null) {
                    vuiSceneBuildCache.setDisplayLocation(buildSceneTask2.sceneId, buildSceneTask2.wrapper.getDisplayLocation());
                }
                SoftReference<View> softReference2 = buildSceneTask2.rootView;
                if (softReference2 != null && softReference2.get() != null) {
                    if (buildSceneTask2.mainThreadSceneList.contains(substring)) {
                        if (VuiUtils.findRecyclerView(buildSceneTask2.rootView) != null && buildSceneTask2.isWholeScene) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.xiaopeng.speech.vui.task.BuildSceneTask.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    BuildSceneTask.this.buildView(currentTimeMillis);
                                    BuildSceneTask.this.handleBuildElement(currentTimeMillis);
                                }
                            });
                        } else {
                            buildSceneTask2.buildView(currentTimeMillis);
                            buildSceneTask2.handleBuildElement(currentTimeMillis);
                        }
                    } else {
                        buildSceneTask2.buildView(currentTimeMillis);
                        buildSceneTask2.handleBuildElement(currentTimeMillis);
                    }
                } else if (buildSceneTask2.viewList != null) {
                    int i = 0;
                    while (i < buildSceneTask2.viewList.size()) {
                        SoftReference<View> softReference3 = buildSceneTask2.viewList.get(i);
                        LogUtils.logDebug(buildSceneTask2.TAG, "buildScene-------------- sceneId:" + buildSceneTask2.sceneId + ",第" + i + "个View" + softReference3);
                        int i2 = i;
                        boolean z2 = z;
                        long j2 = currentTimeMillis;
                        try {
                            VuiElement buildView = buildView(softReference3, buildSceneTask2.elements, buildSceneTask2.viewWrapper.getCustomizeIds(), buildSceneTask2.viewWrapper.getElementListener(), buildSceneTask2.mIdList, currentTimeMillis, null, buildSceneTask2.bizIds, null, 0, (softReference3 == null || !(softReference3.get() instanceof IVuiElement)) ? false : ((IVuiElement) softReference3.get()).isVuiLayoutLoadable(), (softReference3 == null || !(softReference3.get() instanceof RecyclerView)) ? false : z, (softReference3 == null || !(softReference3.get() instanceof RecyclerView)) ? buildSceneTask2.elementChangedListener : null);
                            if (buildView == null || buildView.getId() == null) {
                                buildSceneTask = this;
                                j = j2;
                            } else {
                                j = j2;
                                buildView.setTimestamp(j);
                                buildSceneTask = this;
                                try {
                                    buildSceneTask.elements.add(buildView);
                                    buildSceneTask.setVuiTag(softReference3, buildView.getId());
                                } catch (Exception e) {
                                    e = e;
                                    LogUtils.e(buildSceneTask.TAG, "e:" + e.getMessage());
                                    return;
                                }
                            }
                            i = i2 + 1;
                            currentTimeMillis = j;
                            buildSceneTask2 = buildSceneTask;
                            z = z2;
                        } catch (Exception e2) {
                            e = e2;
                            buildSceneTask = this;
                            LogUtils.e(buildSceneTask.TAG, "e:" + e.getMessage());
                            return;
                        }
                    }
                    buildSceneTask = buildSceneTask2;
                    buildSceneTask.handleBuildElement(currentTimeMillis);
                }
            }
        } catch (Exception e3) {
            e = e3;
            buildSceneTask = buildSceneTask2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void buildView(long j) {
        VuiSceneInfo vuiSceneInfo;
        if (!this.isWholeScene && (vuiSceneInfo = this.info) != null && vuiSceneInfo.isBuildComplete()) {
            VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
            if (sceneCache != null) {
                this.elements = sceneCache.getCache(this.sceneId);
                return;
            }
            return;
        }
        SoftReference<View> findRecyclerView = VuiUtils.findRecyclerView(this.rootView);
        SoftReference<View> softReference = this.rootView;
        List<VuiElement> list = this.elements;
        List<Integer> customizeIds = this.viewWrapper.getCustomizeIds();
        SoftReference<IVuiElementListener> elementListener = this.viewWrapper.getElementListener();
        List<String> list2 = this.mIdList;
        List<String> list3 = this.bizIds;
        SoftReference<View> softReference2 = this.rootView;
        boolean isVuiLayoutLoadable = (softReference2 == null || !(softReference2.get() instanceof IVuiElement)) ? false : ((IVuiElement) this.rootView.get()).isVuiLayoutLoadable();
        SoftReference<View> softReference3 = this.rootView;
        VuiElement buildView = buildView(softReference, list, customizeIds, elementListener, list2, j, null, list3, null, 0, isVuiLayoutLoadable, softReference3 != null && (softReference3.get() instanceof RecyclerView), findRecyclerView == null ? this.elementChangedListener : null);
        if (this.elements.size() == 0 && buildView != null && buildView.getId() != null) {
            buildView.setTimestamp(j);
            buildView.setVisible(null);
            this.elements.add(buildView);
            setVuiTag(this.rootView, buildView.getId());
        }
        if (findRecyclerView == null || findRecyclerView.equals(this.rootView)) {
            return;
        }
        addVuiElementChangedListener();
    }

    private void addVuiElementChangedListener() {
        if ("com.android.systemui".equals(VuiSceneManager.instance().getmPackageName()) || this.elementChangedListener == null) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        linkedList.offer(this.rootView);
        while (!linkedList.isEmpty()) {
            SoftReference softReference = (SoftReference) linkedList.poll();
            String str = (softReference == null || softReference.get() == null) ? null : (String) ((View) softReference.get()).getTag(R.id.vuiElementId);
            if (str != null && this.mIdList.contains(str) && (softReference.get() instanceof IVuiElement)) {
                IVuiElement iVuiElement = (IVuiElement) softReference.get();
                if (iVuiElement.getVuiElementId() == null) {
                    iVuiElement.setVuiElementId(str);
                }
                iVuiElement.setVuiElementChangedListener(this.elementChangedListener);
            } else if (softReference != null && softReference.get() != null && (softReference instanceof IVuiElement)) {
                String str2 = (String) ((View) softReference.get()).getTag();
                IVuiElement iVuiElement2 = (IVuiElement) softReference.get();
                if (str2 != null && str2.startsWith("4657")) {
                    iVuiElement2.setVuiElementChangedListener(this.elementChangedListener);
                }
            }
            if (softReference != null && !(softReference.get() instanceof RecyclerView) && (softReference.get() instanceof ViewGroup)) {
                SoftReference softReference2 = new SoftReference((ViewGroup) softReference.get());
                for (int i = 0; softReference2.get() != null && i < ((ViewGroup) softReference2.get()).getChildCount(); i++) {
                    linkedList.offer(new SoftReference(((ViewGroup) softReference2.get()).getChildAt(i)));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBuildElement(long j) {
        VuiSceneInfo vuiSceneInfo;
        VuiSceneInfo vuiSceneInfo2;
        VuiSceneManager.instance().setSceneIdList(this.sceneId, this.mIdList);
        List<String> subSceneIds = this.viewWrapper.getSubSceneIds();
        ArrayList arrayList = new ArrayList();
        if (subSceneIds != null) {
            int size = subSceneIds.size();
            for (int i = 0; i < size; i++) {
                String str = subSceneIds.get(i);
                if (VuiSceneManager.instance().getVuiSceneListener(str) == null) {
                    VuiSceneInfo vuiSceneInfo3 = this.info;
                    if (vuiSceneInfo3 != null) {
                        vuiSceneInfo3.updateAddSubSceneNum();
                    }
                    VuiSceneManager.instance().initSubSceneInfo(str, this.sceneId);
                } else {
                    VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                    if (sceneCache != null) {
                        List<VuiElement> cache = sceneCache.getCache(str);
                        if (cache != null) {
                            this.elements.addAll(i, cache);
                            VuiSceneInfo vuiSceneInfo4 = this.info;
                            if (vuiSceneInfo4 != null) {
                                vuiSceneInfo4.updateAddSubSceneNum();
                            }
                            VuiSceneManager.instance().setWholeSceneId(str, this.sceneId);
                        } else {
                            arrayList.add(str);
                        }
                    } else {
                        arrayList.add(str);
                    }
                }
            }
            VuiSceneManager.instance().addSubSceneIds(this.sceneId, subSceneIds);
        }
        VuiSceneManager.instance().setIsWholeScene(this.sceneId, this.isWholeScene);
        if (this.elements.size() > 0) {
            VuiScene newVuiScene = getNewVuiScene(this.sceneId, j);
            newVuiScene.setElements(this.elements);
            VuiSceneCache sceneCache2 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
            LogUtils.logInfo(this.TAG, "buildScene completed:" + this.sceneId + ",time:" + (System.currentTimeMillis() - j) + (this.isWholeScene ? "full scene build completed" : ""));
            if (this.isWholeScene && (vuiSceneInfo2 = this.info) != null && vuiSceneInfo2.isFull()) {
                setWholeSceneCache(this.sceneId, sceneCache2, this.elements);
                VuiSceneInfo vuiSceneInfo5 = this.info;
                if (vuiSceneInfo5 != null) {
                    vuiSceneInfo5.setBuildComplete(true);
                }
                VuiSceneManager.instance().sendSceneData(0, false, newVuiScene);
            } else {
                if (!this.isWholeScene && (vuiSceneInfo = this.info) != null && !vuiSceneInfo.isBuildComplete()) {
                    if (sceneCache2 != null) {
                        sceneCache2.setCache(this.sceneId, this.elements);
                    }
                    this.info.setBuildComplete(true);
                } else if (this.isWholeScene) {
                    setWholeSceneCache(this.sceneId, sceneCache2, this.elements);
                }
                VuiSceneInfo vuiSceneInfo6 = this.info;
                List<String> wholeSceneId = vuiSceneInfo6 != null ? vuiSceneInfo6.getWholeSceneId() : null;
                if (wholeSceneId != null) {
                    int size2 = wholeSceneId.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        String str2 = wholeSceneId.get(i2);
                        VuiSceneCache sceneCache3 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                        VuiSceneInfo sceneInfo = VuiSceneManager.instance().getSceneInfo(str2);
                        if (sceneInfo != null && sceneInfo.isFull()) {
                            newVuiScene = getNewVuiScene(str2, j);
                            newVuiScene.setElements(this.elements);
                            setSubSceneElementToCache(str2, sceneCache3, this.elements);
                            LogUtils.logInfo(this.TAG, "main scene update");
                            VuiSceneManager.instance().sendSceneData(1, true, newVuiScene);
                        } else if (sceneInfo != null && !sceneInfo.isFull()) {
                            sceneInfo.updateAddSubSceneNum();
                            List<VuiElement> subSceneElementToCache = setSubSceneElementToCache(str2, sceneCache3, this.elements);
                            if (sceneInfo.isFull()) {
                                sceneInfo.setBuildComplete(true);
                                LogUtils.logInfo(this.TAG, str2 + " full scene build completed ");
                                if (VuiUtils.isActiveSceneId(str2)) {
                                    newVuiScene = getNewVuiScene(str2, j);
                                    newVuiScene.setElements(subSceneElementToCache);
                                    VuiSceneManager.instance().sendSceneData(0, false, newVuiScene);
                                }
                            }
                        }
                    }
                }
                buildSubScenes(arrayList);
            }
            if (this.viewWrapper.getSceneCallbackHandler() != null) {
                this.viewWrapper.getSceneCallbackHandler().onBuildFinished(newVuiScene);
            }
        } else if (this.isWholeScene && arrayList.size() > 0) {
            buildSubScenes(arrayList);
        } else {
            LogUtils.e(this.TAG, "请确认此场景是否包含支持VUI操作的控件");
        }
    }

    private void setWholeSceneCache(String str, VuiSceneCache vuiSceneCache, List<VuiElement> list) {
        if (vuiSceneCache != null) {
            vuiSceneCache.setCache(str, vuiSceneCache.getUpdateFusionCache(str, list, false));
        }
    }

    private List<VuiElement> setSubSceneElementToCache(String str, VuiSceneCache vuiSceneCache, List<VuiElement> list) {
        List<VuiElement> cache = vuiSceneCache.getCache(str);
        if (cache == null) {
            cache = new ArrayList<>();
        }
        if (cache.contains(list.get(0))) {
            return cache;
        }
        List<VuiElement> merge = SceneMergeUtils.merge(cache, list, false);
        vuiSceneCache.setCache(str, merge);
        if (!"user".equals(Build.TYPE) && LogUtils.getLogLevel() <= LogUtils.LOG_DEBUG_LEVEL && !VuiUtils.isActiveSceneId(str)) {
            VuiScene newVuiScene = getNewVuiScene(str, System.currentTimeMillis());
            newVuiScene.setElements(merge);
            LogUtils.logDebug(this.TAG, "buildScene full_scene_info:" + VuiUtils.vuiSceneConvertToString(newVuiScene));
        }
        return merge;
    }

    private void buildSubScenes(List<String> list) {
        int size = list.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                String str = list.get(i);
                IVuiSceneListener vuiSceneListener = VuiSceneManager.instance().getVuiSceneListener(str);
                if (vuiSceneListener != null) {
                    vuiSceneListener.onBuildScene();
                }
                VuiSceneManager.instance().setWholeSceneId(str, this.sceneId);
            }
        }
    }
}
