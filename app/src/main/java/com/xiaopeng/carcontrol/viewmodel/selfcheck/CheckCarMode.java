package com.xiaopeng.carcontrol.viewmodel.selfcheck;

import android.net.Uri;
import android.os.CountDownTimer;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckCategoryItem;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckDetailItem;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckErrorDetail;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckItemEcu;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckItemKey;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckUploadData;
import com.xiaopeng.carcontrol.bean.selfcheck.FaultCheckResult;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.GsonUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.NetworkUtil;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.util.UIUtils;
import com.xiaopeng.carcontrol.viewmodel.selfcheck.CheckCarMode;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.utils.SystemPropertyUtil;
import com.xiaopeng.speech.protocol.bean.recommend.RecommendBean;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class CheckCarMode implements ICheckCarMode {
    private static long CHECK_DELAY_TIME = 6000;
    private static final boolean NEED_UPLOAD = true;
    private ICheckCarListener mViewListener;
    protected final String TAG = getClass().getSimpleName();
    private boolean mIsChecking = false;
    private volatile boolean mIsCheckComplete = false;
    private volatile boolean mIsReceiveGreet = false;
    private int mCheckResultLevel = 0;
    protected List<CheckCategoryItem> mCheckList = new ArrayList();
    protected int mCheckedTotal = 0;
    private int mCheckedCount = 0;
    private int mCheckCategoryIndex = 0;
    private Disposable mDoCheckDisposable = null;
    private CountDownTimer mCheckTimer = null;
    private final int AVATER_EVENT_ID_EXPRESS = 10;
    private final int AVATER_SUB_ID_INVALID = -1;
    private Callback mUploadCallback = new Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.CheckCarMode.1
        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onSuccess(IResponse iResponse) {
            LogUtils.i(CheckCarMode.this.TAG, " self check upload onSuccess : code=" + iResponse.code() + " body=" + iResponse.body());
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onFailure(IResponse iResponse) {
            LogUtils.i(CheckCarMode.this.TAG, " self check upload fail code=" + iResponse.code() + " body=" + iResponse.body());
        }
    };

    protected boolean isNeedMock() {
        return true;
    }

    static /* synthetic */ int access$1008(CheckCarMode checkCarMode) {
        int i = checkCarMode.mCheckCategoryIndex;
        checkCarMode.mCheckCategoryIndex = i + 1;
        return i;
    }

    static /* synthetic */ int access$208(CheckCarMode checkCarMode) {
        int i = checkCarMode.mCheckedCount;
        checkCarMode.mCheckedCount = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        private static final CheckCarMode INSTANCE;

        private Holder() {
        }

        static {
            INSTANCE = BaseFeatureOption.getInstance().isSupportNewSelfCheckArch() ? new CheckCarMode() : new D2CheckCarMode();
        }
    }

    public static CheckCarMode getInstance() {
        return Holder.INSTANCE;
    }

    private void init() {
        LogUtils.i(this.TAG, " init");
        this.mIsChecking = true;
        this.mIsCheckComplete = false;
        generateData();
    }

    private void unInit() {
        LogUtils.i(this.TAG, " destroy");
        Disposable disposable = this.mDoCheckDisposable;
        if (disposable != null) {
            if (!disposable.isDisposed()) {
                this.mDoCheckDisposable.dispose();
            }
            this.mDoCheckDisposable = null;
        }
    }

    protected void generateData() {
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        this.mCheckList.clear();
        this.mCheckedTotal = 0;
        CheckCategoryItem build = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_elec)).ecu(CheckItemEcu.KEY_BCM).title(getString(R.string.selfcheck_category_ecu_elec)).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_HIGH_BEAM_FAIL, 1, getString(R.string.bcm_high_beam_fail))).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_LOW_BEAM_FAIL, 1, getString(R.string.bcm_low_beam_fail))).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_LEFT_TRUN_LAMP_FAIL, 1, getString(R.string.bcm_left_turn_lamp_fail))).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_RIGHT_TRUN_LAMP_FAIL, 1, getString(R.string.bcm_right_turn_lamp_fail))).addItem(new CheckDetailItem(CheckItemKey.ID_BCM_REARFLOG_FAIL, 1, getString(R.string.bcm_rearflog_fail))).build();
        this.mCheckList.add(build);
        this.mCheckedTotal += build.getCheckSize();
        if (carBaseConfig.isSupportAtl()) {
            CheckCategoryItem build2 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_elec)).ecu(CheckItemEcu.KEY_ATLS).title(getString(R.string.selfcheck_category_ecu_atls)).addItem(new CheckDetailItem(CheckItemKey.ATLS_ERROR, 1, getString(R.string.atls_error))).build();
            this.mCheckList.add(build2);
            this.mCheckedTotal += build2.getCheckSize();
        }
        if (CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_MSMD)) {
            CheckCategoryItem build3 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_elec)).ecu(CheckItemEcu.KEY_MSMD).title(getString(R.string.selfcheck_category_ecu_msmd)).addItem(new CheckDetailItem(CheckItemKey.ID_MSMD_ECU_ERROR, 1, getString(R.string.selfcheck_category_ecu_msmd))).build();
            if (CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1)) {
                build3.addItem(new CheckDetailItem(CheckItemKey.ID_MSMD_HEATING_ERROR, 1, getString(R.string.msmd_heating_error)));
            }
            this.mCheckList.add(build3);
            this.mCheckedTotal += build3.getCheckSize();
        }
        if (CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_MSMP)) {
            CheckCategoryItem build4 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_elec)).ecu(CheckItemEcu.KEY_MSMP).title(getString(R.string.selfcheck_category_ecu_msmp)).addItem(new CheckDetailItem(CheckItemKey.ID_MSMP_ECU_ERROR, 1, getString(R.string.msmp_ecu_error))).build();
            if (CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1)) {
                build4.addItem(new CheckDetailItem(CheckItemKey.ID_MSMP_HEATING_ERROR, 1, getString(R.string.msmp_heating_error)));
            }
            this.mCheckList.add(build4);
            this.mCheckedTotal += build4.getCheckSize();
        }
        CheckCategoryItem build5 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_elec)).ecu(CheckItemEcu.KEY_AVAS).title(getString(R.string.selfcheck_category_ecu_avas)).addItem(new CheckDetailItem(CheckItemKey.ID_AVAS_FAULT, 1, getString(R.string.avas_fault))).build();
        this.mCheckList.add(build5);
        this.mCheckedTotal += build5.getCheckSize();
        CheckCategoryItem build6 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_elec)).ecu(CheckItemEcu.KEY_TPMS).title(getString(R.string.selfcheck_category_ecu_tmps)).addItem(new CheckDetailItem("ID_TPMS_SYSFAULTWARN", 3, getString(R.string.tpms_sys_fault))).addItem(new CheckDetailItem(CheckItemKey.ID_TPMS_ABNORMAL_PR_WARN, 3, getString(R.string.tpms_abnormal_pr_warn))).build();
        this.mCheckList.add(build6);
        this.mCheckedTotal += build6.getCheckSize();
        CheckCategoryItem build7 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_elec)).ecu(CheckItemEcu.KEY_SRS).title(getString(R.string.selfcheck_category_ecu_srs)).addItem(new CheckDetailItem(CheckItemKey.ID_SRS_AIRBAG_FAULT, 3, getString(R.string.srs_airbag_fault))).build();
        this.mCheckList.add(build7);
        this.mCheckedTotal += build7.getCheckSize();
        CheckCategoryItem build8 = new CheckCategoryItem.Builder().category(UIUtils.getString(R.string.selfcheck_category_elec)).ecu(CheckItemEcu.KEY_DHC).title(getString(R.string.selfcheck_category_ecu_dhc)).addItem(new CheckDetailItem(CheckItemKey.ID_DHC_ERROR, 1, getString(R.string.dhc_error))).build();
        this.mCheckList.add(build8);
        this.mCheckedTotal += build8.getCheckSize();
        CheckCategoryItem build9 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_multelec)).ecu(CheckItemEcu.KEY_VCU).title(getString(R.string.selfcheck_category_multelec_vcu)).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_EVERR_LAMP_DSP, 3, getString(R.string.vcu_everr_lamp_dsp))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_GEAR_WARNING, 1, getString(R.string.vcu_gear_warning))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_EBS_ERROR, 1, getString(R.string.vcu_ebs_error))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_LIQUID_TEMP_HIGHT_ERROR, 1, getString(R.string.vcu_liquid_temp_hight_error))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_WATER_SENSOR_ERROR, 1, getString(R.string.vcu_water_sensor_error))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_CRUISE_ERROR, 1, getString(R.string.vcu_cruise_error))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_THERMO_RUN_AWAY_ERROR, 3, getString(R.string.vcu_thermo_run_away_error))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_POWER_LIMITATION, 3, getString(R.string.vcu_power_limitation))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_CHG_PORT_HOT, 3, getString(R.string.vcu_chg_port_hot))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_OBC_MSG_LOST, 1, getString(R.string.vcu_obc_msg_lost))).addItem(new CheckDetailItem(CheckItemKey.ID_VCU_DEAD_BATTERY, 3, getString(R.string.vcu_dead_battery))).build();
        this.mCheckList.add(build9);
        this.mCheckedTotal += build9.getCheckSize();
        if (CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_IPUR)) {
            CheckCategoryItem build10 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_multelec)).ecu(CheckItemEcu.KEY_IPUR).title(getString(R.string.selfcheck_category_multelec_ipur)).addItem(new CheckDetailItem(CheckItemKey.ID_IPUR_FAULT, 3, getString(R.string.inpur_fault))).build();
            this.mCheckList.add(build10);
            this.mCheckedTotal += build10.getCheckSize();
        }
        if (CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_IPUF)) {
            CheckCategoryItem build11 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_multelec)).ecu(CheckItemEcu.KEY_IPUF).title(getString(R.string.selfcheck_category_multelec_ipuf)).addItem(new CheckDetailItem(CheckItemKey.ID_IPUF_FAULT, 3, getString(R.string.inpuf_fault))).build();
            this.mCheckList.add(build11);
            this.mCheckedTotal += build11.getCheckSize();
        }
        CheckCategoryItem build12 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_chassis)).ecu(CheckItemEcu.KEY_ESP).title(getString(R.string.selfcheck_category_esp)).addItem(new CheckDetailItem(CheckItemKey.ID_ESP_APB_ERROR, 3, getString(R.string.esp_apb_error))).build();
        this.mCheckList.add(build12);
        this.mCheckedTotal += build12.getCheckSize();
        CheckCategoryItem build13 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_chassis)).ecu(CheckItemEcu.KEY_IBT).title(getString(R.string.selfcheck_category_ibt)).addItem(new CheckDetailItem(CheckItemKey.ID_IBT_FAULT, 3, getString(R.string.ibt_fault))).build();
        this.mCheckList.add(build13);
        this.mCheckedTotal += build13.getCheckSize();
        CheckCategoryItem build14 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_chassis)).ecu(CheckItemEcu.KEY_EPS).title(getString(R.string.selfcheck_category_eps)).addItem(new CheckDetailItem(CheckItemKey.ID_ESP_WARN_LAMP, 3, getString(R.string.esp_warn_lamp))).build();
        this.mCheckList.add(build14);
        this.mCheckedTotal += build14.getCheckSize();
        CheckCategoryItem build15 = new CheckCategoryItem.Builder().category(getString(R.string.selfcheck_category_chassis)).ecu(CheckItemEcu.KEY_CDC).title(getString(R.string.selfcheck_category_cdc)).addItem(new CheckDetailItem(CheckItemKey.ID_CDC_DATAUPLOAD_ST, 3, getString(R.string.cdc_dataupload_st))).build();
        this.mCheckList.add(build15);
        this.mCheckedTotal += build15.getCheckSize();
        for (CheckCategoryItem checkCategoryItem : this.mCheckList) {
            for (CheckDetailItem checkDetailItem : checkCategoryItem.getItems()) {
                checkDetailItem.setCategory(checkCategoryItem);
            }
        }
    }

    private void subscribeObserver() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.-$$Lambda$CheckCarMode$t3z5a2IpCMFP-57cPGKa4mbkfQk
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                CheckCarMode.this.lambda$subscribeObserver$0$CheckCarMode(observableEmitter);
            }
        }).subscribeOn(Schedulers.single()).observeOn(Schedulers.single()).doOnNext(new Consumer() { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.-$$Lambda$FhY43_fD0ipc2WmeBYLwRCgKVfc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ((CheckDetailItem) obj).startCheck();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new AnonymousClass2());
    }

    public /* synthetic */ void lambda$subscribeObserver$0$CheckCarMode(ObservableEmitter emitter) throws Exception {
        try {
            LogUtils.i(this.TAG, " doCheck ");
            if (this.mIsChecking) {
                doCheck(emitter);
            }
        } catch (Exception e) {
            ICheckCarListener iCheckCarListener = this.mViewListener;
            if (iCheckCarListener != null) {
                iCheckCarListener.onCheckError(e);
            }
            this.mDoCheckDisposable.dispose();
            this.mIsChecking = false;
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.viewmodel.selfcheck.CheckCarMode$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass2 implements Observer<CheckDetailItem> {
        AnonymousClass2() {
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable d) {
            CheckCarMode.this.mDoCheckDisposable = d;
        }

        @Override // io.reactivex.Observer
        public void onNext(CheckDetailItem s) {
            CheckCarMode.access$208(CheckCarMode.this);
            if (CheckCarMode.this.mViewListener != null) {
                CheckCarMode.this.mViewListener.onChecking(s);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable e) {
            e.printStackTrace();
            CheckCarMode.this.mIsChecking = false;
            if (CheckCarMode.this.mViewListener != null) {
                CheckCarMode.this.mViewListener.onCheckError(e);
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            LogUtils.i(CheckCarMode.this.TAG, " onComplete ", false);
            if (CheckCarMode.this.mIsChecking) {
                CheckCarMode.this.mIsChecking = false;
                CheckCarMode.this.mIsCheckComplete = true;
                ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.-$$Lambda$CheckCarMode$2$sWNsz066vZU0JwK4L7IUnkpbFeY
                    @Override // java.lang.Runnable
                    public final void run() {
                        CheckCarMode.AnonymousClass2.this.lambda$onComplete$0$CheckCarMode$2();
                    }
                }, 0L);
                CheckCarMode checkCarMode = CheckCarMode.this;
                checkCarMode.mCheckResultLevel = checkCarMode.calcResultLevel();
                CheckCarMode.this.uploadAIMsg();
                if (CheckCarMode.this.mViewListener != null) {
                    CheckCarMode.this.mViewListener.onCheckComplete();
                }
            }
        }

        public /* synthetic */ void lambda$onComplete$0$CheckCarMode$2() {
            CheckCarMode.this.uploadData();
        }
    }

    private void doCheck(ObservableEmitter<CheckDetailItem> emitter) {
        for (CheckCategoryItem checkCategoryItem : this.mCheckList) {
            for (CheckDetailItem checkDetailItem : checkCategoryItem.getItems()) {
                emitter.onNext(checkDetailItem);
            }
        }
        emitter.onComplete();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public void startCheck() {
        if (isChecking()) {
            LogUtils.i(this.TAG, " startCheck is checking");
            return;
        }
        init();
        startCheckTimer();
        if (BaseFeatureOption.getInstance().isSupportCallAvatar()) {
            callAvatar("com.xiaopeng.aiassistant", 10, -1);
        }
    }

    private void startCheckTimer() {
        if (this.mCheckList.size() <= 0) {
            LogUtils.i(this.TAG, " data is null");
            return;
        }
        CountDownTimer countDownTimer = this.mCheckTimer;
        if (countDownTimer == null) {
            long j = CHECK_DELAY_TIME;
            this.mCheckTimer = new CountDownTimer(j, j / this.mCheckList.size()) { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.CheckCarMode.3
                @Override // android.os.CountDownTimer
                public void onTick(long millisUntilFinished) {
                    if (CheckCarMode.this.mViewListener == null || !CheckCarMode.this.isNeedMock()) {
                        return;
                    }
                    LogUtils.d(CheckCarMode.this.TAG, "check countdown onTick progress=" + CheckCarMode.this.mCheckCategoryIndex + " millisUntilFinished=" + millisUntilFinished);
                    if (CheckCarMode.this.mCheckCategoryIndex < CheckCarMode.this.mCheckList.size()) {
                        CheckCarMode.this.mViewListener.onMockChecking(CheckCarMode.access$1008(CheckCarMode.this));
                    }
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    LogUtils.d(CheckCarMode.this.TAG, " mCheckTimer onFinish", false);
                    CheckCarMode.this.startCheckInternal();
                }
            };
        } else {
            countDownTimer.cancel();
        }
        this.mCheckCategoryIndex = 0;
        this.mCheckTimer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCheckInternal() {
        LogUtils.i(this.TAG, " startCheckInternal");
        this.mCheckedCount = 0;
        ICheckCarListener iCheckCarListener = this.mViewListener;
        if (iCheckCarListener != null) {
            iCheckCarListener.onCheckStart();
        }
        subscribeObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadData() {
        String generateResultJsonForCloud = generateResultJsonForCloud();
        if (!TextUtils.isEmpty(generateResultJsonForCloud)) {
            LogUtils.i(this.TAG, "uploadForBigData jsonStr=" + generateResultJsonForCloud);
            NetworkUtil.doPost(16, generateResultJsonForCloud, this.mUploadCallback);
            return;
        }
        LogUtils.w(this.TAG, " upload data is null");
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public void uploadForAIAssistant(final String jsonStr) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.-$$Lambda$CheckCarMode$wduYX8eBWBw3iNIiFdnlDfKyCjM
            @Override // java.lang.Runnable
            public final void run() {
                CheckCarMode.this.lambda$uploadForAIAssistant$1$CheckCarMode(jsonStr);
            }
        });
    }

    public /* synthetic */ void lambda$uploadForAIAssistant$1$CheckCarMode(final String jsonStr) {
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.aiassistant.AiassistantService").path("onCarCheckResult").appendQueryParameter(RecommendBean.SHOW_TIME_RESULT, jsonStr);
        try {
            LogUtils.i(this.TAG, "uploadForAIAssistant, result: " + ((String) ApiRouter.route(builder.build())), false);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(this.TAG, "uploadForAIAssistant() error.");
        }
    }

    private void callAvatar(final String pkg, final int eventId, final int subId) {
        LogUtils.i(this.TAG, "callAvatar: pkg = " + pkg + " eventId = " + eventId + " subId = " + subId, false);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.CheckCarMode.4
            @Override // java.lang.Runnable
            public void run() {
                Uri.Builder builder = new Uri.Builder();
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(VuiConstants.SCENE_PACKAGE_NAME, pkg);
                    jSONObject.put("eventId", String.valueOf(eventId));
                    int i = subId;
                    if (i != -1) {
                        jSONObject.put("subId", String.valueOf(i));
                    }
                    builder.authority("com.xiaopeng.aiavatarservice.APIRouterHelper").path("callAvatar").appendQueryParameter("param", jSONObject.toString());
                    ApiRouter.route(builder.build());
                    LogUtils.i(CheckCarMode.this.TAG, "callAvatar success: eventId =" + eventId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String generateResultJsonForCloud() {
        CheckUploadData checkUploadData = new CheckUploadData();
        checkUploadData.setTs(System.currentTimeMillis());
        checkUploadData.setStatus(hasIssue() ? 1 : 0);
        checkUploadData.setVin(SystemPropertyUtil.getVIN());
        checkUploadData.setCduId(SystemPropertyUtil.getHardwareId());
        for (CheckCategoryItem checkCategoryItem : this.mCheckList) {
            ArrayList<CheckErrorDetail> generatorResult = checkCategoryItem.generatorResult();
            if (generatorResult.size() > 0) {
                checkUploadData.addErrorDetailAll(generatorResult);
                generatorResult.clear();
            }
        }
        return GsonUtil.toJson(checkUploadData);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public boolean hasIssue() {
        return getFaultCount() > 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0010 A[Catch: all -> 0x0035, TryCatch #0 {, blocks: (B:5:0x000a, B:7:0x0010, B:8:0x001e, B:10:0x0024, B:12:0x0030, B:13:0x0033), top: B:18:0x000a }] */
    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getFaultCount() {
        /*
            r5 = this;
            java.util.List<com.xiaopeng.carcontrol.bean.selfcheck.CheckCategoryItem> r0 = r5.mCheckList
            java.util.Iterator r0 = r0.iterator()
            java.util.List<com.xiaopeng.carcontrol.bean.selfcheck.CheckCategoryItem> r1 = r5.mCheckList
            monitor-enter(r1)
            r2 = 0
        La:
            boolean r3 = r0.hasNext()     // Catch: java.lang.Throwable -> L35
            if (r3 == 0) goto L33
            java.lang.Object r3 = r0.next()     // Catch: java.lang.Throwable -> L35
            com.xiaopeng.carcontrol.bean.selfcheck.CheckCategoryItem r3 = (com.xiaopeng.carcontrol.bean.selfcheck.CheckCategoryItem) r3     // Catch: java.lang.Throwable -> L35
            java.util.List r3 = r3.getItems()     // Catch: java.lang.Throwable -> L35
            java.util.Iterator r3 = r3.iterator()     // Catch: java.lang.Throwable -> L35
        L1e:
            boolean r4 = r3.hasNext()     // Catch: java.lang.Throwable -> L35
            if (r4 == 0) goto La
            java.lang.Object r4 = r3.next()     // Catch: java.lang.Throwable -> L35
            com.xiaopeng.carcontrol.bean.selfcheck.CheckDetailItem r4 = (com.xiaopeng.carcontrol.bean.selfcheck.CheckDetailItem) r4     // Catch: java.lang.Throwable -> L35
            boolean r4 = r4.hasIssue()     // Catch: java.lang.Throwable -> L35
            if (r4 == 0) goto L1e
            int r2 = r2 + 1
            goto L1e
        L33:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
            return r2
        L35:
            r0 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.selfcheck.CheckCarMode.getFaultCount():int");
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public String getFaultStr() {
        ArrayList arrayList = new ArrayList();
        for (CheckCategoryItem checkCategoryItem : this.mCheckList) {
            for (CheckDetailItem checkDetailItem : checkCategoryItem.getItems()) {
                if (checkDetailItem.hasIssue()) {
                    arrayList.add(checkDetailItem.getTitle());
                }
            }
        }
        String str = "";
        if (arrayList.size() != 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                String str2 = str + ((String) arrayList.get(i));
                if (i == arrayList.size() - 1) {
                    str = str2 + "。";
                } else {
                    str = str2 + ";";
                }
            }
        }
        return str;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public String getTTs() {
        int faultCount = getFaultCount();
        if (faultCount != 1) {
            return faultCount > 1 ? ResUtils.getString(R.string.selfcheck_tts_multi) : "";
        }
        CheckDetailItem firstFaultItem = getFirstFaultItem();
        if (firstFaultItem == null) {
            LogUtils.e(this.TAG, " ERROR: fault num do not match falut items");
            return "";
        }
        return ResUtils.getString(R.string.selfcheck_tts_one, firstFaultItem.getTitle());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public void receiveGreet() {
        if (BaseFeatureOption.getInstance().isSupportNeedReceiveGreet()) {
            LogUtils.d(this.TAG, "Receive Greet,mIsReceiveGreet : " + this.mIsReceiveGreet);
            if (this.mIsReceiveGreet) {
                return;
            }
            this.mIsReceiveGreet = true;
            uploadAIMsg();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadAIMsg() {
        LogUtils.d(this.TAG, "UploadAIMsg, mIsReceiveGreet: " + this.mIsReceiveGreet + ", mIsCheckComplete: " + this.mIsCheckComplete, false);
        synchronized (this.mCheckList) {
            if ((this.mIsReceiveGreet && this.mIsCheckComplete) || !BaseFeatureOption.getInstance().isSupportNeedReceiveGreet()) {
                String generateJsonForUi = generateJsonForUi(1, true);
                LogUtils.i(this.TAG, "Check_send to infoflow|AIAssistanat extras=" + generateJsonForUi, false);
                uploadForAIAssistant(generateJsonForUi);
                this.mIsReceiveGreet = false;
                this.mIsCheckComplete = false;
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public void stopCheck() {
        ICheckCarListener iCheckCarListener = this.mViewListener;
        if (iCheckCarListener != null) {
            iCheckCarListener.onCheckStop();
        }
        if (this.mIsChecking) {
            CountDownTimer countDownTimer = this.mCheckTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                this.mCheckTimer = null;
            }
            LogUtils.i(this.TAG, "stopCheck clear mCheckRunnable...");
            unInit();
            this.mIsChecking = false;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public void setViewListener(ICheckCarListener viewListener) {
        this.mViewListener = viewListener;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public int getCheckSize() {
        return this.mCheckedTotal;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public int getCategoryItemSize() {
        return this.mCheckList.size();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public int getCheckedCount() {
        return this.mCheckedCount;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public boolean isSeverity() {
        return this.mCheckResultLevel == 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int calcResultLevel() {
        int i = 0;
        for (CheckCategoryItem checkCategoryItem : this.mCheckList) {
            if (checkCategoryItem.getErrorLevel() > i) {
                i = checkCategoryItem.getErrorLevel();
            }
        }
        return i;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public String generateJsonForUi(int status, boolean needDetail) {
        String generateExtrasInternal;
        synchronized (this.mCheckList) {
            generateExtrasInternal = generateExtrasInternal(status, getCheckSize(), getCheckedCount(), getFaultCount(), needDetail);
        }
        return generateExtrasInternal;
    }

    private String generateExtrasInternal(int status, int totalCount, int checkedCount, int faultCount, boolean needDetail) {
        FaultCheckResult faultCheckResult = new FaultCheckResult();
        faultCheckResult.setStatus(status);
        faultCheckResult.setTotalCount(totalCount);
        faultCheckResult.setCheckedCount(checkedCount);
        faultCheckResult.setFaultCount(faultCount);
        if (needDetail) {
            ArrayList<CheckDetailItem> arrayList = new ArrayList();
            for (CheckCategoryItem checkCategoryItem : this.mCheckList) {
                for (CheckDetailItem checkDetailItem : checkCategoryItem.getItems()) {
                    if (checkDetailItem.hasIssue()) {
                        arrayList.add(checkDetailItem);
                    }
                }
            }
            Collections.sort(arrayList, new Comparator() { // from class: com.xiaopeng.carcontrol.viewmodel.selfcheck.-$$Lambda$CheckCarMode$UqXX_sxr8uh-Ow5GdVJK5hbY8II
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return CheckCarMode.lambda$generateExtrasInternal$2((CheckDetailItem) obj, (CheckDetailItem) obj2);
                }
            });
            for (CheckDetailItem checkDetailItem2 : arrayList) {
                faultCheckResult.addDetailResult(checkDetailItem2.getTitle(), SelfCheckUtil.level2Str(checkDetailItem2.hasIssue() ? checkDetailItem2.getLevel() : 0), checkDetailItem2.getLevel());
            }
            arrayList.clear();
        }
        faultCheckResult.setTTS(getTTs());
        return GsonUtil.toJson(faultCheckResult);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ int lambda$generateExtrasInternal$2(CheckDetailItem item1, CheckDetailItem item2) {
        return item2.getLevel() - item1.getLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public String generateMockExtrasForInfoflow(int checkedCount) {
        return generateExtrasInternal(0, getCategoryItemSize(), checkedCount, getFaultCount(), false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public boolean isChecking() {
        return this.mIsChecking && !this.mIsCheckComplete;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public boolean isCheckComplete() {
        return this.mIsCheckComplete;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public CheckDetailItem getFirstFaultItem() {
        for (CheckCategoryItem checkCategoryItem : this.mCheckList) {
            for (CheckDetailItem checkDetailItem : checkCategoryItem.getItems()) {
                if (checkDetailItem.hasIssue()) {
                    return checkDetailItem;
                }
            }
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarMode
    public CheckCategoryItem getCategoryItem(int index) {
        if (index < this.mCheckList.size()) {
            return this.mCheckList.get(index);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getString(int resid) {
        return UIUtils.getString(resid);
    }
}
