package com.xiaopeng.carcontrol.view.dialog.panel;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.direct.IPanelDirectDispatch;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.widget.WindowControlView;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.xui.widget.XTabLayout;

/* loaded from: classes2.dex */
public class WindowControlPanel extends AbstractControlPanel implements IPanelDirectDispatch {
    private XTabLayout mSideTab;
    private WindowDoorViewModel mWinDoorVm;
    private WindowControlView mWindowControlView;
    private boolean mIsLeft = true;
    private final String TAG = getClass().getSimpleName();
    private final String[] mSecondPageData = {"/windowcontrol/drv_side", "/windowcontrol/psn_side"};

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.layout_window_control_panel;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel
    protected int getPanelHeight() {
        return R.dimen.x_dialog_max_height;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel
    protected int getPanelWidth() {
        return R.dimen.x_dialog_xlarge_width;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_WINDOW_CONTROL;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean needInitVm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitViewModel() {
        this.mWinDoorVm = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
    }

    public /* synthetic */ void lambda$onInitView$0$WindowControlPanel(View v) {
        dismiss();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitView() {
        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$WindowControlPanel$UJl5u5Yp2kbl9HKFJ2WmSd5rRGA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WindowControlPanel.this.lambda$onInitView$0$WindowControlPanel(view);
            }
        });
        WindowControlView windowControlView = (WindowControlView) findViewById(R.id.window_control_view);
        this.mWindowControlView = windowControlView;
        windowControlView.setWinControlListener(new WindowControlView.OnWinControlListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.WindowControlPanel.1
            @Override // com.xiaopeng.carcontrol.view.widget.WindowControlView.OnWinControlListener
            public boolean onInterceptControl() {
                return false;
            }

            @Override // com.xiaopeng.carcontrol.view.widget.WindowControlView.OnWinControlListener
            public void onFrontLeftChanged(int pos) {
                LogUtils.d(WindowControlPanel.this.TAG, "onFrontLeftChanged, pos: " + pos);
                if (WindowControlPanel.this.isWindowLockInactive()) {
                    WindowControlPanel.this.mWinDoorVm.setFLWinPos(pos);
                }
            }

            @Override // com.xiaopeng.carcontrol.view.widget.WindowControlView.OnWinControlListener
            public void onRearLeftChanged(int pos) {
                LogUtils.d(WindowControlPanel.this.TAG, "onRearLeftChanged, pos: " + pos);
                if (WindowControlPanel.this.isWindowLockInactive()) {
                    WindowControlPanel.this.mWinDoorVm.setRLWinPos(pos);
                }
            }

            @Override // com.xiaopeng.carcontrol.view.widget.WindowControlView.OnWinControlListener
            public void onFrontRightChanged(int pos) {
                LogUtils.d(WindowControlPanel.this.TAG, "onFrontRightChanged, pos: " + pos);
                if (WindowControlPanel.this.isWindowLockInactive()) {
                    WindowControlPanel.this.mWinDoorVm.setFRWinPos(pos);
                }
            }

            @Override // com.xiaopeng.carcontrol.view.widget.WindowControlView.OnWinControlListener
            public void onRearRightChanged(int pos) {
                LogUtils.d(WindowControlPanel.this.TAG, "onRearRightChanged, pos: " + pos);
                if (WindowControlPanel.this.isWindowLockInactive()) {
                    WindowControlPanel.this.mWinDoorVm.setRRWinPos(pos);
                }
            }
        });
        XTabLayout xTabLayout = (XTabLayout) findViewById(R.id.dialog_tabs_bar);
        this.mSideTab = xTabLayout;
        xTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.WindowControlPanel.2
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
                if (index == 0) {
                    WindowControlPanel.this.updateCurrentWinBtn(true);
                } else if (index != 1) {
                } else {
                    WindowControlPanel.this.updateCurrentWinBtn(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onRefresh() {
        updateCurrentWinBtn(this.mIsLeft);
        this.mWindowControlView.showGesture();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onRegisterLiveData() {
        setLiveDataObserver(this.mWinDoorVm.getDrvWinPosData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$WindowControlPanel$KiHO5k95RXrOSkcHmImGpySfDvk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WindowControlPanel.this.lambda$onRegisterLiveData$1$WindowControlPanel((Float) obj);
            }
        });
        setLiveDataObserver(this.mWinDoorVm.getFrWinPosData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$WindowControlPanel$osHXN2xUgtjOoxmpMR5aMEcZ-9o
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WindowControlPanel.this.lambda$onRegisterLiveData$2$WindowControlPanel((Float) obj);
            }
        });
        setLiveDataObserver(this.mWinDoorVm.getRlWinPosData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$WindowControlPanel$jj0a6eH7Wnuy7FjNwtyIF1T2hFM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WindowControlPanel.this.lambda$onRegisterLiveData$3$WindowControlPanel((Float) obj);
            }
        });
        setLiveDataObserver(this.mWinDoorVm.getRrWinPosData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$WindowControlPanel$ckYm__s2cxsA6YeNnE-PMz7dudI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WindowControlPanel.this.lambda$onRegisterLiveData$4$WindowControlPanel((Float) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onRegisterLiveData$1$WindowControlPanel(Float pos) {
        if (!this.mIsLeft || pos == null) {
            return;
        }
        this.mWindowControlView.setLeftFrontWinPos(pos.intValue());
    }

    public /* synthetic */ void lambda$onRegisterLiveData$2$WindowControlPanel(Float pos) {
        if (this.mIsLeft || pos == null) {
            return;
        }
        this.mWindowControlView.setRightFrontWinPos(pos.intValue());
    }

    public /* synthetic */ void lambda$onRegisterLiveData$3$WindowControlPanel(Float pos) {
        if (!this.mIsLeft || pos == null) {
            return;
        }
        this.mWindowControlView.setLeftRearWinPos(pos.intValue());
    }

    public /* synthetic */ void lambda$onRegisterLiveData$4$WindowControlPanel(Float pos) {
        if (this.mIsLeft || pos == null) {
            return;
        }
        this.mWindowControlView.setRightRearWinPos(pos.intValue());
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.carcontrol.direct.OnPageDirectShowListener
    public void onPageDirectShow(String name) {
        if ("/windowcontrol/drv_side".equals(name)) {
            this.mSideTab.selectTab(0);
            updateCurrentWinBtn(true);
        } else if ("/windowcontrol/psn_side".equals(name)) {
            this.mSideTab.selectTab(1);
        }
        super.onPageDirectShow(name);
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected String[] supportSecondPageForElementDirect() {
        return this.mSecondPageData;
    }

    @Override // com.xiaopeng.carcontrol.direct.IPanelDirectDispatch
    public void dispatchDirectData(Uri url) {
        this.mDirectIntent = null;
        if (url != null) {
            this.mDirectIntent = new Intent();
            this.mDirectIntent.setData(url);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCurrentWinBtn(boolean isLeft) {
        this.mIsLeft = isLeft;
        this.mWindowControlView.setControlSide(isLeft ? 1 : 2);
        updateWinPosition();
    }

    private void updateWinPosition() {
        WindowControlView windowControlView;
        float[] windowsPos = this.mWinDoorVm.getWindowsPos();
        if (windowsPos == null || windowsPos.length < 4 || (windowControlView = this.mWindowControlView) == null) {
            return;
        }
        if (this.mIsLeft) {
            windowControlView.setLeftFrontWinPos((int) windowsPos[0]);
            this.mWindowControlView.setLeftRearWinPos((int) windowsPos[2]);
            return;
        }
        windowControlView.setRightFrontWinPos((int) windowsPos[1]);
        this.mWindowControlView.setRightRearWinPos((int) windowsPos[3]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWindowLockInactive() {
        if (this.mWinDoorVm.isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active);
            return false;
        }
        return true;
    }
}
