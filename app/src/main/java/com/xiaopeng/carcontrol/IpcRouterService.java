package com.xiaopeng.carcontrol;

import android.net.Uri;
import android.os.RemoteException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.helper.PushedMessageHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatSmartControl;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisSmartControl;
import com.xiaopeng.carcontrol.viewmodel.scu.XpilotSmartControl;
import com.xiaopeng.carcontrol.viewmodel.service.IDiagnosticCallback;
import com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel;
import com.xiaopeng.carcontrol.viewmodel.service.SelfCheckViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuSmartControl;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xiaopeng.libconfig.ipc.bean.MessageCenterBean;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class IpcRouterService implements IServicePublisher {
    private static final String AUTHORITY_AI = "com.xiaopeng.aiassistant.IpcRouterService";
    private static final String AUTHORITY_DIAGNOSTIC = "com.xiaopeng.diagnostic.DiagnoseService";
    private static final String AUTHORITY_NAVI_MRR = "com.xiaopeng.napa.MillimeterWaveRadarService";
    private static final String BUNDLE = "bundle";
    private static final String CODE = "faultCode";
    private static final String ID = "id";
    private static final String PATH = "onReceiverData";
    private static final String PATH_ALL_FAULT = "getAllFaultCode";
    private static final String PATH_FAULT_DETAIL = "getFaultCode";
    private static final String PATH_MOBILE_PHONE = "getMobilePhone";
    private static final String PATH_NAVI_MRR = "getMillimeterWaveRadarStatus";
    private static final String PATH_RESERVE_DIAGNOSE = "reserveRemoteDiagnose";
    private static final String PHONE = "mobilePhone";
    private static final String STRING_MSG = "string_msg";
    private static final String TAG = "IpcRouterService";
    private static final String TYPE = "businessType";
    private static final Map<Integer, ICallback> sNotificationCallbacks = new ConcurrentHashMap();
    private SelfCheckViewModel selfCheckViewModel;

    /* loaded from: classes.dex */
    public interface ICallback {
        void onCallback(String content);
    }

    public static void sendMessageToMessageCenter(int scene, MessageCenterBean bean, ICallback callback) {
        if (App.isMainProcess()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("string_msg", new Gson().toJson(bean));
            String jsonObject2 = jsonObject.toString();
            LogUtils.d(TAG, "sendInfoFlowMsg=======bundle:" + jsonObject2);
            try {
                ApiRouter.route(new Uri.Builder().authority(AUTHORITY_AI).path(PATH).appendQueryParameter("id", String.valueOf((int) IpcConfig.MessageCenterConfig.IPC_ID_APP_PUSH_MESSAGE)).appendQueryParameter(BUNDLE, jsonObject2).build());
                if (callback != null) {
                    sNotificationCallbacks.put(Integer.valueOf(scene), callback);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean getMrrGeoFenceStatus() {
        /*
            java.lang.String r0 = "IpcRouterService"
            android.net.Uri$Builder r1 = new android.net.Uri$Builder
            r1.<init>()
            java.lang.String r2 = "com.xiaopeng.napa.MillimeterWaveRadarService"
            android.net.Uri$Builder r1 = r1.authority(r2)
            java.lang.String r2 = "getMillimeterWaveRadarStatus"
            android.net.Uri$Builder r1 = r1.path(r2)
            android.net.Uri r1 = r1.build()
            r2 = 0
            r3 = 0
            java.lang.Object r1 = com.xiaopeng.lib.apirouter.ApiRouter.route(r1)     // Catch: android.os.RemoteException -> L38
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch: android.os.RemoteException -> L38
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L36
            r3.<init>()     // Catch: android.os.RemoteException -> L36
            java.lang.String r4 = "getMrrGeoFenceStatus: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: android.os.RemoteException -> L36
            java.lang.StringBuilder r3 = r3.append(r1)     // Catch: android.os.RemoteException -> L36
            java.lang.String r3 = r3.toString()     // Catch: android.os.RemoteException -> L36
            com.xiaopeng.carcontrol.util.LogUtils.i(r0, r3, r2)     // Catch: android.os.RemoteException -> L36
            goto L56
        L36:
            r3 = move-exception
            goto L3c
        L38:
            r1 = move-exception
            r6 = r3
            r3 = r1
            r1 = r6
        L3c:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "getMrrGeoFenceStatus failed: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r3 = r3.getMessage()
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.String r3 = r3.toString()
            com.xiaopeng.carcontrol.util.LogUtils.i(r0, r3, r2)
        L56:
            if (r1 == 0) goto L5c
            boolean r2 = r1.booleanValue()
        L5c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.IpcRouterService.getMrrGeoFenceStatus():boolean");
    }

    public static void getAllFaultCode(IDiagnosticCallback callback) {
        try {
            String str = (String) ApiRouter.route(new Uri.Builder().authority(AUTHORITY_DIAGNOSTIC).path(PATH_ALL_FAULT).build());
            callback.onSuccess(str);
            LogUtils.i(TAG, "getAllFaultCode: " + str, false);
        } catch (RemoteException e) {
            callback.onFailed("failed");
            LogUtils.i(TAG, "getAllFaultCode failed: " + e.getMessage(), false);
        }
    }

    public static void getFaultCode(int businessType, String faultCode, IDiagnosticCallback callback) {
        try {
            String str = (String) ApiRouter.route(new Uri.Builder().authority(AUTHORITY_DIAGNOSTIC).path(PATH_FAULT_DETAIL).appendQueryParameter(TYPE, String.valueOf(businessType)).appendQueryParameter(CODE, faultCode).build());
            callback.onSuccess(str);
            LogUtils.i(TAG, "getFaultCode: " + str, false);
        } catch (RemoteException e) {
            callback.onFailed("failed");
            LogUtils.i(TAG, "getFaultCode failed: " + e.getMessage(), false);
        }
    }

    public static void getMobilePhone(IDiagnosticCallback callback) {
        try {
            String str = (String) ApiRouter.route(new Uri.Builder().authority(AUTHORITY_DIAGNOSTIC).path(PATH_MOBILE_PHONE).build());
            callback.onSuccess(str);
            LogUtils.i(TAG, "getMobilePhone: " + str, false);
        } catch (RemoteException e) {
            callback.onFailed("failed");
            LogUtils.i(TAG, "getMobilePhone failed: " + e.getMessage(), false);
        }
    }

    public static void reserveRemoteDiagnose(String mobilePhone, IDiagnosticCallback callback) {
        try {
            boolean booleanValue = ((Boolean) ApiRouter.route(new Uri.Builder().authority(AUTHORITY_DIAGNOSTIC).path(PATH_RESERVE_DIAGNOSE).appendQueryParameter(PHONE, mobilePhone).build())).booleanValue();
            callback.onSuccess(String.valueOf(booleanValue));
            LogUtils.i(TAG, "reserveRemoteDiagnose: " + booleanValue, false);
        } catch (RemoteException e) {
            callback.onFailed(OOBEEvent.STRING_FALSE);
            LogUtils.i(TAG, "reserveRemoteDiagnose failed: " + e.getMessage(), false);
        }
    }

    public void onReceiverData(int id, String bundle) {
        if (App.isMainProcess()) {
            LogUtils.d(TAG, "onReceiverData=======id:" + id + ", bundle:" + bundle);
            if (id == 10010) {
                PushedMessageHelper.receivedPushMessage(bundle);
            } else if (id == 10011) {
                try {
                    JSONObject jSONObject = new JSONObject(bundle);
                    if (bundle.contains(TYPE)) {
                        String string = jSONObject.getString("string_msg");
                        LogUtils.d(TAG, "FaultResult" + string, false);
                        showFaultView(string);
                    } else {
                        ICallback remove = sNotificationCallbacks.remove(Integer.valueOf(Integer.parseInt(jSONObject.getString("string_msg"))));
                        if (remove != null) {
                            remove.onCallback(null);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (id == 16201) {
                onXSportResumeDriveMode(bundle);
            } else if (id == 16202) {
                onXSportSaveDriveMode(bundle);
            } else if (id != 16301) {
                switch (id) {
                    case GlobalConstant.CarControlConfig.IPC_NAVI_SMART_AS_SAVE_RESULT /* 16101 */:
                        onSmartAsSaveResult(bundle);
                        return;
                    case GlobalConstant.CarControlConfig.IPC_NAVI_SMART_AS_MOVE_UP_CMD /* 16102 */:
                        onSmartAsMoveUpCmd(bundle);
                        return;
                    case GlobalConstant.CarControlConfig.IPC_NAVI_SMART_AS_MOVE_DOWN_CMD /* 16103 */:
                        onSmartAsMoveDownCmd(bundle);
                        return;
                    case GlobalConstant.CarControlConfig.IPC_NAVI_ENTER_TELESCOPE_RANGE /* 16104 */:
                        XpilotSmartControl.getInstance().enterTelescopeRange();
                        return;
                    case GlobalConstant.CarControlConfig.IPC_NAVI_EXIT_TELESCOPE_RANGE /* 16105 */:
                        XpilotSmartControl.getInstance().exitTelescopeRange();
                        return;
                    default:
                        return;
                }
            } else {
                onRestorePsnSeat();
            }
        }
    }

    public String onGetData(int id, String bundle) {
        if (App.isMainProcess()) {
            LogUtils.d(TAG, "onGetData=======id:" + id + ", bundle:" + bundle);
            if (id != 16302) {
                return null;
            }
            return isPsnSeatEqualMemory();
        }
        return null;
    }

    private int parseBundle(String bundle) {
        try {
            return Integer.parseInt(new JSONObject(bundle).getString("string_msg"));
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void onSmartAsSaveResult(String bundle) {
        ChassisSmartControl.getInstance().onSmartAsSaveResult(bundle);
    }

    private void onSmartAsMoveUpCmd(String bundle) {
        ChassisSmartControl.getInstance().onSmartAsMoveUp(bundle);
    }

    private void onSmartAsMoveDownCmd(String bundle) {
        ChassisSmartControl.getInstance().onSmartAsMoveDown(bundle);
    }

    private void onXSportResumeDriveMode(String bundle) {
        VcuSmartControl.getInstance().onXSportResumeDriveMode(bundle);
    }

    private void onXSportSaveDriveMode(String bundle) {
        VcuSmartControl.getInstance().onXSportSaveDriveMode(bundle);
    }

    private void onRestorePsnSeat() {
        SeatSmartControl.getInstance().restorePsnSeat();
    }

    private String isPsnSeatEqualMemory() {
        return String.valueOf(SeatSmartControl.getInstance().isPsnSeatEqualMemory() ? 1 : 0);
    }

    private void showFaultView(String code) {
        try {
            JSONObject jSONObject = new JSONObject(code);
            int parseInt = Integer.parseInt(jSONObject.getString(VuiConstants.ELEMENT_TYPE));
            if (parseInt == 1) {
                showFaultDetailView(Integer.parseInt(jSONObject.getString(TYPE)), jSONObject.getString(CODE));
            } else if (parseInt == 2) {
                showAllFaultView();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showAllFaultView() {
        this.selfCheckViewModel = (SelfCheckViewModel) ViewModelManager.getInstance().getViewModelImpl(ISelfCheckViewModel.class);
        getAllFaultCode(new IDiagnosticCallback() { // from class: com.xiaopeng.carcontrol.IpcRouterService.1
            @Override // com.xiaopeng.carcontrol.viewmodel.service.IDiagnosticCallback
            public void onSuccess(String result) {
                LogUtils.d(IpcRouterService.TAG, "OnSuccess Result" + result, false);
                IpcRouterService.this.selfCheckViewModel.handleAllFaultData(result);
            }

            @Override // com.xiaopeng.carcontrol.viewmodel.service.IDiagnosticCallback
            public void onFailed(String result) {
                LogUtils.d(IpcRouterService.TAG, "OnFailed Result" + result, false);
                IpcRouterService.this.selfCheckViewModel.handleAllFaultData(result);
            }
        });
    }

    private void showFaultDetailView(int businessType, String faultCode) {
        this.selfCheckViewModel = (SelfCheckViewModel) ViewModelManager.getInstance().getViewModelImpl(ISelfCheckViewModel.class);
        getFaultCode(businessType, faultCode, new IDiagnosticCallback() { // from class: com.xiaopeng.carcontrol.IpcRouterService.2
            @Override // com.xiaopeng.carcontrol.viewmodel.service.IDiagnosticCallback
            public void onSuccess(String result) {
                LogUtils.d(IpcRouterService.TAG, "OnSuccess Detail Result" + result, false);
                IpcRouterService.this.selfCheckViewModel.handleFaultDetailData(result);
            }

            @Override // com.xiaopeng.carcontrol.viewmodel.service.IDiagnosticCallback
            public void onFailed(String result) {
                LogUtils.d(IpcRouterService.TAG, "OnFailed Detail Result" + result, false);
                IpcRouterService.this.selfCheckViewModel.handleFaultDetailData(result);
            }
        });
    }
}
