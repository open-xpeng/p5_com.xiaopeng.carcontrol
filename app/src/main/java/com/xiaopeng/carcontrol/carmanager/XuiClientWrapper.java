package com.xiaopeng.carcontrol.carmanager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.appstore.storeprovider.store.RMDownloadListener;
import com.xiaopeng.appstore.storeprovider.store.StoreResourceProvider;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceDownloadInfo;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.ambient.AmbientManagerCompat;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lludancemanager.Constant;
import com.xiaopeng.lludancemanager.bean.LluDanceViewData;
import com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel;
import com.xiaopeng.lludancemanager.viewmodel.LluDanceViewModel;
import com.xiaopeng.xuimanager.XUIManager;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.contextinfo.ContextInfoManager;
import com.xiaopeng.xuimanager.lightlanuage.LightLanuageManager;
import com.xiaopeng.xuimanager.mediacenter.MediaCenterManager;
import com.xiaopeng.xuimanager.seatmassager.SeatMassagerManager;
import com.xiaopeng.xuimanager.smart.SmartManager;
import com.xiaopeng.xuimanager.soundresource.SoundResourceManager;
import com.xiaopeng.xuimanager.userscenario.UserScenarioManager;
import com.xiaopeng.xuimanager.xapp.XAppManager;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes.dex */
public class XuiClientWrapper {
    private static final String TAG = "XuiClientWrapper";
    private AmbientManagerCompat mAtlManager;
    private ContextInfoManager mCtiManager;
    private LightLanuageManager mLightLanguageManager;
    private RMDownloadListener mListener;
    private MediaCenterManager mMediaCenterManager;
    private SeatMassagerManager mSeatMassagerManager;
    private SmartManager mSmartManager;
    private SoundResourceManager mSoundResManager;
    private XAppManager mXAppManager;
    private UserScenarioManager mXUserScenarioManager;
    private XUIManager mXuiManager;
    private final Object mXuiClientReady = new Object();
    private boolean mIsXuiSvcConnected = false;
    private StoreResourceProvider mResourceManager = new StoreResourceProvider(App.getInstance());
    private final Executor mResourceServiceApiCallExecutor = Executors.newFixedThreadPool(4);
    private final Executor mResourceServiceConnectThread = Executors.newSingleThreadExecutor();
    private final ServiceConnection mXuiConnectionCb = new ServiceConnection() { // from class: com.xiaopeng.carcontrol.carmanager.XuiClientWrapper.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i(XuiClientWrapper.TAG, "onXuiServiceConnected", false);
            XuiClientWrapper.this.initXuiManagers();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i(XuiClientWrapper.TAG, "onXuiServiceDisconnected", false);
            XuiClientWrapper.this.mIsXuiSvcConnected = false;
            XuiClientWrapper.this.releaseResourceManager();
        }
    };

    public Executor getResourceServiceApiCallExecutor() {
        return this.mResourceServiceApiCallExecutor;
    }

    public static XuiClientWrapper getInstance() {
        return SingleHolder.sInstance;
    }

    public void connectToXui() {
        if (this.mIsXuiSvcConnected) {
            return;
        }
        XUIManager createXUIManager = XUIManager.createXUIManager(App.getInstance(), this.mXuiConnectionCb, ThreadUtils.getHandler(0));
        this.mXuiManager = createXUIManager;
        createXUIManager.connect();
        LogUtils.i(TAG, "Start to connect XUI service", false);
    }

    public void disconnect() {
        XUIManager xUIManager;
        if (!this.mIsXuiSvcConnected || (xUIManager = this.mXuiManager) == null) {
            return;
        }
        xUIManager.disconnect();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isXuiServiceConnected() {
        synchronized (this.mXuiClientReady) {
            while (!this.mIsXuiSvcConnected) {
                try {
                    LogUtils.i(TAG, "Waiting XUI service connected", false);
                    this.mXuiClientReady.wait();
                } catch (InterruptedException e) {
                    LogUtils.e(TAG, (String) null, e);
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initXuiManagers() {
        try {
            AmbientManagerCompat ambientManagerCompat = new AmbientManagerCompat(this.mXuiManager);
            if (!ambientManagerCompat.isSupportAtlApiControl()) {
                ambientManagerCompat = null;
            }
            this.mAtlManager = ambientManagerCompat;
            this.mSmartManager = (SmartManager) this.mXuiManager.getXUIServiceManager("smart");
            this.mCtiManager = (ContextInfoManager) this.mXuiManager.getXUIServiceManager("contextinfo");
            this.mXAppManager = (XAppManager) this.mXuiManager.getXUIServiceManager("xapp");
            this.mXUserScenarioManager = (UserScenarioManager) this.mXuiManager.getXUIServiceManager("userscenario");
            this.mMediaCenterManager = (MediaCenterManager) this.mXuiManager.getXUIServiceManager("mediacenter");
            this.mLightLanguageManager = (LightLanuageManager) this.mXuiManager.getXUIServiceManager("lightlanuage");
            this.mSoundResManager = (SoundResourceManager) this.mXuiManager.getXUIServiceManager("sndresource");
            if (CarBaseConfig.getInstance().isSupportSeatMassage() || CarBaseConfig.getInstance().isSupportSeatRhythm()) {
                this.mSeatMassagerManager = (SeatMassagerManager) this.mXuiManager.getXUIServiceManager("seatmassager");
            }
            connectToResourceManager();
            LogUtils.i(TAG, "initXuiManagers end, notify CarClientWrapper", false);
            synchronized (this.mXuiClientReady) {
                this.mIsXuiSvcConnected = true;
                this.mXuiClientReady.notifyAll();
            }
            if (CarClientWrapper.getInstance().isCarServiceConnectedSync()) {
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$XuiClientWrapper$NYKi0CiZYdSFuK33PvSjx9CqQ9s
                    @Override // java.lang.Runnable
                    public final void run() {
                        XuiClientWrapper.this.initControllerXuiManager();
                    }
                });
            }
        } catch (XUIServiceNotConnectedException | IllegalStateException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
    }

    public void connectToResourceManager() {
        if (CarBaseConfig.getInstance().isSupportLluDance() && App.isMainProcess()) {
            this.mResourceServiceConnectThread.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$XuiClientWrapper$kRZDALVjikdJ3fhwJJqx6dxRQAE
                @Override // java.lang.Runnable
                public final void run() {
                    XuiClientWrapper.this.lambda$connectToResourceManager$0$XuiClientWrapper();
                }
            });
        }
    }

    public /* synthetic */ void lambda$connectToResourceManager$0$XuiClientWrapper() {
        if (isXuiServiceConnected()) {
            LogUtils.i(TAG, "connectToResourceManager, isXuiServiceConnected = true");
        }
        StoreResourceProvider storeResourceProvider = this.mResourceManager;
        if (storeResourceProvider == null) {
            LogUtils.i(TAG, "mResourceManager is empty");
            return;
        }
        storeResourceProvider.setServiceConnectionListenerClient(new AnonymousClass2());
        if (!this.mResourceManager.isConnected() && !this.mResourceManager.isConnecting()) {
            LogUtils.i(TAG, "resource manager not connected, start service", false);
            this.mResourceManager.connect();
            return;
        }
        LogUtils.d(TAG, "resource manager is connected or is connecting, connected = " + this.mResourceManager.isConnected() + "   connecting = " + this.mResourceManager.isConnecting(), false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.carmanager.XuiClientWrapper$2  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass2 implements ServiceConnection {
        AnonymousClass2() {
        }

        public /* synthetic */ void lambda$onServiceConnected$0$XuiClientWrapper$2() {
            XuiClientWrapper.this.registerLluDanceOpenCallBack();
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            XuiClientWrapper.this.mResourceServiceApiCallExecutor.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$XuiClientWrapper$2$pQHjTpBe6JHE2MjXlrFTG3vT0A0
                @Override // java.lang.Runnable
                public final void run() {
                    XuiClientWrapper.AnonymousClass2.this.lambda$onServiceConnected$0$XuiClientWrapper$2();
                }
            });
        }

        public /* synthetic */ void lambda$onServiceDisconnected$1$XuiClientWrapper$2() {
            XuiClientWrapper.this.unRegisterLluDanceOpenCallBack();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            XuiClientWrapper.this.mResourceServiceApiCallExecutor.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$XuiClientWrapper$2$5I4c1tRCHPtZzTo091cdJCDm8Ks
                @Override // java.lang.Runnable
                public final void run() {
                    XuiClientWrapper.AnonymousClass2.this.lambda$onServiceDisconnected$1$XuiClientWrapper$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseResourceManager() {
        if (CarBaseConfig.getInstance().isSupportLluDance() && App.isMainProcess()) {
            this.mResourceServiceConnectThread.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$XuiClientWrapper$cwinWuGPMuE4Os_M-7G_TFqGd1Y
                @Override // java.lang.Runnable
                public final void run() {
                    XuiClientWrapper.this.lambda$releaseResourceManager$1$XuiClientWrapper();
                }
            });
        }
    }

    public /* synthetic */ void lambda$releaseResourceManager$1$XuiClientWrapper() {
        LogUtils.i(TAG, "releaseResourceManager, resManager:" + this.mResourceManager + ", listener:" + this.mListener);
        StoreResourceProvider storeResourceProvider = this.mResourceManager;
        if (storeResourceProvider != null) {
            storeResourceProvider.unregisterDownloadListener(this.mListener);
            this.mResourceManager.unbindService();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unRegisterLluDanceOpenCallBack() {
        if (getInstance().getResourceManager() != null) {
            LogUtils.i(TAG, "unRegisterLluDanceOpenCallBack", false);
            if (this.mListener != null) {
                getInstance().getResourceManager().unregisterDownloadListener(this.mListener);
                return;
            }
            return;
        }
        LogUtils.i(TAG, "unRegisterLluDanceOpenCallBack  ResourceManager  not work", false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerLluDanceOpenCallBack() {
        if (getInstance().getResourceManager() != null) {
            LogUtils.i(TAG, "registerLluDanceOpenCallBack", false);
            this.mListener = new AnonymousClass3((LluDanceViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluDanceViewModel.class));
            getInstance().getResourceManager().registerDownloadListener(this.mListener);
            return;
        }
        LogUtils.i(TAG, "registerLluDanceOpenCallBack  ResourceManager  not work", false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.carmanager.XuiClientWrapper$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass3 implements RMDownloadListener {
        final /* synthetic */ LluDanceViewModel val$lluViewModel;

        AnonymousClass3(final LluDanceViewModel val$lluViewModel) {
            this.val$lluViewModel = val$lluViewModel;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.RMDownloadListener
        public void onDownloadCallback(final int i, final ResourceDownloadInfo resourceDownloadInfo) {
            final LluDanceViewModel lluDanceViewModel = this.val$lluViewModel;
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$XuiClientWrapper$3$_O6FYYfF9bHG9SekTwXWzikFzzc
                @Override // java.lang.Runnable
                public final void run() {
                    XuiClientWrapper.AnonymousClass3.lambda$onDownloadCallback$0(i, resourceDownloadInfo, lluDanceViewModel);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$onDownloadCallback$0(final int i, final ResourceDownloadInfo resourceDownloadInfo, final LluDanceViewModel lluViewModel) {
            LogUtils.i(XuiClientWrapper.TAG, "onDownloadCallback " + i + ", info = " + resourceDownloadInfo, false);
            lluViewModel.updateDownloadView(i, resourceDownloadInfo);
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.RMDownloadListener
        public void onMenuOpenCallback(final String rsdId) {
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$XuiClientWrapper$3$xK8hLFlVi0i2b_RZvkEeG2a5MtM
                @Override // java.lang.Runnable
                public final void run() {
                    XuiClientWrapper.AnonymousClass3.this.lambda$onMenuOpenCallback$1$XuiClientWrapper$3(rsdId);
                }
            });
        }

        public /* synthetic */ void lambda$onMenuOpenCallback$1$XuiClientWrapper$3(final String rsdId) {
            boolean isLLuSwEnabled = ((LluViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluViewModel.class)).isLLuSwEnabled();
            LogUtils.i(XuiClientWrapper.TAG, "onInterceptItemClick lluSwitch=" + isLLuSwEnabled, false);
            if (isLLuSwEnabled) {
                LogUtils.i(XuiClientWrapper.TAG, "onMenuOpenCallback   rsdId  :" + rsdId, false);
                if (!XuiClientWrapper.this.checkResIdValid(rsdId)) {
                    LogUtils.i(XuiClientWrapper.TAG, "onMenuOpenCallback rsdId  invalid", false);
                    return;
                }
                Intent intent = new Intent();
                intent.setComponent(ComponentName.unflattenFromString("com.xiaopeng.carcontrol/com.xiaopeng.lludancemanager.view.LluDanceActivityNew"));
                intent.putExtra(Constant.SELECT_RSC_ID, rsdId);
                intent.addFlags(ClientDefaults.MAX_MSG_SIZE);
                App.getInstance().startActivity(intent);
                return;
            }
            NotificationHelper.getInstance().showToast(R.string.llu_effect_sw_disable);
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.RMDownloadListener
        public void unbindService() {
            LogUtils.i(XuiClientWrapper.TAG, "unbindService  ResourceManager", false);
            XuiClientWrapper.this.unRegisterLluDanceOpenCallBack();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initControllerXuiManager() {
        LogUtils.i(TAG, "initControllerXuiManager start");
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            for (String str : CarClientWrapper.CAR_SVC_ARRAY) {
                BaseCarController controller = CarClientWrapper.getInstance().getController(str);
                if (controller != null && controller.dependOnXuiManager()) {
                    controller.initXuiManager();
                }
            }
        }
        LogUtils.i(TAG, "initControllerXuiManager end");
    }

    public AmbientManagerCompat getAtlManager() {
        return this.mAtlManager;
    }

    public SmartManager getSmartManager() {
        return this.mSmartManager;
    }

    public StoreResourceProvider getResourceManager() {
        return this.mResourceManager;
    }

    public ContextInfoManager getCtiManager() {
        return this.mCtiManager;
    }

    public XAppManager getXAppManager() {
        return this.mXAppManager;
    }

    public LightLanuageManager getLightLanguageManager() {
        return this.mLightLanguageManager;
    }

    public UserScenarioManager getXUserScenarioManager() {
        return this.mXUserScenarioManager;
    }

    public SoundResourceManager getSoundResManager() {
        return this.mSoundResManager;
    }

    public void connectResource() {
        StoreResourceProvider storeResourceProvider = this.mResourceManager;
        if (storeResourceProvider != null) {
            storeResourceProvider.connect();
        }
    }

    public void disConnectResource() {
        StoreResourceProvider storeResourceProvider = this.mResourceManager;
        if (storeResourceProvider != null) {
            storeResourceProvider.disconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkResIdValid(String resId) {
        MutableLiveData<List<LluDanceViewData>> liveDataList = ((LluDanceViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluDanceViewModel.class)).getLiveDataList();
        if (liveDataList != null && liveDataList.getValue() != null) {
            for (LluDanceViewData lluDanceViewData : liveDataList.getValue()) {
                LogUtils.i(TAG, "check res id valid , id = " + lluDanceViewData.getId(), false);
                if (resId.equals(lluDanceViewData.getId())) {
                    return true;
                }
            }
            return false;
        }
        LogUtils.i(TAG, "live data get value is null ,CDU reboot or IG_OFF ", false);
        return true;
    }

    public SeatMassagerManager getSeatMassagerManager() {
        return this.mSeatMassagerManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SingleHolder {
        private static final XuiClientWrapper sInstance = new XuiClientWrapper();

        private SingleHolder() {
        }
    }

    public void playBtMedia() {
        try {
            MediaCenterManager mediaCenterManager = this.mMediaCenterManager;
            if (mediaCenterManager == null || !mediaCenterManager.isBtDeviceAvailable()) {
                return;
            }
            this.mMediaCenterManager.playBtMedia();
            LogUtils.i(TAG, "playBtMedia");
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void setBtVolume(int volume) {
        try {
            MediaCenterManager mediaCenterManager = this.mMediaCenterManager;
            if (mediaCenterManager != null) {
                mediaCenterManager.setBtVolume(volume);
                LogUtils.i(TAG, "setBtVolume " + volume);
            }
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
        }
    }

    public int getCurrentBtStatus() {
        MediaCenterManager mediaCenterManager = this.mMediaCenterManager;
        int i = -1;
        if (mediaCenterManager != null) {
            try {
                i = mediaCenterManager.getBtStatus();
                LogUtils.d(TAG, "getCurrentBtStatus status:" + i);
                return i;
            } catch (XUIServiceNotConnectedException e) {
                e.printStackTrace();
                return i;
            }
        }
        return -1;
    }

    public void requestMediaButton(boolean request) {
        if (this.mMediaCenterManager != null) {
            XUIManager xUIManager = this.mXuiManager;
            if (xUIManager != null) {
                xUIManager.registerObserver();
            }
            this.mMediaCenterManager.requestMediaButton(request, (Bundle) null);
        }
    }

    public MediaCenterManager getMediaCenterManager() {
        return this.mMediaCenterManager;
    }
}
