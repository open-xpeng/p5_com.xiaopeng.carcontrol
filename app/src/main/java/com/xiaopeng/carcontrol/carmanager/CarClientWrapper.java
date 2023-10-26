package com.xiaopeng.carcontrol.carmanager;

import android.car.Car;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.impl.AtlController;
import com.xiaopeng.carcontrol.carmanager.impl.AvasController;
import com.xiaopeng.carcontrol.carmanager.impl.AvmController;
import com.xiaopeng.carcontrol.carmanager.impl.BcmController;
import com.xiaopeng.carcontrol.carmanager.impl.CarInfoController;
import com.xiaopeng.carcontrol.carmanager.impl.CdcController;
import com.xiaopeng.carcontrol.carmanager.impl.CiuController;
import com.xiaopeng.carcontrol.carmanager.impl.DcdcController;
import com.xiaopeng.carcontrol.carmanager.impl.DiagnosticController;
import com.xiaopeng.carcontrol.carmanager.impl.EpsController;
import com.xiaopeng.carcontrol.carmanager.impl.EspController;
import com.xiaopeng.carcontrol.carmanager.impl.HvacController;
import com.xiaopeng.carcontrol.carmanager.impl.IcmController;
import com.xiaopeng.carcontrol.carmanager.impl.LluController;
import com.xiaopeng.carcontrol.carmanager.impl.McuController;
import com.xiaopeng.carcontrol.carmanager.impl.MsmController;
import com.xiaopeng.carcontrol.carmanager.impl.ScenarioController;
import com.xiaopeng.carcontrol.carmanager.impl.ScuController;
import com.xiaopeng.carcontrol.carmanager.impl.SfsController;
import com.xiaopeng.carcontrol.carmanager.impl.TboxController;
import com.xiaopeng.carcontrol.carmanager.impl.TpmsController;
import com.xiaopeng.carcontrol.carmanager.impl.VcuController;
import com.xiaopeng.carcontrol.carmanager.impl.XpuController;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class CarClientWrapper {
    public static final String HVAC_SERVICE = "hvac";
    private static final String TAG = "CarClientWrapper";
    public static final String XP_CAR_INFO_SERVICE = "xp_CarInfoService";
    private Car mCarClient;
    private ExecutorService mInitCbExecutor;
    private ICarServiceConnectListener mListener;
    public static final String XP_MCU_SERVICE = "xp_mcu";
    public static final String XP_VCU_SERVICE = "xp_vcu";
    public static final String XP_BCM_SERVICE = "xp_bcm";
    public static final String XP_ESP_SERVICE = "xp_esp";
    public static final String XP_MSM_SERVICE = "xp_msm";
    public static final String XP_EPS_SERVICE = "xp_eps";
    public static final String XP_ICM_SERVICE = "xp_icm";
    public static final String XP_LLU_SERVICE = "xp_llu";
    public static final String XP_ATL_SERVICE = "xp_atl";
    public static final String XP_AVAS_SERVICE = "xp_avas";
    public static final String XP_SCU_SERVICE = "xp_scu";
    public static final String XP_TPMS_SERVICE = "xp_tpms";
    public static final String XP_CIU_SERVICE = "xp_ciu";
    public static final String XP_TBOX_SERVICE = "xp_tbox";
    public static final String XP_DIAGNOSTIC_SERVICE = "xp_diagnostic";
    public static final String XP_XPU_SERVICE = "xp_xpu";
    public static final String XP_SFS_SERVICE = "xp_sfs";
    public static final String XP_AVM_SERVICE = "xp_avm";
    public static final String XP_USER_SCENARIO_SERVICE = "xp_UserScenarioService";
    public static final String XP_DCDC_SERVICE = "xp_dcdc";
    public static final String XP_CDC_SERVICE = "xp_cdc";
    static final String[] CAR_SVC_ARRAY = {XP_MCU_SERVICE, XP_VCU_SERVICE, "hvac", XP_BCM_SERVICE, XP_ESP_SERVICE, XP_MSM_SERVICE, XP_EPS_SERVICE, XP_ICM_SERVICE, XP_LLU_SERVICE, XP_ATL_SERVICE, XP_AVAS_SERVICE, XP_SCU_SERVICE, XP_TPMS_SERVICE, XP_CIU_SERVICE, XP_TBOX_SERVICE, XP_DIAGNOSTIC_SERVICE, XP_XPU_SERVICE, XP_SFS_SERVICE, XP_AVM_SERVICE, XP_USER_SCENARIO_SERVICE, XP_DCDC_SERVICE, XP_CDC_SERVICE};
    private volatile boolean mIsCarSvcConnected = false;
    private final Object mControllerLock = new Object();
    private final HashMap<String, BaseCarController<?, ?>> mControllers = new HashMap<>();
    private CountDownLatch mSvcCountdown = new CountDownLatch(CAR_SVC_ARRAY.length);
    private final ServiceConnection mCarConnectionCb = new ServiceConnection() { // from class: com.xiaopeng.carcontrol.carmanager.CarClientWrapper.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i(CarClientWrapper.TAG, "onServiceConnected", false);
            CarClientWrapper.this.initCarControllers();
            if (CarClientWrapper.this.mListener != null) {
                CarClientWrapper.this.mListener.onCarServiceConnected(true);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i(CarClientWrapper.TAG, "onServiceDisconnected", false);
            CarClientWrapper.this.mIsCarSvcConnected = false;
            if (CarClientWrapper.this.mListener != null) {
                CarClientWrapper.this.mListener.onCarServiceConnected(false);
            }
            if (App.isMainProcess()) {
                return;
            }
            LogUtils.i(CarClientWrapper.TAG, "Force stop Unity process due to Car Service disconnected");
            Process.killProcess(Process.myPid());
        }
    };

    /* loaded from: classes.dex */
    public interface ICarServiceConnectListener {
        void onCarServiceConnected(boolean isConnected);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SingleHolder {
        private static final CarClientWrapper sInstance = new CarClientWrapper();

        private SingleHolder() {
        }
    }

    public static CarClientWrapper getInstance() {
        return SingleHolder.sInstance;
    }

    public void setListener(ICarServiceConnectListener listener) {
        this.mListener = listener;
    }

    public void connectToCar(Handler handler) {
        if (this.mIsCarSvcConnected) {
            return;
        }
        this.mCarClient = Car.createCar(App.getInstance(), this.mCarConnectionCb, handler);
        LogUtils.i(TAG, "Start to connect Car service", false);
        this.mCarClient.connect();
    }

    public void disconnect() {
        Car car;
        if (!this.mIsCarSvcConnected || (car = this.mCarClient) == null) {
            return;
        }
        car.disconnect();
    }

    public boolean isCarServiceConnected() {
        if (!this.mIsCarSvcConnected) {
            try {
                String str = TAG;
                LogUtils.i(str, "Waiting car service ready: " + Thread.currentThread().getName(), false);
                this.mSvcCountdown.await();
                this.mIsCarSvcConnected = true;
                LogUtils.i(str, "Car service has been ready", false);
            } catch (InterruptedException e) {
                LogUtils.e(TAG, (String) null, e);
            }
        }
        return this.mIsCarSvcConnected;
    }

    public boolean isCarServiceConnectedSync() {
        LogUtils.i(TAG, "isCarServiceConnectedSync: " + this.mIsCarSvcConnected, false);
        return this.mIsCarSvcConnected;
    }

    public BaseCarController getController(String serviceName) {
        BaseCarController<?, ?> baseCarController;
        if (XP_CAR_INFO_SERVICE.equals(serviceName)) {
            BaseCarController<?, ?> baseCarController2 = this.mControllers.get(serviceName);
            if (baseCarController2 == null) {
                CarInfoController carInfoController = new CarInfoController(null);
                this.mControllers.put(serviceName, carInfoController);
                return carInfoController;
            }
            return baseCarController2;
        }
        Car carClient = getCarClient();
        synchronized (this.mControllerLock) {
            baseCarController = this.mControllers.get(serviceName);
            if (baseCarController == null) {
                baseCarController = createCarController(serviceName, carClient);
                this.mControllers.put(serviceName, baseCarController);
            }
        }
        return baseCarController;
    }

    private Car getCarClient() {
        if (!this.mIsCarSvcConnected) {
            try {
                String str = TAG;
                LogUtils.i(str, "Waiting car service ready internal", false);
                CountDownLatch countDownLatch = this.mSvcCountdown;
                if (countDownLatch != null && countDownLatch.getCount() > 0) {
                    this.mSvcCountdown.await();
                }
                this.mIsCarSvcConnected = true;
                LogUtils.i(str, "Car service has been ready internal", false);
            } catch (InterruptedException e) {
                LogUtils.e(TAG, (String) null, e);
            }
        }
        return this.mCarClient;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarControllers() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$CarClientWrapper$tpdo77EvTD24vbTM7Rc9j85lEu0
            @Override // java.lang.Runnable
            public final void run() {
                CarClientWrapper.this.lambda$initCarControllers$1$CarClientWrapper();
            }
        });
    }

    public /* synthetic */ void lambda$initCarControllers$1$CarClientWrapper() {
        String[] strArr;
        if (XuiClientWrapper.getInstance().isXuiServiceConnected()) {
            LogUtils.i(TAG, "initCarControllers start");
            CountDownLatch countDownLatch = this.mSvcCountdown;
            if (countDownLatch == null || countDownLatch.getCount() == 0) {
                this.mSvcCountdown = null;
                this.mSvcCountdown = new CountDownLatch(CAR_SVC_ARRAY.length);
            }
            synchronized (this.mControllerLock) {
                createInitCbThreadPool();
                for (String str : CAR_SVC_ARRAY) {
                    final BaseCarController<?, ?> baseCarController = this.mControllers.get(str);
                    if (baseCarController == null) {
                        this.mControllers.put(str, createCarController(str, this.mCarClient));
                    } else {
                        LogUtils.i(TAG, "re-initCarController " + str, false);
                        this.mInitCbExecutor.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$CarClientWrapper$ZXuPLkquU9ADjr5hAOxig_uX20Y
                            @Override // java.lang.Runnable
                            public final void run() {
                                CarClientWrapper.this.lambda$null$0$CarClientWrapper(baseCarController);
                            }
                        });
                        this.mSvcCountdown.countDown();
                    }
                }
                shutdownInitCbThreadPool();
            }
            LogUtils.i(TAG, "initCarControllers end");
        }
    }

    public /* synthetic */ void lambda$null$0$CarClientWrapper(final BaseCarController finaController) {
        finaController.initCarManager(this.mCarClient);
    }

    private BaseCarController createCarController(String serviceName, final Car carClient) {
        final BaseCarController createCarController;
        serviceName.hashCode();
        char c = 65535;
        switch (serviceName.hashCode()) {
            case -1871502322:
                if (serviceName.equals(XP_AVAS_SERVICE)) {
                    c = 0;
                    break;
                }
                break;
            case -1871431131:
                if (serviceName.equals(XP_DCDC_SERVICE)) {
                    c = 1;
                    break;
                }
                break;
            case -1870955074:
                if (serviceName.equals(XP_TBOX_SERVICE)) {
                    c = 2;
                    break;
                }
                break;
            case -1870941687:
                if (serviceName.equals(XP_TPMS_SERVICE)) {
                    c = 3;
                    break;
                }
                break;
            case -753107758:
                if (serviceName.equals(XP_ATL_SERVICE)) {
                    c = 4;
                    break;
                }
                break;
            case -753107695:
                if (serviceName.equals(XP_AVM_SERVICE)) {
                    c = 5;
                    break;
                }
                break;
            case -753107323:
                if (serviceName.equals(XP_BCM_SERVICE)) {
                    c = 6;
                    break;
                }
                break;
            case -753106341:
                if (serviceName.equals(XP_CDC_SERVICE)) {
                    c = 7;
                    break;
                }
                break;
            case -753106168:
                if (serviceName.equals(XP_CIU_SERVICE)) {
                    c = '\b';
                    break;
                }
                break;
            case -753104031:
                if (serviceName.equals(XP_EPS_SERVICE)) {
                    c = '\t';
                    break;
                }
                break;
            case -753103941:
                if (serviceName.equals(XP_ESP_SERVICE)) {
                    c = '\n';
                    break;
                }
                break;
            case -753100596:
                if (serviceName.equals(XP_ICM_SERVICE)) {
                    c = 11;
                    break;
                }
                break;
            case -753097426:
                if (serviceName.equals(XP_LLU_SERVICE)) {
                    c = '\f';
                    break;
                }
                break;
            case -753096744:
                if (serviceName.equals(XP_MCU_SERVICE)) {
                    c = '\r';
                    break;
                }
                break;
            case -753096256:
                if (serviceName.equals(XP_MSM_SERVICE)) {
                    c = 14;
                    break;
                }
                break;
            case -753090978:
                if (serviceName.equals(XP_SCU_SERVICE)) {
                    c = 15;
                    break;
                }
                break;
            case -753090887:
                if (serviceName.equals(XP_SFS_SERVICE)) {
                    c = 16;
                    break;
                }
                break;
            case -753088095:
                if (serviceName.equals(XP_VCU_SERVICE)) {
                    c = 17;
                    break;
                }
                break;
            case -753085770:
                if (serviceName.equals(XP_XPU_SERVICE)) {
                    c = 18;
                    break;
                }
                break;
            case 3214768:
                if (serviceName.equals("hvac")) {
                    c = 19;
                    break;
                }
                break;
            case 264148814:
                if (serviceName.equals(XP_DIAGNOSTIC_SERVICE)) {
                    c = 20;
                    break;
                }
                break;
            case 775511507:
                if (serviceName.equals(XP_USER_SCENARIO_SERVICE)) {
                    c = 21;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                createCarController = AvasController.AvasControllerFactory.createCarController(carClient);
                break;
            case 1:
                createCarController = new DcdcController(carClient);
                break;
            case 2:
                createCarController = new TboxController(carClient);
                break;
            case 3:
                createCarController = new TpmsController(carClient);
                break;
            case 4:
                createCarController = AtlController.AtlControllerFactory.createCarController(carClient);
                break;
            case 5:
                createCarController = new AvmController(carClient);
                break;
            case 6:
                createCarController = BcmController.BcmControllerFactory.createCarController(carClient);
                break;
            case 7:
                createCarController = new CdcController(carClient);
                break;
            case '\b':
                createCarController = CiuController.CiuControllerFactory.createCarController(carClient);
                break;
            case '\t':
                createCarController = new EpsController(carClient);
                break;
            case '\n':
                createCarController = EspController.EspControllerFactory.createCarController(carClient);
                break;
            case 11:
                createCarController = IcmController.IcmControllerFactory.createCarController(carClient);
                break;
            case '\f':
                createCarController = LluController.LluControllerFactory.createCarController(carClient);
                break;
            case '\r':
                createCarController = new McuController(carClient);
                break;
            case 14:
                createCarController = MsmController.MsmControllerFactory.createCarController(carClient);
                break;
            case 15:
                createCarController = ScuController.ScuControllerFactory.createCarController(carClient);
                break;
            case 16:
                createCarController = new SfsController(carClient);
                break;
            case 17:
                createCarController = VcuController.VcuControllerFactory.createCarController(carClient);
                break;
            case 18:
                createCarController = XpuController.XpuControllerFactory.createCarController(carClient);
                break;
            case 19:
                createCarController = HvacController.HvacControllerFactory.createCarController(carClient);
                break;
            case 20:
                createCarController = new DiagnosticController(carClient);
                break;
            case 21:
                createCarController = new ScenarioController(carClient);
                break;
            default:
                throw new IllegalArgumentException("Can not create controller for " + serviceName);
        }
        this.mSvcCountdown.countDown();
        if (createCarController != null) {
            this.mInitCbExecutor.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$CarClientWrapper$Xpz4EVVhMtqp9Hl5LhBaMEM5o20
                @Override // java.lang.Runnable
                public final void run() {
                    CarClientWrapper.lambda$createCarController$2(BaseCarController.this, carClient);
                }
            });
        }
        return createCarController;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$createCarController$2(final BaseCarController controller, final Car carClient) {
        if (controller.dependOnXuiManager()) {
            controller.initXuiManager();
        }
        controller.initCarManager(carClient);
    }

    private void createInitCbThreadPool() {
        ExecutorService executorService = this.mInitCbExecutor;
        if (executorService == null || executorService.isShutdown() || this.mInitCbExecutor.isTerminated()) {
            this.mInitCbExecutor = null;
            this.mInitCbExecutor = Executors.newFixedThreadPool(6);
        }
    }

    private void shutdownInitCbThreadPool() {
        ExecutorService executorService = this.mInitCbExecutor;
        if (executorService == null || executorService.isShutdown() || this.mInitCbExecutor.isTerminated()) {
            return;
        }
        this.mInitCbExecutor.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$CarClientWrapper$FjpG1FNsHnRCPs86jlMZe0hVw7I
            @Override // java.lang.Runnable
            public final void run() {
                CarClientWrapper.this.lambda$shutdownInitCbThreadPool$3$CarClientWrapper();
            }
        });
    }

    public /* synthetic */ void lambda$shutdownInitCbThreadPool$3$CarClientWrapper() {
        LogUtils.i(TAG, "Sleep 10 seconds to shutdown init thread pool", false);
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String str = TAG;
        LogUtils.i(str, "Start to shutdown init thread pool now", false);
        LogUtils.i(str, "Thread pool has been init shutdown, remain " + this.mInitCbExecutor.shutdownNow().size() + " tasks", false);
    }
}
