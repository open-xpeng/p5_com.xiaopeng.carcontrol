package com.xiaopeng.carcontrol.bean.xpilot.category;

import com.xiaopeng.carcontrol.bean.xpilot.XPilotItem;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class XPilotCategoryManager {
    private static final Map<String, IXPilotCategory> sXPilotCategoryList = new ConcurrentHashMap();

    public static XPilotCategoryManager getInstance() {
        return SingleHolder.sInstance;
    }

    public void init(IXPilotCategory instance) {
        sXPilotCategoryList.put(CarStatusUtils.getHardwareCarType(), instance);
        if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
            handleSrrMissFunc(true);
        }
    }

    private IXPilotCategory getXPilotCategory() {
        return sXPilotCategoryList.get(CarStatusUtils.getHardwareCarType());
    }

    public void handleSrrMissFunc(boolean srrMiss) {
        List<String> xPilotSrrRelatedList;
        IXPilotCategory xPilotCategory = getXPilotCategory();
        if (xPilotCategory == null || (xPilotSrrRelatedList = xPilotCategory.getXPilotSrrRelatedList()) == null) {
            return;
        }
        for (String str : xPilotSrrRelatedList) {
            XPilotItem itemByKey = xPilotCategory.getItemByKey(str);
            if (itemByKey != null) {
                itemByKey.setFunAvailable(!srrMiss);
            }
        }
    }

    public List<XPilotItem> getXPilotList() {
        return getXPilotCategory().getXPilotList();
    }

    public XPilotItem getItemByKey(String key) {
        return getXPilotCategory().getItemByKey(key);
    }

    /* loaded from: classes.dex */
    private static class SingleHolder {
        private static final XPilotCategoryManager sInstance = new XPilotCategoryManager();

        private SingleHolder() {
        }
    }
}
