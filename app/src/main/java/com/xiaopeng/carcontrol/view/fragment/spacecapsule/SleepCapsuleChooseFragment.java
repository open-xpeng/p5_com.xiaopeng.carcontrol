package com.xiaopeng.carcontrol.view.fragment.spacecapsule;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XRelativeLayout;

/* loaded from: classes2.dex */
public class SleepCapsuleChooseFragment extends SpaceBaseFragment {
    private static final String TAG = "SleepCapsuleChooseFragment";
    private int mChooseBedType = -1;
    private XRelativeLayout mDoubleBedMode;
    private XDialog mExitDialog;
    private XRelativeLayout mSingleBedMode;
    private ISpaceCapsuleInterface mSpaceListener;
    private XButton mStartBtn;

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.activity_sleep_capsule_choose;
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

    public static SleepCapsuleChooseFragment newInstance() {
        Bundle bundle = new Bundle();
        SleepCapsuleChooseFragment sleepCapsuleChooseFragment = new SleepCapsuleChooseFragment();
        sleepCapsuleChooseFragment.setArguments(bundle);
        return sleepCapsuleChooseFragment;
    }

    private SleepCapsuleChooseFragment() {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISpaceCapsuleInterface) {
            this.mSpaceListener = (ISpaceCapsuleInterface) context;
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (supportVui()) {
            initVuiScene();
        }
        initView(view);
    }

    private void initView(View rootView) {
        rootView.findViewById(R.id.close_iv).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SleepCapsuleChooseFragment$z7HC7X5g9lk6ZxgZnzYTp9I7dk8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SleepCapsuleChooseFragment.this.lambda$initView$0$SleepCapsuleChooseFragment(view);
            }
        });
        XButton xButton = (XButton) rootView.findViewById(R.id.capsule_sleep_start);
        this.mStartBtn = xButton;
        xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SleepCapsuleChooseFragment$TYLuqeAk9K7Ri2W0x9yobSXWUmw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SleepCapsuleChooseFragment.this.lambda$initView$1$SleepCapsuleChooseFragment(view);
            }
        });
        XRelativeLayout xRelativeLayout = (XRelativeLayout) rootView.findViewById(R.id.capsule_sleep_double_image);
        this.mDoubleBedMode = xRelativeLayout;
        xRelativeLayout.setSelected(false);
        this.mDoubleBedMode.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SleepCapsuleChooseFragment$nHJ4DC38sMnvMx841pjaVpezgIo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SleepCapsuleChooseFragment.this.lambda$initView$2$SleepCapsuleChooseFragment(view);
            }
        });
        XRelativeLayout xRelativeLayout2 = (XRelativeLayout) rootView.findViewById(R.id.capsule_sleep_single_image);
        this.mSingleBedMode = xRelativeLayout2;
        xRelativeLayout2.setSelected(false);
        this.mSingleBedMode.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SleepCapsuleChooseFragment$ThvYA3Zor71TdtjBJFI4exCgmv0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SleepCapsuleChooseFragment.this.lambda$initView$3$SleepCapsuleChooseFragment(view);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$SleepCapsuleChooseFragment(View v) {
        ISpaceCapsuleInterface iSpaceCapsuleInterface = this.mSpaceListener;
        if (iSpaceCapsuleInterface != null) {
            iSpaceCapsuleInterface.onSpaceCapsuleEnd();
        }
    }

    public /* synthetic */ void lambda$initView$1$SleepCapsuleChooseFragment(View v) {
        LogUtils.d(TAG, " mChooseBedType:" + this.mChooseBedType);
        int i = this.mChooseBedType;
        if (i == -1) {
            NotificationHelper.getInstance().showToast(R.string.capsule_sleep_choose_tips);
            return;
        }
        ISpaceCapsuleInterface iSpaceCapsuleInterface = this.mSpaceListener;
        if (iSpaceCapsuleInterface != null) {
            iSpaceCapsuleInterface.onSpaceSleepSelect(i);
        }
    }

    public /* synthetic */ void lambda$initView$2$SleepCapsuleChooseFragment(View v) {
        chooseBed(2);
    }

    public /* synthetic */ void lambda$initView$3$SleepCapsuleChooseFragment(View v) {
        chooseBed(1);
    }

    private void chooseBed(int bed) {
        this.mChooseBedType = bed;
        this.mSingleBedMode.setSelected(bed == 1);
        this.mDoubleBedMode.setSelected(this.mChooseBedType == 2);
        StatisticUtils.sendSpaceCapsuleStatistic(PageEnum.SPACE_CAPSULE_SLEEP_PAGE, BtnEnum.SPACE_CAPSULE_SLEEP_CHOOSE_BED, Integer.valueOf(this.mChooseBedType));
    }

    private void showExitDialog() {
        if (this.mExitDialog == null) {
            XDialog xDialog = new XDialog(getContext());
            this.mExitDialog = xDialog;
            xDialog.setTitle(R.string.space_capsule_interrupt_dialog_title);
            this.mExitDialog.setMessage(R.string.space_capsule_exit_interrupt_dialog_content);
            this.mExitDialog.setPositiveButton(R.string.space_capsule_interrupt_positive);
            this.mExitDialog.setNegativeButton(R.string.space_capsule_interrupt_negative);
            this.mExitDialog.setPositiveButtonListener(new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.spacecapsule.-$$Lambda$SleepCapsuleChooseFragment$r0eat_AxCsuCChIkXcikVUrMiMI
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SleepCapsuleChooseFragment.this.lambda$showExitDialog$4$SleepCapsuleChooseFragment(xDialog2, i);
                }
            });
            this.mExitDialog.setCloseVisibility(false);
        }
        this.mExitDialog.show();
    }

    public /* synthetic */ void lambda$showExitDialog$4$SleepCapsuleChooseFragment(XDialog xDialog, int i) {
        ISpaceCapsuleInterface iSpaceCapsuleInterface = this.mSpaceListener;
        if (iSpaceCapsuleInterface != null) {
            iSpaceCapsuleInterface.onSpaceCapsuleEnd();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.spacecapsule.SpaceBaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        XDialog xDialog = this.mExitDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mExitDialog = null;
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
