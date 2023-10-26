package com.xiaopeng.carcontrol.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.fragment.spacecapsule.ICapsuleSleepGuideInterface;
import com.xiaopeng.carcontrol.view.fragment.spacecapsule.MattressSetupFragment;
import com.xiaopeng.carcontrol.view.fragment.spacecapsule.MattressStoreFragment;
import com.xiaopeng.carcontrol.view.fragment.spacecapsule.ShadeSetupFragment;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.speech.protocol.bean.stats.SceneSettingStatisticsBean;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.app.delegate.XActivityBind;
import com.xiaopeng.xui.utils.XActivityUtils;

@XActivityBind(1)
/* loaded from: classes2.dex */
public class SpaceCapsuleUseGuideActivity extends XActivity implements ICapsuleSleepGuideInterface {
    private boolean mAuto;
    private boolean mEnter;
    private String mCurrentFragmentTag = null;
    private int mSleepBedType = -1;
    private String openFrom = "";
    private final String SHADE_SETUP_PAGE_TAG = "shade_setup";
    private final String MATTRESS_SETUP_PAGE_TAG = "mattress_setup";
    private final String MATTRESS_STORE_PAGE_TAG = "mattress_store";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        StartPerfUtils.onCreateBegin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_capsule_sleep_use_guide);
        getWindowAttributes().setGravity(17).setFlags(1024).setSystemUiVisibility(1538);
        this.mEnter = getIntent().getBooleanExtra(GlobalConstant.EXTRA.KEY_SPACE_SLEEP_GUIDE_FLAT, false);
        this.mAuto = getIntent().getBooleanExtra(GlobalConstant.EXTRA.KEY_SPACE_SLEEP_GUIDE_AUTO, false);
        this.mSleepBedType = getIntent().getIntExtra(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, 2);
        this.openFrom = TextUtils.isEmpty(getIntent().getStringExtra(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM)) ? "" : getIntent().getStringExtra(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM);
        LogUtils.d(SceneSettingStatisticsBean.PAGE_GUIDE, "capsule guide mSleepBedType:" + this.mSleepBedType + " " + this.mEnter + " " + this.mAuto + " " + this.openFrom);
        if (this.mEnter) {
            replaceFragment("shade_setup");
        } else {
            replaceFragment("mattress_store");
        }
        findViewById(R.id.close_iv).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.-$$Lambda$SpaceCapsuleUseGuideActivity$SrN8BGQjVIu3GPe_DuPj-Fm3SkA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpaceCapsuleUseGuideActivity.this.lambda$onCreate$0$SpaceCapsuleUseGuideActivity(view);
            }
        });
        uploadBI(true);
        StartPerfUtils.onCreateEnd();
    }

    public /* synthetic */ void lambda$onCreate$0$SpaceCapsuleUseGuideActivity(View v) {
        XActivityUtils.finish(this);
    }

    private void replaceFragment(String fragmentTag) {
        Fragment newInstance;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.space_capsule_enter, R.anim.space_capsule_exit);
        String str = this.mCurrentFragmentTag;
        Fragment findFragmentByTag = str != null ? supportFragmentManager.findFragmentByTag(str) : null;
        if (findFragmentByTag == null) {
            findFragmentByTag = supportFragmentManager.findFragmentById(R.id.capsule_sleep_guide_container);
        }
        if (findFragmentByTag != null && !fragmentTag.equals(this.mCurrentFragmentTag)) {
            beginTransaction.hide(findFragmentByTag);
        }
        this.mCurrentFragmentTag = fragmentTag;
        Fragment findFragmentByTag2 = supportFragmentManager.findFragmentByTag(fragmentTag);
        if (findFragmentByTag2 == null) {
            if ("shade_setup".equals(fragmentTag)) {
                newInstance = ShadeSetupFragment.newInstance(this.mSleepBedType, this.openFrom);
            } else if ("mattress_setup".equals(fragmentTag)) {
                newInstance = MattressSetupFragment.newInstance(this.mSleepBedType, this.openFrom);
            } else {
                newInstance = MattressStoreFragment.newInstance(this.mEnter, this.mSleepBedType, this.openFrom);
            }
            beginTransaction.add(R.id.capsule_sleep_guide_container, newInstance, fragmentTag);
        } else {
            beginTransaction.show(findFragmentByTag2);
        }
        beginTransaction.commitNowAllowingStateLoss();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ICapsuleSleepGuideInterface
    public void onShadeSetup() {
        if ("mattress_setup".equals(this.mCurrentFragmentTag)) {
            replaceFragment("shade_setup");
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ICapsuleSleepGuideInterface
    public void onMattressSetup() {
        replaceFragment("mattress_setup");
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ICapsuleSleepGuideInterface
    public void onMattressStore() {
        if ("mattress_setup".equals(this.mCurrentFragmentTag)) {
            replaceFragment("mattress_store");
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.ICapsuleSleepGuideInterface
    public void onMattressStoreClose() {
        if ("mattress_store".equals(this.mCurrentFragmentTag)) {
            XActivityUtils.finish(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        SpeechHelper.getInstance().stop();
        uploadBI(false);
    }

    private void uploadBI(boolean enter) {
        int i;
        if (enter) {
            PageEnum pageEnum = PageEnum.SPACE_CAPSULE_PAGE;
            BtnEnum btnEnum = BtnEnum.SPACE_CAPSULE_PAGE_ENTER_USE_GUIDE;
            Object[] objArr = new Object[2];
            objArr[0] = 1;
            objArr[1] = Integer.valueOf(this.mAuto ? 1 : 2);
            StatisticUtils.sendSpaceCapsuleStatistic(pageEnum, btnEnum, objArr);
            return;
        }
        if ("mattress_setup".equals(this.mCurrentFragmentTag)) {
            i = 2;
        } else {
            i = "mattress_store".equals(this.mCurrentFragmentTag) ? 3 : 1;
        }
        PageEnum pageEnum2 = PageEnum.SPACE_CAPSULE_PAGE;
        BtnEnum btnEnum2 = BtnEnum.SPACE_CAPSULE_PAGE_EXIT_USE_GUIDE;
        Object[] objArr2 = new Object[3];
        objArr2[0] = Integer.valueOf(i);
        objArr2[1] = 1;
        objArr2[2] = Integer.valueOf(this.mAuto ? 1 : 2);
        StatisticUtils.sendSpaceCapsuleStatistic(pageEnum2, btnEnum2, objArr2);
    }
}
