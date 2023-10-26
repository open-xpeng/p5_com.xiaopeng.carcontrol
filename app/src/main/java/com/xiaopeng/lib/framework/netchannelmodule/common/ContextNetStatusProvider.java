package com.xiaopeng.lib.framework.netchannelmodule.common;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.NetUtils;

/* loaded from: classes2.dex */
public class ContextNetStatusProvider extends ContentProvider {
    public static final String TAG = "NetChannel-MqttChannel";
    private static Context mContext;
    private static MyPhoneStateListener mPhoneStateListener;

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public static Context getApplicationContext() {
        return mContext;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            mContext = context.getApplicationContext();
            if (mPhoneStateListener == null) {
                mPhoneStateListener = new MyPhoneStateListener();
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    telephonyManager.listen(mPhoneStateListener, 256);
                }
            }
        }
        SharePrefsANRFix.fix();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class MyPhoneStateListener extends PhoneStateListener {
        public int signalStrengthValue;

        private MyPhoneStateListener() {
        }

        @Override // android.telephony.PhoneStateListener
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            if (signalStrength.isGsm()) {
                if (signalStrength.getGsmSignalStrength() != 99) {
                    this.signalStrengthValue = (signalStrength.getGsmSignalStrength() * 2) - 113;
                    return;
                } else {
                    this.signalStrengthValue = signalStrength.getGsmSignalStrength();
                    return;
                }
            }
            this.signalStrengthValue = signalStrength.getCdmaDbm();
        }
    }

    private static int getWifiRssi(Context context) {
        WifiInfo connectionInfo;
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
            return 0;
        }
        return connectionInfo.getRssi();
    }

    public static int getNetStrength(int i) {
        Context context = mContext;
        if (context == null) {
            return 0;
        }
        if (i == 10) {
            int wifiRssi = getWifiRssi(context);
            LogUtils.d(TAG, "current net type is wifi, rssi is " + wifiRssi);
            return wifiRssi;
        }
        MyPhoneStateListener myPhoneStateListener = mPhoneStateListener;
        if (myPhoneStateListener != null) {
            int i2 = myPhoneStateListener.signalStrengthValue;
            LogUtils.d(TAG, "current net type is mobile, dbm is " + i2);
            return i2;
        }
        LogUtils.d(TAG, "not init or not wifi, signal strength is 0");
        return 0;
    }

    public static int getNetType() {
        Context context = mContext;
        if (context != null) {
            return NetUtils.getNetworkType(context);
        }
        return 0;
    }

    public static int getNetStrength() {
        return getNetStrength(getNetType());
    }
}
