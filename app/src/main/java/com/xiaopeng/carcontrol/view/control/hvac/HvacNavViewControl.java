package com.xiaopeng.carcontrol.view.control.hvac;

import android.content.res.Configuration;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.DxCarConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.UserBookHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.UIUtils;
import com.xiaopeng.carcontrol.view.control.BaseViewControl;
import com.xiaopeng.carcontrol.view.speech.AcCirculationElementsConstructor;
import com.xiaopeng.carcontrol.view.speech.AcCirculationVuiEventActor;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.hvac.AcHeatNatureMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacCirculationMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacSwitchStatus;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.actor.VuiEventHandler;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XImageView;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class HvacNavViewControl extends BaseViewControl {
    private static final String SCENE_HVAC_SMART_DIALOG = "hvac_smartDialog";
    private static final long SMART_MODE_EXPLAIN_SWITCH_TIME = 3000;
    private static final String TAG = "HvacNavViewControl";
    private ImageView imgPower;
    private View mImgClose;
    private ImageView mImgCycle;
    private ImageView mImgEco;
    private View mImgSmartModeAbout;
    private ImageView mImgSmartModeExplainCooling;
    private ImageView mImgSmartModeExplainDeodorant;
    private View mLayoutBottom;
    private View mLayoutTop;
    private TextView mPm25InsideProgress;
    private TextView mPm25OutSide;
    private XDialog mSmartDialog;
    private Handler mSmartDialogHandler;
    private HvacViewModel mViewModel;
    private final Runnable smartModeSwitchRun = new Runnable() { // from class: com.xiaopeng.carcontrol.view.control.hvac.HvacNavViewControl.1
        @Override // java.lang.Runnable
        public void run() {
            if (HvacNavViewControl.this.mImgSmartModeExplainCooling.getAlpha() > 0.0f) {
                HvacNavViewControl.this.mImgSmartModeExplainCooling.animate().alpha(0.0f).start();
                HvacNavViewControl.this.mImgSmartModeExplainDeodorant.animate().alpha(1.0f).start();
            } else {
                HvacNavViewControl.this.mImgSmartModeExplainCooling.animate().alpha(1.0f).start();
                HvacNavViewControl.this.mImgSmartModeExplainDeodorant.animate().alpha(0.0f).start();
            }
            HvacNavViewControl.this.mSmartDialogHandler.postDelayed(this, HvacNavViewControl.SMART_MODE_EXPLAIN_SWITCH_TIME);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initBottomMenu$7(View v) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initPm25View$1(View v) {
    }

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void onCreate(AppCompatActivity activity, View view) {
        super.onCreate(activity, view);
        View findViewById = this.mRootView.findViewById(R.id.img_close);
        this.mImgClose = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$HLyQNs_nLvHVD1k5FDKF49iO3BM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                HvacNavViewControl.this.lambda$onCreate$0$HvacNavViewControl(view2);
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$0$HvacNavViewControl(View v) {
        this.mActivity.finish();
    }

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void initView() {
        super.initView();
        this.mViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
        initPm25View();
        initBottomMenu();
    }

    private void initPm25View() {
        View findViewById = this.mRootView.findViewById(R.id.layout_top);
        this.mLayoutTop = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$hzGRey-jX6FVXDQhEzRPm3KAvpA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HvacNavViewControl.lambda$initPm25View$1(view);
            }
        });
        this.mPm25OutSide = (TextView) this.mRootView.findViewById(R.id.hvacAirQualityOutsideText);
        TextView textView = (TextView) this.mRootView.findViewById(R.id.tv_pm_inner);
        TextView textView2 = (TextView) this.mRootView.findViewById(R.id.tv_hvac_pm_title);
        this.mPm25InsideProgress = (TextView) this.mRootView.findViewById(R.id.airQualityProgress);
        if (!DxCarConfig.getInstance().isSupportInnerPm25()) {
            textView.setVisibility(8);
            this.mPm25InsideProgress.setVisibility(8);
        } else {
            setPm25InnerLevel(this.mViewModel.getHvacInnerPM25());
            this.mViewModel.getHvacAqInnerData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$aOFrNo4zwStehAVmDyJ9pwiQVeM
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    HvacNavViewControl.this.lambda$initPm25View$2$HvacNavViewControl((Integer) obj);
                }
            });
        }
        if (!DxCarConfig.getInstance().isSupportPM25Out()) {
            textView2.setText(R.string.hvac_aq_title);
            this.mPm25OutSide.setVisibility(8);
        } else {
            setAqiOutside(this.mPm25OutSide, Integer.valueOf(this.mViewModel.getOutsidePm25()));
            this.mViewModel.getmHvacOutsidePM25().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$6ulYiyBAkTMaB-jm94dcpH6WAgM
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    HvacNavViewControl.this.lambda$initPm25View$3$HvacNavViewControl((Integer) obj);
                }
            });
        }
        View findViewById2 = this.mRootView.findViewById(R.id.img_smart_mode_about);
        this.mImgSmartModeAbout = findViewById2;
        findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$DMoNoJzkrj_uWg0TSk7OIzY6b8M
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HvacNavViewControl.this.lambda$initPm25View$4$HvacNavViewControl(view);
            }
        });
    }

    public /* synthetic */ void lambda$initPm25View$2$HvacNavViewControl(Integer integer) {
        setPm25InnerLevel(this.mViewModel.getHvacInnerPM25());
    }

    public /* synthetic */ void lambda$initPm25View$3$HvacNavViewControl(Integer integer) {
        setAqiOutside(this.mPm25OutSide, Integer.valueOf(this.mViewModel.getOutsidePm25()));
    }

    public /* synthetic */ void lambda$initPm25View$4$HvacNavViewControl(View v) {
        showSmartModeExplainDialog();
    }

    private void showSmartModeExplainDialog() {
        if (this.mSmartDialog == null) {
            this.mSmartDialog = new XDialog(this.mActivity, R.style.XDialogView_Large);
            View inflate = LayoutInflater.from(this.mActivity).inflate(R.layout.hvac_smart_mode_explain_layout, (ViewGroup) null);
            this.mImgSmartModeExplainCooling = (ImageView) inflate.findViewById(R.id.img_rapid_cooling_explain);
            this.mImgSmartModeExplainDeodorant = (ImageView) inflate.findViewById(R.id.img_deodorant_explain);
            this.mSmartDialog.setCustomView(inflate);
            this.mSmartDialog.setSystemDialog(2008);
            this.mSmartDialog.setTitle(R.string.hvac_smart_mode_explain_title);
            this.mSmartDialog.setPositiveButton(this.mActivity.getString(R.string.btn_close), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$R70o4K97eA9aqH_5LHqlH0xv8lo
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    HvacNavViewControl.this.lambda$showSmartModeExplainDialog$5$HvacNavViewControl(xDialog, i);
                }
            });
            if (UserBookHelper.isSupport()) {
                this.mSmartDialog.setNegativeButton(this.mActivity.getString(R.string.btn_user_manual), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$gMjcXT047D6ukYW4kHNOkXS9ScI
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        HvacNavViewControl.this.lambda$showSmartModeExplainDialog$6$HvacNavViewControl(xDialog, i);
                    }
                });
            }
        }
        VuiManager.instance().initVuiDialog(this.mSmartDialog, this.mActivity, SCENE_HVAC_SMART_DIALOG);
        this.mSmartDialog.show();
        Window window = this.mSmartDialog.getDialog().getWindow();
        if (window != null) {
            window.setElevation(0.0f);
        }
        startChangeModeHandler();
    }

    public /* synthetic */ void lambda$showSmartModeExplainDialog$5$HvacNavViewControl(XDialog xDialog, int i) {
        stopChangeModeHandler();
    }

    public /* synthetic */ void lambda$showSmartModeExplainDialog$6$HvacNavViewControl(XDialog xDialog, int i) {
        if ((this.mActivity.getIntent().getFlags() & 1024) == 1024) {
            NotificationHelper.getInstance().showToastLong(R.string.hvac_user_manual_toast, true, "hvac");
        } else {
            UserBookHelper.openUserBook(this.mActivity.getString(R.string.hvac_user_book_key), true);
        }
        stopChangeModeHandler();
    }

    private void startChangeModeHandler() {
        if (this.mSmartDialogHandler == null) {
            this.mSmartDialogHandler = new Handler();
        }
        stopChangeModeHandler();
        this.mSmartDialogHandler.postDelayed(this.smartModeSwitchRun, SMART_MODE_EXPLAIN_SWITCH_TIME);
    }

    private void stopChangeModeHandler() {
        Handler handler = this.mSmartDialogHandler;
        if (handler != null) {
            handler.removeCallbacks(this.smartModeSwitchRun);
        }
    }

    private void setPm25InnerLevel(int level) {
        if (level < 0 || level == 1023) {
            setAqiProgress(0, R.color.x_theme_text_01, this.mActivity.getString(R.string.hvac_pm25_error));
        } else if (level <= 50) {
            setAqiProgress(R.drawable.aqi_pmbg_yx, 17170444, level + " " + this.mActivity.getString(R.string.hvac_aqi_grade_yx));
        } else if (level <= 100) {
            setAqiProgress(R.drawable.aqi_pmbg_lh, 17170444, level + " " + this.mActivity.getString(R.string.hvac_aqi_grade_lh));
        } else if (level <= 150) {
            setAqiProgress(R.drawable.aqi_pmbg_qd, 17170444, level + " " + this.mActivity.getString(R.string.hvac_aqi_grade_qd));
        } else if (level <= 200) {
            setAqiProgress(R.drawable.aqi_pmbg_zd, R.color.x_white_color, level + " " + this.mActivity.getString(R.string.hvac_aqi_grade_zd));
        } else if (level <= 300) {
            setAqiProgress(R.drawable.aqi_pmbg_zdw, R.color.x_white_color, level + " " + this.mActivity.getString(R.string.hvac_aqi_grade_zdw));
        } else {
            setAqiProgress(R.drawable.aqi_pmbg_yz, R.color.x_white_color, level + " " + this.mActivity.getString(R.string.hvac_aqi_grade_yz));
        }
    }

    private void setAqiOutside(TextView pm25OutsideTv, Integer integer) {
        int i;
        if (integer != null && integer.intValue() >= 0) {
            if (integer.intValue() <= 50) {
                i = R.color.aqi_grade_yx;
            } else if (integer.intValue() <= 100) {
                i = R.color.aqi_grade_lh;
            } else if (integer.intValue() <= 150) {
                i = R.color.aqi_grade_qd;
            } else if (integer.intValue() <= 200) {
                i = R.color.aqi_grade_zd;
            } else if (integer.intValue() <= 300) {
                i = R.color.aqi_grade_zdw;
            } else {
                i = R.color.aqi_grade_yz;
            }
            pm25OutsideTv.setTextColor(this.mActivity.getColor(i));
            pm25OutsideTv.setText(integer.toString());
            return;
        }
        pm25OutsideTv.setTextColor(this.mActivity.getColor(R.color.x_theme_text_01));
        pm25OutsideTv.setText(this.mActivity.getString(R.string.hvac_pm25_error));
    }

    private void setAqiProgress(int resourceId, int pColorId, String text) {
        if (resourceId == 0) {
            if (this.mPm25InsideProgress.getGravity() != 16) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mPm25InsideProgress.getLayoutParams();
                layoutParams.height = -2;
                this.mPm25InsideProgress.setLayoutParams(layoutParams);
                this.mPm25InsideProgress.setGravity(16);
                this.mPm25InsideProgress.setTextSize(this.mActivity.getResources().getDimension(R.dimen.x_font_body_02_size));
            }
        } else if (this.mPm25InsideProgress.getGravity() != 17) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mPm25InsideProgress.getLayoutParams();
            layoutParams2.height = UIUtils.dp2px(this.mActivity, 24);
            this.mPm25InsideProgress.setGravity(17);
            this.mPm25InsideProgress.setLayoutParams(layoutParams2);
            this.mPm25InsideProgress.setTextSize(this.mActivity.getResources().getDimension(R.dimen.x_font_body_04_size));
        }
        this.mPm25InsideProgress.setBackgroundResource(resourceId);
        this.mPm25InsideProgress.setTextColor(this.mActivity.getColor(pColorId));
        this.mPm25InsideProgress.setText(text);
    }

    public void intoImmerseMode() {
        alphaHidden(this.mImgClose);
        alphaHidden(this.mLayoutTop);
        alphaHidden(this.mImgSmartModeAbout);
        alphaHidden(this.mLayoutBottom);
    }

    private void alphaHidden(View view) {
        if (view == null) {
            return;
        }
        view.animate().alpha(0.0f).start();
    }

    public void exitImmerseMode() {
        alphaShow(this.mImgClose);
        alphaShow(this.mLayoutTop);
        alphaShow(this.mImgSmartModeAbout);
        alphaShow(this.mLayoutBottom);
    }

    private void alphaShow(View view) {
        if (view == null) {
            return;
        }
        view.animate().alpha(1.0f).start();
    }

    private void initBottomMenu() {
        View findViewById = this.mRootView.findViewById(R.id.layout_bottom);
        this.mLayoutBottom = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$pr3Ne7YTIR13Aek-b6jpng2FqbA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HvacNavViewControl.lambda$initBottomMenu$7(view);
            }
        });
        final ImageView imageView = (ImageView) this.mRootView.findViewById(R.id.img_hvac_ac);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$yR-wfoKgC_ZBXIp06HYYIPXGGmA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HvacNavViewControl.this.lambda$initBottomMenu$8$HvacNavViewControl(view);
            }
        });
        imageView.setSelected(this.mViewModel.isHvacAcModeOn());
        this.mViewModel.getHvacTempAcData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$UGuWxErtscpNexydr5Twg3I5Dss
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HvacNavViewControl.this.lambda$initBottomMenu$9$HvacNavViewControl(imageView, (AcHeatNatureMode) obj);
            }
        });
        ImageView imageView2 = (ImageView) this.mRootView.findViewById(R.id.img_hvac_cycle);
        this.mImgCycle = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$TJY_1u606-1WP6ai15u56qu9XTc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HvacNavViewControl.this.lambda$initBottomMenu$10$HvacNavViewControl(view);
            }
        });
        setCycleMode();
        final XImageView xImageView = (XImageView) this.mRootView.findViewById(R.id.img_hvac_auto);
        if (!CarBaseConfig.getInstance().isSupportCloseAutoDirect()) {
            xImageView.setVuiElementType(VuiElementType.BUTTON);
        }
        xImageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$NrD4pJbBLvjq0nPU_ocWNUAyh5k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HvacNavViewControl.this.lambda$initBottomMenu$11$HvacNavViewControl(view);
            }
        });
        xImageView.setSelected(this.mViewModel.isHvacAutoModeOn());
        this.mViewModel.getHvacAutoData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$HIhSeW8DBc-lLhGky2T6fcaPpig
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HvacNavViewControl.this.lambda$initBottomMenu$12$HvacNavViewControl(xImageView, (Boolean) obj);
            }
        });
        ImageView imageView3 = (ImageView) this.mRootView.findViewById(R.id.img_hvac_power);
        this.imgPower = imageView3;
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$m4hovkErtCGbHGk4ppdTYmtGAq0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HvacNavViewControl.this.lambda$initBottomMenu$13$HvacNavViewControl(view);
            }
        });
        this.imgPower.setSelected(this.mViewModel.isHvacPowerModeOn());
        this.mViewModel.getHvacCirculationData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$IFVgpq7XP9bq_YYPIDhI1Q_tsVc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HvacNavViewControl.this.lambda$initBottomMenu$14$HvacNavViewControl((HvacCirculationMode) obj);
            }
        });
        ImageView imageView4 = (ImageView) this.mRootView.findViewById(R.id.img_hvac_eco);
        this.mImgEco = imageView4;
        imageView4.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$YPEhyJkpQ8a_Jm0QY-_j4ebgUwk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HvacNavViewControl.this.lambda$initBottomMenu$15$HvacNavViewControl(view);
            }
        });
        setEconMode();
        this.mViewModel.getHvacEconData().observe(this.mActivity, new Observer() { // from class: com.xiaopeng.carcontrol.view.control.hvac.-$$Lambda$HvacNavViewControl$6Ef4y0S7nxD9FGhMrqZv8L-MPs4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HvacNavViewControl.this.lambda$initBottomMenu$16$HvacNavViewControl((HvacSwitchStatus) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initBottomMenu$8$HvacNavViewControl(View v) {
        boolean isHvacAcModeOn = this.mViewModel.isHvacAcModeOn();
        StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PAGE, BtnEnum.HVAC_AC_SWITCH, Integer.valueOf(StatisticUtils.getSwitchOnOff(!isHvacAcModeOn)));
        this.mViewModel.setHvacAcMode(!isHvacAcModeOn);
    }

    public /* synthetic */ void lambda$initBottomMenu$9$HvacNavViewControl(final ImageView imgAc, AcHeatNatureMode acHeatNatureMode) {
        imgAc.setSelected(this.mViewModel.isHvacAcModeOn());
        VuiEngine.getInstance(this.mActivity.getApplicationContext()).updateScene("hvac", imgAc);
    }

    public /* synthetic */ void lambda$initBottomMenu$10$HvacNavViewControl(View v) {
        int hvacCmd = HvacCirculationMode.toHvacCmd(this.mViewModel.getCirculationMode().toggle());
        PageEnum pageEnum = PageEnum.HVAC_PAGE;
        BtnEnum btnEnum = BtnEnum.HVAC_IN_LOOP_SWITCH;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(StatisticUtils.getSwitchOnOff(hvacCmd == HvacCirculationMode.Inner.ordinal()));
        StatisticUtils.sendHvacStatistic(pageEnum, btnEnum, objArr);
        this.mViewModel.setHvacCirculationMode(hvacCmd);
    }

    public /* synthetic */ void lambda$initBottomMenu$11$HvacNavViewControl(View v) {
        boolean isHvacAutoModeOn = this.mViewModel.isHvacAutoModeOn();
        StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PAGE, BtnEnum.HVAC_AUTO_SWITCH, Integer.valueOf(StatisticUtils.getSwitchOnOff(!isHvacAutoModeOn)));
        this.mViewModel.setHvacAutoMode(!isHvacAutoModeOn);
    }

    public /* synthetic */ void lambda$initBottomMenu$12$HvacNavViewControl(final XImageView imgAuto, Boolean aBoolean) {
        imgAuto.setSelected(this.mViewModel.isHvacAutoModeOn());
        setCycleMode();
        VuiEngine.getInstance(this.mActivity.getApplicationContext()).updateScene("hvac", imgAuto);
    }

    public /* synthetic */ void lambda$initBottomMenu$13$HvacNavViewControl(View v) {
        boolean isHvacPowerModeOn = this.mViewModel.isHvacPowerModeOn();
        StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PAGE, BtnEnum.HVAC_SWITCH, Integer.valueOf(StatisticUtils.getSwitchOnOff(!isHvacPowerModeOn)));
        this.mViewModel.setHvacPowerMode(!isHvacPowerModeOn);
    }

    public /* synthetic */ void lambda$initBottomMenu$14$HvacNavViewControl(HvacCirculationMode hvacCirculationMode) {
        setCycleMode();
    }

    public /* synthetic */ void lambda$initBottomMenu$15$HvacNavViewControl(View v) {
        this.mViewModel.setHvacEconSwitchStatus(HvacSwitchStatus.ON == this.mViewModel.getHvacEconSwitchStatus() ? HvacSwitchStatus.OFF : HvacSwitchStatus.ON);
    }

    public /* synthetic */ void lambda$initBottomMenu$16$HvacNavViewControl(HvacSwitchStatus hvacSwitchStatus) {
        LogUtils.d(TAG, "hvacSwitchStatus:" + hvacSwitchStatus);
        setEconMode();
    }

    public void onHvacPowerChange() {
        this.imgPower.setSelected(this.mViewModel.isHvacPowerModeOn());
        setEconMode();
        VuiEngine.getInstance(this.mActivity.getApplicationContext()).updateScene("hvac", this.imgPower);
    }

    private void setCycleMode() {
        ImageView imageView = this.mImgCycle;
        if (imageView != null) {
            imageView.setImageResource(this.mViewModel.getCirculationMode() == HvacCirculationMode.Inner ? R.drawable.ic_mid_insidecycle_blue : R.drawable.ic_mid_outcycle_blue);
            VuiEngine vuiEngine = VuiEngine.getInstance(this.mActivity.getApplicationContext());
            ImageView imageView2 = this.mImgCycle;
            vuiEngine.updateScene("hvac", imageView2, Collections.singletonList(Integer.valueOf(imageView2.getId())), (IVuiElementListener) this.mActivity);
        }
    }

    private void setEconMode() {
        ImageView imageView = this.mImgEco;
        if (imageView != null) {
            imageView.setSelected(this.mViewModel.isHvacPowerModeOn() && this.mViewModel.getHvacEconSwitchStatus() == HvacSwitchStatus.ON);
            VuiEngine.getInstance(this.mActivity.getApplicationContext()).updateScene("hvac", this.mImgEco);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void onConfigurationChanged(Configuration newConfig) {
        HvacViewModel hvacViewModel;
        super.onConfigurationChanged(newConfig);
        TextView textView = this.mPm25OutSide;
        if (textView == null || (hvacViewModel = this.mViewModel) == null) {
            return;
        }
        setAqiOutside(textView, Integer.valueOf(hvacViewModel.getOutsidePm25()));
    }

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void onDestroy() {
        super.onDestroy();
        stopChangeModeHandler();
        XDialog xDialog = this.mSmartDialog;
        if (xDialog == null || !xDialog.isShowing()) {
            return;
        }
        this.mSmartDialog.dismiss();
    }

    @Override // com.xiaopeng.carcontrol.view.control.BaseViewControl
    public void onBuildScene(List<Integer> list) {
        list.add(Integer.valueOf(this.mImgCycle.getId()));
    }

    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        if (vuiEvent.getHitVuiElement().getId().contains(String.valueOf(R.id.img_hvac_cycle))) {
            if (new AcCirculationVuiEventActor(this.mViewModel, vuiEvent).getMode() == null) {
                VuiEngine.getInstance(this.mActivity).vuiFeedback(view, new VuiFeedback.Builder().state(1).content(this.mActivity.getString(R.string.hvac_vui_not_support)).build());
                return;
            }
            VuiEventHandler.getInstance().runMain(new AcCirculationVuiEventActor(this.mViewModel, vuiEvent));
            showVuiFloatingLayer(view);
        }
    }

    public VuiElement onBuildVuiElement(String id, IVuiElementBuilder iVuiElementBuilder) {
        if (id.contains(String.valueOf(this.mImgCycle.getId()))) {
            return new AcCirculationElementsConstructor().getAcCirculationElements(id, this.mViewModel, this.mActivity.getResources().getStringArray(R.array.hvac_circulation_vui_label));
        }
        return null;
    }
}
