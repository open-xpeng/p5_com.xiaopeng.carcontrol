package com.xiaopeng.carcontrol.view.fragment.xpilot;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.BusinessConstant;
import com.xiaopeng.carcontrol.CarControlService;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.bean.xpilot.XPilotCardItem;
import com.xiaopeng.carcontrol.bean.xpilot.XPilotItem;
import com.xiaopeng.carcontrol.bean.xpilot.XPilotTabItem;
import com.xiaopeng.carcontrol.bean.xpilot.category.XPilotCategoryManager;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.direct.ElementDirectItem;
import com.xiaopeng.carcontrol.direct.ElementDirectManager;
import com.xiaopeng.carcontrol.direct.IElementDirect;
import com.xiaopeng.carcontrol.direct.PageDirectItem;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.XPilotOperateInterface;
import com.xiaopeng.carcontrol.view.dialog.DialogInfoHelper;
import com.xiaopeng.carcontrol.view.dialog.XPilotVideoDialog;
import com.xiaopeng.carcontrol.view.dialog.XpilotExamDialog;
import com.xiaopeng.carcontrol.view.fragment.BaseFragment;
import com.xiaopeng.carcontrol.viewmodel.account.AccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.account.bean.ExamUrlResult;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ApResponse;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuIslaMode;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuLssMode;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.LssSensitivity;
import com.xiaopeng.carcontrol.viewmodel.xpu.NedcState;
import com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XRecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class XPilotFragment extends BaseFragment implements XPilotOperateInterface {
    private static final String TAG = "XPilotFragment";
    private static final String[] sElementDirectSupport = {"/xpilotcontrol/tip/fcw", "/xpilotcontrol/tip/bsd", "/xpilotcontrol/tip/dow", "/xpilotcontrol/tip/ldw", "/xpilotcontrol/tip/elk", "/xpilotcontrol/tip/lss", "/xpilotcontrol/tip/rcw", "/xpilotcontrol/tip/raeb", "/xpilotcontrol/tip/rcta", "/xpilotcontrol/tip/islc", "/xpilotcontrol/tip/isla", "/xpilotcontrol/tip/simple_sas", "/xpilotcontrol/tip/special_sas", "/xpilotcontrol/tip/auto_park", "/xpilotcontrol/tip/mem_park", "/xpilotcontrol/tip/lcc", "/xpilotcontrol/tip/alc", "/xpilotcontrol/tip/cngp", "/xpilotcontrol/tip/ngp", "/xpilotcontrol/tip/hdc", "/xpilotcontrol/tip/esp", "/xpilotcontrol/tip/avh", "/xpilotcontrol/tip/ebw"};
    private AccountViewModel mAccountVm;
    private XPilotAdapter mAdapter;
    private ChassisViewModel mChassisVm;
    private XPilotCardItem mEbwCardItem;
    private XPilotCardItem mElkCardItem;
    private XPilotCardItem mEspCardItem;
    private XPilotTabItem mIslaTabItem;
    private List<XPilotItem> mItems;
    private XPilotVideoDialog mLccVideoDialog;
    private XPilotTabItem mLssSenTabItem;
    private XPilotTabItem mLssTabItem;
    private XPilotVideoDialog mMemParkVideoDialog;
    private XRecyclerView mRecyclerView;
    private ScuViewModel mScuVm;
    private XPilotTabItem mSimpleSasTabItem;
    private XPilotTabItem mSpecialSasTabItem;
    private VcuViewModel mVcuVm;
    private XPilotFuncTipsRunnable mXPilotFuncTipsRunnable;
    private XpilotExamDialog mXpilotExamDialog;
    private XpuViewModel mXpuVm;
    private final List<XPilotItem> mXpuDepItems = new ArrayList();
    private XPilotCardItem mFcwSwItem = null;
    private XPilotCardItem mBsdCardItem = null;
    private XPilotCardItem mAutoParkSwItem = null;
    private XPilotCardItem mMemParkSwItem = null;
    private XPilotCardItem mLccSwItem = null;
    private XPilotCardItem mAlcSwItem = null;
    private XPilotCardItem mNgpSwItem = null;
    private XPilotCardItem mCNgpSwItem = null;
    private XPilotCardItem mCNgpMapItem = null;
    private XPilotCardItem mNgpSettingItem = null;
    private boolean mPendingLccSw = false;
    private View mPendLccView = null;
    private boolean mPendLccIsVuiAction = false;
    private boolean mPendingApaSw = false;
    private View mPendApaView = null;
    private boolean mPendApaIsVuiAction = false;
    private boolean mPendingNgpSw = false;
    private View mPendNgpView = null;
    private boolean mPendNgpIsVuiAction = false;
    private boolean mPendingMemSw = false;
    private View mPendMemView = null;
    private boolean mPendMemIsVuiAction = false;
    private boolean mPendingCngpSw = false;
    private View mPendCngpView = null;
    private boolean mPendCngpIsVuiAction = false;
    private int mNgpSettingPosition = 0;
    private final Runnable mUpdateViewRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$xkqpkpEMpGaXpTXCtXFcxbQqBic
        @Override // java.lang.Runnable
        public final void run() {
            XPilotFragment.this.lambda$new$0$XPilotFragment();
        }
    };

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected boolean isListFragment() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.ILoadingFragment
    public boolean needLoading() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotFragment$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState;

        static {
            int[] iArr = new int[NedcState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState = iArr;
            try {
                iArr[NedcState.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState[NedcState.Failure.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState[NedcState.On.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState[NedcState.TurnOning.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState[NedcState.TurnOnFailure.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState[NedcState.TurnOffing.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private static boolean parseXpuNedcState(NedcState nedcState) {
        int i;
        return nedcState == null || (i = AnonymousClass3.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState[nedcState.ordinal()]) == 1 || i == 2;
    }

    /* loaded from: classes2.dex */
    private class XPilotFuncTipsRunnable implements Runnable {
        private String name;

        private XPilotFuncTipsRunnable(String name) {
            this.name = name;
        }

        @Override // java.lang.Runnable
        public void run() {
            XPilotFragment.this.showXPilotFuncTip(this.name);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: updateResumeView */
    public void lambda$new$0$XPilotFragment() {
        String str = TAG;
        LogUtils.i(str, "updateResumeView");
        XPilotCardItem xPilotCardItem = this.mEspCardItem;
        if (xPilotCardItem != null) {
            xPilotCardItem.setValue(Boolean.valueOf(this.mChassisVm.getEspForUI()));
            this.mAdapter.notifyItemRangeChanged(this.mEspCardItem.getIndex(), 1, Integer.valueOf(this.mEspCardItem.getIndex()));
        }
        XPilotCardItem xPilotCardItem2 = this.mEbwCardItem;
        if (xPilotCardItem2 != null) {
            xPilotCardItem2.setValue(Boolean.valueOf(this.mChassisVm.getEbw()));
            this.mAdapter.notifyItemRangeChanged(this.mEbwCardItem.getIndex(), 1, Integer.valueOf(this.mEbwCardItem.getIndex()));
        }
        ScuViewModel scuViewModel = this.mScuVm;
        boolean z = false;
        if (scuViewModel == null || this.mXpuVm == null) {
            LogUtils.i(str, "onResume mXpuVm or mScuVm is null", false);
        } else if (this.mAutoParkSwItem != null) {
            ScuResponse autoParkSw = scuViewModel.getAutoParkSw();
            boolean z2 = !CarBaseConfig.getInstance().isSupportXpu() || parseXpuNedcState(this.mXpuVm.getNedcState());
            LogUtils.i(str, "onResume key: auto_park,value: " + autoParkSw + "isXpuOn: " + z2, false);
            XPilotCardItem xPilotCardItem3 = this.mAutoParkSwItem;
            if (z2 && autoParkSw == ScuResponse.ON) {
                z = true;
            }
            xPilotCardItem3.setValue(Boolean.valueOf(z));
            this.mAdapter.notifyItemRangeChanged(this.mAutoParkSwItem.getIndex(), 1, Integer.valueOf(this.mAutoParkSwItem.getIndex()));
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    public void updateVuiScene(final View... views) {
        XRecyclerView xRecyclerView = this.mRecyclerView;
        if (xRecyclerView != null) {
            xRecyclerView.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$wxfjJ731H4Dh_Lx-ARWJLrfC8fs
                @Override // java.lang.Runnable
                public final void run() {
                    XPilotFragment.this.lambda$updateVuiScene$1$XPilotFragment(views);
                }
            });
        }
    }

    public /* synthetic */ void lambda$updateVuiScene$1$XPilotFragment(final View[] views) {
        super.updateVuiScene(views);
    }

    @Override // com.xiaopeng.carcontrol.view.XPilotOperateInterface
    public void onVuiItemUpdate(View view) {
        updateVuiScene(this.mRecyclerView);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViewModels() {
        this.mScuVm = (ScuViewModel) getViewModel(IScuViewModel.class);
        this.mXpuVm = (XpuViewModel) getViewModel(IXpuViewModel.class);
        this.mVcuVm = (VcuViewModel) getViewModel(IVcuViewModel.class);
        this.mChassisVm = (ChassisViewModel) getViewModel(IChassisViewModel.class);
        this.mAccountVm = (AccountViewModel) getViewModel(IAccountViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_xpilot;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mRecyclerView = (XRecyclerView) view.findViewById(R.id.x_pilot_list);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViews() {
        String str = TAG;
        LogUtils.i(str, "Init Adapter start", false);
        this.mItems = XPilotCategoryManager.getInstance().getXPilotList();
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), ResUtils.getInt(R.integer.xpilot_list_span_count)));
        if (this.mAdapter == null) {
            XPilotAdapter xPilotAdapter = new XPilotAdapter(getActivity(), this.mItems);
            this.mAdapter = xPilotAdapter;
            xPilotAdapter.setXPilotOperatorListener(this);
        }
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    XPilotFragment xPilotFragment = XPilotFragment.this;
                    xPilotFragment.updateVuiScene(xPilotFragment.mRecyclerView);
                }
            }
        });
        this.mRecyclerView.setAdapter(this.mAdapter);
        VuiManager.instance().initRecyclerView(this.mRecyclerView, getSceneId());
        this.mRecyclerView.animate().alpha(1.0f).setDuration(500L).start();
        if (needLoading()) {
            hideLoading();
        }
        initXPilotFunc();
        LogUtils.i(str, "Init Adapter end", false);
    }

    public /* synthetic */ void lambda$onGuiInitEnd$2$XPilotFragment() {
        super.onGuiInitEnd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    public void onGuiInitEnd() {
        this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$85Z51-GlBkHwQGZ85o78JY0xNTQ
            @Override // java.lang.Runnable
            public final void run() {
                XPilotFragment.this.lambda$onGuiInitEnd$2$XPilotFragment();
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mRecyclerView.setAdapter(null);
        this.mRecyclerView = null;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        XPilotAdapter xPilotAdapter;
        super.onConfigurationChanged(newConfig);
        if (!XThemeManager.isThemeChanged(newConfig) || (xPilotAdapter = this.mAdapter) == null) {
            return;
        }
        xPilotAdapter.notifyDataSetChanged();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mScuVm != null && BaseFeatureOption.getInstance().isSignalRegisterDynamically()) {
            this.mScuVm.registerBusiness(BusinessConstant.KEY_XPILOT);
        }
        ThreadUtils.runOnMainThreadDelay(this.mUpdateViewRunnable, 200L);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        ScuViewModel scuViewModel;
        super.onPause();
        if (BaseFeatureOption.getInstance().isSignalRegisterDynamically() && (scuViewModel = this.mScuVm) != null) {
            scuViewModel.unregisterBusiness(BusinessConstant.KEY_XPILOT);
        }
        ThreadUtils.getHandler(1).removeCallbacks(this.mUpdateViewRunnable);
        if (this.mXPilotFuncTipsRunnable != null) {
            ThreadUtils.getHandler(1).removeCallbacks(this.mUpdateViewRunnable);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            this.mPendingLccSw = false;
            this.mPendLccView = null;
            this.mPendLccIsVuiAction = false;
            this.mPendingNgpSw = false;
            this.mPendNgpView = null;
            this.mPendNgpIsVuiAction = false;
            clearExamEventObserver();
            dismissDialog();
            XPilotAdapter xPilotAdapter = this.mAdapter;
            if (xPilotAdapter != null) {
                xPilotAdapter.disableSoundEffect();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    public void dismissDialog() {
        super.dismissDialog();
        XpilotExamDialog xpilotExamDialog = this.mXpilotExamDialog;
        if (xpilotExamDialog != null && xpilotExamDialog.isShowing()) {
            this.mXpilotExamDialog.dismiss();
        }
        DialogInfoHelper.getInstance().dismissXPilotInfoPanel();
    }

    @Override // com.xiaopeng.carcontrol.view.XPilotOperateInterface
    public void onVuiFeedbackViewCreate(View view) {
        VuiUtils.addHasFeedbackProp((IVuiElement) view);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x008a, code lost:
        if (r6.equals(com.xiaopeng.carcontrol.bean.xpilot.XPilotItem.KEY_MEM_PARK) == false) goto L12;
     */
    @Override // com.xiaopeng.carcontrol.view.XPilotOperateInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onExtBtnClicked(int r6, android.view.View r7) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotFragment.onExtBtnClicked(int, android.view.View):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x01f1, code lost:
        if (r5.equals(com.xiaopeng.carcontrol.bean.xpilot.XPilotItem.KEY_CNGP_MAP) == false) goto L31;
     */
    @Override // com.xiaopeng.carcontrol.view.XPilotOperateInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCardItemChanged(int r9, boolean r10, android.view.View r11) {
        /*
            Method dump skipped, instructions count: 1098
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotFragment.onCardItemChanged(int, boolean, android.view.View):void");
    }

    private void confirmFcwFunc(final View view, final boolean isVuiAction) {
        ScuResponse fcwState = this.mScuVm.getFcwState();
        if (fcwState == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else if (fcwState == ScuResponse.INITIALIZATION) {
            NotificationHelper.getInstance().showToast(R.string.fcw_feature_title_init);
            if (isVuiAction) {
                vuiFeedbackClickForXpilot(R.string.fcw_feature_title_init, view);
            }
        } else if (isParkStallGoAhead(view, isVuiAction)) {
            if (fcwState == ScuResponse.ON) {
                showDialog(R.string.fcw_feature_title, R.string.fcw_feature_feedback, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.fcw_feature_feedback_positive), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$Dwp1eNML3rRh8jOvsU2hsmvjAM0
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        XPilotFragment.this.lambda$confirmFcwFunc$3$XPilotFragment(view, isVuiAction, xDialog, i);
                    }
                }, VuiManager.SCENE_XPILOT_DIALOG_FCW);
            } else {
                this.mScuVm.setFcwEnable();
            }
        }
    }

    public /* synthetic */ void lambda$confirmFcwFunc$3$XPilotFragment(final View view, final boolean isVuiAction, XDialog xDialog, int i) {
        if (isParkStallGoAhead(view, isVuiAction)) {
            this.mScuVm.setFcwEnable();
        }
    }

    private void confirmRctaFunc(View view, boolean isVuiAction) {
        if (this.mScuVm.getRctaState() == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else if (isParkStallGoAhead(view, isVuiAction)) {
            this.mScuVm.setRctaEnable();
        }
    }

    private void confirmSsaFunc(boolean enable, View view, boolean isVuiAction) {
        if (isParkStallGoAhead(view, isVuiAction)) {
            this.mVcuVm.setSsaSwEnable(enable);
        }
    }

    private void confirmNraFunc(boolean enable, View view, boolean isVuiAction) {
        this.mXpuVm.setNraSwEnable(enable);
    }

    private void confirmNraStateFunc(int state, View view, boolean isVuiAction) {
        this.mXpuVm.setNraState(state);
    }

    private void confirmElkFunc(final View view, final boolean isVuiAction) {
        ScuResponse elkState = this.mScuVm.getElkState();
        if (elkState == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else if (isNotParkStall()) {
            showNotParkStallTip(view, isVuiAction);
        } else if (elkState == ScuResponse.OFF) {
            this.mScuVm.setElkEnable(true);
        } else {
            showDialog(R.string.elk_feature_title, R.string.elk_feature_confirm, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$xglrxnLJ52tplXv2IHRc4efCyOk
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    XPilotFragment.this.lambda$confirmElkFunc$4$XPilotFragment(view, isVuiAction, xDialog, i);
                }
            }, VuiManager.SCENE_XPILOT_DIALOG_ELK);
        }
    }

    public /* synthetic */ void lambda$confirmElkFunc$4$XPilotFragment(final View view, final boolean isVuiAction, XDialog xDialog, int i) {
        if (isParkStallGoAhead(view, isVuiAction)) {
            this.mScuVm.setElkEnable(false);
        }
    }

    private void confirmRcwFunc(boolean enable, View view, boolean isVuiAction) {
        if (this.mScuVm.getRcwState() == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else if (isParkStallGoAhead(view, isVuiAction)) {
            this.mScuVm.setRcwEnable(enable);
        }
    }

    private void confirmRaebFunc(final boolean enable, final View view, final boolean isVuiAction) {
        ScuResponse raebState = this.mXpuVm.getRaebState();
        if (raebState == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else if (isParkStallGoAhead(view, isVuiAction)) {
            if (raebState == ScuResponse.ON) {
                showDialog(R.string.raeb_feature_title, R.string.raeb_feature_feedback, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$kwGY9oqZUxWLF_a3gc5PuoNSlCc
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        XPilotFragment.this.lambda$confirmRaebFunc$5$XPilotFragment(view, isVuiAction, enable, xDialog, i);
                    }
                }, VuiManager.SCENE_XPILOT_DIALOG_RAEB);
            } else {
                this.mXpuVm.setRaebEnable(enable);
            }
        }
    }

    public /* synthetic */ void lambda$confirmRaebFunc$5$XPilotFragment(final View view, final boolean isVuiAction, final boolean enable, XDialog xDialog, int i) {
        if (isParkStallGoAhead(view, isVuiAction)) {
            this.mXpuVm.setRaebEnable(enable);
        }
    }

    private void confirmLccFuncPart1(boolean enable, View view, final boolean isVuiAction) {
        if (checkIfXpuAvailable(view, isVuiAction)) {
            if (isVuiAction && enable && !this.mAccountVm.isLogin()) {
                vuiFeedback(R.string.blank, view);
            }
            clearExamEventObserver();
            if (!enable || checkUserLoginState()) {
                confirmLccFuncPart2(enable, view, isVuiAction);
                return;
            }
            this.mPendingLccSw = true;
            this.mPendLccView = view;
            this.mPendLccIsVuiAction = isVuiAction;
        }
    }

    private void confirmLccFuncPart2(boolean enable, View view, final boolean isVuiAction) {
        if (!enable || checkLccSafeExamResult()) {
            confirmLccFuncPart3(enable, view, isVuiAction);
            return;
        }
        this.mPendingLccSw = true;
        this.mPendLccView = view;
        this.mPendLccIsVuiAction = isVuiAction;
        requestLccExamTaskInfo();
    }

    private boolean checkLccSafeExamResult() {
        boolean lccSafeExamResult = this.mScuVm.getLccSafeExamResult();
        LogUtils.i(TAG, "get exam result from local =" + lccSafeExamResult, false);
        return lccSafeExamResult;
    }

    private void requestLccExamTaskInfo() {
        LogUtils.i(TAG, "request lcc exam info form server", false);
        NotificationHelper.getInstance().showToast(R.string.laa_verify_permission);
        watchTaskFailure();
        setLiveDataObserver(this.mAccountVm.getTaskListResult(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$kEv-OGmzDfQGF9E5ti_BrLKQujs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XPilotFragment.this.lambda$requestLccExamTaskInfo$6$XPilotFragment((Integer) obj);
            }
        });
        this.mAccountVm.requestNgpLccRelateExamResult();
    }

    public /* synthetic */ void lambda$requestLccExamTaskInfo$6$XPilotFragment(Integer result) {
        if (result == null) {
            return;
        }
        LogUtils.i(TAG, "check lcc exam result from server: result" + result);
        if (result.intValue() == 1) {
            confirmLccFuncPart3(this.mPendingLccSw, this.mPendLccView, this.mPendLccIsVuiAction);
        } else if (isNotParkStall()) {
            showNotParkStallTip(this.mPendLccView, this.mPendLccIsVuiAction);
        } else {
            showOrUpdateExamQrCodeDialog(null, getLccTaskId());
            requestExamQrCode(getLccTaskId());
        }
    }

    private long getLccTaskId() {
        return CarBaseConfig.getInstance().isSupportLidar() ? 8L : 2L;
    }

    private void confirmLccFuncPart3(boolean enable, View view, final boolean isVuiAction) {
        clearExamEventObserver();
        if (this.mScuVm.getLccState() == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else if (checkIfXpuAvailable(view, isVuiAction) && isParkStallGoAhead(view, isVuiAction)) {
            this.mScuVm.setLccEnable(enable);
        }
    }

    private void confirmApaFuncPart1(boolean enable, View view, final boolean isVuiAction) {
        if (checkIfXpuAvailable(view, isVuiAction)) {
            if (isVuiAction && enable && !this.mAccountVm.isLogin()) {
                vuiFeedback(R.string.blank, view);
            }
            clearExamEventObserver();
            if (!enable || checkUserLoginState()) {
                confirmApaFuncPart2(enable, view, isVuiAction);
                return;
            }
            this.mPendingApaSw = true;
            this.mPendApaView = view;
            this.mPendApaIsVuiAction = isVuiAction;
        }
    }

    private void confirmApaFuncPart2(boolean enable, View view, final boolean isVuiAction) {
        if (!enable || checkApaSafeExamResult()) {
            confirmApaFuncPart3(enable, view, isVuiAction);
            return;
        }
        this.mPendingApaSw = true;
        this.mPendApaView = view;
        this.mPendApaIsVuiAction = isVuiAction;
        requestExamTaskInfo(4L, true);
    }

    private boolean checkApaSafeExamResult() {
        boolean apaSafeExamResult = this.mScuVm.getApaSafeExamResult();
        LogUtils.i(TAG, "get apa exam result from local =" + apaSafeExamResult, false);
        return apaSafeExamResult;
    }

    private void confirmApaFuncPart3(boolean enable, View view, final boolean isVuiAction) {
        clearExamEventObserver();
        if (checkIfXpuAvailable(view, isVuiAction)) {
            ScuResponse autoParkSw = this.mScuVm.getAutoParkSw();
            if (autoParkSw == ScuResponse.FAIL) {
                showFunctionFailTip(view, isVuiAction);
            } else if (isParkStallGoAhead(view, isVuiAction)) {
                this.mScuVm.setAutoParkSw(autoParkSw != ScuResponse.ON);
            }
        }
    }

    private void confirmCngpFuncPart1(boolean enable, View view, final boolean isVuiAction) {
        int xpuXpilotState = this.mScuVm.getXpuXpilotState();
        if (xpuXpilotState == 0) {
            showXPilotCustomTip(view, isVuiAction, R.string.xpilot_st_init);
        } else if (xpuXpilotState != 1) {
            if (xpuXpilotState != 3) {
                return;
            }
            showXPilotCustomTip(view, isVuiAction, R.string.xpilot_st_not_activated);
        } else if (checkIfXpuAvailable(view, isVuiAction)) {
            if (isVuiAction && enable && !this.mAccountVm.isLogin()) {
                vuiFeedback(R.string.blank, view);
            }
            clearExamEventObserver();
            if (!enable || checkUserLoginState()) {
                confirmCngpFuncPart2(enable, view, isVuiAction);
                return;
            }
            this.mPendingCngpSw = true;
            this.mPendCngpView = view;
            this.mPendCngpIsVuiAction = isVuiAction;
        }
    }

    private void confirmCngpFuncPart2(boolean enable, View view, final boolean isVuiAction) {
        if (!enable || checkCngpSafeExamResult()) {
            confirmCngpFuncPart3(enable, view, isVuiAction);
            return;
        }
        this.mPendingCngpSw = true;
        this.mPendCngpView = view;
        this.mPendCngpIsVuiAction = isVuiAction;
        requestExamTaskInfo(6L, true);
    }

    private void confirmCngpFuncPart3(boolean enable, View view, final boolean isVuiAction) {
        clearExamEventObserver();
        if (checkIfXpuAvailable(view, isVuiAction)) {
            ScuResponse cityNgpSt = this.mXpuVm.getCityNgpSt();
            if (cityNgpSt == ScuResponse.FAIL) {
                showFunctionFailTip(view, isVuiAction);
            } else if (isParkStallGoAhead(view, isVuiAction)) {
                if (cityNgpSt == ScuResponse.OFF && this.mScuVm.getNgpState() != ScuResponse.ON) {
                    showXPilotCustomTip(view, isVuiAction, R.string.cngp_open_fail_due_ngp_off);
                } else {
                    this.mXpuVm.setCityNgpSw(cityNgpSt != ScuResponse.ON);
                }
            }
        }
    }

    private boolean checkCngpSafeExamResult() {
        boolean cngpSafeExamResult = this.mXpuVm.getCngpSafeExamResult();
        LogUtils.i(TAG, "get cngp exam result from local =" + cngpSafeExamResult, false);
        return cngpSafeExamResult;
    }

    private void confirmLccFunc(boolean enable, View view, final boolean isVuiAction) {
        ScuResponse lccState = this.mScuVm.getLccState();
        if (lccState == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else if (checkIfXpuAvailable(view, isVuiAction)) {
            if (isNotParkStall()) {
                showNotParkStallTip(view, isVuiAction);
            } else if (lccState == ScuResponse.OFF) {
                if (CarBaseConfig.getInstance().isSupportXpilotLccBindLdw() && this.mScuVm.getLdwState() != ScuResponse.ON) {
                    showXPilotCustomTip(view, isVuiAction, R.string.lcc_open_ldw_first);
                } else {
                    showLccVideoDialog(view, isVuiAction);
                }
            } else {
                this.mScuVm.setLccEnable(false);
            }
        }
    }

    private void showLccVideoDialog(final View view, final boolean isVuiAction) {
        if (!this.mScuVm.isLccVideoWatched()) {
            LogUtils.i(TAG, "show lcc video dialog", false);
            if (this.mLccVideoDialog == null) {
                this.mLccVideoDialog = XPilotVideoDialog.createLccVideoDialog();
            }
            this.mLccVideoDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$4lxM3qDl495l963jO3fW7Yzueyc
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    XPilotFragment.this.lambda$showLccVideoDialog$7$XPilotFragment(dialogInterface);
                }
            });
            this.mLccVideoDialog.show();
            this.mLccVideoDialog.setPositiveButton(getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$JlAETMuHe4k-l0AjFMR-QI_4xqg
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    XPilotFragment.this.lambda$showLccVideoDialog$8$XPilotFragment(isVuiAction, view, xDialog, i);
                }
            });
            return;
        }
        LogUtils.i(TAG, "lcc video has been watched.", false);
        showLccConfirmDialog(isVuiAction, view);
    }

    public /* synthetic */ void lambda$showLccVideoDialog$7$XPilotFragment(DialogInterface dialog) {
        this.mLccVideoDialog.release();
    }

    public /* synthetic */ void lambda$showLccVideoDialog$8$XPilotFragment(final boolean isVuiAction, final View view, XDialog xDialog, int i) {
        this.mScuVm.setLccVideoWatched(true);
        showLccConfirmDialog(isVuiAction, view);
        LogUtils.i(TAG, "confirm for learn lcc video complete.", false);
    }

    private void showLccConfirmDialog(final boolean isVuiAction, final View view) {
        showCustomConfirmDialog(R.string.laa_feature_title, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$Buj066CmWijMbWS_Y8W6PNl5K4E
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                XPilotFragment.this.lambda$showLccConfirmDialog$9$XPilotFragment(view, isVuiAction, xDialog, i);
            }
        }, 0, R.string.laa_feature_confirm_text, R.drawable.img_xpilot_laa_confirm, R.string.laa_feature_confirm_text2, 10, VuiManager.SCENE_XPILOT_DIALOG_LCC);
    }

    public /* synthetic */ void lambda$showLccConfirmDialog$9$XPilotFragment(final View view, final boolean isVuiAction, XDialog xDialog, int i) {
        if (isParkStallGoAhead(view, isVuiAction)) {
            this.mScuVm.setLccEnable(true);
        }
    }

    private boolean checkIfXpuAvailable(View view, boolean isVuiAction) {
        return checkIfXpuAvailable(view, isVuiAction, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0033 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkIfXpuAvailable(android.view.View r4, boolean r5, boolean r6) {
        /*
            r3 = this;
            java.lang.String r0 = "persist.sys.xiaopeng.XPU"
            boolean r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.hasFeature(r0)
            r1 = 0
            if (r0 == 0) goto L2a
            int[] r0 = com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotFragment.AnonymousClass3.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState
            com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel r2 = r3.mXpuVm
            com.xiaopeng.carcontrol.viewmodel.xpu.NedcState r2 = r2.getNedcState()
            int r2 = r2.ordinal()
            r0 = r0[r2]
            r2 = 3
            if (r0 == r2) goto L27
            r2 = 4
            if (r0 == r2) goto L27
            r2 = 5
            if (r0 == r2) goto L27
            r2 = 6
            if (r0 == r2) goto L24
            goto L2a
        L24:
            int r0 = com.xiaopeng.carcontrolmodule.R.string.xpilot_open_fail_xpu_turning_on
            goto L2b
        L27:
            int r0 = com.xiaopeng.carcontrolmodule.R.string.xpilot_open_fail_xpu_off
            goto L2b
        L2a:
            r0 = r1
        L2b:
            if (r0 == 0) goto L33
            if (r6 == 0) goto L32
            r3.showXPilotCustomTip(r4, r5, r0)
        L32:
            return r1
        L33:
            r4 = 1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotFragment.checkIfXpuAvailable(android.view.View, boolean, boolean):boolean");
    }

    private void confirmAlcFunc(boolean enable, final View view, final boolean isVuiAction) {
        ScuResponse alcState = this.mScuVm.getAlcState();
        if (alcState == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else if (checkIfXpuAvailable(view, isVuiAction)) {
            if (!BaseFeatureOption.getInstance().isSupportOpenAlcWhenNotInPGear() && isNotParkStall()) {
                showNotParkStallTip(view, isVuiAction);
            } else if (alcState == ScuResponse.OFF && this.mScuVm.getLccState() != ScuResponse.ON) {
                showXPilotCustomTip(view, isVuiAction, R.string.xpilot_alc_tips_need_lcc);
            } else if (alcState == ScuResponse.OFF) {
                showCustomConfirmDialog(getStringById(R.string.lca_feature_title), getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$1ekNFxwPyUpxNDNQoHfHu_0auHg
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        XPilotFragment.this.lambda$confirmAlcFunc$10$XPilotFragment(view, isVuiAction, xDialog, i);
                    }
                }, 0, isSupportEnhanceLidar() ? getStringById(R.string.lca_lidar_feature_confirm_text) : getStringById(R.string.lca_feature_confirm_text, Integer.valueOf(BaseFeatureOption.getInstance().getAlcSpeed())), 0, (String) null, 10, VuiManager.SCENE_XPILOT_DIALOG_ALC);
            } else {
                this.mScuVm.setAlcEnable(false);
            }
        }
    }

    public /* synthetic */ void lambda$confirmAlcFunc$10$XPilotFragment(final View view, final boolean isVuiAction, XDialog xDialog, int i) {
        if (!BaseFeatureOption.getInstance().isSupportOpenAlcWhenNotInPGear() && isNotParkStall()) {
            showNotParkStallTip(view, isVuiAction);
        } else {
            this.mScuVm.setAlcEnable(true);
        }
    }

    private void confirmNgpFuncPart1(boolean enable, View view, boolean isVuiAction) {
        int xpuXpilotState = this.mScuVm.getXpuXpilotState();
        if (xpuXpilotState == 0) {
            showXPilotCustomTip(view, isVuiAction, R.string.xpilot_st_init);
        } else if (xpuXpilotState != 1) {
            if (xpuXpilotState != 3) {
                return;
            }
            showXPilotCustomTip(view, isVuiAction, R.string.xpilot_st_not_activated);
        } else if (checkIfXpuAvailable(view, isVuiAction)) {
            clearExamEventObserver();
            if (!enable || checkUserLoginState()) {
                confirmNgpFuncPart2(enable, view, isVuiAction);
                return;
            }
            this.mPendingNgpSw = enable;
            this.mPendNgpView = view;
            this.mPendNgpIsVuiAction = isVuiAction;
        }
    }

    private boolean checkUserLoginState() {
        return this.mAccountVm.isLogin();
    }

    private void confirmNgpFuncPart2(boolean enable, View view, boolean isVuiAction) {
        if (!enable || checkNgpSafeExamResult()) {
            confirmNgpFuncPart3(enable, view, isVuiAction);
            return;
        }
        this.mPendingNgpSw = enable;
        this.mPendNgpView = view;
        this.mPendNgpIsVuiAction = isVuiAction;
        requestExamTaskInfo(1L, true);
    }

    private boolean checkNgpSafeExamResult() {
        boolean ngpSafeExamResult = this.mScuVm.getNgpSafeExamResult();
        LogUtils.i(TAG, "get exam result from local,pass the exam? " + ngpSafeExamResult, false);
        return ngpSafeExamResult;
    }

    private boolean checkMemSafeExamResult() {
        boolean memParkSafeExamResult = this.mScuVm.getMemParkSafeExamResult();
        LogUtils.i(TAG, "get mem exam result from local,pass the exam? " + memParkSafeExamResult, false);
        return memParkSafeExamResult;
    }

    private void watchTaskFailure() {
        setLiveDataObserver(this.mAccountVm.getTaskFailure(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$YZ6YMPYnn1LJkhQQAQf_AGMq2OA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XPilotFragment.lambda$watchTaskFailure$11((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$watchTaskFailure$11(Integer failure) {
        if (failure == null) {
            return;
        }
        LogUtils.i(TAG, "get info from server failure. reason :" + failure, false);
        if (failure.intValue() == 3) {
            NotificationHelper.getInstance().showToast(R.string.ngp_open_failure);
        } else if (failure.intValue() == 1 || failure.intValue() == 2) {
            NotificationHelper.getInstance().showToast(R.string.ngp_open_failure_because_of_network);
        }
    }

    private void requestExamTaskInfo(final long taskId, boolean needToast) {
        LogUtils.i(TAG, "request exam info form server", false);
        if (needToast) {
            NotificationHelper.getInstance().showToast(R.string.ngp_verify_permission);
        }
        watchTaskFailure();
        setLiveDataObserver(this.mAccountVm.getTaskResult(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$fYq0uJgc0y_oQy3svS4NZG8x2ZM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XPilotFragment.this.lambda$requestExamTaskInfo$12$XPilotFragment(taskId, (Integer) obj);
            }
        });
        this.mAccountVm.getTaskResult(taskId, false);
    }

    public /* synthetic */ void lambda$requestExamTaskInfo$12$XPilotFragment(final long taskId, Integer result) {
        if (result == null) {
            return;
        }
        LogUtils.i(TAG, "check ngp exam from server: result" + result);
        if (result.intValue() == 1) {
            if (taskId == 1) {
                confirmNgpFuncPart3(this.mPendingNgpSw, this.mPendNgpView, this.mPendNgpIsVuiAction);
            } else if (taskId == 4) {
                confirmApaFuncPart3(this.mPendingApaSw, this.mPendApaView, this.mPendApaIsVuiAction);
            } else if (taskId == 6) {
                confirmCngpFuncPart3(this.mPendingCngpSw, this.mPendCngpView, this.mPendCngpIsVuiAction);
            }
        } else if (!isNotParkStall()) {
            showOrUpdateExamQrCodeDialog(null, taskId);
            requestExamQrCode(taskId);
        } else if (taskId == 1) {
            showNotParkStallTip(this.mPendNgpView, this.mPendNgpIsVuiAction);
        } else if (taskId == 4) {
            showNotParkStallTip(this.mPendApaView, this.mPendApaIsVuiAction);
        } else if (taskId == 6) {
            showNotParkStallTip(this.mPendCngpView, this.mPendCngpIsVuiAction);
        }
    }

    private void requestExamQrCode(final long taskId) {
        setLiveDataObserver(this.mAccountVm.getTaskQrCode(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$Q62RxW3lMMdU4B6yRknRMJMwGKk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XPilotFragment.this.lambda$requestExamQrCode$13$XPilotFragment(taskId, (ExamUrlResult) obj);
            }
        });
        this.mAccountVm.getTaskQrCode(taskId);
    }

    public /* synthetic */ void lambda$requestExamQrCode$13$XPilotFragment(final long taskId, ExamUrlResult examUrl) {
        if (examUrl == null) {
            return;
        }
        showOrUpdateExamQrCodeDialog(examUrl, taskId);
    }

    private void showOrUpdateExamQrCodeDialog(ExamUrlResult examResult, long taskId) {
        if (getActivity() == null) {
            LogUtils.w(TAG, "showOrUpdateExamQrCodeDialog failed, not attached to activity", false);
        } else if (examResult == null) {
            LogUtils.i(TAG, "Just show dialog,and show loading ...", false);
            if (getXpilotExamDialog().isShowing()) {
                return;
            }
            getXpilotExamDialog().showLoading(taskId);
            requestExamResultLoop(taskId);
        } else if (!getXpilotExamDialog().isShowing()) {
            LogUtils.i(TAG, "Exam Qr Dialog is dismiss,ignore the result." + examResult.toString(), false);
        } else {
            LogUtils.i(TAG, "Dialog is show, update the result." + examResult.toString(), false);
            if (examResult.examState == 1) {
                getXpilotExamDialog().updateExamUrl(examResult.examUrl, taskId);
            } else {
                getXpilotExamDialog().updateErrorState(taskId);
            }
        }
    }

    private XpilotExamDialog getXpilotExamDialog() {
        if (this.mXpilotExamDialog == null) {
            XpilotExamDialog xpilotExamDialog = new XpilotExamDialog(App.getInstance().getApplicationContext());
            this.mXpilotExamDialog = xpilotExamDialog;
            xpilotExamDialog.setExamDialogListener(new XpilotExamDialog.ExamDialogListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotFragment.2
                @Override // com.xiaopeng.carcontrol.view.dialog.XpilotExamDialog.ExamDialogListener
                public void retry(long taskId) {
                    XPilotFragment.this.mAccountVm.getTaskQrCode(taskId);
                }

                @Override // com.xiaopeng.carcontrol.view.dialog.XpilotExamDialog.ExamDialogListener
                public void dismissDialog() {
                    XPilotFragment.this.mAccountVm.cancelLoop();
                }
            });
        }
        return this.mXpilotExamDialog;
    }

    private void showXPilotStudyDialog(String studyUrl, String title, String content) {
        if (getActivity() == null) {
            LogUtils.w(TAG, "showXPilotStudyDialog failed, not attached to activity", false);
            return;
        }
        LogUtils.i(TAG, "studyUrl:" + studyUrl);
        getXpilotExamDialog().showStudyDialog(studyUrl, title, content);
    }

    private void requestExamResultLoop(final long taskId) {
        setLiveDataObserver(this.mAccountVm.getTaskLoopResult(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$qHU7CU4PaI_76Bk3lXuiR6RF6HQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XPilotFragment.this.lambda$requestExamResultLoop$14$XPilotFragment(taskId, (Integer) obj);
            }
        });
        this.mAccountVm.getTaskResult(taskId, true);
    }

    public /* synthetic */ void lambda$requestExamResultLoop$14$XPilotFragment(final long taskId, Integer loopResult) {
        if (loopResult != null && loopResult.intValue() == 1) {
            if (taskId == 1) {
                confirmNgpFuncPart3(this.mPendingNgpSw, this.mPendNgpView, this.mPendNgpIsVuiAction);
            } else if (taskId == 2 || taskId == 8) {
                confirmLccFuncPart3(this.mPendingLccSw, this.mPendLccView, this.mPendLccIsVuiAction);
            } else if (taskId == 3 || taskId == 7) {
                confirmMemParkFuncPart3(this.mPendingMemSw, this.mPendMemView, this.mPendMemIsVuiAction);
            } else if (taskId == 4) {
                confirmApaFuncPart3(this.mPendingApaSw, this.mPendApaView, this.mPendApaIsVuiAction);
            } else if (taskId == 6) {
                confirmCngpFuncPart3(this.mPendingCngpSw, this.mPendCngpView, this.mPendCngpIsVuiAction);
            }
            removeObservers(this.mAccountVm.getTaskLoopResult());
            getXpilotExamDialog().autoCloseDialog(taskId);
        }
    }

    private void confirmNgpFuncPart3(boolean enable, View view, boolean isVuiAction) {
        clearExamEventObserver();
        ScuResponse ngpState = this.mScuVm.getNgpState();
        if (ngpState == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
            return;
        }
        if (checkIfXpuAvailable(view, isVuiAction, true)) {
            if (isNotParkStall()) {
                showNotParkStallTip(view, isVuiAction);
            } else if (this.mScuVm.getLccState() != ScuResponse.ON || this.mScuVm.getAlcState() != ScuResponse.ON) {
                showXPilotCustomTip(view, isVuiAction, R.string.ngp_open_fail);
            } else {
                this.mScuVm.setNgpEnable(ngpState == ScuResponse.OFF);
            }
        }
    }

    private void confirmXpuLowPower(final boolean enable, final View view, final boolean isVuiAction) {
        if (this.mXpuVm.getNedcState() == NedcState.Failure) {
            showFunctionFailTip(view, isVuiAction);
        } else {
            showDialog(R.string.xpu_low_power_feature_title, enable ? R.string.xpu_low_power_feature_positive_msg : R.string.xpu_low_power_feature_negative_msg, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$7f7I_QhuMZwORvM-WjwK2ScMtw0
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    XPilotFragment.this.lambda$confirmXpuLowPower$15$XPilotFragment(view, isVuiAction, enable, xDialog, i);
                }
            }, VuiManager.SCENE_XPILOT_DIALOG_XPU_LOW_POWER);
        }
    }

    public /* synthetic */ void lambda$confirmXpuLowPower$15$XPilotFragment(final View view, final boolean isVuiAction, final boolean enable, XDialog xDialog, int i) {
        if (isParkStallGoAhead(view, isVuiAction)) {
            this.mXpuVm.setNedcSwitch(enable);
        }
    }

    private void confirmAutoParkFunc(boolean enable, final View view, final boolean isVuiAction) {
        if (checkIfXpuAvailable(view, isVuiAction)) {
            ScuResponse autoParkSw = this.mScuVm.getAutoParkSw();
            if (autoParkSw == ScuResponse.FAIL) {
                showFunctionFailTip(view, isVuiAction);
            } else if (autoParkSw == ScuResponse.ON) {
                if (isParkStallGoAhead(view, isVuiAction)) {
                    this.mScuVm.setAutoParkSw(false);
                }
            } else if (checkIfXpuAvailable(view, isVuiAction)) {
                showDialog(R.string.auto_park_sw, R.string.auto_park_feedback, getStringById(R.string.btn_cancel), null, getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$gmV4SkjpNhq6CW9ryAF6T9Bqq-A
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        XPilotFragment.this.lambda$confirmAutoParkFunc$16$XPilotFragment(view, isVuiAction, xDialog, i);
                    }
                }, VuiManager.SCENE_XPILOT_DIALOG_AUTO_PARK, R.style.confirm_auto_park_func_dialog);
            }
        }
    }

    public /* synthetic */ void lambda$confirmAutoParkFunc$16$XPilotFragment(final View view, final boolean isVuiAction, XDialog xDialog, int i) {
        if (isParkStallGoAhead(view, isVuiAction)) {
            this.mScuVm.setAutoParkSw(true);
        }
    }

    private void confirmMemParkFuncPart1(boolean enable, View view, boolean isVuiAction) {
        int xpuXpilotState = this.mScuVm.getXpuXpilotState();
        if (xpuXpilotState == 0) {
            showXPilotCustomTip(view, isVuiAction, R.string.mem_park_xpilot_st_init);
        } else if (xpuXpilotState != 1) {
            if (xpuXpilotState != 3) {
                return;
            }
            showXPilotCustomTip(view, isVuiAction, R.string.mem_park_xpilot_st_not_activated);
        } else if (checkIfXpuAvailable(view, isVuiAction)) {
            clearExamEventObserver();
            if (!enable || checkUserLoginState()) {
                confirmMemParkFuncPart2(enable, view, isVuiAction);
                return;
            }
            this.mPendingMemSw = enable;
            this.mPendMemView = view;
            this.mPendMemIsVuiAction = isVuiAction;
        }
    }

    private void confirmMemParkFuncPart2(boolean enable, View view, boolean isVuiAction) {
        if (!enable || checkMemSafeExamResult()) {
            confirmMemParkFuncPart3(enable, view, isVuiAction);
            return;
        }
        this.mPendingMemSw = enable;
        this.mPendMemView = view;
        this.mPendMemIsVuiAction = isVuiAction;
        requestMemExamTaskInfo();
    }

    private void requestMemExamTaskInfo() {
        LogUtils.i(TAG, "request mem exam info form server", false);
        NotificationHelper.getInstance().showToast(R.string.laa_verify_permission);
        watchTaskFailure();
        setLiveDataObserver(this.mAccountVm.getTaskListResult(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$Q9YsedPtTTwOjRhvJiWf3sGIbAs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XPilotFragment.this.lambda$requestMemExamTaskInfo$17$XPilotFragment((Integer) obj);
            }
        });
        this.mAccountVm.requestMemRelateExamResult();
    }

    public /* synthetic */ void lambda$requestMemExamTaskInfo$17$XPilotFragment(Integer result) {
        if (result == null) {
            return;
        }
        LogUtils.i(TAG, "check mem from server: result" + result);
        if (result.intValue() == 1) {
            confirmMemParkFuncPart3(this.mPendingMemSw, this.mPendMemView, this.mPendMemIsVuiAction);
        } else if (isNotParkStall()) {
            showNotParkStallTip(this.mPendMemView, this.mPendMemIsVuiAction);
        } else {
            showOrUpdateExamQrCodeDialog(null, getMemTaskId());
            requestExamQrCode(getMemTaskId());
        }
    }

    private long getMemTaskId() {
        return CarBaseConfig.getInstance().isSupportLidar() ? 7L : 3L;
    }

    private void confirmMemParkFuncPart3(boolean enable, View view, boolean isVuiAction) {
        ApResponse memoryParkSw = this.mScuVm.getMemoryParkSw();
        int xpuXpilotState = this.mScuVm.getXpuXpilotState();
        if (xpuXpilotState == 0 || memoryParkSw == ApResponse.AP_INITIAL) {
            showXPilotCustomTip(view, isVuiAction, R.string.mem_park_xpilot_st_init);
        } else if (xpuXpilotState == 3) {
            showXPilotCustomTip(view, isVuiAction, R.string.mem_park_xpilot_st_not_activated);
        } else if (checkIfXpuAvailable(view, isVuiAction)) {
            if (isNotParkStall()) {
                showNotParkStallTip(view, isVuiAction);
            } else if (this.mScuVm.getAutoParkSw() != ScuResponse.ON) {
                showXPilotCustomTip(view, isVuiAction, R.string.mem_park_open_fail);
            } else {
                this.mScuVm.setMemoryParkSw(memoryParkSw != ApResponse.ON);
            }
        }
    }

    private void showMemParkDialog(final boolean needTurnOn) {
        LogUtils.i(TAG, "show mem park dialog for video.", false);
        if (this.mMemParkVideoDialog == null) {
            this.mMemParkVideoDialog = XPilotVideoDialog.createMemParkVideoDialog();
        }
        this.mMemParkVideoDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$nDhEf7Fc6E0as4KxVt41rmnc8Fk
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                XPilotFragment.this.lambda$showMemParkDialog$18$XPilotFragment(dialogInterface);
            }
        });
        this.mMemParkVideoDialog.show();
        this.mMemParkVideoDialog.setPositiveButton(getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$AAIsowZmRXZiki1ALJWKgJNbyaE
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                XPilotFragment.this.lambda$showMemParkDialog$19$XPilotFragment(needTurnOn, xDialog, i);
            }
        });
    }

    public /* synthetic */ void lambda$showMemParkDialog$18$XPilotFragment(DialogInterface dialog) {
        this.mMemParkVideoDialog.release();
    }

    public /* synthetic */ void lambda$showMemParkDialog$19$XPilotFragment(final boolean needTurnOn, XDialog xDialog, int i) {
        this.mScuVm.setMemParkVideoWatched(true);
        if (needTurnOn) {
            this.mScuVm.setMemoryParkSw(true);
        }
        LogUtils.i(TAG, "confirm for learn mem park video complete.", false);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x007d, code lost:
        if (r3.equals(com.xiaopeng.carcontrol.bean.xpilot.XPilotItem.KEY_SIMPLE_SAS) == false) goto L12;
     */
    @Override // com.xiaopeng.carcontrol.view.XPilotOperateInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onTabItemChanged(int r7, int r8, android.view.View r9) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotFragment.onTabItemChanged(int, int, android.view.View):void");
    }

    @Override // com.xiaopeng.carcontrol.view.XPilotOperateInterface
    public void onInfoClicked(int position) {
        XPilotItem<?> xPilotItem = this.mItems.get(position);
        if ((xPilotItem instanceof XPilotCardItem) || (xPilotItem instanceof XPilotTabItem)) {
            DialogInfoHelper.getInstance().showXPilotInfoPanel(getActivity(), xPilotItem, (DialogInterface.OnShowListener) null, (DialogInterface.OnDismissListener) null);
        }
    }

    private boolean isSwitchNotChange(XPilotItem item, ScuResponse state) {
        return isSwitchNotChange(item, state == ScuResponse.ON || state == ScuResponse.INITIALIZATION);
    }

    private boolean isSwitchNotChange(XPilotItem item, boolean newState) {
        return ((XPilotCardItem) item).getValue().booleanValue() == newState;
    }

    private void initXPilotFunc() {
        int size = this.mItems.size();
        LogUtils.i(TAG, "XPilot item count: " + size, false);
        NedcState nedcState = this.mXpuVm.getNedcState();
        boolean isSupportXpu = CarBaseConfig.getInstance().isSupportXpu();
        for (final int i = 0; i < size; i++) {
            final XPilotItem xPilotItem = this.mItems.get(i);
            if (1 != xPilotItem.getType() && xPilotItem.isFunAvailable()) {
                String key = xPilotItem.getKey();
                key.hashCode();
                char c = 65535;
                switch (key.hashCode()) {
                    case -1659103951:
                        if (key.equals(XPilotItem.KEY_CNGP_MAP)) {
                            c = 0;
                            break;
                        }
                        break;
                    case -1431243816:
                        if (key.equals(XPilotItem.KEY_SIMPLE_SAS)) {
                            c = 1;
                            break;
                        }
                        break;
                    case -1242836200:
                        if (key.equals(XPilotItem.KEY_XPU_LOW_POWER)) {
                            c = 2;
                            break;
                        }
                        break;
                    case -872077473:
                        if (key.equals(XPilotItem.KEY_SPECIAL_SAS)) {
                            c = 3;
                            break;
                        }
                        break;
                    case -651897836:
                        if (key.equals(XPilotItem.KEY_MEM_PARK)) {
                            c = 4;
                            break;
                        }
                        break;
                    case 96664:
                        if (key.equals(XPilotItem.KEY_ALC)) {
                            c = 5;
                            break;
                        }
                        break;
                    case 96979:
                        if (key.equals(XPilotItem.KEY_AVH)) {
                            c = 6;
                            break;
                        }
                        break;
                    case 97843:
                        if (key.equals(XPilotItem.KEY_BSD)) {
                            c = 7;
                            break;
                        }
                        break;
                    case 99660:
                        if (key.equals(XPilotItem.KEY_DOW)) {
                            c = '\b';
                            break;
                        }
                        break;
                    case 100218:
                        if (key.equals(XPilotItem.KEY_EBW)) {
                            c = '\t';
                            break;
                        }
                        break;
                    case 100516:
                        if (key.equals(XPilotItem.KEY_ELK)) {
                            c = '\n';
                            break;
                        }
                        break;
                    case 100738:
                        if (key.equals(XPilotItem.KEY_ESP)) {
                            c = 11;
                            break;
                        }
                        break;
                    case 101210:
                        if (key.equals(XPilotItem.KEY_FCW)) {
                            c = '\f';
                            break;
                        }
                        break;
                    case 103143:
                        if (key.equals(XPilotItem.KEY_HDC)) {
                            c = '\r';
                            break;
                        }
                        break;
                    case 106956:
                        if (key.equals(XPilotItem.KEY_LCC)) {
                            c = 14;
                            break;
                        }
                        break;
                    case 107007:
                        if (key.equals(XPilotItem.KEY_LDW)) {
                            c = 15;
                            break;
                        }
                        break;
                    case 107468:
                        if (key.equals(XPilotItem.KEY_LSS)) {
                            c = 16;
                            break;
                        }
                        break;
                    case 109015:
                        if (key.equals(XPilotItem.KEY_NGP)) {
                            c = 17;
                            break;
                        }
                        break;
                    case 109341:
                        if (key.equals(XPilotItem.KEY_NRA)) {
                            c = 18;
                            break;
                        }
                        break;
                    case 112742:
                        if (key.equals(XPilotItem.KEY_RCW)) {
                            c = 19;
                            break;
                        }
                        break;
                    case 114177:
                        if (key.equals(XPilotItem.KEY_SSA)) {
                            c = 20;
                            break;
                        }
                        break;
                    case 115187:
                        if (key.equals("tts")) {
                            c = 21;
                            break;
                        }
                        break;
                    case 3058324:
                        if (key.equals(XPilotItem.KEY_CNGP)) {
                            c = 22;
                            break;
                        }
                        break;
                    case 3242015:
                        if (key.equals(XPilotItem.KEY_ISLA)) {
                            c = 23;
                            break;
                        }
                        break;
                    case 3242017:
                        if (key.equals(XPilotItem.KEY_ISLC)) {
                            c = 24;
                            break;
                        }
                        break;
                    case 3492620:
                        if (key.equals(XPilotItem.KEY_RAEB)) {
                            c = 25;
                            break;
                        }
                        break;
                    case 3495006:
                        if (key.equals(XPilotItem.KEY_RCTA)) {
                            c = JSONLexer.EOI;
                            break;
                        }
                        break;
                    case 282296948:
                        if (key.equals(XPilotItem.KEY_XPILOT_TTS)) {
                            c = 27;
                            break;
                        }
                        break;
                    case 467650921:
                        if (key.equals(XPilotItem.KEY_LSS_SEN)) {
                            c = 28;
                            break;
                        }
                        break;
                    case 615553000:
                        if (key.equals(XPilotItem.KEY_NGP_SETTING)) {
                            c = 29;
                            break;
                        }
                        break;
                    case 1661170810:
                        if (key.equals("auto_park")) {
                            c = 30;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        XPilotCardItem xPilotCardItem = (XPilotCardItem) xPilotItem;
                        this.mCNgpMapItem = xPilotCardItem;
                        if (!isSupportXpu) {
                            break;
                        } else {
                            this.mXpuDepItems.add(xPilotCardItem);
                            continue;
                        }
                    case 1:
                        this.mSimpleSasTabItem = (XPilotTabItem) xPilotItem;
                        ScuIslaMode simpleSasMode = this.mXpuVm.getSimpleSasMode();
                        LogUtils.i(TAG, "init key simple_sas, value : " + simpleSasMode, false);
                        this.mSimpleSasTabItem.setValue(Integer.valueOf(simpleSasMode.toDisplayIndex()));
                        setLiveDataObserver(this.mXpuVm.getIslaData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$Hjpcd3LF8mo6rw7qvh3r0EQ8Q9s
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$41$XPilotFragment((ScuIslaMode) obj);
                            }
                        });
                        continue;
                    case 2:
                        updateXpuLowPowerItem((XPilotCardItem) xPilotItem, nedcState, false);
                        setLiveDataObserver(this.mXpuVm.getNedcStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$m-d-aS7b9EZQynZZzxgNuvD6mLs
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$43$XPilotFragment(xPilotItem, i, (NedcState) obj);
                            }
                        });
                        continue;
                    case 3:
                        this.mSpecialSasTabItem = (XPilotTabItem) xPilotItem;
                        int specialSasMode = this.mScuVm.getSpecialSasMode();
                        LogUtils.i(TAG, "init key special_sas, value : " + specialSasMode, false);
                        this.mSpecialSasTabItem.setValue(Integer.valueOf(specialSasMode - 1));
                        setLiveDataObserver(this.mScuVm.getSpecialSasData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$0rG3hzI_NVPNMkfS_MZowwaCHpE
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$42$XPilotFragment((Integer) obj);
                            }
                        });
                        continue;
                    case 4:
                        XPilotCardItem xPilotCardItem2 = (XPilotCardItem) xPilotItem;
                        this.mMemParkSwItem = xPilotCardItem2;
                        if (isSupportXpu) {
                            this.mXpuDepItems.add(xPilotCardItem2);
                        }
                        this.mMemParkSwItem.setIsTestFunc(true);
                        this.mMemParkSwItem.setValue(Boolean.valueOf((!isSupportXpu || parseXpuNedcState(nedcState)) && this.mScuVm.getMemoryParkSw() == ApResponse.ON));
                        setLiveDataObserver(this.mScuVm.getMemoryParkData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$9zM3BhwYJgSoHr94H-MS5SRKU0U
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$34$XPilotFragment(xPilotItem, i, (ApResponse) obj);
                            }
                        });
                        continue;
                    case 5:
                        XPilotCardItem xPilotCardItem3 = (XPilotCardItem) xPilotItem;
                        this.mAlcSwItem = xPilotCardItem3;
                        if (isSupportXpu) {
                            this.mXpuDepItems.add(xPilotCardItem3);
                        }
                        r7 = (!isSupportXpu || parseXpuNedcState(nedcState)) && this.mScuVm.getAlcState() == ScuResponse.ON;
                        this.mAlcSwItem.setValue(Boolean.valueOf(r7));
                        LogUtils.i(TAG, "init key: alc, value: " + r7, false);
                        setLiveDataObserver(this.mScuVm.getAlcData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$QNv4xOEkL-qyUTR0tFNTg3JrD1o
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$37$XPilotFragment(i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case 6:
                        xPilotItem.setValue(Boolean.valueOf(this.mChassisVm.getAvhForUI()));
                        setLiveDataObserver(this.mChassisVm.getAvhSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$JTk3UYt7uJpNr7g7xh45RWsUXkk
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$47$XPilotFragment(xPilotItem, i, (Boolean) obj);
                            }
                        });
                        setLiveDataObserver(this.mChassisVm.getAvhFaultData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$D2I3PvkkMHLRiE9VSdGCORYGTj8
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$48$XPilotFragment(xPilotItem, i, (Boolean) obj);
                            }
                        });
                        continue;
                    case 7:
                        this.mBsdCardItem = (XPilotCardItem) xPilotItem;
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getBsdState() == ScuResponse.ON));
                        setLiveDataObserver(this.mScuVm.getBsdData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$bS1tvIGxmn6b4SGe-xTq1kjysYM
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$21$XPilotFragment(xPilotItem, i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case '\b':
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getDowState() == ScuResponse.ON));
                        setLiveDataObserver(this.mScuVm.getDowData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$17bJBlVqqbNpzn1EhnfKsE7pbYA
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$22$XPilotFragment(xPilotItem, i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case '\t':
                        this.mEbwCardItem = (XPilotCardItem) xPilotItem;
                        xPilotItem.setValue(Boolean.valueOf(this.mChassisVm.getEbw()));
                        setLiveDataObserver(this.mChassisVm.getEbwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$K4iZhFoxaqBY_8-CilRVACViEno
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$49$XPilotFragment(xPilotItem, i, (Boolean) obj);
                            }
                        });
                        continue;
                    case '\n':
                        XPilotCardItem xPilotCardItem4 = (XPilotCardItem) xPilotItem;
                        this.mElkCardItem = xPilotCardItem4;
                        if (isSupportXpu) {
                            this.mXpuDepItems.add(xPilotCardItem4);
                        }
                        if (!isSupportXpu || parseXpuNedcState(nedcState)) {
                            xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getElkState() == ScuResponse.ON));
                        } else {
                            this.mElkCardItem.setValue(false);
                        }
                        setLiveDataObserver(this.mScuVm.getElkData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$rVd_nOlLsAbGEPZcGGALui3TmEo
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$25$XPilotFragment(xPilotItem, i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case 11:
                        this.mEspCardItem = (XPilotCardItem) xPilotItem;
                        xPilotItem.setValue(Boolean.valueOf(this.mChassisVm.getEspForUI()));
                        setLiveDataObserver(this.mChassisVm.getEspModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$1bwVUyHe-xyZN7BS3AxCo62aJ0c
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$45$XPilotFragment(xPilotItem, i, (Boolean) obj);
                            }
                        });
                        setLiveDataObserver(this.mChassisVm.getEspFaultData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$ogBKkZezxZMdw_da1SeXoKsMNoY
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$46$XPilotFragment(xPilotItem, i, (Boolean) obj);
                            }
                        });
                        continue;
                    case '\f':
                        this.mFcwSwItem = (XPilotCardItem) xPilotItem;
                        ScuResponse fcwState = this.mScuVm.getFcwState();
                        if (fcwState != ScuResponse.ON && fcwState != ScuResponse.INITIALIZATION) {
                            r7 = false;
                        }
                        xPilotItem.setValue(Boolean.valueOf(r7));
                        updateFcwState(fcwState);
                        setLiveDataObserver(this.mScuVm.getFcwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$C2JkuYE98kR6cN8H53AID-xNDmA
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$20$XPilotFragment(xPilotItem, i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case '\r':
                        xPilotItem.setValue(Boolean.valueOf(this.mChassisVm.getHdc()));
                        setLiveDataObserver(this.mChassisVm.getHdcData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$IrhEI9pe80q-rhK-DAopuVp1h14
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$44$XPilotFragment(xPilotItem, i, (Boolean) obj);
                            }
                        });
                        continue;
                    case 14:
                        XPilotCardItem xPilotCardItem5 = (XPilotCardItem) xPilotItem;
                        this.mLccSwItem = xPilotCardItem5;
                        if (isSupportXpu) {
                            this.mXpuDepItems.add(xPilotCardItem5);
                        }
                        r7 = (!isSupportXpu || parseXpuNedcState(nedcState)) && this.mScuVm.getLccState() == ScuResponse.ON;
                        this.mLccSwItem.setValue(Boolean.valueOf(r7));
                        LogUtils.i(TAG, "init key: lcc, value: " + r7, false);
                        setLiveDataObserver(this.mScuVm.getLccData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$DnWHeTSyugy9qPycGXPZnJNOs4g
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$35$XPilotFragment(i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case 15:
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getLdwState() == ScuResponse.ON));
                        setLiveDataObserver(this.mScuVm.getLdwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$0tTXVHVmKDzHyDUm7fh8-38x1v8
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$26$XPilotFragment(xPilotItem, i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case 16:
                        XPilotTabItem xPilotTabItem = (XPilotTabItem) xPilotItem;
                        this.mLssTabItem = xPilotTabItem;
                        if (isSupportXpu) {
                            this.mXpuDepItems.add(xPilotTabItem);
                        }
                        if (isSupportXpu && !parseXpuNedcState(nedcState)) {
                            this.mLssTabItem.setValue(0);
                        } else {
                            this.mLssTabItem.setValue(Integer.valueOf(this.mScuVm.getLssMode().toDisplayIndex()));
                        }
                        setLiveDataObserver(this.mScuVm.getLssModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$C0AziMuEY88fz6EqTnix7Tg13WI
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$23$XPilotFragment(i, (ScuLssMode) obj);
                            }
                        });
                        continue;
                    case 17:
                        XPilotCardItem xPilotCardItem6 = (XPilotCardItem) xPilotItem;
                        this.mNgpSwItem = xPilotCardItem6;
                        if (isSupportXpu) {
                            this.mXpuDepItems.add(xPilotCardItem6);
                        }
                        this.mNgpSwItem.setIsTestFunc(true);
                        this.mNgpSwItem.setValue(Boolean.valueOf((!isSupportXpu || parseXpuNedcState(nedcState)) && this.mScuVm.getNgpState() == ScuResponse.ON));
                        setLiveDataObserver(this.mScuVm.getNgpData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$FFv-X5gqHPfDcDAm4VliNFfzr5Q
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$38$XPilotFragment(i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case 18:
                        if (xPilotItem instanceof XPilotCardItem) {
                            if (isSupportXpu) {
                                this.mXpuDepItems.add((XPilotCardItem) xPilotItem);
                            }
                            r7 = (parseXpuNedcState(nedcState) && this.mXpuVm.getNraSw()) ? false : false;
                            LogUtils.i(TAG, "init key: nra, value: " + r7, false);
                            xPilotItem.setValue(Boolean.valueOf(r7));
                            setLiveDataObserver(this.mXpuVm.getNraData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$O-gcyMhqJtHdqtFzLTgxnp0OM7I
                                @Override // androidx.lifecycle.Observer
                                public final void onChanged(Object obj) {
                                    XPilotFragment.this.lambda$initXPilotFunc$31$XPilotFragment(xPilotItem, i, (Boolean) obj);
                                }
                            });
                            continue;
                        } else if (xPilotItem instanceof XPilotTabItem) {
                            int nraState = this.mXpuVm.getNraState();
                            LogUtils.i(TAG, "init key: nra, value: " + nraState, false);
                            xPilotItem.setValue(Integer.valueOf(nraState));
                            setLiveDataObserver(this.mXpuVm.getNraStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$TkE4vYp-sBI9LqfcVGjW-3qtU3A
                                @Override // androidx.lifecycle.Observer
                                public final void onChanged(Object obj) {
                                    XPilotFragment.this.lambda$initXPilotFunc$32$XPilotFragment(xPilotItem, i, (Integer) obj);
                                }
                            });
                            break;
                        } else {
                            break;
                        }
                    case 19:
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getRcwState() == ScuResponse.ON));
                        setLiveDataObserver(this.mScuVm.getRcwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$4KvZw5_J8fS145eb4x8Lzpr9SRw
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$28$XPilotFragment(xPilotItem, i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case 20:
                        boolean ssaSw = this.mVcuVm.getSsaSw();
                        LogUtils.i(TAG, "init key: ssa, value: " + ssaSw, false);
                        xPilotItem.setValue(Boolean.valueOf(ssaSw));
                        setLiveDataObserver(this.mVcuVm.getSsaSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$011SmEkO10kFT5ZJ6SJXeW3phi4
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$30$XPilotFragment(xPilotItem, i, (Boolean) obj);
                            }
                        });
                        continue;
                    case 21:
                        xPilotItem.setValue(Integer.valueOf(this.mScuVm.getTtsBroadcastType()));
                        continue;
                    case 22:
                        XPilotCardItem xPilotCardItem7 = (XPilotCardItem) xPilotItem;
                        this.mCNgpSwItem = xPilotCardItem7;
                        if (isSupportXpu) {
                            this.mXpuDepItems.add(xPilotCardItem7);
                        }
                        this.mCNgpSwItem.setIsTestFunc(true);
                        this.mCNgpSwItem.setValue(Boolean.valueOf((!isSupportXpu || parseXpuNedcState(nedcState)) && this.mXpuVm.getCityNgpSt() == ScuResponse.ON));
                        setLiveDataObserver(this.mXpuVm.getCityNgpData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$dbzHUHv5Rc3IvWg1tf-IkvV8bto
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$39$XPilotFragment(i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case 23:
                        this.mIslaTabItem = (XPilotTabItem) xPilotItem;
                        ScuIslaMode islaMode = this.mScuVm.getIslaMode();
                        LogUtils.i(TAG, "init key isla, value : " + islaMode, false);
                        this.mIslaTabItem.setValue(Integer.valueOf(islaMode.toDisplayIndex()));
                        setLiveDataObserver(this.mScuVm.getIslaData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$3XAYS5D9wysfn6i2DdtamFClPJo
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$40$XPilotFragment((ScuIslaMode) obj);
                            }
                        });
                        continue;
                    case 24:
                        if (isSupportXpu) {
                            this.mXpuDepItems.add((XPilotCardItem) xPilotItem);
                        }
                        xPilotItem.setValue(Boolean.valueOf((!isSupportXpu || parseXpuNedcState(nedcState)) && this.mScuVm.getIslcState() == ScuResponse.ON));
                        setLiveDataObserver(this.mScuVm.getIslcData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$r6Euf-uMEHJ0Fed4l3nrDn4GE8s
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$36$XPilotFragment(xPilotItem, i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case 25:
                        xPilotItem.setValue(Boolean.valueOf(this.mXpuVm.getRaebState() == ScuResponse.ON));
                        setLiveDataObserver(this.mXpuVm.getRaebData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$3_mM7q7jpMfsdNJmSnGCaFgqzB0
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$27$XPilotFragment(xPilotItem, i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case 26:
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getRctaState() == ScuResponse.ON));
                        setLiveDataObserver(this.mScuVm.getRctaData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$_lYx5kv6fA455IqkUwQN68ZTpVM
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$29$XPilotFragment(xPilotItem, i, (ScuResponse) obj);
                            }
                        });
                        continue;
                    case 27:
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.isAutoPilotNeedTts()));
                        continue;
                    case 28:
                        XPilotTabItem xPilotTabItem2 = (XPilotTabItem) xPilotItem;
                        this.mLssSenTabItem = xPilotTabItem2;
                        xPilotTabItem2.setValue(Integer.valueOf(this.mXpuVm.getLssSensitivity()));
                        this.mLssSenTabItem.setEnable(this.mScuVm.getLssMode() == ScuLssMode.Lka);
                        setLiveDataObserver(this.mXpuVm.getLssSenData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$lRdml7_zFafcH4Yw7QRRzQHFHt4
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$24$XPilotFragment(i, (LssSensitivity) obj);
                            }
                        });
                        continue;
                    case 29:
                        LogUtils.i(TAG, "init key: ngp_setting", false);
                        this.mNgpSettingPosition = i;
                        XPilotCardItem xPilotCardItem8 = (XPilotCardItem) xPilotItem;
                        this.mNgpSettingItem = xPilotCardItem8;
                        if (isSupportXpu) {
                            this.mXpuDepItems.add(xPilotCardItem8);
                            break;
                        } else {
                            continue;
                        }
                    case 30:
                        XPilotCardItem xPilotCardItem9 = (XPilotCardItem) xPilotItem;
                        this.mAutoParkSwItem = xPilotCardItem9;
                        if (isSupportXpu) {
                            this.mXpuDepItems.add(xPilotCardItem9);
                        }
                        r7 = (!isSupportXpu || parseXpuNedcState(nedcState)) && this.mScuVm.getAutoParkSw() == ScuResponse.ON;
                        LogUtils.i(TAG, "init key: auto_park, value: " + r7, false);
                        xPilotItem.setValue(Boolean.valueOf(r7));
                        setLiveDataObserver(this.mScuVm.getAutoParkData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$_Polj-FvYUgG8ThHDzoZHKvUqo4
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                XPilotFragment.this.lambda$initXPilotFunc$33$XPilotFragment(xPilotItem, i, (ScuResponse) obj);
                            }
                        });
                        continue;
                }
            }
        }
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        if (carBaseConfig.isSupportSrrMiss() || carBaseConfig.isVpmNotReady() || !carBaseConfig.isSupportNgp()) {
            return;
        }
        LogUtils.i(TAG, "check ngp state");
        int xpuXpilotState = this.mScuVm.getXpuXpilotState();
        if (CarBaseConfig.getInstance().isSupportMemPark()) {
            showMemoryParkActiveState(xpuXpilotState, this.mScuVm.getMemoryParkSw());
        }
        showNgpXpilotActiveState(xpuXpilotState);
        showEnhancedBsdState(xpuXpilotState);
        showEnhancedLidarState(xpuXpilotState);
        setLiveDataObserver(this.mScuVm.getXpuXPilotActiveData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$FON7VQumC4ahuyP4xocPlvgVgp0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XPilotFragment.this.lambda$initXPilotFunc$50$XPilotFragment((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initXPilotFunc$20$XPilotFragment(final XPilotItem item, final int position, ScuResponse state) {
        updateFcwState(state);
        if (!isSwitchNotChange(item, state)) {
            ((XPilotCardItem) item).setValueWithSound(state == ScuResponse.ON || state == ScuResponse.INITIALIZATION, isItemVisible(position));
        }
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$21$XPilotFragment(final XPilotItem item, final int position, ScuResponse state) {
        if (isSwitchNotChange(item, state)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(state == ScuResponse.ON, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$22$XPilotFragment(final XPilotItem item, final int position, ScuResponse state) {
        if (isSwitchNotChange(item, state)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(state == ScuResponse.ON, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$23$XPilotFragment(final int position, ScuLssMode lssMode) {
        XPilotTabItem xPilotTabItem;
        if (isNedcAvailable()) {
            this.mLssTabItem.setValue(Integer.valueOf(lssMode.toDisplayIndex()));
            if (CarBaseConfig.getInstance().isSupportLssSen() && (xPilotTabItem = this.mLssSenTabItem) != null) {
                xPilotTabItem.setEnable(lssMode == ScuLssMode.Lka);
                this.mAdapter.notifyItemRangeChanged(position, 2, Integer.valueOf(position));
                return;
            }
            this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
        }
    }

    public /* synthetic */ void lambda$initXPilotFunc$24$XPilotFragment(final int position, LssSensitivity lssSenMode) {
        this.mLssSenTabItem.setValue(Integer.valueOf(lssSenMode.ordinal()));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$25$XPilotFragment(final XPilotItem item, final int position, ScuResponse state) {
        if (isNedcAvailable() && isSwitchNotChange(item, state)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(state == ScuResponse.ON, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$26$XPilotFragment(final XPilotItem item, final int position, ScuResponse state) {
        if (isSwitchNotChange(item, state)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(state == ScuResponse.ON, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$27$XPilotFragment(final XPilotItem item, final int position, ScuResponse state) {
        if (isSwitchNotChange(item, state)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(state == ScuResponse.ON, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$28$XPilotFragment(final XPilotItem item, final int position, ScuResponse state) {
        if (isSwitchNotChange(item, state)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(state == ScuResponse.ON, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$29$XPilotFragment(final XPilotItem item, final int position, ScuResponse state) {
        if (isSwitchNotChange(item, state)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(state == ScuResponse.ON, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$30$XPilotFragment(final XPilotItem item, final int position, Boolean enabled) {
        if (enabled != null) {
            LogUtils.i("AAA", "Ssa changed: " + enabled, false);
            if (isSwitchNotChange(item, enabled.booleanValue())) {
                return;
            }
            ((XPilotCardItem) item).setValueWithSound(enabled.booleanValue(), isItemVisible(position));
            this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
        }
    }

    public /* synthetic */ void lambda$initXPilotFunc$31$XPilotFragment(final XPilotItem item, final int position, Boolean enabled) {
        if (enabled != null) {
            boolean isNedcAvailable = isNedcAvailable();
            if (isNedcAvailable && isSwitchNotChange(item, enabled.booleanValue())) {
                return;
            }
            ((XPilotCardItem) item).setValueWithSound(isNedcAvailable && enabled.booleanValue(), isItemVisible(position));
            this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
        }
    }

    public /* synthetic */ void lambda$initXPilotFunc$32$XPilotFragment(final XPilotItem item, final int position, Integer state) {
        if (state != null) {
            item.setValue(state);
            this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
        }
    }

    public /* synthetic */ void lambda$initXPilotFunc$33$XPilotFragment(final XPilotItem item, final int position, ScuResponse state) {
        boolean isNedcAvailable = isNedcAvailable();
        if (isNedcAvailable && isSwitchNotChange(item, state)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(isNedcAvailable && state == ScuResponse.ON, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$34$XPilotFragment(final XPilotItem item, final int position, ApResponse state) {
        boolean isNedcAvailable = isNedcAvailable();
        boolean z = true;
        if (isNedcAvailable) {
            if (isSwitchNotChange(item, state == ApResponse.ON)) {
                return;
            }
        }
        XPilotCardItem xPilotCardItem = this.mMemParkSwItem;
        if (!isNedcAvailable || state != ApResponse.ON) {
            z = false;
        }
        xPilotCardItem.setValueWithSound(z, isItemVisible(position));
        showMemoryParkActiveState(this.mScuVm.getXpuXpilotState(), state);
    }

    public /* synthetic */ void lambda$initXPilotFunc$35$XPilotFragment(final int position, ScuResponse state) {
        boolean z = false;
        LogUtils.i(TAG, "update key: lcc, value: " + state, false);
        boolean isNedcAvailable = isNedcAvailable();
        if (isNedcAvailable && isSwitchNotChange(this.mLccSwItem, state)) {
            return;
        }
        XPilotCardItem xPilotCardItem = this.mLccSwItem;
        if (isNedcAvailable && state == ScuResponse.ON) {
            z = true;
        }
        xPilotCardItem.setValueWithSound(z, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
        enableNgpSettingItem();
    }

    public /* synthetic */ void lambda$initXPilotFunc$36$XPilotFragment(final XPilotItem item, final int position, ScuResponse state) {
        boolean isNedcAvailable = isNedcAvailable();
        if (isNedcAvailable && isSwitchNotChange(item, state)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(isNedcAvailable && state == ScuResponse.ON, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$37$XPilotFragment(final int position, ScuResponse state) {
        boolean z = false;
        LogUtils.i(TAG, "update key: alc, value: " + state, false);
        boolean isNedcAvailable = isNedcAvailable();
        if (isNedcAvailable && isSwitchNotChange(this.mAlcSwItem, state)) {
            return;
        }
        XPilotCardItem xPilotCardItem = this.mAlcSwItem;
        if (isNedcAvailable && state == ScuResponse.ON) {
            z = true;
        }
        xPilotCardItem.setValueWithSound(z, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$38$XPilotFragment(final int position, ScuResponse state) {
        boolean isNedcAvailable = isNedcAvailable();
        if (isNedcAvailable && isSwitchNotChange(this.mNgpSwItem, state)) {
            return;
        }
        this.mNgpSwItem.setValueWithSound(isNedcAvailable && state == ScuResponse.ON, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, CarBaseConfig.getInstance().isSupportCNgp() ? 3 : 2, Integer.valueOf(position));
        enableNgpSettingItem();
    }

    public /* synthetic */ void lambda$initXPilotFunc$39$XPilotFragment(final int position, ScuResponse state) {
        boolean isNedcAvailable = isNedcAvailable();
        if (isNedcAvailable && isSwitchNotChange(this.mCNgpSwItem, state)) {
            return;
        }
        boolean z = isNedcAvailable && state == ScuResponse.ON;
        this.mCNgpSwItem.setValueWithSound(z, isItemVisible(position));
        if (z && SharedPreferenceUtil.isFirstOpenCngpSw()) {
            showCngpMapPanel(null, false);
            SharedPreferenceUtil.setFirstOpenCngpSw(false);
        }
        this.mAdapter.notifyItemRangeChanged(position, 2, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$40$XPilotFragment(ScuIslaMode islaMode) {
        XPilotTabItem xPilotTabItem = this.mIslaTabItem;
        if (xPilotTabItem != null) {
            xPilotTabItem.setValue(Integer.valueOf(islaMode.toDisplayIndex()));
            this.mAdapter.notifyItemRangeChanged(this.mIslaTabItem.getIndex(), 1, Integer.valueOf(this.mIslaTabItem.getIndex()));
        }
    }

    public /* synthetic */ void lambda$initXPilotFunc$41$XPilotFragment(ScuIslaMode islaMode) {
        XPilotTabItem xPilotTabItem = this.mSimpleSasTabItem;
        if (xPilotTabItem != null) {
            xPilotTabItem.setValue(Integer.valueOf(islaMode.toDisplayIndex()));
            this.mAdapter.notifyItemRangeChanged(this.mSimpleSasTabItem.getIndex(), 1, Integer.valueOf(this.mSimpleSasTabItem.getIndex()));
        }
    }

    public /* synthetic */ void lambda$initXPilotFunc$42$XPilotFragment(Integer sasMode) {
        XPilotTabItem xPilotTabItem = this.mSpecialSasTabItem;
        if (xPilotTabItem != null) {
            xPilotTabItem.setValue(Integer.valueOf(sasMode.intValue() - 1));
            this.mAdapter.notifyItemRangeChanged(this.mSpecialSasTabItem.getIndex(), 1, Integer.valueOf(this.mSpecialSasTabItem.getIndex()));
        }
    }

    public /* synthetic */ void lambda$initXPilotFunc$43$XPilotFragment(final XPilotItem item, final int position, NedcState state) {
        if (state != null) {
            updateXpuLowPowerItem((XPilotCardItem) item, state, isItemVisible(position));
            this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
            updateXpuNedcDepItems(state);
        }
    }

    public /* synthetic */ void lambda$initXPilotFunc$44$XPilotFragment(final XPilotItem item, final int position, Boolean enabled) {
        if (isSwitchNotChange(item, enabled.booleanValue())) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(enabled.booleanValue(), isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$45$XPilotFragment(final XPilotItem item, final int position, Boolean enabled) {
        boolean espForUI = this.mChassisVm.getEspForUI();
        if (isSwitchNotChange(item, espForUI)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(espForUI, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$46$XPilotFragment(final XPilotItem item, final int position, Boolean hasFault) {
        boolean espForUI = this.mChassisVm.getEspForUI();
        if (isSwitchNotChange(item, espForUI)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(espForUI, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$47$XPilotFragment(final XPilotItem item, final int position, Boolean enabled) {
        if (isSwitchNotChange(item, enabled.booleanValue())) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(enabled.booleanValue(), isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$48$XPilotFragment(final XPilotItem item, final int position, Boolean hasFault) {
        boolean avhForUI = this.mChassisVm.getAvhForUI();
        if (isSwitchNotChange(item, avhForUI)) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(avhForUI, isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$49$XPilotFragment(final XPilotItem item, final int position, Boolean enabled) {
        if (isSwitchNotChange(item, enabled.booleanValue())) {
            return;
        }
        ((XPilotCardItem) item).setValueWithSound(enabled.booleanValue(), isItemVisible(position));
        this.mAdapter.notifyItemRangeChanged(position, 1, Integer.valueOf(position));
    }

    public /* synthetic */ void lambda$initXPilotFunc$50$XPilotFragment(Integer activeState) {
        if (activeState != null) {
            if (CarBaseConfig.getInstance().isSupportMemPark()) {
                showMemoryParkActiveState(activeState.intValue(), this.mScuVm.getMemoryParkSw());
            }
            showNgpXpilotActiveState(activeState.intValue());
            showEnhancedBsdState(activeState.intValue());
            showEnhancedLidarState(activeState.intValue());
        }
    }

    private void updateFcwState(ScuResponse fcwState) {
        XPilotCardItem xPilotCardItem = this.mFcwSwItem;
        if (xPilotCardItem != null) {
            xPilotCardItem.setEnable(fcwState != ScuResponse.INITIALIZATION);
            if (fcwState == ScuResponse.INITIALIZATION) {
                if (BaseFeatureOption.getInstance().showXPilotStAsTitle()) {
                    this.mFcwSwItem.setTitleResId(R.string.fcw_feature_title_init);
                } else {
                    this.mFcwSwItem.setDescResId(R.string.fcw_feature_title_init);
                }
            } else if (BaseFeatureOption.getInstance().showXPilotStAsTitle()) {
                this.mFcwSwItem.setTitleResId(R.string.fcw_feature_title);
            } else {
                this.mFcwSwItem.setDescResId(R.string.fcw_feature_desc);
            }
        }
    }

    private boolean isNedcAvailable() {
        return !CarBaseConfig.getInstance().isSupportXpu() || parseXpuNedcState(this.mXpuVm.getNedcState());
    }

    private void showEnhancedBsdState(int xpilotState) {
        XPilotCardItem xPilotCardItem;
        if (!CarBaseConfig.getInstance().isSupportEnhancedBsd() || (xPilotCardItem = this.mBsdCardItem) == null) {
            return;
        }
        if (xpilotState == 1) {
            xPilotCardItem.setTitleResId(R.string.bsd_enhanced_feature_title);
        } else {
            xPilotCardItem.setTitleResId(R.string.bsd_feature_title);
        }
        this.mAdapter.notifyItemRangeChanged(this.mBsdCardItem.getIndex(), 1, Integer.valueOf(this.mBsdCardItem.getIndex()));
    }

    private void showEnhancedLidarState(int xpilotState) {
        if (CarBaseConfig.getInstance().isSupportEnhancedLidarFunc()) {
            boolean z = xpilotState == 1;
            XPilotCardItem xPilotCardItem = this.mLccSwItem;
            if (xPilotCardItem != null) {
                xPilotCardItem.setTitleResId(z ? R.string.laa_lidar_feature_title : R.string.laa_feature_title);
                this.mLccSwItem.setDrawableResId(z ? R.drawable.img_x_pilot_laa_lidar : R.drawable.img_x_pilot_laa);
                this.mLccSwItem.setHelpPic1ResId(z ? R.drawable.img_xpilot_laa_1_lidar : R.drawable.img_xpilot_laa_1);
                this.mLccSwItem.setHelpTxt1(ResUtils.getString(z ? R.string.laa_lidar_feature_help_1 : R.string.laa_feature_help_1));
                this.mLccSwItem.setIsTestFunc(z);
                this.mLccSwItem.setTestDrawableResId(z ? R.drawable.ic_xpilot_lidar : 0);
                this.mAdapter.notifyItemRangeChanged(this.mLccSwItem.getIndex(), 1, Integer.valueOf(this.mLccSwItem.getIndex()));
            }
            XPilotCardItem xPilotCardItem2 = this.mAlcSwItem;
            if (xPilotCardItem2 != null) {
                xPilotCardItem2.setHelpTxt1(z ? ResUtils.getString(R.string.lca_lidar_feature_help_1) : ResUtils.getString(R.string.lca_feature_help_1, Integer.valueOf(BaseFeatureOption.getInstance().getAlcSpeed())));
            }
            XPilotCardItem xPilotCardItem3 = this.mNgpSwItem;
            if (xPilotCardItem3 != null) {
                xPilotCardItem3.setTitleResId(z ? R.string.ngp_lidar_feature_title : R.string.ngp_feature_title);
                this.mNgpSwItem.setIsTestFunc(z);
                this.mNgpSwItem.setTestDrawableResId(z ? R.drawable.ic_xpilot_lidar : 0);
                this.mAdapter.notifyItemRangeChanged(this.mNgpSwItem.getIndex(), 1, Integer.valueOf(this.mNgpSwItem.getIndex()));
            }
        }
    }

    private boolean isSupportEnhanceLidar() {
        return CarBaseConfig.getInstance().isSupportEnhancedLidarFunc() && this.mScuVm.getXpuXpilotState() == 1;
    }

    private void enableNgpSettingItem() {
        XPilotCardItem xPilotCardItem = this.mNgpSettingItem;
        if (xPilotCardItem != null) {
            xPilotCardItem.setEnable(this.mScuVm.getNgpState() == ScuResponse.ON || (CarBaseConfig.getInstance().isSupportCrossBarriers() && this.mScuVm.getLccState() == ScuResponse.ON));
        }
        XPilotAdapter xPilotAdapter = this.mAdapter;
        int i = this.mNgpSettingPosition;
        xPilotAdapter.notifyItemRangeChanged(i, 1, Integer.valueOf(i));
    }

    private void disableNgpSettingItem() {
        XPilotCardItem xPilotCardItem = this.mNgpSettingItem;
        if (xPilotCardItem != null) {
            xPilotCardItem.setEnable(false);
        }
        XPilotAdapter xPilotAdapter = this.mAdapter;
        int i = this.mNgpSettingPosition;
        xPilotAdapter.notifyItemRangeChanged(i, 1, Integer.valueOf(i));
    }

    private void showNgpXpilotActiveState(int xpilotState) {
        if (xpilotState == 2) {
            disableNgpSettingItem();
            this.mNgpSwItem.setNeedPurchase(true);
            this.mNgpSwItem.setExtBtnRes(R.string.xpilot_3_purchase_btn);
            this.mNgpSwItem.setNeedShowSw(false);
            XPilotCardItem xPilotCardItem = this.mCNgpSwItem;
            if (xPilotCardItem != null) {
                xPilotCardItem.setNeedPurchase(true);
                this.mCNgpSwItem.setExtBtnRes(R.string.xpilot_3_purchase_btn);
                this.mCNgpSwItem.setNeedShowSw(false);
            }
            XPilotCardItem xPilotCardItem2 = this.mCNgpMapItem;
            if (xPilotCardItem2 != null) {
                xPilotCardItem2.setEnable(false);
            }
        } else {
            enableNgpSettingItem();
            this.mNgpSwItem.setNeedPurchase(false);
            this.mNgpSwItem.setExtBtnRes(R.string.ngp_study_button);
            this.mNgpSwItem.setNeedShowSw(true);
            XPilotCardItem xPilotCardItem3 = this.mCNgpSwItem;
            if (xPilotCardItem3 != null) {
                xPilotCardItem3.setNeedPurchase(false);
                this.mCNgpSwItem.setExtBtnRes(R.string.ngp_study_button);
                this.mCNgpSwItem.setNeedShowSw(true);
            }
            XPilotCardItem xPilotCardItem4 = this.mCNgpMapItem;
            if (xPilotCardItem4 != null) {
                xPilotCardItem4.setEnable(true);
            }
        }
        this.mAdapter.notifyItemRangeChanged(this.mNgpSwItem.getIndex(), 4, Integer.valueOf(this.mNgpSwItem.getIndex()));
    }

    private void showMemoryParkActiveState(int xpilotState, ApResponse memoryParkState) {
        XPilotCardItem xPilotCardItem = this.mMemParkSwItem;
        if (xPilotCardItem == null) {
            return;
        }
        if (xpilotState == 2) {
            xPilotCardItem.setNeedPurchase(true);
            this.mMemParkSwItem.setExtBtnRes(R.string.xpilot_3_purchase_btn);
            this.mMemParkSwItem.setNeedShowSw(false);
        } else {
            xPilotCardItem.setNeedPurchase(false);
            this.mMemParkSwItem.setExtBtnRes(R.string.mem_park_study_button);
            this.mMemParkSwItem.setNeedShowSw(true);
        }
        this.mAdapter.notifyItemRangeChanged(this.mMemParkSwItem.getIndex(), 1, Integer.valueOf(this.mMemParkSwItem.getIndex()));
    }

    private void updateXpuLowPowerItem(XPilotCardItem item, NedcState nedcState, boolean soundFlag) {
        boolean z = false;
        item.setValueWithSound(nedcState == NedcState.On || nedcState == NedcState.TurnOffing || nedcState == NedcState.TurnOnFailure, soundFlag);
        if (nedcState != NedcState.TurnOning && nedcState != NedcState.TurnOffing) {
            z = true;
        }
        item.setEnable(z);
        int i = AnonymousClass3.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$xpu$NedcState[nedcState.ordinal()];
        if (i == 4) {
            if (BaseFeatureOption.getInstance().showXPilotStAsTitle()) {
                item.setTitleResId(R.string.xpu_low_power_turning_on);
            } else {
                item.setDescResId(R.string.xpu_low_power_turning_on);
            }
        } else if (i == 6) {
            if (BaseFeatureOption.getInstance().showXPilotStAsTitle()) {
                item.setTitleResId(R.string.xpu_low_power_turning_off);
            } else {
                item.setDescResId(R.string.xpu_low_power_turning_off);
            }
        } else if (BaseFeatureOption.getInstance().showXPilotStAsTitle()) {
            item.setTitleResId(R.string.xpu_low_power_feature_title);
        } else {
            item.setDescResId(R.string.xpu_low_power_feature_desc);
        }
    }

    private void updateXpuNedcDepItems(NedcState nedcState) {
        boolean parseXpuNedcState = parseXpuNedcState(nedcState);
        for (XPilotItem xPilotItem : this.mXpuDepItems) {
            if (xPilotItem != null) {
                String key = xPilotItem.getKey();
                if (parseXpuNedcState) {
                    if (XPilotItem.KEY_LCC.equals(key)) {
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getLccState() == ScuResponse.ON));
                    } else if (XPilotItem.KEY_ALC.equals(key)) {
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getAlcState() == ScuResponse.ON));
                    } else if (XPilotItem.KEY_ISLC.equals(key)) {
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getIslcState() == ScuResponse.ON));
                    } else if (XPilotItem.KEY_NGP.equals(key)) {
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getNgpState() == ScuResponse.ON));
                    } else if (XPilotItem.KEY_CNGP.equals(key)) {
                        xPilotItem.setValue(Boolean.valueOf(this.mXpuVm.getCityNgpSt() == ScuResponse.ON));
                    } else if (XPilotItem.KEY_CNGP_MAP.equals(key)) {
                        xPilotItem.setEnable(true);
                    } else if (XPilotItem.KEY_NGP_SETTING.equals(key)) {
                        enableNgpSettingItem();
                    } else if ("auto_park".equals(key)) {
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getAutoParkSw() == ScuResponse.ON));
                    } else if (XPilotItem.KEY_MEM_PARK.equals(key)) {
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getMemoryParkSw() == ApResponse.ON));
                    } else if (XPilotItem.KEY_NRA.equals(key)) {
                        xPilotItem.setValue(Boolean.valueOf(this.mXpuVm.getNraSw()));
                    } else if (XPilotItem.KEY_LSS.equals(key)) {
                        xPilotItem.setValue(Integer.valueOf(this.mScuVm.getLssMode().toDisplayIndex()));
                    } else if (XPilotItem.KEY_ELK.equals(key)) {
                        xPilotItem.setValue(Boolean.valueOf(this.mScuVm.getElkState() == ScuResponse.ON));
                    }
                } else {
                    if (XPilotItem.KEY_LSS.equals(key)) {
                        xPilotItem.setValue(0);
                    } else {
                        xPilotItem.setValue(false);
                    }
                    if (XPilotItem.KEY_CNGP_MAP.equals(key)) {
                        xPilotItem.setEnable(false);
                    } else if (XPilotItem.KEY_NGP_SETTING.equals(key)) {
                        disableNgpSettingItem();
                    }
                }
                this.mAdapter.notifyItemRangeChanged(xPilotItem.getIndex(), 1, Integer.valueOf(xPilotItem.getIndex()));
            }
        }
    }

    private boolean isItemVisible(int position) {
        XRecyclerView xRecyclerView = this.mRecyclerView;
        return (xRecyclerView == null || xRecyclerView.getLayoutManager() == null || this.mRecyclerView.getLayoutManager().findViewByPosition(position) == null) ? false : true;
    }

    private boolean isNotParkStall() {
        return this.mVcuVm.getGearLevelValue() != GearLevel.P;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected int getListItemIdx(Intent intent) {
        XPilotItem itemByKey;
        XPilotItem itemByKey2;
        IElementDirect elementDirect = ElementDirectManager.getElementDirect();
        Uri data = intent.getData();
        if (data == null) {
            LogUtils.w(TAG, "getListItemIdx: uri in intent is null");
            return -1;
        }
        if (elementDirect.isElementDirect(data)) {
            ElementDirectItem element = elementDirect.getElement(data);
            if (element != null && (itemByKey2 = XPilotCategoryManager.getInstance().getItemByKey(element.getKey())) != null) {
                return itemByKey2.getIndex();
            }
        } else if (elementDirect.isPageDirect(data)) {
            PageDirectItem page = elementDirect.getPage(data);
            String lastPathSegment = data.getLastPathSegment();
            if (page != null && lastPathSegment != null && (itemByKey = XPilotCategoryManager.getInstance().getItemByKey(lastPathSegment)) != null) {
                return itemByKey.getIndex();
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected String[] supportSecondPageForElementDirect() {
        return sElementDirectSupport;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.direct.OnPageDirectShowListener
    public void onPageDirectShow(String name) {
        if (this.mXPilotFuncTipsRunnable != null) {
            ThreadUtils.getHandler(1).removeCallbacks(this.mXPilotFuncTipsRunnable);
            this.mXPilotFuncTipsRunnable = null;
        }
        XPilotFuncTipsRunnable xPilotFuncTipsRunnable = new XPilotFuncTipsRunnable(name);
        this.mXPilotFuncTipsRunnable = xPilotFuncTipsRunnable;
        ThreadUtils.runOnMainThreadDelay(xPilotFuncTipsRunnable, 50L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showXPilotFuncTip(String name) {
        XPilotItem<?> itemByKey;
        String str = TAG;
        LogUtils.i(str, "showXPilotFuncTip: " + name);
        name.hashCode();
        char c = 65535;
        switch (name.hashCode()) {
            case -1682820765:
                if (name.equals("/xpilotcontrol/tip/alc")) {
                    c = 0;
                    break;
                }
                break;
            case -1682820450:
                if (name.equals("/xpilotcontrol/tip/avh")) {
                    c = 1;
                    break;
                }
                break;
            case -1682819586:
                if (name.equals("/xpilotcontrol/tip/bsd")) {
                    c = 2;
                    break;
                }
                break;
            case -1682817769:
                if (name.equals("/xpilotcontrol/tip/dow")) {
                    c = 3;
                    break;
                }
                break;
            case -1682817211:
                if (name.equals("/xpilotcontrol/tip/ebw")) {
                    c = 4;
                    break;
                }
                break;
            case -1682816913:
                if (name.equals("/xpilotcontrol/tip/elk")) {
                    c = 5;
                    break;
                }
                break;
            case -1682816691:
                if (name.equals("/xpilotcontrol/tip/esp")) {
                    c = 6;
                    break;
                }
                break;
            case -1682816219:
                if (name.equals("/xpilotcontrol/tip/fcw")) {
                    c = 7;
                    break;
                }
                break;
            case -1682814286:
                if (name.equals("/xpilotcontrol/tip/hdc")) {
                    c = '\b';
                    break;
                }
                break;
            case -1682810473:
                if (name.equals("/xpilotcontrol/tip/lcc")) {
                    c = '\t';
                    break;
                }
                break;
            case -1682810422:
                if (name.equals("/xpilotcontrol/tip/ldw")) {
                    c = '\n';
                    break;
                }
                break;
            case -1682809961:
                if (name.equals("/xpilotcontrol/tip/lss")) {
                    c = 11;
                    break;
                }
                break;
            case -1682808414:
                if (name.equals("/xpilotcontrol/tip/ngp")) {
                    c = '\f';
                    break;
                }
                break;
            case -1682808088:
                if (name.equals("/xpilotcontrol/tip/nra")) {
                    c = '\r';
                    break;
                }
                break;
            case -1682804687:
                if (name.equals("/xpilotcontrol/tip/rcw")) {
                    c = 14;
                    break;
                }
                break;
            case -1397341399:
                if (name.equals("/xpilotcontrol/tip/mem_park")) {
                    c = 15;
                    break;
                }
                break;
            case -627774423:
                if (name.equals("/xpilotcontrol/tip/cngp")) {
                    c = 16;
                    break;
                }
                break;
            case -627590732:
                if (name.equals("/xpilotcontrol/tip/isla")) {
                    c = 17;
                    break;
                }
                break;
            case -627590730:
                if (name.equals("/xpilotcontrol/tip/islc")) {
                    c = 18;
                    break;
                }
                break;
            case -627340127:
                if (name.equals("/xpilotcontrol/tip/raeb")) {
                    c = 19;
                    break;
                }
                break;
            case -627337741:
                if (name.equals("/xpilotcontrol/tip/rcta")) {
                    c = 20;
                    break;
                }
                break;
            case -542969427:
                if (name.equals("/xpilotcontrol/tip/simple_sas")) {
                    c = 21;
                    break;
                }
                break;
            case 27256837:
                if (name.equals("/xpilotcontrol/tip/auto_park")) {
                    c = 22;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_ALC);
                break;
            case 1:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_AVH);
                break;
            case 2:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_BSD);
                break;
            case 3:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_DOW);
                break;
            case 4:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_EBW);
                break;
            case 5:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_ELK);
                break;
            case 6:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_ESP);
                break;
            case 7:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_FCW);
                break;
            case '\b':
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_HDC);
                break;
            case '\t':
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_LCC);
                break;
            case '\n':
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_LDW);
                break;
            case 11:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_LSS);
                break;
            case '\f':
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_NGP);
                break;
            case '\r':
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_NRA);
                break;
            case 14:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_RCW);
                break;
            case 15:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_MEM_PARK);
                break;
            case 16:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_CNGP);
                break;
            case 17:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_ISLA);
                break;
            case 18:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_ISLC);
                break;
            case 19:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_RAEB);
                break;
            case 20:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_RCTA);
                break;
            case 21:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey(XPilotItem.KEY_SIMPLE_SAS);
                break;
            case 22:
                itemByKey = XPilotCategoryManager.getInstance().getItemByKey("auto_park");
                break;
            default:
                itemByKey = null;
                break;
        }
        if (itemByKey == null) {
            LogUtils.d(str, "item is null,show dialog failure.");
        } else {
            DialogInfoHelper.getInstance().showXPilotInfoPanel(App.getInstance().getApplicationContext(), itemByKey, (DialogInterface.OnShowListener) null, (DialogInterface.OnDismissListener) null);
        }
    }

    private void uploadXpilotCardItemBi(XPilotItem xPilotItem, boolean checked) {
        BtnEnum btnEnum;
        PageEnum pageEnum = null;
        BtnEnum btnEnum2 = null;
        if (xPilotItem instanceof XPilotCardItem) {
            PageEnum pageEnum2 = PageEnum.XPILOT_E28_SETTING_PAGE;
            String key = xPilotItem.getKey();
            key.hashCode();
            char c = 65535;
            switch (key.hashCode()) {
                case 96664:
                    if (key.equals(XPilotItem.KEY_ALC)) {
                        c = 0;
                        break;
                    }
                    break;
                case 97843:
                    if (key.equals(XPilotItem.KEY_BSD)) {
                        c = 1;
                        break;
                    }
                    break;
                case 99660:
                    if (key.equals(XPilotItem.KEY_DOW)) {
                        c = 2;
                        break;
                    }
                    break;
                case 101210:
                    if (key.equals(XPilotItem.KEY_FCW)) {
                        c = 3;
                        break;
                    }
                    break;
                case 106956:
                    if (key.equals(XPilotItem.KEY_LCC)) {
                        c = 4;
                        break;
                    }
                    break;
                case 107007:
                    if (key.equals(XPilotItem.KEY_LDW)) {
                        c = 5;
                        break;
                    }
                    break;
                case 3495006:
                    if (key.equals(XPilotItem.KEY_RCTA)) {
                        c = 6;
                        break;
                    }
                    break;
                case 1661170810:
                    if (key.equals("auto_park")) {
                        c = 7;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    btnEnum2 = BtnEnum.XPILOT_E28_SETTING_ALC;
                    break;
                case 1:
                    btnEnum2 = BtnEnum.XPILOT_E28_SETTING_BSD;
                    break;
                case 2:
                    btnEnum2 = BtnEnum.XPILOT_E28_SETTING_DOW;
                    break;
                case 3:
                    btnEnum2 = BtnEnum.XPILOT_E28_SETTING_FCW;
                    break;
                case 4:
                    btnEnum2 = BtnEnum.XPILOT_E28_SETTING_LCC;
                    break;
                case 5:
                    btnEnum2 = BtnEnum.XPILOT_E28_SETTING_LDW;
                    break;
                case 6:
                    btnEnum2 = BtnEnum.XPILOT_E28_SETTING_RCTA;
                    break;
                case 7:
                    btnEnum2 = BtnEnum.XPILOT_E28_SETTING_AUTO_PARK;
                    break;
            }
            btnEnum = btnEnum2;
            pageEnum = pageEnum2;
        } else {
            btnEnum = null;
        }
        if (pageEnum == null || btnEnum == null) {
            return;
        }
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(checked ? 1 : 2);
        StatisticUtils.sendStatistic(pageEnum, btnEnum, objArr);
    }

    private void onLssItemChanged(int selectedIndex, View view, boolean isVuiAction) {
        if (selectedIndex == 1) {
            confirmLdwFunc(view, isVuiAction);
        } else if (selectedIndex == 2) {
            confirmLkaFunc(view, isVuiAction);
        } else if (isNotParkStall()) {
            showNotParkStallTip(view, isVuiAction);
        } else {
            this.mScuVm.setLssMode(ScuLssMode.Off);
        }
    }

    private void onLssSenItemChanged(int selectedIndex, View view, boolean isVuiAction) {
        this.mXpuVm.setLssSensitivity(selectedIndex);
    }

    private boolean confirmLdwFunc(View view, boolean isVuiAction) {
        ScuResponse ldwState = this.mScuVm.getLdwState();
        if (CarBaseConfig.getInstance().isSupportLka()) {
            if (ldwState == ScuResponse.FAIL || (ldwState == ScuResponse.ON && this.mScuVm.getLkaState() == ScuResponse.ON)) {
                showFunctionFailTip(view, isVuiAction);
                return false;
            } else if (isNotParkStall()) {
                showNotParkStallTip(view, isVuiAction);
                return false;
            }
        } else if (ldwState == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
            return false;
        }
        this.mScuVm.setLssMode(ScuLssMode.Ldw);
        return true;
    }

    private boolean confirmLkaFunc(final View view, final boolean isVuiAction) {
        ScuResponse lkaState = this.mScuVm.getLkaState();
        if (lkaState == ScuResponse.FAIL || (lkaState == ScuResponse.ON && this.mScuVm.getLdwState() == ScuResponse.ON)) {
            showFunctionFailTip(view, isVuiAction);
            return false;
        } else if (isNotParkStall()) {
            showNotParkStallTip(view, isVuiAction);
            return false;
        } else {
            showCustomConfirmDialog(R.string.lka_feature_title, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$TIrAf9VeDYQ7T0I0yyowGBz6p1s
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    XPilotFragment.this.lambda$confirmLkaFunc$51$XPilotFragment(view, isVuiAction, xDialog, i);
                }
            }, 0, R.string.lka_feature_confirm, 0, 0, 10, VuiManager.SCENE_XPILOT_DIALOG_LKA);
            return false;
        }
    }

    public /* synthetic */ void lambda$confirmLkaFunc$51$XPilotFragment(final View view, final boolean isVuiAction, XDialog xDialog, int i) {
        enableLka(view, isVuiAction);
    }

    private void enableLka(View view, boolean isVuiAction) {
        if (isParkStallGoAhead(view, isVuiAction)) {
            this.mScuVm.setLssMode(ScuLssMode.Lka);
        }
    }

    private void onIslaItemChanged(int selectedIndex, View view, boolean isVuiAction) {
        if (selectedIndex == 1) {
            confirmLsaFunc(view, isVuiAction);
        } else if (selectedIndex == 2) {
            confirmIslaFunc(view, isVuiAction);
        } else {
            this.mScuVm.setIslaMode(ScuIslaMode.OFF);
        }
    }

    private void confirmLsaFunc(View view, boolean isVuiAction) {
        if (this.mScuVm.getSlaState() == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else {
            this.mScuVm.setIslaMode(ScuIslaMode.SLA);
        }
    }

    private boolean confirmIslaFunc(View view, boolean isVuiAction) {
        if (this.mScuVm.getIslaState() == ScuResponse.FAIL) {
            showFunctionFailTip(view, isVuiAction);
            return false;
        }
        showCustomConfirmDialog(R.string.isla_feature_title, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$jm645G_jQJJwoy5DSBLL2M0-gKg
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                XPilotFragment.this.lambda$confirmIslaFunc$52$XPilotFragment(xDialog, i);
            }
        }, R.drawable.img_xpilot_islc_1, R.string.isla_feature_confirm, 0, 0, 10, VuiManager.SCENE_XPILOT_DIALOG_ISLA);
        return false;
    }

    public /* synthetic */ void lambda$confirmIslaFunc$52$XPilotFragment(XDialog xDialog, int i) {
        this.mScuVm.setIslaMode(ScuIslaMode.ISLA);
    }

    private void onSimpleSasItemChanged(int selectedIndex, View view, boolean isVuiAction) {
        if (selectedIndex == 1) {
            confirmSimpleSasManualFunc(view, isVuiAction);
        } else if (selectedIndex == 2) {
            confirmSimpleSasAutoFunc(view, isVuiAction);
        } else {
            this.mXpuVm.setSimpleSasMode(ScuIslaMode.OFF);
        }
    }

    private void onSpecialSasItemChanged(int selectedIndex, View view, boolean isVuiAction) {
        if (this.mScuVm.getSpecialSasMode() == 4) {
            showFunctionFailTip(view, isVuiAction);
        } else if (selectedIndex == 1) {
            this.mScuVm.setSpecialSasMode(2);
        } else if (selectedIndex == 2) {
            confirmSpecialSasControlFunc(view, isVuiAction);
        } else {
            this.mScuVm.setSpecialSasMode(1);
        }
    }

    private void confirmSimpleSasManualFunc(View view, boolean isVuiAction) {
        if (this.mXpuVm.getSimpleSasMode() == ScuIslaMode.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else {
            this.mXpuVm.setSimpleSasMode(ScuIslaMode.SLA);
        }
    }

    private void confirmSimpleSasAutoFunc(View view, boolean isVuiAction) {
        if (this.mXpuVm.getSimpleSasMode() == ScuIslaMode.FAIL) {
            showFunctionFailTip(view, isVuiAction);
        } else {
            showCustomConfirmDialog(R.string.simple_sas_feature_title, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$w2TNL1WqJbiCHHFYMkTfB8BpVQc
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    XPilotFragment.this.lambda$confirmSimpleSasAutoFunc$53$XPilotFragment(xDialog, i);
                }
            }, R.drawable.img_xpilot_islc_1, R.string.simple_sas_feature_confirm, 0, 0, 10, VuiManager.SCENE_XPILOT_DIALOG_SIMPLE_SAS);
        }
    }

    public /* synthetic */ void lambda$confirmSimpleSasAutoFunc$53$XPilotFragment(XDialog xDialog, int i) {
        this.mXpuVm.setSimpleSasMode(ScuIslaMode.ISLA);
    }

    private void confirmSpecialSasControlFunc(View view, boolean isVuiAction) {
        if (this.mScuVm.getSpecialSasMode() == 5) {
            showFunctionFailTip(view, isVuiAction);
        } else {
            showCustomConfirmDialog(R.string.special_sas_feature_title, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.-$$Lambda$XPilotFragment$fx8beUvT3tsEicMDRrCGzdtiuGg
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    XPilotFragment.this.lambda$confirmSpecialSasControlFunc$54$XPilotFragment(xDialog, i);
                }
            }, R.drawable.img_xpilot_islc_1, R.string.special_sas_feature_confirm, 0, 0, 10, VuiManager.SCENE_XPILOT_DIALOG_SIMPLE_SAS);
        }
    }

    public /* synthetic */ void lambda$confirmSpecialSasControlFunc$54$XPilotFragment(XDialog xDialog, int i) {
        this.mScuVm.setSpecialSasMode(3);
    }

    private void showNgpSettingPanel(View view, boolean isVuiAction) {
        if (checkIfXpuAvailable(view, isVuiAction)) {
            Intent intent = new Intent(this.mContext, CarControlService.class);
            intent.setAction(GlobalConstant.ACTION.ACTION_SHOW_NGP_SETTING_PANEL);
            App.getInstance().startService(intent);
        }
    }

    private void showCngpMapPanel(View view, boolean isVuiAction) {
        if (checkIfXpuAvailable(view, isVuiAction) && isParkStallGoAhead(view, isVuiAction)) {
            Intent intent = new Intent(this.mContext, CarControlService.class);
            intent.setAction(GlobalConstant.ACTION.ACTION_SHOW_CNGP_MAP_PANEL);
            App.getInstance().startService(intent);
        }
    }

    private void showIslaSettingPanel(View view, boolean isVuiAction) {
        if (this.mScuVm.getIslaMode() == ScuIslaMode.ISLA) {
            Intent intent = new Intent(this.mContext, CarControlService.class);
            intent.setAction(GlobalConstant.ACTION.ACTION_SHOW_ISLA_SETTING_PANEL);
            App.getInstance().startService(intent);
            return;
        }
        showXPilotCustomTip(view, isVuiAction, R.string.isla_setting_unavailable_tip);
    }

    private void showSpecialSasSettingPanel(View view, boolean isVuiAction) {
        int specialSasMode = this.mScuVm.getSpecialSasMode();
        if (specialSasMode == 1 || specialSasMode == 2 || specialSasMode == 3) {
            Intent intent = new Intent(this.mContext, CarControlService.class);
            intent.setAction(GlobalConstant.ACTION.ACTION_SHOW_SPECIAL_SAS_SETTING_PANEL);
            App.getInstance().startService(intent);
            return;
        }
        showFunctionFailTip(view, isVuiAction);
    }

    private void clearExamEventObserver() {
        AccountViewModel accountViewModel = this.mAccountVm;
        if (accountViewModel != null) {
            removeObservers(accountViewModel.getTaskQrCode());
            removeObservers(this.mAccountVm.getTaskLoopResult());
            removeObservers(this.mAccountVm.getTaskResult());
            removeObservers(this.mAccountVm.getTaskFailure());
            removeObservers(this.mAccountVm.getTaskListResult());
        }
    }

    private boolean isParkStallGoAhead(View view, boolean isVuiAction) {
        boolean z = this.mVcuVm.getGearLevelValue() == GearLevel.P;
        if (!z) {
            showNotParkStallTip(view, isVuiAction);
        }
        return z;
    }

    private void showFunctionFailTip(View view, boolean isVuiAction) {
        showXPilotCustomTip(view, isVuiAction, R.string.xpilot_open_fail);
    }

    private void showSrrMissedTip(View view, boolean isVuiAction) {
        showXPilotCustomTip(view, isVuiAction, R.string.xpilot_srr_missed_open_fail);
    }

    private void showVpmNotReadyTip(View view, boolean isVuiAction) {
        showXPilotCustomTip(view, isVuiAction, R.string.xpilot_vpm_not_ready_open_fail);
    }

    private void showNotParkStallTip(View view, boolean isVuiAction) {
        showXPilotCustomTip(view, isVuiAction, R.string.xpilot_need_park_gear);
    }

    private void showXPilotCustomTip(View view, boolean isVuiAction, int strRes) {
        NotificationHelper.getInstance().showToast(strRes);
        if (isVuiAction) {
            vuiFeedbackClickForXpilot(strRes, view);
        }
    }
}
