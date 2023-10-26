package com.xiaopeng.libconfig.ipc.bean;

import com.xiaopeng.libconfig.ipc.bean.MessageContentBean;
import com.xiaopeng.xpmeditation.util.TimeUtil;
import java.util.List;
import java.util.UUID;

/* loaded from: classes2.dex */
public class MessageCenterBean {
    public static final int CONTENT_POSITION_NOTIFY = 2;
    public static final int CONTENT_POSITION_NOTIFY_BOX = 1;
    public static final int OPPORTUNITY_GETOFF_SCENE = 4;
    public static final int OPPORTUNITY_GETON_MOMENT = 3;
    public static final int OPPORTUNITY_GETON_SCENE = 1;
    public static final int OPPORTUNITY_OTHER = 0;
    public static final int OPPORTUNITY_RUNNING_SCENE = 2;
    public static final int OPSITION_ACCOUNT = 9;
    public static final int OPSITION_AI = 1;
    public static final int OPSITION_AIOT_SERVICE = 54;
    public static final int OPSITION_APPSTORE = 49;
    public static final int OPSITION_CAR_CONTROL = 18;
    public static final int OPSITION_CHARGE = 5;
    public static final int OPSITION_CONFIGURE = 12;
    public static final int OPSITION_DC = 14;
    public static final int OPSITION_DIAGNOSTIC = 20;
    public static final int OPSITION_MUSIC = 16;
    public static final int OPSITION_NAV = 2;
    public static final int OPSITION_OTA = 11;
    public static final int OPSITION_PRIVACY_SERVICE = 17;
    public static final int OPSITION_RESOURCE_CENTER = 15;
    public static final int OPSITION_SETTINGS = 51;
    public static final int OPSITION_SR_HDPKMAP = 19;
    public static final int OPSITION_WASH = 3;
    public static final int OPSITION_XUI_SERVICE = 56;
    public static final int TYPE_ACC = 12;
    public static final int TYPE_AUDIOBOOKS = 3;
    public static final int TYPE_BIRTHDAY = 11;
    public static final int TYPE_EASTER_EGG = 100;
    public static final int TYPE_HAZE = 20;
    public static final int TYPE_HEAVY_FOG = 19;
    public static final int TYPE_HEAVY_RAINS = 18;
    public static final int TYPE_HEAVY_SNOWFALL = 17;
    public static final int TYPE_MAP = 10;
    public static final int TYPE_MAP_PARK = 14;
    public static final int TYPE_MAP_PATH = 13;
    public static final int TYPE_MUSIC = 2;
    public static final int TYPE_MUSIC_VIP = 22;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_OTA_FAILED = 7;
    public static final int TYPE_OVERTIME = 15;
    public static final int TYPE_PATH_SELECT = 16;
    public static final int TYPE_PM_25 = 9;
    public static final int TYPE_RAINS = 21;
    public static final int TYPE_RECHARGE = 4;
    public static final int TYPE_RECHARGE_FULL = 8;
    public static final int TYPE_SUCCESS = 6;
    public static final int TYPE_WELCOME = 5;
    private int bizType;
    private int carState;
    private String content;
    private Content contentObject;
    private String messageId;
    private int messageType;
    private String packName;
    private int priority;
    private int read_state;
    private int scene;
    private long ts;
    private int type;
    private int uid;
    private long validEndTs;
    private long validStartTs;

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String str) {
        this.messageId = str;
    }

    public int getMessageType() {
        return this.messageType;
    }

    public void setMessageType(int i) {
        this.messageType = i;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public long getValidStartTs() {
        return this.validStartTs;
    }

    public void setValidStartTs(long j) {
        this.validStartTs = j;
    }

    public long getValidEndTs() {
        return this.validEndTs;
    }

    public void setValidEndTs(long j) {
        this.validEndTs = j;
    }

    public long getTs() {
        return this.ts;
    }

    public void setTs(long j) {
        this.ts = j;
    }

    public int getBizType() {
        return this.bizType;
    }

    public void setBizType(int i) {
        this.bizType = i;
    }

    public int getScene() {
        return this.scene;
    }

    public void setScene(int i) {
        this.scene = i;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int i) {
        this.priority = i;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int i) {
        this.uid = i;
    }

    public String getPackName() {
        return this.packName;
    }

    public void setPackName(String str) {
        this.packName = str;
    }

    public int getCarState() {
        return this.carState;
    }

    public void setCarState(int i) {
        this.carState = i;
    }

    public int getRead_state() {
        return this.read_state;
    }

    public void setRead_state(int i) {
        this.read_state = i;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public Content getContentObject() {
        return this.contentObject;
    }

    public void setContentObject(Content content) {
        this.contentObject = content;
    }

    public MessageContentBean getContentBean() {
        Content content = this.contentObject;
        if (content == null) {
            return null;
        }
        return content.getContent();
    }

    public List<MessageContentBean.MsgButton> getButtonList() {
        if (getContentBean() != null) {
            return getContentBean().getButtons();
        }
        return null;
    }

    public MessageContentBean.MsgButton getButton(int i) {
        if (getContentBean() != null) {
            return getContentBean().getButton(i);
        }
        return null;
    }

    /* loaded from: classes2.dex */
    public static class Content {
        MessageContentBean content;
        String contentStr;
        int opportunity;
        int position;
        int type;

        public int getType() {
            return this.type;
        }

        public void setType(int i) {
            this.type = i;
        }

        public int getPosition() {
            return this.position;
        }

        public void setPosition(int i) {
            this.position = i;
        }

        public int getOpportunity() {
            return this.opportunity;
        }

        public void setOpportunity(int i) {
            this.opportunity = i;
        }

        public MessageContentBean getContent() {
            return this.content;
        }

        public void setContent(MessageContentBean messageContentBean) {
            this.content = messageContentBean;
        }

        public String getContentStr() {
            return this.contentStr;
        }

        public void setContentStr(String str) {
            this.contentStr = str;
        }
    }

    public static MessageCenterBean create(int i, MessageContentBean messageContentBean) {
        MessageCenterBean messageCenterBean = new MessageCenterBean();
        messageCenterBean.setMessageId(UUID.randomUUID().toString());
        long currentTimeMillis = System.currentTimeMillis();
        messageCenterBean.setValidStartTs(currentTimeMillis);
        messageCenterBean.setValidEndTs(TimeUtil.ONE_HOUR + currentTimeMillis);
        messageCenterBean.setMessageType(101);
        messageCenterBean.setType(1);
        messageCenterBean.setTs(currentTimeMillis);
        messageCenterBean.setBizType(i);
        messageCenterBean.setPriority(1);
        Content content = new Content();
        content.setType(1);
        content.setOpportunity(0);
        content.setPosition(1);
        content.setContent(messageContentBean);
        messageCenterBean.setContentObject(content);
        return messageCenterBean;
    }

    public String toString() {
        return "MessageCenterBean{messageId='" + this.messageId + "'}";
    }
}
