package com.xiaopeng.speech.protocol.node.navi.bean;

import com.xiaopeng.speech.common.util.DontProguardClass;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.protocol.utils.CarTypeUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@DontProguardClass
/* loaded from: classes2.dex */
public class PoiExtraBean {
    private static final String TAG = "PoiExtraBean";
    private int acFreeNums;
    private int acNums;
    private boolean busiState;
    private String busiTime;
    private List<ChargeBean> content;
    private int dcFreeNums;
    private int dcNums;
    private String payment;
    private int selfSupportFlag;

    public int getSelfSupportFlag() {
        return this.selfSupportFlag;
    }

    public void setSelfSupportFlag(int i) {
        this.selfSupportFlag = i;
    }

    public int getDcNums() {
        return this.dcNums;
    }

    public void setDcNums(int i) {
        this.dcNums = i;
    }

    public int getDcFreeNums() {
        return this.dcFreeNums;
    }

    public void setDcFreeNums(int i) {
        this.dcFreeNums = i;
    }

    public int getAcNums() {
        return this.acNums;
    }

    public void setAcNums(int i) {
        this.acNums = i;
    }

    public int getAcFreeNums() {
        return this.acFreeNums;
    }

    public void setAcFreeNums(int i) {
        this.acFreeNums = i;
    }

    public boolean getBusiState() {
        return this.busiState;
    }

    public void setBusiState(boolean z) {
        this.busiState = z;
    }

    public String getBusiTime() {
        return this.busiTime;
    }

    public void setBusiTime(String str) {
        this.busiTime = str;
    }

    public String getPayment() {
        return this.payment;
    }

    public void setPayment(String str) {
        this.payment = str;
    }

    public List<ChargeBean> getContent() {
        return this.content;
    }

    public void setContent(List<ChargeBean> list) {
        this.content = list;
    }

    public static PoiExtraBean fromJson(String str) {
        PoiExtraBean poiExtraBean = new PoiExtraBean();
        boolean isOverseasCarType = CarTypeUtils.isOverseasCarType();
        LogUtils.d(TAG, "fromJson, isOverseasCarType: " + isOverseasCarType);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("dcNums")) {
                poiExtraBean.dcNums = jSONObject.optInt("dcNums");
            }
            if (jSONObject.has("dcFreeNums")) {
                poiExtraBean.dcFreeNums = jSONObject.optInt("dcFreeNums");
            }
            if (jSONObject.has("acNums")) {
                poiExtraBean.acNums = jSONObject.optInt("acNums");
            }
            if (jSONObject.has("acFreeNums")) {
                if (!isOverseasCarType) {
                    poiExtraBean.acFreeNums = jSONObject.optInt("acFreeNums");
                } else {
                    poiExtraBean.dcNums = jSONObject.optInt("acFreeNums");
                }
            }
            if (jSONObject.has("selfSupportFlag")) {
                poiExtraBean.selfSupportFlag = jSONObject.optInt("selfSupportFlag");
            }
            if (isOverseasCarType) {
                if (jSONObject.has("busiState")) {
                    poiExtraBean.busiState = jSONObject.optBoolean("busiState");
                }
                if (jSONObject.has("busiTime")) {
                    poiExtraBean.busiTime = jSONObject.optString("busiTime");
                }
                if (jSONObject.has("payment")) {
                    poiExtraBean.payment = jSONObject.optString("payment");
                }
                JSONArray optJSONArray = jSONObject.optJSONArray("content");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        ChargeBean parseChargeBean = parseChargeBean(optJSONArray.getJSONObject(i));
                        if (parseChargeBean != null) {
                            arrayList.add(parseChargeBean);
                        }
                    }
                    poiExtraBean.setContent(arrayList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return poiExtraBean;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("dcNums", this.dcNums);
        jSONObject.put("dcFreeNums", this.dcFreeNums);
        jSONObject.put("acNums", this.acNums);
        jSONObject.put("acFreeNums", this.acFreeNums);
        jSONObject.put("selfSupportFlag", this.selfSupportFlag);
        jSONObject.put("busiState", this.busiState);
        jSONObject.put("busiTime", this.busiTime);
        jSONObject.put("payment", this.payment);
        JSONArray jSONArray = new JSONArray();
        List<ChargeBean> list = this.content;
        if (list != null && list.size() > 0) {
            for (ChargeBean chargeBean : this.content) {
                jSONArray.put(chargeBean.toJson());
            }
        }
        jSONObject.put("content", jSONArray);
        return jSONObject;
    }

    private static ChargeBean parseChargeBean(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        ChargeBean chargeBean = new ChargeBean();
        chargeBean.setTotal(jSONObject.optInt("total"));
        chargeBean.setAvailable(jSONObject.optInt("available"));
        chargeBean.setPower(jSONObject.optString("power"));
        return chargeBean;
    }

    public String toString() {
        return "PoiExtraBean{dcNums='" + this.dcNums + "', dcFreeNums='" + this.dcFreeNums + "', acNums='" + this.acNums + "', acFreeNums=" + this.acFreeNums + ", selfSupportFlag=" + this.selfSupportFlag + ", busiState='" + this.busiState + "', busiTime=" + this.busiTime + ", payment='" + this.payment + "', content='" + this.content + '}';
    }
}
