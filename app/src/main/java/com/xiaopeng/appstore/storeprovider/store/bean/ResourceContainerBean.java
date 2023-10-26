package com.xiaopeng.appstore.storeprovider.store.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ResourceContainerBean implements Parcelable {
    public static final Parcelable.Creator<ResourceContainerBean> CREATOR = new Parcelable.Creator<ResourceContainerBean>() { // from class: com.xiaopeng.appstore.storeprovider.store.bean.ResourceContainerBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceContainerBean createFromParcel(Parcel parcel) {
            return new ResourceContainerBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceContainerBean[] newArray(int i) {
            return new ResourceContainerBean[i];
        }
    };
    private int currentPage;
    private List<ResourceBean> mResourceBeanList;
    private int pageCount;
    private int totalCount;
    private int totalPage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ResourceContainerBean() {
        this.mResourceBeanList = new ArrayList();
    }

    protected ResourceContainerBean(Parcel parcel) {
        this.mResourceBeanList = new ArrayList();
        this.totalCount = parcel.readInt();
        this.pageCount = parcel.readInt();
        this.totalPage = parcel.readInt();
        this.currentPage = parcel.readInt();
        this.mResourceBeanList = parcel.createTypedArrayList(ResourceBean.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.totalCount);
        parcel.writeInt(this.pageCount);
        parcel.writeInt(this.totalPage);
        parcel.writeInt(this.currentPage);
        parcel.writeTypedList(this.mResourceBeanList);
    }

    private int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int i) {
        this.totalCount = i;
    }

    private int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int i) {
        this.pageCount = i;
    }

    private int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int i) {
        this.totalPage = i;
    }

    private int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int i) {
        this.currentPage = i;
    }

    public List<ResourceBean> getResourceBeanList() {
        return this.mResourceBeanList;
    }

    public void setResourceBeanList(List<ResourceBean> list) {
        this.mResourceBeanList = list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ResourceContainerBean{");
        sb.append("totalCount=").append(this.totalCount);
        sb.append(", pageCount=").append(this.pageCount);
        sb.append(", totalPage=").append(this.totalPage);
        sb.append(", currentPage=").append(this.currentPage);
        sb.append(", mResourceBeanList=").append(this.mResourceBeanList);
        sb.append('}');
        return sb.toString();
    }
}
