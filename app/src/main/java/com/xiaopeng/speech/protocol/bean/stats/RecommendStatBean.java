package com.xiaopeng.speech.protocol.bean.stats;

import com.xiaopeng.speech.common.util.DontProguardClass;

@DontProguardClass
/* loaded from: classes2.dex */
public class RecommendStatBean extends StatCommonBean {
    private int clickPosition;
    private String clickText;
    private long clickTime;
    private String firstText;
    private String msgId;
    private String refMsgId;
    private String secondText;
    private long showTime;
    private String subType;
    private String thirdText;
    private String type;

    public String getSubType() {
        return this.subType;
    }

    public void setSubType(String str) {
        this.subType = str;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String str) {
        this.msgId = str;
    }

    public String getRefMsgId() {
        return this.refMsgId;
    }

    public void setRefMsgId(String str) {
        this.refMsgId = str;
    }

    public RecommendStatBean() {
        super(401);
    }

    public String getClickText() {
        return this.clickText;
    }

    public void setClickText(String str) {
        this.clickText = str;
    }

    public int getClickPosition() {
        return this.clickPosition;
    }

    public void setClickPosition(int i) {
        this.clickPosition = i;
    }

    public long getClickTime() {
        return this.clickTime;
    }

    public void setClickTime(long j) {
        this.clickTime = j;
    }

    public void setShowTime(long j) {
        this.showTime = j;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public long getShowTime() {
        return this.showTime;
    }

    public String getFirstText() {
        return this.firstText;
    }

    public void setFirstText(String str) {
        this.firstText = str;
    }

    public String getSecondText() {
        return this.secondText;
    }

    public void setSecondText(String str) {
        this.secondText = str;
    }

    public String getThirdText() {
        return this.thirdText;
    }

    public void setThirdText(String str) {
        this.thirdText = str;
    }

    public String toString() {
        return "RecommendStatBean{type='" + this.type + "', showTime='" + this.showTime + "', firstText='" + this.firstText + "', secondText='" + this.secondText + "', thirdText='" + this.thirdText + "'}";
    }
}
