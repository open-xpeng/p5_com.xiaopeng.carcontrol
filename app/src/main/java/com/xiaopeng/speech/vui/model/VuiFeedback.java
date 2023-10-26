package com.xiaopeng.speech.vui.model;

import com.xiaopeng.vui.commons.VuiFeedbackType;

/* loaded from: classes2.dex */
public class VuiFeedback {
    public VuiFeedbackCode code;
    public String content;
    private VuiFeedbackType feedbackType;
    public String resourceName;
    public int state;

    public void setState(int i) {
        this.state = i;
    }

    public int getState() {
        return this.state;
    }

    public VuiFeedbackCode getCode() {
        return this.code;
    }

    public String getContent() {
        return this.content;
    }

    public void setCode(VuiFeedbackCode vuiFeedbackCode) {
        this.code = vuiFeedbackCode;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setResourceName(String str) {
        this.resourceName = str;
    }

    public void setFeedbackType(VuiFeedbackType vuiFeedbackType) {
        this.feedbackType = vuiFeedbackType;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public VuiFeedbackType getFeedbackType() {
        return this.feedbackType;
    }

    public String toString() {
        return "VuiFeedback{code=" + this.code.getFeedbackCode() + ", content='" + this.content + "', resourceName='" + this.resourceName + "', state=" + this.state + ", feedbackType=" + this.feedbackType.getType() + '}';
    }

    private VuiFeedback(Builder builder) {
        this.code = VuiFeedbackCode.SUCCESS;
        this.feedbackType = VuiFeedbackType.TTS;
        this.state = builder.state;
        this.content = builder.content;
        this.code = builder.code;
        this.feedbackType = builder.type;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private String content;
        private int state = -1;
        private VuiFeedbackCode code = VuiFeedbackCode.SUCCESS;
        private VuiFeedbackType type = VuiFeedbackType.TTS;

        public Builder state(int i) {
            this.state = i;
            return this;
        }

        public Builder content(String str) {
            this.content = str;
            return this;
        }

        public Builder code(VuiFeedbackCode vuiFeedbackCode) {
            this.code = vuiFeedbackCode;
            return this;
        }

        public Builder type(VuiFeedbackType vuiFeedbackType) {
            this.type = vuiFeedbackType;
            return this;
        }

        public VuiFeedback build() {
            return new VuiFeedback(this);
        }
    }
}
