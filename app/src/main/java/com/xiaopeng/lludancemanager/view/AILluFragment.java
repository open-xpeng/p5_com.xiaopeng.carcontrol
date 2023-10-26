package com.xiaopeng.lludancemanager.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.lludancemanager.adapter.ItemDecoration;
import com.xiaopeng.lludancemanager.adapter.LluAIAdapter;
import com.xiaopeng.lludancemanager.bean.LluAiLampBean;
import com.xiaopeng.lludancemanager.bean.LluAiUserManualBean;
import com.xiaopeng.lludancemanager.helper.AnimationHelper;
import com.xiaopeng.lludancemanager.viewmodel.AILluViewModel;
import com.xiaopeng.lludancemanager.widget.LampBeamShowView;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRecyclerView;
import java.util.List;

/* loaded from: classes2.dex */
public class AILluFragment extends Fragment {
    private static final String TAG = "AILluFragment";
    private XButton mEndAiLluBtn;
    private ViewGroup mExperienceViewGroup;
    private LampBeamShowView mLampView;
    private LluAIAdapter mLluAIAdapter;
    private LluAiSettingDialog mLluAiSettingDialog;
    private XImageView mNoteView;
    private View mRootView;
    private XButton mStartAiLluBtn;
    private XRecyclerView mUserManualRecyclerView;
    private AILluViewModel mViewModel;
    private ViewGroup mWelcomeViewGroup;

    public static AILluFragment newInstance() {
        return new AILluFragment();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, " onCreate");
        initViewModel();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.fragment_ai_llu, container, false);
        initView();
        initListener();
        return this.mRootView;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mViewModel.stopAiLlu();
        this.mViewModel.unRegisterAILluListener();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        this.mStartAiLluBtn = (XButton) this.mRootView.findViewById(R.id.btn_start_al_llu);
        this.mEndAiLluBtn = (XButton) this.mRootView.findViewById(R.id.btn_end_al_llu);
        this.mWelcomeViewGroup = (ViewGroup) this.mRootView.findViewById(R.id.welcome_layout);
        this.mExperienceViewGroup = (ViewGroup) this.mRootView.findViewById(R.id.experience_layout);
        this.mLampView = (LampBeamShowView) this.mRootView.findViewById(R.id.iv_lamp);
        this.mNoteView = (XImageView) this.mRootView.findViewById(R.id.iv_note);
        XRecyclerView xRecyclerView = (XRecyclerView) this.mRootView.findViewById(R.id.userManualRecyclerView);
        this.mUserManualRecyclerView = xRecyclerView;
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mUserManualRecyclerView.addItemDecoration(new ItemDecoration(16));
        LluAIAdapter lluAIAdapter = new LluAIAdapter();
        this.mLluAIAdapter = lluAIAdapter;
        this.mUserManualRecyclerView.setAdapter(lluAIAdapter);
    }

    private void initListener() {
        this.mViewModel.registerAILluListener();
        this.mStartAiLluBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$AILluFragment$Cj8h0rrR7L01jQGvla7N8G8pXCs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AILluFragment.this.lambda$initListener$0$AILluFragment(view);
            }
        });
        this.mEndAiLluBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$AILluFragment$6EOCZyy3a_lbBpmkDCMZW6kgsyY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AILluFragment.this.lambda$initListener$1$AILluFragment(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$AILluFragment(View v) {
        updateExperienceView(true);
    }

    public /* synthetic */ void lambda$initListener$1$AILluFragment(View v) {
        this.mViewModel.stopAiLlu();
    }

    private void initViewModel() {
        AILluViewModel aILluViewModel = new AILluViewModel();
        this.mViewModel = aILluViewModel;
        setLiveDataObserver(aILluViewModel.getExperienceState(), new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$AILluFragment$7axqC_R-HWNuoL3X_yi0X897-r8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AILluFragment.this.updateExperienceView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getLampMusicState(), new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$AILluFragment$CWTLPN7efrXzg0iaq-GEB2860PU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AILluFragment.this.updateLampView((LluAiLampBean) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getLluAiLampMediatorLiveData(), new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$AILluFragment$yDsLgWKAyxHCNupMMdRyNYhXu4M
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AILluFragment.this.updateLluAiLampView((List) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getLluAiUserManualLiveData(), new Observer() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$AILluFragment$jY5IlypLiAkHIVe8cPumWFY1uX8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AILluFragment.this.updateLluUserManual((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLluUserManual(List<LluAiUserManualBean> lluAiUserManualBeans) {
        LluAIAdapter lluAIAdapter = this.mLluAIAdapter;
        if (lluAIAdapter != null) {
            lluAIAdapter.setList(lluAiUserManualBeans);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLluAiLampView(List<LluAiLampBean> aiLampBeanList) {
        LampBeamShowView lampBeamShowView = this.mLampView;
        if (lampBeamShowView == null || aiLampBeanList == null) {
            return;
        }
        lampBeamShowView.setLluAiLampBeans(aiLampBeanList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLampView(LluAiLampBean lluAiLampBean) {
        Log.d(TAG, "updateLampView:" + lluAiLampBean.toString());
        LampBeamShowView lampBeamShowView = this.mLampView;
        if (lampBeamShowView != null) {
            lampBeamShowView.updateLampBeam(lluAiLampBean);
            this.mNoteView.setImageResource(lluAiLampBean.getNoteTextResId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateExperienceView(boolean isExperience) {
        Log.d(TAG, "updateExperienceView:" + isExperience);
        if (isExperience) {
            showSettingDialog();
            return;
        }
        SpeechHelper.getInstance().stop();
        AnimationHelper.startViewAlphaAnimation(this.mExperienceViewGroup, 0.0f, 1000L, new AnimationHelper.ViewAnimationListener() { // from class: com.xiaopeng.lludancemanager.view.AILluFragment.1
            @Override // com.xiaopeng.lludancemanager.helper.AnimationHelper.ViewAnimationListener
            public void onAnimationEnd() {
                AILluFragment.this.mExperienceViewGroup.setVisibility(8);
            }
        });
        this.mWelcomeViewGroup.setVisibility(0);
        this.mWelcomeViewGroup.setAlpha(0.0f);
        AnimationHelper.startViewAlphaAnimation(this.mWelcomeViewGroup, 1.0f, 1000L, null);
    }

    private void showSettingDialog() {
        if (this.mLluAiSettingDialog == null) {
            this.mLluAiSettingDialog = new LluAiSettingDialog(getActivity());
        }
        this.mLluAiSettingDialog.setPositiveButton(getString(R.string.llu_dance_double_confirm_dialog_positive), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$AILluFragment$av7L7lrcKvFYuKyzh0ZDk6U_I4s
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                AILluFragment.this.lambda$showSettingDialog$2$AILluFragment(xDialog, i);
            }
        });
        this.mLluAiSettingDialog.setNegativeButton(getString(R.string.llu_dance_double_confirm_dialog_negative), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.lludancemanager.view.-$$Lambda$AILluFragment$IHhBIyrpoOmSl1yCd77lTiJQXj4
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                AILluFragment.this.lambda$showSettingDialog$3$AILluFragment(xDialog, i);
            }
        });
        this.mLluAiSettingDialog.show();
    }

    public /* synthetic */ void lambda$showSettingDialog$2$AILluFragment(XDialog xDialog, int i) {
        showView();
    }

    public /* synthetic */ void lambda$showSettingDialog$3$AILluFragment(XDialog xDialog, int i) {
        this.mLluAiSettingDialog.dismiss();
    }

    private void showView() {
        SpeechHelper.getInstance().speak(getString(R.string.llu_ai_lamp_signal_hint));
        this.mExperienceViewGroup.setVisibility(0);
        this.mExperienceViewGroup.setAlpha(0.0f);
        AnimationHelper.startViewAlphaAnimation(this.mExperienceViewGroup, 1.0f, 1000L, null);
        AnimationHelper.startViewAlphaAnimation(this.mWelcomeViewGroup, 0.0f, 1000L, new AnimationHelper.ViewAnimationListener() { // from class: com.xiaopeng.lludancemanager.view.AILluFragment.2
            @Override // com.xiaopeng.lludancemanager.helper.AnimationHelper.ViewAnimationListener
            public void onAnimationEnd() {
                AILluFragment.this.mWelcomeViewGroup.setVisibility(8);
            }
        });
        this.mViewModel.startAiLlu(1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        liveData.observe(this, observer);
    }
}
