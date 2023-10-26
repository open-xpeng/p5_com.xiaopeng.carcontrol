package com.xiaopeng.vui.commons.model;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.vui.commons.VuiActType;
import com.xiaopeng.vui.commons.VuiAction;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class VuiElement implements Cloneable {
    public JsonObject actions;
    public AnimationObj animation;
    private List<VuiElement> elements;
    private Boolean enabled;
    public String fatherElementId;
    public String fatherLabel;
    private String feedbackType;
    public String id;
    public String label;
    private Boolean layoutLoadable;
    private String mode;
    private int position;
    private int priority;
    private JsonObject props;
    private String resourceName;
    public List<String> resultActions;
    private long timestamp;
    public String type;
    public Object values;
    private Boolean visible;

    public void setValues(Object obj) {
        this.values = obj;
    }

    public void setLayoutLoadable(Boolean bool) {
        this.layoutLoadable = bool;
    }

    public Object getValues() {
        return this.values;
    }

    public Boolean isLayoutLoadable() {
        return this.layoutLoadable;
    }

    public AnimationObj getAnimation() {
        return this.animation;
    }

    public void setAnimation(AnimationObj animationObj) {
        this.animation = animationObj;
    }

    public void setResultActions(List<String> list) {
        this.resultActions = list;
    }

    public List<String> getResultActions() {
        return this.resultActions;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String str) {
        this.mode = str;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public void setResourceName(String str) {
        this.resourceName = str;
    }

    public void setFatherElementId(String str) {
        this.fatherElementId = str;
    }

    public void setActions(String str) {
        this.actions = getElementAction(str);
    }

    public void setVisible(Boolean bool) {
        this.visible = bool;
    }

    public void setEnabled(Boolean bool) {
        this.enabled = bool;
    }

    public void setFeedbackType(String str) {
        this.feedbackType = str;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public void setPriority(int i) {
        this.priority = i;
    }

    public int getPriority() {
        return this.priority;
    }

    public JsonObject getProps() {
        return this.props;
    }

    public String getId() {
        return this.id;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public String getLabel() {
        return this.label;
    }

    public String getFatherLabel() {
        return this.fatherLabel;
    }

    public String getType() {
        return this.type;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public void setFatherLabel(String str) {
        this.fatherLabel = str;
    }

    public void setProps(JsonObject jsonObject) {
        this.props = jsonObject;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getFatherElementId() {
        return this.fatherElementId;
    }

    public JsonObject getActions() {
        return this.actions;
    }

    public Boolean isVisible() {
        return this.visible;
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    public String getFeedbackType() {
        return this.feedbackType;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public List<VuiElement> getElements() {
        return this.elements;
    }

    public void setElements(List<VuiElement> list) {
        this.elements = list;
    }

    public VuiElement() {
        this.id = null;
        this.fatherElementId = null;
        this.label = "";
        this.fatherLabel = null;
        this.values = null;
        this.props = null;
        this.type = null;
        this.actions = null;
        this.layoutLoadable = null;
        this.resourceName = null;
        this.mode = null;
        this.resultActions = null;
        this.animation = null;
        this.position = -1;
        this.visible = null;
        this.enabled = null;
        this.feedbackType = null;
        this.timestamp = -1L;
        this.priority = -1;
        this.elements = null;
    }

    public String toString() {
        StringBuilder append = new StringBuilder().append("VuiElement{id='").append(this.id).append('\'').append(", fatherElementId='").append(this.fatherElementId).append('\'').append(", label='").append(this.label).append('\'').append(", fatherLabel='").append(this.fatherLabel).append('\'').append(", values=").append(this.values).append(", props=").append(this.props).append(", type='").append(this.type).append('\'').append(", actions=").append(this.actions).append(", layoutLoadable=").append(this.layoutLoadable).append(", resourceName='").append(this.resourceName).append('\'').append(", mode='").append(this.mode).append('\'').append(", resultActions=").append(this.resultActions).append(", animation=");
        AnimationObj animationObj = this.animation;
        return append.append(animationObj == null ? "" : animationObj.toString()).append(", position=").append(this.position).append(", visible=").append(this.visible).append(", enabled=").append(this.enabled).append(", feedbackType='").append(this.feedbackType).append('\'').append(", timestamp=").append(this.timestamp).append(", priority=").append(this.priority).append(", elements=").append(this.elements).append('}').toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof VuiElement) {
            VuiElement vuiElement = (VuiElement) obj;
            if (isEqualsOfString(this.type, vuiElement.getType()) && isEqualsOfString(this.label, vuiElement.getLabel()) && isEqualsOfString(this.fatherElementId, vuiElement.getFatherElementId()) && isEqualsOfString(this.fatherLabel, vuiElement.getFatherLabel()) && isEqualsOfString(this.mode, vuiElement.getMode()) && isEqualsOfString(this.resourceName, vuiElement.getResourceName()) && this.position == vuiElement.getPosition() && this.priority == vuiElement.getPriority() && isEqualsOfBoolean(this.layoutLoadable, vuiElement.layoutLoadable) && isEqualsOfBoolean(this.visible, vuiElement.visible) && isEqualsOfJson(this.actions, vuiElement.actions) && isEqualsOfJson(this.props, vuiElement.props) && isEqualsOfJson(this.values, vuiElement.values)) {
                if (this.elements == null && vuiElement.getElements() == null) {
                    return true;
                }
                if (this.elements == null || vuiElement.getElements() == null || this.elements.size() != vuiElement.getElements().size()) {
                    return false;
                }
                for (int i = 0; i < this.elements.size(); i++) {
                    if (!this.elements.get(i).equals(vuiElement.elements.get(i))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean isEqualsOfString(String str, String str2) {
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            return true;
        }
        return (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || !str.equals(str2)) ? false : true;
    }

    private boolean isEqualsOfBoolean(Boolean bool, Boolean bool2) {
        if (bool == null && bool2 == null) {
            return true;
        }
        return (bool == null || bool2 == null || bool.booleanValue() != bool2.booleanValue()) ? false : true;
    }

    private boolean isEqualsOfJson(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            return true;
        }
        return (obj == null || obj2 == null || !obj.equals(obj2)) ? false : true;
    }

    public VuiElement(Builder builder) {
        this.id = null;
        this.fatherElementId = null;
        this.label = "";
        this.fatherLabel = null;
        this.values = null;
        this.props = null;
        this.type = null;
        this.actions = null;
        this.layoutLoadable = null;
        this.resourceName = null;
        this.mode = null;
        this.resultActions = null;
        this.animation = null;
        this.position = -1;
        this.visible = null;
        this.enabled = null;
        this.feedbackType = null;
        this.timestamp = -1L;
        this.priority = -1;
        this.elements = null;
        this.type = builder.type;
        this.label = builder.label;
        this.id = builder.id;
        this.timestamp = builder.timeStamp;
        this.actions = builder.actions;
        this.visible = builder.visible;
        this.enabled = builder.enable;
        this.props = builder.props;
        this.values = builder.values;
        this.resourceName = builder.resourceName;
    }

    public static JsonObject getElementAction(String str) {
        if (str == null) {
            return null;
        }
        String[] split = str.split("\\|");
        JSONObject jSONObject = new JSONObject();
        List<String> vuiActionList = VuiAction.getVuiActionList();
        List<String> vuiActTypeList = VuiActType.getVuiActTypeList();
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains(QuickSettingConstants.JOINER)) {
                int indexOf = split[i].indexOf(QuickSettingConstants.JOINER);
                String substring = split[i].substring(0, indexOf);
                if (vuiActionList.contains(substring)) {
                    String[] split2 = split[i].substring(indexOf + 1).split(",");
                    if (split2.length < 2) {
                        return null;
                    }
                    try {
                        if (vuiActTypeList.contains(split2[0])) {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("actType", split2[0]);
                            jSONObject2.put("bizType", split2[1]);
                            jSONObject.put(substring, jSONObject2);
                        }
                    } catch (Exception unused) {
                    }
                }
                return null;
            } else if (!vuiActionList.contains(split[i])) {
                return null;
            } else {
                jSONObject.put(split[i], new JSONObject());
            }
        }
        return (JsonObject) new Gson().fromJson(jSONObject.toString(), (Class<Object>) JsonObject.class);
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private String type = null;
        private String label = "";
        private String id = null;
        private long timeStamp = System.currentTimeMillis();
        private JsonObject actions = null;
        private Boolean visible = null;
        private Boolean enable = null;
        private Object values = null;
        private JsonObject props = null;
        private String resourceName = null;

        public Builder enable(boolean z) {
            return this;
        }

        public Builder type(String str) {
            this.type = str;
            return this;
        }

        public Builder label(String str) {
            this.label = str;
            return this;
        }

        public Builder id(String str) {
            this.id = str;
            return this;
        }

        public Builder timestamp(long j) {
            this.timeStamp = j;
            return this;
        }

        public Builder action(String str) {
            this.actions = VuiElement.getElementAction(str);
            return this;
        }

        public Builder visible(int i) {
            if (i != 0) {
                this.visible = false;
            }
            return this;
        }

        public Builder value(Object obj) {
            this.values = obj;
            return this;
        }

        public Builder props(JsonObject jsonObject) {
            this.props = jsonObject;
            return this;
        }

        public Builder resourceName(String str) {
            this.resourceName = str;
            return this;
        }

        public VuiElement build() {
            return new VuiElement(this);
        }
    }

    /* renamed from: clone */
    public VuiElement m116clone() {
        try {
            return (VuiElement) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
