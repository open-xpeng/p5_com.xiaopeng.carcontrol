package com.xiaopeng.carcontrol.bean.xpilot.map;

/* loaded from: classes.dex */
public class CngpMapCityItem extends CngpMapItem {
    public CngpMapCityItem(int id, String name, int state, int downloadedPercentage) {
        super(id, 2, name, -1, state, downloadedPercentage);
    }

    public CngpMapCityItem(CngpMapItem item) {
        super(item);
    }
}
