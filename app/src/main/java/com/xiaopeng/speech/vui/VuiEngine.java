package com.xiaopeng.speech.vui;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.speech.vui.constants.Foo;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.listener.IVuiEventListener;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.speech.vui.model.VuiScene;
import com.xiaopeng.speech.vui.observer.VuiLifecycleObserver;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.IVuiEngine;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.VuiPriority;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class VuiEngine implements IVuiEngine {
    private static volatile VuiEngine instance;
    private VuiEngineImpl impl;

    private VuiEngine(Context context) {
        this.impl = null;
        if (VuiUtils.canUseVuiFeature() && !VuiConstants.UNITY.equals(context.getApplicationInfo().packageName)) {
            this.impl = new VuiEngineImpl(context, true);
        }
    }

    public static VuiEngine getInstance(Context context) {
        if (instance == null) {
            synchronized (VuiEngine.class) {
                if (instance == null) {
                    instance = new VuiEngine(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void enterScene(String str, int i) {
        enterScene(str, i, VuiUtils.getSourceDisplayLocation());
    }

    public void enterScene(String str, int i, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            if (i == 0) {
                vuiEngineImpl.enterScene(str, VuiUtils.getDisplayLocation(str2), true);
            } else if (i == 2) {
                vuiEngineImpl.enterScene(str, VuiUtils.getDisplayLocation(str2), false);
            }
        }
    }

    public void exitScene(String str, int i) {
        exitScene(str, i, VuiUtils.getSourceDisplayLocation());
    }

    public void exitScene(String str, int i, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            if (i == 0) {
                vuiEngineImpl.exitScene(str, VuiUtils.getDisplayLocation(str2), true, null);
            } else if (i == 2) {
                vuiEngineImpl.exitScene(str, VuiUtils.getDisplayLocation(str2), false, null);
            }
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void enterScene(String str) {
        enterScene(str, VuiUtils.getSourceDisplayLocation());
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void enterScene(String str, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.enterScene(str, VuiUtils.getDisplayLocation(str2), true);
        }
    }

    public void enterScene(String str, boolean z) {
        enterScene(str, z, VuiUtils.getSourceDisplayLocation());
    }

    public void enterScene(String str, boolean z, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.enterScene(str, VuiUtils.getDisplayLocation(str2), z);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void exitScene(String str) {
        exitScene(str, VuiUtils.getSourceDisplayLocation());
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void exitScene(String str, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.exitScene(str, VuiUtils.getDisplayLocation(str2), true, null);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void exitScene(String str, String str2, IVuiSceneListener iVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.exitScene(str, VuiUtils.getDisplayLocation(str2), true, iVuiSceneListener);
        }
    }

    public void exitScene(String str, boolean z) {
        exitScene(str, z, VuiUtils.getSourceDisplayLocation());
    }

    public void exitScene(String str, boolean z, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.exitScene(str, VuiUtils.getDisplayLocation(str2), z, null);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void buildScene(String str, View view) {
        buildScene(str, view, VuiUtils.getSourceDisplayLocation());
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void buildScene(String str, View view, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, view, (List<Integer>) null, (IVuiElementListener) null, (List<String>) null, true, (ISceneCallbackHandler) null, VuiUtils.getDisplayLocation(str2), (IVuiSceneListener) null);
        }
    }

    public void buildScene(String str, View view, boolean z) {
        buildScene(str, view, z, VuiUtils.getSourceDisplayLocation());
    }

    public void buildScene(String str, View view, boolean z, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, view, (List<Integer>) null, (IVuiElementListener) null, (List<String>) null, z, (ISceneCallbackHandler) null, VuiUtils.getDisplayLocation(str2), (IVuiSceneListener) null);
        }
    }

    public void buildScene(String str, View view, ISceneCallbackHandler iSceneCallbackHandler) {
        buildScene(str, view, iSceneCallbackHandler, VuiUtils.getSourceDisplayLocation());
    }

    public void buildScene(String str, View view, ISceneCallbackHandler iSceneCallbackHandler, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, view, (List<Integer>) null, (IVuiElementListener) null, (List<String>) null, true, iSceneCallbackHandler, VuiUtils.getDisplayLocation(str2), (IVuiSceneListener) null);
        }
    }

    public void buildScene(String str, View view, List<String> list, boolean z) {
        buildScene(str, view, list, z, VuiUtils.getSourceDisplayLocation());
    }

    public void buildScene(String str, View view, List<String> list, boolean z, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, view, (List<Integer>) null, (IVuiElementListener) null, list, z, (ISceneCallbackHandler) null, VuiUtils.getDisplayLocation(str2), (IVuiSceneListener) null);
        }
    }

    public void buildScene(String str, View view, List<String> list, ISceneCallbackHandler iSceneCallbackHandler) {
        buildScene(str, view, list, iSceneCallbackHandler, VuiUtils.getSourceDisplayLocation());
    }

    public void buildScene(String str, View view, List<String> list, ISceneCallbackHandler iSceneCallbackHandler, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, view, (List<Integer>) null, (IVuiElementListener) null, list, true, iSceneCallbackHandler, VuiUtils.getDisplayLocation(str2), (IVuiSceneListener) null);
        }
    }

    public void buildScene(String str, View view, List<Integer> list, IVuiElementListener iVuiElementListener, List<String> list2, boolean z, ISceneCallbackHandler iSceneCallbackHandler) {
        buildScene(str, view, list, iVuiElementListener, list2, z, iSceneCallbackHandler, VuiUtils.getSourceDisplayLocation());
    }

    public void buildScene(String str, View view, List<Integer> list, IVuiElementListener iVuiElementListener, List<String> list2, boolean z, ISceneCallbackHandler iSceneCallbackHandler, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, view, list, (IVuiElementListener) null, list2, z, iSceneCallbackHandler, VuiUtils.getDisplayLocation(str2), (IVuiSceneListener) null);
        }
    }

    public void buildScene(String str, List<View> list, List<Integer> list2, IVuiElementListener iVuiElementListener, List<String> list3, boolean z, ISceneCallbackHandler iSceneCallbackHandler) {
        buildScene(str, list, list2, iVuiElementListener, list3, z, iSceneCallbackHandler, VuiUtils.getSourceDisplayLocation());
    }

    public void buildScene(String str, List<View> list, List<Integer> list2, IVuiElementListener iVuiElementListener, List<String> list3, boolean z, ISceneCallbackHandler iSceneCallbackHandler, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, list, list2, iVuiElementListener, list3, z, iSceneCallbackHandler, VuiUtils.getDisplayLocation(str2), (IVuiSceneListener) null);
        }
    }

    public void buildScene(String str, List<View> list, List<String> list2, boolean z) {
        buildScene(str, list, list2, z, VuiUtils.getSourceDisplayLocation());
    }

    public void buildScene(String str, List<View> list, List<String> list2, boolean z, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, list, (List<Integer>) null, (IVuiElementListener) null, list2, z, (ISceneCallbackHandler) null, VuiUtils.getDisplayLocation(str2), (IVuiSceneListener) null);
        }
    }

    public void buildScene(String str, List<View> list, boolean z) {
        buildScene(str, list, z, VuiUtils.getSourceDisplayLocation());
    }

    public void buildScene(String str, List<View> list, boolean z, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, list, (List<Integer>) null, (IVuiElementListener) null, (List<String>) null, z, (ISceneCallbackHandler) null, VuiUtils.getDisplayLocation(str2), (IVuiSceneListener) null);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void buildScene(String str, View view, List<Integer> list, IVuiElementListener iVuiElementListener) {
        buildScene(str, view, list, iVuiElementListener, VuiUtils.getSourceDisplayLocation());
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void buildScene(String str, View view, List<Integer> list, IVuiElementListener iVuiElementListener, String str2) {
        buildScene(str, view, list, iVuiElementListener, (List<String>) null, true, (ISceneCallbackHandler) null, str2);
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void buildScene(String str, View view, List<Integer> list, IVuiElementListener iVuiElementListener, String str2, IVuiSceneListener iVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, view, list, iVuiElementListener, (List<String>) null, true, (ISceneCallbackHandler) null, VuiUtils.getDisplayLocation(str2), iVuiSceneListener);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void buildScene(String str, List<View> list) {
        buildScene(str, list, VuiUtils.getSourceDisplayLocation());
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void buildScene(String str, List<View> list, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, list, (List<Integer>) null, (IVuiElementListener) null, (List<String>) null, true, (ISceneCallbackHandler) null, VuiUtils.getDisplayLocation(str2), (IVuiSceneListener) null);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void buildScene(String str, List<View> list, List<Integer> list2, IVuiElementListener iVuiElementListener) {
        buildScene(str, list, list2, iVuiElementListener, VuiUtils.getSourceDisplayLocation());
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void buildScene(String str, List<View> list, List<Integer> list2, IVuiElementListener iVuiElementListener, String str2) {
        buildScene(str, list, list2, iVuiElementListener, str2, (IVuiSceneListener) null);
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void buildScene(String str, List<View> list, List<Integer> list2, IVuiElementListener iVuiElementListener, String str2, IVuiSceneListener iVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.buildScene(str, list, list2, iVuiElementListener, (List<String>) null, true, (ISceneCallbackHandler) null, VuiUtils.getDisplayLocation(str2), iVuiSceneListener);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void updateScene(String str, List<View> list) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.updateScene(str, list);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void updateElementAttribute(String str, View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.updateElementAttribute(str, Arrays.asList(view));
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void updateDisplayLocation(String str, int i) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.updateDisplayLocation(str, VuiUtils.getDisplayLocation(i));
        }
    }

    public void updateElementAttribute(String str, List<View> list) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.updateElementAttribute(str, list);
        }
    }

    public void updateRecyclerViewItemView(String str, View view, RecyclerView recyclerView) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.updateRecyclerViewItemView(str, Arrays.asList(view), recyclerView);
        }
    }

    public void updateRecyclerViewItemView(String str, List<View> list, RecyclerView recyclerView) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.updateRecyclerViewItemView(str, list, recyclerView);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void updateScene(String str, View view) {
        if (this.impl != null) {
            if ((view instanceof IVuiElement) && ((IVuiElement) view).getVuiElementChangedListener() != null) {
                this.impl.updateScene(str, view);
            } else {
                this.impl.updateScene(str, Arrays.asList(view));
            }
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void updateScene(String str, List<View> list, List<Integer> list2, IVuiElementListener iVuiElementListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.updateScene(str, list, list2, iVuiElementListener);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void updateScene(String str, View view, List<Integer> list, IVuiElementListener iVuiElementListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.updateScene(str, view, list, iVuiElementListener);
        }
    }

    public void handleNewRootviewToScene(String str, List<View> list) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.handleNewRootviewToScene(str, list, VuiPriority.LEVEL2);
        }
    }

    public void handleNewRootviewToScene(String str, View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.handleNewRootviewToScene(str, Arrays.asList(view), VuiPriority.LEVEL2);
        }
    }

    public void handleNewRootviewToScene(String str, View view, VuiPriority vuiPriority) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.handleNewRootviewToScene(str, Arrays.asList(view), vuiPriority);
        }
    }

    public void handleNewRootviewToScene(String str, List<View> list, VuiPriority vuiPriority) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.handleNewRootviewToScene(str, list, vuiPriority);
        }
    }

    public void removeOtherRootViewFromScene(String str, View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeOtherRootViewFromScene(str, Arrays.asList(view));
        }
    }

    public void removeOtherRootViewFromScene(String str, List<View> list) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeOtherRootViewFromScene(str, list);
        }
    }

    public void removeOtherRootViewFromScene(String str) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeOtherRootViewFromScene(str);
        }
    }

    public void addSceneElementGroup(View view, String str, VuiPriority vuiPriority, IVuiSceneListener iVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addSceneElementGroup(view, str, vuiPriority, iVuiSceneListener);
        }
    }

    public void addSceneElement(View view, String str, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addSceneElement(view, str, str2);
        }
    }

    public void removeSceneElementGroup(String str, String str2, IVuiSceneListener iVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeSceneElementGroup(str, str2, iVuiSceneListener);
        }
    }

    public void dispatchVuiEvent(String str, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.dispatchVuiEvent(str, str2);
        }
    }

    public String getElementState(String str, String str2) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.getElementState(str, str2);
        }
        return null;
    }

    public void vuiFeedback(View view, VuiFeedback vuiFeedback) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.vuiFeedback(view, vuiFeedback);
        }
    }

    public void subscribe(String str) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.subscribe(str);
        }
    }

    public void subscribeVuiFeature() {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.subscribeVuiFeature();
        }
    }

    public void unSubscribeVuiFeature() {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.unSubscribeVuiFeature();
        }
    }

    public void unSubscribe() {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.unSubscribe();
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void addVuiSceneListener(String str, View view, IVuiSceneListener iVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addVuiSceneListener(str, view, iVuiSceneListener, null, true);
        }
    }

    public void addVuiSceneListener(String str, View view, IVuiSceneListener iVuiSceneListener, boolean z) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addVuiSceneListener(str, view, iVuiSceneListener, null, z);
        }
    }

    public void addVuiSceneListener(String str, View view, IVuiSceneListener iVuiSceneListener, IVuiElementChangedListener iVuiElementChangedListener, boolean z) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addVuiSceneListener(str, view, iVuiSceneListener, iVuiElementChangedListener, z);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void addVuiSceneListener(String str, View view, IVuiSceneListener iVuiSceneListener, IVuiElementChangedListener iVuiElementChangedListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addVuiSceneListener(str, view, iVuiSceneListener, iVuiElementChangedListener, true);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void removeVuiSceneListener(String str) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeVuiSceneListener(str, null, false);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiEngine
    public void removeVuiSceneListener(String str, IVuiSceneListener iVuiSceneListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeVuiSceneListener(str, iVuiSceneListener, false);
        }
    }

    public void removeVuiSceneListener(String str, boolean z) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeVuiSceneListener(str, null, z);
        }
    }

    public void removeVuiSceneListener(String str, IVuiSceneListener iVuiSceneListener, boolean z) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.removeVuiSceneListener(str, iVuiSceneListener, z);
        }
    }

    public void setVuiElementTag(View view, String str) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setVuiElementTag(view, str);
        }
    }

    public String getVuiElementTag(View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.getVuiElementTag(view);
        }
        return null;
    }

    public void setVuiElementUnSupportTag(View view, boolean z) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setVuiElementUnSupportTag(view, z);
        }
    }

    public void setVuiElementUnStandardSwitch(View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setVuiElementDefaultAction(view, VuiAction.SETCHECK.getName(), true);
        }
    }

    public void setVuiElementDefaultAction(View view, String str, Object obj) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setVuiElementDefaultAction(view, str, obj);
        }
    }

    public void setVuiCustomDisableControlTag(View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setVuiCustomDisableControlTag(view);
        }
    }

    public void setVuiCustomDisableFeedbackTag(View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setVuiCustomDisableFeedbackTag(view);
        }
    }

    public void setVuiStatfulButtonClick(View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setVuiStatefulButtonClick(view);
        }
    }

    public void disableChildVuiAttrWhenInvisible(View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.disableChildVuiAttrWhenInvisible(view);
        }
    }

    public void setVuiLabelUnSupportText(View... viewArr) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setVuiLabelUnSupportText(viewArr);
        }
    }

    public void setVuiElementVisibleTag(View view, boolean z) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setVuiElementVisibleTag(view, z);
        }
    }

    public Boolean getVuiElementVisibleTag(View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.getVuiElementVisibleTag(view);
        }
        return null;
    }

    public void disableVuiFeature() {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.disableVuiFeature();
        }
    }

    public void enableVuiFeature() {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.enableVuiFeature();
        }
    }

    public boolean isVuiFeatureDisabled() {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.isVuiFeatureDisabled();
        }
        return true;
    }

    public boolean isInSpeech() {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.isInSpeech();
        }
        return false;
    }

    public String getVuiElementId(String str, int i, String str2) {
        if (str != null) {
            str2 = str + "_" + str2;
        }
        return i != -1 ? str2 + "_" + i : str2;
    }

    public VuiScene createVuiScene(String str, long j) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.createVuiScene(str, j);
        }
        return null;
    }

    public void setLoglevel(int i) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setLoglevel(i);
        }
    }

    public void addVuiEventListener(String str, IVuiEventListener iVuiEventListener) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.addVuiEventListener(str, iVuiEventListener);
        }
    }

    public void disableViewVuiMode() {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.disableViewVuiMode();
        }
    }

    public void setExecuteVirtualTag(View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setExecuteVirtualTag(view, null);
        }
    }

    public void setExecuteVirtualTag(View view, String str) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setExecuteVirtualTag(view, str);
        }
    }

    public void setVirtualResourceNameTag(View view, String str) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setVirtualResourceNameTag(view, str);
        }
    }

    public void setCustomDoActionTag(View view) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setCustomDoActionTag(view);
        }
    }

    public void setProcessName(String str) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setProcessName(str);
        }
    }

    public void initScene(Lifecycle lifecycle, String str, View view, IVuiSceneListener iVuiSceneListener) {
        initScene(lifecycle, str, view, iVuiSceneListener, null, true, false);
    }

    public void initScene(Lifecycle lifecycle, String str, View view, IVuiSceneListener iVuiSceneListener, IVuiElementChangedListener iVuiElementChangedListener) {
        initScene(lifecycle, str, view, iVuiSceneListener, iVuiElementChangedListener, true, false);
    }

    public void initScene(Lifecycle lifecycle, String str, View view, IVuiSceneListener iVuiSceneListener, IVuiElementChangedListener iVuiElementChangedListener, boolean z, boolean z2) {
        lifecycle.addObserver(new VuiLifecycleObserver(Foo.getContext(), lifecycle, str, view, iVuiSceneListener, iVuiElementChangedListener, z, z2));
    }

    public boolean isSpeechShowNumber() {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            return vuiEngineImpl.isSpeechShowNumber();
        }
        return false;
    }

    public String getActiveSceneId() {
        Map<String, String> activeSceneId;
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl == null || (activeSceneId = vuiEngineImpl.getActiveSceneId()) == null) {
            return null;
        }
        return activeSceneId.get(VuiConstants.SCREEN_DISPLAY_LF);
    }

    public String getRFActiveSceneId() {
        Map<String, String> activeSceneId;
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl == null || (activeSceneId = vuiEngineImpl.getActiveSceneId()) == null) {
            return null;
        }
        return activeSceneId.get(VuiConstants.SCREEN_DISPLAY_RF);
    }

    public void setHasFeedBackTxtByViewDisable(View view, String str) {
        VuiEngineImpl vuiEngineImpl = this.impl;
        if (vuiEngineImpl != null) {
            vuiEngineImpl.setHasFeedBackTxtByViewDisable(view, str);
        }
    }
}
