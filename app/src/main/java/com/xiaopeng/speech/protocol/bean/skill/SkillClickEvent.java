package com.xiaopeng.speech.protocol.bean.skill;

import com.xiaopeng.speech.common.util.DontProguardClass;

@DontProguardClass
/* loaded from: classes2.dex */
public class SkillClickEvent {
    public static final String ACTION_CLICK_TIP = "skill_action_click_tip";
    public String action;
    public String text;

    public SkillClickEvent(String str, String str2) {
        this.action = str;
        this.text = str2;
    }
}
