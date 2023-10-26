package com.xiaopeng.speech.protocol.bean.config;

import com.xiaopeng.speech.common.util.DontProguardClass;
import java.util.Map;

@DontProguardClass
/* loaded from: classes2.dex */
public class SpeechMainData {
    private OperationInfoBean info;
    private Map<String, OperationBean> operations;

    public OperationInfoBean getInfo() {
        return this.info;
    }

    public void setInfo(OperationInfoBean operationInfoBean) {
        this.info = operationInfoBean;
    }

    public Map<String, OperationBean> getOperations() {
        return this.operations;
    }

    public void setOperations(Map<String, OperationBean> map) {
        this.operations = map;
    }
}
