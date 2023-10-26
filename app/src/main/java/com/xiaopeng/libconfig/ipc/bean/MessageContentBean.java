package com.xiaopeng.libconfig.ipc.bean;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class MessageContentBean {
    public static final int COMMING_SOUND_DEFAULT = 0;
    public static final int COMMING_SOUND_NONE = 2;
    public static final int COMMING_SOUND_WARNNING = 1;
    public static final int EXECUTE_AFTER_TTS = 1;
    public static final int EXECUTE_DELAY = 2;
    public static final int EXECUTE_USER = 0;
    public static final int PERMANENT_MSG = 1;
    public static final String SENSING_TYPE_BEHAVIOR = "行为感知";
    public static final String SENSING_TYPE_ENTERTAINMENT = "娱乐感知";
    public static final String SENSING_TYPE_LIFE = "生活感知";
    public static final String SENSING_TYPE_NATURAL = "环境感知";
    public static final String SENSING_TYPE_ROAD = "路况感知";
    public static final String SENSING_TYPE_SOCIAL = "社交感知";
    public static final String SENSING_TYPE_SYSTEM = "系统感知";
    private String backgroundUrl;
    private List<MsgButton> buttons;
    private String callback;
    private boolean cancelFeedbackSound;
    private int comingSoundType;
    private String conditions;
    private int executeDelayTime;
    private int executeType;
    private MsgClose msgClose;
    private MsgShow msgShow;
    private boolean neverShow = false;
    private int permanent;
    private List<MsgPic> pics;
    private String sensingType;
    private ShowRules showRules;
    private String soundArea;
    private List<String> titles;
    private String tts;
    private int type;
    private long validTime;

    public String getSoundArea() {
        return this.soundArea;
    }

    public void setSoundArea(String str) {
        this.soundArea = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public List<String> getTitles() {
        return this.titles;
    }

    public void setTitles(List<String> list) {
        this.titles = list;
    }

    public List<MsgButton> getButtons() {
        return this.buttons;
    }

    public void setButtons(List<MsgButton> list) {
        this.buttons = list;
    }

    public List<MsgPic> getPics() {
        return this.pics;
    }

    public void setPics(List<MsgPic> list) {
        this.pics = list;
    }

    public int getPermanent() {
        return this.permanent;
    }

    public void setPermanent(int i) {
        this.permanent = i;
    }

    public String getTts() {
        return this.tts;
    }

    public void setTts(String str) {
        this.tts = str;
    }

    public long getValidTime() {
        return this.validTime;
    }

    public void setValidTime(long j) {
        this.validTime = j;
    }

    public String getCallback() {
        return this.callback;
    }

    public void setCallback(String str) {
        this.callback = str;
    }

    public String getSensingType() {
        return this.sensingType;
    }

    public void setSensingType(String str) {
        this.sensingType = str;
    }

    public int getComingSoundType() {
        return this.comingSoundType;
    }

    public void setComingSoundType(int i) {
        this.comingSoundType = i;
    }

    public int getExecuteType() {
        return this.executeType;
    }

    public void setExecuteType(int i) {
        this.executeType = i;
    }

    public int getExecuteDelayTime() {
        return this.executeDelayTime;
    }

    public void setExecuteDelayTime(int i) {
        this.executeDelayTime = i;
    }

    public boolean isCancelFeedbackSound() {
        return this.cancelFeedbackSound;
    }

    public void setCancelFeedbackSound(boolean z) {
        this.cancelFeedbackSound = z;
    }

    public boolean isNeverShow() {
        return this.neverShow;
    }

    public void setNeverShow(boolean z) {
        this.neverShow = z;
    }

    public String getConditions() {
        return this.conditions;
    }

    public void setConditions(String str) {
        this.conditions = str;
    }

    public String getBackgroundUrl() {
        return this.backgroundUrl;
    }

    public void setBackgroundUrl(String str) {
        this.backgroundUrl = str;
    }

    public MsgButton getButton(int i) {
        if (getButtons() == null || getButtons().size() <= i) {
            return null;
        }
        return getButtons().get(i);
    }

    public MsgShow getMsgShow() {
        return this.msgShow;
    }

    public void setMsgShow(MsgShow msgShow) {
        this.msgShow = msgShow;
    }

    public ShowRules getShowRules() {
        return this.showRules;
    }

    public void setShowRules(ShowRules showRules) {
        this.showRules = showRules;
    }

    public MsgClose getMsgClose() {
        return this.msgClose;
    }

    public void setMsgClose(MsgClose msgClose) {
        this.msgClose = msgClose;
    }

    /* loaded from: classes2.dex */
    public static class MsgShow {
        String content;
        String pack;

        public String getPack() {
            return this.pack;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String str) {
            this.content = str;
        }

        public void setPack(String str) {
            this.pack = str;
        }
    }

    /* loaded from: classes2.dex */
    public static class MsgClose {
        String content;
        String pack;

        public String getPack() {
            return this.pack;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String str) {
            this.content = str;
        }

        public void setPack(String str) {
            this.pack = str;
        }
    }

    /* loaded from: classes2.dex */
    public static class ShowRules {
        int bgClickable;
        int filter;
        String gearLever;
        int max;
        int needCheck;
        int priority;
        int showTime;

        public int getFilter() {
            return this.filter;
        }

        public int getPriority() {
            return this.priority;
        }

        public int getMax() {
            return this.max;
        }

        public int getNeedCheck() {
            return this.needCheck;
        }

        public void setFilter(int i) {
            this.filter = i;
        }

        public void setNeedCheck(int i) {
            this.needCheck = i;
        }

        public void setMax(int i) {
            this.max = i;
        }

        public void setPriority(int i) {
            this.priority = i;
        }

        public String getGearLever() {
            return this.gearLever;
        }

        public void setGearLever(String str) {
            this.gearLever = str;
        }

        public int getShowTime() {
            return this.showTime;
        }

        public void setShowTime(int i) {
            this.showTime = i;
        }

        public int getBgClickable() {
            return this.bgClickable;
        }

        public void setBgClickable(int i) {
            this.bgClickable = i;
        }
    }

    /* loaded from: classes2.dex */
    public static class MsgButton {
        String content;
        String guideWords;
        String name;
        String notResponseWord;
        String pack;
        String responseGuideTTS;
        String responseTTS;
        String responseWord;
        boolean speechResponse;

        public static MsgButton create(String str, String str2, String str3) {
            return create(str, str2, str3, false);
        }

        public static MsgButton create(String str, String str2, String str3, boolean z) {
            MsgButton msgButton = new MsgButton();
            msgButton.name = str;
            msgButton.pack = str2;
            msgButton.content = str3;
            msgButton.speechResponse = z;
            return msgButton;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getPack() {
            return this.pack;
        }

        public void setPack(String str) {
            this.pack = str;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String str) {
            this.content = str;
        }

        public boolean isSpeechResponse() {
            return this.speechResponse;
        }

        public void setSpeechResponse(boolean z) {
            this.speechResponse = z;
        }

        public String getResponseWord() {
            return this.responseWord;
        }

        public void setResponseWord(String str) {
            this.responseWord = str;
        }

        public String getNotResponseWord() {
            return this.notResponseWord;
        }

        public void setNotResponseWord(String str) {
            this.notResponseWord = str;
        }

        public String getResponseTTS() {
            return this.responseTTS;
        }

        public void setResponseTTS(String str) {
            this.responseTTS = str;
        }

        public String getResponseGuideTTS() {
            return this.responseGuideTTS;
        }

        public void setResponseGuideTTS(String str) {
            this.responseGuideTTS = str;
        }

        public String getGuideWords() {
            return this.guideWords;
        }

        public void setGuideWords(String str) {
            this.guideWords = str;
        }
    }

    /* loaded from: classes2.dex */
    public static class MsgPic {
        String content;
        String pack;
        String url;

        public static MsgPic createPic(String str, String str2, String str3) {
            MsgPic msgPic = new MsgPic();
            msgPic.setUrl(str);
            msgPic.setPack(str2);
            msgPic.setContent(str3);
            return msgPic;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String str) {
            this.url = str;
        }

        public String getPack() {
            return this.pack;
        }

        public void setPack(String str) {
            this.pack = str;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String str) {
            this.content = str;
        }
    }

    public static MessageContentBean createContent() {
        return new MessageContentBean();
    }

    public MessageContentBean addTitle(String str) {
        if (this.titles == null) {
            this.titles = new ArrayList();
        }
        this.titles.add(str);
        return this;
    }

    public MessageContentBean addPic(MsgPic msgPic) {
        if (this.pics == null) {
            this.pics = new ArrayList();
        }
        this.pics.add(msgPic);
        return this;
    }

    public MessageContentBean addButton(MsgButton msgButton) {
        if (this.buttons == null) {
            this.buttons = new ArrayList();
        }
        this.buttons.add(msgButton);
        return this;
    }
}
