package com.xiaopeng.speech.protocol.bean.stock;

import com.lzy.okgo.model.Progress;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.speech.common.bean.BaseBean;
import com.xiaopeng.speech.protocol.utils.StringUtil;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class StockBean extends BaseBean {
    public float curMaxTransNum;
    public float curMaxTransPrice;
    public int curPosition;
    public BaseData mBaseData;
    public List<ChartData> mStockList;
    public List<String> mTimeList;
    public String res = "";
    public float baseLineDate = 0.0f;
    public float length = 0.0f;

    public StockBean() {
        this.mBaseData = null;
        this.mStockList = null;
        this.mTimeList = null;
        this.curPosition = 0;
        this.curMaxTransPrice = 0.0f;
        this.curMaxTransNum = 0.0f;
        this.mStockList = new ArrayList();
        this.curPosition = 0;
        this.curMaxTransPrice = 0.0f;
        this.curMaxTransNum = 0.0f;
        this.mBaseData = new BaseData();
        this.mTimeList = new ArrayList();
    }

    public void clearStockData() {
        List<ChartData> list = this.mStockList;
        if (list == null) {
            this.mStockList = new ArrayList();
        } else {
            list.clear();
        }
        List<String> list2 = this.mTimeList;
        if (list2 == null) {
            this.mTimeList = new ArrayList();
        } else {
            list2.clear();
        }
        this.curPosition = 0;
        this.curMaxTransPrice = 0.0f;
        this.curMaxTransNum = 0.0f;
    }

    public void clearChartData() {
        List<ChartData> list = this.mStockList;
        if (list != null && list.size() > 0) {
            this.mStockList.clear();
        }
        List<String> list2 = this.mTimeList;
        if (list2 == null || list2.size() <= 0) {
            return;
        }
        this.mTimeList.clear();
    }

    public void insertChartData(ChartData chartData) {
        List<ChartData> list = this.mStockList;
        if (list != null) {
            list.add(chartData);
        }
    }

    public void notifyMaxDataRetrive() {
        int size = this.mStockList.size();
        for (int i = this.curPosition; i < size; i++) {
            ChartData chartData = this.mStockList.get(i);
            if (chartData.mTransPrice > this.curMaxTransPrice) {
                this.curMaxTransPrice = chartData.mTransPrice;
            }
            if (chartData.mTransNum > this.curMaxTransNum) {
                this.curMaxTransNum = chartData.mTransNum;
            }
        }
        this.curPosition = size - 1;
    }

    public void parseJson(JSONObject jSONObject) {
        if (jSONObject != null) {
            JSONObject optJSONObject = jSONObject.optJSONObject(SpeechWidget.WIDGET_EXTRA);
            if (optJSONObject == null) {
                this.res = "fail";
                return;
            }
            this.res = IScenarioController.RET_SUCCESS;
            if (this.mBaseData == null) {
                this.mBaseData = new BaseData();
            }
            this.mBaseData.symbol = optJSONObject.optString("symbol");
            this.mBaseData.code = optJSONObject.optString("code");
            this.mBaseData.name = optJSONObject.optString("name");
            this.mBaseData.exchange = optJSONObject.optString("exchange");
            this.mBaseData.low = StringUtil.isDecimalNumber(optJSONObject.optString("low")) ? Float.valueOf(optJSONObject.optString("low")).floatValue() : 0.0f;
            this.mBaseData.current = StringUtil.isDecimalNumber(optJSONObject.optString("current")) ? Float.valueOf(optJSONObject.optString("current")).floatValue() : 0.0f;
            this.mBaseData.peLYR = StringUtil.isDecimalNumber(optJSONObject.optString("peLYR")) ? Float.valueOf(optJSONObject.optString("peLYR")).floatValue() : 0.0f;
            this.mBaseData.peTTM = StringUtil.isDecimalNumber(optJSONObject.optString("peTTM")) ? Float.valueOf(optJSONObject.optString("peTTM")).floatValue() : 0.0f;
            this.mBaseData.high = StringUtil.isDecimalNumber(optJSONObject.optString("high")) ? Float.valueOf(optJSONObject.optString("high")).floatValue() : 0.0f;
            this.mBaseData.lastClose = StringUtil.isDecimalNumber(optJSONObject.optString("lastClose")) ? Float.valueOf(optJSONObject.optString("lastClose")).floatValue() : 0.0f;
            this.mBaseData.percentage = StringUtil.isDecimalNumber(optJSONObject.optString("percentage")) ? Float.valueOf(optJSONObject.optString("percentage")).floatValue() : 0.0f;
            this.mBaseData.change = StringUtil.isDecimalNumber(optJSONObject.optString("change")) ? Float.valueOf(optJSONObject.optString("change")).floatValue() : 0.0f;
            this.mBaseData.amount = StringUtil.isDecimalNumber(optJSONObject.optString("amount")) ? Float.valueOf(optJSONObject.optString("amount")).floatValue() : 0.0f;
            this.mBaseData.open = StringUtil.isDecimalNumber(optJSONObject.optString("open")) ? Float.valueOf(optJSONObject.optString("open")).floatValue() : 0.0f;
            this.mBaseData.adate = dateStrConvert(optJSONObject.optString(Progress.DATE) == null ? "" : optJSONObject.optString(Progress.DATE));
            this.mBaseData.avolume = (StringUtil.isDecimalNumber(optJSONObject.optString("volume")) ? Float.valueOf(optJSONObject.optString("volume")).floatValue() : 0.0f) * 100.0f;
            this.mBaseData.turnoverRate = StringUtil.isDecimalNumber(optJSONObject.optString("turnoverRate")) ? Float.valueOf(optJSONObject.optString("turnoverRate")).floatValue() : 0.0f;
            this.mBaseData.state = optJSONObject.optString("state");
            if (Math.abs(this.mBaseData.high - this.mBaseData.lastClose) > Math.abs(this.mBaseData.lastClose - Math.abs(this.mBaseData.low))) {
                this.baseLineDate = Math.abs(this.mBaseData.lastClose - Math.abs(this.mBaseData.high - this.mBaseData.lastClose));
                this.length = Math.abs(this.mBaseData.high - this.mBaseData.lastClose) * 2.0f;
            } else {
                this.baseLineDate = Math.abs(this.mBaseData.lastClose - Math.abs(this.mBaseData.low - this.mBaseData.lastClose));
                this.length = Math.abs(this.mBaseData.low - this.mBaseData.lastClose) * 2.0f;
            }
            JSONArray optJSONArray = optJSONObject.optJSONArray("tradeTime");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                for (int i = 0; i < length; i++) {
                    String optString = optJSONArray.optString(i);
                    List<String> list = this.mTimeList;
                    if (list != null) {
                        list.add(optString);
                    }
                }
            }
            JSONArray optJSONArray2 = optJSONObject.optJSONObject("chart") != null ? optJSONObject.optJSONObject("chart").optJSONArray(SpeechWidget.TYPE_LIST) : null;
            if (optJSONArray2 != null) {
                int length2 = optJSONArray2.length();
                for (int i2 = 0; i2 < length2; i2++) {
                    try {
                        JSONObject jSONObject2 = optJSONArray2.getJSONObject(i2);
                        if (jSONObject2 != null) {
                            insertChartData(new ChartData(Float.valueOf(jSONObject2.optString("price")).floatValue(), Float.valueOf(jSONObject2.optString("volume")).floatValue(), jSONObject2.optString(Progress.DATE)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            return;
        }
        this.res = "fail";
    }

    private String dateStrConvert(String str) {
        int lastIndexOf;
        String[] split = str.split("\\+");
        if (split != null && split.length == 2) {
            return split[0].replace("T", " ");
        }
        String[] split2 = str.split("T");
        if (split2 == null || split2.length != 2 || (lastIndexOf = split2[1].lastIndexOf("-")) == -1 || lastIndexOf >= split2[1].length()) {
            return null;
        }
        return split2[0] + " " + split2[1].substring(0, lastIndexOf);
    }

    /* loaded from: classes2.dex */
    public static class ChartData {
        public String mTime;
        public float mTransNum;
        public float mTransPrice;

        public ChartData(float f, float f2, String str) {
            this.mTransPrice = 0.0f;
            this.mTransNum = 0.0f;
            this.mTransPrice = f;
            this.mTransNum = f2;
            this.mTime = str;
        }
    }

    /* loaded from: classes2.dex */
    public static class BaseData {
        public float amount;
        public float avolume;
        public float change;
        public float current;
        public float high;
        public float lastClose;
        public float low;
        public float open;
        public float peLYR;
        public float peTTM;
        public float percentage;
        public float turnoverRate;
        public String symbol = "";
        public String code = "";
        public String adate = "";
        public String name = "";
        public String exchange = "";
        public String state = "";

        public String toString() {
            return "BaseData{symbol='" + this.symbol + "', low=" + this.low + ", current=" + this.current + ", peLYR=" + this.peLYR + ", peTTM=" + this.peTTM + ", high=" + this.high + ", lastClose=" + this.lastClose + ", percentage=" + this.percentage + ", change=" + this.change + ", turnoverRate=" + this.turnoverRate + ", code='" + this.code + "', amount=" + this.amount + ", open=" + this.open + ", adate='" + this.adate + "', avolume=" + this.avolume + ", name='" + this.name + "', exchange='" + this.exchange + "', state='" + this.state + "'}";
        }
    }

    public String toString() {
        return "StockBean{mBaseData=" + this.mBaseData + ", mStockList=" + this.mStockList + ", mTimeList=" + this.mTimeList + ", curPosition=" + this.curPosition + ", curMaxTransPrice=" + this.curMaxTransPrice + ", curMaxTransNum=" + this.curMaxTransNum + ", res='" + this.res + "', baseLineDate=" + this.baseLineDate + ", length=" + this.length + '}';
    }
}
