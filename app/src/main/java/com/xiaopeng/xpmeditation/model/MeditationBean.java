package com.xiaopeng.xpmeditation.model;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes2.dex */
public class MeditationBean {
    private DataBean data;

    public DataBean getData() {
        return this.data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    /* loaded from: classes2.dex */
    public static class DataBean implements Serializable {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return this.list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        /* loaded from: classes2.dex */
        public static class ListBean implements Serializable {
            protected int bizId;
            protected String listenUrl;
            protected String picUrl;
            protected String thumbnailUrl;
            protected String title;

            public int getBizId() {
                return this.bizId;
            }

            public void setBizId(int bizId) {
                this.bizId = bizId;
            }

            public String getListenUrl() {
                return this.listenUrl;
            }

            public void setListenUrl(String listenUrl) {
                this.listenUrl = listenUrl;
            }

            public String getPicUrl() {
                return this.picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getThumbnailUrl() {
                return this.thumbnailUrl;
            }

            public void setThumbnailUrl(String thumbnailUrl) {
                this.thumbnailUrl = thumbnailUrl;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String toString() {
                return "ListBean{bizId=" + this.bizId + ", listenUrl='" + this.listenUrl + "', picUrl='" + this.picUrl + "', thumbnailUrl='" + this.thumbnailUrl + "', title='" + this.title + "'}";
            }
        }

        /* loaded from: classes2.dex */
        public static class ListBeanPlus implements Serializable {
            protected int bizId;
            private String detail;
            private String info;
            protected String listenUrl;
            protected String midThumbnail;
            protected String thumbnailUrl;
            protected String title;
            protected String videoUrl;

            public int getBizId() {
                return this.bizId;
            }

            public void setBizId(int bizId) {
                this.bizId = bizId;
            }

            public String getListenUrl() {
                return this.listenUrl;
            }

            public void setListenUrl(String listenUrl) {
                this.listenUrl = listenUrl;
            }

            public String getThumbnailUrl() {
                return this.thumbnailUrl;
            }

            public void setThumbnailUrl(String thumbnailUrl) {
                this.thumbnailUrl = thumbnailUrl;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getInfo() {
                return this.info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getDetail() {
                return this.detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getMidThumbnailUrl() {
                return this.midThumbnail;
            }

            public void setMidThumbnailUrl(String thumbnailUrl) {
                this.midThumbnail = thumbnailUrl;
            }

            public String getVideoUrl() {
                return this.videoUrl;
            }

            public void seVideoUrl(String midThumbnail) {
                this.videoUrl = this.videoUrl;
            }

            public String toString() {
                return "ListBeanPlus{bizId=" + this.bizId + ", listenUrl='" + this.listenUrl + "', videoUrl='" + this.videoUrl + "', thumbnailUrl='" + this.thumbnailUrl + "', midThumbnail='" + this.midThumbnail + "', title='" + this.title + "'}";
            }
        }

        public String toString() {
            return "DataBean{list=" + this.list + '}';
        }

        /* loaded from: classes2.dex */
        public static class SeatBean implements Serializable {
            public int effect;
            public int id;
            public int intensity;

            public String toString() {
                return "SeatBean { id=" + this.id + ",intensity=" + this.intensity + ",effect=" + this.effect + "}";
            }
        }
    }

    public String toString() {
        return "MeditationBean{data=" + this.data + '}';
    }
}
