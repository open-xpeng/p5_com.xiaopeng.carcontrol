package com.xiaopeng.lib.framework.aiassistantmodule.sensor;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import com.xiaopeng.lib.framework.ipcmodule.IpcModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.IContextSensor;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.ISensorCallback;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.ISensorListener;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ContextSensor implements IContextSensor {
    public static final String CMD_REQEST_SENSOR_VALUE = "request_sensor_value";
    public static final String CMD_RESPONSE_SENSOR_VALUE = "response_sensor_value";
    public static final String CMD_SENSOR_CHANGE = "sensor_change";
    public static final String CMD_SUBSCRIBE = "subscribe";
    public static final String CMD_UNSUBSCRIBE = "unsubscribe";
    public static final String DATA_FIELD = "field";
    public static final String DATA_PACKAGE = "package";
    public static final String DATA_SENSOR = "sensor";
    public static final String DATA_VALUE = "value";
    public static final String KEY_CMD = "cmd";
    public static final String KEY_DATA = "data";
    private static final String TAG = "Sensor:ContextSensor";
    private Context mContext;
    private IIpcService mIIpcService;
    private ArrayMap<String, ISensorCallback> mSensorCallbackArrayMap;
    private ArrayMap<String, ISensorListener> mSensorListenerArrayMap;

    public ContextSensor(Context context) {
        Module.register(IpcModuleEntry.class, new IpcModuleEntry(context));
        this.mIIpcService = (IIpcService) Module.get(IpcModuleEntry.class).get(IIpcService.class);
        this.mContext = context;
        this.mSensorListenerArrayMap = new ArrayMap<>(20);
        this.mSensorCallbackArrayMap = new ArrayMap<>(4);
        EventBus.getDefault().register(this);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.IContextSensor
    public void subscribe(String str, ISensorListener iSensorListener) {
        LogUtils.i(TAG, "subscribe sensor = %s", str);
        this.mSensorListenerArrayMap.put(str, iSensorListener);
        sendIPCMsgToAIAssistant(str, null, "subscribe");
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.IContextSensor
    public void unSubscribe(String str, ISensorListener iSensorListener) {
        LogUtils.i(TAG, "unsubscribe sensor = %s", str);
        if (this.mSensorListenerArrayMap.containsKey(str)) {
            this.mSensorListenerArrayMap.get(str);
        }
        sendIPCMsgToAIAssistant(str, null, "unsubscribe");
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.IContextSensor
    public void getSensorValue(String str, String str2, ISensorCallback iSensorCallback) {
        LogUtils.i(TAG, "requestSensorValue sensor = %s, field = %s", str, str2);
        this.mSensorCallbackArrayMap.put(str + str2, iSensorCallback);
        sendIPCMsgToAIAssistant(str, str2, CMD_REQEST_SENSOR_VALUE);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(IIpcService.IpcMessageEvent ipcMessageEvent) {
        if (ipcMessageEvent.getMsgID() == 11020) {
            String string = ipcMessageEvent.getPayloadData().getString(IpcConfig.IPCKey.STRING_MSG);
            LogUtils.i(TAG, "receive ipc msg : id = %d, data = %s", Integer.valueOf((int) IpcConfig.AIAssistantConfig.IPC_CONTEXT_SENSOR), string);
            try {
                JSONObject jSONObject = new JSONObject(string);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String optString = jSONObject2.optString(DATA_SENSOR);
                String optString2 = jSONObject2.optString(DATA_FIELD);
                String string2 = jSONObject2.getString("value");
                if (CMD_SENSOR_CHANGE.equals(jSONObject.optString(KEY_CMD))) {
                    onSensorChange(optString, optString2, string2);
                } else if (CMD_RESPONSE_SENSOR_VALUE.equals(jSONObject.optString(KEY_CMD))) {
                    responseSensorValue(optString, optString2, string2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onSensorChange(String str, String str2, String str3) {
        ISensorListener iSensorListener = this.mSensorListenerArrayMap.get(str);
        if (iSensorListener != null) {
            LogUtils.i(TAG, "onSensorChange class = %s, field = %s, value = %s", str, str2, str3);
            iSensorListener.onSensorChange(str, str2, str3);
        }
    }

    private void responseSensorValue(String str, String str2, String str3) {
        if (this.mSensorCallbackArrayMap.containsKey(str + str2)) {
            this.mSensorCallbackArrayMap.get(str + str2).onSensorReponse(str, str2, str3);
            this.mSensorCallbackArrayMap.remove(str + str2);
        }
    }

    private void sendIPCMsgToAIAssistant(String str, String str2, String str3) {
        try {
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(KEY_CMD, str3);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(DATA_PACKAGE, this.mContext.getPackageName());
            jSONObject2.put(DATA_SENSOR, str);
            jSONObject2.put(DATA_FIELD, str2);
            jSONObject.put("data", jSONObject2);
            bundle.putString(IpcConfig.IPCKey.STRING_MSG, jSONObject.toString());
            LogUtils.i(TAG, "send Sensor msg: %s", jSONObject.toString());
            this.mIIpcService.sendData(IpcConfig.AIAssistantConfig.IPC_CONTEXT_SENSOR, bundle, "com.xiaopeng.aiassistant");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
