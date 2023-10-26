package com.xiaopeng.carcontrol.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.speech.SeatAirVuiEventActor;
import com.xiaopeng.carcontrol.view.speech.SeatHotVuiEventActor;
import com.xiaopeng.carcontrol.view.speech.StatefulButtonConstructorFactory;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.SeatHeatLevel;
import com.xiaopeng.carcontrol.viewmodel.hvac.SeatVentLevel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.actor.VuiEventHandler;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class SeatVentSettingDialog extends XDialog implements LifecycleOwner, View.OnClickListener, IVuiElementListener {
    private static final long BTN_CLICK_PROTECT_DELAY = 500;
    private static final String SCENE_HVAC_SMART_DIALOG = "hvac_seatDialog";
    private static String TAG = "SeatVentSettingDialog";
    private Runnable mAllBtnClickProtectRun;
    private XButton mBtnAllOff;
    private Runnable mFrHeatVentClickProtectRun;
    private XImageButton mImgHeatFr;
    private XImageView mImgHeatFrBg;
    private XImageButton mImgHeatRl;
    private XImageView mImgHeatRlBg;
    private XImageButton mImgHeatRr;
    private XImageView mImgHeatRrBg;
    private XImageButton mImgMainHeat;
    private XImageView mImgMainHeatBg;
    private XImageButton mImgMainVent;
    private XImageButton mImgVentFr;
    private boolean mIsHeatSupport;
    private boolean mIsVentSupport;
    private final LifecycleRegistry mLifecycleRegistry;
    private ISeatHeatVentChangeListener mListener;
    private Runnable mMainVentHeatClickProtectRun;
    private Runnable mRlHeatClickProtectRun;
    private Runnable mRrHeatClickProtectRun;
    private XTextView mTvTitle;
    private HvacViewModel mViewModel;

    /* loaded from: classes2.dex */
    public interface ISeatHeatVentChangeListener {
        void onAllClose();

        void onHeatChanged(int type, SeatHeatLevel level);

        void onVentChanged(int type, SeatVentLevel level);
    }

    public /* synthetic */ void lambda$new$0$SeatVentSettingDialog() {
        setDrvHeatVentClickable(true);
    }

    public /* synthetic */ void lambda$new$1$SeatVentSettingDialog() {
        setPsnHeatVentClickable(true);
    }

    public /* synthetic */ void lambda$new$2$SeatVentSettingDialog() {
        setAllClickable(true);
    }

    public SeatVentSettingDialog(Context context, int resId, HvacViewModel viewModel) {
        this(context, resId, R.style.XDialogSeatSetting, viewModel);
    }

    public SeatVentSettingDialog(Context context, int resId, int style, HvacViewModel viewModel) {
        super(context, style);
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.mLifecycleRegistry = lifecycleRegistry;
        this.mIsVentSupport = false;
        this.mIsHeatSupport = false;
        this.mMainVentHeatClickProtectRun = new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$nQ5WU_xLKmTFvsa72UBywyU9pNA
            @Override // java.lang.Runnable
            public final void run() {
                SeatVentSettingDialog.this.lambda$new$0$SeatVentSettingDialog();
            }
        };
        this.mFrHeatVentClickProtectRun = new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$CCrGpmvdKloAaypTJfXwb3-VrYk
            @Override // java.lang.Runnable
            public final void run() {
                SeatVentSettingDialog.this.lambda$new$1$SeatVentSettingDialog();
            }
        };
        this.mRlHeatClickProtectRun = new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.SeatVentSettingDialog.1
            @Override // java.lang.Runnable
            public void run() {
                if (SeatVentSettingDialog.this.mImgHeatRl != null) {
                    SeatVentSettingDialog.this.mImgHeatRl.setClickable(true);
                }
                if (SeatVentSettingDialog.this.mImgHeatRlBg != null) {
                    SeatVentSettingDialog.this.mImgHeatRlBg.setClickable(true);
                }
            }
        };
        this.mRrHeatClickProtectRun = new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.SeatVentSettingDialog.2
            @Override // java.lang.Runnable
            public void run() {
                if (SeatVentSettingDialog.this.mImgHeatRr != null) {
                    SeatVentSettingDialog.this.mImgHeatRr.setClickable(true);
                }
                if (SeatVentSettingDialog.this.mImgHeatRrBg != null) {
                    SeatVentSettingDialog.this.mImgHeatRrBg.setClickable(true);
                }
            }
        };
        this.mAllBtnClickProtectRun = new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$c6m0d0KK0-lFlcDXNKnyaVJPiLU
            @Override // java.lang.Runnable
            public final void run() {
                SeatVentSettingDialog.this.lambda$new$2$SeatVentSettingDialog();
            }
        };
        this.mViewModel = viewModel;
        this.mIsVentSupport = viewModel.isSupportDrvSeatVent();
        this.mIsHeatSupport = viewModel.isSupportDrvSeatHeat();
        setSystemDialog(2008);
        final View inflate = LayoutInflater.from(context).inflate(resId, getContentView(), false);
        setCustomView(inflate, false);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setElevation(0.0f);
        }
        initViews(inflate);
        setTitleVisibility(false);
        setPositiveButton("");
        setNegativeButton("");
        initData();
        lifecycleRegistry.addObserver(new LifecycleEventObserver() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$DrtOuAQzX31IxCp2SpIyf0HG8wk
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                SeatVentSettingDialog.lambda$new$3(inflate, lifecycleOwner, event);
            }
        });
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$new$3(final View view, LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_STOP) {
            view.cancelPendingInputEvents();
        }
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    private void initData() {
        if (this.mViewModel.isShowSeatHeatVentEntry()) {
            if (CarBaseConfig.getInstance().isSupportDrvSeatVent()) {
                setSeatVentLevel(0, this.mViewModel.getHvacSeatVentLevel());
                this.mViewModel.getHvacSeatVentData().observe(this, new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$9icKz_ygCmmnjn7zeOwM3dseYxE
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SeatVentSettingDialog.this.lambda$initData$4$SeatVentSettingDialog((SeatVentLevel) obj);
                    }
                });
            }
            this.mViewModel.getHvacSeatHeatData().observe(this, new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$pkCp1Tg0Czvs4rci8ePFBuZeXCo
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SeatVentSettingDialog.this.lambda$initData$5$SeatVentSettingDialog((SeatHeatLevel) obj);
                }
            });
            this.mViewModel.getHvacPsnSeatHeatData().observe(this, new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$wi8ij07uKBDlw_ybBnPBHjrRbhg
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SeatVentSettingDialog.this.lambda$initData$6$SeatVentSettingDialog((SeatHeatLevel) obj);
                }
            });
            if (CarBaseConfig.getInstance().isSupportPsnSeatVent()) {
                setSeatVentLevel(1, this.mViewModel.getHvacPsnSeatVentLevel());
                this.mViewModel.getHvacPsnSeatVentData().observe(this, new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$R1a0vgV1yp2JI6eg2W-0Ra60XTI
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        SeatVentSettingDialog.this.lambda$initData$7$SeatVentSettingDialog((SeatVentLevel) obj);
                    }
                });
            }
            this.mViewModel.getHvacRLSeatHeatData().observe(this, new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$Dn_GlCG1kEo39dlVkLjctEYmR90
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SeatVentSettingDialog.this.lambda$initData$8$SeatVentSettingDialog((SeatHeatLevel) obj);
                }
            });
            this.mViewModel.getHvacRRSeatHeatData().observe(this, new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$Ngpf_f2wk7vIQRHpNkILsI7lmkM
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SeatVentSettingDialog.this.lambda$initData$9$SeatVentSettingDialog((SeatHeatLevel) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initData$4$SeatVentSettingDialog(SeatVentLevel seatVentLevel) {
        setSeatVentLevel(0, this.mViewModel.getHvacSeatVentLevel());
    }

    public /* synthetic */ void lambda$initData$5$SeatVentSettingDialog(SeatHeatLevel seatHeatLevel) {
        setSeatHeatLevel(0, this.mViewModel.getHvacSeatHeatLevel());
    }

    public /* synthetic */ void lambda$initData$6$SeatVentSettingDialog(SeatHeatLevel seatHeatLevel) {
        setSeatHeatLevel(1, this.mViewModel.getHvacPsnSeatHeatLevel());
    }

    public /* synthetic */ void lambda$initData$7$SeatVentSettingDialog(SeatVentLevel seatVentLevel) {
        setSeatVentLevel(1, this.mViewModel.getHvacPsnSeatVentLevel());
    }

    public /* synthetic */ void lambda$initData$8$SeatVentSettingDialog(SeatHeatLevel seatHeatLevel) {
        setSeatHeatLevel(2, this.mViewModel.getHvacRLSeatHeatLevel());
    }

    public /* synthetic */ void lambda$initData$9$SeatVentSettingDialog(SeatHeatLevel seatHeatLevel) {
        setSeatHeatLevel(3, this.mViewModel.getHvacRRSeatHeatLevel());
    }

    private void initViews(View view) {
        this.mBtnAllOff = (XButton) view.findViewById(R.id.btn_all_off);
        XImageButton xImageButton = (XImageButton) view.findViewById(R.id.img_main_seat_heat);
        this.mImgMainHeat = xImageButton;
        xImageButton.setVuiElementType(VuiElementType.STATEFULBUTTON);
        VuiEngine.getInstance(App.getInstance()).setVuiElementUnSupportTag(this.mImgMainHeat, false);
        XImageButton xImageButton2 = (XImageButton) view.findViewById(R.id.img_main_seat_vent);
        this.mImgMainVent = xImageButton2;
        xImageButton2.setVuiElementType(VuiElementType.STATEFULBUTTON);
        VuiEngine.getInstance(App.getInstance()).setVuiElementUnSupportTag(this.mImgMainVent, false);
        this.mImgMainHeatBg = (XImageView) view.findViewById(R.id.img_main_seat_heat_bg);
        XImageButton xImageButton3 = (XImageButton) view.findViewById(R.id.img_heat_fr);
        this.mImgHeatFr = xImageButton3;
        xImageButton3.setVuiElementType(VuiElementType.STATEFULBUTTON);
        VuiEngine.getInstance(App.getInstance()).setVuiElementUnSupportTag(this.mImgHeatFr, false);
        XImageButton xImageButton4 = (XImageButton) view.findViewById(R.id.img_vent_fr);
        this.mImgVentFr = xImageButton4;
        xImageButton4.setVuiElementType(VuiElementType.STATEFULBUTTON);
        VuiEngine.getInstance(App.getInstance()).setVuiElementUnSupportTag(this.mImgVentFr, false);
        this.mImgHeatFrBg = (XImageView) view.findViewById(R.id.img_heat_fr_bg);
        XImageButton xImageButton5 = (XImageButton) view.findViewById(R.id.img_heat_rl);
        this.mImgHeatRl = xImageButton5;
        xImageButton5.setVuiElementType(VuiElementType.STATEFULBUTTON);
        VuiEngine.getInstance(App.getInstance()).setVuiElementUnSupportTag(this.mImgHeatRl, false);
        this.mImgHeatRlBg = (XImageView) view.findViewById(R.id.img_heat_rl_bg);
        XImageButton xImageButton6 = (XImageButton) view.findViewById(R.id.img_heat_rr);
        this.mImgHeatRr = xImageButton6;
        xImageButton6.setVuiElementType(VuiElementType.STATEFULBUTTON);
        VuiEngine.getInstance(App.getInstance()).setVuiElementUnSupportTag(this.mImgHeatRr, false);
        this.mImgHeatRrBg = (XImageView) view.findViewById(R.id.img_heat_rr_bg);
        this.mTvTitle = (XTextView) view.findViewById(R.id.tv_title);
        this.mBtnAllOff.setOnClickListener(this);
        if (CarBaseConfig.getInstance().isSupportDrvSeatHeat()) {
            this.mImgMainHeat.setOnClickListener(this);
            this.mImgMainHeat.setClickable(true);
        } else {
            this.mImgMainHeat.setVisibility(4);
        }
        boolean z = this.mIsVentSupport;
        if (!z || !this.mIsHeatSupport) {
            this.mTvTitle.setText(z ? R.string.hvac_seat_vent_adjust : R.string.hvac_seat_title_heat_adjust);
        }
        if (!this.mIsVentSupport) {
            this.mImgMainVent.setVisibility(8);
            this.mImgMainHeatBg.setOnClickListener(this);
        } else {
            this.mImgMainVent.setOnClickListener(this);
        }
        if (CarBaseConfig.getInstance().isSupportPsnSeatHeat()) {
            this.mImgHeatFr.setOnClickListener(this);
            this.mImgHeatFr.setClickable(true);
            this.mImgHeatFrBg.setOnClickListener(this);
            this.mImgHeatFrBg.setClickable(true);
        } else {
            this.mImgHeatFr.setVisibility(4);
            this.mImgHeatFrBg.setClickable(false);
        }
        if (CarBaseConfig.getInstance().isSupportPsnSeatVent()) {
            this.mImgVentFr.setOnClickListener(this);
            this.mImgVentFr.setClickable(true);
        } else {
            this.mImgVentFr.setVisibility(4);
            this.mImgVentFr.setClickable(false);
            if (CarBaseConfig.getInstance().isSupportPsnSeatHeat()) {
                this.mImgHeatFr.setOnClickListener(this);
                this.mImgHeatFr.setClickable(true);
                this.mImgHeatFrBg.setOnClickListener(this);
                this.mImgHeatFrBg.setClickable(true);
            }
        }
        if (CarBaseConfig.getInstance().isSupportRearSeatHeat()) {
            this.mImgHeatRl.setOnClickListener(this);
            this.mImgHeatRlBg.setOnClickListener(this);
            this.mImgHeatRr.setOnClickListener(this);
            this.mImgHeatRrBg.setOnClickListener(this);
            this.mImgHeatRl.setClickable(true);
            this.mImgHeatRlBg.setClickable(true);
            this.mImgHeatRr.setClickable(true);
            this.mImgHeatRrBg.setClickable(true);
        } else {
            this.mImgHeatRl.setVisibility(8);
            this.mImgHeatRr.setVisibility(8);
            this.mImgHeatRlBg.setClickable(false);
            this.mImgHeatRrBg.setClickable(false);
        }
        view.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$SeatVentSettingDialog$dl833qBj5hHZBoAi0lkpNBRTBPI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SeatVentSettingDialog.this.lambda$initViews$10$SeatVentSettingDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initViews$10$SeatVentSettingDialog(View v) {
        dismiss();
    }

    private void setHeatLevel(XImageButton heatView, SeatHeatLevel seatHeatLevel) {
        heatView.setTag(seatHeatLevel);
        heatView.setSelected(SeatHeatLevel.Off != seatHeatLevel);
        int i = AnonymousClass3.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel[seatHeatLevel.ordinal()];
        if (i == 1) {
            heatView.setImageResource(R.drawable.ic_small_seatheatingon01);
        } else if (i == 2) {
            heatView.setImageResource(R.drawable.ic_small_seatheatingon02);
        } else if (i == 3) {
            heatView.setImageResource(R.drawable.ic_small_seatheatingon03);
        } else {
            heatView.setImageResource(R.drawable.ic_small_seatheatingoff);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (this.mListener == null) {
            return;
        }
        if (id == R.id.img_main_seat_vent) {
            if (CarBaseConfig.getInstance().isSupportDrvSeatVent()) {
                setDrvHeatVentClickable(false);
                ThreadUtils.runOnMainThreadDelay(this.mMainVentHeatClickProtectRun, BTN_CLICK_PROTECT_DELAY);
                notifyVentLevelToggled(0, v);
            }
        } else if (id == R.id.img_main_seat_heat || id == R.id.img_main_seat_heat_bg) {
            if (CarBaseConfig.getInstance().isSupportDrvSeatHeat()) {
                setDrvHeatVentClickable(false);
                ThreadUtils.runOnMainThreadDelay(this.mMainVentHeatClickProtectRun, BTN_CLICK_PROTECT_DELAY);
                notifyHeatLevelToggled(0, this.mImgMainHeat);
            }
        } else if (id == R.id.img_heat_fr || id == R.id.img_heat_fr_bg) {
            if (CarBaseConfig.getInstance().isSupportPsnSeatHeat()) {
                setPsnHeatVentClickable(false);
                ThreadUtils.runOnMainThreadDelay(this.mFrHeatVentClickProtectRun, BTN_CLICK_PROTECT_DELAY);
                notifyHeatLevelToggled(1, this.mImgHeatFr);
            }
        } else if (id == R.id.img_heat_rl || id == R.id.img_heat_rl_bg) {
            if (CarBaseConfig.getInstance().isSupportRearSeatHeat()) {
                XImageButton xImageButton = this.mImgHeatRl;
                if (xImageButton != null) {
                    xImageButton.setClickable(false);
                }
                XImageView xImageView = this.mImgHeatRlBg;
                if (xImageView != null) {
                    xImageView.setClickable(false);
                }
                ThreadUtils.runOnMainThreadDelay(this.mRlHeatClickProtectRun, BTN_CLICK_PROTECT_DELAY);
                notifyHeatLevelToggled(2, this.mImgHeatRl);
            }
        } else if (id == R.id.img_heat_rr || id == R.id.img_heat_rr_bg) {
            if (CarBaseConfig.getInstance().isSupportRearSeatHeat()) {
                XImageButton xImageButton2 = this.mImgHeatRr;
                if (xImageButton2 != null) {
                    xImageButton2.setClickable(false);
                }
                XImageView xImageView2 = this.mImgHeatRrBg;
                if (xImageView2 != null) {
                    xImageView2.setClickable(false);
                }
                ThreadUtils.runOnMainThreadDelay(this.mRrHeatClickProtectRun, BTN_CLICK_PROTECT_DELAY);
                notifyHeatLevelToggled(3, this.mImgHeatRr);
            }
        } else if (id == R.id.btn_all_off) {
            setAllClickable(false);
            ThreadUtils.removeRunnable(this.mMainVentHeatClickProtectRun);
            ThreadUtils.removeRunnable(this.mFrHeatVentClickProtectRun);
            ThreadUtils.removeRunnable(this.mRlHeatClickProtectRun);
            ThreadUtils.removeRunnable(this.mRrHeatClickProtectRun);
            ThreadUtils.runOnMainThreadDelay(this.mAllBtnClickProtectRun, BTN_CLICK_PROTECT_DELAY);
            allClose();
        } else if (id == R.id.img_vent_fr && CarBaseConfig.getInstance().isSupportPsnSeatVent()) {
            setPsnHeatVentClickable(false);
            ThreadUtils.runOnMainThreadDelay(this.mFrHeatVentClickProtectRun, BTN_CLICK_PROTECT_DELAY);
            notifyVentLevelToggled(1, this.mImgVentFr);
        }
    }

    private void setDrvHeatVentClickable(boolean value) {
        XImageButton xImageButton = this.mImgMainVent;
        if (xImageButton != null) {
            xImageButton.setClickable(value);
        }
        XImageButton xImageButton2 = this.mImgMainHeat;
        if (xImageButton2 != null) {
            xImageButton2.setClickable(value);
        }
        XImageView xImageView = this.mImgMainHeatBg;
        if (xImageView != null) {
            xImageView.setClickable(value);
        }
    }

    private void setPsnHeatVentClickable(boolean value) {
        XImageButton xImageButton = this.mImgHeatFr;
        if (xImageButton != null) {
            xImageButton.setClickable(value);
        }
        XImageView xImageView = this.mImgHeatFrBg;
        if (xImageView != null) {
            xImageView.setClickable(value);
        }
        XImageButton xImageButton2 = this.mImgVentFr;
        if (xImageButton2 != null) {
            xImageButton2.setClickable(value);
        }
    }

    public void setAllClickable(boolean value) {
        if (getDialog() == null) {
            return;
        }
        XButton xButton = this.mBtnAllOff;
        if (xButton != null) {
            xButton.setClickable(value);
        }
        XImageButton xImageButton = this.mImgMainVent;
        if (xImageButton != null) {
            xImageButton.setClickable(value);
        }
        XImageButton xImageButton2 = this.mImgMainHeat;
        if (xImageButton2 != null) {
            xImageButton2.setClickable(value);
        }
        XImageView xImageView = this.mImgMainHeatBg;
        if (xImageView != null) {
            xImageView.setClickable(value);
        }
        XImageButton xImageButton3 = this.mImgHeatFr;
        if (xImageButton3 != null) {
            xImageButton3.setClickable(value);
        }
        XImageView xImageView2 = this.mImgHeatFrBg;
        if (xImageView2 != null) {
            xImageView2.setClickable(value);
        }
        XImageButton xImageButton4 = this.mImgHeatRl;
        if (xImageButton4 != null) {
            xImageButton4.setClickable(value);
        }
        XImageView xImageView3 = this.mImgHeatRlBg;
        if (xImageView3 != null) {
            xImageView3.setClickable(value);
        }
        XImageButton xImageButton5 = this.mImgHeatRr;
        if (xImageButton5 != null) {
            xImageButton5.setClickable(value);
        }
        XImageView xImageView4 = this.mImgHeatRrBg;
        if (xImageView4 != null) {
            xImageView4.setClickable(value);
        }
        XImageButton xImageButton6 = this.mImgVentFr;
        if (xImageButton6 != null) {
            xImageButton6.setClickable(value);
        }
    }

    private void notifyVentLevelToggled(int type, View view) {
        if (view.getTag() != null) {
            SeatVentLevel seatVentLevel = (SeatVentLevel) view.getTag();
            ISeatHeatVentChangeListener iSeatHeatVentChangeListener = this.mListener;
            if (iSeatHeatVentChangeListener != null) {
                iSeatHeatVentChangeListener.onVentChanged(type, seatVentLevel.toggle());
            }
            LogUtils.d(TAG, "notifyVentLevelToggled");
        }
    }

    private void notifyHeatLevelToggled(int type, View view) {
        if (view.getTag() != null) {
            SeatHeatLevel seatHeatLevel = (SeatHeatLevel) view.getTag();
            ISeatHeatVentChangeListener iSeatHeatVentChangeListener = this.mListener;
            if (iSeatHeatVentChangeListener != null) {
                iSeatHeatVentChangeListener.onHeatChanged(type, seatHeatLevel.toggle());
            }
            LogUtils.d(TAG, "notifyHeatLevelToggled: " + seatHeatLevel.ordinal());
        }
    }

    private void allClose() {
        LogUtils.d(TAG, "allClose");
        ISeatHeatVentChangeListener iSeatHeatVentChangeListener = this.mListener;
        if (iSeatHeatVentChangeListener != null) {
            iSeatHeatVentChangeListener.onAllClose();
        }
    }

    public void setSeatVentLevel(int type, SeatVentLevel seatVentLevel) {
        if (type == 0) {
            setVentLevel(this.mImgMainVent, seatVentLevel);
            updateMainSeatBg();
        } else if (type != 1) {
        } else {
            setVentLevel(this.mImgVentFr, seatVentLevel);
            updatePsnSeatBg();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.dialog.SeatVentSettingDialog$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel;

        static {
            int[] iArr = new int[SeatVentLevel.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel = iArr;
            try {
                iArr[SeatVentLevel.Level1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel[SeatVentLevel.Level2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel[SeatVentLevel.Level3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[SeatHeatLevel.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel = iArr2;
            try {
                iArr2[SeatHeatLevel.Level1.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel[SeatHeatLevel.Level2.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatHeatLevel[SeatHeatLevel.Level3.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private void setVentLevel(XImageButton view, SeatVentLevel seatVentLevel) {
        view.setTag(seatVentLevel);
        int i = AnonymousClass3.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$SeatVentLevel[seatVentLevel.ordinal()];
        if (i == 1) {
            view.setImageResource(R.drawable.ic_small_aeration01);
        } else if (i == 2) {
            view.setImageResource(R.drawable.ic_small_aeration02);
        } else if (i == 3) {
            view.setImageResource(R.drawable.ic_small_aeration03);
        } else {
            view.setImageResource(R.drawable.ic_small_aeratioff);
        }
        view.setSelected(SeatVentLevel.Off != seatVentLevel);
        updateAllBtnStatus();
        updateSeatConstruction(view, seatVentLevel.ordinal(), StatefulButtonConstructorFactory.ConstructorType.SEAT_AIR);
    }

    private void updateSeatConstruction(IVuiElement view, int level, StatefulButtonConstructorFactory.ConstructorType type) {
        StatefulButtonConstructorFactory.getConstructor(type, getDialog().getContext(), level).construct(view);
        VuiEngine.getInstance(App.getInstance()).updateScene(SCENE_HVAC_SMART_DIALOG, (View) view);
    }

    public void setSeatHeatLevel(int type, SeatHeatLevel seatHeatLevel) {
        LogUtils.i(TAG, "setSeatHeatLevel: " + type + ", seatHeatLevel: " + seatHeatLevel);
        if (type == 0) {
            setHeatLevel(this.mImgMainHeat, seatHeatLevel);
            updateMainSeatBg();
            updateSeatConstruction(this.mImgMainHeat, seatHeatLevel.ordinal(), StatefulButtonConstructorFactory.ConstructorType.SEAT_HOT);
        } else if (type == 1) {
            setHeatLevel(this.mImgHeatFr, seatHeatLevel);
            updatePsnSeatBg();
            updateSeatConstruction(this.mImgHeatFr, seatHeatLevel.ordinal(), StatefulButtonConstructorFactory.ConstructorType.SEAT_HOT);
        } else if (type == 2) {
            setHeatLevel(this.mImgHeatRl, seatHeatLevel);
            updateSeatBgVisibility(this.mImgHeatRlBg, seatHeatLevel);
            updateSeatConstruction(this.mImgHeatRl, seatHeatLevel.ordinal(), StatefulButtonConstructorFactory.ConstructorType.SEAT_HOT);
        } else if (type == 3) {
            setHeatLevel(this.mImgHeatRr, seatHeatLevel);
            updateSeatBgVisibility(this.mImgHeatRrBg, seatHeatLevel);
            updateSeatConstruction(this.mImgHeatRr, seatHeatLevel.ordinal(), StatefulButtonConstructorFactory.ConstructorType.SEAT_HOT);
        }
        updateAllBtnStatus();
    }

    private void updateAllBtnStatus() {
        XButton xButton;
        HvacViewModel hvacViewModel = this.mViewModel;
        if (hvacViewModel == null || (xButton = this.mBtnAllOff) == null) {
            return;
        }
        xButton.setEnabled(hvacViewModel.getSeatHeatLevel() > 0 || this.mViewModel.getSeatVentLevel() > 0 || this.mViewModel.getPsnSeatHeatLevel() > 0 || this.mViewModel.getRLSeatHeatLevel() > 0 || this.mViewModel.getRRSeatHeatLevel() > 0 || this.mViewModel.getPsnSeatVentLevel() > 0);
        VuiEngine.getInstance(App.getInstance()).updateScene(SCENE_HVAC_SMART_DIALOG, this.mBtnAllOff);
    }

    private void updateMainSeatBg() {
        SeatHeatLevel seatHeatLevel = SeatHeatLevel.Off;
        if (this.mImgMainHeat.getTag() != null) {
            seatHeatLevel = (SeatHeatLevel) this.mImgMainHeat.getTag();
        }
        if (this.mIsVentSupport) {
            SeatVentLevel seatVentLevel = SeatVentLevel.Off;
            if (this.mImgMainVent.getTag() != null) {
                seatVentLevel = (SeatVentLevel) this.mImgMainVent.getTag();
            }
            if (seatVentLevel == SeatVentLevel.Off && seatHeatLevel == SeatHeatLevel.Off) {
                animateVisible(this.mImgMainHeatBg, false);
                return;
            }
            animateVisible(this.mImgMainHeatBg, true);
            if (seatVentLevel != SeatVentLevel.Off) {
                this.mImgMainHeatBg.setImageResource(R.drawable.seat_front_l);
                return;
            } else {
                this.mImgMainHeatBg.setImageResource(R.drawable.seat_front_l_red);
                return;
            }
        }
        this.mImgMainHeatBg.setImageResource(R.drawable.seat_front_l_red);
        animateVisible(this.mImgMainHeatBg, seatHeatLevel != SeatHeatLevel.Off);
    }

    private void updatePsnSeatBg() {
        SeatHeatLevel seatHeatLevel = SeatHeatLevel.Off;
        if (this.mImgHeatFr.getTag() != null) {
            seatHeatLevel = (SeatHeatLevel) this.mImgHeatFr.getTag();
        }
        if (CarBaseConfig.getInstance().isSupportPsnSeatVent()) {
            SeatVentLevel seatVentLevel = SeatVentLevel.Off;
            if (this.mImgVentFr.getTag() != null) {
                seatVentLevel = (SeatVentLevel) this.mImgVentFr.getTag();
            }
            if (seatVentLevel == SeatVentLevel.Off && seatHeatLevel == SeatHeatLevel.Off) {
                animateVisible(this.mImgHeatFrBg, false);
                return;
            }
            animateVisible(this.mImgHeatFrBg, true);
            if (seatVentLevel != SeatVentLevel.Off) {
                this.mImgHeatFrBg.setImageResource(R.drawable.seat_front_r_blue);
                return;
            } else {
                this.mImgHeatFrBg.setImageResource(R.drawable.seat_front_r);
                return;
            }
        }
        this.mImgHeatFrBg.setImageResource(R.drawable.seat_front_r);
        animateVisible(this.mImgHeatFrBg, seatHeatLevel != SeatHeatLevel.Off);
    }

    private void animateVisible(View view, boolean visible) {
        view.setAlpha(visible ? 1.0f : 0.0f);
    }

    private void updateSeatBgVisibility(XImageView seatBgView, SeatHeatLevel seatHeatLevel) {
        animateVisible(seatBgView, seatHeatLevel != SeatHeatLevel.Off);
    }

    @Override // com.xiaopeng.xui.app.XDialog
    public void dismiss() {
        super.dismiss();
        LogUtils.d(TAG, "dismiss");
        ThreadUtils.removeRunnable(this.mMainVentHeatClickProtectRun);
        ThreadUtils.removeRunnable(this.mFrHeatVentClickProtectRun);
        ThreadUtils.removeRunnable(this.mRlHeatClickProtectRun);
        ThreadUtils.removeRunnable(this.mRrHeatClickProtectRun);
        ThreadUtils.removeRunnable(this.mAllBtnClickProtectRun);
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        this.mListener = null;
    }

    @Override // com.xiaopeng.xui.app.XDialog
    public void show() {
        VuiManager.instance().initVuiDialog(this, getDialog().getContext(), SCENE_HVAC_SMART_DIALOG);
        setVuiElementListener(this);
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        super.show();
        LogUtils.d(TAG, "show");
    }

    public void setOnChangedListener(ISeatHeatVentChangeListener listener) {
        this.mListener = listener;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent event) {
        if (event == null || event.getHitVuiElement() == null) {
            return true;
        }
        VuiElement hitVuiElement = event.getHitVuiElement();
        if (hitVuiElement.getId().contains(String.valueOf(R.id.img_main_seat_heat))) {
            VuiEventHandler.getInstance().runMain(new SeatHotVuiEventActor(true, this.mViewModel, event));
        }
        if (hitVuiElement.getId().contains(String.valueOf(R.id.img_main_seat_vent))) {
            VuiEventHandler.getInstance().runMain(new SeatAirVuiEventActor(this.mViewModel, event, true));
        }
        if (hitVuiElement.getId().contains(String.valueOf(R.id.img_heat_fr))) {
            VuiEventHandler.getInstance().runMain(new SeatHotVuiEventActor(false, this.mViewModel, event));
        }
        if (hitVuiElement.getId().contains(String.valueOf(R.id.img_vent_fr))) {
            VuiEventHandler.getInstance().runMain(new SeatAirVuiEventActor(this.mViewModel, event, false));
        }
        if (hitVuiElement.getId().contains(String.valueOf(R.id.img_heat_rl))) {
            VuiEventHandler.getInstance().runMain(new SeatHotVuiEventActor(true, false, this.mViewModel, event));
        }
        if (hitVuiElement.getId().contains(String.valueOf(R.id.img_heat_rr))) {
            VuiEventHandler.getInstance().runMain(new SeatHotVuiEventActor(false, false, this.mViewModel, event));
        }
        if (!hitVuiElement.getId().contains(String.valueOf(R.id.close_btn))) {
            VuiFloatingLayerManager.show(view);
        }
        return false;
    }
}
