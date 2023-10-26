package com.xiaopeng.carcontrol.view.fragment.spacecapsule;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XCheckBox;
import com.xiaopeng.xui.widget.XImageView;

/* loaded from: classes2.dex */
public class MattressStoreFragment extends SpaceBaseFragment {
    private static final String TAG = "MattressStoreFragment";
    private boolean mEnter;
    private final Handler mHandler = new Handler();
    private int mSleepBedType;
    private ICapsuleSleepGuideInterface mSleepGuideListener;
    private XImageView mStoreImg;

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_sleep_mattress_store;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    protected void initViewModel() {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    protected void initViewModelObserver() {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    public void setClickSelected(int position) {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment
    protected boolean supportVui() {
        return false;
    }

    public static MattressStoreFragment newInstance(boolean enter, int bedType, String openFrom) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(GlobalConstant.EXTRA.KEY_SPACE_SLEEP_GUIDE_FLAT, enter);
        bundle.putInt(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, bedType);
        bundle.putString(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM, openFrom);
        MattressStoreFragment mattressStoreFragment = new MattressStoreFragment();
        mattressStoreFragment.setArguments(bundle);
        return mattressStoreFragment;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ICapsuleSleepGuideInterface) {
            this.mSleepGuideListener = (ICapsuleSleepGuideInterface) context;
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        Bundle arguments = getArguments();
        this.mEnter = arguments.getBoolean(GlobalConstant.EXTRA.KEY_SPACE_SLEEP_GUIDE_FLAT, false);
        this.mSleepBedType = arguments.getInt(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, 2);
        this.mStoreImg = (XImageView) view.findViewById(R.id.capsule_mattress_store_img);
        String string = !TextUtils.isEmpty(arguments.getString(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM)) ? arguments.getString(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM) : "";
        if (this.mSleepBedType == 2) {
            this.mStoreImg.setImageResource(R.drawable.capsule_sleep_mattress_store_img);
        } else {
            this.mStoreImg.setImageResource(R.drawable.capsule_sleep_mattress_store_img_single);
        }
        XCheckBox xCheckBox = (XCheckBox) view.findViewById(R.id.capsule_sleep_not_show_next_time);
        XButton xButton = (XButton) view.findViewById(R.id.capsule_sleep_guide_mattress_store_pre_btn);
        XButton xButton2 = (XButton) view.findViewById(R.id.capsule_sleep_guide_mattress_store_next_btn);
        XButton xButton3 = (XButton) view.findViewById(R.id.capsule_sleep_guide_mattress_store_complete_btn);
        if (!string.equals("guideBtn")) {
            xCheckBox.setVisibility(0);
        } else {
            xCheckBox.setVisibility(8);
        }
        if (this.mEnter) {
            xCheckBox.setText(R.string.capsule_sleep_use_guide_not_show_next_enter);
            xCheckBox.setChecked(SharedPreferenceUtil.isSpaceCapsuleUGEnterShowCheckBoxSelected());
            xCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressStoreFragment$2wNTLAoohxER25wonAub-eUhIPA
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SharedPreferenceUtil.setSpaceCapsuleUGEnterShowCheckBox(z);
                }
            });
            xButton.setVisibility(0);
            xButton2.setVisibility(0);
            xButton3.setVisibility(8);
        } else {
            xCheckBox.setText(R.string.capsule_sleep_use_guide_not_show_next_exit);
            xCheckBox.setChecked(SharedPreferenceUtil.isSpaceCapsuleUGExitShowCheckBoxSelected());
            xCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressStoreFragment$kEM3_k1sYw6T_RdT8nK8twQbY8E
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SharedPreferenceUtil.setSpaceCapsuleUGExitShowCheckBox(z);
                }
            });
            xButton3.setVisibility(0);
            xButton.setVisibility(8);
            xButton2.setVisibility(8);
        }
        xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressStoreFragment$Oa1Y4n2dsinAbCT7acKzlgDYlCo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MattressStoreFragment.this.lambda$init$2$MattressStoreFragment(view2);
            }
        });
        xButton2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressStoreFragment$IOW0dDuDS3jJoas8AWyL2uHUWs4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MattressStoreFragment.this.lambda$init$3$MattressStoreFragment(view2);
            }
        });
        xButton3.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressStoreFragment$Oi8Q6SHppVPpb7yXB00y55IVaaM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MattressStoreFragment.this.lambda$init$4$MattressStoreFragment(view2);
            }
        });
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressStoreFragment$OC2Sd_VkFLns7V-9mBRrd2ms_Gk
            @Override // java.lang.Runnable
            public final void run() {
                MattressStoreFragment.this.lambda$init$5$MattressStoreFragment();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$init$2$MattressStoreFragment(View v) {
        ICapsuleSleepGuideInterface iCapsuleSleepGuideInterface = this.mSleepGuideListener;
        if (iCapsuleSleepGuideInterface != null) {
            iCapsuleSleepGuideInterface.onMattressSetup();
        }
    }

    public /* synthetic */ void lambda$init$3$MattressStoreFragment(View v) {
        ICapsuleSleepGuideInterface iCapsuleSleepGuideInterface = this.mSleepGuideListener;
        if (iCapsuleSleepGuideInterface != null) {
            iCapsuleSleepGuideInterface.onMattressStoreClose();
        }
    }

    public /* synthetic */ void lambda$init$4$MattressStoreFragment(View v) {
        if (this.mSleepGuideListener != null) {
            LogUtils.i(TAG, " mEnter is " + this.mEnter);
            if (!this.mEnter) {
                getActivity().setResult(-1);
            }
            this.mSleepGuideListener.onMattressStoreClose();
        }
    }

    public /* synthetic */ void lambda$init$5$MattressStoreFragment() {
        SpeechHelper.getInstance().speak(ResUtils.getString(this.mEnter ? R.string.capsule_sleep_guide_mattress_store_enter_tts : R.string.capsule_sleep_guide_mattress_store_exit_tts));
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            SpeechHelper.getInstance().stop();
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        SpeechHelper.getInstance().stop();
        this.mHandler.removeCallbacksAndMessages(null);
    }
}
