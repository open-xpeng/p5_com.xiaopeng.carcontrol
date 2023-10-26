package com.xiaopeng.speech.vui.utils;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.view.speech.VuiActions;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xiaopeng.speech.apirouter.Utils;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.VuiEngineImpl;
import com.xiaopeng.speech.vui.constants.Foo;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.speech.vui.model.VuiScene;
import com.xiaopeng.view.WindowManagerFactory;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.vui.commons.VuiPriority;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class VuiUtils {
    public static final String CAR_PLATFORM_A1 = "A1";
    public static final String CAR_PLATFORM_A2 = "A2";
    public static final String CAR_PLATFORM_A3 = "A3";
    public static final String CAR_PLATFORM_Q1 = "Q1";
    public static final String CAR_PLATFORM_Q2 = "Q2";
    public static final String CAR_PLATFORM_Q3 = "Q3";
    public static final String CAR_PLATFORM_Q5 = "Q5";
    public static final String CAR_PLATFORM_Q6 = "Q6";
    public static final String CAR_PLATFORM_Q7 = "Q7";
    public static final String CAR_PLATFORM_Q8 = "Q8";
    public static int LIST_VEDIO_TYPE = 1;
    private static List<String> supportMultiScreenPlatform;
    private static List<String> unSupportType;
    private static ExclusionStrategy mExclusionStrategy = new ExclusionStrategy() { // from class: com.xiaopeng.speech.vui.utils.VuiUtils.1
        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipClass(Class<?> cls) {
            return false;
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getName().equals("timestamp");
        }
    };
    public static final List<String> VUI_ENABLE_APP = Arrays.asList("com.xiaopeng.montecarlo", "com.xiaopeng.musicradio", "com.xiaopeng.carcontrol", "com.xiaopeng.car.settings", "com.xiaopeng.chargecontrol", IpcConfig.App.CAR_CAMERA, IpcConfig.App.CAR_BT_PHONE, "com.xiaopeng.aiassistant", "com.xiaopeng.carspeechservice", VuiConstants.SCENEDEMO, "com.xiaopeng.vui.demo");
    private static ExclusionStrategy mAttrExclusionStrategy = new ExclusionStrategy() { // from class: com.xiaopeng.speech.vui.utils.VuiUtils.2
        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipClass(Class<?> cls) {
            return false;
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getName().equals(VuiConstants.SCENE_ELEMENTS);
        }
    };
    private static boolean isDisableVuiFeature = false;
    private static boolean userDisabledFeature = false;
    private static String sRegion = null;
    private static VuiMode viewMode = VuiMode.NORMAL;
    private static String sType = null;

    public static String getDisplayLocation(int i) {
        return (i != 0 && i == 1) ? VuiConstants.SCREEN_DISPLAY_RF : VuiConstants.SCREEN_DISPLAY_LF;
    }

    public static void test() {
        System.out.println(vuiSceneConvertToString(stringConvertToVuiScene("{\n    \"id\": \"navigation_search\",\n    \"elements\": [\n        {\n            \"type\": \"Button\",\n            \"label\": \"返回\",\n            \"id\": 10020,\n            \"position\": 2\n        },\n        {\n            \"type\": \"EditText\",\n            \"label\": \"请输入目的地\",\n            \"id\": 10030\n        },\n        {\n            \"type\": \"Button\",\n            \"label\": \"重新设置家的位置\",\n            \"id\": 10050\n        },\n        {\n            \"type\": \"Button\",\n            \"label\": \"添加公司位置\",\n            \"id\": 10056\n        },\n        {\n            \"type\": \"Button\",\n            \"label\": \"打开收藏夹\",\n            \"id\": 10062\n        },\n        {\n            \"type\": \"Button\",\n            \"label\": \"充电站\",\n            \"id\": 10098\n        },\n        {\n            \"type\": \"Button\",\n            \"label\": \"美食\",\n            \"id\": 10034\n        },\n        {\n            \"type\": \"Button\",\n            \"label\": \"洗手间\",\n            \"id\": 10089\n        },\n        {\n            \"type\": \"Button\",\n            \"label\": \"停车场\",\n            \"id\": 10056\n        },\n        {\n            \"type\": \"Button\",\n            \"label\": \"维修\",\n            \"id\": 10065\n        },\n        {\n            \"type\": \"Button\",\n            \"label\": \"小鹏4S\",\n            \"id\": 10035\n        },\n        {\n            \"type\": \"Button\",\n            \"label\": \"智能洗车\",\n            \"id\": 10076\n        },\n        {\n            \"type\": \"RecycleView\",\n            \"dynamic\": true,\n            \"id\": 10056,\n            \"elements\": [\n                {\n                    \"type\": \"Button\",\n                    \"label\": \"维修\",\n                    \"id\": 10023\n                }\n            ]\n        }\n    ]\n}")));
    }

    static {
        ArrayList arrayList = new ArrayList();
        unSupportType = arrayList;
        arrayList.add(CAR_PLATFORM_A1);
        unSupportType.add(CAR_PLATFORM_A2);
        unSupportType.add(CAR_PLATFORM_A3);
        ArrayList arrayList2 = new ArrayList();
        supportMultiScreenPlatform = arrayList2;
        arrayList2.add(CAR_PLATFORM_Q7);
    }

    public static String vuiFeedBackConvertToString(VuiFeedback vuiFeedback) {
        if (vuiFeedback == null) {
            return null;
        }
        return new GsonBuilder().create().toJson(vuiFeedback);
    }

    public static synchronized String vuiSceneConvertToString(VuiScene vuiScene) {
        synchronized (VuiUtils.class) {
            if (vuiScene == null) {
                return null;
            }
            try {
                return new GsonBuilder().registerTypeAdapter(Integer.class, new SceneIntegerTypeAdapter()).registerTypeAdapter(Boolean.class, new SceneBooleanTypeAdapter()).create().toJson(vuiScene);
            } catch (Exception e) {
                LogUtils.e("VuiUtils", "vuiSceneConvertToString e:" + e.toString());
                return null;
            }
        }
    }

    public static synchronized String vuiUpdateSceneConvertToString(VuiScene vuiScene) {
        synchronized (VuiUtils.class) {
            if (vuiScene == null) {
                return null;
            }
            try {
                return new GsonBuilder().registerTypeAdapter(Integer.class, new SceneIntegerTypeAdapter()).registerTypeAdapter(Boolean.class, new SceneBooleanTypeAdapter()).setExclusionStrategies(mExclusionStrategy).create().toJson(vuiScene);
            } catch (Exception e) {
                LogUtils.e("VuiUtils", "vuiSceneConvertToString e:" + e.toString());
                return null;
            }
        }
    }

    public static void generateElementValueJSON(JSONObject jSONObject, String str, Object obj) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("value", obj);
            jSONObject.put(str, jSONObject2);
        } catch (Exception e) {
            LogUtils.e("VuiUtils", "generateElementValueJSON e:" + e.getMessage());
        }
    }

    public static String vuiElementGroupConvertToString(List<VuiElement> list) {
        if (list == null) {
            return null;
        }
        return new GsonBuilder().registerTypeAdapter(Integer.class, new SceneIntegerTypeAdapter()).registerTypeAdapter(Boolean.class, new SceneBooleanTypeAdapter()).setExclusionStrategies(mExclusionStrategy).create().toJson(list);
    }

    public static VuiScene stringConvertToVuiScene(String str) {
        Gson gson = new Gson();
        if (str == null) {
            return null;
        }
        return (VuiScene) gson.fromJson(str, (Class<Object>) VuiScene.class);
    }

    public static VuiElement stringConvertToVuiElement(String str) {
        Gson gson = new Gson();
        if (str == null) {
            return null;
        }
        return (VuiElement) gson.fromJson(str, (Class<Object>) VuiElement.class);
    }

    public static boolean is3DApp(String str) {
        return VuiConstants.UNITY.equals(str) || "com.DefaultCompany.VUIDemo".equals(str);
    }

    public static boolean isActiveSceneId(String str) {
        return VuiEngineImpl.mActiveSceneIds.containsValue(str) || VuiEngineImpl.mLeftPopPanelStack.contains(str) || VuiEngineImpl.mRightPopPanelStack.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class SceneIntegerTypeAdapter extends TypeAdapter<Integer> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.TypeAdapter
        public Integer read(JsonReader jsonReader) throws IOException {
            return null;
        }

        SceneIntegerTypeAdapter() {
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, Integer num) throws IOException {
            if (num != null && num.intValue() != -1) {
                jsonWriter.value(num);
            } else {
                jsonWriter.value((Number) null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class SceneBooleanTypeAdapter extends TypeAdapter<Boolean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.TypeAdapter
        public Boolean read(JsonReader jsonReader) throws IOException {
            return null;
        }

        SceneBooleanTypeAdapter() {
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, Boolean bool) throws IOException {
            if (bool != null) {
                jsonWriter.value(bool.booleanValue());
            } else {
                jsonWriter.value((Number) null);
            }
        }
    }

    public static boolean isNumer(String str) {
        try {
            return Pattern.compile("-?[0-9]+(\\.[0-9]+)?").matcher(new BigDecimal(str).toString()).matches();
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isNumeric(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public static <T> T getValueByName(VuiElement vuiElement, String str) {
        Map map;
        LogUtils.logDebug("getEventValueByName", "name:" + str + "," + new Gson().toJson(vuiElement));
        if (vuiElement != null && vuiElement.getResultActions() != null && !vuiElement.getResultActions().isEmpty()) {
            String str2 = vuiElement.getResultActions().get(0);
            if ((vuiElement.getValues() instanceof LinkedTreeMap) && (map = (Map) vuiElement.getValues()) != null) {
                if (map.get(str2) instanceof LinkedTreeMap) {
                    Map map2 = (Map) map.get(str2);
                    if (map2 != null && map2.containsKey(str) && map2.get(str) != null) {
                        return (T) map2.get(str);
                    }
                } else if (map.get(str) != null) {
                    return (T) map.get(str);
                }
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x0132  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setStatefulButtonAttr(android.view.View r16, int r17, java.lang.String[] r18, java.lang.String r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.utils.VuiUtils.setStatefulButtonAttr(android.view.View, int, java.lang.String[], java.lang.String, boolean):void");
    }

    public static void setStatefulButtonAttr(View view, int i, String[] strArr, String str) {
        setStatefulButtonAttr(view, i, strArr, str, false);
    }

    public static void setStatefulButtonAttr(View view, int i, String[] strArr) {
        setStatefulButtonAttr(view, i, strArr, null, false);
    }

    public static JSONObject createStatefulButtonData(int i, String[] strArr, JSONObject jSONObject) {
        if (strArr == null || strArr.length == 0 || i < 0 || i > strArr.length - 1) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        try {
            Object[] objArr = new String[strArr.length];
            int i2 = 0;
            while (i2 < strArr.length) {
                JSONObject jSONObject2 = new JSONObject();
                int i3 = i2 + 1;
                String str = "state_" + i3;
                objArr[i2] = str;
                jSONObject2.put(str, strArr[i2]);
                jSONArray.put(jSONObject2);
                i2 = i3;
            }
            jSONObject.put("states", jSONArray);
            jSONObject.put("curState", objArr[i]);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject createStatefulButtonData(int i, String[] strArr, JSONObject jSONObject, View view) {
        if (strArr == null || strArr.length == 0 || !(view instanceof IVuiElement) || i < 0 || i > strArr.length - 1) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        try {
            String[] strArr2 = new String[strArr.length];
            int i2 = 0;
            while (i2 < strArr.length) {
                JSONObject jSONObject2 = new JSONObject();
                int i3 = i2 + 1;
                String str = "state_" + i3;
                strArr2[i2] = str;
                jSONObject2.put(str, strArr[i2]);
                jSONArray.put(jSONObject2);
                i2 = i3;
            }
            jSONObject.put("states", jSONArray);
            ((IVuiElement) view).setVuiValue(strArr2[i], view);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setStatefulButtonValue(View view, int i) {
        if (view instanceof IVuiElement) {
            ((IVuiElement) view).setVuiValue("state_" + (i + 1), view);
        }
    }

    public static VuiElement generateCommonVuiElement(String str, VuiElementType vuiElementType, String str2, String str3) {
        return generateCommonVuiElement(str, vuiElementType, str2, str3, false, VuiPriority.LEVEL3);
    }

    public static VuiElement generateCommonVuiElement(String str, VuiElementType vuiElementType, String str2, String str3, boolean z, VuiPriority vuiPriority) {
        VuiElement build = new VuiElement.Builder().id(str).type(vuiElementType.getType()).label(str2).action(str3).timestamp(System.currentTimeMillis()).build();
        if (z) {
            build.setLayoutLoadable(Boolean.valueOf(z));
        }
        if (VuiElementType.RECYCLEVIEW.getType().equals(vuiElementType.getType())) {
            build.setLayoutLoadable(true);
        }
        if (VuiPriority.LEVEL3.getLevel() != vuiPriority.getLevel()) {
            build.setPriority(vuiPriority.getLevel());
        }
        return build;
    }

    public static VuiElement generateCommonVuiElement(String str, VuiElementType vuiElementType, String str2, String str3, boolean z, VuiPriority vuiPriority, int i) {
        VuiElement build = new VuiElement.Builder().id(str).type(vuiElementType.getType()).label(str2).action(str3).timestamp(System.currentTimeMillis()).build();
        if (z) {
            build.setLayoutLoadable(Boolean.valueOf(z));
        }
        if (VuiElementType.RECYCLEVIEW.getType().equals(vuiElementType.getType())) {
            build.setLayoutLoadable(true);
            if (LIST_VEDIO_TYPE == i) {
                JsonObject jsonObject = new JsonObject();
                new HashMap();
                jsonObject.addProperty("matchedFirstPriority", (Boolean) true);
                jsonObject.addProperty("firstPriority", (Boolean) true);
                jsonObject.addProperty("listType", "videoList");
                build.setProps(jsonObject);
            }
        }
        if (VuiPriority.LEVEL3.getLevel() != vuiPriority.getLevel()) {
            build.setPriority(vuiPriority.getLevel());
        }
        return build;
    }

    public static VuiElement generateCommonVuiElement(int i, VuiElementType vuiElementType, String str, String str2) {
        return generateCommonVuiElement("" + i, vuiElementType, str, str2);
    }

    public static VuiElement generateCommonVuiElement(int i, VuiElementType vuiElementType, String str) {
        return generateCommonVuiElement("" + i, vuiElementType, str, (String) null);
    }

    public static VuiElement generateCommonVuiElement(String str, VuiElementType vuiElementType, String str2) {
        return generateCommonVuiElement(str, vuiElementType, str2, (String) null);
    }

    public static VuiElement generateStatefulButtonElement(int i, String[] strArr, int i2) {
        return generateStatefulButtonElement(i, strArr, VuiAction.SETVALUE.getName(), "" + i2, "");
    }

    public static VuiElement generateStatefulButtonElement(int i, String[] strArr, String str) {
        return generateStatefulButtonElement(i, strArr, VuiAction.SETVALUE.getName(), str, "");
    }

    public static VuiElement generateStatefulButtonElement(int i, String[] strArr, String str, String str2) {
        return generateStatefulButtonElement(i, strArr, str, str2, "");
    }

    public static VuiElement generateStatefulButtonElement(int i, String[] strArr, String str, int i2) {
        return generateStatefulButtonElement(i, strArr, str, "" + i2, "");
    }

    public static VuiElement generateStatefulButtonElement(int i, String[] strArr, String str, String str2, String str3) {
        if (str == null) {
            str = VuiAction.SETVALUE.getName();
        }
        long currentTimeMillis = System.currentTimeMillis();
        VuiElement build = new VuiElement.Builder().id(str2).type(VuiElementType.STATEFULBUTTON.getType()).label(str3).timestamp(currentTimeMillis).action(str).build();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < strArr.length; i2++) {
            String[] split = TextUtils.isEmpty(strArr[i2]) ? null : strArr[i2].split("-");
            if (split != null) {
                VuiElement build2 = new VuiElement.Builder().id(str2 + "_state_" + i2).type(VuiElementType.STATE.getType()).label(split[0]).timestamp(currentTimeMillis).build();
                if (split.length > 1) {
                    build2.setValues(split[1]);
                } else {
                    build2.setValues(split[0]);
                }
                arrayList.add(build2);
            }
        }
        setStatefulButtonValues(i, strArr, build);
        if (!arrayList.isEmpty()) {
            build.setElements(arrayList);
        }
        return build;
    }

    public static void setStatefulButtonValues(int i, String[] strArr, VuiElement vuiElement) {
        Gson gson = new Gson();
        if (vuiElement.getActions() == null || i < 0 || i >= strArr.length) {
            return;
        }
        ArrayList arrayList = new ArrayList(vuiElement.actions.entrySet());
        JSONObject jSONObject = new JSONObject();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (VuiAction.SETVALUE.getName().equalsIgnoreCase((String) ((Map.Entry) arrayList.get(i2)).getKey())) {
                String[] split = TextUtils.isEmpty(strArr[i]) ? null : strArr[i].split("-");
                if (split != null && split.length >= 1) {
                    generateElementValueJSON(jSONObject, VuiActions.SETVALUE, split[0]);
                }
            }
        }
        vuiElement.setValues(gson.fromJson(jSONObject.toString(), (Class<Object>) JsonObject.class));
    }

    public static boolean canUseVuiFeature() {
        return platformIsSupport() && !userDisabledFeature;
    }

    public static boolean platformIsSupport() {
        if (isOverseasVersion()) {
            return false;
        }
        String carPlatForm = getCarPlatForm();
        if (TextUtils.isEmpty(carPlatForm) && Utils.checkApkExist(VuiConstants.VUI_SCENE_THIRD_APP)) {
            return true;
        }
        return (TextUtils.isEmpty(carPlatForm) || unSupportType.contains(carPlatForm) || !Utils.checkApkExist("com.xiaopeng.carspeechservice")) ? false : true;
    }

    public static boolean is3DUIPlatForm() {
        boolean checkApkExist = Utils.checkApkExist(VuiConstants.UNITY);
        LogUtils.i("VuiUtils", "is3DUIPlatForm:  " + checkApkExist);
        return checkApkExist;
    }

    public static boolean isMultiScreenPlatForm() {
        return supportMultiScreenPlatform.contains(getCarPlatForm());
    }

    public static boolean isOverseasVersion() {
        if (sRegion == null) {
            sRegion = getVersionInCountryCode();
            LogUtils.i("VuiUtils", "CountryCode:" + sRegion);
        }
        if (TextUtils.isEmpty(sRegion)) {
            return false;
        }
        return "EU".equals(sRegion.toUpperCase());
    }

    public static String getVersionInCountryCode() {
        String str = SystemProperties.get("ro.xiaopeng.software", "");
        return ("".equals(str) || str == null) ? str : str.substring(7, 9);
    }

    public static boolean cannotUpload() {
        if (isFeatureDisabled()) {
            LogUtils.d("VuiUtils", "canUseVuiFeature():" + canUseVuiFeature() + ",isFeatureDisabled:" + isFeatureDisabled());
        }
        return !canUseVuiFeature() || isFeatureDisabled();
    }

    public static boolean isFeatureDisabled() {
        return isDisableVuiFeature;
    }

    public static void disableVuiFeature() {
        isDisableVuiFeature = true;
    }

    public static void enableVuiFeature() {
        isDisableVuiFeature = false;
    }

    public static void userSetFeatureState(boolean z) {
        userDisabledFeature = z;
    }

    public static String getResourceName(int i) {
        int indexOf;
        try {
            String resourceName = Foo.getContext().getResources().getResourceName(i);
            return (TextUtils.isEmpty(resourceName) || (indexOf = resourceName.indexOf(QuickSettingConstants.JOINER)) == -1) ? resourceName : resourceName.substring(indexOf + 1);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void userDisableViewMode() {
        viewMode = VuiMode.DISABLED;
    }

    public static VuiMode getViewMode() {
        return viewMode;
    }

    public static SoftReference<View> findRecyclerView(SoftReference<View> softReference) {
        LinkedList linkedList = new LinkedList();
        linkedList.offer(softReference);
        while (!linkedList.isEmpty()) {
            SoftReference<View> softReference2 = (SoftReference) linkedList.poll();
            if (softReference2 != null && (softReference2.get() instanceof RecyclerView)) {
                return softReference2;
            }
            if (softReference2 != null && (softReference2.get() instanceof ViewGroup)) {
                SoftReference softReference3 = new SoftReference((ViewGroup) softReference2.get());
                for (int i = 0; softReference3.get() != null && i < ((ViewGroup) softReference3.get()).getChildCount(); i++) {
                    SoftReference softReference4 = new SoftReference(((ViewGroup) softReference3.get()).getChildAt(i));
                    if (softReference4.get() instanceof ViewGroup) {
                        linkedList.offer(softReference4);
                    }
                }
            }
        }
        return null;
    }

    public static View findChildRecyclerView(RecyclerView recyclerView) {
        if (recyclerView != null) {
            LinkedList linkedList = new LinkedList();
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                linkedList.offer(recyclerView.getChildAt(i));
            }
            while (!linkedList.isEmpty()) {
                View view = (View) linkedList.poll();
                if (view instanceof RecyclerView) {
                    return view;
                }
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                        View childAt = viewGroup.getChildAt(i2);
                        if (childAt instanceof ViewGroup) {
                            linkedList.offer(childAt);
                        }
                    }
                }
            }
            return null;
        }
        return null;
    }

    public static int getExtraPage(VuiElement vuiElement) {
        JsonObject props;
        if (vuiElement.getProps() == null || (props = vuiElement.getProps()) == null || !props.has("extraPage")) {
            return -1;
        }
        return props.get("extraPage").getAsInt();
    }

    public static ViewPager findViewPager(View view) {
        LinkedList linkedList = new LinkedList();
        linkedList.offer(view);
        while (!linkedList.isEmpty()) {
            View view2 = (View) linkedList.poll();
            if (view2 instanceof ViewPager) {
                return (ViewPager) view2;
            }
            if (view2 instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view2;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (childAt instanceof ViewGroup) {
                        linkedList.offer(childAt);
                    }
                }
            }
        }
        return null;
    }

    public static String getPackageNameFromSceneId(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.contains("-")) {
            return str.split("-")[0];
        }
        if (str.contains("_")) {
            return str.split("_")[0];
        }
        return null;
    }

    public static boolean isThirdApp(String str) {
        if ("com.android.systemui".equals(str)) {
            return false;
        }
        return TextUtils.isEmpty(str) || !str.startsWith("com.xiaopeng") || "com.xiaopeng.xpdemo".equals(str) || "com.xiaopeng.VuiDemo".equals(str);
    }

    public static void setValueAttribute(View view, VuiElement vuiElement) {
        Object vuiValue;
        Gson gson = new Gson();
        if (view == null || vuiElement == null || !(view instanceof IVuiElement)) {
            return;
        }
        String type = vuiElement.getType();
        JSONObject jSONObject = new JSONObject();
        if (type != null && type.equals(VuiElementType.RADIOBUTTON.getType())) {
            setPropsEvent(view, vuiElement, jSONObject);
        } else if (type != null && type.equals(VuiElementType.CHECKBOX.getType())) {
            setPropsEvent(view, vuiElement, jSONObject);
        } else if (type != null && type.equals(VuiElementType.SWITCH.getType())) {
            setPropsEvent(view, vuiElement, jSONObject);
        } else if (type != null && type.equals(VuiElementType.SEEKBAR.getType())) {
            if (view != null && (view instanceof ProgressBar)) {
                generateElementValueJSON(jSONObject, VuiAction.SETVALUE.getName(), Integer.valueOf(((ProgressBar) view).getProgress()));
            }
            vuiElement.setValues(gson.fromJson(jSONObject.toString(), (Class<Object>) JsonObject.class));
        } else {
            if (type != null && type.equals(VuiElementType.XSLIDER.getType())) {
                try {
                    try {
                        vuiValue = view instanceof IVuiElement ? ((IVuiElement) view).getVuiValue() : null;
                        if (vuiValue == null) {
                            vuiValue = getReflexMethod(view, "getIndicatorValue");
                        }
                    } catch (Throwable th) {
                        Object reflexMethod = getReflexMethod(view, "getIndicatorValue");
                        if (reflexMethod != null && (reflexMethod instanceof Float)) {
                            generateElementValueJSON(jSONObject, VuiAction.SETVALUE.getName(), Integer.valueOf(((Float) reflexMethod).intValue()));
                            vuiElement.setValues(gson.fromJson(jSONObject.toString(), (Class<Object>) JsonObject.class));
                        }
                        throw th;
                    }
                } catch (Throwable unused) {
                    LogUtils.e("xpui version is not correct");
                    Object reflexMethod2 = getReflexMethod(view, "getIndicatorValue");
                    if (reflexMethod2 == null || !(reflexMethod2 instanceof Float)) {
                        return;
                    }
                    generateElementValueJSON(jSONObject, VuiAction.SETVALUE.getName(), Integer.valueOf(((Float) reflexMethod2).intValue()));
                }
                if (vuiValue == null || !(vuiValue instanceof Float)) {
                    return;
                }
                generateElementValueJSON(jSONObject, VuiAction.SETVALUE.getName(), Integer.valueOf(((Float) vuiValue).intValue()));
                vuiElement.setValues(gson.fromJson(jSONObject.toString(), (Class<Object>) JsonObject.class));
            } else if (type == null || !type.equals(VuiElementType.XTABLAYOUT.getType())) {
            } else {
                try {
                    try {
                        vuiValue = view instanceof IVuiElement ? ((IVuiElement) view).getVuiValue() : null;
                        if (vuiValue == null) {
                            vuiValue = getReflexMethod(view, "getSelectedTabIndex");
                        }
                    } catch (Throwable th2) {
                        Object reflexMethod3 = getReflexMethod(view, "getSelectedTabIndex");
                        if (reflexMethod3 != null && (reflexMethod3 instanceof Integer)) {
                            generateElementValueJSON(jSONObject, VuiAction.SELECTTAB.getName(), Integer.valueOf(((Integer) reflexMethod3).intValue()));
                            vuiElement.setValues(gson.fromJson(jSONObject.toString(), (Class<Object>) JsonObject.class));
                        }
                        throw th2;
                    }
                } catch (Throwable unused2) {
                    LogUtils.w("xpui version is not correct");
                    Object reflexMethod4 = getReflexMethod(view, "getSelectedTabIndex");
                    if (reflexMethod4 == null || !(reflexMethod4 instanceof Integer)) {
                        return;
                    }
                    generateElementValueJSON(jSONObject, VuiAction.SELECTTAB.getName(), Integer.valueOf(((Integer) reflexMethod4).intValue()));
                }
                if (vuiValue == null || !(vuiValue instanceof Integer)) {
                    return;
                }
                generateElementValueJSON(jSONObject, VuiAction.SELECTTAB.getName(), Integer.valueOf(((Integer) vuiValue).intValue()));
                vuiElement.setValues(gson.fromJson(jSONObject.toString(), (Class<Object>) JsonObject.class));
            }
        }
    }

    private static void setPropsEvent(View view, VuiElement vuiElement, JSONObject jSONObject) {
        boolean z;
        Gson gson = new Gson();
        if (view != null && (view instanceof CompoundButton)) {
            z = ((CompoundButton) view).isChecked();
        } else if (view != null && (view instanceof Checkable)) {
            z = ((Checkable) view).isChecked();
        } else if (view != null) {
            Object vuiValue = ((IVuiElement) view).getVuiValue();
            if (vuiValue != null && (vuiValue instanceof Boolean)) {
                z = ((Boolean) vuiValue).booleanValue();
            } else {
                z = view.isSelected();
            }
        } else {
            z = false;
        }
        generateElementValueJSON(jSONObject, VuiAction.SETCHECK.getName(), Boolean.valueOf(z));
        setVuiPriority((IVuiElement) view, vuiElement, z);
        vuiElement.setValues(gson.fromJson(jSONObject.toString(), (Class<Object>) JsonObject.class));
    }

    private static void setVuiPriority(IVuiElement iVuiElement, VuiElement vuiElement, boolean z) {
        if (iVuiElement == null || vuiElement == null) {
            return;
        }
        try {
            JSONObject vuiProps = iVuiElement.getVuiProps();
            if (vuiProps != null && vuiProps.has(VuiConstants.PROPS_DEFAULTPRIORITY)) {
                if (z) {
                    vuiElement.setPriority(VuiPriority.LEVEL1.getLevel());
                } else {
                    vuiElement.setPriority(vuiProps.getInt(VuiConstants.PROPS_DEFAULTPRIORITY));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static Object getReflexMethod(View view, String str) {
        Method declaredMethod;
        if (view != null && (declaredMethod = getDeclaredMethod(view, str, null)) != null) {
            try {
                declaredMethod.setAccessible(true);
                return declaredMethod.invoke(view, null);
            } catch (Exception e) {
                LogUtils.e("VuiUtils", "getReflexMethod:" + e.getMessage());
            }
        }
        return null;
    }

    private static Method getDeclaredMethod(Object obj, String str, Class<?>... clsArr) {
        Method declaredMethod;
        for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            try {
                declaredMethod = cls.getDeclaredMethod(str, clsArr);
            } catch (Exception unused) {
            }
            if (declaredMethod != null) {
                return declaredMethod;
            }
        }
        return null;
    }

    public static void addScrollProps(VuiElement vuiElement, View view) {
        if (vuiElement != null) {
            try {
                JsonObject props = vuiElement.getProps();
                if (props == null) {
                    props = new JsonObject();
                }
                if (view instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) view;
                    if (vuiElement.getActions() != null) {
                        ArrayList arrayList = new ArrayList(vuiElement.actions.entrySet());
                        if (!arrayList.isEmpty()) {
                            if (VuiAction.SCROLLBYY.getName().equals(((Map.Entry) arrayList.get(0)).getKey())) {
                                props.addProperty(VuiConstants.PROPS_SCROLLUP, Boolean.valueOf(recyclerView.canScrollVertically(-1)));
                                props.addProperty(VuiConstants.PROPS_SCROLLDOWN, Boolean.valueOf(recyclerView.canScrollVertically(1)));
                            } else if (VuiAction.SCROLLBYX.getName().equals(((Map.Entry) arrayList.get(0)).getKey())) {
                                props.addProperty(VuiConstants.PROPS_SCROLLRIGHT, Boolean.valueOf(recyclerView.canScrollVertically(1)));
                                props.addProperty(VuiConstants.PROPS_SCROLLLEFT, Boolean.valueOf(recyclerView.canScrollVertically(-1)));
                            }
                        }
                    }
                }
                vuiElement.setProps(props);
            } catch (Exception unused) {
            }
        }
    }

    public static void addProps(VuiElement vuiElement, Map<String, Boolean> map) {
        if (vuiElement == null || map == null) {
            return;
        }
        try {
            if (map.size() > 0) {
                JsonObject props = vuiElement.getProps();
                if (props == null) {
                    props = new JsonObject();
                }
                ArrayList arrayList = new ArrayList(map.entrySet());
                for (int i = 0; i < arrayList.size(); i++) {
                    Map.Entry entry = (Map.Entry) arrayList.get(i);
                    props.addProperty((String) entry.getKey(), (Boolean) entry.getValue());
                }
                vuiElement.setProps(props);
            }
        } catch (Exception unused) {
        }
    }

    public static void addHasFeedbackProp(IVuiElement iVuiElement) {
        if (iVuiElement != null) {
            try {
                JSONObject vuiProps = iVuiElement.getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                vuiProps.put("hasFeedback", true);
                iVuiElement.setVuiProps(vuiProps);
            } catch (Exception unused) {
            }
        }
    }

    public static void addCanVoiceControlProp(IVuiElement iVuiElement) {
        if (iVuiElement != null) {
            try {
                JSONObject vuiProps = iVuiElement.getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                vuiProps.put(VuiConstants.PROPS_VOICECONTROL, true);
                iVuiElement.setVuiProps(vuiProps);
            } catch (Exception unused) {
            }
        }
    }

    public static void addGeneralActProp(IVuiElement iVuiElement, String str) {
        if (iVuiElement != null) {
            try {
                JSONObject vuiProps = iVuiElement.getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                vuiProps.put(VuiConstants.PROPS_GENERALACT, str);
                iVuiElement.setVuiProps(vuiProps);
            } catch (Exception unused) {
            }
        }
    }

    public static void addUnitProp(IVuiElement iVuiElement, String str) {
        if (iVuiElement != null) {
            try {
                JSONObject vuiProps = iVuiElement.getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                vuiProps.put(VuiConstants.PROPS_UNIT, str);
                iVuiElement.setVuiProps(vuiProps);
            } catch (Exception unused) {
            }
        }
    }

    public static void addUnsupportProp(IVuiElement iVuiElement) {
        if (iVuiElement != null) {
            try {
                JSONObject vuiProps = iVuiElement.getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                vuiProps.put(VuiConstants.PROPS_UNSUPPORTED, true);
                iVuiElement.setVuiProps(vuiProps);
            } catch (Exception unused) {
            }
        }
    }

    public static void addDisableTplProp(IVuiElement iVuiElement) {
        if (iVuiElement != null) {
            try {
                JSONObject vuiProps = iVuiElement.getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                vuiProps.put(VuiConstants.PROPS_DISABLETPL, true);
                iVuiElement.setVuiProps(vuiProps);
            } catch (Exception unused) {
            }
        }
    }

    public static void addMatchFirstProp(IVuiElement iVuiElement) {
        if (iVuiElement != null) {
            try {
                JSONObject vuiProps = iVuiElement.getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                vuiProps.put(VuiConstants.PROPS_MATCHFIRSTPRIORITY, true);
                iVuiElement.setVuiProps(vuiProps);
            } catch (Exception unused) {
            }
        }
    }

    public static void addSKipAlreadyProp(IVuiElement iVuiElement) {
        if (iVuiElement != null) {
            try {
                JSONObject vuiProps = iVuiElement.getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                vuiProps.put("skipMultipleAlready", true);
                iVuiElement.setVuiProps(vuiProps);
            } catch (Exception unused) {
            }
        }
    }

    public static void addDefaultPriorityValue(IVuiElement iVuiElement, int i) {
        if (iVuiElement != null) {
            try {
                JSONObject vuiProps = iVuiElement.getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                vuiProps.put(VuiConstants.PROPS_DEFAULTPRIORITY, i);
                iVuiElement.setVuiProps(vuiProps);
            } catch (Exception unused) {
            }
        }
    }

    public static void addProps(IVuiElement iVuiElement, Map<String, Object> map) {
        if (iVuiElement == null || map == null) {
            return;
        }
        try {
            if (map.size() > 0) {
                JSONObject vuiProps = iVuiElement.getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                ArrayList arrayList = new ArrayList(map.entrySet());
                for (int i = 0; i < arrayList.size(); i++) {
                    Map.Entry entry = (Map.Entry) arrayList.get(i);
                    vuiProps.put((String) entry.getKey(), entry.getValue());
                }
                iVuiElement.setVuiProps(vuiProps);
            }
        } catch (Exception unused) {
        }
    }

    public static void setRecyclerViewItemVuiAttr(IVuiElement iVuiElement, int i, String str) {
        setRecyclerViewItemVuiAttr(iVuiElement, i, str, null, null);
    }

    public static void setRecyclerViewItemVuiAttr(IVuiElement iVuiElement, int i, String str, VuiElementType vuiElementType) {
        setRecyclerViewItemVuiAttr(iVuiElement, i, str, vuiElementType, null);
    }

    public static void setRecyclerViewItemVuiAttr(IVuiElement iVuiElement, int i, String str, VuiElementType vuiElementType, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            VuiEngine.getInstance(Foo.getContext()).setVuiElementTag((View) iVuiElement, str2 + "_" + i);
        } else {
            iVuiElement.setVuiElementId(((View) iVuiElement).getId() + "_" + i);
        }
        iVuiElement.setVuiPosition(i);
        if (TextUtils.isEmpty(str)) {
            iVuiElement.setVuiLabel(str);
        }
        iVuiElement.setVuiPosition(i);
        if (vuiElementType != null) {
            iVuiElement.setVuiElementType(vuiElementType);
        }
    }

    public static String getCarPlatForm() {
        String str = sType;
        if (str != null) {
            return str;
        }
        String xpCduType = getXpCduType();
        sType = xpCduType;
        return xpCduType;
    }

    public static String getXpCduType() {
        String str = SystemProperties.get("ro.xiaopeng.software", "");
        return "".equals(str) ? str : str.substring(5, 7);
    }

    public static boolean isPerformVuiAction(View view) {
        if (view instanceof IVuiElement) {
            return ((IVuiElement) view).isPerformVuiAction();
        }
        return false;
    }

    public static String getDisplayLocation(String str) {
        return "0".equals(str) ? VuiConstants.SCREEN_DISPLAY_LF : "1".equals(str) ? VuiConstants.SCREEN_DISPLAY_RF : (VuiConstants.SCREEN_DISPLAY_LF.equals(str) || VuiConstants.SCREEN_DISPLAY_RF.equals(str)) ? str : VuiConstants.SCREEN_DISPLAY_LF;
    }

    public static String getSourceDisplayLocation() {
        if (Foo.getContext() == null) {
            return VuiConstants.SCREEN_DISPLAY_LF;
        }
        try {
            int screenId = WindowManagerFactory.create(Foo.getContext()).getScreenId(Foo.getContext().getApplicationInfo().packageName);
            LogUtils.d("VuiUtils:  ", screenId + "");
            return String.valueOf(screenId);
        } catch (Throwable th) {
            th.printStackTrace();
            return VuiConstants.SCREEN_DISPLAY_LF;
        }
    }
}
