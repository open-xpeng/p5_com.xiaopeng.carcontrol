package com.xiaopeng.appstore.storeprovider;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ResourceContainer implements Parcelable {
    public static final Parcelable.Creator<ResourceContainer> CREATOR = new Parcelable.Creator<ResourceContainer>() { // from class: com.xiaopeng.appstore.storeprovider.ResourceContainer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceContainer createFromParcel(Parcel parcel) {
            return new ResourceContainer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceContainer[] newArray(int i) {
            return new ResourceContainer[i];
        }
    };
    private int currentPage;
    private List<Resource> mResourceList;
    private int pageCount;
    private int totalCount;
    private int totalPage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ResourceContainer() {
        this.mResourceList = new ArrayList();
    }

    public String toString() {
        return "ResContainer{totalCount=" + this.totalCount + ", pageCount=" + this.pageCount + ", totalPage=" + this.totalPage + ", currentPage=" + this.currentPage + ", resList=" + this.mResourceList + '}';
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int i) {
        this.totalCount = i;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int i) {
        this.pageCount = i;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int i) {
        this.totalPage = i;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int i) {
        this.currentPage = i;
    }

    public List<Resource> getResourceList() {
        return this.mResourceList;
    }

    public void setResourceList(List<Resource> list) {
        this.mResourceList = list;
    }

    protected ResourceContainer(Parcel parcel) {
        this.mResourceList = new ArrayList();
        this.totalCount = parcel.readInt();
        this.pageCount = parcel.readInt();
        this.totalPage = parcel.readInt();
        this.currentPage = parcel.readInt();
        this.mResourceList = parcel.createTypedArrayList(Resource.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.totalCount);
        parcel.writeInt(this.pageCount);
        parcel.writeInt(this.totalPage);
        parcel.writeInt(this.currentPage);
        parcel.writeTypedList(this.mResourceList);
    }
}
