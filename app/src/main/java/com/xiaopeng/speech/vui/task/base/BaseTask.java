package com.xiaopeng.speech.vui.task.base;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiaopeng.speech.vui.VuiEngineImpl;
import com.xiaopeng.speech.vui.VuiSceneManager;
import com.xiaopeng.speech.vui.cache.VuiSceneCacheFactory;
import com.xiaopeng.speech.vui.constants.Foo;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.model.VuiScene;
import com.xiaopeng.speech.vui.task.TaskWrapper;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.ResourceUtil;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.speech.vui.vuiengine.R;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.vui.commons.VuiPriority;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class BaseTask implements Task, IVuiElementBuilder {
    private String mPackageName;
    private String mPackageVersion;
    private String sceneId;
    public TaskWrapper wrapper;
    private String TAG = "BaseTask";
    private String mSceneIdPrefix = null;
    private String[] bizTypeEnum = {"Address", "Navi", "Route", "Waypoint", "Pic", "Charge", "Connect", "Disconnect", "Null"};
    protected Gson mGson = new Gson();

    public List<VuiElement> buildVuiElement(View view) {
        return null;
    }

    public List<VuiElement> buildVuiElement(List<View> list) {
        return null;
    }

    public BaseTask(TaskWrapper taskWrapper) {
        this.wrapper = taskWrapper;
        TaskWrapper taskWrapper2 = this.wrapper;
        this.sceneId = taskWrapper2 != null ? taskWrapper2.getSceneId() : "";
        init();
    }

    private void init() {
        PackageInfo packageInfo;
        this.mPackageName = Foo.getContext().getApplicationInfo().packageName;
        try {
            packageInfo = Foo.getContext().getPackageManager().getPackageInfo(this.mPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        this.mPackageVersion = packageInfo.versionName;
        this.mSceneIdPrefix = this.mPackageName + "-" + this.mPackageVersion;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        execute();
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0104, code lost:
        if (r1.equals(com.xiaopeng.vui.commons.VuiMode.SILENT) != false) goto L75;
     */
    /* JADX WARN: Type inference failed for: r1v3, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaopeng.vui.commons.model.VuiElement buildView(java.lang.ref.SoftReference<android.view.View> r24, java.util.List<com.xiaopeng.vui.commons.model.VuiElement> r25, java.util.List<java.lang.Integer> r26, java.lang.ref.SoftReference<com.xiaopeng.vui.commons.IVuiElementListener> r27, java.util.List<java.lang.String> r28, long r29, java.util.List<java.lang.String> r31, java.util.List<java.lang.String> r32, java.lang.String r33, int r34, boolean r35, boolean r36, com.xiaopeng.vui.commons.IVuiElementChangedListener r37) {
        /*
            Method dump skipped, instructions count: 1057
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.task.base.BaseTask.buildView(java.lang.ref.SoftReference, java.util.List, java.util.List, java.lang.ref.SoftReference, java.util.List, long, java.util.List, java.util.List, java.lang.String, int, boolean, boolean, com.xiaopeng.vui.commons.IVuiElementChangedListener):com.xiaopeng.vui.commons.model.VuiElement");
    }

    private VuiElement buildVuiElementGroup(SoftReference<View> softReference, String str, List<Integer> list, SoftReference<IVuiElementListener> softReference2, List<String> list2, long j, List<String> list3, List<String> list4, int i, List<VuiElement> list5, boolean z, boolean z2, IVuiElementChangedListener iVuiElementChangedListener) {
        VuiElement buildVuiElement = buildVuiElement(softReference, list2, list3, str, i, j, z, z2);
        if (buildVuiElement == null || buildVuiElement.getId() == null || buildVuiElement.getType() == null || buildVuiElement.getType().equals(VuiElementType.XSLIDER.getType())) {
            return buildVuiElement;
        }
        String id = buildVuiElement.getId();
        if (buildVuiElement.getType().equals(VuiElementType.UNKNOWN.getType())) {
            LogUtils.d(this.TAG, "element type is unknown" + buildVuiElement.getLabel());
            removeId(softReference, buildVuiElement.getId(), list2, list3);
            return null;
        } else if (buildVuiElement.getType().equals(VuiElementType.STATEFULBUTTON.getType())) {
            return buildVuiElement;
        } else {
            if (softReference != null && (softReference.get() instanceof IVuiElement)) {
                if (((IVuiElement) softReference.get()).isVuiLayoutLoadable() && softReference.get().getVisibility() != 0) {
                    return buildVuiElement;
                }
                if (!"com.android.systemui".equals(VuiSceneManager.instance().getmPackageName()) && softReference.get().getTag(R.id.disableChildVuiAttrsWhenInvisible) != null && softReference.get().getVisibility() != 0) {
                    return buildVuiElement;
                }
            }
            List<VuiElement> elements = buildVuiElement.getElements();
            if (elements == null) {
                elements = new ArrayList<>();
            }
            List<VuiElement> list6 = elements;
            getChildElements(softReference, list6, list, softReference2, list2, j, list3, list4, id, buildVuiElement.isLayoutLoadable() != null ? buildVuiElement.isLayoutLoadable().booleanValue() : false, z2, iVuiElementChangedListener);
            if (list6.size() > 0) {
                buildVuiElement.setElements(list6);
                return buildVuiElement;
            }
            return buildVuiElement;
        }
    }

    private void setVuiElementChangedListener(SoftReference<View> softReference, IVuiElementChangedListener iVuiElementChangedListener, String str) {
        if (iVuiElementChangedListener == null || !(softReference.get() instanceof IVuiElement)) {
            return;
        }
        IVuiElement iVuiElement = (IVuiElement) softReference.get();
        if (TextUtils.isEmpty(iVuiElement.getVuiElementId())) {
            iVuiElement.setVuiElementId(str);
        }
        if (iVuiElement.getVuiElementChangedListener() == null) {
            ((IVuiElement) softReference.get()).setVuiElementChangedListener(iVuiElementChangedListener);
        }
    }

    private void removeId(SoftReference<View> softReference, String str, List<String> list, List<String> list2) {
        if (softReference == null || softReference.get() == null) {
            return;
        }
        if (list.contains(str)) {
            list.remove(str);
        }
        if (list2 != null && list2.contains(str)) {
            list2.remove(str);
        }
        if ("com.android.systemui".equals(VuiSceneManager.instance().getmPackageName()) || TextUtils.isEmpty((String) softReference.get().getTag(R.id.vuiElementId))) {
            return;
        }
        softReference.get().setTag(R.id.vuiElementId, "");
        softReference.get().setTag(null);
    }

    private String escapeQueryChars(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\'') {
                sb.append(" ");
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    private VuiElement buildVuiElement(SoftReference<View> softReference, List<String> list, List<String> list2, String str, int i, long j, boolean z, boolean z2) {
        if (softReference == null || !(softReference.get() instanceof IVuiElement)) {
            return null;
        }
        VuiElement buildVuiElementAttr = buildVuiElementAttr(softReference);
        if (buildVuiElementAttr != null) {
            IVuiElement iVuiElement = (IVuiElement) softReference.get();
            handleId(getElementId(softReference, VuiConstants.ATTRS_ELEMENT_ID), buildVuiElementAttr, list, list2, softReference, str, i, z, z2);
            boolean z3 = iVuiElement.isVuiLayoutLoadable() || (z && !VuiElementType.XGROUPHEADER.getType().equals(buildVuiElementAttr.getType()));
            if (z3) {
                buildVuiElementAttr.setLayoutLoadable(Boolean.valueOf(z3));
            }
            JSONObject vuiProps = iVuiElement.getVuiProps();
            if (vuiProps != null) {
                if (VuiElementType.STATEFULBUTTON.getType().equals(buildVuiElementAttr.getType()) || VuiElementType.RECYCLEVIEW.getType().equals(buildVuiElementAttr.getType())) {
                    createElementByProps(softReference, buildVuiElementAttr, vuiProps, j, z3, false);
                } else if (vuiProps.keys().hasNext()) {
                    buildVuiElementAttr.setProps((JsonObject) this.mGson.fromJson(vuiProps.toString(), (Class<Object>) JsonObject.class));
                }
            }
            if (softReference != null && softReference.get() != null && (softReference.get() instanceof ViewGroup) && softReference.get().getVisibility() == 0 && !iVuiElement.isVuiModeEnabled() && softReference != null && softReference.get() != null && VuiElementType.GROUP.getType().equals(buildVuiElementAttr.getType()) && TextUtils.isEmpty(buildVuiElementAttr.getLabel()) && TextUtils.isEmpty(iVuiElement.getVuiAction())) {
                if (softReference.get().getId() != -1) {
                    if (VuiUtils.getViewMode() != null && VuiUtils.getViewMode().equals(VuiMode.DISABLED)) {
                        LogUtils.d(this.TAG, "ViewGroup is ignored:" + softReference.get());
                        if (!isContainsInAllCache(buildVuiElementAttr.getId())) {
                            removeId(softReference, buildVuiElementAttr.getId(), list, list2);
                            return null;
                        }
                    }
                } else {
                    LogUtils.d(this.TAG, "ViewGroup is ignored:" + softReference.get());
                    removeId(softReference, buildVuiElementAttr.getId(), list, list2);
                    return null;
                }
            }
        }
        return buildVuiElementAttr;
    }

    private boolean isContainsInAllCache(String str) {
        VuiElement vuiElementById = VuiSceneCacheFactory.instance().getSceneCache(VuiSceneCacheFactory.CacheType.BUILD.getType()).getVuiElementById(this.sceneId, str);
        if (vuiElementById == null) {
            LogUtils.e(this.TAG, "缓存中没有此元素");
            return false;
        } else if (VuiElementType.GROUP.getType().equals(vuiElementById.getType())) {
            return true;
        } else {
            LogUtils.e(this.TAG, "缓存中没有此元素");
            return false;
        }
    }

    public VuiElement buildVuiElementAttr(SoftReference<View> softReference) {
        JSONObject jSONObject;
        if (softReference == null || !(softReference.get() instanceof IVuiElement)) {
            return null;
        }
        IVuiElement iVuiElement = (IVuiElement) softReference.get();
        if (VuiUtils.getViewMode() != null && VuiUtils.getViewMode().equals(VuiMode.DISABLED) && softReference != null && !(softReference.get() instanceof ViewGroup) && !iVuiElement.isVuiModeEnabled()) {
            SoftReference softReference2 = new SoftReference((View) softReference.get().getParent());
            if (((softReference2.get() instanceof IVuiElement) && ((IVuiElement) softReference2.get()).getVuiElementType() == VuiElementType.XTABLAYOUT) ? false : true) {
                return null;
            }
        }
        VuiMode vuiMode = iVuiElement.getVuiMode();
        if ((vuiMode == null || vuiMode.equals(VuiMode.DISABLED) || vuiMode.equals(VuiMode.SILENT)) ? false : true) {
            VuiElement vuiElement = new VuiElement();
            String vuiAction = iVuiElement.getVuiAction();
            if (vuiAction != null) {
                vuiElement.setActions(vuiAction);
            }
            String elementLabel = getElementLabel(iVuiElement);
            if (elementLabel == null) {
                LogUtils.e(this.TAG, "label 不合法");
                return null;
            }
            if (elementLabel.contains("'")) {
                elementLabel = escapeQueryChars(elementLabel);
            }
            vuiElement.setLabel(elementLabel);
            vuiElement.setFatherLabel(iVuiElement.getVuiFatherLabel());
            String elementId = getElementId(softReference, VuiConstants.ATTRS_ELEMENT_FATHER_ID);
            if (elementId != null) {
                vuiElement.setFatherElementId(elementId);
            }
            String type = VuiElementType.UNKNOWN.getType();
            if (softReference != null && (softReference.get() instanceof RecyclerView)) {
                vuiElement.setType(VuiElementType.RECYCLEVIEW.getType());
            } else if (softReference != null && softReference.get() != null) {
                VuiElementType vuiElementType = iVuiElement.getVuiElementType();
                if (vuiElementType != null) {
                    type = vuiElementType.getType();
                }
                vuiElement.setType(type);
            }
            int level = iVuiElement.getVuiPriority().getLevel();
            if (level != VuiPriority.LEVEL3.getLevel()) {
                vuiElement.setPriority(level);
            }
            JSONObject vuiProps = iVuiElement.getVuiProps();
            if (!"com.android.systemui".equals(VuiSceneManager.instance().getmPackageName())) {
                if (softReference != null && softReference.get() != null && softReference.get().getTag(R.id.vuiElementUnSupport) != null) {
                    if (((Boolean) softReference.get().getTag(R.id.vuiElementUnSupport)).booleanValue()) {
                        if (vuiProps == null) {
                            try {
                                vuiProps = new JSONObject();
                            } catch (Exception unused) {
                            }
                        }
                        vuiProps.put(VuiConstants.PROPS_UNSUPPORTED, true);
                    } else if (vuiProps != null && vuiProps.has(VuiConstants.PROPS_UNSUPPORTED)) {
                        vuiProps.remove(VuiConstants.PROPS_UNSUPPORTED);
                    }
                }
                if (softReference != null && softReference.get() != null && softReference.get().getTag(R.id.vuiElementDefaultAction) != null && (jSONObject = (JSONObject) softReference.get().getTag(R.id.vuiElementDefaultAction)) != null) {
                    if (vuiProps == null) {
                        try {
                            vuiProps = new JSONObject();
                        } catch (Exception unused2) {
                        }
                    }
                    vuiProps.put(VuiConstants.PROPS_DEFAULTACTION, jSONObject);
                }
            }
            if (vuiProps != null) {
                if (vuiProps.has(VuiConstants.PROPS_VOICECONTROL)) {
                    try {
                        if (!vuiProps.getBoolean(VuiConstants.PROPS_VOICECONTROL)) {
                            vuiProps.remove(VuiConstants.PROPS_VOICECONTROL);
                        }
                    } catch (JSONException unused3) {
                        vuiProps.remove(VuiConstants.PROPS_VOICECONTROL);
                    }
                }
                if (vuiProps.length() > 0) {
                    iVuiElement.setVuiProps(vuiProps);
                }
            }
            vuiElement.setPosition(iVuiElement.getVuiPosition());
            if (vuiElement.getValues() == null) {
                VuiUtils.setValueAttribute(softReference.get(), vuiElement);
            }
            if (softReference != null && softReference.get() != null) {
                if (!"com.android.systemui".equals(VuiSceneManager.instance().getmPackageName())) {
                    Boolean bool = (Boolean) softReference.get().getTag(R.id.vuiElementVisible);
                    if (bool != null) {
                        if (!bool.booleanValue()) {
                            vuiElement.setVisible(false);
                        }
                    } else if (softReference.get().getVisibility() != 0) {
                        vuiElement.setVisible(false);
                    }
                } else if (softReference.get().getVisibility() != 0) {
                    vuiElement.setVisible(false);
                }
            }
            if (softReference != null && softReference.get() != null && softReference.get().getId() != -1 && softReference.get().getId() != 0) {
                String resourceName = VuiUtils.getResourceName(softReference.get().getId());
                if (!TextUtils.isEmpty(resourceName)) {
                    vuiElement.setResourceName(resourceName);
                }
            } else if (softReference != null && softReference.get() != null && !"com.android.systemui".equals(VuiSceneManager.instance().getmPackageName())) {
                Object tag = softReference.get().getTag(R.id.virtualResourceName);
                LogUtils.d(this.TAG, "resourceTag:" + tag);
                if (tag != null) {
                    vuiElement.setResourceName((String) tag);
                }
            }
            if (vuiMode != null && vuiMode.equals(VuiMode.UNDERSTOOD)) {
                vuiElement.setMode(vuiMode.getName());
            }
            return vuiElement;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x00e9 A[Catch: Exception -> 0x00f5, TryCatch #7 {Exception -> 0x00f5, blocks: (B:40:0x0092, B:52:0x00bd, B:54:0x00e9, B:55:0x00f1, B:44:0x00a4, B:46:0x00ac, B:49:0x00b0, B:51:0x00ba, B:50:0x00b7), top: B:194:0x0092 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void createElementByProps(java.lang.ref.SoftReference<android.view.View> r23, com.xiaopeng.vui.commons.model.VuiElement r24, org.json.JSONObject r25, long r26, boolean r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 994
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.task.base.BaseTask.createElementByProps(java.lang.ref.SoftReference, com.xiaopeng.vui.commons.model.VuiElement, org.json.JSONObject, long, boolean, boolean):void");
    }

    private VuiElement getStateFulBtnCurr(String str, List<VuiElement> list) {
        String[] split;
        if (TextUtils.isEmpty(str) || list == null || list.isEmpty() || TextUtils.isEmpty(str) || (split = str.split("_")) == null || split.length <= 1 || !VuiUtils.isNumeric(split[1])) {
            return null;
        }
        return list.get(Integer.parseInt(split[1]) - 1);
    }

    private void getChildElements(SoftReference<View> softReference, List<VuiElement> list, List<Integer> list2, SoftReference<IVuiElementListener> softReference2, List<String> list3, long j, List<String> list4, List<String> list5, String str, boolean z, boolean z2, IVuiElementChangedListener iVuiElementChangedListener) {
        int i;
        SoftReference softReference3;
        int i2;
        String str2;
        String vuiBizId;
        BaseTask baseTask = this;
        if (softReference == null || softReference.get() == null) {
            return;
        }
        if (softReference != null && softReference.get() != null && (softReference.get() instanceof IVuiElement) && (vuiBizId = ((IVuiElement) softReference.get()).getVuiBizId()) != null) {
            if (list5.contains(vuiBizId)) {
                return;
            }
            list5.add(vuiBizId);
        }
        if (softReference != null && softReference.get() != null && (softReference.get() instanceof RecyclerView)) {
            if (((RecyclerView) softReference.get()).getAdapter() == null) {
                return;
            }
            SoftReference softReference4 = new SoftReference((RecyclerView) softReference.get());
            int childCount = ((RecyclerView) softReference4.get()).getChildCount();
            if (softReference4.get() != null && ((RecyclerView) softReference4.get()).getChildCount() == 0) {
                LogUtils.e(baseTask.TAG, "RecyclerView 的child count 为0,view：" + softReference.get());
            }
            String str3 = ",view：";
            LogUtils.d(baseTask.TAG, "RecyclerView 的child count:" + ((RecyclerView) softReference4.get()).getChildCount() + ",view：" + softReference4.get());
            int i3 = 0;
            while (softReference4.get() != null && i3 < ((RecyclerView) softReference4.get()).getChildCount() && i3 < ((RecyclerView) softReference4.get()).getAdapter().getItemCount()) {
                SoftReference<View> softReference5 = new SoftReference<>(((RecyclerView) softReference4.get()).getChildAt(i3));
                if (softReference5.get() == null) {
                    LogUtils.e(baseTask.TAG, "RecyclerView 的child 为null,postion:" + i3);
                }
                int i4 = i3;
                String str4 = str3;
                int i5 = childCount;
                SoftReference softReference6 = softReference4;
                addChildElement(softReference5, list, list5, list2, softReference2, list3, j, list4, str, i4, z, true, iVuiElementChangedListener);
                if (softReference6.get() == null || ((RecyclerView) softReference6.get()).getChildCount() == i5) {
                    i2 = i5;
                    str2 = str4;
                    baseTask = this;
                } else {
                    i2 = i5;
                    baseTask = this;
                    str2 = str4;
                    LogUtils.w(baseTask.TAG, "RecyclerView is not same child count:" + ((RecyclerView) softReference6.get()).getChildCount() + str2 + softReference6.get() + ",len:" + i2);
                }
                i3 = i4 + 1;
                childCount = i2;
                str3 = str2;
                softReference4 = softReference6;
            }
        } else if (softReference != null && (softReference.get() instanceof ListView)) {
            SoftReference softReference7 = new SoftReference((ListView) softReference.get());
            int i6 = 0;
            while (softReference7.get() != null && i6 < ((ListView) softReference7.get()).getCount()) {
                if (((ListView) softReference7.get()).getAdapter() != null) {
                    i = i6;
                    softReference3 = softReference7;
                    addChildElement(new SoftReference<>(((ListView) softReference7.get()).getAdapter().getView(i6, null, (ViewGroup) softReference7.get())), list, list5, list2, softReference2, list3, j, list4, str, i6, z, z2, iVuiElementChangedListener);
                } else {
                    i = i6;
                    softReference3 = softReference7;
                }
                i6 = i + 1;
                softReference7 = softReference3;
            }
        } else if (softReference != null && (softReference.get() instanceof ViewGroup)) {
            int i7 = 0;
            for (SoftReference softReference8 = new SoftReference((ViewGroup) softReference.get()); softReference8.get() != null && i7 < ((ViewGroup) softReference8.get()).getChildCount(); softReference8 = softReference8) {
                addChildElement(new SoftReference<>(((ViewGroup) softReference8.get()).getChildAt(i7)), list, list5, list2, softReference2, list3, j, list4, str, i7, z, z2, iVuiElementChangedListener);
                i7++;
            }
        }
    }

    private void addChildElement(SoftReference<View> softReference, List<VuiElement> list, List<String> list2, List<Integer> list3, SoftReference<IVuiElementListener> softReference2, List<String> list4, long j, List<String> list5, String str, int i, boolean z, boolean z2, IVuiElementChangedListener iVuiElementChangedListener) {
        VuiElement buildView = buildView(softReference, list, list3, softReference2, list4, j, list5, list2, str, i, z, z2, iVuiElementChangedListener);
        if (buildView == null || buildView.getId() == null) {
            return;
        }
        String id = buildView.getId();
        list.add(buildView);
        buildView.setTimestamp(j);
        if ((softReference.get() instanceof ViewGroup) && buildView.getType() != null && buildView.getType().equals(VuiElementType.STATEFULBUTTON.getType())) {
            getChildElements(softReference, list, list3, softReference2, list4, j, list5, list2, id, z, z2, iVuiElementChangedListener);
        }
    }

    public String handleId(String str, VuiElement vuiElement, List<String> list, List<String> list2, SoftReference<View> softReference, String str2, int i, boolean z, boolean z2) {
        if ("com.android.systemui".equals(VuiSceneManager.instance().getmPackageName())) {
            if (list != null && !list.contains(str)) {
                list.add(str);
                if (vuiElement != null) {
                    vuiElement.setId(str);
                }
            }
            if (list2 != null && !list2.contains(str)) {
                list2.add(str);
            }
            return str;
        }
        if ((!z && !z2 && (str == null || str.equals("0") || !str.contains("_"))) || (softReference != null && (softReference.get() instanceof RecyclerView))) {
            String str3 = softReference.get() != null ? (String) softReference.get().getTag(R.id.vuiElementId) : null;
            if (!TextUtils.isEmpty(str3)) {
                if (!list.contains(str3)) {
                    list.add(str3);
                } else {
                    LogUtils.w(this.TAG, "更新不合法");
                }
                if (list2 != null && !list2.contains(str3)) {
                    list2.add(str3);
                }
                if (vuiElement != null) {
                    vuiElement.setId(str3);
                }
                return str3;
            }
        }
        if (str == null || str.equals("0")) {
            if (str2 == null) {
                str = "4657_" + i;
            } else {
                str = str2 + "_" + i;
            }
        } else if (list != null && list.contains(str)) {
            if (str2 == null) {
                str = str + "_" + i;
            } else {
                str = str2 + "_" + str;
                if (list.contains(str)) {
                    str = str + "_" + i;
                }
            }
        }
        if (list != null && !list.contains(str)) {
            list.add(str);
            if (vuiElement != null) {
                vuiElement.setId(str);
            }
            setVuiTag(softReference, str);
            if (list2 != null && !list2.contains(str)) {
                list2.add(str);
            }
            return str;
        }
        LogUtils.w(this.TAG, "容错失败：" + str);
        return str + "_error";
    }

    public void setVuiTag(SoftReference<View> softReference, String str) {
        if ("com.android.systemui".equals(VuiSceneManager.instance().getmPackageName()) || softReference == null || softReference.get() == null) {
            return;
        }
        if (softReference.get().getTag() == null) {
            softReference.get().setTag(str);
            softReference.get().setTag(R.id.vuiElementId, str);
            return;
        }
        String str2 = (String) softReference.get().getTag(R.id.vuiElementId);
        if (str2 == null || str2.equals(str)) {
            return;
        }
        softReference.get().setTag(str);
        softReference.get().setTag(R.id.vuiElementId, str);
    }

    private void showToast(final String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.xiaopeng.speech.vui.task.base.BaseTask.1
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(Foo.getContext(), str, 1).show();
            }
        });
    }

    private String getElementLabel(IVuiElement iVuiElement) {
        String vuiLabel = iVuiElement.getVuiLabel();
        CharSequence charSequence = null;
        if (vuiLabel != null) {
            if (!vuiLabel.contains("|") || vuiLabel.split("\\|").length <= 5) {
                return vuiLabel;
            }
            LogUtils.e(this.TAG, "label 不能超过5个");
            return null;
        }
        if (!"com.android.systemui".equals(VuiSceneManager.instance().getmPackageName())) {
            Object tag = ((View) iVuiElement).getTag(R.id.vuiLabelUnSupportText);
            if ((tag instanceof Boolean) && ((Boolean) tag).booleanValue()) {
                return vuiLabel == null ? "" : vuiLabel;
            }
        }
        if (iVuiElement instanceof TextView) {
            if (!(iVuiElement instanceof EditText)) {
                charSequence = ((TextView) iVuiElement).getText();
            } else {
                EditText editText = (EditText) iVuiElement;
                charSequence = editText.getHint();
                if (charSequence == null) {
                    charSequence = editText.getText();
                }
            }
        }
        return !TextUtils.isEmpty(charSequence) ? charSequence.toString() : "";
    }

    private String getElementId(SoftReference<View> softReference, String str) {
        String vuiElementId;
        if (softReference == null || !(softReference.get() instanceof IVuiElement)) {
            return null;
        }
        IVuiElement iVuiElement = (IVuiElement) softReference.get();
        if (str.equals(VuiConstants.ATTRS_ELEMENT_ID) && softReference != null && softReference.get() != null && softReference.get().getTag() != null && (softReference.get().getTag() instanceof String)) {
            String str2 = (String) softReference.get().getTag();
            if (str2.startsWith("4657")) {
                LogUtils.d(this.TAG, "client has set Tag" + str2);
                return str2;
            }
        }
        if (str.equals(VuiConstants.ATTRS_ELEMENT_FATHER_ID)) {
            vuiElementId = iVuiElement.getVuiFatherElementId();
        } else {
            vuiElementId = str.equals(VuiConstants.ATTRS_ELEMENT_ID) ? iVuiElement.getVuiElementId() : null;
        }
        if (vuiElementId != null && !TextUtils.isEmpty(vuiElementId)) {
            return isNumber(vuiElementId) ? vuiElementId : "" + ResourceUtil.getId(Foo.getContext(), vuiElementId);
        } else if (str.contains(VuiConstants.ATTRS_ELEMENT_FATHER_ID) || softReference == null || softReference.get() == null || softReference.get().getId() == -1) {
            return null;
        } else {
            return "" + softReference.get().getId();
        }
    }

    private boolean isNumber(String str) {
        return Pattern.compile("[0-9]+(_[0-9]+)*").matcher(str).matches();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public VuiScene getNewVuiScene(String str, long j) {
        return new VuiScene.Builder().sceneId(str).appVersion(this.mPackageVersion).packageName(this.mPackageName).timestamp(j).build();
    }

    protected String getSceneUnqiueId(String str) {
        return this.mSceneIdPrefix + "-" + str;
    }

    public String getSceneId() {
        return this.sceneId;
    }

    public String getActiveWholeSceneId(List<String> list) {
        if (list == null) {
            return "";
        }
        String str = VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_LF);
        String str2 = VuiEngineImpl.mActiveSceneIds.get(VuiConstants.SCREEN_DISPLAY_RF);
        return (TextUtils.isEmpty(str) || !list.contains(str)) ? (TextUtils.isEmpty(str2) || !list.contains(str2)) ? "" : str2 : str;
    }
}
