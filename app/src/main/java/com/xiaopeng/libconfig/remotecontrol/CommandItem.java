package com.xiaopeng.libconfig.remotecontrol;

/* loaded from: classes2.dex */
public class CommandItem {
    private int cmd_type;
    private float cmd_value;
    private String fileUrl;

    public int getCmd_type() {
        return this.cmd_type;
    }

    public void setCmd_type(int i) {
        this.cmd_type = i;
    }

    public float getCmd_value() {
        return this.cmd_value;
    }

    public void setCmd_value(float f) {
        this.cmd_value = f;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String str) {
        this.fileUrl = str;
    }

    public String toString() {
        return "CommandItem{cmd_type=" + this.cmd_type + ", cmd_value=" + this.cmd_value + ", fileUrl=" + this.fileUrl + '}';
    }
}
