package com.xiaopeng.carcontrol.bean;

/* loaded from: classes.dex */
public class SyncGroupEventMsg {
    public static final String TYPE_SYNC_ACCOUNT_CHANGE = "TYPE_SYNC_ACCOUNT_CHANGE";
    public static final String TYPE_SYNC_GROUP_CREATED = "TYPE_SYNC_GROUP_CREATED";
    public static final String TYPE_SYNC_GROUP_SELECTED = "TYPE_SYNC_GROUP_SELECTED";
    private int index;
    private boolean isChecked;
    private String name;
    private long uid;
    private int value;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("SyncGroupEventMsg{");
        stringBuffer.append("name='").append(this.name).append('\'');
        stringBuffer.append(", index=").append(this.index);
        stringBuffer.append(", isChecked=").append(this.isChecked);
        stringBuffer.append(", value=").append(this.value);
        stringBuffer.append(", uid=").append(this.uid);
        stringBuffer.append('}');
        return stringBuffer.toString();
    }
}
