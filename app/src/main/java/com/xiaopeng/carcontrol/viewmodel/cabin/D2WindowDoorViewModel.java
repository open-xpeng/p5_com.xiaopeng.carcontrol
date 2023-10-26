package com.xiaopeng.carcontrol.viewmodel.cabin;

import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;

/* loaded from: classes2.dex */
public class D2WindowDoorViewModel extends WindowDoorViewModel {
    private static final String TAG = "D2WindowDoorViewModel";
    private TrunkType mLastTrunkType;
    private boolean isWindowTimeoutRunning = false;
    boolean isTrunkProtecting = false;
    private final Runnable mTrunkProtectTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$D2WindowDoorViewModel$wq6zK6Kp1zpvQddOG-HLVcaudYw
        @Override // java.lang.Runnable
        public final void run() {
            D2WindowDoorViewModel.this.lambda$new$0$D2WindowDoorViewModel();
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public D2WindowDoorViewModel() {
        handleWinPosChanged(1);
        handleWindowStateChange();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void initTrunkState() {
        TrunkState trunkState;
        int rearTrunkState = getRearTrunkState();
        if (rearTrunkState == 1) {
            trunkState = TrunkState.Closing;
        } else if (rearTrunkState == 2) {
            trunkState = TrunkState.Opened;
        } else {
            trunkState = TrunkState.Closed;
        }
        this.mRearTrunkState.postValue(trunkState);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlRearTrunk(TrunkType type) {
        if (this.isTrunkProtecting) {
            LogUtils.i(TAG, "The trunk is in protecting mode, can`t move");
            this.mRearTrunkState.postValue(this.mRearTrunkState.getValue());
            return;
        }
        this.mLastTrunkType = type;
        super.controlRearTrunk(type);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleTrunkStateChanged(int bcmState) {
        TrunkState value = this.mRearTrunkState.getValue();
        LogUtils.d(TAG, "handleTrunkStateChanged: lastTrunkControlType=" + this.mLastTrunkType + ", lastUiState=" + value + ", bcmState=" + bcmState);
        if (bcmState != 0) {
            if (bcmState == 1) {
                handleTrunkOpening(value);
            } else if (bcmState == 2) {
                handleTrunkOpened(value);
            }
        } else if (value != TrunkState.Closed) {
            this.isTrunkProtecting = true;
            ThreadUtils.postDelayed(2, this.mTrunkProtectTask, 1000L);
            this.mRearTrunkState.postValue(TrunkState.Closed);
        }
        this.mLastTrunkType = null;
    }

    private void handleTrunkOpening(TrunkState lastUiState) {
        if (this.mLastTrunkType == TrunkType.Open) {
            this.mRearTrunkState.postValue(TrunkState.Opening);
        } else if (this.mLastTrunkType == TrunkType.Close) {
            this.mRearTrunkState.postValue(TrunkState.Closing);
        } else if (this.mLastTrunkType == null) {
            if (lastUiState == TrunkState.Closed || lastUiState == TrunkState.Pause_Closing) {
                this.mRearTrunkState.postValue(TrunkState.Opening);
            } else if (lastUiState == TrunkState.Opened || lastUiState == TrunkState.Pause_Opening) {
                this.mRearTrunkState.postValue(TrunkState.Closing);
            }
        }
    }

    private void handleTrunkOpened(TrunkState lastUiState) {
        if (lastUiState == TrunkState.Closed) {
            LogUtils.i(TAG, "Trunk has no intermediate state directly from opened to closed");
            return;
        }
        if (this.mLastTrunkType == TrunkType.Pause) {
            if (lastUiState == TrunkState.Opening) {
                this.mRearTrunkState.postValue(TrunkState.Pause_Opening);
            } else if (lastUiState == TrunkState.Closing) {
                this.mRearTrunkState.postValue(TrunkState.Pause_Closing);
            } else {
                this.mRearTrunkState.postValue(TrunkState.Opened);
            }
        } else {
            this.mRearTrunkState.postValue(TrunkState.Opened);
        }
        this.isTrunkProtecting = true;
        ThreadUtils.postDelayed(2, this.mTrunkProtectTask, 1000L);
    }

    public /* synthetic */ void lambda$new$0$D2WindowDoorViewModel() {
        this.isTrunkProtecting = false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onWindowsPosChanged(float[] winsPos) {
        if (!this.isWindowTimeoutRunning) {
            handleWindowStateChange();
        }
        handleWinPosChanged(1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel
    public void controlWindowClose() {
        super.controlWindowClose();
        this.isWindowTimeoutRunning = true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel
    public void controlWindowOpen() {
        super.controlWindowOpen();
        this.isWindowTimeoutRunning = true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlWindowVent() {
        super.controlWindowVent();
        this.isWindowTimeoutRunning = true;
    }
}
