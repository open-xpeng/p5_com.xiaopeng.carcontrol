package com.xiaopeng.speech.protocol.bean.stats;

import com.xiaopeng.speech.common.util.DontProguardClass;

@DontProguardClass
/* loaded from: classes2.dex */
public abstract class StatCommonBean {
    public static final int EVENT_RECOMMEND_ID = 401;
    public static final int EVENT_SCENE_SETTING_ID = 601;
    public static final int EVENT_SCENE_SETTING_SWITCH_ID = 603;
    public static final int EVENT_Skill_ID = 501;
    private float driveTemp;
    public int eventId;
    public String hwVersion;
    public String speechVersionCode;
    public String speechVersionName;
    public float speed;
    public long startTime = System.currentTimeMillis();
    public String userId;

    public StatCommonBean(int i) {
        this.eventId = i;
    }

    public StatCommonBean addhwVersion(String str) {
        this.hwVersion = str;
        return this;
    }

    public StatCommonBean addSpeechVersionName(String str) {
        this.speechVersionName = str;
        return this;
    }

    public StatCommonBean addHardwareId(String str) {
        this.userId = str;
        return this;
    }

    public StatCommonBean addhAppVersionCode(String str) {
        this.speechVersionCode = str;
        return this;
    }

    public void setSpeed(float f) {
        this.speed = f;
    }

    public void setDriveTemp(float f) {
        this.driveTemp = f;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long j) {
        this.startTime = j;
    }
}
