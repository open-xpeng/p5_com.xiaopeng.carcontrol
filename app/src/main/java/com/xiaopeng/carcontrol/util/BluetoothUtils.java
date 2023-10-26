package com.xiaopeng.carcontrol.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import java.util.Set;

/* loaded from: classes2.dex */
public class BluetoothUtils {
    private static final String TAG = "BluetoothUtils";

    public static String getBtConnectedDeviceName() {
        String str;
        Set<BluetoothDevice> bondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (bondedDevices != null) {
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
                if (bluetoothDevice != null && bluetoothDevice.isConnected()) {
                    str = bluetoothDevice.getName();
                    break;
                }
            }
        }
        str = null;
        LogUtils.d(TAG, "getBtConnectedDeviceName: " + str, false);
        return str;
    }
}
