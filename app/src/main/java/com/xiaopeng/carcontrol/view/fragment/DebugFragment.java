package com.xiaopeng.carcontrol.view.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.view.adapter.DebugAvasAdapter;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.widget.XEditText;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.slider.XSlider;

/* loaded from: classes2.dex */
public class DebugFragment extends BaseFragment implements DebugAvasAdapter.OnItemClickListener {
    private AvasViewModel mAvasViewModel;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onViewCreated$0(View v) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onViewCreated$1(View v) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onViewCreated$2(View v) {
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_debug;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViewModels() {
        this.mAvasViewModel = (AvasViewModel) ViewModelManager.getInstance().getViewModelImpl(IAvasViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_rest_mode).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$qogQal81d4royVFNbYm1J3KsBXY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DebugFragment.lambda$onViewCreated$0(view2);
            }
        });
        view.findViewById(R.id.btn_film_mode).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$ICxAqJbDfxG32fzRwDh6biSOF3o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DebugFragment.lambda$onViewCreated$1(view2);
            }
        });
        XSwitch xSwitch = (XSwitch) view.findViewById(R.id.xs_llu_unlock);
        XSwitch xSwitch2 = (XSwitch) view.findViewById(R.id.xs_llu_lock);
        XSwitch xSwitch3 = (XSwitch) view.findViewById(R.id.xs_llu_unlock_with_power);
        XSwitch xSwitch4 = (XSwitch) view.findViewById(R.id.xs_llu_lock_with_power);
        XSwitch xSwitch5 = (XSwitch) view.findViewById(R.id.xs_llu_normal_charge);
        XSwitch xSwitch6 = (XSwitch) view.findViewById(R.id.xs_llu_rapid_charge);
        XEditText xEditText = (XEditText) view.findViewById(R.id.et_speed_limit);
        view.findViewById(R.id.btn_speed_limit_ok).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$ehOCiuALCYG4UV-y33XIqHNo6q4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DebugFragment.lambda$onViewCreated$2(view2);
            }
        });
        boolean isAvasExternalSwEnabled = this.mAvasViewModel.isAvasExternalSwEnabled();
        final XSlider xSlider = (XSlider) view.findViewById(R.id.xs_simulation_volume);
        xSlider.setStartIndex(1);
        xSlider.setEndIndex(100);
        xSlider.setInitIndex(this.mAvasViewModel.getAvasExternalVolume());
        xSlider.setEnabled(isAvasExternalSwEnabled);
        xSlider.setSliderProgressListener(new XSlider.SliderProgressListener() { // from class: com.xiaopeng.carcontrol.view.fragment.DebugFragment.1
            @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
            public void onProgressChanged(XSlider xSlider2, float v, String s) {
            }

            @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
            public void onStartTrackingTouch(XSlider xSlider2) {
            }

            @Override // com.xiaopeng.xui.widget.slider.XSlider.SliderProgressListener
            public void onStopTrackingTouch(XSlider xSlider2) {
                DebugFragment.this.mAvasViewModel.setAvasExternalVolume((int) xSlider2.getIndicatorValue());
            }
        });
        setLiveDataObserver(this.mAvasViewModel.getAvasExternalVolumeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$9s3gX-SsDD8f_bVvGJ1YVGo0m_k
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DebugFragment.lambda$onViewCreated$3(XSlider.this, (Integer) obj);
            }
        });
        final XSwitch xSwitch7 = (XSwitch) view.findViewById(R.id.xs_avas_unlock);
        xSwitch7.setEnabled(isAvasExternalSwEnabled);
        xSwitch7.setChecked(this.mAvasViewModel.isAvasWakeWaitEnable());
        xSwitch7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$8RScvIBaRKx2hzM00T2-tpACpco
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                DebugFragment.this.lambda$onViewCreated$4$DebugFragment(compoundButton, z);
            }
        });
        setLiveDataObserver(this.mAvasViewModel.getAvasWakeWaitSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$VR_7KXoon_UK7WBN6yU1Qe1gmwQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DebugFragment.lambda$onViewCreated$5(XSwitch.this, (Boolean) obj);
            }
        });
        final XSwitch xSwitch8 = (XSwitch) view.findViewById(R.id.xs_avas_lock);
        xSwitch8.setEnabled(isAvasExternalSwEnabled);
        xSwitch8.setChecked(this.mAvasViewModel.isAvasSleepEnable());
        xSwitch8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$YDhEfmnUejNpXosMoLkJnzeAp5A
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                DebugFragment.this.lambda$onViewCreated$6$DebugFragment(compoundButton, z);
            }
        });
        setLiveDataObserver(this.mAvasViewModel.getAvasSleepSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$OgXjhLWjcz004QQ3OjU9ZddUT_A
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DebugFragment.lambda$onViewCreated$7(XSwitch.this, (Boolean) obj);
            }
        });
        ((XSwitch) view.findViewById(R.id.xs_avas_insert_charge)).setEnabled(isAvasExternalSwEnabled);
        final XSwitch xSwitch9 = (XSwitch) view.findViewById(R.id.xs_avas_evulsion_charge);
        xSwitch9.setEnabled(isAvasExternalSwEnabled);
        xSwitch9.setChecked(this.mAvasViewModel.isAvasDisconnectChargingEnable());
        xSwitch9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$LSScfzQk4zMXqlpwsOXy69Vau7o
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                DebugFragment.this.lambda$onViewCreated$8$DebugFragment(compoundButton, z);
            }
        });
        setLiveDataObserver(this.mAvasViewModel.getAvasDisconnectChargingSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$G2pNud0YpP5CP2Bk6eYo5NiQaYs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DebugFragment.lambda$onViewCreated$9(XSwitch.this, (Boolean) obj);
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.avas_effect_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaopeng.carcontrol.view.fragment.DebugFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view2, RecyclerView parent, RecyclerView.State state) {
                outRect.set(10, 10, 10, 10);
            }
        });
        recyclerView.setAdapter(new DebugAvasAdapter(getContext(), this));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onViewCreated$3(final XSlider otherVolumeSb, Integer integer) {
        if (integer != null) {
            otherVolumeSb.setCurrentIndex(integer.intValue());
        }
    }

    public /* synthetic */ void lambda$onViewCreated$4$DebugFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mAvasViewModel.setAvasWakeWaitSwitch(isChecked);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onViewCreated$5(final XSwitch avasUnlock, Boolean enable) {
        if (enable != null) {
            avasUnlock.setChecked(enable.booleanValue());
        }
    }

    public /* synthetic */ void lambda$onViewCreated$6$DebugFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mAvasViewModel.setAvasSleepSwitch(isChecked);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onViewCreated$7(final XSwitch avasLock, Boolean enable) {
        if (enable != null) {
            avasLock.setChecked(enable.booleanValue());
        }
    }

    public /* synthetic */ void lambda$onViewCreated$8$DebugFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mAvasViewModel.setAvasDisconnectChargingSwitch(isChecked);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onViewCreated$9(final XSwitch avasEvulsionCharge, Boolean enable) {
        if (enable != null) {
            avasEvulsionCharge.setChecked(enable.booleanValue());
        }
    }

    @Override // com.xiaopeng.carcontrol.view.adapter.DebugAvasAdapter.OnItemClickListener
    public void onItemClick(final int pos) {
        Context context = getContext();
        if (context != null) {
            final XDialog xDialog = new XDialog(context);
            View inflate = LayoutInflater.from(context).inflate(R.layout.sound_play_layout, (ViewGroup) null);
            inflate.findViewById(R.id.item_1).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$tCWajokTQiSHyWNQCXqu9TNQZMc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DebugFragment.this.lambda$onItemClick$10$DebugFragment(pos, xDialog, view);
                }
            });
            inflate.findViewById(R.id.item_2).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$EPeuHYZ9hwUGtLDU444H7z_VIVs
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DebugFragment.this.lambda$onItemClick$11$DebugFragment(pos, xDialog, view);
                }
            });
            inflate.findViewById(R.id.item_3).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$62Y8QL1yBh0lF6udnuXO3UQQSK8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DebugFragment.this.lambda$onItemClick$12$DebugFragment(pos, xDialog, view);
                }
            });
            inflate.findViewById(R.id.item_4).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$YlqJJw7TjBWGc5N9BEjQC_jDYpg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DebugFragment.this.lambda$onItemClick$13$DebugFragment(pos, xDialog, view);
                }
            });
            inflate.findViewById(R.id.item_5).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$DebugFragment$jgz5od04I4dBqyVVirLFWDlcDL8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DebugFragment.this.lambda$onItemClick$14$DebugFragment(pos, xDialog, view);
                }
            });
            xDialog.setTitle(R.string.sound_play_times).setCustomView(inflate);
            xDialog.show();
        }
    }

    public /* synthetic */ void lambda$onItemClick$10$DebugFragment(final int pos, final XDialog dialog, View v) {
        this.mAvasViewModel.setAvasExternalSound(pos + 5);
        this.mAvasViewModel.setExternalSoundModeCmd(1);
        dialog.dismiss();
    }

    public /* synthetic */ void lambda$onItemClick$11$DebugFragment(final int pos, final XDialog dialog, View v) {
        this.mAvasViewModel.setAvasExternalSound(pos + 5);
        this.mAvasViewModel.setExternalSoundModeCmd(2);
        dialog.dismiss();
    }

    public /* synthetic */ void lambda$onItemClick$12$DebugFragment(final int pos, final XDialog dialog, View v) {
        this.mAvasViewModel.setAvasExternalSound(pos + 5);
        this.mAvasViewModel.setExternalSoundModeCmd(3);
        dialog.dismiss();
    }

    public /* synthetic */ void lambda$onItemClick$13$DebugFragment(final int pos, final XDialog dialog, View v) {
        this.mAvasViewModel.setAvasExternalSound(pos + 5);
        this.mAvasViewModel.setExternalSoundModeCmd(4);
        dialog.dismiss();
    }

    public /* synthetic */ void lambda$onItemClick$14$DebugFragment(final int pos, final XDialog dialog, View v) {
        this.mAvasViewModel.setAvasExternalSound(pos + 5);
        this.mAvasViewModel.setExternalSoundModeCmd(5);
        dialog.dismiss();
    }
}
