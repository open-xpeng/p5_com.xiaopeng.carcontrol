package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.hardware.CarEcuManager;
import android.car.hardware.CarPropertyValue;
import android.os.Binder;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.xuimanager.userscenario.UserScenarioManager;
import java.util.List;

/* loaded from: classes.dex */
public class ScenarioController extends BaseCarController<CarEcuManager, IScenarioController.Callback> implements IScenarioController {
    protected static final String TAG = "ScenarioController";
    private final UserScenarioManager.UserScenarioListener mXuiUserScenarioCallback = new UserScenarioManager.UserScenarioListener() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScenarioController$Sr-XwNj6H_oQK5Ud8-pmfVPfLKU
        public final void onUserScenarioStateChanged(String str, int i) {
            ScenarioController.this.lambda$new$0$ScenarioController(str, i);
        }
    };
    protected UserScenarioManager mXuiUserScenarioManager;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public boolean dependOnXuiManager() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
    }

    public ScenarioController(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "InitCarManager");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
        LogUtils.d(TAG, "Init xui manager start");
        UserScenarioManager xUserScenarioManager = XuiClientWrapper.getInstance().getXUserScenarioManager();
        this.mXuiUserScenarioManager = xUserScenarioManager;
        if (xUserScenarioManager != null) {
            if (this.mIsMainProcess) {
                this.mXuiUserScenarioManager.registerListener(this.mXuiUserScenarioCallback);
            }
        } else {
            LogUtils.e(TAG, "UserScenarioManager is null", false);
        }
        LogUtils.d(TAG, "Init xui manager end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScenarioController
    public String startScenario(boolean enable, String scenario) {
        String str;
        UserScenarioManager userScenarioManager = this.mXuiUserScenarioManager;
        if (userScenarioManager == null) {
            str = null;
        } else if (enable) {
            str = userScenarioManager.enterUserScenario(scenario, IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY);
        } else {
            str = userScenarioManager.exitUserScenario(scenario);
        }
        LogUtils.d(TAG, "startScenario: " + enable + ", scenario: " + scenario + ", ret= " + str, false);
        return str;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScenarioController
    public String getCurrentUserScenario() {
        try {
            UserScenarioManager userScenarioManager = this.mXuiUserScenarioManager;
            if (userScenarioManager != null) {
                return userScenarioManager.getCurrentUserScenario();
            }
            return null;
        } catch (Exception e) {
            LogUtils.e(TAG, "getCurrentUserScenario: " + e.getMessage(), false);
            return null;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScenarioController
    public int getUserScenarioStatus(String scenario) {
        try {
            UserScenarioManager userScenarioManager = this.mXuiUserScenarioManager;
            if (userScenarioManager != null) {
                return userScenarioManager.getUserScenarioStatus(scenario);
            }
            return -1;
        } catch (Exception e) {
            LogUtils.e(TAG, "getCurrentUserScenario: " + e.getMessage(), false);
            return -1;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScenarioController
    public void reportScenarioState(String scenario, int state) {
        UserScenarioManager userScenarioManager = this.mXuiUserScenarioManager;
        if (userScenarioManager != null) {
            userScenarioManager.reportStatus(scenario, state);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScenarioController
    public void registerBinderObserver() {
        UserScenarioManager userScenarioManager = this.mXuiUserScenarioManager;
        if (userScenarioManager != null) {
            userScenarioManager.registerBinderObserver(new Binder());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0054, code lost:
        if (r6.equals(com.xiaopeng.carcontrol.carmanager.controller.IScenarioController.SPACE_CAPSULE_MOVIE_MODE) == false) goto L7;
     */
    /* renamed from: handleUserScenario */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$new$0$ScenarioController(java.lang.String r6, int r7) {
        /*
            Method dump skipped, instructions count: 376
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.ScenarioController.lambda$new$0$ScenarioController(java.lang.String, int):void");
    }
}
