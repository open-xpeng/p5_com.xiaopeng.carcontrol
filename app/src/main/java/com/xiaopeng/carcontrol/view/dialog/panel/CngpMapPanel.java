package com.xiaopeng.carcontrol.view.dialog.panel;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapItem;
import com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapName;
import com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapTitleItem;
import com.xiaopeng.carcontrol.direct.IPanelDirectDispatch;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.view.adapter.CngpMapAdapter;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XRecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class CngpMapPanel extends AbstractControlPanel implements IPanelDirectDispatch {
    private static final int CITY_DATA_COUNT_LIMIT = 2;
    private static final int DOWNLOAD_CHECK_INVERVAL = 5000;
    private List<CngpMapItem> currentList;
    private CngpMapAdapter mAdapter;
    private int mDownloadedItemCount;
    private Disposable mDownloadingDisposable;
    private int mDownloadingItemCount;
    private View mEmptyLayout;
    private XRecyclerView mRecyclerView;
    private View mTip;
    private View mTipIcon;
    private VcuViewModel mVcuVM;
    private XpuViewModel mXpuVM;
    private XDialog mConfirmDialog = null;
    private CngpMapAdapter.ItemClickListener mItemClickListener = new CngpMapAdapter.ItemClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.CngpMapPanel.1
        @Override // com.xiaopeng.carcontrol.view.adapter.CngpMapAdapter.ItemClickListener
        public void onDelete(int id) {
            CngpMapPanel.this.readyDelete(id);
        }

        @Override // com.xiaopeng.carcontrol.view.adapter.CngpMapAdapter.ItemClickListener
        public void onDownload(int id) {
            CngpMapPanel.this.readyDownload(id);
        }

        @Override // com.xiaopeng.carcontrol.view.adapter.CngpMapAdapter.ItemClickListener
        public void onRetry(int id) {
            CngpMapPanel.this.readyDownload(id);
        }
    };
    private Observer cityListObserver = new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$CngpMapPanel$E4b5hVhFXwq1Hy1Pek2nNbbYO5s
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            CngpMapPanel.this.updateCityItemList((List) obj);
        }
    };
    private Observer xpuConnectedObserver = new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$CngpMapPanel$SyaIc2vvbWYSYM93pULNM4r7o78
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            CngpMapPanel.this.onXpuConnectedChanged((Boolean) obj);
        }
    };
    private Observer gearObserver = new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$CngpMapPanel$SvnUPW57ZtvIFdnInhzgR4LLv3s
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            CngpMapPanel.this.onGearLevelChanged((GearLevel) obj);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.layout_cngp_map;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel
    protected int getPanelHeight() {
        return R.dimen.ngp_setting_height;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel
    protected int getPanelWidth() {
        return R.dimen.x_dialog_xlarge_width;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_CNGP_MAP_CONTROL;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean isPanelSupportScroll() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean needInitVm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean shouldDisableVui() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitViewModel() {
        ViewModelManager viewModelManager = ViewModelManager.getInstance();
        this.mXpuVM = (XpuViewModel) viewModelManager.getViewModelImpl(IXpuViewModel.class);
        this.mVcuVM = (VcuViewModel) viewModelManager.getViewModelImpl(IVcuViewModel.class);
    }

    public /* synthetic */ void lambda$onInitView$0$CngpMapPanel(View v) {
        dismiss();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitView() {
        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$CngpMapPanel$nAZANLIUs9GZeHcWrMCYes88Jbc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CngpMapPanel.this.lambda$onInitView$0$CngpMapPanel(view);
            }
        });
        this.mEmptyLayout = findViewById(R.id.layout_empty);
        this.mTip = findViewById(R.id.tip);
        this.mTipIcon = findViewById(R.id.tipIcon);
        XRecyclerView xRecyclerView = (XRecyclerView) findViewById(R.id.rv_cngp);
        this.mRecyclerView = xRecyclerView;
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        if (this.mAdapter == null) {
            CngpMapAdapter cngpMapAdapter = new CngpMapAdapter(this.mContext);
            this.mAdapter = cngpMapAdapter;
            cngpMapAdapter.setClickListener(this.mItemClickListener);
        }
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onShow() {
        super.onShow();
        this.mXpuVM.getAllCities();
        refreshUI();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onDismiss() {
        stopIntervalCheck();
    }

    @Override // com.xiaopeng.carcontrol.direct.IPanelDirectDispatch
    public void dispatchDirectData(Uri url) {
        this.mDirectIntent = null;
        if (url != null) {
            this.mDirectIntent = new Intent();
            this.mDirectIntent.setData(url);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onRegisterLiveData() {
        setLiveDataObserver(this.mXpuVM.getCngpMapItemList(), this.cityListObserver);
        setLiveDataObserver(this.mXpuVM.getXpuConnectedData(), this.xpuConnectedObserver);
        setLiveDataObserver(this.mVcuVM.getGearLevelData(), this.gearObserver);
    }

    private void refreshUI() {
        if (this.mXpuVM.getXpuConnected()) {
            this.mTip.setVisibility(0);
            this.mTipIcon.setVisibility(0);
            this.mRecyclerView.setVisibility(0);
            this.mEmptyLayout.setVisibility(4);
            return;
        }
        this.mTip.setVisibility(4);
        this.mTipIcon.setVisibility(4);
        this.mRecyclerView.setVisibility(4);
        this.mEmptyLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGearLevelChanged(GearLevel level) {
        if (level != GearLevel.P) {
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onXpuConnectedChanged(Boolean connected) {
        if (connected.booleanValue()) {
            this.mXpuVM.getAllCities();
            refreshUI();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCityItemList(List<CngpMapItem> list) {
        final LinkedList<CngpMapItem> sortList = sortList(list);
        this.mAdapter.setData(sortList);
        DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.CngpMapPanel.2
            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public int getOldListSize() {
                if (CngpMapPanel.this.currentList != null) {
                    return CngpMapPanel.this.currentList.size();
                }
                return 0;
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public int getNewListSize() {
                List list2 = sortList;
                if (list2 != null) {
                    return list2.size();
                }
                return 0;
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public boolean areItemsTheSame(int oldPos, int newPos) {
                return ((CngpMapItem) CngpMapPanel.this.currentList.get(oldPos)).getType() == ((CngpMapItem) sortList.get(newPos)).getType();
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public boolean areContentsTheSame(int oldPos, int newPos) {
                return ((CngpMapItem) CngpMapPanel.this.currentList.get(oldPos)).equals(sortList.get(newPos));
            }
        }, false).dispatchUpdatesTo(this.mAdapter);
        this.currentList = sortList;
    }

    private LinkedList<CngpMapItem> sortList(List<CngpMapItem> origin) {
        LinkedList<CngpMapItem> linkedList = new LinkedList<>();
        LinkedList<CngpMapItem> linkedList2 = new LinkedList();
        LinkedList<CngpMapItem> linkedList3 = new LinkedList();
        int i = 0;
        int i2 = 0;
        for (CngpMapItem cngpMapItem : origin) {
            if (cngpMapItem.getState() == 2) {
                linkedList2.add(cngpMapItem);
                i++;
            } else {
                linkedList3.add(cngpMapItem);
                if (cngpMapItem.getState() == 1) {
                    i2++;
                }
            }
        }
        this.mDownloadedItemCount = i;
        this.mDownloadingItemCount = i2;
        if (i2 == 0) {
            stopIntervalCheck();
        } else if (this.mDownloadingDisposable == null) {
            startIntervalCheck();
        }
        boolean checkDownloadCondition = checkDownloadCondition();
        if (this.mDownloadedItemCount > 0) {
            linkedList.add(new CngpMapTitleItem(-1, this.mContext.getString(R.string.cngp_map_panel_category_downloaded), R.color.x_theme_text_01));
            for (CngpMapItem cngpMapItem2 : linkedList2) {
                cngpMapItem2.setActivated(checkDownloadCondition);
                linkedList.add(cngpMapItem2);
            }
        }
        if (linkedList3.size() != 0) {
            linkedList.add(new CngpMapTitleItem(-1, this.mContext.getString(R.string.cngp_map_panel_category_download_available), R.color.x_theme_text_01));
            for (CngpMapItem cngpMapItem3 : linkedList3) {
                cngpMapItem3.setActivated(checkDownloadCondition);
                linkedList.add(cngpMapItem3);
            }
        } else {
            linkedList.add(new CngpMapTitleItem(-1, this.mContext.getString(R.string.cngp_map_panel_category_no_more), R.color.x_theme_text_03));
        }
        return linkedList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readyDelete(final int id) {
        showDialog(R.string.cngp_map_panel_dialog_delete_title, String.format(this.mContext.getString(R.string.cngp_map_panel_dialog_delete_content), CngpMapName.convertIdToName(id)), ResUtils.getString(R.string.btn_cancel), null, ResUtils.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$CngpMapPanel$pFtePTbhBlf4Mz3DCacrkfQdpyA
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                CngpMapPanel.this.lambda$readyDelete$1$CngpMapPanel(id, xDialog, i);
            }
        }, null, VuiManager.SCENE_CNGP_MAP_CONTROL);
    }

    public /* synthetic */ void lambda$readyDelete$1$CngpMapPanel(final int id, XDialog xDialog, int i) {
        this.mXpuVM.deleteCity(id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readyDownload(final int id) {
        if (!checkDownloadCondition()) {
            NotificationHelper.getInstance().showToast(R.string.cngp_map_panel_toast);
        } else {
            showDialog(R.string.cngp_map_panel_dialog_title, this.mContext.getString(R.string.cngp_map_panel_dialog_content), ResUtils.getString(R.string.btn_cancel), null, ResUtils.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$CngpMapPanel$ZA8oLDZmsVT_wQKb3dAWjLwK-Ic
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    CngpMapPanel.this.lambda$readyDownload$2$CngpMapPanel(id, xDialog, i);
                }
            }, null, VuiManager.SCENE_CNGP_MAP_CONTROL);
        }
    }

    public /* synthetic */ void lambda$readyDownload$2$CngpMapPanel(final int id, XDialog xDialog, int i) {
        confirmDownload(id);
    }

    private boolean checkDownloadCondition() {
        LogUtils.i(this.TAG, "checkDownloadCondition: " + this.mDownloadedItemCount + " " + this.mDownloadingItemCount);
        return this.mDownloadedItemCount + this.mDownloadingItemCount < 2;
    }

    private void confirmDownload(int id) {
        this.mDownloadingItemCount++;
        this.mXpuVM.downloadCity(id);
        startIntervalCheck();
    }

    private void startIntervalCheck() {
        Disposable disposable = this.mDownloadingDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        LogUtils.i(this.TAG, "startIntervalCheck");
        this.mDownloadingDisposable = Observable.interval(UILooperObserver.ANR_TRIGGER_TIME, TimeUnit.MILLISECONDS).startWith((Observable<Long>) 0L).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$CngpMapPanel$KGlnRX2RlFcItSJrSumOSNJUJys
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CngpMapPanel.this.lambda$startIntervalCheck$3$CngpMapPanel((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$startIntervalCheck$3$CngpMapPanel(Long l) throws Exception {
        this.mXpuVM.getAllCities();
    }

    private void stopIntervalCheck() {
        LogUtils.i(this.TAG, "stopIntervalCheck");
        Disposable disposable = this.mDownloadingDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.mDownloadingDisposable = null;
        }
    }

    protected void showDialog(int titleId, String message, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener, final DialogInterface.OnDismissListener onDismissListener, String sceneId) {
        dismissDialog();
        XDialog positiveButton = new XDialog(this.mContext).setTitle(titleId).setMessage(message).setNegativeButton(negative, negativeListener).setPositiveButton(positive, positiveListener);
        positiveButton.setSystemDialog(2048);
        if (!TextUtils.isEmpty(sceneId)) {
            positiveButton.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$CngpMapPanel$O9StfVOvmXLdAW9Ww9KUSVJMntg
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    CngpMapPanel.lambda$showDialog$4(onDismissListener, dialogInterface);
                }
            });
        } else if (onDismissListener != null) {
            positiveButton.getDialog().setOnDismissListener(onDismissListener);
        }
        if (!TextUtils.isEmpty(sceneId)) {
            VuiManager.instance().initVuiDialog(positiveButton, this.mContext, sceneId);
        }
        positiveButton.show();
        this.mConfirmDialog = positiveButton;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showDialog$4(final DialogInterface.OnDismissListener onDismissListener, DialogInterface dialog) {
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    protected void dismissDialog() {
        XDialog xDialog = this.mConfirmDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mConfirmDialog = null;
        }
    }
}
