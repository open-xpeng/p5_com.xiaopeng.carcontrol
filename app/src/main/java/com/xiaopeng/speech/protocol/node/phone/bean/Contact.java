package com.xiaopeng.speech.protocol.node.phone.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class Contact implements Serializable, Cloneable {
    private String name;
    private final List<PhoneInfo> phoneInfos = new ArrayList();

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public List<PhoneInfo> getPhoneInfos() {
        return this.phoneInfos;
    }

    public void addPhoneInfo(PhoneInfo phoneInfo) {
        this.phoneInfos.add(phoneInfo);
    }

    /* loaded from: classes2.dex */
    public static class PhoneInfo {
        private String flag;
        private String id;
        private String name;
        private String number;

        public String getNumber() {
            return this.number;
        }

        public void setNumber(String str) {
            this.number = str;
        }

        public String getFlag() {
            return this.flag;
        }

        public void setFlag(String str) {
            this.flag = str;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String str) {
            this.id = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }
    }
}
