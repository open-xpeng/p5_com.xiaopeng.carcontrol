package com.xiaopeng.lludancemanager.view;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.lludancemanager.Constant;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.lludancemanager.adapter.LluDanceFragmentPagerAdapter;
import com.xiaopeng.lludancemanager.data.DanceFragmentPageAdapterData;
import com.xiaopeng.lludancemanager.helper.LluDanceAlarmHelper;
import com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel;
import com.xiaopeng.lludancemanager.viewmodel.LluDanceViewModel;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.utils.XActivityUtils;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.XTabsBar;
import com.xiaopeng.xui.widget.XViewPager;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class LluDanceActivityNew extends XActivity {
    public static final String LLU_DANCE_ORDER_PLAY_ACTION = "llu_dance_order_play_action";
    private static final String TAG = "LluDanceActivityNew";
    private static final int WINDOW_POSITION_OPEN = 0;
    private AudioManager mAudioManager;
    private LluDanceFragmentPagerAdapter mDanceFragmentPagerAdapter;
    private LluDanceViewModel mDanceViewModel;
    private XViewPager mDanceViewPager;
    private Observer<Integer> mIgEventObserver = null;
    private XTabsBar mTabsBar;
    private int mUserDefaultStreamVolume;

    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");
        setContentView(R.layout.activity_llu_dance_new);
        this.mDanceViewPager = (XViewPager) findViewById(R.id.llu_dance_activity_view_pager);
        this.mTabsBar = (XTabsBar) findViewById(R.id.llu_dance_activity_viewpager_tab_layout);
        if (CarBaseConfig.getInstance().isSupportAiLlu()) {
            ViewGroup.LayoutParams layoutParams = this.mTabsBar.getLayoutParams();
            layoutParams.width *= 2;
            this.mTabsBar.setLayoutParams(layoutParams);
        }
        this.mTabsBar.setOnTabsClickListener(new XTabsBar.OnTabsBarClickListener() { // from class: com.xiaopeng.lludancemanager.view.LluDanceActivityNew.1
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeStart(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    LluDanceActivityNew.this.mDanceViewPager.setCurrentItem(index, true);
                }
            }

            @Override // com.xiaopeng.xui.widget.XTabsBar.OnTabsBarClickListener
            public void onTabsBarCloseClick(View view) {
                LluDanceActivityNew.this.exitDanceActivity();
            }
        });
        View findViewById = findViewById(R.id.close_iv);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceActivityNew$Vn3nKZFox1qOoA1Oc_g54VajA04
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LluDanceActivityNew.this.lambda$onCreate$0$LluDanceActivityNew(view);
                }
            });
        }
        ImageView imageView = (ImageView) findViewById(R.id.llu_dance_car_body);
        if (imageView != null) {
            imageView.setImageResource(App.getMainControlPageCarBodyImg());
        }
        this.mDanceViewModel = (LluDanceViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluDanceViewModel.class);
        this.mIgEventObserver = new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$LluDanceActivityNew$uFMnMKwfnGe1aHl6jAUvw1b_rfo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluDanceActivityNew.this.lambda$onCreate$1$LluDanceActivityNew((Integer) obj);
            }
        };
        setLiveDataObserver(this.mDanceViewModel.getIgEvent(), this.mIgEventObserver);
        this.mAudioManager = (AudioManager) getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
        LluDanceFragmentPagerAdapter lluDanceFragmentPagerAdapter = new LluDanceFragmentPagerAdapter(getSupportFragmentManager());
        this.mDanceFragmentPagerAdapter = lluDanceFragmentPagerAdapter;
        lluDanceFragmentPagerAdapter.setAdapterDataList(buildAdapterDataList());
        this.mDanceViewPager.setAdapter(this.mDanceFragmentPagerAdapter);
        this.mDanceFragmentPagerAdapter.feedInTabData(this.mTabsBar);
        this.mDanceViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.xiaopeng.lludancemanager.view.LluDanceActivityNew.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                LluDanceActivityNew.this.mTabsBar.selectTab(position, true);
            }
        });
        filterIntent(getIntent());
    }

    public /* synthetic */ void lambda$onCreate$0$LluDanceActivityNew(View v) {
        exitDanceActivity();
    }

    public /* synthetic */ void lambda$onCreate$1$LluDanceActivityNew(Integer igOffEvent) {
        if (igOffEvent.intValue() == 0) {
            LogUtils.d(TAG, "dance main fragment received ig off event, removeAlarmRequestWithSwitchReset");
            exitDanceActivity();
        }
    }

    private void filterIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String string = extras.getString(Constant.SELECT_RSC_ID);
            if (!TextUtils.isEmpty(string)) {
                this.mDanceViewModel.setIntentRscId(string);
                LogUtils.d(TAG, "dance activity open from dropdown menu dance id = " + string);
                return;
            }
            String string2 = extras.getString(LluDanceAlarmHelper.ORDER_PLAY_DANCE_ITEM_RES_ID);
            this.mDanceViewModel.getOrderedPlayItemIdData().postValue(string2);
            LogUtils.d(TAG, "Karl log open time optimize dance activity new ordered dance id = " + string2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String str = TAG;
        LogUtils.d(str, "onNewIntent");
        Bundle extras = intent.getExtras();
        LogUtils.d(str, "onNewIntent bundle = " + extras);
        if (extras != null) {
            String string = extras.getString(LluDanceAlarmHelper.ORDER_PLAY_DANCE_ITEM_RES_ID);
            this.mDanceViewModel.getOrderedPlayItemIdData().postValue(string);
            LogUtils.d(str, "Karl log open time optimize dance activity new ordered dance id = " + string);
            String string2 = extras.getString(Constant.SELECT_RSC_ID);
            this.mDanceViewModel.setIntentRscId(string2);
            LogUtils.d(str, "dance activity open from dropdown menu dance id = " + string2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        String str = TAG;
        LogUtils.d(str, "onResume");
        this.mUserDefaultStreamVolume = this.mAudioManager.getStreamVolume(3);
        LogUtils.d(str, "onResume get current stream volume = " + this.mUserDefaultStreamVolume);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        LogUtils.d(TAG, "onPause");
        restoreStreamVolume();
        restoreWindowStateToast();
    }

    private void restoreWindowStateToast() {
        String str = TAG;
        LogUtils.i(str, "restoreWindowStateToast");
        if (SharedPreferenceUtil.getLluAutoWindow() && this.mDanceViewModel.isAutoWindowExecuted()) {
            this.mDanceViewModel.resetAutoWindowFlag();
            float[] windowsPos = ((WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class)).getWindowsPos();
            int length = windowsPos.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = true;
                    break;
                }
                float f = windowsPos[i];
                LogUtils.i(TAG, "window position = " + f);
                if (f != 0.0f) {
                    break;
                }
                i++;
            }
            if (z) {
                NotificationHelper.getInstance().showToast(R.string.llu_dance_fragment_exit_dance_fragment_restore_window_toast);
                return;
            } else {
                LogUtils.i(TAG, "");
                return;
            }
        }
        LogUtils.i(str, "not switch on auto window, ignore restore window state . auto window = " + SharedPreferenceUtil.getLluAutoWindow() + "     auto window executed = " + this.mDanceViewModel.isAutoWindowExecuted());
    }

    private void restoreStreamVolume() {
        String str = TAG;
        LogUtils.i(str, "restoreStreamVolume");
        int streamVolume = this.mAudioManager.getStreamVolume(3);
        LogUtils.d(str, "onPause current stream volume = " + streamVolume);
        if (streamVolume == 25) {
            LogUtils.d(str, "onPause user not switch volume in dance playing, reset volume to user default stored before , default = " + this.mUserDefaultStreamVolume);
            this.mAudioManager.setStreamVolume(3, this.mUserDefaultStreamVolume, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        LogUtils.d(TAG, "onStop");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy");
    }

    @Override // com.xiaopeng.xui.app.XActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        exitDanceActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitDanceActivity() {
        if (LluDanceAlarmHelper.alarmUpForDance(this) != null) {
            LogUtils.d(TAG, "existing intent not null, move task to back");
            XActivityUtils.moveTaskToBack(this, true);
            return;
        }
        LogUtils.d(TAG, "existing intent null, finish activity");
        finish();
    }

    private ArrayList<DanceFragmentPageAdapterData> buildAdapterDataList() {
        ArrayList<DanceFragmentPageAdapterData> arrayList = new ArrayList<>();
        arrayList.add(new DanceFragmentPageAdapterData(LluDanceFragmentPagerAdapter.FRAGMENT_LLU_DANCE_TITLE));
        if (CarBaseConfig.getInstance().isSupportAiLlu()) {
            arrayList.add(new DanceFragmentPageAdapterData(LluDanceFragmentPagerAdapter.FRAGMENT_AI_LLU_TITLE));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        liveData.observe(this, observer);
    }
}
