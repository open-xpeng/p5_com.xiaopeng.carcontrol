package com.xiaopeng.speech;

import android.content.Context;
import android.text.TextUtils;
import com.xiaopeng.lib.apirouter.server.ApiPublisherProvider;
import com.xiaopeng.lib.apirouter.server.IManifestHandler;
import com.xiaopeng.lib.apirouter.server.IManifestHelper;
import com.xiaopeng.lib.apirouter.server.ManifestHelper_VuiEngine;
import com.xiaopeng.speech.overall.OverallManager;
import com.xiaopeng.speech.overall.listener.IXpOverallListener;
import com.xiaopeng.speech.vui.VuiEngineImpl;
import com.xiaopeng.speech.vui.listener.IUnityVuiSceneListener;
import com.xiaopeng.speech.vui.listener.IUnityVuiStateListener;
import com.xiaopeng.speech.vui.model.VuiScene;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class UnitySpeechEngine {
    private static String TAG = "UnitySpeechEngine";
    private static VuiEngineImpl impl;

    public static void setLoglevel(int i) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setLoglevel(i);
        }
    }

    public static boolean isVuiFeatureDisabled() {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.isVuiFeatureDisabled();
        }
        return true;
    }

    public static boolean isInSpeech() {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.isInSpeech();
        }
        return false;
    }

    public static void setProcessName(String str) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setProcessName(str);
        }
    }

    public static void dispatchVuiEvent(String str, String str2) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.dispatchVuiEvent(str, str2);
        }
    }

    public static String getElementState(String str, String str2) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.getElementState(str, str2);
        }
        return null;
    }

    public static void init(Context context) {
        if (ApiPublisherProvider.CONTEXT == null) {
            ApiPublisherProvider.CONTEXT = context.getApplicationContext();
        }
        initApiRouter();
        if (impl == null) {
            impl = new VuiEngineImpl(context, false);
        }
        impl.init("ApiRouterUnitySceneService");
        OverallManager.instance().init(context);
    }

    public static void init(Context context, IXpOverallListener iXpOverallListener) {
        LogUtils.logInfo(TAG, "init");
        if (ApiPublisherProvider.CONTEXT == null) {
            ApiPublisherProvider.CONTEXT = context.getApplicationContext();
        }
        initApiRouter();
        if (impl == null) {
            VuiEngineImpl vuiEngineImpl = new VuiEngineImpl(context, false);
            impl = vuiEngineImpl;
            vuiEngineImpl.init("ApiRouterUnitySceneService");
        }
        OverallManager.instance().init(context, iXpOverallListener);
    }

    public static void subScribeOverallCommand(String[] strArr, IXpOverallListener iXpOverallListener) {
        OverallManager.instance().addObserverEvents(strArr, null, iXpOverallListener);
    }

    public static void subScribeOverallCommand(String[] strArr, String[] strArr2, IXpOverallListener iXpOverallListener) {
        OverallManager.instance().addObserverEvents(strArr, strArr2, iXpOverallListener);
    }

    public static void setOverallListener(IXpOverallListener iXpOverallListener) {
        LogUtils.logDebug(TAG, "setOverallListener:" + iXpOverallListener);
        OverallManager.instance().setOverallListener(iXpOverallListener);
    }

    public static void addOverallListener(IXpOverallListener iXpOverallListener) {
        LogUtils.logDebug(TAG, "setOverallListener:" + iXpOverallListener);
        OverallManager.instance().addOverallListener(iXpOverallListener);
    }

    public static void removeOverallListener(IXpOverallListener iXpOverallListener) {
        LogUtils.logDebug(TAG, "removeOverallListener:" + iXpOverallListener);
        OverallManager.instance().removeOverallListener(iXpOverallListener);
    }

    public static void registerVuiSceneListener(String str, IUnityVuiSceneListener iUnityVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addVuiSceneListener(str, null, iUnityVuiSceneListener, null, true);
        }
    }

    public static void unregisterVuiSceneListener(String str, IUnityVuiSceneListener iUnityVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeVuiSceneListener(str, iUnityVuiSceneListener, false);
        }
    }

    public static void setBuildElement(String str) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setBuildElements(str);
        }
    }

    public static void setUpdateElement(String str) {
        if (impl != null) {
            try {
                VuiScene stringConvertToVuiScene = VuiUtils.stringConvertToVuiScene(str);
                impl.setUpdateElements(stringConvertToVuiScene.getSceneId(), stringConvertToVuiScene.getElements());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public static void setUpdateElement(String str, String str2) {
        if (impl != null) {
            try {
                VuiElement stringConvertToVuiElement = VuiUtils.stringConvertToVuiElement(str2);
                ArrayList arrayList = new ArrayList();
                arrayList.add(stringConvertToVuiElement);
                impl.setUpdateElements(str, arrayList);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public static void setUpdateElement(String str, String str2, int i) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setUpdateElements(str, str2, i);
        }
    }

    public static String getStatefulButtonString(int i, String str, String str2, String str3, String str4) {
        VuiElement generateStatefulButtonElement;
        if (TextUtils.isEmpty(str) || (generateStatefulButtonElement = VuiUtils.generateStatefulButtonElement(i, str.split(","), str2, str3, str4)) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(generateStatefulButtonElement);
        return VuiUtils.vuiElementGroupConvertToString(arrayList);
    }

    public static void setUpdateElementValue(String str, String str2, Object obj) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setUpdateElementValue(str, str2, obj);
        }
    }

    public static void setUpdateElementVisible(String str, String str2, boolean z) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setUpdateElementVisible(str, str2, z);
        }
    }

    public static VuiElement getVuiElement(String str, String str2) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.getVuiElement(str, str2);
        }
        return null;
    }

    public static VuiScene getVuiScene(String str) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.getVuiScene(str);
        }
        return null;
    }

    public static void enterScene(String str, boolean z) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.enterScene(str, z);
        }
    }

    public static void enterScene(String str, boolean z, int i) {
        LogUtils.logInfo(TAG, "enterScene:" + str + ",isMainScene:" + z + ",displayLocation:" + i);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.enterScene(str, VuiUtils.getDisplayLocation(i), z);
        }
    }

    public static void updateDisplayLocation(String str, int i) {
        LogUtils.logInfo(TAG, "updateDisplayLocation:" + str + ", displayLocation:" + i);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.updateDisplayLocation(str, VuiUtils.getDisplayLocation(i));
        }
    }

    public static void exitScene(String str) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.exitScene(str, true);
        }
    }

    public static void exitScene(String str, int i) {
        LogUtils.logInfo(TAG, "exitScene:" + str + ",displayLocation:" + i);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.exitScene(str, VuiUtils.getDisplayLocation(i), true, null);
        }
    }

    public static void exitScene(String str, int i, boolean z) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.exitScene(str, VuiUtils.getDisplayLocation(i), z, null);
        }
    }

    public static void dispatchOverallEvent(String str, String str2) {
        OverallManager.instance().dispatchOverallEvent(str, str2);
    }

    public static void dispatchOverallQuery(String str, String str2, String str3) {
        OverallManager.instance().dispatchOverallQuery(str, str2, str3);
    }

    public static void onVuiQuery(String str, String str2) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.onVuiQuery(str, str2);
        }
    }

    public void addVuiElement(String str, String str2) {
        LogUtils.logInfo(TAG, "addVuiElement:" + str + ",data:" + str2);
        if (impl != null) {
            VuiElement stringConvertToVuiElement = VuiUtils.stringConvertToVuiElement(str2);
            ArrayList arrayList = new ArrayList();
            arrayList.add(stringConvertToVuiElement);
            impl.setUpdateElements(str, arrayList);
        }
    }

    public void removeVuiElement(String str, String str2) {
        LogUtils.logInfo(TAG, "removeVuiElement:" + str + ",elementId:" + str2);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeVuiElement(str, str2);
        }
    }

    public static void vuiFeedback(String str, String str2) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.vuiFeedback(str, str2);
        }
    }

    private static void initApiRouter() {
        ApiPublisherProvider.addManifestHandler(new IManifestHandler() { // from class: com.xiaopeng.speech.UnitySpeechEngine.1
            @Override // com.xiaopeng.lib.apirouter.server.IManifestHandler
            public IManifestHelper[] getManifestHelpers() {
                return new IManifestHelper[]{new ManifestHelper_VuiEngine()};
            }
        });
    }

    public static boolean registerVuiStateChangeListener(IUnityVuiStateListener iUnityVuiStateListener) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addVuiStateChangeListener(iUnityVuiStateListener);
        }
        return isShowVuiListIndex();
    }

    public static void unregisterVuiStateChangeListener(IUnityVuiStateListener iUnityVuiStateListener) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeVuiStateChangeListener(iUnityVuiStateListener);
        }
    }

    public static boolean isShowVuiListIndex() {
        if (impl == null || VuiUtils.cannotUpload()) {
            return false;
        }
        return impl.isInSpeech();
    }

    public static void onVuiQueryCallBack(String str, String str2, String str3) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.onVuiQueryCallBack(str, str2, str3);
        }
    }
}
