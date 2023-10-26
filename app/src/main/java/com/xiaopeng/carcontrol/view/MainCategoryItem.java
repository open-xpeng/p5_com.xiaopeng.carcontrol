package com.xiaopeng.carcontrol.view;

/* loaded from: classes2.dex */
public class MainCategoryItem {
    private final Class mFragmentClazz;
    private final int mIcon;
    private final int mIndex;
    private final int mPos;
    private final int mTitle;
    private final String mVuiSceneId;

    public int getItemPos() {
        return this.mPos;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public int getTitle() {
        return this.mTitle;
    }

    public int getIcon() {
        return this.mIcon;
    }

    public Class getFragmentClazz() {
        return this.mFragmentClazz;
    }

    public String getVuiSceneId() {
        return this.mVuiSceneId;
    }

    MainCategoryItem(int index, int title, int icon, Class fragmentClazz) {
        this.mPos = index;
        this.mIndex = index;
        this.mTitle = title;
        this.mIcon = icon;
        this.mFragmentClazz = fragmentClazz;
        this.mVuiSceneId = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MainCategoryItem(int position, int index, int title, int icon, Class fragmentClazz, String vuiSceneId) {
        this.mPos = position;
        this.mIndex = index;
        this.mTitle = title;
        this.mIcon = icon;
        this.mFragmentClazz = fragmentClazz;
        this.mVuiSceneId = vuiSceneId;
    }

    public String toString() {
        return this.mFragmentClazz.getSimpleName();
    }
}
