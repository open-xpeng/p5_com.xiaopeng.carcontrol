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
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.xui.widget.XCheckBox;
import com.xiaopeng.xui.widget.XImageView;

/* loaded from: classes2.dex */
public class ShadeSetupFragment extends SpaceBaseFragment {
    private XImageView mShadeSetupImg;
    private ICapsuleSleepGuideInterface mSleepGuideListener;
    private final Handler mHandler = new Handler();
    private final SpeechHelper mSpeechHelper = SpeechHelper.getInstance();
    private int mSleepBedType = -1;

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_sleep_shade_setup;
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

    public static ShadeSetupFragment newInstance(int bedType, String openFrom) {
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, bedType);
        bundle.putString(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM, openFrom);
        ShadeSetupFragment shadeSetupFragment = new ShadeSetupFragment();
        shadeSetupFragment.setArguments(bundle);
        return shadeSetupFragment;
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
        this.mSleepBedType = arguments.getInt(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, 2);
        String string = !TextUtils.isEmpty(arguments.getString(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM)) ? arguments.getString(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM) : "";
        XImageView xImageView = (XImageView) view.findViewById(R.id.capsule_sleep_guide_shade_setup_img);
        this.mShadeSetupImg = xImageView;
        if (this.mSleepBedType == 2) {
            xImageView.setImageResource(R.drawable.capsule_sleep_shade_setup_img);
        } else {
            xImageView.setImageResource(R.drawable.capsule_sleep_shade_setup_img_single);
        }
        XCheckBox xCheckBox = (XCheckBox) view.findViewById(R.id.capsule_sleep_not_show_next_time);
        if (!string.equals("guideBtn")) {
            xCheckBox.setVisibility(0);
            xCheckBox.setChecked(SharedPreferenceUtil.isSpaceCapsuleUGEnterShowCheckBoxSelected());
            xCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$ShadeSetupFragment$RNLCSK2gmNzdxZjtLNOKHL-nE5g
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SharedPreferenceUtil.setSpaceCapsuleUGEnterShowCheckBox(z);
                }
            });
        } else {
            xCheckBox.setVisibility(8);
        }
        view.findViewById(R.id.capsule_sleep_shade_next_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$ShadeSetupFragment$0I4aTnyNEpD8KIOkumMRj7PzwTw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ShadeSetupFragment.this.lambda$init$1$ShadeSetupFragment(view2);
            }
        });
        this.mSpeechHelper.setTtsPlayListener(new SpeechHelper.TtsPlayListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$ShadeSetupFragment$Rpb5oqs0NeDMhOAxA-xT0hliKGM
            @Override // com.xiaopeng.carcontrol.helper.SpeechHelper.TtsPlayListener
            public final void onTtsPlayEnd(String str) {
                ShadeSetupFragment.this.lambda$init$3$ShadeSetupFragment(str);
            }
        });
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$ShadeSetupFragment$lYAE6UDrZ8Tl_4FFAaY3Su4XGR0
            @Override // java.lang.Runnable
            public final void run() {
                ShadeSetupFragment.this.lambda$init$4$ShadeSetupFragment();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$init$1$ShadeSetupFragment(View v) {
        ICapsuleSleepGuideInterface iCapsuleSleepGuideInterface = this.mSleepGuideListener;
        if (iCapsuleSleepGuideInterface != null) {
            iCapsuleSleepGuideInterface.onMattressSetup();
        }
    }

    public /* synthetic */ void lambda$init$3$ShadeSetupFragment(String tts) {
        if (ResUtils.getString(R.string.capsule_sleep_guide_shade_tts_1).equals(tts)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$ShadeSetupFragment$AZfACIYYBAt6bWzc-5wBRxoGA6c
                @Override // java.lang.Runnable
                public final void run() {
                    ShadeSetupFragment.this.lambda$null$2$ShadeSetupFragment();
                }
            }, 1000L);
        }
    }

    public /* synthetic */ void lambda$null$2$ShadeSetupFragment() {
        this.mSpeechHelper.speak(ResUtils.getString(R.string.capsule_sleep_guide_shade_tts_2));
    }

    public /* synthetic */ void lambda$init$4$ShadeSetupFragment() {
        this.mSpeechHelper.speak(ResUtils.getString(R.string.capsule_sleep_guide_shade_tts_1));
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            this.mSpeechHelper.stop();
            this.mSpeechHelper.setTtsPlayListener(null);
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mSpeechHelper.stop();
        this.mSpeechHelper.setTtsPlayListener(null);
        this.mHandler.removeCallbacksAndMessages(null);
    }
}
