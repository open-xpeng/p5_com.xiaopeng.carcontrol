package com.xiaopeng.speech.protocol.node.phone;

import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.actorapi.ResultActor;
import com.xiaopeng.speech.actorapi.SupportActor;
import com.xiaopeng.speech.common.bean.SliceData;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.jarvisproto.WakeupResult;
import com.xiaopeng.speech.protocol.DeviceInfoKey;
import com.xiaopeng.speech.protocol.VocabDefine;
import com.xiaopeng.speech.protocol.event.ContextEvent;
import com.xiaopeng.speech.protocol.event.PhoneEvent;
import com.xiaopeng.speech.protocol.node.phone.bean.CallLogs;
import com.xiaopeng.speech.protocol.node.phone.bean.Contact;
import com.xiaopeng.speech.protocol.node.phone.bean.PhoneBean;
import com.xiaopeng.speech.protocol.utils.CarTypeUtils;
import com.xiaopeng.speech.protocol.utils.DeflaterUtils;
import com.xiaopeng.speech.proxy.HotwordEngineProxy;
import com.xiaopeng.speech.speechwidget.ContentWidget;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import com.xiaopeng.speech.speechwidget.SupportWidget;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class NewPhoneNode extends SpeechNode<PhoneListener> {
    private static final int CHUNKSIZE = 262144;
    private static final String COMMAND_SPLIT = "#";
    public static final String INTENT_IN_COMING_PHONE = "来电话";
    private static final long SET_EXIT_CALL_TIMEOUT = 150;
    public static final String SKILL_NAME = "离线来电话";
    public static final String TASK_PHONE = "来电话";
    private final String SLOT_ENABLE_TTS;
    private volatile String deviceId;
    private volatile String duiWidget;
    private IBinder mBinder;
    private List<PhoneBean> phoneBeanList;
    private volatile int syncResultCode;

    private NewPhoneNode() {
        this.SLOT_ENABLE_TTS = "enable_tts";
        this.mBinder = new Binder();
        this.syncResultCode = 0;
        SpeechClient.instance().getSpeechState().setCanExitFlag(true);
    }

    /* loaded from: classes2.dex */
    private static class Holder {
        private static final NewPhoneNode Instance = new NewPhoneNode();

        private Holder() {
        }
    }

    public static final NewPhoneNode instance() {
        return Holder.Instance;
    }

    public void syncContacts(List<Contact> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        boolean isOverseasCarType = CarTypeUtils.isOverseasCarType();
        for (Contact contact : list) {
            if (!isOverseasCarType) {
                contact.setName(contact.getName().replaceAll("[0-9a-zA-Z]", ""));
            }
            arrayList.add(contact.getName());
        }
        SpeechClient.instance().getAgent().updateVocab(VocabDefine.CONTACT, (String[]) arrayList.toArray(new String[arrayList.size()]), true);
    }

    public void syncContacts(List<Contact.PhoneInfo> list, String str) {
        this.deviceId = str;
        if (list == null || list.size() <= 0) {
            return;
        }
        if (!CarTypeUtils.isOverseasCarType()) {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            try {
                for (Contact.PhoneInfo phoneInfo : list) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("id", phoneInfo.getId());
                    String name = phoneInfo.getName();
                    if (!TextUtils.isEmpty(name)) {
                        String replaceAll = name.replaceAll("[0-9]", "");
                        if (isEnglish(replaceAll)) {
                            name = replaceAll.toUpperCase();
                        } else {
                            name = replaceAll.replaceAll("[a-zA-Z]", "");
                        }
                    }
                    phoneInfo.setName(name);
                    jSONObject2.put("name", phoneInfo.getName());
                    jSONArray.put(jSONObject2);
                }
                jSONObject.put("data", jSONArray);
                jSONObject.put("deveiceId", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String valueOf = String.valueOf(jSONObject);
            LogUtils.i("PhoneNode", "uncompress source data size " + valueOf.getBytes().length);
            byte[] bytes = DeflaterUtils.compressForGzip(valueOf).getBytes();
            LogUtils.i("PhoneNode", "compress data size： " + bytes.length);
            byte[][] divideArray = divideArray(bytes, 262144);
            if (divideArray == null) {
                return;
            }
            for (byte[] bArr : divideArray) {
                LogUtils.i("PhoneNode", "divide data");
                if (bArr != null) {
                    SpeechClient.instance().getAgent().uploadContact(VocabDefine.CONTACT, new SliceData(bArr, bytes.length), 1);
                }
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Contact.PhoneInfo phoneInfo2 : list) {
            arrayList.add(phoneInfo2.getName());
        }
        SpeechClient.instance().getAgent().updateVocab(VocabDefine.CONTACT, (String[]) arrayList.toArray(new String[arrayList.size()]), true);
    }

    public boolean isEnglish(String str) {
        return str.matches("^[a-zA-Z]*");
    }

    public byte[][] divideArray(byte[] bArr, int i) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        int length = bArr.length;
        int ceil = (int) Math.ceil(length / i);
        byte[][] bArr2 = (byte[][]) Array.newInstance(byte.class, ceil, i);
        int i2 = 0;
        int i3 = 0;
        while (i2 < ceil) {
            int i4 = i3 + i;
            if (i4 > length) {
                System.arraycopy(bArr, i3, bArr2[i2], 0, bArr.length - i3);
            } else {
                System.arraycopy(bArr, i3, bArr2[i2], 0, i);
            }
            i2++;
            i3 = i4;
        }
        return bArr2;
    }

    public void syncCallLogs(List<CallLogs> list, String str) {
        if (list == null || list.size() <= 0) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            for (CallLogs callLogs : list) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("id", callLogs.getId());
                jSONObject2.put("name", callLogs.getName());
                jSONObject2.put("time", callLogs.getTime());
                jSONObject2.put("call_count", callLogs.getCallCount());
                callLogs.setName(callLogs.getName().replaceAll("[0-9a-zA-Z]", ""));
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("data", jSONArray);
            jSONObject.put("deveiceId", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().uploadContacts(VocabDefine.CALLLOGS, String.valueOf(jSONObject), 1);
    }

    public void cleanContacts() {
        SpeechClient.instance().getAgent().updateVocab(VocabDefine.CONTACT, null, false);
    }

    public void setBTStatus(boolean z) {
        LogUtils.i(this, "setBTStatus:%b", Boolean.valueOf(z));
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle("联系人");
        listWidget.setExtraType("phone");
        listWidget.addExtra(DeviceInfoKey.PHONE_BLUETOOTH, String.valueOf(z));
        SpeechClient.instance().getActorBridge().send(new ResultActor(PhoneEvent.QUERY_BLUETOOTH).setResult(listWidget));
    }

    public void onQuerySyncBluetooth(String str, String str2, boolean z, int i) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle("联系人");
        listWidget.setExtraType("phone");
        listWidget.addExtra("deviceId", this.deviceId);
        listWidget.addExtra(DeviceInfoKey.PHONE_BLUETOOTH, String.valueOf(z));
        listWidget.addExtra("phone_sync", String.valueOf(i));
        SpeechClient.instance().getActorBridge().send(new ResultActor(PhoneEvent.QUERY_SYNC_BLUETOOTH).setResult(listWidget));
    }

    public void incomingCallRing(String str, String str2, boolean z) {
        String str3;
        LogUtils.i("incomingCallRing, enable tts: " + z);
        stopSpeechDialog();
        try {
            str3 = new JSONObject().put("来电人", str).put("来电号码", str2).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = "";
        }
        try {
            str3 = new JSONObject().put("isLocalSkill", true).put("来电人", str).put("来电号码", str2).put(WakeupResult.REASON_COMMAND, "command://phone.in.accept#command://phone.in.reject").put("enable_tts", z).toString();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        SpeechClient.instance().getSpeechState().setCanExitFlag(false);
        SpeechClient.instance().getAgent().triggerIntent("离线来电话", "来电话", "来电话", str3);
        setPhoneCallStatus(this.mBinder, 1);
    }

    public synchronized void incomingCallRing(String str, String str2) {
        incomingCallRing(str, str2, true);
    }

    public void outgoingCallRing() {
        LogUtils.i("outgoingCallRing");
        stopSpeechDialog();
        setPhoneCallStatus(this.mBinder, 2);
    }

    public synchronized void callOffhook() {
        LogUtils.i("callOffhook");
        stopSpeechDialog();
        SpeechClient.instance().getSpeechState().setCanExitFlag(true);
        setPhoneCallStatus(this.mBinder, 3);
    }

    public synchronized void callEnd() {
        LogUtils.i("callEnd");
        stopSpeechDialog();
        SpeechClient.instance().getSpeechState().setCanExitFlag(true);
        setPhoneCallStatus(this.mBinder, 0);
    }

    public void stopSpeechDialog() {
        LogUtils.i("stopSpeechDialog");
        SpeechClient.instance().getWakeupEngine().stopDialog();
    }

    public void setPhoneCallStatus(IBinder iBinder, int i) {
        SpeechClient.instance().getSpeechState().setPhoneCallStatusWithBinder(iBinder, i);
    }

    public void enableMainWakeup() {
        enableWakeup();
        LogUtils.i("PhoneNode", "startRecord----------");
        setPhoneCallStatus(this.mBinder, 5);
    }

    public void disableMainWakeup() {
        disableWakeup();
        LogUtils.i("PhoneNode", "stopRecord-------");
        setPhoneCallStatus(this.mBinder, 4);
    }

    public boolean isFastWakeup() {
        return SpeechClient.instance().getWakeupEngine().isDefaultEnableWakeup();
    }

    private void enableWakeup() {
        if (!CarTypeUtils.isOverseasCarType()) {
            SpeechClient.instance().getWakeupEngine().enableWakeupWithInfo(this.mBinder, 2, "蓝牙电话", 1);
            SpeechClient.instance().getWakeupEngine().enableWakeupWithInfo(this.mBinder, 4, "蓝牙电话", 1);
            LogUtils.i("enableWakeup clear hot words");
            SpeechClient.instance().getHotwordEngine().removeHotWords(HotwordEngineProxy.BY_BLUETOOTH_PHONE);
            return;
        }
        SpeechClient.instance().getWakeupEngine().enableWakeupEnhance(null);
        SpeechClient.instance().getWakeupEngine().enableMainWakeupWord(this.mBinder);
    }

    private void disableWakeup() {
        if (!CarTypeUtils.isOverseasCarType()) {
            SpeechClient.instance().getWakeupEngine().disableWakeupWithInfo(this.mBinder, 2, "蓝牙电话", CarTypeUtils.isD55ZH() ? "通话中，语音不可用" : "通话中暂停服务，一会再叫我", 1);
            SpeechClient.instance().getWakeupEngine().disableWakeupWithInfo(this.mBinder, 4, "蓝牙电话", CarTypeUtils.isD55ZH() ? "通话中，语音不可用" : "通话中暂停服务，一会再叫我", 1);
            LogUtils.i("disableWakeup");
            return;
        }
        SpeechClient.instance().getWakeupEngine().disableWakeupEnhance(null);
        SpeechClient.instance().getWakeupEngine().disableMainWakeupWord(this.mBinder);
    }

    public void onQueryContacts(String str, String str2) {
        PhoneBean fromJson = PhoneBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onQueryContacts(str, fromJson);
            }
        }
    }

    public void onQueryDetailPhoneInfo(String str, String str2) {
        JSONArray optJSONArray;
        try {
            this.duiWidget = str2;
            JSONObject jSONObject = new JSONObject(str2);
            ArrayList arrayList = new ArrayList();
            if (jSONObject.has("content") && (optJSONArray = jSONObject.optJSONArray("content")) != null && optJSONArray.length() > 0) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        Contact.PhoneInfo phoneInfo = new Contact.PhoneInfo();
                        phoneInfo.setName(optJSONObject.optString(SpeechWidget.WIDGET_TITLE));
                        if (optJSONObject.has(SpeechWidget.WIDGET_SUBTITLE) && !TextUtils.isEmpty(optJSONObject.optString(SpeechWidget.WIDGET_SUBTITLE))) {
                            phoneInfo.setNumber(optJSONObject.optString(SpeechWidget.WIDGET_SUBTITLE));
                        }
                        JSONObject optJSONObject2 = optJSONObject.optJSONObject(SpeechWidget.WIDGET_EXTRA);
                        if (optJSONObject2 != null) {
                            phoneInfo.setId(optJSONObject2.optString("id"));
                        }
                        arrayList.add(phoneInfo);
                    }
                }
            }
            Object[] collectCallbacks = this.mListenerList.collectCallbacks();
            if (collectCallbacks != null) {
                for (Object obj : collectCallbacks) {
                    ((PhoneListener) obj).onQueryDetailPhoneInfo(arrayList);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void replySupport(String str, boolean z, String str2, boolean z2) {
        SupportWidget supportWidget = new SupportWidget();
        supportWidget.setSupport(z);
        supportWidget.addExtra(DeviceInfoKey.PHONE_BLUETOOTH, String.valueOf(z2));
        supportWidget.setTTS(str2);
        SpeechClient.instance().getActorBridge().send(new SupportActor(str).setResult(supportWidget));
    }

    public void postContactsResult(List<PhoneBean> list, boolean z) {
        postContactsResult("联系人", list, z);
    }

    public void postContactsResult(String str, List<PhoneBean> list, boolean z) {
        this.phoneBeanList = list;
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(str);
        listWidget.setExtraType("phone");
        listWidget.addExtra(DeviceInfoKey.PHONE_BLUETOOTH, String.valueOf(z));
        for (PhoneBean phoneBean : list) {
            ContentWidget contentWidget = new ContentWidget();
            contentWidget.setTitle(phoneBean.getName());
            contentWidget.setSubTitle(phoneBean.getNumber());
            contentWidget.addExtra("phone", phoneBean.toJson());
            listWidget.addContentWidget(contentWidget);
        }
        SpeechClient.instance().getActorBridge().send(new ResultActor(PhoneEvent.QUERY_CONTACTS).setResult(listWidget));
    }

    public void postDetailPhoneInfoResult(List<Contact.PhoneInfo> list) {
        if (list == null) {
            return;
        }
        try {
            List<Contact.PhoneInfo> removeDuplicate = removeDuplicate(list);
            JSONArray jSONArray = new JSONArray();
            for (Contact.PhoneInfo phoneInfo : removeDuplicate) {
                if (phoneInfo != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(SpeechWidget.WIDGET_SUBTITLE, phoneInfo.getNumber());
                    jSONObject.put(SpeechWidget.WIDGET_TITLE, phoneInfo.getName());
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("任意联系人", phoneInfo.getName());
                    jSONObject2.put("号码", phoneInfo.getNumber());
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("phone", jSONObject2.toString());
                    jSONObject.put(SpeechWidget.WIDGET_EXTRA, jSONObject3);
                    jSONArray.put(jSONObject);
                }
            }
            JSONObject jSONObject4 = new JSONObject(this.duiWidget);
            jSONObject4.put("content", jSONArray);
            SpeechClient.instance().getAgent().sendEvent(ContextEvent.WIDGET_LIST, jSONObject4.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Contact.PhoneInfo> removeDuplicate(List<Contact.PhoneInfo> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int size = list.size() - 1; size > i; size--) {
                if (!TextUtils.isEmpty(list.get(size).getNumber()) && list.get(size).getNumber().equals(list.get(i).getNumber())) {
                    list.remove(size);
                }
            }
        }
        return list;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onOut(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            r7 = 0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: org.json.JSONException -> L3e
            r0.<init>(r8)     // Catch: org.json.JSONException -> L3e
            java.lang.String r8 = "phone"
            java.lang.String r8 = r0.optString(r8)     // Catch: org.json.JSONException -> L3e
            boolean r1 = android.text.TextUtils.isEmpty(r8)     // Catch: org.json.JSONException -> L3e
            if (r1 != 0) goto L17
            com.xiaopeng.speech.protocol.node.phone.bean.PhoneBean r7 = com.xiaopeng.speech.protocol.node.phone.bean.PhoneBean.fromJson(r8)     // Catch: org.json.JSONException -> L3e
            goto L42
        L17:
            com.xiaopeng.speech.protocol.node.phone.bean.PhoneBean r8 = new com.xiaopeng.speech.protocol.node.phone.bean.PhoneBean     // Catch: org.json.JSONException -> L3e
            r8.<init>()     // Catch: org.json.JSONException -> L3e
            java.lang.String r7 = "name"
            java.lang.String r7 = r0.optString(r7)     // Catch: org.json.JSONException -> L39
            r8.setName(r7)     // Catch: org.json.JSONException -> L39
            java.lang.String r7 = "number"
            java.lang.String r7 = r0.optString(r7)     // Catch: org.json.JSONException -> L39
            r8.setNumber(r7)     // Catch: org.json.JSONException -> L39
            java.lang.String r7 = "id"
            java.lang.String r7 = r0.optString(r7)     // Catch: org.json.JSONException -> L39
            r8.setId(r7)     // Catch: org.json.JSONException -> L39
            r7 = r8
            goto L42
        L39:
            r7 = move-exception
            r5 = r8
            r8 = r7
            r7 = r5
            goto L3f
        L3e:
            r8 = move-exception
        L3f:
            r8.printStackTrace()
        L42:
            if (r7 != 0) goto L4a
            java.lang.String r7 = "phoneBean == null"
            com.xiaopeng.speech.common.util.LogUtils.e(r6, r7)
            return
        L4a:
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r8 = r6.mListenerList
            java.lang.Object[] r8 = r8.collectCallbacks()
            if (r8 == 0) goto L6c
            r0 = 0
        L53:
            int r1 = r8.length
            if (r0 >= r1) goto L6c
            r1 = r8[r0]
            com.xiaopeng.speech.protocol.node.phone.PhoneListener r1 = (com.xiaopeng.speech.protocol.node.phone.PhoneListener) r1
            java.lang.String r2 = r7.getName()
            java.lang.String r3 = r7.getNumber()
            java.lang.String r4 = r7.getId()
            r1.onOut(r2, r3, r4)
            int r0 = r0 + 1
            goto L53
        L6c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.phone.NewPhoneNode.onOut(java.lang.String, java.lang.String):void");
    }

    public void onPhoneSelectOut(String str, String str2) {
        PhoneBean phoneBean;
        int optInt;
        List<PhoneBean> list;
        try {
            optInt = new JSONObject(str2).optInt("select_num");
            list = this.phoneBeanList;
        } catch (JSONException e) {
            e.printStackTrace();
            phoneBean = null;
        }
        if (list != null && list.size() > 0) {
            if (optInt > 0 && optInt <= this.phoneBeanList.size()) {
                phoneBean = this.phoneBeanList.get(optInt - 1);
                if (phoneBean == null) {
                    LogUtils.e(this, "phoneBean == null");
                    return;
                }
                Object[] collectCallbacks = this.mListenerList.collectCallbacks();
                if (collectCallbacks != null) {
                    for (Object obj : collectCallbacks) {
                        ((PhoneListener) obj).onOut(phoneBean.getName(), phoneBean.getNumber(), phoneBean.getId());
                        SpeechClient.instance().getTTSEngine().speak("好的，正在呼叫 " + phoneBean.getName());
                    }
                    return;
                }
                return;
            }
            SpeechClient.instance().getTTSEngine().speak("您的选择已经超出当前列表范围了哦");
            LogUtils.e(this, "select_num is  == " + optInt);
            return;
        }
        LogUtils.e(this, "phoneBeanList == null");
    }

    public void onInAccept(String str, String str2) {
        SpeechClient.instance().getSpeechState().setCanExitFlag(true);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onInAccept();
            }
        }
    }

    public void onInReject(String str, String str2) {
        SpeechClient.instance().getSpeechState().setCanExitFlag(true);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onInReject();
            }
        }
    }

    public void onRedialSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onRedialSupport();
            }
        }
    }

    public void onRedial(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onRedial();
            }
        }
    }

    public void onCallbackSupport(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onCallbackSupport();
            }
        }
    }

    public void onCallback(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onCallback();
            }
        }
    }

    public void onOutCustomerservice(String str, String str2) {
        String str3;
        try {
            str3 = new JSONObject(str2).optString("number");
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = "";
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onOutCustomerservice(str3);
            }
        }
    }

    public void onOutHelp(String str, String str2) {
        String str3;
        try {
            str3 = new JSONObject(str2).optString("number");
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = "";
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onOutHelp(str3);
            }
        }
    }

    public void onSettingOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onSettingOpen();
            }
        }
    }

    public void onQueryBluetooth(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onQueryBluetooth();
            }
        }
    }

    public void onSyncContactResult(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        this.syncResultCode = Integer.valueOf(str2).intValue();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((PhoneListener) obj).onSyncContactResult(this.syncResultCode);
            }
        }
    }
}
