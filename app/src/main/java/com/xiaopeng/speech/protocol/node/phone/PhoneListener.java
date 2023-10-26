package com.xiaopeng.speech.protocol.node.phone;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.node.phone.bean.Contact;
import com.xiaopeng.speech.protocol.node.phone.bean.PhoneBean;
import java.util.List;

/* loaded from: classes2.dex */
public interface PhoneListener extends INodeListener {
    void onCallback();

    void onCallbackSupport();

    void onInAccept();

    void onInReject();

    void onOut(String str, String str2, String str3);

    void onOutCustomerservice(String str);

    void onOutHelp(String str);

    void onQueryBluetooth();

    void onQueryContacts(String str, PhoneBean phoneBean);

    void onQueryDetailPhoneInfo(List<Contact.PhoneInfo> list);

    void onRedial();

    void onRedialSupport();

    void onSettingOpen();

    void onSyncContactResult(int i);
}
