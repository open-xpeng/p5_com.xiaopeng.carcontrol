package com.xiaopeng.speech.vui.model;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.xiaopeng.lib.framework.aiassistantmodule.interactive.Constants;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class VuiEventImpl extends VuiEvent {
    private VuiElement data;
    private String userQuery = null;
    private String eventName = null;
    private String props = null;

    public VuiEventImpl(VuiElement vuiElement) {
        this.data = null;
        this.data = vuiElement;
    }

    @Override // com.xiaopeng.vui.commons.model.VuiEvent
    public <T> T getEventValue(VuiEvent vuiEvent) {
        VuiElement hitVuiElement;
        Map map;
        LogUtils.logDebug("getEventValue", new Gson().toJson(vuiEvent));
        if (vuiEvent != null && (hitVuiElement = vuiEvent.getHitVuiElement()) != null && hitVuiElement.getResultActions() != null && !hitVuiElement.getResultActions().isEmpty()) {
            Object obj = (String) hitVuiElement.getResultActions().get(0);
            if ((hitVuiElement.getValues() instanceof LinkedTreeMap) && (map = (Map) hitVuiElement.getValues()) != null) {
                if (map.get(obj) instanceof LinkedTreeMap) {
                    Map map2 = (Map) map.get(obj);
                    if (map2 != null) {
                        if (map2.containsKey("value")) {
                            if (map2.get("value") != null) {
                                return (T) map2.get("value");
                            }
                        } else if (map2.containsKey(Constants.INDEX) && map2.get(Constants.INDEX) != null) {
                            return (T) map2.get(Constants.INDEX);
                        }
                    }
                } else if (map.get(map) != null) {
                    return (T) map.get("value");
                }
            }
        }
        return null;
    }

    public String getUserQuery() {
        return this.userQuery;
    }

    public String getEventName() {
        return this.eventName;
    }

    @Override // com.xiaopeng.vui.commons.model.VuiEvent
    public VuiElement getHitVuiElement() {
        VuiElement vuiElement = this.data;
        if (vuiElement != null) {
            if (vuiElement.getResultActions() != null && !this.data.getResultActions().isEmpty()) {
                return this.data;
            }
            List<VuiElement> hitVuiElements = getHitVuiElements(this.data.getElements());
            if (hitVuiElements == null || hitVuiElements.isEmpty()) {
                return null;
            }
            return hitVuiElements.get(0);
        }
        return null;
    }

    public VuiElement getMetaData() {
        return this.data;
    }

    public String getProps() {
        return this.props;
    }

    public void setUserQuery(String str) {
        this.userQuery = str;
    }

    public void setEventName(String str) {
        this.eventName = str;
    }

    public void setData(VuiElement vuiElement) {
        this.data = vuiElement;
    }

    public void setProps(String str) {
        this.props = str;
    }

    @Override // com.xiaopeng.vui.commons.model.VuiEvent
    public List<VuiElement> getHitVuiElements(List<VuiElement> list) {
        if (list == null || list.isEmpty() || isLeafNode(list.get(0))) {
            return list;
        }
        if (list.get(0) == null) {
            return null;
        }
        return getHitVuiElements(list.get(0).getElements());
    }

    private boolean isLeafNode(VuiElement vuiElement) {
        return (vuiElement == null || vuiElement.getResultActions() == null || vuiElement.getResultActions().isEmpty()) ? false : true;
    }
}
