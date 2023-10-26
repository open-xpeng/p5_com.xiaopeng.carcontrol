package com.xiaopeng.lib.framework.netchannelmodule.remotestorage.aws;

/* loaded from: classes2.dex */
public class UploadInfo {
    public static final String APP_ID = "XP-Appid";
    public static final String MODEL = "";
    public static final String UPLOAD_TYPE = "81";
    public static final String VERSION = "2";
    public String app_id;
    public String file;
    public String model;
    public String sign;
    public String timestamp;
    public String type;
    public String v;

    /* loaded from: classes2.dex */
    public static final class Builder {
        String file = "";
        String app_id = "XP-Appid";
        String model = "";
        String type = UploadInfo.UPLOAD_TYPE;
        String v = "2";
        String timestamp = "";
        String sign = "";

        public Builder file(String str) {
            this.file = str;
            return this;
        }

        public Builder model(String str) {
            this.model = str;
            return this;
        }

        public Builder type(String str) {
            this.type = str;
            return this;
        }

        public Builder v(String str) {
            this.v = str;
            return this;
        }

        public Builder timestamp(String str) {
            this.timestamp = str;
            return this;
        }

        public Builder sign(String str) {
            this.sign = str;
            return this;
        }

        public UploadInfo build() {
            return new UploadInfo(this);
        }
    }

    public UploadInfo() {
        this(new Builder());
    }

    UploadInfo(Builder builder) {
        this.file = builder.file;
        this.app_id = builder.app_id;
        this.model = builder.model;
        this.type = builder.type;
        this.v = builder.v;
        this.timestamp = builder.timestamp;
        this.sign = builder.sign;
    }
}
