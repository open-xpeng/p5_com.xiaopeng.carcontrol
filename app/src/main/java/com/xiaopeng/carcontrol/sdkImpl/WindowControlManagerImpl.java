package com.xiaopeng.carcontrol.sdkImpl;

import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowState;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.WindowControlManagerServer;

/* loaded from: classes2.dex */
public class WindowControlManagerImpl extends AbstractManagerImpl implements WindowControlManagerServer.Implementation {
    private WindowControlManagerServer.Callback mCallback;
    private WindowDoorViewModel mWinDoorVm;

    @Override // com.xiaopeng.carcontrol.sdkImpl.AbstractManagerImpl
    protected void initInternal() {
        this.mWinDoorVm = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
        this.mCallback = WindowControlManagerServer.getInstance().getCallback();
    }

    @Override // com.xiaopeng.carcontrol.sdkImpl.AbstractManagerImpl
    protected void observeData() {
        setLiveDataObserver(this.mWinDoorVm.getWindowStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$WindowControlManagerImpl$HEhQomqzh7QzRjH1bqqttqmY-pE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WindowControlManagerImpl.this.lambda$observeData$0$WindowControlManagerImpl((WindowState) obj);
            }
        });
        setLiveDataObserver(this.mWinDoorVm.getDrvWinPosData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$WindowControlManagerImpl$5fkltexml9urc9gC5dFpy0eSXko
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WindowControlManagerImpl.this.lambda$observeData$1$WindowControlManagerImpl((Float) obj);
            }
        });
        setLiveDataObserver(this.mWinDoorVm.getFrWinPosData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$WindowControlManagerImpl$7xjBh8g8cL2vmL_KlQE3_yIu90s
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WindowControlManagerImpl.this.lambda$observeData$2$WindowControlManagerImpl((Float) obj);
            }
        });
        setLiveDataObserver(this.mWinDoorVm.getRlWinPosData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$WindowControlManagerImpl$IZHUy7rJrfJOsh5-SuJ3f-BWU5A
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WindowControlManagerImpl.this.lambda$observeData$3$WindowControlManagerImpl((Float) obj);
            }
        });
        setLiveDataObserver(this.mWinDoorVm.getRrWinPosData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$WindowControlManagerImpl$_Ij_5t0OYyofAlfXRaWdAKXgS6w
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WindowControlManagerImpl.this.lambda$observeData$4$WindowControlManagerImpl((Float) obj);
            }
        });
        setLiveDataObserver(this.mWinDoorVm.getWindowLockStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$WindowControlManagerImpl$V9XyudxQAWi-WnPeQIj72QUgYHQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WindowControlManagerImpl.this.lambda$observeData$5$WindowControlManagerImpl((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$WindowControlManagerImpl(WindowState state) {
        WindowControlManagerServer.Callback callback;
        if (state == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onWindowStateChanged(convertWindowState(state));
    }

    public /* synthetic */ void lambda$observeData$1$WindowControlManagerImpl(Float pos) {
        WindowControlManagerServer.Callback callback;
        if (pos == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onWindowPosChanged(1, pos.floatValue());
    }

    public /* synthetic */ void lambda$observeData$2$WindowControlManagerImpl(Float pos) {
        WindowControlManagerServer.Callback callback;
        if (pos == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onWindowPosChanged(2, pos.floatValue());
    }

    public /* synthetic */ void lambda$observeData$3$WindowControlManagerImpl(Float pos) {
        WindowControlManagerServer.Callback callback;
        if (pos == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onWindowPosChanged(3, pos.floatValue());
    }

    public /* synthetic */ void lambda$observeData$4$WindowControlManagerImpl(Float pos) {
        WindowControlManagerServer.Callback callback;
        if (pos == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onWindowPosChanged(4, pos.floatValue());
    }

    public /* synthetic */ void lambda$observeData$5$WindowControlManagerImpl(Boolean enable) {
        WindowControlManagerServer.Callback callback;
        if (enable == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onWindowLockStateChanged(enable.booleanValue());
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.WindowControlManagerServer.Implementation
    public void controlWindow(int type) {
        if (this.mWinDoorVm.isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active, true);
        } else if (type == 0) {
            this.mWinDoorVm.controlWindowOpen();
        } else if (type == 1) {
            this.mWinDoorVm.controlWindowClose();
        } else if (type != 2) {
        } else {
            this.mWinDoorVm.controlWindowVent();
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.WindowControlManagerServer.Implementation
    public float getWindowPos(int window) {
        if (window != 1) {
            if (window != 2) {
                if (window != 3) {
                    if (window != 4) {
                        return 100.0f;
                    }
                    return this.mWinDoorVm.getRRWinPos();
                }
                return this.mWinDoorVm.getRLWinPos();
            }
            return this.mWinDoorVm.getFRWinPos();
        }
        return this.mWinDoorVm.getFLWinPos();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.WindowControlManagerServer.Implementation
    public void setWindowPos(int window, float pos) {
        if (this.mWinDoorVm.isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active, true);
        } else if (window == 1) {
            this.mWinDoorVm.setFLWinPos(pos);
        } else if (window == 2) {
            this.mWinDoorVm.setFRWinPos(pos);
        } else if (window == 3) {
            this.mWinDoorVm.setRLWinPos(pos);
        } else if (window != 4) {
        } else {
            this.mWinDoorVm.setRRWinPos(pos);
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.WindowControlManagerServer.Implementation
    public boolean isWindowLockActive() {
        return this.mWinDoorVm.isWindowLockActive();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.WindowControlManagerServer.Implementation
    public void setWindowLockActive(boolean on) {
        this.mWinDoorVm.setWindowLockActive(on);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.sdkImpl.WindowControlManagerImpl$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState;

        static {
            int[] iArr = new int[WindowState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState = iArr;
            try {
                iArr[WindowState.Opening.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Opened.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Closing.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.HalfOpened.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Closed.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private int convertWindowState(WindowState state) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[state.ordinal()];
        if (i != 1) {
            int i2 = 2;
            if (i != 2) {
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        return 3;
                    }
                }
                return i2;
            }
            return 1;
        }
        return 0;
    }
}
