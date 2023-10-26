package com.xiaopeng.speech;

import android.content.Context;
import androidx.lifecycle.Lifecycle;
import com.xiaopeng.lib.apirouter.server.ApiPublisherProvider;
import com.xiaopeng.lib.apirouter.server.IManifestHandler;
import com.xiaopeng.lib.apirouter.server.IManifestHelper;
import com.xiaopeng.lib.apirouter.server.ManifestHelper_VuiEngine;
import com.xiaopeng.speech.overall.OverallManager;
import com.xiaopeng.speech.overall.listener.IXpOverallListener;
import com.xiaopeng.speech.overall.listener.IXpRecordListener;
import com.xiaopeng.speech.vui.VuiEngineImpl;
import com.xiaopeng.speech.vui.listener.IXpVuiSceneListener;
import com.xiaopeng.speech.vui.model.VuiScene;
import com.xiaopeng.speech.vui.observer.XpVuiElementChangedObserver;
import com.xiaopeng.speech.vui.observer.XpVuiLifecycleObserver;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class XpSpeechEngine {
    private static String TAG = "XpSpeechEngine";
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
        impl.init("ApiRouterSceneService");
        OverallManager.instance().init(context);
    }

    private static void initApiRouter() {
        ApiPublisherProvider.addManifestHandler(new IManifestHandler() { // from class: com.xiaopeng.speech.XpSpeechEngine.1
            @Override // com.xiaopeng.lib.apirouter.server.IManifestHandler
            public IManifestHelper[] getManifestHelpers() {
                return new IManifestHelper[]{new ManifestHelper_VuiEngine()};
            }
        });
    }

    public static void init(Context context, IXpOverallListener iXpOverallListener) {
        if (ApiPublisherProvider.CONTEXT == null) {
            ApiPublisherProvider.CONTEXT = context.getApplicationContext();
        }
        if (impl == null) {
            impl = new VuiEngineImpl(context, false);
        }
        impl.init("");
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

    public static void registerDupVuiSceneListener(String str, IXpVuiSceneListener iXpVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addDupVuiSceneListener(str, null, iXpVuiSceneListener, null, true);
        }
        LogUtils.logDebug(TAG, "registerVuiSceneListener:" + str + ",listener:" + iXpVuiSceneListener);
        iXpVuiSceneListener.onInitCompleted(new XpVuiElementChangedObserver(iXpVuiSceneListener));
    }

    public static void unregisterDupVuiSceneListener(String str, IXpVuiSceneListener iXpVuiSceneListener) {
        LogUtils.logDebug(TAG, "unregisterVuiSceneListener:" + str + ",listener:" + iXpVuiSceneListener);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeDupVuiSceneListener(str, iXpVuiSceneListener, false);
        }
    }

    public static void registerVuiSceneListener(String str, IXpVuiSceneListener iXpVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addVuiSceneListener(str, null, iXpVuiSceneListener, null, true);
        }
        LogUtils.logDebug(TAG, "registerVuiSceneListener:" + str + ",listener:" + iXpVuiSceneListener);
        iXpVuiSceneListener.onInitCompleted(new XpVuiElementChangedObserver());
    }

    public static void unregisterVuiSceneListener(String str, IXpVuiSceneListener iXpVuiSceneListener) {
        LogUtils.logDebug(TAG, "unregisterVuiSceneListener:" + str + ",listener:" + iXpVuiSceneListener);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeVuiSceneListener(str, iXpVuiSceneListener, false);
        }
    }

    public static void setBuildElement(String str, VuiElement vuiElement) {
        if (impl != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(vuiElement);
            impl.setBuildElements(str, arrayList);
        }
    }

    public static void setBuildElement(String str, List<VuiElement> list) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setBuildElements(str, list);
        }
    }

    public static void setUpdateElement(String str, VuiElement vuiElement) {
        if (impl != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(vuiElement);
            impl.setUpdateElements(str, arrayList);
        }
    }

    public static void setUpdateElement(String str, List<VuiElement> list) {
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setUpdateElements(str, list);
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

    public static void enterScene(String str) {
        LogUtils.logDebug(TAG, "enterScene:" + str);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.enterScene(str, true);
        }
    }

    public static void enterScene(String str, int i) {
        LogUtils.logDebug(TAG, "enterScene:" + str);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.enterScene(str, VuiUtils.getDisplayLocation(i), true);
        }
    }

    public static void enterDupScene(String str, IXpVuiSceneListener iXpVuiSceneListener) {
        LogUtils.logDebug(TAG, "enterScene:" + str);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.enterDupScene(str, true, iXpVuiSceneListener);
        }
    }

    public static void exitScene(String str) {
        LogUtils.logDebug(TAG, "exitScene:" + str);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.exitScene(str, true);
        }
    }

    public static void exitScene(String str, int i) {
        LogUtils.logDebug(TAG, "enterScene:" + str);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.exitScene(str, VuiUtils.getDisplayLocation(i), true, null);
        }
    }

    public static void exitDupScene(String str, IXpVuiSceneListener iXpVuiSceneListener) {
        LogUtils.logDebug(TAG, "exitScene:" + str);
        VuiEngineImpl vuiEngineImpl = impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.exitDupScene(str, true, iXpVuiSceneListener);
        }
    }

    public static void initScene(Lifecycle lifecycle, String str, IXpVuiSceneListener iXpVuiSceneListener) {
        lifecycle.addObserver(new XpVuiLifecycleObserver(str, iXpVuiSceneListener, lifecycle));
        iXpVuiSceneListener.onInitCompleted(new XpVuiElementChangedObserver());
    }

    public static void dispatchOverallEvent(String str, String str2) {
        OverallManager.instance().dispatchOverallEvent(str, str2);
    }

    public static void dispatchOverallQuery(String str, String str2, String str3) {
        OverallManager.instance().dispatchOverallQuery(str, str2, str3);
    }

    public static boolean isSupportRecord() {
        return OverallManager.instance().isSupportRecord();
    }

    public static void initRecord(Context context, String str, IXpRecordListener iXpRecordListener) {
        OverallManager.instance().initRecord(context, str, iXpRecordListener);
    }

    public static void initRecord(Context context, IXpRecordListener iXpRecordListener) {
        OverallManager.instance().initRecord(context, null, iXpRecordListener);
    }

    public static void startRecord(String str) {
        OverallManager.instance().startRecord(str);
    }

    public static void startRecord() {
        OverallManager.instance().startRecord(null);
    }

    public static void stopRecord() {
        OverallManager.instance().stopRecord();
    }

    public static void destroyRecord(IXpRecordListener iXpRecordListener) {
        OverallManager.instance().destroyRecord(iXpRecordListener);
    }

    public static void speak(String str) {
        OverallManager.instance().speak(str);
    }
}
