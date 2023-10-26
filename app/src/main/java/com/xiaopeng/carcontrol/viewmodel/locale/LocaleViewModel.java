package com.xiaopeng.carcontrol.viewmodel.locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.Locale;

/* loaded from: classes2.dex */
public class LocaleViewModel implements ILocaleViewModel {
    private static final String TAG = "LocaleViewModel";
    private String[] mCurrentCalls;
    private String mCurrentCountry;
    private final BroadcastReceiver mLocaleChangedReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.carcontrol.viewmodel.locale.LocaleViewModel.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                LogUtils.i(LocaleViewModel.TAG, "onReceive: " + intent.getAction(), false);
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    LocaleViewModel.this.handleLocaleChanged();
                }
            }
        }
    };
    private final MutableLiveData<String[]> mRescueCallsData = new MutableLiveData<>();
    private final MutableLiveData<String> mCountryData = new MutableLiveData<>();

    public LocaleViewModel() {
        registerLocaleChangedReceiver();
        handleLocaleChanged();
    }

    private void registerLocaleChangedReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        try {
            App.getInstance().registerReceiver(this.mLocaleChangedReceiver, intentFilter);
        } catch (Exception e) {
            LogUtils.w(TAG, e.getMessage(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLocaleChanged() {
        String country = Locale.getDefault().getCountry();
        this.mCurrentCountry = country;
        country.hashCode();
        char c = 65535;
        switch (country.hashCode()) {
            case 2155:
                if (country.equals(GlobalConstant.COUNTRY.COUNTRY_CN)) {
                    c = 0;
                    break;
                }
                break;
            case 2183:
                if (country.equals(GlobalConstant.COUNTRY.COUNTRY_DK)) {
                    c = 1;
                    break;
                }
                break;
            case 2494:
                if (country.equals(GlobalConstant.COUNTRY.COUNTRY_NL)) {
                    c = 2;
                    break;
                }
                break;
            case 2497:
                if (country.equals(GlobalConstant.COUNTRY.COUNTRY_NO)) {
                    c = 3;
                    break;
                }
                break;
            case 2642:
                if (country.equals(GlobalConstant.COUNTRY.COUNTRY_SE)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mCurrentCalls = GlobalConstant.CALLS.RESCUE_CALLS_CN;
                break;
            case 1:
                this.mCurrentCalls = GlobalConstant.CALLS.RESCUE_CALLS_DK;
                break;
            case 2:
                this.mCurrentCalls = GlobalConstant.CALLS.RESCUE_CALLS_NL;
                break;
            case 3:
                this.mCurrentCalls = GlobalConstant.CALLS.RESCUE_CALLS_NO;
                break;
            case 4:
                this.mCurrentCalls = GlobalConstant.CALLS.RESCUE_CALLS_SE;
                break;
            default:
                this.mCurrentCalls = null;
                break;
        }
        this.mCountryData.postValue(this.mCurrentCountry);
        this.mRescueCallsData.postValue(this.mCurrentCalls);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.locale.ILocaleViewModel
    public String getCountry() {
        return this.mCurrentCountry;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.locale.ILocaleViewModel
    public String[] getRescueCalls() {
        return this.mCurrentCalls;
    }

    public LiveData<String> getCountryData() {
        return this.mCountryData;
    }

    public LiveData<String[]> getRecuseCallsData() {
        return this.mRescueCallsData;
    }
}
