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
public class MattressSetupFragment extends SpaceBaseFragment {
    private XImageView mSetupImg;
    private ICapsuleSleepGuideInterface mSleepGuideListener;
    private Handler mHandler = new Handler();
    private SpeechHelper mSpeechHelper = SpeechHelper.getInstance();
    private int mSleepBedType = -1;

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_sleep_mattress_setup;
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

    public static MattressSetupFragment newInstance(int bedType, String openFrom) {
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, bedType);
        bundle.putString(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM, openFrom);
        MattressSetupFragment mattressSetupFragment = new MattressSetupFragment();
        mattressSetupFragment.setArguments(bundle);
        return mattressSetupFragment;
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
        this.mSetupImg = (XImageView) view.findViewById(R.id.capsule_sleep_guide_mattress_setup_img);
        String string = !TextUtils.isEmpty(arguments.getString(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM)) ? arguments.getString(GlobalConstant.EXTRA.KEY_USER_GUIDE_OPEN_FROM) : "";
        if (this.mSleepBedType == 2) {
            this.mSetupImg.setImageResource(R.drawable.capsule_sleep_mattress_setup_img);
        } else {
            this.mSetupImg.setImageResource(R.drawable.capsule_sleep_mattress_setup_img_single);
        }
        XCheckBox xCheckBox = (XCheckBox) view.findViewById(R.id.capsule_sleep_not_show_next_time);
        if (!string.equals("guideBtn")) {
            xCheckBox.setVisibility(0);
            xCheckBox.setChecked(SharedPreferenceUtil.isSpaceCapsuleUGEnterShowCheckBoxSelected());
            xCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressSetupFragment$CQ8_hhtwKxNn5SYcNu8EfUmVmhE
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SharedPreferenceUtil.setSpaceCapsuleUGEnterShowCheckBox(z);
                }
            });
        } else {
            xCheckBox.setVisibility(8);
        }
        view.findViewById(R.id.capsule_sleep_guide_mattress_pre_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressSetupFragment$d6ufcrsXVke9bTFOpGpNcHpfGig
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MattressSetupFragment.this.lambda$init$1$MattressSetupFragment(view2);
            }
        });
        view.findViewById(R.id.capsule_sleep_guide_mattress_next_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressSetupFragment$MA8UxqPwZayD4nOkvemmgzULx6k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MattressSetupFragment.this.lambda$init$2$MattressSetupFragment(view2);
            }
        });
        this.mSpeechHelper.setTtsPlayListener(new SpeechHelper.TtsPlayListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressSetupFragment$VputPGPjnQQHE_VA5VsSY1vqARo
            @Override // com.xiaopeng.carcontrol.helper.SpeechHelper.TtsPlayListener
            public final void onTtsPlayEnd(String str) {
                MattressSetupFragment.this.lambda$init$3$MattressSetupFragment(str);
            }
        });
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressSetupFragment$zVdxG86KPB3VRTaSzdU-eIP4U8M
            @Override // java.lang.Runnable
            public final void run() {
                MattressSetupFragment.this.lambda$init$4$MattressSetupFragment();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$init$1$MattressSetupFragment(View v) {
        ICapsuleSleepGuideInterface iCapsuleSleepGuideInterface = this.mSleepGuideListener;
        if (iCapsuleSleepGuideInterface != null) {
            iCapsuleSleepGuideInterface.onShadeSetup();
        }
    }

    public /* synthetic */ void lambda$init$2$MattressSetupFragment(View v) {
        ICapsuleSleepGuideInterface iCapsuleSleepGuideInterface = this.mSleepGuideListener;
        if (iCapsuleSleepGuideInterface != null) {
            iCapsuleSleepGuideInterface.onMattressStore();
        }
    }

    public /* synthetic */ void lambda$init$4$MattressSetupFragment() {
        this.mSpeechHelper.speak(ResUtils.getString(R.string.capsule_sleep_guide_mattress_setup_tts_1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: processMattressSetupTts */
    public void lambda$init$3$MattressSetupFragment(String tts) {
        if (ResUtils.getString(R.string.capsule_sleep_guide_mattress_setup_tts_1).equals(tts)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressSetupFragment$zFpL4pTV2lJtKdF2riFNzJNiK5Q
                @Override // java.lang.Runnable
                public final void run() {
                    MattressSetupFragment.this.lambda$processMattressSetupTts$5$MattressSetupFragment();
                }
            }, 1000L);
        } else if (ResUtils.getString(R.string.capsule_sleep_guide_mattress_setup_tts_2).equals(tts)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressSetupFragment$l6aIbTyCNj3kAwjxi9rt7mJ9iFI
                @Override // java.lang.Runnable
                public final void run() {
                    MattressSetupFragment.this.lambda$processMattressSetupTts$6$MattressSetupFragment();
                }
            }, 1000L);
        } else if (ResUtils.getString(R.string.capsule_sleep_guide_mattress_setup_tts_3).equals(tts)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$MattressSetupFragment$z862N-njqw4xeWZn-AfDvMGVhc4
                @Override // java.lang.Runnable
                public final void run() {
                    MattressSetupFragment.this.lambda$processMattressSetupTts$7$MattressSetupFragment();
                }
            }, 1000L);
        }
    }

    public /* synthetic */ void lambda$processMattressSetupTts$5$MattressSetupFragment() {
        this.mSpeechHelper.speak(ResUtils.getString(R.string.capsule_sleep_guide_mattress_setup_tts_2));
    }

    public /* synthetic */ void lambda$processMattressSetupTts$6$MattressSetupFragment() {
        this.mSpeechHelper.speak(ResUtils.getString(R.string.capsule_sleep_guide_mattress_setup_tts_3));
    }

    public /* synthetic */ void lambda$processMattressSetupTts$7$MattressSetupFragment() {
        this.mSpeechHelper.speak(ResUtils.getString(R.string.capsule_sleep_guide_mattress_setup_tts_4));
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
