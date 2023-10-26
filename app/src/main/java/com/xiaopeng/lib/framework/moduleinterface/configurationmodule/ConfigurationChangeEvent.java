package com.xiaopeng.lib.framework.moduleinterface.configurationmodule;

import java.util.List;

/* loaded from: classes2.dex */
public class ConfigurationChangeEvent {
    private List<IConfigurationData> mChangeList;

    public List<IConfigurationData> getChangeList() {
        return this.mChangeList;
    }

    public void setChangeList(List<IConfigurationData> list) {
        this.mChangeList = list;
    }

    public String toString() {
        StringBuilder append = new StringBuilder().append("ConfigurationChangeEvent{list size:");
        List<IConfigurationData> list = this.mChangeList;
        return append.append(list != null ? list.size() : 0).append("}").toString();
    }
}
