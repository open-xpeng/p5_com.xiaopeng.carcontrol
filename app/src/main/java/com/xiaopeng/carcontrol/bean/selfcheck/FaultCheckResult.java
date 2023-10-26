package com.xiaopeng.carcontrol.bean.selfcheck;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.speech.protocol.bean.recommend.RecommendBean;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class FaultCheckResult {
    @SerializedName("checkedCount")
    private int mCheckedCount;
    @SerializedName("detailResult")
    private List<DetailResult> mDetailResult = new ArrayList();
    @SerializedName("faultCount")
    private int mFaultCount;
    @SerializedName("status")
    private int mStatus;
    @SerializedName("totalCount")
    private int mTotalCount;
    @SerializedName("tts")
    private String mTts;

    public void addDetailResult(String title, String result, int level) {
        this.mDetailResult.add(new DetailResult(title, result, level));
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }

    public int getTotalCount() {
        return this.mTotalCount;
    }

    public void setTotalCount(int totalCount) {
        this.mTotalCount = totalCount;
    }

    public int getCheckedCount() {
        return this.mCheckedCount;
    }

    public void setCheckedCount(int checkedCount) {
        this.mCheckedCount = checkedCount;
    }

    public int getFaultCount() {
        return this.mFaultCount;
    }

    public void setFaultCount(int faultCount) {
        this.mFaultCount = faultCount;
    }

    public List<DetailResult> getDetailResult() {
        return this.mDetailResult;
    }

    /* loaded from: classes.dex */
    public class DetailResult {
        @SerializedName("level")
        private int level;
        @SerializedName(SpeechWidget.WIDGET_TITLE)
        private String mTitle;
        @SerializedName(RecommendBean.SHOW_TIME_RESULT)
        private String result;

        public DetailResult(String title, String result, int level) {
            this.mTitle = title;
            this.result = result;
            this.level = level;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public void setTitle(String title) {
            this.mTitle = title;
        }

        public String getResult() {
            return this.result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getLevel() {
            return this.level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }

    public String getTTS() {
        return this.mTts;
    }

    public void setTTS(String TTS) {
        this.mTts = TTS;
    }
}
