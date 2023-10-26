package com.xiaopeng.xpmeditation.moreless;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes2.dex */
public class MorelessInfo implements Serializable {
    private String cover_primary_color;
    private String cover_url;
    private Description description;
    private DescriptionAuthor descriptionAuthor;
    private boolean free;
    private String id;
    private boolean is_51;
    private boolean is_71;
    private Name name;
    private int sound_size_in_bytes;
    private String sound_url;
    private Subtitle subtitle;
    private List<Tags> tags;
    private String type;
    private String video_cover_url;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Name getName() {
        return this.name;
    }

    public void setSubtitle(Subtitle subtitle) {
        this.subtitle = subtitle;
    }

    public Subtitle getSubtitle() {
        return this.subtitle;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Description getDescription() {
        return this.description;
    }

    public void setDescriptionAuthor(DescriptionAuthor descriptionAuthor) {
        this.descriptionAuthor = descriptionAuthor;
    }

    public DescriptionAuthor getDescriptionAuthor() {
        return this.descriptionAuthor;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public List<Tags> getTags() {
        return this.tags;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean getFree() {
        return this.free;
    }

    public void setCoverUrl(String coverUrl) {
        this.cover_url = coverUrl;
    }

    public String getCoverUrl() {
        return this.cover_url;
    }

    public void setVideoCoverUrl(String videoCoverUrl) {
        this.video_cover_url = videoCoverUrl;
    }

    public String getVideoCoverUrl() {
        return this.video_cover_url;
    }

    public void setCoverPrimaryColor(String coverPrimaryColor) {
        this.cover_primary_color = coverPrimaryColor;
    }

    public String getCoverPrimaryColor() {
        return this.cover_primary_color;
    }

    public void setSoundUrl(String soundUrl) {
        this.sound_url = soundUrl;
    }

    public String getSoundUrl() {
        return this.sound_url;
    }

    public void setSoundSizeInBytes(int soundSizeInBytes) {
        this.sound_size_in_bytes = soundSizeInBytes;
    }

    public int getSoundSizeInBytes() {
        return this.sound_size_in_bytes;
    }

    public void setIs51(boolean is51) {
        this.is_51 = is51;
    }

    public boolean getIs51() {
        return this.is_51;
    }

    public void setIs71(boolean is71) {
        this.is_71 = is71;
    }

    public boolean getIs71() {
        return this.is_71;
    }

    public String toString() {
        return "MorelessInfo{id='" + this.id + "', type='" + this.type + "', name=" + this.name + ", subtitle=" + this.subtitle + ", description=" + this.description + ", descriptionAuthor=" + this.descriptionAuthor + ", tags=" + this.tags + ", free=" + this.free + ", cover_url='" + this.cover_url + "', video_cover_url='" + this.video_cover_url + "', cover_primary_color='" + this.cover_primary_color + "', sound_url='" + this.sound_url + "', sound_size_in_bytes=" + this.sound_size_in_bytes + ", is_51=" + this.is_51 + ", is_71=" + this.is_71 + '}';
    }

    /* loaded from: classes2.dex */
    public static class Name implements Serializable {
        private String en;
        private String zh_hans;
        private String zh_hant;

        public void setEn(String en) {
            this.en = en;
        }

        public String getEn() {
            return this.en;
        }

        public void setZh_hans(String zh_hans) {
            this.zh_hans = zh_hans;
        }

        public String getZh_hans() {
            return this.zh_hans;
        }

        public void setZh_hant(String zh_hant) {
            this.zh_hant = zh_hant;
        }

        public String getZh_hant() {
            return this.zh_hant;
        }
    }

    /* loaded from: classes2.dex */
    public static class Subtitle implements Serializable {
        private String en;
        private String zh_hans;
        private String zh_hant;

        public void setEn(String en) {
            this.en = en;
        }

        public String getEn() {
            return this.en;
        }

        public void setZh_hans(String zh_hans) {
            this.zh_hans = zh_hans;
        }

        public String getZh_hans() {
            return this.zh_hans;
        }

        public void setZh_hant(String zh_hant) {
            this.zh_hant = zh_hant;
        }

        public String getZh_hant() {
            return this.zh_hant;
        }
    }

    /* loaded from: classes2.dex */
    public static class Description implements Serializable {
        private String en;
        private String zh_hans;
        private String zh_hant;

        public void setEn(String en) {
            this.en = en;
        }

        public String getEn() {
            return this.en;
        }

        public void setZh_hans(String zh_hans) {
            this.zh_hans = zh_hans;
        }

        public String getZh_hans() {
            return this.zh_hans;
        }

        public void setZh_hant(String zh_hant) {
            this.zh_hant = zh_hant;
        }

        public String getZh_hant() {
            return this.zh_hant;
        }
    }

    /* loaded from: classes2.dex */
    public static class DescriptionAuthor implements Serializable {
        private String en;
        private String zh_hans;
        private String zh_hant;

        public void setEn(String en) {
            this.en = en;
        }

        public String getEn() {
            return this.en;
        }

        public void setZh_hans(String zh_hans) {
            this.zh_hans = zh_hans;
        }

        public String getZh_hans() {
            return this.zh_hans;
        }

        public void setZh_hant(String zh_hant) {
            this.zh_hant = zh_hant;
        }

        public String getZh_hant() {
            return this.zh_hant;
        }
    }

    /* loaded from: classes2.dex */
    public static class Tags implements Serializable {
        private String en;
        private String zh_hans;
        private String zh_hant;

        public void setEn(String en) {
            this.en = en;
        }

        public String getEn() {
            return this.en;
        }

        public void setZh_hans(String zh_hans) {
            this.zh_hans = zh_hans;
        }

        public String getZh_hans() {
            return this.zh_hans;
        }

        public void setZh_hant(String zh_hant) {
            this.zh_hant = zh_hant;
        }

        public String getZh_hant() {
            return this.zh_hant;
        }
    }
}
