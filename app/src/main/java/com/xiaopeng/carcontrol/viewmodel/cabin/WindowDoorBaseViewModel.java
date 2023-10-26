package com.xiaopeng.carcontrol.viewmodel.cabin;

import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public abstract class WindowDoorBaseViewModel implements IWindowDoorViewModel, IBcmController.Callback {
    private static final String DOOR_STATE_CLOSED = "0";
    private static final String DOOR_STATE_OPENED = "1";
    private static final String STATE_SEPARATOR = ",";
    private static final String TAG = "WindowDoorBaseViewModel";
    private static final float WINDOW_VENT_THRESHOLD_FRONT = 89.0f;
    private static final float WINDOW_VENT_THRESHOLD_REAR = 80.0f;
    IBcmController mBcmController;
    private IVcuController mVcuController;

    protected abstract void handleAutoDoorHandleChanged(boolean enabled);

    protected abstract void handleAutoWindowLockSwChanged(boolean enable);

    protected abstract void handleBonnetStateChanged(boolean isClosed);

    protected abstract void handleCentralLockStateChanged(boolean isLocked);

    protected abstract void handleDoorStateChanged(String mixedDoorState);

    protected abstract void handleDoorStateChanged(boolean[] doorState);

    protected abstract void handleElcTrunkPosChanged(int pos);

    protected abstract void handleHighSpdCloseWinChanged(boolean enable);

    protected abstract void handleKeyCtrlModeChanged(int mode);

    protected abstract void handleLeftSdcDoorPosChanged(int pos);

    protected abstract void handleLeftSdcStateChanged(int state);

    protected abstract void handleLeftSdcSysRunningStateChanged(int state);

    protected abstract void handleLeftSlideDoorLockStateChanged(int state);

    protected abstract void handleLeftSlideDoorModeChanged(int mode);

    protected abstract void handleLeftSlideDoorStateChanged(int state);

    protected abstract void handleRightSdcDoorPosChanged(int pos);

    protected abstract void handleRightSdcStateChanged(int state);

    protected abstract void handleRightSdcSysRunningStateChanged(int state);

    protected abstract void handleRightSlideDoorLockStateChanged(int state);

    protected abstract void handleRightSlideDoorModeChanged(int mode);

    protected abstract void handleRightSlideDoorStateChanged(int state);

    protected abstract void handleSdcAutoWinDownChanged(boolean auto);

    protected abstract void handleSdcBrakeCloseDoorCfgChange(int cfg);

    protected abstract void handleSdcKeyCfgChanged(int cfg);

    protected abstract void handleSdcMaxDoorOpeningAngleChange(int angle);

    protected abstract void handleSunShadePosChange(int pos);

    protected abstract void handleTrunkStateChanged(int state);

    protected abstract void handleTrunkWorkStatusChanged(int status);

    protected void handleWinPosChanged(int window) {
    }

    protected abstract void handleWindowLockStateChanged(boolean on);

    /* JADX INFO: Access modifiers changed from: package-private */
    public WindowDoorBaseViewModel() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mBcmController = (IBcmController) carClientWrapper.getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onDoorStateChanged(int[] doorState) {
        handleDoorStateChanged(doorState);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onCentralLockStateChanged(boolean isLocked) {
        handleCentralLockStateChanged(isLocked);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onAutoDoorHandleChanged(boolean enabled) {
        handleAutoDoorHandleChanged(enabled);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onWindowPosChanged(int window, float windowStates) {
        handleWinPosChanged(window);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onBonnetStateChanged(int state) {
        handleBonnetStateChanged(state == 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onTrunkStateChanged(int state) {
        handleTrunkStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onElcTrunkPosChanged(int pos) {
        handleElcTrunkPosChanged(pos);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onTrunkWorkStatusChange(int state) {
        handleTrunkWorkStatusChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onWindowKeyControlModeChanged(int mode) {
        handleKeyCtrlModeChanged(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onAutoWindowLockSwChanged(boolean enable) {
        handleAutoWindowLockSwChanged(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onWindowLockStateChanged(boolean on) {
        handleWindowLockStateChanged(on);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onHighSpdCloseWinChanged(boolean enable) {
        handleHighSpdCloseWinChanged(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onLeftSdcStateChanged(int state) {
        handleLeftSdcStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onRightSdcStateChanged(int state) {
        handleRightSdcStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onLeftSdcDoorPosChanged(int pos) {
        handleLeftSdcDoorPosChanged(pos);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onRightSdcDoorPosChanged(int pos) {
        handleRightSdcDoorPosChanged(pos);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onLeftSdcSysRunningStateChanged(int state) {
        handleLeftSdcSysRunningStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onRightSdcSysRunningStateChanged(int state) {
        handleRightSdcSysRunningStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onSdcKeyCfgChanged(int cfg) {
        handleSdcKeyCfgChanged(cfg);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onSdcWinAutoDown(boolean auto) {
        handleSdcAutoWinDownChanged(auto);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onSdcMaxDoorOpeningAngleChanged(int angle) {
        handleSdcMaxDoorOpeningAngleChange(angle);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onSdcBrakeCloseDoorCfgChanged(int cfg) {
        handleSdcBrakeCloseDoorCfgChange(cfg);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onLeftSlideDoorModeChanged(int mode) {
        handleLeftSlideDoorModeChanged(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onRightSlideDoorModeChanged(int mode) {
        handleRightSlideDoorModeChanged(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onLeftSlideDoorStateChanged(int state) {
        handleLeftSlideDoorStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onRightSlideDoorStateChanged(int state) {
        handleRightSlideDoorStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onLeftSlideDoorLockStateChanged(int state) {
        handleLeftSlideDoorLockStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onRightSlideDoorLockStateChanged(int state) {
        handleRightSlideDoorLockStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isDoorClosed(int doorIdx) {
        int[] doorsState = this.mBcmController.getDoorsState();
        return doorsState != null && doorsState.length >= 4 && doorsState[doorIdx] == 0;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isAllDoorsClosed() {
        int[] doorsState = this.mBcmController.getDoorsState();
        return doorsState != null && doorsState.length >= 4 && doorsState[0] == 0 && doorsState[1] == 0 && doorsState[2] == 0 && doorsState[3] == 0;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isCentralLocked() {
        return this.mBcmController.getDoorLockState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setCentralLock(boolean lock) {
        this.mBcmController.setCentralLock(lock);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public String getMixedDoorState() {
        return convertMixedDoorState(this.mBcmController.getDoorsState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean getDoorLockState() {
        return this.mBcmController.getDoorLockState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setAutoDoorHandleEnable(boolean enable) {
        this.mBcmController.setAutoDoorHandleEnable(enable, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isAutoDoorHandleEnabled() {
        return this.mBcmController.isAutoDoorHandleEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public float[] getWindowsPos() {
        return this.mBcmController.getAllWindowPosition();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public float getFLWinPos() {
        return this.mBcmController.getWinPos(0);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public float getFRWinPos() {
        return this.mBcmController.getWinPos(1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public float getRLWinPos() {
        return this.mBcmController.getWinPos(2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public float getRRWinPos() {
        return this.mBcmController.getWinPos(3);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean[] getWindowsState() {
        float[] windowsPos = getWindowsPos();
        if (windowsPos == null || windowsPos.length < 4) {
            return null;
        }
        int length = windowsPos.length;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            zArr[i] = windowsPos[i] < 95.0f;
        }
        return zArr;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isWindowInitFailed(int index) {
        return isWindowInitFailed(index, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isWindowInitFailed(int index, boolean tts) {
        if (this.mBcmController.isWindowInitFailed(index)) {
            String string = ResUtils.getString(R.string.window_init_failed);
            NotificationHelper.getInstance().showToast(string);
            if (tts) {
                SpeechHelper.getInstance().speak(string);
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isWindowOpened() {
        boolean[] windowsState = getWindowsState();
        if (windowsState != null) {
            return windowsState[0] || windowsState[1] || windowsState[2] || windowsState[3];
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlWindowAuto(boolean open) {
        this.mBcmController.controlWindowAuto(open);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlWindowVent() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            if (isAllDoorsClosed()) {
                this.mBcmController.controlWinVent();
                return;
            }
            IBcmController iBcmController = this.mBcmController;
            boolean isDoorClosed = isDoorClosed(0);
            float f = WINDOW_VENT_THRESHOLD_FRONT;
            float fLWinPos = isDoorClosed ? 89.0f : getFLWinPos();
            if (!isDoorClosed(1)) {
                f = getFRWinPos();
            }
            iBcmController.setWindowsMovePositions(fLWinPos, f, WINDOW_VENT_THRESHOLD_REAR, WINDOW_VENT_THRESHOLD_REAR);
            return;
        }
        this.mBcmController.controlWinVent();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlFLWin(boolean open) {
        this.mBcmController.setWindowMoveCmd(0, open ? 4 : 2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlFRWin(boolean open) {
        this.mBcmController.setWindowMoveCmd(1, open ? 4 : 2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlRLWin(boolean open) {
        this.mBcmController.setWindowMoveCmd(2, open ? 4 : 2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlRRWin(boolean open) {
        this.mBcmController.setWindowMoveCmd(3, open ? 4 : 2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setAllWinPos(float position) {
        if (isWindowInitFailed(4)) {
            return;
        }
        if (isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active, true);
        } else {
            this.mBcmController.setWinPos(4, position);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setFLWinPos(float position) {
        if (isWindowInitFailed(0)) {
            return;
        }
        this.mBcmController.setWinPos(0, position);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setFRWinPos(float position) {
        if (isWindowInitFailed(1)) {
            return;
        }
        this.mBcmController.setWinPos(1, position);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setRLWinPos(float position) {
        if (isWindowInitFailed(2)) {
            return;
        }
        this.mBcmController.setWinPos(2, position);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setRRWinPos(float position) {
        if (isWindowInitFailed(3)) {
            return;
        }
        this.mBcmController.setWinPos(3, position);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setLeftWinPos(float position) {
        if (isWindowInitFailed(7)) {
            return;
        }
        this.mBcmController.setWindowsMovePositions(position, 255.0f, position, 255.0f);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setRightWinPos(float position) {
        if (isWindowInitFailed(8)) {
            return;
        }
        this.mBcmController.setWindowsMovePositions(255.0f, position, 255.0f, position);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setFrontWinPos(float position) {
        if (isWindowInitFailed(5)) {
            return;
        }
        this.mBcmController.setWindowsMovePositions(position, position, 255.0f, 255.0f);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setRearWinPos(float position) {
        if (isWindowInitFailed(6)) {
            return;
        }
        this.mBcmController.setWindowsMovePositions(255.0f, 255.0f, position, position);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setDrvWinMovePos(float position) {
        setFLWinPos(position);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isHighSpdCloseWinEnabled() {
        return this.mBcmController.isHighSpdCloseWinEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setHighSpdCloseWin(boolean enable) {
        this.mBcmController.setHighSpdCloseWin(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isWindowLockActive() {
        return this.mBcmController.getWindowLockState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setWindowLockActive(boolean on) {
        this.mBcmController.setWindowLockState(on);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlSdc(boolean left, boolean open) {
        if (open && ((this.mVcuController.getGearLevel() != 4 && this.mVcuController.getGearLevel() != 2) || this.mVcuController.getCarSpeed() > 3.0f)) {
            LogUtils.w(TAG, "not GEAR_LEVEL_P/N or speed > 3", false);
            NotificationHelper.getInstance().showToast(R.string.sdc_control_failed_tips);
            return;
        }
        controlSdc(left, getSdcRunningCmd(left, open));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlSdc(boolean left, int cmd) {
        if (left) {
            this.mBcmController.controlLeftSdc(cmd);
        } else {
            this.mBcmController.controlRightSdc(cmd);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getLeftSdcDoorPos() {
        return this.mBcmController.getLeftSdcDoorPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getRightSdcDoorPos() {
        return this.mBcmController.getRightSdcDoorPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getLeftSdcSysRunningState() {
        return this.mBcmController.getLeftSdcSysRunningState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getRightSdcSysRunningState() {
        return this.mBcmController.getRightSdcSysRunningState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setSdcKeyCfg(int cfg) {
        this.mBcmController.setSdcKeyOpenCtrlCfg(cfg);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getSdcKeyCfg() {
        return this.mBcmController.getSdcKeyOpenCtrlCfg();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setSdcWinAutoDown(boolean open) {
        this.mBcmController.setSdcWindowsAutoDownSwitch(open);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean getSdcWinAutoDown() {
        return this.mBcmController.getSdcWindowsAutoDownSwitch();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setSdcMaxAutoDoorOpeningAngle(int angle) {
        int leftSdcSysRunningState = getLeftSdcSysRunningState();
        int rightSdcSysRunningState = getRightSdcSysRunningState();
        if (leftSdcSysRunningState == 2 || leftSdcSysRunningState == 3 || rightSdcSysRunningState == 2 || rightSdcSysRunningState == 3) {
            LogUtils.d(TAG, "The Sdc System is Running, return", false);
            return;
        }
        LogUtils.d(TAG, "setSdcMaxAutoDoorOpeningAngle, angle: " + angle, false);
        this.mBcmController.setSdcMaxAutoDoorOpeningAngle(angle);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getSdcMaxAutoDoorOpeningAngle() {
        return this.mBcmController.getSdcMaxAutoDoorOpeningAngle();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setSdcBrakeCloseDoorCfg(int cfg) {
        this.mBcmController.setSdcBrakeCloseDoorCfg(cfg);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getSdcBrakeCloseDoorCfg() {
        return this.mBcmController.getSdcBrakeCloseDoorCfg();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getLeftSlideDoorMode() {
        return this.mBcmController.getLeftSlideDoorMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setLeftSlideDoorMode(int mode) {
        this.mBcmController.setLeftSlideDoorMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getRightSlideDoorMode() {
        return this.mBcmController.getRightSlideDoorMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setRightSlideDoorMode(int mode) {
        this.mBcmController.setRightSlideDoorMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getLeftSlideDoorState() {
        return this.mBcmController.getLeftSlideDoorState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getRightSlideDoorState() {
        return this.mBcmController.getRightSlideDoorState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlLeftSlideDoor(int cmd) {
        String str = TAG;
        LogUtils.d(str, "controlLeftSlideDoor, cmd: " + cmd);
        if (cmd == 1) {
            if (checkLeftSlideDoorOpenAvailability() == 0) {
                this.mBcmController.controlLeftSlideDoor(1);
            }
        } else if (cmd == 2) {
            if (checkLeftSlideDoorCloseAvailability() == 0) {
                this.mBcmController.controlLeftSlideDoor(2);
            }
        } else if (cmd == 3) {
            if (getLeftSlideDoorState() == 3 || getLeftSlideDoorState() == 4) {
                this.mBcmController.controlLeftSlideDoor(3);
            } else {
                LogUtils.d(str, "Left slide door is not in opening or closing state");
            }
        } else {
            LogUtils.d(str, "Invalid control type");
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlRightSlideDoor(int cmd) {
        String str = TAG;
        LogUtils.d(str, "controlRightSlideDoor, cmd: " + cmd);
        if (cmd == 1) {
            if (checkRightSlideDoorOpenAvailability() == 0) {
                this.mBcmController.controlRightSlideDoor(1);
            }
        } else if (cmd == 2) {
            if (checkRightSlideDoorCloseAvailability() == 0) {
                this.mBcmController.controlRightSlideDoor(2);
            }
        } else if (cmd == 3) {
            if (getRightSlideDoorState() == 3 || getRightSlideDoorState() == 4) {
                this.mBcmController.controlRightSlideDoor(3);
            } else {
                LogUtils.d(str, "Right slide door is not in opening or closing state");
            }
        } else {
            LogUtils.d(str, "Invalid control type");
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int checkLeftSlideDoorOpenAvailability() {
        if (getLeftSlideDoorState() == 1) {
            LogUtils.d(TAG, "open left slide door failed, left slide door is already full open");
            return 1;
        } else if (this.mVcuController.getGearLevel() != 4 || this.mVcuController.getCarSpeed() > 3.0f) {
            NotificationHelper.getInstance().showToast(R.string.slide_door_open_fail_running);
            LogUtils.d(TAG, "open left slide door failed, vehicle is running");
            return 2;
        } else if (isCentralLocked() || isLeftSlideDoorLocked()) {
            LogUtils.d(TAG, "open left slide door failed, left slide door lock is locked");
            return 4;
        } else if (getLeftSlideDoorMode() != 0) {
            LogUtils.d(TAG, "open left slide door failed, left slide door mode is not power mode");
            return 3;
        } else {
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int checkLeftSlideDoorCloseAvailability() {
        if (getLeftSlideDoorState() == 2) {
            LogUtils.d(TAG, "close left slide door failed, left slide door is already closed");
            return 1;
        } else if (getLeftSlideDoorMode() != 0) {
            LogUtils.d(TAG, "close left slide door failed, left slide door is not in power mode");
            return 2;
        } else {
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int checkRightSlideDoorOpenAvailability() {
        if (getRightSlideDoorState() == 1) {
            LogUtils.d(TAG, "open right slide door failed, right slide door is already full open");
            return 1;
        } else if (this.mVcuController.getGearLevel() != 4 || this.mVcuController.getCarSpeed() > 3.0f) {
            NotificationHelper.getInstance().showToast(R.string.slide_door_open_fail_running);
            LogUtils.d(TAG, "open right slide door failed, vehicle is running");
            return 2;
        } else if (isCentralLocked() || isRightSlideDoorLocked()) {
            LogUtils.d(TAG, "open right slide door failed, right slide door mode is not power mode");
            return 4;
        } else if (getRightSlideDoorMode() != 0) {
            LogUtils.d(TAG, "open right slide door failed, right slide door is not in power mode");
            return 3;
        } else {
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int checkRightSlideDoorCloseAvailability() {
        if (getRightSlideDoorState() == 2) {
            LogUtils.d(TAG, "close right slide door failed, right slide door is already closed");
            return 1;
        } else if (getRightSlideDoorMode() != 0) {
            LogUtils.d(TAG, "close right slide door failed, right slide door is not in power mode");
            return 2;
        } else {
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isLeftSlideDoorLocked() {
        return this.mBcmController.getLeftSlideDoorLockState() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isRightSlideDoorLocked() {
        return this.mBcmController.getRightSlideDoorLockState() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean getBonnetState() {
        return this.mBcmController.getBonnetState() == 0;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getRearTrunkState() {
        return this.mBcmController.getRearTrunk();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getElcTrunkState() {
        return this.mBcmController.getElcTrunkState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isTrunkClosed() {
        return CarBaseConfig.getInstance().isSupportElcTrunk() ? convertTrunkState(getElcTrunkState()) == TrunkState.Closed : getRearTrunkState() == 0;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getElcTrunkPos() {
        return this.mBcmController.getElcTrunkPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void openRearTrunk() {
        if (this.mVcuController.getGearLevel() == 4) {
            if (App.isMainProcess() && this.mBcmController.getRearTrunk() == 2) {
                QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TRUNK_INT, 2);
            }
            this.mBcmController.controlRearTrunk(1);
            return;
        }
        if (App.isMainProcess()) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TRUNK_INT, Integer.valueOf(getRearTrunkState()));
        }
        NotificationHelper.getInstance().showToast(R.string.trunk_unable_with_none_park_gear);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlRearTrunk(int type) {
        if (CarBaseConfig.getInstance().isSupportTrunkOverHeatingProtected()) {
            int trunkWorkModeStatus = this.mBcmController.getTrunkWorkModeStatus();
            if (trunkWorkModeStatus == 1) {
                NotificationHelper.getInstance().showToast(R.string.trunk_system_faulty);
                return;
            } else if (trunkWorkModeStatus == 2) {
                NotificationHelper.getInstance().showToast(R.string.trunk_over_heating);
                return;
            }
        }
        this.mBcmController.controlRearTrunk(type);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getTrunkWorkStatus() {
        return this.mBcmController.getTrunkWorkModeStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setAutoWindowLockSw(boolean enable) {
        this.mBcmController.setAutoWindowLockSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean getAutoWindowLockSw() {
        return this.mBcmController.getAutoWindowLockSw();
    }

    private void handleDoorStateChanged(int[] doorState) {
        if (doorState == null || doorState.length < 4) {
            LogUtils.e(TAG, "Door state array is invalid");
            return;
        }
        boolean[] zArr = new boolean[4];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 3; i++) {
            zArr[i] = doorState[i] == 0;
            sb.append(zArr[i] ? "0" : "1");
            if (i < 3) {
                sb.append(",");
            }
        }
        handleDoorStateChanged(zArr);
        handleDoorStateChanged(sb.toString());
    }

    private static String convertMixedDoorState(int[] doorState) {
        if (doorState == null || doorState.length < 4) {
            LogUtils.e(TAG, "door state array is error");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 3; i++) {
            sb.append(doorState[i] == 0 ? "0" : "1");
            if (i < 3) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000f, code lost:
        if (r0 == 2) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getSdcRunningCmd(boolean r5, boolean r6) {
        /*
            r4 = this;
            if (r5 == 0) goto L7
            int r0 = r4.getLeftSdcSysRunningState()
            goto Lb
        L7:
            int r0 = r4.getRightSdcSysRunningState()
        Lb:
            r1 = 2
            r2 = 3
            if (r6 == 0) goto L12
            if (r0 != r1) goto L17
            goto L14
        L12:
            if (r0 != r2) goto L16
        L14:
            r1 = r2
            goto L17
        L16:
            r1 = 1
        L17:
            java.lang.String r0 = com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel.TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getSdcRunningCmd, left="
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r5 = r2.append(r5)
            java.lang.String r2 = ", open="
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = ", cmd="
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.String r5 = r5.toString()
            r6 = 0
            com.xiaopeng.carcontrol.util.LogUtils.d(r0, r5, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel.getSdcRunningCmd(boolean, boolean):int");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onSunShadePosChanged(int pos) {
        if (pos < 0) {
            pos = 0;
        } else if (pos > 100) {
            pos = 100;
        }
        handleSunShadePosChange(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setSunShadePos(int pos) {
        LogUtils.d(TAG, "setSunShadePos:" + pos, false);
        if (!isSunShadeInitialized() || isSunShadeHotProtect() || isSunShadeBlocked()) {
            return;
        }
        this.mBcmController.setSunShadePos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getSunShadePos() {
        return this.mBcmController.getSunShadePos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void openSunShade() {
        if (isSunShadeHotProtect()) {
            return;
        }
        this.mBcmController.controlSunShade(2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void closeSunShade() {
        if (isSunShadeBlocked()) {
            return;
        }
        this.mBcmController.controlSunShade(1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void stopSunShade() {
        if (isSunShadeInitialized()) {
            this.mBcmController.controlSunShade(3);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void resetSunShade() {
        if (CarBaseConfig.getInstance().isSupportShadeInitCloseConstraint() && getSunShadePos() != 0) {
            NotificationHelper.getInstance().showToast(R.string.sun_shade_not_closed);
        } else {
            this.mBcmController.resetSunShade();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isSunShadeInitialized() {
        boolean isSunShadeInitialized = this.mBcmController.isSunShadeInitialized();
        if (!isSunShadeInitialized) {
            NotificationHelper.getInstance().showToast(R.string.sun_shade_initializing);
        }
        return isSunShadeInitialized;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isSunShadeHotProtect() {
        boolean isSunShadeHotProtect = this.mBcmController.isSunShadeHotProtect();
        if (isSunShadeHotProtect) {
            NotificationHelper.getInstance().showToast(R.string.sun_shade_hot_protect);
        }
        return isSunShadeHotProtect;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isSunShadeBlocked() {
        boolean z = this.mBcmController.isSunShadeAntiPinch() || this.mBcmController.isSunShadeIceBreak();
        if (z) {
            NotificationHelper.getInstance().showToast(R.string.sun_shade_block);
        }
        return z;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void registerBusiness(String... keys) {
        this.mBcmController.registerBusiness(keys);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void unregisterBusiness(String... keys) {
        this.mBcmController.unregisterBusiness(keys);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public boolean isTrunkSensorEnable() {
        return this.mBcmController.isTrunkSensorEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setTrunkSensorEnable(boolean enable) {
        this.mBcmController.setTrunkSensorEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public int getTrunkFullOpenPos() {
        return IWindowDoorViewModel.convertTrunkFullOpenPosLevel2Pos(this.mBcmController.getTrunkFullOpenPos());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void setTrunkFullOpenPos(int pos) {
        this.mBcmController.setTrunkFullOpenPos(pos);
    }

    public static TrunkState convertTrunkStateToQuickSetting(int state) {
        switch (state) {
            case 0:
            case 1:
                return TrunkState.Closed;
            case 2:
            case 3:
                return TrunkState.Opening;
            case 4:
                return TrunkState.Pause_Opening;
            case 5:
                return TrunkState.Opened;
            case 6:
            case 7:
                return TrunkState.Closing;
            case 8:
            case 9:
                return TrunkState.Pause_Closing;
            case 10:
                return TrunkState.Min_Open_Angle;
            default:
                return null;
        }
    }

    public static TrunkState convertTrunkState(int state) {
        switch (state) {
            case 0:
            case 1:
            case 10:
                return TrunkState.Closed;
            case 2:
            case 3:
                return TrunkState.Opening;
            case 4:
                return TrunkState.Pause_Opening;
            case 5:
                return TrunkState.Opened;
            case 6:
            case 7:
                return TrunkState.Closing;
            case 8:
            case 9:
                return TrunkState.Pause_Closing;
            default:
                return null;
        }
    }
}
