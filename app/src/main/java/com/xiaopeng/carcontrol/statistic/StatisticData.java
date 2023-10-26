package com.xiaopeng.carcontrol.statistic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class StatisticData {
    private String mButtonId;
    private String mButtonName;
    private String mPageId;
    private String mPageName;
    private Map<String, Object> mParms;

    private StatisticData(Builder builder) {
        this.mParms = null;
        this.mPageId = builder.mPageId;
        this.mPageName = builder.mPageName;
        this.mButtonId = builder.mButtonId;
        this.mButtonName = builder.mButtonName;
        this.mParms = builder.mParms;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private String mButtonId;
        private String mButtonName;
        private String mPageId;
        private String mPageName;
        private Map<String, Object> mParms;

        public Builder pageId(String val) {
            this.mPageId = val;
            return this;
        }

        public Builder pageName(String val) {
            this.mPageName = val;
            return this;
        }

        public Builder buttonId(String val) {
            this.mButtonId = val;
            return this;
        }

        public Builder buttonName(String val) {
            this.mButtonName = val;
            return this;
        }

        public Builder putParm(String name, String value) {
            if (this.mParms == null) {
                this.mParms = new ConcurrentHashMap();
            }
            this.mParms.put(name, value);
            return this;
        }

        public Builder putParm(String name, Boolean value) {
            if (this.mParms == null) {
                this.mParms = new ConcurrentHashMap();
            }
            this.mParms.put(name, value);
            return this;
        }

        public Builder putParm(String name, Character value) {
            if (this.mParms == null) {
                this.mParms = new ConcurrentHashMap();
            }
            this.mParms.put(name, value);
            return this;
        }

        public Builder putParm(String name, Number value) {
            if (this.mParms == null) {
                this.mParms = new ConcurrentHashMap();
            }
            this.mParms.put(name, value);
            return this;
        }

        public Builder setParms(Map<String, Object> parms) {
            this.mParms = parms;
            return this;
        }

        public StatisticData build() {
            return new StatisticData(this);
        }
    }

    public String getPageId() {
        return this.mPageId;
    }

    public String getButtonId() {
        return this.mButtonId;
    }

    public Map<String, Object> getParms() {
        return this.mParms;
    }
}
