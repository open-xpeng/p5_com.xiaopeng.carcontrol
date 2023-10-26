package com.xiaopeng.carcontrol.bean.xpilot;

import com.xiaopeng.carcontrol.util.ResUtils;

/* loaded from: classes.dex */
public class XPilotTabItem extends XPilotItem<Integer> {
    private final String[] mTabItems;

    public XPilotTabItem(int index, String key, int titleResId, int descResId, String[] titleItems) {
        this(index, key, titleResId, descResId, titleItems, 0, 0, 0, 0, 0, 0);
    }

    public XPilotTabItem(int index, String key, int titleResId, int descResId, String[] titleItems, int manualIdx, int helpPic1ResId, int helpTxt1ResId, int helpPic2ResId, int helpTxt2ResId, int keywordId) {
        super(index, key, 3, titleResId, descResId, 0, manualIdx, keywordId);
        if (titleItems == null || titleItems.length == 0) {
            throw new IllegalArgumentException("Title item array is empty");
        }
        this.mTabItems = titleItems;
        this.mHelpPic1ResId = helpPic1ResId;
        this.mHelpTxt1 = ResUtils.getString(helpTxt1ResId);
        this.mHelpPic2ResId = helpPic2ResId;
        this.mHelpTxt2 = ResUtils.getString(helpTxt2ResId);
    }

    public String[] getTabItems() {
        return this.mTabItems;
    }
}
