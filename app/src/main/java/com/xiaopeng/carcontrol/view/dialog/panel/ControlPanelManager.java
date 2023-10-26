package com.xiaopeng.carcontrol.view.dialog.panel;

import android.net.Uri;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.direct.IPanelDirectDispatch;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class ControlPanelManager {
    public static final int HOME_SYSTEM_TYPE = 2048;
    public static final int NORMAL_SYSTEM_TYPE = 2008;
    private static final int PANEL_FAST_SHOW_ID = -101;
    public static final int SUPER_SYSTEM_TYPE = 2047;
    private static final String TAG = "ControlPanelManager";
    private final Map<String, AbstractControlPanel> mPanels;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static final ControlPanelManager INSTANCE = new ControlPanelManager();

        private SingleHolder() {
        }
    }

    private ControlPanelManager() {
        this.mPanels = new ConcurrentHashMap();
    }

    public static ControlPanelManager getInstance() {
        return SingleHolder.INSTANCE;
    }

    public void initPanels(final Map<String, Class<? extends AbstractControlPanel>> panelList) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$ControlPanelManager$gbYWQHmT5cDVsUQvUOOhW3JtQ-8
            @Override // java.lang.Runnable
            public final void run() {
                ControlPanelManager.this.lambda$initPanels$1$ControlPanelManager(panelList);
            }
        });
    }

    public /* synthetic */ void lambda$initPanels$1$ControlPanelManager(final Map panelList) {
        LogUtils.d(TAG, "init start");
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            panelList.forEach(new BiConsumer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$ControlPanelManager$iv1DUUcA6y2DiVjGIBCDXgwugZY
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ControlPanelManager.this.lambda$null$0$ControlPanelManager((String) obj, (Class) obj2);
                }
            });
        }
        LogUtils.d(TAG, "init end");
    }

    public /* synthetic */ void lambda$null$0$ControlPanelManager(String key, Class panelClass) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mPanels.put(key, createPanel(panelClass));
    }

    private AbstractControlPanel getPanel(String panelKey) {
        if (TextUtils.isEmpty(panelKey)) {
            LogUtils.e(TAG, "getPanel: panelKey is null");
            return null;
        }
        return this.mPanels.get(panelKey);
    }

    private static AbstractControlPanel createPanel(Class<? extends AbstractControlPanel> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void show(String panelKey, int windowType) {
        show(panelKey, windowType, null, true);
    }

    public void show(String panelKey, int windowType, boolean playTts) {
        show(panelKey, windowType, null, playTts);
    }

    public void show(String panelKey, int windowType, Uri url, boolean playTts) {
        if (ClickHelper.isFastClick((int) PANEL_FAST_SHOW_ID, 200L)) {
            LogUtils.d(TAG, "show panel too fast!!!");
            return;
        }
        AbstractControlPanel panel = getPanel(panelKey);
        if (panel != null) {
            getInstance().dismissAllPanelsExcept(panelKey);
            if (url != null && (panel instanceof IPanelDirectDispatch)) {
                ((IPanelDirectDispatch) panel).dispatchDirectData(url);
            }
            panel.show(windowType, playTts);
            return;
        }
        LogUtils.e(TAG, "Please invoke initPanels(" + panelKey + ") first");
    }

    public void dismiss(String panelKey) {
        AbstractControlPanel panel = getPanel(panelKey);
        if (panel != null) {
            panel.dismiss();
        } else {
            LogUtils.e(TAG, "There is not panel " + panelKey);
        }
    }

    public void dismissAllPanels() {
        dismissAllPanelsExcept(null);
    }

    private void dismissAllPanelsExcept(String panelKey) {
        for (Map.Entry<String, AbstractControlPanel> entry : this.mPanels.entrySet()) {
            String key = entry.getKey();
            if (key != null && !key.equals(panelKey) && entry.getValue() != null) {
                entry.getValue().dismiss();
            }
        }
    }

    public boolean isShow(String panelKey) {
        AbstractControlPanel panel = getPanel(panelKey);
        if (panel != null) {
            return panel.isShow();
        }
        LogUtils.e(TAG, "There is not panel " + panelKey);
        return false;
    }
}
