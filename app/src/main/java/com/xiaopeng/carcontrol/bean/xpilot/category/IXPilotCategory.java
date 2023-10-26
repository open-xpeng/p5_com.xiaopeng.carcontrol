package com.xiaopeng.carcontrol.bean.xpilot.category;

import com.xiaopeng.carcontrol.bean.xpilot.XPilotItem;
import java.util.List;

/* loaded from: classes.dex */
public interface IXPilotCategory {
    XPilotItem getItemByKey(String Key);

    List<XPilotItem> getXPilotList();

    List<String> getXPilotSrrRelatedList();
}
