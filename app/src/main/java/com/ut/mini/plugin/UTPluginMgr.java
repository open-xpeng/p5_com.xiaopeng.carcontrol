package com.ut.mini.plugin;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.alibaba.mtl.log.b;
import com.alibaba.mtl.log.e.i;
import com.ut.mini.core.appstatus.UTMCAppStatusCallbacks;
import com.ut.mini.core.appstatus.UTMCAppStatusRegHelper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class UTPluginMgr implements UTMCAppStatusCallbacks {
    public static final String PARTNERPLUGIN_UTPREF = "com.ut.mini.perf.UTPerfPlugin";
    private static UTPluginMgr a = new UTPluginMgr();
    private HandlerThread b = null;
    private Handler mHandler = null;
    private List<UTPlugin> n = new LinkedList();
    private List<String> o = new ArrayList();
    private List<String> p = new ArrayList<String>() { // from class: com.ut.mini.plugin.UTPluginMgr.1
        {
            add(UTPluginMgr.PARTNERPLUGIN_UTPREF);
        }
    };
    private List<UTPlugin> q = new LinkedList();

    @Override // com.ut.mini.core.appstatus.UTMCAppStatusCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // com.ut.mini.core.appstatus.UTMCAppStatusCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // com.ut.mini.core.appstatus.UTMCAppStatusCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // com.ut.mini.core.appstatus.UTMCAppStatusCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // com.ut.mini.core.appstatus.UTMCAppStatusCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // com.ut.mini.core.appstatus.UTMCAppStatusCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // com.ut.mini.core.appstatus.UTMCAppStatusCallbacks
    public void onActivityStopped(Activity activity) {
    }

    private UTPluginMgr() {
        if (Build.VERSION.SDK_INT >= 14) {
            UTMCAppStatusRegHelper.registerAppStatusCallbacks(this);
        }
    }

    public static UTPluginMgr getInstance() {
        return a;
    }

    private void N() {
        HandlerThread handlerThread = new HandlerThread("UT-PLUGIN-ASYNC");
        this.b = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.b.getLooper()) { // from class: com.ut.mini.plugin.UTPluginMgr.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1 && (message.obj instanceof a)) {
                    a aVar = (a) message.obj;
                    UTPlugin a2 = aVar.a();
                    int i = aVar.i();
                    Object msgObj = aVar.getMsgObj();
                    if (a2 != null) {
                        try {
                            if (msgObj instanceof UTPluginMsgDispatchDelegate) {
                                UTPluginMsgDispatchDelegate uTPluginMsgDispatchDelegate = (UTPluginMsgDispatchDelegate) msgObj;
                                if (uTPluginMsgDispatchDelegate.isMatchPlugin(a2)) {
                                    a2.onPluginMsgArrivedFromSDK(i, uTPluginMsgDispatchDelegate.getDispatchObject(a2));
                                }
                            } else {
                                a2.onPluginMsgArrivedFromSDK(i, msgObj);
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    public boolean isPartnerPluginExist(String str) {
        return this.o.contains(str);
    }

    private synchronized void a(int i, UTPluginContextValueDispatchDelegate uTPluginContextValueDispatchDelegate) {
        if (uTPluginContextValueDispatchDelegate == null) {
            return;
        }
        for (UTPlugin uTPlugin : this.q) {
            uTPluginContextValueDispatchDelegate.onPluginContextValueChange(uTPlugin.getPluginContext());
            uTPlugin.onPluginContextValueUpdate(i);
        }
    }

    public void updatePluginContextValue(int i) {
        if (i != 1) {
            return;
        }
        a(i, new UTPluginContextValueDispatchDelegate() { // from class: com.ut.mini.plugin.UTPluginMgr.3
            @Override // com.ut.mini.plugin.UTPluginContextValueDispatchDelegate
            public void onPluginContextValueChange(UTPluginContext uTPluginContext) {
                uTPluginContext.setDebugLogFlag(i.l());
            }
        });
    }

    public void runPartnerPlugin() {
        List<String> list = this.p;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (String str : this.p) {
            if (!TextUtils.isEmpty(str)) {
                try {
                    Object newInstance = Class.forName(str).newInstance();
                    if (newInstance instanceof UTPlugin) {
                        registerPlugin((UTPlugin) newInstance, true);
                        i.a("runPartnerPlugin[OK]:" + str, new String[0]);
                        this.o.add(str);
                    }
                } catch (ClassNotFoundException unused) {
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private UTPluginContext a() {
        UTPluginContext uTPluginContext = new UTPluginContext();
        uTPluginContext.setContext(b.a().getContext());
        if (i.l()) {
            uTPluginContext.setDebugLogFlag(i.l());
        }
        return uTPluginContext;
    }

    public synchronized void registerPlugin(UTPlugin uTPlugin, boolean z) {
        if (uTPlugin != null) {
            if (!this.q.contains(uTPlugin)) {
                uTPlugin.a(a());
                this.q.add(uTPlugin);
                if (!z) {
                    this.n.add(uTPlugin);
                }
                uTPlugin.onRegistered();
            }
        }
    }

    public synchronized void unregisterPlugin(UTPlugin uTPlugin) {
        if (uTPlugin != null) {
            if (this.q.contains(uTPlugin)) {
                this.q.remove(uTPlugin);
                uTPlugin.onUnRegistered();
                uTPlugin.a(null);
            }
        }
        List<UTPlugin> list = this.n;
        if (list != null && list.contains(uTPlugin)) {
            this.n.remove(uTPlugin);
        }
    }

    private boolean a(int i, int[] iArr) {
        if (iArr != null) {
            boolean z = false;
            for (int i2 : iArr) {
                if (i2 == i) {
                    z = true;
                }
            }
            return z;
        }
        return false;
    }

    public synchronized boolean dispatchPluginMsg(int i, Object obj) {
        boolean z;
        List<UTPlugin> list;
        if (this.mHandler == null) {
            N();
        }
        z = false;
        if (this.q.size() > 0) {
            for (UTPlugin uTPlugin : this.q) {
                int[] returnRequiredMsgIds = uTPlugin.returnRequiredMsgIds();
                if (returnRequiredMsgIds != null && a(i, returnRequiredMsgIds)) {
                    if (i != 1 && ((list = this.n) == null || !list.contains(uTPlugin))) {
                        a aVar = new a();
                        aVar.g(i);
                        aVar.c(obj);
                        aVar.a(uTPlugin);
                        Message obtain = Message.obtain();
                        obtain.what = 1;
                        obtain.obj = aVar;
                        this.mHandler.sendMessage(obtain);
                        z = true;
                    }
                    if (obj instanceof UTPluginMsgDispatchDelegate) {
                        UTPluginMsgDispatchDelegate uTPluginMsgDispatchDelegate = (UTPluginMsgDispatchDelegate) obj;
                        if (uTPluginMsgDispatchDelegate.isMatchPlugin(uTPlugin)) {
                            uTPlugin.onPluginMsgArrivedFromSDK(i, uTPluginMsgDispatchDelegate.getDispatchObject(uTPlugin));
                        }
                    } else {
                        uTPlugin.onPluginMsgArrivedFromSDK(i, obj);
                    }
                    z = true;
                }
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        private int K;
        private UTPlugin a;
        private Object f;

        private a() {
            this.K = 0;
            this.f = null;
            this.a = null;
        }

        public int i() {
            return this.K;
        }

        public void g(int i) {
            this.K = i;
        }

        public Object getMsgObj() {
            return this.f;
        }

        public void c(Object obj) {
            this.f = obj;
        }

        public UTPlugin a() {
            return this.a;
        }

        public void a(UTPlugin uTPlugin) {
            this.a = uTPlugin;
        }
    }

    @Override // com.ut.mini.core.appstatus.UTMCAppStatusCallbacks
    public void onSwitchBackground() {
        dispatchPluginMsg(2, null);
    }

    @Override // com.ut.mini.core.appstatus.UTMCAppStatusCallbacks
    public void onSwitchForeground() {
        dispatchPluginMsg(8, null);
    }
}
