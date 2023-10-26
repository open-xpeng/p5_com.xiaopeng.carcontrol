package com.xiaopeng.speech.protocol.bean.search;

import com.xiaopeng.speech.common.util.DontProguardClass;
import com.xiaopeng.speech.protocol.node.navi.bean.ChargeBean;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@DontProguardClass
/* loaded from: classes2.dex */
public class ChargeData {
    private List<ChargingStationData> searchData;

    public List<ChargingStationData> getSearchData() {
        return this.searchData;
    }

    @DontProguardClass
    /* loaded from: classes2.dex */
    public static class ChargingStationData {
        public static final int CHARGING_SERVICE_SELF = 1;
        private int acFreeNums;
        private int acNums;
        private boolean busiState;
        private String busiTime;
        private float chargingFee;
        private List<ChargeBean> content;
        private int dcFreeNums;
        private int dcNums;
        private String displayDistance;
        private String displayExpRemainDis;
        private float distance;
        private float expRemainDis;
        private FreeNumPre freeNumPre;
        private String locationDes;
        private String name;
        private String operName;
        private String payment;
        private int selfSupportFlag;
        private float serviceFee;
        private String stationAddr;
        private String stationId;

        public String getStationId() {
            return this.stationId;
        }

        public String getName() {
            return this.name;
        }

        public String getDisplayDistance() {
            return this.displayDistance;
        }

        public void setDisplayDistance(String str) {
            this.displayDistance = str;
        }

        public String getDisplayExpRemainDis() {
            return this.displayExpRemainDis;
        }

        public void setDisplayExpRemainDis(String str) {
            this.displayExpRemainDis = str;
        }

        public int getSelfSupportFlag() {
            return this.selfSupportFlag;
        }

        public String getStationAddr() {
            return this.stationAddr;
        }

        public float getExpRemainDis() {
            return this.expRemainDis;
        }

        public float getDistance() {
            return this.distance;
        }

        public int getDcNums() {
            return this.dcNums;
        }

        public int getDcFreeNums() {
            return this.dcFreeNums;
        }

        public int getAcNums() {
            return this.acNums;
        }

        public int getAcFreeNums() {
            return this.acFreeNums;
        }

        public float getChargingFee() {
            return this.chargingFee;
        }

        public float getServiceFee() {
            return this.serviceFee;
        }

        public void setBusiTime(String str) {
            this.busiTime = str;
        }

        public String getBusiTime() {
            return this.busiTime;
        }

        public String getLocationDes() {
            return this.locationDes;
        }

        public FreeNumPre getFreeNumPre() {
            return this.freeNumPre;
        }

        public String getOperName() {
            return this.operName;
        }

        public void setStationId(String str) {
            this.stationId = str;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setSelfSupportFlag(int i) {
            this.selfSupportFlag = i;
        }

        public void setStationAddr(String str) {
            this.stationAddr = str;
        }

        public void setExpRemainDis(float f) {
            this.expRemainDis = f;
        }

        public void setDistance(float f) {
            this.distance = f;
        }

        public void setDcNums(int i) {
            this.dcNums = i;
        }

        public void setDcFreeNums(int i) {
            this.dcFreeNums = i;
        }

        public void setAcNums(int i) {
            this.acNums = i;
        }

        public void setAcFreeNums(int i) {
            this.acFreeNums = i;
        }

        public void setChargingFee(float f) {
            this.chargingFee = f;
        }

        public void setServiceFee(float f) {
            this.serviceFee = f;
        }

        public void setLocationDes(String str) {
            this.locationDes = str;
        }

        public void setOperName(String str) {
            this.operName = str;
        }

        public boolean getBusiState() {
            return this.busiState;
        }

        public void setBusiState(boolean z) {
            this.busiState = z;
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

        public JSONObject toJson() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("stationId", getStationId());
            jSONObject.put("name", getName());
            jSONObject.put("selfSupportFlag", this.selfSupportFlag);
            jSONObject.put("stationAddr", this.stationAddr);
            jSONObject.put("expRemainDis", this.expRemainDis);
            jSONObject.put("distance", this.distance);
            jSONObject.put("dcNums", this.dcNums);
            jSONObject.put("dcFreeNums", this.dcFreeNums);
            jSONObject.put("acNums", this.acNums);
            jSONObject.put("acFreeNums", this.acFreeNums);
            jSONObject.put("chargingFee", this.chargingFee);
            jSONObject.put("serviceFee", this.serviceFee);
            jSONObject.put("busiTime", this.busiTime);
            jSONObject.put("locationDes", this.locationDes);
            jSONObject.put("operName", this.operName);
            jSONObject.put("displayDistance", this.displayDistance);
            jSONObject.put("displayExpRemainDis", this.displayExpRemainDis);
            jSONObject.put("busiState", this.busiState);
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

        public String toString() {
            return "ChargingStationData{stationId='" + this.stationId + "', name='" + this.name + "', selfSupportFlag=" + this.selfSupportFlag + ", stationAddr='" + this.stationAddr + "', expRemainDis=" + this.expRemainDis + ", distance=" + this.distance + ", dcNums=" + this.dcNums + ", dcFreeNums=" + this.dcFreeNums + ", acNums=" + this.acNums + ", acFreeNums=" + this.acFreeNums + ", chargingFee=" + this.chargingFee + ", serviceFee=" + this.serviceFee + ", busiTime='" + this.busiTime + "', locationDes='" + this.locationDes + "', operName=" + this.operName + ", displayDistance=" + this.displayDistance + ", displayExpRemainDis=" + this.displayExpRemainDis + ", content='" + this.content + ", freeNumPre=" + this.freeNumPre + '}';
        }
    }

    @DontProguardClass
    /* loaded from: classes2.dex */
    public static class FreeNumPre {
        private List<Integer> acNums;
        private List<Integer> dcNums;
        private int interval;
        private boolean showHistory;
        private List<Integer> timePoint;
        private long timeStamp;

        public int getInterval() {
            return this.interval;
        }

        public List<Integer> getTimePoints() {
            return this.timePoint;
        }

        public List<Integer> getDcNums() {
            return this.dcNums;
        }

        public List<Integer> getAcNums() {
            return this.acNums;
        }

        public boolean isShowHistory() {
            return this.showHistory;
        }

        public long getTimeStamp() {
            return this.timeStamp;
        }
    }

    public String toString() {
        return "ChargeData{searchData=" + this.searchData + '}';
    }
}
