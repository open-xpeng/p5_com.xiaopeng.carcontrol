package com.xiaopeng.speech.proxy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import com.xiaopeng.speech.coreapi.IAppMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class AppMgrProxy extends IAppMgr.Stub implements ConnectManager.OnConnectCallback {
    private Context mContext;
    private IPCRunner<IAppMgr> mIpcRunner = new IPCRunner<>("AppMgrProxy");
    private List<String> mAppNames = new ArrayList();

    public AppMgrProxy(Context context) {
        this.mContext = context;
        registerApp(context.getPackageName(), getAppName(this.mContext));
    }

    public void setHandler(WorkerHandler workerHandler) {
        this.mIpcRunner.setWorkerHandler(workerHandler);
    }

    @Override // com.xiaopeng.speech.coreapi.IAppMgr
    public void registerApp(final String str, final String str2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAppMgr, Object>() { // from class: com.xiaopeng.speech.proxy.AppMgrProxy.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAppMgr iAppMgr) throws RemoteException {
                if (!AppMgrProxy.this.mAppNames.contains(str2)) {
                    AppMgrProxy.this.mAppNames.add(str2);
                }
                iAppMgr.registerApp(str, str2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAppMgr
    public String getPackageByAppName(final String str) {
        return (String) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAppMgr, String>() { // from class: com.xiaopeng.speech.proxy.AppMgrProxy.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAppMgr iAppMgr) throws RemoteException {
                return iAppMgr.getPackageByAppName(str);
            }
        }, "");
    }

    @Override // com.xiaopeng.speech.coreapi.IAppMgr
    public String[] getAppNameByPackage(final String str) {
        List list = (List) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAppMgr, List<String>>() { // from class: com.xiaopeng.speech.proxy.AppMgrProxy.3
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public List<String> run(IAppMgr iAppMgr) throws RemoteException {
                return Arrays.asList(iAppMgr.getAppNameByPackage(str));
            }
        }, null);
        if (list != null) {
            return (String[]) list.toArray(new String[list.size()]);
        }
        return null;
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        try {
            this.mIpcRunner.setProxy(iSpeechEngine.getAppMgr());
            reRegisterApp();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.mIpcRunner.fetchAll();
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mIpcRunner.setProxy(null);
    }

    private void reRegisterApp() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAppMgr, Object>() { // from class: com.xiaopeng.speech.proxy.AppMgrProxy.4
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAppMgr iAppMgr) throws RemoteException {
                LogUtils.i(this, "reRegisterApp...");
                for (String str : AppMgrProxy.this.mAppNames) {
                    iAppMgr.registerApp(AppMgrProxy.this.mContext.getPackageName(), str);
                }
                return null;
            }
        });
    }

    private String getAppName(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            int i = applicationInfo.labelRes;
            return i == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(i);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
