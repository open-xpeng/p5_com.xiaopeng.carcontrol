package com.xiaopeng.carcontrol.bean.selfcheck;

import com.xiaopeng.carcontrol.viewmodel.selfcheck.SelfCheckUtil;

/* loaded from: classes.dex */
public class CheckDetailItem extends CheckBaseItem {
    private static final String TAG = "Check_CheckDetailItem";
    private CheckCategoryItem mCategory;
    private String mKey;

    public CheckDetailItem() {
    }

    public CheckDetailItem(String key, int level, String title) {
        this.mKey = key;
        setLevel(level);
        this.mTitle = title;
    }

    private CheckDetailItem(Builder builder) {
        this.mTitle = builder.mTitle;
        setKey(builder.mKey);
        setLevel(builder.mLevel);
        setCategory(builder.mCategory);
    }

    public String getKey() {
        return this.mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public CheckCategoryItem getCategory() {
        return this.mCategory;
    }

    public void setCategory(CheckCategoryItem category) {
        this.mCategory = category;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private CheckCategoryItem mCategory;
        private String mKey;
        private int mLevel;
        private String mTitle;

        public Builder title(String val) {
            this.mTitle = val;
            return this;
        }

        public Builder key(String val) {
            this.mKey = val;
            return this;
        }

        public Builder level(int val) {
            this.mLevel = val;
            return this;
        }

        public Builder category(CheckCategoryItem val) {
            this.mCategory = val;
            return this;
        }

        public CheckDetailItem build() {
            return new CheckDetailItem(this);
        }
    }

    @Override // com.xiaopeng.carcontrol.bean.selfcheck.CheckBaseItem
    public void startCheck() throws Exception {
        super.startCheck();
        setResult(SelfCheckUtil.getCheckResult(this.mKey));
    }
}
