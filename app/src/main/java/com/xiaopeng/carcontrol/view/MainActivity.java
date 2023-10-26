package com.xiaopeng.carcontrol.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.unity3d.player.UnityPlayer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.direct.DxElementDirect;
import com.xiaopeng.carcontrol.direct.PageDirectItem;
import com.xiaopeng.carcontrol.direct.message.DirectEventMsg;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.UserBookHelper;
import com.xiaopeng.carcontrol.model.DebugFuncModel;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.MainActivity;
import com.xiaopeng.carcontrol.view.fragment.BaseFragment;
import com.xiaopeng.carcontrol.view.fragment.EmptyFragment;
import com.xiaopeng.carcontrol.view.fragment.VuiFragment;
import com.xiaopeng.carcontrol.view.unity.IUnityControl;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.ciu.CiuViewModel;
import com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.xui.app.delegate.XActivityBind;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRecyclerView;
import com.xiaopeng.xui.widget.XTextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

@XActivityBind(1)
/* loaded from: classes2.dex */
public class MainActivity extends VuiMultiSceneActivity implements IUnityControl {
    private static final String EMPTY = "EmptyFragment";
    private static final long MAX_DURATION = 400;
    private static final int MSG_SWITCH_UNITY_THEME = 256;
    private static final int NEED_TIME = 7;
    private static final String TAG = "CarMainActivity";
    private SimpleItemRecyclerViewAdapter mAdapter;
    private View mLeftControlView;
    private XRecyclerView mRecyclerView;
    private View mUnityMask;
    protected UnityPlayer mUnityPlayer;
    private VcuViewModel mVcuViewModel;
    private final boolean mIsUnityEnabled = false;
    private int mUnityViewAngle = 0;
    private int mSelectedIndex = 0;
    private int mSelectedPosition = -1;
    private String mCurrentFragmentTag = null;
    private volatile boolean mIsConnected2CarSvc = false;
    private volatile boolean mIsWaitingCarSvc = false;
    private Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.xiaopeng.carcontrol.view.MainActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 256) {
                MainActivity.this.updateUnityTheme();
            }
        }
    };
    private long mLastTime = 0;
    private int mTimes = 0;

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUnityTheme() {
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public boolean isMainScene() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.view.unity.IUnityControl
    public void switchUnityAngle(int viewAngle) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        StartPerfUtils.onCreateBegin();
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.mCurrentFragmentTag = savedInstanceState.getString("mCurrentFragmentTag");
            this.mSelectedIndex = savedInstanceState.getInt("mSelectedIndex");
            LogUtils.d(TAG, "onCreate: mSelectedIndex=" + this.mSelectedIndex + ", mCurrentFragmentTag=" + this.mCurrentFragmentTag, false);
        }
        setContentView(R.layout.activity_main);
        if (BaseFeatureOption.getInstance().isSupportFullscreenPanel()) {
            getWindowAttributes().setGravity(17);
        }
        getWindowVisible().setAutoVisibleEnableOnPause(true);
        XImageView xImageView = (XImageView) findViewById(R.id.close_iv);
        if (xImageView != null) {
            xImageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$uN6E_3BLnffi30GUgkBc1qXwv-M
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.this.lambda$onCreate$0$MainActivity(view);
                }
            });
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(VuiConstants.PROPS_GENERALACT, VuiConstants.GENERALACT_CLOSE);
                xImageView.setVuiProps(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        findViewById(R.id.user_book_enter).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$7gz38A0w2_QmxVxpycVj_gum_Bc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.lambda$onCreate$1(view);
            }
        });
        this.mLeftControlView = findViewById(R.id.left_control_view);
        XRecyclerView xRecyclerView = (XRecyclerView) findViewById(R.id.category_list);
        this.mRecyclerView = xRecyclerView;
        xRecyclerView.setVuiCanControlScroll(false);
        if (this.mRecyclerView != null) {
            SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(MainCategory.getCategoryItems());
            this.mAdapter = simpleItemRecyclerViewAdapter;
            this.mRecyclerView.setAdapter(simpleItemRecyclerViewAdapter);
            VuiManager.instance().initRecyclerView(this.mRecyclerView, VuiManager.SCENE_TAB);
            this.mSelectedIndex = getIntent().getIntExtra(GlobalConstant.EXTRA.MAIN_CATEGORY_INDEX, 0);
        } else {
            finish();
        }
        initDebugBtn();
        initCarLifeBtn();
        this.mIsConnected2CarSvc = CarClientWrapper.getInstance().isCarServiceConnectedSync();
        LogUtils.i(TAG, "onCreate mIsConnected2CarSvc: " + this.mIsConnected2CarSvc, false);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$UX4REz1Lx9lfL4sicld7poc55cc
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.this.lambda$onCreate$4$MainActivity();
            }
        });
        initVui();
        StatisticUtils.sendStatistic(PageEnum.MAIN_PAGE, BtnEnum.MAIN_BTN, new Object[0]);
        StartPerfUtils.onCreateEnd();
    }

    public /* synthetic */ void lambda$onCreate$0$MainActivity(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onCreate$1(View v) {
        UserBookHelper.openUserBook("", false);
        StatisticUtils.sendStatistic(PageEnum.USERGUIDE_PAGE, BtnEnum.USER_GUIDER_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$onCreate$4$MainActivity() {
        this.mVcuViewModel = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        if (CarBaseConfig.getInstance().isSupportCiuConfig()) {
            final CiuViewModel ciuViewModel = (CiuViewModel) ViewModelManager.getInstance().getViewModelImpl(ICiuViewModel.class);
            runOnUiThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$2hb0s-RLeqzdI-QdE9Tb-MMhipo
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.this.lambda$null$3$MainActivity(ciuViewModel);
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$3$MainActivity(final CiuViewModel mCiuViewModel) {
        mCiuViewModel.getCiuStateData().observe(this, new Observer() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$KiSFER3qQw7iwPeIxIhFAeWewD0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.lambda$null$2$MainActivity((Boolean) obj);
            }
        });
        refreshSmartCiuView(mCiuViewModel.isCiuExist());
    }

    public /* synthetic */ void lambda$null$2$MainActivity(Boolean isExisted) {
        if (isExisted != null) {
            refreshSmartCiuView(isExisted.booleanValue());
        }
    }

    private void initCarLifeBtn() {
        if (BaseFeatureOption.getInstance().isSupportCarLife()) {
            View findViewById = findViewById(R.id.car_life_btn);
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$vuSAtR0yiNZuTZ04hG1GmTDmYWk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.this.lambda$initCarLifeBtn$5$MainActivity(view);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initCarLifeBtn$5$MainActivity(View v) {
        try {
            Intent intent = new Intent("com.xiaopeng.carlife.launch.home");
            intent.addFlags(ClientDefaults.MAX_MSG_SIZE);
            intent.addCategory("android.intent.category.DEFAULT");
            startActivity(intent);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), false);
        }
        StatisticUtils.sendCarLifeStatistic(PageEnum.CAR_LIFE_PAGE, BtnEnum.CAR_LIFE_PAGE_ENTER, new Object[0]);
    }

    private void initDebugBtn() {
        if (DebugFuncModel.getInstance().isDebugTabEnabled()) {
            return;
        }
        findViewById(R.id.debug_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$nV_VL33I8j_f0rG3FzQYW_AYQF8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$initDebugBtn$6$MainActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initDebugBtn$6$MainActivity(View v) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastTime < MAX_DURATION) {
            int i = this.mTimes + 1;
            this.mTimes = i;
            if (7 - i <= 0) {
                this.mTimes = 0;
                DebugFuncModel.getInstance().setDebugTabEnable(true);
                this.mAdapter.setItems(MainCategory.getCategoryItems());
            }
        } else {
            this.mTimes = 0;
            this.mTimes = 0 + 1;
        }
        this.mLastTime = currentTimeMillis;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        LogUtils.i(TAG, "onStart", false);
        StartPerfUtils.onStartBegin();
        super.onStart();
        this.mIsConnected2CarSvc = CarClientWrapper.getInstance().isCarServiceConnectedSync();
        LogUtils.i(TAG, "onStart set mIsConnected2CarSvc: " + this.mIsConnected2CarSvc, false);
        Intent intent = getIntent();
        if (intent != null) {
            changeDirectionAction(intent);
        }
        StartPerfUtils.onStartEnd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        StartPerfUtils.onResumeBegin();
        LogUtils.d(TAG, "onResume", false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
            DxElementDirect.getInstance().setMainActivityResumed(true);
        }
        super.onResume();
        ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$BXudtvmAGiKylfa2_jnRdGLbEpM
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.this.lambda$onResume$7$MainActivity();
            }
        }, 0L);
        if (this.mRecyclerView.getAdapter() != null) {
            this.mRecyclerView.getAdapter().notifyDataSetChanged();
        }
        App.getInstance().reconnectToResourceManager();
        StartPerfUtils.onResumeEnd();
    }

    public /* synthetic */ void lambda$onResume$7$MainActivity() {
        LogUtils.d(TAG, "onResume mSelectedIndex: " + this.mSelectedIndex, false);
        this.mAdapter.changeItem(this.mSelectedIndex);
    }

    private /* synthetic */ void lambda$onResume$8() {
        this.mUnityMask.setVisibility(8);
    }

    @Override // android.app.Activity
    protected void onRestart() {
        StartPerfUtils.onReStartBegin();
        super.onRestart();
        StartPerfUtils.onReStartEnd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle outState) {
        LogUtils.d(TAG, "onSaveInstanceState: mCurrentFragmentTag-->" + this.mCurrentFragmentTag + ",mSelectedIndex-->" + this.mSelectedIndex);
        outState.putString("mCurrentFragmentTag", this.mCurrentFragmentTag);
        outState.putInt("mSelectedIndex", this.mSelectedIndex);
        super.onSaveInstanceState(outState);
    }

    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter;
        super.onConfigurationChanged(newConfig);
        if (!XThemeManager.isThemeChanged(newConfig) || (simpleItemRecyclerViewAdapter = this.mAdapter) == null) {
            return;
        }
        simpleItemRecyclerViewAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.VuiMultiSceneActivity, com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        LogUtils.d(TAG, "onPause", false);
        DxElementDirect.getInstance().setMainActivityResumed(false);
        EventBus.getDefault().unregister(this);
        super.onPause();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        LogUtils.d(TAG, "onStop", false);
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    private void changeDirectionAction(Intent intent) {
        PageDirectItem page;
        if (!DxElementDirect.getInstance().isPageDirect(intent) || (page = DxElementDirect.getInstance().getPage(intent.getData())) == null || page.getData() == null) {
            return;
        }
        this.mSelectedIndex = MainCategory.getItemIdxByClassName(page.getData());
        LogUtils.i(TAG, "changeDirectionAction uri:" + intent.getData(), false);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(DirectEventMsg event) {
        PageDirectItem page;
        Uri url = event.getUrl();
        LogUtils.i(TAG, "Receive DirectEventMsg: " + url);
        if (!DxElementDirect.getInstance().isPageDirect(url) || (page = DxElementDirect.getInstance().getPage(url)) == null || page.getData() == null) {
            return;
        }
        Intent intent = getIntent();
        if (intent != null) {
            intent.setData(url);
            setIntent(intent);
        }
        final int itemIdxByClassName = MainCategory.getItemIdxByClassName(page.getData());
        if (itemIdxByClassName != this.mSelectedIndex) {
            ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$ygQje5CnD1lavQcTp2O34UyYrlw
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.this.lambda$onUpdateEvent$9$MainActivity(itemIdxByClassName);
                }
            }, 100L);
        } else {
            dispatchDirectEventToFragment();
        }
    }

    public /* synthetic */ void lambda$onUpdateEvent$9$MainActivity(final int targetIdx) {
        this.mAdapter.changeItem(targetIdx);
    }

    private void dispatchDirectEventToFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        String str = this.mCurrentFragmentTag;
        Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(str);
        LogUtils.i(TAG, "dispatchDirectEventToFragment: current:" + str + "----" + findFragmentByTag, false);
        if (findFragmentByTag == null) {
            findFragmentByTag = supportFragmentManager.findFragmentById(R.id.category_detail_container);
            LogUtils.i(TAG, "dispatchDirectEventToFragment: find current fragment:" + findFragmentByTag, false);
        }
        if (findFragmentByTag instanceof BaseFragment) {
            ((BaseFragment) findFragmentByTag).dispatchElementDirect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeFragment(final MainCategoryItem item, boolean force) {
        LogUtils.i(TAG, "changeFragment to " + item.getIndex() + ", by force: " + force, false);
        if (!this.mIsConnected2CarSvc) {
            LogUtils.i(TAG, "Car service has not been connected", false);
            if (this.mIsWaitingCarSvc) {
                return;
            }
            this.mIsWaitingCarSvc = true;
            showEmptyFragment(true);
            this.mAdapter.setEnable(false);
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$5pwW80a8nsQ2kF4DFpt6xDDY3Rk
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.this.lambda$changeFragment$11$MainActivity(item);
                }
            });
            return;
        }
        this.mAdapter.setEnable(true);
        changeFragmentInner(item, force);
    }

    public /* synthetic */ void lambda$changeFragment$11$MainActivity(final MainCategoryItem targetItem) {
        LogUtils.d(TAG, "Check if car service connected", false);
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            this.mIsConnected2CarSvc = true;
        }
        this.mIsWaitingCarSvc = true;
        ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$L5bQll3NBgrR0NHiv6OsSV3cjYM
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.this.lambda$null$10$MainActivity(targetItem);
            }
        }, 50L);
    }

    public /* synthetic */ void lambda$null$10$MainActivity(final MainCategoryItem targetItem) {
        LogUtils.d(TAG, "Connect2CarTask onPostExecute", false);
        showEmptyFragment(false);
        this.mAdapter.setEnable(true);
        Lifecycle.State currentState = getLifecycle().getCurrentState();
        LogUtils.d(TAG, "Current life cycle state: " + currentState, false);
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            changeFragmentInner(targetItem, true);
        } else {
            LogUtils.e(TAG, "changeFragmentInner exit, cause activity is in " + currentState, false);
        }
    }

    private void changeFragmentInner(MainCategoryItem item, boolean force) {
        String str = this.mCurrentFragmentTag;
        String mainCategoryItem = item.toString();
        if (force || !mainCategoryItem.equals(str)) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            this.mCurrentFragmentTag = mainCategoryItem;
            FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
            Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(str);
            LogUtils.d(TAG, "changeFragmentInner: current:" + str + "----" + findFragmentByTag);
            if (findFragmentByTag == null) {
                findFragmentByTag = supportFragmentManager.findFragmentById(R.id.category_detail_container);
                LogUtils.d(TAG, "changeFragmentInner: current fragment:" + findFragmentByTag);
            }
            if (findFragmentByTag != null && !mainCategoryItem.equals(str)) {
                beginTransaction.hide(findFragmentByTag);
                beginTransaction.setMaxLifecycle(findFragmentByTag, Lifecycle.State.STARTED);
            }
            LogUtils.d(TAG, "changeFragmentInner: new fragment:" + mainCategoryItem);
            Fragment findFragmentByTag2 = supportFragmentManager.findFragmentByTag(mainCategoryItem);
            if (findFragmentByTag2 == null) {
                try {
                    Fragment fragment = (Fragment) item.getFragmentClazz().newInstance();
                    beginTransaction.add(R.id.category_detail_container, fragment, mainCategoryItem);
                    initFragmentVuiParams((VuiFragment) fragment, item.getVuiSceneId(), true, this.mSubScenes, getIdentifiedFatherId(item.getItemPos()));
                } catch (IllegalAccessException | InstantiationException e) {
                    LogUtils.e(TAG, (String) null, e, false);
                }
            } else {
                beginTransaction.show(findFragmentByTag2);
                beginTransaction.setMaxLifecycle(findFragmentByTag2, Lifecycle.State.RESUMED);
            }
            beginTransaction.commitNowAllowingStateLoss();
        }
    }

    private String getIdentifiedFatherId(int index) {
        String str = getResources().getIdentifier("text1", "id", getPackageName()) + "_" + index;
        LogUtils.i(TAG, "getIdentifiedFatherId: " + str);
        return str;
    }

    private void showEmptyFragment(boolean show) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(EMPTY);
        if (show) {
            if (findFragmentByTag == null) {
                supportFragmentManager.beginTransaction().replace(R.id.category_detail_container, new EmptyFragment(), EMPTY).commitNowAllowingStateLoss();
            }
        } else if (findFragmentByTag != null) {
            supportFragmentManager.beginTransaction().remove(findFragmentByTag).commitNowAllowingStateLoss();
        }
    }

    private void refreshSmartCiuView(boolean isExisted) {
        SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter = this.mAdapter;
        if (simpleItemRecyclerViewAdapter != null) {
            simpleItemRecyclerViewAdapter.refreshSmartCiuView(isExisted);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.VuiMultiSceneActivity
    public void initVui() {
        this.mSubScenes.clear();
        this.mSubScenes.add(VuiManager.SCENE_TAB);
        this.mSceneId = VuiManager.SCENE_TAB;
        this.mRootView = this.mLeftControlView;
        this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$K9nnemMmSPhlI7j-MUQoz3N_pR4
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.this.initVuiScene();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
        private int mCiuItemPos;
        private boolean mHasCiuItem;
        private final View.OnClickListener mOnClickListener;
        private final View.OnTouchListener mOnTouchListener;
        private final List<MainCategoryItem> mItems = new ArrayList();
        private volatile boolean mEnable = false;
        private final SparseArray<MainCategoryItem> mItemsWithIdx = new SparseArray<>();

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public /* bridge */ /* synthetic */ void onBindViewHolder(ViewHolder viewHolder, int position, List payloads) {
            onBindViewHolder2(viewHolder, position, (List<Object>) payloads);
        }

        public /* synthetic */ boolean lambda$new$0$MainActivity$SimpleItemRecyclerViewAdapter(View v, MotionEvent event) {
            return !this.mEnable || (event.getAction() == 0 && ClickHelper.isFastClick(100L));
        }

        public /* synthetic */ void lambda$new$1$MainActivity$SimpleItemRecyclerViewAdapter(View v) {
            onItemClick(((Integer) v.getTag()).intValue());
        }

        SimpleItemRecyclerViewAdapter(List<MainCategoryItem> items) {
            boolean isSupportCiuConfig = CarBaseConfig.getInstance().isSupportCiuConfig();
            this.mHasCiuItem = isSupportCiuConfig;
            this.mCiuItemPos = isSupportCiuConfig ? 5 : -1;
            this.mOnTouchListener = new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$SimpleItemRecyclerViewAdapter$wZCNkuag21v4ILpP4RRsVvzviPY
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return MainActivity.SimpleItemRecyclerViewAdapter.this.lambda$new$0$MainActivity$SimpleItemRecyclerViewAdapter(view, motionEvent);
                }
            };
            this.mOnClickListener = new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$SimpleItemRecyclerViewAdapter$N6ILbdyVz3L_Q-EIVurV4kwu2G4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.SimpleItemRecyclerViewAdapter.this.lambda$new$1$MainActivity$SimpleItemRecyclerViewAdapter(view);
                }
            };
            setItems(items);
        }

        public void changeItem(int itemIdx) {
            MainCategoryItem itemByIndex = getItemByIndex(itemIdx);
            if (itemByIndex != null) {
                onItemClick(MainActivity.this.mAdapter.mItems.indexOf(itemByIndex), true);
            } else {
                onItemClick(0, true);
            }
        }

        public void setEnable(boolean enable) {
            this.mEnable = enable;
        }

        void setItems(List<MainCategoryItem> items) {
            this.mItems.clear();
            this.mItemsWithIdx.clear();
            for (int i = 0; i < items.size(); i++) {
                MainCategoryItem mainCategoryItem = items.get(i);
                this.mItems.add(mainCategoryItem);
                this.mItemsWithIdx.put(mainCategoryItem.getIndex(), mainCategoryItem);
                if (mainCategoryItem.getIndex() == 5) {
                    this.mCiuItemPos = i;
                }
            }
            if (!this.mHasCiuItem) {
                this.mCiuItemPos = -1;
            }
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            MainCategoryItem mainCategoryItem = this.mItems.get(position);
            viewHolder.mItemView.setCompoundDrawablesWithIntrinsicBounds(0, mainCategoryItem.getIcon(), 0, 0);
            viewHolder.mItemView.setText(mainCategoryItem.getTitle());
            viewHolder.mItemView.setSelected(mainCategoryItem.getIndex() == MainActivity.this.mSelectedIndex);
            viewHolder.mItemView.setTag(Integer.valueOf(position));
            initVui(viewHolder.mItemView, position);
        }

        private void initVui(View view, int position) {
            if (view instanceof IVuiElement) {
                IVuiElement iVuiElement = (IVuiElement) view;
                iVuiElement.setVuiPosition(position);
                iVuiElement.setVuiElementId(view.getId() + "_" + position);
            }
        }

        /* renamed from: onBindViewHolder  reason: avoid collision after fix types in other method */
        public void onBindViewHolder2(ViewHolder viewHolder, int position, List<Object> payloads) {
            if (!payloads.isEmpty()) {
                int[] iArr = (int[]) payloads.get(0);
                LogUtils.d(MainActivity.TAG, "onBindViewHolder: position=" + position + ", payloads=" + Arrays.toString(iArr));
                for (int i : iArr) {
                    if (i == position) {
                        viewHolder.mItemView.setSelected(position == MainActivity.this.mSelectedPosition);
                    } else {
                        viewHolder.mItemView.setSelected(false);
                    }
                }
                return;
            }
            super.onBindViewHolder((SimpleItemRecyclerViewAdapter) viewHolder, position, payloads);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mItems.size();
        }

        MainCategoryItem getItem(int position) {
            return this.mItems.get(position);
        }

        MainCategoryItem getItemByIndex(int index) {
            return this.mItemsWithIdx.get(index);
        }

        void refreshSmartCiuView(boolean isCiuExisted) {
            if (this.mHasCiuItem != isCiuExisted) {
                if (isCiuExisted) {
                    LogUtils.d(MainActivity.TAG, "Show ciu view", false);
                    setItems(MainCategory.getCategoryItems());
                } else {
                    LogUtils.d(MainActivity.TAG, "Hide ciu view", false);
                    this.mItems.remove(this.mCiuItemPos);
                    this.mHasCiuItem = false;
                    this.mCiuItemPos = -1;
                    if (MainActivity.this.mSelectedIndex == 5) {
                        switchItem(this.mItems.get(0));
                    }
                    notifyDataSetChanged();
                }
                this.mHasCiuItem = isCiuExisted;
            }
        }

        private void switchItem(final MainCategoryItem item) {
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$SimpleItemRecyclerViewAdapter$j0kSev_Irv66NUqA9HIMojVsL5U
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.SimpleItemRecyclerViewAdapter.this.lambda$switchItem$2$MainActivity$SimpleItemRecyclerViewAdapter(item);
                }
            });
        }

        public /* synthetic */ void lambda$switchItem$2$MainActivity$SimpleItemRecyclerViewAdapter(final MainCategoryItem item) {
            MainActivity.this.mSelectedIndex = item.getIndex();
            notifyDataSetChanged();
            MainActivity.this.changeFragment(item, false);
        }

        private void onItemClick(int itemPosition) {
            onItemClick(itemPosition, false);
        }

        private void onItemClick(int itemPosition, final boolean forceUpdate) {
            final MainCategoryItem item = getItem(itemPosition);
            LogUtils.d(MainActivity.TAG, "onCategory clicked " + item);
            if (item != null) {
                if (forceUpdate || itemPosition != MainActivity.this.mSelectedPosition) {
                    int i = MainActivity.this.mSelectedPosition;
                    MainActivity.this.mSelectedPosition = itemPosition;
                    MainActivity.this.mSelectedIndex = item.getIndex();
                    notifyItemRangeChanged(Math.min(i, MainActivity.this.mSelectedPosition), Math.abs(i - MainActivity.this.mSelectedPosition) + 1, new int[]{i, MainActivity.this.mSelectedPosition});
                    ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$MainActivity$SimpleItemRecyclerViewAdapter$loABco1d5f4D2bKLTUgm4ZKm-ZA
                        @Override // java.lang.Runnable
                        public final void run() {
                            MainActivity.SimpleItemRecyclerViewAdapter.this.lambda$onItemClick$3$MainActivity$SimpleItemRecyclerViewAdapter(item, forceUpdate);
                        }
                    });
                }
            }
        }

        public /* synthetic */ void lambda$onItemClick$3$MainActivity$SimpleItemRecyclerViewAdapter(final MainCategoryItem item, final boolean forceUpdate) {
            MainActivity.this.changeFragment(item, forceUpdate);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public class ViewHolder extends RecyclerView.ViewHolder {
            XTextView mItemView;

            ViewHolder(View itemView) {
                super(itemView);
                XTextView xTextView = (XTextView) itemView;
                this.mItemView = xTextView;
                xTextView.setOnTouchListener(SimpleItemRecyclerViewAdapter.this.mOnTouchListener);
                this.mItemView.setOnClickListener(SimpleItemRecyclerViewAdapter.this.mOnClickListener);
            }
        }
    }
}
