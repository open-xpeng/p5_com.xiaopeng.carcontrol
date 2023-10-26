package com.xiaopeng.speech.protocol.bean.base;

import com.xiaopeng.speech.common.util.DontProguardClass;
import java.io.Serializable;
import java.util.Map;

@DontProguardClass
/* loaded from: classes2.dex */
public class ButtonBean implements Serializable {
    public ActionBean action;
    public String showText;
    public String text;

    @DontProguardClass
    /* loaded from: classes2.dex */
    public static class ActionBean implements Serializable {
        public DataBean data;
        public String type;

        @DontProguardClass
        /* loaded from: classes2.dex */
        public static class DataBean {
            public String code;
            public ParamBean param;
        }

        @DontProguardClass
        /* loaded from: classes2.dex */
        public static class ParamBean {
            public String antlr;
            public String command;
            public Map req;
        }
    }

    public String toString() {
        return "ButtonBean{text='" + this.text + "', showText=" + this.showText + ", action=" + this.action + '}';
    }
}
