package com.xiaopeng.speech.protocol.bean.card;

import com.xiaopeng.speech.common.bean.BaseBean;
import com.xiaopeng.speech.common.util.DontProguardClass;
import com.xiaopeng.speech.protocol.bean.base.ButtonBean;
import java.util.List;

@DontProguardClass
/* loaded from: classes2.dex */
public class CardBean extends BaseBean {
    public CardContentBean cardContent;
    public List<ButtonBean> relateList;
    public String relateText;

    @DontProguardClass
    /* loaded from: classes2.dex */
    public static class CardContentBean {
        public String cardType;
        public CardDataBean data;

        @DontProguardClass
        /* loaded from: classes2.dex */
        public static class CardDataBean {
            public List<ButtonBean> buttonList;
            public ButtonBean detail;
            public MediaBean media;
            public String text;
            public String title;
            public String tts;

            @DontProguardClass
            /* loaded from: classes2.dex */
            public static class MediaBean {
                public String name;
                public String type;
                public String url;
                public String url_list;
            }
        }
    }
}
