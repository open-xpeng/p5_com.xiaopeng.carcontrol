package com.xiaopeng.carcontrol.bean.selfcheck;

import com.xiaopeng.carcontrol.viewmodel.selfcheck.SelfCheckUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CheckCategoryItem extends CheckBaseItem {
    private String mCategory;
    private String mEcu;
    private int mFaultLevel;
    private List<CheckDetailItem> mItems;

    @Override // com.xiaopeng.carcontrol.bean.selfcheck.CheckBaseItem
    public void startCheck() {
    }

    public CheckCategoryItem() {
        this.mItems = new ArrayList();
        this.mFaultLevel = -1;
    }

    public CheckCategoryItem(String category, String ecu) {
        this.mItems = new ArrayList();
        this.mFaultLevel = -1;
        this.mCategory = category;
        this.mEcu = ecu;
    }

    private CheckCategoryItem(Builder builder) {
        this.mItems = new ArrayList();
        this.mFaultLevel = -1;
        this.mTitle = builder.mTitle;
        this.mCategory = builder.mCategory;
        setItems(builder.mItems);
        this.mEcu = builder.mEcu;
    }

    public List<CheckDetailItem> getItems() {
        return this.mItems;
    }

    public void setItems(List<CheckDetailItem> items) {
        this.mItems = items;
    }

    public void addItem(CheckDetailItem item) {
        this.mItems.add(item);
    }

    public String getEcu() {
        return this.mEcu;
    }

    public void setEcu(String ecu) {
        this.mEcu = ecu;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private String mCategory;
        private String mEcu;
        private List<CheckDetailItem> mItems = new ArrayList();
        private String mTitle;

        public Builder title(String val) {
            this.mTitle = val;
            return this;
        }

        public Builder category(String val) {
            this.mCategory = val;
            return this;
        }

        public Builder items(List<CheckDetailItem> val) {
            this.mItems = val;
            return this;
        }

        public Builder ecu(String val) {
            this.mEcu = val;
            return this;
        }

        public Builder addItem(CheckDetailItem item) {
            this.mItems.add(item);
            return this;
        }

        public CheckCategoryItem build() {
            return new CheckCategoryItem(this);
        }
    }

    public int getCheckSize() {
        return this.mItems.size();
    }

    public ArrayList<CheckErrorDetail> generatorResult() {
        ArrayList<CheckErrorDetail> arrayList = new ArrayList<>();
        for (CheckDetailItem checkDetailItem : this.mItems) {
            if (checkDetailItem.hasIssue()) {
                CheckErrorDetail checkErrorDetail = new CheckErrorDetail();
                checkErrorDetail.setSys(this.mCategory);
                checkErrorDetail.setEcu(this.mEcu);
                checkErrorDetail.setEcuName(this.mTitle);
                checkErrorDetail.setKey(checkDetailItem.getKey());
                checkErrorDetail.setLevel(SelfCheckUtil.level2Str(getLevel()));
                checkErrorDetail.setErrorName(checkDetailItem.getTitle());
                for (CheckResult checkResult : checkDetailItem.getResult().getDetailResult()) {
                    checkErrorDetail.addResultItem(checkResult.getSigName(), checkResult.getSigVal());
                }
                arrayList.add(checkErrorDetail);
            }
        }
        return arrayList;
    }

    @Override // com.xiaopeng.carcontrol.bean.selfcheck.CheckBaseItem
    public int getLevel() {
        int i = 0;
        for (CheckDetailItem checkDetailItem : this.mItems) {
            if (checkDetailItem.getLevel() > i) {
                i = checkDetailItem.getLevel();
            }
        }
        return i;
    }

    public int getErrorLevel() {
        if (this.mFaultLevel == -1) {
            this.mFaultLevel = 0;
            for (CheckDetailItem checkDetailItem : this.mItems) {
                if (checkDetailItem.hasIssue()) {
                    int level = checkDetailItem.getLevel();
                    int i = this.mFaultLevel;
                    if (level > i) {
                        i = checkDetailItem.getLevel();
                    }
                    this.mFaultLevel = i;
                }
            }
        }
        return this.mFaultLevel;
    }
}
