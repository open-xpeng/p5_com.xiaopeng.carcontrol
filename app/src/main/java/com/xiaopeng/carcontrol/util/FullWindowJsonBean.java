package com.xiaopeng.carcontrol.util;

/* loaded from: classes2.dex */
public class FullWindowJsonBean {
    private String component;
    private Boolean enable;
    private Boolean fullscreen;
    private Integer id;
    private String label;
    private Integer mode;
    private String packageName;
    private Boolean physical;
    private Boolean resizing;
    private Integer screenId;
    private Integer sharedId;
    private Integer state;
    private String token;
    private Integer type;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSharedId() {
        return this.sharedId;
    }

    public void setSharedId(Integer sharedId) {
        this.sharedId = sharedId;
    }

    public Integer getScreenId() {
        return this.screenId;
    }

    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMode() {
        return this.mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Boolean getEnable() {
        return this.enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getResizing() {
        return this.resizing;
    }

    public void setResizing(Boolean resizing) {
        this.resizing = resizing;
    }

    public Boolean getFullscreen() {
        return this.fullscreen;
    }

    public void setFullscreen(Boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public Boolean getPhysical() {
        return this.physical;
    }

    public void setPhysical(Boolean physical) {
        this.physical = physical;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getComponent() {
        return this.component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
