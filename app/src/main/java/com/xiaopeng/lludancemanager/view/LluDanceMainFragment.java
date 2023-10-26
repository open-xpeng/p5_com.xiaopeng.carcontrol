package com.xiaopeng.lludancemanager.view;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.android.internal.util.CollectionUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioMode;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.lludancemanager.Constant;
import com.xiaopeng.lludancemanager.LluDanceHelper;
import com.xiaopeng.lludancemanager.LluDanceOrderTimeHelper;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.lludancemanager.adapter.ItemDecoration;
import com.xiaopeng.lludancemanager.adapter.LluDanceAdapter;
import com.xiaopeng.lludancemanager.bean.LluDanceViewData;
import com.xiaopeng.lludancemanager.helper.LluDanceAlarmHelper;
import com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel;
import com.xiaopeng.lludancemanager.viewmodel.LluDanceViewModel;
import com.xiaopeng.lludancemanager.widget.LluDanceStateLayout;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XRecyclerView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.List;

/* loaded from: classes2.dex */
public class LluDanceMainFragment extends LluBaseFragment implements View.OnClickListener {
    private static final String LLU_DANCE_PLAY_MODE_STATISTIC_CODE_DIRECTLY = "1";
    private static final String LLU_DANCE_PLAY_MODE_STATISTIC_CODE_ORDER = "2";
    private static final int LLU_SHOW_SCROLL_BAR_LIMIT = 8;
    private static final String TAG = "LluDanceMainFragment";
    private static final String TAG_DANCE_PLAY_FRAGMENT = "tag_dance_play_fragment";
    private LluDanceViewData mCurrentSelectData;
    private Observer<Boolean> mDanceBreakObserver;
    private Observer<Boolean> mDanceStopFromFrameworkObserver;
    private LluDanceSettingDialog mLluConfirmDialog;
    private LluDanceAdapter mLluDanceAdapter;
    private XRecyclerView mLluDanceDataListRecyclerView;
    private LluDancePlayFragment mLluDancePlayFragment;
    private LluDanceStateLayout mLluDanceStateLayout;
    private List<LluDanceViewData> mLluDanceViewDataList;
    private XRelativeLayout mOrderInfoContainer;
    private XTextView mOrderInfoTimeLeft;
    private XTextView mOrderInfoTitle;
    private String mOrderedDanceId;
    private long mOrderedTime;
    private XLinearLayout mRequestDanceDataLoading;
    private ScenarioViewModel mScenarioViewModel;
    private LluDanceViewData mSelectForPlayDate;
    private CountDownTimer mTimeLeftCountDownTimer;
    private Observer<String> mOrderedDanceItemIdObserver = null;
    private Observer<Integer> mIgEventObserver = null;
    private Observer<Integer> mDefaultSelectedPosition = null;
    private int mCurrentIndex = -1;

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment
    protected boolean supportVui() {
        return true;
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment
    protected String vuiSceneId() {
        return VuiManager.SCENE_LLU_MAIN;
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment
    protected void initViewModelObserver() {
        this.mOrderedDanceItemIdObserver = new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$86XBZQQaqfwUpiVwrfC2GsLwaBI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluDanceMainFragment.this.lambda$initViewModelObserver$1$LluDanceMainFragment((String) obj);
            }
        };
        this.mIgEventObserver = new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$sQo7Undz4YXfu1cDY1jVljSJ_qE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluDanceMainFragment.this.lambda$initViewModelObserver$2$LluDanceMainFragment((Integer) obj);
            }
        };
        this.mDefaultSelectedPosition = new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$sEd7-WBkeXggyxLSSOlRM-bUMg0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluDanceMainFragment.this.lambda$initViewModelObserver$3$LluDanceMainFragment((Integer) obj);
            }
        };
        this.mDanceBreakObserver = new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$3sHHexMHP71X1EEDnvKXk2wA1kw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluDanceMainFragment.this.lambda$initViewModelObserver$4$LluDanceMainFragment((Boolean) obj);
            }
        };
        this.mDanceStopFromFrameworkObserver = new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$1dDShFsMfwDIAp_EgiioGiTZ964
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluDanceMainFragment.this.lambda$initViewModelObserver$5$LluDanceMainFragment((Boolean) obj);
            }
        };
    }

    public /* synthetic */ void lambda$initViewModelObserver$1$LluDanceMainFragment(String orderedItemId) {
        if (orderedItemId == null || !LluDanceHelper.checkLightDanceAvailable()) {
            return;
        }
        LogUtils.d(TAG, "Karl log open time optimize ordered dance item id  = " + orderedItemId);
        for (final LluDanceViewData lluDanceViewData : this.mLluDanceViewDataList) {
            if (lluDanceViewData.getId().equals(orderedItemId)) {
                refreshOrderInfoContainer();
                LogUtils.d(TAG, "Karl log open time optimize call start dance play fragment");
                this.mOrderInfoTitle.postDelayed(new Runnable() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$NUMHJWQyOCyd0TLqw-jDBWWuF1A
                    @Override // java.lang.Runnable
                    public final void run() {
                        LluDanceMainFragment.this.lambda$null$0$LluDanceMainFragment(lluDanceViewData);
                    }
                }, 2000L);
                return;
            }
        }
    }

    public /* synthetic */ void lambda$null$0$LluDanceMainFragment(final LluDanceViewData lluDanceViewData) {
        startPlayFragment(lluDanceViewData, true);
    }

    public /* synthetic */ void lambda$initViewModelObserver$2$LluDanceMainFragment(Integer igOffEvent) {
        if (igOffEvent.intValue() == 0) {
            LogUtils.d(TAG, "dance main fragment received ig off event, removeAlarmRequestWithSwitchReset");
            refreshOrderInfoContainer();
        }
    }

    public /* synthetic */ void lambda$initViewModelObserver$3$LluDanceMainFragment(Integer position) {
        int size = this.mLluDanceViewDataList.size();
        String str = TAG;
        LogUtils.d(str, "mDefaultSelectedPosition position = " + position + "data size" + size);
        if (size == 0) {
            LogUtils.w(str, "There is not llu data.");
            return;
        }
        if (position.intValue() >= size) {
            position = 0;
        }
        this.mCurrentIndex = position.intValue();
        this.mLluDanceAdapter.selected(position.intValue());
        LluDanceViewData lluDanceViewData = this.mLluDanceViewDataList.get(position.intValue());
        this.mCurrentSelectData = lluDanceViewData;
        updateBtnState(Integer.valueOf(lluDanceViewData.getState()), (int) this.mCurrentSelectData.getDownloadedPercentage());
        this.mLluDanceDataListRecyclerView.smoothScrollToPosition(position.intValue());
    }

    public /* synthetic */ void lambda$initViewModelObserver$4$LluDanceMainFragment(Boolean danceBreak) {
        if (danceBreak.booleanValue()) {
            LogUtils.d(TAG, "dance break ");
            exitPlayFragment();
        }
    }

    public /* synthetic */ void lambda$initViewModelObserver$5$LluDanceMainFragment(Boolean play) {
        if (!play.booleanValue()) {
            LogUtils.d(TAG, "dance stop play ");
            exitPlayFragment();
            return;
        }
        LogUtils.d(TAG, "dance start play ");
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment
    protected void initViewModel() {
        this.mDanceViewModel = (LluDanceViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluDanceViewModel.class);
        this.mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
        setLiveDataObserver(this.mDanceViewModel.getLiveDataList(), new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$fQH_O2psWh_1dPWfc6UM2UfAORM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluDanceMainFragment.this.lambda$initViewModel$6$LluDanceMainFragment((List) obj);
            }
        });
        setLiveDataObserver(this.mDanceViewModel.getOrderedPlayItemIdData(), this.mOrderedDanceItemIdObserver);
        setLiveDataObserver(this.mDanceViewModel.getIgEvent(), this.mIgEventObserver);
        setLiveDataObserver(this.mDanceViewModel.getDefaultSelectedOption(), this.mDefaultSelectedPosition);
        setLiveDataObserver(this.mDanceViewModel.getLluDanceBreak(), this.mDanceBreakObserver);
        setLiveDataObserver(this.mDanceViewModel.getLluDanceStartPlayData(), this.mDanceStopFromFrameworkObserver);
        setLiveDataObserver(this.mScenarioViewModel.getScenarioStateData(), new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$mrgeNYSSH-kEY4Tidjex6p-tARs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluDanceMainFragment.this.lambda$initViewModel$7$LluDanceMainFragment((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initViewModel$6$LluDanceMainFragment(List lluDanceViewData) {
        this.mLluDanceViewDataList = lluDanceViewData;
        refreshOrderInfoContainer();
        this.mRequestDanceDataLoading.setVisibility(8);
        this.mLluDanceDataListRecyclerView.setVerticalScrollBarEnabled(lluDanceViewData.size() >= 8);
        this.mLluDanceAdapter.setList(lluDanceViewData);
        if (this.mCurrentIndex == -1 || CollectionUtils.isEmpty(this.mLluDanceViewDataList)) {
            return;
        }
        int size = this.mLluDanceViewDataList.size();
        int i = this.mCurrentIndex;
        if (size > i) {
            updateBtnState(Integer.valueOf(this.mLluDanceViewDataList.get(i).getState()), (int) this.mLluDanceViewDataList.get(this.mCurrentIndex).getDownloadedPercentage());
        }
    }

    public /* synthetic */ void lambda$initViewModel$7$LluDanceMainFragment(Boolean enable) {
        LluDancePlayFragment lluDancePlayFragment;
        LogUtils.i(TAG, "enter meditation , break llu.");
        if (enable == null || !enable.booleanValue() || (lluDancePlayFragment = this.mLluDancePlayFragment) == null) {
            return;
        }
        lluDancePlayFragment.stopDanceByMeditationMode();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.llu_dance_fragment;
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtils.d(TAG, "on onViewCreated");
        initViews(view);
        if (supportVui()) {
            initVuiScene();
        }
        this.mLluDanceDataListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mLluDanceDataListRecyclerView.addItemDecoration(new ItemDecoration(16));
        this.mLluDanceDataListRecyclerView.initVuiAttr(VuiManager.SCENE_LLU_MAIN, VuiEngine.getInstance(getContext()), true, true);
        LluDanceAdapter lluDanceAdapter = new LluDanceAdapter(new LluDanceAdapter.OnItemListener() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$q0xAJwjSlpM8TO2VWzo3hyu8egw
            @Override // com.xiaopeng.lludancemanager.adapter.LluDanceAdapter.OnItemListener
            public final void onItemClick(int i, LluDanceViewData lluDanceViewData) {
                LluDanceMainFragment.this.lambda$onViewCreated$8$LluDanceMainFragment(i, lluDanceViewData);
            }
        });
        this.mLluDanceAdapter = lluDanceAdapter;
        this.mLluDanceDataListRecyclerView.setAdapter(lluDanceAdapter);
        this.mLluDanceStateLayout.setListener(new LluDanceStateLayout.OnClickListener() { // from class: com.xiaopeng.lludancemanager.view.LluDanceMainFragment.1
            @Override // com.xiaopeng.lludancemanager.widget.LluDanceStateLayout.OnClickListener
            public void playDance(LluDanceViewData data) {
                LluDanceMainFragment.this.mCurrentSelectData = data;
                if (ClickHelper.isFastClick(1000L, false)) {
                    LogUtils.d(LluDanceMainFragment.TAG, "click llu play button too quick!");
                } else {
                    LluDanceMainFragment.this.doActionByType(data);
                }
            }

            @Override // com.xiaopeng.lludancemanager.widget.LluDanceStateLayout.OnClickListener
            public void downloadDance(LluDanceViewData data) {
                LluDanceMainFragment.this.mCurrentSelectData = data;
                LluDanceMainFragment.this.doActionByType(data);
            }

            @Override // com.xiaopeng.lludancemanager.widget.LluDanceStateLayout.OnClickListener
            public void pauseDownloadDance(LluDanceViewData data) {
                LluDanceMainFragment.this.mCurrentSelectData = data;
                LluDanceMainFragment.this.doActionByType(data);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public /* synthetic */ void lambda$onViewCreated$8$LluDanceMainFragment(int position, LluDanceViewData data) {
        this.mCurrentIndex = position;
        LluDanceViewData lluDanceViewData = this.mLluDanceViewDataList.get(position);
        this.mCurrentSelectData = lluDanceViewData;
        SharedPreferenceUtil.setLLuDanceCurrentSelectRscId(lluDanceViewData.getId());
        updateBtnState(Integer.valueOf(this.mCurrentSelectData.getState()), (int) this.mCurrentSelectData.getDownloadedPercentage());
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        this.mLluDanceStateLayout.onVuiEvent(view, vuiEvent);
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mDanceViewModel.getAllData();
        LogUtils.d(TAG, "onResume");
        this.mDanceViewModel.chooseSelectPositionNew(this.mLluDanceViewDataList);
        refreshOrderInfoContainer();
    }

    @Override // com.xiaopeng.lludancemanager.view.LluBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        LogUtils.d(TAG, "onPause");
        cancelTimeLeftCountDown();
        this.mOrderInfoContainer.setVisibility(8);
        exitPlayFragment();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        LogUtils.d(TAG, "onStop");
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        unRegisterLiveDataObserver();
        LogUtils.d(TAG, "onDestroyView");
    }

    private void unRegisterLiveDataObserver() {
        removeLiveDataObserver(this.mDanceViewModel.getOrderedPlayItemIdData(), this.mOrderedDanceItemIdObserver);
        removeLiveDataObserver(this.mDanceViewModel.getIgEvent(), this.mIgEventObserver);
        removeLiveDataObserver(this.mDanceViewModel.getDefaultSelectedOption(), this.mDefaultSelectedPosition);
        removeLiveDataObserver(this.mDanceViewModel.getLluDanceBreak(), this.mDanceBreakObserver);
        removeLiveDataObserver(this.mDanceViewModel.getLluDanceStartPlayData(), this.mDanceStopFromFrameworkObserver);
    }

    private void refreshOrderInfoContainer() {
        if (this.mLluDanceViewDataList != null) {
            PendingIntent alarmUpForDance = LluDanceAlarmHelper.alarmUpForDance(getContext());
            if (alarmUpForDance != null) {
                long longExtra = alarmUpForDance.getIntent().getLongExtra(LluDanceAlarmHelper.ORDER_PLAY_DANCE_ITEM_TIME, System.currentTimeMillis());
                String stringExtra = alarmUpForDance.getIntent().getStringExtra(LluDanceAlarmHelper.ORDER_PLAY_DANCE_ITEM_RES_ID);
                if (this.mOrderInfoContainer.getVisibility() != 8 && longExtra == this.mOrderedTime && (stringExtra == null || stringExtra.equals(this.mOrderedDanceId))) {
                    return;
                }
                this.mOrderInfoContainer.setVisibility(0);
                VuiManager.instance().updateVuiScene(vuiSceneId(), getContext(), this.mOrderInfoContainer);
                LluDanceViewData findDanceItemById = findDanceItemById(stringExtra);
                this.mOrderedTime = longExtra;
                this.mOrderedDanceId = stringExtra;
                String stringExtra2 = alarmUpForDance.getIntent().getStringExtra(LluDanceAlarmHelper.ORDER_PLAY_DANCE_TITLE);
                if (findDanceItemById != null) {
                    stringExtra2 = findDanceItemById.getTitle();
                } else {
                    LogUtils.d(TAG, "danceItemById is null!");
                }
                int length = stringExtra2.length();
                if (length > 12) {
                    stringExtra2 = stringExtra2.substring(0, 12);
                    LogUtils.d(TAG, "title length > 12 ");
                }
                LogUtils.d(TAG, "title = " + stringExtra2 + "      title length = " + length);
                this.mOrderInfoTitle.setText(getString(R.string.llu_dance_fragment_order_info_title, LluDanceOrderTimeHelper.getDateFormatForTimeInMills(this.mOrderedTime), stringExtra2));
                long currentTimeMillis = this.mOrderedTime - System.currentTimeMillis();
                this.mOrderInfoTimeLeft.setText(Html.fromHtml(getString(R.string.llu_dance_fragment_order_info_time_left, LluDanceOrderTimeHelper.getDateFormatForTimeInMills(currentTimeMillis)), 0));
                startTimeLeftCountDown(currentTimeMillis);
                return;
            }
            this.mOrderInfoContainer.setVisibility(8);
            VuiManager.instance().updateVuiScene(vuiSceneId(), getContext(), this.mOrderInfoContainer);
            cancelTimeLeftCountDown();
            return;
        }
        LogUtils.d(TAG, "refresh order info , but still no dance list data");
    }

    private void cancelTimeLeftCountDown() {
        CountDownTimer countDownTimer = this.mTimeLeftCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void startTimeLeftCountDown(long timeLeft) {
        cancelTimeLeftCountDown();
        this.mTimeLeftCountDownTimer = new CountDownTimer(timeLeft, 1000L) { // from class: com.xiaopeng.lludancemanager.view.LluDanceMainFragment.2
            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
                String timeLeftFormat = LluDanceOrderTimeHelper.getTimeLeftFormat(millisUntilFinished);
                LogUtils.d(LluDanceMainFragment.TAG, "time left onTick : format time = " + timeLeftFormat);
                LluDanceMainFragment.this.mOrderInfoTimeLeft.setText(Html.fromHtml(LluDanceMainFragment.this.getString(R.string.llu_dance_fragment_order_info_time_left, timeLeftFormat), 0));
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                LogUtils.i(LluDanceMainFragment.TAG, "count down just for update ui. finish to to nothing");
                LluDanceMainFragment.this.mOrderInfoContainer.setVisibility(8);
                VuiManager.instance().updateVuiScene(LluDanceMainFragment.this.vuiSceneId(), LluDanceMainFragment.this.getContext(), LluDanceMainFragment.this.mOrderInfoContainer);
            }
        };
        LogUtils.d(TAG, "start time left count down : time left = " + timeLeft + "      format time = " + LluDanceOrderTimeHelper.getTimeLeftFormat(timeLeft));
        this.mTimeLeftCountDownTimer.start();
    }

    private LluDanceViewData findDanceItemById(String resId) {
        List<LluDanceViewData> list = this.mLluDanceViewDataList;
        if (list != null) {
            for (LluDanceViewData lluDanceViewData : list) {
                if (resId.equals(lluDanceViewData.getId())) {
                    return lluDanceViewData;
                }
            }
            return null;
        }
        LogUtils.d(TAG, "dance view data list is null");
        return null;
    }

    private void startPlayFragment(LluDanceViewData danceViewData, boolean orderPlay) {
        if (this.mLluConfirmDialog.isShowing()) {
            this.mLluConfirmDialog.dismiss();
        }
        LogUtils.d(TAG, "start play fragment for dance view data = " + danceViewData);
        this.mLluDancePlayFragment.setPlayingDanceViewData(danceViewData);
        this.mLluDancePlayFragment.setOrderPlay(orderPlay);
        getParentFragmentManager().beginTransaction().add(R.id.llu_dance_activity_fragment_container, this.mLluDancePlayFragment, TAG_DANCE_PLAY_FRAGMENT).commit();
        this.mDanceViewModel.getOrderedPlayItemIdData().setValue(null);
    }

    private void exitPlayFragment() {
        Log.d(TAG, "exit play fragment ");
        getParentFragmentManager().beginTransaction().remove(this.mLluDancePlayFragment).commit();
    }

    private void initViews(View rootView) {
        this.mLluDancePlayFragment = new LluDancePlayFragment();
        this.mLluConfirmDialog = new LluDanceSettingDialog(getContext());
        this.mLluDanceDataListRecyclerView = (XRecyclerView) rootView.findViewById(R.id.llu_dance_data_list_recycler_view);
        this.mRequestDanceDataLoading = (XLinearLayout) rootView.findViewById(R.id.llu_dance_activity_request_dance_data_loading_container);
        this.mLluDanceStateLayout = (LluDanceStateLayout) rootView.findViewById(R.id.llu_activity_dance_video_play_action_container);
        this.mOrderInfoContainer = (XRelativeLayout) rootView.findViewById(R.id.llu_dance_main_fragment_order_info_container);
        this.mOrderInfoTitle = (XTextView) rootView.findViewById(R.id.llu_dance_main_fragment_order_info_title);
        this.mOrderInfoTimeLeft = (XTextView) rootView.findViewById(R.id.llu_dance_main_fragment_order_info_time_left);
        ((XButton) rootView.findViewById(R.id.llu_dance_main_fragment_order_info_settings)).setOnClickListener(this);
        ((XButton) rootView.findViewById(R.id.llu_dance_main_fragment_order_info_delete)).setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v.getId() == R.id.llu_activity_dance_item_download_progress) {
            this.mDanceViewModel.pauseDownLoad(getSelectedDanceViewData());
        } else if (v.getId() == R.id.llu_activity_dance_video_play_action_container) {
            executeOperationForSelectedItem();
        } else if (v.getId() == R.id.llu_dance_main_fragment_order_info_settings) {
            LogUtils.d(TAG, "setting for order info");
            showLluDialogConfirm();
        } else if (v.getId() == R.id.llu_dance_main_fragment_order_info_delete) {
            LogUtils.d(TAG, "delete order event");
            removeAlarmRequest();
        }
    }

    private LluDanceViewData getSelectedDanceViewData() {
        if (this.mCurrentSelectData == null) {
            this.mCurrentSelectData = new LluDanceViewData(App.getInstance().getString(R.string.llu_dance_activity_dance_item_more_title), App.getInstance().getString(R.string.llu_dance_activity_dance_item_more_author), "", Constant.END_ITEM_MORE_ID, "", -1, -1L, -1.0d, "", "");
        }
        return this.mCurrentSelectData;
    }

    private void executeOperationForSelectedItem() {
        doActionByType(getSelectedDanceViewData());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doActionByType(LluDanceViewData danceViewData) {
        if (danceViewData != null) {
            int state = danceViewData.getState();
            if (state != 1) {
                if (state == 3) {
                    this.mDanceViewModel.pauseDownLoad(danceViewData);
                    LogUtils.d(TAG, "pauseDownload...");
                    return;
                } else if (state == 5) {
                    this.mDanceViewModel.resumeDownload(danceViewData.getId());
                    LogUtils.d("yjk", "doAction ---> 开始继续下载");
                    return;
                } else if (state != 7) {
                    if (state == 8) {
                        if (LluDanceHelper.checkLightDanceAvailable()) {
                            LogUtils.d(TAG, "doAction start play llu video");
                            startPlayLluVideo();
                            return;
                        }
                        return;
                    }
                    LogUtils.e(TAG, "default case:");
                    return;
                }
            }
            this.mDanceViewModel.startDownload(danceViewData);
            StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LLU_DANCE_E28_DANCE_ITEM_DOWNLOAD, danceViewData.getId() + "_" + danceViewData.getTitle());
        }
    }

    private void startPlayLluVideo() {
        showLluDialogConfirm();
    }

    private void showLluDialogConfirm() {
        this.mSelectForPlayDate = getSelectedDanceViewData();
        this.mLluConfirmDialog.setPositiveButton(getString(R.string.llu_dance_double_confirm_dialog_positive), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$sYwio4QUsOPDYXDRXyFGzUjr3ho
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                LluDanceMainFragment.this.lambda$showLluDialogConfirm$9$LluDanceMainFragment(xDialog, i);
            }
        });
        this.mLluConfirmDialog.setNegativeButton(getString(R.string.llu_dance_double_confirm_dialog_negative), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceMainFragment$Jzc9JG12uT4-l4fps5Q3LVwPNLY
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                LluDanceMainFragment.this.lambda$showLluDialogConfirm$10$LluDanceMainFragment(xDialog, i);
            }
        });
        this.mLluConfirmDialog.show();
    }

    public /* synthetic */ void lambda$showLluDialogConfirm$9$LluDanceMainFragment(XDialog xDialog, int i) {
        String str = TAG;
        LogUtils.d(str, "mLluConfirmDialog is " + this.mLluConfirmDialog.isOrderPlaySwitchCheck());
        if (ClickHelper.isFastClick(500L, true)) {
            LogUtils.d(str, "click llu dialog confirm button too quick!");
        } else if (LluDanceHelper.checkLightDanceAvailable() && !this.mScenarioViewModel.isScenarioModeRunning(ScenarioMode.Meditation)) {
            if (this.mLluConfirmDialog.isOrderPlaySwitchCheck()) {
                long orderedPlayTime = this.mLluConfirmDialog.getOrderedPlayTime();
                LogUtils.d(str, "ordered time for confirm dialog = " + orderedPlayTime + "    system time = " + System.currentTimeMillis() + "     orderedPlayTime > System.currentTimeMillis() = " + (orderedPlayTime > System.currentTimeMillis()));
                if (orderedPlayTime > System.currentTimeMillis()) {
                    LluDanceAlarmHelper.setAlarmForDance(getContext(), this.mSelectForPlayDate.getId(), this.mSelectForPlayDate.getEffectName(), orderedPlayTime, this.mSelectForPlayDate.getTitle());
                    refreshOrderInfoContainer();
                } else {
                    this.mLluConfirmDialog.refreshOrderedTime();
                    NotificationHelper.getInstance().showToast(R.string.llu_dance_double_confirm_dialog_order_play_time_overdue);
                }
            } else {
                LogUtils.d(str, "order time switch off");
                removeAlarmRequest();
                startPlayFragment(this.mSelectForPlayDate, false);
            }
            StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LLU_DANCE_E28_DANCE_PLAY_MODE, this.mLluConfirmDialog.isOrderPlaySwitchCheck() ? "2" : "1", this.mSelectForPlayDate.getId() + "_" + this.mSelectForPlayDate.getTitle());
        } else {
            LogUtils.d(str, "llu play action, is in Meditation: " + this.mScenarioViewModel.isScenarioModeRunning(ScenarioMode.Meditation));
        }
    }

    public /* synthetic */ void lambda$showLluDialogConfirm$10$LluDanceMainFragment(XDialog xDialog, int i) {
        this.mLluConfirmDialog.dismiss();
    }

    private void removeAlarmRequest() {
        LluDanceAlarmHelper.cancelAlarmForDance(getContext());
        cancelTimeLeftCountDown();
        this.mOrderInfoContainer.setVisibility(8);
        VuiManager.instance().updateVuiScene(vuiSceneId(), getContext(), this.mOrderInfoContainer);
    }

    private void updateBtnState(Integer state, int progress) {
        String valueOf = String.valueOf(R.id.llu_dance_data_list_recycler_view);
        if (this.mCurrentSelectData != null && !CollectionUtils.isEmpty(this.mLluDanceViewDataList)) {
            this.mLluDanceStateLayout.setVuiFatherElementId(valueOf + "_" + this.mLluDanceViewDataList.indexOf(this.mCurrentSelectData));
        }
        this.mLluDanceStateLayout.updateData(this.mCurrentSelectData);
        LogUtils.i(TAG, "set btn state state = " + state + "    progress = " + progress);
    }
}
