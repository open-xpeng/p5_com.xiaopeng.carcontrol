package com.xiaopeng.speech.vui.task;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import com.google.gson.JsonObject;
import com.xiaopeng.speech.vui.VuiSceneManager;
import com.xiaopeng.speech.vui.cache.VuiSceneCache;
import com.xiaopeng.speech.vui.cache.VuiSceneCacheFactory;
import com.xiaopeng.speech.vui.model.VuiScene;
import com.xiaopeng.speech.vui.model.VuiSceneInfo;
import com.xiaopeng.speech.vui.task.base.BaseTask;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.speech.vui.vuiengine.R;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class UpdateSceneAttributeTask extends BaseTask {
    private String TAG;
    private String sceneId;
    private SoftReference<View> updateView;
    private List<SoftReference<View>> viewList;
    private TaskWrapper viewWrapper;

    public UpdateSceneAttributeTask(TaskWrapper taskWrapper) {
        super(taskWrapper);
        this.TAG = "VuiEngine_UpdateSceneAttributeTask";
        this.viewWrapper = taskWrapper;
        this.sceneId = taskWrapper.getSceneId();
        this.updateView = taskWrapper.getView();
        this.viewList = taskWrapper.getViewList();
    }

    @Override // com.xiaopeng.speech.vui.task.base.Task
    public void execute() {
        updateScene();
    }

    private void updateScene() {
        try {
            if ((this.updateView == null && this.viewList == null) || TextUtils.isEmpty(this.sceneId) || !VuiSceneManager.instance().canUpdateScene(this.sceneId)) {
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            VuiSceneCache sceneCache = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
            ArrayList arrayList = new ArrayList();
            if (this.updateView != null) {
                LogUtils.d(this.TAG, "updateView：" + this.updateView.get());
                VuiElement buildElement = buildElement(sceneCache, this.updateView, currentTimeMillis);
                if (buildElement != null) {
                    arrayList.add(buildElement);
                }
            } else if (this.viewList != null) {
                for (int i = 0; i < this.viewList.size(); i++) {
                    VuiElement buildElement2 = buildElement(sceneCache, this.viewList.get(i), currentTimeMillis);
                    if (buildElement2 != null) {
                        arrayList.add(buildElement2);
                    }
                }
            }
            if (arrayList.size() > 0) {
                VuiScene newVuiScene = getNewVuiScene(this.sceneId, currentTimeMillis);
                newVuiScene.setElements(arrayList);
                VuiSceneInfo sceneInfo = VuiSceneManager.instance().getSceneInfo(this.sceneId);
                boolean isWholeScene = sceneInfo != null ? sceneInfo.isWholeScene() : true;
                VuiSceneCache sceneCache2 = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType());
                if (isWholeScene) {
                    setWholeSceneCache(this.sceneId, sceneCache2, arrayList);
                    VuiSceneManager.instance().sendSceneData(1, true, newVuiScene);
                    return;
                }
                if (sceneCache2 != null) {
                    sceneCache2.setCache(this.sceneId, sceneCache2.getFusionCache(this.sceneId, arrayList, true));
                }
                List<String> wholeSceneId = sceneInfo != null ? sceneInfo.getWholeSceneId() : null;
                int size = wholeSceneId == null ? 0 : wholeSceneId.size();
                LogUtils.logDebug(this.TAG, "UpdateSceneAttributeTask wholeSceneIds:" + wholeSceneId);
                if (size > 0) {
                    String activeWholeSceneId = getActiveWholeSceneId(wholeSceneId);
                    if (!TextUtils.isEmpty(activeWholeSceneId)) {
                        VuiScene newVuiScene2 = getNewVuiScene(activeWholeSceneId, currentTimeMillis);
                        newVuiScene2.setElements(arrayList);
                        setWholeSceneCache(activeWholeSceneId, sceneCache2, arrayList);
                        VuiSceneManager.instance().sendSceneData(1, true, newVuiScene2);
                    }
                    for (int i2 = 0; i2 < size; i2++) {
                        String str = wholeSceneId.get(i2);
                        if (!VuiUtils.isActiveSceneId(str)) {
                            VuiScene newVuiScene3 = getNewVuiScene(str, currentTimeMillis);
                            newVuiScene3.setElements(arrayList);
                            setWholeSceneCache(str, sceneCache2, arrayList);
                            VuiSceneManager.instance().sendSceneData(1, true, newVuiScene3);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(this.TAG, "e:" + e.getMessage());
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

    private String getElementId(SoftReference<View> softReference) {
        if (softReference == null || softReference.get() == null || !(softReference.get() instanceof IVuiElement)) {
            return null;
        }
        String str = (String) softReference.get().getTag(R.id.vuiElementId);
        if (TextUtils.isEmpty(str)) {
            if (softReference.get().getTag() != null && (softReference.get().getTag() instanceof String)) {
                String str2 = (String) softReference.get().getTag();
                if (str2.startsWith("4657")) {
                    return str2;
                }
            }
            String vuiElementId = ((IVuiElement) softReference.get()).getVuiElementId();
            if (TextUtils.isEmpty(vuiElementId)) {
                LogUtils.e(this.TAG, "update 元素的属性时必须是build过的元素");
                return null;
            }
            return vuiElementId;
        }
        return str;
    }

    private VuiElement buildElement(VuiSceneCache vuiSceneCache, SoftReference<View> softReference, long j) {
        VuiElement onBuildVuiElement;
        String elementId = getElementId(softReference);
        if (TextUtils.isEmpty(elementId)) {
            return null;
        }
        VuiElement vuiElementById = vuiSceneCache.getVuiElementById(this.sceneId, elementId);
        if (vuiElementById == null) {
            LogUtils.e(this.TAG, "缓存中没有此元素");
            return null;
        } else if (softReference == null || !(softReference.get() instanceof IVuiElementListener) || (onBuildVuiElement = ((IVuiElementListener) softReference.get()).onBuildVuiElement(elementId, this)) == null) {
            VuiElement buildVuiElementAttr = buildVuiElementAttr(softReference);
            if (buildVuiElementAttr == null || softReference == null) {
                return null;
            }
            buildVuiElementAttr.setId(elementId);
            IVuiElement iVuiElement = (IVuiElement) softReference.get();
            if (iVuiElement == null) {
                return null;
            }
            JSONObject vuiProps = iVuiElement.getVuiProps();
            if (vuiProps != null) {
                if (VuiElementType.STATEFULBUTTON.getType().equals(buildVuiElementAttr.getType())) {
                    createElementByProps(softReference, buildVuiElementAttr, vuiProps, j, false, false);
                } else if (vuiProps.keys().hasNext()) {
                    buildVuiElementAttr.setProps((JsonObject) this.mGson.fromJson(vuiProps.toString(), (Class<Object>) JsonObject.class));
                }
            }
            if (vuiElementById.isLayoutLoadable() != null && vuiElementById.isLayoutLoadable().booleanValue()) {
                buildVuiElementAttr.setLayoutLoadable(true);
            }
            buildVuiElementAttr.setTimestamp(j);
            if (buildVuiElementAttr.equals(vuiElementById)) {
                LogUtils.logDebug(this.TAG, "updateScene same");
                return null;
            }
            if (!VuiElementType.STATEFULBUTTON.getType().equals(buildVuiElementAttr.getType()) && vuiElementById.getElements() != null && vuiElementById.getElements().size() > 0) {
                buildVuiElementAttr.setElements(vuiElementById.getElements());
            }
            return buildVuiElementAttr;
        } else {
            return onBuildVuiElement;
        }
    }

    private void setWholeSceneCache(String str, VuiSceneCache vuiSceneCache, List<VuiElement> list) {
        if (vuiSceneCache != null) {
            List<VuiElement> fusionCache = vuiSceneCache.getFusionCache(str, list, false);
            vuiSceneCache.setCache(str, fusionCache);
            if ("user".equals(Build.TYPE) || LogUtils.getLogLevel() > LogUtils.LOG_DEBUG_LEVEL) {
                return;
            }
            VuiScene newVuiScene = getNewVuiScene(str, System.currentTimeMillis());
            newVuiScene.setElements(fusionCache);
            LogUtils.logDebug(this.TAG, "updateSceneTask full_scene_info" + VuiUtils.vuiSceneConvertToString(newVuiScene));
        }
    }
}
