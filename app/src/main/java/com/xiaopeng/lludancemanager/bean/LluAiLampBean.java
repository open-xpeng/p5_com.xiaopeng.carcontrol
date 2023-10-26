package com.xiaopeng.lludancemanager.bean;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes2.dex */
public class LluAiLampBean {
    @SerializedName("id")
    private int mIndex;
    @SerializedName("lamp_beam_bg")
    private String mLampBg;
    private int mLampBgResId;
    @SerializedName("note_audio")
    private String mNoteAudio;
    private int mNoteAudioResId;
    @SerializedName("note_text")
    private String mNoteText;
    private int mNoteTextResId;
    @SerializedName("lamp_beam_pos")
    private int mPos;
    @SerializedName("lamp_beam_width")
    private int mWidth;

    public int getIndex() {
        return this.mIndex;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    public int getPos() {
        return this.mPos;
    }

    public void setPos(int pos) {
        this.mPos = pos;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public String getLampBg() {
        return this.mLampBg;
    }

    public void setLampBg(String lampBg) {
        this.mLampBg = lampBg;
    }

    public String getNoteAudio() {
        return this.mNoteAudio;
    }

    public void setNoteAudio(String noteAudio) {
        this.mNoteAudio = noteAudio;
    }

    public String getNoteText() {
        return this.mNoteText;
    }

    public void setNoteText(String noteText) {
        this.mNoteText = noteText;
    }

    public int getLampBgResId() {
        return this.mLampBgResId;
    }

    public void setLampBgResId(int lampBgResId) {
        this.mLampBgResId = lampBgResId;
    }

    public int getNoteAudioResId() {
        return this.mNoteAudioResId;
    }

    public void setNoteAudioResId(int noteAudioResId) {
        this.mNoteAudioResId = noteAudioResId;
    }

    public int getNoteTextResId() {
        return this.mNoteTextResId;
    }

    public void setNoteTextResId(int noteTextResId) {
        this.mNoteTextResId = noteTextResId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LluAiLampBean{");
        sb.append("mIndex=").append(this.mIndex);
        sb.append(", mPos=").append(this.mPos);
        sb.append(", mWidth=").append(this.mWidth);
        sb.append(", mLampBg='").append(this.mLampBg).append('\'');
        sb.append(", mLampBgResId=").append(this.mLampBgResId);
        sb.append(", mNoteAudio='").append(this.mNoteAudio).append('\'');
        sb.append(", mNoteAudioResId=").append(this.mNoteAudioResId);
        sb.append(", mNoteText='").append(this.mNoteText).append('\'');
        sb.append(", mNoteTextResId=").append(this.mNoteTextResId);
        sb.append('}');
        return sb.toString();
    }
}
